package org;

import java.io.File;

/**
 * ftp 下载 信息
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-24
 */
public class FtpDownloadInfo {

	private String ftpIP = LoadConfig.getInstance().getFtpIp();

	private Integer ftpPort = LoadConfig.getInstance().getFtpPort();

	private String name = LoadConfig.getInstance().getFtpName();

	private String pwd = LoadConfig.getInstance().getFtpPwd();

	/**
	 * ftp 客户端
	 */
	// private FTPClient ftpClient;
	private String sourceName;

	/**
	 * 源文件名称
	 */
	private String fileNmae;

	/**
	 * 目标文件
	 */
	private File targetFile;

	// 验证 文件
	private File valiFile;

	private File zipTargetFile;

	public File getZipTargetFile() {
		return zipTargetFile;
	}

	public void setZipTargetFile(File zipTargetFile) {
		this.zipTargetFile = zipTargetFile;
	}

	public FtpDownloadInfo(String sourceName, String fileNmae, File targetFile,
			File valiFile, File zipTargetFile) {
		super();
		this.sourceName = sourceName;
		this.fileNmae = fileNmae;
		this.targetFile = targetFile;
		this.valiFile = valiFile;
		this.zipTargetFile = zipTargetFile;
	}

	public String getFileNmae() {
		return fileNmae;
	}

	public void setFileNmae(String fileNmae) {
		this.fileNmae = fileNmae;
	}

	public File getTargetFile() {
		return targetFile;
	}

	public void setTargetFile(File targetFile) {
		this.targetFile = targetFile;
	}

	public String getFtpIP() {
		return ftpIP;
	}

	public void setFtpIP(String ftpIP) {
		this.ftpIP = ftpIP;
	}

	public Integer getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(Integer ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public File getValiFile() {
		return valiFile;
	}

	public void setValiFile(File valiFile) {
		this.valiFile = valiFile;
	}

}
