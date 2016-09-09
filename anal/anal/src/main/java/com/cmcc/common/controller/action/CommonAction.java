package com.cmcc.common.controller.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.cmcc.common.util.UserSessionObj;

public class CommonAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2841698232831061531L;

	protected Log logger = LogFactory.getLog(getClass());

	/**
	 * 保存UserSessionObj到用户的Session中，并同步OnlineUserMap。
	 * 
	 * @param sessionObj
	 *            要保存的UserSessionObj
	 */
	public void setUserSessionInfo(UserSessionObj sessionObj) {
		ServletActionContext.getRequest().getSession().setAttribute("userInfo",
				sessionObj);

	}

	/**
	 * 得到UserSessionObj
	 * 
	 * @return 获得的UserSessionObj
	 */
	public UserSessionObj getUserSessionInfo() {
		return (UserSessionObj) ServletActionContext.getRequest().getSession()
				.getAttribute("userInfo");
	}
	
	/**
	 * 获得当前用户的分页数
	 * 
	 * @return 当前用户的分页数
	 */
	public int getUserPageSize() {
		return getUserSessionInfo().getPageSize().intValue();
	}
}
