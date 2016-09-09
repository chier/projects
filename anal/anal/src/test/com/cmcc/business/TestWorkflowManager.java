package com.cmcc.business;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import com.cmcc.TestBase;
import com.cmcc.framework.business.interfaces.workflow.IWorkflowManager;
import com.cmcc.framework.model.Workflow;

/**
 * workflow 业务类测试
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-5-31
 */
public class TestWorkflowManager extends TestBase {
	static IWorkflowManager manager;

	@BeforeClass
	public static void initBeforeClass() {
		manager = (IWorkflowManager) getBean("workflowManager");
	}
	
	@Test
	public void findWorkflowAll(){
		List<Workflow> list = manager.findAllWorkflow();
		Assert.assertNotNull(list);
		logger.info(list.size());
		for(Workflow w : list){
			logger.info(w.getName());
		}
	}

}
