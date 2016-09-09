/*
 * 文件名： LoadConfig.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jfree.util.Log;

import com.cmcc.common.Global;

/**
 * 读取系统配置文件的Java类
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.7 $
 * 
 * @since 2008-11-27
 */
public class LoadConfig {

	private static LoadConfig instance;
	/** zhangzhanliang irxml 文件目录 */
	private String jrxmlUrl;

	/** 平台默认字符集 */
	private String defaultCharSet;
	/** 不得使用最近几次重复的密码 */
	private int recentlyPassword;
	/** 客服邮箱地址 */
	// private String customerServiceMail;

	/** 组结构编码的长度位数，设置2则代表编码只能两位，则统一分类最多支持创建99个组 */
	// private int groupCodeLength;

	/** 分页时每页显示的条目数 */
	private int pageSize;

	/** Session超时时间（分钟） */
	private Integer sessionTimeOut;

	/** 平台生成文件存放位置的相对路径 */
	private String genFilePath;

	/** 平台是否做了集群 */
	private boolean cluster;

	/** 平台的请求后缀名 */
	private String actionExt;

	/** 平台登录是否有验证码 */
	// private boolean verifyImageCode;

	/** 平台是否简化日志提高性能 */
	// private boolean predigestLog;

	/** 用户和用户组映射关系，"single"代表用户和组一一对应,"multi"代表用户和组多对多关系。 */
	// private String userGroupMapping;

	/** 异常资源i18n文件名 */
	private String exceptionMessageFileName;

	/** 资源信息i18n文件名 */
	private String messageFileName;

	/** Cache的实现类全路径 */
	// private String cacheImpl;

	/** 发送邮件的地址 */
	// private String smtpMail;

	/** 邮件服务器是否需要身份验证 */
	// private String smtpAuth;

	/** 邮件服务器地址 */
	// private String smtpHost;

	/** 登陆邮件服务器的用户名 */
	// private String mailUser;

	/** 登陆邮件服务器的密码 */
	// private String mailPasswd;

	/** 密码有效期限 如果为0表示没有期限 */
	private int passwordTime;

	/** 系统提前多少天进行提示 */
	private int systemOverdueRemindDays;

	/** 账号锁定 0表示有锁定功能 1表示没有锁定功能 */
	 private int userLocking;

	// private String localAddress;

	/**
	 * 内容管理上传文件相对路径
	 */
	// private String contentStaticFilepath;

	/**
	 * 内容信息Tab页页面分页显示记录条数
	 */
	// private Integer contentTabPageSize;

	/**
	 * 内容信息Web页页面分页显示记录条数
	 */
	// private Integer contentWebPageSize;

	// /**
	// * 内容信息Web页点完MORE的页面记录条数
	// */
	// private Integer contentShowMorePageSize;
		/**
		 * 内容管理上传文件相对路径
		 */
		private String contentStaticFilepath;
	// *
	// * 内容管理抓取远程图片存放路径

	// private String contentCatchImagePath;

	 private Long contentAttachMaxSize;

	/** 体验版用户的使用期限配置 单位是月 */
	// private int subuseDeploy;

	/** 体验版用户的使用模块权限配置 */
	// private String subuseDeployName;

	/** 教育版用户的行业编码 * */
	// private String callingCode;

	/** 版本号 * */
	// private String versionCode;

	/**
	 * 测试企业短信限额
	 */
	// private Integer demoCorpMsgLmt;/

	/** 缓存客户端数量 * */
	// private int clientNum;

	/** memcached缓存数据失效时间，单位秒 * */
	// private int cacheInvalidationTime;

	/** 搜索模块地址 */

	/** 工程 workflow 节点图片文件保存目录 */
	private String staticNodeFilePath;

	/**
	 * 年份
	 */
	private List years = null;
	
	public String getExceptionMessageFileName() {
		return exceptionMessageFileName;
	}

	public void setExceptionMessageFileName(String exceptionMessageFileName) {
		this.exceptionMessageFileName = exceptionMessageFileName;
	}

	public String getMessageFileName() {
		return messageFileName;
	}

	public void setMessageFileName(String messageFileName) {
		this.messageFileName = messageFileName;
	}

	public int getPasswordTime() {
		return passwordTime;
	}

	public void setPasswordTime(int passwordTime) {
		this.passwordTime = passwordTime;
	}

	public int getSystemOverdueRemindDays() {
		return systemOverdueRemindDays;
	}

	public void setSystemOverdueRemindDays(int systemOverdueRemindDays) {
		this.systemOverdueRemindDays = systemOverdueRemindDays;
	}

	public int getUserLocking() {
		return userLocking;
	}

	public void setUserLocking(int userLocking) {
		this.userLocking = userLocking;
	}

	/** 工程 workflow 节点图片文件大小限制 */
	private Long staticNodeFileMaxSize;

	protected Element root;

	/** Ldap server begin */
	// Ldap
	// private String sunLdapContext;

	// Ldap服务连接地址
	// private String hostUrl;

	// 登陆LDAP的用户名和密码 没有放置在属性配置文件中，属性文件暂时和短信一起 SmsTemplet.properties
	// private String adminUID;

	// 密码
	// private String adminPWD;

	// 安全访问需要的证书库
	// private String sslTrustStore;

	// 安全通道访问
	// private String securityProtocol;

	// 连接TimeOut
	// private String timeOut;

	// 用户组根节点
	private String baseGroupDN;

	// 用户根节点
	private String baseUserDN;

	// 验证方法（1：LDAP验证；2：DB验证；3：LDAP和DB都验证）
	private String validateMethod;

	// tab页签未激活图片
	// public String tablogourl;

	// tab页签激活图片
	// public String tablogohover;

	// tab 页签滑过图片
	// public String tablogomouseover;

	// 校园版
	// private String tablogourl_v2;

	// private String tablogohover_v2;

	// aop 体验转正式调用地址
	// private String aopAddress;

	// private String mopUrl;

	// private String adcWSUrl;

	// 官网系统公告URL路径
	// private String sysmsgUrl;

	// 官网 url 路径
	// private String efetionurl;

	// private String SICode;

	// private int modalTotal; // 布署的总模块数

	// private int modalCurr; // 当前的模块

	/** Ldap server end */
	
	/** 在调查数据，在根据年份进行分组时，条件通过map里面获取 */
	private static Map surveyGroup = new HashMap();
	
	

	public synchronized static LoadConfig getInstance() {

		if (instance == null) {
			instance = new LoadConfig();
		}

		return instance;
	}

	public void reload() {
		load();
	}

	protected LoadConfig() {
		load();
	}

	/**
	 * 读取系统的基本配置文件
	 */
	public void load() {
		SAXReader reader = new SAXReader();

		try {
			Document doc = reader.read(this.getClass().getResourceAsStream(
					Global.CONFIGPATH + Global.CONFIGXMLFILE));

			root = doc.getRootElement();

			this.defaultCharSet = root.element("defaultCharSet").getTextTrim();

			this.actionExt = root.element("actionExt").getTextTrim();

			// this.groupCodeLength = new
			// Integer(root.element("groupCodeLength")
			// .getTextTrim()).intValue();

			this.pageSize = new Integer(root.element("pageSize").getTextTrim())
					.intValue();

			this.sessionTimeOut = new Integer(root.element("sessionTimeOut")
					.getTextTrim());

			this.genFilePath = root.element("genFilePath").getTextTrim();

			this.cluster = new Boolean(root.element("cluster").getTextTrim())
					.booleanValue();

			// this.verifyImageCode = new
			// Boolean(root.element("verifyImageCode")
			// .getTextTrim()).booleanValue();

			// this.predigestLog = new Boolean(root.element("predigestLog")
			// .getTextTrim()).booleanValue();

			// this.userGroupMapping = root.element("userGroupMapping")
			// .getTextTrim();

			// this.messageFileName = root.element("i18nMessageFileName")
			// .getTextTrim();

			// this.exceptionMessageFileName = root.element(
			// "i18nExceptionMessageFileName").getTextTrim();

			// this.customerServiceMail = root.element("customerServiceMail")
			// .getTextTrim();

			// this.cacheImpl = root.element("cacheImpl").getTextTrim();

			// this.demoCorpMsgLmt = new Integer(root.element("demoCorpMsgLmt")
			// .getTextTrim()).intValue();
			// // 解析Mail配置信息
			// Element mailConfig = root.element("mailConfig");
			// this.smtpMail = mailConfig.elementTextTrim("smtpMail");
			// this.smtpAuth = mailConfig.elementTextTrim("smtpAuth");
			// this.smtpHost = mailConfig.elementTextTrim("smtpHost");
			// this.mailUser = mailConfig.elementTextTrim("mailUser");
			// this.mailPasswd = mailConfig.elementTextTrim("mailPasswd");
			// 获得LDAP链接的相关配置信息
			// Element ldapConfig = root.element("ldapConnect");
			// this.sunLdapContext = ldapConfig.elementTextTrim("ldapContext");
			// this.hostUrl = ldapConfig.elementTextTrim("host");
			// this.adminUID = ldapConfig.elementTextTrim("adminUID");
			// this.adminPWD = ldapConfig.elementTextTrim("adminPWD");
			// this.baseGroupDN = ldapConfig.elementTextTrim("baseGroupDN");
			// this.baseUserDN = ldapConfig.elementTextTrim("baseUserDN");
			// this.securityProtocol = ldapConfig
			// .elementTextTrim("securityProtocol");
			// this.sslTrustStore = ldapConfig.elementTextTrim("trustStore");
			// this.validateMethod =
			// ldapConfig.elementTextTrim("validateMethod");
			// this.timeOut = ldapConfig.elementTextTrim("timeOut");
			this.contentStaticFilepath = root.element("contentStaticFilepath")
					.getTextTrim();
			
			this.contentAttachMaxSize = new Long(root.element(
					"contentAttachMaxSize").getTextTrim());
			
			Element userSecurity = root.element("userSecurity");

			this.passwordTime = Integer.parseInt(userSecurity
					.elementTextTrim("passwordTime"));

			this.systemOverdueRemindDays = Integer.parseInt(userSecurity
					.elementTextTrim("systemOverdueRemindDays"));

			this.userLocking = Integer.parseInt(userSecurity
					.elementTextTrim("userLocking"));

			this.recentlyPassword = Integer.parseInt(userSecurity
					.elementTextTrim("recentlyPassword"));
			this.recentlyPassword = Integer.parseInt(userSecurity
					.elementTextTrim("recentlyPassword"));
			this.staticNodeFilePath = root.element("staticNodeFilePath")
					.getTextTrim();
			this.staticNodeFileMaxSize = new Long(root.element(
					"staticNodeFileMaxSize").getTextTrim());

			this.jrxmlUrl = root.element("jrxmlUrl").getTextTrim();
			
			// 调查数据分组时，查询条件的
			Element suveyDataS = root.element("suveyDataS");
			if(this.surveyGroup == null){
				this.surveyGroup = new HashMap();
			}else{
				this.surveyGroup.clear();
			}
			Iterator<Element> its = suveyDataS.elementIterator("CEN_GROUP");
			Element objs = null;
			String name = null;
			String values = null;
			while(its.hasNext()){
				objs = its.next();
				name = objs.attributeValue("name");
				values = objs.getTextTrim();
				this.surveyGroup.put(name.trim(), values);
			}
			
			
			
			// 调查数据分组时，查询条件的
			Element elYears = root.element("years");
						
						
			if(this.years == null){
				this.years = new ArrayList();
			}else{
				this.years.clear();
			}
			Iterator<Element> it = elYears.elementIterator("value");
			Element obj = null;
			String value = null;
			while(it.hasNext()){
				obj = it.next();
				value = obj.getTextTrim();
				this.years.add(value);
			}
			

		} catch (DocumentException e) {
			Log.error(e.getMessage(),e);
		}
	}

	// public String getCustomerServiceMail() {
	// return customerServiceMail;
	// }

	// public void setCustomerServiceMail(String customerServiceMail) {
	// this.customerServiceMail = customerServiceMail;
	// }

	// public int getGroupCodeLength() {
	// return groupCodeLength;
	// }
	//
	// public void setGroupCodeLength(int groupCodeLength) {
	// this.groupCodeLength = groupCodeLength;
	// }

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getSessionTimeOut() {
		return sessionTimeOut;
	}

	public void setSessionTimeOut(Integer sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	public String getGenFilePath() {
		return genFilePath;
	}

	public void setGenFilePath(String genFilePath) {
		this.genFilePath = genFilePath;
	}

	public boolean isCluster() {
		return cluster;
	}

	public void setCluster(boolean cluster) {
		this.cluster = cluster;
	}

	public String getActionExt() {
		return actionExt;
	}

	public void setActionExt(String actionExt) {
		this.actionExt = actionExt;
	}

	// public boolean isVerifyImageCode() {
	// return verifyImageCode;
	// }

	// public void setVerifyImageCode(boolean verifyImageCode) {
	// this.verifyImageCode = verifyImageCode;
	// }

	// public boolean isPredigestLog() {
	// return predigestLog;
	// }
	//
	// public void setPredigestLog(boolean predigestLog) {
	// this.predigestLog = predigestLog;
	// }

	// public String getUserGroupMapping() {
	// return userGroupMapping;
	// }

	// public void setUserGroupMapping(String userGroupMapping) {
	// this.userGroupMapping = userGroupMapping;
	// }

	// public String getExceptionMessageFileName() {
	// return exceptionMessageFileName;
	// }

	// public void setExceptionMessageFileName(String exceptionMessageFileName)
	// {
	// this.exceptionMessageFileName = exceptionMessageFileName;
	// }

	// public String getMessageFileName() {
	// return messageFileName;
	// }

	// public void setMessageFileName(String messageFileName) {
	// this.messageFileName = messageFileName;
	// }

	// public String getCacheImpl() {
	// return cacheImpl;
	// }

	// public void setCacheImpl(String cacheImpl) {
	// this.cacheImpl = cacheImpl;
	// }

	public String getDefaultCharSet() {
		return defaultCharSet;
	}

	public void setDefaultCharSet(String defaultCharSet) {
		this.defaultCharSet = defaultCharSet;
	}

	/**
	 * @return 返回 baseGroupDN。
	 */
	public String getBaseGroupDN() {
		return baseGroupDN;
	}

	/**
	 * @param baseGroupDN
	 *            要设置的 baseGroupDN。
	 */
	public void setBaseGroupDN(String baseGroupDN) {
		this.baseGroupDN = baseGroupDN;
	}

	/**
	 * @return 返回 baseUserDN。
	 */
	public String getBaseUserDN() {
		return baseUserDN;
	}

	/**
	 * @param baseUserDN
	 *            要设置的 baseUserDN。
	 */
	public void setBaseUserDN(String baseUserDN) {
		this.baseUserDN = baseUserDN;
	}

	/**
	 * @return 返回 validateMethod。
	 */
	public String getValidateMethod() {
		return validateMethod;
	}

	/**
	 * @param validateMethod
	 *            要设置的 validateMethod。
	 */
	public void setValidateMethod(String validateMethod) {
		this.validateMethod = validateMethod;
	}

	public Long getStaticNodeFileMaxSize() {
		return staticNodeFileMaxSize;
	}

	public int getRecentlyPassword() {
		return recentlyPassword;
	}

	public void setRecentlyPassword(int recentlyPassword) {
		this.recentlyPassword = recentlyPassword;
	}

	public void setStaticNodeFileMaxSize(Long staticNodeFileMaxSize) {
		this.staticNodeFileMaxSize = staticNodeFileMaxSize;
	}

	public String getStaticNodeFilePath() {
		return staticNodeFilePath;
	}

	public void setStaticNodeFilePath(String staticNodeFilePath) {
		this.staticNodeFilePath = staticNodeFilePath;
	}

	public String getJrxmlUrl() {
		return jrxmlUrl;
	}

	public void setJrxmlUrl(String jrxmlUrl) {
		this.jrxmlUrl = jrxmlUrl;
	}
	public String getContentStaticFilepath() {
		return this.contentStaticFilepath;
	}

	public void setContentStaticFilepath(String contentStaticFilepath) {
		this.contentStaticFilepath = contentStaticFilepath;
	}
	public Long getContentAttachMaxSize() {
		return contentAttachMaxSize;
	}
	public void setContentAttachMaxSize(Long contentAttachMaxSize) {
		this.contentAttachMaxSize = contentAttachMaxSize;
	}

	public List getYears() {
		return years;
	}

	public void setYears(List years) {
		this.years = years;
	}

	public Map getSurveyGroup() {
		return surveyGroup;
	}

	public void setSurveyGroup(Map surveyGroup) {
		LoadConfig.surveyGroup = surveyGroup;
	}
	

	
}
