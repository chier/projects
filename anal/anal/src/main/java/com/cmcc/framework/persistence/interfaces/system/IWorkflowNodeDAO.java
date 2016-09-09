package com.cmcc.framework.persistence.interfaces.system;

import java.util.List;

import com.cmcc.framework.model.WorkflowNode;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

public interface IWorkflowNodeDAO<T extends WorkflowNode> extends
		IBaseDAO<WorkflowNode> {

	/**
	 * 根据 wid 返回节点集合信息
	 * 
	 * @param wid
	 * @return
	 */
	List<WorkflowNode> findByWid(Integer wid);
}
