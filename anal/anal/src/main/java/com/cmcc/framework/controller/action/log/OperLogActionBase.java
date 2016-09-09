/**      
 * 文件名称： OperLogActionBase.java
 * 
 * 创建时间： 2009.3.23
 *      
 * Copyright (c) 2009 by Shilimin.      
 */
package com.cmcc.framework.controller.action.log;

import com.cmcc.common.controller.action.CommonAction;
import com.cmcc.framework.business.interfaces.log.IOperLogManager;
//import com.cmcc.framework.business.interfaces.system.model.IModelManager;

/**
 * 
 * 功能描述：查询系统操作日志Action基类
 * 
 * @author <a href="mailto:shilimin@hdxt.net.cn">Shilimin</a>
 * 
 * @version 1.0
 */

public class OperLogActionBase extends CommonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4712177050982797431L;
	/** 日志接口 */
	protected IOperLogManager operLogManager;
	
	/** 模块业务接口 */
//	protected IModelManager modelManager;

	public IOperLogManager getOperLogManager() {

		return operLogManager;
	}

	public void setOperLogManager(IOperLogManager operLogManager) {

		this.operLogManager = operLogManager;
	}

//	public IModelManager getModelManager() {
//		return modelManager;
//	}
//
//	public void setModelManager(IModelManager modelManager) {
//		this.modelManager = modelManager;
//	}
	
	
	/*
	 *点击的节点名称及连接地址 
	 * */
	protected String label;
	protected String link;

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

}
