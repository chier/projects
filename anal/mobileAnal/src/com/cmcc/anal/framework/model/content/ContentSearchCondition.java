/*
 * 文件名： ContentSearchCondition.java
 * 
 * 创建日期： 2009-3-11
 *
 * Copyright(C) 2009, by caowei.
 *
 * 原始作者: 曹巍
 */
package com.cmcc.anal.framework.model.content;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cmcc.anal.common.persistence.support.SearchCondition;


/**
 * 
 * 内容信息查询条件类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.2 $
 * 
 * @since Mar 11, 2009
 */
public class ContentSearchCondition extends SearchCondition {
	@Override
	public String toString() {
		ToStringBuilder tostring = new ToStringBuilder(this);
		tostring.append(super.toString());
		if (this.getEid() != null) {
			tostring.append("Eid", this.getEid());
		}
		if (this.getContenttype() != null) {
			tostring.append("contype", this.getContenttype());
		}
		if (this.getState() != null) {
			tostring.append("state", this.getState());
		}
		return tostring.toString();
	}

	/**
	 * 企业ID
	 */
	private Integer eid;

	/**
	 * 内容状态 发送状态 值为4
	 */
	private Integer state;

	/**
	 * 内容状态 保存状态 值为 0
	 */
	private Integer stateSave;

	/**
	 * 内容类型
	 */
	private Integer contenttype;

	/**
	 * 类内容类型
	 */
	private Integer ctId;

	private String ids;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	/**
	 * 获取企业ID
	 * 
	 * @return
	 */
	public Integer getEid() {
		return eid;
	}

	/**
	 * 设置企业ID
	 * 
	 * @param eid
	 */
	public void setEid(Integer eid) {
		this.eid = eid;
	}

	/**
	 * 获取内容状态
	 * 
	 * @return
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * 设置内容状态
	 * 
	 * @param state
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * 获取内容类型
	 * 
	 * @return
	 */
	public Integer getContenttype() {
		return contenttype;
	}

	/**
	 * 设置内容类型
	 * 
	 * @param contenttype
	 */
	public void setContenttype(Integer contenttype) {
		this.contenttype = contenttype;
	}

	/**
	 * 设置内容保存状态
	 * 
	 * @return
	 */
	public Integer getStateSave() {
		return stateSave;
	}

	/**
	 * 获取内容保存状态
	 * 
	 * @param stateSave
	 */
	public void setStateSave(Integer stateSave) {
		this.stateSave = stateSave;
	}

	public Integer getCtId() {
		return ctId;
	}

	public void setCtId(Integer ctId) {
		this.ctId = ctId;
	}

}