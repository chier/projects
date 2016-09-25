package com.cmcc.anal.file;
import java.io.File;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.cmcc.anal.TestBase;
import com.cmcc.anal.common.persistence.generic.interfaces.IGenericDAO;
public class JsperFileSaveDB extends TestBase{
	private static Logger log = Logger.getLogger(JsperFileSaveDB.class);
	
	@Test
	public void queryFile(){
		String filePath = "F:\\projects\\Environmental\\2016-08-27\\page";
		File file = new File(filePath);
		if(!file.exists()){
			log.error("file not find");
			return;
		}
		for(File subFile : file.listFiles()){
			log.info("subFile name = " + subFile.getName());
			if("2013年iDataFax采集".equals(subFile.getName())){
				datafax2013(subFile);
			}
//			if("2014年补充数据-iDataFax采集".equals(subFile.getName())){
//				datafax2014(subFile);
//			}
		}
	}
	
	private void datafax2013(File file){
		// jrfilepath 字段
		String jrfilepath = "\\\\" + file.getName();
		// 节点名称字段
		String node_name;
		Integer pid = 0;
		Integer node_type = 1;
		IGenericDAO dao = (IGenericDAO) getBean("webAdminDAO");
		log.info(jrfilepath);
		String dbfilepath = null;
		for(File subFile : file.listFiles()){
			node_name = getFileName(subFile.getName());
			dbfilepath = jrfilepath + "\\\\" + subFile.getName(); 
			String sql = "INSERT INTO jrxmlinfo (pid,node_name,node_type,jrfilepath) " +
					"VALUE("+pid+",'"+node_name+"',"+node_type+",'"+dbfilepath+"')";
			log.info("sql = [" +sql + "]");
			int id = dao.getHibernate_Anal().sqlUpdate(sql);
			log.info("id="+id);
		}
	}
	
	
	private void datafax2014(File file){
		// jrfilepath 字段
		String jrfilepath = "\\\\" + file.getName();
		// 节点名称字段
		String node_name;
		Integer pid = 0;
		Integer node_type = 1;
		IGenericDAO dao = (IGenericDAO) getBean("webAdminDAO");
		log.info(jrfilepath);
		String parJrfilePath = null;
		String dbfilepath = null;
		for(File subFile : file.listFiles()){
			parJrfilePath = jrfilepath + "\\\\" + subFile.getName(); 
			for(File ssFile : subFile.listFiles()){
				node_name = getFileName(ssFile.getName());
				dbfilepath = parJrfilePath + "\\\\" + ssFile.getName(); 
				String sql = "INSERT INTO jrxmlinfo (pid,node_name,node_type,jrfilepath) " +
						"VALUE("+pid+",'"+node_name+"',"+node_type+",'"+dbfilepath+"')";
				log.info("sql = [" +sql + "]");
				int id = dao.getHibernate_Anal().sqlUpdate(sql);
				log.info("id="+id);
			}
		}
	}
	
	public String getFileName(String fullname) {
		int pos = fullname.lastIndexOf(".");
		if (pos > 0) {
			return fullname.substring(0, pos);
		} else {
			return fullname;
		}

	}
}

















