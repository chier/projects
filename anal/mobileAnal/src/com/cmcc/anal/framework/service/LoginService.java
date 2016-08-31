package com.cmcc.anal.framework.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.cmcc.anal.common.controller.encrypt.IPasswordEncoder;
import com.cmcc.anal.common.exception.BusinessException;
import com.cmcc.anal.common.exception.DataException;
import com.cmcc.anal.common.util.MapperFactory;
import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.cmcc.anal.framework.model.ComprehensiveModel;
import com.cmcc.anal.framework.model.admin.WebAdmin;
import com.cmcc.anal.framework.persistence.interfaces.IWebAdminDAO;
import com.talent.platform.core.json.JsonWrap;

/**
 * 
 * 
 * @filename: com.cmcc.anal.framework.service.LoginService
 * @copyright: Copyright (c)2014
 * @company: 北京掌中无限信息技术有限公司
 * @author: 张占亮
 * @version: 1.0
 * @create time: 2014-7-6 上午10:44:53
 * @record <table cellPadding="3" cellSpacing="0" style="width:600px"> <thead
 *         style="font-weight:bold;background-color:#e3e197">
 *         <tr>
 *         <td>date</td>
 *         <td>author</td>
 *         <td>version</td>
 *         <td>description</td>
 *         </tr>
 *         </thead> <tbody style="background-color:#ffffeb">
 *         <tr>
 *         <td>2014-7-6</td>
 *         <td>张占亮</td>
 *         <td>1.0</td>
 *         <td>create</td>
 *         </tr>
 *         </tbody> </table>
 */

@Component
@Transactional
public class LoginService {

	private static Logger logger = LoggerFactory.getLogger(LoginService.class);

	/**
	 * other 请求
	 * 
	 * @param jsonRequestVo
	 * @param jsonResponseVo
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	public void report(JsonRequestVo jsonRequestVo,
			JsonResponseVo jsonResponseVo, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String callback = request.getParameter("callback");
		String op = jsonRequestVo.getOp();
		String msg = null;
		Object o = jsonRequestVo.getData();
		JSONObject obj = JSONObject.fromObject(o);

		// 获取综合分析的列表信息
		if ("System.login".equalsIgnoreCase(op)) {
			String temp_account = obj.getString("account");
			String password = obj.getString("password");
			if (temp_account == null || temp_account.isEmpty()
					|| password == null || password.isEmpty()) {
				WebUtils.outputJsonResponseVo(response, op, 2, "缺少必要的参数!", "",callback);
				return;
			}

			boolean isLogin = login(temp_account, password);

			if (isLogin) {
				WebUtils.outputJsonResponseVo(response, op, 200, "登录成功!", "",callback);
			} else {
				WebUtils.outputJsonResponseVo(response, op, 0, "帐号和密码错误！", "",callback);
			}
		}
	}
	
	/**
	 * 当 op == System.login 的时候 调用访方法
	 * 
	 * @param jsonRequestVo
	 * @param jsonResponseVo
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	private void login(JsonRequestVo jsonRequestVo,
			JsonResponseVo jsonResponseVo, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		try {
			Map<String, String> object = (Map<String, String>) jsonRequestVo
					.getData();
			String temp_account = object.get("account");
			String password = object.get("password");
			if (temp_account == null || temp_account.isEmpty()
					|| password == null || password.isEmpty()) {
				jsonResponseVo.setCode(2);
				jsonResponseVo.setMsg("缺少必要的参数");
				WebUtils.outputString(response, JsonWrap
						.beanToJsonString(jsonResponseVo));
				return;
			}

			boolean isLogin = login(temp_account, password);

			if (isLogin) {
				jsonResponseVo.setCode(200);
				jsonResponseVo.setMsg("登录成功!");
				WebUtils.outputString(response, JsonWrap
						.beanToJsonString(jsonResponseVo));
			} else {
				jsonResponseVo.setCode(0);
				jsonResponseVo.setMsg("帐号和密码错误！");
				WebUtils.outputString(response, JsonWrap
						.beanToJsonString(jsonResponseVo));
			}
		} catch (Exception e) {
			logger.error("", e);
			jsonResponseVo.setCode(-1);
			jsonResponseVo.setMsg("系统异常，请联系管理员！");
			WebUtils.outputString(response, JsonWrap
					.beanToJsonString(jsonResponseVo));
		}
	}

	public boolean login(String account, String password)
			throws BusinessException, DataException {
		logger.info("account = " + account + "| password = " + password);
		IWebAdminDAO webAdminDAO = MapperFactory.getMapper(IWebAdminDAO.class);

		WebAdmin userDB = webAdminDAO.findByAdminName(account);

		int flag = validateUserIdAndPassword(userDB, password);
		if (flag == 0) {
			return true;
		}
		return false;
	}

	private int validateUserIdAndPassword(WebAdmin userDB, String password)
			throws BusinessException, DataException {

		// flag为用户名密码是否正确的标志，0：正确； 1：不正确
		int flag = 1;

		if (userDB != null) {
			IPasswordEncoder passwordEncoder = MapperFactory
					.getMapper(IPasswordEncoder.class);
			String pwd = passwordEncoder.encode(password);
			if (userDB.getPassword().equalsIgnoreCase(pwd)) {
				flag = 0;
			}
		}
		return flag;
	}
}
