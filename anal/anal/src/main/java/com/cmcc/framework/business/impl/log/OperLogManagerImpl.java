/**      
 * 文件名称： OperLogManagerImpl.java
 * 
 * 创建时间： 2009.3.23
 *      
 * Copyright (c) 2009 by shilimin     
 */
package com.cmcc.framework.business.impl.log;

import java.util.List;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.business.interfaces.log.IOperLogManager;
import com.cmcc.framework.model.log.OperateLog;
import com.cmcc.framework.persistence.interfaces.log.IOperLogDAO;

/**
 * 
 * 功能描述：系统广播管理的实现类
 * 
 * @author <a href="mailto:shilimin@hdxt.net.cn">shilimin</a>
 * 
 * @version 1.0
 */

public class OperLogManagerImpl implements IOperLogManager {
	private IOperLogDAO<OperateLog> operLogDAO;

	public OperLogManagerImpl() {
		super();
	}

	public OperLogManagerImpl(IOperLogDAO<OperateLog> operLogDAO) {
		super();
		this.operLogDAO = operLogDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmcc.framework.business.interfaces.log.IOperLogManager#findBy(com.cmcc.common.persistence.support.Page)
	 */
	public List<OperateLog> findBy(Page page) throws BusinessException,
			DataException {

		return operLogDAO.findByPage(page);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmcc.framework.business.interfaces.log.IOperLogManager#findBy(java.lang.Integer)
	 */
	public Page findBy(Integer intsize) throws BusinessException, DataException {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmcc.framework.business.interfaces.log.IOperLogManager#findBy(com.cmcc.common.persistence.support.SearchCondition,
	 *      java.lang.Integer)
	 */
	public Page findBy(SearchCondition searchCond, Integer intsize)
			throws BusinessException, DataException {

		return operLogDAO.findBy(searchCond, intsize);
	}

	public void setOperLogDAO(IOperLogDAO operLogDAO) {
		this.operLogDAO = operLogDAO;
	}

	/* (non-Javadoc)
	 * @see com.cmcc.framework.business.interfaces.log.IOperLogManager#saveOperateLog(com.cmcc.framework.model.log.OperateLog)
	 */
	public void saveOperateLog(OperateLog operateLog) {
		operLogDAO.saveOperateLog(operateLog);
	}
	
	public void updateOperateLog(OperateLog operateLog) {
		operLogDAO.updateOperateLog(operateLog);
	}
	
	public List queryLog(OperateLog o,Integer i){
		return operLogDAO.queryLog(o, i);
	}
	/**
	 * 查找记录
	 * 
	 * @param operateLog
	 */
	public OperateLog get(OperateLog o){
		return operLogDAO.get(o);
	}
}
