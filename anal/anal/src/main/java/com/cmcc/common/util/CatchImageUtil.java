/*
 * CatchImageUtil.java
 * 远程图片抓取工具类
 * 原始作者 <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 2009-3-19
 */
package com.cmcc.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 远程图片抓取工具类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since Mar 19, 2009
 */
public class CatchImageUtil {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(CatchImageUtil.class);

	/**
	 * 根据网页地址获取网页内容
	 * 
	 * @param httpUrl
	 *            网页地址
	 * @return 网页内容
	 */
	private byte[] bytes = new byte[5096];;
	public String getHtmlCode(String httpUrl) {
		if (logger.isDebugEnabled()) {
			logger.debug("getHtmlCode(String httpUrl=" + httpUrl + ") - start");
		}

		String htmlCode = "";
		try {
			InputStream in;
			URL url = new java.net.URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.connect();
			in = connection.getInputStream();
			int length = -1;
			while ((length = in.read(bytes)) != -1) {
				htmlCode += new String(bytes, 0, length);
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("getHtmlCode(String httpUrl=" + httpUrl + ")", e);
			}

			e.printStackTrace();
		}
		if (htmlCode == null) {

			if (logger.isInfoEnabled()) {
				logger.info("getHtmlCode(String httpUrl=" + httpUrl + ") - end - return value=");
			}
			return "";
		}

		if (logger.isInfoEnabled()) {
			logger.info("getHtmlCode(String httpUrl=" + httpUrl + ") - end - return value=" + htmlCode);
		}
		return htmlCode;
	}
	/**
	 * 获取网页内容中所有图片的链接
	 * 
	 * @param 网页内容字符串
	 * @return 图片链接地址集合
	 */
	public List<String> getImagePath(String content) {
		if (logger.isDebugEnabled()) {
			logger.debug("getImagePath(String content=" + content + ") - start");
		}

		List<String> resultList = new ArrayList<String>();
		// String searchImgReg =
		// "(?x)(src|SRC|background|BACKGROUND)=('|\")(http://([\\w-]+\\.)+[\\w-]+(:[0-9]+)*(/[\\w-]+)*(/[\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";
		// String searchImgReg =
		// "(?x)(src|SRC|background|BACKGROUND)=('|\")/?(([\\w-]+/)*([\\w-]+\\.(jpg|JPG|png|PNG|gif|GIF)))('|\")";
		String searchImgReg = "(?<=src=\").+?\\.(gif|jpg|bmp|png|GIF|JPG|BMP|PNG)(?=\")";
		Pattern pattern = Pattern.compile(searchImgReg);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			resultList.add(matcher.group());
		}

		if (logger.isInfoEnabled()) {
			logger.info("getImagePath(String content=" + content + ") - end - return value=" + resultList);
		}
		return resultList;
	}
	/**
	 * 根据图片地址抓取图片 并保存到本地路径下
	 * 
	 * @param imagepath
	 *            图片地址
	 * @param localpath
	 *            本地路径(绝对路径) 例如在windows系统中 d:/
	 * @return 抓取成功(true)or失败(false)
	 */
	public synchronized boolean catchPic(String imagepath, String localpath) {
		BufferedInputStream input = null;
		OutputStream out = null;
		int last = imagepath.lastIndexOf("/");
		String filename = null;
		if (last > 0) {
			filename = imagepath.substring(last + 1, imagepath.length());
		}
		URL url = null;

		try {
			if (filename != null) {
				url = new URL(imagepath);
				input = new BufferedInputStream(url.openStream());
				int len;
				out = new FileOutputStream(localpath + "/" + filename);
				while ((len = input.read(bytes)) > 0) {
					out.write(bytes, 0, len);
				}
				return true;
			}
		} catch (MalformedURLException e) {
			if (logger.isErrorEnabled()) {
				logger.error("catchPic(MalformedURLException)", e);
			}

			e.printStackTrace();
		} catch (IOException ioe) {
			if (logger.isErrorEnabled()) {
				logger.error("catchPic(IOException)", ioe);
			}

			ioe.printStackTrace();
			if (filename != null) {
				File f = new File(localpath + filename);
				if (f.exists()) {
					f.delete();
				}
			}
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (Exception ex) {
				if (logger.isErrorEnabled()) {
					logger.error("catchPic(Exception)", ex);
				}

				ex.printStackTrace();
			}
		}

	
		return false;
	}
}
