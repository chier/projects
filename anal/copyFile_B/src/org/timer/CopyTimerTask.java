package org.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.Bootstrap;
import org.FtpDownloadThread;
import org.LoadConfig;
import org.apache.log4j.Logger;
import org.util.DateUtil;

/**
 * 复制时间类
 * 
 * @author zhangzhanliang
 * 
 */
public class CopyTimerTask extends TimerTask {

	private Logger logger = Logger.getLogger(CopyTimerTask.class);

	/**
	 * ftp 目录
	 */
	public final static String sourceUrl = LoadConfig.getInstance()
			.getSourceUrl();

	/**
	 * 目标文件夹
	 */
	public final static String targetUrl = LoadConfig.getInstance()
			.getTargetUrl();

	/**
	 * 验证目录
	 */
	public final static String validationUrl = LoadConfig.getInstance()
			.getValidationUrl();

	/**
	 * 存储目录
	 */
	public final static String zipTargetUrl = LoadConfig.getInstance()
			.getZipTargetUrl();

	// ftp IP
	public final static String ftpIp = LoadConfig.getInstance().getFtpIp();

	/**
	 * 判断线程是否存于活跃状态
	 */
	private void isThreadAlive() {
		FtpDownloadThread thread = FtpDownloadThread.getInstance();
		// 判断 线程是否处于活跃状态
		while (thread.isAlive()) {
			logger.info("线程处于活跃状态");
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public void run() {
		
		LoadConfig.currentDate = DateUtil.getSystemCurrentDate();
		
		logger.info("判断程序是否处于下载状态");
		isThreadAlive();
		logger.info("程序处于非下载状态     任务开始启动");

		Date executeTime = new Date(this.scheduledExecutionTime());
		logger.info("本次任务执行时间是："
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(executeTime));

		if (Bootstrap.daemon == null) {
			Bootstrap.daemon = new Bootstrap();
			try {
				Bootstrap.daemon.init();
			} catch (Throwable t) {
				logger.error("Bootstrap 实始化异常", t);
				return;
			}
		}

		try {
			Bootstrap.daemon.start();
		} catch (Throwable t) {
			logger.error("Bootstarap 开始异常", t);
		}
	}
}
