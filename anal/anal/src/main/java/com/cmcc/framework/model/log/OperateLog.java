/*
 * 文件名： OperateLog.java
 * 
 * 创建日期： 2009-3-23
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.framework.model.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cmcc.common.model.PersistentObject;

/**
 * 
 * 管理员操作日志实体
 * 
 * @author <a href="mailto:sun128837@126.com">conntsing</a>
 * 
 * @version $Revision: 1.2 $
 * 
 * @since 2009-3-23
 */
@Entity
@Table(name = "gweb_operlog")
public class OperateLog extends PersistentObject {
	private static final long serialVersionUID = 6009292868923410895L;
	/**
	 * ID
	 */
	private Integer identifier;
	/*
	 *企业id 
	 */
	private Integer eid;
	/*
	 * 管理员id
	 */
	private Integer adminId;
	/*
	 * 操作时间
	 */
	private Date operateTime;
	/*
	 * 管理员名称
	 */
	private String adminName;
	/*
	 * 企业编号
	 */
	private String c0;
	/*
	 * 企业简称
	 */
	private String shortName;
	/*
	 * 操作描述
	 */
	private String operateDesc;
	/*
	 * 被操作员工名称
	 */
	private String employeeName;
	/*
	 * 被操作部门名称
	 */
	private String deptName;
	/*
	 * 操作标识
	 */
	private String operateMark;
	/*
	 *操作记录数（数据条数） 
	 */
	private Integer operateRecords;
	
	private String iocUrl;

	/**
	 * @return 返回 eid。
	 */
	@Column(name = "eid")
	public Integer getEid() {
		return eid;
	}

	/**
	 * @param eid
	 *            要设置的 eid。
	 */
	public void setEid(Integer eid) {
		this.eid = eid;
	}

	/**
	 * @return 返回 adminId。
	 */
	@Column(name = "admin_id")
	public Integer getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId
	 *            要设置的 adminId。
	 */
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	/**
	 * 
	 * @return 返回operateMark
	 */
	@Column(name = "operate_mark")
	public String getOperateMark() {
		return operateMark;
	}
 
	public void setOperateMark(String operateMark) {
		this.operateMark = operateMark;
	}
	/**
	 * 
	 * @return  返回operateRecords
	 */
	@Column(name = "operate_records")
	public Integer getOperateRecords() {
		return operateRecords;
	}

	public void setOperateRecords(Integer operateRecords) {
		this.operateRecords = operateRecords;
	}

	/**
	 * @return 返回 operateTime。
	 */
	@Column(name = "operate_time")
	public Date getOperateTime() {
		return operateTime;
	}

	/**
	 * @param operateTime
	 *            要设置的 operateTime。
	 */
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	/**
	 * @return 返回 adminName。
	 */
	@Column(name = "admin_name")
	public String getAdminName() {
		return adminName;
	}

	/**
	 * @param adminName
	 *            要设置的 adminName。
	 */
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	/**
	 * @return 返回 c0。
	 */
	@Column(name = "c0")
	public String getC0() {
		return c0;
	}

	/**
	 * @param c0
	 *            要设置的 c0。
	 */
	public void setC0(String c0) {
		this.c0 = c0;
	}

	/**
	 * @return 返回 shortName。
	 */
	@Column(name = "short_name")
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param shortName
	 *            要设置的 shortName。
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	
	/**
	 *
	 *
	 * @return 返回 operateType
	 */
	@Column(name = "operate_type")
	public String getOperateDesc() {
	
		return operateDesc;
	}

	/**
	 *	要设置的operateType
	 *
	 * @param operateType 
	 */
	public void setOperateDesc(String operateDesc) {
	
		this.operateDesc = operateDesc;
	}

	/**
	 * @return 返回 employeeName。
	 */
	@Column(name = "employee_name")
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * @param employeeName
	 *            要设置的 employeeName。
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * @return 返回 deptName。
	 */
	@Column(name = "dept_name")
	public String getDeptName() {
		return deptName;
	}

	/**
	 * @param deptName
	 *            要设置的 deptName。
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "aiid")
	public Integer getIdentifier() {

		return identifier;
	}

	public void setIdentifier(Integer identifier) {
	
		this.identifier = identifier;
	}

	@Transient
	public String getIocUrl() {
		return iocUrl;
	}

	public void setIocUrl(String iocUrl) {
		this.iocUrl = iocUrl;
	}
}
