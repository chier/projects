/*
 * 文件名： HibernateEntityDAOClient.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.persistence.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.cmcc.common.persistence.dao.interfaces.IEntityDAOClient;
import com.cmcc.common.persistence.util.GenericsUtil;


/**
 * IEntityDAOClient接口的实现
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
@SuppressWarnings("unchecked")
public class HibernateEntityDAOClient<T> implements IEntityDAOClient<T> {

	private SessionFactory sessionFactory;

	private Session session;

	/** DAO所管理的Entity类型 */
	protected Class<T> entityClass;

	/** 定义Session局部线程 */
	private ThreadLocal localSession = new ThreadLocal();

	/** 定义事物局部线程 */
	private ThreadLocal localTransaction = new ThreadLocal();

	public HibernateEntityDAOClient() {

		entityClass = GenericsUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 取得entityClass. JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 * 
	 * @return 实体对象的Class
	 */
	protected Class<T> getEntityClass() {
		return entityClass;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * 创建一个Session
	 * 
	 * @return 返回一个Session
	 */
	private Session openSession() {
		session = SessionFactoryUtils.getSession(sessionFactory, true);
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(session));
		localSession.set(session);
		return session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAOClient#closeSession()
	 */
	public void closeSession() {
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.releaseSession(session, sessionFactory);
		// System.out.println("session is close");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAOClient#getSession()
	 */
	public Session getSession() {
		Session session = (Session) localSession.get();
		if (session == null) {
			session = openSession();
		}
		return session;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAOClient#beginTransaction()
	 */
	public void beginTransaction() {
		Transaction tx = this.getSession().beginTransaction();
		localTransaction.set(tx);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAOClient#endTransaction(boolean)
	 */
	public void endTransaction(boolean commit) {
		Transaction tx = (Transaction) this.localTransaction.get();
		if (commit) {
			tx.commit();
		}		else {
			tx.rollback();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAOClient#createQuery(java.lang.String,
	 *      java.lang.Object[])
	 */
	public Query createQuery(String hql, Object... values) {
		Query query = this.getSession().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAOClient#saveOrUpdate(java.lang.Object)
	 */
	public void saveOrUpdate(Object o) {
		Session session = this.getSession();
		session.saveOrUpdate(o);
	}
}