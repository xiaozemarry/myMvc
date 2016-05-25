package tools;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang.StringUtils;


public class StringTools {
	   private static final String NULL = "undefined";
	   private static final String UNDEFINED = "null";
	   private static byte[] lock = new byte[0];
	   private static StringTools instance = null;
	   private StringTools(){}
	   public  static StringTools instance(){
		   if(instance==null)
		   {
			   synchronized (lock) 
			   {
				   if(instance == null )
				   {
					   System.out.println(Thread.currentThread().getName());
					   instance = new StringTools();
				   }
			   }
		   }
		   return instance;
	   }
	   /**
	    * 非法字符串包括(null,undefined,有空格)
	    * @param str 需要判断的字符串
	    * @return true:非法
	    */
	   public boolean isIllegalStr(String str){
		   if(str==null)return true;
		   String trimStr = StringUtils.trimToNull(str);
		   if(
			  trimStr==null ||
			  StringTools.UNDEFINED.equalsIgnoreCase(trimStr)||
			  StringTools.NULL.equalsIgnoreCase(trimStr)
			 )
		   return true;
		   return false;
	   }
	   
	   public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					 StringTools stl = StringTools.instance();
				}
			});
			t.start();
			try
			{
				t.wait(200);
				Thread.sleep(200);
			} catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
