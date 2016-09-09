/* 
 * 文件名称：ModifyContentInfoAction.java
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
import com.cmcc.common.util.date.DateUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.ContentFormBean;
import com.cmcc.framework.model.content.ContentBody;
import com.cmcc.framework.model.content.ContentInfo;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * 修改内容信息操作类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.5 $
 * 
 * @since 2009-3-3
 */
public class ModifyContentInfoAction extends WebActionBase
		implements
			ModelDriven {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ModifyContentInfoAction.class);

	/**
	 * this.formbean
	 */
	private ContentFormBean contentformbean = new ContentFormBean();
	private static final long serialVersionUID = -5773872264075715429L;
	/**
	 * 返回模型驱动
	 */
	public Object getModel() {
		return contentformbean;
	}
	private String operateExtned;
	private String nextResult;

	public String getNextResult() {
		return nextResult;
	}

	public void setNextResult(String nextResult) {
		this.nextResult = nextResult;
	}
	// private File up;
	//
	// private String upFileName;
	//
	// private String upContentType;
	/**
	 * 修改内容方法
	 */
	// 添加注解协助实现日志拦截
	@GenericLogger(operateMark = OperateMark.CONTENT_UPDATE,operateDescription = "内容信息管理-修改", isExtend = true)
	public String execute() throws BusinessException {
		if (logger.isDebugEnabled()) {
			logger.debug("execute() - start");
		}

		try {
			// ContentAttach attach = null;

			ContentInfo contentVO = this.cpConVO(contentformbean, this
					.getRequest());
			if (contentVO != null) {
				ContentBody body = this.getContentBodyManager().get(
						contentVO.getContentbody().getIdentifier());
				String content = this.getRequest().getParameter("content");
				contentVO.setContentbody(body);
				if (content != null && !content.equals("")) {
					contentVO.setContent(content);
				}
				// 暂时去掉上传附件功能
				// if (up != null && up.exists()) {
				// if (up.length() > Global.CONTENT_ATTACH_MAXSIZE.longValue())
				// {
				// this.getRequest().setAttribute("beor", "attacherror");
				// if (contentVO != null) {
				// this.getRequest().setAttribute("contentVO",
				// contentVO);
				// }
				// this
				// .getRequest()
				// .setAttribute(
				// "attachMaxSize",
				// StringUtil
				// .changeCapacities(Global.CONTENT_ATTACH_MAXSIZE
				// .longValue()));
				// String groupId = this.getRequest().getParameter(
				// "groupId");
				// if (groupId != null) {
				// this.getRequest().setAttribute("groupId", groupId);
				// }
				// up.delete();
				// return "beor";
				// } else {
				// List<ContentAttach> attlist = this
				// .getContentAttachManager().getByContent(
				// contentVO);
				// if (attlist.size() == 0) {
				// attach = uploadFile();
				// } else {
				// up.delete();
				// }
				// }
				// }
				this.getContentInfoManager().saveOrUpdate(contentVO);
				// if (attach != null) {
				// attach.setContent(contentVO);
				// this.getContentAttachManager().saveOrUpdate(attach);
				// }
				// ContentVersion version = new ContentVersion();
				// Integer adminId = 1;
				// UserSessionObj obj = this.getUserSessionInfo();
				// if (obj != null) {
				// if (obj.getId() != null) {
				// adminId = obj.getId();
				// }
				// }
				// version.setAdminid(adminId);
				// version.setContentinfo(contentVO);
				// version.setOptDesc("修改");
				// version.setOpttime(DateUtil.getNowDate());
				// this.getContentVersionManager().saveOrUpdate(version);
				// String filepath = Global.CONTENT_CATCH_IMAGEPATH;
				// String realpath = this.getRequest().getSession()
				// .getServletContext().getRealPath(filepath);
				// logger.info("filepath=(" + filepath + "),realpath=(" +
				// realpath
				// + ")");
				// CatchImageThread catithread = new CatchImageThread();
				// catithread.start(contentVO.getContent(), contentVO
				// .getContentbody().getIdentifier(), filepath, realpath,
				// this.getBasePath());
				// catithread = null;
				if (logger.isInfoEnabled()) {
					logger.info("execute() - end - return value=" + contentVO);
				}
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
		String groupId = this.getRequest().getParameter("groupId");
		if (groupId == null) {
			groupId = "0";
		}
		if (groupId != null) {
			if (groupId.equals("1") ||groupId.equals("11")||groupId.equals("12")) {
				this.setOperateExtned("修改公司新闻");
			} else if (groupId.equals("2") ||groupId.equals("21")||groupId.equals("22")) {
				this.setOperateExtned("修改通知公告");
			}

		}
		this.nextResult = "groupId=" + groupId;

		return SUCCESS;
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
	private ContentInfo cpConVO(ContentFormBean contentfrom,
			HttpServletRequest request) throws IllegalAccessException,
			InvocationTargetException {
		if (logger.isDebugEnabled()) {
			logger.debug("cpConVO(ContentFormBean contentfrom=" + contentfrom
					+ ", HttpServletRequest request=" + request + ") - start");
		}

		ContentInfo contentVO = this.getContentInfoManager().get(
				this.contentformbean.getIdentifier());
		if (logger.isInfoEnabled()) {
			logger.info("content-" + this.getRequest().getParameter("content"));
		}
		if (contentVO != null) {
			BeanUtils.copyProperties(contentVO, contentfrom);
			// contentVO.setAuthor(StringUtil.StringFilter(contentVO.getAuthor()));
			// contentVO.setTitle(StringUtil.StringFilter(contentVO.getTitle()));
			// contentVO.setContentSource(StringUtil.StringFilter(contentVO
			// .getContentSource()));
			
			String ctId = request.getParameter("ctId");
			ctId = StringUtil.isBlank(ctId) ? "0" : ctId;
			contentVO.setCtId(Integer.valueOf(ctId));
			
			String attIds = request.getParameter("attIds");
			attIds = StringUtil.isBlank(attIds) ? "" : attIds;
			contentVO.setAttIds(attIds);
			
			String istop = request.getParameter("isTop");
			if (istop != null) {
				contentVO.setIsTop(new Integer(1));
			} else {
				contentVO.setIsTop(new Integer(0));
			}
			String action = request.getParameter("action");
			// 如果当前状态是保存状态，则修改时间我状态。
			if(action != null && action.equals("saveToDraft")){
				//contentVO.setReleasedate(DateUtil.getNowDate());
			}else if(action != null && action.equals("modify")){
				contentVO.setState(new Integer(4));
				contentVO.setReleasedate(DateUtil.getNowDate());
			}
			
//			 数据管理试点
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
			
			
		}

		if (logger.isInfoEnabled()) {
			logger.info("cpConVO(ContentFormBean contentfrom=" + contentfrom
					+ ", HttpServletRequest request=" + request
					+ ") - end - return value=" + contentVO);
		}
		return contentVO;
	}
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
