/*
 * 文件名： DepartmentOperatorAction.java
 * 
 * 创建日期： 2009-2-27
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.framework.controller.action.department;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

// import com.cmcc.common.cache.PoolConfigInfoMap;
import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.UserSessionObj;
// import com.cmcc.framework.model.corporation.CompanyInfo;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.model.department.DepartmentSearcher;

// import com.cmcc.framework.model.employee.Employee;
// import com.cmcc.framework.model.system.admin.WebAdmin;

/**
 * 所有部门操作检验入口action
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-10
 */
public class DepartmentOperatorAction extends WebActionBase {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(DepartmentOperatorAction.class);

	private static final long serialVersionUID = 1438471950672864671L;

	private String nextResult;

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

	/**
	 * 增加部门的输入校验
	 * 
	 * @return
	 * @throws DataException
	 * @throws BusinessException
	 */
	public String createInput() throws DataException, BusinessException {
		//		
		try {

			Integer groupId = new Integer(this.getRequest().getParameter(
					"groupId"));
			// Integer eid = this.getUserSessionInfo().getEid();
			// Integer poolId =
			// PoolConfigInfoMap.get(eid).getPhysical_pool_id();
			// List<Employee> userList = employeeManager.getEmployeeList(eid,
			// groupId);

			// if (null != userList && userList.size() > 0) {
			// this.getRequest().setCharacterEncoding("utf-8");
			// this.getResponse().setCharacterEncoding("utf-8");
			// this.getResponse().setContentType("text/xml");
			// out.print("haveEmployee");
			// return null;
			// }
			this.getRequest().setAttribute("nameInvalid",
					this.getRequest().getParameter("nameInvalid"));
			String shortName = "组织管理";
			String parentName = "";
			Integer parentId = 0;
			int adminSelectDept = -1; // 判断管理员的是否有全公司范围

			if (null != groupId && groupId != 0) {
				DepartmentSearcher searchCond = new DepartmentSearcher();
				searchCond.setDprtid(groupId);
				// searchCond.setEid(eid);
				// searchCond.setPoolid(poolId);
				// List<Department> list = this.departmentManager
				// .findBy(searchCond);
				Department dept = this.departmentManager.findDeptById(groupId);
				if (dept.getDepth() >= 8) {
					PrintWriter out = this.getResponse().getWriter();
					this.getRequest().setCharacterEncoding("utf-8");
					this.getResponse().setContentType("text/xml");
					out.print("endDept");
					return null;
				}
				parentId = dept.getIdentifier();
				parentName = dept.getName();
			} else {
				parentName = shortName;
			}

			this.getRequest().setAttribute("adminSelectDept", adminSelectDept);

			this.getRequest().setAttribute("nameMax",
					this.getRequest().getParameter("nameMax"));
			this.getRequest().setAttribute("decMax",
					this.getRequest().getParameter("decMax"));
			this.getRequest().setAttribute("noAssign",
					this.getRequest().getParameter("noAssign"));
			this.getRequest().setAttribute("endDept",
					this.getRequest().getParameter("endDept"));
			this.getRequest().setAttribute("shortName", shortName);
			this.getRequest().setAttribute("hasItem",
					this.getRequest().getParameter("hasItem"));
			this.getRequest().setAttribute("gaff10",
					this.getRequest().getParameter("gaff10"));
			this.getRequest().setAttribute("parentName", parentName);
			this.getRequest().setAttribute("parentId", parentId);
			this.getRequest().setAttribute("groupId", groupId);
			this.getRequest().setAttribute("actionName", "createDepartment");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException();
		} finally {
			// if (out != null)
			// out.close();
			// out = null;
		}
		return "create";
	}

	/**
	 * 增加部门的修改校验
	 * 
	 * @return
	 */
	public String editInput() throws DataException, BusinessException {
		// hasItem=true&groupId=12402&gId=12403
		try {
			Integer groupId = new Integer(this.getRequest().getParameter(
					"groupId"));
			Integer gId = new Integer(this.getRequest().getParameter("gId"));

			DepartmentSearcher searchCond = null;
//			searchCond.setDprtid(gId);
//			List<Department> list = this.departmentManager.findBy(searchCond);
			Department department = this.departmentManager.findDeptById(gId);

			String shortName = "组织管理";
			String parentName = shortName;
			if (!(department.getParentId().intValue() == 0)) {
				searchCond = new DepartmentSearcher();
				searchCond.setDprtid(department.getParentId());
				List<Department> departmenttmp = this.departmentManager
						.findBy(searchCond);
				parentName = departmenttmp != null && departmenttmp.size() > 0 ? departmenttmp
						.get(0).getName()
						: shortName;
			}

			// this.getRequest().setAttribute("adminSelectDept",
			// adminSelectDept);
			this.getRequest().setAttribute("nameMax",
					this.getRequest().getParameter("nameMax"));
			this.getRequest().setAttribute("decMax",
					this.getRequest().getParameter("decMax"));
			this.getRequest().setAttribute("noAssign",
					this.getRequest().getParameter("noAssign"));
			this.getRequest().setAttribute("shortName", shortName);

			this.getRequest().setAttribute("nameInvalid",
					this.getRequest().getParameter("nameInvalid"));

			this.getRequest().setAttribute("hasItem",
					this.getRequest().getParameter("hasItem"));

			this.getRequest().setAttribute("gaff10",
					this.getRequest().getParameter("gaff10"));

			this.getRequest().setAttribute("parentName", parentName);
			this.getRequest().setAttribute("department", department);
			this.getRequest().setAttribute("groupId", groupId);
			this.getRequest().setAttribute("actionName", "modifyDepartment");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return "edit";
	}

	// /**
	// * 移动部门
	// *
	// * @return
	// * @throws DataException
	// * @throws BusinessException
	// */
	// // 添加注解协助实现日志拦截
	// @GenericLogger(operateMark = OperateMark.DEPARTMENT_BATCH_UPDATE,
	// operateDescription = "组织结构管理-移动部门", isOperateDepartment = true,
	// isRollback = true, isOperateRecords = true)
	// public String move() throws DataException, BusinessException {
	//
	// try {
	// // 选中移动的部门ID字符串，以 "，"隔开。 zzl
	// String gIdStr = this.getRequest().getParameter("gIdstr");
	// // 移动目标部门的 id zzl
	// String gId = this.getRequest().getParameter("gId");
	//
	// Integer eid = this.getUserSessionInfo().getEid();
	// Integer poolId = PoolConfigInfoMap.get(eid).getPhysical_pool_id();
	// String[] gIdStrs = gIdStr.split(",");
	// this.setOperateRecords(gIdStrs.length);// 日志需要记录,移动部门的数量
	// ServletOutputStream out = this.getResponse().getOutputStream();
	// // 移动之后节点含有员工
	// if (departmentManager.hasEmployee(eid, Integer.parseInt(gId))) {
	// this.isRollback = true;
	// out.write("hasItem".getBytes());
	// return null;
	// }
	// DepartmentSearcher searchCond = new DepartmentSearcher();
	// searchCond.setParentid(Integer.parseInt(gId));
	// searchCond.setEid(eid);
	// searchCond.setPoolid(poolId);
	// // 移动目标部门下的子部门信息
	// List<Department> newDepartment = this.departmentManager
	// .getSubGroupsById(searchCond);
	// // 选中移动部门的信息
	// List<Department> oldDepartment = this.departmentManager
	// .getDepartmentsByIds(eid, poolId, gIdStrs);
	// // 同级别名称重复
	// for (int i = 0; i < newDepartment.size(); i++) {
	// for (int n = 0; n < oldDepartment.size(); n++) {
	// if (newDepartment.get(i).getName().equals(
	// oldDepartment.get(n).getName())) {
	// this.isRollback = true;
	// out.write("nameInvalid".getBytes());
	//
	// return null;
	// }
	// }
	// }
	//
	// searchCond.setParentid(null);
	// searchCond.setDprtid(Integer.parseInt(gId));
	// // 移动目标部门的信息
	// List<Department> parentList = this.departmentManager
	// .findBy(searchCond);
	//
	// searchCond.setDprtid(Integer.parseInt(gIdStrs[0]));
	// // 第一个选中移动部门的信息
	// // 对code的操作
	// if (null != parentList && parentList.size() > 0) {
	// // Department parent = parentList.get(0);
	// // String startCode = "";
	// // String endCode = "";
	// // if (null != oldparentList && oldparentList.size() > 0) {
	// // startCode = "0000";
	// // // 要移动的节点的code
	// //
	// // if (null != gIdStrs && gIdStrs.length > 1) {
	// // if (oldparentList.get(0).getParentId() != 0) {
	// // if (oldparentList.get(0).getCode().length() > 0) {
	// // startCode = oldparentList.get(0).getCode();
	// // }
	// // // 要移动的节点的所有子孙中最长的code
	// // // endCode = this.departmentManager.getMaxDeptCode(
	// // // eid, oldparentList.get(0).getParentId());
	// // String[] codes = new String[gIdStrs.length];
	// // for (int i = 0; i < gIdStrs.length; i++) {
	// // codes[i] = this.departmentManager
	// // .getMaxDeptCode(eid, oldparentList.get(
	// // 0).getParentId(), Integer
	// // .parseInt(gIdStrs[i]));
	// // }
	// // endCode = codes[0];
	// // for (int i = 0; i < codes.length - 1; i++) {
	// // if (codes[i].length() > codes[i + 1].length()) {
	// // endCode = codes[i + 1];
	// // }
	// // }
	// // } else {
	// // endCode = this.departmentManager.getMaxDeptCode(
	// // eid, oldparentList.get(0).getDprtid());
	// // }
	// // } else {
	// // // 取得当前节点
	// // // Integer parentid = Integer.parseInt(gIdStrs[0]);
	// // if (oldparentList.get(0).getParentId() != 0) {
	// // if (oldparentList.get(0).getCode().length() > 0) {
	// // startCode = oldparentList.get(0).getCode();
	// // }
	// // endCode = this.departmentManager.getMaxDeptCode(
	// // eid, oldparentList.get(0).getParentId(),
	// // oldparentList.get(0).getDprtid());
	// //
	// // } else {
	// // endCode = this.departmentManager.getMaxDeptCode(
	// // eid, oldparentList.get(0).getDprtid(),
	// // oldparentList.get(0).getDprtid());
	// // }
	// // }
	// //
	// // logger.info("|startCode=" + startCode + "|endCode="
	// // + endCode + "|msg=移动部门计算code");
	// // }
	// // // 这个地方是最深级别子孙code的长度减掉要移动的这一级节点code的长度，等于最深级别子孙code*4
	// // Integer endLength = 0;
	// // if (endCode.length() > startCode.length() - 4)
	// // endLength = parent.getCode().length()
	// // + endCode.substring(startCode.length() - 4,
	// // endCode.length()).length();
	// // System.out.println("dd");
	// // 判断是否移动到的父节点级别、或则加起来的最深子孙节点级别超过8级
	// // if (parent.getCode().length() > 32 || endLength > 32) {
	// // out.write("gaff10".getBytes());
	// // this.isRollback = true;
	// //
	// // return null;
	// // }
	// }
	// // 对节点深度的操作
	// if (null != parentList && parentList.size() > 0) {
	// // oldDepartment //选中移动部门的信息
	// // parentList //移动目标部门的信息
	// Department parent = parentList.get(0);
	// Short parentDepth = parent.getDepth();
	// Integer oldDeptId = null;
	// Short oldDepth = null;
	// Integer moveNum = null;
	// for (int i = 0; i < oldDepartment.size(); i++) {
	// oldDeptId = oldDepartment.get(i).getDprtid();
	// oldDepth = oldDepartment.get(i).getDepth();
	// // 取得deptId子节点最大的depth
	// Short maxDepth = this.departmentManager.getMaxDepth(eid,
	// oldDeptId, poolId);
	// moveNum = maxDepth.intValue() - oldDepth.intValue() + 1;
	//
	// if ((parentDepth + moveNum) > 8) {
	// out.write("gaff10".getBytes());
	// this.isRollback = true;
	// return null;
	// }
	// }
	// }
	// String names = this.departmentManager.moveGroupsByIds(eid, gIdStrs,
	// Integer.parseInt(gId), poolId, this.getUserSessionInfo()
	// .getId(), this.getUserSessionInfo().getRoleLevel());
	// this.setDepartmentNames(names);
	//
	// out.write("true".getBytes());
	// Integer[] deptids = new Integer[gIdStrs.length];
	// for (int i = 0; i < gIdStrs.length; i++) {
	// deptids[i] = Integer.valueOf(gIdStrs[i]);
	// }
	// this.employeeManager.saveEmppermissionPush(eid, poolId, deptids);
	// } catch (Exception e) {
	// logger.error("|msg=移动部门", e); //$NON-NLS-1$
	// throw new BusinessException();
	// }
	// return null;
	// }
	//
	/**
	 * 总入口
	 * 
	 * @return
	 * @throws DataException
	 * @throws BusinessException
	 */
	public String index() throws DataException, BusinessException {
		return "index";
	}

	//
	/**
	 * 删除操作
	 * 
	 * @return
	 * @throws DataException
	 * @throws BusinessException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	// 添加注解协助实现日志拦截
	public String delete() throws DataException, BusinessException, IOException {
		this.getRequest().setCharacterEncoding("utf-8");
		this.getResponse().setContentType("text/xml");
		PrintWriter out = this.getResponse().getWriter();
		try {
			Integer groupId = new Integer(this.getRequest().getParameter(
					"groupId"));
			List<String> allWillRemoveDepList = new ArrayList<String>();// 所有准备删除的部门
			// String child = this.getRequest().getParameter("child");
			String allRemove = this.getRequest().getParameter("allRemove");
			// String[] childArr = child.split(",");
			String[] allWillRemoveArr = allRemove.split(",");

			// this.setOperateRecords(allWillRemoveArr.length);// 日志需要， 删除部门数量

			DepartmentSearcher deptSearchCond = new DepartmentSearcher();
			deptSearchCond.setIds(allWillRemoveArr); // 根据id返回信息
			List<Department> removeList = this.departmentManager
					.findBy(deptSearchCond);// 删除成功后使用

			allWillRemoveDepList = Arrays.asList(allWillRemoveArr);
			this.departmentManager
					.deleteDeptAndSonDeptByDepId(allWillRemoveDepList);// 删除所有选择的部门以及子部门

			if (groupId != 0) {
				deptSearchCond = new DepartmentSearcher();
				deptSearchCond.setParentid(groupId);
				List<Department> nowChildList = this.departmentManager
						.findBy(deptSearchCond);
				if (nowChildList.size() <= 1) {// 此时父部门无子部门,修改父部门节点状态
					this.departmentManager.updateDepToLeafNode(groupId);
				}
			}
			StringBuffer depSb = new StringBuffer();
			for (int i = 0; i < removeList.size(); i++) {
				if (i == removeList.size() - 1) {
					depSb.append(removeList.get(i).getName());
				} else {
					depSb.append(removeList.get(i).getName() + ",");
				}
			}
			StringBuffer sb = new StringBuffer();
			String jsonObj = "{deptName:'"
					+ this.doString(sb, depSb.toString(), "", 30) + "'}";

			List<String> jsonList = new ArrayList<String>();
			jsonList.add(jsonObj);
			JSONArray jsonArray = JSONArray.fromObject(jsonList);
			out.print(jsonArray);
			// Integer[] deptids = new Integer[childArr.length];
			// for (int i = 0; i < childArr.length; i++) {
			// if (null != childArr[i] && childArr[i].trim().length() > 0) {
			// deptids[i] = Integer.valueOf(childArr[i]);
			// }
			// }
		} catch (Exception e) {
			out.write(0);
			logger.error("|msg=删除部门|", e);
			throw new BusinessException();
		} finally {
			out.close();
		}

		return null;
	}

	// public String batchInput() throws DataException, BusinessException {
	// return "batchInput";
	// }
	//
	// /**
	// * 请求导入部门excle模版
	// *
	// * @return
	// */
	// public String requesetTemplate() {
	// try {
	// String strFileName = "department.xls";
	// strFileName = java.net.URLEncoder.encode(strFileName, "UTF-8"); //
	// 处理中文文件名的问题
	// strFileName = new String(strFileName.getBytes("UTF-8"), "GBK"); //
	// 处理中文文件名的问题
	// this.getResponse().reset();
	// this.getResponse().setCharacterEncoding("UTF-8");
	// this.getResponse().setContentType("application/vnd.ms-excel");
	// this.getResponse().setHeader("Content-Disposition",
	// "attachment; filename=" + strFileName);
	// File f = new File(getSession().getServletContext().getRealPath("/")
	// + "static/configguide/staff.xls"); // 你的文件
	// if (this.getUserSessionInfo().getVersion().intValue() == 2) {
	// f = new File(getSession().getServletContext().getRealPath("/")
	// + "static/configguide/staff2.xls"); // 你的文件
	// }
	//
	// InputStream is = new FileInputStream(f); // 文件输入流
	//
	// ServletOutputStream out = this.getResponse().getOutputStream();
	// byte[] bs = new byte[1024]; // 读取缓冲区
	// int len;
	// while ((len = is.read(bs)) != -1) { // 循环读取
	// out.write(bs, 0, len); // 写入到输出流
	// }
	// out.flush();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// public String isHadEmp() throws Exception {
	//
	// this.getRequest().setCharacterEncoding("utf-8");
	// this.getResponse().setCharacterEncoding("utf-8");
	// this.getResponse().setContentType("text/xml");
	// PrintWriter out = this.getResponse().getWriter();
	// UserSessionObj userinfo = this.getUserSessionInfo();
	// try {
	// String[] depId = this.getRequest().getParameterValues("gId");
	// Integer poolId = PoolConfigInfoMap.get(userinfo.getEid())
	// .getPhysical_pool_id();
	// List<String> allChildDepList = new ArrayList<String>();// 准备删除部门中所有子部门
	// List<Integer> allParentDepList = new ArrayList<Integer>();// 准备删除部门中所有父部门
	// List<Integer> allWillRemoveDepList = new ArrayList<Integer>();//
	// 所有准备删除的部门
	// List<Department> removeList = this.departmentManager
	// .getDepartmentsByIds(userinfo.getEid(), poolId, depId);
	// int size = removeList.size();// 页面所选部门个数
	// Department tempDep = new Department();
	// for (int i = 0; i < size; i++) {
	// tempDep = removeList.get(i);
	// if (tempDep.getIsLeaf() == 1) {
	// allChildDepList.add(String.valueOf(tempDep.getDprtid()));
	// } else {
	// allParentDepList.add(tempDep.getDprtid());
	// }
	// allWillRemoveDepList.add(tempDep.getDprtid());
	// }
	// int parentSize = allParentDepList.size();
	// for (int i = 0; i < parentSize; i++) {
	//
	// List<Department> thisChildList = this.departmentManager
	// .getAllSubDepartment(userinfo.getEid(),
	// allParentDepList.get(i), poolId);
	// for (int k = 0; k < thisChildList.size(); k++) {
	// tempDep = thisChildList.get(k);
	// if (tempDep.getIsLeaf() == 1) {
	// allChildDepList
	// .add(String.valueOf(tempDep.getDprtid()));
	// }
	// if (!allWillRemoveDepList.contains(tempDep.getDprtid())) {
	// allWillRemoveDepList.add(tempDep.getDprtid());
	// }
	// }
	// }
	// StringBuffer allChild = new StringBuffer();
	// StringBuffer allParent = new StringBuffer();
	// StringBuffer allWillRemove = new StringBuffer();
	//
	// for (int i = 0; i < allChildDepList.size(); i++) {
	// if (i == allChildDepList.size() - 1) {
	// allChild.append(String.valueOf(allChildDepList.get(i)));
	// } else {
	// allChild.append(String.valueOf(allChildDepList.get(i))
	// + ",");
	// }
	// }
	// for (int i = 0; i < allParentDepList.size(); i++) {
	// if (i == allParentDepList.size() - 1) {
	// allParent.append(String.valueOf(allParentDepList.get(i)));
	// } else {
	// allParent.append(String.valueOf(allParentDepList.get(i))
	// + ",");
	// }
	// }
	// for (int i = 0; i < allWillRemoveDepList.size(); i++) {
	// if (i == allWillRemoveDepList.size() - 1) {
	// allWillRemove.append(String.valueOf(allWillRemoveDepList
	// .get(i)));
	// } else {
	// allWillRemove.append(String.valueOf(allWillRemoveDepList
	// .get(i))
	// + ",");
	// }
	// }
	// List<Employee> empList = this.employeeManager.getAllEmployeeList(
	// userinfo.getEid(), allChildDepList.toArray(new String[0]),
	// true);
	// String jsonObj = "{num:'" + empList.size() + "',child:'" + allChild
	// + "',parent:'" + allParent + "',allRemove:'"
	// + allWillRemove + "'}";
	// out.print(jsonObj);
	// } catch (Exception e) {
	// logger.error("|eid:" + userinfo.getEid() + "|loginid:"
	// + userinfo.getLoginId() + "|msg=" + logException(e));
	// throw new BusinessException("获取部门员工失败");
	// } finally {
	// out.close();
	// }
	// return null;
	// }
	//
	// private String logException(Exception e) {
	// StringWriter sw = new StringWriter();
	// PrintWriter pw = new PrintWriter(sw);
	// e.printStackTrace(pw);
	// pw.close();
	// return sw.toString();
	// }
	//
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
}
