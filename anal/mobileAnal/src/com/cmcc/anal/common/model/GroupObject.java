/*
 * 文件名： GroupObject.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.anal.common.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 具有组关系的持久对象基类
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
@MappedSuperclass
public abstract class GroupObject extends CommonObject {

	private static final long serialVersionUID = -2870523663465704080L;

	/** 当前组的父级组的Id */
	protected Integer parentId;

//	/** 组的编码 */
//	protected String code;

	/**
	 * 当前组是否是叶子节点
	 * 
	 * 0：不是叶子 1：是叶子
	 * 
	 */
	protected Integer isLeaf = new Integer(1);

	/**
	 * 获得当前组的父级组的Id
	 * 
	 * @return 当前组的父级组的Id
	 * 
	 */
	
	@Transient
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 设置当前组的父级组的Id
	 * 
	 * @param parentGroupId
	 *            当前组的父级组的Id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	/**
	 * 获得当前组是否是叶子节点
	 * 
	 * @return 当前组是否是叶子节点0：不是叶子 1：是叶子
	 * 
	 */
	@Transient
	public Integer getIsLeaf() {
		return isLeaf;
	}

	/**
	 * 设置当前组是否是叶子结点0：不是叶子 1：是叶子
	 * 
	 * @param isLeaf
	 */
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * 获得当前组编码
	 * 
	 * @return 当前组编码。
	 * 
	 */
//	@Transient
//	public String getCode() {
//		return code;
//	}

	/**
	 * 设置当前组编码
	 * 
	 * @param code
	 *            当前组编码。
	 */
//	public void setCode(String code) {
//		this.code = code;
//	}

	public String toString() {
		ToStringBuilder strBuilder = new ToStringBuilder(this);
		strBuilder.append(super.toString());
		strBuilder.append("parentId", this.getParentId());
		strBuilder.append("isLeaf", this.getIsLeaf());
		// strBuilder.append("code", this.getCode());
		return strBuilder.toString();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof GroupObject)) {
			return false;
		// if (super.equals(o)) {
		// if (this.code != null) {
		// return this.code.equals(((GroupObject) o).getCode());
		// }
		// else {
		// return (((GroupObject) o).getCode() == null);
		// }
		// }
		} else {
			return false;
		}
	}
}
