package com.cmcc.business;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.framework.business.interfaces.role.IRoleManager;
import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.role.Role;
import com.cmcc.framework.model.role.RoleSearcher;

public class TestRoleManager extends TestBase {
	static IRoleManager roleManager = null;

	@BeforeClass
	public static void initBeforeClass() {
		roleManager = (IRoleManager) getBean("roleManager");
	}

//	@Test
	public void saveOrUpdate() {
		Role role = new Role();
		role.setRoleName("name" + System.currentTimeMillis());
		role.setRoleDec("desc");
		role.setCreateTime(new Date());
		role.setDmlflog((short) 1);
		role.setDmlTime(new Date());
		role.setRoleType(1);
//		role.setRoleScope(0);
		roleManager.saveOrUpdateRole(role);
	}

//	@Test
	public void findByAll() {
		List<Role> roleList = roleManager.getAllRole();
		Assert.assertNotNull(roleList);
		logger.info(roleList.size());
	}

//	@Test
	public void findRoleBy() throws BusinessException {
		RoleSearcher roleSeacher = new RoleSearcher();
		roleSeacher.setRoleName("eeee");
		Page page = roleManager.findRoleBy(12, roleSeacher);
		Assert.assertNotNull(page);

		page.setSearchCondition(roleSeacher);

		List<Role> list = roleManager.findRoleByPage(page);
		Assert.assertNotNull(list);
		logger.info(list.size());
	}

//	@Test
	public void findWorkByRid() {
		List<Workflow> list = roleManager.findWorkByRid(1);
		Assert.assertNotNull(list);
		for (Workflow work : list) {
			logger.info(" id= " + work.getId() + " | name = " + work.getName());
		}
	}
	
//	@Test
	public void updateRoleWorkflow(){
		String [] wids = new String[]{"1","2","3","4","5"};
		Integer roldid = 3;
		roleManager.updateRoleWorkflow(roldid, wids);
	}
	
	@Test
	public void findAdminByRoleId(){
		List<WebAdmin> adminList = roleManager.findAdminByRoldId(8);
		Assert.assertNotNull(adminList);
	}
}
