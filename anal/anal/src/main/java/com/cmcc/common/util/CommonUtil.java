package com.cmcc.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CommonUtil {
	/**
	 * 判断字符串是否为全部字母
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isLetter(String s) {
		boolean b = true;
		char[] values = s.toCharArray();
		for (int a = 0; a < values.length; a++) {
			if (!((values[a] >= 65 && values[a] <= 122) && Character.isLetter(values[a]))) {
				b = false;
				break;
			}
		}
		return b;
	}

	/**
	 * 随机密码，数字与字母间隔
	 * 
	 * @param count
	 * @return
	 */
	public static String randomPwd(Integer count) {
		String num = "0123456789";
		String letter = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb1 = new StringBuilder();
		Random rnd = new Random();
		int count1 = 0;
		int count2 = 0;
		if (count % 2 == 0) {
			count1 = count2 = count / 2;
		} else {
			count1 = count / 2;
			count2 = count / 2 + 1;
		}
		char[] nums = new char[count1];
		char[] letters = new char[count2];
		for (int i = 0; i < count1; i++) {
			nums[i] = num.charAt(rnd.nextInt(num.length()));
		}
		for (int i = 0; i < count2; i++) {
			letters[i] = letter.charAt(rnd.nextInt(letter.length()));
		}
		for (int i = 0; i < count1; i++) {
			sb1.append(nums[i]);
			sb1.append(letters[i]);
		}
		if (count % 2 != 0) {
			sb1.append(letters[count2 - 1]);
		}
		String romPwd = sb1.toString().replaceAll("0", "t").replaceAll("o", "d").replaceAll("l", "u").replaceAll("i", "y").replaceAll("5", "8").replaceAll("b", "7").replaceAll("6", "p").replaceAll("1", "8").replaceAll("g", "3").replaceAll("s", "t");
		return romPwd;
	}

	/**
	 * 判断字符串是否为全部数字
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isDigital(String s) {
		boolean b = true;
		char[] values = s.toCharArray();
		for (int a = 0; a < values.length; a++) {
			if (!Character.isDigit(values[a])) {
				b = false;
				break;
			}
		}
		return b;
	}

	/**
	 * 获取随机用户名，只能是小写字母
	 * 
	 * @param len
	 *            密码长度
	 * @return
	 */
	public static String getRandomStringForName(int len) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int a = (int) (100 * Math.random() + 100 * Math.random());
			while (true) {
				if (a > 96 & a < 123)
					break;
				else
					a = (int) (100 * Math.random() + 100 * Math.random());
			}
			str.append((char) a);
		}
		return str.toString();
	}

	/**
	 * 获取随机密码
	 * 
	 * @param len
	 *            密码长度
	 * @return
	 */
	public static String getRandomStringForPwd(int len) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < len; i++) {
			int a = (int) (100 * Math.random() + 100 * Math.random());
			while (true) {
				if ((a >= 48 & a <= 57) || (a >= 65 && a < 90) || (a >= 97 && a <= 122))
					break;
				else
					a = (int) (100 * Math.random() + 100 * Math.random());
			}
			str.append((char) a);
		}
		return str.toString();
	}

	/**
	 * 检查手机号是否是中移动号码
	 * 
	 * @param mp
	 * @return
	 */
	public static boolean isMobileCmcc(String mp) {
		String smp = String.valueOf(mp);
		if (smp.startsWith("86")) {
			smp = smp.substring(2);
		}
		if (smp.length() != 11) {
			return false;
		}
		smp = smp.substring(0, 3);
		if (smp.equals("134") || smp.equals("135") || smp.equals("136") || smp.equals("137") || smp.equals("138") || smp.equals("139") || smp.equals("158") || smp.equals("159") || smp.equals("157") || smp.equals("150") || smp.equals("151")) {
			return true;
		}
		return false;
	}

	/**
	 * 按照指定时间格式格式化日期
	 * 
	 * @param input_date
	 *            需要格式化的日期
	 * @param format_patten
	 *            格式化后的字符串
	 * @return
	 */
	public static String formatDate(Date input_date, String format_patten) {
		SimpleDateFormat time = null;
		try {
			time = new SimpleDateFormat(format_patten);
		} catch (Exception e) {
			time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		return time.format(input_date);
	}

	// 随机数字
	@SuppressWarnings("unused")
	public static String randomCode(Integer connt) throws Exception {
		// 定义验证码的字符表
		StringBuffer rands = new StringBuffer();
		;
		for (int i = 0; i < connt; i++) {
			rands.append((int) (Math.random() * 10));
		}
		return rands.toString();
	}

	public static void main(String[] args) {
		System.out.println(formatDate(new Date(), "yyyy-MM-dd"));
		String sql = "SELECT CEN_OBJECT_CODE as 'AA', PARENT_CEN_OBJECT_CODE as 'BB' FROM  cen_object";
		String[] s = getMetaData(sql);
		System.out.println(s);
	}

	// 获取元数据
	public static String[] getMetaData(String sql) {
		String[] source = sql.toLowerCase().split("from")[0].split("select")[1].split(",");
		String[] dest = new String[source.length];
		for (int i = 0; i < source.length; i++) {
			String[] tmp = source[i].trim().split("\\.")[source[i].trim().split("\\.").length - 1].split(" ");
			dest[i] = tmp[tmp.length - 1];
			dest[i] = dest[i].replace("'", "");
		}
		return dest;
	}
}
