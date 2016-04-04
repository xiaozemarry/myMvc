package tools;

import org.apache.commons.lang.StringUtils;


public class StringTools {
	   private static final String NULL = "undefined";
	   private static final String UNDEFINED = "null";
	   private static StringTools instance = new StringTools();
	   private StringTools(){}
	   public synchronized static StringTools instance(){
		   if(instance==null)instance = new StringTools();
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
}
