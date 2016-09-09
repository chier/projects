package com.cmcc.framework.controller.action.warning;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Float;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.WarningVO;
import com.cmcc.framework.controller.formbean.ZtreeVO;

/**
 * 预警分析 action
 * 
 * @author zhangzhanliang
 * 
 */
public class WarningAction extends WebActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5753617818157507057L;

	private Logger logger = Logger.getLogger(WarningAction.class);

	/**
	 * 默认路径
	 */
	public String execute() {

		List<String> statUnitList = this.warningManager.findAllS999StatUnit();
		getRequest().setAttribute("statUnitList", statUnitList);
		return SUCCESS;
	}
	
	
	/**
	 * 设置默认的值
	 */
	public String settingDefault() {
		return "settingDefault";
	}
	
	/**
	 * 保存默认设置的值
	 * @return
	 */
	public String saveSettingDefault(){
		
		PrintWriter writer = null;
		try{
			String hidFirstMin = getRequest().getParameter("hidFirstMin");
			String hidFirstMax = getRequest().getParameter("hidFirstMax");
			String hidId = getRequest().getParameter("hidId");
			
			int min = 0;
			int max = 0;
			int id = 0;
			if(!StringUtils.isBlank(hidFirstMin) && StringUtils.isNumeric(hidFirstMin)){
				min = Integer.valueOf(hidFirstMin);
			}
			
			if(!StringUtils.isBlank(hidFirstMax) && StringUtils.isNumeric(hidFirstMax)){
				max = Integer.valueOf(hidFirstMax);
			}
			
			if(!StringUtils.isBlank(hidId) && StringUtils.isNumeric(hidId)){
				id = Integer.valueOf(hidId);
			}
			this.warningManager.saveSettingDefault(id, min, max);
			writer = getResponse().getWriter();
			writer.print("0");
		}catch(Exception e){
			if(writer != null){
				writer.close();
				writer = null;
			}
		}
		return null;
	}

	/**
	 * 返回树类别code信息
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String viewTreeTypeCodeInfo() throws BusinessException {
		Writer writer = null;
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			// String srid = getRequest().getParameter("rid");
			writer = this.getResponse().getWriter();
			List<ZtreeVO> l = null;
			// if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
			// logger.error("rid is null or rid is not numeric | rid = "
			// + srid);
			// l = new ArrayList<ZtreeVO>();
			// } else {
			l = this.warningManager.findTreeTypeAndCodeByRid(0);
			map.put("trees", l);
			// }

			// String btns = permissionManager.findPcenterByRid(Integer
			// .valueOf(srid), PermissionMark.CustomBtn_permission
			// .getValue());
			// if (!StringUtil.isBlank(btns)) {
			// String[] objs = btns.split(",");
			// map.put("btns", objs);
			// }

			JSONObject jsonObject = JSONObject.fromObject(map);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();
			logger.info("json = " + json);
			// json = new String(json.getBytes(),"gbk");
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 查询报表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryReportTable() {
		String type = getRequest().getParameter("type");
		getRequest().setAttribute("type", type);

		WarningVO vo = setWarningVO();

		List l = this.warningManager.findListByVOSQL(vo);
		List<String> headList = (List<String>) l.get(0);
		List<Object[]> dataList = (List<Object[]>) l.get(1);

		if (dataList == null || dataList.size() == 0) {
			PrintWriter out = null;
			try {
				out = this.getResponse().getWriter();
				out.write("<script>parent.clearData();</script>");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
					out = null;
				}
			}
			return null;
		}

		getRequest().setAttribute("itemCount", headList.size());
		getRequest().setAttribute("headList", headList);
		getRequest().setAttribute("dataList", dataList);

		String json = getJsonData(dataList, headList);
		String img_config = getGuidesDefaultChart(headList, vo);

		getRequest().setAttribute("charttype", img_config);
		getRequest().setAttribute("jsondata", json);

		String jsonNegativeData = getJsonNegativeData(dataList, headList, vo);
		String negativeData = getNegativeDefaultChart(headList, vo);
		getRequest().setAttribute("jsonNegativeData", jsonNegativeData);
		getRequest().setAttribute("negativeData", negativeData);
		return "reportTable";
	}

	/**
	 * 显示不适合上限与下限的信息
	 * 
	 * @param vo
	 * @param itemList
	 * @param wvo
	 * @return
	 */
	public String getJsonNegativeData(List<Object[]> vo, List<String> itemList,
			WarningVO wvo) {
		StringBuffer dataxml = new StringBuffer();
		dataxml.append("[");
		String quent = "";
		try {
			for (int i = 0; i < vo.size(); i++) {
				dataxml.append("{");
				int count = 0;
				for (int j = 0; j < itemList.size(); j++) {

					if (j == 0)
						quent = "\"";
					else
						quent = "";
					if (itemList.get(j).indexOf("上限") == -1
							&& itemList.get(j).indexOf("下限") == -1) {
						count++;
						if (count == 1) {
							dataxml.append("\"" + itemList.get(j) + "\":"
									+ quent + vo.get(i)[j] + quent + ",");
						} else {
							dataxml.append("\"" + itemList.get(j) + "\":"
									+ quent
									+ getMaxMin(vo.get(i)[j], wvo, count)
									+ quent + ",");
						}

					}
				}
				dataxml.deleteCharAt(dataxml.length() - 1);
				dataxml.append("},");
			}
			dataxml.deleteCharAt(dataxml.length() - 1);
			dataxml.append("]");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return dataxml.toString();
	}

	/**
	 * 返回比较出现的上限与下限值
	 * 
	 * @return
	 */
	private float getMaxMin(Object value, WarningVO wvo, int count) {
		Float fv = Float.valueOf(value.toString());

		if (count == 2) {
			if (!StringUtil.isBlank(wvo.getHidFirstMin())) { // 下限
				Float firstMin = Float.valueOf(wvo.getHidFirstMin());
				if (fv < firstMin) {
					return fv - firstMin;
				}
			}

			if (!StringUtil.isBlank(wvo.getHidFirstMax())) { // 上限
				Float firstMax = Float.valueOf(wvo.getHidFirstMin());
				if (fv > firstMax) {
					return fv - firstMax;
				}
			}
		}

		if (count == 3) {
			if (!StringUtil.isBlank(wvo.getHidSecondMin())) { // 下限
				Float firstMin = Float.valueOf(wvo.getHidSecondMin());
				if (fv < firstMin) {
					return fv - firstMin;
				}
			}

			if (!StringUtil.isBlank(wvo.getHidSecondMax())) { // 上限
				Float firstMax = Float.valueOf(wvo.getHidSecondMax());
				if (fv > firstMax) {
					return fv - firstMax;
				}
			}

		}
		return 0l;
	}

	/**
	 * 返回多维图 default charts 配置信息 配置信息 参考 GuidesDefaultChart.xml 文件
	 * 
	 * @return
	 */
	public String getNegativeDefaultChart(List<String> itemList, WarningVO vo) {
		StringBuilder str = new StringBuilder();
		// StringBuilder guidesStr = new StringBuilder(); //警戒线
		StringBuilder dataFieldStr = new StringBuilder(); // 数据字段
		StringBuilder chartSeries = new StringBuilder(); // 图表系统
		// 警戒线
		// guidesStr.append(getGuidesChartSetting(vo));
		// dataField 数据字段
		dataFieldStr.append("<dataField>");
		dataFieldStr.append("<field>" + vo.getHidArea() + "</field>");
		if (!StringUtil.isBlank(vo.getHidFirstName())) {
			dataFieldStr.append("<field>" + vo.getHidFirstName() + "</field>");
		}
		if (!StringUtil.isBlank(vo.getHidSecondName())) {
			dataFieldStr.append("<field>" + vo.getHidSecondName() + "</field>");
		}

		dataFieldStr.append("</dataField>");
		// chartSeries 图表系列
		chartSeries.append("<chartSeries>");
		for (int i = 0; i < itemList.size(); i++) {
			// fieldstr += "<field>" + itemList.get(i) + "</field>";
			if (i != 0) {
				if (itemList.get(i).indexOf("上限") == -1
						&& itemList.get(i).indexOf("下限") == -1) {
					if (i == 1) {
						chartSeries
								.append("<series vAxisID='1' ><seriesType>column</seriesType>");
						chartSeries
								.append("<seriesTitle>"
										+ itemList.get(i)
										+ "</seriesTitle><seriesColor>0xFF6600</seriesColor>");
						chartSeries.append("<valueField>" + itemList.get(i)
								+ "</valueField>");

						chartSeries.append("</series>");
					} else {
						chartSeries
								.append("<series vAxisID='2' ><seriesType>line</seriesType>");
						chartSeries
								.append("<seriesTitle>"
										+ itemList.get(i)
										+ "</seriesTitle><seriesColor>0xFCD202</seriesColor>");
						chartSeries.append("<valueField>" + itemList.get(i)
								+ "</valueField>");
						chartSeries.append("</series>");
					}
				}
			}
		}
		chartSeries.append("</chartSeries>");

		// 其它信息
		str.append("<chart>");
		// str.append(guidesStr);
		str.append(chartSeries);
		str.append("<chartTitle>图表吿</chartTitle><xAxisField>" + itemList.get(0)
				+ "</xAxisField>");
		str.append("<chartD>2D</chartD>");
		str
				.append("<chartView showTitle='true' showLegend='true'></chartView>");
		str
				.append("<chartControl enableCursor='true' enableScrollbar='true'></chartControl>");

		str.append("</chart>");

		// str.append("<chart><guides defaultColor='0xFF9900'
		// defaultBullet='warning_16.png'><guide value='700'
		// lineColor='0x00ff00' valueColor='0x663399' bullet='warning_16.png'
		// label='警戒线一'></guide><guide value='400' lineColor='0x00ff00'
		// valueColor='0x663300' bullet=''
		// label='警戒线二'></guide></guides><dataField><field>省</field><field>行政区划代码（合计值）</field><field>行政区值</field></dataField><chartSeries><series><seriesType>column</seriesType><seriesTitle>行政区划代码（合计值）</seriesTitle><valueField>行政区划代码（合计值）</valueField></series><series><seriesType>line</seriesType><seriesTitle>行政区值</seriesTitle><valueField>行政区值</valueField></series></chartSeries><chartTitle>图表名</chartTitle><xAxisField>省</xAxisField><chartD>2D</chartD><chartView
		// showTitle='true' showLegend='true'></chartView><chartControl
		// enableCursor='true' enableScrollbar='true'></chartControl></chart>");

		return str.toString();
	}

	/**
	 * 根据数据生成 json
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String getJsonData(List<Object[]> vo, List<String> itemList) {

		StringBuffer dataxml = new StringBuffer();
		dataxml.append("[");
		String quent = "";
		try {
			for (int i = 0; i < vo.size(); i++) {
				dataxml.append("{");
				for (int j = 0; j < itemList.size(); j++) {
					if (j == 0)
						quent = "\"";
					else
						quent = "";
					dataxml.append("\"" + itemList.get(j) + "\":" + quent
							+ vo.get(i)[j] + quent + ",");
				}
				dataxml.deleteCharAt(dataxml.length() - 1);
				dataxml.append("},");
			}
			dataxml.deleteCharAt(dataxml.length() - 1);
			dataxml.append("]");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return dataxml.toString();
	}

	public String getJsonNegativeChartData(List<Object[]> vo,
			List<String> itemList) {

		StringBuffer dataxml = new StringBuffer();
		dataxml.append("[");
		String quent = "";
		try {
			for (int i = 0; i < vo.size(); i++) {
				dataxml.append("{");
				for (int j = 0; j < itemList.size(); j++) {
					if (j == 0)
						quent = "\"";
					else
						quent = "";
					dataxml.append("\"" + itemList.get(j) + "\":" + quent
							+ vo.get(i)[j] + quent + ",");
				}
				dataxml.deleteCharAt(dataxml.length() - 1);
				dataxml.append("},");
			}
			dataxml.deleteCharAt(dataxml.length() - 1);
			dataxml.append("]");

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return dataxml.toString();
	}

	public WarningVO setWarningVO() {
		WarningVO vo = new WarningVO();
		HttpServletRequest request = this.getRequest();
		String hidFirstName = request.getParameter("hidFirstName");
		String hidFirstCode = request.getParameter("hidFirstCode");
		String hidFirstTable = request.getParameter("hidFirstTable");
		// 下限
		String hidFirstMin = request.getParameter("hidFirstMin");
		// 上限
		String hidFirstMax = request.getParameter("hidFirstMax");

		String hidSecondCode = request.getParameter("hidSecondCode");
		String hidSecondName = request.getParameter("hidSecondName");
		String hidSecondTable = request.getParameter("hidSecondTable");

		// 下限
		String hidSecondMin = request.getParameter("hidSecondMin");
		// 上限
		String hidSecondMax = request.getParameter("hidSecondMax");

		String hidArea = request.getParameter("hidArea");
		String hidSaveWar = request.getParameter("hidSaveWar");
		String txtName = request.getParameter("txtName");

		vo.setHidArea(hidArea);
		vo.setHidFirstCode(hidFirstCode);
		vo.setHidFirstMax(hidFirstMax);
		vo.setHidFirstMin(hidFirstMin);
		vo.setHidFirstName(hidFirstName);
		vo.setHidFirstTable(hidFirstTable);
		vo.setHidSecondCode(hidSecondCode);
		vo.setHidSecondMax(hidSecondMax);
		vo.setHidSecondMin(hidSecondMin);
		vo.setHidSecondName(hidSecondName);
		vo.setHidSecondTable(hidSecondTable);
		vo.setHidSaveWar(hidSaveWar);
		vo.setTxtName(txtName);
		return vo;
	}

	/**
	 * 显示保存目录树
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String viewDirectoryTree() throws BusinessException {
		Writer writer = null;
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			// String srid = getRequest().getParameter("rid");
			writer = this.getResponse().getWriter();
			List<ZtreeVO> l = null;

			l = this.warningManager.findDisctoryTreeByTypeAndRid(0, 0);
			map.put("trees", l);

			JSONObject jsonObject = JSONObject.fromObject(map);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();
			logger.info("json = " + json);
			// json = new String(json.getBytes(),"gbk");
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 保存信息
	 * 
	 * @return
	 */
	@GenericLogger(operateMark = OperateMark.WARNING_ADD, operateDescription = "预警分析-保存预警", isExtend = true)
	public String saveWarning() {
		WarningVO vo = setWarningVO();
		this.warningManager.saveOrUpdateWarning(vo);
		return null;
	}

	/**
	 * 根据数据生成 默认的图标显示属怿
	 */
	public String getDefaultChart(List<String> itemList) {
		/*
		 * <chart> <dataField> <field>xlabel</field> <field>value1</field>
		 * <field>value2</field> <!-- ... --> </dataField> <chartSeries>
		 * <series> <seriesType>column</seriesType> <seriesTitle>系列1</seriesTitle>
		 * <valueField>value1</valueField> </series> <!-- ... -->
		 * </chartSeries> <chartTitle>图表吿</chartTitle> <xAxisField>xlabel</xAxisField>
		 * <chartD>2D</chartD> <chartView showTitle="true" showLegend="true"></chartView>
		 * <chartControl enableCursor="true" enableScrollbar="true"></chartControl>
		 * </chart>
		 */

		String setStr = "";
		String seriesStr = "";
		String fieldstr = "";

		setStr = "<chart><dataField>";
		for (int i = 0; i < itemList.size(); i++) {
			fieldstr += "<field>" + itemList.get(i) + "</field>";
			if (i != 0) {
				if (itemList.get(i).indexOf("上限") == -1
						&& itemList.get(i).indexOf("下限") == -1) {
					if (i == 1) {
						seriesStr += "<series><seriesType>column</seriesType>";
						seriesStr += "<seriesTitle>" + itemList.get(i)
								+ "</seriesTitle>";
						seriesStr += "<valueField>" + itemList.get(i)
								+ "</valueField>";
						seriesStr += "</series>";
					} else {
						seriesStr += "<series><seriesType>line</seriesType>";
						seriesStr += "<seriesTitle>" + itemList.get(i)
								+ "</seriesTitle>";
						seriesStr += "<valueField>" + itemList.get(i)
								+ "</valueField>";
						seriesStr += "</series>";
					}
				}
			}
		}
		setStr += fieldstr;
		setStr += "</dataField>";
		setStr += "<chartSeries>" + seriesStr + "</chartSeries>";
		setStr += "<chartTitle>图表吿</chartTitle><xAxisField>" + itemList.get(0)
				+ "</xAxisField><chartD>2D</chartD>";
		setStr += "<chartView showTitle='true' showLegend='true'></chartView>";
		setStr += "<chartControl enableCursor='true' enableScrollbar='true'></chartControl>";
		setStr += "</chart>";

		return setStr;
	}

	/**
	 * 判断是否有警戒线
	 * 
	 * @param vo
	 * @return
	 */
	private boolean isGuides(WarningVO vo) {
		if (!StringUtil.isBlank(vo.getHidFirstMax()) // 第一个字段上限
				|| !StringUtil.isBlank(vo.getHidFirstMin()) // 第一个字段下限
				|| !StringUtil.isBlank(vo.getHidSecondMax()) // 第二个字段上限
				|| !StringUtil.isBlank(vo.getHidSecondMin()) // 第二个字段下限
		) {
			return true;
		}
		return false;
	}

	/**
	 * 返回警戒线 配置信息
	 * 
	 * @param vo
	 * @return
	 */
	private String getGuidesChartSetting(WarningVO vo) {
		if (!isGuides(vo)) {
			return "";
		}
		StringBuilder guidesStr = new StringBuilder(); // 警戒线

		if (isGuides(vo)) {
			guidesStr.append("<guides>");
		}

		if (!StringUtil.isBlank(vo.getHidFirstMax())) { // 第一字段上限
			guidesStr
					.append("<guide vAxisID='1' value='"
							+ vo.getHidFirstMax()
							+ "' lineColor='0x00ff00' defaultColor='0xFF9900'  valueColor='0x663399'	bullet='"
							+ getBasePath() + "/images/warning_16.png' label='"
							+ vo.getHidFirstName() + "上限'></guide>");
		}
		if (!StringUtil.isBlank(vo.getHidFirstMin())) {
			guidesStr
					.append("<guide vAxisID='1' value='"
							+ vo.getHidFirstMin()
							+ "' lineColor='0x00ff00' defaultColor='0x663300'  valueColor='0x663399'	bullet='"
							+ getBasePath() + "/images/warning_16.png' label='"
							+ vo.getHidFirstName() + "下限'></guide>");
		}

		if (!StringUtil.isBlank(vo.getHidSecondMax())) {
			guidesStr
					.append("<guide vAxisID='2'  value='"
							+ vo.getHidSecondMax()
							+ "' lineColor='0x00ff00' defaultColor='0xFF9900' valueColor='0x663399'	bullet='"
							+ getBasePath() + "/images/warning_16.png' label='"
							+ vo.getHidSecondName() + "上限'></guide>");
		}
		if (!StringUtil.isBlank(vo.getHidSecondMin())) {
			guidesStr
					.append("<guide vAxisID='2'  value='"
							+ vo.getHidSecondMin()
							+ "' lineColor='0x00ff00' defaultColor='0x663300'  valueColor='0x663300'	bullet='"
							+ getBasePath() + "/images/warning_16.png' label='"
							+ vo.getHidSecondName() + "下限'></guide>");
		}

		if (isGuides(vo)) {
			guidesStr.append("</guides>");
		}
		return guidesStr.toString();
	}

	/**
	 * 返回多维图 default charts 配置信息 配置信息 参考 GuidesDefaultChart.xml 文件
	 * 
	 * @return
	 */
	public String getGuidesDefaultChart(List<String> itemList, WarningVO vo) {
		StringBuilder str = new StringBuilder();
		StringBuilder guidesStr = new StringBuilder(); // 警戒线
		StringBuilder dataFieldStr = new StringBuilder(); // 数据字段
		StringBuilder chartSeries = new StringBuilder(); // 图表系统
		// 警戒线
		guidesStr.append(getGuidesChartSetting(vo));
		// dataField 数据字段
		dataFieldStr.append("<dataField>");
		dataFieldStr.append("<field>" + vo.getHidArea() + "</field>");
		if (!StringUtil.isBlank(vo.getHidFirstName())) {
			dataFieldStr.append("<field>" + vo.getHidFirstName() + "</field>");
		}
		if (!StringUtil.isBlank(vo.getHidSecondName())) {
			dataFieldStr.append("<field>" + vo.getHidSecondName() + "</field>");
		}

		dataFieldStr.append("</dataField>");
		// chartSeries 图表系列
		chartSeries.append("<chartSeries>");
		for (int i = 0; i < itemList.size(); i++) {
			// fieldstr += "<field>" + itemList.get(i) + "</field>";
			if (i != 0) {
				if (itemList.get(i).indexOf("上限") == -1
						&& itemList.get(i).indexOf("下限") == -1) {
					if (i == 1) {
						chartSeries
								.append("<series vAxisID='1' ><seriesType>column</seriesType>");
						chartSeries
								.append("<seriesTitle>"
										+ itemList.get(i)
										+ "</seriesTitle><seriesColor>0xFF6600</seriesColor>");
						chartSeries.append("<valueField>" + itemList.get(i)
								+ "</valueField>");

						chartSeries.append("</series>");
					} else {
						chartSeries
								.append("<series vAxisID='2' ><seriesType>line</seriesType>");
						chartSeries
								.append("<seriesTitle>"
										+ itemList.get(i)
										+ "</seriesTitle><seriesColor>0xFCD202</seriesColor>");
						chartSeries.append("<valueField>" + itemList.get(i)
								+ "</valueField>");
						chartSeries.append("</series>");
					}
				}
			}
		}
		chartSeries.append("</chartSeries>");

		// 其它信息
		str.append("<chart>");
		str.append(guidesStr);
		str.append(chartSeries);
		str.append("<chartTitle>图表吿</chartTitle><xAxisField>" + itemList.get(0)
				+ "</xAxisField>");
		str.append("<chartD>2D</chartD>");
		str
				.append("<chartView showTitle='true' showLegend='true'></chartView>");
		str
				.append("<chartControl enableCursor='true' enableScrollbar='true'></chartControl>");

		str.append("</chart>");

		// str.append("<chart><guides defaultColor='0xFF9900'
		// defaultBullet='warning_16.png'><guide value='700'
		// lineColor='0x00ff00' valueColor='0x663399' bullet='warning_16.png'
		// label='警戒线一'></guide><guide value='400' lineColor='0x00ff00'
		// valueColor='0x663300' bullet=''
		// label='警戒线二'></guide></guides><dataField><field>省</field><field>行政区划代码（合计值）</field><field>行政区值</field></dataField><chartSeries><series><seriesType>column</seriesType><seriesTitle>行政区划代码（合计值）</seriesTitle><valueField>行政区划代码（合计值）</valueField></series><series><seriesType>line</seriesType><seriesTitle>行政区值</seriesTitle><valueField>行政区值</valueField></series></chartSeries><chartTitle>图表名</chartTitle><xAxisField>省</xAxisField><chartD>2D</chartD><chartView
		// showTitle='true' showLegend='true'></chartView><chartControl
		// enableCursor='true' enableScrollbar='true'></chartControl></chart>");

		return str.toString();
	}

	/**
	 * 浏览所有的树信息
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String viewAllTree() throws BusinessException {
		Writer writer = null;
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			writer = this.getResponse().getWriter();
			List<ZtreeVO> l = null;
			l = this.warningManager.findDisctoryTreeByTypeAndRid(1, 0);
			map.put("trees", l);

			JSONObject jsonObject = JSONObject.fromObject(map);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();
			logger.info("json = " + json);
			// json = new String(json.getBytes(),"gbk");
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 显示预警分析值
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String showWarning() throws BusinessException {

		Writer writer = null;
		try {
			this.getResponse().setContentType("text/html;charset=GB2312");
			writer = this.getResponse().getWriter();
			String templeCode = getRequest().getParameter("templeCode");
			WarningVO wvo = this.warningManager.findWarningVoByCode(templeCode);
			JSONObject jsonObject = JSONObject.fromObject(wvo);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();
			logger.info("json = " + json);
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("显示预警分析数据出错！");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 创建树目录
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String createDirectoryTree() throws BusinessException {
		Writer writer = null;
		try {
			String name = getRequest().getParameter("name");
			String pid = getRequest().getParameter("pid");
			writer = getResponse().getWriter();
			logger.info("name = " + name);
			logger.info("pid = " + pid);
			if (!StringUtil.isBlank(name) && !StringUtil.isBlank(pid)) {
				Long id = this.warningManager.addTreeDirectory(name, pid);
				writer.write(id.toString());
			} else {
				writer.write("");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 重命名目录名称
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String renameDirectoryTree() throws BusinessException {
		Writer writer = null;
		try {
			String name = getRequest().getParameter("name");
			String id = getRequest().getParameter("id");
			writer = getResponse().getWriter();
			logger.info("name = " + name);
			logger.info("id = " + id);
			if (!StringUtil.isBlank(name) && !StringUtil.isBlank(id)) {
				this.warningManager.renameTreeDirectory(name, id);
				writer.write(id.toString());
			} else {
				writer.write("");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 重命名目录名称
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String removeDirectoryTree() throws BusinessException {
		Writer writer = null;
		try {
			String id = getRequest().getParameter("id");
			writer = getResponse().getWriter();
			logger.info("id = " + id);
			if ( !StringUtil.isBlank(id)) {
				this.warningManager.removeTreeDirectory(id);
				writer.write(id.toString());
			} else {
				writer.write("");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}
	
	
	/**
	 * 删除template信息
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String removeTemplate() throws BusinessException {
		Writer writer = null;
		try {
			String id = getRequest().getParameter("id");
			writer = getResponse().getWriter();
			logger.info("id = " + id);
			if ( !StringUtil.isBlank(id)) {
				this.warningManager.removeTemplate(id);
				writer.write(id.toString());
			} else {
				writer.write("");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 重命名预警分析信息
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String reNameTemplate() throws BusinessException {
		Writer writer = null;
		try {
			String id = getRequest().getParameter("id");
			String name = getRequest().getParameter("name");
			writer = getResponse().getWriter();
			logger.info("id = " + id);
			logger.info("name = " + name);
			if (!StringUtil.isBlank(name) && !StringUtil.isBlank(id)) {
				this.warningManager.renameTemplate(name, id);
				writer.write(id.toString());
			} else {
				writer.write("");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}
}
