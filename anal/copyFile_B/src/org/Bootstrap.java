package org;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.file.RemoveFile;
import org.timer.CopyTimerTask;
import org.util.DateUtil;

/**
 * 启动类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-17
 */
public class Bootstrap {

	private static Logger logger = Logger.getLogger(Bootstrap.class);

	private Integer copyType = null;

	private CopyDirectory copyDirectory;

	private FtpCopyDirectory ftpCopyDirectory;

	private RemoveFile removeFile;

	public static Bootstrap daemon = null;

	/**
	 * 初始化数据类
	 * 
	 */
	public void init() {
		setCatalinaHome();

		copyDirectory = new CopyDirectory();

		ftpCopyDirectory = new FtpCopyDirectory();

		removeFile = new RemoveFile();

		copyType = LoadConfig.getInstance().getCopyType();

	}

	/**
	 * 开始
	 * 
	 * @throws IOException
	 * 
	 */
	public void start() throws IOException {

		removeFile.start();

		if (1 == copyType.intValue()) {
			copyDirectory.start();
		}
		if (2 == copyType.intValue()) {
			ftpCopyDirectory.start();
		}
	}

	public static void setCatalinaHome() {
		logger.info("start " + System.getProperty("MYCATALINA_HOME"));
		if (System.getProperty("MYCATALINA_HOME") != null)
			return;

		logger.info("System.getProperty('user.dir')"
				+ System.getProperty("user.dir"));
		File bootstrapJar = new File(System.getProperty("user.dir"), "copy.jar");
		logger.info(System.getProperty("MYCATALINA_HOME"));
		if (bootstrapJar.exists()) {
			try {
				System.setProperty("MYCATALINA_HOME",
						(new File(System.getProperty("user.dir"), ".."))
								.getCanonicalPath());

				logger.info("getCanonicalPath  "
						+ System.getProperty("MYCATALINA_HOME"));

			} catch (Exception e) {
				System.setProperty("MYCATALINA_HOME",
						System.getProperty("user.dir"));
			}
		} else {
			System.setProperty("MYCATALINA_HOME",
					System.getProperty("user.dir"));

		}
		logger.info("end " + System.getProperty("MYCATALINA_HOME"));
	}

	public static void main(String[] args) {
		if (daemon == null) {
			daemon = new Bootstrap();
			try {
				daemon.init();
			} catch (Throwable t) {
				t.printStackTrace();
				return;
			}
		}
		String executeTime = LoadConfig.getInstance().getExecuteTime();
		long period = LoadConfig.getInstance().getTimeperiod();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timer timer = new Timer("copyFile");
		TimerTask task = new CopyTimerTask();
		String firstTimeStr = null;
		String startTime = DateUtil.getSystemCurrentDateTime();// 系统当前时期
		String endTime = DateUtil.getSystemCurrentDate() + " " + executeTime;
		int c;
		try {
			c = DateUtil.compareTwoDate(startTime, endTime);

			if (c > 0) {
				firstTimeStr = DateUtil.getDayAfterToday() + " " + executeTime;
			} else {
				firstTimeStr = endTime;
			}

			logger.info("初次执行任务时间是：  " + firstTimeStr);
			Date firstTime = df.parse(firstTimeStr);
			firstTime = new Date();
			 timer.schedule(task, firstTime, period);
//			timer.schedule(task, new Date(), period);
		} catch (ParseException e) {
			logger.error("时间格式错误", e);
		}
	}

}
