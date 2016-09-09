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
 * ���ļ������Ĺ��߷���
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
	 * ������ļ�ѹ��Ϊһ���ļ�, ѹ������, �ļ���·�����ڸ���
	 * 
	 * @param dest
	 *            ѹ��Ϊ�ĸ��ļ�
	 * @param pathNames
	 *            ��Ҫѹ�����ļ����ļ���(��·��)
	 */
	public static void zipFiles(String dest, String[] pathNames,
			String fileComment) throws IOException {
		//�ȼ��ܣ���ѹ��
		//		pathNames = encrypt(pathNames, null);
		zipFiles(dest, pathNames, fileComment, Deflater.DEFAULT_COMPRESSION);
	}

	/**
	 * 
	 * @param dest
	 *            ����ļ�
	 * @param pathNames
	 *            �ļ�Ŀ¼��
	 * @param fileComment
	 *            zipע��
	 * @param compressLevel
	 *            ѹ������(0 ~ 9) 0Ϊ��ѹ��
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
	 * ��һ��ZipOutputStream����ѹ����ʹ�����Ҫ����closeZipOutputStream() �ر�
	 * 
	 * @param dest
	 *            ѹ���ļ���
	 * @param fileComment
	 *            ѹ���ļ�ע��
	 * @param compressLevel
	 *            ѹ������
	 * @return ZipOutputStream
	 * @throws IOException
	 * @author zhangming
	 * @since 2006-10-26 17:27:39
	 */
	public static ZipOutputStream getZipOutputStream(String dest,
			String fileComment, int compressLevel) throws IOException {

		File f = new File(dest);
		if (log.isDebugEnabled()) {
			log.debug("��ָ��" + dest + "]��ZipOutputStream...");
		}
		ZipOutputStream zos;
		try {
			zos = new ZipOutputStream(new FileOutputStream(f));
			zos.setLevel(compressLevel);
			if (fileComment != null) {
				zos.setComment(fileComment);
			}
		} catch (IOException e) {
			log.debug("��ZipOutputStreamʧ��");
			f.delete();
			throw e;
		}

		return zos;
	}

	/**
	 * ���һ���ļ���ZipOutputStream�� *
	 * 
	 * @param zos
	 *            ʹ��getZipOutputStream()��
	 * @param pathName
	 *            Ҫѹ�����ļ�·��
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
			//ѹ���ļ�
			encrypt(new String[] { pathName }, null);
			String s = getFileName(pathName);
			ZipEntry zipEntry = new ZipEntry(s);
			zipEntry.setComment(comment);
			zos.putNextEntry(zipEntry);
			writeFile(pathName, zos);

		} catch (IOException e) {
			log.error("����ļ�[" + pathName + "]��ѹ���ļ��г���!");
			throw e;
		}
	}

	/**
	 * �ر�ZipOutputStream
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
			log.error("�ر�ZipOutputStreamʧ��!");
			throw e;
		}
	}

	/**
	 * ��Ŀ¼ path �µ������ļ���ԭĿ¼�ṹѹ��Ϊ �ļ� destPathName
	 * 
	 * @param dir
	 *            ��ѹ��Ŀ¼�ľ���·��
	 * @param destPathName
	 *            ѹ������ļ��ľ���·��, �����ļ���
	 */
	public static void zipFiles(String dir, String destPathName)
			throws IOException {
		zipFiles(dir, destPathName, null,false);
	}

	/**
	 * ��Ŀ¼ path �µ������ļ���ԭĿ¼�ṹѹ��Ϊ �ļ� destPathName
	 * 
	 * @param dir
	 *            ��ѹ��Ŀ¼�ľ���·��(�����·���µ��ļ�����ѹ����
	 * @param destPathName
	 *            ѹ������ļ��ľ���·��, �����ļ���
	 * @param fileComment
	 *            ������ zip �ļ���ӵ� comment
	 * @param encFlag  
	 *           �Ƿ�Ҫ����         
	 */
	public static void zipFiles(String dir, String destPathName,
			String fileComment, boolean encFlag) throws IOException {
		//�ȼ��ܣ���ѹ��
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
	 * �� zip �ļ� pathName ��ѹ���� pathName ��ͬһ��Ŀ¼��
	 */
	public static void unzipFile(String pathName) throws IOException {
		unzipFile(pathName, null);
	}

	public static void main(String[] args) throws Exception {
		unzipFile("c:/temp/upload/111/epras20.zip");
	}

	/**
	 * ��ѹ��zip�ļ�
	 * 
	 * @param pathName
	 *            zip �ļ���·�����ļ���
	 * @param destDir
	 *            ��ѹ������
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
				//��ѹ�ļ�
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
	 * ����zip�ļ��е�ָ���ļ�
	 * @param pathName
	 *            zip �ļ���·�����ļ���
	 * @param fileName
	 *            Ҫ���ص��ļ���
	 * @param destDir
	 *            Ŀ���ļ�·��
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
					//��ѹ�ļ�
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
	 * �õ�ZipFile�������ڴ�ѹ���ļ�����ȡ�ļ�
	 * 
	 * @param pathName
	 *            zip �ļ���·�����ļ���
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
	 * ��zip������ȡ�����ļ�
	 * 
	 * @param zf
	 *            ZipFile����
	 * @param entryName
	 *            entity��(һ��Ϊ�ļ�����������Ŀ¼)
	 * @param destDir
	 *            ��ѹ������
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
			//��ѹ�ļ�
			decrypt(destDir + ze.getName(), destDir + ze.getName());
		} finally {
			IOUtils.closeQuietly(is);
		}

		if (log.isDebugEnabled()) {
			log.debug("file [" + entryName + "] unzipped");
		}
	}

	/**
	 * �ر�zipFile
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
	 * �ж��ļ��Ƿ���ѹ������
	 * 
	 * @param zf
	 *            ZipFile����
	 * @param fileName
	 *            �ļ���
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
	 * �� is д���ļ� pathName. ��� pathName ����Ŀ¼������, �򴴽���Ŀ¼
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
	 * ���ļ� pathName д�� out
	 */
	public static void writeFile(String pathName, OutputStream out)
			throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("writing file [" + pathName + "]...");
		}
		// FileReader reader = null; ���б�������
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
	 * ���ļ� pathName ��ĩβ length ���ֽ������ out, ��������һ���µ��п�ʼ
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
	 * ���ַ��� s д���ļ� pathName.
	 * 
	 * Note: ��������������������, ��ʹ�� FileWriterд��ʱ��, ��������. �����еĻ�����װ�Ĳ���ϵͳʹ�õ�ȱʡ�ַ������� GBK,
	 * ���� GB18030 (����һ����GBK����ı��뼯). �������Ļ�������ʱ�����ֽ�ͬ�����ַ���д���ļ�������ݺ�ͨ����־��ӡ������̨�ϵ�
	 * ���ݲ�һ�µĵط�, ����д���ļ�������ݻ���һЩ�ַ�. ���������� OutputStreamWriter ָ���ַ���Ϊ GBK ��д�ļ�.
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
	 *             д�ļ�ʱ����IOExceptionʱ�׳�
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
	 * д�ļ���ʱ��ͳһʹ�������������� Writer.
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
	 * ȡ���ļ����ļ���
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
	 * ����ļ���·��
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
	 * ɾ�������ļ�. Ҳ������ɾ��Ŀ¼.
	 * 
	 * @param pathNames
	 *            �ļ�·���ľ���·��
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
	 * ���һ����ʱ��ΨһĿ¼��. ����һ����ʱ�����Ϊ�ַ���Ŀ¼, ����:1135155994840. ���������������ͬ��, ��ֹ������ͬ��Ŀ¼��.
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
	 * ���·�����û��FileSeparator, ����
	 */
	public static String addFileSeparator(String path) {
		if (!path.endsWith(getFileSeparator())) {
			return path + getFileSeparator();
		}
		return path;
	}

	/**
	 * �����·�� pathname ���windowsĿ¼�ָ��� \ �� \\ �滻Ϊ /
	 */
	public static String toUnixPath(String pathname) {
		String s = StringUtils.replace(pathname, "\\\\", "/");
		s = StringUtils.replace(s, "\\", "/");
		return s;
	}

	/**
	 * �����·�� pathname ���unixĿ¼�ָ��� / �� \\ �滻Ϊ \
	 */
	public static String toWindowsPath(String pathname) {
		String s = StringUtils.replace(pathname, "/", "\\");
		s = StringUtils.replace(s, "\\\\", "\\");
		return s;
	}

	/**
	 * ���ļ� srcFile �ƶ��� destFile. ��� destFile ����, �ұ� srcFile ��, ����ݲ��� overwrite
	 * �����Ƿ񸲸�
	 * 
	 * @param srcFile
	 *            ���ƶ����ļ�
	 * @param destFile
	 *            �ƶ������ļ�
	 * @param overwrite
	 *            �Ƿ񸲸�
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
	 * ����Ŀ¼
	 */
	public static void mkdir(String dir) throws IOException {
		File f = new File(dir);
		org.apache.commons.io.FileUtils.forceMkdir(f);
	}

	/**
	 * �ж� dir ��һ��Ŀ¼��Ŀ¼����
	 */
	public static boolean isDirExist(String dir) {
		File f = new File(dir);
		return f.exists() && f.isDirectory();
	}

	/**
	 * ��Ŀ¼�¼�����Ŀ¼���ļ� �� addFile2Dir(c:/dir, afile) ���� c:/dir/afile Ŀ¼�����ڽ���Ŀ¼
	 * 
	 * @param dir
	 *            Ŀ¼��
	 * @param file
	 *            �ļ�������Ŀ¼��
	 * @return �ļ�����Ŀ¼������·��
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
//			throw new EprasRuntimeException("�ļ�����ʧ��", e);
		}

		return destPath;
	}

	/**
	 * ��Ŀ¼�¼�����Ŀ¼,������������� �� addFile2Dir(c:/dir, subDir) ���� c:/dir/subDir
	 * Ŀ¼�����ڽ���Ŀ¼
	 * 
	 * @param dir
	 *            Ŀ¼��
	 * @param subDir
	 *            ��Ŀ¼��
	 * @return �ļ�����Ŀ¼������·��
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
//			throw new EprasRuntimeException("�ļ�����ʧ��", e);
			e.printStackTrace();
		}
		return destPath;
	}

	/**
	 * ����Դ�ļ�·���´���������ʼ�ļ��У����TEMPĿ¼�Ѿ�����Ҫɾ�� ��Ŀ¼����������ļ� �˷����ϸ�˵�㲻��һ������������ķ���
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
				if (relativeDirs[i].trim().toLowerCase().equals("temp")) {// Ϊ��ʹ��������������޹أ��˴���ʱд��
					deleteDir(file);
					file.mkdirs();
				}
			}
		}
	}

	/**
	 * ��ü����ļ�
	 * @return ������Կ
	 */
	public static KeyPair getKeyPair() {
		//��������Կ��
		KeyPair kp = null;
		try {
			String fileName = "conf/security.key";
			InputStream is = FileUtils.class.getClassLoader()
					.getResourceAsStream(fileName);
			ObjectInputStream oos = new ObjectInputStream(is);
			kp = (KeyPair) oos.readObject();
			oos.close();
		} catch (Exception e) {
//			throw new EprasRuntimeException("��ȡ�����ļ�����.", e);
			e.printStackTrace();
		}
		return kp;
	}

	/**
	 * ���ļ�srcFile���ܺ�洢ΪdestFile
	 * 
	 * @param srcFiles Ҫ���ܵ������ļ�
	 * @param srcPath  Ҫ���ܵ�·��
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
					log.debug("�����ļ�[" + srcFile + "]...");
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
//			throw new EprasRuntimeException("����ʧ��.", e);
			e.printStackTrace();
		}
		return destFiles;
	}

	/**
	 * ���ļ�srcFile���ܺ�洢ΪdestFile
	 * 
	 * @param srcFile 
	 * @param destFile
	 */
	public static void decrypt(String srcFile, String destFile) {
		try {
			if (srcFile.toUpperCase().indexOf(".HES") <= 0) {
				if (log.isDebugEnabled()) {
					log.debug("�����ļ�[" + srcFile + "]...");
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
				log.debug("·����1��" + destFile);
				log.debug("·����2��" + srcFile);

//				DesUtil.decrypt(srcFile, destFile);
				//org.apache.commons.io.FileUtils.copyFile(new File(destFile), new File("d:\\asdf.csv"));
				delete(srcFile);
				new File(destFile).renameTo(new File(srcFile));
			}
		} catch (Exception e) {
//			throw new EprasRuntimeException("����ʧ��.", e);
			e.printStackTrace();
		}
	}
}