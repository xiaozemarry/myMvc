package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

public class NewMain {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
		final int nThreads = 10;
		final ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
		final Semaphore semaphore = new Semaphore(nThreads);
		try {
			ServerSocket serverSocket = new ServerSocket(5000);
			SocketHandler<Object> task = null;
			while (true) {
				Socket clientSocket = serverSocket.accept();
				task = new SocketHandler<Object>(clientSocket, semaphore);
				//task.call();
				executor.submit(task);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
