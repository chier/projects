/*
 * 文件名： IEntityDAOClient.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.anal.common.persistence.dao.interfaces;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * 针对单个EntityClient对象的DAO接口
 * 
 * 适用OpenSessionInClient模式 因为OpenSessionInViewFilter只适用在WEB下
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-11-27
 */
public interface IEntityDAOClient<T> {

	/**
	 * 获得Session
	 * 
	 * @return 返回Session
	 */
	 Session getSession();

	/**
	 * 设置sessionFactory
	 * 
	 * @param sessionFactory
	 */
	 void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * 关闭Session
	 */
	 void closeSession();

	/**
	 * 创建Query对象.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以返回Query后自行设置.
	 * 留意可以连续设置,如 dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * 
	 * @param values
	 *            可变参数 用户可以如下四种方式使用 dao.getQuery(hql) dao.getQuery(hql,arg0);
	 *            dao.getQuery(hql,arg0,arg1); dao.getQuery(hql,new
	 *            Object[arg0,arg1,arg2])
	 */
	 Query createQuery(String hql, Object... values);

	/**
	 * 保存对象
	 * 
	 * @param o
	 *            被保存的对象
	 */
	 void saveOrUpdate(Object o);

	/**
	 * 开启事务
	 */
	 void beginTransaction();

	/**
	 * 提交事务或者回滚
	 * 
	 * @param commit
	 *            是提交事务还是回滚事务
	 */
	 void endTransaction(boolean commit);
}
