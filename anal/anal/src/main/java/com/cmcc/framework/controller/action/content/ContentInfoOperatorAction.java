/* 
 * 文件名称：ContentInfoOperatorAction.java
 * 
 * 创建时间：2009-3-3
 *
 * 原始作者：曹巍
 */
package com.cmcc.framework.controller.action.content;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.business.interfaces.content.IContentInfoManager;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.ContentFormBean;
import com.cmcc.framework.controller.formbean.ContentType;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.model.content.ContentAttach;
import com.cmcc.framework.model.content.ContentBody;
import com.cmcc.framework.model.content.ContentInfo;
import com.cmcc.framework.model.content.ContentSearchCondition;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 内容管理操作操作入口类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.4 $
 * 
 * @since 2009-3-3
 */
public class ContentInfoOperatorAction extends WebActionBase implements
		ModelDriven {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ContentInfoOperatorAction.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = -5902540544053315594L;

	private String operateExtned;

	/**
	 * this.form
	 */
	private ContentFormBean contentformbean = new ContentFormBean();

	/**
	 * this.nextResult
	 */
	private String nextResult;

	/**
	 * 返回模型驱动 formbean
	 */
	public Object getModel() {
		return this.contentformbean;
	}

	private void getContentTypes() {
		List<ContentType> typeList = this.contentInfoManager
				.findAllContnetType();
		this.getRequest().setAttribute("typeList", typeList);
	}

	/**
	 * 添加内容入口
	 * 
	 * @return
	 */
	public String addInput() throws DataException, BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("addInput() - start");
		}
		// loadProperties();
		this.getRequest().setAttribute("action", "create");
		// rushAttachMap();
		getContentTypes();
		String groupId = this.getRequest().getParameter("groupId");
		this.getRequest().setAttribute("groupId", groupId);

		List<ZtreeVO> zlist = this.dataDownManager.findPilotByAll();

		JSONArray jsonArray = JSONArray.fromObject(zlist);
		String json = jsonArray.toString();
		
		getRequest().setAttribute("jsontrees", json);
		logger.info(json);
		if (logger.isInfoEnabled()) {
			logger.info("addInput() - end - return value=editIn");
		}
		return "editIn";
	}

	/**
	 * 更新入口
	 * 
	 * @return
	 */
	public String modifyInput() throws DataException, BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("modifyInput() - start");
		}

		HttpServletRequest req = this.getRequest();
		// loadProperties();
		String groupId = this.getRequest().getParameter("groupId");
		String id = req.getParameter("conid");
		if (id != null) {
			String s_contentId = id;
			Integer i_contentId = null;
			if (s_contentId != null) {
				try {
					i_contentId = new Integer(s_contentId);
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("modifyInput()", e);
					}

					e.printStackTrace();
				}
				if (i_contentId != null) {
					ContentInfo contentVO = this.getContentInfoManager().get(
							i_contentId);
					ContentBody contentbody = this.getContentBodyManager().get(
							contentVO.getContentbody().getIdentifier());
					if (contentbody != null) {
						contentVO.setContentbody(contentbody);
						if (contentVO.getContent() != null
								&& !contentVO.getContent().equals("")) {
							if (logger.isInfoEnabled()) {
								logger
										.info("content="
												+ contentVO.getContent());
							}
						}
					}
					if (contentVO != null) {
						// List<ContentAttach> attachList = this
						// .getContentAttachManager().getByContent(
						// contentVO);
						// if (attachList != null) {
						// this.getRequest().setAttribute("attachList",
						// attachList);
						// }
						if (contentVO.getState() == 4) {
							this.getRequest().setAttribute("action", "modify");
						}

						if (contentVO.getState() == 0) {
							this.getRequest().setAttribute("action",
									"saveToDraft");
						}

						List<ContentAttach> attachList = this.contentAttachManager
								.getByContent(contentVO);

						this.getRequest().setAttribute("contentVO", contentVO);
						req.setAttribute("groupId", groupId);
						this.getRequest()
								.setAttribute("attachList", attachList);
						getContentTypes();
						
						
						List<ZtreeVO> zlist = this.dataDownManager.findPilotByAll();
						ZtreeVO tempVo = null;
						Iterator<ZtreeVO> it = zlist.iterator();
						while(it.hasNext()){
							tempVo = it.next();
							if(tempVo.getId() == contentVO.getTid().intValue()){
								tempVo.setChecked(true);
							}
						}
						
						JSONArray jsonArray = JSONArray.fromObject(zlist);
						String json = jsonArray.toString();
						
						getRequest().setAttribute("jsontrees", json);
						logger.info(json);
						
						
						if (logger.isInfoEnabled()) {
							logger.info("modifyInput() - end - return value=editIn");
						}
						return "editIn";
					}
				}
			}
		}

		if (logger.isInfoEnabled()) {
			logger.info("modifyInput() - end - return value=" + null);
		}
		return null;
	}

	/**
	 * 删除内容入口
	 * 
	 * @return
	 */
	// 添加注解协助实现日志拦截
	@GenericLogger(operateMark = OperateMark.CONTENT_UPDATE, operateDescription = "内容信息管理-删除", isExtend = true)
	public String removeInput() throws DataException, BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("removeInput() - start");
		}

		HttpServletRequest req = this.getRequest();
		String idstring = req.getParameter("cIdstr");

		String groupId = req.getParameter("groupId");
		if (groupId != null) {
			if (groupId.equals("1")) {
				this.setOperateExtned("删除公司新闻");
			} else if (groupId.equals("2")) {
				this.setOperateExtned("删除通知公告");
			}

		}
		if (idstring != null) {
			String[] ids = idstring.split(",");
			ContentInfo tmpcontent = null;
			// List<ContentAttach> sattach = null;
			// ContentAttach element = null;
			// String contentstaticrealpath = null;
			// String filepath = null;
			for (int i = 0; i < ids.length; i++) {
				tmpcontent = this.getContentInfoManager().get(
						new Integer(ids[i]));
				if (tmpcontent != null) {
					// sattach = this.contentAttachManager
					// .getByContent(tmpcontent);
					// if (sattach != null && sattach.size() > 0) {
					// for (int p = 0; p < sattach.size(); p++) {
					// element = sattach.get(p);
					// filepath = element.getLink() + element.getAttName();
					// contentstaticrealpath = req.getSession()
					// .getServletContext().getRealPath(filepath);
					// FileUtil.deleteFile(contentstaticrealpath);
					// }
					//
					// }
					// 
					this.getContentInfoManager().remove(tmpcontent);
				}
			}
		}

		this.getResponse().setContentType("text/html;charset=GBK");
		try {
			ServletOutputStream out = this.getResponse().getOutputStream();
			out.write("true".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (logger.isInfoEnabled()) {
			logger.info("removeInput() - end - return value=removeInput");
		}
		return null;
	}

	/**
	 * 首页内容信息
	 * 
	 * @return
	 * @throws DataException
	 * @throws BusinessException
	 */
	public String indexContent() throws DataException, BusinessException {
		try {
			List<ContentType> typeList = null;
			if (this.getUserSessionInfo().getRoleLevel().intValue() == 0) { // 超管
				typeList = this.contentInfoManager.findContnetType(5);
			} else {
				typeList = this.contentInfoManager.findContnetType(5,
						getUserSessionInfo().getRid());
			}

			getRequest().setAttribute("typeList", typeList);
		} catch (Exception e) {
			logger.error("内容首页显示错误", e);
		}
		return "indexContent";
	}

	/**
	 * 数据下载首页
	 * 
	 * @return
	 */
	public String indexDataDataDownload() {
		String currentPage = getRequest().getParameter("page");
		try {
			// List<ContentType> typeList = null;
			int intpage = 1;
			if (!StringUtil.isBlank(currentPage)) {
				intpage = Integer.valueOf(currentPage);
			}
			ContentSearchCondition searcher = new ContentSearchCondition();
			searcher.setContenttype(1);
			searcher.setState(new Integer(4));
			searcher.setStateSave(new Integer(0));
			searcher.setCtId(Integer.valueOf(IContentInfoManager.DataDownload));

			Page page = this.contentInfoManager.getPageBySearchList(12,
					searcher);
			page.setPage(intpage);
			List<ContentInfo> infoList = this.contentInfoManager
					.getAllBySearchListPage(searcher, page);

			getRequest().setAttribute("pageInfo", page);
			getRequest().setAttribute("infoList", infoList);

			// ContentType type = null;
			// if (this.getUserSessionInfo().getRoleLevel().intValue() == 0) {
			// // 超管
			// type = this.contentInfoManager.findContnetType(12,
			// IContentInfoManager.DataDownload, 0);
			// } else {
			// type = this.contentInfoManager.findContnetType(12,
			// IContentInfoManager.DataDownload, getUserSessionInfo()
			// .getRid());
			// }
			//
			// getRequest().setAttribute("conType", type);
		} catch (Exception e) {
			logger.error("内容首页显示错误", e);
		}
		return "indexDataDown";
	}

	/**
	 * 更多内容页面
	 * 
	 * @return
	 * @throws BussinessException
	 */
	public String moreContent() throws BusinessException {
		String sId = getRequest().getParameter("ctId");
		String currentPage = getRequest().getParameter("page");
		List<ContentType> typeList = this.contentInfoManager.findContnetType();
		getRequest().setAttribute("typeList", typeList);
		if (StringUtil.isBlank(sId) || !StringUtil.isNumeric(sId)) {
			return "moreContent";
		} else {
			int intpage = 1;
			if (!StringUtil.isBlank(currentPage) && StringUtil.isNumeric(sId)) {
				intpage = Integer.valueOf(currentPage);
			}

			ContentSearchCondition searcher = new ContentSearchCondition();
			searcher.setContenttype(1);
			searcher.setState(new Integer(4));
			searcher.setStateSave(new Integer(0));
			searcher.setCtId(Integer.valueOf(sId));
			if (this.getUserSessionInfo().getRoleLevel().intValue() != 0) {
				int rid = getUserSessionInfo().getRid();
				for (int i = 0; i < this.contentInfoManager.permissionIds.length; i++) {
					if (Integer.valueOf(sId).intValue() == contentInfoManager.permissionIds[i]
							.intValue()) {
						String pcenter = this.permissionManager
								.findPcenterByRid(rid,
										PermissionMark.CenterTree_permission
												.getValue());
						searcher.setIds(pcenter);
					}
				}
			}

			Page page = this.contentInfoManager.getPageBySearchList(20,
					searcher);
			page.setPage(intpage);
			List<ContentInfo> infoList = this.contentInfoManager
					.getAllBySearchListPage(searcher, page);

			getRequest().setAttribute("pageInfo", page);
			getRequest().setAttribute("cid", sId);
			getRequest().setAttribute("infoList", infoList);

		}

		getRequest().setAttribute("ctId", sId);
		return "moreContent";
	}

	public String getNextResult() {
		return nextResult;
	}

	public void setNextResult(String nextResult) {
		this.nextResult = nextResult;
	}

	/**
	 * @return 返回 operateExtned。
	 */
	public String getOperateExtned() {
		return operateExtned;
	}

	/**
	 * @param operateExtned
	 *            要设置的 operateExtned。
	 */
	public void setOperateExtned(String operateExtned) {
		this.operateExtned = operateExtned;
	}

}
