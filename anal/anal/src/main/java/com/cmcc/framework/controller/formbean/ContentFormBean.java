/* 
 * 文件名称：ContentFormBean.java
 * 
 * 创建时间：2009-3-3
 *
 * 原始作者：曹巍
 */
package com.cmcc.framework.controller.formbean;

import java.util.Date;

/**
 * 
 * 内容信息formbean
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-3-3
 */
public class ContentFormBean {
	/**
	 * ID
	 */
	private Integer identifier;

	/**
	 * 内容标题
	 */
	private String title;
	/**
	 * 内容描述
	 */
	private String desc;
	/**
	 * 内容作者
	 */
	private String author;

	// /**
	// * 内容发布日期
	// */
	// private Date releasedate;

	/**
	 * 内容来源
	 */
	private String contentSource;

	/**
	 * 内容来源链接地址
	 */
	private String contentSourceLink;

	/**
	 * 获取ID
	 * 
	 * @return id ID
	 */
	public Integer getIdentifier() {
		return identifier;
	}
	/**
	 * 设置ID
	 * 
	 * @param identifier
	 *            ID
	 */
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	/**
	 * 获取标题
	 * 
	 * @return 标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */

	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取描述
	 * 
	 * @return 描述
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * 设置描述
	 * 
	 * @param desc
	 *            描述
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * 获取作者
	 * 
	 * @return 作者
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置作者
	 * 
	 * @param author
	 *            作者
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	// /**
	// * 获取发布日期
	// *
	// * @return 发布日期
	// */
	// public Date getReleasedate() {
	// return releasedate;
	// }
	// /**
	// * 设置作者 发布日期
	// *
	// * @param releasedate
	// * 发布日期
	// */
	// public void setReleasedate(Date releasedate) {
	// this.releasedate = releasedate;
	// }
	/**
	 * 获取内容来源
	 * 
	 * @return
	 */
	public String getContentSource() {
		return contentSource;
	}
	/**
	 * 设置作者 内容来源
	 * 
	 * @param contentSource
	 *            内容来源
	 */
	public void setContentSource(String contentSource) {
		this.contentSource = contentSource;
	}
	/**
	 * 获取链接地址
	 * 
	 * @return 链接地址
	 */
	public String getContentSourceLink() {
		return contentSourceLink;
	}
	/**
	 * 设置链接地址
	 * 
	 * @param contentSourceLink
	 *            链接地址
	 */
	public void setContentSourceLink(String contentSourceLink) {
		this.contentSourceLink = contentSourceLink;
	}

}
