/*
 * 文件名： GenericDAOImpl.java
 * 
 * 创建日期： 2009-2-23
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.common.persistence.generic.impl;

import java.util.Random;

import com.cmcc.common.Global;
import com.cmcc.common.persistence.dao.interfaces.IEntityDAO;
import com.cmcc.common.persistence.generic.interfaces.IGenericDAO;
import com.cmcc.common.persistence.util.GenericsUtil;

/**
 * 
 * DAO超类，主要提供hibernate操作方法
 * 
 * @author <a href="mailto:sun128837@126.com">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-2-23
 */
@SuppressWarnings("unchecked")
public class GenericDAOImpl<T> implements IGenericDAO<T> {
	/**
	 * 线呈安全
	 */
	// private static ThreadLocal<IEntityDAO> local = new
	// ThreadLocal<IEntityDAO>();
	/*
	 * 
	 * (non-Javadoc)synchronized
	 * 
	 * @seecom.efetionmanage.common.persistence.dao.interfaces.IGenericDAO#
	 * getHibernate_Writer_Interface(boolean, java.lang.Integer)
	 */
	public IEntityDAO<T> getHibernate_Writer_Interface(String dbname,
			Integer poolId) {
		if (null != dbname && !dbname.equalsIgnoreCase("")) {
			if (dbname.equalsIgnoreCase("gcs_db")) {
				return this.getGcs_w();
			} else if (dbname.equalsIgnoreCase("goss_db")) {
				return this.getGoss_w();
			} else if (dbname.equalsIgnoreCase("gsms_db")) {// 增加gsms
				return this.getGsms_w();
			} else {
				return this.getGdb_w();
			}
		} else {
			return this.getPoolx_w(poolId);
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @seecom.efetionmanage.common.persistence.dao.interfaces.IGenericDAO#
	 * getHibernate_reader_Interface(boolean, java.lang.Integer)
	 */
	public IEntityDAO<T> getHibernate_reader_Interface(String dbname,
			Integer poolId) {
		return getHibernate_Anal();
	}

	public static final Integer SLAVENUM = 2;

	/* 获取Poolx_w的数据源 */
	private IEntityDAO getPoolx_w(int x) {

		int urlid = ((x + 1) % 40);
		String beanname = "pool_" + urlid + "_w";
		return SwappSessionFactory(beanname);
	}

	private IEntityDAO getPoolx_r(int x) {

		int urlid = ((x + 1) % 40);
		Random random = new Random();
		int rand = random.nextInt(SLAVENUM);
		String beanname = "pool_" + urlid + "_r_" + rand;
		return SwappSessionFactory(beanname);
	}

	private IEntityDAO getGdb_r() {
		Random random = new Random();
		int rand = random.nextInt(SLAVENUM);
		String beanname = "gweb_r_" + rand;
		return SwappSessionFactory(beanname);
	}

	private IEntityDAO getGdb_w() {
		return getHibernate_Anal();
	}

	private IEntityDAO getGcs_w() {
		return SwappSessionFactory("gcs_w");
	}

	private IEntityDAO getGoss_r() {
		return SwappSessionFactory("goss_r");
	}

	private IEntityDAO getGinf_r() {
		return SwappSessionFactory("ginf_r");
	}

	private IEntityDAO getGoss_w() {
		return SwappSessionFactory("goss_w");
	}

	private IEntityDAO getGsms_w() {
		return SwappSessionFactory("gsms_w");
	}

	private IEntityDAO SwappSessionFactory(String hibernateEntityName) {
		IEntityDAO iEntityDAO = (IEntityDAO) Global._ctx
				.getBean(hibernateEntityName);

		iEntityDAO.setEntityClass(GenericsUtil
				.getSuperClassGenricType(getClass()));
		return iEntityDAO;
	}

	public IEntityDAO<T> getHibernate_Anal() {
		return SwappSessionFactory("anal_w");
	}
	
	public IEntityDAO<T> getHibernate_S9999() {
		return SwappSessionFactory("s9999_w");
	}
	
	public IEntityDAO<T> getHibernate_Analysis() {
		return SwappSessionFactory("analysis_w");
	}
	
	
//	public IEntityDAO getHibernate_Anals() {
//		return SwappSessionFactory("anal_s");
//	}
	
//	public IEntityDAO getHibernate_AliasAnals() {
//		return SwappSessionFactory("anal_s");
//	}
}
