package com.cmcc.framework.business.impl.login;

import java.util.ArrayList;
import java.util.List;

import com.cmcc.common.Global;
import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.framework.business.interfaces.login.ILoginManager;
import com.cmcc.framework.business.interfaces.permission.IPermissionManager;
import com.cmcc.framework.controller.formbean.CustomVO;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.model.WebModel;
import com.cmcc.framework.persistence.interfaces.admin.IWebAdminDAO;
import com.cmcc.framework.persistence.interfaces.model.IWebModelDAO;

/**
 * 登录功能业务实现类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public class LoginManagerImpl implements ILoginManager {

	/**
	 * 管理员接口
	 */
	private IWebAdminDAO webAdminDAO;

	private IWebModelDAO webModelDAO;
	private IPermissionManager permissionManager;
	
	public IWebModelDAO getWebModelDAO() {
		return webModelDAO;
	}

	public void setWebModelDAO(IWebModelDAO webModelDAO) {
		this.webModelDAO = webModelDAO;
	}

	
	public IPermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(IPermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	public IWebAdminDAO getWebAdminDAO() {
		return webAdminDAO;
	}

	public void setWebAdminDAO(IWebAdminDAO webAdminDAO) {
		this.webAdminDAO = webAdminDAO;
	}

	public WebAdmin getByLoginId(String name) {
		return webAdminDAO.findByAdminName(name);
	}

	public List<WebModel> findModelByAdmin(WebAdmin admin) {
		if (admin.getLevel().intValue() == 0) {
			return webModelDAO.findByAll();
		} else {
			return webModelDAO.findByIds(admin.getModelIds());
		}
	}
	

}
