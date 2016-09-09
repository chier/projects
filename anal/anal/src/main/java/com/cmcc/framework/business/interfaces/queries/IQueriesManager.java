package com.cmcc.framework.business.interfaces.queries;

import java.util.List;
import java.util.Map;

import com.cmcc.common.persistence.support.Page;
import com.cmcc.framework.controller.formbean.ItemTableVO;
import com.cmcc.framework.controller.formbean.QueriesVO;
import com.cmcc.framework.model.Structure;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 即席查询 manager
 * 
 * @author zhangzhanliang
 * 
 */
public interface IQueriesManager {

	ComboPooledDataSource getDataSource();

	void setDataSource(ComboPooledDataSource dataSource);

	/**
	 * 返回 group 类型的数据
	 * 
	 * @return
	 */
	List<QueriesVO> findByGroup();
	
	/**
	 * 返回 group 类型的数据
	 * 
	 * @return
	 */
	List<QueriesVO> findByGroupAndYear(String year);
	
	/**
	 * 返回 group 类型的数据
	 * @param rid 角色id
	 * @return
	 */
	List<QueriesVO> findByGroup(int rid,int ptype);
	
	/**
	 * 返回 group 类型的数据
	 * @param rid 角色id
	 * @return
	 */
	List<QueriesVO> findByGroupAndYear(String year,int rid,int ptype);
	
	List<QueriesVO> findStructureByPid(Integer pid,int rid,int ptype);
	
	/**
	 * 返回对象类型的数据
	 * @param type 1= 表示 单个  2 表示多个
	 * @return
	 */
	List<QueriesVO> findByObject(String groupName, String id,int type,Map<String,Integer> idMap);
	
	/**
	 * 返回对象类型的数据
	 * @param type 1= 表示 单个  2 表示多个
	 * @return
	 */
	List<QueriesVO> findByObject(String groupName, String id);
	
	/**
	 * 返回对象类型的数据
	 * @param type 1= 表示 单个  2 表示多个
	 * @return
	 */
	List<QueriesVO> findByObjectByCodes(String codes,Integer sid);
	
	
	List<QueriesVO> findByObjectByCodes(String codes,Integer sid,int rid,int type);
	
	
	/**
	 * 返回对象类型的数据
	 * @param type 1= 表示 单个  2 表示多个
	 * @param rid 表示 角色id
	 * @return
	 */
	List<QueriesVO> findByObject(String groupName, String id,int rid,int type);

	/**
	 * 返回 表类型的 类型的数据
	 * 
	 * @param talCode
	 *            表的 code
	 * @return
	 * 
	 */
	List<QueriesVO> findByTable(String talCode, String id);
	
	/**
	 * 返回 表类型的 类型的数据
	 * 
	 * @param talCode
	 *            表的 code
	 * @param rid
	 * 			角色id
	 * @return
	 * 
	 */
	List<QueriesVO> findByTable(String talCode, String id,int rid,int ptype);
	
	/**
	 * 返回 表类型的 类型的数据
	 * 
	 * @param talCode
	 *            表的 code
	 * @parm type 1 表示单个  2表示多个
	 * 
	 * @return
	 * 
	 */
	List<QueriesVO> findByTable(String talCode, String id,int type,Map<String,Integer> idMap);

	/**
	 * 返回右侧字段头信息
	 * 
	 * @param tableName
	 * @return
	 */
	List<ItemTableVO> findItemTableBy(String tableName);

	/**
	 * 返回传递的数组
	 * 
	 * @param talCode
	 *            表名称
	 * @return objcet object[0] 表示的是字段的值 object[1] page 信息
	 */
	Object[] findByTableItem(String tableName, List<ItemTableVO> voList,
			String searchValue, int curPage, int pageSize);

	/**
	 * 
	 * @param tableName
	 * @param voList
	 * @param searchValue
	 * @return
	 */
	List<Object[]> findByTableItem(String tableName, List<ItemTableVO> voList,
			String searchValue);

	/**
	 * 
	 * @param tableName
	 * @param voList
	 * @param searchValue
	 * @return
	 */
	List<Object[]> findPageByTableItem(String tableName, List<ItemTableVO> voList,
			String searchValue,Page page);
	/**
	 * 查询总数
	 * 
	 * @param tableName
	 * @param voList
	 * @param searchValue
	 * @return
	 */
	long findCountByTableItem(String tableName, List<ItemTableVO> voList,
			String searchValue);
	
	void aaPlate014();
	
	List<QueriesVO> findStructureByPid(Integer pid);
	
	Structure findStructureById(Integer id);
}
