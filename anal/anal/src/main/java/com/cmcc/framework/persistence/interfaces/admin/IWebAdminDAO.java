package com.cmcc.framework.persistence.interfaces.admin;

import java.util.List;

import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.exception.DataException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

/**
 * 管理员操作接口
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 * @param <T>
 */
public interface IWebAdminDAO<T extends WebAdmin> extends IBaseDAO<WebAdmin> {

	/**
	 * 根据用户名返回用户信息
	 * 
	 * @param adminName
	 * @return
	 */
	WebAdmin findByAdminName(String adminName);

	/**
	 * 根据id 查找对象
	 * 
	 * @param id
	 * @return
	 */
	public WebAdmin getById(Integer id);

	/**
	 * 根据查询条件返回Page对象
	 * 
	 * @param pagesize
	 * @param name
	 * @param eid
	 * @return
	 */
	public Page getPageLikeBySearchList(int pagesize, String name, Integer eid);

	/**
	 * 根据查询条件返回模糊查询
	 * 
	 * @param name
	 * @param eid
	 * @param page
	 * @return
	 */
	public List<WebAdmin> getAllLikeBySearchListPage(String name, Integer eid,
			Page page);

	/**
	 * 根据输入的名字，判断数据库中同一个企业下是否存在同名
	 * 
	 * @param name
	 * @param eid
	 * @return true 表示没有同名 false 表示有同名
	 */
	public boolean getSameName(String name, Integer adminid);

	/**
	 * 根据企业代码和用户名获得唯一用户
	 * 
	 * @param name
	 * @param code
	 * @return
	 */
	public WebAdmin getAdminByLoginId(String name, String code);

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
	 * 根据eid和mp查询出管理员信息
	 * 
	 * @return
	 */
	WebAdmin getWebAdminByMp(Integer eid, Long mp);

	/**
	 * 保存或更新管理员权限对象
	 * 
	 * @param adminId
	 * @param pids
	 * @return
	 */
	void saveOrUpdate(Integer adminId, String[] pids);

	/**
	 * 根据管理员ID删除管理员权限
	 * 
	 * @param adminId
	 */
	void removeList(Integer adminId) throws BusinessException, DataException;

	WebAdmin getWebAdminByUserId(Integer id);

	/**
	 * 查询企业管理员的总人数
	 * 
	 * @param eid
	 *            不可为空，空将抛出DataException
	 * @param initials
	 *            查询条件
	 * @return
	 * @throws DataException
	 */
	Integer findAdminListCount(Integer eid, String initials)
			throws DataException;

	/**
	 * 查找企业管理员列表，可分页
	 * 
	 * @param eid
	 *            不可为空，空将抛出DataException
	 * @param initials
	 * @param firstResult
	 * @param pageSize
	 * @return
	 * @throws DataException
	 */
	List<WebAdmin> getAdminList(Integer eid, String initials,
			Integer firstResult, Integer pageSize) throws DataException;

	/**
	 * modefiy yangshuo 2009-10-12 逻辑删除管理员信息
	 * 
	 * @param adminId
	 * @return
	 */
	void removeAdmin(Integer adminId) throws BusinessException, DataException;

	/**
	 * 查询 父管理下所有子管理员
	 * 
	 * @param eid
	 * @param parentId
	 * @param searchName
	 * @return
	 * @throws DataException
	 */
	List<WebAdmin> fineByParentId(Integer eid, Integer parentId,
			String searchName) throws DataException;

	/**
	 * 查询 父管理下所有子管理员
	 * 
	 * @param eid
	 * @param parentId
	 * @param searchName
	 * @return
	 * @throws DataException
	 */
	List<WebAdmin> fineByParentId(Integer eid, String searchName, Page page,
			List<WebAdmin> list, Integer parId) throws DataException;
	/**
	 * 修改管理员到未分配部门中
	 * @param deptIds
	 */
	void updateAdminByDept(List<String> deptIds);
	
	/**
	 * 根据条件集合，查询所有管理员
	 * @param searchCond
	 * 			条件集合
	 * @return
	 */
	List<WebAdmin> findBy(SearchCondition searchCond);
}
