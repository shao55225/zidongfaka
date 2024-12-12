package com.xunpay.money.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 时间公用类
 * @author Administrator
 *
 */
public class DateUtils {
    
	private static final DateFormat yyyyMM = new SimpleDateFormat("yyyMM");
	private static final DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final DateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String get(Date date, String pattern) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(pattern).format(date);
	}
	
	public static Date parse(String dateStr, String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getYmd(Date date) {
		if (date == null) {
			return "";
		}
		return yyyyMMdd.format(date);
	}
	
	public static String getYm(Date date) {
		if (date == null) {
			return "";
		}
		return yyyyMM.format(date);
	}
	
	public static Date parseYmd(String dateStr) {
		try {
			return yyyyMMdd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getYmdHmi(Date date) {
		if (date == null) {
			return "";
		}
		return yyyyMMddHHmm.format(date);
	}
	
	public static Date parseYmdHmi(String dateStr) {
		try {
			return yyyyMMddHHmm.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getYmdHmis(Date date) {
		if (date == null) {
			return "";
		}
		return yyyyMMddHHmmss.format(date);
	}
	
	public static Date parseYmdHmis(String dateStr) {
		try {
			return yyyyMMddHHmmss.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int getDays(Date begin, Date end) {
		begin = parseYmd(getYmd(begin));
		end = parseYmd(getYmd(end));
		return (int) ((end.getTime() - begin.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 生成日期随机序列码
	 * @param fix	前缀
	 * @param pattern  日期格式
	 * @param randCount  后面几位随机码
	 * @return 
	 * 	如: getDateSerializable("P", "yyyyMMdd", 3); <br />
	 *  结果: P20140820xxx    x为随机数
	 */
	public static String getDateSerializable(String fix, String pattern, int randCount) {
		return StringUtils.trimToEmpty(fix) + get(new Date(), pattern) + RandomUtils.randomNumber(randCount);
	}
	
}
