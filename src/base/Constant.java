package base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * 常量
 *
 */
public class Constant {
	private Constant() {
	}

	/**
	 * 编码格式
	 */
	public static final String CHARSETFORUTF8 = "UTF-8";
	/**
	 * 多数据源默认使用的名称(一般项目都只会用一个数据库连接)
	 */
	private static final String DEFAULT_DRUID_DATASOURCE_NAME = "default";
	/**
	 * testcookie能保存的最长时间(5分钟)
	 */
	public static final int TESTMAXCOOKIEAGE = 1000 * 60 * 5;
	/**
	 * cookie能保存的最长时间(一个月)
	 */
	public static final int MAXCOOKIEAGE = 60 * 60 * 60;// 一个小时
	/**
	 * cookieName
	 */
	public static final String COOKIENAME = "_userinfo";

	/**
	 * 数据库默认受影响的条数(无法正常更新返回的结果)
	 */
	public static final int DBEFFECTROWNUMBER = -10;
	
	//常用的一些setContentType需要的值
	
	public static final String XML  = "text/xml";
	public static final String HTML = "text/html";
	public static final String TEXT  = "text/plain";
	public static final String PDF  = "application/pdf";
	public static final String JSON  = "application/json";
	
}
