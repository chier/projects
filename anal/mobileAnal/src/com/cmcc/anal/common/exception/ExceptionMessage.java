/*
 * 文件名： ExceptionMessage.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.anal.common.exception;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 从属性文件获取异常i18n信息的Java类
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
public class ExceptionMessage {
//
//	private static final String BUNDLE_NAME = Global.EXCEPTION_I18N_MESSAGE_FILE;
//
//	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
//			.getBundle(BUNDLE_NAME);
//
//	private ExceptionMessage() {
//	}
//
//	public static String formatString(String key, Object[] args) {
//		return MessageFormat.format(getString(key), args);
//	}
//
//	public static String formatString(String key, Object arg0, Object arg1) {
//		return MessageFormat.format(getString(key), new Object[]
//			{ arg0, arg1 });
//	}
//
//	public static String formatString(String key, Object arg) {
//		return MessageFormat.format(getString(key), new Object[]
//			{ arg });
//	}
//
//	public static String getString(String key) {
//		try {
//			return RESOURCE_BUNDLE.getString(key);
//		}		catch (MissingResourceException e) {
//			e.printStackTrace();
//			return key + "Not Found!";
//		}
//	}
}
