/*
 * 文件名： Global.java
 * 
 * 创建日期�?2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作�? <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;

import com.cmcc.common.config.LoadConfig;

// import com.cmcc.common.util.ServerList;
// import com.cmcc.common.xrt.Queue;
// import com.cmcc.common.xrt.bean.XrtConnectBean;
// import com.cmcc.framework.model.gcfgssikeys.GcfgSsikeys;
// import com.cmcc.ws.business.interfaces.EnterpriseEmployeeService;
// import com.cmcc.ws.business.interfaces.EnterpriseService;

/**
 * 定义一些框架需要的全局变量
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.9 $
 * 
 * @since 2008-11-27
 */
public class Global {

	/** workflow 节点图片目录 */
	public static String staticNodeFilePath = LoadConfig.getInstance()
			.getStaticNodeFilePath();

	/** workflow 节点图片大小限制 */
	public static long staticNodeFileMaxSize = LoadConfig.getInstance()
			.getStaticNodeFileMaxSize();

	/** zhangzhanliang jrxml html 服务器存储路径 */
	public static String staticRportHtml = "static/report/html/";
	/** zhangzhanliang jrxml 文件 文件后缀名 */
	public static String jrxmlSuffix = ".jrxml";

	/** 加载WebService和媒体服务器的URL地址,以及配置参数 */
	// public static List<ServerList> wsEnterpriseEmployeeService = new
	// ArrayList<ServerList>();
	// public static HashMap<Integer, EnterpriseEmployeeService> serviceMap =
	// new HashMap<Integer, EnterpriseEmployeeService>();
	// public static HashMap<Integer, EnterpriseService> serviceEnterpriseMap =
	// new HashMap<Integer, EnterpriseService>();
	public static HashMap<Integer, String> mediaMap = new HashMap<Integer, String>();

	public static int webServiceMid = 1023;

	public static int mediaServiceMid = 1018;

	public static String webServiceUrl = "/EnterpriseEmployeeService.ws";

	public static String webEnterpriseService = "/EnterpriseService.ws";

	/** 核心域配�? */
	// public static HashMap<String, GcfgSsikeys> ssikeyMap = new
	// HashMap<String, GcfgSsikeys>();
	public static String SSO_SERVER_DOMAIN = "211.99.191.99";

	/** 配置文件路径 */
	public static final String CONFIGPATH = "/";

	/** 当前服务器的路径 */

	/** 系统配置文件�? */
	// public static final String SPRINGCONFIGXMLFILE =
	// "applicationContext.xml";
	/** 当前应用的绝对路径，例如：D:\Eclipse\workspace\test\WebRoot\ */
	public static String APP_REAL_PATH = null;

	/** 当前系统中所有体验版的url */
	public static String[] turl = null;

	/** 系统配置文件�? */
	public static final String CONFIGXMLFILE = "systemConfig.xml";

	/** 分页时每页显示的条目�? */
	public static int PAGESIZE = LoadConfig.getInstance().getPageSize();

	/** 平台生成文件存放位置的相对路�? */
	public static String GEN_FILE_PATH = LoadConfig.getInstance()
			.getGenFilePath();

	/** 平台是否做了集群 */
	public static boolean IS_CLUSTER = LoadConfig.getInstance().isCluster();

	/** 平台的请求后缀�? */
	public static String ACTION_EXT = LoadConfig.getInstance().getActionExt();

	/** 平台登录是否有验证码 */
	// public static boolean IS_VERIFY_IMAGE_CODE = LoadConfig.getInstance()
	// .isVerifyImageCode();

	/** 平台是否简化日志提高性能 */
	// public static boolean IS_PREDIGEST_LOG = LoadConfig.getInstance()
	// .isPredigestLog();

	/** Session超时时间（分钟） */
	public static Integer SESSION_TIMEOUT = LoadConfig.getInstance()
			.getSessionTimeOut();

	/** 组结构编码的长度 */
	// public static final int GROUPCODELENGTH = LoadConfig.getInstance()
	// .getGroupCodeLength();

	/** 默认字符�? */
	public static final String CHARSET = LoadConfig.getInstance()
			.getDefaultCharSet();// "UTF-8";

	/** 客服邮箱地址 */
	// public static String CUSTOMER_SERVICE_MAIL = LoadConfig.getInstance()
	// .getCustomerServiceMail();

	/** 异常i18n资源的文件名 */
	// public static final String EXCEPTION_I18N_MESSAGE_FILE = LoadConfig
	// .getInstance().getExceptionMessageFileName();

	/** i18n资源文件�? */
	// public static final String I18N_MESSAGE_FILE = LoadConfig.getInstance()
	// .getMessageFileName();

	/** Cache的实现类全路�? */
	// public static String CACHE_IMPL =
	// LoadConfig.getInstance().getCacheImpl();

	/** Cache在初始化的时候是否被执行 */
	// public static boolean CACHE_FLAG = true;

	/** 用户和用户组对应关系�?single"代表用户和组一一对应,"multi"代表用户和组多对多关系�? */
	// public static final String USER_GROUP_MAPPING = LoadConfig.getInstance()
	// .getUserGroupMapping();

	/**
	 * IP数据文件�?
	 */
	public static final String IPDATE_FILE = "QQWry.Dat";

	/**
	 * IP数据文件的存放地址，例如：D:\Eclipse\workspace\test\WebRoot\static\IPDate\
	 */
	public static String IPDATE_FILE_PATH = null;

	/** 应用启动的时�? */
	public static final long APP_BEGINTIME = System.currentTimeMillis();

	/** Spring的ApplicationContext，使用的时候要自己设置�? */
	public static ApplicationContext _ctx = null;
	/** 不得使用最近几次重复的密码 */
	public static int RECENTLY_PASSWORD = LoadConfig.getInstance()
			.getRecentlyPassword();
	/** 密码有效期限 如果�?表示没有期限 */
	public static int PASSWORD_TIME = LoadConfig.getInstance()
			.getPasswordTime();

	/** 调度对象 */
	public static Scheduler SCHEDULER = null;

	/** 获取Ldap的组根节�? */
	public static final String LDAP_BASEGROUPDN = LoadConfig.getInstance()
			.getBaseGroupDN();

	/** 获取Ldap的用户根节点 */
	public static final String LDAP_BASEUSERDN = LoadConfig.getInstance()
			.getBaseUserDN();

	/** 获取Ldap的验证方法（1：LDAP验证�?：DB验证�?：LDAP和DB都验证） */
	public static final String LDAP_VALIDATEMETHOD = LoadConfig.getInstance()
			.getValidateMethod();

	/**
	 * Fckeditor上传文件相对路径
	 */
	public static String FCK_USERFILESPATH = null;

	/**
	 * XRT消息队列
	 */
	// public static Queue queue_ereq = new Queue();
	//
	// public static ArrayList<XrtConnectBean> xrt_list = new
	// ArrayList<XrtConnectBean>();
	public static final int XRT_QUEUE_LEN = 1000;

	public static final String XRT_ERRRESULT = "ERROR";

	public static final String XRT_LOGIN = "<?xml version=\"1.0\"?>"
			+ "<stream:stream xmlns:stream=\"http://etherx.jabber.org/streams\" "
			+ "xmlns=\"jabber:component:accept\" to=\"{0}\" >";

	public static final String XRT_HAND = "<handshake>aaee83c26aeeafcbabeabfcbcd50df997e0a2a1e</handshake>";

	public static final String UDP_MID = "mop.cmcc";

	public static final String XRT_MID = "1005";

	public static Thread xrtDAEMON = null;

	// XRT配置加载时间间隔
	public static final long XRT_CFG_RELOAD_INTERVAL = 360000L;

	public static Map permissionCache = new HashMap();

	/**
	 * 内容管理上传文件存放相对路径
	 */
	public static final String CONTENT_STATIC_FILEPATH = LoadConfig
			.getInstance().getContentStaticFilepath();
	/**
	 * 内容管理上传附件限制大小
	 */
	public static Long CONTENT_ATTACH_MAXSIZE = LoadConfig.getInstance()
			.getContentAttachMaxSize();
}
