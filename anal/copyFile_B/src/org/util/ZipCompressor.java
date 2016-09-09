package org.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 压缩工具类
 * 
 * @author zhangzhanliang
 * 
 */
public class ZipCompressor {

	private static Logger logger = Logger.getLogger(ZipCompressor.class);

	static final int BUFFER = 8192;

	// private File zipFile;

	public ZipCompressor() {

	}

	public void compress(File zipFile, File file) {
		if (file == null || !file.exists())
			throw new RuntimeException(file.getAbsolutePath() + "不存在！");
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,
					new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			out.setEncoding("gb2312");
			String basedir = "";
			compress(file, out, basedir);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param zipName
	 *            压缩后的文件名及路径
	 * @param srcPathName
	 *            需要压缩 的文件夹
	 */
	public void compress(String zipName, File file) {
		File zipFile = new File(zipName);

		compress(zipFile, file);
	}

	/**
	 * 
	 * @param zipName
	 *            压缩后的文件名及路径
	 * @param srcPathName
	 *            需要压缩 的文件夹
	 */
	public void compress(String zipName, String srcPathName) {
		File file = new File(srcPathName);
		compress(zipName, file);
	}

	private void compress(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			// logger.info("压缩1：" + basedir + file.getName());
			this.compressDirectory(file, out, basedir);
		} else {
			// logger.info("压缩2：" + basedir + file.getName());
			this.compressFile(file, out, basedir);
		}
	}

	/** 压缩一个目录 */
	private void compressDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists())
			return;

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			compress(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/** 压缩一个文件 */
	private void compressFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER];
			while ((count = bis.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}

	/**
	 * 
	 * @param zipName
	 *            需要解压的文件
	 * @param zipPath
	 *            解压存储路径
	 * @throws Exception
	 */
	public static void decompress(String zipName, String zipPath)
			throws Exception {
		// logger.info("显示吗");
		FileOutputStream fos;
		InputStream is;

		String path1 = "";
		String tempPath = "";
		ZipEntry zipEntry = null;
		if (!new File(zipPath).exists()) {
			new File(zipPath).mkdir();
		}
		ZipFile zipFile = new ZipFile(zipName);
		File temp = new File(zipPath);
		String strPath = temp.getAbsolutePath();
		Enumeration<Object> enu = zipFile.getEntries();
		while (enu.hasMoreElements()) {
			zipEntry = (ZipEntry) enu.nextElement();

			path1 = zipEntry.getName();
			String str = path1;
			if (path1.lastIndexOf("/") != -1) {
				str = path1.substring(0, (path1.lastIndexOf("/")));
				File t = new File(strPath + File.separator + str);
				if (!t.exists()) {
					t.mkdirs();
				}
			} else {
				File t = new File(strPath + File.separator + str);
				if (!t.exists()) {
					t.createNewFile();
				}
			}

			// 创建文件夹
			if (zipEntry.isDirectory()) {

				tempPath = strPath + File.separator + path1;
				File dir = new File(tempPath);
				dir.mkdirs();
				continue;
			} else {

				is = zipFile.getInputStream(zipEntry);
				BufferedInputStream bis = new BufferedInputStream(is);
				path1 = zipEntry.getName();
				tempPath = strPath + File.separator + path1;
				logger.info("解压： " + tempPath);
				String subdirtemp = path1;
				for (int i = 0; i < subdirtemp.length(); i++) {
					if (subdirtemp.substring(i, i + 1).equalsIgnoreCase("\\")) {
						String tempstr = strPath + File.separator
								+ subdirtemp.substring(0, i);
						File subdir = new File(tempstr);
						if (!subdir.exists()) {
							subdir.mkdir();
						}
					}
				}
				fos = new FileOutputStream(tempPath);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				// int c ;
				// while((c = bis.read())!=-1){
				// bos.write(c);
				// }

				byte[] buf = new byte[1024];
				int len;
				while ((len = bis.read(buf)) > 0) {
					bos.write(buf, 0, len);
				}
				bis.close();
				bos.close();
				bis.close();
				bos.close();

			}
		}
		zipFile.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ZipCompressor zc = new ZipCompressor();
		zc.compress("d:\\szhzip.zip", "D:\\tmp");
		logger.info("压缩完成");
		try {
			// zc.decompress("d:\\szhzip.zip", "d:\\tempPhotos");
			logger.info("解压完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
