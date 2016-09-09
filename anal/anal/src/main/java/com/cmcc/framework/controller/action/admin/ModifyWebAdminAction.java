/*
 * 文件名： ModifyWebAdminAction.java
 * 
 * 创建日期： 2009-2-25
 *
 * Copyright(C) 2009, by gaojl.
 *
 * 原始作者: <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 */
package com.cmcc.framework.controller.action.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.WebAdminFormBean;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.model.WebModel;
import com.cmcc.framework.model.role.Role;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 修改管理员的action
 * 
 * @author <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 * 
 * @version $Revision: 1.14 $
 * 
 * @since 2009-2-25
 */
@SuppressWarnings("unchecked")
public class ModifyWebAdminAction extends WebActionBase implements ModelDriven {

	private static final long serialVersionUID = -8165947805223181602L;

	/**
	 * GenericLogger
	 */
	private String employeeNames;

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ModifyWebAdminAction.class);

	/**
	 * this.nextResult
	 */
	private String nextResult;

	/**
	 * this.formbean
	 */
	private WebAdminFormBean webAdminForm = new WebAdminFormBean();

	/**
	 * 模型驱动
	 */
	public Object getModel() {
		return this.webAdminForm;
	}

	/**
	 * 修改管理员方法
	 */
	@GenericLogger(operateMark = OperateMark.ADMIN_UPDATE, operateDescription = "管理员维护管理-修改管理员", isOperateEmployee = true, isOperateRecords = false)
	public String execute() throws BusinessException {

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("ModifyWebAdminAction.execute()----start");
			}
			HttpServletRequest request = this.getRequest();
			String page = this.getRequest().getParameter("page");
			String id = request.getParameter("id");
			String modName = request.getParameter("modName");
			String modPwd = request.getParameter("modPwd");
			String name = request.getParameter("admin");
			String[] arrMId = getRequest().getParameterValues("modelId");
			StringBuilder mids = new StringBuilder();
			if (arrMId != null && arrMId.length > 0) {
				for (int i = 0; i < arrMId.length; i++) {
					mids.append(arrMId[i]);
					if (i != arrMId.length - 1) {
						mids.append(",");
					}
				}
			}
			
			String roleId = getRequest().getParameter("roleId");
			if(StringUtil.isBlank(roleId) || !StringUtil.isNumeric(roleId)){
				// request.setAttribute("isSameName", !sameName);
				logger.error("roleI 是个空或者非数字类型 ! " + roleId);
				roleId = "0";
			}
			int rid = Integer.valueOf(roleId);
			

			// 管理员信息
			WebAdmin admin = this.getWebAdminManager().get(Integer.valueOf(id));

			if (modPwd != null && modPwd.equals("1")) {
				admin.setPassword(this.getPasswordEncoder().encode(
						webAdminForm.getPassword()));
			}
			if (modName != null && modName.equals("1")) {
				if (name != null) {
					admin.setAdmin(name);
				}
			}
			admin.setCount(0);
			admin.setDmlTime(new Date());
			admin.setDmlflog((short) 2);
			admin.setModelIds(mids.toString());
			admin.setRoleId(rid);
			this.getWebAdminManager().saveOrUpdate(admin); // 实现修改管理员信息

			if (logger.isDebugEnabled()) {
				logger.debug("ModifyWebAdminAction.execute()----end");
			}
			this.nextResult = "page=" + page;
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(
					"efetionmanage.framework.exception.webadmin.modifyAdminException");
		}

	}

	/**
	 * 修改管理员入口
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	public String toModify() throws BusinessException {

		try {
			if (logger.isDebugEnabled()) {
				logger.debug("ModifyWebAdminAction.toModify()----start");
			}
			HttpServletRequest request = this.getRequest();
			List<WebModel> modelist = this.webModelManager.findByAll();
			this.getRequest().setAttribute("modelList", modelist);
			String page = this.getRequest().getParameter("page");
			String adminId = request.getParameter("adminId");
			WebAdmin admin = this.getWebAdminManager().get(
					Integer.valueOf(adminId));

			String[] mids = null;
			if (!StringUtil.isBlank(admin.getModelIds())) {
				mids = admin.getModelIds().split(",");
			}
			
			List<Role> roleList = this.roleManager.getAllRole();
			this.getRequest().setAttribute("roleList", roleList);

			request.setAttribute("mids", mids);
			request.setAttribute("webAdmin", admin);
			request.setAttribute("page", page);

			this.getRequest().setAttribute("actionUrl", "modifyWebAdminAction");
			if (logger.isDebugEnabled()) {
				logger.debug("ModifyWebAdminAction.toModify()----end");
			}
			return "toModify";
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(
					"efetionmanage.framework.exception.webadmin.toModifyAdminException");
		}

	}

	public String getEmployeeNames() {
		return employeeNames;
	}

	public void setEmployeeNames(String employeeNames) {
		this.employeeNames = employeeNames;
	}

	public String getNextResult() {
		return nextResult;
	}

	public void setNextResult(String nextResult) {
		this.nextResult = nextResult;
	}

}
