package com.cmcc.anal.framework.controller.common;

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

import com.cmcc.anal.common.util.DateUtil;
import com.cmcc.anal.common.util.LogUtils;
import com.cmcc.anal.common.util.StringUtil;
import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.cmcc.anal.framework.controller.home.HomeController;
import com.cmcc.anal.framework.service.CaseInfoService;
import com.cmcc.anal.framework.service.CaseStatisticsService;
import com.cmcc.anal.framework.service.ComprehensiveService;
import com.cmcc.anal.framework.service.LoginService;
import com.cmcc.anal.framework.service.PollutantService;
import com.cmcc.anal.framework.service.SurveyDataService;
import com.talent.platform.core.json.JsonWrap;

/**
 * 请求的主体页
 * 
 * @filename: com.cmcc.anal.framework.controller.common.ReportController
 * @copyright: Copyright (c)2014
 * @company: 北京掌中无限信息技术有限公司
 * @author: 张占亮
 * @version: 1.0
 * @create time: 2014-7-6 下午4:43:30
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
@Controller
@RequestMapping("/controller/*")
public class ReportController {
	private static Logger logger = LoggerFactory
			.getLogger(ReportController.class);

	private static Logger jsonLog = LogUtils.getHttpJsonLog();

	// public static final String URL_ACCOUNT_LOGIN = "/login";

	public static final String URL_ACCOUNT_REPORT = "/report";

	@Resource(name = "loginService")
	private LoginService loginService;

	@Resource(name = "comprehensiveService")
	private ComprehensiveService comprehensiveService;
	
	@Resource(name = "surveyDataService")
	private SurveyDataService surveyDataService;
	
	@Resource(name = "caseStatisticsService")
	private CaseStatisticsService caseStatisticsService;
	
	
	@Resource(name = "caseInfoService")
	private CaseInfoService caseInfoService;
	
	@Resource(name = "pollutantService")
	private PollutantService pollutantService;
	
	
	@RequestMapping(value = URL_ACCOUNT_REPORT)
	public void report(@RequestBody
	String requestBody, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {

		JsonResponseVo jsonResponseVo = new JsonResponseVo();
		JsonRequestVo jsonRequestVo = null;
		try {
			String jsonStr = request.getParameter("jsonStr");

			// 返回业务响应
			if (StringUtil.isBlank(requestBody)) {
				jsonLog.debug("input:{}", jsonStr);
				requestBody = request.getParameter("tt_requestbody");
				if (StringUtil.isBlank(requestBody)
						&& StringUtil.isBlank(jsonStr)) {
					return;
				}
			} else {
				jsonLog.debug("input:{}", requestBody);
			}

			if (StringUtil.isBlank(requestBody) && !StringUtil.isBlank(jsonStr)) {
				requestBody = jsonStr;
				OtherController.other(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			}

			String callback = request.getParameter("callback");

			jsonRequestVo = JsonWrap.jsonStringToBean_Fastjson(requestBody,
					JsonRequestVo.class);

			// jsonResponseVo.setToken(jsonRequestVo.getToken());
			jsonResponseVo.setOp(jsonRequestVo.getOp());
			jsonResponseVo.setMsg("ok");
			// jsonResponseVo.setCallback(callback);

			if (jsonRequestVo.getOp().indexOf("System") != -1) {
				loginService.report(jsonRequestVo, jsonResponseVo, request, response,
						modelMap);
			} else if ("Report.getTime".equalsIgnoreCase(jsonRequestVo.getOp())) {
				this.reportGetTime(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			} else if (jsonRequestVo.getOp().indexOf("Home") != -1) {
				HomeController.report(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			} else if (jsonRequestVo.getOp().indexOf("Comprehensive") != -1) {
				comprehensiveService.report(jsonRequestVo, jsonResponseVo,
						request, response, modelMap);
			} else if (jsonRequestVo.getOp().indexOf("SurveyData") != -1) {
				surveyDataService.report(jsonRequestVo, jsonResponseVo,
						request, response, modelMap);
			}
			else if (jsonRequestVo.getOp().indexOf("CaseStatistics") != -1) {
				caseStatisticsService.report(jsonRequestVo, jsonResponseVo,
						request, response, modelMap);
			}
			else if (jsonRequestVo.getOp().indexOf("CaseInfo") != -1) {
				caseInfoService.report(jsonRequestVo, jsonResponseVo,
						request, response, modelMap);
			}else if(jsonRequestVo.getOp().indexOf("Pollutant") != -1){
				pollutantService.report(jsonRequestVo, jsonResponseVo, request, response, modelMap);
			}
			else {
				jsonResponseVo.setCode(-1);
				jsonResponseVo.setMsg("请求参数错误！");
				WebUtils.outputString(response, JsonWrap
						.beanToJsonString(jsonResponseVo), callback);
			}
		} catch (Exception e) {
			try {
				OtherController.other(jsonRequestVo, jsonResponseVo, request,
						response, modelMap);
			} catch (Exception e1) {
				logger.error("", e1);
			}
			// logger.error("", e);
			// JsonResponseVo jsonResponseVo = new JsonResponseVo();
			// jsonResponseVo.setResult(-1);
			// jsonResponseVo.setResultdesc("系统异常，请联系管理员！");
			// try {
			// WebUtils.outputString(response,
			// JsonWrap.beanToJsonString(jsonResponseVo));
			// } catch (Exception e1) {
			// logger.error("", e1);
			// }
		}
	}

	

	/**
	 * 当 op == Report.getTime
	 * 
	 * @param jsonRequestVo
	 * @param jsonResponseVo
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	private void reportGetTime(JsonRequestVo jsonRequestVo,
			JsonResponseVo jsonResponseVo, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		String callback = request.getParameter("callback");
		try {
			jsonResponseVo.setCode(0);
			jsonResponseVo.setMsg("服务器端的时间！");

			String currentDate = DateUtil.getSystemCurrentDateTime();
			Map map = new HashMap();
			map.put("time", currentDate);
			jsonResponseVo.setData(map);
			WebUtils.outputString(response, JsonWrap
					.beanToJsonString(jsonResponseVo), callback);
		} catch (Exception e) {
			logger.error("", e);
			jsonResponseVo.setCode(1);
			jsonResponseVo.setMsg("系统异常，请联系管理员！");
			WebUtils.outputString(response, JsonWrap
					.beanToJsonString(jsonResponseVo), callback);
		}
	}
}
