package com.cmcc.framework.persistence.interfaces;

import java.io.Serializable;
import java.util.List;

import com.cmcc.common.persistence.generic.interfaces.IGenericDAO;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;

public interface IBaseDAO<T> extends IGenericDAO <T>{

	/**
	 * 根据分页集合信息 返回查询到的数据集
	 * 
	 * @param pageInfo
	 *            分页集合信息
	 * @return 查询到的数据集
	 */
	List<T> findByPage(Page pageInfo);

	/**
	 * 根据查询条件 返回分页信息
	 * 
	 * @param searchCond
	 *            查询条件集合
	 * @param pageSize
	 *            每页显示数量
	 * @return 分页信息集合
	 */
	Page findBy(SearchCondition searchCond, Integer pageSize);

	/**
	 * 添加或保存对象到数据
	 * 
	 * @param t
	 *            对象
	 */
	void saveOrUpdate(T t);

	/**
	 * 删除对象
	 * 
	 * @param t
	 *            对象
	 */
	void delete(T t);

	/**
	 * 根据主键id 删除对象
	 * 
	 * @param id
	 *            主键 id
	 */
	T deleteById(Serializable id);

	/**
	 * 根据对象id 返回对象
	 * 
	 * @param id
	 *            主键id
	 * @return 对象
	 */
	T findById(Serializable id);
}
