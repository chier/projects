package com.cmcc.anal.framework.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.cmcc.anal.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.cmcc.anal.framework.model.CaseStatistics;

/**
 * 试点上报详情 service 层实现
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
public class CaseInfoService extends GenericDAOImpl {
	private static Logger logger = LoggerFactory
			.getLogger(CaseInfoService.class);

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
		try {
			String callback = request.getParameter("callback");
			String op = jsonRequestVo.getOp();
			String msg = null;

			// 获取综合分析的列表信息
			if ("CaseInfo.PieChart".equalsIgnoreCase(op)) {

				// Map map = new HashMap();
				Object o = jsonRequestVo.getData();
				JSONObject obj = JSONObject.fromObject(o);
				int year = obj.getInt("years");
				// 查询出所有的信息上报信息
				List<CaseStatistics> csl = this.findCaseStatisticsByYear(year);
				// map.put("csList", csl);
				List<Map> mapList = doCleanUpList(csl);
				long allStatTotle = doAllStatTotle(csl); // 上报总数

				doPieChart(mapList, allStatTotle);

				WebUtils.outputJsonResponseVo(response, op, 0, "OK", mapList,
						callback);
			}

			if ("CaseInfo.detailList".equalsIgnoreCase(op)) {
				Object o = jsonRequestVo.getData();
				JSONObject obj = JSONObject.fromObject(o);
				int year = obj.getInt("years");
				// 查询出所有的信息上报信息
				List<CaseStatistics> csl = this.findCaseStatisticsByYear(year);
				List<Map> mapList = detailList(csl);
				WebUtils.outputJsonResponseVo(response, op, 0, "OK", mapList,
						callback);
			}

			if ("CaseInfo.detailChart".equalsIgnoreCase(op)) {
				Object o = jsonRequestVo.getData();
				JSONObject obj = JSONObject.fromObject(o);
				int year = obj.getInt("years");
				int ctype = obj.getInt("ctype");
				// 查询出所有的信息上报信息
				List<CaseStatistics> csl = this.findCaseStatisticsByYear(year);
				List<Map> mapList = this.doDetailChart(csl, ctype);
				WebUtils.outputJsonResponseVo(response, op, 0, "OK", mapList,
						callback);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

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

	/**
	 * 增加饼图的百分比属性
	 * 
	 * @param mapList
	 * @param total
	 */
	private void doPieChart(List<Map> mapList, long total) {
		Iterator<Map> it = mapList.iterator();
		Map map = null;
		float pCount = 0;
		double flRatios = 0;
		DecimalFormat df = new DecimalFormat("0.0");// 格式化小数，不足的补0
		while (it.hasNext()) {
			map = it.next();
			pCount = Float.valueOf(map.get("pCount").toString());
			flRatios = (pCount / total) * 100;

			map.put("ratios", df.format(flRatios));
		}
	}

	/**
	 * 将数据整理成适应列表的数据
	 * 
	 * @param mapList
	 */
	private List<Map> detailList(List<CaseStatistics> csl) {
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
				map.put("total", 0);
				map.put("createTime", cs.getPilot().getCreateTime());
				doDetailListData(csl, map);
				mapList.add(map);
			}
		}
		return mapList;
	}

	public void doDetailListData(List<CaseStatistics> csl, Map map) {
		long count = 0;
		CaseStatistics cs = null;
		for (int i = 0; i < csl.size(); i++) {
			cs = csl.get(i);
			if (Integer.valueOf(map.get("pid").toString()).intValue() == cs
					.getPilot().getIdentifier().intValue()) {
				if (cs.getCtype().intValue() == 1) { // 居民键康类
					map.put("healthTotal", cs.getCnumber());
				}
				if (cs.getCtype().intValue() == 2) { // 环境调查类
					map.put("environmentTotal", cs.getCnumber());
				}
				count += csl.get(i).getCnumber().intValue();
			}
		}
		map.put("total", count);
	}

	private List<Map> doDetailChart(List<CaseStatistics> csl, int type) {
		List<Map> mapList = new ArrayList<Map>();
		Map pilotMap = new HashMap();
		CaseStatistics cs = null;
		for (int i = 0; i < csl.size(); i++) {
			cs = csl.get(i);
			if (cs.getCtype().intValue() == type) {
				Map map = new HashMap();
				map.put("pid", cs.getPilot().getIdentifier());
				map.put("pname", cs.getPilot().getPname());
				map.put("pCount", cs.getCnumber());
				map.put("createTime", cs.getPilot().getCreateTime());
				mapList.add(map);
			}
		}
		return mapList;
	}

}
