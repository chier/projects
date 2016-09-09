/*
 * 文件名： ListWebAdminAction.java
 * 
 * 创建日期： 2009-2-25
 *
 * Copyright(C) 2009, by gaojl.
 *
 * 原始作者: <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 *
 */
package com.cmcc.framework.controller.action.analysis;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.config.LoadConfig;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.framework.business.interfaces.analysis.IReportManager;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.model.analysis.Option;
import com.cmcc.framework.model.analysis.Pilot;

/**
 * 区域分析接口
 * 
 * @author <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 * 
 * @version $Revision: 1.3 $
 * 
 * @since 2009-2-25
 */
public class ReportAction extends WebActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1323643877023633828L;
	/**
	 * 区域分析业务层
	 */
	private IReportManager reportManager;

	public IReportManager getReportManager() {
		return reportManager;
	}

	public void setReportManager(IReportManager reportManager) {
		this.reportManager = reportManager;
	}

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(ReportAction.class);

	/**
	 * 获取年份
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 * @throws IOException
	 */
	public String getYears() throws BusinessException, DataException,
			IOException {
		PrintWriter writer = null;
		try {
			Map result = new HashMap();
			result.put("code", "1");
			result.put("msg", "正确");

			List years = LoadConfig.getInstance().getYears();

			result.put("result", years);
			JSONObject object = JSONObject.fromObject(result);
			getResponse().setHeader("Content-type", "text/html;charset=UTF-8");  
			writer = this.getResponse().getWriter();
			String msg = object.toString();
//			logger.info("msg = " + msg);
//			msg = new String(msg.getBytes("GBK"), "ISO8859-1");
//			logger.info("msg = " + msg);
			writer.write(msg);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
				writer = null;
			}
		}
		return null;
	}

	/**
	 * 根据年份，获取所有的试点
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	public String getPilots() throws BusinessException, DataException {
		String paramYears = this.getRequest().getParameter("paramYears");
		PrintWriter writer = null;
		try {

			if (StringUtils.isBlank(paramYears)) {
				paramYears = "null";
			} else {
				if (paramYears.endsWith(",")) {
					paramYears = paramYears.substring(0,
							paramYears.length() - 1);
				}
			}

			Map map = new HashMap();
			map.put("code", "1");
			map.put("msg", "正确");

			List<Pilot> result = this.reportManager
					.findPilotByYears(paramYears);
			List<Map> mapResult = this.replatePilot(result);
			map.put("result", mapResult);

			JSONObject object = JSONObject.fromObject(map);
			getResponse().setHeader("Content-type", "text/html;charset=UTF-8");  
			writer = this.getResponse().getWriter();
			String msg = object.toString();
			writer.write(msg);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
				writer = null;
			}
		}
		return null;
	}

	private List<Map> replatePilot(List<Pilot> list) {
		List<Map> mapList = new ArrayList<Map>(list.size());
		Map map = null;
		Iterator<Pilot> it = list.iterator();
		Pilot p = null;
		while (it.hasNext()) {
			map = new HashMap();
			p = it.next();
			map.put("code", p.getCode());
			map.put("lon", p.getLon());
			map.put("lat", p.getLat());
			map.put("试点区名称", p.getPname());
			map.put("试点区人口", p.getPopulation());
			map.put("试点区面积", p.getAcreage());
			map.put("试点区主产业", p.getMainIndustry());
			map.put("试点区GDP", p.getGdp());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 根据年份和试点code 获取试点下所有选项信息
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	public String getOptions() throws BusinessException, DataException {
		String paramYears = this.getRequest().getParameter("paramYears");
		String paramPilotCode = this.getRequest()
				.getParameter("paramPilotCode");
		PrintWriter writer = null;
		try {

			if (StringUtils.isBlank(paramYears)) {
				paramYears = "null";
			} else {
				if (paramYears.endsWith(",")) {
					paramYears = paramYears.substring(0,
							paramYears.length() - 1);
				}
			}

			if (StringUtils.isBlank(paramPilotCode)) {
				paramPilotCode = "0";
			}

			if (!StringUtils.isNumeric(paramPilotCode)) {
				paramPilotCode = "0";
			}

			Map map = new HashMap();
			map.put("code", "1");
			map.put("msg", "正确");

			List<Option> optionList = this.reportManager
					.findOptionByYearsAndCode(paramYears,
							Integer.valueOf(paramPilotCode));

			List<Map> mapList = this.replateOption(optionList);
			map.put("result", mapList);

			JSONObject object = JSONObject.fromObject(map);
			getResponse().setHeader("Content-type", "text/html;charset=UTF-8");  
			writer = this.getResponse().getWriter();
			String msg = object.toString();
			writer.write(msg);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
				writer = null;
			}
		}
		return null;
	}

	private List<Map> replateOption(List<Option> list) {
		List<Map> mapList = new ArrayList<Map>();
		Iterator<Option> it = list.iterator();
		Option option = null;
		Map map = null;
		while (it.hasNext()) {
			map = new HashMap();
			option = it.next();
			map.put(option.getName(), option.getIdentification());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 根据年份、试点code和选项 获取试点下所有详细信息
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	public String getPilotPlaces() throws BusinessException, DataException {

		String paramYears = this.getRequest().getParameter("paramYears");
		String paramPilotCode = this.getRequest()
				.getParameter("paramPilotCode");
		String paramOptions = this.getRequest().getParameter("paramOptions");
		PrintWriter writer = null;
		try {
			
			if (StringUtils.isBlank(paramYears)) {
				paramYears = "null";
			} else {
				if (paramYears.endsWith(",")) {
					paramYears = paramYears.substring(0,
							paramYears.length() - 1);
				}
			}
			
			if (StringUtils.isBlank(paramOptions)) {
				paramOptions = "null";
			} else {
				paramOptions.replaceAll("[\\t\\n\\r]", "");
				if (paramOptions.endsWith(",")) {
					paramOptions = paramOptions.substring(0,
							paramOptions.length() - 1);
				}
			}

			if (StringUtils.isBlank(paramPilotCode)) {
				paramPilotCode = "0";
			}

			if (!StringUtils.isNumeric(paramPilotCode)) {
				paramPilotCode = "0";
			}
			
			List<Map> list = this.reportManager.findPlaces(paramYears, paramOptions, Integer.valueOf(paramPilotCode));
			
			Map map = new HashMap();
			map.put("code", "1");
			map.put("msg", "正确");

			List<Option> optionList = this.reportManager
					.findOptionByYearsAndCode(paramYears,
							Integer.valueOf(paramPilotCode));

			List<Map> mapList = this.replateOption(optionList);
			map.put("result", list);

			JSONObject object = JSONObject.fromObject(map);
			
			getResponse().setHeader("Content-type", "text/html;charset=UTF-8");  
			writer = this.getResponse().getWriter();
			String str = object.toString();
			writer.write(str);
		} catch (IOException ioe) {
			logger.error(ioe.getMessage(), ioe);
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
				writer = null;
			}
		}
		return null;
	}

}
