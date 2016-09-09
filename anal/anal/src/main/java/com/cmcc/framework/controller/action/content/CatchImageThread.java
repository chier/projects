/*
 * CatchImageThread.java
 * 远程图片抓取线程类
 * 原始作者 <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 2009-3-19
 */
package com.cmcc.framework.controller.action.content;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.Global;
import com.cmcc.common.util.CatchImageUtil;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.business.interfaces.content.IContentBodyManager;
import com.cmcc.framework.model.content.ContentBody;

/**
 * 
 * 远程图片抓取线程类
 * 
 * 由创建和修改内容信息Action类(CreateContentInfoAction和ModifyContentInfoAction类调用)
 * 在创建或修改内容信息完毕后 调用此类，解析内容信息中所有包含图片信息的字符串 并将字符串中所有远程图片保存到本地 同时
 * 修改内容信息主体中图片路径为本地图片路径
 * 
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since Mar 19, 2009
 */
public class CatchImageThread implements Runnable {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(CatchImageThread.class);

	/**
	 * 内容字符串
	 */
	private String content;
	/**
	 * 内容主体ID
	 */
	private Integer contentBodyId;
	/**
	 * 文件保存的绝对路径
	 */
	private String realFilePath;
	/**
	 * 项目应用上下文
	 */
	private String basePath;
	/**
	 * 文件保存的相对路径
	 */
	private String filePath;

	/**
	 * 获取 应用程序上下文
	 * 
	 * @return 应用程序上下文
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * 设置 应用程序上下文
	 * 
	 * @param basePath
	 *            应用程序上下文
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * 获取 图片保存绝对路径
	 * 
	 * @return 图片保存绝对路径
	 */
	public String getRealFilePath() {
		return realFilePath;
	}

	/**
	 * 设置 图片保存绝对路径
	 * 
	 * @param realFilePath
	 *            图片保存绝对路径
	 */
	public void setRealFilePath(String realFilePath) {
		this.realFilePath = realFilePath;
	}

	/**
	 * 获取 图片保存相对路径
	 * 
	 * @return 图片保存相对路径
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * 设置 图片保存相对路径
	 * 
	 * @param filePath
	 *            图片保存相对路径
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 获取 内容字符串
	 * 
	 * @return 内容字符串
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置 内容字符串
	 * 
	 * @param filePath
	 *            内容字符串
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 获取 内容主体ID
	 * 
	 * @return 内容主体ID
	 */
	public Integer getContentBodyId() {
		return contentBodyId;
	}

	/**
	 * 设置 内容主体ID
	 * 
	 * @param filePath
	 *            内容主体ID
	 */
	public void setContentBodyId(Integer contentBodyId) {
		this.contentBodyId = contentBodyId;
	}

	/**
	 * 线程执行方法
	 * 
	 * 
	 * 通过解析内容编辑界面中用户所填写的新闻内容，获取内容中所有包含图片信息的字符串<img>标签中的src属性，
	 * 并将所有src指向的远程图片保存到应用程序中指定的目录，然后将内容信息所有<img>标签中的src换成本地图片的路径
	 */
	public void run() {
		if (logger.isDebugEnabled()) {
			logger.debug("run() - start");
		}

		IContentBodyManager contentbodymanager = (IContentBodyManager) Global._ctx
				.getBean("contentBodyManager");// 获取内容主体Manager Bean
		String tmpfile;
		CatchImageUtil util = new CatchImageUtil();// 远程抓取图片工具类
		String newcontent = this.getContent();// 替换后的新闻内容
		boolean update = false;// 是否有要替换的远程图片路径TAG
		if (this.getContent() != null) {
			List<String> r = util.getImagePath(this.getContent());// 获取远程图片集合
			String filename = "";
			String newpath = null;
			String fckpath = Global.FCK_USERFILESPATH;
			if (fckpath == null || fckpath.equals("")) {
				fckpath = "";
			}
			// 循环抓取图片，并修改图片src路径
			for (int j = 0; j < r.size(); j++) {
				tmpfile = r.get(j);
				if (tmpfile.indexOf(this.getBasePath()) != -1
						|| tmpfile.indexOf(fckpath) != -1) {
					continue;// 如果图片路径为本地路径 跳过
				}
				if (util.catchPic(r.get(j), this.getRealFilePath())) {
					// 抓取图片
					filename = tmpfile.substring(tmpfile.lastIndexOf("/") + 1,
							tmpfile.length());

					newpath = this.getBasePath() + this.getFilePath()
							+ filename;
					newcontent = StringUtil.replace(newcontent, tmpfile,
							newpath);// 替换图片路径为本地图片路径
					logger.info("filename=(" + filename + "),newpath=("
							+ newpath + ")");
					update = true;// TAG置为TRUE
				}

			}
			if (update) {
				// 如果替换标志位为true 更新内容主体表中对应记录
				ContentBody body = contentbodymanager.get(this
						.getContentBodyId());
				if (body != null) {
					body.setBody(newcontent);
					contentbodymanager.saveOrUpdate(body);

				}
			}

		}

		if (logger.isInfoEnabled()) {
			logger.info("run() - end");
		}
	}// end of run

	/**
	 * 线程开启方法 供其他类调用
	 * 
	 * @param content
	 *            内容主体字符串（要修改的内容字符串）
	 * @param bodyId
	 *            内容主体ID （要修改的内容主体）
	 * @param filepath
	 *            图片保存相对路径 （systemConfig.xml中contentCatchImagePath节点）
	 * @param realPath
	 *            图片保存绝对路径（在Action类中根据图片的相对路径转换而成）
	 * @param base
	 *            应用程序上下文 (Action类中调用this.getBasePath()返回结果例如:
	 *            http://localhost/mop)
	 */
	public void start(String content, Integer bodyId, String filepath,
			String realPath, String base) {
		this.setContent(content);
		this.setContentBodyId(bodyId);
		this.setFilePath(filepath);
		this.setRealFilePath(realPath);
		this.setBasePath(base);
		Thread t = new Thread(this);
		t.start();
	}

}
