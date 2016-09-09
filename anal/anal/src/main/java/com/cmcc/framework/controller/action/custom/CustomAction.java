package com.cmcc.framework.controller.action.custom;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;

import com.cmcc.common.Global;
import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.CommonUtil;
import com.cmcc.common.util.DomTreeBuilderUtil;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.StringUtils;
import com.cmcc.common.util.date.DateUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.CustomVO;
import com.cmcc.framework.controller.formbean.ItemTableVO;
import com.cmcc.framework.controller.formbean.ZtreeVO;

/**
 * 定制分析action
 * 
 * @author wangbo
 * @since 2013/3/6
 */
public class CustomAction extends WebActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2967239163930191846L;

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(CustomAction.class);

	/**
	 * 左侧数请求解析方法
	 */
	public String execute() throws BusinessException {
		Integer intPage = 0;

		try {
			String tableCode = getRequest().getParameter("tableCode");
			String currPage = getRequest().getParameter("page");

			if (StringUtil.isBlank(tableCode)) {
				logger.error("tableCode is null");
				throw new BusinessException("tableCode is null");
			}

			if (!StringUtil.isBlank(currPage) && StringUtil.isNumeric(currPage)) {
				intPage = Integer.valueOf(currPage);
			}
			Object[] arrays = this.customManager.findByTableItem(tableCode,
					intPage, Global.PAGESIZE);
			List<String> itemList = (List<String>) arrays[0];
			List<Object[]> tableList = (List<Object[]>) arrays[1];
			Page pageInfo = (Page) arrays[2];

			getRequest().setAttribute("itemList", itemList);
			getRequest().setAttribute("tableList", tableList);
			getRequest().setAttribute("pageInfo", pageInfo);
		} catch (Exception e) {
			logger.error("显示表信息出错是!", e);
		}
		return "listQueries";
	}

	/**
	 * group 类型的查询
	 */
	public String executeGroup() throws BusinessException {
		XMLWriter writer = null;
		ServletOutputStream stream = null;
		try {
			if (getUserSessionInfo() == null) {
				throw new BusinessException("会话取消");
			}
			List<CustomVO> jlist = null;
			if (this.getUserSessionInfo().getRoleLevel().intValue() == 0) {
				jlist = this.customManager.findByGroup();
			} else {
				jlist = this.customManager.findByGroup(getUserSessionInfo()
						.getRid());
			}
			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			stream = this.getResponse().getOutputStream();

			writer = new XMLWriter(stream);
			if (jlist != null && jlist.size() > 0) {
				writer.write(DomTreeBuilderUtil.createCustomDomTree(jlist,
						"customAction!executeObject." + Global.ACTION_EXT, this
								.getBasePath(), "type=", "", ""));
			} else {
				Document document = DocumentHelper.createDocument();
				writer.write(document);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("读取树异常");
		} finally {
			closeWriter(writer);
			if (stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (IOException e) {
					logger.error(e);
				}

				stream = null;
			}
		}
		return null;
	}

	/**
	 * object 类型的查询
	 */
	public String executeObject() throws BusinessException {
		XMLWriter writer = null;
		ServletOutputStream stream = null;
		try {
			String id = getRequest().getParameter("groupId");
			String name = getRequest().getParameter("name");
			if (StringUtil.isBlank(name)) {
				logger.error("目录名称传送为空 ");
			}
			name = new String(name.getBytes("ISO8859-1"), "utf-8");
			List<CustomVO> jlist;
			if (this.getUserSessionInfo().getRoleLevel().intValue() == 0) {
				jlist = this.customManager.findByObject(name, id);
			} else {
				jlist = this.customManager.findByObject(name, id,
						getUserSessionInfo().getRid());
			}

			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			stream = this.getResponse().getOutputStream();

			writer = new XMLWriter(stream);
			if (jlist != null && jlist.size() > 0) {
				writer.write(DomTreeBuilderUtil.createCustomDomTree(jlist,
						"CustomAction!executeTable." + Global.ACTION_EXT, this
								.getBasePath(), "", "", "customRight"));
			} else {
				Document document = DocumentHelper.createDocument();
				writer.write(document);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("构造叶子节点异常");
		} finally {
			closeWriter(writer);
			if (stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (IOException e) {
					logger.error(e);
				}

				stream = null;
			}
		}
		return "listCustom";
	}

	/**
	 * table第三层 类型的查询 定制分析生成表
	 */

	@GenericLogger(operateMark = OperateMark.CUSTOM_QUERY, operateDescription = "定制分析-浏览", isExtend = true)
	public String executeTable() throws BusinessException {
		try {
			String id = getRequest().getParameter("id");
			String sql = "";
			String img_config = "";
			if (id == null || "".equals(id)) {
				sql = getRequest().getParameter("sqlValue");
				img_config = getRequest().getParameter("img_config");
			} else {
				sql = this.customManager.getSql(id);
				img_config = this.customManager.getChartSet(id);
				// img_config = img_config.replaceAll("\r|\n", "");
				getRequest().setAttribute("id", id);
			}

			String customTitle = getRequest().getParameter("customTitle");

			if (customTitle != null && !"".equals(customTitle)) {
				customTitle = new String(customTitle.getBytes("ISO8859-1"),
						"utf8");
				getRequest().setAttribute("customTitle", customTitle);
			}
			// TODO 获取表头
			List<Object[]> vo = this.customManager.findByDesignatedTable(sql);
			String[] itemList = CommonUtil.getMetaData(sql);
			String json = getJsonData(vo, itemList);
			// 如果没有设置图标属性，则设置默认图标属性
			if (img_config == null || "".equals(img_config)) {
				img_config = getDefaultChart(itemList);
			}

			// 按钮权限判断
			String btns = "_defaulteror";
			if (getUserSessionInfo().getRoleLevel().intValue() != 0) {
				btns = this.permissionManager.findPcenterByRid(
						getUserSessionInfo().getRid(),
						PermissionMark.CustomBtn_permission.getValue());
			} else {
				btns = "";
			}
			getRequest().setAttribute("btns", btns);
			getRequest().setAttribute("tableList", vo);
			getRequest().setAttribute("itemList", itemList);
			getRequest().setAttribute("jsondata", json);
			getRequest().setAttribute("strsql", sql);
			getRequest().setAttribute("customId", id);
			getRequest().setAttribute("status", 1);
			getRequest().setAttribute("charttype", img_config);
			getRequest().setAttribute("customTitle", customTitle);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "tableCustom";
	}

	/**
	 * 更新图标配置文件
	 */

	/**
	 * 根据数据生成 默认的图标显示属性
	 */
	public String getDefaultChart(String[] itemList) {
		/*
		 * <chart> <dataField> <field>xlabel</field> <field>value1</field>
		 * <field>value2</field> <!-- ... --> </dataField> <chartSeries>
		 * <series> <seriesType>column</seriesType> <seriesTitle>系列1</seriesTitle>
		 * <valueField>value1</valueField> </series> <!-- ... -->
		 * </chartSeries> <chartTitle>图表名</chartTitle> <xAxisField>xlabel</xAxisField>
		 * <chartD>2D</chartD> <chartView showTitle="true" showLegend="true"></chartView>
		 * <chartControl enableCursor="true" enableScrollbar="true"></chartControl>
		 * </chart>
		 */

		String setStr = "";
		String seriesStr = "";
		String fieldstr = "";

		setStr = "<chart><dataField>";
		for (int i = 0; i < itemList.length; i++) {
			fieldstr += "<field>" + itemList[i] + "</field>";
			if (i != 0) {
				seriesStr += "<series><seriesType>column</seriesType>";
				seriesStr += "<seriesTitle>" + itemList[i] + "</seriesTitle>";
				seriesStr += "<valueField>" + itemList[i] + "</valueField>";
				seriesStr += "</series>";
			}
		}
		setStr += fieldstr;
		setStr += "</dataField>";
		setStr += "<chartSeries>" + seriesStr + "</chartSeries>";
		setStr += "<chartTitle>图表名</chartTitle><xAxisField>" + itemList[0]
				+ "</xAxisField><chartD>2D</chartD>";
		setStr += "<chartView showTitle='true' showLegend='true'></chartView>";
		setStr += "<chartControl enableCursor='true' enableScrollbar='true'></chartControl>";
		setStr += "</chart>";

		return setStr;
	}

	/**
	 * 根据数据生成 json
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String getJsonData(List<Object[]> vo, String[] itemList) {

		StringBuffer dataxml = new StringBuffer();
		dataxml.append("[");
		String quent = "";
		try {

			for (int i = 0; i < vo.size(); i++) {
				dataxml.append("{");
				for (int j = 0; j < itemList.length; j++) {
					if (j == 0)
						quent = "\"";
					else
						quent = "";
					dataxml.append("\"" + itemList[j] + "\":" + quent
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

	/**
	 * 定制分析生成flash
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String executeFlashTable() throws BusinessException {
		try {
			String customTitle = getRequest().getParameter("customTitle");
			customTitle = new String(customTitle.getBytes("ISO8859-1"), "utf8");
			String id = getRequest().getParameter("id");
			String sql = this.customManager.getSql(id);
			List<Object[]> vo = this.customManager.findByDesignatedTable(sql);
			String json = "[";
			for (int i = 0; i < vo.size(); i++) {
				json += "{\"X_Label\":\"" + vo.get(i)[0] + "\",\"value\":"
						+ vo.get(i)[1] + "},";
			}
			json = json.substring(0, json.length() - 1);
			json += "]";
			getRequest().setAttribute("tableList", json);
			getRequest().setAttribute("status", 1);
			getRequest().setAttribute("customTitle", customTitle);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "flashCustom";
	}

	/**
	 * loap分析生成flash
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String executeFlashTable2() throws BusinessException {
		try {
			String sql = getRequest().getParameter("sqlValue");
			List<Object[]> vo = this.customManager.findByDesignatedTable(sql);
			String json = "[";
			for (int i = 0; i < vo.size(); i++) {
				json += "{\"X_Label\":\"" + vo.get(i)[0] + "\",\"value\":"
						+ vo.get(i)[1] + "},";
			}
			json = json.substring(0, json.length() - 1);
			json += "]";
			getRequest().setAttribute("tableList", json);
			getRequest().setAttribute("status", 1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return "flashCustom";
	}

	/**
	 * 树的登录方法
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String treeIndex() throws BusinessException {
		try {
			HttpServletRequest request = this.getRequest();

		} catch (Exception e) {
			logger.error("构造树异常", e);
			throw new BusinessException("构造树异常");
		}
		return "listTreeCustom";
	}

	public String flashTableFrame() throws BusinessException {
		try {
			String id = getRequest().getParameter("id");
			String customTitle = getRequest().getParameter("customTitle");
			customTitle = new String(customTitle.getBytes("ISO8859-1"), "utf8");
			getRequest().setAttribute("status", 1);
			getRequest().setAttribute("id", id);
			getRequest().setAttribute("customTitle", customTitle);

			return "listCustom";
		} catch (Exception e) {
			logger.error("编码转换异常", e);
			throw new BusinessException("编码转换异常");
		}

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
	 * 首页 index
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String index() throws BusinessException {
		return "index";
	}

	/**
	 * 欢迎页面
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String welcomePage() throws BusinessException {
		return "welcome";
	}

	public String exportTable() throws BusinessException {
		OutputStream os = null;
		try {
			String id = getRequest().getParameter("customId");
			String sql = null;
			if (StringUtil.isBlank(id)) {
				sql = getRequest().getParameter("strsql");
				sql = new String(sql.getBytes("ISO8859-1"), "utf8");
			} else {
				sql = this.customManager.getSql(id);
			}
			String customTitle = getRequest().getParameter("customTitle");
			customTitle = new String(customTitle.getBytes("ISO8859-1"), "utf8");

			List<Object[]> vo = this.customManager.findByDesignatedTable(sql);
			String[] itemList = CommonUtil.getMetaData(sql);
			this.getRequest().setCharacterEncoding("UTF-8");
			String currentDay = DateUtil.currentDate();
			String fname = customTitle + "查询结果" + currentDay + ".xls";// Excel文件名字
			// String sql = “xxxx”;
			// List list = getService().findList(sql);

			os = getResponse().getOutputStream();
			getResponse().reset();
			getResponse().setHeader(
					"Content-disposition",
					"attachment;filename="
							+ new String(fname.getBytes("GBK"), "ISO8859-1"));
			getResponse().setContentType("application/msexcel;charset=UTF-8");
			int c = 0;
			int curPage = 0;
			exportExcel(os, itemList, vo, c + 1, curPage);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
					os = null;
				} catch (IOException e) {
					logger.error("关闭下载流异常", e);
					throw new BusinessException("关闭下载流异常");
				}

			}
		}
		return null;
	}

	/**
	 * 
	 * @param os
	 * @param itemVoList
	 * @param tableList
	 * @param row
	 * @param col
	 * @throws Exception
	 */
	public void exportExcel(OutputStream os, String[] itemList,
			List<Object[]> tableList, int row, int col) throws Exception {

		// int row = 1;// 从第三行开始写
		// int col = 0;// 从第一列开始写
//		String imageName = getRequest().getParameter("imageName");
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(os);// 创建Excel文件
			WritableSheet ws = wwb.createSheet("导出信息", 0);// 创建sheet
			// 设置表头
			Label label = null;
//			ItemTableVO vo = null;
			if (col == 0) {
				for (int i = 0; i < itemList.length; i++) {
					String itemName = itemList[i];
					label = new Label(i, 0, itemName);
					ws.addCell(label);
				}
			}
			// 设置表数据
			if (tableList != null && tableList.size() > 0) {
				Object[] arro = null;
				for (int i = 0; i < tableList.size(); i++) {
					arro = (Object[]) tableList.get(i);
					for (int j = 0; j < arro.length; j++) {
						ws.addCell(new Label(col++, row, String
								.valueOf(arro[j])));// 
					}
					row++;
					col = 0;
				}

			}
//			if (imageName != null && !"".equals(imageName)) {
				exportPic(wwb);
//			}
			wwb.write();

		} catch (Exception e) {
			logger.error("导出 excel 异常", e);
		} finally {
			itemList = null;
			if (wwb != null) {
				try {
					wwb.close();
				} catch (Exception e) {
					logger.error("导出 excel 异常", e);

				} finally {
					wwb = null;
				}
			}
		}
	}

	public String exportPic(WritableWorkbook workbook)
			throws Exception {
		WritableSheet sheet = workbook.createSheet("导出图片", 0);
		sheet.mergeCells(0, 0, 18, 18);// 起始列，起始行，终止列，终止行
		WritableFont bold = new WritableFont(WritableFont.ARIAL, 10,
				WritableFont.BOLD);
		WritableCellFormat titleFormat = new WritableCellFormat(bold);

		titleFormat.setAlignment(Alignment.CENTRE);
		titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE);

		Label title = new Label(0, 0, "导出图表", titleFormat);

		sheet.setRowView(0, 600, true);
		sheet.addCell(title);

		 String imageName = (String) this.getSession().getAttribute("CUSTOM_IMAGE");
		// 图片F:\\pica_workspace\\anal\\tmp\\19840273418297.png
		// getServletContext().getRealPath("/");
		String realPath = getRequest().getSession().getServletContext()
				.getRealPath("/");
		File image = new File(realPath + "/static/temp/" + imageName);
		if (image != null) {
			WritableImage ri = new WritableImage(0, 0, 15, 15, image);
			sheet.getSettings().setProtected(false);
			sheet.addImage(ri);
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
				Long id = this.customManager.addTreeDirectory(name, pid);
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
				this.customManager.renameTreeDirectory(name, id);
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
			if (!StringUtil.isBlank(id)) {
				this.customManager.removeTreeDirectory(id);
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
			int rid = getUserSessionInfo().getRid();
			if (0 == getUserSessionInfo().getRoleLevel().intValue()) {
				rid = 0;
			}
			l = this.customManager.findTreeByTypeAndRid(1, rid);
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
	 * 保存图片
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws IOException
	 */
	public String saveImage() throws BusinessException, IOException {
		this.getResponse().setContentType("application/octet-stream");
		InputStream is = getRequest().getInputStream();
		try {
			int size = 0;
			int len = 0;
//			byte[] b = new byte[100000];
			byte[] tmp = new byte[1024];
			long time = System.currentTimeMillis();// 系统时间 ；
			String imageName = time + ".png";
			this.getSession().setAttribute("CUSTOM_IMAGE", imageName);
			String realPath = getRequest().getSession().getServletContext()
					.getRealPath("/");
			File f = new File(realPath + "/static/temp/" + imageName);
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));

			while ((len = is.read(tmp)) != -1) {
				dos.write(tmp, 0, len);
				size += len;
			}
			dos.flush();
			dos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

}
