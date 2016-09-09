package com.cmcc.common.controller.interceptor.enums;

/**
 * 权限管理 权限类型枚举
 * 
 * @author guowz
 * @since 2009-10-9
 * 
 */

public enum PermissionMark {

	/* 默认值 */
	OPERATE_DEFAULT(0), CustomTree_permission(1), // 定制分析树节点信息
	CustomBtn_permission(6), // 定制分析树节点信息
	QueriesTree_permission(2), // 即席分析 树节点信息
	QueriesBtn_permission(5), // 即席查询 按钮权限
	LoapTree_permission(3), // loap 树节点权限
	LoapBtn_permission(7), // loap 树节点权限
	RawDataTree_permission(4), // 原始数据 树节点权限
	RawDataBtn_permission(8), // 原始数据 树节点权限
	CenterTree_permission(9), // 数据下载 树节点权限
	CenterAttachDownload_permission(10), // 数据下载 附件下载的权限
	DataDownTree_permission(11)	//数据管理 树节点权限
	;

	private PermissionMark() {

	}

	private PermissionMark(int permission) {
		this.permission = permission;

	}

	private int permission;

	// private String operate;

	// public String toString(){
	// return this.operate;
	// }
	public String toString() {
		return String.valueOf(this.permission);
	}

	public int getValue() {
		return permission;
	}
}
