/*
 * 文件名： CharacterCoding.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.util;

import java.net.URLEncoder;

/**
 * 处理字符编码的Java类
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public class CharacterCodingUtil {

	public static final String GBK2unicode(String value) {
		try {
			if (value == null)
				return "";
			else if (value.length() == 0)
				return "";
			else
				return new String(value.getBytes("GBK"), "ISO8859-1");
		}
		catch (Exception e) {
		}
		return "";
	}

	public static String unicode2GBK(String value) {
		try {
			if (value == null)
				return "";
			else if (value.length() == 0)
				return "";
			else
				return new String(value.getBytes("ISO8859-1"), "GBK");
		}
		catch (Exception e) {
		}
		return "";
	}

	public static String URLencodeGBK(String value) {
		try {
			if (value == null)
				return "";
			else if (value.length() == 0)
				return "";
			else
				return URLEncoder.encode(value, "GBK");
		}
		catch (Exception e) {
		}
		return "";
	}

	public static String URLencodeUTF8(String value) {
		try {
			if (value == null)
				return "";
			else if (value.length() == 0)
				return "";
			else
				return URLEncoder.encode(value, "UTF8");
		}
		catch (Exception e) {
		}
		return "";
	}

	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 * 
	 * @param s
	 *            原文件名
	 * 
	 * @return 重新编码后的文件名
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			}
			else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				}
				catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 把byte数组转换为16进制字符串.
	 * 
	 * @param byteAry
	 *            byte[]
	 * @return String
	 */
	public static String byte2hex(byte[] byteAry) {
		StringBuffer strBF = new StringBuffer();
		String stmp;
		for (int n = 0; n < byteAry.length; n++) {
			stmp = (Integer.toHexString(byteAry[n] & 0XFF));
			if (stmp.length() == 1) {
				strBF.append("0").append(stmp);
			}
			else {
				strBF.append(stmp);
			}
		}
		return strBF.toString();
	}

	/**
	 * 把16进制字符串转成byte[].
	 * 
	 * @param hex
	 *            String
	 * @throws IllegalArgumentException
	 *             参数错误
	 * @return byte[] 数据
	 */
	public static byte[] hex2byte(String hex) {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] byteAry = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			byteAry[j] = new Integer(byteint).byteValue();
		}
		return byteAry;
	}

}
