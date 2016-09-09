/*
 * 文件名： CreateWebAdminAction.java
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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.WebAdminFormBean;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.model.WebModel;
import com.cmcc.framework.model.role.Role;
import com.opensymphony.xwork2.ModelDriven;

/**
 * eabd8ce9404507aa8c22714d3f5eada9 : aaa111 创建管理员的action
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-9
 */
@SuppressWarnings("unchecked")
public class CreateWebAdminAction extends WebActionBase implements ModelDriven {

	private static final long serialVersionUID = 7037856247554169181L;

	/**
	 * this.formbean
	 */
	private WebAdminFormBean webAdminForm = new WebAdminFormBean();

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(CreateWebAdminAction.class);

	public Object getModel() {
		return this.webAdminForm;
	}

	/**
	 * GenericLogger
	 */
	private String employeeNames;

	public String getEmployeeNames() {
		return employeeNames;
	}

	public void setEmployeeNames(String employeeNames) {
		this.employeeNames = employeeNames;
	}

	/**
	 * 创建管理员方法
	 */
	@GenericLogger(operateMark = OperateMark.ADMIN_ADD, operateDescription = "管理员维护管理-创建管理员", isOperateEmployee = true, isOperateRecords = false)
	public String execute() throws BusinessException {

		if (logger.isDebugEnabled()) {
			logger.debug("CreateWebAdminAction.execute()----start");
		}
		try {
			HttpServletRequest request = this.getRequest();
			UserSessionObj obj = this.getUserSessionInfo();
			Date date = new Date();
			if (obj != null) {

				/**
				 * 禁止管理员重名
				 */
				boolean sameName = getWebAdminManager().getSameName(
						webAdminForm.getAdmin(), webAdminForm.getIdentifier());
				logger.info("admin create : " + webAdminForm.getAdmin()
						+ " is " + sameName);
				String[] arrMId = getRequest().getParameterValues("modelId");
				String roleId = getRequest().getParameter("roleId");
			
				if (!sameName) {
					request.setAttribute("isSameName", !sameName);
					return "toCreateWebAdmin";
				}
				StringBuilder mids = new StringBuilder();
				if (arrMId != null && arrMId.length > 0) {
					for (int i = 0; i < arrMId.length; i++) {
						mids.append(arrMId[i]);
						if (i != arrMId.length - 1) {
							mids.append(",");
						}
					}
				}
				if(StringUtil.isBlank(roleId) || !StringUtil.isNumeric(roleId)){
					// request.setAttribute("isSameName", !sameName);
					logger.error("roleI 是个空或者非数字类型 ! " + roleId);
					return "toCreateWebAdmin";
				}
				int rid = Integer.valueOf(roleId);
				WebAdmin admin = new WebAdmin();
				PropertyUtils.copyProperties(admin, webAdminForm);
				admin.setPassword(this.getPasswordEncoder().encode(
						webAdminForm.getPassword()));
				admin.setLevel(1);

				admin.setIdentifier(null);
				admin.setCount(0);
				admin.setCreateTime(date);
				admin.setDmlTime(date);
				admin.setDmlflog((short) 1);
//				admin.setDeptId(0);
				admin.setModelIds(mids.toString());
				admin.setRoleId(rid);

				setEmployeeNames(admin.getAdmin());
				webAdminManager.saveOrUpdate(admin);
				return SUCCESS;
			} else {
				throw new BusinessException(
						"efetionmanage.framework.exception.noSession");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(
					"efetionmanage.framework.exception.webadmin.addAdminException");
		}

	}

	/**
	 * 添加管理员入口
	 * 
	 * @return
	 */
	public String toCreate() throws BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("CreateWebAdminAction.toCreate()----start");
		}
		try {
			// List<Role> roleList = this.webAdminManager.findRoleBy();
			List<WebModel> modelList = this.webModelManager.findByAll();
			// this.getRequest().setAttribute("deptId",
			// getRequest().getParameter("groupId"));// 部门id
			this.getRequest().setAttribute("modelList", modelList);
			
			List<Role> roleList = this.roleManager.getAllRole();
			this.getRequest().setAttribute("roleList", roleList);
			this.getRequest().setAttribute("actionUrl", "createWebAdminAction");
			this.getRequest().setAttribute("size", 0);
			if (logger.isDebugEnabled()) {
				logger.debug("CreateWebAdminAction.toCreate()----end");
			}
			return "toCreateWebAdmin";
		} catch (Exception e) {
			throw new BusinessException(
					"efetionmanage.framework.exception.webadmin.toAddAdminException");
		}
	}
}
