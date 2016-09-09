package com.cmcc.framework.persistence.impl;

import java.io.Serializable;

import com.cmcc.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

public abstract class BaseDAOImpl<T> extends GenericDAOImpl<T> implements IBaseDAO<T> {

	public void delete(T t) {
		getHibernate_Anal().remove(t);
	}

	public T deleteById(Serializable id) {
		T t = findById(id);
		delete(t);
		return t;
	}

	public T findById(Serializable id) {
		return getHibernate_Anal().get(id);
	}

	public void saveOrUpdate(T t) {
		getHibernate_Anal().saveOrUpdate(t);
	}
}
