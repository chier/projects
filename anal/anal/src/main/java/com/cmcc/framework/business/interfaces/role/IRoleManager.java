package com.cmcc.framework.business.interfaces.role;

import java.util.List;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.role.Role;
import com.cmcc.framework.model.role.RoleSearcher;

/**
 * 角色业务接口类
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-9
 */
public interface IRoleManager {
	
	/**
	 * 由企业id，角色id取角色
	 * 
	 * @param eId
	 * @param roleId
	 * @return
	 */
	Role findRoleById(Integer roleId);
	
	/**
	 * 取企业角色分页
	 * 
	 * @param eId
	 * @param pageSize
	 * @return
	 * @throws BusinessException
	 */
	Page findRoleBy(Integer pageSize,
			RoleSearcher roleSeacher) throws BusinessException;
	
	/**
	 * 取企业角色当前页内容
	 * 
	 * @param eid
	 * @param page
	 * @return
	 * @throws BusinessException
	 */
	List<Role> findRoleByPage(Page page)
			throws BusinessException;
	/**
	 * 取得企业下所有角色
	 * 
	 * @param eId
	 * @return
	 */
	List<Role> getAllRole();
	

	/**
	 * 保存角色
	 * 
	 * @param role
	 */
	void saveOrUpdateRole(Role role);

	/**
	 * 由角色名称返回角色
	 * 
	 * @param eid
	 * @param roleName
	 * @return
	 */
	Role getRoleByName(String roleName);

	/**
	 * 删除角色
	 * 
	 * @param eid
	 * @param roleId
	 * @param poolId
	 */
	void deleteRole(Integer roleId);
	
	/**
	 * 根据 role id 返回所有的项目列表
	 * @param roleId
	 * @return
	 */
	List<Workflow> findWorkByRid(Integer roleId);

	/**
	 * 保存角色与项目关系 
	 *  1. 先清空项目关系。
	 *  2. 添加
	 * @param roleId
	 * @param wids
	 */
	void updateRoleWorkflow(Integer roleId,String[] wids);
	
	/**
	 * 
	 * @param roleId
	 * @return
	 */
	List<WebAdmin> findAdminByRoldId(Integer roleId);
	
}
