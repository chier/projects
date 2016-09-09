/*
 * 文件名： WebActionBase.java
 * 
 * 创建日期： 2009-2-24
 *
 * Copyright(C) 2009, by gaojl.
 *
 * 原始作者: <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 */
package com.cmcc.framework.controller.action.webaction;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.dom4j.io.XMLWriter;

import com.cmcc.common.controller.action.CommonAction;
import com.cmcc.common.util.encrypt.IPasswordEncoder;
import com.cmcc.framework.business.interfaces.admin.IWebAdminManager;
import com.cmcc.framework.business.interfaces.content.IContentAttachManager;
import com.cmcc.framework.business.interfaces.content.IContentBodyManager;
import com.cmcc.framework.business.interfaces.content.IContentInfoManager;
import com.cmcc.framework.business.interfaces.custom.ICustomManager;
import com.cmcc.framework.business.interfaces.datadown.IDataDownManager;
import com.cmcc.framework.business.interfaces.department.IDepartmentManager;
import com.cmcc.framework.business.interfaces.login.ILoginManager;
import com.cmcc.framework.business.interfaces.model.IWebModelManager;
import com.cmcc.framework.business.interfaces.permission.IPermissionManager;
import com.cmcc.framework.business.interfaces.queries.IQueriesManager;
import com.cmcc.framework.business.interfaces.rawdata.IRawDataManager;
import com.cmcc.framework.business.interfaces.role.IRoleManager;
import com.cmcc.framework.business.interfaces.warning.IWarningManager;

/**
 * web页面action的基类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-6
 */
public class WebActionBase extends CommonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5944583747573093129L;

	private Logger logger = Logger.getLogger(WebActionBase.class);

	/**
	 * 登录业务接口
	 */
	protected ILoginManager loginManager;

	/** 加密业务接口 */
	protected IPasswordEncoder passwordEncoder;

	/**
	 * 管理员业务接口
	 */
	protected IWebAdminManager webAdminManager;

	/**
	 * 模块业务接口
	 */
	protected IWebModelManager webModelManager;

	/**
	 * 角色业务接口
	 */
	protected IRoleManager roleManager;

	protected IDepartmentManager departmentManager;

	/**
	 * 原始数据
	 */
	protected IRawDataManager rawDataManager;

	/**
	 * 即席查询
	 */
	protected IQueriesManager queriesManager;

	/**
	 * 定制分析
	 */
	protected ICustomManager customManager;

	/**
	 * 权限
	 */
	protected IPermissionManager permissionManager;

	/**
	 * 预警分析
	 */
	protected IWarningManager warningManager;

	protected IContentInfoManager contentInfoManager;

	protected IContentAttachManager contentAttachManager;

	protected IContentBodyManager contentBodyManager;
	
	protected IDataDownManager dataDownManager;
	
	

	public IDataDownManager getDataDownManager() {
		return dataDownManager;
	}

	public void setDataDownManager(IDataDownManager dataDownManager) {
		this.dataDownManager = dataDownManager;
	}

	public IWarningManager getWarningManager() {
		return warningManager;
	}

	public void setWarningManager(IWarningManager warningManager) {
		this.warningManager = warningManager;
	}

	public IQueriesManager getQueriesManager() {
		return queriesManager;
	}

	public void setQueriesManager(IQueriesManager queriesManager) {
		this.queriesManager = queriesManager;
	}

	public ILoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(ILoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public IPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(IPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public IWebAdminManager getWebAdminManager() {
		return webAdminManager;
	}

	public void setWebAdminManager(IWebAdminManager webAdminManager) {
		this.webAdminManager = webAdminManager;
	}

	public IWebModelManager getWebModelManager() {
		return webModelManager;
	}

	public void setWebModelManager(IWebModelManager webModelManager) {
		this.webModelManager = webModelManager;
	}

	public IRoleManager getRoleManager() {
		return roleManager;
	}

	public void setRoleManager(IRoleManager roleManager) {
		this.roleManager = roleManager;
	}

	public IDepartmentManager getDepartmentManager() {
		return departmentManager;
	}

	public void setDepartmentManager(IDepartmentManager departmentManager) {
		this.departmentManager = departmentManager;
	}

	public IRawDataManager getRawDataManager() {
		return rawDataManager;
	}

	public void setRawDataManager(IRawDataManager rawDataManager) {
		this.rawDataManager = rawDataManager;
	}

	public ICustomManager getCustomManager() {
		return customManager;
	}

	public void setCustomManager(ICustomManager customManager) {
		this.customManager = customManager;
	}

	public IPermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(IPermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	public IContentAttachManager getContentAttachManager() {
		return contentAttachManager;
	}

	public void setContentAttachManager(
			IContentAttachManager contentAttachManager) {
		this.contentAttachManager = contentAttachManager;
	}

	public IContentInfoManager getContentInfoManager() {
		return contentInfoManager;
	}

	public void setContentInfoManager(IContentInfoManager contentInfoManager) {
		this.contentInfoManager = contentInfoManager;
	}

	public IContentBodyManager getContentBodyManager() {
		return contentBodyManager;
	}

	public void setContentBodyManager(IContentBodyManager contentBodyManager) {
		this.contentBodyManager = contentBodyManager;
	}

	public void closeWriter(XMLWriter writer) {
		if (writer != null) {
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				logger.error(e);
			}

			writer = null;
		}
	}
}
