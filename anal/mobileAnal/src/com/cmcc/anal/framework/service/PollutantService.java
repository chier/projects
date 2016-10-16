package com.cmcc.anal.framework.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.cmcc.anal.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.cmcc.anal.framework.model.Pilot;

/** 
 * 试点污染物分析  service层实现 
 * @author w
 *
 */
@Component
@Transactional
public class PollutantService extends GenericDAOImpl {

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
//			String msg = null;

			// 获取污染物分析下的试点信息
			if ("Pollutant.getPilots".equalsIgnoreCase(op)) {
				Object o = jsonRequestVo.getData();
				JSONObject obj = JSONObject.fromObject(o);
				int year = obj.getInt("years");
				// 查询出所有的试点信息
				List<Pilot> pList =  findPilotByYear(year);
				WebUtils.outputJsonResponseVo(response, op, 0, "OK", pList,
						callback);
			}
			
			// SAMPLETYPE sampletype
			// 获取污染物分析下的所有样品类别信息
			if ("Pollutant.getSampletype".equalsIgnoreCase(op)) {
				Object o = jsonRequestVo.getData();
				JSONObject obj = JSONObject.fromObject(o);
				int year = obj.getInt("years");	//年份
//				JSONArray pilots = obj.getJSONArray("pilots");
				String strPilots = obj.getString("pilots"); // 试点集合
				String[] arrStr = strPilots.split(",");
				List pilots = new ArrayList();
				for(int i=0;i<arrStr.length;i++){
					pilots.add(arrStr[i]);
				}
				// 查询出所有的试点信息
				List<Map> list = findSampletype(year,pilots);
				WebUtils.outputJsonResponseVo(response, op, 0, "OK", list,
						callback);
			}
			
			// 获取污染特下污染物
			if ("Pollutant.getDetect".equalsIgnoreCase(op)) {
				Object o = jsonRequestVo.getData();
				JSONObject obj = JSONObject.fromObject(o);
				int year = obj.getInt("years");	//年份
				String strSampletypes = obj.getString("sampletypes");
				String pilots = obj.getString("pilots");
//				String pilots = obj.getString("pilots"); // 试点集合
//				strSampletypes = new String(strSampletypes.getBytes("ISO8859-1"),"UTF-8");
				String[] arrStr = strSampletypes.split(",");
				List sampletypes = new ArrayList();
				for(int i=0;i<arrStr.length;i++){
					sampletypes.add(arrStr[i]);
				}
				// 查询出年份、样品类型、试点省份下的污染物
				List<Map> list = findDetect(year,sampletypes,pilots);
				WebUtils.outputJsonResponseVo(response, op, 0, "OK", list,
						callback);
			}
			
			if("Pollutant.getDetectData".equalsIgnoreCase(op)){
				Object o = jsonRequestVo.getData();
				JSONObject obj = JSONObject.fromObject(o);
				int year = obj.getInt("years");	//年份
				String strSampletypes = obj.getString("sampletypes");//样品类别
//				strSampletypes = new String(strSampletypes.getBytes("ISO8859-1"),"UTF8");
				String[] arrStr = strSampletypes.split(",");
				List sampletypes = new ArrayList();
				for(int i=0;i<arrStr.length;i++){
					sampletypes.add(arrStr[i]);
				}
				
				String strPilots = obj.getString("pilots");//试点行政区划代码
//				strPilots = new String(strPilots.getBytes("ISO8859-1"),"UTF8");
				String[] arrPilots = strPilots.split(",");
				List pilots = new ArrayList();
				for(int i=0;i<arrPilots.length;i++){
					pilots.add(arrPilots[i]);
				}
				
				String detectIndex = obj.getString("detectIndex"); // 污染物集合
//				detectIndex = new String(detectIndex.getBytes("ISO8859-1"),"utf8");
				
				String algorithm = obj.getString("algorithm");//统计函数
				
				
				// 查询出所有的试点信息
				List<Map> chartsList = findDetectChartsData(year,sampletypes,pilots,detectIndex,algorithm);
				List<Map> tableList = this.findDetectTableData(year, sampletypes,pilots, detectIndex);
				logger.info("chartsList.size = " + chartsList.size() + "|tableList.size() = " + tableList.size());
				Map result = new HashMap();
				result.put("chartsList", chartsList);
				result.put("tableList", tableList);
				
				WebUtils.outputJsonResponseVo(response, op, 0, "OK", result,
						callback);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 根据年份，返回存在污染物的访年份下的试点
	 * 1.查询 t_jg_datastorage 表，返回 pilotcode 试点id code集合
	 * 2.根据 id 集合，查询  	pilot_db 详细数据
	 * @param year
	 * @return
	 */
	private List<Pilot> findPilotByYear(int year){
		List<Pilot> pList = null;
		String jqSQL = "SELECT STATUNITCODE from t_jg_detectstorage where SURVEYYEAR = '" + year + "' " +
				"AND STATUNITCODE IS NOT NULL GROUP BY STATUNITCODE";
		List<String> jqList = this.getHibernate_Anal().createSQLQuery(jqSQL);
		if(jqList == null || jqList.size() == 0){
			return new ArrayList<Pilot>();
		}
		
		String pHQL = "FROM Pilot WHERE year = " + year + " and statunitcodeshort IN (";
		for(int i=0;i<jqList.size();i++){
			pHQL += "'"+jqList.get(i).substring(0, 2)+"'";
			if(i != jqList.size() -1){
				pHQL += ",";
			}
		}
		pHQL += ") ";
		pList = this.getHibernate_Anal().createQuery(pHQL);
		return pList;
	}
	
	/**
	 * 根据年份和试点 code 信息，返回样品类别，排重处理
	 * @param year
	 * @param pilots
	 * @return
	 */
	private List<Map> findSampletype(int year,List<String> pilots){
		String sql = "select ID,FID,SAMPLETYPE from t_jg_detectstorage where SURVEYYEAR = '" 
				+ year + "' and STATUNITCODESHORT in (";
		for(int i=0;i<pilots.size();i++){
			sql += "'"+pilots.get(i).substring(0, 2)+"'";
			if(i != pilots.size() -1){
				sql += ",";
			}
		}
		sql += ") group by SAMPLETYPE";
		List<Map>  list =  this.getHibernate_Anal().createMapSQLQuery(sql);
		return list;
	}
	
	/**
	 * 根据样品类别，返回污染物类别
	 * @param sampletypes
	 * @return
	 */
	private List<Map> findDetect(int year,List sampletypes,String pilots){
		String sql = "select ID,DETECTINDEX,UNITS from t_jg_detectstorage where SURVEYYEAR=" + year +" AND SAMPLETYPE in ( ";
		for(int i=0;i<sampletypes.size();i++){
			sql += "'" + sampletypes.get(i) + "'";
			if(i != sampletypes.size() -1){
				sql +=",";
			}
		}
		sql += ") AND STATUNITCODESHORT='"+ pilots.substring(0,2) +"' AND DETECTINDEX IS NOT NULL AND DETECTINDEX<>''  group by DETECTINDEX";
		List<Map> list = this.getHibernate_Anal().createMapSQLQuery(sql);
		return list;
	}
	
	/**
	 * 污染物的报表数据
	 * SELECT surveyyear,sampletype,DETECTINDEX,MAX(TESTRESULTSNUM) AS  TESTRESULTSNUM FROM t_jg_detectstorage WHERE surveyyear = '2013' AND sampletype IN ('废气','环境空气','废气','环境空气') AND detectIndex='苯并 (k）荧蒽'
GROUP BY sampletype
	 * @param year	年份
	 * @param sampletypes	样品类别
	 * @param detectIndex	污染物名称
	 * @return
	 */
	private List<Map> findDetectChartsData(int year,List sampletypes,List pilots,String detectIndex,String algorithm){
		String sql = "select surveyyear,sampletype,DETECTINDEX,SUBSTR(samplecode,1,5) as pilotShortName, " 
					+ algorithm + "(TESTRESULTSNUM) AS  TESTRESULTSNUM " +
				"from t_jg_detectstorage where surveyyear = '" + year + "' and sampletype in (";
		for(int i=0;i<sampletypes.size();i++){
			sql += "'"+sampletypes.get(i)+"'";
			if(i != sampletypes.size() -1){
				sql += ",";
			}
		}
		sql += ") and detectIndex='" + detectIndex + "'";
		sql += " and STATUNITCODESHORT in (";
		for(int i=0;i<pilots.size();i++){
			sql += "'"+pilots.get(i).toString().substring(0, 2) +"'";
			if(i != pilots.size()-1){
				sql +=",";
			}
		}
		sql+= ")";
		
		sql += " AND testresultsnum >0  GROUP BY STATUNITCODESHORT,SUBSTR(samplecode,1,5)";
		
		List<Map> list = this.getHibernate_Anal().createMapSQLQuery(sql);
		
		for(Map map : list){
//			String pHQL = "FROM Pilot WHERE year = " + year + " and statunitcodeshort = '" + map.get("STATUNITCODESHORT") +"'";
//			List<Pilot> pList = this.getHibernate_Anal().createQuery(pHQL);
//			map.put("pilotShortName", pList.get(0).getShartName());
			if(map.get("pilotShortName") != null && map.get("pilotShortName").toString().length() == 5){
				map.put("pilotShortName", map.get("pilotShortName").toString().substring(2,5));
			}else{
				map.put("pilotShortName", "");
			}
			
		}
		return list;
	}
	
	/**
	 * 污染物的详细数据
	 * SELECT surveyyear,sampletype,DETECTINDEX,MAX(TESTRESULTSNUM) AS  TESTRESULTSNUM FROM t_jg_detectstorage WHERE surveyyear = '2013' AND sampletype IN ('废气','环境空气','废气','环境空气') AND detectIndex='苯并 (k）荧蒽'
GROUP BY sampletype
	 * @param year	年份
	 * @param sampletypes	样品类别
	 * @param detectIndex	污染物名称
	 * @return
	 */
	private List<Map> findDetectTableData(int year,List sampletypes,List pilots,String detectIndex){
		
		
		String sql = "select surveyyear,sampletype,DETECTINDEX,TESTRESULTSNUM,STATUNITCODESHORT " +
				"from t_jg_detectstorage where surveyyear = '" + year + "' and sampletype in (";
		for(int i=0;i<sampletypes.size();i++){
			sql += "'"+sampletypes.get(i)+"'";
			if(i != sampletypes.size() -1){
				sql += ",";
			}
		}
		sql += ") and detectIndex='" + detectIndex + "'";
		sql += " and STATUNITCODESHORT in (";
		for(int i=0;i<pilots.size();i++){
			sql += "'"+pilots.get(i).toString().substring(0, 2) +"'";
			if(i != pilots.size()-1){
				sql +=",";
			}
		}
		sql+= ") AND TESTRESULTSNUM >0 ";
//		sql += " GROUP BY sampletype";
		List<Map> list = this.getHibernate_Anal().createMapSQLQuery(sql);

		for(Map map : list){
			String pHQL = "FROM Pilot WHERE year = " + year + " and statunitcodeshort = '" + map.get("STATUNITCODESHORT") +"'";
			List<Pilot> pList = this.getHibernate_Anal().createQuery(pHQL);
			map.put("pilotShortName", pList.get(0).getShartName());
		}
		return list;
	}
}
