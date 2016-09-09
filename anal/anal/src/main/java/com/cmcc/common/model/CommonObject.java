/*
 * 文件名： CommonObject.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 具有名字的持久对象基类
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
@MappedSuperclass
public abstract class CommonObject extends PersistentObject {

	private static final long serialVersionUID = -2808699280046144990L;

	/** 当前对象的名字 */
	protected String name;

	/**
	 * 获得当前对象的名字
	 * 
	 * @return 当前对象的名字
	 * 
	 */
	@Transient
	public String getName() {
		return name;
	}

	/**
	 * 设置当前对象的名字
	 * 
	 * @param name
	 *            当前对象的名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		ToStringBuilder strBuilder = new ToStringBuilder(this);
		strBuilder.append(super.toString());
		strBuilder.append("name", this.getName());
		return strBuilder.toString().replaceAll("'", "\"");
	}

	public boolean equals(Object o) {
		if (this == o) {

			return true;
		}
		if (!(o instanceof CommonObject)) {
			return false;
		}
		if (super.equals(o)) {
			if (this.name != null) {
				return this.name.equals(((CommonObject) o).getName());
			}			else {
				return (((CommonObject) o).getName() == null);
			}
		}		else {
			return false;
		}
	}
}
