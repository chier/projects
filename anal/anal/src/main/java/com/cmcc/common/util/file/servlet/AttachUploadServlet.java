package com.cmcc.common.util.file.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.Global;
import com.cmcc.framework.business.interfaces.content.IContentAttachManager;
import com.cmcc.framework.model.content.ContentAttach;
import com.cmcc.framework.model.content.ContentInfo;

/**
 * Servlet implementation class upload
 */
public class AttachUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String CONTENT_TYPE = "text/html; charset=GBK";

	private IContentAttachManager contentAttachManager = null;

	private static final Log logger = LogFactory
			.getLog(AttachUploadServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AttachUploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() {
		contentAttachManager = (IContentAttachManager) Global._ctx
				.getBean("contentAttachManager");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 判断该请求形式是否为MULTIPART/FORM-DATA
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		// logger.info("isMultipart: " + isMultipart);
		if (!isMultipart) {
			return;
		}

		// 准备表示上传表单数据的工厂，即 DiskFileItemFactory
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 指定该上传表单数据最大能够使用的内存，如果超过，会将该数据直接转移到硬盘上存储
		factory.setSizeThreshold(8 * 1024);
		// 指定使用硬盘存储表单数据的临时目录
		factory.setRepository(new File("c:\\"));

		// 根据刚才的工厂创建表示表单数据的对象，即 ServletFileUpload
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 指定对上传数据大小的限制，-1为不限制（单位，字节）
		upload.setSizeMax(Global.CONTENT_ATTACH_MAXSIZE);
		Map map = new HashMap();
		
		// 解析请求
		try {
			List /* FileItem */items = upload.parseRequest(request);
			// 迭代处理每一个表单域，每一个表单域使用FileItem类来表示
			logger.info("items.size(): " + items.size());
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				logger.info("--------------------------------------------");
				FileItem item = (FileItem) iter.next();
				// 如果是普通的表单域
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString();
					logger.info("name is: " + name);
					logger.info("value is: "
							+ new String(value.getBytes("ISO-8859-1"), "GBK"));
				} else { // 如果是type=file的表单域
					//数据库  pojo
					ContentAttach attach = new ContentAttach();
					
					String fieldName = item.getFieldName();
					String fileName = item.getName();
					String contentType = item.getContentType();
					boolean isInMemory = item.isInMemory();
					long sizeInBytes = item.getSize();
					logger.info("fieldName is: " + fieldName);
					logger.info("fileName is: " + fileName);
					logger.info("contentType is: " + contentType);
					logger.info("isInMemory is: " + isInMemory);
					logger.info("sizeInBytes is: " + sizeInBytes);

					// 去除原始的路径信息，只保留文件名
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
						logger.info(fileName);
					}
					if (fileName.equals("")) {
						logger.info("文件为空！");
						continue;
					}
					String suffix = fileName.substring(fileName.lastIndexOf(".") );
					attach.setAttName(fileName);
					// 创建一个新文件以存储上传数据
					// 注意该目录必须存在，否则须使用代码或使用手工创建
					String realPath = this.getServletContext().getRealPath("/");
					File uploadedFile = new File(realPath
							+ Global.staticNodeFilePath + "\\" + System.currentTimeMillis() + suffix);
					// 将数据写入文件
					item.write(uploadedFile);
					attach.setLink(uploadedFile.getAbsolutePath());
					logger.info("文件已上传至：" + uploadedFile.getAbsolutePath());
					attach.setAvailflg(1);
					attach.setContent(new ContentInfo(0));
					this.contentAttachManager.saveOrUpdate(attach);
					logger.info("id = " + attach.getIdentifier());
					map.put("attIds", attach.getIdentifier());
				}
			}
			PrintWriter writer = response.getWriter();
			map.put("success", true);
			JSONObject object = JSONObject.fromObject(map);
			writer.write(object.toString());
			if (writer != null) {
				writer.flush();
				writer.close();
				writer = null;
			}
		} catch (FileUploadException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			
		}

	}

}
