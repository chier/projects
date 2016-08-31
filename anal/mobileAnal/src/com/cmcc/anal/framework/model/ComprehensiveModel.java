package com.cmcc.anal.framework.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComprehensiveModel {
	private static Logger logger = LoggerFactory
			.getLogger(ComprehensiveModel.class);

	/**
	 * comId
	 */
	private Integer comId = null;

	/**
	 * 
	 */
	private String comName = null;

	private boolean leaf;

	private List<ComprehensiveModel> items;

	public Integer getComId() {
		return comId;
	}

	public void setComId(Integer comId) {
		this.comId = comId;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<ComprehensiveModel> getItems() {
		return items;
	}

	public void setItems(List<ComprehensiveModel> items) {
		this.items = items;
	}

}
