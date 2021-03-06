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
package com.cmcc.anal.framework.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cmcc.anal.common.model.PersistentObject;

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

	/** 试点名称 */
	private String pname;

	/** 试点地址 */
	private String paddress;

	private Date createTime;// 创建管理员时间

	private Date dmlTime;// 删除管理员时间

	private Short dmlflog;// 管理员操作记录
	
	private String shartName;// 简称
	
	private String statunitcode;// 行政区划代码
	
	private Integer year; // 年份
	
	private String statunitcodeshort; //行政区划代码简码
	
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
	
	@Column(name = "shortName")
	public String getShartName() {
		return shartName;
	}

	public void setShartName(String shartName) {
		this.shartName = shartName;
	}
	
	@Column(name = "statunitcode")
	public String getStatunitcode() {
		return statunitcode;
	}

	public void setStatunitcode(String statunitcode) {
		this.statunitcode = statunitcode;
	}

	@Column(name = "year")
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "statunitcodeshort")
	public String getStatunitcodeshort() {
		return statunitcodeshort;
	}

	public void setStatunitcodeshort(String statunitcodeshort) {
		this.statunitcodeshort = statunitcodeshort;
	}
	
	

}
