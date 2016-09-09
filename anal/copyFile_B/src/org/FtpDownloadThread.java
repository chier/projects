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

	private static FtpDownloadThread ftpDownloadThread;
	/**
	 * 判断此线程是
	 */
	private static boolean isAlive = false;
	/**
	 * over txt 已经创建
	 *  0 表示 未启动   1 表示 正在运行  2表示已经结束
	 */
	private static int isOver = 0; 

	private static List<FtpDownloadInfo> pool = new LinkedList<FtpDownloadInfo>();

	/**
	 * 记录所有错误文件，下载完成后重新下载
	 */
	private List<FtpDownloadInfo> errorPool = new LinkedList<FtpDownloadInfo>();

	private FtpDownloadThread() {
	}

	public synchronized static FtpDownloadThread getInstance() {
		if (ftpDownloadThread == null) {
			ftpDownloadThread = new FtpDownloadThread();
		}
		return ftpDownloadThread;
	}

	public static void initData(FtpDownloadInfo info) {
		synchronized (pool) {
			pool.add(info);
			pool.notifyAll();
		}
	}

	/**
	 * 判断线程是否存于活跃 状态
	 * 
	 * @return
	 */
	public static boolean isDownAlive() {
		return isAlive && isOver == 2;
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
		isAlive = true;
		isOver = 1;
		logger.info(Thread.currentThread().getName()
				+ "  Thread running..........");

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
					isAlive = false;
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
					File valiFile = info.getValiFile();
					File zipTargetFile = info.getZipTargetFile();
					logger.info(fileNmae + "  to " + targetFile);

					// 删除本地记录的文件
					if (targetFile != null && targetFile.exists()) {
						targetFile.delete();
						targetFile.createNewFile();
					}

					// ftp.enterLocalPassiveMode();// 被动模式
					ftp.setFileType(FTP.BINARY_FILE_TYPE);// 设置为二进制传输
					is = new FileOutputStream(targetFile);
					boolean completed = ftp.retrieveFile(
							new String(fileNmae.getBytes("GBK"), "ISO-8859-1"),
							is); // true 表示成功完成
					if (completed) {
						
						if(isOver == 1){
							incrementFiles();
						}
						
						ZipCompressor zc = new ZipCompressor();
						IOUtil.copyFile(targetFile, valiFile, true);

						String zipStr = zipTargetFile.getParent()
								+ File.separator + zipTargetFile.getName()
								+ ".zip";
						zc.compress(zipStr, targetFile);
						File zipFile = new File(zipStr);

						logger.info(zipTargetFile.getAbsoluteFile() + "压缩完成");

						logger.info(zipFile.getAbsoluteFile() + "  执行加密操作");
						EncryptUtil encryptUtil = new EncryptUtil();
						boolean completedEncr = encryptUtil.encrypt(zipFile,
								LoadConfig.getInstance().getKey());
						logger.info(zipFile.getAbsoluteFile() + "  加密操作完成");
						if (completedEncr == true) {
							zipFile.delete();
							logger.info(zipTargetFile.getAbsoluteFile() + "删除");
						}
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

			if (filesDownloadCount == FtpCopyDirectory.getFilesTotal() || filesDownloadCount > FtpCopyDirectory.getFilesTotal() ) {
				if(isOver == 1){
					
					IOUtil.createOverFile(LoadConfig.currentDate);
					filesDownloadCount = 1;
					isOver = 2;
					logger.info("本次下载结束");
				}
				
			}
		}
	}
}
