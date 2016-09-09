package org.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.timer.CopyTimerTask;

/**
 * io 操作类
 * 
 * @author Administrator
 * 
 */
public class IOUtil {

	private static Logger logger = Logger.getLogger(IOUtil.class);

	/**
	 * 复制单个文件
	 * 
	 * @param srcFileName
	 *            待复制的文件名
	 * @param descFileName
	 *            目标文件名
	 * @param overlay
	 *            如果目标文件存在，是否覆盖
	 * @return 如果复制成功返回true，否则返回false
	 */
	public static boolean copyFile(File srcFile, File destFile, boolean overlay) {

		// 判断源文件是否存在
		if (srcFile == null || !srcFile.exists()) {
			logger.error("源文件：" + srcFile.getAbsolutePath() + "不存在！");
			return false;
		} else if (!srcFile.isFile()) {
			logger.error("复制文件失败，源文件：" + srcFile.getAbsolutePath() + "不是一个文件！");
			return false;
		}

		// 判断目标文件是否存在
		if (destFile != null && destFile.exists()) {
			// 如果目标文件存在并允许覆盖
			if (overlay) {
				// 删除已经存在的目标文件，无论目标文件是目录还是单个文件
				destFile.delete();
			}
		} else {
			// 如果目标文件所在目录不存在，则创建目录
			if (!destFile.getParentFile().exists()) {
				// 目标文件所在目录不存在
				if (!destFile.getParentFile().mkdirs()) {
					// 复制文件失败：创建目标文件所在目录失败
					return false;
				}
			}
		}

		// 复制文件
		int byteread = 0; // 读取的字节数
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);
			byte[] buffer = new byte[1024];

			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			return true;
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				if (out != null)
					out.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 如果文件 存在，则进行删除
	 * 
	 * @param file
	 * @return
	 */
	public static boolean existsToDelete(File file) {
		try {
			if (file != null && file.exists()) {
				file.delete();
			}
			return true;
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
		return false;
	}

	/**
	 * 如果文件不存在，则进行创建
	 * 
	 * @param file
	 * @return
	 */
	public static boolean existsToMkdirs(File file) {
		try {
			if (file == null || !file.exists()) {
				file.mkdirs();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param updateDate
	 */
	public static void createOverFile(String updateDate) {
		String overFilePath = CopyTimerTask.zipTargetUrl + File.separator
				+ "over.txt";
		File overFile = new File(overFilePath);

		try {
			if (overFile != null && overFile.exists()) {
				overFile.delete();
			}

			overFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileWriter writer = null;
		BufferedWriter bwriter = null;
		try {
			writer = new FileWriter(overFile);
			bwriter = new BufferedWriter(writer);

			bwriter.write(updateDate);

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (bwriter != null) {
				try {
					bwriter.flush();
					bwriter.close();
					writer.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				} finally {
					bwriter = null;
					writer = null;
				}
			}
		}

	}

	/**
	 * 读取文件内容
	 * 
	 * @param url
	 * @return
	 */
	public static String readerFile(String url) {
		StringBuilder su = new StringBuilder();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(url);
			int size = fis.available();// 字节数
			byte[] b = new byte[size];
			fis.read(b); // 读取
			for (byte bo : b) {
				su.append((char) bo);// 获取信息
			}
			return su.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					fis = null;
				}

			}
		}

		return null;
	}
}
