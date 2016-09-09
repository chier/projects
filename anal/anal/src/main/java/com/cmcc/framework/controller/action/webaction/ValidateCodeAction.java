package com.cmcc.framework.controller.action.webaction;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cmcc.common.controller.action.BaseAction;

/**
 * 验证码验证action
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-6
 */
public class ValidateCodeAction extends BaseAction {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1883833740664454873L;

	public String execute() throws Exception {
		HttpServletRequest request = this.getRequest();
		PrintWriter out = this.getResponse().getWriter();
		String picCode = request.getParameter("piccode");
		String loginCode = (String)validateUser(request, picCode);
		if(loginCode != null){
			out.print(loginCode);
		}
		return null;
	}
	
	/**
	 * 对登陆验证码检验
	 * @param request
	 * @param picCode
	 * @return 
	 */
	public String validateUser(HttpServletRequest request, String picCode) {
		HttpSession session = request.getSession();
		String checkCode = (String) session.getAttribute("check_code");
		if (checkCode != null && picCode != null && checkCode.equals(picCode)) {
			return "1";	//前台验证码输入正确时
		}
		return "2";	//前台验证码输入错误时
	}
}


