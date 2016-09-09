/*
 * 文件名： SysInfo.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.StringTokenizer;

/**
 * 得到系统运行时的一些信息.
 * 
 * @author <a href="mailto:chiersystem@gmail.com" alt="张占亮">张占亮</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public class SysInfo {
	/** JAVA版本1.3. */
	public static final int JAVAVERSION_13 = 13;

	public static final int JAVAVERSION_14 = 14;

	private static final float JAVAVER_13 = (float) 1.3;

	/**
	 * 返回文件路径分隔符,缺省为\.
	 * 
	 * @return 文件路径分隔符
	 */
	public static String getFileSeparator() {
		String sFileSeparator = "\\";
		try {
			sFileSeparator = System.getProperty("file.separator");
		}
		catch (SecurityException e) {
		}
		return sFileSeparator;
	}

	/**
	 * 得到换行符. 缺省为\r\n
	 * 
	 * @return 换行符
	 */
	public static String getLineSeparator() {
		String sLineSeparator = "\r\n";
		try {
			sLineSeparator = System.getProperty("line.separator");
		}
		catch (SecurityException e) {
		}
		return sLineSeparator;
	}

	/**
	 * 得到当前Java的版本
	 * 
	 * @return 当前Java的版本
	 */
	public static int getJavaVersion() {
		String sVersion;
		int nVersion = JAVAVERSION_13;
		try {
			sVersion = System.getProperty("java.specification.version");
			nVersion = (int) (10 * myparseFloat(sVersion, JAVAVER_13));
		}
		catch (SecurityException e) {
			SystemUtil.errPrintLine("Error get Java Version");
		}
		return nVersion;
	}

	/**
	 * Java版本是否为1.4或以上.
	 * 
	 * @return 是否为1.4或以上
	 */
	public static boolean isJavaVersion14() {
		return getJavaVersion() >= JAVAVERSION_14;
	}

	/**
	 * 分析一个字符串得到float,如果错误,设置一个缺省值.
	 * 
	 * @param str1
	 *            String
	 * @param nDefault
	 *            缺省值
	 * @return float
	 */
	private static float myparseFloat(String str1, float nDefault) {
		float result;
		try {
			result = ((null == str1) || (str1.length() == 0)) ? nDefault
					: Float.parseFloat(str1);
		}
		catch (NumberFormatException e) {
			result = nDefault;
		}
		return result;
	}

	/**
	 * 得到系统文件的编码.
	 * 
	 * @return 系统文件的编码.
	 */
	public static String getSYSFileEncoding() {
		return System.getProperty("file.encoding");
	}

	/**
	 * 获得当前运行操作系统的版本号
	 * 
	 * @return 运行操作系统的版本号
	 */
	public static String getOSVersion() {
		return System.getProperty("os.version");
	}

	/**
	 * 获得当前运行操作系统的名称
	 * 
	 * @return 运行操作系统的名称
	 */
	public static String getOSName() {
		return System.getProperty("os.name");
	}

	/**
	 * 获得当前运行的JVM的提供商
	 * 
	 * @return 运行的JVM的提供商
	 */
	public static String getJVMVendor() {
		return System.getProperty("java.vm.vendor");
	}

	/**
	 * 获得当前运行的JVM版本号
	 * 
	 * @return 运行的JVM版本号
	 */
	public static String getJVMVersion() {
		return System.getProperty("java.vm.version");
	}

	/**
	 * 获得CPU的个数
	 * 
	 * @return CPU的个数
	 */
	public static int getCPUCount() {
		return Runtime.getRuntime().availableProcessors();
	}

	/**
	 * 获得Java安装目录地址
	 * 
	 * @return Java安装目录地址
	 */
	public static String getJavaHome() {
		return System.getProperty("java.home");
	}

	/**
	 * 获得Java 虚拟机中的内存总量，以字节为单位。
	 * 
	 * @return Java 虚拟机中的内存总量
	 */
	public static long getTotalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	/**
	 * 获得Java 虚拟机试图使用的最大内存量，以字节为单位。
	 * 
	 * @return Java 虚拟机试图使用的最大内存量
	 */
	public static long getMaxMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	/**
	 * 获得Java 虚拟机中的空闲内存量，以字节为单位。
	 * 
	 * @return Java 虚拟机中的空闲内存量
	 */
	public static long getFreeMemory() {
		return Runtime.getRuntime().freeMemory();
	}

	/**
	 * 获得网卡的Mac地址，只能获得第一块的。
	 * 
	 * @return 网卡的Mac地址
	 * 
	 * @throws IOException
	 */
	public final static String getMacAddress() throws IOException {
		String os = SysInfo.getOSName();

		try {
			if (os.startsWith("Windows")) {
				return windowsParseMacAddress(windowsRunIpConfigCommand());
			}
			else if (os.startsWith("Linux")) {
				return linuxParseMacAddress(linuxRunIfConfigCommand());
			}
			else {
				throw new IOException("unknown operating system: " + os);
			}
		}
		catch (ParseException ex) {
			ex.printStackTrace();
			throw new IOException(ex.getMessage());
		}
	}

	/* * Linux stuff */
	private final static String linuxParseMacAddress(String ipConfigResponse)
			throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		}
		catch (java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim();
			boolean containsLocalHost = line.indexOf(localHost) >= 0;

			if (containsLocalHost && lastMacAddress != null) {
				return lastMacAddress;
			} // see if line contains MAC address

			int macAddressPosition = line.indexOf("HWaddr");
			if (macAddressPosition <= 0)
				continue;
			String macAddressCandidate = line.substring(macAddressPosition + 6)
					.trim();
			if (linuxIsMacAddress(macAddressCandidate)) {
				lastMacAddress = macAddressCandidate;
				return lastMacAddress;
			}
		}
		ParseException ex = new ParseException("cannot read MAC address for "
				+ localHost + " from [" + ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	}

	private final static boolean linuxIsMacAddress(String macAddressCandidate) {
		if (macAddressCandidate.length() != 17)
			return false;
		return true;
	}

	private final static String linuxRunIfConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ifconfig");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
		StringBuffer buffer = new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1)
				break;
			buffer.append((char) c);
		}
		String outputText = buffer.toString();
		stdoutStream.close();
		return outputText;
	}

	/* * Windows stuff */

	private final static String windowsParseMacAddress(String ipConfigResponse)
			throws ParseException {
		String localHost = null;
		try {
			localHost = InetAddress.getLocalHost().getHostAddress();
		}
		catch (java.net.UnknownHostException ex) {
			ex.printStackTrace();
			throw new ParseException(ex.getMessage(), 0);
		}
		StringTokenizer tokenizer = new StringTokenizer(ipConfigResponse, "\n");
		String lastMacAddress = null;
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken().trim(); // see if line contains
			// IP address
			if (line.endsWith(localHost) && lastMacAddress != null) {
				return lastMacAddress;
			} // see if line contains MAC address

			int macAddressPosition = line.indexOf(":");
			if (macAddressPosition <= 0) {
				continue;
			}

			String macAddressCandidate = line.substring(macAddressPosition + 1)
					.trim();
			if (windowsIsMacAddress(macAddressCandidate))// &Egrave;&iexcl;&micro;&Atilde;MAC&micro;&Oslash;&Ouml;·
			{
				lastMacAddress = macAddressCandidate;
				return lastMacAddress;
			}
		}
		ParseException ex = new ParseException("cannot read MAC address from ["
				+ ipConfigResponse + "]", 0);
		ex.printStackTrace();
		throw ex;
	}

	private final static boolean windowsIsMacAddress(String macAddressCandidate) {
		if (macAddressCandidate.length() != 17)
			return false;
		return true;
	}

	private final static String windowsRunIpConfigCommand() throws IOException {
		Process p = Runtime.getRuntime().exec("ipconfig /all");
		InputStream stdoutStream = new BufferedInputStream(p.getInputStream());
		StringBuffer buffer = new StringBuffer();
		for (;;) {
			int c = stdoutStream.read();
			if (c == -1)
				break;
			buffer.append((char) c);
		}
		String outputText = buffer.toString();
		stdoutStream.close();
		return outputText;
	}

	/* * Main */
	public final static void main(String[] args) {
		try {
			System.out.println("Network infos:");
			System.out.println("Operating System:   " + SysInfo.getOSName());
			System.out.println("IP/Localhost:   "
					+ InetAddress.getLocalHost().getHostAddress());
			System.out.println("MAC Address:   " + getMacAddress());
		}
		catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
