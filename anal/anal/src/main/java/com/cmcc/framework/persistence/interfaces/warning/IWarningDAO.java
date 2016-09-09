package com.cmcc.framework.persistence.interfaces.warning;

import java.io.Serializable;
import java.util.List;

import com.cmcc.common.persistence.generic.interfaces.IGenericDAO;
import com.cmcc.common.persistence.query.QueryTemplateDTO;
import com.cmcc.framework.controller.formbean.ZtreeVO;

/**
 * 预警分析 dao
 * 
 * @author zhangzhanliang
 * 
 */
public interface IWarningDAO extends IGenericDAO {
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
	 * 返回所有试点信息
	 * 
	 * @return
	 */
	List<String> findAllS999StatUnit();

	/**
	 * 增加目录
	 * 
	 * @param vo
	 */
	Long addTreeDirectory(String name,String pid);;

	/**
	 * 删除树目录
	 * 
	 * @param vo
	 */
	void removeTreeDirectory(Serializable id);

	/**
	 * 删除树目录
	 * 
	 * @param vo
	 */
	void renameTreeDirectory(String name,String id);

	/**
	 * 删除 预警分析模板
	 * @param id
	 */
	void removeTemplate(Serializable id);
	
	/**
	 * 保存设置默认值
	 * @param id
	 * @param min
	 * @param max
	 */
	void saveSettingDefault(int id,int min,int max);
	/**
	 * 重命名 预警分析模板
	 * @param name
	 * @param id
	 */
//	void renameTemplate(String name,String id);
}
