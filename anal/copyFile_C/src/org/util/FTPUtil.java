package org.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * FTP 上传下载类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-16
 * 
 * 转自于：http://blog.csdn.net/hbcui1984/article/details/2720204
 */
public class FTPUtil {

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @Version1.0 Jul 27, 2008 4:31:09 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String url, int port, String username,
			String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(path);
			ftp.storeFile(filename, input);

			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**
	 * 1.将本地文件上传到FTP服务器上，代码如下：
	 * 
	 */
	public void testUpLoadFromDisk() {
		try {
			FileInputStream in = new FileInputStream(new File("D:/test.txt"));
			boolean flag = uploadFile("127.0.0.1", 21, "test", "test",
					"D:/ftp", "test.txt", in);
			System.out.println(flag);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 2 在FTP服务器上生成一个文件，并将一个字符串写入到该文件中
	 * 
	 */
	public void testUpLoadFromString() {
		try {
			InputStream input = new ByteArrayInputStream("test ftp"
					.getBytes("utf-8"));
			boolean flag = uploadFile("127.0.0.1", 21, "test", "test",
					"D:/ftp", "test.txt", input);
			System.out.println(flag);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * 
	 * @Version1.0 Jul 27, 2008 5:32:36 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * @param url
	 *            FTP服务器hostname
	 * @param port
	 *            FTP服务器端口
	 * @param username
	 *            FTP登录账号
	 * @param password
	 *            FTP登录密码
	 * @param remotePath
	 *            FTP服务器上的相对路径
	 * @param fileName
	 *            要下载的文件名
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String url, int port, String username,
			String password, String remotePath, String fileName,
			String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	public static void main(String[] args) {
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			// ftp.setRemoteHost("127.0.0.1");
			// FTPMessageCollector listener = new FTPMessageCollector();
			// ftpClient.setMessageListener(listener);
			// ftp.connect();
			// ftp.connect("192.168.1.103");
			ftp.connect("tgftp.nws.noaa.gov");
			ftp.login("anonymous", "testing@apache.org");

			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			// ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.out.println("没有连接到ftp服务器");
			}

			// File localFile = new File("c:/aa.txt");
			//
			// OutputStream is = new FileOutputStream(localFile);
			// ftp.retrieveFile("aa.txt", is);
			// is.close();

			// ftp.changeWorkingDirectory("/");// 转移到FTP服务器目录
			// // System.out.println(ftp.);
			ftp.changeWorkingDirectory("SL.us008001/DF.an/DC.sflnd");
			ftp.enterLocalPassiveMode();
			FTPFile[] fs = ftp.listFiles();
			System.out.println(fs.length);
			for (FTPFile ff : fs) {
				ff.isDirectory();
				System.out.println(ff.getName());
//				if (ff.getName().equals(fileName)) {
					File localFile = new File("c:/txt/" + ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
//				}

			}
			System.out.println("over");
			ftp.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
	}
}
