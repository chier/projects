package com.cmcc.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 对文件操作的工具方法
 * 
 * @author chenke
 */
public class FileUtils {
	private static final String FILE_SEPARATOR = "/";

	private static Log log = LogFactory.getLog(FileUtils.class);

	public static int DEFAULT_COMPRESS_LEVEL = 1;

	private FileUtils() {

	}

	public static String getFileSeparator() {
		return FILE_SEPARATOR;
	}

	/**
	 * 将多个文件压缩为一个文件, 压缩包里, 文件的路径都在根下
	 * 
	 * @param dest
	 *            压缩为哪个文件
	 * @param pathNames
	 *            需要压缩的文件的文件名(含路径)
	 */
	public static void zipFiles(String dest, String[] pathNames,
			String fileComment) throws IOException {
		//先加密，后压缩
		//		pathNames = encrypt(pathNames, null);
		zipFiles(dest, pathNames, fileComment, Deflater.DEFAULT_COMPRESSION);
	}

	/**
	 * 
	 * @param dest
	 *            输出文件
	 * @param pathNames
	 *            文件目录名
	 * @param fileComment
	 *            zip注释
	 * @param compressLevel
	 *            压缩级别(0 ~ 9) 0为不压缩
	 * @throws IOException
	 * @author zhangming
	 * @since 2006-8-15 13:33:41
	 */
	public static void zipFiles(String dest, String[] pathNames,
			String fileComment, int compressLevel) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("zipping files [" + Arrays.asList(pathNames) + "] to ["
					+ dest + "]...");
		}
		File f = new File(dest);
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f));
			zos.setLevel(compressLevel);
			if (fileComment != null) {
				zos.setComment(fileComment);
			}
			for (int i = 0; i < pathNames.length; i++) {
				String s = getFileName(pathNames[i]);
				ZipEntry zipEntry = new ZipEntry(s);
				zipEntry.setComment(fileComment);
				zos.putNextEntry(zipEntry);
				writeFile(pathNames[i], zos);
			}
			zos.close();
			if (log.isDebugEnabled()) {
				log.debug("file [" + dest + "] zipped");
			}
		} catch (IOException e) {
			f.delete();
			throw e;
		}
	}

	/**
	 * 打开一个ZipOutputStream用于压缩，使用完后要调用closeZipOutputStream() 关闭
	 * 
	 * @param dest
	 *            压缩文件名
	 * @param fileComment
	 *            压缩文件注释
	 * @param compressLevel
	 *            压缩级别
	 * @return ZipOutputStream
	 * @throws IOException
	 * @author zhangming
	 * @since 2006-10-26 17:27:39
	 */
	public static ZipOutputStream getZipOutputStream(String dest,
			String fileComment, int compressLevel) throws IOException {

		File f = new File(dest);
		if (log.isDebugEnabled()) {
			log.debug("打开指向" + dest + "]的ZipOutputStream...");
		}
		ZipOutputStream zos;
		try {
			zos = new ZipOutputStream(new FileOutputStream(f));
			zos.setLevel(compressLevel);
			if (fileComment != null) {
				zos.setComment(fileComment);
			}
		} catch (IOException e) {
			log.debug("打开ZipOutputStream失败");
			f.delete();
			throw e;
		}

		return zos;
	}

	/**
	 * 添加一个文件到ZipOutputStream中 *
	 * 
	 * @param zos
	 *            使用getZipOutputStream()打开
	 * @param pathName
	 *            要压缩的文件路径
	 * @throws IOException
	 * @author zhangming
	 * @since 2006-10-26 17:31:45
	 */
	public static synchronized void addToZipOutputStream(ZipOutputStream zos,
			String pathName, String comment) throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("zipping file [" + pathName + "]...");
		}

		try {
			//压缩文件
			encrypt(new String[] { pathName }, null);
			String s = getFileName(pathName);
			ZipEntry zipEntry = new ZipEntry(s);
			zipEntry.setComment(comment);
			zos.putNextEntry(zipEntry);
			writeFile(pathName, zos);

		} catch (IOException e) {
			log.error("添加文件[" + pathName + "]到压缩文件中出错!");
			throw e;
		}
	}

	/**
	 * 关闭ZipOutputStream
	 * 
	 * @param zos
	 *            ZipOutputStream
	 * @throws IOException
	 * @author zhangming
	 * @since 2006-10-26 17:35:08
	 */
	public static void closeZipOutputStream(ZipOutputStream zos)
			throws IOException {

		try {
			zos.close();
			if (log.isDebugEnabled()) {
				log.debug("finished  zip");
			}
		} catch (IOException e) {
			log.error("关闭ZipOutputStream失败!");
			throw e;
		}
	}

	/**
	 * 将目录 path 下的所有文件按原目录结构压缩为 文件 destPathName
	 * 
	 * @param dir
	 *            被压缩目录的绝对路径
	 * @param destPathName
	 *            压缩后的文件的绝对路径, 包含文件名
	 */
	public static void zipFiles(String dir, String destPathName)
			throws IOException {
		zipFiles(dir, destPathName, null,false);
	}

	/**
	 * 将目录 path 下的所有文件按原目录结构压缩为 文件 destPathName
	 * 
	 * @param dir
	 *            被压缩目录的绝对路径(将这个路径下的文件进行压缩）
	 * @param destPathName
	 *            压缩后的文件的绝对路径, 包含文件名
	 * @param fileComment
	 *            给整个 zip 文件添加的 comment
	 * @param encFlag  
	 *           是否要加密         
	 */
	public static void zipFiles(String dir, String destPathName,
			String fileComment, boolean encFlag) throws IOException {
		//先加密，后压缩
		if (encFlag) {
			encrypt(null, dir);
		}

		File f = new File(destPathName);
		try {
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(f));
			zos.setLevel(DEFAULT_COMPRESS_LEVEL);
			if (fileComment != null) {
				zos.setComment(fileComment);
			}
			Collection c = org.apache.commons.io.FileUtils.listFiles(new File(
					dir), null, true);
			for (Iterator iter = c.iterator(); iter.hasNext();) {
				File ff = (File) iter.next();
				String ss = ff.getAbsolutePath();
				ss = FileUtils.toUnixPath(ss);
				;
				String name = StringUtils.substringAfter(ss, dir);
				ZipEntry ze = new ZipEntry(name);
				if (new File(ss).isDirectory()) {
					continue;
				}
				zos.putNextEntry(ze);
				writeFile(ss, zos);
			}
			zos.close();
			if (log.isDebugEnabled()) {
				log.debug("file [" + destPathName + "] zipped");
			}
		} catch (IOException e) {
			f.delete();
			throw e;
		}
	}

	/**
	 * 将 zip 文件 pathName 解压缩到 pathName 的同一个目录下
	 */
	public static void unzipFile(String pathName) throws IOException {
		unzipFile(pathName, null);
	}

	public static void main(String[] args) throws Exception {
		unzipFile("c:/temp/upload/111/epras20.zip");
	}

	/**
	 * 解压缩zip文件
	 * 
	 * @param pathName
	 *            zip 文件的路径和文件名
	 * @param destDir
	 *            解压到哪里
	 */
	public static void unzipFile(String pathName, String destDir)
			throws IOException {
		if (destDir == null) {
			destDir = getPath(pathName);
		}
		if (!destDir.endsWith(FileUtils.getFileSeparator())) {
			destDir += FileUtils.getFileSeparator();
		}
		ZipFile zf = new ZipFile(pathName);
		for (Enumeration e = zf.entries(); e.hasMoreElements();) {
			ZipEntry ze = (ZipEntry) e.nextElement();
			if (log.isDebugEnabled()) {
				log.debug("unzipping file [" + ze.getName() + "] to ["
						+ destDir + "]");
			}
			InputStream is = null;
			try {
				is = zf.getInputStream(ze);
				writeInputStreamToFile(is, destDir + ze.getName());
				//解压文件
				//				decrypt(destDir + ze.getName(), destDir + ze.getName());
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("file [" + pathName + "] unzipped");
		}
	}

	/**
	 * 下载zip文件中的指定文件
	 * @param pathName
	 *            zip 文件的路径和文件名
	 * @param fileName
	 *            要下载的文件名
	 * @param destDir
	 *            目的文件路径
	 * @throws IOException
	 */
	public static void unzipAFile(String pathName, String fileName,
			String destDir) throws IOException {
		if (destDir == null) {
			destDir = getPath(pathName);
		}
		if (!destDir.endsWith(FileUtils.getFileSeparator())) {
			destDir += FileUtils.getFileSeparator();
		}
		ZipFile zf = new ZipFile(pathName);
		for (Enumeration e = zf.entries(); e.hasMoreElements();) {
			ZipEntry ze = (ZipEntry) e.nextElement();
			InputStream is = null;
			try {
				if (ze.getName().equals(fileName)) {
					if (log.isDebugEnabled()) {
						log.debug("unzipping a file [" + ze.getName()
								+ "] to [" + destDir + "]");
					}
					is = zf.getInputStream(ze);
					writeInputStreamToFile(is, destDir + ze.getName());
					//解压文件
					//					decrypt(destDir + ze.getName(), destDir + ze.getName());
					return;
				}
			} finally {
				IOUtils.closeQuietly(is);
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("file [" + pathName + "] unzipped");
		}
	}

	/**
	 * 得到ZipFile对象用于从压缩文件中提取文件
	 * 
	 * @param pathName
	 *            zip 文件的路径和文件名
	 */
	public static ZipFile getZipFile(String pathName) throws IOException {

		ZipFile zf = new ZipFile(pathName);

		return zf;

	}

	public static synchronized ZipEntry getEntryByName(ZipFile zf,
			String entryName, boolean ignoreCase) {
		if (StringUtils.isEmpty(entryName)) {
			return null;
		}

		ZipEntry zipEntry = null;
		Enumeration enu = zf.entries();
		while (enu.hasMoreElements()) {
			zipEntry = (ZipEntry) enu.nextElement();
			String ename = zipEntry.getName();
			if (ignoreCase && entryName.equalsIgnoreCase(ename)) {
				return zipEntry;
			}

			if (!ignoreCase && entryName.equals(ename)) {
				return zipEntry;
			}

		}

		return null;

	}

	/**
	 * 从zip包中提取单个文件
	 * 
	 * @param zf
	 *            ZipFile对象
	 * @param entryName
	 *            entity名(一般为文件名，不包含目录)
	 * @param destDir
	 *            解压到哪里
	 */
	public static synchronized void retrieveFileFromZipFile(ZipFile zf,
			String entryName, String destDir) throws IOException {

		if (destDir == null) {
			destDir = getPath(entryName);
		}
		if (!destDir.endsWith(FileUtils.getFileSeparator())) {
			destDir += FileUtils.getFileSeparator();
		}

		entryName = getFileName(entryName);
		//		ZipEntry ze = zf.getEntry(entryName);
		ZipEntry ze = getEntryByName(zf, entryName, true);
		if (ze == null) {
			log.warn("file [" + entryName + "] not exists in [" + zf.getName()
					+ "]");
			return;
		}
		if (log.isDebugEnabled()) {
			log.debug("from zip retrieve file [" + ze.getName() + "] to ["
					+ destDir + "]");
		}

		InputStream is = null;
		try {
			is = zf.getInputStream(ze);
			writeInputStreamToFile(is, destDir + ze.getName());
			//解压文件
			decrypt(destDir + ze.getName(), destDir + ze.getName());
		} finally {
			IOUtils.closeQuietly(is);
		}

		if (log.isDebugEnabled()) {
			log.debug("file [" + entryName + "] unzipped");
		}
	}

	/**
	 * 关闭zipFile
	 * 
	 * @param zf
	 * @throws IOException
	 * @author zhangming
	 * @since 2006-10-26 17:58:11
	 */
	public static void closeZipFile(ZipFile zf) throws IOException {
		zf.close();
	}

	/**
	 * 判断文件是否在压缩包里
	 * 
	 * @param zf
	 *            ZipFile对象
	 * @param fileName
	 *            文件名
	 * @author zhangming
	 * @since 2006-11-2 10:00:38
	 */
	public static boolean existsInFile(ZipFile zf, String fileName) {
		ZipEntry ze = zf.getEntry(fileName);
		if (log.isDebugEnabled()) {
			log.debug("file [" + fileName + "] not exists in [" + zf.getName()
					+ "]");
		}

		return ze != null ? true : false;
	}

	/**
	 * 将 is 写到文件 pathName. 如果 pathName 所在目录不存在, 则创建该目录
	 */
	public static void writeInputStreamToFile(InputStream is, String pathName)
			throws IOException {
		File f = null;
		FileOutputStream os = null;
		try {
			f = new File(pathName);
			String path = f.getParent();
			mkdir(path);
			os = new FileOutputStream(f);
			IOUtils.copy(is, os);
		} catch (IOException e) {
			if (f != null) {
				delete(f);
			}
			throw e;
		} finally {
			IOUtils.closeQuietly(os);
		}
	}

	/**
	 * 将文件 pathName 写到 out
	 */
	public static void writeFile(String pathName, OutputStream out)
			throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("writing file [" + pathName + "]...");
		}
		// FileReader reader = null; 会有编码问题
		FileInputStream fis = null;
		try {
			// reader = new FileReader(pathName);
			fis = new FileInputStream(pathName);
			// IOUtils.copy(reader, out);
			IOUtils.copy(fis, out);
		} finally {
			// IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(fis);
		}
	}

	/**
	 * 将文件 pathName 的末尾 length 个字节输出到 out, 且输出会从一个新的行开始
	 */
	public static void writeTailOfFile(String pathName, Writer out, long length)
			throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("writing file [" + pathName + "]...");
		}
		File f = null;
		InputStream is = null;
		try {
			f = new File(pathName);
			is = new FileInputStream(f);
			if (f.length() > length) {
				long n = f.length() - length - 1;
				is.skip(n);
			}
			Reader reader = new InputStreamReader(is);
			for (int n = 0; n != '\r' && n != '\n'; n = reader.read()) {
			}
			int n = reader.read();
			if (n != '\n') {
				out.write(n);
			}
			IOUtils.copy(reader, out);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	/**
	 * 将字符串 s 写成文件 pathName.
	 * 
	 * Note: 曾经遇到过这样的问题, 在使用 FileWriter写的时候, 代码如下. 由于有的机器安装的操作系统使用的缺省字符集不是 GBK,
	 * 而是 GB18030 (这是一个比GBK更大的编码集). 在这样的机器上有时候会出现将同样的字符串写到文件里的内容和通过日志打印到控制台上的
	 * 内容不一致的地方, 比如写到文件里的内容会少一些字符. 因此这里采用 OutputStreamWriter 指定字符集为 GBK 来写文件.
	 * 
	 * <pre>
	 * FileWriter fw = null;
	 * try {
	 * 	fw = new FileWriter(pathName);
	 * 	fw.write(s);
	 * 	fw.flush();
	 * } finally {
	 * 	IOUtils.closeQuietly(fw);
	 * }
	 * </pre>
	 * 
	 * @throws EprasRuntimeException
	 *             写文件时出现IOException时抛出
	 */
	public static void writeStringToFile(String s, String pathName)
			throws IOException {
		Writer out = null;
		try {
			out = getFileWriter(pathName);
			out.write(s);
			out.flush();
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	/**
	 * 写文件的时候统一使用这个方法来获得 Writer.
	 */
	public static Writer getFileWriter(String pathName) throws IOException {
		return getFileWriter(new File(pathName));
	}

	public static Writer getFileWriter(File file) throws IOException {
		return new OutputStreamWriter(new FileOutputStream(file), "GBK");
		// OutputStreamWriter out = new OutputStreamWriter(new
		// FileOutputStream(file), "GB18030");
		// System.out.println(out.getEncoding());
		// return out;
	}

	/**
	 * 取得文件的文件名
	 */
	public static String getFileName(String pathName) {
		if (pathName == null) {
			return null;
		}

		pathName = toUnixPath(pathName);
		int n = pathName.lastIndexOf(FileUtils.getFileSeparator());
		if (n == -1) {
			return pathName;
		} else {
			return pathName.substring(n + 1);
		}
	}

	/**
	 * 获得文件的路径
	 */
	public static String getPath(String pathName) {
		if (pathName == null) {
			return null;
		}
		pathName = toUnixPath(pathName);
		int n = pathName.lastIndexOf(FileUtils.getFileSeparator());
		if (n == -1) {
			return pathName;
		} else {
			return pathName.substring(0, n);
		}
	}

	private static final boolean[] EMPTY_BOOLEAN = new boolean[0];

	/**
	 * 删除所有文件. 也可用于删除目录.
	 * 
	 * @param pathNames
	 *            文件路径的绝对路径
	 */
	public static boolean[] delete(String[] pathNames) {
		if (pathNames == null || pathNames.length == 0) {
			return EMPTY_BOOLEAN;
		}
		boolean[] b = new boolean[pathNames.length];
		for (int i = 0; i < pathNames.length; i++) {
			File f = new File(pathNames[i]);
			if (f.isDirectory()) {
				b[i] = deleteDir(f);
			} else {
				b[i] = delete(f);
			}
		}
		return b;
	}

	public static boolean delete(String pathName) {
		return delete(new String[] { pathName })[0];
	}

	// private static boolean delete(File f) {
	// return f.delete();
	// }

	private static final int DELETE_RETRY_SLEEP_MILLIS = 10;

	/**
	 * Accommodate Windows bug encountered in both Sun and IBM JDKs. Others
	 * possible. If the delete does not work, call System.gc(), wait a little
	 * and try again.
	 */
	private static boolean delete(File f) {
		if (!f.delete()) {
			// if (Os.isFamily("windows")) {
			System.gc();
			// }
			try {
				Thread.sleep(DELETE_RETRY_SLEEP_MILLIS);
			} catch (InterruptedException ex) {
				// Ignore Exception
			}
			if (!f.delete()) {
				return false;
			}
		}
		return true;
	}

	public static boolean deleteDir(File d) {
		String[] list = d.list();
		if (list == null) {
			list = new String[0];
		}
		for (int i = 0; i < list.length; i++) {
			String s = list[i];
			File f = new File(d, s);
			if (f.isDirectory()) {
				deleteDir(f);
			} else {
				if (!delete(f)) {
					log.info("Unable to delete file " + f.getAbsolutePath());
					return false;
				}
			}
		}
		if (!delete(d)) {
			log.info("Unable to delete directory " + d.getAbsolutePath());
			return false;
		}
		return true;
	}

	private static final String LOCK = "LOCK";

	/**
	 * 获得一个临时的唯一目录名. 返回一个以时间戳作为字符的目录, 例如:1135155994840. 这个方法本身做了同步, 防止出现相同的目录名.
	 */
	public static String getDirName() {
		synchronized (LOCK) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
			return System.currentTimeMillis() + "";
		}
	}

	/**
	 * 如果路径最后没有FileSeparator, 则补上
	 */
	public static String addFileSeparator(String path) {
		if (!path.endsWith(getFileSeparator())) {
			return path + getFileSeparator();
		}
		return path;
	}

	/**
	 * 将相对路径 pathname 里的windows目录分隔符 \ 和 \\ 替换为 /
	 */
	public static String toUnixPath(String pathname) {
		String s = StringUtils.replace(pathname, "\\\\", "/");
		s = StringUtils.replace(s, "\\", "/");
		return s;
	}

	/**
	 * 将相对路径 pathname 里的unix目录分隔符 / 和 \\ 替换为 \
	 */
	public static String toWindowsPath(String pathname) {
		String s = StringUtils.replace(pathname, "/", "\\");
		s = StringUtils.replace(s, "\\\\", "\\");
		return s;
	}

	/**
	 * 将文件 srcFile 移动到 destFile. 如果 destFile 存在, 且比 srcFile 新, 则根据参数 overwrite
	 * 决定是否覆盖
	 * 
	 * @param srcFile
	 *            被移动的文件
	 * @param destFile
	 *            移动到的文件
	 * @param overwrite
	 *            是否覆盖
	 */
	public static void move(File srcFile, File destFile, boolean overwrite)
			throws IOException {
		boolean move = srcFile.renameTo(destFile);
		if (!move) {
			moveUsingCopy(srcFile, destFile, overwrite);
		}
	}

	public static void move(String srcFile, String destFile) throws IOException {
		move(srcFile, destFile, true);
	}

	public static void move(String srcFile, String destFile, boolean overwrite)
			throws IOException {
		move(new File(srcFile), new File(destFile), overwrite);
	}

	private static void moveUsingCopy(File srcFile, File destFile,
			boolean overwrite) throws IOException {
		if (!destFile.exists()) {
			org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
			delete(srcFile);
			return;
		}
		if (org.apache.commons.io.FileUtils.isFileNewer(srcFile, destFile)) {
			org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
			delete(srcFile);
		} else {
			if (overwrite) {
				org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
				delete(srcFile);
			} else {
				if (log.isDebugEnabled()) {
					log.debug("[" + srcFile.getAbsolutePath()
							+ "] is older than [" + destFile.getAbsolutePath()
							+ "], ignore it");
				}
			}
		}
	}

	/**
	 * 创建目录
	 */
	public static void mkdir(String dir) throws IOException {
		File f = new File(dir);
		org.apache.commons.io.FileUtils.forceMkdir(f);
	}

	/**
	 * 判断 dir 是一个目录且目录存在
	 */
	public static boolean isDirExist(String dir) {
		File f = new File(dir);
		return f.exists() && f.isDirectory();
	}

	/**
	 * 给目录下加入子目录或文件 如 addFile2Dir(c:/dir, afile) 返回 c:/dir/afile 目录不存在建立目录
	 * 
	 * @param dir
	 *            目录名
	 * @param file
	 *            文件名或子目录名
	 * @return 文件或子目录的完整路径
	 * @author zhangming
	 * @since 2006-11-7 16:48:18
	 */
	public static String addFile2Dir(String dir, String file) {
		String destPath = null;
		try {
			dir = toUnixPath(dir);
			// dir = getPath(dir);
			if (!isDirExist(dir)) {
				mkdir(dir);
			}

			int n = dir.lastIndexOf(FileUtils.getFileSeparator());
			while (n == dir.length() - 1) {
				dir = dir.substring(0, n);
				n = dir.lastIndexOf(FileUtils.getFileSeparator());
			}
			file = toUnixPath(file);

			destPath = dir + getFileSeparator() + file;

		} catch (Exception e) {
			e.printStackTrace();
//			throw new EprasRuntimeException("文件操作失败", e);
		}

		return destPath;
	}

	/**
	 * 给目录下加入子目录,如果不存在则建立 如 addFile2Dir(c:/dir, subDir) 返回 c:/dir/subDir
	 * 目录不存在建立目录
	 * 
	 * @param dir
	 *            目录名
	 * @param subDir
	 *            子目录名
	 * @return 文件或子目录的完整路径
	 * @author zhangming
	 * @since 2006-11-7 16:48:18
	 */
	public static String addSub2Dir(String dir, String subDir) {
		String destPath = null;
		try {
			destPath = addFile2Dir(dir, subDir);
			if (!isDirExist(destPath)) {
				mkdir(destPath);
			}
		} catch (Exception e) {
//			throw new EprasRuntimeException("文件操作失败", e);
			e.printStackTrace();
		}
		return destPath;
	}

	/**
	 * 在资源文件路径下创建几个初始文件夹，如果TEMP目录已经存在要删除 改目录下面的所有文件 此方法严格说算不上一个工具类里面的方法
	 * 
	 * @author cuiwencai
	 * @since 2007-11-8
	 */
	public static void addResFolder(String baseDir, String[] relativeDirs) {
		File file = null;
		for (int i = 0; i < relativeDirs.length; i++) {
			file = new File(baseDir + relativeDirs[i]);
			if (!file.exists()) {
				file.mkdirs();
			} else {
				if (relativeDirs[i].trim().toLowerCase().equals("temp")) {// 为了使工具类和其他类无关，此处暂时写死
					deleteDir(file);
					file.mkdirs();
				}
			}
		}
	}

	/**
	 * 获得加密文件
	 * @return 加密密钥
	 */
	public static KeyPair getKeyPair() {
		//产生新密钥对
		KeyPair kp = null;
		try {
			String fileName = "conf/security.key";
			InputStream is = FileUtils.class.getClassLoader()
					.getResourceAsStream(fileName);
			ObjectInputStream oos = new ObjectInputStream(is);
			kp = (KeyPair) oos.readObject();
			oos.close();
		} catch (Exception e) {
//			throw new EprasRuntimeException("读取加密文件出错.", e);
			e.printStackTrace();
		}
		return kp;
	}

	/**
	 * 把文件srcFile加密后存储为destFile
	 * 
	 * @param srcFiles 要加密的所有文件
	 * @param srcPath  要加密的路径
	 */
	private static String[] encrypt(String[] srcFiles, String srcPath) {
		String[] destFiles = null;
		try {
			SecureRandom sr = new SecureRandom();
			int i = 0;
			if (StringUtils.isNotEmpty(srcPath)) {
				Collection c = org.apache.commons.io.FileUtils.listFiles(
						new File(srcPath), null, true);
				srcFiles = new String[c.size()];
				for (Iterator iter = c.iterator(); iter.hasNext(); i++) {
					File ff = (File) iter.next();
					String ss = ff.getAbsolutePath();
					srcFiles[i] = FileUtils.toUnixPath(ss);
				}
			}

			destFiles = srcFiles;

			for (i = 0; i < srcFiles.length; i++) {
				String srcFile = srcFiles[i];
				if (srcFile.toUpperCase().indexOf(".HES") > 0) {
					continue;
				}
				if (log.isDebugEnabled()) {
					log.debug("加密文件[" + srcFile + "]...");
				}
				String destFile = srcFile + ".RSA";
				//				InputStream fis = new FileInputStream(srcFile);
				//				FileOutputStream fos = new FileOutputStream(destFile);
				//				
				//				byte[] b = new byte[100];
				//				int n=0;
				////				//while ((n=fis.read(b))>=0) {
				//				while ((n=fis.read(b))>=0) {
				//					fos.write(Base64.decode(new String(b)));
				//					//fos.write(cipher.doFinal(b));
				//				}
				//				fos.close();
				//				fis.close();
				//				RSAEncryptUtil.encryptFile(srcFile, destFile, publicKey);
				//				String passWord="1234ABCD";
				//				FileDES fileDes=new FileDES(srcFile,destFile,passWord,true);
//				DesUtil.encrypt(srcFile, destFile);
				delete(srcFile);
				new File(destFile).renameTo(new File(srcFile));
			}
		} catch (Exception e) {
//			throw new EprasRuntimeException("加密失败.", e);
			e.printStackTrace();
		}
		return destFiles;
	}

	/**
	 * 把文件srcFile解密后存储为destFile
	 * 
	 * @param srcFile 
	 * @param destFile
	 */
	public static void decrypt(String srcFile, String destFile) {
		try {
			if (srcFile.toUpperCase().indexOf(".HES") <= 0) {
				if (log.isDebugEnabled()) {
					log.debug("解密文件[" + srcFile + "]...");
				}

				destFile = srcFile + ".RSA";

				//				FileInputStream fis = new FileInputStream(srcFile);
				//				FileOutputStream fos = new FileOutputStream(destFile);
				//				byte[] b = new byte[64];
				//				int n=0;
				////				while (fis.read(b) != -1) {
				//				while ((n=fis.read(b))>=0) {
				//					fos.write(Base64.decode(new String(b)));
				//					//fos.write(cipher.doFinal(b));
				//				}
				//				fos.close();
				//				fis.close();
				//				RSAEncryptUtil.decryptFile(srcFile, destFile, privateKey);
				//				String passWord="1234ABCD";
				//				FileDES fileDes=new FileDES(srcFile,destFile,passWord,false);
				log.debug("路径是1：" + destFile);
				log.debug("路径是2：" + srcFile);

//				DesUtil.decrypt(srcFile, destFile);
				//org.apache.commons.io.FileUtils.copyFile(new File(destFile), new File("d:\\asdf.csv"));
				delete(srcFile);
				new File(destFile).renameTo(new File(srcFile));
			}
		} catch (Exception e) {
//			throw new EprasRuntimeException("解密失败.", e);
			e.printStackTrace();
		}
	}
}