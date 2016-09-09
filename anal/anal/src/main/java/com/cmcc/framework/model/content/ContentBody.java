/* 
 * 文件名称：ContentBody.java
 * 
 * 创建时间：2009-2-20 上午09:34:12
 *
 * 原始作者：曹巍
 */
package com.cmcc.framework.model.content;

import java.io.UnsupportedEncodingException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 功能描述：发布内容数据表信息对应的Java类
 * 
 * @author 曹巍 创建时间：2009-2-20 上午09:34:12
 * 
 */
@Entity
@Table(name = "gweb_content_body")
public class ContentBody implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6618483151612527608L;

	/**
	 * ID
	 */

	private Integer identifier;

	/**
	 * 
	 */

	private byte[] body;

	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof ContentBody))
			return false;

		return super.equals(o);
	}

	@Override
	public String toString() {
		ToStringBuilder tostring = new ToStringBuilder(this);
		try {
			tostring.append("body", new String(this.getBody(), "gb2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tostring.toString();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "conbodyid")	
	public Integer getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	/**
	 * 获取内容主体
	 * 
	 * @return 内容主体
	 */
	@Column(name = "conbody", length = 100000)
	public byte[] getBody() {
		return body;
	}

	/**
	 * 设置内容主体
	 * 
	 * @param 内容主体
	 */
	public void setBody(byte[] body) {
		this.body = body;
	}

	public void setBody(String stringbody) {
		if (stringbody != null && !stringbody.equals("")) {
			body = stringbody.getBytes();
		}
	}

}