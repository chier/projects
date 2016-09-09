package com.cmcc.framework.controller.action.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;

import com.cmcc.common.Global;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.DomTreeBuilderUtil;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.model.WebModel;

/**
 * 模块树显示action
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public class ListModelTreeAction extends WebActionBase {

	private static final long serialVersionUID = 8609432967784654810L;

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ListModelTreeAction.class);

	/**
	 * 管理模块子模块请求处理
	 */
	public String execute() throws BusinessException {
		try {
			UserSessionObj obj = this.getUserSessionInfo();

			if (logger.isDebugEnabled()) {
				logger.debug("ListModelTreeAction.execute()----start");
			}
			if (obj != null) {
				// 判断用户是否校园版
				HttpServletRequest request = this.getRequest();
				String groupId = request.getParameter("groupId");
				if (groupId != null) {
					List<WebModel> returnList = new ArrayList<WebModel>();

					this.getResponse().setContentType(
							"text/xml;charset=" + Global.CHARSET);
					ServletOutputStream stream = this.getResponse()
							.getOutputStream();

					XMLWriter writer = new XMLWriter(stream);
					if (returnList != null && returnList.size() > 0) {

						writer.write(DomTreeBuilderUtil
								.createModuleDomTree(returnList,
										"listModelTree." + Global.ACTION_EXT,
										this.getBasePath(), "type=", null,
										"right", obj));
					} else {
						Document document = DocumentHelper.createDocument();
						writer.write(document);
					}
				}
				if (logger.isDebugEnabled()) {
					logger.debug("ListModelTreeAction.execute()----end");
				}
			} else {
				throw new BusinessException(
						"efetionmanage.framework.exception.noSession");
			}
		} catch (Exception e) {
			System.out.println("异常信息========：" + e.getMessage());
			throw new BusinessException(
					"efetionmanage.framework.exception.modelTree.nomodels");
		}
		return null;
	}

	public String modelList() throws BusinessException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("ListModelTreeAction.modelList()----start");
			}
			
			
			UserSessionObj obj = this.getUserSessionInfo();
			if (obj != null) {
				List<WebModel> resultList = obj.getAllModel();
				List<WebModel> temList = new ArrayList<WebModel>();
				WebModel WebModel = new WebModel();
				if (resultList != null && resultList.size() > 0) {
					for (int i = 0; i < resultList.size(); i++) {
						WebModel = resultList.get(i);
						temList.add(WebModel);
						// }
					}
					this.getRequest().setAttribute("rootGroups",
							resultList.toArray());
					if (logger.isDebugEnabled()) {
						logger.debug("ListModelTreeAction.init()----end");
					}
					return "modelList";
				} else {
					return "modelList";
				}

			} else {
				throw new BusinessException(
						"efetionmanage.framework.exception.noSession");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("msg" + e);
			throw new BusinessException(
					"efetionmanage.framework.exception.modelTree.nomodels");
		}
	}

	/**
	 * 管理模块初始化
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String init() throws BusinessException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("ListModelTreeAction.init()----start");
			}
			UserSessionObj obj = this.getUserSessionInfo();
			if (obj != null) {
				List<WebModel> resultList = obj.getAllModel();
				List<WebModel> temList = new ArrayList<WebModel>();
				WebModel WebModel = new WebModel();
				if (resultList != null && resultList.size() > 0) {
					for (int i = 0; i < resultList.size(); i++) {
						WebModel = resultList.get(i);
						temList.add(WebModel);
						// }
					}
					this.getRequest().setAttribute("rootGroups",
							resultList.toArray());
					if (logger.isDebugEnabled()) {
						logger.debug("ListModelTreeAction.init()----end");
					}
					return "initRoot";
				} else {
					return "initRoot";
				}

			} else {
				throw new BusinessException(
						"efetionmanage.framework.exception.noSession");
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("msg" + e);
			throw new BusinessException(
					"efetionmanage.framework.exception.modelTree.nomodels");
		}
	}

	/**
	 * 鍔犺浇娆㈣繋椤甸潰
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String loadWelcome() throws BusinessException {
		try {
			if (logger.isDebugEnabled()) {
				logger.debug("ListModelTreeAction.init()----start");
			}
			UserSessionObj obj = this.getUserSessionInfo();
			if (obj != null) {
				return SUCCESS;
			} else {
				throw new BusinessException(
						"efetionmanage.framework.exception.noSession");
			}
		} catch (Exception e) {
			throw new BusinessException(
					"efetionmanage.framework.exception.modelTree.nomodels");
		}
	}
}
