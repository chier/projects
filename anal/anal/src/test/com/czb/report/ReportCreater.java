package com.czb.report;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReportCreater {
	private static final Log logger = LogFactory.getLog(ReportCreater.class);
	private String jasperReportPath = "C:\\japsersoft\\";// 报表的模板文件存放路径（相对classpath，通过spring注入）
	/**
	 * jasperDesignMap作为一个缓存来存储编译后的JasperReport模板
	 */
	private Map<String, JasperReport> jasperDesignMap = new ConcurrentHashMap<String, JasperReport>();

	public void resetJasperDesignCache() {
		jasperDesignMap.clear();
	}

	/**
	 * controller调用该方法来产生ReportPrint对象
	 */
	public ReportPrint createReport(final String reportKey, final ResultSet rs,
			Map<String, Object> reportParams) throws ReportException {
		try {
			return _createReport(reportKey, rs, reportParams);
		} catch (JRException e) {
			logger.error(null, e);
			throw new ReportException("产生报表出错" + reportKey);
		}
	}

	private ReportPrint _createReport(final String reportKey,
			final ResultSet rs, Map<String, Object> reportParams)
			throws ReportException, JRException {
		JasperReport jasperReport = getJasperReport(reportKey);
		ReportPrint reportPrint = new ReportPrint();
		JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(
				rs);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				reportParams, resultSetDataSource);
		reportPrint.setJasperPrint(jasperPrint);

		return reportPrint;
	}

	private JasperReport getJasperReport(final String reportKey) {
		try {
			return _getJasperReport(reportKey);
		} catch (IOException e) {
			logger.error(null, e);
			throw new ReportException("关闭文件流异常:" + reportKey);
		} catch (JRException e) {
			logger.error(null, e);
			throw new ReportException("产生报表异常:" + reportKey);
		}
	}

	private JasperReport _getJasperReport(final String reportKey)
			throws IOException, JRException {
		JasperReport jasperReport = null;
		if (jasperDesignMap.containsKey(reportKey)) {
			jasperReport = jasperDesignMap.get(reportKey);
		} else {
			jasperReport = getJasperReportFromFile(reportKey);
			jasperDesignMap.put(reportKey, jasperReport);
		}

		return jasperReport;
	}

	/**
	 * 从模板文件编译获得模板对象
	 */
	private JasperReport getJasperReportFromFile(final String reportKey)
			throws IOException, JRException {
		String filePath = jasperReportPath + reportKey + ".jrxml";// 图省事只支持jrxml的
		InputStream jasperFileIS = null;
		JasperReport jasperReport = null;

		try {
			jasperFileIS = this.getClass().getClassLoader()
					.getResourceAsStream(filePath);
			if (jasperFileIS == null) {
				File file = new File(filePath);
				if (!file.isFile()) {
					throw new ReportException("报表文件不存在:" + filePath);
				}
				jasperFileIS = new FileInputStream(file);
			}

			JasperDesign jasperDesign = JRXmlLoader.load(jasperFileIS);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		} finally {
			if (jasperFileIS != null) {
				jasperFileIS.close();
			}
		}

		return jasperReport;
	}

	public String getJasperReportPath() {
		return jasperReportPath;
	}

	public void setJasperReportPath(String jasperReportPath) {
		this.jasperReportPath = jasperReportPath;
	}

	public static void main(String[] argv) {

	}

}
