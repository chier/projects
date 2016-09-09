package com.cmcc.framework.model.role;

import com.cmcc.common.persistence.support.SearchCondition;

public class RoleSearcher extends SearchCondition {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3083200360012279136L;

	String roleName;

	Integer roleId;

	Integer userId;

	String userName;

	Integer roleScope;

	Integer roleNotScope;

	Integer permissionId;

	String permissionName;

	Long mp;

	Long fid;

	String userMark;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRoleScope() {
		return roleScope;
	}

	public void setRoleScope(Integer roleScope) {
		this.roleScope = roleScope;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public Long getMp() {
		return mp;
	}

	public void setMp(Long mp) {
		this.mp = mp;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public String getUserMark() {
		return userMark;
	}

	public void setUserMark(String userMark) {
		this.userMark = userMark;
	}

	public Integer getRoleNotScope() {
		return roleNotScope;
	}

	public void setRoleNotScope(Integer roleNotScope) {
		this.roleNotScope = roleNotScope;
	}

}
