package com.cmcc.framework.business.interfaces.rawdata;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;

import com.cmcc.common.exception.BusinessException;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 原始数据 业务操作类
 * 
 * @author zhangzhanliang
 * @file com.cmcc.framework.business.interfaces.rawdata --- IRawDataManager.java
 * @version 2013-2-25 -下午6:17:47
 */
public interface IRawDataManager {

	/**
	 * .开发人员开发报表设计文件，也就是定义一个.jrxml文件（就是最开始的那个XML 文件）。
	 * 
	 * @param file
	 * @return
	 * @throws JRException
	 */
	JasperDesign getJasperDesign(File file) throws JRException;

	/**
	 * 使用JasperReports提供API中的JasperCompileManager类编译.jrxml文 件，编译后生成一个.jasper文件。
	 * 
	 * @param jd
	 * @return
	 */
	JasperReport getJasperReport(JasperDesign jd) throws JRException;

	/**
	 * 使用JasperReports提供API中的JasperFillManager类填充编译后的.jasper
	 * 文件，填充后生成一个.jrprint文件。
	 * 
	 * @param jr
	 * @return
	 */
	JasperPrint getJasperPrint(JasperReport jr) throws JRException,
			SQLException;

	/**
	 * 使用JasperReports提供API中的JasperFillManager类填充编译后的.jasper
	 * 文件，填充后生成一个.jrprint文件。
	 * 
	 * @param jr
	 * @return
	 */
	JasperPrint getJasperPrint(JasperReport jr, Map<String, Object> parameters)
			throws JRException, SQLException;

	ComboPooledDataSource getDataSource();

	void setDataSource(ComboPooledDataSource dataSource);

	/**
	 * 返回数据库的 sql
	 * 
	 * @return
	 */
	Connection getConnection() throws SQLException;

	/**
	 * 导出html file
	 * 
	 * @param jrxmlFile
	 *            jrxml 文件
	 * @param path
	 *            存储的 path 路径
	 * @param fileName
	 *            文件名
	 * @throws BusinessException
	 */
	void exportHtmlFile(File jrxmlFile, String path, String fileName)
			throws SQLException, JRException;
}
