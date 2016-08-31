package com.cmcc.anal.framework.persistence.impl;

import java.util.List;

import com.cmcc.anal.framework.model.admin.WebAdmin;
import com.cmcc.anal.framework.persistence.interfaces.IWebAdminDAO;

/**
 * webadmin 管理员 DAO实现类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 * @param <T>
 */
public class WebAdminDAOImpl<T extends WebAdmin> extends BaseDAOImpl<WebAdmin> implements IWebAdminDAO<WebAdmin> {

	@Override
	@SuppressWarnings("unchecked")
	public WebAdmin findByAdminName(String adminName) {
		String hql = "from WebAdmin as admin where admin.admin = ? and admin.dmlflog <> 3";
		List<WebAdmin> list = this.getHibernate_Anal().createQuery(hql, adminName);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
