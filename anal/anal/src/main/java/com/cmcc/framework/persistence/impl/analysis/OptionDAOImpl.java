package com.cmcc.framework.persistence.impl.analysis;

import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.analysis.Option;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.analysis.IOptionDAO;

/**
 * 环境选项
 * 
 * @author w
 * 
 * @param <T>
 */
public class OptionDAOImpl<T extends Option> extends BaseDAOImpl<Option>
		implements IOptionDAO<Option> {

	@Override
	public List<Option> findByPage(Page pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Option> findByYearsAndCode(String years, Integer code) {
		String hql = "from Option where year in (" + years + ")";
		return this.getHibernate_Anal().find(hql);
	}

}
