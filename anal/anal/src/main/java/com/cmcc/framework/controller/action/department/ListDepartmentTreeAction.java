/*
 * 文件名： ListDepartmentTreeAction.java
 * 
 * 创建日期： 2009-2-19
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.framework.controller.action.department;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.io.XMLWriter;

import com.cmcc.common.Global;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.util.DomTreeBuilderUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.model.department.DepartmentSearcher;

/**
 * 
 * 获得企业组织结构tree的action
 * 
 * @author <a href="mailto:sun128837@126.com">conntsing</a>
 * 
 * @version $Revision: 1.15 $
 * 
 * @since 2009-2-18
 */
public class ListDepartmentTreeAction extends WebActionBase {

	private static final long serialVersionUID = 2906069871233739386L;

	private static final Log logger = LogFactory
			.getLog(ListDepartmentTreeAction.class);

	@SuppressWarnings("unchecked")
	public String execute() throws DataException, BusinessException {

		Integer eid = this.getUserSessionInfo().getEid();
		HttpServletRequest request = this.getRequest();
		String groupId = request.getParameter("groupId");
		// 组织操作树 userGroup
		String type = this.getRequest().getParameter("type");
		String kind = this.getRequest().getParameter("kind");//操作模块
		logger.info("groupId = " + groupId + " | type= " + type);
		DepartmentSearcher searchCond = new DepartmentSearcher();
		searchCond.setParentid(Integer.parseInt(groupId));
		searchCond.setEid(eid);
		try {
			if (groupId != null) {
				List<Department> resultList = null;
				resultList = this.departmentManager.findBy(searchCond);
				Department d = new Department();
				d.setName("未分配部门");
				d.setParentId(0);
				d.setDeptId(-1);
				d.setIsLeaf(1);
				d.setDescrption("未分配部门");
				d.setDepartmentOrder(0);
				resultList.add(d);

				this.getResponse().setContentType(
						"text/xml;charset=" + Global.CHARSET);
				ServletOutputStream stream = this.getResponse()
						.getOutputStream();

				XMLWriter writer = new XMLWriter(stream);

				if (type.equalsIgnoreCase("userGroup")) {
					if(kind != null && "admin".equalsIgnoreCase(kind)){
						writer.write(DomTreeBuilderUtil
								.createGroupDomTree(
										resultList,
										"listDepartmentTree."
												+ Global.ACTION_EXT,
										this.getBasePath()
												+ "/manage/safemanage/webadmin/listWebAdminAction."
												+ Global.ACTION_EXT,
										"type=userGroup&kind=" + kind + "treedate="
												+ new Date(), null,
										"right1"));
					}else{
						writer.write(DomTreeBuilderUtil
								.createGroupDomTree(
										resultList,
										"listDepartmentTree."
												+ Global.ACTION_EXT,
										this.getBasePath()
												+ "/manage/common/department/listDepartments."
												+ Global.ACTION_EXT,
										"type=userGroup&treedate="
												+ new Date(), null,
										"right1"));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BusinessException(ex.getMessage());
		}
		return null;
	}

	/**
	 * 请求部门入口
	 * 
	 * @return
	 * @throws DataException
	 * @throws BusinessException
	 */
	public String initDepartment() throws DataException, BusinessException {

		this.getRequest().setAttribute("rootGroup", getRootGroup());
		this.getRequest().setAttribute("type", "userGroup");
		String kind = this.getRequest().getParameter("kind");
		if(kind != null && "admin".equalsIgnoreCase(kind)){
			getRequest().setAttribute("kind", kind);
		}
		return "initDepartment";
	}

	/** 获取根节点 */
	public Department getRootGroup() {
		Integer eid = this.getUserSessionInfo().getEid();
		Department rootGroup = new Department();
		rootGroup.setDeptId(0);
		rootGroup.setName("组织管理");
		rootGroup.setEid(eid);
		return rootGroup;
	}
	//
	// /**
	// * 配置向导部门入口
	// *
	// * @return
	// * @throws DataException
	// * @throws BusinessException
	// */
	// public String initConfigguide() throws DataException, BusinessException {
	//
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "departmentconfig");
	//
	// return "initConfigguide";
	// }
	//
	// /**
	// * 请求员工入口
	// *
	// * @return
	// * @throws DataException
	// * @throws BusinessException
	// */
	// public String initEmployee() throws DataException, BusinessException {
	//
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "employee");
	//
	// return "initEmployee";
	// }
	//
	// /**
	// * 带权限检查的checkbox 部门多选 每个节点都可选
	// *
	// * @return
	// */
	// public String checkBox() throws DataException, BusinessException {
	//
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "userGroupCHK");
	// this.getRequest().setAttribute("isRootCheck", "false");
	// this.getRequest().setAttribute("checkType", "checkbox");
	//
	// return "checkBox";
	// }
	//
	// /**
	// * zhangzhanliang@live.cn 将数据全部取出，同步显示出来 当前主要运行于 电子调查模块 主要是通过树显示下面的方法 :
	// * executeXML <!-- 可以级联的权限检查的 checkbox 使用的是部门多选 -->
	// *
	// * @return
	// * @throws Exception
	// */
	// public String groupCheckBoxCase() throws Exception {
	//
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "userGroupCHK");
	// this.getRequest().setAttribute("isRootCheck", "false");
	// this.getRequest().setAttribute("checkType", "checkbox");
	//
	// String kind = this.getRequest().getParameter("kind");
	//
	// if (kind != null && kind.equals("questionnaire")) {// 问卷短信
	//
	// this.getRequest().setAttribute("kind", "questionnaire");
	// }
	//
	// // return "checkBoxCase";
	// return "checkBoxCaseXML";
	// }
	//
	// /**
	// * 可以级联的权限检查的checkbox(使用extree)
	// *
	// * 部门多选 每个节点都可选
	// *
	// * @return
	// */
	// public String checkBoxCase() throws DataException, BusinessException {
	//
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "employeeCHK");
	// this.getRequest().setAttribute("isRootCheck", "false");
	// this.getRequest().setAttribute("checkType", "checkbox");
	// this.getRequest().setAttribute("mclusterId",
	// this.getRequest().getParameter("mclusterId"));
	//
	// return "checkBoxCase";
	// }
	//
	// /**
	// * 可以级联的权限检查的checkbox(使用extree)
	// *
	// * 部门多选 每个节点都可选 因为运用的是同步，所以另建一个方法,专用于群组信息，添加成员时显示的部门树 zhangzhanliang 建
	// * 2009-07-21
	// *
	// * @return
	// */
	// public String checkBoxCaseSynchronization() throws DataException,
	// BusinessException {
	//
	// String kind = this.getRequest().getParameter("kind");
	//
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "employeeCHK");
	// this.getRequest().setAttribute("isRootCheck", "false");
	// this.getRequest().setAttribute("checkType", "checkbox");
	// this.getRequest().setAttribute("mclusterId",
	// this.getRequest().getParameter("mclusterId"));
	//
	// this.getRequest().setAttribute("kind",
	// this.getRequest().getParameter("kind"));
	//
	// // 群组添加成员
	// String searchValue = this.getRequest()
	// .getParameter("searchMemberValue");
	// this.getRequest().getSession().removeAttribute("m_all_user_of_dept_id");
	// if (searchValue != null) {
	// searchValue = StringUtil.urlDecode(searchValue, "UTF-8");
	// this.getRequest().setAttribute("searchMemberValue", searchValue);
	// this.getRequest().setAttribute("is_open_all", "true");
	// }
	//
	// /* 共享文件 */
	// if (kind != null && kind.equals("shareFileExtTree")) {
	// String onlyAllLeafperStr = "";
	// try {
	// Integer eid = this.getUserSessionInfo().getEid();
	// String dirIdStr = this.getRequest().getParameter("dirId");
	// if (dirIdStr != null && dirIdStr.trim().length() > 0) {
	// List<SharefilePermission> list = this.getSharefileManager()
	// .getDirPermission(eid, Integer.valueOf(dirIdStr));
	//
	// if (list != null) {
	// for (SharefilePermission permission : list) {
	// onlyAllLeafperStr += permission.getEmpId()
	// .toString()
	// + ",";
	// }
	// if (onlyAllLeafperStr.length() > 0) {
	// onlyAllLeafperStr = onlyAllLeafperStr.substring(0,
	// onlyAllLeafperStr.length() - 1);
	// }
	// }
	// }
	// } catch (Exception e) {
	// logger.error("msg=" + e.getMessage());
	// }
	//
	// this.getRequest().setAttribute("onlyAllLeafperStr",
	// onlyAllLeafperStr);
	//
	// return "shareFileExtTree";
	// }
	//
	// return "checkBoxCaseSynchronization";
	// }
	//
	// /**
	// * 带权限检查只要子节点的checkbox 部门多选 只有叶子节点都可选
	// *
	// * @return
	// * @throws Exception
	// */
	// @SuppressWarnings("unchecked")
	// public String checkBoxLeaf() throws Exception {
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "userGroupLeafCHK");
	// this.getRequest().setAttribute("isRootCheck", "false");
	// this.getRequest().setAttribute("checkType", "checkbox");
	//
	// /* 组织结构管理设置权限树start sw add */
	// String kind = this.getRequest().getParameter("kind");
	// if (kind != null && kind.equals("setper")) {// 判断是否是权限设置
	//
	// } else {
	// String deptIds = this.getRequest().getParameter("deptIds");
	// if (deptIds != null) {
	// this.getRequest().setAttribute("deptIds", deptIds);
	// }
	// }
	// /* 组织结构管理设置权限树end */
	//
	// return "checkBoxleaf";
	// }
	//
	// /** 用户使用多选 */
	// public String userCheckBox() {
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	//
	// this.getRequest().setAttribute("type", "employeeCHK");
	// this.getRequest().setAttribute("isRootCheck", "false");
	// this.getRequest().setAttribute("checkType", "checkbox");
	// return "checkBox";
	// }
	//
	// /**
	// * 不带权限检查的checkbox
	// *
	// * @return
	// */
	// public String checkBoxShare() throws DataException, BusinessException {
	//
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	//
	// this.getRequest().setAttribute("type", "userGroupCHK");
	// this.getRequest().setAttribute("isRootCheck", "true");
	// this.getRequest().setAttribute("checkType", "checkbox");
	//
	// return "checkBoxShare";
	// }
	//
	// /**
	// * create or modiry 管理员操作
	// * 管理范围使用多选 一次全部获取
	// */
	// public String initCheckBox() throws DataException, BusinessException {
	//
	// Integer eid = this.getUserSessionInfo().getEid();
	// List<Department> list = null; // json 所有部门
	// UserSessionObj objs = this.getUserSessionInfo();
	// Integer poolId = PoolConfigInfoMap.get(eid).getPhysical_pool_id();
	// if(objs.getRoleLevel() == 0 || objs.getVersion() != 2){
	// list = this.getDepartmentManager().getAll(eid,poolId);
	// }else{ // 校园版，普通管理员时，显示管理范围
	// String[] deptid = adminDeptManager
	// .getDepartmentIdsWithPermission(eid, this
	// .getUserSessionInfo().getId()); // 得到管理员管理范围内的 部门id 集合
	// list = this.departmentManager.getDepartmentsByIds(
	// eid, poolId, deptid); // 得到所有部门
	// }
	//		
	// String compName = companyservice.get(eid,
	// PoolConfigInfoMap.get(eid).getPhysical_pool_id())
	// .getShortName();
	// // zhangzhanliang@live.cn
	// List listDepartment = null;
	// String adminString = this.getRequest().getParameter("adminid");
	// if (!StringUtil.isBlank(adminString)
	// && StringUtil.isNumeric(adminString)) {
	//
	// WebAdmin admin = this.getWebAdminManager().get(
	// Integer.valueOf(adminString));
	// listDepartment = this.adminDeptManager.findByDepartmentIdsList(
	// admin.getEid(), admin.getIdentifier());
	//
	// }
	//
	// this.getRequest().setAttribute("listDepartment", listDepartment);
	// this.getRequest().setAttribute("compName", compName);
	// this.getRequest().setAttribute("jsonObj", list);
	// return "checkboxcase";
	// }
	//
	// /** 权限列表使用多选 一次全部获取 */
	// public String initPermissionCheckBox() throws DataException,
	// BusinessException {
	// UserSessionObj obj = this.getUserSessionInfo();
	// int versionId = obj.getVersion().intValue();//版本： 0 企业版 2 校园版
	// Integer level = obj.getRoleLevel(); //
	//		
	// List<VersionInfo> versionInfos = this.modelManager
	// .getModelInfoByVersionId(versionId, 1);
	// Integer versionLen = versionInfos.size();
	// Integer[] models = new Integer[versionLen];
	// for (int i = 0; i < versionLen; i++) {
	// // if(versionId == 2){
	// // Set<ModelInfo> setModelInfo = obj.getAllRole();
	// // }else{
	// models[i] = versionInfos.get(i).getModelId();
	// // }
	// }
	// List<ModelInfo> modelInfos = null;
	// if (models != null) {
	// if(versionId == 2 ){
	// if(level != 0){
	// Integer[] modelIds = this.modelManager
	// .getModelInfoByAdminId(obj.getId());
	// modelInfos = this.modelManager
	// .getModelInfoById(modelIds, 0);
	// }else{
	// modelInfos = this.modelManager.getModelInfoById(models, 0);
	// }
	// }else{
	// modelInfos = this.modelManager.getModelInfoById(models, 1);
	// }
	//			
	// }
	//		
	// this.getRequest().setAttribute("compName", "全选");
	//		
	// List<String> model2List = new ArraysUtil().getModel2List();
	// String sb = "";
	// ModelInfo info = null;
	// int sizeModel2 = 0;
	// for (int i = 0; i < modelInfos.size(); i++) {
	// info = (ModelInfo) modelInfos.get(i);
	//			
	// if(versionId == 2 &&
	// model2List.indexOf(info.getModelName()) == -1){
	// info = null;
	// }else{
	// sizeModel2 ++;
	// }
	// if(info != null){
	// if (i == modelInfos.size() - 1 || (sizeModel2 == model2List.size() &&
	// versionId == 2 )) {
	// sb = sb + "{dprtid:'" + info.getIdentifier() + "',name:'"
	// + info.getModelName() + "',isLeaf:'" + info.getIsLeaf()
	// + "',parentId:'" + info.getParentId() + "',state:'1'}";
	// } else {
	// sb = sb + "{dprtid:'" + info.getIdentifier() + "',name:'"
	// + info.getModelName() + "',isLeaf:'" + info.getIsLeaf()
	// + "',parentId:'" + info.getParentId() + "',state:'1'}"
	// + "|";
	// }
	// }
	//			
	// }
	// logger.info("jsonObj：" + sb);
	// this.getRequest().setAttribute("jsonObj", sb);
	// return "permissionCheckBox";
	// }
	//
	// /** 员工设置权限使用多选 一次全部获取 */
	// public String initCheckBoxForEmployee() throws DataException,
	// BusinessException {
	//
	// Integer eid = this.getUserSessionInfo().getEid();
	// List<Department> list = null;
	// list = this.getDepartmentManager().getAll(eid,
	// PoolConfigInfoMap.get(eid).getPhysical_pool_id());
	// String compName = companyservice.get(eid,
	// PoolConfigInfoMap.get(eid).getPhysical_pool_id())
	// .getShortName();
	// this.getRequest().setAttribute("compName", compName);
	// this.getRequest().setAttribute("jsonObj", list);
	//
	// String kind = this.getRequest().getParameter("kind");
	// if (kind != null && kind.equals("setEl")) {
	// String dept = this.getRequest().getParameter("deptIds");
	// String deptIds = "";
	// if (dept.indexOf(",") > 0) {
	// deptIds = dept.replaceAll(",", ";");
	// } else {
	// deptIds = this.getRequest().getParameter("deptIds") + ";";
	// }
	// this.getRequest().setAttribute("deptIds", deptIds);
	// }
	// return "checkboxcaseforemployeerole";
	// }
	//
	// /** 部门使用单选 */
	// public String radio() throws DataException, BusinessException {
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "userGroupCHK");
	// this.getRequest().setAttribute("isRootCheck", "true");
	// this.getRequest().setAttribute("checkType", "radio");
	// this.getRequest().setAttribute("parentId",
	// this.getRequest().getParameter("parentId"));
	// String readyUpDepId = this.getRequest().getParameter("readyUpDepId");
	// // this.getRequest().setAttribute("kinds",
	// // this.getRequest().getParameter("kinds"));//添加部门
	// this.getRequest().setAttribute("readyUpDepId", readyUpDepId);//
	// 准备修改部门的部门id(修改部门页面)
	// return "checkBox";
	// }
	//
	// /** 部门单选，只作用在子部门 */
	// public String radioLeaf() throws DataException, BusinessException {
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "userGroupLeafCHK");
	// this.getRequest().setAttribute("isRootCheck", "false");
	// this.getRequest().setAttribute("checkType", "radio");
	// this.getRequest().setAttribute("parentId",
	// this.getRequest().getParameter("parentId"));
	// return "checkBox";
	// }
	//
	// /** 权限管理，用户试用单选 * */
	// public String checkRadio() {
	//		
	// String isadmin = this.getRequest().getParameter("isadmin");
	//
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	//
	// this.getRequest().setAttribute("type", "employeeCheck");
	//
	// this.getRequest().setAttribute("isRootCheck", "false");
	//
	// this.getRequest().setAttribute("checkType", "radio");
	//		
	// this.getRequest().setAttribute("mclusterId",
	// this.getRequest().getParameter("mclusterId"));
	//		
	// this.getRequest().setAttribute("isadmin", isadmin);
	// // 员工列表选择
	// String searchValue = this.getRequest().getParameter("searchOwnerValue");
	//		
	// this.getRequest().getSession()
	// .removeAttribute("o_all_user_of_dept_id_admin"+this.getUserSessionInfo().getSessionId());
	// if (searchValue != null) {
	// searchValue = StringUtil.urlDecode(searchValue, "UTF-8");
	// this.getRequest().setAttribute("searchOwnerValue", searchValue);
	// }
	// return "checkBox";
	// }
	//
	// /**
	// * 超管与员工绑定，用户使用单选
	// * 暂时未用到，而且用的是修改联系人入口.
	// */
	// public String ckRadio() {
	//
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	//
	// this.getRequest().setAttribute("type", "employeeCK");
	//
	// this.getRequest().setAttribute("isRootCheck", "false");
	//
	// this.getRequest().setAttribute("checkType", "radio");
	// this.getRequest().setAttribute("mclusterId",
	// this.getRequest().getParameter("mclusterId"));
	//
	// return "checkBox";
	// }
	//
	// /** 用户使用单选 */
	// public String userRadio() {
	//		
	// String isadmin = this.getRequest().getParameter("isadmin");
	//		
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	//
	// this.getRequest().setAttribute("type", "employeeCHK");
	//
	// this.getRequest().setAttribute("isRootCheck", "false");
	//
	// this.getRequest().setAttribute("kind",
	// this.getRequest().getParameter("kind"));
	//
	// this.getRequest().setAttribute("checkType", "radio");
	// this.getRequest().setAttribute("oclusterId",
	// this.getRequest().getParameter("oclusterId"));
	//
	// this.getRequest().setAttribute("isadmin", isadmin);
	// // 群主选择
	// String searchValue = this.getRequest().getParameter("searchOwnerValue");
	//
	// this.getRequest().getSession().removeAttribute("o_all_user_of_dept_id");
	// if (searchValue != null) {
	// searchValue = StringUtil.urlDecode(searchValue, "UTF-8");
	// this.getRequest().setAttribute("searchOwnerValue", searchValue);
	// this.getRequest().setAttribute("is_open_all", "true");
	// }
	//
	// return "checkBox";
	// }
	//

	//
	// public String initMonitorUserTree() {
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "monitorUserFilter");
	// this.getRequest().setAttribute("isRootCheck", "false");
	// this.getRequest().setAttribute("checkType", "radio");
	// this.getRequest().setAttribute("kind",
	// this.getRequest().getParameter("kind"));
	//
	// return "monitorUserTree";
	// }
	//
	// public String initAddMonitorUserTree() throws DataException,
	// BusinessException {
	// this.getRequest().setAttribute("rootGroup", getRootGroup());
	// this.getRequest().setAttribute("type", "addMonitorUser");
	// this.getRequest().setAttribute("isRootCheck", "false");
	// this.getRequest().setAttribute("checkType", "radio");
	// this.getRequest().setAttribute("clusterFlag",
	// this.getRequest().getParameter("clusterFlag"));
	// this.getRequest().setAttribute("kind",
	// this.getRequest().getParameter("kind"));
	//
	// return "addMonitorUserTree";
	// }
	//
	// private void clusterMemberTree(Integer eid, String mclusterId,
	// String groupId, List<Department> resultList, XMLWriter writer)
	// throws NumberFormatException, BusinessException, IOException {
	//
	// String empValue = this.getRequest().getParameter("searchMemberValue");
	// empValue = StringUtil.urlDecode(empValue, "UTF-8");
	// List<Employee> userList = null;
	//
	// // 群成员检索
	// if (empValue != null && empValue.trim().length() > 0) {
	// // 以社员名为检索条件，检索出这个企业下的所有社员（不包含群成员）
	// List allUserOfParentDepartIdList = new ArrayList();
	// Object userIdObj = this.getRequest().getSession().getAttribute(
	// "m_all_user_of_dept_id");
	// if (userIdObj != null) {
	// allUserOfParentDepartIdList = (List) userIdObj;
	// } else {
	// List<Employee> allUserList = employeeManager
	// .getEmployeeListWithoutExistedClusterMember(eid, null,
	// Integer.parseInt(mclusterId), empValue);
	//
	// if (allUserList != null && allUserList.size() > 0) {
	// List<String> allUserOfDepartIdList = new ArrayList<String>();
	// for (Employee emp : allUserList) {
	// String deptIdStr = employeeManager.getDeptIds(eid, emp
	// .getUserId());
	// if (deptIdStr != null && deptIdStr.length() > 0) {
	// List<String> nextDeptIdList = Arrays
	// .asList(deptIdStr.split(","));
	// allUserOfDepartIdList.addAll(nextDeptIdList);
	// }
	// }
	// // 踢除重复元素
	// HashSet set = new HashSet(allUserOfDepartIdList);
	// allUserOfDepartIdList.clear();
	// allUserOfDepartIdList.addAll(set);
	// // 查出每个部门的所有父部门
	// if (allUserOfDepartIdList.size() > 0) {
	// for (String deptIdStr : allUserOfDepartIdList) {
	// List nextDeptList = this.departmentManager
	// .findAllNodeByDidEid(eid, new Integer(
	// deptIdStr));
	// allUserOfParentDepartIdList.addAll(nextDeptList);
	// }
	// }
	// this.getRequest().getSession().setAttribute(
	// "m_all_user_of_dept_id",
	// allUserOfParentDepartIdList);
	// }
	// }
	// userList = employeeManager
	// .getEmployeeListWithoutExistedClusterMember(eid, Integer
	// .parseInt(groupId), Integer.parseInt(mclusterId),
	// empValue);
	//
	// List<Department> removeList = new ArrayList<Department>();
	//
	// if (resultList != null && resultList.size() > 0) {
	// for (Department depart : resultList) {
	// if (depart.getDprtid().intValue() == -1) {
	// List noDepartOfUserList = employeeManager
	// .getEmployeeListWithoutExistedClusterMember(
	// eid, depart.getDprtid(), Integer
	// .parseInt(mclusterId), empValue);
	// if (noDepartOfUserList == null
	// || noDepartOfUserList.size() == 0) {
	// removeList.add(depart);
	// }
	// } else {
	// if (!allUserOfParentDepartIdList.contains(depart
	// .getDprtid())) {
	// removeList.add(depart);
	// }
	// }
	// }
	// if (removeList.size() > 0) {
	// resultList.removeAll(removeList);
	// }
	// }
	//
	// writer.write(DomTreeBuilderUtil.createItemDomCheckBoxTree(
	// resultList, userList, "listDepartmentTree."
	// + Global.ACTION_EXT, "type=employeeCHK&treedate="
	// + new Date()
	// + "&mclusterId="
	// + mclusterId
	// + "&kind=cluster"
	// + "&searchMemberValue="
	// + URLEncoder.encode(URLEncoder.encode(empValue,
	// "UTF-8"), "UTF-8")));
	// } else {
	// userList = employeeManager
	// .getEmployeeListWithoutExistedClusterMember(eid, Integer
	// .parseInt(groupId), Integer.parseInt(mclusterId),
	// null);
	//
	// writer.write(DomTreeBuilderUtil.createItemDomCheckBoxTree(
	// resultList, userList, "listDepartmentTree."
	// + Global.ACTION_EXT, "type=employeeCHK&treedate="
	// + new Date() + "&kind=cluster&mclusterId="
	// + mclusterId));
	// }
	// }
	//
	// /**
	// * 新建群组或者超管
	// * @param eid
	// * @param oclusterId
	// * @param groupId
	// * @param resultList
	// * @param writer
	// * @throws NumberFormatException
	// * @throws BusinessException
	// * @throws IOException
	// */
	// private void clusterOwnerTree(Integer eid, String oclusterId,
	// String groupId, List<Department> resultList, XMLWriter writer)
	// throws NumberFormatException, BusinessException, IOException {
	// // 新建群时要判断群主是否为非移动用户
	// Integer ocluId = null;
	// if (!"create".equals(oclusterId)) {
	// ocluId = Integer.parseInt(oclusterId);
	// }
	// String isadmin = this.getRequest().getParameter("isadmin");
	// String empValue = this.getRequest().getParameter("searchOwnerValue");
	// empValue = StringUtil.urlDecode(empValue, "UTF-8");
	// List<Employee> userList = null;
	// // 群成员检索
	// if (empValue != null && empValue.trim().length() > 0) {
	// // 以社员名为检索条件，检索出这个企业下的所有社员（不包含群成员）
	// List allUserOfParentDepartIdList = new ArrayList();
	// Object userIdObj = this.getRequest().getSession().getAttribute(
	// "o_all_user_of_dept_id");
	// if (userIdObj != null) {
	// allUserOfParentDepartIdList = (List) userIdObj;
	// } else {
	// List<Employee> allUserList = employeeManager
	// .getEmployeeListWithoutExistedClusterOwner(eid, null,
	// ocluId, empValue,isadmin);
	//
	// if (allUserList != null && allUserList.size() > 0) {
	// List<String> allUserOfDepartIdList = new ArrayList<String>();
	// for (Employee emp : allUserList) {
	// String deptIdStr = employeeManager.getDeptIds(eid, emp
	// .getUserId());
	// if (deptIdStr != null && deptIdStr.length() > 0) {
	// List<String> nextDeptIdList = Arrays
	// .asList(deptIdStr.split(","));
	// allUserOfDepartIdList.addAll(nextDeptIdList);
	// }
	// }
	// // 踢除重复元素
	// HashSet set = new HashSet(allUserOfDepartIdList);
	// allUserOfDepartIdList.clear();
	// allUserOfDepartIdList.addAll(set);
	//
	// // 查出每个部门的所有父部门
	// if (allUserOfDepartIdList.size() > 0) {
	// for (String deptIdStr : allUserOfDepartIdList) {
	// List nextDeptList = this.departmentManager
	// .findAllNodeByDidEid(eid, new Integer(
	// deptIdStr));
	// allUserOfParentDepartIdList.addAll(nextDeptList);
	// }
	// }
	// this.getRequest().getSession().setAttribute(
	// "o_all_user_of_dept_id",
	// allUserOfParentDepartIdList);
	// }
	// }
	//
	// userList = employeeManager
	// .getEmployeeListWithoutExistedClusterOwner(eid, Integer
	// .parseInt(groupId), ocluId, empValue,isadmin);
	//
	// List<Department> removeList = new ArrayList<Department>();
	// if (resultList != null && resultList.size() > 0) {
	// for (Department depart : resultList) {
	// if (depart.getDprtid().intValue() == -1) {
	// List noDepartOfUserList = employeeManager
	// .getEmployeeListWithoutExistedClusterOwner(eid,
	// depart.getDprtid(), ocluId, empValue,isadmin);
	// if (noDepartOfUserList == null
	// || noDepartOfUserList.size() == 0) {
	// removeList.add(depart);
	// }
	// } else {
	// if (!allUserOfParentDepartIdList.contains(depart
	// .getDprtid())) {
	// removeList.add(depart);
	// }
	// }
	// }
	// if (removeList.size() > 0) {
	// resultList.removeAll(removeList);
	// }
	// }
	//
	// writer.write(DomTreeBuilderUtil.createItemDomCheckBoxTree(
	// resultList, userList, "listDepartmentTree."
	// + Global.ACTION_EXT, "isadmin="+ isadmin
	// + "&type=employeeCHK&treedate="
	// + new Date()
	// + "&kind=cluster"
	// + "&oclusterId="
	// + oclusterId
	// + "&searchOwnerValue="
	// + URLEncoder.encode(URLEncoder.encode(empValue,
	// "UTF-8"), "UTF-8")));
	// } else {
	// userList = employeeManager
	// .getEmployeeListWithoutExistedClusterOwner(eid, Integer
	// .parseInt(groupId), ocluId, null,isadmin);
	// writer.write(DomTreeBuilderUtil.createItemDomCheckBoxTree(
	// resultList, userList, "listDepartmentTree."
	// + Global.ACTION_EXT, "isadmin="+ isadmin
	// + "&type=employeeCHK&treedate="
	// + new Date() + "&kind=cluster&oclusterId="
	// + oclusterId));
	// }
	// }
	//
	// /**
	// * 绑定管理员列表
	// * @param eid 企业 id
	// * @param groupId 父级部门Id
	// * @param resultList 部门集合
	// * @param writer 显示IO 流
	// * @throws NumberFormatException
	// * @throws BusinessException
	// * @throws IOException
	// */
	// private void clusterOwnerTree_CheckRadio(Integer eid,
	// String groupId, List<Department> resultList, XMLWriter writer)
	// throws NumberFormatException, BusinessException, IOException {
	// /**
	// * http session id
	// */
	// String sessionId = this.getUserSessionInfo().getSessionId();
	//		
	//		
	// UserSessionObj obj = this.getUserSessionInfo();
	// /**
	// * session user ids 员工ID集合
	// */
	// String sessionAllUserId = "o_all_user_of_dept_id_admin"+sessionId;
	//		
	// String empValue = this.getRequest().getParameter("searchOwnerValue");
	// String isadmin = this.getRequest().getParameter("isadmin");
	// empValue = StringUtil.urlDecode(empValue, "UTF-8");
	// List<Employee> userList = null;
	// // 群成员检索
	// if (empValue != null && empValue.trim().length() > 0) {
	// // 以社员名为检索条件，检索出这个企业下的所有社员（不包含群成员）
	// List allUserOfParentDepartIdList = new ArrayList();
	// Object userIdObj = this.getRequest().getSession().getAttribute(
	// sessionAllUserId);
	// if (userIdObj != null) {
	// allUserOfParentDepartIdList = (List) userIdObj;
	// } else {
	// List<Employee> allUserList = employeeManager
	// .getEmployeeListWithoutAdmin(eid, null, empValue,isadmin);
	//
	// if (allUserList != null && allUserList.size() > 0) {
	// List<String> allUserOfDepartIdList = new ArrayList<String>();
	// for (Employee emp : allUserList) {
	// String deptIdStr = employeeManager.getDeptIds(eid, emp
	// .getUserId());
	// if (deptIdStr != null && deptIdStr.length() > 0) {
	// List<String> nextDeptIdList = Arrays
	// .asList(deptIdStr.split(","));
	// allUserOfDepartIdList.addAll(nextDeptIdList);
	// }
	// }
	// // 踢除重复元素
	// HashSet set = new HashSet(allUserOfDepartIdList);
	// allUserOfDepartIdList.clear();
	// allUserOfDepartIdList.addAll(set);
	//
	// // 查出每个部门的所有父部门
	// if (allUserOfDepartIdList.size() > 0) {
	// for (String deptIdStr : allUserOfDepartIdList) {
	// List nextDeptList = this.departmentManager
	// .findAllNodeByDidEid(eid, new Integer(
	// deptIdStr));
	// allUserOfParentDepartIdList.addAll(nextDeptList);
	// }
	// }
	// this.getRequest().getSession().setAttribute(
	// sessionAllUserId,
	// allUserOfParentDepartIdList);
	// }
	// }
	//
	// userList = employeeManager
	// .getEmployeeListWithoutAdmin(eid, Integer
	// .parseInt(groupId), empValue,isadmin);
	//
	// List<Department> removeList = new ArrayList<Department>();
	// // 当为校园版，多级管理员时，显示的部门为管理范围内的部门。
	// if(obj.getVersion() == 0 && obj.getRoleLevel() != 0){
	// Integer poolId = PoolConfigInfoMap.get(eid).getPhysical_pool_id();
	//				
	// String[] deptid = adminDeptManager
	// .getDepartmentIdsWithPermission(eid, this
	// .getUserSessionInfo().getId());
	//				
	// try {
	// List<Department>tempList = new ArrayList<Department>();
	// List<Department> list = this.departmentManager.getDepartmentsByIds(
	// eid, poolId, deptid);
	// for(int i = 0; i< list.size();i++){
	// if(list.get(i).getIdentifier().equals(groupId)){
	// tempList.add(list.get(i));
	// }
	// }
	// Department d = new Department();
	// d.setName("未分配部门");
	// d.setParentId(0);
	// d.setDprtid(-1);
	// d.setIsLeaf(1);
	// d.setDescrption("未分配部门");
	// d.setDepartmentOrder(0);
	// tempList.add(d);
	// resultList = tempList;
	// } catch (DataException e) {
	// e.printStackTrace();
	// }
	// }
	//			
	// if (resultList != null && resultList.size() > 0) {
	// for (Department depart : resultList) {
	// if (depart.getDprtid().intValue() == -1) {
	// List noDepartOfUserList = employeeManager
	// .getEmployeeListWithoutAdmin(eid,
	// depart.getDprtid(), empValue,isadmin);
	// if (noDepartOfUserList == null
	// || noDepartOfUserList.size() == 0) {
	// removeList.add(depart);
	// }
	// } else {
	// if (!allUserOfParentDepartIdList.contains(depart
	// .getDprtid())) {
	// removeList.add(depart);
	// }
	// }
	// }
	// if (removeList.size() > 0) {
	// resultList.removeAll(removeList);
	// }
	// }
	//
	// writer.write(DomTreeBuilderUtil.createItemDomCheckBoxTree(
	// resultList, userList, "listDepartmentTree."
	// + Global.ACTION_EXT, "isadmin="+ isadmin
	// + "&type=employeeCheck&treedate="
	// + new Date()
	// + "&searchOwnerValue="
	// + URLEncoder.encode(URLEncoder.encode(empValue,
	// "UTF-8"), "UTF-8")));
	// } else {
	//			
	// // 当为校园版，多级管理员时，显示的部门为管理范围内的部门。
	// if(obj.getVersion() == 0 && obj.getRoleLevel() != 0){
	// Integer poolId = PoolConfigInfoMap.get(eid).getPhysical_pool_id();
	//				
	// String[] deptid = adminDeptManager
	// .getDepartmentIdsWithPermission(eid, this
	// .getUserSessionInfo().getId());
	//				
	// try {
	// List<Department>tempList = new ArrayList<Department>();
	// List<Department> list = this.departmentManager.getDepartmentsByIds(
	// eid, poolId, deptid);
	// for(int i = 0; i< list.size();i++){
	// if(list.get(i).getIdentifier().equals(groupId)){
	// tempList.add(list.get(i));
	// }
	// }
	// Department d = new Department();
	// d.setName("未分配部门");
	// d.setParentId(0);
	// d.setDprtid(-1);
	// d.setIsLeaf(1);
	// d.setDescrption("未分配部门");
	// d.setDepartmentOrder(0);
	// tempList.add(d);
	// resultList = tempList;
	// } catch (DataException e) {
	// e.printStackTrace();
	// }
	// }
	// userList = employeeManager
	// .getEmployeeListWithoutAdmin(eid, Integer
	// .parseInt(groupId), null,isadmin);
	//
	// writer.write(DomTreeBuilderUtil.createItemDomCheckBoxTree(
	// resultList, userList, "listDepartmentTree."
	// + Global.ACTION_EXT, "isadmin="+ isadmin
	// + "&type=employeeCheck&treedate="
	// + new Date()));
	// }
	// }
	//
	// private void monitorUserTree(Integer eid, String groupId,
	// List<Department> resultList, XMLWriter writer)
	// throws NumberFormatException, BusinessException, IOException {
	// String base = this.getBasePath();
	// List<Employee> userList = null;
	// List allUserOfParentDepartIdList = new ArrayList();
	// Object userIdObj = this.getRequest().getSession().getAttribute(
	// "monitor_all_user_of_dept_id");
	// if (userIdObj != null) {
	// allUserOfParentDepartIdList = (List) userIdObj;
	// } else {
	// List<MonitorUser> monitorUserList = monitorUserManager
	// .getMonitorUserList(eid);
	//
	// if (monitorUserList != null && monitorUserList.size() > 0) {
	// List<String> allUserOfDepartIdList = new ArrayList<String>();
	// for (MonitorUser monitorUser : monitorUserList) {
	// String deptIdStr = employeeManager.getDeptIds(eid,
	// monitorUser.getUserid());
	// if (deptIdStr != null && deptIdStr.length() > 0) {
	// List<String> nextDeptIdList = Arrays.asList(deptIdStr
	// .split(","));
	// allUserOfDepartIdList.addAll(nextDeptIdList);
	// }
	// }
	// // 踢除重复元素
	// HashSet set = new HashSet(allUserOfDepartIdList);
	// allUserOfDepartIdList.clear();
	// allUserOfDepartIdList.addAll(set);
	//
	// // 查出每个部门的所有父部门
	// if (allUserOfDepartIdList.size() > 0) {
	// for (String deptIdStr : allUserOfDepartIdList) {
	// List nextDeptList = this.departmentManager
	// .findAllNodeByDidEid(eid,
	// new Integer(deptIdStr));
	// allUserOfParentDepartIdList.addAll(nextDeptList);
	// }
	// }
	// this.getRequest().getSession().setAttribute(
	// "monitor_all_user_of_dept_id",
	// allUserOfParentDepartIdList);
	// }
	// }
	//
	// // 过滤用户
	// List<MonitorUser> monitorUserList = monitorUserManager
	// .getMonitorUserList(eid);
	// List<Integer> monitorUserIdList = new ArrayList<Integer>();
	// for (MonitorUser monitorUser : monitorUserList) {
	// monitorUserIdList.add(monitorUser.getUserid());
	// }
	// userList = employeeManager.getEmployeeList(eid, Integer
	// .parseInt(groupId));
	// if (userList != null && userList.size() > 0) {
	// List<Employee> removeEmpList = new ArrayList<Employee>();
	// for (Employee emp : userList) {
	// if (!monitorUserIdList.contains(emp.getUserId())) {
	// removeEmpList.add(emp);
	// }
	// }
	// if (removeEmpList.size() > 0) {
	// userList.removeAll(removeEmpList);
	// }
	// }
	//
	// List<Department> removeDepartList = new ArrayList<Department>();
	// if (resultList != null && resultList.size() > 0) {
	// for (Department depart : resultList) {
	// if (depart.getDprtid().intValue() == -1) {
	// boolean unassign = false;
	// for (MonitorUser monitorUser : monitorUserList) {
	// String deptIdStr = employeeManager.getDeptIds(eid,
	// monitorUser.getUserid());
	// if (deptIdStr != null && deptIdStr.length() == 0) {
	// unassign = true;
	// break;
	// }
	// }
	// if (!unassign) {
	// removeDepartList.add(depart);
	// }
	// } else {
	// if (!allUserOfParentDepartIdList.contains(depart
	// .getDprtid())) {
	// removeDepartList.add(depart);
	// }
	// }
	// }
	// if (removeDepartList.size() > 0) {
	// resultList.removeAll(removeDepartList);
	// }
	// }
	//
	// writer.write(DomTreeBuilderUtil.createItemDomTree(resultList, userList,
	// "listDepartmentTree." + Global.ACTION_EXT,
	// "type=monitorUserFilter&kind=monitorUser&treedate="
	// + new Date(), "listMessageFrame", base, null));
	// }
	//
	// private void addMonitorUserTree(Integer eid, String groupId,
	// List<Department> resultList, XMLWriter writer, String clusterFlag)
	// throws NumberFormatException, BusinessException, IOException {
	//
	// String base = this.getBasePath();
	// writer.write(DomTreeBuilderUtil.createItemDomTree(resultList, null,
	// "listDepartmentTree." + Global.ACTION_EXT,
	// "type=addMonitorUser&kind=monitorUser&treedate=" + new Date(),
	// "frameEmp", base, clusterFlag));
	// }
	//
	// public String getEmp() throws Exception {
	// PrintWriter out = this.getResponse().getWriter();
	// UserSessionObj userinfo = this.getUserSessionInfo();
	// try {
	// this.getRequest().setCharacterEncoding("utf-8");
	// this.getResponse().setCharacterEncoding("utf-8");
	// this.getResponse().setContentType("text/xml");
	// String deptId = this.getRequest().getParameter("deptId");
	// String userStr = this.getRequest().getParameter("userInfo");
	// if (userStr != null && !userStr.trim().equals("")) {
	// userStr = java.net.URLDecoder.decode(
	// this.getRequest().getParameter("userInfo"), "UTF-8")
	// .trim();
	// }
	//
	// String pcoreVcardTableName = GetTableNmae
	// .getPcoreVcardTableName(userinfo.getEid().longValue());
	//
	// String pcoreCorpEmployeeTableName = GetTableNmae
	// .getPcoreCorpEmployeeTableName(userinfo.getEid()
	// .longValue());
	// Integer poolId = PoolConfigInfoMap.get(userinfo.getEid())
	// .getPhysical_pool_id();
	// List<MsmInfoBean> list = new ArrayList<MsmInfoBean>();
	// String jsonObj = "";
	// StringBuffer allDeptIds = new StringBuffer(100);
	// if (deptId.equals("all")) {
	//
	// if (this.getUserSessionInfo().getRoleLevel().equals(0)) {
	//
	// String allLeafDeptIds = this.departmentManager
	// .getAllLeaftDepart(userinfo.getEid());
	// if (allLeafDeptIds == null || allLeafDeptIds.equals("")) {
	// allDeptIds.append("-1");
	// } else {
	// allDeptIds.append(allLeafDeptIds);
	// allDeptIds.append(",-1");
	// }
	// } else {
	//
	// String[] rightDepts = adminDeptManager
	// .getDepartmentIdsWithPermission(userinfo.getEid(),
	// this.getUserSessionInfo().getId());
	// if (rightDepts != null && rightDepts.length > 0) {
	// for (int i = 0; i < rightDepts.length; i++) {
	//
	// if (i == rightDepts.length - 1) {
	// allDeptIds.append(rightDepts[i]);
	// } else {
	// allDeptIds.append(rightDepts[i]).append(",");
	// }
	// }
	// allDeptIds.append(",-1");
	// } else {
	// allDeptIds.append("-1");
	// }
	//
	// }
	// list = this.empinfoservice.getEmployeeInfoByDeptId(allDeptIds
	// .toString(), pcoreVcardTableName,
	// pcoreCorpEmployeeTableName, poolId, userinfo.getEid(),
	// userStr);
	//
	// } else {
	//
	// List<Department> allNeedSetDeptList = new ArrayList<Department>();
	//
	// if (deptId.equals("-1")) {
	// allDeptIds = allDeptIds.append("-1");
	// } else {
	// allNeedSetDeptList = this.departmentManager
	// .getAllSubDepartment(userinfo.getEid(), Integer
	// .parseInt(deptId), poolId);// 该部门下所有子节点。
	// if (allNeedSetDeptList != null
	// && allNeedSetDeptList.size() > 0) {
	//
	// String[] rightDepts;
	// if (this.getUserSessionInfo().getRoleLevel().equals(0)) {
	// List<String> tempList = new ArrayList<String>();
	// for (int i = 0; i < allNeedSetDeptList.size(); i++) {
	// tempList.add(String.valueOf(allNeedSetDeptList
	// .get(i).getDprtid()));
	// }
	// rightDepts = tempList.toArray(new String[0]);
	// } else {
	// rightDepts = adminDeptManager
	// .getDepartmentIdsWithPermission(userinfo
	// .getEid(), this
	// .getUserSessionInfo().getId()); // 该管理员权限范围内的部门节点
	// }
	// List<String> leafList = new ArrayList<String>();
	//
	// leafList = Arrays.asList(rightDepts);
	//
	// List<String> rightLeafList = new ArrayList<String>();// 该部门下有权限的子节点
	//
	// for (int i = 0; i < allNeedSetDeptList.size(); i++) {
	//
	// if (leafList.contains(String
	// .valueOf(allNeedSetDeptList.get(i)
	// .getDprtid()))
	// && allNeedSetDeptList.get(i).getIsLeaf()
	// .equals(1)) {
	// rightLeafList.add(String
	// .valueOf(allNeedSetDeptList.get(i)
	// .getDprtid()));
	//
	// }
	// }
	//
	// if (rightLeafList != null && rightLeafList.size() > 0) {
	// for (int i = 0; i < rightLeafList.size(); i++) {
	// if (i == rightLeafList.size() - 1) {
	// allDeptIds.append(rightLeafList.get(i));
	//
	// } else {
	// allDeptIds.append(rightLeafList.get(i))
	// .append(",");
	//
	// }
	// }
	// }
	//
	// } else {
	// allDeptIds.append("-1");
	// }
	//
	// }
	//
	// list = this.empinfoservice.getEmployeeInfoByDeptId(allDeptIds
	// .toString(), pcoreVcardTableName,
	// pcoreCorpEmployeeTableName, poolId, userinfo.getEid(),
	// userStr);
	//
	// }
	// StringBuffer userObj = new StringBuffer();
	// userObj.append("{userJson:[");
	// int size = list.size();
	// if (size > 0) {
	// for (int i = 0; i < size; i++) {
	//
	// if (i == size - 1) {
	// userObj.append("{userId:'" + list.get(i).getUserId()
	// + "',mp:'" + list.get(i).getMp()
	// + "',userName:'"
	// + toHtml(list.get(i).getUserRealName())
	// + "'}]}");
	// } else {
	// userObj.append("{userId:'" + list.get(i).getUserId()
	// + "',mp:'" + list.get(i).getMp()
	// + "',userName:'"
	// + toHtml(list.get(i).getUserRealName()) + "'}"
	// + ",");
	// }
	//
	// }
	// } else {
	// userObj.append("]}");
	// }
	//
	// List<String> jsonList = new ArrayList<String>();
	// jsonList.add(userObj.toString());
	// JSONArray jsonArray = JSONArray.fromObject(jsonList);
	// out.print(jsonArray);
	// return null;
	// } catch (Exception e) {
	// logger.error("|eid:" + userinfo.getEid() + "|loginid:"
	// + userinfo.getLoginId() + "|msg=" + logException(e));
	// out.print("-1");
	// } finally {
	// out.close();
	// }
	// return null;
	//
	// }
	// private String parseDeptmentArray(List<Department> dList){
	// String strDepement = "";
	// if(null!=dList){
	// for(Department dpt:dList){
	// strDepement += dpt.getDprtid() + ",";
	// }
	// strDepement = strDepement.length() > 0 ?
	// strDepement.substring(0,strDepement.length()-1):strDepement;
	// }
	// return strDepement;
	// }
	//    
	// private String parseDepementArray(String []allDeptIdsWithPermission){
	// String strDepement = "";
	// if(null!=allDeptIdsWithPermission){
	// for(String deptId:allDeptIdsWithPermission){
	// strDepement += deptId + ",";
	// }
	// strDepement = strDepement.length() > 0 ?
	// strDepement.substring(0,strDepement.length()-1):strDepement;
	// }
	// return strDepement;
	// }
	//    
	// public String getQuestionEmp() throws Exception {
	// PrintWriter out = this.getResponse().getWriter();
	// UserSessionObj userinfo = this.getUserSessionInfo();
	// try {
	// this.getRequest().setCharacterEncoding("utf-8");
	// this.getResponse().setCharacterEncoding("utf-8");
	// this.getResponse().setContentType("text/xml");
	// String deptId = this.getRequest().getParameter("deptId");
	// String userStr = this.getRequest().getParameter("userInfo");
	// if (userStr != null && !userStr.trim().equals("")) {
	// userStr = java.net.URLDecoder.decode(
	// this.getRequest().getParameter("userInfo"), "UTF-8")
	// .trim();
	// }
	//
	// String pcoreVcardTableName = GetTableNmae
	// .getPcoreVcardTableName(userinfo.getEid().longValue());
	//
	// String pcoreCorpEmployeeTableName = GetTableNmae
	// .getPcoreCorpEmployeeTableName(userinfo.getEid()
	// .longValue());
	// Integer poolId = PoolConfigInfoMap.get(userinfo.getEid())
	// .getPhysical_pool_id();
	// List<MsmInfoBean> list = new ArrayList<MsmInfoBean>();
	// String jsonObj = "";
	// StringBuffer allDeptIds = new StringBuffer(100);
	// if (deptId.equals("all")) {
	//
	// if (this.getUserSessionInfo().getRoleLevel().equals(0)) {
	//
	// String allLeafDeptIds = this.departmentManager
	// .getAllLeaftDepart(userinfo.getEid());
	// if (allLeafDeptIds == null || allLeafDeptIds.equals("")) {
	// allDeptIds.append("-1");
	// } else {
	// allDeptIds.append(allLeafDeptIds);
	// allDeptIds.append(",-1");
	// }
	// } else {
	//
	// String[] rightDepts = adminDeptManager
	// .getDepartmentIdsWithPermission(userinfo.getEid(),
	// this.getUserSessionInfo().getId());
	// if (rightDepts != null && rightDepts.length > 0) {
	// for (int i = 0; i < rightDepts.length; i++) {
	//
	// if (i == rightDepts.length - 1) {
	// allDeptIds.append(rightDepts[i]);
	// } else {
	// allDeptIds.append(rightDepts[i]).append(",");
	// }
	// }
	// allDeptIds.append(",-1");
	// } else {
	// allDeptIds.append("-1");
	// }
	//
	// }
	// list = this.empinfoservice.getEmployeeInfoByDeptIdForQuestion(
	// allDeptIds.toString(), pcoreVcardTableName,
	// pcoreCorpEmployeeTableName, poolId, userinfo.getEid(),
	// userStr);
	//
	// } else {
	//
	//
	// String queryDeptIds = "";
	// if (deptId.equals("-1")) {
	// queryDeptIds = "-1";
	// } else {
	// if(0==this.getUserSessionInfo().getRoleLevel()){
	// //超级管理员
	// List<Department> dList =
	// this.departmentManager.getAllSubDepartment(this.getUserSessionInfo().getEid(),
	// Integer.parseInt(deptId), poolId);
	// queryDeptIds = parseDeptmentArray(dList);
	// }else{
	// //普通管理员
	// UserSessionObj userObj = getUserSessionInfo();
	// String []allDeptIdsWithPermission =
	// employeeManager.getDeptIdsWithPermission(userObj,
	// Integer.valueOf(deptId));
	// queryDeptIds = this.parseDepementArray(allDeptIdsWithPermission);
	//						
	// }
	// // allNeedSetDeptList = this.departmentManager
	// // .getAllSubDepartment(userinfo.getEid(), Integer
	// // .parseInt(deptId), poolId);
	// // if (allNeedSetDeptList != null
	// // && allNeedSetDeptList.size() > 0) {
	// // for (int i = 0; i < allNeedSetDeptList.size(); i++) {
	// //
	// // if (i == allNeedSetDeptList.size() - 1) {
	// // allDeptIds.append(allNeedSetDeptList.get(i)
	// // .getDprtid());
	// //
	// // } else {
	// // allDeptIds.append(
	// // allNeedSetDeptList.get(i).getDprtid())
	// // .append(",");
	// //
	// // }
	// // }
	// // } else {
	// // allDeptIds.append("-1");
	// // }
	//
	// }
	//
	// list = this.empinfoservice.getEmployeeInfoByDeptIdForQuestion(
	// queryDeptIds, pcoreVcardTableName,
	// pcoreCorpEmployeeTableName, poolId, userinfo.getEid(),
	// userStr);
	//
	// }
	// StringBuffer userObj = new StringBuffer();
	// userObj.append("{userJson:[");
	// String userMark;// 有手机号则为手机号,否则为飞信号,都没有则为"".发到页面起标识作用
	// int size = list.size();
	// if (size > 0) {
	// for (int i = 0; i < size; i++) {
	//
	// if (list.get(i).getMp() != null
	// && !list.get(i).getMp().equals("")) {
	// userMark = String.valueOf(list.get(i).getMp());
	// } else if (list.get(i).getPhone() != null
	// && !list.get(i).getPhone().equals("")) {
	//
	// userMark = String.valueOf(list.get(i).getPhone());
	// } else if (list.get(i).getFid() != null
	// && !list.get(i).getFid().equals("")
	// && list.get(i).getFid().intValue() > 0) {
	// userMark = String.valueOf(list.get(i).getFid());
	// } else {
	// userMark = "";
	// }
	//
	// if (i == size - 1) {
	// userObj.append("{userId:'" + list.get(i).getUserId()
	// + "',userName:'"
	// + toHtml(list.get(i).getUserRealName())
	// + "',userMark:'" + userMark + "'}]}");
	// } else {
	// userObj.append("{userId:'" + list.get(i).getUserId()
	// + "',userName:'"
	// + toHtml(list.get(i).getUserRealName())
	// + "',userMark:'" + userMark + "'}" + ",");
	// }
	//
	// }
	// } else {
	// userObj.append("]}");
	// }
	//
	// List<String> jsonList = new ArrayList<String>();
	// jsonList.add(userObj.toString());
	// JSONArray jsonArray = JSONArray.fromObject(jsonList);
	// out.print(jsonArray);
	// return null;
	// } catch (Exception e) {
	// logger.error("|eid:" + userinfo.getEid() + "|loginid:"
	// + userinfo.getLoginId() + "|msg=" + logException(e));
	// out.print("-1");
	// } finally {
	// out.close();
	// }
	// return null;
	//
	// }
	//
	// public String getChooseUserMp() throws Exception {
	//
	// PrintWriter out = this.getResponse().getWriter();
	// UserSessionObj userinfo = this.getUserSessionInfo();
	// try {
	// this.getRequest().setCharacterEncoding("utf-8");
	// this.getResponse().setCharacterEncoding("utf-8");
	// this.getResponse().setContentType("text/xml");
	// String ChoosedUserName = this.getRequest().getParameter(
	// "ChoosedUserName");
	// if (ChoosedUserName != null && !ChoosedUserName.trim().equals("")) {
	// ChoosedUserName = java.net.URLDecoder.decode(ChoosedUserName,
	// "UTF-8").trim();
	// }
	//
	// String pcoreVcardTableName = GetTableNmae
	// .getPcoreVcardTableName(userinfo.getEid().longValue());
	//
	// String pcoreCorpEmployeeTableName = GetTableNmae
	// .getPcoreCorpEmployeeTableName(userinfo.getEid()
	// .longValue());
	// Integer poolId = PoolConfigInfoMap.get(userinfo.getEid())
	// .getPhysical_pool_id();
	// List<MsmInfoBean> list = new ArrayList<MsmInfoBean>();
	// String jsonObj = "";
	// StringBuffer allDeptIds = new StringBuffer(100);
	//
	// String[] rightDepts = adminDeptManager
	// .getDepartmentIdsWithPermission(userinfo.getEid(), this
	// .getUserSessionInfo().getId());
	// if (rightDepts != null) {
	// for (int i = 0; i < rightDepts.length; i++) {
	//
	// if (i == rightDepts.length - 1) {
	// allDeptIds.append(rightDepts[i]);
	//
	// } else {
	// allDeptIds.append(rightDepts[i]).append(",");
	//
	// }
	// }
	// }
	// allDeptIds.append(",-1");
	// String kind = this.getRequest().getParameter("kind");
	// if (kind != null && kind.equals("smg")) {
	// list = this.empinfoservice.getEmployeeInfoByDeptIdNoLike(
	// allDeptIds.toString(), pcoreVcardTableName,
	// pcoreCorpEmployeeTableName, poolId, userinfo.getEid(),
	// ChoosedUserName, userinfo.getRoleLevel());
	// } else {
	// String allLeafDeptIds = this.departmentManager
	// .getAllLeaftDepart(userinfo.getEid());
	//				
	// if(allLeafDeptIds!=null&&!allLeafDeptIds.equals("")){
	//					
	// allLeafDeptIds = allLeafDeptIds + ",-1";
	//					
	// }else{
	// allLeafDeptIds = "-1";
	// }
	//
	// //allLeafDeptIds = allLeafDeptIds + ",-1";
	// list = this.empinfoservice.getEmployeeInfoByDeptIdAllNoLike(
	// allLeafDeptIds, pcoreVcardTableName,
	// pcoreCorpEmployeeTableName, poolId, userinfo.getEid(),
	// ChoosedUserName, userinfo.getRoleLevel());
	// }
	//
	// if (list.size() > 0) {
	// StringBuffer empNames = new StringBuffer(100);
	// if (kind != null && kind.equals("smg")) {
	// for (int i = 0; i < list.size(); i++) {
	// if (i == list.size() - 1) {
	// empNames.append(list.get(i).getUserRealName())
	// .append("(" + list.get(i).getMp() + ")");
	// } else {
	// empNames.append(list.get(i).getUserRealName())
	// .append("(" + list.get(i).getMp() + ")")
	// .append(",");
	// }
	// }
	// } else {
	// String userMark;
	// for (int i = 0; i < list.size(); i++) {
	// if (list.get(i).getMp() != null
	// &&
	// !list.get(i).getMp().equals("")&&!"null".equals(list.get(i).getMp().toString()))
	// {
	// userMark = String.valueOf(list.get(i).getMp());
	// } else if (list.get(i).getPhone() != null
	// &&
	// !list.get(i).getPhone().equals("")&&!"null".equals(list.get(i).getPhone().toString()))
	// {
	// userMark = String.valueOf(list.get(i).getPhone());
	//
	// } else if (list.get(i).getFid() != null
	// &&
	// !list.get(i).getFid().equals("")&&!"null".equals(list.get(i).getFid().toString()))
	// {
	// userMark = String.valueOf(list.get(i).getFid());
	// } else {
	// userMark = "";
	// }
	// if (i == list.size() - 1) {
	// empNames.append(list.get(i).getUserRealName()
	// + (userMark.equals("") ? "" : ("("
	// + userMark + ")")));
	// } else {
	// empNames.append(list.get(i).getUserRealName()
	// + (userMark.equals("") ? "" : ("("
	// + userMark + ")")) + ",");
	// }
	//
	// }
	//
	// }
	//
	// out.print(empNames);
	// } else {
	// out.print("0");
	// }
	// return null;
	// } catch (Exception e) {
	// logger.error("|eid:" + userinfo.getEid() + "|loginid:"
	// + userinfo.getLoginId() + "|msg=" + logException(e));
	// out.print("-1");
	// } finally {
	// out.close();
	// }
	// return null;
	// }
	//
	// private String toHtml(String str) {
	// String val = "";
	// String regex="[^a-z*A-Z*0-9*\u4e00-\u9fa5*]\\**";
	// if (str != null && str.length() > 0) {
	// val = str;
	// /* val = val.replaceAll("\\\\", "\\\\\\\\");
	// val = val.replaceAll("'", "\\\\'");
	// val = val.replaceAll("<", "&lt;");
	// val = val.replaceAll(">", "&gt;");*/
	// val = val.replaceAll(regex, "");
	// }
	// return val;
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
	// /**
	// * 用户通过JSON 的方式 传递部门数据，并全部显示 <a>zhangzhanliang@live.cn</a> 当前主要应用于超管
	// * 范围,完成后注意不同管理员管理的部门范围不同.
	// */
	// public String executeXML() {
	// Integer eid = this.getUserSessionInfo().getEid();
	// Integer userId = this.getUserSessionInfo().getId();
	// List<Department> resultList = new ArrayList<Department>();
	// Integer poolId = PoolConfigInfoMap.get(eid).getPhysical_pool_id();
	// DepartmentSearcher searchCond = new DepartmentSearcher();
	// searchCond.setEid(eid);
	// searchCond.setPoolid(poolId);
	// try {
	// if (this.getUserSessionInfo().isSuperAdmin()) {
	// resultList = this.departmentManager
	// .getSubGroupsById(searchCond);
	// } else {
	// resultList = this.departmentManager.getDepartmentsByIds(eid,
	// poolId, adminDeptManager
	// .getDepartmentIdsWithPermission(eid, this
	// .getUserSessionInfo().getId()));
	// }
	//
	// } catch (BusinessException e1) {
	// e1.printStackTrace();
	// } catch (DataException e1) {
	// e1.printStackTrace();
	// } // try catch 结束，返回权限范围内所有的部门
	//
	// this.getResponse().setContentType("text/xml;charset=" + Global.CHARSET);
	// try {
	// ServletOutputStream stream = this.getResponse().getOutputStream();
	// XMLWriter writer = new XMLWriter(stream);
	// Department d = new Department();
	// d.setName("未分配部门");
	// d.setParentId(0);
	// d.setDprtid(-1);
	// d.setIsLeaf(1);
	// d.setDescrption("未分配部门");
	// d.setDepartmentOrder(0);
	// resultList.add(d);
	// Document document = null;
	// if (null != resultList && resultList.size() > 0) {
	// document = DomTreeBuilderUtil
	// .createGroupDomCheckBoxTreeSyn(resultList);
	// } else {
	// document = DocumentHelper.createDocument();
	// }
	// writer.write(document);
	// writer.close();
	// stream.close();
	// } catch (IOException e) {
	// logger.error(new StringBuilder("|eid=").append(eid).append(
	// "|loginid=").append(this.getUserSessionInfo().getLoginId())
	// .append("|msg= create json String to error").append(
	// e.getMessage()).append("|"));
	// }
	// return null;
	// }
	//
	// private String[] getStrArr(Object[] obj) {
	// String[] strArr = new String[obj.length];
	// for (int i = 0; i < obj.length; i++) {
	// strArr[i] = obj[i].toString();
	// }
	// return strArr;
	// }
	//
	// private Document addRootClusterForSndMsg(Document document){
	// if (!hasRights("/manage/usermanage/cluster")) {
	// return document;
	// }
	// Element rootElement = document.getRootElement();
	// if (null == rootElement) {
	// rootElement = document.addElement("tree");
	// }
	//		
	// Element rootClusterElement = DocumentHelper.createElement("tree");
	// if(this.getUserSessionInfo().getVersion()==2){
	// ActionContext.getContext().setLocale(Locale.TAIWAN);
	// }
	// rootClusterElement.addAttribute("text",
	// getText("efetionmanage.framework.cluster.tree"));
	// rootClusterElement.addAttribute("isleaf", "0");
	// rootClusterElement.addAttribute("action", "cluster?groupId=0");
	// rootElement.elements().add(0, rootClusterElement);
	//		
	// List treeList = new ArrayList<ClusterTreeNode>();
	// List<ClusterCategory> clusterCategories =
	// clusterManager.getClusterCategories();
	// ClusterTreeNode category1 = new ClusterTreeNode();
	// category1.setIdentifier((int)
	// clusterCategories.get(0).getClassId().byteValue());
	// category1.setName(clusterCategories.get(0).getClassName());
	// category1.setIsLeaf(0);
	// category1.setParentId(0);
	// treeList.add(category1);
	//
	// ClusterTreeNode category2 = new ClusterTreeNode();
	// category2.setIdentifier((int)
	// clusterCategories.get(1).getClassId().byteValue());
	// category2.setName(clusterCategories.get(1).getClassName());
	// category2.setIsLeaf(0);
	// category2.setParentId(0);
	// treeList.add(category2);
	// DomTreeBuilderUtil.deptTreeAppend(rootClusterElement,treeList,
	// getBasePath()+"/manage/usermanage/cluster/clusterTreeAction!getClusterForSndMsg."
	// + Global.ACTION_EXT,
	// "cluster", "", null, "right1");
	// return document;
	// }
	//	
	// private boolean hasRights(String nameSpaceStr) {
	// boolean hasRights = false;
	// Set<ModelInfo> rights = this.getUserSessionInfo().getAllRole();
	// Iterator iter = rights.iterator();
	// while (iter.hasNext()) {
	// ModelInfo role = (ModelInfo) iter.next();
	// String superUrl = role.getActionUrl();
	// if (superUrl.indexOf("/") != -1) {
	// superUrl = superUrl.substring(0, superUrl.lastIndexOf("/"));
	// }
	// if (nameSpaceStr.equalsIgnoreCase(superUrl)) {
	// hasRights = true;
	// break;
	// }
	// }
	// return hasRights;
	// }
}
