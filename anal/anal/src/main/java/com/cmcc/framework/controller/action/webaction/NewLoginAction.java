/**
 * 文件名： LoginAction.java
 * 
 * 
 * 创建日期： 2009-11-06
 *
 **/

package com.cmcc.framework.controller.action.webaction;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.controller.formbean.ContentType;
import com.cmcc.framework.model.admin.WebAdmin;

/**
 * 处理登录的ACTION类
 * 
 * @since 2009-11-06
 */
public class NewLoginAction extends WebActionBase {

	private static final long serialVersionUID = 5650607074346682847L;

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(NewLoginAction.class);

	/** 判断是哪个版本 * */
	private Integer versionId;

	/** 管理员名称 * */
	private String name;

	private String jltwzy;

	public Integer getVersionId() {

		return versionId;
	}

	public void setVersionId(Integer versionId) {

		this.versionId = versionId;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	@Override
	@GenericLogger(operateMark = OperateMark.LOGIN_LOGIN, operateDescription = "登录分析系统", isOperateEmployee = true, isOperateRecords = false)
	public String execute() throws Exception {

		if (logger.isInfoEnabled()) {
			MDC.put("event", "login");
			logger.info("execute() -start");
		}
		String name = this.getRequest().getParameter("admin"); // 用户名
		String password = this.getRequest().getParameter("password"); // 密码

		logger.info(new StringBuilder(" name.admin = ").append(name).append(
				" | password = ").append(password));
		if (name != null && !"".equals(name) && password != null
				&& !"".equals(password)) {
			name = name.trim();
			password = password.trim();
		}
		WebAdmin userDB = null;
		String errorResult = "passwordError";

		if (name != null && !"".equals(name)) {
			userDB = loginManager.getByLoginId(name);
			if (userDB != null && userDB.getPassword() != null) {
				if (this.validateUserIdAndPassword(userDB, password)
						.equals("1")) {
					UserSessionObj sessionObj = loadData(userDB);

					// 去掉验证码的session
					logger.info("sessionID=" + this.getSession().getId());
					this.getRequest().setAttribute("name", name);
					// if (userDB.getLevel() == 0) {
					return SUCCESS;
					// } else {
					// return "workflowIndex";
					// }
				}
				this.getRequest().setAttribute("relogin", "pwdErr");
				this.getRequest().setAttribute("name", name);
				this.getRequest().setAttribute("password", password);
				return errorResult;
			}
		}
		logger.info("帐号为空");
		this.getRequest().setAttribute("relogin", "nameErr");
		this.getRequest().setAttribute("name", name);
		this.getRequest().setAttribute("password", password);
		return errorResult;
	}

	/**
	 * 验证用户id与密码
	 */
	public String validateUserIdAndPassword(WebAdmin userDB, String password)
			throws BusinessException, DataException {

		// flag为用户名密码是否正确的标志，0：正确； 1：不正确
		String flag = "0";
		// 密码输错的次数
		Integer flogNum = 0;
		if (userDB != null) {
			String pwd = this.getPasswordEncoder().encode(password);
			if (userDB.getPassword().equalsIgnoreCase(pwd)) {
				// if(userDB.getPassword().equalsIgnoreCase(password)){
				userDB.setCount(flogNum);
				this.getWebAdminManager().saveOrUpdate(userDB);
				this.getRequest().setAttribute("flogNum", flogNum);
				flag = "1";
			} else {
				// WebAdmin webAdmin =
				// this.getWebAdminManager().get(userDB.getIdentifier());
				flogNum = userDB.getCount();
				if (flogNum != null && flogNum.intValue() > 1) {
					// String pass = CommonUtil.randomPwd(10);
					// userDB.setPassword(this.getPasswordEncoder().encode(pass));
					// this.getWebAdminManager().saveOrUpdate(userDB);
					flogNum = 3;
				} else {
					flogNum += 1;
				}
				userDB.setCount(flogNum);
				this.getWebAdminManager().saveOrUpdate(userDB);
				this.getRequest().setAttribute("flogNum", flogNum);
			}
		} else {
			this.getRequest().setAttribute("relogin", "nameErr");
		}
		return flag;
	}

	/**
	 * 保存信息到 UserSessionObj 中
	 * 
	 * @param admin
	 * @return
	 * @throws Exception
	 */
	public UserSessionObj loadData(WebAdmin admin) throws Exception {
		UserSessionObj userObj = new UserSessionObj();
		try {
			userObj.setId(admin.getIdentifier());
			userObj.setLoginId(admin.getAdmin());
			userObj.setRoleLevel(admin.getLevel());
			userObj.setSessionId(this.getSession().getId());
			userObj.setRid(admin.getRoleId() == null ? 0 : admin.getRoleId());
			userObj.setIpAddress(this.getRequest().getRemoteAddr());
			userObj.setAllModel(this.loginManager.findModelByAdmin(admin));
			logger.info("|adminId=" + admin.getIdentifier() + " | roleLevel="
					+ admin.getLevel() + "|msg=login admin info|");
			/* 判断是否是超级管理员 */

			logger.info("admin.getIdentifier()" + admin.getIdentifier());
			this.setUserSessionInfo(userObj);
			return userObj;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("err msg:" + e);
			throw new BusinessException(
					"efetionmanage.framework.exception.system");
		}

	}

	/**
	 * 登录首页
	 * @return
	 */
	public String index(){
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
		return "index";
	}
	//
	// /**
	// * 判断用户登陆是否超时
	// *
	// */
	// public void checkTimeout() {
	// try {
	// if (this.getUserSessionInfo() == null) {
	// this.getResponse().getWriter().write("1");
	// } else {
	// this.getResponse().getWriter().write("0");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	public String jltwIndex() {
		return SUCCESS;
	}

	public String getJltwzy() {
		return jltwzy;
	}

	public void setJltwzy(String jltwzy) {
		this.jltwzy = jltwzy;
	}
}
