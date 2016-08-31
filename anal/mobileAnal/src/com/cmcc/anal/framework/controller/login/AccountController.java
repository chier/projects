/**
 * 
 */
package com.cmcc.anal.framework.controller.login;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.cmcc.anal.framework.service.LoginService;
import com.talent.platform.core.json.JsonWrap;

@Controller
@RequestMapping("/account/*")
public class AccountController {

	private static Logger log = LoggerFactory.getLogger(AccountController.class);

	public static final String URL_ACCOUNT_LOGIN = "/login";
	
	
	@Resource(name = "loginService")
	private LoginService loginService;
	
	/**
	 * {"data":{"account":"100002", "password":"111111"}}
	 * 返回json格式：{"data":{"account":{"account":"200","accountid":10001,"createtime":"20121106","email":"litetang@139.com","moblie":"13111111111","modifytime":"20121106","name":"\u6d4b\u8bd5200","password":"111111","role":"","sex":"0","status":0},"roleids":"1"},"result":0,"resultdesc":"\u64cd\u4f5c\u6210\u529f","token":"-yUIo0;-8d8tvMVjOP-RMTQq8Sp9ctuj"}
	 * @param requestBody
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@RequestMapping(value = URL_ACCOUNT_LOGIN)
	public void login(@RequestBody String requestBody, HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {

		try {
			//返回业务响应
			JsonResponseVo jsonResponseVo = new JsonResponseVo();
			if (requestBody == null) {
				return;
			}

		
			
			JsonRequestVo jsonRequestVo = JsonWrap.jsonStringToBean_Fastjson(requestBody, JsonRequestVo.class);
			Map<String, String> object = (Map<String, String>) jsonRequestVo.getData();
			String temp_account = object.get("account");
			String password = object.get("password");
			if (temp_account == null || temp_account.isEmpty() || password == null || password.isEmpty()) {
				jsonResponseVo.setCode(2);
				jsonResponseVo.setMsg("缺少必要的参数");
				WebUtils.outputString(response, JsonWrap.beanToJsonString(jsonResponseVo));
				return;
			}
			
			boolean isLogin = loginService.login(temp_account,password);
			if(isLogin){
				jsonResponseVo.setCode(0);
				jsonResponseVo.setMsg("登录成功!");
				WebUtils.outputString(response, JsonWrap.beanToJsonString(jsonResponseVo));
			}else{
				jsonResponseVo.setCode(0);
				jsonResponseVo.setMsg("帐号和密码错误！");
				WebUtils.outputString(response, JsonWrap.beanToJsonString(jsonResponseVo));
			}
		} catch (Exception e) {
			log.error("", e);
			JsonResponseVo jsonResponseVo = new JsonResponseVo();
			jsonResponseVo.setMsg("登陆失败");
			WebUtils.outputString(response, JsonWrap.beanToJsonString(jsonResponseVo));
		}
	}
}
