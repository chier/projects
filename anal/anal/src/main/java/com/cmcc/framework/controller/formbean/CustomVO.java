package com.cmcc.framework.controller.formbean;

import java.io.Serializable;

/**
 * 即席查询 tree VO 显示类
 * 
 * @author zhangzhanliang
 * 
 */
public class CustomVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5758862099697993100L;

	/** 模块ID */
	private Integer identifier;

	/** 当前组的父级组的Id (为-1时，说明该模块不显示，只为和权限对应) */
	private Integer parentId;

	/** 模块 name */
	private String modelName;

	/** 模块请求路径 */
	private String actionUrl;
	
	/** 查询sql*/
	private String sql;

	/** 是否显示图片*/
	private int isImage;
	/**
	 * 当前组是否是叶子节点
	 * 
	 * 0：不是叶子 1：是叶子
	 * 
	 */
	private Integer isLeaf = new Integer(1);

	/** 表 code */
	private String tableCode;

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public Integer getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public int getIsImage() {
		return isImage;
	}

	public void setIsImage(int isImage) {
		this.isImage = isImage;
	}

}
