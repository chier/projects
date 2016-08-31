/*
 * 文件名： AppContextListener.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.anal.common.controller.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cmcc.anal.common.Global;
import com.cmcc.anal.common.util.DateUtil;

//import com.cmcc.common.cache.PoolConfigInfoMap;
//import com.cmcc.common.cache.QuestionnaireSMSIDMap;
//import com.cmcc.common.persistence.DBInit;
//import com.cmcc.common.util.file.FileUtil;
//import com.cmcc.common.xrt.XrtConfigLoader;
//import com.cmcc.framework.business.interfaces.questionnaire.IQuestionnaireManager;
//import com.cmcc.framework.business.interfaces.system.model.IModelManager;
//import com.cmcc.framework.model.poolcfg.PoolConfigInfo;
//import com.cmcc.framework.model.questionnaire.Questionnaire;
//import com.cmcc.framework.model.system.model.ModelInfo;
//import com.cmcc.framework.persistence.interfaces.poolcfg.IPoolConfigDAO;
//import com.cmcc.framework.persistence.interfaces.questionnaire.IQuestionnaireDAO;

/**
 * 
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
public class AppContextListener implements ServletContextListener {

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(AppContextListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seejavax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		if (logger.isInfoEnabled()) {
			MDC.put("userId", "background");
			MDC.put("event", "appShutdown");
			logger.info("App has been shutdown at "
					+ DateUtil.getSystemCurrentDateTime());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 *      .ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		initGlobal(servletContextEvent);

		if (logger.isInfoEnabled()) {
			MDC.put("userId", "background");
			MDC.put("event", "appStartup");
			logger.info("App has been startup at "
					+ DateUtil.getSystemCurrentDateTime());
		}

	}

	/**
	 * 初始化全局变量
	 * 
	 */
	private void initGlobal(ServletContextEvent servletContextEvent) {
		Global._ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContextEvent
						.getServletContext());
		logger.info("dd");
		if(Global._ctx  == null){
			logger.info("Global._ctx  is null ");
		}else{
			logger.info("Global._ctx  is not null");
		}
//		WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		
	}

}
