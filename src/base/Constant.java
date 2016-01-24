package base;

import java.text.SimpleDateFormat;

/**
 * 常量
 *
 */
public class Constant {
	private Constant(){}
	/**
	 * testcookie能保存的最长时间(5分钟)
	 */
   public static final int TESTMAXCOOKIEAGE = 1000*60*5;
	/**
	 * cookie能保存的最长时间(一个月)
	 */
    public static final int MAXCOOKIEAGE = 60*60;//一个小时
   /**
    * cookieName
    */
   public static final String COOKIENAME = "_userinfo";
   /**
    * 格式化时间
    * eg:2015-05-05 12:12:12
    */
   public static final SimpleDateFormat SDFYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   /**
    * 格式化时间
    * eg:2015-05-05
    */
   public static final SimpleDateFormat SDFYMD = new SimpleDateFormat("yyyy-MM-dd");
   /**
    * 格式化时间
    * eg:2015/05/05 12:12:12
    */
   public static final SimpleDateFormat SDFYMDHMS1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
   /**
    * 格式化时间
    * eg:2015/05/05
    */
   public static final SimpleDateFormat SDFYMD1 = new SimpleDateFormat("yyyy/MM/dd");
}
