package com.cmcc.anal.framework.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class MapModel {
	private static Logger logger = LoggerFactory.getLogger(MapModel.class);
	
	private String key;
	
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}


