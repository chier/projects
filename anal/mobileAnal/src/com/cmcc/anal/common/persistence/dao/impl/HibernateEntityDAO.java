/*
 * 文件名： HibernateEntityDAO.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.anal.common.persistence.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.util.Assert;

import com.cmcc.anal.common.persistence.dao.interfaces.IEntityDAO;
import com.cmcc.anal.common.persistence.support.Page;
import com.cmcc.anal.common.persistence.support.SearchCondition;
import com.cmcc.anal.common.persistence.util.GenericsUtil;
import com.cmcc.anal.common.util.CommonUtil;
import com.cmcc.anal.common.util.DateTimeUtil;
// import com.cmcc.framework.model.questionnaire.Questionnaire;

/**
 * 负责为单个Entity 提供CRUD操作的Hibernate DAO基类。 <p/>
 * 子类只要在类定义时指定所管理Entity的Class，即拥有对单个Entity对象的CRUD操作。
 * 
 * <pre>
 * public interface IGroupCodeDAO&lt;T&gt; extends IEntityDAO&lt;T&gt; {
 * }
 * </pre>
 * 
 * <pre>
 * public class GroupCodeDAOHibernateImpl extends HibernateEntityDAO&lt;GroupCode&gt;
 * 		implements IGroupCodeDAO&lt;GroupCode&gt; {
 * }
 * </pre>
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.2 $
 * 
 * @since 2008-11-27
 */

public class HibernateEntityDAO<T> extends HibernateDaoSupport implements
		IEntityDAO<T> {

	/**
	 * DAO所管理的Entity类型.
	 */
	protected Class<T> entityClass;

	/**
	 * 在构造函数中将泛型T.class赋给entityClass
	 */
	public HibernateEntityDAO() {
		entityClass = GenericsUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 取得entityClass. JDK1.4不支持泛型的子类可以抛开Class<T> entityClass,重载此函数达到相同效果。
	 * 
	 * @return 实体对象的Class
	 */
	public Class<T> getEntityClass() {
		return entityClass;
	}

	/**
	 * @param entityClass
	 *            要设置的 entityClass。
	 */
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * 取得Entity的Criteria
	 * 
	 * @return 取得的Entity的Criteria
	 */
	public List getEntityCriteria(final DetachedCriteria detachedCriteria) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List list;
				try {
					Criteria criteria = detachedCriteria
							.getExecutableCriteria(session);
					list = criteria.list();
				} finally {
					releaseSession(session);
				}
				return list;
			}
		});
	}

	public List getEntityCriteriaByPage(
			final DetachedCriteria detachedCriteria, final Page page) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				List list;
				try {
					Criteria criteria = detachedCriteria
							.getExecutableCriteria(session);

					criteria.setFirstResult(page.getFirstItemPos());
					criteria.setMaxResults(page.getPageSize());
					list = criteria.list();
				} finally {
					releaseSession(session);
				}
				return list;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#createCriteria(org.hibernate
	 *      .criterion.Criterion[])
	 */
	public List createCriteria(Criterion... criterion) {
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);
		for (Criterion c : criterion) {
			detachedCriteria.add(c);
		}

		return getEntityCriteria(detachedCriteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#getId(java.lang.Object)
	 */
	public Serializable getId(Object entity) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return (Serializable) PropertyUtils.getProperty(entity, getIdName());
	}

	@Deprecated
	public Session getHibernateSession() {
		return super.getSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#getIdName()
	 */
	public String getIdName() {
		String idName = getSessionFactory().getClassMetadata(getEntityClass())
				.getIdentifierPropertyName();
		Assert.hasText(idName, getEntityClass().getSimpleName()
				+ "has no id column define");
		return idName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#find(java.lang.String,
	 *      java.lang.Object[])
	 */
	public List find(final String hql, final Object... values) {
		Assert.hasText(hql);

		return getHibernateTemplate().find(hql, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findBy(com.portal.common
	 *      .persistence.support.SearchCondition)
	 */
	public List<T> findBy(SearchCondition searchCond) {
		Assert.hasText(searchCond.getField());
		return createCriteria(Restrictions.eq(searchCond.getField(), searchCond
				.getFieldValue()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findBy(com.portal.common
	 *      .persistence.support.SearchCondition, java.lang.String,
	 *      java.lang.String)
	 */
	public List<T> findBy(SearchCondition searchCond, String order,
			String orderBy) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);
		detachedCriteria.add(Restrictions.eq(searchCond.getField(), searchCond
				.getFieldValue()));

		if (order.equalsIgnoreCase("asc")) {
			detachedCriteria.addOrder(Order.asc(order));
		} else if (order.equalsIgnoreCase("desc")) {
			detachedCriteria.addOrder(Order.desc(order));
		}
		return getEntityCriteria(detachedCriteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findByNotLike(com.portal
	 *      .common.persistence.support.SearchCondition)
	 */
	public List<T> findByNotLike(SearchCondition searchCond) {
		Assert.hasText(searchCond.getField());
		return createCriteria(Restrictions.ne(searchCond.getField(), searchCond
				.getFieldValue()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findBy(com.portal.common
	 *      .persistence.support.SearchCondition, int)
	 */
	public Page findBy(final SearchCondition searchCond, int pageSize) {
		Assert.hasText(searchCond.getField());
		// // 创建查询
		List countlist = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						List list;
						try {
							String q = "select count(*) from "
									+ entityClass.getName() + " g where g."
									+ searchCond.getField() + "=:fieldValue";

							Query query = s.createQuery(q);
							query.setParameter("fieldValue", searchCond
									.getFieldValue());
							list = query.list();
						} finally {
							releaseSession(s);
						}
						return list;
					}
				});
		long totalCount = (Long) countlist.get(0);
		if (totalCount < 1) {
			totalCount = 0;
		}
		return new Page(totalCount, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findBy(com.portal.common
	 *      .persistence.support.SearchCondition,
	 *      com.portal.common.persistence.support.Page)
	 */
	public List<T> findBy(SearchCondition searchCond, final Page page) {
		Assert.hasText(searchCond.getField());
		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);
		detachedCriteria.add(Restrictions.eq(searchCond.getField(), searchCond
				.getFieldValue()));

		if (page.getOrderBy() != null) {
			if (page.getOrder().equalsIgnoreCase("asc")) {
				detachedCriteria.addOrder(Order.asc(page.getOrderBy()));
			} else {
				detachedCriteria.addOrder(Order.desc(page.getOrderBy()));
			}
		} else {
			detachedCriteria.addOrder(Order.desc(this.getIdName()));
		}

		return this.getEntityCriteriaByPage(detachedCriteria, page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findByLike(com.portal.common
	 *      .persistence.support.SearchCondition)
	 */
	public List<T> findByLike(SearchCondition searchCond) {
		Assert.hasText(searchCond.getField());
		return createCriteria(Restrictions.like(searchCond.getField(), "%"
				+ searchCond.getFieldValue() + "%", MatchMode.ANYWHERE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findByLike(com.portal.common
	 *      .persistence.support.SearchCondition,
	 *      com.portal.common.persistence.support.Page)
	 */
	public List<T> findByLike(SearchCondition searchCond, final Page page) {
		Assert.hasText(searchCond.getField());

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);
		detachedCriteria.add(Restrictions.like(searchCond.getField(), "%"
				+ searchCond.getFieldValue() + "%", MatchMode.ANYWHERE));

		if (page.getOrderBy() != null) {
			if (page.getOrder().equalsIgnoreCase("asc")) {
				detachedCriteria.addOrder(Order.asc(page.getOrderBy()));
			} else {
				detachedCriteria.addOrder(Order.desc(page.getOrderBy()));
			}
		} else {
			detachedCriteria.addOrder(Order.desc(this.getIdName()));
		}

		return this.getEntityCriteriaByPage(detachedCriteria, page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findByLike(com.portal.common
	 *      .persistence.support.SearchCondition, int)
	 */
	public Page findByLike(final SearchCondition searchCond, int pageSize) {
		Assert.hasText(searchCond.getField());

		// 创建查询

		List countlist = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						List list;
						try {
							String q = "select count(*) from "
									+ entityClass.getName() + " g where g."
									+ searchCond.getField() + "like:fieldValue";

							Query query = s.createQuery(q);
							query.setParameter("fieldValue", "%"
									+ searchCond.getFieldValue() + "%");
							list = query.list();
						} finally {
							releaseSession(s);
						}
						return list;
					}
				});
		long totalCount = (Long) countlist.get(0);
		// 返回分页对象
		if (totalCount < 1) {
			totalCount = 0;
		}

		return new Page(totalCount, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findUniqueBy(java.lang.String ,
	 *      java.lang.Object)
	 */
	public T findUniqueBy(String name, Object value) {
		Assert.hasText(name);
		final DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);
		detachedCriteria.add(Restrictions.eq(name, value));

		return (T) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				T t;
				try {
					Criteria criteria = detachedCriteria
							.getExecutableCriteria(session);
					t = (T) criteria.uniqueResult();
				} finally {
					releaseSession(session);
				}
				return t;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#get(java.io.Serializable)
	 */
	public T get(Serializable id) {
		return (T) getHibernateTemplate().get(getEntityClass(), id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#getAll()
	 */
	public List<T> getAll() {
		return getHibernateTemplate().loadAll(getEntityClass());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#getAll(int)
	 */
	public Page getAll(int pageSize) {
		// 创建查询
		List countlist = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						List list;
						try {
							String q = " select count(*) from "
									+ entityClass.getName();

							Query query = s.createQuery(q);
							list = query.list();
						} finally {
							releaseSession(s);
						}
						return list;
					}
				});
		long totalCount = (Long) countlist.get(0);

		// 返回分页对象
		if (totalCount < 1) {
			totalCount = 0;
		}
		return new Page(totalCount, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#getAll(com.portal.common
	 *      .persistence.support.Page)
	 */
	public List<T> getAll(final Page page) {

		DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);

		if (page.getOrderBy() != null) {
			if (page.getOrder().equalsIgnoreCase("asc")) {
				detachedCriteria.addOrder(Order.asc(page.getOrderBy()));
			} else {
				detachedCriteria.addOrder(Order.desc(page.getOrderBy()));
			}
		} else {
			detachedCriteria.addOrder(Order.desc(this.getIdName()));
		}

		return this.getEntityCriteriaByPage(detachedCriteria, page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#getAllCount()
	 */
	public Long getAllCount() {
		List countlist = (List) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						List list;
						try {
							String q = " select count(*) from "
									+ entityClass.getName();

							Query query = s.createQuery(q);
							list = query.list();
						} finally {
							releaseSession(s);
						}
						return list;
					}
				});
		Long totalCount = (Long) countlist.get(0);

		return totalCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#isNotUnique(java.lang.Object ,
	 *      java.lang.String)
	 */
	public boolean isNotUnique(final Object entity, final String names) {
		Assert.hasText(names);

		final DetachedCriteria detachedCriteria = DetachedCriteria
				.forClass(this.entityClass);
		detachedCriteria.setProjection(Projections.rowCount());

		return (Boolean) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException {
						Criteria criteria = detachedCriteria
								.getExecutableCriteria(session);
						String[] nameList = names.split(",");
						try {
							// 循环加入
							for (String name : nameList) {
								criteria.add(Restrictions
										.eq(name, PropertyUtils.getProperty(
												entity, name)));
							}

							// 以下代码为了如果是update的情况,排除entity自身.
							// 通过Hibernate的MetaData接口取得主键名
							String idName = getIdName();
							// 取得entity的主键值
							Serializable id = getId(entity);
							// 如果id!=null,说明对象已存在,该操作为update,加入排除自身的判断
							if (id != null) {
								criteria.add(Restrictions.not(Restrictions.eq(
										idName, id)));
							}
						} catch (IllegalAccessException e) {
							logger.error("Error when reflection on entity,"
									+ e.getMessage());
							return false;
						} catch (InvocationTargetException e) {
							logger.error("Error when reflection on entity,"
									+ e.getMessage());
							return false;
						} catch (NoSuchMethodException e) {
							logger.error("Error when reflection on entity,"
									+ e.getMessage());
							return false;
						} finally {
							releaseSession(session);
						}
						return (Integer) criteria.uniqueResult() > 0;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#remove(java.lang.Object)
	 */
	public void remove(Object o) {
		getHibernateTemplate().delete(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#removeById(java.io.Serializable )
	 */
	public void removeById(Serializable id) {
		remove(get(id));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#saveOrUpdate(java.lang.Object )
	 */
	public void saveOrUpdate(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	/*
	 * public Integer saveOrUpdateExt4QN(final Object o){ final Questionnaire qn =
	 * (Questionnaire)o; if(0==qn.getDeployType()){ return (Integer)
	 * getHibernateTemplate().execute( new HibernateCallback(){ public Object
	 * doInHibernate(Session session) throws HibernateException { Integer retV =
	 * 0 ; String hql = "select q from Questionnaire as q where q.status IN
	 * (2,3,4) AND q.deployType = 0 ORDER BY q.smsMtnum desc"; List list = null;
	 * try { Query query = session.createQuery(hql); list = query.list();
	 * if(list != null ){ if(list.size()>=100){ retV = 1; }else{ Questionnaire
	 * tempqn = (Questionnaire)list.get(0); String smsNum =
	 * tempqn.getSmsMtnum(); Integer nowNum = new Integer(smsNum); if(nowNum<99){
	 * nowNum++; }else{ List sourceList = new ArrayList(); for(int i=0;i<99;i++){
	 * sourceList.add(i); } for(int i=0;i<list.size();i++){ Questionnaire
	 * tempQN = (Questionnaire)list.get(i); Integer hadSMSNum = new
	 * Integer(tempQN.getSmsMtnum()); sourceList.remove(hadSMSNum); }
	 * Questionnaire tempQN = (Questionnaire)list.get(0); nowNum = new
	 * Integer(tempQN.getSmsMtnum()); } qn.setSmsMtnum(nowNum.toString());
	 * session.saveOrUpdate(qn); }
	 * 
	 * }else{ session.saveOrUpdate(qn); } }catch(Exception e){
	 * logger.error("Error when saveOrUpdateExt4QN," + e.getMessage()); retV =
	 * -1; } finally { releaseSession(session); } return retV; } } );
	 * 
	 * }else{ getHibernateTemplate().saveOrUpdate(qn); return 0; } }
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#createQuery(java.lang.String ,
	 *      java.lang.Object[])
	 */
	public List createQuery(final String hql, final Object... values) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {

				List list;
				try {
					Query query = s.createQuery(hql);
					if (values != null) {
						for (int i = 0; i < values.length; i++) {
							query.setParameter(i, values[i]);
						}
					}
					list = query.list();
				} finally {
					releaseSession(s);
				}
				return list;
			}
		});
	}

	public int hqlUpdate(final String hql, final Object... values) {

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session s)
							throws HibernateException, SQLException {
						try {
							Query query = s.createQuery(hql);
							for (int i = 0; i < values.length; i++) {
								query.setParameter(i, values[i]);
							}
							return query.executeUpdate();
						} finally {
							releaseSession(s);
						}
					}
				});
	}

	public List createQueryByPageIndex(final String hql, final int index,
			final int pageSize, final Object... values) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {

				List list;
				try {
					Query query = s.createQuery(hql);
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
					query.setFirstResult(index).setMaxResults(pageSize);
					list = query.list();
				} finally {
					releaseSession(s);
				}
				return list;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmcc.common.persistence.dao.interfaces#sqlQuery(final String
	 *      sql, final Class sclass, final Object... values)
	 */
	public List<T> sqlQuery(final String sql, final Class clazz,
			final Object... values) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				List list = null;
				try {
					SQLQuery query = session.createSQLQuery(sql);
					query.addEntity(clazz);
					if (values != null && values.length > 0) {
						for (int i = 0; i < values.length; i++) {
							query.setParameter(i, values[i]);
						}
					}
					list = query.list();
				} finally {
					releaseSession(session);
				}

				return list;
			}
		});
	}

	public List<T> sqlQuery(final String sql, final int fetch, final int max,
			final Class clazz, final Object... params) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				List list;
				try {
					SQLQuery query = session.createSQLQuery(sql);
					query.addEntity(clazz);
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
					query.setFetchSize(fetch);
					query.setMaxResults(max);
					list = query.list();
				} finally {
					releaseSession(session);
				}
				return list;
			}
		});
	}

	public List<T> sqlQuery(final String sql, final Page page,final Object... params) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				List list;
				try {
					SQLQuery query = session.createSQLQuery(sql);
//					query.addEntity(clazz);
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
					query.setFirstResult(page.getFirstItemPos());
					query.setMaxResults(page.getPageSize());
					list = query.list();
				} finally {
					releaseSession(session);
				}
				return list;
			}
		});
	}
	
	public List<T> sqlQuery(final String sql, final Page page,final Class clazz,final Object... params) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws SQLException {
				List list;
				try {
					SQLQuery query = session.createSQLQuery(sql);
					query.addEntity(clazz);
					for (int i = 0; i < params.length; i++) {
						query.setParameter(i, params[i]);
					}
					query.setFirstResult(page.getFirstItemPos());
					query.setMaxResults(page.getPageSize());
					list = query.list();
				} finally {
					releaseSession(session);
				}
				return list;
			}
		});
	}
	
	public int sqlUpdate(final String sql, final Object... objs) {
		Integer result = 0;
		result = (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws SQLException {
						int updnum;
						try {
							SQLQuery query = session.createSQLQuery(sql);
							if (objs != null && objs.length > 0) {
								for (int i = 0; i < objs.length; i++) {
									query.setParameter(i, objs[i]);
								}
							}
							updnum = 0;
							updnum = query.executeUpdate();

						} finally {
							releaseSession(session);
						}
						return updnum;
					}
				});
		return result;
	}

	public int msgSqlUpdate(final String sql, final Object... objs) {
		Integer result = 0;
		result = (Integer) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws SQLException {
						int updnum;
						java.sql.PreparedStatement pre = null;
						try {

							pre = session.connection().prepareStatement(sql);

							pre.setInt(1, Integer.parseInt(String
									.valueOf(objs[0])));
							pre.setString(2, String.valueOf(objs[1]));

							SimpleDateFormat sdf = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							Date d = (Date) objs[2];
							String createStr = sdf.format(d);

							Timestamp createTime = Timestamp.valueOf(createStr);

							pre.setTimestamp(3, createTime);
							pre.setString(4, String.valueOf(objs[3]));
							if (objs[4] != null) {
								Date sendTime = DateTimeUtil.toDate(String
										.valueOf(objs[4] + ":00"));
								String sendStr = sdf.format(sendTime);
								Timestamp sendDate = Timestamp.valueOf(sendStr);
								pre.setTimestamp(5, sendDate);
							} else {
								pre.setDate(5, null);
							}

							pre.setString(6, String.valueOf(objs[5]));
							pre.setInt(7, Integer.parseInt(String
									.valueOf(objs[6])));
							if (objs[7] != null) {
								pre.setInt(8, Integer.parseInt(String
										.valueOf(objs[7])));
							} else {
								pre.setInt(8, -1);// 同一次短信记录在历史表中，第一条记录设置为-1。

							}
							pre.setInt(9, Integer.parseInt(String
									.valueOf(objs[8])));

							pre.setInt(10, Integer.parseInt(String
									.valueOf(objs[9])));// 短信类型
							pre.setInt(11, Integer.parseInt(String
									.valueOf(objs[10])));// 是否可回复
							pre.setString(12, String.valueOf(objs[11]));// 短信序号

							pre.setInt(13, Integer.parseInt(String
									.valueOf(objs[12])));// 是否已终止

							if (null == objs
									|| objs[13].toString().trim().length() < 1) {
								pre.setTimestamp(14, null);// 终止时间
							} else {
								Date sTime = DateTimeUtil.toDate(String
										.valueOf(objs[13] + ":00"));
								String stopStr = sdf.format(sTime);
								Timestamp stopTime = Timestamp.valueOf(stopStr);
								pre.setTimestamp(14, stopTime);// 终止时间
							}

							pre.setString(15, String.valueOf(objs[14]));
							pre.setInt(16, Integer.parseInt(String
									.valueOf(objs[15])));// 短信类型
							pre.executeUpdate();

							ResultSet rs = pre.getGeneratedKeys();
							rs.next();
							updnum = rs.getInt(1);

						} finally {
							session.connection().close();
							pre.close();
							releaseSession(session);
						}
						return updnum;
					}
				});
		return result;
	}

	public List createSQLQuery(final String sql, final Object... values) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {

				List list;
				try {
					SQLQuery query = s.createSQLQuery(sql);
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
					
					list = query.list();
					
				} finally {
					releaseSession(s);
				}
				return list;
			}
		});
	}
	
	public List createAliasSQLQuery(final String sql, final Object... values) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {

				List list;
				try {
					String[] alias = CommonUtil.getMetaData(sql);
					SQLQuery query = s.createSQLQuery(sql);
					for (int i = 0; i < alias.length; i++) {
						query.addScalar(alias[i], new org.hibernate.type.StringType());
					}
					for (int i = 0; i < values.length; i++) {
						query.setParameter(i, values[i]);
					}
					list = query.list();
				} finally {
					releaseSession(s);
				}
				return list;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#listQuery(java.lang.String,
	 *      int, int, java.lang.Object[])
	 */
	public List listQuery(String hql, int index, int pageSize, Object... values) {

		return createQuery(hql, values, index, pageSize);
	}

	/**
	 * @param hql
	 * @param index
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public List<T> listQueryForPage(String hql, int index, int pageSize,
			Object... values) {
		Assert.hasText(hql);
		return createQuery(hql, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findObject(java.lang.String,
	 *      java.lang.Object[])
	 */
	public List<T> findObject(String hql, Object... values) {
		Assert.hasText(hql);
		return this.find(hql, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.portal.common.persistence.dao.IEntityDAO#findByHQLandPage(java.lang
	 *      .String, ,Page page, java.lang.Object[])
	 */
	public List<T> findByHQLandPage(final String hql, final Page page,
			final Object... values) {

		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				List list;
				try {
					Query query = s.createQuery(hql);
					for (int p = 0; p < values.length; p++) {
						if (values[p] != null) {
							query.setParameter(p, values[p]);
						}
					}
					query.setFirstResult(page.getFirstItemPos());
					query.setMaxResults(page.getPageSize());
					list = query.list();
				} finally {
					releaseSession(s);
				}
				return list;
			}
		});
	}

	/*
	 * public void falseDelete(Serializable id) { Object obj = this.get(id); if
	 * (obj instanceof Questionnaire) { Questionnaire questionnaire =
	 * (Questionnaire) obj; if (questionnaire.getVisible() == 1) {
	 * questionnaire.setVisible(0); questionnaire.setSmsMtnum(null);
	 * questionnaire.setEditTime(new Date()); this.saveOrUpdate(questionnaire); } } }
	 */
	@SuppressWarnings("unchecked")
	public List listSqlUpdate(final String sql, final Object... values) {

		List<Long> list = new ArrayList<Long>();
		list = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					@SuppressWarnings("deprecation")
					public Object doInHibernate(Session session)
							throws SQLException {
						java.sql.PreparedStatement ps = null;
						long key;
						List<Long> keyList = new ArrayList<Long>();
						try {

							ps = session.connection().prepareStatement(sql);

							ps.setInt(1, Integer.parseInt(String
									.valueOf(values[0])));
							ps.setString(2, String.valueOf(values[1]));
							ps.setDate(3, new java.sql.Date(((Date) values[2])
									.getTime()));
							ps.setString(4, String.valueOf(values[3]));
							if (values[4] != null) {
								ps.setDate(5, new java.sql.Date(
										((Date) values[4]).getTime()));

							} else {
								ps.setDate(5, null);
							}

							ps.setString(6, String.valueOf(values[5]));

							ps.setInt(7, Integer.parseInt(String
									.valueOf(values[6])));

							if (values.length == 8) {
								ps.setInt(8, Integer.parseInt(String
										.valueOf(values[7])));
							}

							ps.executeUpdate(sql);

							ResultSet rs = ps.getGeneratedKeys();

							rs.next();
							key = rs.getLong(1);
							keyList.add(key);
							return keyList;
						} finally {
							releaseSession(session);
							ps.close();
						}
					}
				});
		return list;
	}

	public Long sqlInsert(final String sql, final Object[] objs) {
		return (Long) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws SQLException {
						SQLQuery query = session.createSQLQuery(sql);
						if(objs != null){
							for (int i = 0; i < objs.length; i++) {
								query.setParameter(i, objs[i]);
							}
						}
						query.executeUpdate();
						String sqlid = "select LAST_INSERT_ID() as id";
						query = session.createSQLQuery(sqlid);
						query.addScalar("id", Hibernate.LONG);
						List list = query.list();
						Long id = 0L;
						if (list.get(0) != null) {
							id = (Long) list.get(0);
						}
						releaseSession(session);
						return id;
					}
				});
	}

	public List findByHql(final String hql, final int first, final int count,
			final Object... values) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session s) throws HibernateException,
					SQLException {
				List list;
				try {
					Query query = s.createQuery(hql);
					for (int p = 0; p < values.length; p++) {
						if (values[p] != null) {
							query.setParameter(p, values[p]);
						}
					}
					query.setFirstResult(first);
					query.setMaxResults(count);
					list = query.list();
				} finally {
					releaseSession(s);
				}
				return list;
			}
		});
	}
}
