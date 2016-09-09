package com.cmcc.framework.persistence.impl.role;

import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.role.RoleWorkflow;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.role.IRoleWorkflowDAO;

/**
 * DAO 操作映射类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-11
 * @param <T>
 */
public class RoleWorkflowDAOImpl<T extends RoleWorkflow> extends
		BaseDAOImpl<RoleWorkflow> implements IRoleWorkflowDAO<RoleWorkflow> {

	public List<Integer> findWidByRid(Integer rid) {
		List<Integer> list;
		String hql = "select wid from RoleWorkflow where rid = ?";
		list = this.getHibernate_Anal().createQuery(hql, rid);
		return list;
	}

	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RoleWorkflow> findByPage(Page pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	public int deleteByRid(Integer rid) {
		String hql = "delete from  RoleWorkflow where rid = ?";
		return this.getHibernate_Anal().hqlUpdate(hql, rid);
	}

}
