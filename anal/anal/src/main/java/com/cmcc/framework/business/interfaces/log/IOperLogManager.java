package com.cmcc.framework.business.interfaces.log;

import java.util.List;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.log.OperateLog;
import com.cmcc.framework.persistence.interfaces.log.IOperLogDAO;

/**
 * @author shilimin
 * 
 */
public interface IOperLogManager {

	/**
	 * 获取查询日志的DAO
	 * 
	 * @param operLogDAO
	 */
	public void setOperLogDAO(IOperLogDAO operLogDAO);

	/**
	 * 根据分页信息获取数据
	 * 
	 * @param pageinfo
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	public List<OperateLog> findBy(Page pageinfo) throws BusinessException,
			DataException;

	/**
	 * 
	 * 
	 * @param intsize
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	public Page findBy(Integer intsize) throws BusinessException, DataException;

	/**
	 * 保存操作日志
	 * 
	 * @param operateLog
	 */
	public void saveOperateLog(OperateLog operateLog);
	/**
	 * 修改操作日志
	 * @param operateLog
	 */
	public void updateOperateLog(OperateLog o);
	/**
	 * 查询操作日志(最近浏览历史和常用功能)
	 * @param operateLog,i(1:最近浏览历史2:常用功能)
	 */
	public List queryLog(OperateLog o,Integer i);

	/**
	 * 根据查询条件和每页数量获得Page
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
	 * 查找记录
	 * 
	 * @param operateLog
	 */
	public OperateLog get(OperateLog o);
}
