package com.cmcc.business;

import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.framework.business.interfaces.permission.IPermissionManager;
import com.cmcc.framework.controller.formbean.ZtreeVO;

/**
 * 权限单元测试
 * 
 * @author zhangzhanliang
 * 
 */
public class TestPermissionManager extends TestBase {
	static IPermissionManager permissionManager = null;

	@BeforeClass
	public static void initBeforeClass() {
		permissionManager = (IPermissionManager) getBean("permissionManager");
	}

	@Test
	public void saveAndUpdateCustomTreePer() {
//		permissionManager.saveAndUpdateCustomTreePer(0, "1,2,3,4,5,6,6", 1,
//				PermissionMark.CustomTree_permission.getValue());

	}

	// @Test
	public void saveRolePermission() {
		int rid = 0;
		int pid = 1;
		while (pid < 50) {
			this.permissionManager.saveRolePermission(rid,
					PermissionMark.CustomTree_permission.getValue(), pid);
			pid++;
		}
	}

	@Test
	public void findPermissionByRid() {
		List<Integer> l = this.permissionManager.findPermissionByRid(0);
		Assert.assertNotNull(l);
	}

	@Test
	public void findQueriesByTree() {
		int rid = 0;
		List<ZtreeVO> l = this.permissionManager.findQueriesByTree(rid);
		Assert.assertNotNull(l);

		JSONArray jsonArray = JSONArray.fromObject(l);
		String json = jsonArray.toString();
		System.out.println("=======================");
		System.out.println(json);
		System.out.println("=======================");
	}
}
