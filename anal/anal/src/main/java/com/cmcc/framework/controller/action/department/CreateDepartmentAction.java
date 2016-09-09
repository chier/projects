/*
 * 文件名： CreateDepartmentAction.java
 * 
 * 创建日期： 2009-2-23
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.framework.controller.action.department;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.model.department.DepartmentSearcher;

/**
 * 
 * 创建企业组织部门action
 * 
 * @author <a href="mailto:sun128837@126.com">conntsing</a>
 * 
 * @version $Revision: 1.9 $
 * 
 * @since 2009-2-23
 */
public class CreateDepartmentAction extends WebActionBase {
	private static final long serialVersionUID = 2104983867981861555L;

	private static final Log logger = LogFactory
			.getLog(CreateDepartmentAction.class);

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

	@SuppressWarnings("deprecation")
	// 添加注解协助实现日志拦截
	public String execute() throws DataException, BusinessException {

		try {
			Integer eid = this.getUserSessionInfo().getEid();
			// Integer poolId =
			// PoolConfigInfoMap.get(eid).getPhysical_pool_id();
			Integer groupId = Integer.parseInt(this.getRequest().getParameter(
					"groupId"));
			String parentIdStr = this.getRequest().getParameter("parentId");
			String name = this.getRequest().getParameter("name").trim();

			Integer parentId = null != parentIdStr
					&& !parentIdStr.equalsIgnoreCase("") ? Integer
					.parseInt(parentIdStr) : groupId;

			if (name != null && name.length() > 50) {
				nextResult = "nameMax=true&groupId=" + groupId;
				this.isRollback = true;
				return "result";
			}
			if (this.getRequest().getParameter("description").trim() != null
					&& this.getRequest().getParameter("description").trim()
							.length() > 256) {
				nextResult = "decMax=true&groupId=" + groupId;
				this.isRollback = true;
				return "result";
			}

			if (parentId == 0 && name.equals("未分配部门")) {

				nextResult = "noAssign=true&groupId=" + groupId;
				this.isRollback = true;
				return "result";

			}
			DepartmentSearcher searchCond = new DepartmentSearcher();
			// searchCond.setPoolid(poolId);
			// searchCond.setEid(eid);
			searchCond.setParentid(parentId);
			searchCond.setName(name);
			// 查询同级下是否有重名的部门
			List<Department> nameInvalid = this.departmentManager
					.findBy(searchCond);
			if (null != nameInvalid && nameInvalid.size() > 0) {
				nextResult = "nameInvalid=true&groupId=" + groupId;
				this.isRollback = true;
				return "result";
			}

			searchCond.setName(null);
			searchCond.setParentid(null);
			searchCond.setDprtid(parentId);
			List<Department> parentList = this.departmentManager
					.findBy(searchCond);
			Department parent = null;
			if (null != parentList && parentList.size() > 0) {
				parent = parentList.get(0);
				if (parent.getDepth().intValue() >= 8) {
					nextResult = "gaff10=true&groupId=" + groupId;
					this.isRollback = true;
					return "result";
				}
			}

			Department department = new Department();

			department.setName(name);
			// department.setInitials(CnToSpell.getFirstSpell(department.getName()
			// .trim().replaceAll(" ", "").replaceAll(" ", "")));
			department.setDescrption(this.getRequest().getParameter(
					"description").trim());
			department.setParentId(parentId);
			department.setCreateTime(new Date());
			department.setEid(eid);
			department.setIsLeaf(new Integer("1"));
			// department.setLastTime(new Date());
			department.setDmlflog((short) 1);
			department.setDmltime(new Date());
			department.setDepartmentOrder(0);

			// searchCond.setPoolid(poolId);

//			DepartmentSearcher searcherParent = new DepartmentSearcher();
//			// searcherParent.setEid(eid);
//			// searcherParent.setPoolid(poolId);
//			searcherParent.setDprtid(department.getParentId());
//			List<Department> departmentList = departmentManager
//					.findBy(searcherParent);
			Short depth = null;
			if (parent != null) {
				depth = parent.getDepth();
			}
			if (department.getParentId().intValue() == 0) {
				department.setDepth(Short
						.valueOf(Integer.valueOf(1).toString()));
			} else {
				department.setDepth(Short.valueOf(Integer.valueOf(depth + 1)
						.toString()));
			}
			if (department.getParentId() != 0 && parent != null) {
				if (parent.getIsLeaf() != 0) {
					parent.setIsLeaf(0);
					this.departmentManager.saveOrUpdateDept(parent);
				}
			}
			departmentManager.saveOrUpdateDept(department);

			nextResult = "groupId=" + parentId + "&operate=add";
		} catch (Exception e) {

			e.printStackTrace();
			throw new BusinessException(
					"efetionmanage.framework.exception.department.createDepartmentException");
		}

		return SUCCESS;
	}

	// public String toSetDeptKind() {
	//
	// Integer perKind = this.departmentManager.getDeptAuthWitch(this
	// .getUserSessionInfo().getEid());
	// this.getRequest().setAttribute("perKind", perKind);
	// return "tosetperkind";
	//
	// }

	// @GenericLogger(operateDescription = "部门权限管理-部门查看权限配置",
	// isOperateDepartment = true)
	// public String SetDeptPerKindOrUpdatePer() throws Exception {
	// UserSessionObj userinfo = this.getUserSessionInfo();
	// PrintWriter out = this.getResponse().getWriter();
	// try {
	// Integer eid = this.getUserSessionInfo().getEid();
	// this.getRequest().setCharacterEncoding("utf-8");
	// this.getResponse().setCharacterEncoding("utf-8");
	// this.getResponse().setContentType("text/xml");
	// String isUpdate = this.getRequest().getParameter("isUpdate");
	// String perKind = this.getRequest().getParameter("perKind");
	// if (isUpdate != null && isUpdate.equals("1")) {
	// this.departmentManager.setDeptPerKind(eid, Integer
	// .parseInt(perKind));
	// if (perKind.equals("0")) {
	// this.departmentManager.setAllDeptPer(eid);
	// } else {
	// this.departmentManager.setOnlyMyDeptPer(eid);
	// }
	// } else {
	// this.departmentManager.setDeptPerKind(eid, Integer
	// .parseInt(perKind));
	// }
	// out.write(perKind);
	// } catch (Exception e) {
	// out.write(-1);
	// logger.info(e.getMessage());
	// e.printStackTrace();
	// } finally {
	// out.close();
	// }
	// return null;
	// }
}
