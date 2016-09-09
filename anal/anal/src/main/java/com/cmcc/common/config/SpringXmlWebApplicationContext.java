/*
 * 文件名： SpringXmlWebApplicationContext.java
 * 
 *
 * 创建日期： 2008-11-27
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.cmcc.common.util.ConfigUtil;

/**
 * 搜索指定的目录下Spring WebContext的配置文件,用于Spring调用
 * <p>
 * Spring的配置文件必须声明<br>
 * &lt;!DOCTYPE&nbsp;beans&nbsp;PUBLIC&nbsp;&quot;-//SPRING//DTD&nbsp;BEAN&nbsp;
 * 2.0//EN&quot;&nbsp;&quot;http://www.springframework.org/dtd/spring-beans-2.0.
 * dtd&quot;&gt;<br>
 * <br>
 * beans父标签声明务必加上自动装配:<br>
 * <b>&lt;beans&nbsp;default-autowire=&quot;byName&quot;&gt;</b>
 * <p>
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
public class SpringXmlWebApplicationContext extends XmlWebApplicationContext {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(SpringXmlWebApplicationContext.class);

	/**
	 * 应用上下文根路径
	 */
	private String contextBasePath;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.web.context.support.XmlWebApplicationContext#
	 * getDefaultConfigLocations()
	 */
	protected String[] getDefaultConfigLocations() {
		if (logger.isDebugEnabled()) {
			logger.debug("getDefaultConfigLocations() - start");
		}

		contextBasePath = getServletContext().getRealPath("");
		String[] returnStringArray = ConfigUtil.getConfigLocationArray(
				contextBasePath, "spring");

		if (logger.isDebugEnabled()) {
			logger.debug("getDefaultConfigLocations() - end");
		}
		return returnStringArray;
	}

	public String getContextBasePath() {
		return contextBasePath;
	}
}
