/*
 * 文件名： MathUtil.java
 * 
 * 创建日期： 2008-2-26
 *
 * Copyright(C) 2008, by Along.
 *
 * 原始作者: <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 */
package com.cmcc.common.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;


/**
 * 数学计算工具类
 * 
 * @author <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-2-26
 */
public class MathUtil {

	/**
	 * a除以b的整数,向上取整.
	 * 
	 * @param divHeader
	 *            int
	 * @param divUnder
	 *            int
	 * @return int
	 */
	public static int ceildiv(int divHeader, int divUnder) {
		return (int) Math.ceil((double) divHeader / (double) divUnder);

	}

	/**
	 * 返回两个数相除的结果,按照nSyo的格式输出. 格式说明参考NumberFormat的帮助.
	 * 
	 * @param divHeader
	 *            被除数
	 * @param divUnder
	 *            除数
	 * @param nSyo
	 *            格式
	 * @return String
	 */
	public static String mydiv(int divHeader, int divUnder, String nSyo) {
		if (divHeader == 0 || divUnder == 0) {
			return "0.0";
		}

		NumberFormat astr = NumberFormat.getInstance();
		((DecimalFormat) astr).applyPattern(nSyo);

		return astr.format((double) divHeader / (double) divUnder);
	}

	/**
	 * 得到一个随机整数,最大是n.
	 * 
	 * @param nMax
	 *            最大值
	 * @return 输出:随机整数
	 */
	public static int getRandom(int nMax) {
		Random hello;
		hello = new Random();
		return hello.nextInt(nMax);
	}

	/**
	 * 得到随机数,加上字符串前缀.
	 * 
	 * @param nMax
	 *            随机数的最大值
	 * @param strPre
	 *            字符串前缀
	 * @return 字符串前缀加上随机数
	 */
	public static String getRandom(int nMax, String strPre) {
		Random hello = new Random();

		String result = strPre + hello.nextInt(nMax);
		return result;
	}
}
