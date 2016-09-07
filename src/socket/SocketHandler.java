package socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class SocketHandler<V> implements Callable<V> {
	private Socket socket;
	private Semaphore semaphore;

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	public void setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public SocketHandler(Socket socket, Semaphore semaphore) {
		super();
		this.socket = socket;
		this.semaphore = semaphore;
	}

	@Override
	public V call() throws Exception {
		try {
			this.getSemaphore().acquire();
			int value = new Random().nextInt(2000);
			System.out.println("waiting for:" + value+"s");
			Thread.sleep(value);
			InputStream input = this.getSocket().getInputStream();
			
		    byte[] buffer = new byte[2048];
	        int readBytes = 0;
	        StringBuilder stringBuilder = new StringBuilder();
	        while((readBytes = input.read(buffer)) > 0){
	            stringBuilder.append(new String(buffer, 0, readBytes));
	        }
	        
			OutputStream output = this.getSocket().getOutputStream();
			String val =  stringBuilder.toString();
			output.write(val.getBytes());
			output.flush();
			
			this.getSocket().shutdownInput();
			this.getSocket().shutdownOutput();
			System.out.println("writed data finish,waitting for next socket client\n");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.getSemaphore().release();
		}
		return null;
	}

}
