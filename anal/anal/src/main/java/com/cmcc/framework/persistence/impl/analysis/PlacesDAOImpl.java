package com.cmcc.framework.persistence.impl.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.analysis.Places;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.analysis.IPlacesDAO;

/**
 * 试点DAO实现类
 * 
 * @author w
 * 
 * @param <T>
 */
public class PlacesDAOImpl<T extends Places> extends BaseDAOImpl<Places>
		implements IPlacesDAO<Places> {

	@Override
	public List<Places> findByPage(Page pageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Places> findByYear(String years, String options) {
		StringBuffer optionTypes = new StringBuffer();
		String[] arr = options.split(",");
		for (int i = 0; i < arr.length; i++) {
			optionTypes.append("'").append(arr[i].replaceAll("[\\t\\n\\r]", "")).append("'");
			if (i != arr.length - 1) {
				optionTypes.append(",");
			}
		}

		String hql = "from Places where year in (" + years
				+ ") and optionType in ( " + optionTypes.toString() + ")";
		return this.getHibernate_Anal().find(hql);
	}

	@Override
	public List<Map> findItem(String years,Integer code, Places places) {
		List<Map> mapList = new ArrayList<Map>();

		String itemSql = "SELECT `ITEM_CODE`,`ITEM_NAME` FROM `tbl_item` WHERE `tbl_code` = '"
				+ places.getTableCode().replaceAll("[\\t\\n\\r]", "") + "' and `METRIC_NAME` = 2";
		List<Object[]> itemList = getHibernate_S9999().createSQLQuery(itemSql);

		StringBuilder bu = new StringBuilder("SELECT ");
		for (int i = 0; i < itemList.size(); i++) {
			bu.append("`").append(itemList.get(i)[0].toString()).append("`");
			if (i != itemList.size() - 1) {
				bu.append(",");
			}
		}
		bu.append(" from " + places.getTableCode());
		bu.append(" where `code` like '"+code+"%'");
		if(years.indexOf("2014") != -1){
			bu.append(" or `spec_code` like '"+code+"%'");
		}
		List<Object[]> tableList = getHibernate_S9999().createSQLQuery(
				bu.toString());

		Iterator<Object[]> it = tableList.iterator();
		Object[] objs = null;
		Map map = null;
		while (it.hasNext()) {
			map = new HashMap();
			objs = it.next();
			for (int i = 0; i < objs.length; i++) {
				
				if(places.getLon().equalsIgnoreCase(itemList.get(i)[0].toString())){
					map.put("lon", objs[i]);
				}else if(places.getLat().equalsIgnoreCase(itemList.get(i)[0].toString())){
					map.put("lat", objs[i]);
				}else{
					map.put(itemList.get(i)[1], objs[i]);
				}
			}
			map.put("code", code);
			map.put("type", places.getOptionType());
			mapList.add(map);
		}

		return mapList;
	}

	@Override
	public List<Places> findByYear(String years) {
		String hql = "from Places where year in (" + years
				+ ")";
		return this.getHibernate_Anal().find(hql);
	}

}
