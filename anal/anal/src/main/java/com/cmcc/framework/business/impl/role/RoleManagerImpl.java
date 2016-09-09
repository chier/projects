package com.cmcc.framework.business.impl.role;

import java.util.List;

import org.apache.log4j.Logger;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.framework.business.interfaces.role.IRoleManager;
import com.cmcc.framework.model.Workflow;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.admin.WebAdminSearch;
import com.cmcc.framework.model.role.Role;
import com.cmcc.framework.model.role.RoleSearcher;
import com.cmcc.framework.model.role.RoleWorkflow;
import com.cmcc.framework.persistence.interfaces.admin.IWebAdminDAO;
import com.cmcc.framework.persistence.interfaces.role.IRoleDAO;
import com.cmcc.framework.persistence.interfaces.role.IRoleWorkflowDAO;
import com.cmcc.framework.persistence.interfaces.system.IWorkflowDAO;

/**
 * 角色业务实现类
 * 
 * @author <a href="mailto:songwei@fetionyy.com.cn">song wei</a>
 * @version $Revision: 1.1 $
 * @since 2009-9-7
 */
public class RoleManagerImpl implements IRoleManager {
	Logger logger = Logger.getLogger(RoleManagerImpl.class);

	private IRoleDAO roleDAO;

	private IRoleWorkflowDAO roleWorkflowDAO;

	private IWorkflowDAO workflowDAO;

	private IWebAdminDAO webAdminDAO;

	public IWebAdminDAO getWebAdminDAO() {
		return webAdminDAO;
	}

	public void setWebAdminDAO(IWebAdminDAO webAdminDAO) {
		this.webAdminDAO = webAdminDAO;
	}

	public IWorkflowDAO getWorkflowDAO() {
		return workflowDAO;
	}

	public void setWorkflowDAO(IWorkflowDAO workflowDAO) {
		this.workflowDAO = workflowDAO;
	}

	public IRoleWorkflowDAO getRoleWorkflowDAO() {
		return roleWorkflowDAO;
	}

	public void setRoleWorkflowDAO(IRoleWorkflowDAO roleWorkflowDAO) {
		this.roleWorkflowDAO = roleWorkflowDAO;
	}

	public IRoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public void saveOrUpdateRole(Role role) {
		this.roleDAO.saveOrUpdate(role);
	}

	public List<Role> getAllRole() {
		return this.roleDAO.findByAll();
	}

	public Role getRoleByName(String roleName) {

		return this.roleDAO.findByName(roleName);
	}

	public List<Role> findRoleByPage(Page page) throws BusinessException {
		return this.roleDAO.findByPage(page);
	}

	public Page findRoleBy(Integer pageSize, RoleSearcher roleSeacher)
			throws BusinessException {

		return this.roleDAO.findBy(roleSeacher, pageSize);
	}

	public Role findRoleById(Integer roleId) {
		return (Role) this.roleDAO.findById(roleId);
	}

	public void deleteRole(Integer roleId) {
		roleDAO.deleteById(roleId);
	}

	@SuppressWarnings("unchecked")
	public List<Workflow> findWorkByRid(Integer roleId) {
		List<Integer> wList = roleWorkflowDAO.findWidByRid(roleId);
		return workflowDAO.findByIds(wList);
	}

	public void updateRoleWorkflow(Integer roleId, String[] wids) {
		roleWorkflowDAO.deleteByRid(roleId);

		if (wids != null && wids.length > 0) {
			for (String s : wids) {
				RoleWorkflow rw = new RoleWorkflow();
				rw.setRid(roleId);
				rw.setWid(Integer.valueOf(s));
				roleWorkflowDAO.saveOrUpdate(rw);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<WebAdmin> findAdminByRoldId(Integer roleId) {
		WebAdminSearch search = new WebAdminSearch();
		search.setRoleId(roleId);
		return webAdminDAO.findBy(search);
	}
}