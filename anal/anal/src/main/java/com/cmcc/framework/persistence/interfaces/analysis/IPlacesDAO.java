package com.cmcc.framework.persistence.interfaces.analysis;

import java.util.List;
import java.util.Map;

import com.cmcc.framework.model.analysis.Places;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

/**
 * 试点信息DAO接口
 * 
 * @author w
 * 
 * @param <T>
 */
public interface IPlacesDAO<T extends Places> extends IBaseDAO<Places> {

	/**
	 * 根据年份返回试点详情
	 * 
	 * @param year
	 * @return
	 */
	List<Places> findByYear(String years, String options);
	
	
	/**
	 * 根据年份返回试点详情
	 * 
	 * @param year
	 * @return
	 */
	List<Places> findByYear(String years);

	/**
	 * 查出所有字段及相关信息
	 * 
	 * @param optionType
	 * @param code
	 * @param places
	 * @return
	 */
	List<Map> findItem(String years,Integer code, Places places);
}
