package com.cmcc.framework.persistence.interfaces.system;

import java.util.List;

import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;


/**
 * 工作流 workflow 对象 dao 接口
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-5-30
 */
public interface IWorkflowDAO<T extends Workflow> extends IBaseDAO<Workflow> {

	/**
	 * 
	 * @return
	 */
	List<Workflow> findByAll();
	
	/**
	 * 根据id集合返回所有项目
	 * @param wids
	 * @return
	 */
	List<Workflow> findByIds(List<Integer> wids);
	
}
