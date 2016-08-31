/**
 * 
 */
package com.cmcc.anal.common.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.talent.platform.core.SpringUtils;

/**
 * 
 * @filename: com.pica.wificity.util.GetBeanServlet
 * @copyright: Copyright (c)2010
 * @company: talent
 * @author: 谭耀武
 * @version: 1.0
 * @create time: 2012-4-24 下午5:35:38
 * @record <table cellPadding="3" cellSpacing="0" style="width:600px">
 *         <thead style="font-weight:bold;background-color:#e3e197">
 *         <tr>
 *         <td>date</td>
 *         <td>author</td>
 *         <td>version</td>
 *         <td>description</td>
 *         </tr>
 *         </thead> <tbody style="background-color:#ffffeb">
 *         <tr>
 *         <td>2012-4-24</td>
 *         <td>谭耀武</td>
 *         <td>1.0</td>
 *         <td>create</td>
 *         </tr>
 *         </tbody>
 *         </table>
 */
public class ApplicationContextServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7358930496283378752L;

	private static Logger logger = LoggerFactory.getLogger(ApplicationContextServlet.class);

	/**
	 * 
	 */
	public ApplicationContextServlet() {

	}

	private static WebApplicationContext context = null;

	public void init() throws ServletException {
		JSON.DEFFAULT_DATE_FORMAT = DateTimeUtil.TIMESTAMP_PATTERN;

		

		context = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	}

	/**
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		if (context == null) {
			return com.talent.platform.core.SpringUtils.getApplicationContext();
		} else {
			return context;
		}
	}

	/**
	 * eg: getApplicationContext("config/applicationContext.xml")
	 * @param classPathOfConfigFile
	 * @return
	 */
	public static ApplicationContext getApplicationContext(String classPathOfConfigFile) {
		if (context == null) {
			return SpringUtils.getApplicationContext(classPathOfConfigFile);
		} else {
			return context;
		}
	}

	/**
	 * 
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String beanName, Class<T> clazz) {
		if (context == null) {
			return SpringUtils.getBean(beanName, clazz);
		} else {
			return context.getBean(beanName, clazz);
		}
	}

	/**
	 * 
	 * @param classPathOfConfigFile 形如：config/applicationContext.xml
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String classPathOfConfigFile, String beanName, Class<T> clazz) {
		if (context == null) {
			return SpringUtils.getBean(classPathOfConfigFile, beanName, clazz);
		} else {
			return context.getBean(beanName, clazz);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
