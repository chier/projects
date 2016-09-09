package com.cmcc.framework.business.impl.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.cmcc.framework.business.interfaces.analysis.IReportManager;
import com.cmcc.framework.model.analysis.Option;
import com.cmcc.framework.model.analysis.Pilot;
import com.cmcc.framework.model.analysis.Places;
import com.cmcc.framework.persistence.interfaces.analysis.IOptionDAO;
import com.cmcc.framework.persistence.interfaces.analysis.IPilotDAO;
import com.cmcc.framework.persistence.interfaces.analysis.IPlacesDAO;
import common.Logger;

/**
 * 区域分析业务层
 * 
 * @author w
 * 
 */
public class ReportManagerImpl implements IReportManager {

	private Logger logger = Logger.getLogger(ReportManagerImpl.class);
	/**
	 * 试点DAO接口
	 */
	private IPilotDAO pilotDAO;

	/**
	 * 环境选项DAO接口
	 */
	private IOptionDAO optionDAO;

	private IPlacesDAO placesDAO;

	public IPilotDAO getPilotDAO() {
		return pilotDAO;
	}

	public void setPilotDAO(IPilotDAO pilotDAO) {
		this.pilotDAO = pilotDAO;
	}

	public IOptionDAO getOptionDAO() {
		return optionDAO;
	}

	public void setOptionDAO(IOptionDAO optionDAO) {
		this.optionDAO = optionDAO;
	}

	public IPlacesDAO getPlacesDAO() {
		return placesDAO;
	}

	public void setPlacesDAO(IPlacesDAO placesDAO) {
		this.placesDAO = placesDAO;
	}

	@Override
	public List<Pilot> findPilotByYears(String year) {
		return pilotDAO.findByYear(year);
	}

	@Override
	public List<Option> findOptionByYearsAndCode(String years, Integer code) {
		return optionDAO.findByYearsAndCode(years, code);
	}

	/**
	 * 1。查看关联表选项信息。
	 * 2。通过表信息再次查询相关表的信息
	 * 3。
	 */
	@Override
	public List findPlaces(String years, String optionTypes, Integer code) {
		List<Places> placesList = this.placesDAO.findByYear(years, optionTypes);
		
		List<Map> result = new ArrayList<Map>();
		Iterator<Places> it = placesList.iterator();
		Places places = null;
		List<Map> tempList = null;
		while(it.hasNext()){
			places = it.next();
			tempList = this.placesDAO.findItem(years,code, places);
			result.addAll(tempList);
		}
		
		return result;
	}

}
