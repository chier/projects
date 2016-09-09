package com.cmcc.persistence;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.model.analysis.Pilot;
import com.cmcc.framework.model.analysis.Places;
import com.cmcc.framework.persistence.interfaces.analysis.IPilotDAO;
import com.cmcc.framework.persistence.interfaces.analysis.IPlacesDAO;

/**
 * 试点表的详细情况
 * 
 * @author w
 * 
 */
public class TestPilotDAO extends TestBase {
	static IPilotDAO pilotDAO = null;
	static IPlacesDAO placesDAO = null;

	@BeforeClass
	public static void initBeforeClass() {
		pilotDAO = (IPilotDAO) getBean("pilotDAO");
		placesDAO = (IPlacesDAO) getBean("placesDAO");
	}

	// @Test
//	public void findByYear() {
//		String years = "2012,2013,2014,2015";
//		List<Pilot> pilotList = pilotDAO.findByYear(years);
//		Iterator<Pilot> it = pilotList.iterator();
//		Pilot pilot = null;
//		Places places = null;
//		while (it.hasNext()) {
//			pilot = it.next();
//			List<Places> placesList = placesDAO.findByYear(years);
//			Iterator<Places> placesIt = placesList.iterator();
//			while (placesIt.hasNext()) {
//				places = placesIt.next();
//				List<Map> mapList = placesDAO.findItem(pilot.getCode(), places);
//				if (mapList != null && mapList.size() > 0) {
//					String lon = (String) mapList.get(0).get("lon");
//					String lat = (String) mapList.get(0).get("lat");
//					pilot.setLon(lon);
//					pilot.setLat(lat);
//					pilotDAO.saveOrUpdate(pilot);
//					break;
//				} else {
//					System.out.println(" years = " + pilot.getYear()
//							+ " | pname = " + pilot.getPname());
//				}
//
//			}
//		}
//	}

	@Test
	public void findByYears() {
		List<Pilot> pilotList = pilotDAO.findByYear("2014");
		Iterator<Pilot> it = pilotList.iterator();
		Pilot pilot = null;
		while (it.hasNext()) {
			pilot = it.next();
			String sql = "SELECT S101_s101A12,S101_s101A8,S101_s101A18,S101_s101A23,S101_s101A24,S101_s101A25,S101_s101A26,S101_s101A27 FROM t2plate001 WHERE `code` LIKE '"
					+ pilot.getCode() + "%'";
			List<Object[]> resultList = pilotDAO.getHibernate_S9999()
					.createSQLQuery(sql);
			if (resultList != null && resultList.size() != 0) {
				Object[] objs = resultList.get(0);
				pilot.setPopulation(Integer.valueOf(objs[0].toString())); // 人口
				pilot.setAcreage(Integer.valueOf(objs[1].toString())); // 面积
				pilot.setGdp(Float.valueOf(objs[2].toString()));// GDP
				StringBuilder sbui = new StringBuilder();
				sbui.append(objs[3].toString()).append("、")
						.append(objs[4].toString()).append("、")
						.append(objs[5].toString()).append("、")
						.append(objs[6].toString()).append("、")
						.append(objs[7].toString());
				pilot.setMainIndustry(sbui.toString());
				pilotDAO.saveOrUpdate(pilot);
			} else {
				System.out.println(" years = " + pilot.getYear()
						+ " | pname = " + pilot.getPname());
			}

		}
	}
}
