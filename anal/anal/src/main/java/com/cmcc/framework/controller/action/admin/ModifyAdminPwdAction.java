/*
 * 文件名： ModifyAdminPwdAction.java
 * 
 * 创建日期： 2009-3-10
 *
 * Copyright(C) 2009, by gaojl.
 *
 * 原始作者: <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 */
package com.cmcc.framework.controller.action.admin;

import javax.servlet.ServletOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.WebAdminFormBean;
import com.cmcc.framework.model.admin.WebAdmin;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 修改管理员密码的action
 * 
 * @author <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-3-10
 */
@SuppressWarnings("unchecked")
public class ModifyAdminPwdAction extends WebActionBase implements
		ModelDriven {

	private static final long serialVersionUID = 5071850472303938307L;

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ModifyAdminPwdAction.class);
	/**
	 * GenericLogger
	 */
	private String employeeNames;
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
	 * 修改管理员密码入口
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	public String toModifyPwd() throws BusinessException {
		try {
			// 判断session是否为空
			UserSessionObj obj = this.getUserSessionInfo();
			if (obj != null) {
				return "toModifyPwd";
			} else {
				throw new BusinessException(
						"efetionmanage.framework.exception.noSession");
			}
		} catch (Exception e) {
			throw new BusinessException(
					"efetionmanage.framework.exception.webadmin.toModifyAdminException");
		}

	}

	/**
	 * 修改管理员密码
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	@GenericLogger(operateMark = OperateMark.ADMIN_PWD_UPDATE, operateDescription = "权限管理-修改密码", isOperateEmployee = true, isOperateRecords = false)
	public String execute() throws BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("ModifyWebAdminAction.ModifyPwd()----start");
		}
		try {
			UserSessionObj obj = this.getUserSessionInfo();
			if (obj != null) {
				String oldpwd = this.getRequest().getParameter("oldPwd");
				String newPwd = this.getRequest().getParameter("newPwd");
				WebAdmin admin = this.getWebAdminManager().get(obj.getId());
				if (oldpwd != null
						&& admin.getPassword().equalsIgnoreCase(
								this.getPasswordEncoder().encode(oldpwd))) {
					if (newPwd != null && !newPwd.equals("")) {
						admin.setPassword(this.getPasswordEncoder().encode(
								newPwd));
						this.getWebAdminManager().saveOrUpdate(admin);
						this.getRequest().setAttribute("action", "modifyPwd");
//						 for GenericLogger
						setEmployeeNames(admin.getAdmin());
						ServletOutputStream out = this.getResponse()
								.getOutputStream();
						out.write("true".getBytes());
					} else {
						throw new BusinessException(
								"efetionmanage.framework.exception.webadmin.passwordIsNULLException");
					}
				} else {
					ServletOutputStream out = this.getResponse()
							.getOutputStream();
					out.write("false".getBytes());
				}

			} else {
				throw new BusinessException(
						"efetionmanage.framework.exception.noSession");
			}
		} catch (Exception e) {
			throw new BusinessException(
					"efetionmanage.framework.exception.webadmin.modifypwdException");
		}
		return null;
	}

	public String getEmployeeNames() {
		return employeeNames;
	}

	public void setEmployeeNames(String employeeNames) {
		this.employeeNames = employeeNames;
	}
}
