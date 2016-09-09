/*
 * 文件名： WebAdminFormBean.java
 * 
 * 创建日期： 2009-2-25
 *
 * Copyright(C) 2009, by gaojl.
 *
 * 原始作者: <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 */
package com.cmcc.framework.controller.formbean;
/**
 * 管理员的formBean类
 * 
 * @author <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2009-2-25
 */
public class WebAdminFormBean {

	/** id*/
	private Integer identifier;
	/** 用户登录用户名 */
	private String admin;
	/** 企业编号 */
	private Long code;
	/** 企业简称 */
	private String simpleName;
	/** 企业id*/
	private Integer eid;
	/** 用户级别 0超级管理员，1普通管理员*/
	private Integer level;
	/** 登录密码 */
	private String password;
	
	private Long adminTel;/**管理员手机*/
	private Integer sendFlag ;/**是否发送*/
	private Integer roleId; /** 角色 id*/
	private Integer deptId;//部门id
	
	private String [] modelId;
	
	
	
	public String[] getModelId() {
		return modelId;
	}
	public void setModelId(String[] modelId) {
		this.modelId = modelId;
	}
	public Integer getDeptId() {
		return deptId;
	}
	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Long getAdminTel() {
		return adminTel;
	}
	public void setAdminTel(Long adminTel) {
		this.adminTel = adminTel;
	}
	public Integer getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(Integer sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public Integer getEid() {
		return eid;
	}
	public void setEid(Integer eid) {
		this.eid = eid;
	}
	public Integer getIdentifier() {
		return identifier;
	}
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSimpleName() {
		return simpleName;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
}
