package com.cmcc.framework.persistence.interfaces.analysis;

import java.util.List;

import com.cmcc.framework.model.analysis.Pilot;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

/**
 * 试点信息DAO接口
 * 
 * @author w
 * 
 * @param <T>
 */
public interface IPilotDAO<T extends Pilot> extends IBaseDAO<Pilot> {

	/**
	 * 根据年份返回试点详情
	 * 
	 * @param year
	 * @return
	 */
	List<Pilot> findByYear(String years);
}
