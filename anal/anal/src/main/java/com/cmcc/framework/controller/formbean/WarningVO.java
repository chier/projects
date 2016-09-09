package com.cmcc.framework.controller.formbean;

import com.cmcc.common.persistence.query.BaseDTO;

/**
 * 预警分析 VO 获取提交类
 * 
 * @author zhangzhanliang
 * 
 */
public class WarningVO extends BaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8138110742083407835L;
	/**
	 * 模板id
	 */
	String templateCode;

	String hidFirstName = null;

	String hidFirstCode;

	String hidFirstTable;

	// 下限
	String hidFirstMin;

	// 上限
	String hidFirstMax;

	String hidSecondCode;

	String hidSecondName;

	String hidSecondTable;

	// 下限
	String hidSecondMin;

	// 上限
	String hidSecondMax;

	String hidArea;
	/**
	 * 存储的目录 id
	 */
	String hidSaveWar;

	String txtName;

	public String getHidArea() {
		return hidArea;
	}

	public void setHidArea(String hidArea) {
		this.hidArea = hidArea;
	}

	public String getHidFirstCode() {
		return hidFirstCode;
	}

	public void setHidFirstCode(String hidFirstCode) {
		this.hidFirstCode = hidFirstCode;
	}

	public String getHidFirstMax() {
		return hidFirstMax;
	}

	public void setHidFirstMax(String hidFirstMax) {
		this.hidFirstMax = hidFirstMax;
	}

	public String getHidFirstMin() {
		return hidFirstMin;
	}

	public void setHidFirstMin(String hidFirstMin) {
		this.hidFirstMin = hidFirstMin;
	}

	public String getHidFirstName() {
		return hidFirstName;
	}

	public void setHidFirstName(String hidFirstName) {
		this.hidFirstName = hidFirstName;
	}

	public String getHidFirstTable() {
		return hidFirstTable;
	}

	public void setHidFirstTable(String hidFirstTable) {
		this.hidFirstTable = hidFirstTable;
	}

	public String getHidSecondCode() {
		return hidSecondCode;
	}

	public void setHidSecondCode(String hidSecondCode) {
		this.hidSecondCode = hidSecondCode;
	}

	public String getHidSecondMax() {
		return hidSecondMax;
	}

	public void setHidSecondMax(String hidSecondMax) {
		this.hidSecondMax = hidSecondMax;
	}

	public String getHidSecondMin() {
		return hidSecondMin;
	}

	public void setHidSecondMin(String hidSecondMin) {
		this.hidSecondMin = hidSecondMin;
	}

	public String getHidSecondName() {
		return hidSecondName;
	}

	public void setHidSecondName(String hidSecondName) {
		this.hidSecondName = hidSecondName;
	}

	public String getHidSecondTable() {
		return hidSecondTable;
	}

	public void setHidSecondTable(String hidSecondTable) {
		this.hidSecondTable = hidSecondTable;
	}

	public String getHidSaveWar() {
		return hidSaveWar;
	}

	public void setHidSaveWar(String hidSaveWar) {
		this.hidSaveWar = hidSaveWar;
	}

	public String getTxtName() {
		return txtName;
	}

	public void setTxtName(String txtName) {
		this.txtName = txtName;
	}

	public String getTemplateCode() {
		return templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

}
