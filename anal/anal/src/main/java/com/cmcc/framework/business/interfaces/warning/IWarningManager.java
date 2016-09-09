package com.cmcc.framework.business.interfaces.warning;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.cmcc.common.persistence.query.QueryTemplateDTO;
import com.cmcc.framework.controller.formbean.WarningVO;
import com.cmcc.framework.controller.formbean.ZtreeVO;

/**
 * 预警分析 manager
 * 
 * @author Administrator
 * 
 */
public interface IWarningManager {
	/**
	 * 保存或者更新方法
	 * 
	 * @param queryTemplateDTO
	 */
	void saveOrUpdate(QueryTemplateDTO queryTemplateDTO);

	/**
	 * 返回预警分析 类型字段和code信息
	 * 
	 * @param rid
	 *            角色
	 * @return
	 */
	List<ZtreeVO> findTreeTypeAndCodeByRid(int rid);

	/**
	 * 查找目录树 根据类型和角色
	 * 
	 * @param type
	 *            类型 1 表示全部 0 表示只获取目录
	 * @param rid
	 *            角色 id 
	 *             0 表示全部
	 *             其它值表示对应的角色
	 * @return
	 */
	List<ZtreeVO> findDisctoryTreeByTypeAndRid(int type, int rid)
			throws SQLException, IOException;

	/**
	 * 返回所有试点信息
	 * 
	 * @return
	 */
	List<String> findAllS999StatUnit();

	/**
	 * 根据自己写的SQL 查询数据
	 * 
	 * @param vo
	 * @return
	 */
	List findListByVOSQL(WarningVO vo);

	/**
	 * 保存 或者修改 WarningVO
	 * 
	 * @param vo
	 * @return
	 */
	int saveOrUpdateWarning(WarningVO vo);

	/**
	 * 根据代码 返回 模板信息
	 * 
	 * @param templeCode
	 * @return
	 */
	WarningVO findWarningVoByCode(String templeCode);

	
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
	 * 删除 预警分析模板
	 * @param id
	 */
	void removeTemplate(Serializable id);
	/**
	 * 重命名 预警分析模板
	 * @param name
	 * @param id
	 */
	void renameTemplate(String name,String id);
	
	
	/**
	 * 保存设置默认值
	 * @param id
	 * @param min
	 * @param max
	 */
	void saveSettingDefault(int id,int min,int max);
	
	/**
	 * 查询目录下所有的子目录
	 * 
	 * @return
	 */
//	List<ZtreeVO> queryTreeChildren(Serializable pid);
	
}
