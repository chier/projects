package com.cmcc.framework.business.interfaces.analysis;

import java.util.List;

import com.cmcc.framework.model.analysis.Option;
import com.cmcc.framework.model.analysis.Pilot;

/**
 * 区域分析业务层接口
 * 
 * @author w
 * 
 */
public interface IReportManager {

	/**
	 * 根据年份返回试点信息
	 * 
	 * @return
	 */
	List<Pilot> findPilotByYears(String year);

	/**
	 * 
	 * @param years
	 * @param code
	 * @return
	 */
	List<Option> findOptionByYearsAndCode(String years, Integer code);

	/**
	 * 
	 * @param years
	 * @param optionTypes
	 * @param code
	 * @return
	 */
	List findPlaces(String years,String optionTypes,Integer code);
}
