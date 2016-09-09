package org;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.timer.CopyTimerTask;
import org.util.DateUtil;
import org.util.IOUtil;

/**
 * ftp 下载 类
 * 
 * @author Administrator
 * 
 */
public class FtpCopyDirectory {

	private Logger logger = Logger.getLogger(FtpCopyDirectory.class);

	private String ftpIP = LoadConfig.getInstance().getFtpIp();

	private Integer ftpPort = LoadConfig.getInstance().getFtpPort();

	private String name = LoadConfig.getInstance().getFtpName();

	private String pwd = LoadConfig.getInstance().getFtpPwd();

	private FtpDownloadThread thread = new FtpDownloadThread();

	private static String overFilePath = null;

	private static int filesTotal = -1;

	private int total = 0;

	private int errorCount = 0; // 错误记录数量

	public static void main(String[] args) {
		new FtpCopyDirectory().start();
	}

	/**
	 * 获得 over.txt 文件，并获得它更新的内容
	 * 
	 * @param ftp
	 *            ftp 客户端
	 * @param ftpFiles
	 *            目录下所以文件
	 * @return 如果返回空 则表示不存在 over.txt文件 返回其它时间值字符串 表示 ftp已经更新的最新的日期
	 */
	private String getOverTxtUpdateDate(FTPClient ftpClient, FTPFile[] ftpFiles) {
		for (FTPFile file : ftpFiles) {
			if ("over.txt".equals(file.getName())) {
				OutputStream is = null;
				String zipTarget = CopyTimerTask.zipTargetUrl;
				File targetFile = new File(zipTarget + File.separator
						+ "over.txt");
				boolean completed = false;
				try {
					IOUtil.existsToDelete(targetFile);
					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);// 设置为二进制传输
					is = new FileOutputStream(targetFile);
					completed = ftpClient
							.retrieveFile(new String(
									"over.txt".getBytes("GBK"), "ISO-8859-1"),
									is);

				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				} finally {
					if (is != null) {
						try {
							is.flush();
							is.close();
						} catch (IOException e) {
							logger.error(e.getMessage(), e);
						} finally {
							is = null;
						}
					}
				}

				if (completed) { // ftpClient 下载完成
					String body = IOUtil.readerFile(targetFile);
					return body;
				}
			}
		}
		return null;
	}

	/**
	 * 对 config.currentdate 进行赋值
	 * 
	 * @param currentStr
	 */
	public boolean setConfigCurrentDate(String currentStr) {
		boolean brak = false;
		if (currentStr == null) {
			errorCount++;
			logger.error(" 严重错误 ! ftp 上没有找到 over.txt 文件. 5秒后重试");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}

			if (errorCount < 4) {
				logger.error(" 严重错误 ! ftp 上没有找到 over.txt 文件.");
				start();
			}
		} else {
			brak = true;
			LoadConfig.currentDate = currentStr;
		}
		return brak;
	}

	/**
	 * 查找本地与服务器有区别的目录
	 * 
	 * @param zipTargetFile
	 *            本地目录
	 * @param ftpFiles
	 *            服务器文件集合
	 * @return
	 */
	public List<FTPFile> findOtherFile(File zipTargetFile, FTPFile[] ftpFiles) {

		List<FTPFile> otherList = new ArrayList<FTPFile>();
		String currentStr = LoadConfig.currentDate;
		// 获取本地所有日期目录
		Map zipMap = new HashMap();
		String[] zipName = zipTargetFile.list();
		for (String s : zipName) {
			zipMap.put(s, s);
		}

		String tempFtpName = null;
		for (FTPFile f : ftpFiles) {
			tempFtpName = f.getName();
			if (!".".equals(tempFtpName) && !"..".equals(tempFtpName)) {
				if (zipMap.get(tempFtpName) == null
						|| currentStr.equals(tempFtpName)) {
					if (DateUtil.compareTwoDate(currentStr, tempFtpName,
							"yyyy-MM-dd") >= 0) {
						otherList.add(f);
					}
				}
			}
		}
		return otherList;
	}

	public void start() {
		thread.start(LoadConfig.getInstance().getFtpThreadNum());
		FTPClient ftpClient = new FTPClient();
		try {

			int reply;
			logger.info("ftpIP=" + ftpIP + " | ftpPort" + ftpPort
					+ " | name = " + name + " | pwd = " + pwd);

			ftpClient.connect(ftpIP, ftpPort);
			ftpClient.login(name, pwd);
			ftpClient.setControlEncoding("GB2312");
			reply = ftpClient.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.info("没有连接到ftp服务器");
				return;
			}

			String parth = CopyTimerTask.sourceUrl; // ftp 源目录
			String zipTargetUrl = CopyTimerTask.zipTargetUrl; // zip 备份存储目录
			File zipTargetFile = new File(zipTargetUrl);
			IOUtil.existsToMkdirs(zipTargetFile);

			String valiUrl = CopyTimerTask.validationUrl; // 验证目录，即全部目录
			IOUtil.existsToMkdirs(new File(valiUrl));

			// zip 解压后 更新目录
			String zipUpdateUrl = LoadConfig.getInstance().getZipUpdateUrl();
			zipUpdateUrl = zipUpdateUrl + File.separator
					+ DateUtil.getSystemCurrentDate();
			IOUtil.existsToMkdirs(new File(zipUpdateUrl));

			// localPath = localPath + "/" + DateUtil.getSystemCurrentDate();
			// if (LoadConfig.getInstance().getZipIsCouBak().intValue() == 1) {
			// 自动备份 即在目录下创建日期格式的文件
			// zipTargetUrl = zipTargetUrl + "/"
			// + DateUtil.getSystemCurrentDate();

			// }

			ftpClient.changeWorkingDirectory(parth);
			// ftp.enterLocalPassiveMode();
			FTPFile[] ftpFiles = ftpClient.listFiles();
			String currentStr = this.getOverTxtUpdateDate(ftpClient, ftpFiles);
			boolean b = setConfigCurrentDate(currentStr);
			if (b == false) {
				return;
			}
			List<FTPFile> otherList = findOtherFile(zipTargetFile, ftpFiles);

			// String targetUrl = CopyTimerTask.targetUrl; // 备份目录
			// targetUrl = targetUrl + File.separator + currentStr;
			// IOUtil.existsToMkdirs(new File(targetUrl));

			for (FTPFile f : otherList) {
				String name = f.getName();
				if (f.isFile()) { // 当保存的是文件时
					// 即根目录 只有一个over.txt 文件，其它文件不理彩
				}

				if (f.isDirectory()) { // 当保存的是目录时
					String sourceDir = parth + "/" + name;
					// zip 复制目录
					String zipDir = zipTargetUrl + File.separator + name;

					String zipUpdateDir = zipUpdateUrl + File.separator + name;
					// 验证目录
					String valiDir = valiUrl;

					copyDirectiory(sourceDir, valiDir, zipDir, zipUpdateDir);

				}

			}

			ftpClient.logout();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					logger.error(ioe.getMessage(), ioe);
				} finally {
					ftpClient = null;
				}
			}
		}

		// filesTotal = total;
	}

	public void createOverFile() {
		File overFile = new File(overFilePath);
		try {
			overFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载 ftp 文件
	 * 
	 * @param sourceFile
	 *            ftp 原文件地址
	 * @param ftpFile
	 *            ftp 文件
	 * @param zipUpdateFile
	 *            每天备份文件
	 * @param valiFile
	 *            全部文件
	 * @param zipTargetFile
	 *            zip 备份文件
	 * @throws IOException
	 */
	public synchronized void copyFile(String sourceFile, FTPFile ftpFile,
			File zipUpdateFile, File valiFile, File zipTargetFile)
			throws IOException {

		if (valiFile == null || !valiFile.exists()
				|| ftpFile.getSize() != valiFile.length()) {
			total++;
			FtpDownloadInfo info = new FtpDownloadInfo(sourceFile,
					ftpFile.getName(), zipUpdateFile, valiFile, zipTargetFile);
			thread.initData(info);
		}

	}

	/**
	 * 复制文件夹
	 * 
	 * @param fileName
	 *            ftp 目录名称
	 * @param valiDir
	 *            全部 即验证目录名称
	 * @param zipTargetUrl
	 *            zip 备份 目录名称
	 * @param zipUpdateDir
	 *            zip 解压后 备份目录
	 */
	public void copyDirectiory(String fileName, String valiDir,
			String zipTargetUrl, String zipUpdateDir) {
		IOUtil.existsToMkdirs(new File(valiDir));
		IOUtil.existsToMkdirs(new File(zipTargetUrl));
		IOUtil.existsToMkdirs(new File(zipUpdateDir));

		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(ftpIP, ftpPort);

			ftp.login(name, pwd);
			ftp.setControlEncoding("GB2312");
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
				String name = ff.getName();
				if (!".".equals(ff.getName()) && !"..".equals(ff.getName())
						&& !"atm".equals(ff.getName())) {
					if (ff.isFile()) { // 当保存的是文件时
						String fname = name.substring(0,
								ff.getName().length() - 9);
						File zipUpdateFile = new File(zipUpdateDir
								+ File.separator + fname);
						File valiFile = new File(valiDir + File.separator
								+ fname);

						File zipTargetFile = new File(zipTargetUrl
								+ File.separator + ff.getName());

						copyFile(fileName, ff, zipUpdateFile, valiFile,
								zipTargetFile);
					}
					if (ff.isDirectory()) { // 当保存的是文件夹时
						// 准备复制的源文件夹
						String sourceDir2 = fileName + "/"
								+ ff.getName();

						String valiDir2 = valiDir + File.separator
								+ ff.getName();

						String zipTargetDir2 = zipTargetUrl + File.separator
								+ ff.getName();

						String zipUpdateDir2 = zipUpdateDir + File.separator
								+ ff.getName();

						copyDirectiory(sourceDir2, valiDir2, zipTargetDir2,
								zipUpdateDir2);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
