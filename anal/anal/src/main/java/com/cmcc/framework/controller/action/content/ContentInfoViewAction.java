/* 
 * 文件名称：ContentInfoViewAction.java
 * 
 * 创建时间：2009-3-3
 *
 * 原始作者：曹巍
 */
package com.cmcc.framework.controller.action.content;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.date.DateUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.ContentFormBean;
import com.cmcc.framework.controller.formbean.ContentType;
import com.cmcc.framework.model.content.ContentAttach;
import com.cmcc.framework.model.content.ContentBody;
import com.cmcc.framework.model.content.ContentInfo;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 内容信息查看action类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-3-3
 */
public class ContentInfoViewAction extends WebActionBase
		implements
			ModelDriven {
	/**
	 * this.formbean
	 */
	private ContentFormBean contentformbean = new ContentFormBean();

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ContentInfoViewAction.class);

	private static final long serialVersionUID = 4675203180467818859L;

	public Object getModel() {
		return this.contentformbean;
	}
	/**
	 * 预览方法
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String frontView() throws BusinessException {
		try {
			HttpServletRequest req = this.getRequest();
			ContentInfo contentVO = this.buildContent(req);
			contentVO.setTitle(StringUtil.str2DefaultTextHtml(contentVO
					.getTitle()));
			contentVO.setAuthor(StringUtil.str2DefaultTextHtml(contentVO
					.getAuthor()));
			contentVO.setContentSource(StringUtil.str2DefaultTextHtml(contentVO
					.getContentSource()));
			AttachMap map = (AttachMap) this.getRequest().getSession(false)
					.getAttribute("att_list");
			if (map != null && map.getSize() > 0) {
				List<ContentAttach> attachList = new ArrayList<ContentAttach>();
				Iterator<ContentAttach> it = map.getAll();
				while (it.hasNext()) {
					attachList.add(it.next());
				}
				this.getRequest().setAttribute("attachList", attachList);

			}
			setCompanyInfo();
			this.getRequest().setAttribute("action", "frontView");
			req.setAttribute("CONTENTINFO", contentVO);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 显示内容信息方法
	 */
	public String execute() {
		HttpServletRequest req = this.getRequest();
		Integer iconid = null;
		String sconid = req.getParameter("contentId");
		String cateName = req.getParameter("cateName");
		String ctId = req.getParameter("ctId");
		if (sconid != null) {
			try {
				iconid = new Integer(sconid);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			if (iconid != null) {
				ContentInfo contentVO = this.contentInfoManager.get(iconid);
				if (contentVO != null) {
					contentVO.setTitle(StringUtil.str2DefaultTextHtml(contentVO
							.getTitle()));
					contentVO.setAuthor(StringUtil
							.str2DefaultTextHtml(contentVO.getAuthor()));
					contentVO.setContentSource(StringUtil
							.str2DefaultTextHtml(contentVO.getContentSource()));
					List<ContentAttach> attachList = this
							.getContentAttachManager().getByContent(contentVO);
					ContentBody body = this.getContentBodyManager().get(
							contentVO.getContentbody().getIdentifier());
					if (body != null) {
						contentVO.setContentbody(body);
					}
					if (attachList != null && attachList.size() > 0) {
						this.getRequest().setAttribute("attachList", attachList);
					}
					setCompanyInfo();
					
					List<ContentType> typeList = this.contentInfoManager.findContnetType();
					this.contentInfoManager.updateAddViewNumber(contentVO.getIdentifier());
					req.setAttribute("CONTENTINFO", contentVO);
					req.setAttribute("action", "view");
					req.setAttribute("typeList", typeList);
					req.setAttribute("cateName", cateName);
					req.setAttribute("ctId", ctId);
					return SUCCESS;
				}
			}

		}
		return null;
	}
	/**
	 * 将ContentFormBean中数据同步复制到ContentInfo VO类中
	 * 
	 * @param request
	 *            请求request
	 * @return ContentInfo 内容信息实体
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private ContentInfo buildContent(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		ContentInfo contentVO = new ContentInfo();
		contentVO.setTitle(contentformbean.getTitle());
		contentVO.setAuthor(contentformbean.getAuthor());
		String content = request.getParameter("content");
		if (content != null) {
			contentVO.setContent(content);
		}

		contentVO.setReleasedate(DateUtil.getNowDate());
		contentVO.setContentSource(contentformbean.getContentSource());
		contentVO.setContentSourceLink(contentformbean.getContentSourceLink());
		contentVO.setDesc(contentformbean.getDesc());
		return contentVO;
	}
	private void setCompanyInfo() {
	}
	/**
	 * formbean getter
	 * 
	 * @return
	 */
	public ContentFormBean getContentformbean() {
		return contentformbean;
	}
	/**
	 * formbean setter
	 * 
	 * @param contentformbean
	 */
	public void setContentformbean(ContentFormBean contentformbean) {
		this.contentformbean = contentformbean;
	}

}
