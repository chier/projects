package com.cmcc.persistence;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.persistence.interfaces.system.IWorkflowDAO;

/**
 * 对 workflow dao类进行单元测试
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-5-30
 */
public class TestWorkflowDAO extends TestBase {

	static IWorkflowDAO dao = null;

	@BeforeClass
	public static void initBeforeClass() {
		dao = (IWorkflowDAO) getBean("workflowDAO");
	}

	@Test
	public void saveOrUpdate() {
		Workflow workflow = new Workflow();
		workflow.setName("test");
		workflow.setCreatetime(new Date());
		workflow.setDmltime(new Date());
		workflow.setDmlflog(1);
		dao.saveOrUpdate(workflow);
	}
	
	
	
}
