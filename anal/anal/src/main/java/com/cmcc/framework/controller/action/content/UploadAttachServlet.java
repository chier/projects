package com.cmcc.framework.controller.action.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.cmcc.common.Global;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.model.content.ContentAttach;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;

public class UploadAttachServlet extends HttpServlet {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(UploadAttachServlet.class);

	private ServletConfig config;
	private String FileName;
	private static final long serialVersionUID = 6218159993356366729L;
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		if (logger.isDebugEnabled()) {
			logger.debug("doGet(HttpServletRequest request=" + request
					+ ", HttpServletResponse response=" + response
					+ ") - start");
		}
		ServletOutputStream out = null;
		response.setContentType("text/html;charset=utf-8");
		try {
			out = response.getOutputStream();
		} catch (IOException e1) {

		}
		SmartUpload mySmartUpload = new SmartUpload();

		try {
			String contype = request.getContentType();
			int length = request.getContentLength();
			if (contype.indexOf("multipart/form-data") != -1) {
				if (length < Global.CONTENT_ATTACH_MAXSIZE.longValue()) {
					mySmartUpload.setMaxFileSize(Global.CONTENT_ATTACH_MAXSIZE
							.longValue());
					mySmartUpload.initialize(config, request, response);

					mySmartUpload.upload();
					File myFile = mySmartUpload.getFiles().getFile(0);
					String filename = new String(myFile.getFileName()
							.getBytes(), "utf-8");
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
								.append("_").append(timeext)).append(".")
								.append(ext).toString();

						myFile.saveAs((new StringBuilder(String
								.valueOf(Global.CONTENT_STATIC_FILEPATH)))
								.append(FileName).toString(), 1);
						ContentAttach attach = new ContentAttach();
						attach.setAvailflg(new Integer(1));
						attach.setIdentifier(null);
						attach.setAttName(FileName);
						attach.setLink(Global.CONTENT_STATIC_FILEPATH);
						AttachMap attmap = (AttachMap) request
								.getSession(false).getAttribute("att_list");
						if (attmap != null) {
							attmap.addAttach(timeext, attach);
						}
						out.println("<script>parent.callback('1&"
								+ timeext
								+ "~"
								+ StringUtil.urlEncode(attach.getAttName(),
										"utf-8") + "')</script>");
						out.flush();
					} else {
						try {
							out
									.println("<script>parent.callback('0&a');</script>");
							out.flush();
						} catch (IOException e) {
							if (logger.isErrorEnabled()) {
								logger.error("uploadFiles()", e);
							}

							e.printStackTrace();
						}
					}
				} else {
					try {
						out
								.println("<script>parent.callback('-1&a');</script>");
						out.flush();
					} catch (IOException e) {
						if (logger.isErrorEnabled()) {
							logger.error("uploadFiles()", e);
						}

						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger
						.error("doGet(HttpServletRequest request=" + request
								+ ", HttpServletResponse response=" + response
								+ ")", e);
			}
			try {
				out.println("<script>parent.callback('0&a');</script>");
			} catch (IOException es) {
				if (logger.isErrorEnabled()) {
					logger.error("uploadFiles()", es);
				}
			}
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					if (logger.isErrorEnabled()) {
						logger.error("uploadFiles()", e);
					}
				}
			}

		}

	}
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("doPost(HttpServletRequest req=" + req
					+ ", HttpServletResponse resp=" + resp + ") - start");
		}

		this.doGet(req, resp);

		if (logger.isInfoEnabled()) {
			logger.info("doPost(HttpServletRequest req=" + req
					+ ", HttpServletResponse resp=" + resp + ") - end");
		}
	}
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}

}
