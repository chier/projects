package com.cmcc.framework.model.role;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cmcc.common.persistent.PersistentObject;

@Entity
@Table(name = "gweb_role")
public class Role implements PersistentObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5569422739817730191L;

	/**
	 * 主键 自增
	 */
	Integer roleId;

	/**
	 * 角色名称
	 */
	String roleName;

	/**
	 * 角色描述
	 */
	String roleDec;

	/**
	 * 操作时间
	 */
	Date dmlTime;

	/**
	 * 添加时间
	 */
	Date createTime;

	/**
	 * 操作类型 1 表示 添加 2 表示 修改 3 表示 删除
	 */
	Short dmlflog;

	/**
	 * 角色类型 0：默认everyon 不可修改角色 1：自定义的角色
	 */
	Integer roleType;

	/**
	 * 
	 * 处理title问题
	 */
	String showRoleDec;

	/**
	 * 处理title问题
	 * 
	 * @return
	 */
	int decLength;

	/**
	 * 角色范围 0：未分配权限; 1：全公司，2：自定义范围，默认：0
	 */
//	Integer roleScope;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id")
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Column(name = "role_name")
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "role_desc")
	public String getRoleDec() {
		return roleDec;
	}

	public void setRoleDec(String roleDec) {
		this.roleDec = roleDec;
	}

	@Column(name = "dmlflog")
	public Short getDmlflog() {
		return dmlflog;
	}

	public void setDmlflog(Short dmlflog) {
		this.dmlflog = dmlflog;
	}

	@Column(name = "dmltime")
	public Date getDmlTime() {
		return dmlTime;
	}

	public void setDmlTime(Date dmlTime) {
		this.dmlTime = dmlTime;
	}

	@Column(name = "createtime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Transient
	public String getShowRoleDec() {
		return showRoleDec;
	}

	public void setShowRoleDec(String showRoleDec) {
		this.showRoleDec = showRoleDec;
	}

	@Transient
	public int getDecLength() {
		return decLength;
	}

	public void setDecLength(int decLength) {
		this.decLength = decLength;
	}

	@Column(name = "role_type")
	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

//	@Column(name = "role_scope")
//	public Integer getRoleScope() {
//		return roleScope;
//	}
//
//	public void setRoleScope(Integer roleScope) {
//		this.roleScope = roleScope;
//	}
}
