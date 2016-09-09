/*
 * 文件名： Pilot.java
 * 
 * 创建日期： 2009-2-27
 *
 * Copyright(C) 2009, by gaojl.
 *
 * 原始作者: <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 */
package com.cmcc.framework.model.analysis;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cmcc.common.model.PersistentObject;

/**
 * 试点信息对应的Java类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-6
 */
@Entity
@Table(name = "option_db")
public class Option extends PersistentObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8553604521575751716L;

	/** id */
	private Integer identifier;
	/**
	 * 试点代码
	 */
	private Integer code;
	/**
	 * 试点年份
	 */
	private Integer year;
	/**
	 * 经度；经线
	 */
	private String name;
	/**
	 * 纬度；纬线
	 */
	private String identification;

	public Option() {
		super();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Option)) {
			return false;
		}
		return false;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	@Column(name = "pcode")
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	@Column(name = "year")
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "identification")
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

}
