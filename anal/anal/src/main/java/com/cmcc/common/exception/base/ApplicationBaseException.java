/*
 * 文件名： ApplicationBaseException.java
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
 * <code>ApplicationBaseException</code> 和它的子类用于表示应用级的异常
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
public class ApplicationBaseException extends Exception {

	private static final long serialVersionUID = 9190919788820873367L;

	protected Throwable throwable;

	public ApplicationBaseException() {
		super("Application Error");
		throwable = null;
	}

	public ApplicationBaseException(String msg) {
		super(msg);
		throwable = null;
		
	}

	public ApplicationBaseException(Throwable throwable) {
		this.throwable = throwable;
	}

	public ApplicationBaseException(String msg, Throwable throwable) {
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
