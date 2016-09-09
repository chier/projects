package com.cmcc.framework.persistence.interfaces.model;

import java.util.List;

import com.cmcc.framework.model.model.WebModel;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

/**
 * webmodel 模块 DAO 接口
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 * @param <T>
 */
public interface IWebModelDAO<T extends WebModel> extends IBaseDAO<WebModel> {

	/**
	 * 返回所有模块
	 * 
	 * @return
	 */
	List<WebModel> findByAll();

	/**
	 * 根据id返回模块的名称
	 * 
	 * @param ids
	 * @return
	 */
	String findNameByIds(String ids);
	
	/**
	 * 根据id返回模块的名称
	 * 
	 * @param ids
	 * @return
	 */
	List<WebModel> findByIds(String ids);
}
