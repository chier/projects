/**      
 * 文件名称： ListOperLogAction.java
 * 
 * 创建时间： 2009.3.23
 *      
 * Copyright (c) 2009 by shilimin
 */
package com.cmcc.framework.controller.action.log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cmcc.common.Global;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.UserSessionObj;
// import com.cmcc.common.util.GetTableNmae;
// import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.model.log.OperLogSearch;
import com.cmcc.framework.model.log.OperateLog;

// import com.cmcc.framework.model.system.model.ModelInfo;
// import com.cmcc.framework.model.system.model.VersionInfo;

/**
 * 功能描述：查询系统操作日志
 * 
 * @author <a href="mailto:shilimin@hdxt.net.cn">shilimin</a>
 * 
 * @version 1.3
 */
public class ListOperLogAction extends OperLogActionBase {

	private static final long serialVersionUID = -4548685570297748234L;

	@SuppressWarnings("deprecation")
	public String execute() throws DataException, BusinessException {

		UserSessionObj userObj = this.getUserSessionInfo();
		if (userObj == null) {
			throw new BusinessException(
					"efetionmanage.framework.exception.noSession");
		}
		Integer eid = 10011;
		String pageSize = this.getRequest().getParameter("pageSize");
		String userName = this.getRequest().getParameter("user_name");
		String operateTime = this.getRequest().getParameter("operate_time");
		String endTime = this.getRequest().getParameter("end_time");
		Date st = new Date();
		if (operateTime == null || "".equals(operateTime)) {
			operateTime = (st.getYear() + 1900) + "-";
			if ((st.getMonth() + 1) < 10) {
				operateTime = operateTime + "0" + (st.getMonth() + 1) + "-";
			} else {
				operateTime = operateTime + (st.getMonth() + 1) + "-";
			}
			if (st.getDate() < 10) {
				operateTime = operateTime + "0" + st.getDate();
			} else {
				operateTime = operateTime + st.getDate();
			}
			operateTime += " 00:00:00";
		}
		if (endTime == null || "".equals(endTime)) {
			endTime = (st.getYear() + 1900) + "-";
			if ((st.getMonth() + 1) < 10) {
				endTime = endTime + "0" + (st.getMonth() + 1) + "-";
			} else {
				endTime = endTime + (st.getMonth() + 1) + "-";
			}
			if ((st.getDate()) < 10) {
				endTime = endTime + "0" + (st.getDate());
			} else {
				endTime = endTime + (st.getDate());
			}
			int h = st.getHours();
			int m = st.getMinutes();
			if (h < 10) {
				endTime += " 0" + h;
			} else {
				endTime += " " + h;
			}
			if (m < 10) {
				endTime += ":0" + m;
			} else {
				endTime += ":" + m;
			}
			// end_time += " 23:59";
		}
		OperLogSearch searchCond = new OperLogSearch();
		searchCond.setEid(eid);
		// searchCond.setTableName(tableName);
		if (operateTime != null && !"".equalsIgnoreCase(operateTime)) {
			if (operateTime.length() == 16) {
				searchCond.setOperateTime((operateTime + ":00"));
			} else {
				searchCond.setOperateTime((operateTime));
			}

			this.getRequest().setAttribute("operate_time", operateTime);
		}
		if (endTime != null && !"".equalsIgnoreCase(endTime)) {

			if (endTime.length() == 16) {
				searchCond.setEndTime((endTime + ":59"));
			} else {
				searchCond.setEndTime((endTime));
			}
			this.getRequest().setAttribute("end_time", endTime);
		}

		if (userName != null && !"".equalsIgnoreCase(userName)) {
			searchCond.setAdminName(userName);
			this.getRequest().setAttribute("user_name", userName);
		}

		int ipagesize;
		if (pageSize != null) {
			ipagesize = Integer.parseInt(pageSize);
		} else {
			ipagesize = Global.PAGESIZE;
		}

		Page pageinfo = this.operLogManager.findBy(searchCond, ipagesize);
		String page = this.getRequest().getParameter("page");
		int ipage = 0;
		if (page != null) {
			ipage = Integer.parseInt(page);
		}
		pageinfo.setPage(ipage);
		pageinfo.setSearchCondition(searchCond);
		List<OperateLog> listOperLog;
		List<OperateLog> tempOperLog = new ArrayList<OperateLog>();
		if (pageinfo.getResultCount() == 0) {
			listOperLog = null;
		} else {
			listOperLog = operLogManager.findBy(pageinfo);
			tempOperLog = listOperLog;
		}
		this.getRequest().setAttribute("listOperLog", tempOperLog);
		this.getRequest().setAttribute("pageInfo", pageinfo);

		return SUCCESS;
	}

	/**
	 * 记录日志实现：用户常用模块和最近浏览历史功能
	 */
	public String noteLog() {
//		UserSessionObj userObj = this.getUserSessionInfo();
//		if (label != null && !label.trim().equals("")) {
//			try {
//				label = java.net.URLDecoder.decode(
//						this.getRequest().getParameter("label"), "UTF-8")
//						.trim();
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//		}
//
//		String newlabel = label;
//		if (2 == userObj.getVersion()) {
//			label = changeType(label);
//		}
//		// 过滤一些无需记录的点击
//		if (null != label
//				&& ("首页".equals(label) || "快速向导".equals(label)
//						|| "新功能导航".equals(label) || "功能演示(flash)".equals(label))) {
//			return null;
//		}
//		if (null != link && link.contains("javascript")) {
//			return null;
//		}
//		// 正式程序执行开始
//		List<VersionInfo> vlist;
//		List<ModelInfo> mlist = null;
//		try {
//			// session 中获取mlist
//			mlist = (List) this.getSession().getAttribute("mst");
//			if (null == mlist || mlist.size() == 0) {
//				// 查询所有企业版本模块
//				vlist = modelManager.getModelInfoByVersionId(0, 0);
//				Integer[] ids = new Integer[vlist.size()];
//				for (int i = 0; i < vlist.size(); i++) {
//					ids[i] = vlist.get(i).getModelId();
//				}
//				// 查询模块信息
//				mlist = modelManager.getModelInfoById(ids, 0);
//				// session 中设置 mlist
//				this.getSession().setAttribute("mst", mlist);
//			}
//			// 查询该企业的该管理员是否点击过此模块
//			OperateLog p = new OperateLog();
//			p.setEid(userObj.getEid());
//			p.setAdminId(userObj.getId());
//			p.setShortName(newlabel);
//			OperateLog o = operLogManager.get(p);
//			// 如果点击过修改点击记录数即可
//			if (null != o) {
//				o.setOperateRecords((o.getOperateRecords() + 1));
//				o.setOperateTime(new Date());
//				operLogManager.updateOperateLog(o);
//				return null;
//			}
//			// 员工信息同步和部门信息同步的URL中有参数,单独处理
//			String empURL = "/manage/sync/syncEmpAction!list.portal?oprFlag";
//			String deptURL = "/manage/sync/syncDeptAction!list.portal?oprFlag";
//			// 如果没有点击过,保存一条新纪录到数据库中
//			for (ModelInfo m : mlist) {
//				String url = m.getActionUrl();
//				if (url.contains("?")) {
//					url += "&groupId=" + m.getIdentifier();
//				} else {
//					url += "?groupId=" + m.getIdentifier();
//				}
//				// 判断点击的模块是左侧的模块
//				if (m.getModelName().equals(label)
//						&& (link.indexOf(empURL) != -1
//								|| link.indexOf(deptURL) != -1 || link
//								.indexOf(url) != -1)) {
//					OperateLog oper = new OperateLog();
//					oper.setEid(userObj.getEid());
//					oper.setAdminId(userObj.getId());
//					oper.setAdminName(userObj.getShowName());
//					// 点击模块名称
//					oper.setShortName(newlabel);
//					// 企业代码
//					oper.setC0(userObj.getCode());
//					// 点击模块的父级模块ID
//					oper.setOperateDesc(String.valueOf(m.getParentId()));
//					// 点击模块的URL
//					oper.setEmployeeName(m.getActionUrl());
//					// 记录标识
//					oper.setOperateMark("point");
//					// 点击次数
//					oper.setOperateRecords(1);
//					oper.setOperateTime(new Date());
//					operLogManager.saveOperateLog(oper);
//					break;
//				}
//			}
//		} catch (BusinessException e1) {
//			e1.printStackTrace();
//		} catch (DataException e1) {
//			e1.printStackTrace();
//		}
		return null;
	}

	/**
	 * 把校园版中字样转化为企业中字样
	 * 
	 * @return date
	 */
	public static String changeType(String s) {
		String str = "";
		if (null == s || "".equals(s))
			return str;
		else
			str = s;
		if (s.contains("校园")) {
			str = s.replace("校园", "企业");
			return str;
		}
		if (s.contains("人员")) {
			str = s.replace("人员", "员工");
			return str;
		}
		return str;
	}

}
