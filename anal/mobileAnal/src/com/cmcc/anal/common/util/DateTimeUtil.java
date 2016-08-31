package com.cmcc.anal.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 日期处理辅助类
 * 
 * @author jiangf
 */
public class DateTimeUtil {
	public static final String LONGTIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String MEDIATIME_PATTERN = "yyyy-MM-dd";

	public static final String SHORTTIME_PATTERN = "HH:mm:SS";

	public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmmss";

	public static boolean isDateTime(String dt) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		try {
			sdf.parse(dt);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isDateTime(String dt, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			sdf.parse(dt);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 * @param dateStr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String dateStr, String pattern) throws ParseException {
		if (StringUtils.isBlank(pattern) || StringUtils.isBlank(dateStr) 
				|| dateStr.length() != dateStr.length()){
			throw new ParseException("can parse the date string " + dateStr + " with the pattern " + pattern, 0);
		}
		
		SimpleDateFormat parser = new SimpleDateFormat();
		parser.applyPattern(pattern);
		return parser.parse(dateStr);
	}

	/**
	 * 得到指定日期的字符串格式：YYYY-MM-DD HH:mm:SS.ms
	 * 
	 * @param dt
	 *            指定的日期
	 * @return
	 */
	public static String getFullTime(Calendar dt) {
		return formatCalendar(dt, LONGTIME_PATTERN);
	}

	/**
	 * 得到当前日期的字符串格式：YYYY-MM-DD HH:mm:SS.ms
	 * 
	 * @return
	 */
	public static String getFullTime() {
		return getFullTime(Calendar.getInstance());
	}

	/**
	 * 得到指定日期的字符串格式：YYYYMMDDHHMMSS
	 * 
	 * @param dt
	 *            指定的日期
	 * @return
	 */
	public static String getTimestamp(Calendar dt) {
		return formatCalendar(dt, TIMESTAMP_PATTERN);
	}

	/**
	 * 得到当前日期的字符串格式：YYYYMMDDHHMMSS
	 * @return
	 */
	public static String getTimestamp() {
		return getTimestamp(Calendar.getInstance());
	}

	/**
	 * 得到短日期格式：HH:mm:SS.ms
	 * 
	 * @param datetime
	 *            日期
	 * @return
	 */
	public static String getShortTime(Calendar datetime) {
		return formatCalendar(datetime, SHORTTIME_PATTERN);
	}

	/**
	 * 得到当前日期短日期格式：HH:mm:SS.ms
	 * 
	 * @return
	 */
	public static String getShortTime() {
		return getShortTime(Calendar.getInstance());
	}

	/**
	 * 得到日期格式：YYYY-MM-DD
	 * 
	 * @param datetime
	 *            日期
	 * @return
	 */
	public static String getMediumTime(Calendar datetime) {
		return formatCalendar(datetime, MEDIATIME_PATTERN);
	}

	/**
	 * 得到当前日期的年月日格式：YYYY-MM-DD
	 * 
	 * @return
	 */
	public static String getMediumTime() {
		return getMediumTime(Calendar.getInstance());
	}

	/**
	 * 比较两个日期的早晚，日期格式为：yyyy-MM－dd
	 * 
	 * @param date1
	 *            日期1
	 * @param date2
	 *            日期2
	 * @return 如果日期1晚于日期2，则返回大于0；如果日期1等日期2，则返回0；如果日期1早于日期2，则返回小于0
	 */
	public static int compareDate(String date1, String date2) {
		Date dt1 = toDate(date1);
		Date dt2 = toDate(date2);
		return dt1.compareTo(dt2);
	}

	/**
	 * 得到当前年份
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 得到当前月份
	 * 
	 * @return
	 */
	public static int getCurrentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 得到当前日
	 * 
	 * @return
	 */
	public static int getCurrentDay() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 格式化给定时间
	 * 
	 * @param cal
	 *            给定时间
	 * @param pattern
	 *            要格式化的模式
	 * @return 格式化后的字符串
	 */
	public static String formatCalendar(Calendar cal, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(cal.getTime());
	}

	/**
	 * 将字符串(yyy-MM-dd)转化为日期
	 * 
	 * @param strDate
	 *            待转化的日期字符串
	 * @return 日期对象,如果字符串格式非法，则返回null
	 * @throws
	 */
	public static Date toDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(MEDIATIME_PATTERN);
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将字符串(yyy-MM-dd)转化为日期
	 * 
	 * @param strDate
	 *            待转化的日期字符串
	 * @return 日期对象,如果字符串格式非法，则返回null
	 * @throws
	 */
	public static Date toDateTime(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(LONGTIME_PATTERN);
		try {
			return sdf.parse(strDate);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将字符串(yyy-MM-dd)转化为日期
	 * 
	 * @param strDate
	 *            待转化的日期字符串
	 * @return 日期对象,如果字符串格式非法，则返回null
	 * @throws
	 */
	public static Calendar toCalendar(String strDate) {
		Date dt = toDate(strDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal;
	}

	public static String getYesterday() {
		return getYesterday(MEDIATIME_PATTERN);
	}

	public static String getYesterday(String PATTERN) {
		Calendar cale = Calendar.getInstance();
		cale.add(Calendar.DATE, -1);
		return formatCalendar(cale, PATTERN);
	}

	public static Date getNextDay(Date today) {
		Calendar cale = Calendar.getInstance();
		cale.setTime(today);
		cale.add(Calendar.DATE, 1);
		return cale.getTime();
	}

	public static void main(String[] args) {
		System.out.println(getYesterday(LONGTIME_PATTERN));
		System.out.println(getTimestamp(new Date()));
	}

	public static long getTwoDay(Date date, Date mydate) {
		try {

			long seconds = (date.getTime() - mydate.getTime()) / 1000;

			long day = seconds / (24 * 60 * 60); // 相差的天数
			long hour = (seconds) / (60 * 60);// 相差的小时数
			long minut = (seconds) / (60);// 相差的分钟数

			if (minut < 0) {
				minut = minut * -1;
			}

			return minut;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 对所提供的日期时间进行年、月、日的累加
	 * 
	 * @param aDate
	 *            原始日期 格式必需为：yyyyMMddHHmmss
	 * @param Day
	 *            需要累加的天数
	 * @param aYear
	 *            需要累加的年数
	 * @param amonth
	 *            需要累加的月数
	 * @return 累加完成的时间 yyyyMMdd
	 */

	public static String getAddDay14(String aDate, int aYear, int aMonth, int aDay) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		if (aDate == null || aDate.length() != 10) {
			return "";
		}
		int tYear = Integer.parseInt(aDate.substring(0, 4));
		int tMonth = Integer.parseInt(aDate.substring(5, 7));
		int tDay = Integer.parseInt(aDate.substring(8, 10));

		calendar.set(tYear, tMonth, tDay);
		calendar.add(Calendar.DATE, aDay);
		calendar.add(Calendar.MONTH, aMonth - 1);
		calendar.add(Calendar.YEAR, aYear);

		return df.format(calendar.getTime());
	}

	/** 获取两个时间间隔,精确到秒 */
	public static long getTwoDaySecond(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);

			long second = (date.getTime() - mydate.getTime()) / 1000;

			return second;
		} catch (Exception e) {
			return 0;
		}
	}

	//日期加分钟后的日期代码
	public static Date addDateMinut(String day, int x) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制  
		Date date = null;
		try {
			date = format.parse(day);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (date == null)
			return null;

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, x);// 24小时制  
		date = cal.getTime();
		cal = null;
		return date;

	}
	
	/**
	 *  Function:
	 *			yyyy-MM-dd HH:mm:ss 日期格式化 
	 *  @author zhijide@pica.com  DateTime 2012-10-19 下午07:05:48
	 *  @param d
	 *  @return
	 */
	public static String getDateStr(Date d) {
		DateFormat df = new SimpleDateFormat(LONGTIME_PATTERN);
		return df.format(d);
	}
	
	/**
	 *  Function:
	 *			yyyyMMddHHmmss 日期格式化 
	 *  @author zhijide@pica.com  DateTime 2012-10-19 下午07:06:52
	 *  @param d
	 *  @return
	 */
	public static String getTimestamp(Date d) {
		DateFormat df = new SimpleDateFormat(TIMESTAMP_PATTERN);
		return df.format(d);
	}
	
	/**
	 *  Function:
	 *			yyyy-MM-dd 日期格式化 
	 *  @author zhijide@pica.com  DateTime 2012-10-19 下午07:08:28
	 *  @param d
	 *  @return
	 */
	public static String getMediumTime(Date d) {
		DateFormat df = new SimpleDateFormat(MEDIATIME_PATTERN);
		return df.format(d);
	}
	
}