/**      
 * 文件名称： OperLog.java
 * 
 * 创建时间： Mar 23, 2009 1:41:10 PM
 *      
 * Copyright (c) 2009 by Li Benyong.      
 */
package com.cmcc.framework.model.admin;

import com.cmcc.common.persistence.support.SearchCondition;

/**
 * webAdmin 查询条件集合
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-9
 */
public class WebAdminSearch extends SearchCondition {
	private static final long serialVersionUID = 5129397248020971771L;

	/**
	 * 管理员名称
	 */
	private String adminName;

	/**
	 * 部门 id
	 */
	private Integer deptId;

	/**
	 * 角色id
	 */
	private Integer roleId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getAdminName() {
		if (adminName == null || "".equals(adminName)) {
			return null;
		}
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

}
