/*
 * 文件名： IEntityDAO.java
 * 
 * 创建日期： 2008-11-27
 *
 * Copyright(C) 2008, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 *
 */
package com.cmcc.common.persistence.dao.interfaces;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;

/**
 * 针对单个Entity对象的DAO接口
 * 
 * @author <a href="mailto:sun128837@126.com" alt="changqing">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-11-27
 */
@SuppressWarnings("unchecked")
public interface IEntityDAO<T> {
//	void falseDelete(Serializable id);
	/**
	 * 供未继承实现实体操作的DAO封装实体影射类型
	 * @param entityClass
	 */
	void setEntityClass(Class<T> entityClass);
	/**
	 * 获取实体类型
	 * @return
	 */
	Class<T> getEntityClass();
	/**
	 * 获取hibernate session,推荐在系统需要批量更新数据的时候控制实体的缓存对象使用
	 * 在本系统中推荐使用spring封装过的hibernateTempate
	 * @return
	 */
    @Deprecated
	Session getHibernateSession();
    /**
     * 通过使用DetachedCriteria支持hibernateTemplate条件查询
     * @param detachedCriteria
     * @return
     */
	List getEntityCriteria(DetachedCriteria detachedCriteria);

    /**
     * 
     * 通过使用DetachedCriteria支持hibernateTemplatef分页条件查询
     * @param detachedCriteria
     * @param page
     * @return
     */
	List getEntityCriteriaByPage(DetachedCriteria detachedCriteria, Page page);

	/**
	 * 根据ID获取对象
	 * 
	 * @param id
	 *            对象的Id
	 * @return 获得的对象或null。
	 */
	T get(Serializable id);

	/**
	 * 获取全部对象
	 * 
	 * @return 获取的全部对象的List，List的size可能为0。
	 */
	List<T> getAll();

	/**
	 * 获取全部对象，并根据page返回当前页的List。
	 * 
	 * @param page
	 *            分页信息对象
	 * @return 符合条件的对象List，List的size可能为0。
	 */
	List<T> getAll(Page page);

	/**
	 * 获取全部对象，并返回该对象的分页信息对象。
	 * 
	 * @param pageSize
	 *            分页大小
	 * @return 分页信息对象
	 */
	Page getAll(int pageSize);

	/**
	 * 获取全部对象数量
	 * 
	 * @return 全部对象数量
	 */
	Long getAllCount();

	/**
	 * 保存或者更新对象
	 * 
	 * @param o
	 *            要保存或更新的对象
	 */
	void saveOrUpdate(Object o);
	
	/**
	 * saveOrUpdateExt4QN,保存电子调查，保存电子调查需要
	 * 获取调查的发送编号（smsnum），这个过程需要使用事务，所以扩张了该方法
	 * @return Integer , 0 保存成功，1 短信电子调查超过上线， -1 保存失败， -2 未知错误
	 */
//	public Integer saveOrUpdateExt4QN(Object o);
	

	/**
	 * 删除对象
	 * 
	 * @param o
	 *            要删除的对象
	 */
	void remove(Object o);

	/**
	 * 根据ID删除对象
	 * 
	 * @param id
	 *            对象的Id
	 */
	void removeById(Serializable id);

	/**
	 * HQL查询
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            可变参数 用户可以如下四种方式使用 dao.find(hql) dao.find(hql,arg0);
	 *            dao.find(hql,arg0,arg1); dao.find(hql,new
	 *            Object[arg0,arg1,arg2])
	 * 
	 * @return 查到的对象List，List的size可能为0。
	 */
	List find(String hql, Object... values);

	/**
	 * HQL查询
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            可变参数 用户可以如下四种方式使用 dao.find(hql) dao.find(hql,arg0);
	 *            dao.find(hql,arg0,arg1); dao.find(hql,new
	 *            Object[arg0,arg1,arg2])
	 * 
	 * @return 查到的对象List，List的size可能为0。
	 */
	List<T> findByHQLandPage(String hql, Page page, Object... values);

	/**
	 * 根据属性名和属性值查询对象
	 * 
	 * @param searchCond
	 *            查询条件
	 * 
	 * @return 符合条件的对象List，List的size可能为0。
	 */
	List<T> findBy(SearchCondition searchCond);

	/**
	 * 根据属性名和属性值查询对象
	 * 
	 * @param searchCond
	 *            查询条件
	 * @param order
	 *            排序的字段
	 * @param orderBy
	 *            排序，asc：升序，desc：降序。
	 * 
	 * @return 符合条件的对象List，List的size可能为0。
	 */
	List<T> findBy(SearchCondition searchCond, String order, String orderBy);

	/**
	 * 根据属性名和属性值查询不符合查询条件的对象
	 * 
	 * @param searchCond
	 *            查询条件
	 * 
	 * @return 找到的对象List，List的size可能为0。
	 */
	List<T> findByNotLike(SearchCondition searchCond);

	/**
	 * 根据属性名和属性值查询对象，并根据page返回当前页的List。
	 * 
	 * @param searchCond
	 *            查询条件
	 * @param page
	 *            分页信息对象
	 * @return 符合条件的对象List，List的size可能为0。
	 */
	List<T> findBy(SearchCondition searchCond, Page page);

	/**
	 * 根据属性名和属性值查询对象，并返回该对象的分页信息对象。
	 * 
	 * @param searchCond
	 *            查询条件
	 * @param pageSize
	 *            分页大小
	 * @return 分页信息对象
	 */
	Page findBy(SearchCondition searchCond, int pageSize);

	/**
	 * 根据属性名和属性值查询单个对象.
	 * 
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * 
	 * @return 符合条件的唯一对象
	 */
	T findUniqueBy(String name, Object value);

	/**
	 * 根据属性名和属性值以Like AnyWhere方式查询对象
	 * 
	 * @param searchCond
	 *            查询条件
	 * @return 符合条件的对象List，List的size可能为0。
	 */
	List<T> findByLike(SearchCondition searchCond);

	/**
	 * 根据属性名和属性值以Like AnyWhere方式查询对象，并根据page返回当前页的List。
	 * 
	 * @param searchCond
	 *            查询条件
	 * @param page
	 *            分页信息对象
	 * @return 符合条件的对象List，List的size可能为0。
	 */
	List<T> findByLike(SearchCondition searchCond, Page page);

	/**
	 * 根据属性名和属性值以Like AnyWhere方式查询对象，并返回该对象的分页信息对象。
	 * 
	 * @param searchCond
	 *            查询条件
	 * @param pageSize
	 *            分页大小
	 * @return 分页信息对象
	 */
	Page findByLike(SearchCondition searchCond, int pageSize);

	/**
	 * 
	 * 判断对象某些属性的值在数据库中不存在重复
	 * 
	 * @param entity
	 *            各属性的值
	 * @param names
	 *            在POJO里不能重复的属性列表,以逗号分割 如"name,loginid,password"
	 * @return 是否存在重复
	 */
	boolean isNotUnique(Object entity, String names);

	/**
	 * 執行Query对象查詢.
	 * 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以返回Query后自行设置.
	 * 留意可以连续设置,如 dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * 
	 * @param values
	 *            可变参数 用户可以如下四种方式使用 dao.getQuery(hql) dao.getQuery(hql,arg0);
	 *            dao.getQuery(hql,arg0,arg1); dao.getQuery(hql,new
	 *            Object[arg0,arg1,arg2])
	 */
	List createQuery(String hql, Object... values);
	/**
     * 执行hql的update操作
	 * @param values
	 *            可变参数 用户可以如下四种方式使用 dao.getQuery(hql) dao.getQuery(hql,arg0);
	 *            dao.getQuery(hql,arg0,arg1); dao.getQuery(hql,new
     */
	int hqlUpdate(final String hql, final Object... values) ;

	/**
	 * 执行本地SQL查询，支持泛型
	 * 
	 * @param sql
	 *            要执行的本地sql
	 * @param clazz
	 *            类别
	 * @param values
	 *            sql中的参数
	 * @param <T>
	 *            实体类类型
	 * @return 查询结果集
	 */
	List<T> sqlQuery(String sql, Class clazz,
			Object... values);

	/**
	 * 执行本地SQL更新
	 * 
	 * @param sql
	 *            要执行的本地 sql
	 * @param objs
	 *            sql中的参数
	 * @return 更新的行数
	 */
	int sqlUpdate(String sql, Object... objs);
	
	/**
	 * 
	 */
	int msgSqlUpdate(String sql, Object... objs);
	
	/**
	 * 执行本地SQL更新
	 * 
	 * @param sql
	 *            要执行的本地 sql
	 * @param objs
	 *            sql中的参数
	 * @return 返回刚插入进去的记录的主键
	 */
	List listSqlUpdate(String sql,Object... objs);

	/**
	 * 
	 * @param sql
	 * @param fetch
	 * @param max
	 * @param clazz
	 * @param values
	 * @param <T>
	 * @return
	 */
	List<T> sqlQuery(String sql, int fetch, int max,
			Class clazz, Object... params);
	
	/**
	 * 
	 * @param sql
	 * @param fetch
	 * @param max
	 * @param values
	 * @return
	 */
	List sqlQuery(String sql,final Page page, Object... params);
	
	/**
	 * 
	 * @param sql
	 * @param fetch
	 * @param max
	 * @param values
	 * @return
	 */
	List sqlQuery(String sql,final Page page,Class clazz,Object... params);
	
	
	List createSQLQuery(String sql, Object... values);
	
	List createAliasSQLQuery(String sql, Object... values);

	/**
	 * 通过Query来创建结果集
	 * 
	 * @param hql
	 *            HQL
	 * @param index
	 *            分页的索引
	 * @param pageSize
	 *            分页大小
	 * @param values
	 *            帮定的对象
	 * 
	 * @return List 查询结果的List，List的size可能为0。
	 */
	List listQuery(String hql, int index, int pageSize, Object... values);
	
	List findByHql(String hql,int first,int count,Object... values);

	/**
	 * 创建Criteria对象
	 * 
	 * @param criterion
	 *            可变条件列表,Restrictions生成的条件
	 * @return 创建的Criteria对象
	 */
	List createCriteria(Criterion... criterion);

	/**
	 * 获得当前类的PK字段名
	 * 
	 * @return 获得的字段名
	 */
	String getIdName();

	/**
	 * 取得对象的主键值,辅助函数
	 * 
	 * @param entity
	 *            要获取主键的对象
	 * @return 取得对象的主键值
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	Serializable getId(Object entity) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException;

	/**
	 * HQL查询
	 * 
	 * @param hql
	 *            HQL语句
	 * @param values
	 *            可变参数 用户可以如下四种方式使用 dao.find(hql) dao.find(hql,arg0);
	 *            dao.find(hql,arg0,arg1); dao.find(hql,new
	 *            Object[arg0,arg1,arg2])
	 * 
	 * @return 查到的对象List，List的size可能为0。
	 */
	List<T> findObject(String hql, Object... values);
	
	
	/**
	 * 插入sql,并返回自增的字段
	 * @param sql
	 * @param objs
	 * @return
	 */
	public Long sqlInsert(final String sql, final Object[] objs);
	
}
