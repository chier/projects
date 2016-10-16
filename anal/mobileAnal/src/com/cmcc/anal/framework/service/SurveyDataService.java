package com.cmcc.anal.framework.service;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.cmcc.anal.common.Global;
import com.cmcc.anal.common.config.LoadConfig;
import com.cmcc.anal.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.anal.common.persistence.support.Page;
import com.cmcc.anal.common.util.StringUtil;
import com.cmcc.anal.common.util.WebUtils;
import com.cmcc.anal.common.vo.JsonRequestVo;
import com.cmcc.anal.common.vo.JsonResponseVo;
import com.cmcc.anal.framework.model.CodeExprVO;
import com.cmcc.anal.framework.model.ItemTableVO;
import com.cmcc.anal.framework.model.MapModel;
import com.cmcc.anal.framework.model.SurveyDataModel;

/**
 * 调查数据 service 层实现
 * 
 * @filename: com.cmcc.anal.framework.service.SurveyDataService
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
public class SurveyDataService extends GenericDAOImpl {
	private static Logger logger = Logger
			.getLogger(SurveyDataService.class);

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
//		String msg = null;
		Object o = jsonRequestVo.getData();
		JSONObject obj = JSONObject.fromObject(o);
		
		
		// 获取调查数据的列表信息
		if ("SurveyData.getList".equalsIgnoreCase(op)) {
			int years = 2013;
			String strYears = obj.getString("years");
			if(!StringUtil.isBlank(strYears) && StringUtil.isNumeric(strYears)){
				years = Integer.valueOf(strYears);
			}
			
			List<SurveyDataModel> l = null; //this.findByYears(0);
//			l = this.findByCen_Object(years);
			// 2016.9.24 修改  zhangzlg 不再读取S9999的cen_obje表，改读  anal的structure_db
			l = findStructure(years);
			
			if(l.size() == 0){
				WebUtils.outputJsonResponseVo(response, op, 0, "OK", "", callback);
			}else{
//				String strCodes = findCen_Object_NameByCen_Group(l);
//				findByCen_object_table_rel(l,strCodes);
	//			WebUtils.outputJsonResponseVo(response, op, 0, "OK", "[{\"text\":\"父类\",\"items\":[{\"text\":\"Asia\",\"items\":[{\"text\":\"Japan\",\"items\":[{\"text\":\"Acura\",\"leaf\":true},{\"text\":\"Honda\",\"leaf\":true},{\"text\":\"Infiniti\",\"leaf\":true},{\"text\":\"Mitsubishi\",\"leaf\":true},{\"text\":\"Nissan\",\"leaf\":true},{\"text\":\"Scion\",\"leaf\":true},{\"text\":\"Subaru\",\"leaf\":true},{\"text\":\"Toyota\",\"leaf\":true}]},{\"text\":\"Korea\",\"items\":[{\"text\":\"Hyundai\",\"leaf\":true},{\"text\":\"Kia\",\"leaf\":true}]}]},{\"text\":\"United Kingdom\",\"items\":[{\"text\":\"Aston Martin\",\"leaf\":true},{\"text\":\"Bentley\",\"leaf\":true},{\"text\":\"TVR\",\"leaf\":true},{\"text\":\"Land Rover\",\"leaf\":true}]},{\"text\":\"Europe\",\"items\":[{\"text\":\"Germany\",\"items\":[{\"text\":\"Audi\",\"leaf\":true},{\"text\":\"BMW\",\"leaf\":true},{\"text\":\"Opel\",\"leaf\":true},{\"text\":\"Porsche\",\"leaf\":true},{\"text\":\"Volkswagen\",\"leaf\":true}]},{\"text\":\"France\",\"items\":[{\"text\":\"Citro?n\",\"leaf\":true},{\"text\":\"Renault\",\"leaf\":true},{\"text\":\"Peugeot\",\"leaf\":true}]}]},{\"text\":\"United States\",\"items\":[{\"text\":\"Buick\",\"leaf\":true},{\"text\":\"Cadillac\",\"leaf\":true},{\"text\":\"Chevrolet\",\"leaf\":true},{\"text\":\"Chrysler\",\"leaf\":true},{\"text\":\"Ford\",\"leaf\":true},{\"text\":\"Jeep\",\"leaf\":true},{\"text\":\"Oldsmobile\",\"leaf\":true},{\"text\":\"Saturn\",\"leaf\":true},{\"text\":\"Tesla\",\"leaf\":true}]}]}]", callback);
				
				WebUtils.outputJsonResponseVo(response, op, 0, "OK", l, callback);
			}
		}

		// 查询调查数据的字段信息，并组成查询条件页面列表
		if ("SurveyData.tblItem".equalsIgnoreCase(op)) {
			String tblCode = obj.getString("tblCode");
			tblCode = tblCode.toLowerCase();
			List<MapModel> l = findTabItemByTblCode(tblCode);
			WebUtils.outputJsonResponseVo(response, op, 0, "OK", l, callback);
		}
		
		// 列表信息的事件
		if ("SurveyData.detail".equalsIgnoreCase(op)) {
			Integer intPage = 0;
			String tblCode = obj.getString("tblCode");
			tblCode = tblCode.toLowerCase();
			String currPage = obj.getString("page");
			String searchValue = obj.getString("searchValue");
//			String strYears = obj.getString("years");
			
			if (!StringUtil.isBlank(currPage) && StringUtil.isNumeric(currPage)) {
				intPage = Integer.valueOf(currPage);
				if(intPage < 0){
					intPage = 0;
				}
			}
			if(!StringUtil.isBlank(searchValue)){
				searchValue = new String(searchValue.getBytes("ISO8859-1"),"UTF-8");
			}

			// 查询出表头名称
			List<ItemTableVO> itemVoList = this.findItemTableBy(tblCode);
			Object[] arrays = this.findByTableItem(tblCode, itemVoList,
					searchValue, intPage, Global.PAGESIZE);
			List<Object[]> tableList = (List<Object[]>) arrays[0];
			Page pageInfo = (Page) arrays[1];

			List<Map> tableMapList = new ArrayList<Map>(tableList.size());
			Iterator<Object[]> it = tableList.iterator();
			Map tableMap = null;
			Object[] objs = null;
			ItemTableVO vo = null;
			while (it.hasNext()) {
				objs = it.next();
				tableMap = new HashMap();
				for (int i = 0; i < itemVoList.size(); i++) {
					vo = itemVoList.get(i);
					tableMap.put(vo.getItemCode(), objs[i]);
				}
				tableMapList.add(tableMap);
				tableMap = null;
				objs = null;
			}

			Map map = new HashMap();

			map.put("itemList", itemVoList);
			map.put("tableList", tableMapList);
			map.put("pageInfo", pageInfo);
			map.put("itemCount", itemVoList.size());

			map.put("tableCode", tblCode);
			map.put("searchValue", searchValue);

			WebUtils.outputJsonResponseVo(response, op, 0, "OK", map, callback);

		}

		// 查询调查数据的字段信息，并组成查询条件页面列表
		// 原始数据按钮事件
		if ("SurveyData.japserInfo".equalsIgnoreCase(op)) {
			String tblCode = obj.getString("tblCode");
			tblCode = tblCode.toLowerCase();
			String code = obj.getString("code");
			logger.info("tableCode = " + tblCode + " |code=" + code + "");

			String jrxmPath = findJrxmlinfoByTableCode(tblCode);
			logger.info(jrxmPath);
//			String html = "我想要怎么显示就延长显示" + jrxmPath;
			//			
			// Map map = this.findById(comId);
			
			int rowNum = this.getRownumByMysql(tblCode, code);
			String html = viewJrxml(jrxmPath,request,rowNum == 0 ?0:rowNum -1);
			WebUtils.outputJsonResponseVo(response, op, 0, "OK", html, callback);
		}
		
		if("SurveyData.dfPicInfo".equalsIgnoreCase(op)){
			String tblCode = obj.getString("tblCode");
			tblCode = tblCode.toLowerCase();
			String code = obj.getString("code");
			logger.info("tableCode = " + tblCode + " |code=" + code + "");
			String filePath = this.dfPicInfo(tblCode, code);
//			String jrxmPath = findJrxmlinfoByTableCode(tblCode);
//			logger.info(jrxmPath);
			//			
			// Map map = this.findById(comId);
			
//			int rowNum = this.getRownumByMysql(tblCode, code);
//			String html = viewJrxml(jrxmPath,request,rowNum == 0 ?0:rowNum -1);
			WebUtils.outputJsonResponseVo(response, op, 0, "OK", filePath, callback);
		}
	}
	
	/**
	 * *8
	 * @param tblCode
	 * @param code
	 * @return
	 */
	private  String dfPicInfo(String tblCode,String code){
		String sql = "SELECT `code`,DFRASTER,DFSTUDY FROM `" + tblCode +"` where `code` = '" + code + "'";
		List<Map> result = this.getHibernate_S9999().createMapSQLQuery(sql);
		String filePath = null;
		if(result != null && result.size() >0){
			Map map = result.get(0);
			String dfraster = map.get("DFRASTER") == null ?"":map.get("DFRASTER").toString();
			String dfstudy = map.get("DFSTUDY") == null ?"":map.get("DFSTUDY").toString();
			
			String suveyDFPicUrl = LoadConfig.getInstance().getSuveyDFPngUrl();
			String picUrl = this.findPicUrl(dfstudy);
			
			suveyDFPicUrl = suveyDFPicUrl + "\\" + picUrl + "\\pages\\";
			dfraster = dfraster.replace("/", "\\\\");
			suveyDFPicUrl += dfraster;
			suveyDFPicUrl += ".png";
			
			File file = new File(suveyDFPicUrl);
			if(file.isFile()){
				System.out.println(file.getAbsolutePath());
				filePath = file.getAbsolutePath();
			}else{
				System.out.println("不是文件");
			}
			
		}
		return filePath;
	}
	
	private String findPicUrl(String dfstudy){
		int length = dfstudy.length();
		StringBuilder sbui = new StringBuilder("s");
		if(length <3){
			int i = 3-length;
			while(i>0){
				sbui.append("0");
				i--;
			}
		}
		sbui.append(dfstudy);
		return sbui.toString();
	}

	/**
	 * 2016.9.24 修改  zhangzlg 不再读取S9999的cen_obje表，改读  anal的structure_db
	 * mobileCharts 调查数据的左侧列表数
	 * @param years
	 * @return
	 * @throws Exception
	 * @author zhangzhanliang
	 */
	public List<SurveyDataModel> findStructure(int years) throws Exception{
		List<SurveyDataModel> l = new ArrayList<SurveyDataModel>();
		Integer sid = this.topSid(years);
		if(sid == -1){
			return l;
		}
		queryMiddleItems(l,sid,null);
		return l;
	}
	
	/**
	 * 查询最顶层数据
	 * @param years
	 * @return
	 * @author zhangzhanliang
	 */
	private Integer topSid(int years){
		String topSQL = "select sid from structure_db where sname = '"
				+ years + "年'";
		List<Integer> topList = this.getHibernate_Anal()
				.createSQLQuery(topSQL);
		if(topList == null || topList.size() == 0){
			return -1;
		}
		Integer sid = topList.get(0);
		return sid;
	}
	
	/**
	 * 查询最底层结构
	 * @param tableCodes
	 * @return
	 * @author zhangzhanliang
	 */
	private List<SurveyDataModel> queryBottomItems(String tableCodes){
		List<SurveyDataModel> items = new ArrayList<SurveyDataModel>();
		String [] codes = tableCodes.split(",");
		String tableSQL = "select TBL_CODE,TBL_NAME from logical_table " +
				"where TBL_CODE in(";
		for(int i =0;i<codes.length;i++){
			tableSQL += "'" + codes[i] + "'";
			if(i != codes.length -1){
				tableSQL +=",";
			}
		}
		tableSQL += ")";
		List<Object[]> tableList = this.getHibernate_S9999()
				.createSQLQuery(tableSQL);
		
		for(Object[] tableMap : tableList){
			SurveyDataModel tableModel = new SurveyDataModel();
			tableModel.setLeaf(true);
			tableModel.setSurId(tableMap[0].toString());
			tableModel.setSurName(tableMap[1].toString());
			items.add(tableModel);
		}
		return items;
	}
	
	
	private List<SurveyDataModel>  queryMiddleItems(
			List<SurveyDataModel> topList,Integer parentId,List<SurveyDataModel> itemList){
		String parentSQL = "select sname,tableCodes,sid from structure_db " +
				"where spid = '"+parentId+"'";
		List<Object[]> parentList = this.getHibernate_Anal()
				.createSQLQuery(parentSQL);
		SurveyDataModel topModel = null;
		for(Object[] parMap : parentList){
			topModel = new SurveyDataModel();
			topModel.setSurName(parMap[0].toString());
			topModel.setLeaf(false);
			topModel.setSurId(parMap[2].toString());
			if(parMap[1] != null){
				String tableCodes = parMap[1].toString();
				List<SurveyDataModel> items = this.queryBottomItems(tableCodes);
				topModel.setItems(items);
			}else{
				// 当 tableCodes = null 的时候,表示该结构是目录
				Integer pId = Integer.valueOf(parMap[2].toString());
				List<SurveyDataModel> subItemList = new ArrayList<SurveyDataModel>();
				queryMiddleItems(topList,pId,subItemList);
				topModel.setItems(subItemList);
			}
			if(itemList == null ){
				topList.add(topModel);
			}else{
				itemList.add(topModel);
			}
		}
		return topList;
	}
	
	
	/**
	 * 查询 cen_object 表的CEN_GROUP信息
	 * @param years
	 * @return
	 * @throws Exception
	 */
	public List<SurveyDataModel> findByCen_Object(int years) throws Exception{
		List<SurveyDataModel> l = null;
		try {
			String queryWhere = "";
			LoadConfig conifg = LoadConfig.getInstance();
			
			Object value = conifg.getSurveyGroup().get(String.valueOf(years));
			
			
			StringBuilder q = new StringBuilder("SELECT CEN_GROUP  FROM  cen_object ");
			if(value != null){
				queryWhere = " where CEN_GROUP like '" + value.toString()  +"'";
				q.append(queryWhere);
			}else{
				return new ArrayList<SurveyDataModel>();
			}
			q.append(" group by CEN_GROUP" );
			List<String> countlist = this.getHibernate_S9999()
					.createSQLQuery(q.toString());

			l = new ArrayList<SurveyDataModel>();
			SurveyDataModel model = null;
			for (int i = 0, j = 1; i < countlist.size(); i++, j++) {

				if (countlist.get(i) != null) {
					model = new SurveyDataModel();
					model.setSurName(countlist.get(i));
					model.setLeaf(false);
					l.add(model);
					model = null;
				}
			}
		} catch (Exception e) {
			logger.error("查询调查数据，Cen_Object 目录出错", e);
			throw e;
		}
		return l;
	}
	/**
	 * 根据分组情况，返回分组
	 * @param l
	 * @return
	 */
	public String findCen_Object_NameByCen_Group(List<SurveyDataModel> l){
		StringBuilder sbui = new StringBuilder();
		for(int  i=0;i<l.size();i++){
			sbui.append("'").append(l.get(i).getSurName()).append("'");
			if(i != l.size() -1){
				sbui.append(",");
			}
		}
		String hql = "SELECT CEN_OBJECT_NAME,CEN_OBJECT_CODE,CEN_GROUP  FROM  cen_object where `CEN_GROUP` in (" + sbui.toString() + ")";
		
		
		StringBuilder whereBuil = new StringBuilder();
		List<Object[]> countlist = this.getHibernate_S9999()
									.createSQLQuery(hql);
		Object[] al = null;
		Iterator<SurveyDataModel> it = null;
		SurveyDataModel m = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			al = countlist.get(i);
			whereBuil.append("'").append(al[1].toString()).append("'");
			if(i != countlist.size() -1){
				whereBuil.append(",");
			}
			
			it = l.iterator();
			while(it.hasNext()){
				m = it.next();
				if(m.getSurName().equals(al[2].toString())){
					m.setSurId(m.getSurId() + "," + al[1].toString());
					if(m.getItems() == null){
						m.setItems(new ArrayList<SurveyDataModel>());
					}
					m.getItems().add(new SurveyDataModel(al[1].toString(),al[0].toString(),false));
					break;
				}
			}
		}
		return whereBuil.toString();
	}
	
	
	
	
	public void findByCen_object_table_rel(List<SurveyDataModel> l,String inStr) throws Exception {
		try {
			String q = "SELECT CEN_OBJECT_CODE,c.`TBL_CODE`, c.`TBL_NAME` FROM `cen_object_table_rel` b,`logical_table` c WHERE b.tbl_code=c.tbl_code and `CEN_OBJECT_CODE` in ("+
				inStr + ")";
			List<Object[]> countlist = this.getHibernate_S9999()
					.createSQLQuery(q);

			SurveyDataModel m = null;
			SurveyDataModel subm = null;
			Iterator<SurveyDataModel> it = null;
			Object[] al = null;
			for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
				al = countlist.get(i);
				it = l.iterator();
				subItem :while(it.hasNext()){
					m = it.next();
					logger.debug("model ids = " + m.getSurId() + " | name = " + al[0].toString() + " TRUE = " + (m.getSurId().indexOf(al[0].toString()) != -1));
					if(m.getSurId().indexOf(al[0].toString()) != -1){
						Iterator<SurveyDataModel> subIt = m.getItems().iterator();
						while(subIt.hasNext()){
							subm = subIt.next();
							if(subm.getSurId().equals(al[0].toString())){
								if(subm.getItems() == null){
									subm.setItems(new ArrayList<SurveyDataModel>());
								}
								subm.getItems().add(new SurveyDataModel(al[1].toString(),al[2].toString(),true));
								break subItem;
							}
							
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error("查询综合分析左侧列表的所有数据出异常", e);
			throw e;
		}
	}

	/**
	 * metric_name='3' 3含义表示：3，可显示可查询，可以在列表中显示，可以做为搜索条件。 2含义表示：2可显示，在列表中显示。
	 * 1含义表示：1 不可显示，
	 * 
	 * @param tbl_code
	 * @return
	 */
	public List<MapModel> findTabItemByTblCode(String tbl_code)
			throws Exception {
		List<MapModel> l = null;
		try {
			String q = "SELECT `ITEM_CODE`,`ITEM_NAME` FROM `tbl_item` WHERE `tbl_code` = ? AND metric_name='2' ORDER BY coordinate";
			List<Object[]> countlist = this.getHibernate_S9999()
					.createSQLQuery(q, tbl_code);

			l = new ArrayList<MapModel>();
			MapModel model = null;
			for (int i = 0, j = 1; i < countlist.size(); i++, j++) {

				if (countlist.get(i) != null) {
					model = new MapModel();
					Object[] al = countlist.get(i);
					model.setKey(al[0].toString());
					model.setValue(al[1].toString());
					l.add(model);
					model = null;
				}
			}

		} catch (Exception e) {
			logger.error("查询综合分析左侧列表的所有数据出异常", e);
			throw e;
		}
		return l;
	}

	public List<ItemTableVO> findItemTableBy(String tableName) {
		List<ItemTableVO> itemVoList = new ArrayList<ItemTableVO>();
		String itemSql = "SELECT `ITEM_CODE`,`ITEM_NAME`,`ITEM_TYPE`,`REL_CATE_CODE_EXPR` FROM `tbl_item` WHERE `tbl_code` = '"
				+ tableName + "' AND metric_name='2' ORDER BY coordinate";
		List<Object[]> itemList = getHibernate_S9999().createSQLQuery(itemSql);
		ItemTableVO temp = null;
		String expr = null;
		Map<String, List<CodeExprVO>> exprMap = null;
		for (int i = 0; i < itemList.size(); i++) {
			temp = new ItemTableVO();
			temp.setItemCode(String.valueOf(itemList.get(i)[0]));
			temp.setItemName(String.valueOf(itemList.get(i)[1]));
			temp.setItemType(String.valueOf(itemList.get(i)[2]));
			expr = String.valueOf(itemList.get(i)[3]);
			if (!StringUtil.isBlank(expr) && !"null".equals(expr)) {
				String[] sp = expr.split(",");
				if (!StringUtil.isBlank(sp[0])) {
					exprMap = new HashMap<String, List<CodeExprVO>>();
					List<CodeExprVO> arr = findCodeExpr(sp[0]);
					exprMap.put(temp.getItemCode(), arr);
					temp.setItemType("3");
					temp.setExprMap(exprMap);
				}
			}
			itemVoList.add(temp);
			expr = null;
		}
		return itemVoList;
	}

	private List<CodeExprVO> findCodeExpr(String tablName) {
		List<CodeExprVO> arr = new ArrayList<CodeExprVO>();
		try {
			String hql = "select * from " + tablName;
			CodeExprVO vo = null;
			List<Object[]> l = this.getHibernate_S9999().createSQLQuery(hql);
			for (int i = 0; i < l.size(); i++) {
				vo = new CodeExprVO();
				Object[] arro = (Object[]) l.get(i);
				if (arro != null && arro.length == 2) {
					vo.setCode(String.valueOf(arro[0]));
					vo.setName(String.valueOf(arro[1]));
					arr.add(vo);
				} else {
					logger.error(tablName + "   返回的数据有错误");
				}
			}
		} catch (Exception e) {
			logger.error("查询数据指标出错。", e);
		}
		return arr;
	}
	/**
	 * 查询表信息，及相关的表数据信息
	 * @param tableName 表名称
	 * @param voList 信息页数
	 * @param sv	表示查询条件
	 * @param curPage	当前页
	 * @param pageSize 一共多少页
	 * @return
	 */
	private Object[] findByTableItem(String tableName, List<ItemTableVO> voList,
			String sv, int curPage, int pageSize) {
		Object[] tableObject = new Object[2];
		if (voList == null || voList.size() == 0) {
			return null;
		}
		String countSql = "SELECT count(1) from `" + tableName + "_info`";
		if (!StringUtil.isBlank(sv)) {
			countSql += " where ";
			countSql += sv;
		}
		List r = getHibernate_S9999().createSQLQuery(countSql);

		long totalresult = 0;
		if (r != null && r.size() > 0) {
			totalresult = Long.valueOf(r.get(0).toString());
		}
		if (totalresult < 1) {
			totalresult = 0;
		}
		Page page = new Page(totalresult, pageSize);
		page.setPage(curPage);

		StringBuilder bu = new StringBuilder("SELECT ");
		for (int i = 0; i < voList.size(); i++) {
			bu.append("`").append(voList.get(i).getItemCode()).append("`");
			if (i != voList.size() - 1) {
				bu.append(",");
			}
		}
		bu.append(" from `" + tableName + "_info`");

		if (!StringUtil.isBlank(sv)) {
			bu.append(" where ");
			bu.append(sv);
		}
		if (logger.isInfoEnabled())
			logger.info(bu.toString());
		try{
			List<Object[]> tableList = getHibernate_S9999().sqlQuery(bu.toString(),
					page);
			tableObject[0] = tableList; // 表信息
			tableObject[1] = page; // 页数
		}catch(Exception e){
			logger.error(e.getMessage(),e);
		}
		return tableObject;
	}

	/**
	 * 根据传输的表编号，返回原始数据jrxml存储的路径
	 * 
	 * @param tableCode
	 * @return
	 */
	public String findJrxmlinfoByTableCode(String tableCode) {
		// Plate009/
		String node_name = tableCode;
		try {
			String hql = "select jrfilepath from jrxmlinfo where tableCode = '"
					+ node_name + "'";
			;
			List<String> l = this.getHibernate_Anal().createSQLQuery(hql);
			if (l != null && l.size() > 0) {
				return l.get(0);
			}
		} catch (Exception e) {
			logger.error("根据传输的表编号，返回原始数据jrxml存储的路径。", e);
		}
		return null;
	}

	public static void main(String[] args) {
		String jsperPath = "01调查区县数据信息";
		StringBuffer sbuf = new StringBuffer(jsperPath);
		System.out.println(sbuf.insert(2," "));
		
	}

	public String viewJrxml(String jsperPath, HttpServletRequest request,int rowNum) {
		try {
			String fileName = jsperPath.substring(
					jsperPath.lastIndexOf("\\") + 1, jsperPath.length());
			String parStr = jsperPath.substring(jsperPath.indexOf("\\") + 1,
					jsperPath.lastIndexOf("\\"));
			String serverParStr = parStr;
			String clientParStr = parStr;
			String clientFileName = fileName;

			String jrxmlUrl = LoadConfig.getInstance().getJrxmlUrl();
			
//			fileName = new String(fileName.getBytes("ISO8859-1"), "utf-8");
			if (!StringUtil.isBlank(parStr)) {
//				parStr = new String(parStr.getBytes("ISO8859-1"), "utf-8");
				serverParStr = parStr;
				clientParStr = parStr;
				serverParStr = serverParStr.replaceAll(" ", "");
				serverParStr = convertChinese(serverParStr);
			}

			// 服务器上生成 html 存放目录
			String htmlFilePath = createServerHtmlDirectory(request,
					serverParStr);

			// 服务器上生成 jasper 存放目录
			String jasperFilePath = createServerJasperDirectory(request,
					serverParStr);

			// 服务器上生成 jrprint 存放目录
			String jrprintFilePath = createServerJrprintDirectory(request,
					serverParStr);

			StringBuilder sfile = new StringBuilder();
			sfile.append(jrxmlUrl);
			logger.info("默认是： jrxmlUrl = " + jrxmlUrl);
			if (!jrxmlUrl.endsWith(File.separator)) {
				sfile.append(File.separator);
			}
			logger.info("判断是否存在  separator  jrxmlUrl = " + jrxmlUrl);
			File tF = new File(jrxmlUrl);
			if(tF.exists()){
				logger.info("jrxmlUrl = " + jrxmlUrl + " | 目录存在");
			}else{
				logger.info("jrxmlUrl = " + jrxmlUrl + " | 目录不存在");
			}
			logger.info("数据库存在的 parStr = " + parStr);
			if (!StringUtil.isBlank(parStr)) {
				File parFile = null;
				parFile = new File(sfile.toString(),parStr);
				logger.info("parFile = " + parFile.getAbsolutePath());
				if(parFile.exists()){
					logger.info("parFile 存在");
					sfile.append(parStr).append(File.separator);
				}else{
					logger.info("parFile 不存在");
					StringBuffer sbuf = new StringBuffer(parStr).insert(2, " ");
					parFile = new File(sfile.toString(),sbuf.toString());
					logger.info("路径中空格去掉后 ：　sbuf　＝　" + sbuf);
					if(parFile.exists()){
						sfile.append(sbuf).append(File.separator);
					}else{
						throw new Exception("没有找到文件目录: " + sfile);
					}
				}
			}
			
			File jrsmlFile = null;
			if(!StringUtil.isBlank(fileName)){
				jrsmlFile = new File(sfile.toString(),fileName);
				if(!jrsmlFile.exists()){
					StringBuffer sbuf = new StringBuffer(fileName).insert(4, " ");
					jrsmlFile = new File(sfile.toString(),sbuf.toString());
					if(!jrsmlFile.exists()){
						throw new Exception("没有找到文件目录;" + jrsmlFile);
					}
				}
			}
			
			if (logger.isDebugEnabled())
				logger.debug("file path = " + jrsmlFile);
			
			return viewJrxmlPage(jrsmlFile, htmlFilePath,
					jasperFilePath, jrprintFilePath, clientFileName,
					clientParStr, request,rowNum);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 带中文的全部转换成提示首字母
	 * 
	 * @param name
	 * @return
	 */
	private String convertChinese(String name) {
		if (StringUtil.isChinese(name)) {
			StringBuilder s = new StringBuilder();
			char[] ch = name.toCharArray();
			for (int i = 0; i < ch.length; i++) {
				char c = ch[i];
				if (StringUtil.isChinese(c)) {
					s.append(StringUtil.convertZH(String.valueOf(c)));
				} else {
					s.append(String.valueOf(c));
				}
			}
			return s.toString();
		}
		return name;
	}

	/**
	 * 创建服务器上存放 html 的目录
	 * 
	 */
	private String createServerHtmlDirectory(HttpServletRequest request,
			String serverParStr) {

		String path = request.getRealPath(Global.staticRportHtml);
		return this.createServerDirectory(request, serverParStr, path);
	}

	/**
	 * 创建服务器上存放 jrprint 文件的目录
	 * 
	 * @param request
	 * @param serverParStr
	 * @return
	 */
	private String createServerJrprintDirectory(HttpServletRequest request,
			String serverParStr) {
		String jasperPath = request.getRealPath(Global.staticRportJrprint);
		return this.createServerDirectory(request, serverParStr, jasperPath);
	}

	/**
	 * 创建服务器上存放 jasper的目录
	 * 
	 */
	private String createServerJasperDirectory(HttpServletRequest request,
			String serverParStr) {
		// 服务器上生成 jasper 存放目录
		String jasperPath = request.getRealPath(Global.staticRportJasper);
		return this.createServerDirectory(request, serverParStr, jasperPath);
	}

	/**
	 * 创建服务器上目录
	 * 
	 * @param request
	 * @param serverParStr
	 * @param path
	 */
	private String createServerDirectory(HttpServletRequest request,
			String serverParStr, String path) {
		if (logger.isDebugEnabled())
			logger.debug("html path = " + path);
		StringBuilder htmlFilePath = new StringBuilder(path);
		if (!StringUtil.isBlank(serverParStr)) {
			if (!path.endsWith(File.separator)) {
				htmlFilePath.append(File.separator);
			}
			htmlFilePath.append(serverParStr);
			File htmlFile = new File(htmlFilePath.toString());
			if (!htmlFile.exists()) {
				htmlFile.mkdirs();
			}
		}
		return htmlFilePath.toString();
	}

	/**
	 * /** 分页显示 jrxml文件内容
	 * 
	 * @param jrxmlFile
	 *            jrxml 文件路
	 * @param htmlPath
	 *            HTML 文件存放路径
	 * @param jasperPath
	 *            jasper 文件存放路径
	 * @param jrprintFilePath
	 *            jrprint 文件存放路径
	 * @param fileName
	 * @param parStr
	 * @return
	 * @throws BusinessException
	 */
	public String viewJrxmlPage(File jrxmlFile, String htmlPath,
			String jasperPath, String jrprintPath, String fileName,
			String parStr, HttpServletRequest request,int rowNum) {
		StringBuffer sbuffer = new StringBuffer();
		try {
			String converJrxmlName = convertJrxmlFileName(jrxmlFile.getName());

			JasperReport jr = jasperServerTaskOrCompile(jrxmlFile,
					converJrxmlName, jasperPath);

			JasperPrint jp = jrprintServerTaskOrCompile(jr, converJrxmlName,
					jrprintPath);

			// HttpServletRequest request = this.getRequest();
			JRXhtmlExporter exporter = new JRXhtmlExporter();
			
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			// exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
			// getResponse().getWriter());
			exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER,
					sbuffer);
			// String imageDIR = this.getContext().getFile().getAbsolutePath();
			// getWebApplicationContext(request).getResource(jasperFilePath).getFile();
			// request.getres
			String imageDIR = request.getRealPath("/images/jasperreports/");
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,
					imageDIR);

			String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();

			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, basePath
					+ "/images/jasperreports/");
			// exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
			// this.getBasePath() + "/servlets/image?image=");

			exporter.setParameter(
					JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
					Boolean.TRUE);
			exporter.setParameter(
					JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,
					Boolean.TRUE);
			exporter.setParameter(JRExporterParameter.PAGE_INDEX, rowNum);
			exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
			// exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
			// "");
			// exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
			try {
				exporter.exportReport();
			} catch (Exception e) {
				logger.error("jrxml 生成 xhtml 时出错！", e);
			}

			// 解决ie浏览器图片缓存问题
			String rastr = "plt" + fileName.substring(1, 4) + ".png?t="
					+ Math.random();// fileName.substring(1, 3);
			Pattern pattern = Pattern
					.compile("(/images/jasperreports/)(\\w+)(\")");
			Matcher matcher = pattern.matcher(sbuffer.toString());
			String g = "";
			int offset = 0;
			while (matcher.find()) {
				offset = matcher.end();
				offset = matcher.start();
				g = matcher.group(0);
			}
			sbuffer.insert(offset + 22, rastr);
			
		} catch (Exception e) {
			logger.error("分布显示错误", e);
		}
		return sbuffer.toString();
	}

	private String convertJrxmlFileName(String fileName) {
		String name = fileName;
		if (fileName.endsWith(Global.jrxmlSuffix)) {
			name = fileName.substring(0, fileName
					.lastIndexOf(Global.jrxmlSuffix));
			name = name.replaceAll(" ", "");
			name = convertChinese(name);
		} else {
			return null;
		}
		return name;
	}

	/**
	 * 如果 jrxml 在服务器上已经存在 jasper 文件则直接获取 如果 不存 则通过 jrxml 文件编译
	 * 
	 * @param jrxmlFile
	 *            jrxml 文件
	 * @param converJrxmlName
	 *            转换为中文字母的文件名
	 * @param jasperPath
	 *            服务器上 jasperpath 存放的目录
	 * @return
	 * @throws JRException
	 */
	private JasperReport jasperServerTaskOrCompile(File jrxmlFile,
			String converJrxmlName, String jasperPath) throws JRException {
		JasperReport jr = null;

		String jasperFilePath = jasperPath + File.separator + converJrxmlName
				+ ".jasper";
		File jasperFile = new File(jasperFilePath);
		if (jasperFile.exists()) {
			jr = (JasperReport) JRLoader.loadObject(jasperFilePath);
		} else {
			// 1.开发人员开发报表设计文件，也就是定义一个.jrxml文件（就是最开始的那个XML
			// 文件）。
//			jrxmlFile = new File("c:\\japsersoft\\01 调查区县数据信息\\P001调查区县基本情况.jrxml");
			JasperDesign jd = JRXmlLoader.load(jrxmlFile);// jrxml文件
			// 2. 使用JasperReports提供API中的JasperCompileManager类编译.jrxml文
			// 件，编译后生成一个.jasper文件。
			jr = JasperCompileManager.compileReport(jd);

			// 将 jd 即读取到的 jrxml 文件 编译成 jasper文件存放到硬盘中
			SimpleJasperReportsContext jasperReportsContext = new SimpleJasperReportsContext();
			JasperCompileManager.getInstance(jasperReportsContext)
					.compileToFile(jd, jasperFilePath);
		}
		return jr;
	}

	/**
	 * 如果 jrprint 文件服务器存在 则直接获取；如果不存在，则通过jasper对象进行编译
	 * 
	 * @param jr
	 * @param converJrxmlName
	 * @param jrprintPath
	 * @return
	 * @throws SQLException
	 * @throws JRException
	 */
	private JasperPrint jrprintServerTaskOrCompile(JasperReport jr,
			String converJrxmlName, String jrprintPath) throws JRException,
			SQLException {
		JasperPrint jp = null;
		String path = jrprintPath + File.separator + converJrxmlName
				+ ".jrprint";
		File file = new File(path);
		if (file.exists()) {
			jp = (JasperPrint) JRLoader.loadObject(path);
		} else {
			// 3.使用JasperReports提供API中的JasperFillManager类填充编译后的.jasper
			// 文件，填充后生成一个.jrprint文件。
			jp = getJasperPrint(jr);
			// 保存 jrprint 文件
			JRSaver.saveObject(jp, file);
		}
		return jp;
	}

	public JasperPrint getJasperPrint(JasperReport jr) throws JRException,
			SQLException {
		return getJasperPrint(jr, null);
	}

	public JasperPrint getJasperPrint(JasperReport jr,
			Map<String, Object> parameters) throws JRException, SQLException {
		Connection conn = getConnection();
		try {
			return JasperFillManager.fillReport(jr, parameters, conn);
		} finally {
			releaseConnection(conn);
		}
	}

	public Connection getConnection() throws SQLException {
		return SessionFactoryUtils.getDataSource(
				this.getHibernate_S9999().getSessionFactory()).getConnection();
	}

	private void releaseConnection(Connection conn) throws SQLException {
		if (conn != null) {
			if (!conn.isClosed()) {
				conn.close();
			}
			conn = null;
		}
	}
	
	
	private int getRownumByMysql(String tableNmae,String code){
		try {
			String q = "select count(*) as rownum from "+tableNmae+" as a,"+tableNmae+" as b where a.code>=b.code and a.code = "+code+" group by a.code";
			List list = this.getHibernate_S9999()
					.createSQLQuery(q);
			if(list != null && list.size() > 0){
				return Integer.valueOf(list.get(0).toString());
			}

		} catch (Exception e) {
			logger.error("getRownumByMysql数据异常", e);
		}
		return 0;
	}
	
	
}
