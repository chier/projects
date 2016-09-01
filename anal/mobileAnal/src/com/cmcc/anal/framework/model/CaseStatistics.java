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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cmcc.anal.common.model.PersistentObject;
import com.cmcc.anal.framework.model.content.ContentBody;

/**
 * 试点类别上报的 model类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-6
 */
@Entity
@Table(name = "casestatistics_db")
public class CaseStatistics extends PersistentObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7504747880710504864L;

	/** id */
	private Integer identifier;

	/**
	 * 上报的类型。1：居民健康类。2：环境调查类
	 */
	private Integer ctype;

	// private Integer pid;/

	private Pilot pilot;

	private Integer cnumber;

	private Date createTime;// 创建管理员时间

	private Date dmlTime;// 删除管理员时间

	private Short dmlflog;// 管理员操作记录

	public CaseStatistics() {
		super();
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
	@Column(name = "cid")
	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	@Column(name = "cnumber")
	public Integer getCnumber() {
		return cnumber;
	}

	public void setCnumber(Integer cnumber) {
		this.cnumber = cnumber;
	}

	@Column(name = "ctype")
	public Integer getCtype() {
		return ctype;
	}

	public void setCtype(Integer ctype) {
		this.ctype = ctype;
	}

	@OneToOne(targetEntity = Pilot.class, cascade = { CascadeType.REFRESH})
	@JoinColumn(name = "pid")
	public Pilot getPilot() {
		return pilot;
	}

	public void setPilot(Pilot pilot) {
		this.pilot = pilot;
	}

	// @Column(name = "pid")
	// public Integer getPid() {
	// return pid;
	// }
	//
	// public void setPid(Integer pid) {
	// this.pid = pid;
	// }

}
