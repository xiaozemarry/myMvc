package thread.calcSum;

import java.util.concurrent.Semaphore;

import org.apache.commons.lang.math.RandomUtils;

public class Worker implements Runnable {
	private String name;
	private Semaphore semaphore;
	private Worker(){}
	public Worker(String name,Semaphore semaphore){
		this.name=name;
		this.semaphore = semaphore;
	}

	@Override
	public void run() {
		try 
		{
			semaphore.acquire();
			//Thread.sleep(RandomUtils.nextInt(5000));
			Thread.sleep(RandomUtils.nextInt(2000));
			System.out.println(this.name+":"+"do Working!!!剩余:"+this.semaphore.availablePermits());
			semaphore.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
