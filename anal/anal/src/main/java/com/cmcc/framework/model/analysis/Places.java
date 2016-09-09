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
@Table(name = "places_db")
public class Places extends PersistentObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8553604521575751716L;

	/** id */
	private Integer identifier;
	/**
	 * 试点年份
	 */
	private Integer year;
	/**
	 * 试点code
	 */
	private String pilotCode;
	/**
	 * 表 code
	 */
	private String tableCode;
	/**
	 * 污染物类型 option_db 的标识
	 */
	private String optionType;

	/**
	 * 经度字段
	 */
	private String lon;
	/**
	 * 纬度字段
	 */
	private String lat;
	/**
	 * 备注
	 */
	private String remark;

	public Places() {
		super();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Places)) {
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

	@Column(name = "year")
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "pilot_code")
	public String getPilotCode() {
		return pilotCode;
	}

	public void setPilotCode(String pilotCode) {
		this.pilotCode = pilotCode;
	}

	@Column(name = "table_code")
	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	@Column(name = "option_type")
	public String getOptionType() {
		return optionType;
	}

	public void setOptionType(String optionType) {
		this.optionType = optionType;
	}

	@Column(name = "lon")
	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	@Column(name = "lat")
	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
