package com.cmcc.persistence;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.model.model.WebModel;
import com.cmcc.framework.persistence.interfaces.model.IWebModelDAO;

/**
 * 单元测试 model 模块
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public class TestWebModelDAO extends TestBase {
	static IWebModelDAO dao = null;

	@BeforeClass
	public static void initBeforeClass() {
		dao = (IWebModelDAO) getBean("webModelDAO");
	}

	@SuppressWarnings("unchecked")
	@Test
	public void findByAll() {
		List<WebModel> list = dao.findByAll();
		for (WebModel model : list) {
			logger.info(model.getModelName());
		}
	}
}
