/*
 * 文件名： DepartmentSearcher.java
 * 
 * 创建日期： 2009-2-26
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.framework.model.department;

import java.util.Date;

import com.cmcc.common.persistence.support.SearchCondition;

/**
 * 部门查询条件对应的Java类
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-10
 */
public class DepartmentSearcher extends SearchCondition {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3749275906085358064L;

	/**
	 * 仅作为参数传递，不做条件
	 */
	private Integer poolid;

	/*
	 * 拼音首字母
	 */
	private String initials;
	/**
	 * @return 返回 initials。
	 */
	public String getInitials() {
		return initials;
	}

	/**
	 * @param initials 要设置的 initials。
	 */
	public void setInitials(String initials) {
		this.initials = initials;
	}

	/*
	 *查询条件：企业id 
	 */
	private Integer eid;

	/*
	 *查询条件：部门id 
	 */
	private Integer dprtid;

	/*
	 *查询条件：父类id
	 */
	private Integer parentid;

	/*
	 *查询条件：部门名称
	 */
	private String name;

	/*
	 *查询条件：企业id 
	 */
	private Date createTime;

	/*
	 *查询条件：最后一次修改时间 
	 */
	private Date lastTime;

	/*
	 *查询条件：状态
	 */
	private Integer status;
	
	private String[] ids;
	/**
	 * @return 返回 ids。
	 */
	public String[] getIds() {
		return ids;
	}

	/**
	 * @param ids 要设置的 ids。
	 */
	public void setIds(String[] ids) {
		this.ids = ids;
	}

	/**
	 * @return 返回 poolid。
	 */
	public Integer getPoolid() {
		return poolid;
	}

	/**
	 * @param poolid 要设置的 poolid。
	 */
	public void setPoolid(Integer poolid) {
		this.poolid = poolid;
	}
	/**
	 * @return 返回 eid。
	 */
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
	 * @return 返回 dprtid。
	 */
	public Integer getDprtid() {
		return dprtid;
	}

	/**
	 * @param dprtid
	 *            要设置的 dprtid。
	 */
	public void setDprtid(Integer dprtid) {
		this.dprtid = dprtid;
	}

	/**
	 * @return 返回 parentid。
	 */
	public Integer getParentid() {
		return parentid;
	}

	/**
	 * @param parentid
	 *            要设置的 parentid。
	 */
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	/**
	 * @return 返回 name。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            要设置的 name。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 返回 createTime。
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            要设置的 createTime。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return 返回 lastTime。
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * @param lastTime
	 *            要设置的 lastTime。
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**
	 * @return 返回 status。
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            要设置的 status。
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}
