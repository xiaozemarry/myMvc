package utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.Validate;

public class DateUtils {
	private DateUtils() {
	}

	/**
	 * 常用的日期格式
	 */
	public static final String[] PARTTERN = new String[] { "yyyy年MM月dd日HH时mm分ss秒ms毫秒", "yyyy-MM-dd HH:mm:ss ms",
			"yyyy年MM月dd日HH时mm分ss秒", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss", "yyyyMMddHHmmss", "yyyy年MM月dd日",
			"yyyy/MM/dd", "yyyy-MM-dd", };

	/**
	 * 获取格式化的时分秒微秒(通常用于显示耗时)
	 * 
	 * @param ms
	 *            时间差
	 * @return
	 * @throws ParseException
	 */
	public static String getMarks(int ms) throws ParseException {
		final Date baseDate = parseToDate("2015-01-01 00:00:00");
		Date addSeconds = org.apache.commons.lang.time.DateUtils.addMilliseconds(baseDate, ms);
		Calendar instance = Calendar.getInstance();
		instance.setTime(addSeconds);
		int hou = instance.get(Calendar.HOUR);// 时
		int min = instance.get(Calendar.MINUTE);// 分
		int sec = instance.get(Calendar.SECOND);// 秒
		int minsec = instance.get(Calendar.MILLISECOND);// 毫 秒
		return String.format("%s时%s分%s秒%s微秒", new Object[] { hou, min, sec, minsec });
	}

	/**
	 * 获取格式化的时分秒微秒,如果参数大于Integer.MAX_VALUE。返回null(通常用于显示耗时)
	 * 
	 * @param ms
	 *            时间差
	 * @return
	 * @throws ParseException
	 */
	public static String getMarks(long ms) throws ParseException {
		if (ms > Integer.MAX_VALUE) {
			return null;
		}
		return getMarks((int) ms);
	}

	public static Date parseToDate(final String date) throws NullPointerException , ParseException {
		Validate.notNull(date);
		final Date parseDate = org.apache.commons.lang.time.DateUtils.parseDate(date, PARTTERN);
		return parseDate;
	}

	public static long parseToLong(final String date) throws NullPointerException, ParseException {
		return parseToDate(date).getTime();
	}

	public static Calendar parseToCalendar(final String date) throws NullPointerException, ParseException {
		final Calendar instance = Calendar.getInstance();
		instance.setTime(parseToDate(date));
		return instance;
	}
	
	public static java.sql.Date parseToSqlDate(final String date) throws NullPointerException, ParseException {
		final java.sql.Date sqlDate = new java.sql.Date(parseToLong(date));
		return sqlDate;
	}
	/**
	 * 把当前时间格式化为yyyy年MM月dd日HH时mm分ss秒
	 * @return
	 * 
	 */
	public static String formatNow(){
		return DateFormatUtils.format(new Date(),PARTTERN[2]);
	}
	
	public static String formatNow(final String parttern){
		return DateFormatUtils.format(new Date(),parttern);
	}
}
