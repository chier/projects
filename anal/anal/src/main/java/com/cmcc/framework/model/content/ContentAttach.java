/* 
 * 文件名称：ContentAttach.java
 * 
 * 创建时间：2009-2-20 上午09:34:12
 *
 * 原始作者：曹巍
 */
package com.cmcc.framework.model.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 功能描述：发布内容附件信息对应的Java类
 * 
 * @author 曹巍 创建时间：2009-2-20 上午09:34:12
 */
@Entity
@Table(name = "gweb_content_attach")
public class ContentAttach implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3469999343654577980L;

	/**
	 * ID
	 */

	private Integer identifier;

	private ContentInfo content;

	/**
	 * 名称
	 */
	private String attName;

	/**
	 * 有效标识
	 */
	private Integer availflg;

	/**
	 * 附件存放路径 (服务器相对路径) /static/contentManager/files
	 */
	private String link;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof ContentAttach))
			return false;

		return super.equals(o);
	}

	@Override
	public String toString() {
		ToStringBuilder tostring = new ToStringBuilder(this);
		tostring.append(super.toString());
		if (this.getAttName() != null) {
			tostring.append("attname", this.getAttName());
		}
		if (this.getLink() != null) {
			tostring.append("link", this.getLink());
		}
		return tostring.toString();
	}

	@ManyToOne(targetEntity = ContentInfo.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "contentid")
	public ContentInfo getContent() {
		return content;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "conattid")
	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	public void setContent(ContentInfo content) {
		this.content = content;
	}

	/**
	 * 获取文件存放路径
	 * 
	 * @return 存放路径
	 */
	@Column(name = "conattlink", length = 256)
	public String getLink() {
		return link;
	}

	/**
	 * 
	 * @param link
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * 获取有效标识
	 * 
	 * @return 有效标识
	 */
	@Column(name = "conattavailflg", length = 1)
	public Integer getAvailflg() {
		return availflg;
	}

	/**
	 * 设置有效标识
	 * 
	 * @param availflg
	 */
	public void setAvailflg(Integer availflg) {
		this.availflg = availflg;
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	@Column(name = "conattname", length = 256)
	public String getAttName() {
		return attName;
	}

	/**
	 * 设置有效标识
	 * 
	 * @param availflg
	 */
	public void setAttName(String attName) {
		this.attName = attName;
	}

}
