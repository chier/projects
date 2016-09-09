package com.cmcc.framework.business.interfaces.admin;

import java.util.List;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.UserSessionObj;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.role.Role;

/**
 * 管理员业务接口类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public interface IWebAdminManager {

	/**
	 * 保存和修改管理员信息
	 * 
	 * @param webAdmin
	 */
	void saveOrUpdate(WebAdmin webAdmin);

	/**
	 * 根据ID获取管理员对象
	 * 
	 * @param id
	 *            管理员的ID
	 * 
	 * @return 获得的管理员对象或null
	 */
	public WebAdmin get(Integer id) throws BusinessException, DataException;

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
			throws BusinessException, DataException;

	/**
	 * 获取全部管理员对象
	 * 
	 * @return 获取的全部管理员对象的List,List的size可能为0
	 */
	public List<WebAdmin> getAll() throws BusinessException, DataException;

	/**
	 * 获取全部管理员对象,并根据 page 返回当前页的List
	 * 
	 * @param page
	 *            分页信息对象
	 * 
	 * @return 符合条件的管理员对象的List,List的size可能为0
	 */
	public List<WebAdmin> getAll(Page page) throws BusinessException,
			DataException;

	/**
	 * 获取管理员的分页信息对象
	 * 
	 * @param pageSize
	 *            分页大小
	 * 
	 * @return 分页信息对象
	 */
	public Page getAll(int pageSize) throws BusinessException, DataException;

	/**
	 * 保存或更新管理员对象
	 * 
	 * @param o
	 *            要保存或更新的管理员对象
	 */
	public void saveOrUpdate(WebAdmin o, Integer id) throws BusinessException,
			DataException;

	/**
	 * 删除管理员对象
	 * 
	 * @param o
	 *            要删除的管理员对象
	 */
	public void remove(WebAdmin o) throws BusinessException, DataException;

	/**
	 * 根据查询对象返回查询结果List
	 * 
	 * @param searchCond
	 * @param p
	 * @return
	 */
	public List<WebAdmin> findByPage(Page p) throws BusinessException,
			DataException;

	/**
	 * 根据查询条件返回page对象
	 * 
	 * @param pagesize
	 * @param searchCond
	 * @return
	 */
	public Page findBy(SearchCondition searchCond, int pagesize)
			throws BusinessException, DataException;

	/**
	 * 根据查询条件返回Page对象
	 * 
	 * @param pagesize
	 * @param searchCond
	 * @return
	 */
	public Page getPageLikeBySearchList(int pagesize, String name, Integer eid)
			throws BusinessException, DataException;

	/**
	 * 根据查询条件返回模糊查询
	 * 
	 * @param searchCond
	 * @param page
	 * @return
	 */
	public List<WebAdmin> getAllLikeBySearchListPage(String name,
			UserSessionObj userObj, Page page, List<WebAdmin> sonAdminList)
			throws BusinessException, DataException;

	/**
	 * 根据输入的名字，判断数据库中同一个企业下是否存在同名
	 * 
	 * @param name
	 * @param eid
	 * @return true 表示没有同名 false 表示有同名
	 */
	public boolean getSameName(String name, Integer adminid);

	/**
	 * 根据eid ,code 查找企业联系人手机号
	 * 
	 * @param eid
	 * @param code
	 * @return
	 */
	public Long getPhotoByEid(Integer eid, String code);

	/**
	 * 根据企业code查询企业状态
	 * 
	 * @param code
	 * @param simpleName
	 * @return
	 */
	public Short getCorpStaticByCode(String code, String simpleName);

	/**
	 * modefiy yangshuo 2009-9-14 根据企业简称判断在企业表中是否存在
	 * 
	 * @param simpleName
	 * @return
	 */
	List haveSimpleNameList(String simpleName);

	/**
	 * 根据企业简称判断在企业表中是否存在
	 * 
	 * @param simpleName
	 * @return
	 */
	public List IsHaveSimpleName(String simpleName);

	/***************************************************************************
	 * 根据模块名称获取详细内容
	 **************************************************************************/
	public List getGWebModel(String name);

	/**
	 * 根据eid查询出已绑定的员工
	 * 
	 * @param eid
	 * @return
	 */
	public List<WebAdmin> getBindEmployees(String eid);

	/**
	 * 查询出超管的信息
	 * 
	 * @return
	 */
	List<WebAdmin> getSuperWebAdmin(Integer eid, Integer level);

	/**
	 * 根据eid和userid查询出管理员信息
	 * 
	 * @return
	 */
	WebAdmin getWebAdminById(Integer eid, Integer userid);

	/**
	 * 保存或更新管理员权限对象
	 * 
	 * @param adminId
	 * @param pids
	 * @return
	 */
	void saveOrUpdateModels(Integer adminId, String[] pids,
			List<WebAdmin> sonAdminList) throws BusinessException,
			DataException;

	/**
	 * 根据管理员ID删除管理员权限
	 * 
	 * @param adminId
	 */
	void removeList(Integer adminId) throws BusinessException, DataException;

	WebAdmin getWebAdminByUserId(Integer id);

	/**
	 * 查询管理员信息，组装Page实例，并返回
	 * 
	 * @param eid
	 * @param initials
	 *            查询条件
	 * @param pageSize
	 *            分页每页显示信息的数量
	 * @return
	 * @throws BusinessException
	 */
	public Page getAdminListPageInfo(UserSessionObj obj, String initials,
			Integer pageSize, List<WebAdmin> list) throws BusinessException,
			DataException;

	/**
	 * 查询的管理员列表
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws DataException
	 */
	List<WebAdmin> getAdminList(Integer eid, String initials, Page page)
			throws BusinessException, DataException;

	/**
	 * 根据eid和mp查询出管理员信息
	 * 
	 * @return
	 */
	WebAdmin getWebAdminByMp(Integer eid, Long mp);

	/**
	 * 返回有效的角色信息
	 * 
	 * @return
	 */
	List<Role> findRoleBy();

}
