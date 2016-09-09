package com.cmcc.framework.business.interfaces.department;

import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.department.Department;

/**
 * 部门业务接口类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-10
 */
public interface IDepartmentManager {

	/**
	 * 根据分页集合信息 返回查询到的数据集
	 * 
	 * @param pageInfo
	 *            分页集合信息
	 * @return 查询到的数据集
	 */
	List<Department> findByPage(Page pageInfo);

	/**
	 * 根据查询条件 返回分页信息
	 * 
	 * @param searchCond
	 *            查询条件集合
	 * @param pageSize
	 *            每页显示数量
	 * @return 分页信息集合
	 */
	Page findBy(SearchCondition searchCond, Integer pageSize);

	/**
	 * 根据 条件集合 返回部门信息
	 * 
	 * @param searchCond
	 * @return
	 */
	List<Department> findBy(SearchCondition searchCond);

	/**
	 * 根据企业id 返回企业信息
	 * 
	 * @param deptId
	 * @return
	 */
	Department findDeptById(Integer deptId);
	
	/**
	 * 更新部门为子部门
	 * @param deptId
	 */
	void updateDepToLeafNode( Integer deptId);
	
	/**
	 * 删除部门 及其所有子部门
	 * @param eid
	 * @param deptId
	 */
	public void deleteDeptAndSonDeptByDepId(List<String> deptId);
	
	/**
	 * 修改或者更新部门
	 * @param dept
	 */
	void saveOrUpdateDept(Department dept);
}
