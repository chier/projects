/*
 * 文件名： SystemRuntimeBaseException.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.exception.base;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.cmcc.common.Global;
import com.cmcc.common.util.mail.MailUtil;

/**
 * <code>SystemRuntimeBaseException</code> 和它的子类用于指示系统的运行时异常
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
public class SystemRuntimeBaseException extends RuntimeException {

	private static final long serialVersionUID = -7966469492296450104L;

	protected Throwable throwable;

	public SystemRuntimeBaseException() {
		super("System Runtime Error");
		throwable = null;
	}

	public SystemRuntimeBaseException(String msg) {
		super(msg);
		throwable = null;
		
	}

	public SystemRuntimeBaseException(Throwable throwable) {
		this.throwable = throwable;
	}

	public SystemRuntimeBaseException(String msg, Throwable throwable) {
		this(msg);
		this.throwable = throwable;
	}

	public Throwable getLinkedException() {
		return throwable;
	}

	public void setLinkedException(Throwable throwable) {
		this.throwable = throwable;
	}

	public String getMessage() {
		if (throwable == null) {
			return super.getMessage();
		}		else {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			throwable.printStackTrace(pw);
			return super.getMessage() + ": " + throwable.getMessage()
					+ sw.toString();
		}
	}

	protected void sendMail(String mailBody) {
		try {
			MailUtil mailUtil = new MailUtil();
//			mailUtil.setFrom(Global.SMTP_MAIL);
//			mailUtil.setTo(Global.CUSTOMER_SERVICE_MAIL);
			mailUtil.setSubject( "Platform received Exception");
			mailUtil.setBody(mailBody);
			mailUtil.sendOut();
		}		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
