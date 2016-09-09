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
@Table(name = "pilot_db")
public class Pilot extends PersistentObject {

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
	private String lon;
	/**
	 * 纬度；纬线
	 */
	private String lat;
	/**
	 * 试点人口
	 */
	private Integer population;
	/**
	 * 试点面积
	 */
	private Integer acreage;
	/**
	 * 试点主产业
	 */
	private String mainIndustry;
	/**
	 * 试点GDP
	 */
	private Float gdp;

	/** 试点名称 */
	private String pname;

	/** 试点地址 */
	private String paddress;

	private Date createTime;// 创建管理员时间

	private Date dmlTime;// 删除管理员时间

	private Short dmlflog;// 管理员操作记录

	public Pilot() {
		super();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Pilot)) {
			return false;
		}
		if (super.equals(o)) {
			if ((this.pname != null)) {
				return this.pname.equals(((Pilot) o).getPname());
			}
		}
		return false;
	}

	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "dmlFlog")
	public Short getDmlflog() {
		return dmlflog;
	}

	public void setDmlflog(Short dmlflog) {
		this.dmlflog = dmlflog;
	}

	@Column(name = "dmlTime")
	public Date getDmlTime() {
		return dmlTime;
	}

	public void setDmlTime(Date dmlTime) {
		this.dmlTime = dmlTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "pid")
	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	@Column(name = "paddress")
	public String getPaddress() {
		return paddress;
	}

	public void setPaddress(String paddress) {
		this.paddress = paddress;
	}

	/**
	 * 获得用户登录Id
	 * 
	 * @return 用户登录Id
	 * 
	 */
	@Column(name = "pname", length = 32, nullable = true)
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
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

	@Column(name = "population")
	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}

	@Column(name = "acreage")
	public Integer getAcreage() {
		return acreage;
	}

	public void setAcreage(Integer acreage) {
		this.acreage = acreage;
	}

	@Column(name = "mainIndustry")
	public String getMainIndustry() {
		return mainIndustry;
	}

	public void setMainIndustry(String mainIndustry) {
		this.mainIndustry = mainIndustry;
	}

	@Column(name = "gdp")
	public Float getGdp() {
		return gdp;
	}

	public void setGdp(Float gdp) {
		this.gdp = gdp;
	}
}
