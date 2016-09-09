package com.cmcc.business;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.business.interfaces.custom.ICustomManager;
import com.cmcc.framework.controller.formbean.ZtreeVO;

public class TestCustommanager extends TestBase {

	static ICustomManager customManager = null;

	@BeforeClass
	public static void initBeforeClass() {
		customManager = (ICustomManager) getBean("customManager");
	}

	// @Test
	public void testFindByDesignatedTable() {
		String sql = "SELECT CEN_OBJECT_CODE as 'AA', PARENT_CEN_OBJECT_CODE as 'BB' FROM  cen_object";
		List<Object[]> obj = customManager.findByDesignatedTable(sql);
		System.out.println(obj);
		// List<CustomVO> list = customManager.findByTable("cen_object", "0");
		/*
		 * Iterator<String> it = map.keySet().iterator(); while(it.hasNext()) {
		 * String key = it.next(); System.out.println(map.get(key)); }
		 */
		// System.out.println(map.get("sizeColumn"));
		// System.out.println(map.get("objs"));
		// List l = (List) map.get("objs");
		// for (int i = 0; i < l.size(); i++) {
		// Object[] o = (Object[]) l.get(i);
		// for (int j = 0; j < o.length; j++) {
		// System.out.println(o[j]);
		// }
		//			
		// }
	}

	@Test
	public void findByAll() {
		List<ZtreeVO> l = this.customManager.findByAll();
		Assert.assertNotNull(l);
		JSONArray jsonArray = JSONArray.fromObject(l);
		System.out.println(jsonArray);

	}
}
