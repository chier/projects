/*
 * 文件名： MailAuthenticator.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Mail身份认证信息的Java类
 *
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public class MailAuthenticator extends Authenticator{
	
	private String username;

	private String password;

	public PasswordAuthentication performCheck(String user, String pass) {
		username = user;
		password = pass;
		return getPasswordAuthentication();
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

}
