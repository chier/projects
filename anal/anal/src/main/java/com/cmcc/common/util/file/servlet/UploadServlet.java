package com.cmcc.common.util.file.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.Global;
import com.cmcc.common.util.StringUtil;
import com.jspsmart.upload.SmartUpload;

/**
 * 上传文件服务器
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-7-23
 */
public class UploadServlet extends HttpServlet {
	private static final Log logger = LogFactory.getLog(UploadServlet.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5344519603073214518L;

	private ServletConfig config;

	private String FileName;

	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}

	/**
	 * -1 表示错误 即上传文件在太大
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter writer = null;

		SmartUpload mySmartUpload = new SmartUpload();

		try {
			int length = request.getContentLength();
			writer = response.getWriter();
			if (Global.staticNodeFileMaxSize != 0
					&& length > Global.staticNodeFileMaxSize) {
				writer.print("-1");
			} else {
				mySmartUpload.initialize(config, request, response);

				mySmartUpload.upload();

				com.jspsmart.upload.File myFile = mySmartUpload.getFiles()
						.getFile(0);
				String filename = new String(myFile.getFileName().getBytes(),
						"utf-8");
				filename = filename.substring(0, filename.lastIndexOf("."));
				filename = StringUtil.StringFilter(filename);
				if (!myFile.isMissing()) {
					Date currTime = new Date();
					SimpleDateFormat formatter2 = new SimpleDateFormat(
							"yyyyMMddhhmmssS", Locale.US);
					String timeext = new String(formatter2.format(currTime)
							.getBytes("iso-8859-1"));
					String ext = myFile.getFileExt();
					FileName = (new StringBuilder(String.valueOf(filename))
							.append("_").append(timeext)).append(".").append(
							ext).toString();

					myFile.saveAs((new StringBuilder(String
							.valueOf(Global.staticNodeFilePath))).append(
							FileName).toString(), 1);
					writer.print(Global.staticNodeFilePath + FileName);
				}
			}
		} catch (Exception e) {
			writer.print("-1");
			// 可以跳转出错页面
			logger.error(e.getMessage(), e);
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
				writer = null;
			}
		}
	}
}
