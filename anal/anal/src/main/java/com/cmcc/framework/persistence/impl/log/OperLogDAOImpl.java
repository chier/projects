/**      
 * 文件名称： OperLogDAOHibernateImpl.java
 * 
 * 创建时间： 2009.3.23
 *      
 * Copyright (c) 2009 shilimin      
 */
package com.cmcc.framework.persistence.impl.log;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.hql.ast.tree.FromClause;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.date.DateUtil;
//import com.cmcc.common.util.GetTableNmae;
import com.cmcc.framework.model.log.OperLogSearch;
import com.cmcc.framework.model.log.OperateLog;
import com.cmcc.framework.persistence.interfaces.log.IOperLogDAO;


/**
 * 功能描述：系统日志查询DAO的接口实现
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-9
 * @param <T>
 */
public class OperLogDAOImpl<T extends OperateLog> extends
		GenericDAOImpl<OperateLog> implements IOperLogDAO<OperateLog> {

	public List<OperateLog> findByPage(Page pageinfo) {
		OperLogSearch seacher = (OperLogSearch) pageinfo.getSearchCondition();
		List paramList = new ArrayList();
		StringBuffer hqlStr = new StringBuffer("from OperateLog as al where 1=1 ");
		if (seacher.getAdminName() != null
				&& !seacher.getAdminName().toString().equalsIgnoreCase("")) {
			hqlStr.append(" and al.adminName like ?");
			paramList.add("%" + seacher.getAdminName() + "%");
		}
		if (seacher.getOperateTime() != null) {
			hqlStr.append(" and al.operateTime >= ?");
			paramList.add(DateUtil.toDate(seacher.getOperateTime()));
		}
		if (seacher.getEndTime() != null) {
			hqlStr.append(" and al.operateTime <= ?");
			paramList.add(DateUtil.toDate(seacher.getEndTime()));
		}
		if (seacher.getEid() != null) {
			hqlStr.append(" and al.eid = ? and operateMark <> 'point' ");
			paramList.add(seacher.getEid());
		}
		hqlStr.append(" order by al.operateTime desc ");
		hqlStr.append(" limit " + pageinfo.getFirstItemPos() + ","
				+ pageinfo.getPageSize());
		List<OperateLog> list = getHibernate_reader_Interface("gweb_db",
				new Integer(0)).findByHQLandPage(hqlStr.toString(),pageinfo, paramList.toArray());
		hqlStr.delete(0, hqlStr.length());
		hqlStr = null;
		return list;
	}

	public Page findBy(SearchCondition searchCond, Integer intsize)
			throws BusinessException, DataException {
		OperLogSearch seacher = (OperLogSearch) searchCond;
//
		List paramList = new ArrayList();
		StringBuffer hqlStr = new StringBuffer("select count(*) from OperateLog as al where 1=1 ");
		if (seacher.getAdminName() != null
				&& !seacher.getAdminName().toString().equalsIgnoreCase("")) {
			hqlStr.append(" and al.adminName like ?");
			paramList.add("%" + seacher.getAdminName() + "%");
		}
		if (seacher.getOperateTime() != null) {
			hqlStr.append(" and al.operateTime >= ?");
			paramList.add(DateUtil.toDate(seacher.getOperateTime()));
		}
		if (seacher.getEndTime() != null) {
			hqlStr.append(" and al.operateTime <= ?");
			paramList.add(DateUtil.toDate(seacher.getEndTime()));
		}
		if (seacher.getEid() != null) {
			hqlStr.append(" and al.eid = ? and operateMark <> 'point' ");
			paramList.add(seacher.getEid());
		}
		List resultList = this.getHibernate_reader_Interface("gweb_db",
				new Integer(0)).createQuery(hqlStr.toString(), paramList.toArray());
		long totalCount = ((Long) resultList.get(0)).longValue();
		// 返回分页对象
		if (totalCount < 1) {
			totalCount = 0;
		}
		hqlStr.delete(0, hqlStr.length());
		hqlStr = null;
		return new Page(totalCount, intsize);
//		return null;
	}

	/*
	 * 保存日志到库 (non-Javadoc)
	 * 
	 * @see com.cmcc.framework.persistence.interfaces.log.IOperLogDAO#saveOperateLog(com.cmcc.framework.model.log.OperateLog)
	 */
	public void saveOperateLog(OperateLog operateLog) {
		this.getHibernate_Anal().saveOrUpdate(operateLog);
	}
	
	public void updateOperateLog(OperateLog o){
		this.getHibernate_Anal().saveOrUpdate(o);
	}
	@SuppressWarnings("unchecked")
	public List queryLog(OperateLog o,Integer i){
		List<OperateLog> list =null;
		String s = "from OperateLog";
		try {
			list = getHibernate_Anal().createSQLQuery(s.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public OperateLog get(OperateLog o){
		return getHibernate_Anal().get(o.getIdentifier());
	}

}
