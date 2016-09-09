package com.cmcc.framework.controller.action.loap;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRChild;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRExpression;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.io.XMLWriter;

import com.cmcc.common.Global;
import com.cmcc.common.config.LoadConfig;
import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.CommonUtil;
import com.cmcc.common.util.DomTreeBuilderUtil;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.ItemTableVO;
import com.cmcc.framework.controller.formbean.JrxmlVO;
import com.cmcc.framework.controller.formbean.QueriesVO;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.model.Structure;

/**
 * 即席查询action
 * 
 * @author zhangzhanliang
 * @since 2013/2/23
 */
public class LoapAction extends WebActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2967239163930191846L;

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(LoapAction.class);

	/**
	 * 左侧数请求解析方法
	 */
	public String execute() throws BusinessException {
		Integer intPage = 0;

		try {
			String tableCode = getRequest().getParameter("tableCode");
			String currPage = getRequest().getParameter("page");
			String searchValue = getRequest().getParameter("searchValue");

			if (StringUtil.isBlank(tableCode)) {
				logger.error("tableCode is null");
				throw new BusinessException("tableCode is null");
			}

			if (!StringUtil.isBlank(currPage) && StringUtil.isNumeric(currPage)) {
				intPage = Integer.valueOf(currPage);
			}
			List<ItemTableVO> itemVoList = queriesManager
					.findItemTableBy(tableCode);
			Object[] arrays = this.queriesManager.findByTableItem(tableCode,
					itemVoList, searchValue, intPage, Global.PAGESIZE);
			List<Object[]> tableList = (List<Object[]>) arrays[0];
			Page pageInfo = (Page) arrays[1];

			getRequest().setAttribute("itemList", itemVoList);
			getRequest().setAttribute("tableList", tableList);
			getRequest().setAttribute("pageInfo", pageInfo);
			getRequest().setAttribute("itemCount", itemVoList.size());

			getRequest().setAttribute("tableCode", tableCode);
			getRequest().setAttribute("searchValue", searchValue);
		} catch (Exception e) {
			logger.error("显示表信息出错是!", e);
		}
		return "listLoap";
	}

	/**
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws UnsupportedEncodingException
	 */
	public String leftFrame() throws BusinessException,
			UnsupportedEncodingException {
		String tableCode = getRequest().getParameter("tableCode");
		String tableName = getRequest().getParameter("name");
		tableName = new String(tableName.getBytes("ISO8859-1"), "utf-8");
		getRequest().setAttribute("tableCode", tableCode);
		getRequest().setAttribute("tableName", tableName);
		return "leftFrame";
	}

	/**
	 * 返回右侧上 关于字段头的信息
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String leftTopFrame() throws BusinessException {
		try {
			String tableCode = getRequest().getParameter("tableCode");
			String tableName = getRequest().getParameter("tableName");
			tableName = new String(tableName.getBytes("ISO8859-1"), "utf-8");
			if (StringUtil.isBlank(tableCode)) {
				logger.error("tableCode is null");
				throw new BusinessException("tableCode is null");
			}
			List<ItemTableVO> itemVoList = queriesManager
					.findItemTableBy(tableCode);
			// 按钮权限判断
			String btns = "_errorOver";
			if (getUserSessionInfo().getRoleLevel().intValue() != 0) {
				btns = this.permissionManager.findPcenterByRid(
						getUserSessionInfo().getRid(),
						PermissionMark.LoapBtn_permission.getValue());
			} else {
				btns = "";
			}

			getRequest().setAttribute("btns", btns);

			getRequest().setAttribute("itemList", itemVoList);
			getRequest().setAttribute("tableCode", tableCode);
			getRequest().setAttribute("tableName", tableName);
		} catch (Exception e) {
			logger.error("显示表头信息出错是!", e);
		}
		return "leftTopFrame";
	}

	/**
	 * group 类型的查询
	 */
	public String executeGroup() throws BusinessException {
		try {
			String year = getRequest().getParameter("year");
			List<QueriesVO> jlist = null;
			if (this.getUserSessionInfo().getRoleLevel().intValue() == 0) {
				jlist = this.queriesManager.findByGroupAndYear(year);
			} else {
				jlist = this.queriesManager.findByGroupAndYear(year,getUserSessionInfo()
						.getRid(), PermissionMark.LoapTree_permission
						.getValue());
			}

			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			ServletOutputStream stream = this.getResponse().getOutputStream();

			XMLWriter writer = new XMLWriter(stream);
			if (jlist != null && jlist.size() > 0) {
				writer.write(DomTreeBuilderUtil.createLoapDomTree(jlist,
						"loapAction!executeObject." + Global.ACTION_EXT, this
								.getBasePath(), "type=", "", "loapRight"));
			} else {
				Document document = DocumentHelper.createDocument();
				writer.write(document);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("jrxml文件列表获取错误");
		}
		return null;
	}

	/**
	 * object 类型的查询
	 */
	public String executeObject() throws BusinessException {
		try {
			String id = getRequest().getParameter("groupId");
			String name = getRequest().getParameter("name");
			if (StringUtil.isBlank(name)) {
				logger.error("目录名称传送为空 ");
			}
			name = new String(name.getBytes("ISO8859-1"), "utf-8");
			List<QueriesVO> jlist = null;
			if (this.getUserSessionInfo().getRoleLevel().intValue() == 0) {
				jlist = this.queriesManager.findByObject(name, id);
			} else {
				jlist = this.queriesManager.findByObject(name, id,
						getUserSessionInfo().getRid(),
						PermissionMark.LoapTree_permission.getValue());
			}

			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			ServletOutputStream stream = this.getResponse().getOutputStream();

			XMLWriter writer = new XMLWriter(stream);
			if (jlist != null && jlist.size() > 0) {
				writer.write(DomTreeBuilderUtil.createLoapDomTree(jlist,
						"loapAction!executeTable." + Global.ACTION_EXT, this
								.getBasePath(), "", "", "loapRight"));
			} else {
				Document document = DocumentHelper.createDocument();
				writer.write(document);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("jrxml文件列表获取错误");
		}
		return null;
	}

	/**
	 * table第三层 类型的查询
	 */
	public String executeTable() throws BusinessException {
		try {
			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			ServletOutputStream stream = this.getResponse().getOutputStream();
			Document document = DocumentHelper.createDocument();
			XMLWriter writer = new XMLWriter(stream);

			String tableCode = getRequest().getParameter("tableCode");
			String id = getRequest().getParameter("groupId");
			if (!StringUtil.isBlank(tableCode)) {
				List<QueriesVO> jlist = null;
//				if (getUserSessionInfo().getRoleLevel().intValue() == 0) {
					jlist = this.queriesManager.findByTable(tableCode, id);
//				} else {
//					jlist = this.queriesManager.findByTable(tableCode, id,
//							getUserSessionInfo().getRid(),
//							PermissionMark.LoapTree_permission.getValue());
//				}
				if (jlist != null && jlist.size() > 0) {
					document = DomTreeBuilderUtil.createLoapDomTree(jlist, "",
							this.getBasePath(), "", "", "loapRight");
				}
			}

			writer.write(document);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("jrxml文件列表获取错误");
		}
		return null;
	}
	
	/**
	 * group 类型的查询
	 */
	public String executeGroupAndYear() throws BusinessException {
		try {
			List<QueriesVO> jlist = new ArrayList<QueriesVO>();
			QueriesVO vo2012 = new QueriesVO();
			vo2012.setModelName("2012年");
			vo2012.setIdentifier(0);
			vo2012.setParentId(0);
			vo2012.setIsLeaf(0);
			vo2012.setActionUrl(getContext()
					+ "/manage/loap/loapAction!executeGroup."
					+ Global.ACTION_EXT + "?groupId=0&name=2012年&year=2012&url=");
			
			jlist.add(vo2012);
			
			QueriesVO vo2013 = new QueriesVO();
			vo2013.setModelName("2013年");
			vo2013.setIdentifier(0);
			vo2013.setParentId(0);
			vo2013.setIsLeaf(0);
			vo2013.setActionUrl(getContext()
					+ "/manage/loap/loapAction!executeGroup."
					+ Global.ACTION_EXT + "?groupId=0&name=2013年&year=2013&url=");
			
			jlist.add(vo2013);
			
			
			QueriesVO vo2014 = new QueriesVO();
			vo2014.setModelName("2014年");
			vo2014.setIdentifier(0);
			vo2014.setParentId(0);
			vo2014.setIsLeaf(0);
			vo2014.setActionUrl(getContext()
					+ "/manage/loap/loapAction!executeGroup."
					+ Global.ACTION_EXT + "?groupId=0&name=2014年&year=2014&url=");
			
			jlist.add(vo2014);

			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			ServletOutputStream stream = this.getResponse().getOutputStream();

			XMLWriter writer = new XMLWriter(stream);
			if (jlist != null && jlist.size() > 0) {
				writer.write(DomTreeBuilderUtil.createLoapDomTreeYear(jlist,this
								.getBasePath(), "type=", "", "loapRight"));
			} else {
				Document document = DocumentHelper.createDocument();
				writer.write(document);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("jrxml文件列表获取错误");
		}
		return null;
	}

	/**
	 * group 类型的查询
	 */
	public String executeStructure() throws BusinessException {
		try {
			String spid = getRequest().getParameter("groupId");
			String stype = getRequest().getParameter("stype");
			if("1".equals(stype)){
				return executeStructureObject(spid);
			}
			List<QueriesVO> jlist = null;
			if(this.getUserSessionInfo().getRoleLevel().intValue() == 0){
				jlist = this.queriesManager.findStructureByPid(Integer.valueOf(spid));
			}else{
				jlist = this.queriesManager.findStructureByPid(Integer.valueOf(spid),getUserSessionInfo().getRid(),PermissionMark.LoapTree_permission.getValue());
			}

			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			ServletOutputStream stream = this.getResponse().getOutputStream();

			XMLWriter writer = new XMLWriter(stream);
			if (jlist != null && jlist.size() > 0) {
				writer.write(DomTreeBuilderUtil.createQueriesDomTree(jlist,
						"loapAction!executeStructure." + Global.ACTION_EXT,
						this.getBasePath(), "", "", "queriesRight"));
			} else {
				Document document = DocumentHelper.createDocument();
				writer.write(document);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("jrxml文件列表获取错误");
		}
		return null;
	}
	
	/**
	 * object 类型的查询
	 */
	public String executeStructureObject(String id) throws BusinessException {
		try {
			Structure stru = queriesManager.findStructureById(Integer.valueOf(id));
			List<QueriesVO> jlist = null;
			if(this.getUserSessionInfo().getRoleLevel().intValue() == 0){
				jlist = this.queriesManager.findByObjectByCodes(stru.getScenter(),stru.getSid());
			}else{
				jlist = this.queriesManager.findByObjectByCodes(stru.getScenter(),stru.getSid(),getUserSessionInfo().getRid(),PermissionMark.LoapTree_permission.getValue());
			}

			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			ServletOutputStream stream = this.getResponse().getOutputStream();

			XMLWriter writer = new XMLWriter(stream);
			if (jlist != null && jlist.size() > 0) {
				writer.write(DomTreeBuilderUtil.createQueriesDomTree(jlist,
						"loapAction!executeTable." + Global.ACTION_EXT, this
								.getBasePath(), "", "", "queriesRight"));
			} else {
				Document document = DocumentHelper.createDocument();
				writer.write(document);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			throw new BusinessException("jrxml文件列表获取错误");
		}
		return null;
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

			QueriesVO vo = new QueriesVO();
			vo.setModelName("试点调查数据");
			vo.setIdentifier(0);
			vo.setParentId(0);
			vo.setIsLeaf(0);
			vo.setActionUrl(getContext()
					+ "/manage/loap/loapAction!executeStructure."
					+ Global.ACTION_EXT + "?groupId=0&name=loap查询&url=");
			request.setAttribute("rootGroup", vo);

		} catch (Exception e) {
			logger.error("jrxml文件列表方法错误", e);
			throw new BusinessException("jrxml文件列表方法错误");
		}
		return "listTreeLoap";
	}

	/**
	 * 左侧 jrxml文件列表
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String listJrxml() throws BusinessException {
		try {
			HttpServletRequest request = this.getRequest();
			List<JrxmlVO> jlist = new ArrayList<JrxmlVO>();
			String jrxmlUrl = LoadConfig.getInstance().getJrxmlUrl();
			File f = new File(jrxmlUrl);
			if (!f.exists()) {
				throw new BusinessException(jrxmlUrl + " 目录不存在");
			}
			// selectFile(jlist, f, jrxmlUrl);
			request.setAttribute("jrxmlList", jlist);
		} catch (Exception e) {
			logger.error("jrxml文件列表方法错误", e);
			throw new BusinessException("jrxml文件列表方法错误");
		}
		return "listJrxml";
	}

	/**
	 * 浏览目录
	 * 
	 * @param jlist
	 * @param file
	 * @param url
	 *            文件路径
	 */
	private void selectFile(List<JrxmlVO> jlist, File file, String url,
			String parId) {
		String[] fn = file.list();
		File f = null;
		JrxmlVO vo = new JrxmlVO();
		StringBuilder ids = new StringBuilder();
		String jrxmlUrl = LoadConfig.getInstance().getJrxmlUrl();
		String parStr = url.substring(jrxmlUrl.length());
		for (int i = 0, j = 1; i < fn.length; i++, j++) {
			ids.append(j);
			ids.append(parId);
			String n = fn[i];

			f = new File(url + File.separator + n);
			vo = new JrxmlVO();
			vo.setModelName(n);
			vo.setParentId(Integer.valueOf(parId));
			vo.setIdentifier(Integer.valueOf(ids.toString()));
			vo.setActionUrl("/manage/rawdata/rawDataAction!viewJrxml."
					+ Global.ACTION_EXT + "?groupId=" + ids + "&name=" + n
					+ "&parStr=" + parStr);
			vo.setParentUrl(parStr);
			if (logger.isDebugEnabled())
				logger.debug(n);
			if (f.exists()) {
				if (f.isDirectory()) {
					vo.setIsLeaf(0);
					jlist.add(vo);
				}
				if (f.isFile() && n.indexOf(".jrxml") != -1) {
					vo.setIsLeaf(1);
					jlist.add(vo);
				}
			}
			ids.delete(0, ids.length());
		}
	}

	/**
	 * 分页显示 jrxml文件内容
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String viewJrxmlPage(File jrxmlFile, String path, String fileName,
			String parStr) throws BusinessException {
		try {

			// 1.开发人员开发报表设计文件，也就是定义一个.jrxml文件（就是最开始的那个XML
			// 文件）。
			JasperDesign jd = JRXmlLoader.load(jrxmlFile);// jrxml文件
			JRSection jrs = jd.getDetailSection();
			JRBand[] bands = jrs.getBands();
			for (JRBand b : bands) {
				logger.debug(b);
				List<JRChild> cl = b.getChildren();
				logger.debug(cl);
				for (int i = 0; i < cl.size(); i++) {
					JRChild c = cl.get(i);
					if (c instanceof net.sf.jasperreports.engine.design.JRDesignImage) {
						JRDesignImage jrdi = (JRDesignImage) c;
						logger.debug(jrdi);
						JRExpression jre = jrdi.getExpression();
						logger.debug(jre);
						if (jre instanceof net.sf.jasperreports.engine.design.JRDesignExpression) {
							JRDesignExpression jrd = (JRDesignExpression) jre;
							logger.debug(jrd);
						}
					}
					logger.debug(c);
				}
				JRElement e = b.getElementByKey("image");
				logger.debug(e);
			}
			// 2. 使用JasperReports提供API中的JasperCompileManager类编译.jrxml文
			// 件，编译后生成一个.jasper文件。
			JasperReport jr = JasperCompileManager.compileReport(jd);

			JRQuery query = jr.getQuery();
			logger.debug(query.getText());
			// 3.使用JasperReports提供API中的JasperFillManager类填充编译后的.jasper
			// 文件，填充后生成一个.jrprint文件。
			JasperPrint jp = rawDataManager.getJasperPrint(jr);
			// JasperFillManager.fillReport(jr, null,
			// this.rawDataManager.getConnection());
			HttpServletRequest request = this.getRequest();

			JRHtmlExporter exporter = new JRHtmlExporter();
			int pageIndex = 0;
			int lastPageIndex = 0;
			if (jp.getPages() != null) {
				lastPageIndex = jp.getPages().size() - 1;
			}
			String pageStr = request.getParameter("page");
			try {
				if (!StringUtil.isBlank(pageStr))
					pageIndex = Integer.parseInt(pageStr);
			} catch (Exception e) {
				logger.error("传递的页数非数字", e);
				throw new BusinessException("传递的页数非数字。page = " + pageStr);
			}

			if (pageIndex < 0) {
				pageIndex = 0;
			}

			if (pageIndex > lastPageIndex) {
				pageIndex = lastPageIndex;
			}

			Page pageInfo = new Page(lastPageIndex);
			pageInfo.setPage(pageIndex);

			StringBuffer sbuffer = new StringBuffer();

			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
			// exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
			// getResponse().getWriter());
			exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER,
					sbuffer);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, this
					.getBasePath()
					+ "/images/jasperreports/");
			exporter.setParameter(JRExporterParameter.PAGE_INDEX, new Integer(
					pageIndex));
			exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
			exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
					"");
			exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
			exporter.exportReport();

			request.setAttribute("page", pageInfo);
			request.setAttribute("sbuffer", sbuffer.toString());
			request.setAttribute("pathUrl",
					"/manage/rawdata/rawDataAction!viewJrxml."
							+ Global.ACTION_EXT + "?&name=" + fileName
							+ "&parStr=" + parStr);
			logger.info(sbuffer);
		} catch (Exception e) {
			logger.error("分布显示错误", e);
			throw new BusinessException("分页显示错误");
		}
		return "viewPage";
	}

	/**
	 * 右侧 jrxml 文件显示
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("deprecation")
	public String viewJrxml() throws BusinessException {
		try {
			HttpServletRequest request = this.getRequest();
			String fileName = request.getParameter("name");
			String parStr = request.getParameter("parStr");
			String serverParStr = parStr;
			String clientParStr = parStr;
			String clientFileName = fileName;
			if (StringUtil.isBlank(fileName)) {
				logger.error("fileName is null");
				throw new BusinessException("fileName is null");
			}

			String jrxmlUrl = LoadConfig.getInstance().getJrxmlUrl();
			fileName = new String(fileName.getBytes("ISO8859-1"), "utf-8");
			if (!StringUtil.isBlank(parStr)) {
				parStr = new String(parStr.getBytes("ISO8859-1"), "utf-8");
				serverParStr = parStr;
				serverParStr = serverParStr.replaceAll(" ", "");
				serverParStr = convertChinese(serverParStr);
			}
			String name = fileName;
			if (fileName.endsWith(Global.jrxmlSuffix)) {
				name = fileName.substring(0, fileName
						.lastIndexOf(Global.jrxmlSuffix));
				name = name.replaceAll(" ", "");
				name = convertChinese(name);
			} else {
				return null;
			}

			HttpServletResponse response = this.getResponse();
			String path = request.getRealPath(Global.staticRportHtml);
			if (logger.isDebugEnabled())
				logger.debug("path = " + path);
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

			StringBuilder sfile = new StringBuilder();
			sfile.append(jrxmlUrl);
			if (!jrxmlUrl.endsWith(File.separator)) {
				sfile.append(File.separator);
			}
			if (!StringUtil.isBlank(parStr)) {
				sfile.append(parStr).append(File.separator);
			}
			sfile.append(fileName);
			if (logger.isDebugEnabled())
				logger.debug("file path = " + sfile);

			response.setContentType("text/xml;charset=" + Global.CHARSET);
			response.setHeader("Content-Type", "text/html; charset=UTF-8");

			// File f = new File(htmlFilePath.toString() + File.separator + name
			// + ".html");
			// StringBuilder send = null;
			// if (!f.exists()) {
			// exportHtmlFile(new File(sfile.toString()),
			// htmlFilePath.toString() + File.separator, name);
			// while (!f.exists()) {
			// Thread.sleep(500);
			// }
			// } // 重定向 文件路径 StringBuilder
			// send = new StringBuilder();
			// send.append(getContext()).append("/")
			// .append(Global.staticRportHtml);
			// if (!StringUtil.isBlank(serverParStr))
			// send.append(serverParStr).append("/");
			// send.append(name).append(".html");
			// if (logger.isDebugEnabled())
			// logger.debug(send);
			// response.sendRedirect(send.toString());
			return viewJrxmlPage(new File(sfile.toString()), htmlFilePath
					.toString()
					+ File.separator, clientFileName, clientParStr);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
	}

	@GenericLogger(operateMark = OperateMark.CUSTOM_QUERY, operateDescription = "OLAP分析-浏览", isExtend = true)
	public String showFlashTable() throws BusinessException {
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
		return "showFlashTable";
	}

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
	 * 保存数据 action
	 * 
	 * @return
	 */

	@GenericLogger(operateMark = OperateMark.OLAP_ADD, operateDescription = "OLAP-添加", isExtend = true)
	public String saveCostomana() {
		try {
			String id = getRequest().getParameter("customId");
			String name = getRequest().getParameter("cosName");
			String memo = getRequest().getParameter("texmemo");
			String sql = getRequest().getParameter("sqlValue");
			String imgConfig = getRequest().getParameter("imgConfig");
			String pid = getRequest().getParameter("pid");
			imgConfig = imgConfig.replaceAll("\r|\n", "");

			if (id == null || "".equals(id)) {
				sql = sql.replaceAll("\r|\n", "");
				this.customManager.insert(name, memo, imgConfig, sql, pid);
			} else {
				this.customManager.update(id, name, memo, imgConfig, sql, pid);
			}

		} catch (Exception e) {
			logger.error("保存 loap时出错", e);
		}
		return null;
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

	/**
	 * ajax 异步请求 返回存储目录树
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String viewAllTree() throws BusinessException {
		Writer writer = null;
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			writer = this.getResponse().getWriter();
			List<ZtreeVO> l = null;
			// l = this.warningManager.findDisctoryTreeByTypeAndRid(1, 0);

			int rid = this.getUserSessionInfo().getRid();
			if (this.getUserSessionInfo().getRoleLevel().intValue() == 0) {
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
			throw new BusinessException("定制分析 存储目录树信息错误");
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
