/*
 * 文件名： DataException.java
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
 * 数据不符合要求的异常Java类
 *
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public class DataException extends ApplicationBaseException {

	private static final long serialVersionUID = -2946009845958865297L;

	public DataException() {
        super();
    }

    public DataException(String msg) {
        super(msg);
    }

    public DataException(Throwable throwable) {
        super(throwable);
    }

    public DataException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
