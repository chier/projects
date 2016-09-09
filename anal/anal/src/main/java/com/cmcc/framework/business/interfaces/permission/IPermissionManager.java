package com.cmcc.framework.business.interfaces.permission;

import java.util.List;

import com.cmcc.framework.controller.formbean.ZtreeVO;

/**
 * 权限管理业务模块
 * 
 * @author zhangzhanliang
 * 
 */
public interface IPermissionManager {

	/**
	 * 添加角色与权限关系表
	 * 
	 * @param rid
	 * @param pid
	 */
	int saveRolePermission(int rid, int ptype, int pid);

	/**
	 * 根据角色id 返回权限的 id 信息
	 * 
	 * @return
	 */
	List<Integer> findPermissionByRid(int rid);

	/**
	 * 保存或者修改 Permission 对象
	 * 
	 * @param pid
	 * @param pcenter
	 * @param pdefault
	 * @param mid
	 * @param type
	 * @return
	 */
	int saveAndUpdatePermission(int rid, String pcenter, int mid, int type);

	/**
	 * 根据角色 id 和权限类型 返回权限的值
	 * 
	 * @param rid
	 *            角色id
	 * @param ptype
	 *            权限类型
	 * @return
	 */
	String findPcenterByRid(int rid, int ptype);

	/**
	 * 返回定制分析的所有根节点
	 * 
	 * @return
	 */
	List<ZtreeVO> findCustomByTree(int rid);

	/**
	 * 返回即席查询的所有树节点信息
	 * 
	 * @param rid
	 * @param map
	 *            存储id和名称信息
	 * @return
	 */
	List<ZtreeVO> findQueriesByTree(int rid);

	/**
	 * 返回原始数据的所有树节点信息
	 * 
	 * @param rid
	 * @return
	 */
	List<ZtreeVO> findRowDataByTree(int rid);
	
	/**
	 * 返回分析报告的所有树节点信息
	 * 
	 * @param rid
	 * @return
	 */
	List<ZtreeVO> findCenterByTree(int rid);
	
	/**
	 * 返回分析报告的所有树节点信息
	 * 
	 * @param rid
	 * @return
	 */
	List<ZtreeVO> findDataDownByTree(int rid);

	/**
	 * 返回 loap 的所有树节点信息
	 * 
	 * @param rid
	 * @return
	 */
	List<ZtreeVO> findLoapByTree(int rid);

	/**
	 * 根据角色 返回有权限的目录或者文件名
	 * 
	 * @param rid
	 * @return
	 */
	List<String> findRawDataPerByRid(int rid);
	
}
