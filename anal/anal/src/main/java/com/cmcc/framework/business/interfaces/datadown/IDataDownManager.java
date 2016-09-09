package com.cmcc.framework.business.interfaces.datadown;

import java.io.Serializable;
import java.util.List;

import com.cmcc.common.model.CommonObject;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.model.datadown.FileShareInfo;
import com.cmcc.framework.model.datadown.FileShareSearcher;
import com.cmcc.framework.model.datadown.PilotInfo;

/**
 * 数据管理 业务接口
 * 
 * @author zhangzhanliang
 * 
 */
public interface IDataDownManager {

	/**
	 * 根据角色权限 返回树的列表信息
	 * 
	 * @param type
	 *            0 表示只获取目录 1表示 获取所有信息
	 * @param rid
	 *            0 表示没有角色 其它值 表示角色的id
	 * @return
	 */
	List<ZtreeVO> findTreeByRid(int type, int rid);

	/**
	 * 重命名文件管理目录
	 * 
	 * @param name
	 * @param id
	 */
	void renameFileShareDirectory(String name, String id);

	/**
	 * 删除文件管理目录
	 * 
	 * @param id
	 */
	void removeFileShareDirectory(Serializable id);

	/**
	 * 创建文件共享目录
	 * 
	 * @param name
	 * @param pid
	 * @return
	 */
	Long createFileShareDirectory(String name, String pid);

	/**
	 * 存储fileShareinfo信息到DB
	 * 
	 * @param fileShare
	 * @return
	 */
	Long saveFileShare(FileShareInfo fileShare);

	/**
	 * 根据条件返回列表
	 * 
	 * @param searcher
	 *            条件集合 当前显示页数
	 * @return array[0] = 分页信息 array[1] = 列表信息 array[2] = 后缀下拉信息
	 */
	Object[] findFileByFileShare(FileShareSearcher searcher, Integer intsize);

	/**
	 * 删除文件操作
	 * 
	 * @param id
	 *            文件id
	 * 
	 */
	void removeFile(Serializable id);

	/**
	 * 根据id 返回信息
	 * 
	 * @param id
	 * @return
	 */
	FileShareInfo findFileShareInfoById(Serializable id);

	/**
	 * 返回所有试点分类信息
	 * 
	 * @return
	 */
	List<ZtreeVO> findPilotByAll();

	/**
	 * 根据pid 返回试点和试点下分类信息
	 * 
	 * @param pid
	 * @return
	 */
	List<PilotInfo> findPilotByPidAndRid(Integer pid,Integer rid);
}
