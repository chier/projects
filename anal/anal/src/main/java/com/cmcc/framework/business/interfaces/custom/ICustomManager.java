package com.cmcc.framework.business.interfaces.custom;

import java.io.Serializable;
import java.util.List;

import com.cmcc.framework.controller.formbean.CustomVO;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 定制分析 manager
 * 
 * @author wangbo
 * 
 */
public interface ICustomManager {

	ComboPooledDataSource getDataSource();

	void setDataSource(ComboPooledDataSource dataSource);

	/**
	 * 返回所有有效的节点信息
	 * 
	 * @return
	 */
	List<ZtreeVO> findByAll();

	/**
	 * 返回 group 类型的数据
	 * 
	 * @return
	 */
	List<CustomVO> findByGroup();

	/**
	 * 根据权限 返回对象
	 * 
	 * @param rid
	 *            角色id
	 * @return
	 */
	List<CustomVO> findByGroup(int rid);

	/**
	 * 返回对象类型的数据
	 * 
	 * @return
	 */
	List<CustomVO> findByObject(String groupName, String id);

	/**
	 * 根据角色返回对象类型的数据
	 * 
	 * @return
	 */
	List<CustomVO> findByObject(String groupName, String id, int rid);

	/**
	 * 返回 表类型的 类型的数据
	 * 
	 * @param talCode
	 *            表的 code
	 * @return
	 * 
	 */
	List<CustomVO> findByTable(String talCode, String id);

	/**
	 * 返回传递的数组
	 * 
	 * @param talCode
	 *            表名称
	 * @return objcet[0] 表示的是字段名称 object[1] 表示的是字段的值 与 object[0] 相对应
	 * 
	 * object[2] page 信息
	 */
	Object[] findByTableItem(String tableName, int curPage, int pageSize);

	/**
	 * 返回查询指定的sql
	 * 
	 * @param sql
	 * @return
	 */
	List<Object[]> findByDesignatedTable(String sql);

	/**
	 * 动态查询表头
	 * 
	 * @param sql
	 * @return
	 */
	List<Object[]> findTableHeader(String sql);

	/**
	 * 添加数据库
	 * 
	 * @param name
	 * @param memo
	 * @param imgConfig
	 * @param sv
	 * @param pid
	 * @return
	 */
	int insert(String name, String memo, String imgConfig, String sv, String pid);

	int update(String id, String name, String memo, String imgConfig,
			String sv, String pid);

	/**
	 * 查询要执行的sql语句
	 * 
	 * @param id
	 * @return
	 */
	String getSql(String id);

	String getChartSet(String id);

	/**
	 * 增加目录
	 * 
	 * @param vo
	 */
	Long addTreeDirectory(String name,String pid);

	/**
	 * 删除树目录
	 * 
	 * @param vo
	 */
	void removeTreeDirectory(Serializable id);
	/**
	 * 重命名树目录
	 * 
	 * @param vorenameDirectoryTree
	 */
	void renameTreeDirectory(String name,String id);
	
	
	/**
	 * 根据类型和角色 返回权限下的定制分析目录
	 * @param type
	 * 		type 1 表示提取所有 0 表示对应的目录
	 * 		rid	0 表示超级管理员角色  其它值表示角色ID
	 * @param rid
	 * @return
	 */
	List<ZtreeVO> findTreeByTypeAndRid(int type,int rid);
}
