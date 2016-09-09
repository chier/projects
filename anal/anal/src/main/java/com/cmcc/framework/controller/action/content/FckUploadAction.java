/*
 * FckUploadAction.java 
 * 2009-4-1
 * author caowei
 */
package com.cmcc.framework.controller.action.content;

import java.io.File;
import com.cmcc.common.util.file.FileUtil;
import com.cmcc.common.util.StringUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import com.cmcc.common.Global;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cmcc.common.util.PortraitMedia;

import com.cmcc.common.controller.action.CommonAction;

/**
 * FCK上传文件类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since Apr 1, 2009
 */
public class FckUploadAction extends CommonAction {

	private static final long serialVersionUID = -6630699062603755926L;
	private File NewFile;
	private String NewFileFileName;
	private String NewFileContentType;

	public File getNewFile() {
		return NewFile;
	}
	public void setNewFile(File newFile) {
		NewFile = newFile;
	}
	public String getNewFileFileName() {
		return NewFileFileName;
	}
	public void setNewFileFileName(String newFileFileName) {
		NewFileFileName = newFileFileName;
	}
	public String getNewFileContentType() {
		return NewFileContentType;
	}
	public void setNewFileContentType(String newFileContentType) {
		NewFileContentType = newFileContentType;
	}
	public String execute() {
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String retVal = "0";
		String newName = "";
		String fileUrl = "";
		String errorMessage = "";
		String cmcc = "";
		PortraitMedia port = new PortraitMedia();
		String url = "";
		String typeStr = request.getParameter("Type");
		boolean errorflag = false;
		byte[] temp = new byte[1024];
		FileInputStream input = null;
		String filetype = null;
		if (typeStr.equals("Image")) {
			try {
				input = new FileInputStream(NewFile);
				input.read(temp, 0, 1024);
				filetype = FileUtil.getExt(temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (filetype != null) {
				if (!filetype.equals("jpg") && !filetype.equals("gif")
						&& !filetype.equals("png") && !filetype.equals("bmp")) {
					retVal = "202";
					errorMessage = "";// 文件类型不对
				} else {
					if (NewFile.length() <= Global.CONTENT_ATTACH_MAXSIZE) {
						try {

							int random = this.getRandom(Global.mediaMap.size());
							url = Global.mediaMap.get(random);
							cmcc = port.uploadContentImage(NewFile, url
									+ "/settabimage.aspx");

						} catch (Exception ex) {
							errorflag = true;
						}
						if (errorflag) {
							for (int i = 0; i < Global.mediaMap.size(); i++) {
								url = Global.mediaMap.get(i);
								try {
									cmcc = port.uploadContentImage(NewFile, url
											+ "/settabimage.aspx");
									break;
								} catch (Exception ex1) {
									continue;
								}
							}
						}
						if (cmcc != null && !cmcc.equals("")) {

							cmcc = StringUtil.urlEncode(cmcc, "utf-8");
							String oneg = "";
							if (!this.getContext().equals("/")) {
								oneg = this.getContext();
							}
							fileUrl = oneg + "/servlet/GetContentImageInfo?f="
									+ cmcc;
							newName = NewFileFileName;
						} else {
							retVal = "203";
						}
					} else {
						retVal = "1";
						errorMessage = "图片大小不能超过"
								+ StringUtil
										.changeCapacities(Global.CONTENT_ATTACH_MAXSIZE);
					}

				}
			} else {
				retVal = "203";// 无参数filetype
			}

		}

		try {
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.OnUploadCompleted(" + retVal + ",'"
					+ fileUrl + "','" + newName + "','" + errorMessage + "');");
			out.println("</script>");
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NewFile.delete();
		return null;
	}
	private int getRandom(int size) {
		Random rand = new Random();
		return Math.abs(rand.nextInt()) % size;
	}

}
