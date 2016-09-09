package org;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.timer.CopyTimerTask;
import org.util.DateUtil;
import org.util.EncryptUtil;
import org.util.IOUtil;
import org.util.ZipCompressor;

/**
 * 复制文件夹
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-16
 */
public class CopyDirectory {

	private Logger logger = Logger.getLogger(CopyDirectory.class);

	public void start() throws IOException {
		logger.info("copy directory start");

		String sourceUrl = CopyTimerTask.sourceUrl; // 源目录
		String validationUrl = CopyTimerTask.validationUrl; // 验证目录
		String targetUrl = CopyTimerTask.targetUrl; // 目标目录
		String zipUrl = CopyTimerTask.zipTargetUrl; // zip 压缩文件 存放目录

		targetUrl = targetUrl + File.separator + LoadConfig.currentDate;// 目标目录
																		// 日期记录
		zipUrl = zipUrl + File.separator + LoadConfig.currentDate;// 压缩目录 时期记录

		(new File(targetUrl)).mkdirs();
		(new File(zipUrl)).mkdirs();

		// 获取源文件夹当前下的文件或目录
		// File[] file = (new File(sourceUrl)).listFiles();
		String[] file = (new File(sourceUrl)).list();
		// File[] valiFiles = (new File(validationUrl)).listFiles();
		String[] valiFiles = (new File(validationUrl)).list();

		if (file == null) {
			logger.info("msg = " + sourceUrl + "  is null");
			return;
		}
		File tempFile = null;
		File tempValiFile = null;
		for (int i = 0; i < file.length; i++) {
			String name = file[i];
			tempFile = (new File(sourceUrl + File.separator + name));
			if (tempFile.isFile()) {
				// 特殊后缀名时，直接下载
				if (inSuffixList(name)) {
					copyFileToAllDirectiory(tempFile, validationUrl, targetUrl,
							zipUrl);
				} else {
					if(valiFiles == null){
						copyFileToAllDirectiory(tempFile, validationUrl,
								targetUrl, zipUrl);
						continue;
					}
					// 如果文件在验证文件存在，比较大小，如果不符合则下载
					boolean isExists = false;
					for (int j = 0; j < valiFiles.length; j++) {
						if (name.equals(valiFiles[j])) {
							isExists = true;
							tempValiFile = new File(validationUrl
									+ File.separator + name);
							if (tempFile.length() != tempValiFile.length()) {
								copyFileToAllDirectiory(tempFile,
										validationUrl, targetUrl, zipUrl);
							}
						}
					}
					// 如果不存在验证目录中，则下载
					if (isExists == false) {
						copyFileToAllDirectiory(tempFile, validationUrl,
								targetUrl, zipUrl);
					}
				}
			}
			if (tempFile.isDirectory()) {
				// 复制目录
				String sourceDir = sourceUrl + File.separator + name;
				String validationDir = validationUrl + File.separator + name;
				String targetDir = targetUrl + File.separator + name;
				String zipDir = zipUrl + File.separator + name;
				copyDirectioryToAllDirectiory(sourceDir, validationDir,
						targetDir, zipDir);
			}
		}
		logger.info("copy directory end");
		logger.info("开始创建 over.txt 文件");

		IOUtil.createOverFile(LoadConfig.currentDate);
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
	 * 复制文件到所有需要目录中
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param valiFile
	 *            验证目录
	 * @param targetUrl
	 *            记录文件目录
	 * @param zipUrl
	 *            zip 下载目录
	 */
	public void copyFileToAllDirectiory(File sourceFile, String valiUrl,
			String targetUrl, String zipUrl) {
		String fileName = sourceFile.getName();
		// 验证文件
		File valiFile = new File(valiUrl + File.separator + fileName);
		IOUtil.existsToDelete(valiFile);

		// 记录目录文件
		File targetFile = new File(targetUrl + File.separator + fileName);
		IOUtil.existsToDelete(targetFile);

		// zip 文件
		File zipFile = new File(zipUrl + File.separator + fileName + ".zip");
		IOUtil.existsToDelete(zipFile);

		// 复制验证文件
		copyFile(sourceFile, valiFile);
		// 复制记录文件
		copyFile(sourceFile, targetFile);

		// 开始压缩
		logger.info(zipFile.getAbsoluteFile() + "  开始压缩");
		ZipCompressor zc = new ZipCompressor();
		zc.compress(zipFile, targetFile);
		logger.info(zipFile.getAbsoluteFile() + "  压缩完成");

		logger.info(zipFile.getAbsoluteFile() + "  执行加密操作");
		EncryptUtil encryptUtil = new EncryptUtil();
		boolean completedEncr = encryptUtil.encrypt(zipFile, LoadConfig
				.getInstance().getKey());
		logger.info(zipFile.getAbsoluteFile() + "  加密操作完成");
		if (completedEncr == true) {
			zipFile.delete();
			logger.info(zipFile.getAbsoluteFile() + "删除");
		}
	}

	// 复制文件
	public void copyFile(File sourceFile, File targetFile) {
		logger.info(sourceFile.getAbsoluteFile() + "  to "
				+ targetFile.getAbsolutePath());
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = null;
		BufferedInputStream inBuff = null;
		FileOutputStream output = null;
		BufferedOutputStream outBuff = null;
		try {
			input = new FileInputStream(sourceFile);

			inBuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲
			output = new FileOutputStream(targetFile);
			outBuff = new BufferedOutputStream(output);

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();

		} catch (FileNotFoundException e) {
			logger.error("文件没有找到", e);
		} catch (IOException e) {
			logger.error("复制文件 io 错误", e);
		} finally {
			// 关闭流

			try {
				if (inBuff != null)
					inBuff.close();
				if (outBuff != null)
					outBuff.close();
				if (output != null)
					output.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				inBuff = null;
				outBuff = null;
				output = null;
				input = null;
			}

		}
	}

	/**
	 * 复制目录到所有需要目录中
	 * 
	 * @param sourceDir
	 *            源目录
	 * @param valiDir
	 *            验证目录 即全部目录
	 * @param targerDir
	 *            日志记录目录 即增量目录
	 * @param zipDir
	 *            压缩记录目录
	 */
	public void copyDirectioryToAllDirectiory(String sourceDir, String valiDir,
			String targetDir, String zipDir) {
		File valiFile = new File(valiDir);
		IOUtil.existsToMkdirs(valiFile);

		File targetFile = new File(targetDir);
		IOUtil.existsToMkdirs(targetFile);

		File zipFile = new File(zipDir);
		IOUtil.existsToMkdirs(zipFile);
		String[] file = (new File(sourceDir)).list();
		
		String[] valiFiles = valiFile.list();
		
		File tempFile = null;
		File tempValiFile = null;
		for (int i = 0; i < file.length; i++) {
			String name = file[i];
			tempFile = new File(sourceDir + File.separator + name);
			if (tempFile.isFile()) {
				if (inSuffixList(name)) {
					copyFileToAllDirectiory(tempFile, valiDir, targetDir,
							zipDir);
				} else {
					
					if(valiFiles == null){
						copyFileToAllDirectiory(tempFile, valiDir, targetDir,
								zipDir);
						continue;
					}
					
					// 如果文件在验证文件存在，比较大小，如果不符合则下载
					boolean isExists = false;
					for (int j = 0; j < valiFiles.length; j++) {
						if (name.equals(valiFiles[j])) {
							isExists = true;
							tempValiFile = new File(valiDir + File.separator
									+ name);
							if (tempFile.length() != tempValiFile.length()) {
								copyFileToAllDirectiory(tempFile, valiDir,
										targetDir, zipDir);
							}
						}
					}
					// 如果不存在验证目录中，则下载
					if (isExists == false) {
						copyFileToAllDirectiory(tempFile, valiDir, targetDir,
								zipDir);
					}
				}
			}
			if (tempFile.isDirectory()) {
				// 复制目录
				String sourceUrl = sourceDir + File.separator + name;
				String valiUrl = valiDir + File.separator + name;
				String targetUrl = targetDir + File.separator + name;
				String zipUrl = zipDir + File.separator + name;
				copyDirectioryToAllDirectiory(sourceUrl, valiUrl, targetUrl,
						zipUrl);
			}
		}
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
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());
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
