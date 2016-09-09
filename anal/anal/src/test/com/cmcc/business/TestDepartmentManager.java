package com.cmcc.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.framework.business.interfaces.department.IDepartmentManager;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.model.department.DepartmentSearcher;

public class TestDepartmentManager extends TestBase {
	static IDepartmentManager departmentManager = null;

	@BeforeClass
	public static void initBeforeClass() {
		departmentManager = (IDepartmentManager) getBean("departmentManager");
	}

//	@Test
	public void findBy() {
		DepartmentSearcher deptSearchCond = new DepartmentSearcher();
		deptSearchCond.setName("产品");
		deptSearchCond.setParentid(65);
		Page page = departmentManager.findBy(deptSearchCond, 12);
		Assert.assertNotNull(page);

		page.setSearchCondition(deptSearchCond);
		List<Department> list = departmentManager.findByPage(page);
		Assert.assertNotNull(list);
		logger.info(list.size());
		for (Department dept : list) {
			logger.info(" id = " + dept.getDeptId() + " name = "
					+ dept.getName());
		}
	}

//	@Test
	public void findBySearcher() {
		DepartmentSearcher deptSearchCond = new DepartmentSearcher();
		// deptSearchCond.setName("产品");
		deptSearchCond.setParentid(0);
		List<Department> list = departmentManager.findBy(deptSearchCond);
		Assert.assertNotNull(list);
		logger.info(list.size());
		List<String> plist = new ArrayList<String>();
		for (Department dept : list) {
			plist.add(dept.getDeptId().toString());
			logger.info(" id = " + dept.getDeptId() + " name = "
					+ dept.getName());
		}
		String[] ids = new String[plist.size()];
		DepartmentSearcher deptSe = new DepartmentSearcher();
		deptSe.setIds(plist.toArray(ids));
		list = departmentManager.findBy(deptSe);
		Assert.assertNotNull(list);
		logger.info(list.size());
		for (Department dept : list) {
			logger.info(" id = " + dept.getDeptId() + " name = "
					+ dept.getName());
		}
	}

	@Test
	public void deleteDeptAndSonDeptByDepId() {
//		DepartmentSearcher deptSearchCond = new DepartmentSearcher();
//		deptSearchCond.setName("name");
//		deptSearchCond.setParentid(0);
//		List<Department> list = departmentManager.findBy(deptSearchCond);
//		List<String> sl = new ArrayList<String>();
//		for (int i = 0; i < list.size(); i++) {
//			sl.add(list.get(i).getIdentifier().toString());
//		}
		List<String> l = new ArrayList<String>();
		l.add("196");
//		departmentManager.deleteDeptAndSonDeptByDepId(l);
		
		DepartmentSearcher deptSearchCond = new DepartmentSearcher();
		deptSearchCond.setIds(l.toArray(new String[l.size()])); // 根据id返回信息
		departmentManager.findBy(deptSearchCond);
	}

//	@Test
	public void saveOrUpdate() {
		Department department = new Department();

		department.setName("name");
		// department.setDeptName("deptname");
		// department.setInitials(CnToSpell.getFirstSpell(department.getName()
		// .trim().replaceAll(" ", "").replaceAll(" ", "")));
		department.setDescrption("desc");
		department.setParentId(0);
		department.setCreateTime(new Date());
		department.setEid(10011);
		department.setIsLeaf(new Integer("1"));
		// department.setLastTime(new Date());
		department.setDmlflog((short) 1);
		department.setDmltime(new Date());
		department.setDepartmentOrder(0);
		department.setDepth((short) 0);
		try {
			departmentManager.saveOrUpdateDept(department);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
