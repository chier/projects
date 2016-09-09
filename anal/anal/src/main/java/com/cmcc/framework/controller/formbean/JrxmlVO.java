package com.cmcc.framework.controller.formbean;

import java.io.Serializable;

/**
 * irxml VO 类
 * 
 * @author zhangzhanliang
 * 
 */
public class JrxmlVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6709716274533400201L;

	/** 模块ID */
	private Integer identifier;

	/** 当前组的父级组的Id (为-1时，说明该模块不显示，只为和权限对应) */
	private Integer parentId;

	/** 模块名称 */
	private String modelName;

	/** 模块请求路径 */
	private String actionUrl;

	/** 父级点URL */
	private String parentUrl;

	/**
	 * 当前组是否是叶子节点
	 * 
	 * 0：不是叶子 1：是叶子
	 * 
	 */
	private Integer isLeaf = new Integer(1);

	/** 模块图片路径 */
	private String imageUrl;

	/** 模块的权限类型 （0. 为非可选权限对应的模块 1，为可选权限对应的模块） */
	private Integer permissionType;

	/**
	 * 当点击的节点是根节点时，是否需要iframe重定向的标记 1重定向 0不重定向
	 */
	private Integer flag;

	/** 模块对应的权限 */
	// private Set<Permission> permissions;
	/**
	 * 获得模块的请求路径
	 * 
	 * @return 模块的请求路径
	 * 
	 */
	private int modelTypeAdd;

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	/**
	 * 当前组是否是叶子节点
	 * 
	 * 0：不是叶子 1：是叶子
	 * 
	 */
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public int getModelTypeAdd() {
		return modelTypeAdd;
	}

	public void setModelTypeAdd(int modelTypeAdd) {
		this.modelTypeAdd = modelTypeAdd;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(Integer permissionType) {
		this.permissionType = permissionType;
	}

	public String getParentUrl() {
		return parentUrl;
	}

	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}

}
