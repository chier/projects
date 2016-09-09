package com.cmcc.framework.persistence.interfaces.department;

import java.util.List;

import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

/**
 * 部门 dao 接口
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-10
 * @param <T>
 */
public interface IDepartmentDAO<T extends Department> extends
		IBaseDAO<Department> {

	/**
	 * 返回所有部门
	 * 
	 * @return
	 */
	List<Department> findByAll();

	/**
	 * 根据 条件集合 返回部门信息
	 * 
	 * @param searchCond
	 * @return
	 */
	List<Department> findBy(SearchCondition searchCond);
	/**
	 * 根据 id 删除部门信息
	 * @param deptId
	 */
	void deleteByIds(List<String> deptId);
}
