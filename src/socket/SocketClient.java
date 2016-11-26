package socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SocketClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		int nThreads = 100;
		final ExecutorService executor = Executors.newFixedThreadPool(nThreads,new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread my = new Thread(r);
				my.setName("socket-client-"+UUID.randomUUID());
				return my;
			}
		});
		for (int i = 0; i < nThreads; i++) {
			Runnable task = new Runnable() {
				@Override
				public void run() {
					Socket socket;
					try {
						socket = new Socket("127.0.0.1", 5000);
						OutputStream outputStream = socket.getOutputStream();
						String inputStr = "客户端数据"+Thread.currentThread().getName(); 
						//System.out.println(inputStr);
						outputStream.write(inputStr.getBytes());
						outputStream.flush();
						socket.shutdownOutput();
						
						InputStream input = socket.getInputStream();
						byte[] buffer = new byte[2048];
						int readBytes = 0;
						StringBuilder stringBuilder = new StringBuilder();
						while ((readBytes = input.read(buffer)) > 0) {
							stringBuilder.append(new String(buffer, 0, readBytes));
						}
						socket.shutdownInput();
						System.out.println("服务器数据:"+stringBuilder);
						socket.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			executor.submit(task);
		}
		executor.shutdown();
	}
}
