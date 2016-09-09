package com.cmcc.persistence;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.model.role.Role;
import com.cmcc.framework.persistence.interfaces.role.IRoleDAO;

public class TestRoleDAO extends TestBase {
	static IRoleDAO dao = null;

	@BeforeClass
	public static void initBeforeClass() {
		dao = (IRoleDAO) getBean("roleDAO");
	}
	
	
	@Test
	public void saveOrUpdate(){
		Role role = new Role();
		role.setRoleName("name" + System.currentTimeMillis());
		role.setRoleDec("desc");
		role.setCreateTime(new Date());
		role.setDmlflog((short)1);
		role.setDmlTime(new Date());
		dao.saveOrUpdate(role);
	}
	
	
	@Test
	public void findByAll(){
		List<Role> roleList = dao.findByAll();
		Assert.assertNotNull(roleList);
		logger.info(roleList.size());
	}
}
