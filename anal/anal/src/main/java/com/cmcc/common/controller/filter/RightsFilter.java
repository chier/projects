package com.cmcc.common.controller.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;

import com.cmcc.common.util.UserSessionObj;
//import com.cmcc.framework.model.system.model.ModelInfo;

/**
 * 权限过滤器
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-6
 */
@SuppressWarnings("unchecked")
public class RightsFilter implements Filter {

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(RightsFilter.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		if (hasRights(httpRequest)) {
			chain.doFilter(request, response);
		} else {
			UserSessionObj obj = (UserSessionObj) httpRequest.getSession().getAttribute("userInfo");
			// 如果Session失效直接返回登陆页面
			if (obj == null) {
				// httpResponse.sendRedirect(getRootPath(httpRequest)+
				// "/commons/nosession.jsp");
				httpResponse.sendRedirect(getRootPath(httpRequest) + "/commons/beforetimeout.jsp");
			}else{
				chain.doFilter(request, response);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {

	}

	/**
	 * 获得应用的地址，例如：http://127.0.0.1:8080/mylearningii
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 应用的URL访问地址
	 */
	private String getRootPath(HttpServletRequest request) {

		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName();

		if (request.getServerPort() != 80) {
			basePath += ":" + request.getServerPort() + path;
		} else {
			basePath += path;
		}

		return basePath;
	}

	/**
	 * 获得请求的Action所在的名称空间，例如：/student/common
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 请求的Action所在的名称空间
	 */
	private String getRequestActionNameSpace(HttpServletRequest request) {

		String absoluteUrl = request.getRequestURL().toString();
		String nameSpaceStr = absoluteUrl.substring(getRootPath(request).length(), absoluteUrl
				.lastIndexOf("/"));
		
		if(nameSpaceStr==null||"".equals(nameSpaceStr.trim())){ //命名空间/
			nameSpaceStr = "/";
		}else{
			char a = nameSpaceStr.charAt(nameSpaceStr.indexOf("/") + 1);
			if ("/".equals(a + "")) {
				nameSpaceStr = nameSpaceStr.substring(1, nameSpaceStr.length());
			}
		}
		
		return nameSpaceStr;
		// String nameSpaceStr =
		// absoluteUrl.substring(getRootPath(request).length(),
		// absoluteUrl.lastIndexOf(".portal"));
		// return nameSpaceStr;
	}

	@SuppressWarnings("unused")
	private String getRequestActionNameSubSpace(HttpServletRequest request) {

		String absoluteUrl = request.getRequestURL().toString();
		String nameSpaceStr = absoluteUrl.substring(getRootPath(request).length(), absoluteUrl
				.lastIndexOf(".portal"));
		return nameSpaceStr;
	}

	/**
	 * 获得请求的Action的名字，例如：userOperator!addInput.action
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 请求的Action的名字
	 */
	@SuppressWarnings("unused")
	private String getRequestActionName(HttpServletRequest request) {

		String absoluteUrl = request.getRequestURL().toString();

		String actionName = absoluteUrl.substring(absoluteUrl.lastIndexOf("/") + 1, absoluteUrl.length());

		return actionName;
	}

	/**
	 * 判断用户是否有权限
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return 是否有权限
	 */
	private boolean hasRights(HttpServletRequest request) {

		// logger.info("验证用户权限");
		boolean hasRights = false;
		String nameSpaceStr = getRequestActionNameSpace(request);
		
		if (nameSpaceStr.indexOf("/public/common") != -1) {
			hasRights = true;
		} 

		if (nameSpaceStr.indexOf("/manage/regionalAnalyis") != -1) {
			hasRights = true;
		} 
		
		return hasRights;
	}

}
