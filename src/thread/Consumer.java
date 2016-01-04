package thread;

import java.util.concurrent.BlockingQueue;

public class Consumer  implements Runnable{
	
    private final BlockingQueue sharedQueue;
 
    public Consumer (BlockingQueue sharedQueue) {
        this.sharedQueue = sharedQueue;
    }
 
    @Override
    public void run() {
        //while(sharedQueue.size()>0){
    	while(true){
            try 
            {
            	//if(sharedQueue.size()==0)break;
            	Thread.sleep(100);
            	System.out.println("消费:"+sharedQueue.take());
               // System.out.println("消费: "+ sharedQueue.take());
            } catch (InterruptedException e) 
            {
            	e.printStackTrace();
            }
        }
    }
 
}