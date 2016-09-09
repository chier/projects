/* 
 * 文件名称：ListContentInfosAction.java
 * 
 * 创建时间：2009-3-3上午09:34:12
 *
 * 原始作者：曹巍
 */
package com.cmcc.framework.controller.action.content;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.Global;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.ContentType;
import com.cmcc.framework.model.content.ContentInfo;

/**
 * 后台显示内容信息操作类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.5 $
 * 
 * @since 2009-3-3
 */
public class ListContentInfosAction extends WebActionBase {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ListContentInfosAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 6257689546490995205L;
	/**
	 * 显示方法
	 */
	public String execute() throws DataException, BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("execute() - start");
		}

		String groupId = this.getRequest().getParameter("groupId");
		String ctid = this.getRequest().getParameter("ctid");
		String strSearch = getRequest().getParameter("searchName");
		Integer eid = 1;
		UserSessionObj usersession = this.getUserSessionInfo();
		if (usersession != null) {
			if (usersession.getEid() != null) {
				eid = usersession.getEid();
			} else {
				throw new BusinessException("eiderror");
			}
		} else {
			throw new BusinessException("usersessionerror");
		}
		int igroupId = 0;
		int intcrid = 0;
		if (groupId != null) {
			try {
				if(!StringUtil.isBlank(ctid) && StringUtil.isNumeric(ctid)){
					intcrid = Integer.parseInt(ctid);
				}
				igroupId = Integer.parseInt(groupId);
			} catch (NumberFormatException e) {
				if (logger.isErrorEnabled()) {
					logger.error("execute()", e);
				}

				throw new BusinessException("contenttypeerror");
			}
		}
		HttpServletRequest req = this.getRequest();
		String page = req.getParameter("page");
		String order = req.getParameter("order");
		String orderBy = req.getParameter("orderBy");
		String pageSize = req.getParameter("pageSize");
		int ipagesize;
		if (pageSize != null) {
			ipagesize = Integer.parseInt(pageSize);
		} else {
			ipagesize = Global.PAGESIZE;
		}
		if("输入标题查找".equals(strSearch)){
			strSearch = "";
		}

		Page pageinfo = this.getContentInfoManager().getPageByEIDAndConType(
				eid, igroupId,intcrid, strSearch,ipagesize);

		int ipage = 0;
		if (page != null) {
			ipage = Integer.parseInt(page);
		}
		if (order != null) {
			pageinfo.setOrder(order);
		}
		if (orderBy != null) {
			pageinfo.setOrderBy(orderBy);
		}
		pageinfo.setPage(ipage);
		List<ContentInfo> contentList = this.getContentInfoManager()
				.getAllByEIDAndContype(pageinfo, igroupId,intcrid ,strSearch,eid);
		
		List<ContentType> typeList = this.getContentInfoManager().findAllContnetType();
		
		this.getRequest().setAttribute("contentList", contentList);
		
		req.setAttribute("typeList", typeList);
		req.setAttribute("pageInfo", pageinfo);
		req.setAttribute("order", order);
		req.setAttribute("orderBy", orderBy);
		req.setAttribute("actionName", "listContents");
		req.setAttribute("groupId", groupId);
		req.setAttribute("ctid", ctid);

		if (logger.isInfoEnabled()) {
			logger.info("execute() - end - return value=" + SUCCESS);
		}
		return SUCCESS;
	}
	/**
	 * 默认入口
	 * 
	 * @return defaultIn
	 */
	public String defaultIn() {
//	    Integer eid = this.getUserSessionInfo().getEid();
//	    Integer version = this.getUserSessionInfo().getVersion().intValue();
//	    contentInfoManager.initCatalog(eid,version);
		return "defaultIn";
	}
}
