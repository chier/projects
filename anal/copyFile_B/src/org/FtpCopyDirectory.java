package org;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.timer.CopyTimerTask;

public class FtpCopyDirectory {

	private Logger logger = Logger.getLogger(FtpCopyDirectory.class);

	private String ftpIP = LoadConfig.getInstance().getFtpIp();

	private Integer ftpPort = LoadConfig.getInstance().getFtpPort();

	private String name = LoadConfig.getInstance().getFtpName();

	private String pwd = LoadConfig.getInstance().getFtpPwd();

	private FtpDownloadThread thread = FtpDownloadThread.getInstance();

	private static int filesTotal = -1;

	private int total = 0;

	public static void main(String[] args) {
		new FtpCopyDirectory().start();
	}

	public void start() {
		thread.start(LoadConfig.getInstance().getFtpThreadNum());
		FTPClient ftp = new FTPClient();
		try {

			int reply;
			logger.info("ftpIP=" + ftpIP + " | ftpPort" + ftpPort
					+ " | name = " + name + " | pwd = " + pwd);
			ftp.connect(ftpIP, ftpPort);
			ftp.setControlEncoding("GB2312");
			ftp.login(name, pwd);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.info("没有连接到ftp服务器");
				return;
			}
			String parth = CopyTimerTask.sourceUrl;
			String localPath = CopyTimerTask.targetUrl;
			String validationUrl = CopyTimerTask.validationUrl;
			String zipTargetUrl = CopyTimerTask.zipTargetUrl;
			localPath = localPath + File.separator + LoadConfig.currentDate;
			zipTargetUrl = zipTargetUrl + "/" + LoadConfig.currentDate;
			File file = new File(localPath);
			file.mkdirs();

			File f = new File(zipTargetUrl);
			f.mkdirs();
			ftp.changeWorkingDirectory(parth);

			FTPFile[] fs = ftp.listFiles();

			for (FTPFile ff : fs) {
				if (!".".equals(ff.getName()) && !"..".equals(ff.getName())) {
					if (ff.isFile()) { // 当保存的是文件时
						File localFile = new File(localPath + "/"
								+ ff.getName());
						File valiFile = new File(validationUrl + "/"
								+ ff.getName());
						File zipTargetFile = new File(zipTargetUrl + "/"
								+ ff.getName());
						copyFile(parth, ff, localFile, valiFile, zipTargetFile);
					}
					if (ff.isDirectory()) { // 当保存的是文件夹时
						// 准备复制的源文件夹
						String dir1 = parth + "/" + ff.getName();
						// 准备复制的目标文件夹
						String dir2 = localPath + "/" + ff.getName();

						String valiDir = validationUrl + "/" + ff.getName();

						String zipTarget = zipTargetUrl + "/" + ff.getName();
						copyDirectiory(dir1, dir2, valiDir, zipTarget);
					}
				}
			}

			// overFilePath = validationUrl + File.separator + "over.txt";

			ftp.logout();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}

		filesTotal = total;
	}

	public void creaetZipFile() {
		// logger.info("压缩开始");
		// LoadConfig config = LoadConfig.getInstance();
		// ZipCompressor zc = new ZipCompressor();
		// zc.compress(config.getZipTargetUrl() + File.separator
		// + config.getZipName(), CopyTimerTask.validationUrl);
		// logger.info("压缩完成");
		// if (LoadConfig.getInstance().getIsKey().intValue() == 1) {
		// logger.info("执行加密操作");
		// EncryptUtil encryptUtil = new EncryptUtil();
		// encryptUtil.encrypt(new File(config.getZipTargetUrl()
		// + File.separator + config.getZipName()), config.getKey());
		// logger.info("加密操作完成");
		// }

	}

	/**
	 * 
	 * @param sourceFile
	 *            ftp 目录
	 * @param ftpFile
	 *            ftp 文件
	 * @param targetFile
	 *            本地目标记录的文件
	 * @param valiFile
	 *            本地验证文件
	 * @param zipTargetFile
	 *            本地zip存储文件
	 * @throws IOException
	 */
	public synchronized void copyFile(String sourceFile, FTPFile ftpFile,
			File targetFile, File valiFile, File zipTargetFile)
			throws IOException {
		if (ftpFile == null) {
			return;
		}
		String ftpName = ftpFile.getName();
		// 判断文件名后缀 是否存在后缀列表中。如果存在则不进行判断直接下载
		if (inSuffixList(ftpName)) {
			total++;
			FtpDownloadInfo info = new FtpDownloadInfo(sourceFile,
					ftpFile.getName(), targetFile, valiFile, zipTargetFile);

			thread.initData(info);
		}

		if (valiFile == null || !valiFile.exists()
				|| ftpFile.getSize() != valiFile.length()) {
			total++;
			FtpDownloadInfo info = new FtpDownloadInfo(sourceFile,
					ftpFile.getName(), targetFile, valiFile, zipTargetFile);
			// FtpDownloadThread thread = new FtpDownloadThread(info);
			// thread.start();
			thread.initData(info);
		}

	}

	/**
	 * 判断名称后缀是否在后缀列表中
	 * 
	 * @param name
	 * @return
	 */
	private boolean inSuffixList(String name) {
		if (name == null || name.isEmpty()) {
			return false;
		}
		List<String> list = LoadConfig.getInstance().getSuffix();
		for (String suffix : list) {
			if (name.endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param fileName
	 * @param targetDir
	 */
	public void copyDirectiory(String fileName, String targetDir,
			String valiDir, String zipTargetUrl) {
		(new File(targetDir)).mkdirs();// 创建文件夹
		(new File(valiDir)).mkdirs();// 创建文件夹
		(new File(zipTargetUrl)).mkdirs();// 创建文件夹

		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(ftpIP, ftpPort);
			ftp.setControlEncoding("GB2312");
			ftp.login(name, pwd);
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.out.println("没有连接到ftp服务器");
				return;
			}
			ftp.changeWorkingDirectory(fileName);
			ftp.enterLocalPassiveMode();

			FTPFile[] fs = ftp.listFiles();

			for (FTPFile ff : fs) {
				if (!".".equals(ff.getName()) && !"..".equals(ff.getName())) {
					if (ff.isFile()) { // 当保存的是文件时
						File localFile = new File(targetDir + "/"
								+ ff.getName());
						File valiFile = new File(valiDir + "/" + ff.getName());

						File zipTargetFile = new File(zipTargetUrl + "/"
								+ ff.getName());
						copyFile(fileName, ff, localFile, valiFile,
								zipTargetFile);
					}
					if (ff.isDirectory()) { // 当保存的是文件夹时
						// 准备复制的源文件夹
						String dir1 = fileName + "/" + ff.getName();
						// 准备复制的目标文件夹
						String dir2 = targetDir + "/" + ff.getName();
						// 验证文件夹
						String dir3 = valiDir + "/" + ff.getName();
						String zipTargetDir = zipTargetUrl + "/" + ff.getName();
						copyDirectiory(dir1, dir2, dir3, zipTargetDir);
					}
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					logger.info("ftp disconnect error", ioe);
				}
			}
		}
	}

	public static int getFilesTotal() {
		return filesTotal;
	}
}
