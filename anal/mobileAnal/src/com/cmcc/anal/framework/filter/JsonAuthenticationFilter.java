/**
 * 
 */
package com.cmcc.anal.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.cmcc.anal.common.util.LogUtils;
import com.cmcc.anal.common.util.StringUtils;
import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.talent.platform.core.json.JsonWrap;

/**
 * 使用说明：
 * 1、客户端需要在请求头加上JsonAuthenticationFilter=true
 * 2、开发人员通过JsonAuthenticationFilter.getRequestBody(HttpServletRequest, HttpServletResponse, boolean)来获取请求体。
 * @author tanyaowu
 * 
 */
public class JsonAuthenticationFilter implements Filter {

	private static Logger jsonLog = LogUtils.getHttpJsonLog();

	public static String propertyName4Data;

	public static String encoding;

	private static String paramName4SkipAuth = "JsonAuthenticationFilter_skipAuth";

	public static final String ACCOUNT_TOKEN_ID = "account_token";
	
	public static final String PROVIDER_TOKEN_ID = "provider_token";

	/**
	 * 
	 */
	public JsonAuthenticationFilter() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,
			ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		
		String uri = httpRequest.getRequestURI();
		String ctx = httpRequest.getContextPath();
		if ((ctx + "/common/httpclient/getHtml.talent").equals(uri)
				|| (ctx + "/common/echoHtml.talent").equals(uri)
				|| (ctx + "/common/httpclient/init.talent").equals(uri)
				|| (ctx + "/common/httpclient/getCookie.talent").equals(uri)
				|| (ctx + "/account/login.talent").equals(uri)) {
			
			jsonLog.debug("http tool'url. skip auth :{}", httpRequest.getRequestURL());
			filterChain.doFilter(request, response);
			return;
		}

		String header1 = httpRequest.getHeader("JsonAuthenticationFilter");
		if ("true".equals(httpRequest.getParameter(paramName4SkipAuth)) || (header1 == null || "".equals(header1))
				|| !"true".equals(header1)) {
			jsonLog.debug("skip auth :{}", httpRequest.getRequestURL());
			filterChain.doFilter(request, response);
			return;
		}

		jsonLog.debug("need auth:{}", httpRequest.getRequestURL());

		String requestBody = WebUtils.readRequestBody(request);
		
		jsonLog.debug("requestBody:{}", requestBody);
		
		requestBody = StringUtils.mytrim(requestBody);

		JsonRequestVo jsonRequestVo = null;

		try {
			try {
				jsonRequestVo = JsonWrap.jsonStringToBean(requestBody, JsonRequestVo.class);
			} catch (Exception e) {
				jsonLog.error("can not parse: {}", requestBody);
				jsonLog.error(e.getMessage(), e);

				JsonResponseVo jsonResponseVo = new JsonResponseVo();
				jsonResponseVo.setCode(500);
				jsonResponseVo.setMsg("can not parse the string:" + requestBody);
				jsonResponseVo.setData("can not parse the string:" + requestBody);
				myForward(httpRequest, httpServletResponse, jsonResponseVo);
				return;
			}

//			if (check(jsonRequestVo, httpRequest, httpServletResponse)) {
//				jsonLog.debug("token is valid");
//				request.setAttribute(propertyName4Data, requestBody);
//				filterChain.doFilter(request, response);
//				return;
//			} else {
				jsonLog.info("have no right");
				JsonResponseVo jsonResponseVo = new JsonResponseVo();
				jsonResponseVo.setCode(403);
				jsonResponseVo.setMsg("have not right");
				jsonResponseVo.setData("have not right");
				myForward(httpRequest, httpServletResponse, jsonResponseVo);
				return;
//			}
		} catch (Exception e) {
			jsonLog.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param httprequest
	 * @param httpServletResponse
	 * @param jsonResponseVo
	 * @throws Exception
	 */
	private static void myForward(HttpServletRequest httprequest, HttpServletResponse httpServletResponse,
			JsonResponseVo jsonResponseVo) throws Exception {
		String attrName = "tt_wificity_jsonauthentication";
		String jsonStr = JsonWrap.beanToJsonString_Fastjson(jsonResponseVo);
		httprequest.setAttribute(attrName, jsonStr);
		String forwardPath = "/common/echoAttribute.talent?attrName=" + attrName
				+ "&contentType=application/json;charset=UTF-8&" + paramName4SkipAuth + "=true";
		RequestDispatcher dispatcher = httprequest.getRequestDispatcher(forwardPath);
		dispatcher.forward(httprequest, httpServletResponse);
	}

	/**
	 * 
	 * 检查访问权限
	 * @param jsonRequestVo
	 * @param response
	 * @return true: 有权限, 
	 * @throws Exception 
	 */
//	private static boolean check(JsonRequestVo jsonRequestVo, HttpServletRequest httpRequest,
//			HttpServletResponse response) throws Exception {
//
////		String test = httpRequest.getParameter("test_jsonAuthentication");
////
////		if ("true".equals(test)) {
////			return true;
////		}
////		
////		String token = (String) RedisUtils.get(jsonRequestVo.getToken());
////		
////		if ((token) == null) {
////			JsonResponseVo jsonResponseVo = new JsonResponseVo();
////			jsonResponseVo.setCode(BaseConstant.JsonCode.TIMEOUT);
////			jsonResponseVo.setMsg("timeout or not login in");
////			myForward(httpRequest, response, jsonResponseVo);
////			return false;
////		} else {
////			return true;
////		}
//	}

	/**
	 * 
	 * @param httprequest
	 * @param response
	 * @param outputJsonWhenBodyIsNull 当获取到的内容为null时，是否向请求端发送json响应。true: 发送
	 * @return
	 * @throws Exception
	 */
	public static String getRequestBody(HttpServletRequest httprequest, HttpServletResponse response,
			boolean outputJsonWhenBodyIsNull) throws Exception {
		String o = (String) httprequest.getAttribute(propertyName4Data);
		if (o == null && outputJsonWhenBodyIsNull) {
			JsonResponseVo jsonResponseVo = new JsonResponseVo();
//			jsonResponseVo.setCode(com.pica.wifibase.config.BaseConstant.JsonCode.NO_PERMISSION);
			jsonResponseVo.setMsg("have not right");
			myForward(httprequest, response, jsonResponseVo);
		}
		return o;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		propertyName4Data = filterConfig.getInitParameter("propertyName4Data");
		encoding = filterConfig.getInitParameter("encoding");

		jsonLog.debug("propertyName4Data: {}", propertyName4Data);
		jsonLog.debug("encoding: {}", encoding);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		JsonRequestVo jsonAuthenticationVo = new JsonRequestVo();
//
////		Reason reason = new Reason();
////		reason.setConsequence("77777");
////		jsonAuthenticationVo.setData(reason);
////
////		jsonAuthenticationVo.setToken("444444444444444444444");
////
////		String jsonStr = JsonWrap.beanToJsonString(jsonAuthenticationVo);
////
////		JsonRequestVo dd = JsonWrap.jsonStringToBean(jsonStr, JsonRequestVo.class);
//
//		jsonLog.info(dd.toString());

	}

}
