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
package com.cmcc.common.controller.listener;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cmcc.common.Global;
//import com.cmcc.common.cache.PoolConfigInfoMap;
//import com.cmcc.common.cache.QuestionnaireSMSIDMap;
//import com.cmcc.common.persistence.DBInit;
import com.cmcc.common.util.date.DateUtil;
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
//		initDB();
//		if (!FileUtil.fileExist(Global.APP_REAL_PATH
//				+ Global.CONTENT_STATIC_FILEPATH)) {
//			FileUtil.creatDirs(Global.APP_REAL_PATH
//					+ Global.CONTENT_STATIC_FILEPATH);
//		}
//		if (!FileUtil.fileExist(Global.APP_REAL_PATH
//				+ Global.CONTENT_CATCH_IMAGEPATH)) {
//			FileUtil.creatDirs(Global.APP_REAL_PATH
//					+ Global.CONTENT_CATCH_IMAGEPATH);
//		}

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

		Global.APP_REAL_PATH = servletContextEvent.getServletContext()
				.getRealPath("/");

		Global.IPDATE_FILE_PATH = Global.APP_REAL_PATH + "static/IPDate/";

		Global._ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContextEvent
						.getServletContext());
		java.io.InputStream ins = null;
		ins = servletContextEvent.getServletContext().getResourceAsStream(
				"/WEB-INF/classes/fckeditor.properties");
		java.util.Properties fckconfig = new java.util.Properties();
		if (ins != null) {
			try {
				fckconfig.load(ins);
				String userFilesPath = (String) fckconfig
						.get("connector.userFilesPath");
				ins.close();
				Global.FCK_USERFILESPATH = userFilesPath;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		ins = null;
		fckconfig = null;
		// Load xrt configuration
//		XrtConfigLoader.load();
		
		/*
		 * 初始化問卷調查短信問卷id隊列
		 */
//		IQuestionnaireDAO questionnaireDAO = (IQuestionnaireDAO) Global._ctx.getBean("questionnaireDAO");
//		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questionnaire.class);
//		detachedCriteria.add(Restrictions.eq("deployType", 0));
//		detachedCriteria.add(Restrictions.eq("status", 2));
//		List<Questionnaire> ql = questionnaireDAO.findQuestionnaireBy(detachedCriteria);
//		for(int i = 0 ; i < ql.size() ; i ++){
//			ConcurrentLinkedQueue<String> questionnaireSMSQueue = null;
//			if(QuestionnaireSMSIDMap.contains(ql.get(i).getEid())){
//				questionnaireSMSQueue = QuestionnaireSMSIDMap.get(ql.get(i).getEid());
//				questionnaireSMSQueue.remove(ql.get(i).getSmsMtnum());
//			}else{
//				questionnaireSMSQueue = new ConcurrentLinkedQueue<String>();
//
//				for(int j = 1; j < 10; j ++){
//					if(null !=ql.get(i).getSmsMtnum()){
//						if(!ql.get(i).getSmsMtnum().equalsIgnoreCase("0"+j))
//							questionnaireSMSQueue.add("0"+j);
//					}else{
//
//						questionnaireSMSQueue.add("0"+j);
//					}
//				}
//				for(int j= 10; j < 100; j ++){
//
//					if(null !=ql.get(i).getSmsMtnum()){
//						if(!ql.get(i).getSmsMtnum().equalsIgnoreCase("0"+j))
//							questionnaireSMSQueue.add(String.valueOf(j));
//					}else{
//						questionnaireSMSQueue.add(String.valueOf(j));
//						
//					}
//				}
//				QuestionnaireSMSIDMap.put(ql.get(i).getEid(), questionnaireSMSQueue);
//			}
//		}
	}
	/**
	 * 检查更新并初始化数据库
	 * 
	 */
//	@SuppressWarnings("unchecked")
//	private void initDB() {
//		IPoolConfigDAO<PoolConfigInfo> poolConfigDAO = (IPoolConfigDAO) Global._ctx
//				.getBean("poolConfigDAO");
//		IModelManager modelManager = (IModelManager) Global._ctx
//				.getBean("modelManager");
//
//		IQuestionnaireManager questionnaireManager = (IQuestionnaireManager) Global._ctx
//					.getBean("questionnaireManager");
//		if(questionnaireManager.isTemplateDataInit()){
//			DBInit.initQuestionnaireTemplate();
//		}
//		try {
//			List<ModelInfo> rlist = modelManager.getAll();
//			if (rlist == null || rlist.size() == 0) {
//				DBInit.main(null);
//			}
//
//		} catch (Exception e) {
//			Global.CACHE_FLAG = false;
//			DBInit.main(null);
//			e.printStackTrace();
//		}
//		try {
//			List<PoolConfigInfo> info = poolConfigDAO
//					.getHibernate_reader_Interface("gcfg", null).getAll();
//			if (null != info && info.size() > 0) {
//				for (int i = 0; i < info.size(); i++) {
//					PoolConfigInfoMap.put(info.get(i).getLogic_pool_id(), info
//							.get(i));
//				}
//			} else {
//				// 疑似测试环境，数据表没有数据，默认处理为eid=1 poolid=0
//				PoolConfigInfo defaultPool = new PoolConfigInfo();
//				defaultPool.setLogic_pool_id(1);
//				defaultPool.setPhysical_pool_id(0);
//				defaultPool.setStatus(1);
//				PoolConfigInfoMap.put(new Integer(1), defaultPool);
//
//			}
//
//		} catch (Exception e) {
//
//			// 如果是初始化的时候就不做任何缓存，等数据全部初始化以后在做缓存
//			Global.CACHE_FLAG = false;
//			List<PoolConfigInfo> info = poolConfigDAO
//					.getHibernate_reader_Interface("gcfg", null).getAll();
//			PoolConfigInfoMap.put(info.get(0).getLogic_pool_id(), info.get(0));
//		}
//		Global.CACHE_FLAG = true;
//	}

}
