package org.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 调用apache ftp组件实现的FTP客户端
 * 
 * @author 朱朝辉(zhuzhaohui@ultrapower.com.cn)
 * @version Apr 21, 2010 5:50:00 PM
 */
public class FtpClientApacheUtil {
	private static FTPClient ftpClient = new FTPClient();
	private static String userName = "";
	private static String password = "";
	private static String host = "";
	private static int port = 0;
	private static String remoteFileDir = "";
	private static String remoteBakFileDir = "";

	/**
	 * @param usernamestr
	 *            FTP登录账号
	 * @param passwordstr
	 *            FTP登录密码
	 * @param hoststr
	 *            FTP服务器主机地址
	 * @param portstr
	 *            FTP服务器端口
	 * @param remoteFileDirstr
	 *            FTP服务器上的相对路径,下载地址
	 * @param remoteBakFileDirstr
	 *            FTP服务器上的相对路径,上传地址
	 */
	public FtpClientApacheUtil(String usernamestr, String passwordstr,
			String hoststr, int portstr, String remoteFileDirstr,
			String remoteBakFileDirstr) {
		userName = usernamestr;
		password = passwordstr;
		host = hoststr;
		port = portstr;
		remoteFileDir = remoteFileDirstr;
		remoteBakFileDir = remoteBakFileDirstr;
	}

	/**
	 * 向FTP服务器上传文件
	 * 
	 * @param input
	 *            输入流
	 * @return 如果上传成功,则返回 true;否则返回 false。
	 */
	public static boolean uploadFile(String filename, InputStream input) {
		boolean success = false;
		try {
			int reply;
			ftpClient.setControlEncoding("GBK");// 支持中文
			ftpClient.connect("192.168.20.14");// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftpClient.login("mawenmiao", "mawenmiao");// 登录
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return success;
			}
			ftpClient.changeWorkingDirectory("/ftp");
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.storeFile(filename, input);
			input.close();
			// ftpClient.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
					ioe.printStackTrace();
				}
			}
		}
		return success;
	}

	/**
	 * 从FTP服务器下载文件
	 * 
	 * @param localPath
	 *            下载后保存到本地的路径
	 * @return 如果下载成功,则返回 true;否则返回 false。
	 */
	public static boolean downFile(String localPath) {
		boolean success = false;
		try {
			int reply;
			ftpClient.connect(host, port);
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			System.out.println("host：" + host);
			System.out.println("port：" + port);
			System.out.println("userName：" + userName);
			System.out.println("password：" + password);
			boolean loginflag = ftpClient.login(userName, password);// 登录
			if (loginflag == true) {
				System.out.println("登陆ftp服务器目录成功");
			} else {
				System.out.println("登陆ftp服务器目录失败");
			}
			reply = ftpClient.getReplyCode();
			System.out.println("reply：" + reply);
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return success;
			}
			System.out.println("进入ftp服务器目录" + remoteFileDir + "....begin");
			boolean a = ftpClient.changeWorkingDirectory(remoteFileDir);// 转移到FTP服务器目录
			if (a == true) {
				System.out.println("进入ftp服务器目录" + remoteFileDir + "....成功");
			} else {
				System.out.println("进入ftp服务器目录" + remoteFileDir + "....失败");
			}
			ftpClient.setControlEncoding("GBK");// 支持中文
			FTPFile[] fs = ftpClient.listFiles();
//			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			for (int i = 0; i < fs.length; i++) {
				System.out.println(fs[i].getName());
				if (!".".equals(fs[i].getName()) && !"..".equals(fs[i].getName())) {
					System.out.println("开始下载文件：" + fs[i].getName() + "到本地");
					System.out.println("file:" + fs[i].getName());
					File localFile = new File(localPath + "/" + fs[i].getName());
					System.out.println("下载到本地的文件：" + localPath + "/"
							+ fs[i].getName());
					OutputStream is = new FileOutputStream(localFile);
					boolean down = ftpClient.retrieveFile(new String(fs[i].getName().getBytes("GBK"),"iso-8859-1"), is);
					is.close();
					if (down == true)
						ftpClient.deleteFile(fs[i].getName());
				}
			}
			// ftpClient.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					System.out.println(ioe.getMessage());
					ioe.printStackTrace();
				}
			}
		}
		return success;
	}

	public static void main(String[] args) {
		FtpClientApacheUtil ftpCliet = new FtpClientApacheUtil("admin",
				"tarcy", "10.100.103.162", 8004, "copyFile2012-10-16",
				"copyFile2012-10-16");
		// FileInputStream fis;
		ftpCliet.downFile("D:\\tempPhotos");
		// fis = new FileInputStream(new
		// File("D:\\dt\\upload\\20100903102232_GSM国漫数据汇总.xls"));
		// uploadFile("test.xls",fis);
		// uploadFile("20100903102232_GSM国漫数据汇总.xls",fis);

	}

}
