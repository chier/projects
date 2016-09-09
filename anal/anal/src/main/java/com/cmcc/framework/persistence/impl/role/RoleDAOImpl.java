package com.cmcc.framework.persistence.impl.role;

import java.util.ArrayList;
import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.role.Role;
import com.cmcc.framework.model.role.RoleSearcher;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.role.IRoleDAO;

/**
 * 角色dao实现类
 * 
 * @author <a href="mailto:songwei@fetionyy.com.cn">song wei</a>
 * @version $Revision: 1.2 $
 * @since 2009-9-7
 */
public class RoleDAOImpl<T extends Role> extends BaseDAOImpl<Role> implements
		IRoleDAO<Role> {

	public List<Role> findByAll() {
		String hql = "from Role as r where r.dmlflog <> 3 order by roleId desc";
		List<Role> list = this.getHibernate_reader_Interface("", null)
				.find(hql);
		if (list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	public Role findByName(String roleName) {
		String hql = "from Role where roleName = ?";
		List<Role> list = this.getHibernate_reader_Interface("", null).find(
				hql, roleName);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public List<Role> findByPage(Page page) {
		List<Role> list = new ArrayList<Role>();
		RoleSearcher roleSeacher = (RoleSearcher) page.getSearchCondition();
		String hql = "from Role ";
		String roleName = roleSeacher.getRoleName();
		if (roleName != null
				&& !roleName.replaceAll(" ", "").replaceAll("　", "").equals("")) {
			hql = hql
					+ " where ( roleName like ?) order by roleType asc, roleId desc";
			roleName = roleName.replaceAll("%", "\\\\%");
			roleName = roleName.replaceAll("\\\\", "\\\\\\\\");
			list = this.getHibernate_reader_Interface("", null)
					.findByHQLandPage(hql, page, "%" + roleName + "%");
		} else {
			hql = hql + " order by roleId desc";
			list = this.getHibernate_reader_Interface("", null)
					.findByHQLandPage(hql, page);
		}

		return list;

	}

	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		RoleSearcher roleSeacher = (RoleSearcher) searchCond;
		String hql = "select count(r) from Role as r  where  r.dmlflog <> 3 ";
		List<Long> list = new ArrayList<Long>();
		String roleName = roleSeacher.getRoleName();
		if (roleName != null
				&& !roleName.replaceAll(" ", "").replaceAll("　", "").equals("")) {
			roleName = roleName.replaceAll("%", "\\\\%");
			roleName = roleName.replaceAll("\\\\", "\\\\\\\\");
			hql = hql + "  and ( roleName like ? )";
			list = this.getHibernate_reader_Interface("", null).find(hql,
					"%" + roleName + "%");
		} else {
			list = this.getHibernate_reader_Interface("", null).find(hql);
		}
		Long totalCount = (Long) list.get(0);
		return new Page(totalCount, pageSize);
	}

	public List<Role> findBy(SearchCondition search) {
		RoleSearcher roleSearch = (RoleSearcher)search;
		String hql = "from Role as r  where  r.dmlflog <> 3 ";
		List paramList = new ArrayList();
		if(roleSearch.getRoleNotScope() != null){
			hql += " and r.roleScope <> ? ";
			paramList.add(roleSearch.getRoleNotScope());
		}
		
		List<Role> list = this.getHibernate_reader_Interface("", null).find(
				hql, paramList.toArray());
		return list;
	}
}
