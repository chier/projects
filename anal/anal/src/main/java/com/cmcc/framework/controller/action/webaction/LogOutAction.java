package com.cmcc.framework.controller.action.webaction;

import com.cmcc.common.Global;
import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.common.util.date.DateUtil;
/**
 * 处理退出登录的ACTION类
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-9
 */
public class LogOutAction extends WebActionBase {

	private static final long serialVersionUID = 697290677642420409L;

	@GenericLogger(operateMark = OperateMark.LOGIN_LOGOUT, operateDescription = "退出管理后台", isOperateEmployee = true, isOperateRecords = false)
	public String execute() throws BusinessException {

		UserSessionObj sessionUserInfo = this.getUserSessionInfo();

		try {
			if (sessionUserInfo != null) {
				sessionUserInfo.setLogOffTime(DateUtil
						.getSystemCurrentDateTime());
			}
			return SUCCESS;
		} catch (Exception e) {
			throw new BusinessException(
					"efetionmanage.framework.exception.logout", e);
		}
	}
}
