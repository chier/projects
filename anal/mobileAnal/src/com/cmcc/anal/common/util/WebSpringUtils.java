/**
 * 
 */
package com.cmcc.anal.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * 
 * @filename: com.pica.wificity.util.SpringUtils
 * @copyright: Copyright (c)2010
 * @company: talent
 * @author: 谭耀武
 * @version: 1.0
 * @create time: 2012-4-24 下午5:44:52
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
public class WebSpringUtils {
	private static Logger log = LoggerFactory.getLogger(WebSpringUtils.class);

	/**
	 * 
	 */
	public WebSpringUtils() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

	/**
	 * 
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String beanName, Class<T> clazz) {
		return ApplicationContextServlet.getBean(beanName, clazz);
	}

	/**
	 * 
	 * @param classPathOfConfigFile 形如：config/applicationContext.xml
	 * @param beanName
	 * @param clazz
	 * @return
	 */
	public static <T> T getBean(String classPathOfConfigFile, String beanName,
			Class<T> clazz) {
		return ApplicationContextServlet.getBean(classPathOfConfigFile, beanName, clazz);
	}
	
	
	
	/**
     * 
     * @return
     */
    public static ApplicationContext getApplicationContext()
    {
        return ApplicationContextServlet.getApplicationContext();
    }
    
    /**
     * eg: getApplicationContext("config/applicationContext.xml")
     * @param classPathOfConfigFile
     * @return
     */
    public static ApplicationContext getApplicationContext(String classPathOfConfigFile)
    {
        return ApplicationContextServlet.getApplicationContext(classPathOfConfigFile);
    }

}
