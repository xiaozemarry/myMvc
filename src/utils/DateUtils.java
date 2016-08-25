package utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import base.Constant;


public class DateUtils {
	/**
	 * 获取格式化的时分秒微秒(通常用于显示耗时)
	 * @param ms 时间差
	 * @return
	 * @throws ParseException
	 */
	public static String getMarks(int ms) throws ParseException {
		Date baseDate = Constant.SDFYMDHMS.parse("2015-01-01 00:00:00");
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
	 * 获取格式化的时分秒微秒(通常用于显示耗时)
	 * @param ms 时间差
	 * @return
	 * @throws ParseException
	 */
	public static String getMarks(long ms) throws ParseException {
		if(ms>Integer.MAX_VALUE){
			 return null;
		}
		return getMarks((int)ms);
	}
}
