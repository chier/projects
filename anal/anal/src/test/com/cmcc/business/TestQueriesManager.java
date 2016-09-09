package com.cmcc.business;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.framework.business.interfaces.queries.IQueriesManager;
import com.cmcc.framework.controller.formbean.QueriesVO;

public class TestQueriesManager extends TestBase {
	static IQueriesManager queriesManager = null;

	@BeforeClass
	public static void initBeforeClass() {
		queriesManager = (IQueriesManager) getBean("queriesManager");
	}

	/**
	 * 返回 group类别的信息
	 * 
	 */
//	@Test
	public void findByGroup() {
		System.out.println(" findByGroup start ");
		List<QueriesVO> l = queriesManager.findByGroup();
		Assert.assertNotNull(l);
		for (int i = 0; i < l.size(); i++) {
			findByObject(l.get(i).getModelName());
		}
		System.out.println("findByGroup  end ");
	}

	public void findByObject(String name) {
		System.out.println(name + " 	start ");
		List<QueriesVO> l = queriesManager.findByObject(name, "6");
		Assert.assertNotNull(l);
		System.out.println(name + "		end ");
	}
	
//	public void findByTableItem(){
//		String tableNmae = "plate001";
////		Object[] ao = queriesManager.findByTableItem(tableNmae,2,3);
//		Assert.assertNotNull(ao);
//		
//		List<String> itemL = (List<String>) ao[0];
//		for(int i = 0 ; i <itemL.size();i++){
//			System.out.println(itemL.get(i));
//		}
//		List<Object[]> olist = (List<Object[]>) ao[1];
//		System.out.println(olist.size());
//		for(int i =0;i<olist.size();i++){
//			Object[] obj = olist.get(i);
//			System.out.println(obj[1]);
//		}
//		
//		Page page = (Page) ao[2];
//		
//		System.out.print(page);
//	}
}
