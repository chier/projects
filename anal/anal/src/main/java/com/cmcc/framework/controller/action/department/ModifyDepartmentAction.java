/*
 * 文件名： ModifyDepartmentAction.java
 * 
 * 创建日期： 2009-2-28
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.framework.controller.action.department;

import java.util.Date;
import java.util.List;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.model.department.DepartmentSearcher;

/**
 * 修改部门信息
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-10
 */
public class ModifyDepartmentAction extends WebActionBase {

	private static final long serialVersionUID = 8121563761155644325L;

	private String nextResult;

	protected boolean isRollback = false;

	public boolean isRollback() {
		return isRollback;
	}

	public void setRollback(boolean isRollback) {
		this.isRollback = isRollback;
	}

	/**
	 * @return 返回 nextResult。
	 */
	public String getNextResult() {
		return nextResult;
	}

	/**
	 * @param nextResult
	 *            要设置的 nextResult。
	 */
	public void setNextResult(String nextResult) {
		this.nextResult = nextResult;
	}

	// 添加注解协助实现日志拦截
	public String execute() throws DataException, BusinessException {
		String deptid = this.getRequest().getParameter("identifier");
//		Integer eid = this.getUserSessionInfo().getEid();
		// Integer poolId = PoolConfigInfoMap.get(eid).getPhysical_pool_id();
		String parentIdStr = this.getRequest().getParameter("parentId");
		Integer groupId = Integer.parseInt(this.getRequest().getParameter(
				"groupId"));
		Integer parentId = null != parentIdStr
				&& !parentIdStr.equalsIgnoreCase("") ? Integer
				.parseInt(parentIdStr) : groupId;
		DepartmentSearcher searchCond = new DepartmentSearcher();
//		searchCond.setDprtid(Integer.parseInt(deptid));
		// searchCond.setEid(eid);
		// searchCond.setPoolid(poolId);
//		List<Department> list = this.departmentManager.findBy(searchCond);
		Department department = this.departmentManager.findDeptById(Integer.parseInt(deptid));
		String name = this.getRequest().getParameter("name").trim();

		if (name != null && name.length() > 50) {
			nextResult = "nameMax=true&groupId=" + parentId + "&gId=" + deptid;
			this.isRollback = true;
			return "result";
		}
		if (this.getRequest().getParameter("description").trim() != null
				&& this.getRequest().getParameter("description").trim()
						.length() > 256) {
			nextResult = "decMax=true&groupId=" + parentId + "&gId=" + deptid;
			this.isRollback = true;
			return "result";
		}

		if (parentId == 0 && name.equals("未分配部门")) {
			nextResult = "noAssign=true&groupId=" + parentId + "&gId=" + deptid;
			this.isRollback = true;
			return "result";
		}
		department.setName(name);
		searchCond.setDprtid(null);
		searchCond.setParentid(parentId);
		searchCond.setName(name);
		List<Department> nameInvalid = this.departmentManager
				.findBy(searchCond);

		if (null != nameInvalid && nameInvalid.size() > 0) {
			if (nameInvalid.size() == 1
					&& !nameInvalid.get(0).getDeptId().equals(
							Integer.parseInt(deptid))) {
				nextResult = "nameInvalid=true&groupId=" + parentId + "&gId="
						+ deptid;
				this.isRollback = true;
				return "result";
			}
		}
		department.setDescrption(this.getRequest().getParameter("description")
				.trim());

		this.departmentManager.saveOrUpdateDept(department);
		nextResult = "groupId=" + parentId + "&operate=modify";
		return SUCCESS;
	}
}
