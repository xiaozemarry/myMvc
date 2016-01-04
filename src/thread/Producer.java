package thread;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {
 
    private final BlockingQueue sharedQueue;
    private synchronized static int getIndex(){
    	return index++;
    }
    private static int index = 1;
    public Producer(BlockingQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }
 
    @Override
    public void run() {
    	while(true)
    	  {
                try 
                {
                	Thread.sleep(5000);
					sharedQueue.put(getIndex());
					if(index%10==0)Thread.sleep(5000);
					if(index>=500)break;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
        }
}