package com.cmcc.anal.framework.controller.home;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
 
/**
 * 前端 home 模块的请求
 * 
 * @filename:	 com.cmcc.anal.framework.controller.home.HomeController
 * @copyright:   Copyright (c)2014
 * @company:     北京掌中无限信息技术有限公司
 * @author:      张占亮
 * @version:     1.0
 * @create time: 2014-8-21 上午10:49:19
 * @record
 * <table cellPadding="3" cellSpacing="0" style="width:600px">
 * <thead style="font-weight:bold;background-color:#e3e197">
 * 	<tr>   <td>date</td>	<td>author</td>		<td>version</td>	<td>description</td></tr>
 * </thead>
 * <tbody style="background-color:#ffffeb">
 * 	<tr><td>2014-8-21</td>	<td>张占亮</td>	<td>1.0</td>	<td>create</td></tr>
 * </tbody>
 * </table>
 */
public class HomeController {
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * other 请求
	 * @param jsonRequestVo
	 * @param jsonResponseVo
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	public static  void report(JsonRequestVo jsonRequestVo, JsonResponseVo jsonResponseVo, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//		response.setContentType("text/html");

//		PrintWriter out = response.getWriter();

//		request.setCharacterEncoding("UTF-8");
		//
//		String jsonStr = request.getParameter("jsonStr");

		String callback = request.getParameter("callback");
//		JSONObject jo = JSONObject.fromObject(jsonStr);
//		String op = jo.getString("op");
		String op = jsonRequestVo.getOp();
		//		System.out.println(op);

		String msg = null;
		
		// 返回年份
		if ("Home.getYears".equalsIgnoreCase(op)) {
			msg = "{\"op\":\"Home.getYears\",\"code\":0,\"msg\":\"ok\",\"data\":{\"years\":[\"2012\",\"2013\",\"2014\",\"2015\",\"2016\"],\"currentYears\":\"2013\"}}";
		}
		
		WebUtils.outputString(response,msg,callback);
//		outJonsp(callback, out, msg);
//		out.flush();
//		out.close();
	}

	private static void outJonsp(String callback, PrintWriter out, String msg) {

		try {
			msg = new String(msg.getBytes("UTF-8"), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		out.write(callback + "(" + msg + ")");
	}
}
