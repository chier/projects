/* 
 * 文件名称：ContentAttachOperatorAction.java
 * 
 * 创建时间：2009-3-3
 *
 * 原始作者：曹巍
 */
package com.cmcc.framework.controller.action.content;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.file.FileUtil;
import com.cmcc.common.util.file.ZipFileUtil;
import com.cmcc.framework.business.interfaces.content.IContentInfoManager;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.content.ContentAttach;
import com.cmcc.framework.model.content.ContentInfo;

/**
 * 内容附件上传删除操作类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-3-3
 */
public class ContentAttachOperatorAction extends WebActionBase {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ContentAttachOperatorAction.class);

	private static final long serialVersionUID = -3702255233821794421L;

	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	/**
	 * 下载方法类
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws UnsupportedEncodingException
	 */
	public String download() throws BusinessException,
			UnsupportedEncodingException {
		String sctId = getRequest().getParameter("ctId");

		if (StringUtil.isBlank(sctId) || !StringUtil.isNumeric(sctId)) {
			return null;
		}
		ContentAttach attach = this.contentAttachManager.get(Integer
				.valueOf(sctId));
		if (attach == null || StringUtil.isBlank(attach.getAttName())
				|| StringUtil.isBlank(attach.getLink())) {
			return null;
		}
		File file = new File(attach.getLink());
		if (!file.exists()) {
			return null;
		}
		String attName = attach.getAttName();
		attName = new String(attName.getBytes("GBK"), "ISO8859-1");
		this.contentInfoManager.updateAddAttachNumber(attach.getContent()
				.getIdentifier());
		getResponse().setContentType("application/octet-stream;charset=GBK");
		getResponse().setHeader("Content-Disposition",
				"attachment; filename= " + attName);
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = this.getResponse().getOutputStream();
			inputStream = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int x = 0;
			while ((x = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes);
			}
		} catch (IOException ioe) {
			logger.error(ioe);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
				inputStream = null;
			}
			if (outputStream != null) {

				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
				outputStream = null;
			}
		}
		return null;
	}

	/**
	 * 下载方法类
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws IOException
	 */
	@GenericLogger(operateMark = OperateMark.EXPORT_DATA_DOWNLOAD_ALL, operateDescription = "数据下载-附件群下载", isExtend = true)
	public String downloadAll() throws BusinessException, IOException {
		String sctId = getRequest().getParameter("ctIds");
		String title = getRequest().getParameter("title");

		if (StringUtils.isBlank(title)) {
			title = "数据下载";
		} else {
			title = new String(title.getBytes("ISO8859-1"), "utf-8");
			
		}
		// Writer out = getResponse().getWriter();
		if (StringUtil.isBlank(sctId)) {
			return null;
		}
		List<ContentInfo> infoList = this.contentInfoManager
				.findInfoByIds(sctId);
		StringBuilder bui = new StringBuilder();
		for (ContentInfo info : infoList) {
			if (!StringUtil.isBlank(info.getAttIds())) {
				bui.append(info.getAttIds());
				bui.append(",");
			}
		}
		if (bui.length() > 0) {
			if (bui.lastIndexOf(",") == bui.length() - 1) {
				bui.delete(bui.length() - 1, bui.length());
			}
		} else {
			return null;
		}
		List<ContentAttach> attachList = contentAttachManager.findByIds(bui
				.toString());
		
		File file = new File(this.compressFiles(attachList, title));
		title += ".zip";
		String attName = new String(title.getBytes("GBK"), "ISO8859-1");
		// attName = URLDecoder.decode(attName);// .decode(attName,"UTF-8");
		getResponse().setContentType("application/octet-stream;charset=GBK");
		getResponse().setHeader("Content-Disposition",
				"attachment; filename= " + attName);
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = this.getResponse().getOutputStream();
			inputStream = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int x = 0;
			while ((x = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes);
			}
			// out.write("<script>window.close();</script>");
			// out.flush();
		} catch (IOException ioe) {
			logger.error(ioe);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
				inputStream = null;
			}
			if (outputStream != null) {

				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
				outputStream = null;
			}
			// if(out != null){
			// out.flush();
			// out.close();
			// out = null;
			// }
			file.delete();

		}
		return null;
	}

	private String compressFiles(List<ContentAttach> attachList, String title)
			throws IOException {
		// 创建压缩的文件 以名称加时间为主
		String zipFileName = "数据下载" + System.currentTimeMillis() + ".zip";
		String realPath = ServletActionContext.getServletContext().getRealPath(
				"/");
		String path = realPath + "\\static\\temp\\" + title + "\\";
		File zipFile = new File(realPath + "\\static\\temp\\" + zipFileName);
		if (!zipFile.exists()) {
			zipFile.createNewFile();
		}
		File pFile = new File(path);
		if (!pFile.exists()) {
			pFile.mkdirs();
		}

		//
		ContentAttach attach = null;
		for (int i = 0; i < attachList.size(); i++) {
			attach = attachList.get(i);

			if (attach == null || StringUtil.isBlank(attach.getAttName())
					|| StringUtil.isBlank(attach.getLink())) {
				logger
						.error("msg = attach.getAttName() is null | attach.getAttName() = "
								+ attach.getAttName());
				continue;
			}
			File file = new File(attach.getLink());
			if (!file.exists()) {
				logger.error("attach.getLink() is not exists"
						+ attach.getAttName());
				continue;
			}
			String filelink = path + attach.getAttName();

			FileUtil.copyfile(file, filelink, true);

			// 更新下载数据
			this.contentInfoManager.updateAddAttachNumber(attach.getContent()
					.getIdentifier());
		}
		ZipFileUtil.compress(zipFile, path);
		FileUtil.deletedir(pFile);

		return zipFile.getAbsolutePath();
	}

	/**
	 * 判断当前用户是否有权限下载附件
	 * 
	 * @return
	 */
	public String isPermission() throws BusinessException {

		Writer writer = null;
		int outWriter = 0; // 1 表示 true 0 表示false
		try {
			writer = getResponse().getWriter();
			if (getUserSessionInfo().getRoleLevel().intValue() == 0) {
				writer.write("1");
				writer.flush();
				return null;
			}
			// int intsctId = getUserSessionInfo().getRid();
			// 附件信息
			String sctId = getRequest().getParameter("ctId");
			if (StringUtil.isBlank(sctId) || !StringUtil.isNumeric(sctId)) {
				writer.write(0);
				writer.flush();
				return null;
			}

			if (getUserSessionInfo().getRoleLevel().intValue() != 0) {
				int rid = this.getUserSessionInfo().getRid();
				// 附件下载的权限值
				String pcenter = this.permissionManager.findPcenterByRid(rid,
						PermissionMark.CenterAttachDownload_permission
								.getValue());
				if (!StringUtil.isBlank(pcenter)) {
					ContentAttach attach = this.contentAttachManager
							.get(Integer.valueOf(sctId));
					if (attach == null
							|| StringUtil.isBlank(attach.getAttName())
							|| StringUtil.isBlank(attach.getLink())) {
						return null;
					}
					int ctid = attach.getContent().getCtId();

					switch (ctid) {
					case IContentInfoManager.TechnicalReport:
						if (pcenter.indexOf("boxTechnicalReport") != -1) {
							outWriter = 1;
						}
						break;
					case IContentInfoManager.DataDownload:
						if (pcenter.indexOf("boxDataDownload") != -1) {
							outWriter = 1;
						}
						break;
					default:
						outWriter = 1;
						break;
					}
				}
			} else {
				outWriter = 1;
			}
			writer.write("" + outWriter);
			writer.flush();
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
				writer = null;
			}
		}

		return null;
	}

	/**
	 * 判断当前用户是否有权限下载附件
	 * 
	 * @return
	 */
	public String isDataDownPermission() throws BusinessException {

		Writer writer = null;
		int outWriter = 0; // 1 表示 true 0 表示false
		try {
			writer = getResponse().getWriter();
			if (getUserSessionInfo().getRoleLevel().intValue() == 0) {
				writer.write("1");
				writer.flush();
				return null;
			}

			if (getUserSessionInfo().getRoleLevel().intValue() != 0) {
				int rid = this.getUserSessionInfo().getRid();
				// 附件下载的权限值
				String pcenter = this.permissionManager.findPcenterByRid(rid,
						PermissionMark.CenterAttachDownload_permission
								.getValue());
				if (!StringUtil.isBlank(pcenter)) {
					if (pcenter.indexOf("boxDataDownload") != -1) {
						outWriter = 1;
					}
				}
			}
			writer.write("" + outWriter);
			writer.flush();
		} catch (IOException e) {
			logger.error(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
				writer = null;
			}
		}

		return null;
	}
}
