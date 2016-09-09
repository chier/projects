package com.cmcc.framework.business.impl.rawdata;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRQuery;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.framework.business.interfaces.rawdata.IRawDataManager;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.*;

/**
 * 原始数据 业务实现类
 * 
 * @author zhangzhanliang
 * @file com.cmcc.framework.business.impl.rawdata --- RawDataManagerImpl.java
 * @version 2013-2-25 -下午6:19:44
 */
public class RawDataManagerImpl implements IRawDataManager {

	private Logger logger = Logger.getLogger(RawDataManagerImpl.class);
	private ComboPooledDataSource dataSource;

	public ComboPooledDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(ComboPooledDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public JasperDesign getJasperDesign(File file) throws JRException {
		return JRXmlLoader.load(file);// jrxml文件
	}

	public JasperReport getJasperReport(JasperDesign jd) throws JRException {
		return JasperCompileManager.compileReport(jd);
	}

	public JasperPrint getJasperPrint(JasperReport jr) throws JRException,
			SQLException {
		return getJasperPrint(jr, null);
	}

	public Connection getConnection() throws SQLException {
		return this.dataSource.getConnection();
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

	public void exportHtmlFile(File jrxmlFile, String path, String fileName)
			throws SQLException, JRException {
		Connection conn = null;
		try {
			conn = this.getConnection();
			// 1.开发人员开发报表设计文件，也就是定义一个.jrxml文件（就是最开始的那个XML
			// 文件）。
			JasperDesign jd = JRXmlLoader.load(jrxmlFile);// jrxml文件
			// 2. 使用JasperReports提供API中的JasperCompileManager类编译.jrxml文
			// 件，编译后生成一个.jasper文件。
			JasperReport jr = JasperCompileManager.compileReport(jd);
			JRQuery query = jr.getQuery();
			logger.debug(query.getText());
			// 3.使用JasperReports提供API中的JasperFillManager类填充编译后的.jasper
			// 文件，填充后生成一个.jrprint文件。
			JasperPrint jp = JasperFillManager.fillReport(jr, null, conn);

			// 4.使用导出管理器（JasperExportManager）或 者各种格式的文件导出器
			// （JRXxxExporter）将.jrprit文件导出成各种格式的报表文件。
			JasperExportManager.exportReportToHtmlFile(jp, path + fileName
					+ ".html");
			// 5.使用 JRViewer 工具类来直接浏览报表。

		} catch (JRException e) {
			logger.error(e);
			throw e;
		} catch (SQLException e) {
			logger.error(e);
			throw e;
		} finally {
			releaseConnection(conn);
		}
	}

	private void releaseConnection(Connection conn) throws SQLException {
		if (conn != null) {
			if (!conn.isClosed()) {
				conn.close();
			}
			conn = null;
		}
	}

}
