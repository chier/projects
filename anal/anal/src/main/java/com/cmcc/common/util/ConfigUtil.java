/*
 * 文件名： ConfigurationUtil.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 配置文件搜索类
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public class ConfigUtil {

	private static String webContentPath;

	public static void setWebContentPath(String webContentPath) {
		ConfigUtil.webContentPath = webContentPath;
	}

	/**
	 * 根据提供的参数获取应用的绝对路径
	 * 
	 * @param webdir
	 *            应用的web路径
	 * @return String
	 */
	public static String getWebApplicationPath() {
		if (webContentPath == null)
			return new File("web").getAbsolutePath();
		return webContentPath;
	}

	/**
	 * 获得应用配置文件所在的绝对路径
	 * 
	 * @param webdir
	 *            应用的web路径
	 * @return String
	 */
	public static String getConfigPath() {
		return getWebApplicationPath() + "/WEB-INF/classes/config";
	}

	/**
	 * 获取配置文件的全路径，即绝对路径
	 * 
	 * @param path
	 * @param cfgFileName
	 * @return String[0...n]
	 */
	public static String[] getFullConfigLocationArray(String path,
			String cfgFileName) {
		return getConfigLocationArray(path, cfgFileName, "", true);
	}

	/**
	 * 获取配置文件的路径,可选择时候否获取绝对路径和相对路径
	 * 
	 * @param path
	 * @param fileName
	 * @param isFullPath
	 * @return String[0...n]
	 */
	public static String[] getConfigLocationArray(String path,
			String cfgFileName, boolean isFullPath) {
		return getConfigLocationArray(path, cfgFileName, "", isFullPath);
	}

	/**
	 * 获取配置文件的路径，和classpath同级的路径
	 * 
	 * @param path
	 * @param cfgFileName
	 * @return
	 */
	public static String[] getConfigLocationArray(String path,
			String cfgFileName) {
		return getConfigLocationArray(path, cfgFileName, "", false);
	}

	private static String[] getConfigLocationArray(String path,
			String cfgFileName, String filePrefix, boolean isFullPath) {
		String curPath = "/WEB-INF/classes/config";
		File dir = new File(path + curPath);
		List<String> files = new ArrayList<String>();
		getConfigLocations(dir, curPath, cfgFileName, filePrefix, files,
				isFullPath);
		return (String[]) files.toArray(new String[0]);
	}

	private static void getConfigLocations(File dir, String curPath,
			String cfgFileName, String filePrefix, List<String> files,
			boolean isFullPath) {
		String[] fileNames = dir.list();
		if (fileNames == null)
			return;
		String filePath = dir.getPath();
		for (int i = 0; i < fileNames.length; i++) {
			if (fileNames[i].equals(".") || fileNames[i].equals(".."))
				continue;
			if (fileNames[i].startsWith(cfgFileName)
					&& fileNames[i].endsWith(".xml")) {
				if (isFullPath)
					files.add(filePath + "/" + fileNames[i]);
				else
					files.add(filePrefix + curPath + "/" + fileNames[i]);
				continue;
			}
			File file = new File(filePath + "/" + fileNames[i]);
			if (file.isDirectory()) {
				// recursion
				getConfigLocations(file, curPath + "/" + fileNames[i],
						cfgFileName, filePrefix, files, isFullPath);
			}
		}
	}
}
