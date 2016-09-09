package com.cmcc.framework.persistence.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.Structure;
import com.cmcc.framework.persistence.interfaces.StructureDAO;

public class StructureDAOImpl<T extends Structure> extends
		BaseDAOImpl<Structure> implements StructureDAO<Structure> {
	private static Logger log = Logger.getLogger(StructureDAOImpl.class);

	@Override
	public List<Structure> findByPage(Page pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Structure> findByPid(Integer spid) {
		String hql = "from Structure where spid=?";
		return this.getHibernate_Anal().find(hql, spid);
	}

	@Override
	public List<Structure> findByPid(Integer spid, String pcenter) {
		String hql = "from Structure where spid=? and sid in (" + pcenter + ")";
		return this.getHibernate_Anal().find(hql, spid);
	}
}
