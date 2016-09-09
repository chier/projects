package org;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 系统文件读取
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-16
 */
public class LoadConfig {

	private static Logger logger = Logger.getLogger(LoadConfig.class);

	/**
	 * 当前记录的日期
	 */
	public static String currentDate = null;
	
	private static LoadConfig instance;

	/** 配置文件路径 */
	public static final String CONFIGPATH = "/";

	/** 系统配置文件�? */
	public static final String CONFIGXMLFILE = "systemConfig.xml";

	protected Element root;

	/**
	 * 复制类型 ： 1表示虚拟硬盘复制 2 表示FTP下载
	 */
	private Integer copyType;

	/**
	 * 源目录
	 */
	private String sourceUrl;

	/**
	 * 目标目录
	 */
	private String targetUrl;

	/**
	 * 参照目录
	 */
	private String validationUrl;

	/**
	 * ftp ip 地址
	 */
	private String ftpIp;

	/**
	 * ftp 端口
	 */
	private Integer ftpPort;

	/**
	 * 帐号名称
	 */
	private String ftpName;

	/**
	 * 密码
	 */
	private String ftpPwd;

	/**
	 * 删除几个月前的备份
	 */
	private Integer delDays;

	/**
	 * 实施时间
	 */
	private String executeTime;

	/**
	 * 时间间隔
	 */
	private Long timeperiod;

	private Integer ftpThreadNum;

	/**
	 * 加密码
	 */
	private String key;

	/**
	 * 压缩目标目录
	 */
	private String zipTargetUrl;
	/**
	 * 压缩文件 解压后目录
	 */
	private String zipUpdateUrl;

	protected LoadConfig() {
		load();
	}

	public synchronized static LoadConfig getInstance() {
		if (instance == null) {
			instance = new LoadConfig();
		}

		return instance;
	}

	public void load() {
		SAXReader reader = new SAXReader();

		Document doc = null;
		try {
			String home = System.getProperty("MYCATALINA_HOME");
			logger.info("home = " + home);
			if (home != null || !"".equals(home)) {
				try {
					doc = reader.read(home + LoadConfig.CONFIGPATH
							+ LoadConfig.CONFIGXMLFILE);
				} catch (Exception e) {
					logger.info("doc error ", e);
					doc = null;
				}
				logger.info("doc == null " + (doc == null));
			} else {
				doc = reader.read(this.getClass().getResourceAsStream(
						LoadConfig.CONFIGPATH + LoadConfig.CONFIGXMLFILE));
			}

			if (doc == null) {
				doc = reader.read(this.getClass().getResourceAsStream(
						LoadConfig.CONFIGPATH + LoadConfig.CONFIGXMLFILE));
			}

			root = doc.getRootElement();

			this.copyType = Integer.valueOf(root.element("copyType")
					.getTextTrim());

			this.sourceUrl = root.element("sourceUrl").getTextTrim();
			this.targetUrl = root.element("targetUrl").getTextTrim();
			this.validationUrl = root.element("validationUrl").getTextTrim();
			this.ftpIp = root.element("ftpIp").getTextTrim();
			this.ftpPort = Integer.valueOf(root.element("ftpPort")
					.getTextTrim());
			this.ftpName = root.element("ftpName").getTextTrim();
			this.ftpPwd = root.element("ftpPwd").getTextTrim();
			this.delDays = Integer.valueOf(root.element("delDays")
					.getTextTrim());

			this.executeTime = root.element("executeTime").getTextTrim();

			this.timeperiod = Long.valueOf(root.element("timeperiod")
					.getTextTrim());
			this.ftpThreadNum = Integer.valueOf(root.element("ftpThreadNum")
					.getTextTrim());
			this.key = root.element("key").getTextTrim();

			this.zipTargetUrl = root.element("zipTargetUrl").getTextTrim();

			this.zipUpdateUrl = root.element("zipUpdateUrl").getTextTrim();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public Integer getCopyType() {
		return copyType;
	}

	public void setCopyType(Integer copyType) {
		this.copyType = copyType;
	}

	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}

	public String getFtpName() {
		return ftpName;
	}

	public void setFtpName(String ftpName) {
		this.ftpName = ftpName;
	}

	public Integer getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpPwd() {
		return ftpPwd;
	}

	public void setFtpPwd(String ftpPwd) {
		this.ftpPwd = ftpPwd;
	}

	public Element getRoot() {
		return root;
	}

	public void setRoot(Element root) {
		this.root = root;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}

	public Long getTimeperiod() {
		return timeperiod;
	}

	public void setTimeperiod(Long timeperiod) {
		this.timeperiod = timeperiod;
	}

	public String getZipTargetUrl() {
		return zipTargetUrl;
	}

	public void setZipTargetUrl(String zipTargetUrl) {
		this.zipTargetUrl = zipTargetUrl;
	}

	public Integer getDelDays() {
		return delDays;
	}

	public void setDelDays(Integer delDays) {
		this.delDays = delDays;
	}

	public Integer getFtpThreadNum() {
		if (ftpThreadNum <= 0) {
			ftpThreadNum = 5;
		}
		if (ftpThreadNum > 10) {
			ftpThreadNum = 10;
		}
		return ftpThreadNum;
	}

	public void setFtpThreadNum(Integer ftpThreadNum) {
		this.ftpThreadNum = ftpThreadNum;
	}

	public String getValidationUrl() {
		return validationUrl;
	}

	public void setValidationUrl(String validationUrl) {
		this.validationUrl = validationUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getZipUpdateUrl() {
		return zipUpdateUrl;
	}

	public void setZipUpdateUrl(String zipUpdateUrl) {
		this.zipUpdateUrl = zipUpdateUrl;
	}

}
