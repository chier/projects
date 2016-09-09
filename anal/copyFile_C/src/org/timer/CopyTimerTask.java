package org.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

import org.Bootstrap;
import org.LoadConfig;
import org.apache.log4j.Logger;

public class CopyTimerTask extends TimerTask {

	Logger logger = Logger.getLogger(CopyTimerTask.class);

	/**
	 * ftp  源文件夹
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
	 * 	zip存储目录
	 */
	public final static String zipTargetUrl = LoadConfig.getInstance()
			.getZipTargetUrl();

	// ftp IP
	public final static String ftpIp = LoadConfig.getInstance().getFtpIp();

	@Override
	public void run() {
		Date executeTime = new Date(this.scheduledExecutionTime());
		logger.info("本次任务执行时间是："
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(executeTime));

		if (Bootstrap.daemon == null) {
			Bootstrap.daemon = new Bootstrap();
			try {
				Bootstrap.daemon.init();
			} catch (Throwable t) {
				t.printStackTrace();
				return;
			}
		}

		try {
			Bootstrap.daemon.start();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
