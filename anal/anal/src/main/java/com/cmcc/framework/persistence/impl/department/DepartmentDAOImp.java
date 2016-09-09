package com.cmcc.framework.persistence.impl.department;

import java.util.ArrayList;
import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.model.department.DepartmentSearcher;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.department.IDepartmentDAO;

/**
 * 部门 DAO实现类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-10
 * @param <T>
 */
public class DepartmentDAOImp<T extends Department> extends
		BaseDAOImpl<Department> implements IDepartmentDAO<Department> {

	@SuppressWarnings("unchecked")
	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		List paramList = new ArrayList();
		DepartmentSearcher deptSearchCond = (DepartmentSearcher) searchCond;
		StringBuilder hqlStr = new StringBuilder(
				"select count(d) from Department as d where d.dmlflog <> 3 ");

		// 父级部门节点判断
		if (deptSearchCond.getParentid() != null) {
			hqlStr.append(" and d.parentId = ? ");
			paramList.add(deptSearchCond.getParentid());
		}

		// 名称
		if (!StringUtil.isBlank(deptSearchCond.getName())) {
			hqlStr.append(" and d.name like ? ");
			paramList.add("%" + deptSearchCond.getName() + "%");
		}

		List r = this.getHibernate_reader_Interface("gweb", null).createQuery(
				hqlStr.toString(), paramList.toArray());
		long totalresult = 0;
		if (r != null && r.size() > 0) {
			totalresult = (Long) r.get(0);
		}
		if (totalresult < 1) {
			totalresult = 0;
		}
		return new Page(totalresult, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List findByPage(Page pageInfo) {
		DepartmentSearcher deptSearchCond = (DepartmentSearcher) pageInfo
				.getSearchCondition();
		List paramList = new ArrayList();
		StringBuilder hqlStr = new StringBuilder(
				"from Department as d where d.dmlflog <> 3 ");

		// 父级部门节点判断
		if (deptSearchCond.getParentid() != null) {
			hqlStr.append(" and d.parentId = ? ");
			paramList.add(deptSearchCond.getParentid());
		}

		// 名称
		if (!StringUtil.isBlank(deptSearchCond.getName())) {
			hqlStr.append(" and d.name like ?");
			paramList.add("%" + deptSearchCond.getName() + "%");
		}

		List returnList = this.getHibernate_reader_Interface("gweb", null)
				.findByHQLandPage(hqlStr.toString(), pageInfo,
						paramList.toArray());

		if (returnList != null && returnList.size() > 0) {
			return returnList;
		} else {
			return new ArrayList();
		}
	}

	public List<Department> findByAll() {
		String hql = "from Department";
		List<Department> list = this.getHibernate_Anal().createQuery(hql,
				null);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	public List<Department> findBy(SearchCondition searchCond) {
		List paramList = new ArrayList();
		DepartmentSearcher deptSearchCond = (DepartmentSearcher) searchCond;
		StringBuilder hqlStr = new StringBuilder(
				" from Department as d where d.dmlflog <> 3 ");

		// 父级部门节点判断
		if (deptSearchCond.getParentid() != null) {
			hqlStr.append(" and d.parentId = ? ");
			paramList.add(deptSearchCond.getParentid());
		}

		// 名称
		if (!StringUtil.isBlank(deptSearchCond.getName())) {
			hqlStr.append(" and d.name like ? ");
			paramList.add("%" + deptSearchCond.getName() + "%");
		}

		// 根据id集合 返回信息
		if (deptSearchCond.getIds() != null
				&& deptSearchCond.getIds().length > 0) {
			String[] ids = deptSearchCond.getIds();
			if (ids != null && ids.length > 0) {
				if (!StringUtil.isBlank(ids[0])) {
					hqlStr.append(" and  d.deptId in(");
					for (int i = 0; i < ids.length; i++) {
						hqlStr.append(ids[i]);
						if (i < ids.length - 1)
							hqlStr.append(",");
					}
					hqlStr.append(")");
				}
			}
		}

		List<Department> list = this.getHibernate_Anal().createQuery(
				hqlStr.toString(), paramList.toArray());
		if (list != null && list.size() > 0) {
			return list;
		}
		return new ArrayList();
	}

	public void deleteByIds(List<String> deptId) {
		if (deptId.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < deptId.size(); i++) {
				if (i == deptId.size() - 1) {
					sb.append(deptId.get(i));
				} else {
					sb.append(deptId.get(i) + ",");
				}
			}
			String hql = "update Department as d  set d.dmlflog = 3 , d.dmltime = now() where deptid in ("
					+ sb.toString() + ")";
			this.getHibernate_Anal().hqlUpdate(hql);
		}
	}

}
