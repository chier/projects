package com.cmcc.framework.controller.action.rawdata;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.SimpleJasperReportsContext;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
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
import com.cmcc.common.util.DomTreeBuilderUtil;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.JrxmlVO;

/**
 * 原始数据action
 * 
 * @author zhangzhanliang
 * @since 2013/2/23
 */
public class RawDataAction extends WebActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2967239163930191846L;

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(RawDataAction.class);

	private Integer page = 1;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * 左侧数请求解析方法
	 */
	public String execute() throws BusinessException {
		try {
			// UserSessionObj obj = this.getUserSessionInfo();
			String id = getRequest().getParameter("groupId");
			String name = getRequest().getParameter("name");
			if (StringUtil.isBlank(name)) {
				logger.error("目录名称传送为空 ");
			}
			name = new String(name.getBytes("ISO8859-1"), "utf-8");
			// String isLeaf = getRequest().getParameter("isLeaf");
			String parentUrl = getRequest().getParameter("parentUrl");
			if (!StringUtil.isBlank(parentUrl))
				parentUrl = new String(parentUrl.getBytes("ISO8859-1"), "utf-8");
			List<JrxmlVO> jlist = new ArrayList<JrxmlVO>();
			String jrxmlUrl = LoadConfig.getInstance().getJrxmlUrl();
			StringBuilder s = new StringBuilder();
			s.append(jrxmlUrl);
			if (!jrxmlUrl.endsWith(File.separator)) {
				s.append(File.separator);
			}
			if (!StringUtil.isBlank(parentUrl)) {
				s.append(parentUrl).append(File.separator);
			}
			if (Integer.valueOf(id) != 0) {
				s.append(name);
			}
			if (logger.isDebugEnabled())
				logger.debug(s.toString());
			File f = new File(s.toString());
			if (!f.exists()) {
				throw new BusinessException(jrxmlUrl + " 目录不存在");
			}
			selectFile(jlist, f, s.toString(), id);

			this.getResponse().setContentType(
					"text/xml;charset=" + Global.CHARSET);
			ServletOutputStream stream = this.getResponse().getOutputStream();

			XMLWriter writer = new XMLWriter(stream);
			if (jlist != null && jlist.size() > 0) {
				writer
						.write(DomTreeBuilderUtil
								.createRawDataDomTree(
										jlist,
										"rawDataAction." + Global.ACTION_EXT,
										this.getBasePath(),
										"type=",
										"groupId=${root.identifier}&name=${root.modelName}&isLeaf=${root.isLeaf}&parentUrl=${root.parentUrl}",
										"rawDataRight"));
			} else {
				Document document = DocumentHelper.createDocument();
				writer.write(document);
			}

		} catch (Exception e) {
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

			List<JrxmlVO> jlist = new ArrayList<JrxmlVO>();
			String jrxmlUrl = LoadConfig.getInstance().getJrxmlUrl();

			File f = new File(jrxmlUrl);
			if (!f.exists()) {
				throw new BusinessException(jrxmlUrl + " 目录不存在");
			}
			/*
			 * selectFile(jlist, f, jrxmlUrl); request.setAttribute("jrxmlList",
			 * jlist);
			 */
			JrxmlVO vo = new JrxmlVO();
			vo.setModelName("调查数据表");
			vo.setIdentifier(0);
			vo.setParentId(0);
			vo.setIsLeaf(0);
			vo.setActionUrl(getContext() + "/manage/rawdata/rawDataAction."
					+ Global.ACTION_EXT + "?groupId=0&name=根目录&url=");
			vo.setParentUrl(jrxmlUrl);
			request.setAttribute("rootGroup", vo);

		} catch (Exception e) {
			logger.error("jrxml文件列表方法错误", e);
			throw new BusinessException("jrxml文件列表方法错误");
		}
		return "listJrxml";
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
		List<String> perList = null;
		int roleLevel = getUserSessionInfo().getRoleLevel().intValue();
		if (roleLevel != 0) {
			perList = this.permissionManager
					.findRawDataPerByRid(getUserSessionInfo().getRid());
		}

		for (int i = 0, j = 1; i < fn.length; i++, j++) {
			ids.delete(0, ids.length());
			ids.append(j);
			ids.append(parId);
			String n = fn[i];

			f = new File(url + File.separator + n);
			if (!f.exists()) {
				continue;
			}
			vo = new JrxmlVO();
			if (f.isFile() && n.endsWith(".jrxml") != true) {
				continue;
			}

			// 判断是目录还是文件
			if (f.isDirectory()) {
				vo.setIsLeaf(0);
			} else {
				vo.setIsLeaf(1);
			}

			if (n.endsWith(".jrxml")) {
				n = n.substring(0, n.length() - 6);
			}
			if (perList != null && !isPermission(perList, n)) {
				continue;
			}
			vo.setModelName(n);
			vo.setParentId(Integer.valueOf(parId));
			vo.setIdentifier(Integer.valueOf(ids.toString()));
			vo.setActionUrl("/manage/rawdata/rawDataAction!viewJrxml."
					+ Global.ACTION_EXT + "?groupId=" + ids + "&fname=" + n
					+ ".jrxml&parStr=" + parStr);
			vo.setParentUrl(parStr);
			if (logger.isDebugEnabled())
				logger.debug(n);
			jlist.add(vo);
			ids.delete(0, ids.length());
		}
	}

	/**
	 * 判断该目录是否有权限允许访问
	 * 
	 * @return true 表示有权限 false 表示没有权限
	 */
	private boolean isPermission(List<String> perList, String fileName) {
		boolean isPer = false;
		for (String pstr : perList) {
			if (StringUtil.isBlank(pstr)) {
				continue;
			}
			if (StringUtil.isBlank(fileName)) {
				continue;
			}
			fileName = fileName.replaceAll(" ", "");
			if (pstr.equals(fileName)) {
				return true;
			}
		}
		return isPer;
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
			jp = rawDataManager.getJasperPrint(jr);
			// 保存 jrprint 文件
			JRSaver.saveObject(jp, file);
		}
		return jp;
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
			String parStr) throws BusinessException {
		try {
			String converJrxmlName = convertJrxmlFileName(jrxmlFile.getName());
			
			JasperReport jr = jasperServerTaskOrCompile(jrxmlFile,
					converJrxmlName, jasperPath);

			JasperPrint jp = jrprintServerTaskOrCompile(jr, converJrxmlName,
					jrprintPath);

			HttpServletRequest request = this.getRequest();

			JRXhtmlExporter exporter = new JRXhtmlExporter();
			int pageIndex = 0;
			int lastPageIndex = 0;
			if (jp.getPages() != null) {
				lastPageIndex = jp.getPages().size() == 0 ? 0 : jp.getPages()
						.size() - 1;
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
			// String imageDIR = this.getContext().getFile().getAbsolutePath();
			// getWebApplicationContext(request).getResource(jasperFilePath).getFile();
			// request.getres
			String imageDIR = ServletActionContext.getServletContext()
					.getRealPath("/images/jasperreports/");
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,
					imageDIR);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, this
					.getBasePath()
					+ "/images/jasperreports/");
			// exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
			// this.getBasePath() + "/servlets/image?image=");

			exporter.setParameter(
					JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
					Boolean.TRUE);
			exporter.setParameter(
					JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,
					Boolean.TRUE);
			exporter.setParameter(JRExporterParameter.PAGE_INDEX, new Integer(
					pageIndex));
			exporter.setParameter(JRHtmlExporterParameter.HTML_HEADER, "");
			// exporter.setParameter(JRHtmlExporterParameter.BETWEEN_PAGES_HTML,
			// "");
			// exporter.setParameter(JRHtmlExporterParameter.HTML_FOOTER, "");
			try {
				exporter.exportReport();
			} catch (Exception e) {
				logger.error("jrxml 生成 xhtml 时出错！", e);
				throw new BusinessException("jrxml 生成 xhtml 时出错！");
			}

			// 解决ie浏览器图片缓存问题
			String rastr = "?t=" + Math.random();//fileName.substring(1, 3);
			Pattern pattern = Pattern
					.compile("(/images/jasperreports/)(\\w+)(\")");
			Matcher matcher = pattern.matcher(sbuffer.toString());
			String g = "";
			int offset = 0;
			while (matcher.find()) {
				offset = matcher.end();
				offset = matcher.start();
				offset = matcher.end();
				g=matcher.group(0);
			}
			sbuffer.insert(offset-1, rastr);
			//sbuffer.indexOf(".png")

			// go on
			request.setAttribute("page", pageInfo);
			request.setAttribute("sbuffer", sbuffer.toString());
			request.setAttribute("fname", fileName);
			request.setAttribute("parStr", parStr);

			request.setAttribute("pathUrl",
					"/manage/rawdata/rawDataAction!viewJrxml."
							+ Global.ACTION_EXT + "?&parStr=" + parStr);

			request.setAttribute("exportReportPathUrl",
					"/manage/rawdata/rawDataAction!exportReport."
							+ Global.ACTION_EXT + "?&parStr=" + parStr);

			// 按钮权限判断
			String btns = "_errorOver";
			if (getUserSessionInfo().getRoleLevel().intValue() != 0) {
				btns = this.permissionManager.findPcenterByRid(
						getUserSessionInfo().getRid(),
						PermissionMark.RawDataBtn_permission.getValue());
			} else {
				btns = "";
			}
			getRequest().setAttribute("btns", btns);

			if (logger.isDebugEnabled())
				logger.debug(sbuffer);
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
	
	@GenericLogger(operateMark = OperateMark.RAW_DATA_QUERY,operateDescription = "原始数据-浏览数据", isExtend = true)
	public String viewJrxml() throws BusinessException {
		try {
			HttpServletRequest request = this.getRequest();
			HttpServletResponse response = this.getResponse();
			String fileName = request.getParameter("fname");
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

			return viewJrxmlPage(new File(sfile.toString()), htmlFilePath,
					jasperFilePath, jrprintFilePath, clientFileName,
					clientParStr);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException(e);
		}
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
	 * 创建服务器上存放 html 的目录
	 * 
	 */
	private String createServerHtmlDirectory(HttpServletRequest request,
			String serverParStr) {

		String path = request.getRealPath(Global.staticRportHtml);
		return this.createServerDirectory(request, serverParStr, path);
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
	 * 导出 report 格式等数据
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String exportReport() throws BusinessException {
		OutputStream stream = null;
		try {
			stream = this.getResponse().getOutputStream();
			String type = getRequest().getParameter("type");
			String fileName = getRequest().getParameter("fname");
			String parStr = getRequest().getParameter("parStr");

			if (StringUtil.isBlank(fileName)) {
				logger.error("fileName is null");
				throw new BusinessException("fileName is null");
			}

			String jrxmlUrl = LoadConfig.getInstance().getJrxmlUrl();
			fileName = new String(fileName.getBytes("ISO8859-1"), "utf-8");
			if (!StringUtil.isBlank(parStr)) {
				parStr = new String(parStr.getBytes("ISO8859-1"), "utf-8");
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
			JasperPrint jp = null;

			// 1.开发人员开发报表设计文件，也就是定义一个.jrxml文件（就是最开始的那个XML
			// 文件）。
			File jrxmlFile = new File(sfile.toString());
			JasperDesign jd = JRXmlLoader.load(jrxmlFile);// jrxml文件

			JasperReport jr = JasperCompileManager.compileReport(jd);

			jp = rawDataManager.getJasperPrint(jr);// exportReportToXmlStream
			if (StringUtil.isBlank(type)) {
				return null;
			}
			String downName = fileName.substring(0, fileName
					.lastIndexOf(Global.jrxmlSuffix));
			downName = downName.replaceAll(" ", "");
			downName = new String(downName.getBytes("GB2312"), "ISO-8859-1");
			if (type.equals("html")) {
				getResponse().setContentType(
						"application/xhtml+xml;charset=GB2312");
				getResponse().setHeader("Content-Disposition",
						"attachment; filename= " + downName + ".html");
				JRXhtmlExporter exporter = new JRXhtmlExporter();
				String imageDIR = ServletActionContext.getServletContext()
						.getRealPath("/images/jasperreports/");
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,
						imageDIR);
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, this
						.getBasePath()
						+ "/images/jasperreports/");
				// exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
				// this.getBasePath() + "/servlets/image?image=");

				exporter.setParameter(
						JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
						Boolean.TRUE);
				exporter.setParameter(
						JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,
						Boolean.TRUE);
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
				exporter
						.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);

				exporter.exportReport();
			}
			if (type.equals("pdf")) {
				getResponse().setContentType("application/pdf;charset=GB2312");
				getResponse().setHeader("Content-Disposition",
						"attachment; filename= " + downName + ".pdf");
				JasperExportManager.exportReportToPdfStream(jp, stream);
			}
			if (type.equals("excel")) {
				getResponse().setContentType(
						"application/x-excel;charset=GB2312");
				getResponse().setHeader("Content-Disposition",
						"attachment; filename= " + downName + ".xlsx");

				JRXlsxExporter exporter = new JRXlsxExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
				exporter
						.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
				exporter.setParameter(
						JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.FALSE);
				exporter.exportReport();

			}
			if (type.equals("world")) {

				getResponse().setContentType(
						"application/msword;charset=GB2312");
				getResponse().setHeader("Content-Disposition",
						"attachment; filename= " + downName + ".docx");
				JRDocxExporter exporter = new JRDocxExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jp);
				exporter
						.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
				exporter.exportReport();

			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
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

	public static void main(String[] args) {
		// String jrxmlUrl = LoadConfig.getInstance().getJrxmlUrl();
		// File f = new File(jrxmlUrl);
		// if (!f.exists()) {
		// System.out.println("目录不存在");
		// }
		// String[] fn = f.list();
		// File tf = null;
		// for (String n : fn) {
		// if (n.indexOf(".jrxml") != -1) {
		// tf = new File(jrxmlUrl + File.separator + n);
		// System.out.println(n);
		// }
		// }

		// String f = "AAA.IFRAME";
		// System.out.println(f.endsWith(".IFRAME"));
		// System.out.println(f.lastIndexOf(".IFRAME"));
		//
		// System.out.println(f.substring(0, f.lastIndexOf(".IFRAME")));

	}
}
