package org.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import com.ultrapower.eoms.common.core.util.StringUtils;
//import com.ultrapower.eoms.common.core.util.NumberUtils;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;


/**
 * 采用sun提供的ftp组件实现ftp客户端 
 * @author 朱朝辉(zhuzhaohui@ultrapower.com.cn)
 * @version Apr 21, 2010 8:42:55 PM
 */
public class FtpClientSolarisUtil {

	private FtpClient ftp;
	private String romateDir = "";
	private String userName = "";
	private String password = "";
	private String host = "";
	private String port = "21";

	/**
	 * 构造器
	 * @param url ftp远程地址
	 * @exception IOException
	 */
	public FtpClientSolarisUtil(String url) throws IOException {
		// String url="ftp://root:gzl_SJZ1616@10.120.32.60:21/ftptest/d/dd";
		int len = url.indexOf("//");
		String strTemp = url.substring(len + 2);
		len = strTemp.indexOf(":");
		userName = strTemp.substring(0, len);
		strTemp = strTemp.substring(len + 1);

		len = strTemp.indexOf("@");
		password = strTemp.substring(0, len);
		strTemp = strTemp.substring(len + 1);

		host = "";
		len = strTemp.indexOf(":");
		if (len < 0)// 没有设置端口
		{
			len = strTemp.indexOf("/");
			host = strTemp.substring(0, len);
			strTemp = strTemp.substring(len + 1);
		} else {
			host = strTemp.substring(0, len);
			strTemp = strTemp.substring(len + 1);
			len = strTemp.indexOf("/");
			port = strTemp.substring(0, len);
			strTemp = strTemp.substring(len + 1);
		}
		romateDir = strTemp;
		ftp = new FtpClient();
		ftp.openServer(host);

	}

	/**
	 * 构造方法，新建一个FtpClient对象，并打开FTP服务器
	 * @param host ftp远程主机地址
	 * @param port ftp远程地址端口
	 */
	public FtpClientSolarisUtil(String host, int port) throws IOException {
		ftp = new FtpClient();
		ftp.openServer(host, port);
	}

	/**
	 * 登陆方法，通过用户名密码登陆到指定的FTP服务器上去
	 * @param username 
	 * @param password
	 * @return String  返回欢迎信息
	 */
	public String login(String username, String password) throws IOException {
		this.ftp.login(username, password);
		if (!romateDir.equals(""))
			ftp.cd(romateDir);
		return this.ftp.welcomeMsg;
	}

	/**
	 * 登陆FTP服务器
	 * @return String  返回欢迎信息
	 * @exception IOException
	 */
	public String login() throws IOException {
		this.ftp.login(userName, password);
		if (!romateDir.equals(""))
			ftp.cd(romateDir);
		return this.ftp.welcomeMsg;
	}
	
	/**
	 * 上传文件
	 * @param pathname 本地路径
	 * @param filename 要上传的文件名称
	 * @return 如果上传成功,则返回 true;否则返回 false。
	 */
	public  boolean upload(String pathname, String filename,String newFilename) throws IOException {
		boolean flag = false;
//		filename = StringUtils.checkNullString(filename);
		if (filename.equals(""))
			return flag;
		if (!this.ftp.serverIsOpen()) {
			System.out.println("服务器连接不可用！");
		}
		this.ftp.binary();
		TelnetOutputStream os = null;
		FileInputStream is = null;
		try {
			// 用ftp上传后的文件名与原文件名相同，同为filename变量内容
			os = this.ftp.put(newFilename);
			java.io.File file_in = new java.io.File(pathname + File.separator
					+ filename);
			if (file_in.length() == 0) {
				System.out.println("上传文件为空!");
			}
			is = new FileInputStream(file_in);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			flag = true;
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}
		System.out.println(filename + "上传成功!");
		this.ftp.ascii();
		return flag;
	}
	public static void main(String[] args){
		FtpClientSolarisUtil f;
		try {
			f = new FtpClientSolarisUtil("ftp://mawenmiao:mawenmiao@192.168.20.14:21/ftp");
			f.login();
			f.upload("D:\\dt\\upload\\","20100903102232_GSM国漫数据汇总.xls","GSM国漫数据汇总.xls");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void download() {
	}

	/**
	 * 推出操作
	 */
	public void close() {
		try {
			if (ftp != null)
				ftp.closeServer();
		} catch (Exception ex) {
		}
	}
}
