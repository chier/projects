package com.cmcc.framework.persistence.impl.system;

import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.system.IWorkflowDAO;

public class WorkflowDAOImpl<T extends Workflow> extends BaseDAOImpl<Workflow>
		implements IWorkflowDAO<Workflow> {

	public List<Workflow> findByAll() {
		String hql = "from Workflow";
		return getHibernate_Anal().find(hql, null);
	}

	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		return null;
	}

	public List<Workflow> findByPage(Page pageInfo) {
		return null;
	}

	public List<Workflow> findByIds(List<Integer> wids) {
		if (wids == null && wids.size() == 0) {
			return null;
		}
		StringBuilder hql = new StringBuilder("from Workflow where id in ( ");
		int size = wids.size();
		for (int i = 0; i < size; i++) {
			hql.append(wids.get(i));
			if (i != size - 1) {
				hql.append(",");
			}
		}
		hql.append(" ) ");

		return getHibernate_Anal().find(hql.toString(), null);
	}
}
