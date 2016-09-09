package org;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.timer.CopyTimerTask;
import org.util.DateUtil;
import org.util.EncryptUtil;
import org.util.IOUtil;
import org.util.ZipCompressor;

/**
 * ftp 实现下载线程池
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-24
 */
public class FtpDownloadThread extends Thread {
	private Logger logger = Logger.getLogger(FtpDownloadThread.class);

	private static int filesDownloadCount = 1; // 记录下载完成后的文件数

	private static List<FtpDownloadInfo> pool = new LinkedList<FtpDownloadInfo>();

	/**
	 * 记录所有错误文件，下载完成后重新下载
	 */
	private List<FtpDownloadInfo> errorPool = new LinkedList<FtpDownloadInfo>();

	/**
	 * over txt 已经创建
	 *  0 表示 未启动   1 表示 正在运行  2表示已经结束
	 */
	private static int isOver = 0; 
	
	public FtpDownloadThread() {
	}

	public static void initData(FtpDownloadInfo info) {
		synchronized (pool) {
			pool.add(info);
			pool.notifyAll();
		}
	}

	private static synchronized void incrementFiles() {
		filesDownloadCount++;
	}

	// 启动线程
	public static void start(int num) {
		for (int i = 0; i < num; i++) {
			(new Thread(new FtpDownloadThread(), "ftpDownloadThread" + i))
					.start();
		}
	}

	public void run() {
		logger.info(Thread.currentThread().getName()
				+ "  Thread running..........");
		isOver = 1;
		FtpDownloadInfo info = null;
		while (filesDownloadCount != FtpCopyDirectory.getFilesTotal()) {

			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						logger.info(" wait is " + this.toString());
						pool.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				info = (FtpDownloadInfo) pool.remove(0);
			}

			if (info != null) {
				OutputStream is = null;
				int reply;
				FTPClient ftp = new FTPClient();
				ftp.enterLocalPassiveMode();
				try {
					ftp.connect(info.getFtpIP(), info.getFtpPort());
					ftp.login(info.getName(), info.getPwd());
					reply = ftp.getReplyCode();
					if (!FTPReply.isPositiveCompletion(reply)) {
						ftp.disconnect();
						logger.error("没有连接到ftp服务器");
						return;
					}
					ftp.changeWorkingDirectory(info.getSourceName());
					ftp.setControlEncoding("GBK");
					String fileNmae = info.getFileNmae();
					File targetFile = info.getTargetFile();
					IOUtil.existsToDelete(targetFile);
					
					File valiFile = info.getValiFile();
					IOUtil.existsToDelete(valiFile);
					
					File zipTargetFile = info.getZipTargetFile();
					logger.info(fileNmae + "  to " + zipTargetFile);

					// ftp.enterLocalPassiveMode();// 被动模式
					ftp.setFileType(FTP.BINARY_FILE_TYPE);// 设置为二进制传输
					is = new FileOutputStream(zipTargetFile);
					boolean completed = ftp.retrieveFile(new String(fileNmae
							.getBytes("GBK"), "ISO-8859-1"), is); // true 表示成功完成
					if (completed) {
						if(isOver == 1){
							incrementFiles();
						}
						ZipCompressor zc = new ZipCompressor();
						
						logger.info(zipTargetFile.getAbsoluteFile()
								+ " 开始解密");
						EncryptUtil encryptUtil = new EncryptUtil();
						encryptUtil.decrypt(zipTargetFile, LoadConfig
								.getInstance().getKey());

						logger.info(zipTargetFile.getAbsoluteFile()
								+ " 结束解密");
						zipTargetFile.delete();
						
						String zipUrl = zipTargetFile.getAbsolutePath()
								.substring(0,
										zipTargetFile.getAbsolutePath()
												.length() - 5);
						logger.info(zipUrl
								+ " 开始解压");
						zc.decompress(zipUrl, zipTargetFile.getParent());
						logger.info(zipUrl
								+ " 结束解压");
						
						String fileurl = zipTargetFile.getAbsolutePath()
								.substring(0,
										zipTargetFile.getAbsolutePath()
												.length() - 9);
						
						
						logger.info(fileurl);
						IOUtil.copyFile(new File(fileurl), targetFile, true);
						IOUtil.copyFile(new File(fileurl), valiFile, true);
						
						
					} else {
						errorPool.add(info);
						logger.error(info.getFileNmae() + " error ");
					}

				} catch (SocketException e1) {
					errorPool.add(info);
					logger.info(info.getFileNmae() + " error ", e1);
				} catch (IOException e1) {
					errorPool.add(info);
					logger.info(info.getFileNmae() + " error ", e1);
				} catch (Exception e) {
					logger.info(info.getFileNmae() + " error ", e);
				} finally {
					try {
						is.flush();
						is.close();

					} catch (IOException e) {
						logger.error(e.getMessage(), e);
					} finally {
						is = null;
					}

					if (ftp.isConnected()) {
						try {
							ftp.disconnect();
						} catch (IOException ioe) {
							logger.info("ftp disconnect error", ioe);
						}
					}
				}
			}

			if (isOver == 1) {
				logger.info("一共有文件文件 " + FtpCopyDirectory.getFilesTotal()
						+ " | 当前下载文件有：" + filesDownloadCount);
			}

			if (filesDownloadCount == FtpCopyDirectory.getFilesTotal()
					|| this.filesDownloadCount > FtpCopyDirectory.getFilesTotal()) {
				if(isOver == 1){
					IOUtil.createOverFile(LoadConfig.currentDate);
					IOUtil.createUpdateOverFile(DateUtil.getSystemCurrentDate());
					String targetDir = CopyTimerTask.targetUrl + File.separator
							+ DateUtil.getSystemCurrentDate();
					IOUtil.copyDirectiory(CopyTimerTask.validationUrl, targetDir);
//					IOUtil.createOverFile(LoadConfig.currentDate);
					
					isOver = 2;
					logger.info("本次下载结束");
					
				}
			}
		}
	}
}
