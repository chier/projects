package org.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.LoadConfig;
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
	 * 读取文件内容
	 * 
	 * @param url
	 * @return
	 */
	public static String readerFile(File file) {
		StringBuilder su = new StringBuilder();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
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

	/**
	 * 
	 * @param updateDate
	 */
	public static void createOverFile(String updateDate) {
		String overFilePath = CopyTimerTask.validationUrl + File.separator
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
	 * 
	 * @param updateDate
	 */
	public static void createUpdateOverFile(String updateDate) {
		String overFilePath = 	LoadConfig.getInstance().getZipUpdateUrl() + File.separator
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
	 * 下载完成后，进行全部备份
	 */
	public static void copyDirectiory(String sourceDir, String targetDir) {
	
		existsToMkdirs(new File(targetDir));
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				// 源文件
				File sourceFile = file[i];
				// 目标文件
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ file[i].getName());
//				copyFile(sourceFile, targetFile);
				IOUtil.copyFile(sourceFile, targetFile);
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

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) {
		logger.info(sourceFile.getAbsoluteFile() + "  to "
				+ targetFile.getAbsolutePath());
		IOUtil.existsToDelete(targetFile);
		if(targetFile == null || !targetFile.exists()){
			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				inBuff = null;
				outBuff = null;
				output = null;
				input = null;
			}

		}
	}
}
