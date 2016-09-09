package com.cmcc.jrxml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.junit.Assert;
import org.junit.Test;

import com.cmcc.report.JasperReportUtil;
//import com.cmcc.util.DBConnection;
import com.lowagie.text.pdf.PdfWriter;

/*
 * 简单测试例子
 * @author zhangzhanliang
 * @file com.cmcc.jrxml --- JasperSimpleTest.java
 * @version 2013-2-22 -下午06:19:08
 */

public class JasperSimpleTest {
	String fm = "P236";
	String fn = "c:\\japsersoft\\";
	String fileName = fn + fm + ".jrxml";

	@Test
	public void test1() throws JRException {
		try {
			// 1.开发人员开发报表设计文件，也就是定义一个.jrxml文件（就是最开始的那个XML
			// 文件）。

			File sourceFile = new File(fileName);
			JasperDesign jd = JRXmlLoader.load(sourceFile);// jrxml文件
			// 2. 使用JasperReports提供API中的JasperCompileManager类编译.jrxml文
			// 件，编译后生成一个.jasper文件。
			JasperReport jr = JasperCompileManager.compileReport(jd);
			// 3.使用JasperReports提供API中的JasperFillManager类填充编译后的.jasper
			// 文件，填充后生成一个.jrprint文件。
//			JasperPrint jp = JasperFillManager.fillReport(jr, null,
//					DBConnection.getInstance());
			// 4.使用导出管理器（JasperExportManager）或 者各种格式的文件导出器
			// （JRXxxExporter）将.jrprit文件导出成各种格式的报表文件。
//			JasperExportManager.exportReportToPdfFile(jp, fn + fm + ".pdf");
//			JasperExportManager.exportReportToHtmlFile(jp, fn + fm + ".html");
			// 5.使用 JRViewer 工具类来直接浏览报表。

		} catch (JRException e) {
			e.printStackTrace();
			throw e;
		} 
		System.out.println("**************over**********");
	}

	public void test4() throws JRException {
		File sourceFile = new File(fileName);
		JasperPrint jasperPrint = (JasperPrint) JRLoader.loadObject(sourceFile);// jasper

		File destFile = new File(sourceFile.getParent(), jasperPrint.getName()
				+ ".pdf");

		JRPdfExporter exporter = new JRPdfExporter();

		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, destFile);
		exporter.setParameter(JRPdfExporterParameter.IS_ENCRYPTED, Boolean.TRUE);
		exporter.setParameter(JRPdfExporterParameter.IS_128_BIT_KEY,
				Boolean.TRUE);
		exporter.setParameter(JRPdfExporterParameter.USER_PASSWORD, "jasper");
		exporter.setParameter(JRPdfExporterParameter.OWNER_PASSWORD, "reports");
		exporter.setParameter(JRPdfExporterParameter.PERMISSIONS, new Integer(
				PdfWriter.AllowCopy | PdfWriter.AllowPrinting));

		try {
			exporter.exportReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void test2() throws FileNotFoundException {
		try {

			String fileName = "c:\\report1.jrxml";
			File sourceFile = new File(fileName);

			// - Chargement et compilation du rapport
			JasperDesign jasperDesign = JRXmlLoader.load(sourceFile);
			JasperReport jasperReport = JasperCompileManager // ligne 316
					.compileReport(jasperDesign);

			// - Paramètres à envoyer au rapport

			// - Execution du rapport
			// JasperPrint jasperPrint = JasperFillManager.fillReport(
			// jasperReport, parameters, new JREmptyDataSource());
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					new FileInputStream(sourceFile), null,
					new net.sf.jasperreports.engine.JREmptyDataSource());

			// - Création du rapport au format PDF
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					"C:\\Devis_.pdf");

		} catch (JRException e) {

			e.printStackTrace();
		}
	}

	// @Test
	public void test3() throws Exception {
		JasperReport jasperReport = new JasperReportUtil()
				.getJasperReportFromFile("report1");
		Assert.assertNotNull("jasperReport is null", jasperReport);
		System.out.println(jasperReport);
	}

}
