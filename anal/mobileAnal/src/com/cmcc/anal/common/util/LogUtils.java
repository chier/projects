/**
 * 
 */
package com.cmcc.anal.common.util;

import org.slf4j.LoggerFactory;

public class LogUtils {
	private static org.slf4j.Logger log = LoggerFactory.getLogger(LogUtils.class);

	/**
	 * 
	 */
	public LogUtils() {

	}

	/**
	 * 获取http+json日志类
	 * @return
	 */
	public static org.slf4j.Logger getHttpJsonLog() {
		return getLog("httpJsonLog");
	}

	/**
	 * 获取http+xml日志类
	 * @return
	 */
	public static org.slf4j.Logger getHttpXmlLog() {
		return getLog("httpXmlLog");
	}

	/**
	 * 获取webservice日志类
	 * @return
	 */
	public static org.slf4j.Logger getWebserviceLog() {
		return getLog("webserviceLog");
	}
	
	/**
	 * 
	 * @return
	 */
	public static org.slf4j.Logger getFtpLogServiceLog() {
		return getLog("ftpLogServiceLog");
	}
	
	
	
	public static org.slf4j.Logger getFtpLogBindLog() {
		return getLog("ftpLogBindLog");
	}
	
	

	private static org.slf4j.Logger getLog(String logname) {
		org.slf4j.Logger logger = LoggerFactory.getLogger(logname);
		return logger;
	}

	
	public static class Logger {
		public static org.slf4j.Logger getLogger(Class clazz) {
			return LoggerFactory.getLogger(clazz);
		}
	}

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String []args){
		org.slf4j.Logger log = getFtpLogServiceLog();
		org.slf4j.Logger bindlog = getFtpLogBindLog();
		for (int i = 0; i < 10000000; i++){
			log.error("999999999999999999999999999999999999999999999999999999999999999999999999999999999");
			bindlog.error("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
