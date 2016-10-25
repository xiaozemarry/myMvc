package base;

import java.text.SimpleDateFormat;

/**
 * 常量
 *
 */
public class Constant {
   private Constant(){}
   public static final String CHARSETFORUTF8 = "UTF-8";
   /**
    * 多数据源默认使用的名称(一般项目都只会用一个数据库连接)
    */
   private static final String DEFAULT_DRUID_DATASOURCE_NAME = "default";
	/**
	 * testcookie能保存的最长时间(5分钟)
	 */
   public static final int TESTMAXCOOKIEAGE = 1000*60*5;
	/**
	 * cookie能保存的最长时间(一个月)
	 */
    public static final int MAXCOOKIEAGE = 60*60*60;//一个小时
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
   /**
    * 格式化时间
    * eg:20150505121212
    */
   public static final SimpleDateFormat SDFYMDHMSA = new SimpleDateFormat("yyyyMMddHHmmss");
   
   /**
    * 数据库默认受影响的条数(无法正常更新返回的结果)
    */
   public static final int DBEFFECTROWNUMBER = -10;
}
