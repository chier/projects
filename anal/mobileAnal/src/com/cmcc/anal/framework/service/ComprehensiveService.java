package com.cmcc.anal.framework.service;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.cmcc.anal.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.cmcc.anal.framework.model.ComprehensiveModel;
import com.cmcc.anal.framework.model.MapModel;

/**
 * 综合分析 service 层实现
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
public class ComprehensiveService extends GenericDAOImpl {
	private static Logger logger = LoggerFactory
			.getLogger(ComprehensiveService.class);

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

		String callback = request.getParameter("callback");
		String op = jsonRequestVo.getOp();
		String msg = null;
		Object o = jsonRequestVo.getData();
		JSONObject obj = JSONObject.fromObject(o);
		
		// 获取综合分析的列表信息
		if ("Comprehensive.getList".equalsIgnoreCase(op)) {
			int years = obj.getInt("years");
			List<ComprehensiveModel> l = this.findAllByYears(years);
			WebUtils.outputJsonResponseVo(response, op, 0, "OK", l,callback);
//			 msg = "{\"op\" : \"Comprehensive.getList\",\"code\" : 0,\"msg\" :\"ok\",\"data\" :[{\"comId\":\"1\",\"comName\":\"name_1\"},{\"comId\":\"2\",\"comName\":\"name_2\"},{\"comId\":\"3\",\"comName\":\"name_3\"},{\"comId\":\"4\",\"comName\":\"名称_4\"},]}";
		}

		if ("Comprehensive.detail".equalsIgnoreCase(op)) {
		
			int comId = obj.getInt("comId");
			
			Map map = this.findById(comId);
			WebUtils.outputJsonResponseVo(response, op, 0, "OK", map,callback);
//			msg = "{\"op\" : \"Comprehensive.detail\",\"code\" : 0,\"msg\" : \"ok\",\"data\" : {\"titles\":{\"key\":\"县\",\"value\":\"年工业总产值（合计值)\"},\"result\":[{\"key\":\"大余\",\"value\":\"165697.0\"},{\"key\":\"高淳\",\"value\":\"363664.1\"},{\"key\":\"洪洞\",\"value\":\"1309923.7\"}]}}";
//			WebUtils.outputString(response, msg, callback);
			
			
			
		}

	}

	/**
	 * 查询综合分析左侧列表的所有数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ComprehensiveModel> findAllByYears(int years) throws Exception {
		List<ComprehensiveModel> l = new ArrayList<ComprehensiveModel>();
		
		try {
			ComprehensiveModel model = null;
			ComprehensiveModel parModel = null;
			Map<String,ComprehensiveModel> map = new HashMap<String,ComprehensiveModel>();
			String q = "SELECT id, node_name,pid,node_type FROM costomana where time_type =?  ORDER BY pid,id ";
			List<Object[]> cl = getHibernate_Anal().createSQLQuery(q,years);
			for (int i = 0, j = 1; i < cl.size(); i++, j++) {

				if (cl.get(i) != null) {
					model = new ComprehensiveModel();
					Object[] al = cl.get(i);
					model.setComId(al[0] == null ? 0 : Integer.valueOf(al[0]
							.toString()));
					model.setComName(al[1].toString());
					int nodeType = Integer.valueOf(al[3].toString()).intValue();
//					logger.debug("nodeType = " + nodeType + " & left = " + (nodeType == 1));
					model.setLeaf(nodeType== 1);
					
//					if(Integer.valueOf(al[2].toString()) != 0){
//						logger.debug("");
//					}
//					logger.debug("pid = " + al[2] + " | map.get " + map.get(al[2].toString()));
					
					
					if(Integer.valueOf(al[2].toString()) != 0 && map.get(al[2].toString()) != null){
						parModel = map.get(al[2].toString());
						if(parModel.getItems() == null){
							parModel.setItems(new ArrayList<ComprehensiveModel>());
						}
						parModel.getItems().add(model);
					}else{
						map.put(al[0].toString(), model);
					}
					model = null;
				}
			}
			
//			List list = new ArrayList();
			Iterator iter = map.entrySet().iterator();  //获得map的Iterator
			while(iter.hasNext()) {
				Entry entry = (Entry)iter.next();
				l.add((ComprehensiveModel) entry.getValue());
			}
			
		} catch (Exception e) {
			logger.error("查询综合分析左侧列表的所有数据出异常", e);
			throw e;
		}
		return l;
	}

	
	public Map findById(Integer id) throws Exception{
		Map map = new HashMap();
		String sql = "SELECT node_sql,img_config FROM  costomana WHERE id  = ? and img_config IS NOT NULL";
		List<Object[]> ol = getHibernate_Anal().createAliasSQLQuery(sql, id);
		if(ol != null && ol.size() > 0){
			String node_sql = ol.get(0)[0].toString();
			String img_config = ol.get(0)[1].toString();
			
			//
			MapModel titles = this.toModelAndXMLByStr(img_config);
			map.put("titles", titles);
			
			List<MapModel> result  = this.findNodeBySql(node_sql);
			map.put("result", result);
		}
		return map;
		
	}
	/**
	 * 将数据库中存储的XML 放到mapModel对象中，这将是titles
	 * @param img_config
	 * @return
	 */
	private MapModel toModelAndXMLByStr(String img_config){
		MapModel mapModel = new MapModel();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new ByteArrayInputStream(img_config.getBytes("UTF-8")));
			
			Element root = doc.getRootElement();
			Element dataField = root.element("dataField");
			Iterator<Element> it = dataField.elementIterator("field");
			
			if(it.hasNext()){
				mapModel.setKey((it.next().getTextTrim()));
			}
			
			if(it.hasNext()){
				mapModel.setValue((it.next().getTextTrim()));
			}
			
		} catch (DocumentException e) {
			logger.error(e.getMessage(),e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(),e);
		}
		
		return mapModel;
	}
	
	/**
	 * 返回详细的数据信息
	 * @param sql
	 * @return
	 */
	private List<MapModel> findNodeBySql(String sql){
		List<MapModel> list = new ArrayList<MapModel>();
		
		List<Object[]> ol = getHibernate_S9999().createAliasSQLQuery(sql);
		Object[] objs = null;
		MapModel model = null;
		if(ol != null &&  ol.size() > 0){
			for(int i=0;i<ol.size();i++){
				objs = ol.get(i);
				model = new MapModel();
				model.setKey(objs[0] == null ? "" : objs[0].toString());
				model.setValue(objs[1] == null ? "" : objs[1].toString());
				list.add(model);
			}
		}
		
		return list;
	}
	
	public static void main(String []args){
		String img_config = "<chart><dataField>  <field>患有疲乏无力症状</field>  <field>人数</field></dataField><chartSeries><series isHided='false'><seriesType>pie</seriesType><seriesTitle>人数</seriesTitle><valueField>人数</valueField></series></chartSeries><chartTitle>图表名</chartTitle><xAxisField>患有疲乏无力症状</xAxisField><chartD>2D</chartD><chartView showTitle='true' showLegend='true'></chartView><chartControl enableCursor='true' enableScrollbar='true'></chartControl></chart>";
		
		
		
	}
	
	
}
