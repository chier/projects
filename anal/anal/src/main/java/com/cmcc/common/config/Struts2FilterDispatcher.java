/*
 * 文件名： Struts2FilterDispatcher.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.FilterDispatcher;

import com.cmcc.common.util.ConfigUtil;
import com.cmcc.common.util.StringUtil;

/**
 * 加载指定目录下的Struts配置文件
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since Oct 16, 2008
 */
public class Struts2FilterDispatcher extends FilterDispatcher {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(Struts2FilterDispatcher.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.dispatcher.FilterDispatcher#createDispatcher(javax
	 * .servlet.FilterConfig)
	 */
	@Override
	protected Dispatcher createDispatcher(FilterConfig filterConfig) {
		if (logger.isDebugEnabled()) {
			logger.debug("createDispatcher(FilterConfig) - start");
		}

		String[] configfiles = ConfigUtil.getConfigLocationArray(filterConfig
				.getServletContext().getRealPath(""), "struts", true);
		String configfile = "";
		for (String temp : configfiles) {
			if (!temp.contentEquals("struts-interceptor.xml"))
				configfile += temp + ",";
		}

		Map<String, String> params = new HashMap<String, String>();
		String value = "struts-default.xml,struts-plugin.xml,";

		if (StringUtil.isNullorBlank(configfile)) {
			if (logger.isWarnEnabled()) {
				logger.warn("You don't have any configure files,Use default ones!");
			}
			value += "struts.xml";
		} else {
			String name = "config";
			String interceptor = "/struts-interceptor.xml";
			String commonConfigPath = "WEB-INF/classes/config/common";
			// configfile =
			// configfile.replace("WEB-INF/classes/config/common/struts-interceptor.xml",
			// "");
			value += filterConfig.getServletContext().getRealPath(
					commonConfigPath)
					+ interceptor + "," + configfile;
			params.put(name, value);
			if (logger.isInfoEnabled()) {
				logger.info("Found you custom configure files,prepare to load...");
			}
		}

		Dispatcher dispatcher = new Dispatcher(
				filterConfig.getServletContext(), params);
		if (logger.isDebugEnabled()) {
			logger.debug("createDispatcher(FilterConfig) - end");
		}
		return dispatcher;
	}
}
