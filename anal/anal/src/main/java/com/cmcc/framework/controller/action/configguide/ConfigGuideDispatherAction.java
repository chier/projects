/**      
 * 文件名称： ConfigGuideDispatherAction.java
 * 
 * 创建时间： Apr 27, 2009 2:17:14 PM
 *      
 * Copyright (c) 2009 by Shi Limin.      
 */
package com.cmcc.framework.controller.action.configguide;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;

//import com.cmcc.common.cache.PoolConfigInfoMap;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.log.OperateLog;
//import com.cmcc.framework.model.system.model.WebModel;
import com.cmcc.framework.model.model.WebModel;

/**
 * 功能描述：负责跳转
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-9
 */
public class ConfigGuideDispatherAction extends WebActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2059257418357649129L;

	public String addDeptInput() {
//		Integer eid = this.getUserSessionInfo().getEid();
//		Integer poolId = PoolConfigInfoMap.get(eid).getPhysical_pool_id();
//		String shortName = companyservice.get(eid, poolId).getShortName();
//		getRequest().setAttribute("shortName", shortName);
		return "addDeptInput";
	}

	public String index() {
		/**
		 * 老版本
		 */
//		getSession().removeAttribute("selectedGroupId");
//		return addDeptInput();
		return "showFlash";
	}
	
	public String quickGuide() {
//		UserSessionObj userObj = this.getUserSessionInfo();
//		// 判断用户是否校园版
//		boolean campusVersion= false;
//		if(userObj.getVersion() == 2){
//			campusVersion = true;
//		}
//		//判断快速向导页面上的“批量添加人员向导向导”是否有权限打开
//		List<OperateLog> noRightList = new ArrayList<OperateLog>();
//		noRightList = this.checkRight(userObj.getAllRole(), createQucikGuideList(campusVersion));
//		this.getRequest().setAttribute("xglyxd", noRightList.get(0).getEmployeeName());
		
		return "quickGuide";
	}
	public String newFun() {
//		UserSessionObj userObj = this.getUserSessionInfo();
//		// 判断用户是否校园版
//		boolean campusVersion= false;
//		if(userObj.getVersion() == 2){
//			campusVersion = true;
//		}
//		List<OperateLog> noRightList = new ArrayList<OperateLog>();
//		noRightList = this.checkRight(userObj.getAllRole(), createNewFunctionList(campusVersion));
//		
//		this.getRequest().setAttribute("srtx", noRightList.get(0).getEmployeeName());
//		this.getRequest().setAttribute("exceltj", noRightList.get(1).getEmployeeName());
		return "newFun";
	}
	
	public String adminGuid(){
//		UserSessionObj userObj = this.getUserSessionInfo();
//		// 判断用户是否校园版
//		boolean campusVersion= false;
//		if(userObj.getVersion() == 2){
//			campusVersion = true;
//		}
//		List<OperateLog> noRightList = new ArrayList<OperateLog>();
//		noRightList = this.checkRight(userObj.getAllRole(), crateNewAdminGuidList(campusVersion));
//		this.getRequest().setAttribute("addemployee", noRightList.get(0).getEmployeeName());
//		this.getRequest().setAttribute("openemployee", noRightList.get(1).getEmployeeName());
//		this.getRequest().setAttribute("groupsms", noRightList.get(2).getEmployeeName());
//		this.getRequest().setAttribute("esurvey", noRightList.get(3).getEmployeeName());
//		this.getRequest().setAttribute("isCampus", campusVersion);
		
		return "adminGuid";
	}
	
	/**
	 * 负责跳转至首页
	 * @return
	 */
	public String firstPage() {
		return "firstPage";
	}
	
	//新功能导航页面上的超链接
	private List<OperateLog> createNewFunctionList(boolean isCampusVersion){
		List<OperateLog> newFunctionList = new ArrayList<OperateLog>();
		OperateLog log1 = new OperateLog();
		log1.setEmployeeName("/manage/reminder/reminderAction.portal");////该模块连接
		log1.setShortName("内容管理->生日提醒");//模块名称
		log1.setIocUrl("/images/icon_nrgl_srtx.gif");
		newFunctionList.add(log1);
		
		OperateLog log2 = new OperateLog();
		log2.setEmployeeName("/manage/usermanage/employee/guideCustom.portal");////该模块连接
		log2.setShortName(replaceEnt2Campus("通过自定义Excel文件批量添加员工",isCampusVersion));//模块名称
		log2.setIocUrl("/images/icon_nrgl_yggl.gif");
		newFunctionList.add(log2);
		
		return newFunctionList;
	}
	
	//快速向导页面上的超链接
	private List<OperateLog> createQucikGuideList(boolean isCampusVersion){
		List<OperateLog> qucikGuideList = new ArrayList<OperateLog>();
		OperateLog log1 = new OperateLog();
		log1.setEmployeeName("/manage/usermanage/employee/guideIndex.portal");////该模块连接
		log1.setShortName(replaceEnt2Campus("批量添加员工向导",isCampusVersion));//模块名称
		log1.setIocUrl("/images/icon_nrgl_pltj.gif");
		qucikGuideList.add(log1);
				
		return qucikGuideList;
	}
	//新管理员快速向导页面上的超链接
	private List<OperateLog> crateNewAdminGuidList(boolean isCampusVersion){
		List<OperateLog> newAdminGuidList = new ArrayList<OperateLog>();
		OperateLog log1 = new OperateLog();
		log1.setEmployeeName("/manage/usermanage/employee/listEmployeeAction!indexInput.portal");////该模块连接
		newAdminGuidList.add(log1);
		OperateLog log2 = new OperateLog();
		log2.setEmployeeName("/manage/usermanage/employee/listEmployeeAction!indexInput.portal");////该模块连接
		newAdminGuidList.add(log2);
		OperateLog log3 = new OperateLog();
		log3.setEmployeeName("/manage/contentmanage/message/smsmsg!listSmsmgHistory.portal");////该模块连接
		newAdminGuidList.add(log3);
		OperateLog log4 = new OperateLog();
		log4.setEmployeeName("/manage/contentmanage/questionnaire/listQuestionnaire.portal");////该模块连接
		newAdminGuidList.add(log4);
				
		return newAdminGuidList;
	}
	
	//判断某用户是否用权限点击paramList中列出的超链接
	private List<OperateLog> checkRight(Set<WebModel> allRole,List<OperateLog> paramList){
		List<String> allUrls = new ArrayList<String>();
		for(WebModel mi:allRole){
			allUrls.add(mi.getActionUrl());
		}
		
		List<OperateLog> result = new ArrayList<OperateLog>();
		if(allUrls == null || allUrls.size()<1){
			if(paramList !=null && paramList.size()>0){
				for(OperateLog param : paramList){
					param.setEmployeeName("noright");
					result.add(param);
				}
			}
		}else{
			if(paramList !=null && paramList.size()>0){
				for(OperateLog param : paramList){
					if(allUrls.contains(param.getEmployeeName())){	//有权限
						result.add(param);
					}else {
						boolean hasright = false;
						for(String url : allUrls){
							String urlnamespce = url==null?"":url;
							if(url.lastIndexOf("/")>0){
								urlnamespce = url.substring(0,url.lastIndexOf("/"));
							}
							String paramnamespace = param.getEmployeeName();
							if( param.getEmployeeName().lastIndexOf("/")>0){
								paramnamespace = param.getEmployeeName().substring(0,  param.getEmployeeName().lastIndexOf("/"));
							}
														
							if(urlnamespce.equals(paramnamespace)){
								hasright = true;
								break;
							}
						}
						if(!hasright)
							param.setEmployeeName("noright");
						result.add(param);
					}
				}
			}
		}
		return result;
	}
	
    private String replaceEnt2Campus(String str,boolean isCampusVersion){
    	if(!isCampusVersion || str==null || "".equals(str))
    		return str;
    	else 
    		return str.replaceAll("企业", "校园").replaceAll("员工", "人员");
    }
	/**
	 * 请求导入部门excle模版
	 * 
	 * @return
	 */
	public String requesetTemplate() {
//		try {
//			String strFileName = "department.xls";
//			strFileName = java.net.URLEncoder.encode(strFileName, "UTF-8"); // 处理中文文件名的问题
//			strFileName = new String(strFileName.getBytes("UTF-8"), "GBK"); // 处理中文文件名的问题
//			this.getResponse().reset();
//			this.getResponse().setCharacterEncoding("UTF-8");
//			this.getResponse().setContentType("application/vnd.ms-excel");
//			this.getResponse().setHeader("Content-Disposition",
//					"attachment; filename=" + strFileName);
//			File f = new File(getSession().getServletContext().getRealPath("/")
//					+ "static/configguide/staff.xls"); // 你的文件
//			InputStream is = new FileInputStream(f); // 文件输入流
//
//			ServletOutputStream out = this.getResponse().getOutputStream();
//			byte[] bs = new byte[1024]; // 读取缓冲区
//			int len;
//			while ((len = is.read(bs)) != -1) { // 循环读取
//				out.write(bs, 0, len); // 写入到输出流
//			}
//			out.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}
	public static void main(String[] args) {
		List<OperateLog> newFunctionList = new ArrayList<OperateLog>();
		List<OperateLog> qucikGuideList = new ArrayList<OperateLog>();
		Set<WebModel> all = new HashSet<WebModel>();
		WebModel mi1 = new WebModel();
		mi1.setIdentifier(1);
		mi1.setActionUrl("/base/test/abc.portel");
		all.add(mi1);

		WebModel mi2 = new WebModel();
		mi2.setIdentifier(2);
		mi2.setActionUrl("/base/test/aaa/abc.portel");
		all.add(mi2);
		WebModel mi3 = new WebModel();
		mi3.setIdentifier(3);
		mi3.setActionUrl("/base/test/bbb/abc.portel");
		all.add(mi3);
		WebModel mi4 = new WebModel();
		mi4.setIdentifier(4);
		mi4.setActionUrl("/base/test/ddd/abc.portel");
		all.add(mi4);
		
		List<OperateLog> newAdminGuidList = new ArrayList<OperateLog>();
		OperateLog log1 = new OperateLog();
		log1.setEmployeeName("/base/test/aaa/abc.portel");////该模块连接
		newAdminGuidList.add(log1);
		
		OperateLog log2 = new OperateLog();
		log2.setEmployeeName("/base/test/bbb/abc.portel");////该模块连接
		newAdminGuidList.add(log2);
		
		newFunctionList = new ConfigGuideDispatherAction().checkRight(all, newAdminGuidList);
		for (OperateLog operateLog : newFunctionList) {
			System.out.println(operateLog.getEmployeeName());
		}
	}
}
