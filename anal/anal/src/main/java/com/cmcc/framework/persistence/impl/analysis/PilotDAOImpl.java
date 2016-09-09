package com.cmcc.framework.persistence.impl.analysis;

import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.analysis.Pilot;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.analysis.IPilotDAO;

/**
 * 试点DAO实现类
 * 
 * @author w
 * 
 * @param <T>
 */
public class PilotDAOImpl<T extends Pilot> extends BaseDAOImpl<Pilot> implements
		IPilotDAO<Pilot> {

	@Override
	public List<Pilot> findByPage(Page pageInfo) {
		return null;
	}

	@Override
	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		return null;
	}

	@Override
	public List<Pilot> findByYear(String years) {
		String hql = "from Pilot where year in (" + years + ")";
		List<Pilot> list = getHibernate_Anal().find(hql);
		return list;
	}

}
