package com.cmcc.framework.business.impl.department;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.business.interfaces.department.IDepartmentManager;
import com.cmcc.framework.model.department.Department;
import com.cmcc.framework.model.department.DepartmentSearcher;
import com.cmcc.framework.persistence.interfaces.admin.IWebAdminDAO;
import com.cmcc.framework.persistence.interfaces.department.IDepartmentDAO;

/**
 * 部门业务实现类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-10
 */
public class DepartmentManagerImpl implements IDepartmentManager {

	private IDepartmentDAO departmentDAO;
	
	private IWebAdminDAO webAdminDAO;
	
	public IWebAdminDAO getWebAdminDAO() {
		return webAdminDAO;
	}

	public void setWebAdminDAO(IWebAdminDAO webAdminDAO) {
		this.webAdminDAO = webAdminDAO;
	}

	public IDepartmentDAO getDepartmentDAO() {
		return departmentDAO;
	}

	public void setDepartmentDAO(IDepartmentDAO departmentDAO) {
		this.departmentDAO = departmentDAO;
	}

	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		return departmentDAO.findBy(searchCond, pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<Department> findByPage(Page pageInfo) {
		return departmentDAO.findByPage(pageInfo);
	}

	public Department findDeptById(Integer deptId) {
		return (Department) departmentDAO.findById(deptId);
	}

	@SuppressWarnings("unchecked")
	public List<Department> findBy(SearchCondition searchCond) {
		return departmentDAO.findBy(searchCond);
	}

	public void deleteDeptAndSonDeptByDepId(List<String> deptId) {
		List<String> delList = new ArrayList<String>();// 删除部门id集合
		getAllSonDept(deptId, delList);
		departmentDAO.deleteByIds(delList);
		webAdminDAO.updateAdminByDept(delList);
		
	}

	private void getAllSonDept(List<String> deptId, List<String> delList) {
		DepartmentSearcher deptSearchCond = new DepartmentSearcher();
		// deptSearchCond.setName("产品");
		deptSearchCond.setParentid(0);
		for (int i = 0; i < deptId.size(); i++) {
			String id = deptId.get(i);
			deptSearchCond = new DepartmentSearcher();
			deptSearchCond.setParentid(Integer.valueOf(id));
			List<Department> l = departmentDAO.findBy(deptSearchCond);
			if (l != null && l.size() > 0) {
				String[] ts = new String[l.size()];
				for (int j = 0; j < l.size(); j++) {
					ts[j] = l.get(j).getIdentifier().toString();
				}
				getAllSonDept(Arrays.asList(ts), delList);
			}
		}
		delList.addAll(deptId);
	}

	@SuppressWarnings("unchecked")
	public void updateDepToLeafNode(Integer deptId) {
		Department d = (Department) departmentDAO.findById(deptId);
		d.setIsLeaf(1);
		d.setDmlflog((short) 2);
		d.setDmltime(new Date());
		departmentDAO.saveOrUpdate(d);

	}

	public void saveOrUpdateDept(Department dept) {
		dept.setDmlflog((short) 2);
		dept.setDmltime(new Date());
		departmentDAO.saveOrUpdate(dept);
	}

}
