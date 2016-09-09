/*
 * 文件名： SystemUtil.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.util;

/**
 * System函数的封装.
 * 
 * @author <a href="mailto:cheirsystem@gmail.com" alt="张占亮">张占亮</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public class SystemUtil {

	/**
	 * 打印一行信息.
	 * 
	 * @param message
	 *            信息
	 */
	public static void printLine(String message) {
		System.out.println(message); // NOPMD
	}

	/**
	 * 打印信息.
	 * 
	 * @param message
	 *            信息
	 */
	public static void print(String message) {
		System.out.print(message); // NOPMD
	}

	/**
	 * 打印一行错误信息. (System.err)
	 * 
	 * @param message
	 *            信息
	 */
	public static void errPrintLine(String message) {
		System.err.println(message); // NOPMD
	}
}
