/*
 * 文件名： GenericLoggerInterceptor.java
 * 
 * 创建日期： 2009-3-23
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.common.controller.interceptor;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

import com.cmcc.common.Global;
//import com.cmcc.common.cache.PoolConfigInfoMap;
import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.util.UserSessionObj;
//import com.cmcc.common.util.UserSessionObj;
//import com.cmcc.framework.business.interfaces.corporation.ICompanyInfoManager;
import com.cmcc.framework.business.interfaces.log.IOperLogManager;
import com.cmcc.framework.model.log.OperateLog;

/**
 * 
 * 记录系统日志的拦截器
 * 
 * @author <a href="mailto:chiersystem@gmail.com">zhangzhanliang</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-3-23
 * 
 */
public class GenericLoggerInterceptor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(GenericLoggerInterceptor.class);

	@SuppressWarnings("unchecked")
	public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
		if (logger.isDebugEnabled()) {
			logger.debug("invoke(ProceedingJoinPoint) - start"); //$NON-NLS-1$
		}

		try {

			Object result = joinPoint.proceed();

			Class cl = joinPoint.getTarget().getClass();
			Method[] methods = cl.getMethods();
			GenericLogger genericLogger = null;
			UserSessionObj userSessionObj = null;
			String departmentNames = "";
			String employeeNames = "";
			String descExtendStr = "";
			Integer operateRecords = 1;
			boolean isRollback = false;
			for (Method m : methods) {
				if (m.getName().equals(joinPoint.getSignature().getName())) {
					genericLogger = m.getAnnotation(GenericLogger.class);
				}
				if (m.getName().equalsIgnoreCase("getusersessioninfo")) {
					userSessionObj = (UserSessionObj) m.invoke(joinPoint
							.getTarget());
				}
			}

			OperateLog opLog = new OperateLog();
			if (null != genericLogger) {
//				if (genericLogger.isOperateDepartment() || genericLogger.isExtend() || genericLogger.isOperateEmployee() || genericLogger.isRollback()) {
//					for (Method m : methods) {
//						String methodName = m.getName();
//						if (methodName.equalsIgnoreCase("getdepartmentnames")) {//操作範圍：部門
//							departmentNames = (String) m.invoke(joinPoint
//									.getTarget()); 
//						} else if (methodName.equalsIgnoreCase("getemployeenames")) {//操作範圍：部門
//
//							employeeNames = (String) m.invoke(joinPoint
//									.getTarget()); 
//						} else if (methodName.equalsIgnoreCase("getOperateExtned")) {//操作描述擴展
//
//							descExtendStr = (String) m.invoke(joinPoint
//									.getTarget()); 
//						} else if (methodName.equalsIgnoreCase("isRollback")) {//是否繼續
//							
//							isRollback = (Boolean) m.invoke(joinPoint
//									.getTarget()); 
//						}
//					}
//				}
				if (genericLogger.isOperateDepartment()) {
					for (Method m : methods) {
						if (m.getName().equalsIgnoreCase("getdepartmentnames")) {
							departmentNames = (String) m.invoke(joinPoint
									.getTarget());
							break;
						}
					}
				}
				if (genericLogger.isOperateEmployee()) {
					for (Method m : methods) {
						if (m.getName().equalsIgnoreCase("getemployeenames")) {
							employeeNames = (String) m.invoke(joinPoint
									.getTarget());
							break;
						}
					}
				}
				if (genericLogger.isExtend()) {
					for (Method m : methods) {
						if (m.getName().equalsIgnoreCase("getOperateExtned")) {
							descExtendStr = (String) m.invoke(joinPoint
									.getTarget());
							break;
						}
					}
				}
				if (genericLogger.isRollback()) {
					for (Method m : methods) {
						if (m.getName().equalsIgnoreCase("isRollback")) {
							isRollback = (Boolean) m.invoke(joinPoint
									.getTarget());
							break;
						}
					}
				}
				if (genericLogger.isOperateRecords()){
					for (Method m : methods) {
						if (m.getName().equalsIgnoreCase("getoperaterecords")) {
							operateRecords = (Integer) m.invoke(joinPoint
									.getTarget());
							break;
						}
					}
				}
				if(!isRollback && userSessionObj != null){
//					ICompanyInfoManager companyservice = (ICompanyInfoManager) Global._ctx.getBean("companyservice");
					opLog.setEid(userSessionObj.getEid());
					opLog.setAdminId(userSessionObj.getId());
					opLog.setAdminName(userSessionObj.getLoginId());
					opLog.setC0("10011");
					opLog.setOperateDesc(genericLogger.operateDescription());
					opLog.setOperateTime(new Date(System.currentTimeMillis()));
					opLog.setOperateMark(genericLogger.operateMark().toString());
					opLog.setOperateRecords(operateRecords);
					opLog.setShortName("分析平台");
					if(null == departmentNames || (null != departmentNames && departmentNames.equals(""))){

						if(null != employeeNames && !employeeNames.equals("")){
							opLog.setDeptName("");
						}else{
							opLog.setDeptName(opLog.getShortName());
						}
					}else{
						opLog.setDeptName(departmentNames);
					}

					opLog.setEmployeeName(employeeNames);
					if ( null != descExtendStr && !descExtendStr.equalsIgnoreCase("")) {
						opLog.setOperateDesc(genericLogger.operateDescription()
								+ descExtendStr);
					}
					IOperLogManager logmanage = (IOperLogManager) Global._ctx
							.getBean("operLogManager");
					logmanage.saveOperateLog(opLog);
				}

				if (logger.isInfoEnabled()) {
//					logger.info("operateMark####"+genericLogger.operateMark().toString());
//					logger.info("|operateMark="+opLog.getOperateMark()+"|adminid=" + opLog.getAdminId()+"|operatedesc" + opLog.getOperateDesc()+"|operatetime=" + opLog.getOperateTime()+"|shortName=" + opLog.getShortName()+"|msg=管理平台操作日志");
					
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("invoke(ProceedingJoinPoint) - exception ignored", e); //$NON-NLS-1$  

		} finally {

		}

		if (logger.isDebugEnabled()) {
			logger.debug("invoke(ProceedingJoinPoint) - end"); //$NON-NLS-1$
		}
		return null;

	}
}
