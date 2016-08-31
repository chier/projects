/**
 * 
 */
package com.cmcc.anal.framework.controller.common;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmcc.anal.common.util.DateTimeUtil;
import com.cmcc.anal.common.util.LogUtils;
import com.cmcc.anal.common.util.StringUtils;
import com.cmcc.anal.common.util.WebUtils;
import com.talent.platform.core.util.JaxbUtils;

/**
 * 
 * @filename: com.pica.wificity.controller.common.CommonController
 * @copyright: Copyright (c)2010
 * @company: talent
 * @author: 谭耀武
 * @version: 1.0
 * @create time: 2012-3-8 下午2:02:09
 * @record <table cellPadding="3" cellSpacing="0" style="width:600px">
 *         <thead style="font-weight:bold;background-color:#e3e197">
 *         <tr>
 *         <td>date</td>
 *         <td>author</td>
 *         <td>version</td>
 *         <td>description</td>
 *         </tr>
 *         </thead> <tbody style="background-color:#ffffeb">
 *         <tr>
 *         <td>2012-3-8</td>
 *         <td>谭耀武</td>
 *         <td>1.0</td>
 *         <td>create</td>
 *         </tr>
 *         </tbody>
 *         </table>
 */
@Controller
@RequestMapping("/common/*")
public class CommonController {

	private static Logger log = LoggerFactory.getLogger(CommonController.class);

	private static Logger httpXmlLog = LogUtils.getHttpXmlLog();

	public static final String XML_FORWARD_PREFIX = "/xmlforward";

	public static final String XML_BODY_ATTR_NAME = "XML_BODY_NAME";

	/**
	 * 
	 */
	public CommonController() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/**
	 * xml请求的统一入口
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
//	@RequestMapping(value = "/dispatch")
//	public void dispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		httpXmlLog.debug("client ip:{}, request url : {}", request.getRemoteAddr(), request.getRequestURL());
//
//		String requestBody = WebUtils.readRequestBody(request);
//		
//		httpXmlLog.debug("requestBody:{}\r\n", requestBody);
//		
//		requestBody = StringUtils.mytrim(requestBody);
//
//		request.setAttribute(XML_BODY_ATTR_NAME, requestBody);
//
//		String msgname = request.getHeader("msgname");
//		
//		try {
//			StringBodyRoot requestObj = (StringBodyRoot)XBlink.fromXml(requestBody, StringBodyRoot.class, null);//(requestBody, NobodyRoot.class);
//			String timestamp = requestObj.getTimestamp();
//			String _msgname = requestObj.getMsgname();
//			
//			
//			try {
//				DateTimeUtil.parse(timestamp, com.pica.wifibase.util.DateTimeUtil.TIMESTAMP_PATTERN);
//			} catch (Exception e) {
//				log.error(e.getMessage(), e);
//				httpXmlLog.error(e.getMessage(), e);
//				
//				StringBodyRoot xmlResponseVo = new StringBodyRoot();
//				xmlResponseVo.setResult(com.pica.wifibase.config.BaseConstant.SrvParmErrorCode.PARM_IS_NULL_ERROR);
//				//xmlResponseVo.setResultdesc("the request body is invalid [the timestamp field'value is invalid]. request body is: \r" + requestBody);
//				xmlResponseVo.setResultdesc("the request body is invalid [the timestamp{"+timestamp+"} field'value is invalid].");
//				myForward(request, response, xmlResponseVo);
//				return;
//			}
//			
//			
//			if (StringUtils.isBlank(_msgname)) {
//				StringBodyRoot xmlResponseVo = new StringBodyRoot();
//				xmlResponseVo.setResult(com.pica.wifibase.config.BaseConstant.SrvParmErrorCode.PARM_IS_NULL_ERROR);
//				xmlResponseVo.setResultdesc("the request body is invalid [the msgname can not be null or blank].");
//				myForward(request, response, xmlResponseVo);
//				return;
//			}
//			
//			
//		} catch (Exception e) {
//			log.error(e.getMessage(), e);
//			httpXmlLog.error(e.getMessage(), e);
//			
//			StringBodyRoot xmlResponseVo = new StringBodyRoot();
//			xmlResponseVo.setResult(com.pica.wifibase.config.BaseConstant.SrvParmErrorCode.PARM_IS_NULL_ERROR);
//			xmlResponseVo.setResultdesc("the request body is invalid.");
//			myForward(request, response, xmlResponseVo);
//			return;
//		}
//
//		if (msgname == null || "".equals(msgname)) {
//			StringBodyRoot xmlResponseVo = new StringBodyRoot();
//			xmlResponseVo.setResult(com.pica.wifibase.config.BaseConstant.SrvParmErrorCode.PARM_IS_NULL_ERROR);
//			xmlResponseVo.setResultdesc("you must set msgname at request header.");
//			myForward(request, response, xmlResponseVo);
//			return;
//		}
//		// 接口名不区分大小写(统一小写)
//		msgname = msgname.toLowerCase();
//		
//		String forward = XML_FORWARD_PREFIX + "/" + msgname + ".talent";
//
//		httpXmlLog.debug("msgname:{}, forward:{}", msgname, forward);
//		
//
//		RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
//		dispatcher.forward(request, response);
//	}
//
//	/**
//	 * 
//	 * @param httprequest
//	 * @param response
//	 * @param outputJsonWhenBodyIsNull 当获取到的内容为null时，是否向请求端发送json响应。true: 发送
//	 * @return
//	 * @throws Exception
//	 */
//	public static String getRequestBody(HttpServletRequest httprequest, HttpServletResponse response,
//			boolean outputJsonWhenBodyIsNull) throws Exception {
//		String o = (String) httprequest.getAttribute(XML_BODY_ATTR_NAME);
//		if (o == null && outputJsonWhenBodyIsNull) {
//			StringBodyRoot xmlResponseVo = new StringBodyRoot();
//			xmlResponseVo.setResult("403");
//			xmlResponseVo.setResultdesc("have not right");
//			myForward(httprequest, response, xmlResponseVo);
//		}
//		return o;
//	}
//
//	/**
//	 * 
//	 * @param httprequest
//	 * @param httpServletResponse
//	 * @param jsonResponseVo
//	 * @throws Exception
//	 */
//	private static void myForward(HttpServletRequest httprequest, HttpServletResponse httpServletResponse,
//			StringBodyRoot xmlResponseVo) throws Exception {
//		String attrName = "tt_wificity_xmlauthentication";
//		String xmlStr = JaxbUtils.obj2xml(xmlResponseVo);
//		httprequest.setAttribute(attrName, xmlStr);
//		String forwardPath = "/common/echoAttribute.talent?attrName=" + attrName
//				+ "&contentType=application/xml;charset=UTF-8";
//		RequestDispatcher dispatcher = httprequest.getRequestDispatcher(forwardPath);
//		dispatcher.forward(httprequest, httpServletResponse);
//	}
//
//	@RequestMapping(value = "/toError/{status}")
//	public String toError(@PathVariable("status") String status) throws Exception {
//		return "error/" + status;
//	}

	/**
	 * 回显示html
	 * 
	 * @param html
	 * @param httpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/echoHtml")
	public static void echoHtml(HttpServletRequest request, HttpServletResponse httpServletResponse, String html)
			throws Exception {
		try {
			String contentType = request.getParameter("contentType");

			if (contentType != null && !"".equals(contentType)) {
				httpServletResponse.setContentType(contentType);
			} else {
				httpServletResponse.setContentType("text/html;charset=UTF-8");
			}

			WebUtils.outputString(httpServletResponse, html);
		} catch (Exception e) {
			log.error("", e);
			WebUtils.outputString(httpServletResponse, e.toString());
		}
	}

	/**
	 * 回显request.getAttribute(attrName)
	 * @param request
	 * @param httpServletResponse
	 * @param html
	 * @throws Exception
	 */
//	@RequestMapping(value = "/echoAttribute")
//	public static void echoAttribute(HttpServletRequest request, HttpServletResponse httpServletResponse,
//			String attrName) throws Exception {
//		try {
//			String contentType = request.getParameter("contentType");
//
//			if (contentType != null && !"".equals(contentType)) {
//				httpServletResponse.setContentType(contentType);
//			} else {
//				httpServletResponse.setContentType("text/html;charset=UTF-8");
//			}
//
//			String html = (String) request.getAttribute(attrName);
//			WebUtils.outputString(httpServletResponse, html);
//		} catch (Exception e) {
//			log.error("", e);
//			WebUtils.outputString(httpServletResponse, e.toString());
//		}
//	}
//
//
//	/**
//	 * 
//	 * @param request
//	 * @param response
//	 * @param password
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "/getDecodePassword")
//	public void getDecodePassword(HttpServletRequest request, HttpServletResponse response, String password)
//			throws Exception {
//		try {
//			String datas = com.alibaba.druid.filter.config.ConfigTools.decrypt(password);
//			WebUtils.outputString(response, datas);
//		} catch (Exception e) {
//			WebUtils.outputString(response, e.getMessage());
//		}
//	}
}
