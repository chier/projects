package com.cmcc.framework.persistence.impl.model;

import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.model.model.WebModel;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.model.IWebModelDAO;

/**
 * 模块 dao 实现类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public class WebModelDAOImpl<T extends WebModel> extends BaseDAOImpl<WebModel>
		implements IWebModelDAO<WebModel> {

	public List<WebModel> findByAll() {
		String hql = "from WebModel where permissionType = 1 ORDER BY modelSort";
		return this.getHibernate_Anal().createQuery(hql);
	}

	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		return null;
	}

	public List<WebModel> findByPage(Page pageInfo) {
		return null;
	}

	public String findNameByIds(String ids) {

		String hql = "select modelName from WebModel where identifier in ( "
				+ ids + ") and permissionType = 1 ORDER BY modelSort";
		List<String> list = this.getHibernate_Anal().createQuery(hql);
		StringBuilder bu = new StringBuilder();
		for (int i = 0, l = list.size(); i < l; i++) {
			if (!StringUtil.isBlank(list.get(i)))
				bu.append(list.get(i));
			if (i != l - 1) {
				bu.append(",");
			}
		}
		return bu.toString();
	}

	public List<WebModel> findByIds(String ids) {
		String hql = "from WebModel as m where m.identifier in ( " + ids + ") and permissionType = 1 ORDER BY modelSort";
		return this.getHibernate_Anal().createQuery(hql);
	}

}
