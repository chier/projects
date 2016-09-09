/*
 * 文件名： ListWebAdminAction.java
 * 
 * 创建日期： 2009-2-25
 *
 * Copyright(C) 2009, by gaojl.
 *
 * 原始作者: <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 */
package com.cmcc.framework.controller.action.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.admin.WebAdminSearch;
import com.cmcc.framework.model.department.Department;

/**
 * 管理员列表的action
 * 
 * @author <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 * 
 * @version $Revision: 1.3 $
 * 
 * @since 2009-2-25
 */
@SuppressWarnings("unchecked")
public class ListWebAdminAction extends WebActionBase {

	private static final long serialVersionUID = 1886686899370206699L;

	/**
	 * Logger for this class
	 */
	// 查询条件
	private String searchName;

	/** ***************************分页参数******************************* */
	private Integer page = 1;

	private Page pageInfo;

	/** *************************返回页面的数据**************************** */
	private List<WebAdmin> adminList;

	private static final Log logger = LogFactory
			.getLog(ListWebAdminAction.class);

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Page getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(Page pageInfo) {
		this.pageInfo = pageInfo;
	}

	public List<WebAdmin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<WebAdmin> adminList) {
		this.adminList = adminList;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	
	public String index(){
		return "index";
	}
	

	// modified by yangshuo 2009-09-24 14:30
	@GenericLogger(operateMark = OperateMark.ADMIN_SELECT, operateDescription = "管理员维护管理-显示用户列表", isOperateEmployee = true, isOperateRecords = false)
	public String execute() throws BusinessException, DataException {
		String deptName = "用户帐号管理";
		try {
			UserSessionObj userObj = this.getUserSessionInfo();
			if (userObj == null) {
				logger.error("msg=efetionmanage.framework.exception.noSession");
				throw new BusinessException(
						"efetionmanage.framework.exception.noSession");
			} else {
				if (null == searchName || searchName.equals("")
						|| searchName.equals("输入用户名查找")) {
					searchName = "";
				} else {
					searchName = searchName.trim();
				}
				WebAdminSearch search = new WebAdminSearch();
				String groupId = this.getRequest().getParameter("groupId");
				if(groupId != null &&  !"".equals(groupId)&&!"0".equals(groupId) && StringUtil.isNumeric(groupId)){
					search.setDeptId(Integer.valueOf(groupId));
					Department dept = this.departmentManager.findDeptById(Integer.valueOf(groupId));
					deptName = dept.getName();
				}
				
				search.setAdminName(searchName);
				// 查询分页信息
				try {
					pageInfo = this.webAdminManager.findBy(search,this
							.getUserPageSize());

				} catch (DataException e) {
					logger.error("msg=" + e.getMessage());
					e.printStackTrace();
				}

				pageInfo.setPage(page);
				pageInfo.setSearchCondition(search);
				if (pageInfo.getMaxItemNum() > 0) {
					// 如果总条数大于0则查询记录
					try {
						this.adminList = this.webAdminManager
								.findByPage(pageInfo);
					} catch (DataException e) {
						logger.error("msg=" + e.getMessage());
						e.printStackTrace();
					}
				}
				List list = new ArrayList();
				if (adminList != null && adminList.size() > 0) {
					list.addAll(adminList);
					this.getRequest().setAttribute("list", list);

				} else {
					this.getRequest().setAttribute("list", "");
				}
				
				this.getRequest().setAttribute("deptName",deptName);
				this.getRequest().setAttribute("groupId", groupId);
			}
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("msg=" + e.getMessage());
			throw new BusinessException("无法获得管理员列表", e);
		}
	}
	
	/**
	 * ajax 判断 判断管理员帐号，是否在企业中存在
	 * 
	 * @return 返回名称 @ throws Exception 异常信息
	 */
	public String validateAdminName() throws Exception {
		String adminName = this.getRequest().getParameter("adminName");
		String adminId = this.getRequest().getParameter("adminid");
		Integer adminid = 0;

		if (!StringUtil.isBlank(adminId) && StringUtil.isNum(adminId)) {
				adminid = Integer.valueOf(adminId);
		}
		PrintWriter out = null;
		try {
			if (StringUtil.isBlank(adminName)) { // 传输过来的用户名为空时，抛出空指针异常
				throw new NullPointerException();
			}
			out = this.getResponse().getWriter();
			 boolean sameName = webAdminManager.getSameName(adminName, adminid);
			logger.info("sameName is " + sameName);
			// 1 : 表示管理员用户名已经存在。 0: 表示管理员用户名不存在
			out.print(sameName ? "0" : "1");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}
}
