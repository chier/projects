package com.cmcc.framework.business.impl.workflow;

import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.cmcc.common.util.NodeUtil;
import com.cmcc.framework.business.interfaces.workflow.IWorkflowManager;
import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.model.WorkflowNode;
import com.cmcc.framework.persistence.interfaces.system.IWorkflowDAO;
import com.cmcc.framework.persistence.interfaces.system.IWorkflowNodeDAO;

/**
 * workflow 业务实现类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-5-31
 */
public class WorkflowManagerImpl implements IWorkflowManager {

	private IWorkflowDAO<Workflow> workflowDAO;

	private IWorkflowNodeDAO<WorkflowNode> workflowNodeDAO;

	public void setWorkflowDAO(IWorkflowDAO<Workflow> workflowDAO) {
		this.workflowDAO = workflowDAO;
	}

	public IWorkflowNodeDAO<WorkflowNode> getWorkflowNodeDAO() {
		return workflowNodeDAO;
	}

	public void setWorkflowNodeDAO(
			IWorkflowNodeDAO<WorkflowNode> workflowNodeDAO) {
		this.workflowNodeDAO = workflowNodeDAO;
	}

	public List<Workflow> findAllWorkflow() {
		return workflowDAO.findByAll();
	}

	public List<WorkflowNode> findNodeByWid(Integer wid) {
		return this.workflowNodeDAO.findByWid(wid);
	}

	@SuppressWarnings("unchecked")
	public List[] findDBbyNid(Integer nid) {
		WorkflowNode node = this.workflowNodeDAO.findById(nid);
		Map<String, String> map = NodeUtil.selectXml(node.getNodeSetting());
		return NodeUtil.findDB(map);
	}

}
