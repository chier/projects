/**
 * 
 */
package com.cmcc.anal.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @filename:	 com.talent.platform.demo.ftp.FtpDemo
 * @copyright:   Copyright (c)2010
 * @company:     talent
 * @author:      谭耀武
 * @version:     1.0
 * @create time: 2012-9-5 下午2:36:14
 * @record
 * <table cellPadding="3" cellSpacing="0" style="width:600px">
 * <thead style="font-weight:bold;background-color:#e3e197">
 * 	<tr>   <td>date</td>	<td>author</td>		<td>version</td>	<td>description</td></tr>
 * </thead>
 * <tbody style="background-color:#ffffeb">
 * 	<tr><td>2012-9-5</td>	<td>谭耀武</td>	<td>1.0</td>	<td>create</td></tr>
 * </tbody>
 * </table>
 */
public class FtpUtils {
	private static Logger log = LoggerFactory.getLogger(FtpUtils.class);

	static String host = "192.168.9.236";
	static int port = 21;
	static String username = "ftpuser1";
	static String password = "pica2010";

	/**
	 * 
	 */
	public FtpUtils() {
		
	}

	/**
	 * Description: 向FTP服务器上传文件
	 * @param host FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param remotefiledir FTP服务器保存目录
	 * @param remotefilename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 * @throws IOException 
	 */
	public static boolean uploadFile(String host, int port, String username, String password, String remotefiledir,
			String remotefilename, File localFile) throws IOException {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			
			
			ftp.connect(host, port);//连接FTP服务器
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录

			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}

			boolean flag = ftp.makeDirectory(remotefiledir);
			log.debug("makeDirectory {} : {}", remotefiledir, flag);
//			
//			FTPFile[] ss = ftp.listFiles();

			flag = ftp.changeWorkingDirectory(remotefiledir);
			log.debug("changeWorkingDirectory to {} : {}", remotefiledir, flag);

			InputStream input = new FileInputStream(localFile);
			success = ftp.storeFile(remotefilename, input);

			input.close();
			ftp.logout();
		} catch (IOException e) {
			log.error("", e);
			throw e;
		} finally {
			if (ftp.isConnected()) {
				ftp.disconnect();
			}
		}
		return success;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 * @param host FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName 要下载的文件名
	 * @param localPath 下载后保存到本地的路径
	 * @param localFilename 下载后保存到本地的文件名(如果为null则和远程的文件名一样)
	 * @return
	 * @throws IOException 
	 */
	public static boolean downFile(String host, int port, String username, String password, String remotePath,
			String fileName, String localPath, String localFilename) throws IOException {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);//转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = null;
					if (localFilename != null){
						localFile = new File(localPath, localFilename);
					}else {
						localFile = new File(localPath, ff.getName());
					}

					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}

			ftp.logout();
			success = true;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					log.error(ioe.getMessage(), ioe);
				}
			}
		}
		return success;
	}

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		File localFile = new File("F:/work/platform-v2.0.1/platform-base-web/src/main/webapp/js/baseweb.js");
		String remotePath = "/ss";
		String remotefilename = "testUploadFile.js";
		
		
		boolean flag = uploadFile(host, port, username, password, remotePath, remotefilename, localFile);
		log.debug("uploadFile: " + flag);
		
		String localPath = "c:/";
		String localFilename = "xxx.js";
		flag = downFile(host, port, username, password, remotePath, remotefilename, localPath, localFilename);
		log.debug("downfile: " + flag);
	}
}
