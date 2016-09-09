package com.cmcc.common.controller.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cmcc.framework.business.interfaces.workflow.IWorkflowManager;
import com.opensymphony.xwork2.ActionSupport;

/**
 * $Id: BaseAction.java,v 1.5 2010/08/10 09:16:58 weijiguang Exp $
 */

public class BaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8936537693551597249L;

	protected IWorkflowManager workflowManager;

	public IWorkflowManager getWorkflowManager() {
		return workflowManager;
	}

	public void setWorkflowManager(IWorkflowManager workflowManager) {
		this.workflowManager = workflowManager;
	}

	/**
	 * 得到HttpRequest
	 * 
	 * @return 获得的HttpRequest
	 */
	public HttpServletRequest getRequest() {

		return ServletActionContext.getRequest();
	}

	/**
	 * 得到HttpSession
	 * 
	 * @return 获得的HttpSession
	 */
	public HttpSession getSession() {

		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * 得到HttpResponse
	 * 
	 * @return 获得的HttpResponse
	 */
	public HttpServletResponse getResponse() {

		return ServletActionContext.getResponse();
	}

	/**
	 * 得到上下文路径，例如：/portal
	 * 
	 * @return 上下文路径
	 */
	public String getContext() {

		return this.getRequest().getContextPath();
	}

	/**
	 * 得到服务全路径，例如：http://127.0.0.1:8080/portal
	 * 
	 * @return 服务全路径
	 */
	public String getBasePath() {

		String basePath = this.getRequest().getScheme() + "://"
				+ this.getRequest().getServerName() + ":"
				+ this.getRequest().getServerPort() + this.getContext();
		return basePath;
	}
	
	/**
	 * 返回项目所在路径
	 * @return
	 */
	public String getRealPath(){
		String realPath = ServletActionContext.getServletContext().getRealPath("/");
		return realPath;
	}

	protected String writer(String jsonstr) {
		PrintWriter writer = null;
		try {
			writer = this.getResponse().getWriter();
			writer.write(jsonstr);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
		return jsonstr;
	}
}
