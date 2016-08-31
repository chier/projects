package com.cmcc.anal.framework.controller.common;

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
import com.cmcc.anal.framework.controller.home.HomeController;

public class OtherController {
	private static Logger logger = LoggerFactory.getLogger(OtherController.class);

	/**
	 * other 请求
	 * @param jsonRequestVo
	 * @param jsonResponseVo
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	public static  void other(JsonRequestVo jsonRequestVo, JsonResponseVo jsonResponseVo, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		//		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

//		request.setCharacterEncoding("UTF-8");
		//
		String jsonStr = request.getParameter("jsonStr");

		String callback = request.getParameter("callback");
		JSONObject jo = JSONObject.fromObject(jsonStr);
		String op = jo.getString("op");
		//		System.out.println(op);

		String msg = null;
		if ("Report.getTime".equalsIgnoreCase(op)) {
			msg = "{\"op\":\"Sys.getTime\",\"code\":0,\"msg\":\"ok\",\"data\":{\"time\":\"2010-12-21 08:30:00\"}}";
		}

		if ("Report.detailChart".equals(op)) {
			msg = "{\"op\":\"Report.detailChart\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-14\",\"total\":\"82.22\",\"orders\":\"45\",\"mark\":1},{\"date\":\"2012-4-15\",\"total\":\"222.22\",\"orders\":\"145\",\"mark\":0},{\"date\":\"2012-4-16\",\"total\":\"111.22\",\"orders\":\"45\",\"mark\":0},{\"date\":\"2012-4-17\",\"total\":\"222.22\",\"orders\":\"345\",\"mark\":0},{\"date\":\"2012-4-18\",\"total\":\"333.22\",\"orders\":\"245\",\"mark\":0},{\"date\":\"2012-4-19\",\"total\":\"666.22\",\"orders\":\"45\",\"mark\":0},{\"date\":\"2012-4-20\",\"total\":\"222.22\",\"orders\":\"145\",\"mark\":0},{\"date\":\"2012-4-21\",\"total\":\"99.22\",\"orders\":\"245\",\"mark\":0},{\"date\":\"2012-4-22\",\"total\":\"222.22\",\"orders\":\"45\",\"mark\":1}]}";
		}

		if ("Report.total".equals(op)) {
			msg = "{\"op\":\"Report.total\",\"code\":0,\"msg\":\"ok\",\"data\":{\"total\":\"123\",\"orders\":\"122\"}}";
		}

		if ("Report.totalLoopGrowth".equals(op)) {
			msg = "{\"op\":\"Report.totalLoopGrowth\",\"code\":0,\"msg\":\"ok\",\"data\":{\"growth\":\"11.43%\",\"flightGrowth\":\"11.43%\",\"hotelGrowth\":\"11.43%\"}}";
		}

		if ("Report.detail".equals(op)) {
			msg = "{\"op\":\"Report.detail\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-15\",\"total\":\"222.22\",\"orders\":\"145\",\"flightOrders\":\"12\",\"flightTotal\":\"1269\",\"hotelOrders\":\"5\",\"hotelTotal\":\"330\"},{\"date\":\"2012-4-16\",\"total\":\"111.22\",\"orders\":\"45\",\"flightOrders\":\"12\",\"flightTotal\":\"1269\",\"hotelOrders\":\"5\",\"hotelTotal\":\"330\"},{\"date\":\"2012-4-17\",\"total\":\"222.22\",\"orders\":\"345\",\"flightOrders\":\"12\",\"flightTotal\":\"1269\",\"hotelOrders\":\"5\",\"hotelTotal\":\"330\"},{\"date\":\"2012-4-18\",\"total\":\"333.22\",\"orders\":\"245\",\"flightOrders\":\"12\",\"flightTotal\":\"1269\",\"hotelOrders\":\"5\",\"hotelTotal\":\"330\"},{\"date\":\"2012-4-19\",\"total\":\"666.22\",\"orders\":\"45\",\"flightOrders\":\"12\",\"flightTotal\":\"1269\",\"hotelOrders\":\"5\",\"hotelTotal\":\"330\"},{\"date\":\"2012-4-20\",\"total\":\"222.22\",\"orders\":\"145\",\"flightOrders\":\"12\",\"flightTotal\":\"1269\",\"hotelOrders\":\"5\",\"hotelTotal\":\"330\"},{\"date\":\"2012-4-21\",\"total\":\"99.22\",\"orders\":\"245\",\"flightOrders\":\"12\",\"flightTotal\":\"1269\",\"hotelOrders\":\"5\",\"hotelTotal\":\"330\"},{\"date\":\"总计\",\"total\":\"99.22\",\"orders\":\"245\",\"flightOrders\":\"12\",\"flightTotal\":\"1269\",\"hotelOrders\":\"5\",\"hotelTotal\":\"330\"},{\"date\":\"环比增长\",\"total\":\"35%\",\"orders\":\"0\",\"flightOrders\":\"0\",\"flightTotal\":\"35%\",\"hotelOrders\":\"0\",\"hotelTotal\":\"35%\"}]}";
		}

		if ("Flight.flightTotal".equals(op)) {
			msg = "{\"op\":\"Flight.flightTotal\",\"code\":0,\"msg\":\"ok\",\"data\":{\"total\":\"123\",\"orders\":\"110\"}}";
		}

		if ("Hotel.hotelTotal".equals(op)) {
			msg = "{\"op\":\"Hotel.hotelTotal\",\" code \":0,\" msg \":\" ok \",\" data \":{\" total \":\" 123 \",\" orders \":\" 110 \"}}";
		}

		if ("Flight.totalDetail".equals(op)) {
			msg = "{\"op\":\"Report.totalDetail\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\"},{\"date\":\"2012-4-16\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\"},{\"date\":\"2012-4-17\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\"},{\"date\":\"2012-4-18\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\"},{\"date\":\"2012-4-19\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\"},{\"date\":\"2012-4-20\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\"}]}";
		}

		if ("Flight.flightOffPoint".equals(op)) {
			// msg = "{\"op\":\"Flight.flightOffPoint\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"offPoint\":\"北京\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"offPoint\":\"天津\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"上海\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"广州\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"深圳\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"其他\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-16\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"offPoint\":\"北京\",\"cancelOrders\":\"5\"},......{\"date\":\"2012-4-20\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"其他\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"offPoint\":\"北京\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"offPoint\":\"天津\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"上海\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"广州\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"深圳\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"其他\",\"cancelOrders\":\"5\"}]}";
			msg = "{\"op\":\"Flight.flightOffPoint\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"offPoint\":\"北京\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"offPoint\":\"天津\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"上海\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"广州\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"深圳\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"其他\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"offPoint\":\"北京\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"offPoint\":\"天津\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"上海\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"广州\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"深圳\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"其他\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"offPoint\":\"北京\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"offPoint\":\"天津\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"上海\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"广州\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"深圳\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"offPoint\":\"其他\",\"cancelOrders\":\"5\"},]}";
		}

		if ("Flight.flightOffPointPieChart".equals(op)) {
			msg = "{\"op\":\"Flight.flightOffPointPieChart\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"flightTotal\":\"17.43%\",\"offPoint\":\"上海\"},{\"flightTotal\":\"9.41%\",\"offPoint\":\"北京\"},{\"flightTotal\":\"17.43%\",\"offPoint\":\"其他\"},{\"flightTotal\":\"17.43%\",\"offPoint\":\"广州\"},{\"flightTotal\":\"20.87%\",\"offPoint\":\"天津\"},{\"flightTotal\":\"17.43%\",\"offPoint\":\"深圳\"}]}";
		}

		if ("Flight.flightAirline".equals(op)) {
			// 	msg = "{\"op\":\"Flight.flightAirline\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"airline\":\"国航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"airline\":\"海航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"深航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"南航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"东航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"其他\",\"cancelOrders\":\"5\"},......{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"airline\":\"国航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"airline\":\"海航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"深航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"南航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"东航\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"其他\",\"cancelOrders\":\"5\"}]}";
			msg = "{\"op\":\"Flight.flightAirline\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"airline\":\"国航\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"airline\":\"海航\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"深航\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"南航\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"东航\",\"cancelOrders\":\"5\"},{\"date\":\"1月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"其他\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"airline\":\"国航\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"airline\":\"海航\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"深航\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"南航\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"东航\",\"cancelOrders\":\"5\"},{\"date\":\"2月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"其他\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"airline\":\"国航\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"airline\":\"海航\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"深航\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"南航\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"东航\",\"cancelOrders\":\"5\"},{\"date\":\"3月\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"airline\":\"其他\",\"cancelOrders\":\"5\"},]}";
		}

		if ("Flight.flightAirlinePieChart".equals(op)) {
			msg = "{\"op\":\"Flight.flightAirlinePieChart\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"flightTotal\":\"17.43%\",\"airline\":\"其他\"},{\"flightTotal\":\"17.43%\",\"airline\":\"南航\"},{\"flightTotal\":\"17.43%\",\"airline\":\"深航\"},{\"flightTotal\":\"20.87%\",\"airline\":\"海航\"},{\"flightTotal\":\"17.43%\",\"airline\":\"东航\"},{\"flightTotal\":\"9.41%\",\"airline\":\"国航\"}]}";
		}

		if ("Flight.flightCabin".equals(op)) {
			msg = "{\"op\":\"Flight.flightCabin\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"82.22\",\"cabin\":\"头等、商务舱\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"182.22\",\"cabin\":\"经济舱全价\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-15\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"cabin\":\"经济舱折扣\",\"cancelOrders\":\"5\"},......{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"cabin\":\"头等、商务舱\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"cabin\":\"经济舱全价\",\"cancelOrders\":\"5\"},{\"date\":\"2012-4-21\",\"flightOrders\":\"45\",\"flightTotal\":\"152.22\",\"cabin\":\"经济舱折扣\",\"cancelOrders\":\"5\"}]}";
		}

		if ("Flight.flightCabinPieChart".equals(op)) {
			msg = "{\"op\":\"Flight.flightCabinPieChart\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"flightTotal\":\"43.73%\",\"cabin\":\"经济舱全价\"},{\"flightTotal\":\"36.53%\",\"cabin\":\"经济舱折扣\"},{\"flightTotal\":\"19.73%\",\"cabin\":\"头等、商务舱\"}]}";
		}

		if ("Flight.detailChart".equals(op)) {
			msg = "{\"op\":\"Flight.detailChart\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-14\",\"flightTotal\":\"82.22\",\"flightOrders\":\"45\",\"mark\":1},{\"date\":\"2012-4-15\",\"flightTotal\":\"222.22\",\"flightOrders\":\"145\",\"mark\":0},{\"date\":\"2012-4-16\",\"flightTotal\":\"111.22\",\"flightOrders\":\"45\",\"mark\":0},{\"date\":\"2012-4-17\",\"flightTotal\":\"222.22\",\"flightOrders\":\"345\",\"mark\":0},{\"date\":\"2012-4-18\",\"flightTotal\":\"333.22\",\"flightOrders\":\"245\",\"mark\":0},{\"date\":\"2012-4-19\",\"flightTotal\":\"666.22\",\"flightOrders\":\"45\",\"mark\":0},{\"date\":\"2012-4-20\",\"flightTotal\":\"222.22\",\"flightOrders\":\"145\",\"mark\":0},{\"date\":\"2012-4-21\",\"flightTotal\":\"99.22\",\"flightOrders\":\"245\",\"mark\":0},{\"date\":\"2012-4-22\",\"flightTotal\":\"222.22\",\"flightOrders\":\"45\",\"mark\":1}]}";
		}

		if ("Hotel.hotelCity".equals(op)) {
			msg = "{\"op\":\"Hotel.hotelCity\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\",\"city\":\"北京\"},{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"182.22\",\"city\":\"天津\"},{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"city\":\"上海\"},{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"city\":\"广州\"},{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"city\":\"深圳\"},{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"city\":\"其他\"},{\"date\":\"2012-4-16\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\",\"city\":\"北京\"},{\"date\":\"2012-4-20\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"city\":\"其他\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\",\"city\":\"北京\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"182.22\",\"city\":\"天津\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"city\":\"上海\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"city\":\"广州\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"city\":\"深圳\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"city\":\"其他\"}]}";
		}

		if ("Hotel.hotelCityPieChart".equals(op)) {
			msg = "{\"op\":\"Hotel.hotelCityPieChart\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"hotelTotal\":\"18.55%\",\"city\":\"上海\"},{\"hotelTotal\":\"10.52%\",\"city\":\"北京\"},{\"hotelTotal\":\"15.08%\",\"city\":\"其他\"},{\"hotelTotal\":\"13.62%\",\"city\":\"广州\"},{\"hotelTotal\":\"23.31%\",\"city\":\"天津\"},{\"hotelTotal\":\"18.92%\",\"city\":\"深圳\"}]}";
		}

		if ("Hotel.totalDetail".equals(op)) {
			msg = "{\"op\":\"Hotel.totalDetail\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\"},{\"date\":\"2012-4-16\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\"},{\"date\":\"2012-4-17\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\"},{\"date\":\"2012-4-18\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\"},{\"date\":\"2012-4-19\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\"},{\"date\":\"2012-4-20\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\"}]}";
		}

		if ("Hotel.hotelStar".equals(op)) {
			// msg = "{\"op\":\"Hotel.hotelStar\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\",\"star\":\"经济型\"},{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"182.22\",\"star\":\"3星\"},{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"4星\"},{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"5星\"},{\"date\":\"2012-4-15\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"其他\"},......{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"182.22\",\"star\":\"经济型\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"3星\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"4星\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"5星\"},{\"date\":\"2012-4-21\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"其他\"}]}";
			msg = "{\"op\":\"Hotel.hotelStar\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"1月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\",\"star\":\"经济型\"},{\"date\":\"1月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"182.22\",\"star\":\"3星\"},{\"date\":\"1月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"4星\"},{\"date\":\"1月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"5星\"},{\"date\":\"1月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"其他\"},{\"date\":\"2月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\",\"star\":\"经济型\"},{\"date\":\"2月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"182.22\",\"star\":\"3星\"},{\"date\":\"2月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"4星\"},{\"date\":\"2月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"5星\"},{\"date\":\"2月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"其他\"},{\"date\":\"3月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"82.22\",\"star\":\"经济型\"},{\"date\":\"3月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"182.22\",\"star\":\"3星\"},{\"date\":\"3月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"4星\"},{\"date\":\"3月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"5星\"},{\"date\":\"3月\",\"hotelOrders\":\"45\",\"hotelTotal\":\"152.22\",\"star\":\"其他\"},]}";
		}

		if ("Hotel.hotelStarPieChart".equals(op)) {
			msg = "{\"op\":\"Hotel.hotelStarPieChart\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"hotelTotal\":\"21.11%\",\"star\":\"其他\"},{\"hotelTotal\":\"21.11%\",\"star\":\"4星\"},{\"hotelTotal\":\"25.27%\",\"star\":\"3星\"},{\"hotelTotal\":\"11.4%\",\"star\":\"经济型\"},{\"hotelTotal\":\"21.11%\",\"star\":\"5星\"}]}";
		}

		if ("Hotel.detailChart".equals(op)) {
			msg = "{\"op\":\"Hotel.detailChart\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"2012-4-14\",\"hotelTotal\":\"82.22\",\"hotelOrders\":\"45\",\"mark\":1},{\"date\":\"2012-4-15\",\"hotelTotal\":\"222.22\",\"hotelOrders\":\"145\",\"mark\":0},{\"date\":\"2012-4-16\",\"hotelTotal\":\"111.22\",\"hotelOrders\":\"45\",\"mark\":0},{\"date\":\"2012-4-17\",\"hotelTotal\":\"222.22\",\"hotelOrders\":\"345\",\"mark\":0},{\"date\":\"2012-4-18\",\"hotelTotal\":\"333.22\",\"hotelOrders\":\"245\",\"mark\":0},{\"date\":\"2012-4-19\",\"hotelTotal\":\"666.22\",\"hotelOrders\":\"45\",\"mark\":0},{\"date\":\"2012-4-20\",\"hotelTotal\":\"222.22\",\"hotelOrders\":\"145\",\"mark\":0},{\"date\":\"2012-4-21\",\"hotelTotal\":\"99.22\",\"hotelOrders\":\"245\",\"mark\":0},{\"date\":\"2012-4-22\",\"hotelTotal\":\"222.22\",\"hotelOrders\":\"45\",\"mark\":1}]}";
		}

		if ("Dept.totalLinearGraph".equals(op)) {
			msg = "{\"op\":\"Dept.totalLinearGraph\",\"code\":\"0\",\"msg\":\"ok\",\"data\":{\"flightTotal\":\"30162\",\"hotelTotal\":\"30162\",\"total\":\"30162\"}}";
		}

		if ("Dept.detailList".equals(op)) {
			msg = "{\"op\":\"Dept.detailList\",\"code\":\"0\",\"msg\":\"ok\",\"data\":[{\"date\":\"21/05/2012\",\"deptCode\":\"dc1234\",\"deptName\":\"无线所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"},{\"date\":\"21/05/2012\",\"deptCode\":\"dc1232\",\"deptName\":\"电商所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"},{\"date\":\"22/05/2012\",\"deptCode\":\"dc1234\",\"deptName\":\"无线所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"},{\"date\":\"22/05/2012\",\"deptCode\":\"dc1232\",\"deptName\":\"电商所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"},{\"date\":\"23/05/2012\",\"deptCode\":\"dc1234\",\"deptName\":\"无线所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"},{\"date\":\"23/05/2012\",\"deptCode\":\"dc1232\",\"deptName\":\"电商所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"},{\"date\":\"24/05/2012\",\"deptCode\":\"dc1234\",\"deptName\":\"无线所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"},{\"date\":\"24/05/2012\",\"deptCode\":\"dc1232\",\"deptName\":\"电商所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"},{\"date\":\"25/05/2012\",\"deptCode\":\"dc1234\",\"deptName\":\"无线所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"},{\"date\":\"25/05/2012\",\"deptCode\":\"dc1232\",\"deptName\":\"电商所\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.0\",\"total\":\"30162.0\"}]}";
		}

		if ("Dept.totalPieChart".equals(op)) {
			msg = "{\"op\":\"Dept.totalPieChart\",\"code\":\"0\",\"msg\":\"ok\",\"data\":[{\"deptCode\":\"dc123456\",\"deptName\":\"无线所\",\"total\":300,\"ratios\":\"30%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"电商所\",\"total\":200,\"ratios\":\"20%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"有线所\",\"total\":100,\"ratios\":\"10%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"电源所\",\"total\":100,\"ratios\":\"10%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"建筑所\",\"total\":100,\"ratios\":\"10%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"其他\",\"total\":100,\"ratios\":\"10%\"}]}";
		}

		if ("Dept.detailChart".equals(op)) {
			msg = "{\"op\":\"Dept.detailChart\",\"code\":\"0\",\"msg\":\"ok\",\"data\":[{\"date\":\"21/05/2012\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.00\",\"total\":\"30162.00\",\"mark\":0},{\"date\":\"22/05/2012\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.00\",\"total\":\"30162.00\",\"mark\":0},{\"date\":\"23/05/2012\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.00\",\"total\":\"30162.00\",\"mark\":0},{\"date\":\"24/05/2012\",\"flightTotal\":\"23591.0\",\"hotelTotal\":\"6571.00\",\"total\":\"30162.00\",\"mark\":0}]}";
		}

		if ("Dept.getList".equals(op)) {
			msg = "{\"op\":\"Dept.getList\",\"code\":\"0\",\"msg\":\"ok\",\"data\":[{\"deptCode\":\"dc123456\",\"deptName\":\"无线所\",\"total\":300,\"ratios\":\"30%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"电商所\",\"total\":200,\"ratios\":\"20%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"有线所\",\"total\":100,\"ratios\":\"10%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"电源所\",\"total\":100,\"ratios\":\"10%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"建筑所\",\"total\":100,\"ratios\":\"10%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"其他\",\"total\":100,\"ratios\":\"10%\"}]}";
		}

		if ("Dept.getAllList".equals(op)) {
			msg = "{\"op\":\"Dept.getAllList\",\"code\":\"0\",\"msg\":\"ok\",\"data\":[{\"deptCode\":\"dc123456\",\"deptName\":\"无线所\",\"total\":300,\"ratios\":\"30%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"电商所\",\"total\":200,\"ratios\":\"20%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"有线所\",\"total\":100,\"ratios\":\"10%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"电源所\",\"total\":100,\"ratios\":\"10%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"建筑所\",\"total\":100,\"ratios\":\"10%\"},{\"deptCode\":\"dc123456\",\"deptName\":\"其他\",\"total\":100,\"ratios\":\"10%\"}]}";
		}

		if ("Dept.areaList".equals(op)) {
			msg = "{\"op\":\"Dept.areaList\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"1月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"huabei\":\"99\",\"xibei\":\"4445\",\"xinan\":\"543\",\"dongbei\":\"654\"},{\"date\":\"2月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"3月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"4月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"5月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"6月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"7月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"}]}";
		}

		if ("Dept.area".equals(op)) {
			msg = "{\"op\":\"Dept.areaList\",\"code\":0,\"msg\":\"ok\",\"data\":[{\"date\":\"1月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"huabei\":\"99\",\"xibei\":\"4445\",\"xinan\":\"543\",\"dongbei\":\"654\"},{\"date\":\"2月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"3月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"4月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"5月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"6月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"},{\"date\":\"7月\",\"deptCode\":\"4321\",\"deptName\":\"部门一\",\"productType\":\"机票金额\",\"huazhong\":\"15\",\"huanan\":\"234\",\"xibei\":\"4445\",\"xinan\":\"543\",\"huabei\":\"99\",\"dongbei\":\"654\"}]}";
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
