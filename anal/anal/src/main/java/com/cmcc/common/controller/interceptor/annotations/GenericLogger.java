/*
 * 文件名： GenericLogger.java
 * 
 * 创建日期： 2009-3-23
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.common.controller.interceptor.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.cmcc.common.controller.interceptor.enums.OperateMark;

/**
 * 
 * 相关日志记录使用到的注解类型
 * @author <a href="mailto:sun128837@126.com">conntsing</a>
 * 
 * @version $Revision: 1.2 $
 * 
 * @since 2009-3-23
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface GenericLogger {
	/*
	 * 描述
	 */
	String operateDescription() default "";
	/*
	 * 是否会操作员工
	 */
	boolean isOperateEmployee() default false;
	/*
	 * 是否会操作部门
	 */
	boolean isOperateDepartment() default false;
	/*
	 * 是否扩展
	 */
	boolean isExtend() default false;
	/*
	 * 是否存在继续记录日志;(当业务执行到某个逻辑的时候，存在逻辑否情况，不修改数据直接跳出方法)
	 */
	boolean isRollback() default false;
	/*
	 * 是否记录批量操作数据记录数（条数）
	 */
	boolean isOperateRecords() default false;
	/*
	 * 操作标识
	 */
	OperateMark operateMark() default OperateMark.OPERATE_DEFAULT;
}
