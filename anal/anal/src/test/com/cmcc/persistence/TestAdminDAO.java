package com.cmcc.persistence;

import java.util.Date;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.persistence.interfaces.admin.IWebAdminDAO;

public class TestAdminDAO extends TestBase {
	static IWebAdminDAO dao = null;

	@BeforeClass
	public static void initBeforeClass() {
		dao = (IWebAdminDAO) getBean("webAdminDAO");
	}

	@Test
	public void saveOrUpdate() {
		WebAdmin admin = new WebAdmin();
		admin.setAdmin("admin " + System.currentTimeMillis());
		admin.setCount(0);
		admin.setCreateTime(new Date());
		admin.setDmlflog((short) 0);
		admin.setDmlTime(new Date());
		admin.setLevel(1);
		admin.setPassword("111111");
		dao.saveOrUpdate(admin);
	}

	@Test
	public void findByAdminName() {
		WebAdmin admin = dao.findByAdminName("admins");
		Assert.assertNotNull(admin);
	}
}
