package com.cmcc.framework.controller.action.role;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.cmcc.common.Global;
import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.role.Role;
import com.cmcc.framework.model.role.RoleSearcher;

/**
 * 角色 action
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-9
 */
public class RoleAction extends WebActionBase {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8525540736854920335L;

	private Logger logger = Logger.getLogger(RoleAction.class);

	/**
	 * 角色名称
	 */
	protected String roleName;

	/**
	 * 角色描述
	 */
	protected String roleDec;

	/**
	 * 主键
	 */
	protected Integer roleId;

	/**
	 * 
	 */
	public String findAllRole() throws BusinessException {// 查询当前所有角色

		UserSessionObj userinfo = this.getUserSessionInfo();
		try {
			String nowPage = this.getRequest().getParameter("page");
			RoleSearcher roleSeacher = new RoleSearcher();
			if (this.getRoleName() != null) {
				roleSeacher.setRoleName(this.getRoleName().trim());
			}
			Page pageInfo = this.roleManager.findRoleBy(Global.PAGESIZE,
					roleSeacher);
			pageInfo.setSearchCondition(roleSeacher);
			if (nowPage != null) {
				pageInfo.setPage(Integer.parseInt(nowPage));

			}
			List<Role> roleList = this.roleManager.findRoleByPage(pageInfo);
			this.getRequest().setAttribute("pageInfo", pageInfo);
			StringBuffer sb = new StringBuffer();
			StringBuffer roleDec = new StringBuffer();
			for (int i = 0; i < roleList.size(); i++) {
				if (roleList.get(i).getRoleDec() != null) {
					roleDec.append(roleList.get(i).getRoleDec().trim()
							.replaceAll(" ", "").replaceAll("　", ""));
					if (!roleDec.equals("")) {
						String str = this.doString(sb, roleDec.toString(),
								"<br/>", 36);
						roleList.get(i).setShowRoleDec(str);
						sb.delete(0, sb.length());
					}
				}
				roleDec.delete(0, roleDec.length());
			}
			this.getRequest().setAttribute("roleList", roleList);
			if (this.getRoleName() != null && !this.roleName.equals("")) {
				this.getRequest().setAttribute("roleName",
						toHtml(this.roleName));
			}
			if (this.getRequest().getParameter("flag") != null
					&& this.getRequest().getParameter("flag").equals("delete")) {

				this.getRequest().setAttribute("flag", 1);
			}
			return "savasuccess";
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			e.printStackTrace();
			throw new BusinessException("查找角色失败");
		}

	}

	@GenericLogger(operateMark = OperateMark.ROLE_BATCH_DELETE, operateDescription = "角色管理-删除角色", isOperateRecords = true)
	public String deleteRole() throws IOException, BusinessException {
		UserSessionObj userinfo = this.getUserSessionInfo();
		try {
			String[] roleIds = this.getRequest().getParameterValues("roleId");

			for (int i = 0; i < roleIds.length; i++) {
				this.roleManager.deleteRole(Integer.valueOf(roleIds[i]));
			}
			logger.info("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=做了删除角色的操作");
			/** 修改权限版本end* */
			this.getResponse().sendRedirect(
					this.getBasePath()
							+ "/manage/rolemanage/role/roleAction!findAllRole."
							+ Global.ACTION_EXT + "?flag=delete");
			return null;
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			throw new BusinessException("删除角色失败");
		}

	}

	public String isHadSameNameRole() throws IOException, BusinessException {
		this.getRequest().setCharacterEncoding("utf-8");
		this.getResponse().setContentType("text/xml");
		PrintWriter out = this.getResponse().getWriter();
		UserSessionObj userinfo = this.getUserSessionInfo();
		try {

			String roleName = this.getRequest().getParameter("roleName");
			Role role = roleManager.getRoleByName(roleName);
			if (role != null) {
				out.write("1");
			} else {
				out.write("0");
			}
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			throw new BusinessException("获取角色名称失败");
		} finally {
			out.close();
		}
		return null;
	}

	@GenericLogger(operateMark = OperateMark.ROLE_ADD, operateDescription = "角色管理-创建新角色")
	public String saveRole() throws BusinessException {
		UserSessionObj userinfo = this.getUserSessionInfo();
		try {
			if (this.roleName == null
					|| this.roleName.trim().replaceAll(" ", "").replaceAll("　",
							"").equals("")) {
				this.getRequest().setAttribute("errors", 1);
				return "adderror";
			} else if (this.roleName.length() > 60) {
				this.getRequest().setAttribute("errors", 2);
				return "adderror";
			} else if (this.roleDec.length() > 256) {
				this.getRequest().setAttribute("errors", "noneed");// 页面现在不需要有这个提示，先设置上
				return "adderror";
			} else if (this.roleManager.getRoleByName(StringUtil
					.ToDBC(this.roleName.trim().replaceAll(" ", "").replaceAll(
							"　", ""))) != null) {
				this.getRequest().setAttribute("errors", 5);
				return "adderror";

			} else {

				Role role = new Role();
				role.setRoleDec(this.getRoleDec());
				String roleName = StringUtil.ToDBC(this.roleName.trim()
						.replaceAll(" ", "").replaceAll("　", ""));
				role.setRoleName(roleName);
				role.setRoleType(1);
				role.setCreateTime(new Date());
				role.setDmlTime(new Date());
				role.setDmlflog((short) 1);
				this.roleManager.saveOrUpdateRole(role);
				this.getRequest().setAttribute("saveFlag", 1);
				logger.info("|eid:" + userinfo.getEid() + "|loginid:"
						+ userinfo.getLoginId() + "|msg=做了添加角色的操作");
				return "adderror";
			}
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			throw new BusinessException("创建角色失败");
		}
	}

	public String isHadRole() throws Exception {// 判断此角色是否存在
		PrintWriter out = this.getResponse().getWriter();
		UserSessionObj userinfo = this.getUserSessionInfo();
		try {
			String roleId = this.getRequest().getParameter("roleId");
			Role role = this.roleManager.findRoleById(Integer.parseInt(roleId));
			this.getRequest().setCharacterEncoding("utf-8");
			this.getResponse().setContentType("text/xml");

			if (role == null) {
				out.write("0");
			} else {
				out.write(role.getRoleName());
			}
			return null;
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			throw new BusinessException("获得角色失败");
		} finally {
			out.close();
		}
	}

	public String toUpdateRole() throws Exception { // 转向修改角色页面
		UserSessionObj userinfo = this.getUserSessionInfo();
		try {
			String thisRoleId = this.getRequest().getParameter("roleId");
			Role role = this.roleManager.findRoleById(Integer
					.parseInt(thisRoleId));
			this.getRequest().setAttribute("roleName", role.getRoleName());
			this.getRequest().setAttribute("oldRoleName", role.getRoleName());
			this.getRequest().setAttribute("roleDec", role.getRoleDec());
			this.getRequest().setAttribute("roleId", role.getRoleId());
			this.getRequest().setAttribute("roleType", role.getRoleType());
			return "toupdate";
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			throw new BusinessException("获得角色失败");
		}

	}

	@GenericLogger(operateMark = OperateMark.ROLE_UPDATE, operateDescription = "角色管理-修改角色")
	public String updateRole() throws BusinessException {// 修改角色
		String roleType = this.getRequest().getParameter("roleType");
		UserSessionObj userinfo = this.getUserSessionInfo();
		try {
			String oldRoleName = this.getRequest().getParameter("oldRoleName");
			if (this.roleName == null
					|| this.roleName.trim().replaceAll(" ", "").replaceAll("　",
							"").equals("")) {
				this.getRequest().setAttribute("errors", 1);
				this.getRequest().setAttribute("roleType", roleType);
				return "updateerror";
			} else if (this.roleName.length() > 60) {
				this.getRequest().setAttribute("errors", 2);
				this.getRequest().setAttribute("roleType", roleType);
				return "updateerror";
			} else if (this.roleDec.length() > 256) {
				this.getRequest().setAttribute("errors", "noneed");// 页面现在不需要有这个提示，先设置上
				this.getRequest().setAttribute("roleType", roleType);
				return "updateerror";

			} else if (this.roleManager.getRoleByName(StringUtil
					.ToDBC(this.roleName.trim().replaceAll(" ", "").replaceAll(
							"　", ""))) != null
					&& !oldRoleName.trim().replaceAll(" ", "").replaceAll("　",
							"").equals(
							this.roleName.trim().replaceAll(" ", "")
									.replaceAll("　", ""))) {
				this.getRequest().setAttribute("errors", 5);
				this.getRequest().setAttribute(
						"roleName",
						this.roleName.trim().replaceAll(" ", "").replaceAll(
								"　", ""));
				this.getRequest().setAttribute(
						"oldRoleName",
						oldRoleName.trim().replaceAll(" ", "").replaceAll("　",
								""));
				this.getRequest().setAttribute("roleId", this.roleId);
				this.getRequest().setAttribute("roleDec", this.roleDec);
				return "updateerror";

			} else {
				String roleId = this.getRequest().getParameter("roleId");
				Role role = this.roleManager.findRoleById(Integer
						.parseInt(roleId));
				// role.setRoleId(Integer.parseInt(roleId));
				// role.setEId(userinfo.getEid());
				role.setRoleDec(this.getRoleDec());
				String roleName = StringUtil.ToDBC(this.roleName.trim()
						.replaceAll(" ", "").replaceAll("　", ""));
				role.setRoleName(roleName);
				role.setDmlTime(new Date());
				role.setDmlflog((short) 2);
				// role.setRolePinyin(CnToSpell.getFullSpell(roleName));
				// role.setRoleInitials(CnToSpell.getFirstSpell(this.roleName
				// .trim().replaceAll(" ", "").replaceAll(" ", "")));
				// role.setRoleScope(0);
				role.setRoleType(1);
				this.roleManager.saveOrUpdateRole(role);
				this.getRequest().setAttribute("updateFlag", 1);
				logger.info("|eid:" + userinfo.getEid() + "|loginid:"
						+ userinfo.getLoginId() + "|msg=做了修改角色的操作");
				return "toupdate";
			}
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			throw new BusinessException("修改角色失败");
		}

	}

	public String goManagePer() throws NumberFormatException, BusinessException {// 转向修改角色权限页面
		UserSessionObj userinfo = this.getUserSessionInfo();
		try {
			String roleId = this.getRequest().getParameter("roleId");
			Role role = this.roleManager.findRoleById(Integer.parseInt(roleId));
			String fromType = this.getRequest().getParameter("fromType");
			if (fromType != null && fromType.equals("fromListEmp")) {
				this.getRequest().setAttribute("fromType", "fromListEmp");
			}
			List<Workflow> workflowList = null;
			// 全公司 或者未分配权限
			// if (role.getRoleScope() == 0 || role.getRoleScope() == 1) {
			// workflowList = this.workflowManager.findAllWorkflow();
			// } else {
			// workflowList = this.roleManager.findWorkByRid(Integer
			// .parseInt(roleId));
			// }

			// //所选角色功能权限
			this.getRequest().setAttribute("thisRole", role);
			this.getRequest().setAttribute("roleName", role.getRoleName());
			this.getRequest().setAttribute("workList", workflowList);

			return "managePer";
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			throw new BusinessException("获取角色员工关系失败");
		}

	}

	@GenericLogger(operateMark = OperateMark.ROLE_UPDATE, operateDescription = "角色管理-修改角色功能权限")
	public String saveRolePer() throws BusinessException {// 修改权限
		UserSessionObj userinfo = this.getUserSessionInfo();
		try {
			String roleScope = this.getRequest().getParameter("roleScope");// 角色范围
			// 1:
			// 全公司
			// 2：自定义
			// 范默认：0

			String roleId = this.getRequest().getParameter("roleId");
			// //自定义范围时选择的员工
			String[] pers = this.getRequest().getParameterValues("workId");// 选择的项目

			Role role = this.roleManager.findRoleById(Integer.parseInt(roleId));
			// role.setRoleScope(Integer.parseInt(roleScope));
			this.roleManager.saveOrUpdateRole(role);// 更新role的角色范围
			roleManager.updateRoleWorkflow(Integer.parseInt(roleId), pers);

			this.getRequest().setAttribute("flag", 1);
			logger.info("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=做了修改角色功能权限或范围的操作");
			return "managePer";
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			throw new BusinessException("修改角色功能权限失败");
		}

	}

	/**
	 * 转向修改角色员工页面
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String goManageEmp() throws BusinessException {// 转向修改角色员工页面
		UserSessionObj userinfo = this.getUserSessionInfo();
		try {
			String roleId = this.getRequest().getParameter("roleId");
			// 所有成员列表
			List<WebAdmin> allAdminList = this.webAdminManager.getAll();
			this.getRequest().setAttribute("allAdminList", allAdminList);
			// 已有成员列表
			List<WebAdmin> adminList = this.roleManager
					.findAdminByRoldId(Integer.parseInt(roleId));
			this.getRequest().setAttribute("adminList", adminList);
			this.getRequest().setAttribute("roleId", roleId);
			String roleName = this.roleManager.findRoleById(
					Integer.parseInt(roleId)).getRoleName();
			this.getRequest().setAttribute("roleName", roleName);
			this.getRequest().setAttribute("optype", "emp");
			if (this.getRequest().getParameter("type") != null
					&& this.getRequest().getParameter("type").equals("listEmp")) {
				this.getRequest().setAttribute("type", "listEmp");
			}
			return "selectRoleEmp";
		} catch (Exception e) {
			logger.error("|eid:" + userinfo.getEid() + "|loginid:"
					+ userinfo.getLoginId() + "|msg=" + logException(e));
			throw new BusinessException("获取角色员工关系失败");
		}

	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

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

	private String toHtml(String str) {
		String val = "";
		if (str != null && str.length() > 0) {
			val = str;
			val = val.replaceAll("\\\\", "\\\\\\\\");
			val = val.replaceAll("'", "\\\\'");
			val = val.replaceAll("<", "&lt;");
			val = val.replaceAll(">", "&gt;");

		}
		return val;
	}

	private String logException(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.close();
		return sw.toString();
	}

	public String getRoleDec() {
		return roleDec;
	}

	public void setRoleDec(String roleDec) {
		this.roleDec = roleDec;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
