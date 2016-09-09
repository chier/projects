/**      
 * 文件名称： IOperLogDAO.java
 * 
 * 创建时间： 2009.3.23
 *      
 * Copyright (c) 2009 by shilimin 
 */
package com.cmcc.framework.persistence.interfaces.log;

import java.util.List;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.generic.interfaces.IGenericDAO;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.log.OperateLog;

/**
 * 
 * 功能描述：系统日志查询DAO的接口
 * 
 * @author <a href="mailto:shilimin@hdxt.net.cn">shilimin</a>
 * 
 * @version 1.0
 */

public interface IOperLogDAO<T extends OperateLog> extends
		IGenericDAO<OperateLog> {

	/**
	 * 根据page条件获取数据
	 * 
	 * @param page
	 * @return
	 */
	List<T> findByPage(Page page);

	/**
	 * 
	 * 
	 * @param searchCond
	 * @param intsize
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	public Page findBy(SearchCondition searchCond, Integer intsize)
			throws BusinessException, DataException;

	/**
	 * 保存操作日志
	 * 
	 * @param operateLog
	 */
	public void saveOperateLog(OperateLog operateLog);
	/**
	 * 修改操作日志
	 * 
	 * @param operateLog
	 */
	public void updateOperateLog(OperateLog operateLog);
	/**
	 * 查询操作日志(最近浏览历史和常用功能)
	 * @param operateLog,i(1:最近浏览历史2:常用功能)
	 */
	public List queryLog(OperateLog o,Integer i);
	/**
	 * 查找记录
	 * 
	 * @param operateLog
	 */
	public OperateLog get(OperateLog o);
}
