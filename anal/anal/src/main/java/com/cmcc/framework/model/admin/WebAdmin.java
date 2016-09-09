/*
 * 文件名： WebAdmin.java
 * 
 * 创建日期： 2009-2-27
 *
 * Copyright(C) 2009, by gaojl.
 *
 * 原始作者: <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 */
package com.cmcc.framework.model.admin;

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
 * 管理员用户信息对应的Java类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-6
 */
@Entity
/* 表示users表的唯一约束是loginid 这个名字对应数据库的字段名 */
@Table(name = "gweb_admin")
public class WebAdmin extends PersistentObject {

	private static final long serialVersionUID = 5540260999663878275L;

	/** id */
	private Integer identifier;

	/** 用户登录用户名 */
	private String admin;

	/** 用户级别 0超级管理员，1普通管理员 */
	private Integer level;

	/** 登录密码 */
	private String password;

	private Integer count;// 管理员登陆错误次数

	private Date createTime;// 创建管理员时间

	private Date dmlTime;// 删除管理员时间

	private Short dmlflog;// 管理员操作记录

	private Integer roleId;// 角色 id

	// private Integer deptId;// 部门id

	/**
	 * 0 : 表示工程 1 : 单独设置 并且 管理员不管理新增部门 2 : 单独设置 管理员管理新增部门
	 */
	// private short selectWorkFlag;// 全选标志
	private String modelIds;

	private String modelNames;


	public WebAdmin() {
		super();
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof WebAdmin)) {
			return false;
		}
		if (super.equals(o)) {
			if ((this.admin != null)) {
				return this.admin.equals(((WebAdmin) o).getAdmin());
			}
		}
		return false;
	}

	/**
	 * 获得用户登录Id
	 * 
	 * @return 用户登录Id
	 * 
	 */
	@Column(name = "admin_name", length = 32, nullable = true)
	public String getAdmin() {
		return admin;
	}

	/**
	 * 设置用户登录Id
	 * 
	 * @param admin
	 *            用户登录Id
	 */
	public void setAdmin(String admin) {
		this.admin = admin;
	}

	/**
	 * 获得用户的登录密码
	 * 
	 * @return 用户的登录密码
	 * 
	 */
	@Column(name = "md5pwd", length = 32, nullable = true)
	public String getPassword() {
		return password;
	}

	/**
	 * 设置用户的登录密码
	 * 
	 * @param password
	 *            用户的登录密码
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * /** 获得用户identifier
	 * 
	 * @return 用户identifier
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "admin_id")
	public Integer getIdentifier() {
		return identifier;
	}

	/**
	 * 设置用户id
	 * 
	 * @param identifier
	 *            用户id
	 */
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	/**
	 * 获得用户级别level
	 * 
	 * @return 用户level
	 * 
	 */
	@Column(name = "level", length = 1, nullable = true)
	public Integer getLevel() {
		return level;
	}

	/**
	 * 设置用户的级别
	 * 
	 * @param level
	 *            用户level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "err_login_count")
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Column(name = "createtime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "dmltime")
	public Date getDmlTime() {
		return dmlTime;
	}

	public void setDmlTime(Date dmlTime) {
		this.dmlTime = dmlTime;
	}

	// @Column(name = "select_work_flag")
	// public short getSelectWorkFlag() {
	// return selectWorkFlag;
	// }
	//
	// public void setSelectWorkFlag(short selectWorkFlag) {
	// this.selectWorkFlag = selectWorkFlag;
	// }

	@Column(name = "dmlflog")
	public Short getDmlflog() {
		return dmlflog;
	}

	public void setDmlflog(Short dmlflog) {
		this.dmlflog = dmlflog;
	}

	@Column(name = "rid")
	public Integer getRoleId() {
	return roleId;
	}
	
	public void setRoleId(Integer roleId) {
	this.roleId = roleId;
	}
	//
	// @Column(name = "deptId")
	// public Integer getDeptId() {
	// return deptId;
	// }

	// public void setDeptId(Integer deptId) {
	// this.deptId = deptId;
	// }

	@Column(name = "modelIds")
	public String getModelIds() {
		return modelIds;
	}

	public void setModelIds(String modelIds) {
		this.modelIds = modelIds;
	}

	// public int getRid(){

	// }
	

	@Transient
	public String getModelNames() {
		return modelNames;
	}

	public void setModelNames(String modelNames) {
		this.modelNames = modelNames;
	}


	// @Transient

}
