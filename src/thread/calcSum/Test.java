package thread.calcSum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public class Test {
	public static void main(String[] args) throws Exception {
		    final Semaphore semaphore = new Semaphore(2,true);//每次只允许2个工人用餐厅
			final int poolSize = 6;
			ExecutorService executorService1 = Executors.newFixedThreadPool(poolSize);
			for (int i = 0; i < poolSize; i++) 
			{
				executorService1.execute(new Worker("张三"+i,semaphore));
			}
			executorService1.shutdown();
		if(1==1)return;
//	    ExecutorService pool = Executors.newFixedThreadPool(5);
//	    final long waitTime = 8 * 1000;
//	    final long awaitTime = 5 * 1000;
//	 
//	    Runnable task1 = new Runnable(){
//	        public void run(){
//	            try {
//	                System.out.println("task1 start");
//	                Thread.sleep(waitTime);
//	                System.out.println("task1 end");
//	            } catch (InterruptedException e) {
//	                System.out.println("task1 interrupted: " + e);
//	            }
//	        }
//	    };
//	 
//	    Runnable task2 = new Runnable(){
//	        public void run(){
//	            try {
//	                System.out.println("  task2 start");
//	                Thread.sleep(1000);
//	                System.out.println("  task2 end");
//	            } catch (InterruptedException e) {
//	                System.out.println("task2 interrupted: " + e);
//	            }
//	        }
//	    };
//	    // 让学生解答某个很难的问题
//	    pool.execute(task1);
//	 
//	    // 生学生解答很多问题
//	    for(int i=0; i<1000; ++i){
//	        pool.execute(task2);
//	    }
//	 
//	    try {
//	        // 向学生传达“问题解答完毕后请举手示意！”
//	        pool.shutdown();
//	 
//	        // 向学生传达“XX分之内解答不完的问题全部带回去作为课后作业！”后老师等待学生答题
//	        // (所有的任务都结束的时候，返回TRUE)
//	        if(!pool.awaitTermination(awaitTime, TimeUnit.MILLISECONDS)){
//	            // 超时的时候向线程池中所有的线程发出中断(interrupted)。
//	            pool.shutdownNow();
//	        }
//	    } catch (InterruptedException e) {
//	        // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
//	        System.out.println("awaitTermination interrupted: " + e);
//	        pool.shutdownNow();
//	    }
//	 
//	    System.out.println("end!!!!!!!!!!!!!!!!!!");
//		if(1==1)return;
		int target[] = new int[50];
		for (int i = 0; i < target.length; i++) 
		{
			target[i] = 1;
		}
		
		//List<Future<Long>> tasks = new ArrayList<Future<Long>>();
		ExecutorService executorService = null;
		executorService = Executors.newFixedThreadPool(4);
		List<Callable<Long>> callableList = new ArrayList<Callable<Long>>();
		for (int i = 0; i < 10; i++) 
		{
			Callable<Long> callable = new RealCalcSum(target);
			callableList.add(callable);
			//FutureTask<Long> future = new FutureTask<Long>(callable);
            //executorService.submit(future);
			//tasks.add(future);
		}
		List<Future<Long>> tasks = executorService.invokeAll(callableList);
		executorService.shutdown();
		Long reault = 0L;
		for(Future<Long> future:tasks)
		{
			reault +=future.get();
		}
		System.out.println(reault);
		System.out.println(reault);
		//executorService.awaitTermination(100,TimeUnit.MILLISECONDS);
		while(!executorService.isTerminated())
		{
		    //System.out.println("Thread Running!!!");
		}
		System.out.println("All Threads Finish!!!");
	}
}
