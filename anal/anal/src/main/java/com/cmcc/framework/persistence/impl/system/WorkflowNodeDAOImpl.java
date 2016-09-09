package com.cmcc.framework.persistence.impl.system;

import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.WorkflowNode;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.system.IWorkflowNodeDAO;

public class WorkflowNodeDAOImpl<T extends WorkflowNode> extends
		BaseDAOImpl<WorkflowNode> implements IWorkflowNodeDAO<WorkflowNode> {

	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WorkflowNode> findByPage(Page pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<WorkflowNode> findByWid(Integer wid) {
		String query = "from WorkflowNode as node where node.dmlFlog<>3 and node.wid = ?";
		List<WorkflowNode> list = this.getHibernate_Anal().createQuery(query,
				wid);
		return list;
	}

}
