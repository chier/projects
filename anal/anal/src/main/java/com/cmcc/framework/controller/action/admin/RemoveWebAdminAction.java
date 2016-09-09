/*
 * 文件名： RemoveWebAdminAction.java
 * 
 * 创建日期： 2009-2-25
 *
 * Copyright(C) 2009, by gaojl.
 *
 * 原始作者: <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 */
package com.cmcc.framework.controller.action.admin;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.admin.WebAdmin;

/**
 * 删除管理员的action
 * 
 * @author <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 * 
 * @version $Revision: 1.5 $
 * 
 * @since 2009-2-25
 */
public class RemoveWebAdminAction extends WebActionBase {

	private static final long serialVersionUID = -5659561543292113724L;

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(RemoveWebAdminAction.class);

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

	// 删除管理员操作
	@GenericLogger(operateMark = OperateMark.ADMIN_DELETE, operateDescription = "管理员维护管理-删除管理员", isOperateEmployee = true, isOperateRecords = true)
	public String execute() throws BusinessException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("RemoveWebAdminAction.execute()----start");
			}
			HttpServletRequest request = this.getRequest();
			String page = this.getRequest().getParameter("page");
			String aid = request.getParameter("removeids");
			String[] ids = aid.split(";");
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
					WebAdmin admin = this.getWebAdminManager().get(
							Integer.valueOf(ids[i]));

					getWebAdminManager().remove(admin);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("RemoveWebAdminAction.execute()----end");
				}
				ServletOutputStream out = this.getResponse().getOutputStream();
				out.write("true".getBytes());
			} else {
				ServletOutputStream out = this.getResponse().getOutputStream();
				out.write("false".getBytes());
			}
			this.getRequest().setAttribute("page", page);
		} catch (Exception e) {
			throw new BusinessException(
					"efetionmanage.framework.exception.webadmin.removeAdminException");
		}
		return null;
	}
}
