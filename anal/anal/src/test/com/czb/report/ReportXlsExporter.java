package com.czb.report;

import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class ReportXlsExporter implements IReportFileExporter {

	public void export(ReportPrint reportPrint, OutputStream os) throws JRException {
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT,	reportPrint.getJasperPrint());
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
		exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
	            Boolean.FALSE);
	    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
	            Boolean.FALSE);
	    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
	            Boolean.TRUE);
	    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE,
	            Boolean.TRUE);
	    exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES,
	            new String[]{"Sheet1"});
	    exporter.exportReport();
	}

}
