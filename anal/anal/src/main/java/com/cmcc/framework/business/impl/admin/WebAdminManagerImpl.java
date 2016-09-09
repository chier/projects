package com.cmcc.framework.business.impl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.business.interfaces.admin.IWebAdminManager;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.role.Role;
import com.cmcc.framework.model.role.RoleSearcher;
import com.cmcc.framework.persistence.interfaces.admin.IWebAdminDAO;
import com.cmcc.framework.persistence.interfaces.model.IWebModelDAO;
import com.cmcc.framework.persistence.interfaces.role.IRoleDAO;

/**
 * 管理员业务接口实现类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public class WebAdminManagerImpl implements IWebAdminManager {

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(WebAdminManagerImpl.class);

	private IWebAdminDAO webAdminDAO;
	
	private IRoleDAO roleDAO;
	
	private IWebModelDAO webModelDAO;
	
	

	public IWebModelDAO getWebModelDAO() {
		return webModelDAO;
	}

	public void setWebModelDAO(IWebModelDAO webModelDAO) {
		this.webModelDAO = webModelDAO;
	}

	public IRoleDAO getRoleDAO() {
		return roleDAO;
	}

	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	public IWebAdminDAO getWebAdminDAO() {
		return webAdminDAO;
	}

	public void setWebAdminDAO(IWebAdminDAO webAdminDAO) {
		this.webAdminDAO = webAdminDAO;
	}

	public void saveOrUpdate(WebAdmin webAdmin) {
		webAdminDAO.saveOrUpdate(webAdmin);
	}

	/**
	 * 根据ID获取管理员对象
	 * 
	 * @param id
	 *            管理员的ID
	 * 
	 * @return 获得的管理员对象或null
	 */
	public WebAdmin get(Integer id) throws BusinessException, DataException {
		if (logger.isInfoEnabled()) {
			logger.info("get(Serializable id=" + id + ") - start");
		}
		WebAdmin admin = this.webAdminDAO.getById(id);
		if (logger.isInfoEnabled()) {
			logger.info("get(Serializable id=" + id
					+ ") - end - return value = " + admin);
		}
		if (admin != null) {
			admin.setModelNames(this.webModelDAO.findNameByIds(admin.getModelIds()));
			return admin;
		} else {
			return null;
		}
	}

	/**
	 * 根据登录名获取管理员对象
	 * 
	 * @param loginId
	 *            管理员的登录名loginId
	 * @param code
	 *            企业代码 code
	 * @return 获得的管理员对象或null
	 */
	public WebAdmin getByLoginId(String loginId, String code)
			throws BusinessException, DataException {
		if (logger.isInfoEnabled()) {
			logger.info("getByLoginId(String loginId=" + loginId + ",code="
					+ code + ") - start");
		}
		WebAdmin admin = null;
		if (loginId != null && code != null) {
			admin = this.webAdminDAO.getAdminByLoginId(loginId, code);
		} else {
			throw new BusinessException(
					"WebAdminManagerImpl.getByLoginId(loginId=" + loginId
							+ ",code=" + code + ") ");
		}
		if (logger.isInfoEnabled()) {
			logger.info("getByLoginId(String loginId=" + loginId + ",code="
					+ code + ")- end -return value=" + admin);
		}
		return admin;
	}

	/**
	 * 获取全部管理员对象
	 * 
	 * @return 获取的全部管理员对象的List,List的size可能为0
	 */
	public List<WebAdmin> getAll() throws BusinessException, DataException {
		if (logger.isInfoEnabled()) {
			logger.info("getAll() - start");
		}

		List<WebAdmin> returnList = this.webAdminDAO
				.getHibernate_reader_Interface("gweb", null).getAll();

		if (logger.isInfoEnabled()) {
			logger.info("getAll() - end - return value = " + returnList.size());
		}
		if (returnList != null && returnList.size() > 0) {
			return returnList;
		} else {
			return null;
		}
	}

	/**
	 * 获取全部管理员对象,并根据 page 返回当前页的List
	 * 
	 * @param page
	 *            分页信息对象
	 * 
	 * @return 符合条件的管理员对象的List,List的size可能为0
	 */
	public List<WebAdmin> getAll(Page page) throws BusinessException,
			DataException {
		if (logger.isInfoEnabled()) {
			logger.info("getAll(Page page) - start");
		}
		List<WebAdmin> returnList = this.webAdminDAO
				.getHibernate_reader_Interface("gweb", null).getAll(page);
		if (logger.isInfoEnabled()) {
			logger.info("getAll(Page page) - end - return value = "
					+ returnList.size());
		}
		if (returnList != null && returnList.size() > 0) {
			return returnList;
		} else {
			return null;
		}
	}

	/**
	 * 获取管理员的分页信息对象
	 * 
	 * @param pageSize
	 *            分页大小
	 * 
	 * @return 分页信息对象
	 */
	public Page getAll(int pageSize) throws BusinessException, DataException {
		if (logger.isInfoEnabled()) {
			logger.info("getAll(int pageSize=" + pageSize + ") - start");
		}

		Page returnPage = this.webAdminDAO.getHibernate_reader_Interface(
				"fetion", null).getAll(pageSize);

		if (logger.isInfoEnabled()) {
			logger.info("getAll(int pageSize=" + pageSize
					+ ") - end - return value = " + returnPage);
		}
		return returnPage;
	}

	/**
	 * 删除管理员对象
	 * 
	 * @param o
	 *            要删除的管理员对象
	 */
	public void remove(WebAdmin admin) throws BusinessException, DataException {
		if (logger.isInfoEnabled()) {
			logger.info("remove(WebAdmin admin=" + admin + ") - start");
		}
		if (admin != null) {
			webAdminDAO.delete(admin);
		} else {
			throw new BusinessException("WebAdminManagerImpl.remove(admin="
					+ admin + ")");
		}
		if (logger.isInfoEnabled()) {
			logger.info("remove(WebAdmin admin=" + admin + ")- end");
		}
	}

	/**
	 * 保存或更新管理员对象
	 * 
	 * @param o
	 *            要保存或更新的管理员对象
	 */
	public void saveOrUpdate(WebAdmin admin, Integer id)
			throws BusinessException, DataException {
		if (logger.isInfoEnabled()) {
			logger.info("saveOrUpdate(WebAdmin admin=" + admin + ") - start");
		}
		if (admin != null) {
			if (id != null) {
				WebAdmin supperAdmin = this.webAdminDAO.getById(id);
				if (supperAdmin != null) {
					// admin.setSimpleName(supperAdmin.getSimpleName());
					this.webAdminDAO
							.getHibernate_Writer_Interface("gweb", null)
							.saveOrUpdate(admin);
				} else {
					throw new BusinessException(
							"WebAdminManagerImpl.saveOrUpdate(id=" + id + ")");
				}
			} else {
				this.webAdminDAO.getHibernate_Writer_Interface("gweb", null)
						.saveOrUpdate(admin);
			}
		} else {
			throw new BusinessException(
					"WebAdminManagerImpl.saveOrUpdate(admin=" + admin + ")");
		}
		if (logger.isInfoEnabled()) {
			logger.info("saveOrUpdate(WebAdmin admin=" + admin + ")- end");
		}
	}

	/**
	 * 根据查询对象返回查询结果List
	 * 
	 * @param searchCond
	 * @param p
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<WebAdmin> findByPage(Page page) throws BusinessException,
			DataException {
		List<WebAdmin> list = this.webAdminDAO.findByPage(page);
		for(int i=0;i<list.size();i++){
			list.get(i).setModelNames(this.webModelDAO.findNameByIds(list.get(i).getModelIds())); 
		}
		return list;
	}

	/**
	 * 根据查询条件返回page对象
	 * 
	 * @param pagesize
	 * @param searchCond
	 * @return
	 */
	public Page findBy(SearchCondition searchCond, int pagesize)
			throws BusinessException, DataException {
		return this.webAdminDAO.findBy(searchCond, pagesize);
	}

	/**
	 * 根据查询条件返回Page对象
	 * 
	 * @param pagesize
	 * @param searchCond
	 * @return
	 */
	public Page getPageLikeBySearchList(int pagesize, String name, Integer eid)
			throws BusinessException {
		return this.webAdminDAO.getPageLikeBySearchList(pagesize, name, eid);
	}

	/**
	 * 根据输入的名字，判断数据库中同一个企业下是否存在同名
	 * 
	 * @param name
	 * @param eid
	 * @return true 表示没有同名 false 表示有同名
	 */
	public boolean getSameName(String name, Integer adminid) {
		return this.webAdminDAO.getSameName(name, adminid);
	}

	/**
	 * 根据查询条件返回模糊查询
	 * 
	 * @param searchCond
	 * @param page
	 * @return
	 */
	public List<WebAdmin> getAllLikeBySearchListPage(String name,
			UserSessionObj userObj, Page page, List<WebAdmin> sonAdminList)
			throws BusinessException, DataException {
		List<WebAdmin> list = null;
		if (userObj.getRoleLevel() != 0) {
			list = webAdminDAO.fineByParentId(userObj.getEid(), name, page,
					null, userObj.getId());
		} else {
			list = this.webAdminDAO.getAllLikeBySearchListPage(name, userObj
					.getEid(), page);
		}
		if (list != null && list.size() > 0) {
			return list;
		} else {
			return null;
		}
	}

	/**
	 * 根据eid ,code 查找企业联系人手机号
	 * 
	 * @param eid
	 * @param code
	 * @return
	 */
	public Long getPhotoByEid(Integer eid, String code) {
		Long mp = this.webAdminDAO.getPhotoByEid(eid, code);
		if (mp != null) {
			return mp;
		} else {
			return null;
		}
	}

	/**
	 * 根据企业code查询企业状态
	 * 
	 * @param code
	 * @param simpleName
	 * @return
	 */
	public Short getCorpStaticByCode(String code, String simpleName) {
		Short stat = this.webAdminDAO.getCorpStaticByCode(code, simpleName);
		if (stat != null) {
			return stat;
		} else {
			return null;
		}
	}

	/**
	 * modefiy yangshuo 2009-9-14 根据企业简称判断在企业表中是否存在
	 * 
	 * @param simpleName
	 * @return
	 */
	public List haveSimpleNameList(String simpleName) {
		return this.webAdminDAO.haveSimpleNameList(simpleName);
	}

	/**
	 * 根据企业简称判断在企业表中是否存在
	 * 
	 * @param simpleName
	 * @return
	 */
	public List IsHaveSimpleName(String simpleName) {
		return this.webAdminDAO.IsHaveSimpleName(simpleName);
	}

	/***************************************************************************
	 * 根据模块名称获取详细内容
	 **************************************************************************/
	public List getGWebModel(String name) {
		return this.webAdminDAO.getGWebModel(name);
	}

	/**
	 * 根据eid查询出已绑定的员工
	 * 
	 * @param eid
	 * @return
	 */
	public List<WebAdmin> getBindEmployees(String eid) {
		return this.webAdminDAO.getBindEmployees(eid);
	}

	/**
	 * 查询出超管的信息
	 * 
	 * @return
	 */
	public List<WebAdmin> getSuperWebAdmin(Integer eid, Integer level) {
		return this.webAdminDAO.getSuperWebAdmin(eid, level);
	}

	/**
	 * 根据eid和userid查询出管理员信息
	 * 
	 * @return
	 */
	public WebAdmin getWebAdminById(Integer eid, Integer userid) {
		return this.webAdminDAO.getWebAdminById(eid, userid);
	}

	/**
	 * 保存或更新管理员权限对象
	 * 
	 * @param adminId
	 * @param pids
	 * @return
	 * @throws DataException
	 * @throws BusinessException
	 */
	public void saveOrUpdateModels(Integer adminId, String[] pids,
			List<WebAdmin> sonAdminList) throws BusinessException,
			DataException {
		Integer[] modelIds = null;
		String[] sonModel = null;
		this.webAdminDAO.saveOrUpdate(adminId, pids);
	}

	/**
	 * 权限过滤 判断子节点的权限是否有不存在父节点权限范围内的，过滤掉
	 * 
	 * @param modelIds
	 * @param pids
	 * @return
	 */
	private String[] modelInPids(Integer[] modelIds, String[] pids) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < pids.length; i++) {
			for (int j = 0; j < modelIds.length; j++) {
				if (Integer.valueOf(pids[i]).intValue() == modelIds[j]
						.intValue()) {
					list.add(modelIds[j].toString());
				}
			}
		}
		String[] sonModel = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			sonModel[i] = list.get(i);
		}
		return sonModel;
	}

	/**
	 * 根据管理员ID删除管理员权限
	 * 
	 * @param adminId
	 */
	public void removeList(Integer adminId) throws BusinessException,
			DataException {
		this.webAdminDAO.removeList(adminId);
	}

	public WebAdmin getWebAdminByUserId(Integer id) {
		return this.webAdminDAO.getWebAdminByUserId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmcc.framework.business.interfaces.system.admin.IWebAdminManager#getAdminListPageInfo(java.lang.Integer,
	 *      java.lang.String, java.lang.Integer)
	 */
	public Page getAdminListPageInfo(UserSessionObj obj, String initials,
			Integer pageSize, List<WebAdmin> adminList)
			throws BusinessException, DataException {
		Integer max = 0;

		if (obj.getRoleLevel() != 0) {
			// getAllSonAdmin(obj.getEid(), obj.getId(), adminList);
			adminList = this.webAdminDAO.fineByParentId(obj.getEid(), obj
					.getId(), initials);
			if (adminList != null) {
				max = adminList.size();
			} else {
				max = 0;
			}
		} else {
			max = this.webAdminDAO.findAdminListCount(obj.getEid(), initials);
		}
		return new Page(max, pageSize);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmcc.framework.business.interfaces.system.admin.IWebAdminManager#getAdminList(java.lang.Integer,
	 *      java.lang.String, com.cmcc.common.persistence.support.Page)
	 */
	public List<WebAdmin> getAdminList(Integer eid, String initials, Page page)
			throws BusinessException, DataException {
		return this.webAdminDAO.getAdminList(eid, initials, page
				.getFirstItemPos(), page.getPageSize());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmcc.framework.business.interfaces.system.admin.IWebAdminManager#removeAdmin(java.lang.Integer)
	 */

	/**
	 * 根据eid和mp查询出管理员信息
	 * 
	 * @return
	 */
	public WebAdmin getWebAdminByMp(Integer eid, Long mp) {
		return this.webAdminDAO.getWebAdminByMp(eid, mp);
	}

	@SuppressWarnings("unchecked")
	public List<Role> findRoleBy() {
		RoleSearcher roleSearch = new RoleSearcher();
		roleSearch.setRoleNotScope(Integer.valueOf(0));
		return roleDAO.findBy(roleSearch);
	}
}
