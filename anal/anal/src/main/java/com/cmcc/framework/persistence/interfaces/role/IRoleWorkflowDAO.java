package com.cmcc.framework.persistence.interfaces.role;

import java.util.List;

import com.cmcc.framework.model.role.RoleWorkflow;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

/**
 * 角色与项目关系 DAO接口
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-11
 */
public interface IRoleWorkflowDAO<T extends RoleWorkflow> extends
		IBaseDAO<RoleWorkflow> {

	/**
	 * 根据角色id 返回 wid
	 * 
	 * @param rid
	 * @return
	 */
	List<Integer> findWidByRid(Integer rid);
	
	/**
	 * 根据角色 id 删除所有关系
	 * @param rid
	 * @return
	 */
	int deleteByRid(Integer rid);
}
