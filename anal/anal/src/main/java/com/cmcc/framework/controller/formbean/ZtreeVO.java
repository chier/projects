package com.cmcc.framework.controller.formbean;

import java.io.Serializable;

/**
 * ztree 树 显示的vo 封装
 * 
 * @author Administrator
 * 
 */
public class ZtreeVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4797019487074498284L;

	/**
	 * 主键id
	 */
	private int id;

	/**
	 * 父id
	 */
	private int pId;

	/**
	 * 显示的名称
	 */
	private String name;

	/**
	 * 是否打开 true 表示打开 false 表示不打开
	 */
	private boolean open;

	/**
	 * 是否已经勾选 true 表示勾选。false 表示不勾选
	 */
	private boolean checked;

	/**
	 * 是否允许勾选
	 */
	private boolean nocheck = false;

	/**
	 * 类型 各个模块的类型值 是不同的 比如即席查询 type =1 表示每一节点 type =2 表示第二节点 type 表示第三节点
	 */
	private int type;

	/**
	 * 节点类型所对应的值，其实可以只使用这个值来表示
	 */
	private String typeValue;

	/**
	 * code值
	 */
	private String codeValue;

	/**
	 * 表值
	 */
	private String tableValue;

	// private String icon = "/anal/js/tree/rawdatacommontree/images/file.gif";

	// private String iconOpen =
	// "/anal/js/tree/rawdatacommontree/images/openfolder.gif";

	// private String iconClose =
	// "/anal/js/tree/rawdatacommontree/images/foldericon.gif";

	/* 预警分析 下限值 */
	private String minVal;

	/* 预警分析 上限值 */
	private String maxVal;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int id) {
		pId = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTypeValue() {
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public String getTableValue() {
		return tableValue;
	}

	public void setTableValue(String tableValue) {
		this.tableValue = tableValue;
	}

	// public String geticon() {
	// return this.icon;
	// }

	// public String getIconOpen() {
	// return iconOpen;
	// }

	// public void setIconOpen(String iconOpen) {
	// this.iconOpen = iconOpen;
	// }

	// public String getIconClose() {
	// return iconClose;
	// }

	public String getMaxVal() {
		return maxVal;
	}

	public void setMaxVal(String maxVal) {
		this.maxVal = maxVal;
	}

	public String getMinVal() {
		return minVal;
	}

	public void setMinVal(String minVal) {
		this.minVal = minVal;
	}

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}

}
