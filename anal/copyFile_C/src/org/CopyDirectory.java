package org;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.timer.CopyTimerTask;
import org.util.DateUtil;

/**
 * 复制文件夹
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-16
 */
public class CopyDirectory {

	private Logger logger = Logger.getLogger(CopyDirectory.class);

	public void start() throws IOException {
		String url1 = CopyTimerTask.sourceUrl; // 源目录
		String url2 = CopyTimerTask.targetUrl; // 目标目录
		Date startDate = new Date();
		// 创建目标文件夹
		(new File(url2)).mkdirs();
		url2 = url2 + "/" + DateUtil.getSystemCurrentDate();
		(new File(url2)).mkdirs();
		logger.info("copy " + url1 + " start in  "
				+ DateUtil.DateToString(startDate));
		logger.info("copy " + url2 + " start in  "
				+ DateUtil.DateToString(startDate));
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(url1)).listFiles();
		if (file == null) {
			logger.info("msg = " + url1 + "  is null");
			return;
		}
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 复制文件
				copyFile(file[i], new File(url2 + File.separator
						+ file[i].getName()));
			}
			if (file[i].isDirectory()) {
				// 复制目录
				String sourceDir = url1 + File.separator + file[i].getName();
				String targetDir = url2 + File.separator + file[i].getName();
				copyDirectiory(sourceDir, targetDir);
			}
		}

		Date endDate = new Date();
		logger.info("copy " + url2 + " end in  "
				+ DateUtil.DateToString(endDate));

		logger.info("一共花费了 " + (endDate.getTime() - startDate.getTime()) / 1000
				+ " 秒");
		File overFile = new File(url2 + File.separator + "over.txt");
		overFile.createNewFile();

	}

	// 复制文件
	public void copyFile(File sourceFile, File targetFile) throws IOException {
		logger.info(sourceFile.getCanonicalPath() + "  to " + targetFile);
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}

	// 复制文件夹
	public void copyDirectiory(String sourceDir, String targetDir)
			throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(new File(targetDir)
						.getAbsolutePath()
						+ File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + file[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}
}
