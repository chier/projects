package com.cmcc.persistence;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.framework.model.log.OperLogSearch;
import com.cmcc.framework.model.log.OperateLog;
import com.cmcc.framework.persistence.interfaces.log.IOperLogDAO;
import com.cmcc.common.util.date.DateUtil;

public class TestOperLogDAO extends TestBase {
	static IOperLogDAO dao = null;

	@BeforeClass
	public static void initBeforeClass() {
		dao = (IOperLogDAO) getBean("operLogDAO");
	}
	
	/**
	 * 
	 *
	 */
	@Test
	public void findBy(){
		Page page = null;
		OperLogSearch searchCond = new OperLogSearch();
		searchCond.setEid(10011);
		searchCond.setAdminName("admi");
		searchCond.setEndTime("2012-06-09 10:46:59");
		searchCond.setOperateTime("2008-10-03 00:00:00");
		try {
			page = dao.findBy(searchCond, 12);
			page.setSearchCondition(searchCond);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
		
		List<OperateLog> list = dao.findByPage(page);
		
		logger.info(list.size());
	}
}
