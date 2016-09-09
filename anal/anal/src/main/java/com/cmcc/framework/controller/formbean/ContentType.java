package com.cmcc.framework.controller.formbean;

import java.util.List;

import com.cmcc.framework.model.content.ContentInfo;

/**
 * 内容发布 formBean
 * 
 * @author zhangzhanliang
 * 
 */
public class ContentType {

	private Integer ctId;

	private String ctName;

	/**
	 * 是否是公共的模块 0 表示是公共的 1 表示不是公共的
	 */
	private Integer ctPbulic;

	/**
	 * 判断模块的附件 是不是公共的 0 表示是公共的 1 表示 不是公共的
	 */
	private Integer ctPublicAttach;
	/**
	 * 本类型下 info 集合
	 */
	private List<ContentInfo> infoList;

	public Integer getCtId() {
		return ctId;
	}

	public void setCtId(Integer ctId) {
		this.ctId = ctId;
	}

	public String getCtName() {
		return ctName;
	}

	public void setCtName(String ctName) {
		this.ctName = ctName;
	}

	public Integer getCtPbulic() {
		return ctPbulic;
	}

	public void setCtPbulic(Integer ctPbulic) {
		this.ctPbulic = ctPbulic;
	}

	public Integer getCtPublicAttach() {
		return ctPublicAttach;
	}

	public void setCtPublicAttach(Integer ctPublicAttach) {
		this.ctPublicAttach = ctPublicAttach;
	}

	public List<ContentInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<ContentInfo> infoList) {
		this.infoList = infoList;
	}

}
