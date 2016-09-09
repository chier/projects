package com.cmcc.framework.controller.action.department;

import java.util.List;

import com.cmcc.common.Global;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.model.department.DepartmentSearcher;

/**
 * 读取当前节点的部门
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-10
 */
public class ListDepartmentsAction extends WebActionBase {
	private static final long serialVersionUID = 6737791154539834354L;

	public String execute() throws DataException, BusinessException {

		UserSessionObj obj = this.getUserSessionInfo();
		Integer roleLevel = 1;
		if (obj != null) {
			roleLevel = obj.getRoleLevel();
		}
		String kind = this.getRequest().getParameter("kind");
		String groupIdStr = this.getRequest().getParameter("groupId");// 父id
		Integer groupId = 0;
		if (null != groupIdStr && !groupIdStr.equalsIgnoreCase("")) {
			groupId = Integer.parseInt(groupIdStr);
		}

		Integer eid = this.getUserSessionInfo().getEid();
		String searchText = this.getRequest().getParameter("searchText");
		String orderBy = this.getRequest().getParameter("orderBy");
		String order = this.getRequest().getParameter("order");

		DepartmentSearcher searchCond = new DepartmentSearcher();
		searchCond.setEid(eid);
		searchCond.setParentid(groupId);
		if (null != searchText && !searchText.equalsIgnoreCase("")) {
			searchCond.setName(searchText);
		}

		Page departmentsPageInfo = this.departmentManager.findBy(searchCond,
				Global.PAGESIZE);
		departmentsPageInfo.setSearchCondition(searchCond);
		String page = this.getRequest().getParameter("page");
		if (page != null) {
			departmentsPageInfo.setPage(new Integer(page).intValue());
		}

		if (orderBy != null && !orderBy.equalsIgnoreCase("")) {
			departmentsPageInfo.setOrderBy(orderBy);
			departmentsPageInfo.setOrder(order);
		}
		List<Department> departments = this.departmentManager
				.findByPage(departmentsPageInfo);
		for (Department d : departments) {
			if (d.getIdentifier().equals(-1)) {
				departments.remove(d);
				break;
			}
		}

		if (groupId == -1) {
			this.getRequest().setAttribute("deptName", "未分配部门");
		} else if (groupId != 0) {
			this.getRequest().setAttribute("deptName",
					departmentManager.findDeptById(groupId).getName());
		} else {
			this.getRequest().setAttribute("deptName", "组织管理");
		}

		StringBuffer sb = new StringBuffer();
		StringBuffer depDec = new StringBuffer();
		for (int i = 0; i < departments.size(); i++) {
			if (departments.get(i).getDescrption() != null) {
				depDec.append(departments.get(i).getDescrption().trim()
						.replaceAll(" ", "").replaceAll("　", ""));
				if (!depDec.equals("")) {
					String str = this.doString(sb, depDec.toString(), "<br/>",
							20);
					departments.get(i).setShowDes(str);
					sb.delete(0, sb.length());
				}
			}
			depDec.delete(0, depDec.length());

			if (departments.get(i).getParentId() == 0) {
				departments.get(i).setTips("");
			} else {
				searchCond.setParentid(null);
				searchCond.setDprtid(departments.get(i).getParentId());
				searchCond.setName(null);
				searchCond.setInitials(null);
				departments.get(i).setTips(
						this.departmentManager.findDeptById(
								departments.get(i).getParentId()).getName());
			}
		}
		this.getRequest().setAttribute("roleLevel", roleLevel);
		this.getRequest().setAttribute("orderBy", orderBy);
		this.getRequest().setAttribute("order", order);
		this.getRequest().setAttribute("searchText", searchText);
		this.getRequest().setAttribute("pageInfo", departmentsPageInfo);
		this.getRequest().setAttribute("departments", departments);
		this.getRequest().setAttribute("actionName", "listDepartments");
		this.getRequest().setAttribute("groupId", groupId);
		this.getRequest().setAttribute("oprate",
				this.getRequest().getParameter("operate"));
		this.getRequest().setAttribute("hasItem",
				this.getRequest().getParameter("hasItem"));
		this.getRequest().setAttribute("gaff10",
				this.getRequest().getParameter("gaff10"));
		return SUCCESS;
	}

	// public String goSortDep() {
	// String deptId = this.getRequest().getParameter("deptId");
	// Integer eId = this.getUserSessionInfo().getEid();
	// List<Department> depList = this.departmentManager.getDepByParentId(
	// Integer.parseInt(deptId), eId);
	// this.getRequest().setAttribute("groupId", deptId);
	//
	// if (depList != null && depList.size() > 0) {
	// this.getRequest().setAttribute("firstDeptid",
	// depList.get(0).getDprtid());
	// }
	// if (depList != null && depList.size() > 0) {
	// for (int i = 0; i < depList.size(); i++) {
	// String name = depList.get(i).getName();
	// depList.get(i).setTips(name);
	// String deptName = this.toDoString(new StringBuffer(), name
	// .toString(), "...", 24);
	// depList.get(i).setName(deptName);
	// }
	// }
	// this.getRequest().setAttribute("depList", depList);
	// String deptName = this.historyservice.getDeptNameByEidDepId(eId,
	// Integer.parseInt(deptId));
	// if (deptName != null && !deptName.equals("")) {
	// this.getRequest().setAttribute("deptName", deptName);
	// } else {
	// this.getRequest().setAttribute("deptName",
	// this.employeeManager.getEidName(eId));
	// }
	// return "gosort";
	// }
	//
	// @GenericLogger(operateMark = OperateMark.DEPARTMENT_BATCH_UPDATE,
	// operateDescription = "组织结构管理-部门排序", isOperateDepartment = true,
	// isRollback = true, isOperateRecords = true)
	// public String saveSort() throws IOException {
	//
	// String eid = String.valueOf(this.getUserSessionInfo().getEid());
	// String deptId = (String) this.getRequest().getParameter("groupId");
	// String idOrder = this.getRequest().getParameter("ids");
	// String[] ids = idOrder.split(",");
	// if (ids.length > 1) {
	// this.setOperateRecords(ids.length);// 日志需要,记录移动部门数量
	// this.departmentManager.SortDept(Integer.parseInt(eid), Integer
	// .parseInt(deptId), ids);
	// Integer poolId = PoolConfigInfoMap.get(eid).getPhysical_pool_id();
	// CompanyInfo company = this.companyservice.get(
	// Integer.parseInt(eid), poolId);
	// company.setOrgVersion(new Date());
	// this.companyservice.saveCompanyInfo(company, poolId);
	// }
	// this
	// .getResponse()
	// .sendRedirect(
	// this.getBasePath()
	// + "/manage/common/department/listDepartments.portal?groupId="
	// + deptId + "&flag=1");
	// return null;
	// }

	public String doString(StringBuffer sb, String roleDec, String str,
			int space) {
		if (StringUtil.getStrLength(roleDec, "gbk") <= space) {
			sb.append(roleDec);
		} else {
			sb.append(StringUtil.msubstr(roleDec, space, "gbk") + str);

			doString(sb, roleDec.substring(StringUtil.msubstr(roleDec, space,
					"gbk").length()), str, space);
		}
		return sb.toString();
	}

	public String toDoString(StringBuffer sb, String roleDec, String str,
			int space) {
		if (StringUtil.getStrLength(roleDec, "gbk") <= space) {
			sb.append(roleDec);
		} else {
			sb.append(StringUtil.msubstr(roleDec, space, "gbk") + str);

		}
		return sb.toString();
	}
}
