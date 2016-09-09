/*
 * 文件名： BusinessException.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.exception;

import com.cmcc.common.exception.base.ApplicationBaseException;

/**
 * 系统业务操作异常的Java类
 *
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public class BusinessException extends ApplicationBaseException {

	private static final long serialVersionUID = -8385330309200263594L;

	public BusinessException() {
		super();
	}

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(Throwable throwable) {
		super(throwable);
	}

	public BusinessException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
