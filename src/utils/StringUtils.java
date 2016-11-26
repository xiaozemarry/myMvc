package utils;

import java.io.UnsupportedEncodingException;

import base.Constant;

public class StringUtils {
	/**
	 * shoule not be init
	 */
	private StringUtils() {
	}

	public static byte[] getByte(String str) {
		str = str == null?"null":str;
		try {
			return str.getBytes(Constant.CHARSETFORUTF8);
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		return null;
	}
	
	public static byte[] getByte(String str,String chartSet) {
		str = str == null?"null":str;
		chartSet = chartSet==null?Constant.CHARSETFORUTF8:chartSet;
		try {
			return str.getBytes(Constant.CHARSETFORUTF8);
		} catch (UnsupportedEncodingException e) {
			// ignore
		}
		return null;
	}
}
