package com.czb.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.jasperreports.engine.JRException;

import org.junit.Assert;
import org.junit.Test;

/**
 * 简单测试
 * 
 * @author zhangzhanliang
 * 
 */
public class SimpleTest {
	private String name = "report1";

	@Test
	public void test1() {
		String s = "c:\\a.pdf";
		File f = new File(s);
		if (!f.isFile()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Assert.assertNotNull(s + " is null", f);
		IReportFileExporter irfe = new ReportPdfExporter();
		try {
			OutputStream os = new FileOutputStream(f);
			ReportCreater rc = new ReportCreater();
			ReportPrint rp = rc.createReport(name, null, null);
			Assert.assertNotNull("rp is null",rp);
			irfe.export(rp, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
}
