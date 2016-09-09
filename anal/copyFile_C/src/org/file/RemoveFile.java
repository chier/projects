package org.file;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.LoadConfig;
import org.apache.log4j.Logger;
import org.util.DateUtil;

/**
 * 删除过其备份的文件
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-17
 */
public class RemoveFile {
	static Logger logger = Logger.getLogger(RemoveFile.class);

	public void start() throws IOException {
		// String url2 = CopyTimerTask.url2; // 目标目录
		String url2 = LoadConfig.getInstance().getTargetUrl(); // 目标目录
		// Integer demMonth = LoadConfig.getInstance().getDelMonth();
		Integer delDays = LoadConfig.getInstance().getDelDays();
		File[] files = (new File(url2)).listFiles();
		if (files == null) {
			logger.info("url = " + url2 + " 下面没有目录");
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = DateUtil.getSystemCurrentDateTime();
		logger.info("系统当前时间是： " + nowTime);

		if (delDays < 0) {
			delDays = 90;
		}

		String tempNmae;
		for (File file : files) {
			tempNmae = file.getName();
			logger.info(file.getPath());
			try {
				Date tempDate = df.parse(tempNmae + " 00:00:00");
				long c = System.currentTimeMillis() - tempDate.getTime();
				long d = c / (1000 * 60 * 60 * 24);
				if (d > delDays) {
					deleteFile(file);
					logger.info("delete  " + tempNmae);
				}
			} catch (ParseException e) {
				continue;
			}

		}
	}

	/**
	 * 删除所有文件夹
	 * 
	 * @param file
	 */
	public boolean deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				deleteFile(file2);
			}
		}
		return file.delete();
	}
}
