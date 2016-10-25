package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import base.Constant;

public class NIOUtils {
	private static final Logger logger = Logger.getLogger(NIOUtils.class);

	/**
	 * shoule not be init
	 */
	private NIOUtils() {
	}

	public static boolean write(final File out, final byte[] array, final boolean append) throws IOException {
		Validate.notNull(out);
		final RandomAccessFile accessFile = new RandomAccessFile(out, "rw");
		final FileChannel channel = accessFile.getChannel();
		final long size = channel.size();
		if (append) {
			accessFile.seek(size);// 追加内容
		}
		boolean result = write(channel, array, append);
		close(accessFile);
		return result;
	}

	/**
	 * 真实写入,auto close FileChannel
	 * 
	 * @param channel
	 * @param array
	 * @throws IOException
	 */
	public static boolean write(final FileChannel channel, final byte[] array, final boolean append)
			throws IOException,NullPointerException {
		Validate.notNull(channel);
		Validate.notNull(array);
		if (!append) {
			channel.truncate(0);// 清空内容
		}
		FileLock lock = null;
		try {
			lock = tryLock(channel);
			if (lock != null) {
				ByteBuffer buffer = ByteBuffer.wrap(array);
				channel.write(buffer);
				channel.force(true);// 确认刷新进去
			} else {
				logger.info("无法获取独占锁");
				return false;
			}
		} catch (Exception e) {
			logger.error("tryLock", e);
		} finally {
			release(lock, channel);
			close(channel);
		}
		return true;
	}

	public static boolean write(final File out, String contents, final boolean append)
			throws UnsupportedEncodingException, IOException {
		contents = contents == null ? "null" : contents;
		return write(out, contents.getBytes(Constant.CHARSETFORUTF8), append);
	}

	public static boolean write(final String outPath, final String contents, final boolean append)
			throws UnsupportedEncodingException, IOException {
		Validate.notNull(outPath);
		final File out = new File(outPath);
		return write(out, contents.getBytes(Constant.CHARSETFORUTF8), append);
	}

	/**
	 * 如果要追加内容,out变量构造函数时必须设置为追加
	 * 
	 * @param out
	 * @param contents
	 * @param append
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static void write(final FileOutputStream out, final String contents, final boolean append)
			throws UnsupportedEncodingException, IOException,NullPointerException {
		Validate.notNull(out);
		final FileChannel channel = out.getChannel();
		write(channel, StringUtils.getByte(contents), append);
		close(out);
	}

	/**
	 * 根据路径获取文件内容 <br/>
	 * read only,thread safe
	 * 
	 * @param filePath
	 * @return 文件内容
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static String read(final String filePath) throws IOException, FileNotFoundException {
		final File file = new File(filePath);
		return read(file);
	}

	/**
	 * 根据文件获取文件内容 <br/>
	 * read only,thread safe
	 * 
	 * @param filePath
	 * @return 文件内容
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static String read(final File file) throws IOException, FileNotFoundException,NullPointerException {
		if (!file.exists())
			throw new FileNotFoundException(file.getParent() + "-->not found。");
		final RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
		final FileChannel fileChannel = randomAccessFile.getChannel();
		int capacity = 1024;
		final long fileSize = fileChannel.size();
		capacity = capacity > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) fileSize;
		final StringBuilder sb = new StringBuilder((int) fileSize);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);// 每次读取1M,DirectBuffer的效率会更高。
		int length = 0;
		while ((length = fileChannel.read(byteBuffer)) > 0) {
			byteBuffer.flip();
			final byte[] bytes = byteBuffer.array();
			final String item = new String(bytes, 0, length, Constant.CHARSETFORUTF8);
			sb.append(item);
			byteBuffer.clear();// 复用
		}
		fileChannel.close();
		randomAccessFile.close();
		return sb.toString();
	}

	/**
	 * 获取文件锁,返回null,获取锁失败,一般配合该类的release方法一起使用 <br/>
	 * 写入文件内容的时候必须使用该类获取锁然后释放
	 * 
	 * @param fileChannel
	 * @return
	 * @throws ClosedChannelException
	 */
	public static FileLock tryLock(final FileChannel fileChannel) throws ClosedChannelException {
		FileLock lock = null;
		try {
			lock = fileChannel.tryLock();
		} catch (IOException e) {
			logger.error("IOException", e);
		} catch (OverlappingFileLockException e) {
			// logger.error("case OverlappingFileLockException", e);
			logger.error("overlappingFileLock");
		}
		return lock;
	}

	/**
	 * 获取文件某一部分的锁,返回null,获取锁失败,一般配合该类的release方法一起使用 <br/>
	 * 写入文件内容的时候必须使用该类获取锁然后释放
	 * 
	 * @param fileChannel
	 * @param position
	 *            起始位置,必须大于0,否则锁无效
	 * @param size
	 *            从position开始之后的文件大小,必须大于0,否则锁无效
	 * @return
	 * @throws ClosedChannelException
	 */
	public static FileLock tryLock(final FileChannel fileChannel, final long position, final long size)
			throws ClosedChannelException {
		FileLock lock = null;
		if (position <= 0 || size <= 0) {
			return lock;
		}

		try {
			lock = fileChannel.tryLock();
		} catch (IOException e) {
			logger.error("case IOException", e);
		} catch (OverlappingFileLockException e) {
			// logger.error("case OverlappingFileLockException", e);
			logger.error("overlappingFileLock");
		}
		return lock;
	}

	public static void release(final FileLock fileLock, final FileChannel fileChannel) {
		if (fileLock != null) {
			try {
				fileLock.release();
			} catch (IOException e) {
				// ignore
			}
		}
		close(fileChannel);
	}

	public static void close(final FileChannel fileChannel) {
		if (fileChannel != null) {
			try {
				fileChannel.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	public static void close(final RandomAccessFile accessFile) {
		if (accessFile != null) {
			try {
				accessFile.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

	public static void close(final OutputStream output) {
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}
}
