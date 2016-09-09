package com.cmcc.persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.common.persistence.query.QueryTemplateDTO;
import com.cmcc.common.persistence.uuid.UUID;
import com.cmcc.framework.persistence.interfaces.warning.IWarningDAO;

/**
 * 预警分析 dao 单元测试
 * 
 * @author Administrator
 * 
 */
public class TestWarningDAO extends TestBase {
	static IWarningDAO dao = null;

	@BeforeClass
	public static void initBeforeClass() {
		dao = (IWarningDAO) getBean("warningDAO");
	}

	/**
	 * 保存或者更新
	 * 
	 */
	@Test
	public void saveOrUpdate() {
		QueryTemplateDTO queryTemplateDTO = new QueryTemplateDTO();
		queryTemplateDTO.setTemplateCode(UUID.getUuid());
		
		
		
		dao.saveOrUpdate(queryTemplateDTO);
	}

}
