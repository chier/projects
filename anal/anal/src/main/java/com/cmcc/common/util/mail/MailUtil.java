/*
 * 文件名： MailUtil.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.util.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.cmcc.common.Global;
import com.cmcc.common.util.CharacterCodingUtil;

/**
 * 发送邮件的工具类
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
public class MailUtil {

	// MIME邮件对象
	private MimeMessage mimeMsg;

	// 邮件会话对象
	private Session session;

	// Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象
	private Multipart mp;

	// 循环发送的次数，防止一次发送不成功
	private static int reaction = 3;

	/**
	 * 默认构造函数，从Config里面读取Mail的配置信息
	 * 
	 */
	public MailUtil() {
		init();
	}

	/**
	 * 构建一个需要身份验证的MailUtil实例
	 * 
	 * @param mailUser
	 *            登陆Mail服务器的用户
	 * @param mailPasswd
	 *            登陆Mail服务器的密码
	 */
	public MailUtil(String mailUser, String mailPasswd) {
		try {
			Properties props = new Properties();
			// props.put("mail.smtp.host", Global.SMTP_HOST);
			props.put("mail.smtp.auth", "true");
			MailAuthenticator mailAuthenticator = new MailAuthenticator();
			mailAuthenticator.performCheck(mailUser, mailPasswd);
			session = Session.getDefaultInstance(props, mailAuthenticator);
			mimeMsg = new MimeMessage(session);
			mp = new MimeMultipart();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从配置文件读取SMTP配置信息
	 * 
	 */
	private void init() {
		// try {
		// Properties props = new Properties();
		// // props.put("mail.smtp.host", Global.SMTP_HOST);
		// // if (Global.SMTP_AUTH.equalsIgnoreCase("true")) {
		// // props.put("mail.smtp.auth", Global.SMTP_AUTH);
		// // MailAuthenticator mailAuthenticator = new MailAuthenticator();
		// // mailAuthenticator.performCheck(Global.MAIL_USER,
		// // Global.MAIL_PASSWORD);
		// session = Session.getDefaultInstance(props, mailAuthenticator);
		// }
		// else {
		// session = Session.getDefaultInstance(props, null);
		// }
		// mimeMsg = new MimeMessage(session);
		// mp = new MimeMultipart();
		//
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * 设置发件人地址
	 * 
	 * @param from
	 *            发件人地址的字符串
	 * @throws Exception
	 */
	public void setFrom(String from) throws Exception {
		mimeMsg.setFrom(new InternetAddress(from));
	}

	/**
	 * 设置收件人地址
	 * 
	 * @param to
	 *            用“,”隔开的收件人地址的字符串，例如“test@163.com,test2@sohu.com”
	 * @throws Exception
	 */
	public void setTo(String to) throws Exception {
		mimeMsg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
	}

	/**
	 * 设置抄送人地址
	 * 
	 * @param copyto
	 *            用“,”隔开的抄送人地址的字符串，例如“test@163.com,test2@sohu.com”
	 * @throws Exception
	 */
	public void setCopyTo(String copyto) throws Exception {
		mimeMsg.setRecipients(Message.RecipientType.CC,
				InternetAddress.parse(copyto));
	}

	/**
	 * 设置秘密抄送人地址
	 * 
	 * @param blindCopyTo
	 *            用“,”隔开的秘密抄送人地址的字符串，例如“test@163.com,test2@sohu.com”
	 * @throws Exception
	 */
	public void setBlindCopyTo(String blindCopyTo) throws Exception {
		mimeMsg.setRecipients(Message.RecipientType.BCC,
				InternetAddress.parse(blindCopyTo));
	}

	/**
	 * 设置邮件的主题
	 * 
	 * @param mailSubject
	 *            要设置的主题
	 * @throws Exception
	 */
	public void setSubject(String mailSubject) throws Exception {
		mimeMsg.setSubject(mailSubject);
	}

	/**
	 * 设置邮件的Body
	 * 
	 * @param mailBody
	 *            要设置的邮件Body
	 * @throws Exception
	 */
	public void setBody(String mailBody) throws Exception {
		mp = new MimeMultipart();
		BodyPart bp = new MimeBodyPart();
		bp.setContent(mailBody, "text/html;charset=" + Global.CHARSET);
		mp.addBodyPart(bp);
	}

	/**
	 * 增加附件
	 * 
	 * @param filename
	 *            附件的完整文件名，例如：c:\test.txt
	 * @throws Exception
	 */
	public void addFileAffix(String filename) throws Exception {
		BodyPart bp = new MimeBodyPart();
		FileDataSource fileds = new FileDataSource(filename);
		bp.setDataHandler(new DataHandler(fileds));
		bp.setFileName(CharacterCodingUtil.GBK2unicode(fileds.getName()));
		mp.addBodyPart(bp);
	}

	public boolean sendOut() {
		// 是否发送成功的标志
		boolean success = false;
		// 检查是否设置了发件人地址
		try {
			if (mimeMsg.getFrom().length < 1) {
				// mimeMsg.setFrom(new InternetAddress(Global.SMTP_MAIL));
			}
		} catch (AddressException e1) {
			e1.printStackTrace();
		} catch (MessagingException e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < reaction; i++) {
			try {
				mimeMsg.setContent(mp);
				mimeMsg.saveChanges();
				Transport.send(mimeMsg);
				success = true;
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (success == true) {
				return success;
			}

			try {
				Thread.sleep(3000);
			} catch (Exception e) {
			}
		}
		return success;
	}

}
