package com.cmcc.framework.business.interfaces.workflow;

import java.util.List;

import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.model.WorkflowNode;

/**
 * workflow 业务接口
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-5-31
 */
public interface IWorkflowManager {

	/**
	 * 返回所有工程
	 * 
	 * @return
	 */
	List<Workflow> findAllWorkflow();

	/**
	 * 返回工程所有节点
	 * 
	 * @param wid
	 * @return
	 */
	List<WorkflowNode> findNodeByWid(Integer wid);

	/**
	 * 根据id节点返回所有工程
	 * 
	 * @param nid
	 * @return
	 */
	List[] findDBbyNid(Integer nid);
}
