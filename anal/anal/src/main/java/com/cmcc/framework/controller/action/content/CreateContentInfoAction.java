/* 
 * 文件名称：CreateContentInfoAction.java
 * 
 * 创建时间：2009-3-3
 *
 * 原始作者：曹巍
 */
package com.cmcc.framework.controller.action.content;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.common.util.date.DateUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.ContentFormBean;
import com.cmcc.framework.model.content.ContentAttach;
import com.cmcc.framework.model.content.ContentInfo;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 添加新闻内容类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.3 $
 * 
 * @since 2009-3-3
 */

public class CreateContentInfoAction extends WebActionBase implements
		ModelDriven {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(CreateContentInfoAction.class);

	/**
	 * this.formbean
	 */
	private ContentFormBean contentformbean = new ContentFormBean();

	private static final long serialVersionUID = 4675203180467818859L;

	// private File up;
	//
	// private String upFileName;
	//
	// private String upContentType;
	/**
	 * this.nextResult
	 */
	private String nextResult;

	private String operateExtned;

	public Object getModel() {
		return this.contentformbean;
	}

	/**
	 * 添加新闻action
	 */
	// 添加注解协助实现日志拦截
	@GenericLogger(operateMark = OperateMark.CONTENT_ADD, operateDescription = "内容信息管理-添加", isExtend = true)
	public String execute() throws BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("execute() - start");
		}
		HttpServletRequest req = this.getRequest();

		String groupId = req.getParameter("groupId");
		try {
			String content = null;
			ContentAttach attach = null;
			ContentInfo contentVO = this.buildContent(req, new Integer(4));
			content = this.getRequest().getParameter("content");

			if (content != null) {
				contentVO.setContent(content);
			} else {
				contentVO.setContent("");
			}
			if (groupId != null) {
				if (groupId.equals("1") || groupId.equals("11")
						|| groupId.equals("12")) {
					this.setOperateExtned("发布公司新闻");
				} else if (groupId.equals("2") || groupId.equals("21")
						|| groupId.equals("22")) {
					this.setOperateExtned("发布通知公告");
				}
			}

			this.getContentInfoManager().saveOrUpdate(contentVO);
			contentVO.setConorder(contentVO.getIdentifier());
			this.getContentInfoManager().saveOrUpdate(contentVO);
			if (logger.isInfoEnabled()) {
				logger.info("execute() - end - return value=" + contentVO);
			}

		} catch (IllegalAccessException e) {
			if (logger.isErrorEnabled()) {
				logger.error("execute()", e);
			}
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			if (logger.isErrorEnabled()) {
				logger.error("execute()", e);
			}
			e.printStackTrace();
		}
		this.nextResult = "groupId=" + groupId;
		return SUCCESS;
	}

	/**
	 * 将ContentFormBean中数据同步复制到ContentInfo VO类中
	 * 
	 * @param request
	 *            请求request
	 * @param typeState
	 *            操作类型 4 表示 发送 , 0 表示保存
	 * @return ContentInfo 内容信息实体
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private ContentInfo buildContent(HttpServletRequest request,
			Integer typeState) throws IllegalAccessException,
			InvocationTargetException, BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("buildContent(HttpServletRequest request=" + request
					+ ") - start");
		}

		ContentInfo contentVO = new ContentInfo();
		if (logger.isInfoEnabled()) {
			logger.info("content-" + this.getRequest().getParameter("content"));
		}
		BeanUtils.copyProperties(contentVO, contentformbean);
		// contentVO.setAuthor(contentVO.getAuthor());
		// contentVO.setTitle(contentVO.getTitle());
		// contentVO.setContentSource(contentVO.getContentSource());
		UserSessionObj usersession = this.getUserSessionInfo();
		Integer eid = 1;
		if (usersession != null) {
			if (usersession.getEid() != null) {
				eid = usersession.getEid();
			} else {
				throw new BusinessException(
						"efetionmanage.common.error.eiderror");
			}
		} else {
			throw new BusinessException(
					"efetionmanage.common.error.usersessionerror");
		}
		contentVO.setEid(eid);
		if (typeState == 4) {
			contentVO.setReleasedate(DateUtil.getNowDate());
		}
		// Date t = contentVO.getReleasedate();
		// if (t != null) {
		// t.setHours(DateUtil.hour().intValue());
		// t.setMinutes(DateUtil.minute());
		// }
		String contype = request.getParameter("groupId");
		Integer icontype = null;
		if (contype != null) {
			try {
				String sg = String.valueOf(contype);
				if (sg.length() > 1) {
					contype = sg.substring(0, 1);
				}
				icontype = new Integer(contype);
			} catch (NumberFormatException e) {
				if (logger.isErrorEnabled()) {
					logger.error("buildContent(HttpServletRequest request="
							+ request + ")", e);
				}

				throw new BusinessException(
						"efetionmanage.framework.contentManager.contenttypeerror",
						e);
			}

		}

		String ctId = request.getParameter("ctId");
		ctId = StringUtil.isBlank(ctId) ? "0" : ctId;
		contentVO.setCtId(Integer.valueOf(ctId));

		String attIds = request.getParameter("attIds");
		attIds = StringUtil.isBlank(attIds) ? "" : attIds;
		contentVO.setAttIds(attIds);

		contentVO.setAvail_flg(new Integer(1));
		contentVO.setState(typeState);
		contentVO.setIdentifier(null);
		contentVO.setContype(icontype);
		contentVO.setConorder(new Integer(0));
		contentVO.setAdminId(this.getUserSessionInfo().getId()); // 添加外键

		String istop = request.getParameter("isTop");
		if (istop != null) {
			contentVO.setIsTop(new Integer(1));
		} else {
			contentVO.setIsTop(new Integer(0));
		}

		if (logger.isInfoEnabled()) {
			logger.info("buildContent(HttpServletRequest request=" + request
					+ ") - end - return value=" + contentVO);
		}

		// 数据管理试点
		String sid = request.getParameter("sid");
		if (!StringUtils.isBlank(sid) && StringUtils.isNumeric(sid)) {
			contentVO.setSid(Integer.valueOf(sid));
		} else {
			contentVO.setSid(0);
		}
		// 数据管理类别
		String tid = request.getParameter("tid");
		if (!StringUtils.isBlank(tid) && StringUtils.isNumeric(tid)) {
			contentVO.setTid(Integer.valueOf(tid));
		} else {
			contentVO.setTid(0);
		}
		return contentVO;
	}

	/**
	 * 保存新闻action
	 */
	// 添加注解协助实现日志拦截
	public String saveToDraft() throws BusinessException {
		HttpServletRequest req = this.getRequest();
		String groupId = req.getParameter("groupId");
		try {
			String content = null;
			ContentInfo contentVO = this.buildContent(req, new Integer(0));
			content = this.getRequest().getParameter("content");
			if (content != null) {
				contentVO.setContent(content);
			} else {
				contentVO.setContent("");
			}
			if (groupId != null) {
				if (groupId.equals("1") || groupId.equals("11")
						|| groupId.equals("12")) {
					this.setOperateExtned("发布公司新闻");
				} else if (groupId.equals("2") || groupId.equals("21")
						|| groupId.equals("22")) {
					this.setOperateExtned("发布通知公告");
				}

			}
			this.getContentInfoManager().saveOrUpdate(contentVO);
			contentVO.setConorder(contentVO.getIdentifier());
			this.getContentInfoManager().saveOrUpdate(contentVO);
			if (logger.isInfoEnabled()) {
				logger.info("execute() - end - return value=" + contentVO);
			}

		} catch (IllegalAccessException e) {
			if (logger.isErrorEnabled()) {
				logger.error("execute()", e);
			}
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			if (logger.isErrorEnabled()) {
				logger.error("execute()", e);
			}
			e.printStackTrace();
		}
		this.nextResult = "groupId=" + groupId;
		return SUCCESS;
	}

	/**
	 * this.formbean getter
	 * 
	 * @return formbean
	 */
	public ContentFormBean getContentformbean() {
		return contentformbean;
	}

	/**
	 * this.formbean setter
	 * 
	 * @param contentformbean
	 */
	public void setContentformbean(ContentFormBean contentformbean) {
		this.contentformbean = contentformbean;
	}

	/**
	 * this.nextResult getter
	 * 
	 * @return nextResult
	 */
	public String getNextResult() {
		return nextResult;
	}

	/**
	 * this.nextResult setter
	 * 
	 * @param nextResult
	 */
	public void setNextResult(String nextResult) {
		this.nextResult = nextResult;
	}

	// 暂时去掉上传附件功能
	// public File getUp() {
	// return up;
	// }
	// public void setUp(File up) {
	// this.up = up;
	// }
	// public String getUpFileName() {
	// return upFileName;
	// }
	// public void setUpFileName(String upFileName) {
	// this.upFileName = upFileName;
	// }
	// public String getUpContentType() {
	// return upContentType;
	// }
	// public void setUpContentType(String upContentType) {
	// this.upContentType = upContentType;
	// }
	// private ContentAttach uploadFile() {
	// ContentAttach attach = null;
	// String extFilename, filename, outpath, fileTo = "", randomFileName;
	// if (upFileName != null && !upFileName.equals("")) {
	// extFilename = FileUtil.getFileExtName(upFileName);
	// filename = FileUtil.getFileName(upFileName);
	// outpath = this.getRequest().getSession().getServletContext()
	// .getRealPath(Global.CONTENT_STATIC_FILEPATH);
	// if (!FileUtil.fileExist(outpath)) {
	// FileUtil.creatDir(outpath);
	// }
	// randomFileName = FileUtil.getRandomFileName(filename);
	// fileTo = outpath + "/" + randomFileName + "." + extFilename;
	// try {
	// FileUtil.copyfile(up.getAbsolutePath(), fileTo, true);
	// attach = new ContentAttach();
	// attach.setIdentifier(null);
	// attach.setAttName(randomFileName + "." + extFilename);
	// attach.setLink(Global.CONTENT_STATIC_FILEPATH);
	// attach.setAvailflg(new Integer(1));
	// } catch (IOException e) {
	// return null;
	// }
	//
	// }
	//
	// return attach;
	// }
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
