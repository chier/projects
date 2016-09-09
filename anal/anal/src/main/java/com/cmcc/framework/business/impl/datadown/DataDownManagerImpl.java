package com.cmcc.framework.business.impl.datadown;

import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.date.DateUtil;
import com.cmcc.framework.business.interfaces.datadown.IDataDownManager;
import com.cmcc.framework.business.interfaces.permission.IPermissionManager;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.model.datadown.FileShareInfo;
import com.cmcc.framework.model.datadown.FileShareSearcher;
import com.cmcc.framework.model.datadown.PilotInfo;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 文件共享 业务实现类
 * 
 * @author zhangzhanliang
 * 
 */
public class DataDownManagerImpl extends GenericDAOImpl implements
		IDataDownManager {

	private Logger logger = Logger.getLogger(DataDownManagerImpl.class);

	private IPermissionManager permissionManager;

	private ComboPooledDataSource dataSource;

	public IPermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(IPermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	public ComboPooledDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(ComboPooledDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List<ZtreeVO> findTreeByRid(int type, int rid) {
		// 创建树的集合
		List<ZtreeVO> l = new ArrayList<ZtreeVO>();

		// 根目录的添加
		ZtreeVO root = new ZtreeVO();
		root.setId(0);
		root.setpId(0);
		root.setName("文件管理");
		root.setOpen(true);
		l.add(root);
		root = null;

		// SQL 语句
		StringBuilder q = new StringBuilder(
				"SELECT `id`,`pid`,`node_name` FROM `file_share` WHERE `node_type` = 0");
		List<Object[]> objList = this.getHibernate_Anal().createSQLQuery(
				q.toString());

		Object[] obj = null;
		for (int i = 0; i < objList.size(); i++) {
			obj = objList.get(i);
			root = new ZtreeVO();
			root.setId((Integer) obj[0]);
			root.setpId((Integer) obj[1]);
			root.setName(String.valueOf(obj[2]));
			l.add(root);
		}
		return l;
	}

	public Long createFileShareDirectory(String name, String pid) {
		String q = "INSERT INTO `file_share`(`pid`,`node_name`,`createTime`,`node_type`) VALUE(?,?,?,0)";
		List l = new ArrayList();
		l.add(pid);
		l.add(name);
		l.add(new Date());
		return this.getHibernate_Anal().sqlInsert(q, l.toArray());
	}

	public void removeFileShareDirectory(Serializable id) {
		String q = "delete from `file_share` where `id` = " + id;
		getHibernate_Anal().sqlUpdate(q);

	}

	public void renameFileShareDirectory(String name, String id) {
		String q = "update `file_share` set `node_name`= ? where id = ?";
		List l = new ArrayList();
		l.add(name);
		l.add(id);
		getHibernate_Anal().sqlUpdate(q, l.toArray());

	}

	/**
	 * @return array[0] = 分页信息 array[1] = 列表信息 array[2] = 后缀下拉信息
	 */
	public Object[] findFileByFileShare(FileShareSearcher searcher,
			Integer intsize) {
		// 返回对象数组
		Object[] objArr = new Object[3];
		List list = new ArrayList();
		// 分页信息
		StringBuilder pageSql = new StringBuilder(
				"SELECT count(*) from `file_share` where `node_type` = '1'");
		pageSql.append(getWhere(searcher, list));
		List resultList = this.getHibernate_Anal().createSQLQuery(
				pageSql.toString(), list.toArray());
		long totalCount = ((BigInteger) resultList.get(0)).longValue();
		// 返回分页对象
		if (totalCount < 1) {
			totalCount = 0;
		}
		Page page = new Page(totalCount, intsize);
		page.setPage(searcher.getCurPage());
		objArr[0] = page;

		// 列表信息
		list = new ArrayList();
		StringBuilder q = new StringBuilder(
				"SELECT `id`,`pid`,`node_name`,`node_type`,`suffix`,`adminName`,`createTime`,`desc`,`size`,`path` from `file_share` where `node_type` = '1'");
		q.append(getWhere(searcher, list));
		q.append("order by `createTime` desc ");
		logger.info(q.toString());
		List<FileShareInfo> infoList = this.getHibernate_Anal().sqlQuery(
				q.toString(), page, FileShareInfo.class, list.toArray());
		objArr[1] = infoList;

		// 后缀下拉信息
		StringBuilder suffixSql = new StringBuilder(
				"select `suffix` from `file_share` group by `suffix`");
		List<String> suffixList = this.getHibernate_Anal().createSQLQuery(
				suffixSql.toString());
		List<String> notEmptyList = new ArrayList<String>();
		for (String suffix : suffixList) {
			if (!StringUtils.isBlank(suffix)) {
				notEmptyList.add(suffix);
				suffix = null;
			}
		}
		objArr[2] = notEmptyList;
		return objArr;
	}

	private String getWhere(FileShareSearcher searcher, List list) {
		StringBuilder bui = new StringBuilder();
		// 目录id
		if (searcher.getFsId() != null && searcher.getFsId().intValue() != 0) {
			bui.append(" and `pid` = ? ");
			list.add(searcher.getFsId());
		}
		// 文件名称
		if (!StringUtils.isBlank(searcher.getFileName())) {
			bui.append(" and `node_name` like '%" + searcher.getFileName()
					+ "%'");
			// list.add(searcher.getFileName());
		}
		// 文件类型
		if (!StringUtils.isBlank(searcher.getFileExt())) {
			bui.append(" and `suffix`=?");
			list.add(searcher.getFileExt());
		}
		// 起始时间
		if (!StringUtils.isBlank(searcher.getOperateTime())) {
			bui.append(" and `createTime` >= ?");
			list.add(DateUtil.toDate(searcher.getOperateTime()));
		}
		// 结束时间
		if (!StringUtils.isBlank(searcher.getEndTime())) {
			bui.append(" and `createTime` <= ?");
			list.add(DateUtil.toDate(searcher.getEndTime()));
		}
		return bui.toString();
	}

	public void removeFile(Serializable id) {
		String sql = "select `path` from `file_share` where `id` = " + id;
		List<String> list = this.getHibernate_Anal().createSQLQuery(sql);
		if (list != null && list.size() > 0) {
			String path = list.get(0);
			if (!StringUtils.isBlank(path)) {
				File file = new File(path);
				file.delete();
			}
		}

		String delSql = "delete from `file_share` where `id` = " + id;
		this.getHibernate_Anal().sqlUpdate(delSql);
	}

	public Long saveFileShare(FileShareInfo info) {
		String sql = "insert into `file_share`(`pid`,`node_name`,`node_type`,`suffix`,`adminName`,`createTime`,`desc`,`size`,`path`) value(?,?,?,?,?,?,?,?,?) ";
		List list = new ArrayList();
		list.add(info.getPid());
		list.add(info.getNode_name());
		list.add(info.getNode_type());
		list.add(info.getSuffix());
		list.add(info.getAdminName());
		list.add(new Date());
		list.add(info.getDesc());
		list.add(info.getSize());
		list.add(info.getPath());
		return getHibernate_Anal().sqlInsert(sql, list.toArray());
	}

	public FileShareInfo findFileShareInfoById(Serializable id) {
		StringBuilder q = new StringBuilder(
				"SELECT `id`,`pid`,`node_name`,`node_type`,`suffix`,`adminName`,`createTime`,`desc`,`size`,`path` from `file_share` where `id` = ?");
		List<FileShareInfo> list = this.getHibernate_Anal().sqlQuery(
				q.toString(), FileShareInfo.class, id);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<ZtreeVO> findPilotByAll() {
		List<ZtreeVO> zList = new ArrayList<ZtreeVO>();
		String sql = "select `id`,`pid`,`name` from `date_type`";

		List<Object[]> list = this.getHibernate_Anal().createSQLQuery(sql);
		ZtreeVO vo = null;
		Object[] arr;
		for (int i = 0; i < list.size(); i++) {
			vo = new ZtreeVO();
			arr = list.get(i);
			vo.setId((Integer) arr[0]);
			vo.setpId((Integer) arr[1]);
			if (vo.getpId() == 0) {
				vo.setOpen(true);
				vo.setNocheck(true);
			}
			vo.setName(arr[2].toString());

			zList.add(vo);
		}
		return zList;
	}

	@SuppressWarnings("unchecked")
	public List<PilotInfo> findPilotByPidAndRid(Integer pid, Integer rid) {
		String whereStr = "";
		if (rid != 0) {
			String pstr = this.permissionManager.findPcenterByRid(rid,
					PermissionMark.DataDownTree_permission.getValue());
			whereStr = " and `id` in (" + pstr + ")";
		}
		List<PilotInfo> zList = new ArrayList<PilotInfo>();
		String sql = "select `id`,`pid`,`name` from `date_type` where `pid`=? ";
		if (rid != 0) {
			sql += whereStr;
		}
		List<Object[]> list = this.getHibernate_Anal().createSQLQuery(sql, pid);
		PilotInfo vo = null;
		Object[] arr;
		for (int i = 0; i < list.size(); i++) {
			vo = new PilotInfo();
			arr = list.get(i);
			vo.setId((Integer) arr[0]);
			vo.setPid((Integer) arr[1]);
			vo.setName(arr[2].toString());
			zList.add(vo);
		}
		return zList;
	}
}
