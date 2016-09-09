package com.cmcc.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import com.czb.report.ReportException;

public class JasperReportUtil {

	public final static String jasperReportPath = "c:\\japsersoft\\";

	/**
	 * 从模板文件编译获得模板对象
	 */
	public JasperReport getJasperReportFromFile(final String reportKey)
			throws IOException, JRException {
		String filePath = jasperReportPath + reportKey + ".jrxml";// 图省事只支持jrxml的
		InputStream jasperFileIS = null;
		JasperReport jasperReport = null;

		try {
			/*
			 * jasperFileIS = this.getClass().getClassLoader()
			 * .getResourceAsStream(filePath);
			 */
			File file = new File(filePath);
			if (!file.isFile()) {
				throw new ReportException("报表文件不存在:" + filePath);
			}
			jasperFileIS = new FileInputStream(file);

			if (jasperFileIS == null) {
				throw new ReportException("报表文件不存在:" + filePath);
			}

			JasperDesign jasperDesign = JRXmlLoader.load(jasperFileIS);
			// 使用JasperReports提供API中的JasperCompileManager类编译*.jrxml文
			// 件，编译后生成一个*.jasper文件。

			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		} finally {
			if (jasperFileIS != null) {
				jasperFileIS.close();
			}
		}
		return jasperReport;
	}
}
