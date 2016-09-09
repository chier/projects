package com.cmcc.framework.persistence.impl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.cmcc.common.util.StringUtil;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.admin.WebAdminSearch;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.admin.IWebAdminDAO;

/**
 * webadmin 管理员 DAO实现类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 * @param <T>
 */
public class WebAdminDAOImpl<T extends WebAdmin> extends BaseDAOImpl<WebAdmin>
		implements IWebAdminDAO<WebAdmin> {

	@SuppressWarnings("unchecked")
	public WebAdmin findByAdminName(String adminName) {
		String hql = "from WebAdmin as admin where admin.admin = ? and admin.dmlflog <> 3";
		List<WebAdmin> list = this.getHibernate_Anal().createQuery(hql,
				adminName);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据id 查找对象
	 * 
	 * @param id
	 * @return
	 */
	public WebAdmin getById(Integer id) {
		WebAdmin admin = this.getHibernate_reader_Interface("gweb", null).get(
				id);
		if (admin != null) {
			return admin;
		} else {
			return null;
		}
	}

	/**
	 * 根据查询对象返回查询结果List
	 * 
	 * @param searchCond
	 * @param p
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WebAdmin> findByPage(Page p) {
		WebAdminSearch search = (WebAdminSearch) p.getSearchCondition();
		int index = 0;
		index = p.getFirstItemPos();
		if (index <= 0) {
			index = 0;
		}
		List paramList = new ArrayList();
		String hqlStr = "from WebAdmin c where c.level=1 and c.dmlflog <> 3";
		if (search != null && !StringUtil.isBlank(search.getAdminName())) {
			hqlStr += " and c.admin = ?";
			paramList.add(search.getAdminName());
		}
		if (search != null && search.getDeptId() != null) {
			hqlStr += " and c.deptId = ?";
			paramList.add(search.getDeptId());
		}
		hqlStr += "order by c.identifier desc";

		List returnList = this.getHibernate_reader_Interface("gweb", null)
				.findByHQLandPage(hqlStr, p, paramList.toArray());

		if (returnList != null && returnList.size() > 0) {
			return returnList;
		} else {
			return null;
		}

	}

	/**
	 * 根据查询条件返回page对象
	 * 
	 * @param pagesize
	 * @param searchCond
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		WebAdminSearch search = (WebAdminSearch) searchCond;

		List paramList = new ArrayList();
		String hqlStr = "select count(c) from WebAdmin c where c.level=1 and c.dmlflog <> 3";
		if (search != null && !StringUtil.isBlank(search.getAdminName())) {
			hqlStr += " and c.admin = ?";
			paramList.add(search.getAdminName());
		}
		if (search != null && search.getDeptId() != null) {
			hqlStr += " and c.deptId = ?";
			paramList.add(search.getDeptId());
		}
		List r = this.getHibernate_reader_Interface("gweb", null).createQuery(
				hqlStr, paramList.toArray());
		long totalresult = 0;
		if (r != null && r.size() > 0) {
			totalresult = (Long) r.get(0);
		}
		if (totalresult < 1) {
			totalresult = 0;
		}

		return new Page(totalresult, pageSize);
	}

	/**
	 * 根据查询条件返回模糊查询
	 * 
	 * @param searchCond
	 * @param page
	 * @return
	 */
	public List<WebAdmin> getAllLikeBySearchListPage(String name, Integer eid,
			Page p) {
		String hqlStr = "from WebAdmin c where c.admin like ? and c.eid=? and c.level=1 and status=1 order by c.identifier desc";
		List<WebAdmin> returnList = this.getHibernate_reader_Interface("gweb",
				null).findByHQLandPage(hqlStr, p,
				new Object[] { "%" + name + "%", eid });
		return returnList;
	}

	/**
	 * 根据查询条件返回Page对象
	 * 
	 * @param pagesize
	 * @param searchCond
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page getPageLikeBySearchList(int pagesize, String name, Integer eid) {

		String hqlStr = "select count(c) from WebAdmin c where c.admin like ? and c.eid=?  and c.level=1 order by c.identifier desc";

		List r = this.getHibernate_reader_Interface("gweb", null).createQuery(
				hqlStr, "%" + name + "%", eid);
		long totalresult = 0;
		if (r != null && r.size() > 0) {
			totalresult = (Long) r.get(0);
		}
		if (totalresult < 1) {
			totalresult = 0;
		}

		return new Page(totalresult, pagesize);
	}

	/**
	 * 根据输入的名字，判断数据库中同一个企业下是否存在同名
	 * 
	 * @param name
	 * @param eid
	 * @return true 表示没有同名 false 表示有同名
	 */
	@SuppressWarnings("unchecked")
	public boolean getSameName(String name, Integer adminid) {
		String hql = "select a.identifier from WebAdmin as a where a.admin =? and a.identifier <> ? and a.dmlflog <> 3  order by a.identifier desc";
		Object[] values = new Object[2];
		values[0] = name;
		values[1] = adminid;
		List list = this.getHibernate_reader_Interface("gweb", null).find(hql,
				values);

		if (list == null) {
			return true;
		}

		if (list.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 根据企业代码和用户名获得唯一用户
	 * 
	 * @param name
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public WebAdmin getAdminByLoginId(String name, String code) {
		String hql = "from WebAdmin as a where a.admin=? and a.code=? and a.status=1 order by a.count asc";
		Object[] values = new Object[2];
		values[0] = name;
		values[1] = code;
		List<WebAdmin> admin = this.getHibernate_reader_Interface("gweb", null)
				.find(hql, values);
		if (admin != null && admin.size() > 0) {
			return admin.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据eid ,code 查找企业联系人手机号
	 * 
	 * @param eid
	 * @param code
	 * @return
	 */
	public Long getPhotoByEid(Integer eid, String code) {
		String sql = "select c.contact_mp from gweb_corp_info c where c.eid="
				+ eid + " and c.corp_code='" + code + "'";
		List list = this.getHibernate_reader_Interface("gweb", null)
				.createSQLQuery(sql);
		if (list != null && list.size() > 0) {
			Object obj = (Object) list.get(0);
			return Long.valueOf(String.valueOf(obj));
		} else {
			return null;
		}
	}

	/**
	 * 根据企业code查询企业状态
	 * 
	 * @param code
	 * @param simpleName
	 * @return
	 */
	public Short getCorpStaticByCode(String code, String simpleName) {
		String sql = "select c.order_flag,c.short_name from gweb_corp_info c where c.corp_code='"
				+ code + "'";
		List list = this.getHibernate_reader_Interface("gweb", null)
				.createSQLQuery(sql);
		if (list != null && list.size() > 0) {
			Object[] obj = (Object[]) list.get(0);
			Short a = Short.valueOf(String.valueOf(obj[0]));
			String[] t = new String[3];
			if (simpleName != null && simpleName.equals(String.valueOf(obj[1]))) {
				return a;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * modefiy yangshuo 2009-9-14 根据企业简称判断在企业表中是否存在
	 * 
	 * @param simpleName
	 * @return
	 */
	public List haveSimpleNameList(String simpleName) {
		String sql = "select c.createtime,c.demo_flag,c.calling_code, c.short_name,c.eid,c.order_flag "
				+ "from gweb_corp_info c where c.short_name=? and c.order_flag=1 ";

		List list = this.getHibernate_reader_Interface("gweb", null)
				.createSQLQuery(sql, new Object[] { simpleName });
		return list;
	}

	/**
	 * 根据企业简称判断在企业表中是否存在
	 * 
	 * @param simpleName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List IsHaveSimpleName(String simpleName) {
		String sql = "select c.createtime,c.demo_flag,c.calling_code, c.version_id from gweb_corp_info c where c.short_name='"
				+ simpleName + "'";
		List list = this.getHibernate_reader_Interface("gweb", null)
				.createSQLQuery(sql);
		// if (list != null && list.size() > 0 ) {
		// System.out.println(list.size());
		// return true;
		// } else {
		// return false;
		// }
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getGWebModel(String name) {
		String sql = "select c.model_aiid,c.model_action_url,c.model_parent_id from gweb_model c "
				+ "where c.model_aiid=(select min( model_aiid)  "
				+ "from  gweb_model where model_name ='" + name + "')";
		List list = this.getHibernate_reader_Interface("gweb", null)
				.createSQLQuery(sql);
		return list;
	}

	// modified by yangshuo 2009-06-24 15:42
	/**
	 * 根据eid查询出已绑定的员工
	 * 
	 * @param eid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WebAdmin> getBindEmployees(String eid) {
		String hql = "from WebAdmin as wa where wa.eid=" + Integer.valueOf(eid)
				+ " and wa.adminUserid<>0 and wa.status=1";
		List<WebAdmin> list = this.getHibernate_reader_Interface("gweb", null)
				.find(hql);
		return list;
	}

	/**
	 * 查询出超管的信息
	 * 
	 * @return
	 */
	public List<WebAdmin> getSuperWebAdmin(Integer eid, Integer level) {
		String hql = "from WebAdmin as wa where wa.eid=? and wa.level=? and wa.status=1";
		Object[] values = new Object[2];
		values[0] = eid;
		values[1] = level;
		List find = this.getHibernate_reader_Interface("gweb", null).find(hql,
				values);
		List<WebAdmin> admin = find;
		if (admin != null && admin.size() > 0) {
			return admin;
		} else {
			return null;
		}
	}

	/**
	 * 根据eid和userid查询出管理员信息
	 * 
	 * @return
	 */
	public WebAdmin getWebAdminById(Integer eid, Integer userid) {
		String hql = "from WebAdmin as wa where wa.eid=? and wa.adminUserid=? and wa.status=1";
		Object[] values = new Object[2];
		values[0] = eid;
		values[1] = userid;
		List find = this.getHibernate_reader_Interface("gweb", null).find(hql,
				values);
		List<WebAdmin> admin = find;
		if (admin != null && admin.size() > 0) {
			return admin.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据eid和mp查询出管理员信息
	 * 
	 * @return
	 */
	public WebAdmin getWebAdminByMp(Integer eid, Long mp) {
		String hql = "from WebAdmin as wa where wa.eid=? and wa.adminTel=? and wa.status=1";
		Object[] values = new Object[2];
		values[0] = eid;
		values[1] = mp;
		List find = this.getHibernate_reader_Interface("gweb", null).find(hql,
				values);
		List<WebAdmin> admin = find;
		if (admin != null && admin.size() > 0) {
			return admin.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 保存或更新管理员权限对象
	 * 
	 * @param adminId
	 * @param pids
	 * @return
	 */
	public void saveOrUpdate(Integer adminId, String[] pids) {

		if (pids != null && pids.length > 0) {
			StringBuffer sb = new StringBuffer();
			sb
					.append("insert into gweb_corpadmin_permission (admin_id, model_id) ");
			sb
					.append("select ")
					.append(
							adminId
									+ ", model_aiid from gweb_model where model_aiid in (");
			for (int i = 0; i < pids.length - 1; i++) {
				sb.append(pids[i] + ", ");
			}
			sb.append(pids[pids.length - 1] + ")");
			this.getHibernate_Writer_Interface("gweb", null).sqlUpdate(
					sb.toString(), new Object[] {});
		}
	}

	/**
	 * 根据管理员ID删除管理员权限
	 * 
	 * @param adminId
	 */
	public void removeList(Integer adminId) throws BusinessException,
			DataException {
		if (adminId != null) {
			String sql = "delete from gweb_corpadmin_permission where admin_id =?";
			this.getHibernate_Writer_Interface("gweb", null).sqlUpdate(sql,
					adminId);
		}
	}

	public WebAdmin getWebAdminByUserId(Integer id) {
		String hql = "from WebAdmin as wa where wa.adminUserid=?";
		Object[] values = new Object[1];
		values[0] = id;
		List find = this.getHibernate_reader_Interface("gweb", null).find(hql,
				values);
		List<WebAdmin> admin = find;
		if (admin != null && admin.size() > 0) {
			return admin.get(0);
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmcc.framework.persistence.interfaces.system.admin.IWebAdminDAO#findAdminListCount(java.lang.Integer,
	 *      java.lang.String)
	 */
	public Integer findAdminListCount(Integer eid, String initials)
			throws DataException {
		if (null == eid) {
			throw new DataException(
					" This method must be required eid,but eid is null!");
		}
		StringBuffer sb = new StringBuffer();
		sb
				.append("select count(c) from WebAdmin c where c.eid=? and c.level=1 and status=1");
		// hqlStr = "select count(c) from WebAdmin c where c.eid=? and c.level=1
		// and admin=?";
		List paramList = new ArrayList();
		paramList.add(eid);
		List list;
		if (null != initials && !initials.equals("")) {
			sb.append("and c.admin like ?");
			paramList.add("%" + initials + "%");
			list = this.getHibernate_reader_Interface("gweb", null)
					.createQuery(sb.toString(), paramList.toArray());
		} else {
			list = this.getHibernate_reader_Interface("gweb", null)
					.createQuery(sb.toString(), paramList.toArray());
		}
		if (null != list && list.size() > 0) {
			return Integer.valueOf(list.get(0).toString());
		} else {
			return 0;
		}
	}

	public List<WebAdmin> getAdminList(Integer eid, String initials,
			Integer firstResult, Integer pageSize) throws DataException {
		// StringBuffer sb = new StringBuffer();
		// sb.append("from WebAdmin where eid=? ");
		// if (initials != null && !"".equals(initials)) {
		// sb.append("and (initials like ? or user_name like ? ) ");
		// }
		// sb.append("order by identifier desc ");
		// if (firstResult != null && pageSize != null) {
		// List returnList = this.getHibernate_reader_Interface("gweb",
		// null).findByHQLandPage(sb.toString(), page, values)
		// .createSQLQuery(hqlStr);
		// Query query = session.createQuery(hql);
		//
		// query.setFirstResult(firstResult);
		// query.setMaxResult(pageSize);
		//
		// List list = query.list();
		//
		// }
		return null;
	}

	// select * from pcore_employee_blacklist where eid = ? if (null != initials
	// && !initials.equals("")) {
	// buf.append(" and (initials like ? or user_name like ? )");
	// } order by aiid desc if (null != firstResult && null != pageSize) {
	// buf.append(" limit ").append(firstResult).append(",").append(pageSize);
	// }

	public void removeAdmin(Integer adminId) throws DataException {
		if (adminId != null) {
			Date date = new Date();
			String sql = "update gweb_corp_admin set status = 0, removetime=? where admin_id = ?";
			this.getHibernate_Writer_Interface("gweb", null).sqlUpdate(sql,
					date, adminId);
		}
	}

	/**
	 * 根据父管理员ID 返回所有子管理员
	 */
	@SuppressWarnings("unchecked")
	public List<WebAdmin> fineByParentId(Integer eid, Integer parentId,
			String searchName) throws DataException {
		List<WebAdmin> list = null;
		StringBuilder hql = new StringBuilder(
				"from WebAdmin where status <> 0 and eid = ? and parentAdminId = ?");
		if (null != searchName && !searchName.equals("")) {
			hql.append(" and admin like ?");
			list = this.getHibernate_reader_Interface("gweb", null)
					.createQuery(hql.toString(), eid, parentId,
							"%" + searchName + "%");
		} else {
			list = this.getHibernate_reader_Interface("gweb", null)
					.createQuery(hql.toString(), eid, parentId);
		}
		if (list != null) {
			return list;
		}
		return new ArrayList<WebAdmin>();
	}

	@SuppressWarnings("unchecked")
	public List<WebAdmin> fineByParentId(Integer eid, String searchName,
			Page page, List<WebAdmin> list, Integer parId) throws DataException {
		List objs = new ArrayList();
		StringBuilder hql = new StringBuilder(
				"from WebAdmin c where c.eid=? and c.level=1 and status=1");
		objs.add(eid);

		if (parId != null && parId != 0) {
			hql.append(" and c.parentAdminId = ? ");
			objs.add(parId);
		}
		if (null != searchName && !"".equals(searchName)) {
			hql.append(" and c.admin like ? ");
			objs.add("%" + searchName + "%");
		}
		if (list != null && list.size() != 0) {
			hql.append(" and  c.identifier in (");
			for (int i = 0; i < list.size(); i++) {
				hql.append(list.get(i).getIdentifier());
				if (i != list.size() - 1) {
					hql.append(",");
				}
			}
			hql.append(")");
		}
		hql.append("order by c.identifier desc");
		List<WebAdmin> returnList = this.getHibernate_reader_Interface("gweb",
				null).findByHQLandPage(hql.toString(), page, objs.toArray());
		return returnList;
	}

	public void updateAdminByDept(List<String> deptIds) {
		if (deptIds == null && deptIds.size() == 0) {
			return;
		}
		StringBuilder hql = new StringBuilder(
				"update WebAdmin set deptId = 1 where deptId in ( ");
		for (int i = 0; i < deptIds.size(); i++) {
			hql.append(deptIds.get(i));
			if (i != deptIds.size() - 1) {
				hql.append(",");
			}
		}
		hql.append(")");

		this.getHibernate_Anal().hqlUpdate(hql.toString());

	}

	@SuppressWarnings("unchecked")
	public List<WebAdmin> findBy(SearchCondition searchCond) {
		WebAdminSearch search = (WebAdminSearch)searchCond;
		List paramList = new ArrayList();
		String hqlStr = "from WebAdmin c where c.level=1 and c.dmlflog <> 3";
		if (search != null && !StringUtil.isBlank(search.getAdminName())) {
			hqlStr += " and c.admin = ?";
			paramList.add(search.getAdminName());
		}
		if (search != null && search.getDeptId() != null) {
			hqlStr += " and c.deptId = ?";
			paramList.add(search.getDeptId());
		}
		if(search != null && search.getRoleId() != null){
			hqlStr += " and c.roleId = ?";
			paramList.add(search.getRoleId());
		}
		hqlStr += "order by c.identifier desc";

		List returnList = this.getHibernate_reader_Interface("gweb", null)
				.createQuery(hqlStr, paramList.toArray());

		if (returnList != null && returnList.size() > 0) {
			return returnList;
		} else {
			return null;
		}
	}
}
