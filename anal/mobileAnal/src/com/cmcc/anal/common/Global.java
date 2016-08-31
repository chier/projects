/*
 * 文件名： Global.java
 * 
 * 创建日期�?2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作�? <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.anal.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.cmcc.anal.common.config.LoadConfig;

/**
 * 定义一些框架需要的全局变量
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.9 $
 * 
 * @since 2008-11-27
 */
public class Global {
	/** Spring的ApplicationContext，使用的时候要自己设置�? */
	public static ApplicationContext _ctx = null;

	/** 配置文件路径 */
	public static final String CONFIGPATH = "/";

	/** 系统配置文件�? */
	public static final String CONFIGXMLFILE = "systemConfig.xml";

	public static final Integer PAGESIZE = LoadConfig.getInstance().getPageSize();

	/** zhangzhanliang jrxml html 服务器存储路径 */
	public static String staticRportHtml = "static/report/html/";

	/** zhangzhanliang jrxml jasper 服务器存储路 */
	public static String staticRportJasper = "static/report/jasper/";

	/** zhangzhanliang jrxml jasper 服务器存储路 */
	public static String staticRportJrprint = "static/report/jrprint/";
	
	/** zhangzhanliang jrxml 文件 文件后缀名 */
	public static String jrxmlSuffix = ".jrxml";


}
