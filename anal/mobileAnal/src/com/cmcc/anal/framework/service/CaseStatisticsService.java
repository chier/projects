package com.cmcc.anal.framework.service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.cmcc.anal.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.cmcc.anal.framework.model.CaseStatistics;
import com.cmcc.anal.framework.model.ComprehensiveModel;
import com.cmcc.anal.framework.model.MapModel;
import com.cmcc.anal.framework.model.Pilot;

/**
 * 试点基本分析 service 层实现
 * 
 * @filename: com.cmcc.anal.framework.service.ComprehensiveService
 * @copyright: Copyright (c)2014
 * @company: 北京掌中无限信息技术有限公司
 * @author: 张占亮
 * @version: 1.0
 * @create time: 2014-8-25 下午06:38:32
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
 *         <td>2014-8-25</td>
 *         <td>张占亮</td>
 *         <td>1.0</td>
 *         <td>create</td>
 *         </tr>
 *         </tbody> </table>
 */
@Component
@Transactional
public class CaseStatisticsService extends GenericDAOImpl {
	private static Logger logger = LoggerFactory
			.getLogger(CaseStatisticsService.class);

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
		try{
		String callback = request.getParameter("callback");
		String op = jsonRequestVo.getOp();
		String msg = null;

		// 获取综合分析的列表信息
		if ("CaseStatistics.caseBase".equalsIgnoreCase(op)) {

			Map map = new HashMap();
			Object o = jsonRequestVo.getData();
			JSONObject obj = JSONObject.fromObject(o);
			int year = obj.getInt("years");
			// 查询出所有的信息上报信息
			List<CaseStatistics> csl = this.findCaseStatisticsByYear(year);
			// map.put("csList", csl);
			List<Map> mapList = doCleanUpList(csl);

			long allStatTotle = doAllStatTotle(csl); // 上报总数
			long allPilotTotle = mapList.size(); // 试点总数

			long healthTotle = doCtypeTotle(csl, 1); // 健康总数
			long environmentTotle = doCtypeTotle(csl, 2);// 环境总数

			
			float averageStat = allStatTotle/allPilotTotle;
			float healthAverage = healthTotle/allPilotTotle;
			float environmentAverage = environmentTotle/allPilotTotle;
			
			
			map.put("detailList", mapList);
			map.put("allStatTotle", allStatTotle);
			map.put("allPilotTotle", allPilotTotle);
			map.put("healthTotle", healthTotle);
			map.put("environmentTotle", environmentTotle);
			
			map.put("averageStat", averageStat);
			map.put("healthAverage", healthAverage);
			map.put("environmentAverage", environmentAverage);

			// Pilot p = findPilotById(1);

			WebUtils.outputJsonResponseVo(response, op, 0, "OK", map, callback);
			// msg = "{\"op\" : \"Comprehensive.getList\",\"code\" : 0,\"msg\"
			// :\"ok\",\"data\"
			// :[{\"comId\":\"1\",\"comName\":\"name_1\"},{\"comId\":\"2\",\"comName\":\"name_2\"},{\"comId\":\"3\",\"comName\":\"name_3\"},{\"comId\":\"4\",\"comName\":\"名称_4\"},]}";
		}

		if ("CaseStatistics.detail".equalsIgnoreCase(op)) {
			// Object o = jsonRequestVo.getData();
			// // System.out.println(o);
			// JSONObject obj = JSONObject.fromObject(o);
			// int comId = obj.getInt("comId");
			//
			// Map map = this.findById(comId);
			// WebUtils.outputJsonResponseVo(response, op, 0, "OK", map,
			// callback);
			// msg = "{\"op\" : \"Comprehensive.detail\",\"code\" : 0,\"msg\" :
			// \"ok\",\"data\" :
			// {\"titles\":{\"key\":\"县\",\"value\":\"年工业总产值（合计值)\"},\"result\":[{\"key\":\"大余\",\"value\":\"165697.0\"},{\"key\":\"高淳\",\"value\":\"363664.1\"},{\"key\":\"洪洞\",\"value\":\"1309923.7\"}]}}";
			// WebUtils.outputString(response, msg, callback);
		}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw e;
		}

	}

	public Pilot findPilotById(Integer pid) {
		String sql = "select * from pilot_db where identifier = ?";
		List<Pilot> l = this.getHibernate_Anal()
				.sqlQuery(sql, Pilot.class, pid);
		if (l == null || l.size() == 0) {
			return null;
		}
		return l.get(0);
	}

	public List<CaseStatistics> findCaseStatisticsByYear(int year) {
		String sql = "SELECT * FROM casestatistics_db WHERE YEAR(createTime) = ?";
		List<CaseStatistics> l = this.getHibernate_Anal().sqlQuery(sql,
				CaseStatistics.class, year);
		if (l == null || l.size() == 0) {
			return null;
		}
		return l;
	}

	public long doAllStatTotle(List<CaseStatistics> csl) {
		long allStatTotle = 0;
		for (int i = 0; i < csl.size(); i++) {
			allStatTotle += csl.get(i).getCnumber();
		}
		return allStatTotle;
	}

	public long doCtypeTotle(List<CaseStatistics> csl, int ctype) {
		long healthTotle = 0;
		for (int i = 0; i < csl.size(); i++) {
			if (csl.get(i).getCtype().intValue() == ctype) {
				healthTotle += csl.get(i).getCnumber();
			}
		}
		return healthTotle;
	}

	/**
	 * 对原始数据进行清理
	 * 
	 * @return
	 */
	public List<Map> doCleanUpList(List<CaseStatistics> csl) {
		List<Map> mapList = new ArrayList<Map>();
		Map pilotMap = new HashMap();
		CaseStatistics cs = null;
		for (int i = 0; i < csl.size(); i++) {
			cs = csl.get(i);
			if (pilotMap.get(cs.getPilot().getIdentifier()) == null) {
				pilotMap.put(cs.getPilot().getIdentifier(), cs.getPilot());
				Map map = new HashMap();
				map.put("pid", cs.getPilot().getIdentifier());
				map.put("pname", cs.getPilot().getPname());
				map.put("pCount", 0);
				map.put("createTime", cs.getPilot().getCreateTime());
				doMapData(csl, map);
				mapList.add(map);
			}
		}

		return mapList;
	}

	public void doMapData(List<CaseStatistics> csl, Map map) {
		long count = 0;
		for (int i = 0; i < csl.size(); i++) {
			if (Integer.valueOf(map.get("pid").toString()).intValue() == csl
					.get(i).getPilot().getIdentifier().intValue()) {
				count += csl.get(i).getCnumber().intValue();
			}
		}
		map.put("pCount", count);
	}

}
