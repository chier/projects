package com.cmcc.anal.framework.model;


import java.util.List;
import java.util.Map;

/**
 * item VO 显示类
 * 
 * @author zhangzhanliang
 * @file com.cmcc.framework.controller.formbean --- ItemTableVO.java
 * @version 2013-3-6 -下午04:19:34
 */
public class ItemTableVO {

	/**
	 * 字段名称
	 */
	private String itemCode;

	/**
	 * 显示的名称
	 */
	private String itemName;

	/**
	 * 类型 1 表示 数值 2 表示字符 3 表示的是个列表值 通过code 去map里面取出来值
	 */
	private String itemType;

	/**
	 * 存储格式是
	 */
	private Map<String, List<CodeExprVO>> exprMap;

	public Map<String, List<CodeExprVO>> getExprMap() {
		return exprMap;
	}

	public void setExprMap(Map<String, List<CodeExprVO>> exprMap) {
		this.exprMap = exprMap;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

}
