package com.cmcc.anal.framework.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SurveyDataModel {
	private static Logger logger = LoggerFactory
			.getLogger(SurveyDataModel.class);

	/**
	 * comId
	 */
	private String surId = null;

	/**
	 * 
	 */
	private String surName = null;

	private boolean leaf = true;

	private List<SurveyDataModel> items;

	public SurveyDataModel(){
		
	}
	
	public SurveyDataModel(String surId,String surName,boolean leaf){
		this.surId = surId;
		this.surName = surName;
		this.leaf = leaf;
	}
	
	public String getSurId() {
		return surId;
	}

	public void setSurId(String surId) {
		this.surId = surId;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public List<SurveyDataModel> getItems() {
		return items;
	}

	public void setItems(List<SurveyDataModel> items) {
		this.items = items;
	}

}
