/*
 * 文件名： SearchCondition.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.persistence.support;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 查询条件的基类
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public class SearchCondition implements Serializable{

	/** 查询的字段 */
	protected String field;

	/** 查询字段对应的值 */
	protected Object fieldValue;

	public SearchCondition() {
		super();
	}

	public SearchCondition(String field, Object fieldValue) {
		super();
		this.field = field;
		this.fieldValue = fieldValue;
	}

	/**
	 * 获得要查询的字段名
	 * 
	 * @return 返回要查询的字段名。
	 */
	public String getField() {
		return field;
	}

	/**
	 * 设置要查询的字段名
	 * 
	 * @param field
	 *            要设置的查询的字段名。
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * 获得要查询的字段值
	 * 
	 * @return 返回要查询的字段值。
	 */
	public Object getFieldValue() {
		return fieldValue;
	}

	/**
	 * 设置要查询的字段值
	 * 
	 * @param fieldValue
	 *            要设置的查询的字段值。
	 */
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}

	public String toString() {

		ToStringBuilder strBuilder = new ToStringBuilder(this);
		
		strBuilder.append(super.toString());

		strBuilder.append("field", this.getField());
		strBuilder.append("fieldValue", this.getFieldValue());

		return strBuilder.toString();
	}

}
