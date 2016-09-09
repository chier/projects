package com.cmcc.business;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.framework.business.interfaces.admin.IWebAdminManager;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.admin.WebAdminSearch;
import com.cmcc.framework.model.role.Role;

public class TestWebAdminManager extends TestBase {
	static IWebAdminManager webAdminManager = null;

	@BeforeClass
	public static void initBeforeClass() {
		webAdminManager = (IWebAdminManager) getBean("webAdminManager");
	}

	@Test
	public void findBy() {
		WebAdminSearch search = new WebAdminSearch();
		try {
			Page pageInfo = webAdminManager.findBy(search, 12);
			Assert.assertNotNull(pageInfo);
			pageInfo.setSearchCondition(search);
			List<WebAdmin> list = webAdminManager.findByPage(pageInfo);
			Assert.assertNotNull(list);
			for (WebAdmin admin : list) {
				logger.info(admin.toString());
			}

		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void remove() {
		int id = 7;
		WebAdmin admin;
		try {
			admin = webAdminManager.get(id);
			webAdminManager.remove(admin);
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getSameName() {
		boolean sameName = webAdminManager.getSameName("admins", 2);
		logger.info(sameName);
		Assert.assertNotNull(sameName);
		Assert.assertTrue(sameName);
		// Assert.assertTrue(sameName);
	}

	@Test
	public void findRoleBy() {
		List<Role> list = webAdminManager.findRoleBy();
		Assert.assertNotNull(list);
		for (Role role : list) {
			logger.info("name = " + role.getRoleName());
		}
	}
}
