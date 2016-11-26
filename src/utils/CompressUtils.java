package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

/**
 * 解压缩辅助类
 * 
 * @author @me
 *
 */
public class CompressUtils {
     
	/**压缩
	 * 
	 * @param target 需要压缩的文件或者文件夹
	 * @param zipFilePath压缩后所在的位置
	 */
	public static void compressFiles(final File target,final File compressFilePath){
		if(!target.exists() || !compressFilePath.exists()){
			
		}else{
			if(target.isFile()){
				compressFiles2Zip(new File[]{target},compressFilePath.getAbsolutePath());
			}else{
				compressFiles2Zip(target.listFiles(),compressFilePath.getAbsolutePath());
			}
		}
	}

	/**
	 * 把文件压缩成zip格式
	 * 
	 * @param files
	 *            需要压缩的文件
	 * @param zipFilePath
	 *            压缩后的zip文件路径 ,如"D:/test/aa.zip";
	 */
	public static void compressFiles2Zip(File[] files, String zipFilePath) {
		if (files != null && files.length > 0) {
			if (isEndsWithZip(zipFilePath)) {
				ZipArchiveOutputStream zaos = null;
				try {
					File zipFile = new File(zipFilePath);
					zaos = new ZipArchiveOutputStream(zipFile);
					// Use Zip64 extensions for all entries where they are
					// required
					zaos.setUseZip64(Zip64Mode.AsNeeded);
					// 将每个文件用ZipArchiveEntry封装
					// 再用ZipArchiveOutputStream写到压缩文件中
					for (File file : files) {
						if (file != null) {
							ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(file, file.getName());
							zaos.putArchiveEntry(zipArchiveEntry);
							InputStream is = null;
							try {
								is = new FileInputStream(file);
								byte[] buffer = new byte[1024 * 5];
								int len = -1;
								while ((len = is.read(buffer)) != -1) {
									// 把缓冲区的字节写入到ZipArchiveEntry
									zaos.write(buffer, 0, len);
								}
								// Writes all necessary data for this entry.
								zaos.closeArchiveEntry();
							} catch (Exception e) {
								throw new RuntimeException(e);
							} finally {
								if (is != null)
									is.close();
							}

						}
					}
					zaos.finish();
				} catch (Exception e) {
					throw new RuntimeException(e);
				} finally {
					try {
						if (zaos != null) {
							zaos.close();
						}
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}

			}

		}

	}
	/**
	* 判断文件名是否以.zip为后缀
	* @param fileName 需要判断的文件名
	* @return 是zip文件返回true,否则返回false
	*/
	public static boolean isEndsWithZip(String fileName) {
	boolean flag = false;
	if(fileName != null && !"".equals(fileName.trim())) {
	if(fileName.endsWith(".ZIP")||fileName.endsWith(".zip")){
		flag = true;
	   }
	}
	return flag;
	}
}
