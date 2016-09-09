package com.cmcc.business;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.business.interfaces.model.IWebModelManager;
import com.cmcc.framework.model.model.WebModel;

/**
 * webmodel manager 单元测试类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public class TestWebModelManager extends TestBase {
	static IWebModelManager webModelManager = null;

	@BeforeClass
	public static void initBeforeClass() {
		webModelManager = (IWebModelManager) getBean("webModelManager");
	}

	@Test
	public void findByAll() {
		List<WebModel> list = webModelManager.findByAll();
		for(WebModel model : list){
			logger.info(model.getModelName());
		}
	}
}
