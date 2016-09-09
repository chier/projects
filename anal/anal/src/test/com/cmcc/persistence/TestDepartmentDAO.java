package com.cmcc.persistence;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.persistence.interfaces.department.IDepartmentDAO;

public class TestDepartmentDAO extends TestBase {
	static IDepartmentDAO dao = null;

	@BeforeClass
	public static void initBeforeClass() {
		dao = (IDepartmentDAO) getBean("departmentDAO");
	}

	@Test
	public void findByAll() {
		List<Department> list = dao.findByAll();
		Assert.assertNotNull(list);
		logger.info(list.size());
		for (Department dept : list) {
			logger.info(dept.getIdentifier() + " \t " + dept.getName());
		}
	}
}
