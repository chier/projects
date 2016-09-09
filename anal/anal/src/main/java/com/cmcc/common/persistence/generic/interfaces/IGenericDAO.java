/*
 * 文件名： IGenericDAO.java
 * 
 * 创建日期： 2009-2-23
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.common.persistence.generic.interfaces;

import java.io.Serializable;
import java.util.List;

import com.cmcc.common.persistence.dao.interfaces.IEntityDAO;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;

/**
 * 
 * 所有DAO操作接口的基类
 * 
 * @author <a href="mailto:sun128837@126.com">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-2-23
 */
public interface IGenericDAO<T> {
	/**
	 * 获取数据写入操作support接口
	 * 
	 * @param isPool
	 *            是否pool区
	 * @param poolId
	 *            如果是请传入要读取的pool的配置：物理poolid
	 * @return
	 */
	IEntityDAO<T> getHibernate_Writer_Interface(String dbname, Integer poolId);

	/**
	 * 获取数据读取操作support接口
	 * 
	 * @param isPool
	 *            是否pool区
	 * @param poolId
	 *            如果是请传入要读取的pool的配置：物理poolid
	 * @return
	 */
	IEntityDAO<T> getHibernate_reader_Interface(String dbname, Integer poolId);

	/**
	 * 获取数据读取操作support接口
	 * 
	 * @return
	 */
	IEntityDAO<T> getHibernate_Anal();

	IEntityDAO<T> getHibernate_S9999();

	IEntityDAO<T> getHibernate_Analysis();

}
