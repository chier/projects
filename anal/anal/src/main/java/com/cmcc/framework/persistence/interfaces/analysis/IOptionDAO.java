package com.cmcc.framework.persistence.interfaces.analysis;

import java.util.List;

import com.cmcc.framework.model.analysis.Option;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

/**
 * 环境选项
 * 
 * @author w
 * 
 * @param <T>
 */
public interface IOptionDAO<T extends Option> extends IBaseDAO<Option> {

	/**
	 * 根据年份和试点code 返回选项信息
	 * 
	 * @param years
	 * @param code
	 * @return
	 */
	List<Option> findByYearsAndCode(String years, Integer code);
}
