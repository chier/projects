package com.cmcc.framework.persistence.interfaces.role;

import java.util.List;

import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.model.role.Role;
import com.cmcc.framework.persistence.interfaces.IBaseDAO;

/**
 * 角色数据库查询接口
 * 
 * @author <a href="mailto:songwei@fetionyy.com.cn">song wei</a>
 * @version $Revision: 1.1 $
 * @since 2009-9-7
 */

public interface IRoleDAO<T extends Role> extends IBaseDAO<Role> {
	/**
	 * 取企业下所有角色
	 * 
	 * @param eId
	 * @return
	 */
	List<Role> findByAll();
	
	/**
	 * 由角色名称取角色
	 * 
	 * @param eid
	 * @param roleName
	 * @return
	 */
	Role findByName(String roleName);
	
	/**
	 * 根据条件集合 返回信息
	 * @param search
	 * @return
	 */
	List<Role> findBy(SearchCondition search);
	
}
