package com.cmcc.framework.business.impl.custom;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cmcc.common.Global;
import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.business.interfaces.custom.ICustomManager;
import com.cmcc.framework.business.interfaces.permission.IPermissionManager;
import com.cmcc.framework.business.interfaces.queries.IQueriesManager;
import com.cmcc.framework.controller.formbean.CustomVO;
import com.cmcc.framework.controller.formbean.QueriesVO;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.model.model.Custom;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 即席查询 manager impl
 * 
 * @author zhangzhanliang
 * 
 */
public class CustomManagerImpl extends GenericDAOImpl implements ICustomManager {
	private Logger logger = Logger.getLogger(CustomManagerImpl.class);

	private IPermissionManager permissionManager;

	private ComboPooledDataSource dataSource;

	public ComboPooledDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(ComboPooledDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public IPermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(IPermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	@SuppressWarnings("unchecked")
	public List<CustomVO> findByGroup() {
		// 创建查询
		List<CustomVO> l = new ArrayList<CustomVO>();
		String q = "SELECT id, node_name, node_type FROM  costomana WHERE pid = 0 GROUP BY node_name ORDER BY id";

		List<Object[]> countlist = getHibernate_Anal().createSQLQuery(q);

		CustomVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null && !"".equals(countlist.get(i))) {
				int leaf = (Integer) (countlist.get(i))[2];
				vo = new CustomVO();
				vo.setIdentifier((Integer) (countlist.get(i))[0]);
				vo.setIsLeaf(leaf);
				vo.setModelName((countlist.get(i))[1].toString());
				vo.setParentId(0);
				if (leaf == 1) {
					vo.setActionUrl("/manage/custom/customAction."
							+ Global.ACTION_EXT);
				} else {
					vo.setActionUrl("javascript:;");
				}

				// vo.setActionUrl("/manage/queries/queriesAction." +
				// Global.ACTION_EXT);
				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<CustomVO> findByObject(String groupName, String id) {
		if (!StringUtil.isNumeric(id)) {
			id = "00";
		}

		// 创建查询
		List<CustomVO> l = new ArrayList<CustomVO>();
		String q = "SELECT id,node_name,node_type  FROM  costomana where `pid` = "
				+ id;

		List<Object[]> countlist = getHibernate_Anal().createSQLQuery(q);

		CustomVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new CustomVO();
				vo.setIdentifier((Integer) al[0]);
				vo.setIsLeaf((Integer) al[2]);
				// vo.setTableCode(String.valueOf(al[0]));
				vo.setSql("");
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(Integer.parseInt(id));
				if ((Integer) al[2] == 0) {
					vo.setActionUrl("javascript:;");
				} else {
					vo
							.setActionUrl("/manage/custom/customAction!flashTableFrame."
									+ Global.ACTION_EXT
									+ "?id="
									+ (Integer) al[0]
									+ "&customTitle="
									+ vo.getModelName());
				}

				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<CustomVO> findByObject(String groupName, String id, int rid) {
		if (!StringUtil.isNumeric(id)) {
			id = "00";
		}
		String pceter = this.permissionManager.findPcenterByRid(rid,
				PermissionMark.CustomTree_permission.getValue());
		// 创建查询
		List<CustomVO> l = new ArrayList<CustomVO>();
		String q = "SELECT id,node_name,node_type  FROM  costomana where `pid` = "
				+ id + " and `id` in (" + pceter + ")";

		List<Object[]> countlist = getHibernate_Anal().createSQLQuery(q);

		CustomVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new CustomVO();
				vo.setIdentifier((Integer) al[0]);
				vo.setIsLeaf((Integer) al[2]);
				// vo.setTableCode(String.valueOf(al[0]));
				vo.setSql("");
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(Integer.parseInt(id));
				if ((Integer) al[2] == 0) {
					vo.setActionUrl("javascript:;");
				} else {
					vo
							.setActionUrl("/manage/custom/customAction!flashTableFrame."
									+ Global.ACTION_EXT
									+ "?id="
									+ (Integer) al[0]
									+ "&customTitle="
									+ vo.getModelName());
				}

				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<CustomVO> findByTable(String talCode, String id) {

		if (!StringUtil.isNumeric(id)) {
			id = "000";
		}

		// 创建查询
		List<CustomVO> l = new ArrayList<CustomVO>();
		String sql = "SELECT c.`TBL_CODE`, c.`TBL_NAME` FROM `cen_object_table_rel` b,`logical_table` c WHERE b.tbl_code=c.tbl_code;";
		List<Object[]> countlist = getHibernate_S9999().createSQLQuery(sql);

		CustomVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new CustomVO();
				vo.setIdentifier(Integer.valueOf(j + id));
				vo.setIsLeaf(1);
				vo.setTableCode(String.valueOf(al[0]));
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(0);
				vo.setActionUrl("/manage/queries/queriesAction."
						+ Global.ACTION_EXT);
				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public Object[] findByTableItem(String tableName, int curPage, int pageSize) {
		Object[] tableObject = new Object[3];
		String itemSql = "SELECT `ITEM_CODE` FROM `tbl_item` WHERE `tbl_code` = '"
				+ tableName + "'";
		List<String> itemList = getHibernate_S9999().createSQLQuery(itemSql);

		String countSql = "SELECT count(1) from " + tableName;
		List r = getHibernate_S9999().createSQLQuery(countSql);

		long totalresult = 0;
		if (r != null && r.size() > 0) {
			totalresult = Long.valueOf(r.get(0).toString());
			;
		}
		if (totalresult < 1) {
			totalresult = 0;
		}
		Page page = new Page(totalresult, pageSize);
		page.setPage(curPage);

		StringBuilder bu = new StringBuilder("SELECT ");
		for (int i = 0; i < itemList.size(); i++) {
			bu.append("`").append(itemList.get(i)).append("`");
			if (i != itemList.size() - 1) {
				bu.append(",");
			}
		}
		bu.append(" from " + tableName);
		if (logger.isDebugEnabled())
			logger.debug(bu.toString());
		List<Object[]> tableList = getHibernate_S9999().sqlQuery(bu.toString(),
				page);

		tableObject[0] = itemList; // 字段名称信息
		tableObject[1] = tableList; // 表信息
		tableObject[2] = page; // 页数

		return tableObject;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findByDesignatedTable(String sql) {
		List<Object[]> objs = new ArrayList<Object[]>();
		objs = getHibernate_S9999().createAliasSQLQuery(sql);

		return objs;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findTableHeader(String sql) {
		List<Object[]> objs = new ArrayList<Object[]>();
		objs = getHibernate_S9999().createAliasSQLQuery(sql);

		return objs;
	}

	public int insert(String name, String memo, String imgConfig, String sv,
			String pid) {
		String hql = "insert into `costomana` (`pid`,`node_name`,`node_type`,`node_sql`,`img_config`,`memo`) values (?,?,1,?,?,?)";
		return this.getHibernate_Anal().sqlUpdate(hql, Integer.valueOf(pid),
				name, sv, imgConfig, memo);
	}

	public int update(String id, String name, String memo, String imgConfig,
			String sv, String pid) {
		String hql = "update `costomana` set img_config=? where id=" + id;
		// hql += "name"
		return this.getHibernate_Anal().sqlUpdate(hql, imgConfig);
	}

	public String getSql(String id) {
		String hql = "select node_sql from `costomana` where id = " + id;
		Object img_config = this.getHibernate_Anal().createSQLQuery(hql).get(0);// .toString();
		String sql = "";
		if (img_config == null) {
			sql = "";
		} else {
			sql = img_config.toString().replace("\r|\n", "");
		}
		return sql;
	}

	public String getChartSet(String id) {
		String hql = "select img_config from `costomana` where id = " + id;
		Object img_config = this.getHibernate_Anal().createSQLQuery(hql).get(0);// .toString();
		String sql = "";
		if (img_config == null) {
			sql = "";
		} else {
			sql = img_config.toString().replace("\r|\n", "");
		}
		return sql;
	}

	public List<CustomVO> findByGroup(int rid) {
		// 创建查询
		List<CustomVO> l = new ArrayList<CustomVO>();
		String pcenter = this.permissionManager.findPcenterByRid(rid,
				PermissionMark.CustomTree_permission.getValue());
		String q = "SELECT id, node_name, node_type FROM  costomana where id in(?) group by node_name order by id ;";

		List<Object[]> countlist = this.getHibernate_Anal().createSQLQuery(q,
				pcenter);

		CustomVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null && !"".equals(countlist.get(i))) {
				int leaf = (Integer) (countlist.get(i))[2];
				vo = new CustomVO();
				vo.setIdentifier((Integer) (countlist.get(i))[0]);
				vo.setIsLeaf(leaf);
				vo.setModelName((countlist.get(i))[1].toString());
				vo.setParentId(0);
				vo.setActionUrl("javascript:void(0);");
				// vo.setActionUrl("/manage/queries/queriesAction." +
				// Global.ACTION_EXT);
				l.add(vo);
			}
		}
		return l;
	}

	public List<ZtreeVO> findByAll() {
		// 创建查询
		List<ZtreeVO> l = new ArrayList<ZtreeVO>();
		String q = "SELECT id, pid,node_name, node_type FROM  costomana";

		List<Object[]> countlist = this.getHibernate_Anal().createSQLQuery(q);

		ZtreeVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null && !"".equals(countlist.get(i))) {
				vo = new ZtreeVO();
				vo.setId((Integer) (countlist.get(i))[0]);
				vo.setpId((Integer) (countlist.get(i))[1]);
				vo.setName((countlist.get(i))[2].toString());
				l.add(vo);
			}
		}
		return l;
	}

	/**
	 * type 1 表示提取所有 0 表示对应的目录
	 * rid	0 表示超级管理员角色  其它值表示角色ID
	 */
	public List<ZtreeVO> findTreeByTypeAndRid(int type, int rid) {
		
		String pcenter = this.permissionManager.findPcenterByRid(rid,
				PermissionMark.CustomTree_permission.getValue());
		
		// 创建查询
		List<ZtreeVO> l = new ArrayList<ZtreeVO>();
		StringBuilder q = new StringBuilder("SELECT id,pid,node_name,node_type  FROM  costomana");
		
		if(type != 1){
			q.append(" where `node_type` = " + type);
		}
		
		if(rid != 0){
			if(StringUtils.indexOf(q.toString(), "where") == -1){
				q.append(" where ");
			}else{
				q.append(" and ");
			}
			q.append(" `id` in (" + pcenter + ")");
		}
		
		List<Object[]> countlist = getHibernate_Anal().createSQLQuery(q.toString());

		ZtreeVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new ZtreeVO();
				vo.setId((Integer) al[0]);
				vo.setpId((Integer) (al[1]));
				vo.setName(String.valueOf(al[2]));
				vo.setType((Integer)al[3]);
				l.add(vo);
			}
		}
		return l;
	}

	public Long addTreeDirectory(String name, String pid) {
		String q = "INSERT INTO `costomana` (`pid`,`node_name`,`node_type`) VALUE ("
			+ pid + ",'" + name + "',0)";
	return this.getHibernate_Anal().sqlInsert(q, null);
	}

	public void removeTreeDirectory(Serializable id) {
		String q = "delete from `costomana` where `id` = " + id;
		this.getHibernate_Anal().sqlUpdate(q);
		
	}

	public void renameTreeDirectory(String name, String id) {
		String q = "UPDATE `costomana` SET `node_name` = ? WHERE id = ?";
		List list = new ArrayList();
		list.add(name);
		list.add(id);
		this.getHibernate_Anal().sqlUpdate(q, list.toArray());
	}
}
