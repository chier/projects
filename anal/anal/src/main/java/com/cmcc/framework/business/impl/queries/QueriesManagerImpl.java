package com.cmcc.framework.business.impl.queries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cmcc.common.Global;
import com.cmcc.common.config.LoadConfig;
import com.cmcc.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.business.interfaces.permission.IPermissionManager;
import com.cmcc.framework.business.interfaces.queries.IQueriesManager;
import com.cmcc.framework.controller.formbean.CodeExprVO;
import com.cmcc.framework.controller.formbean.ItemTableVO;
import com.cmcc.framework.controller.formbean.QueriesVO;
import com.cmcc.framework.model.Structure;
import com.cmcc.framework.persistence.interfaces.StructureDAO;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 即席查询 manager impl
 * 
 * @author zhangzhanliang
 * 
 */
public class QueriesManagerImpl extends GenericDAOImpl implements
		IQueriesManager {
	private Logger logger = Logger.getLogger(QueriesManagerImpl.class);

	private ComboPooledDataSource dataSource;

	private IPermissionManager permissionManager;

	private StructureDAO structureDAO;

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

	public StructureDAO getStructureDAO() {
		return structureDAO;
	}

	public void setStructureDAO(StructureDAO structureDAO) {
		this.structureDAO = structureDAO;
	}

	@SuppressWarnings("unchecked")
	public List<QueriesVO> findByGroup() {
		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String q = "SELECT CEN_GROUP  FROM  cen_object  group by CEN_GROUP ;";

		List<String> countlist = getHibernate_S9999().createSQLQuery(q);

		QueriesVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null && !"".equals(countlist.get(i))) {
				vo = new QueriesVO();
				vo.setIdentifier(j);
				vo.setIsLeaf(0);
				vo.setModelName(countlist.get(i));
				vo.setTableCode(countlist.get(i));
				vo.setParentId(0);
				vo.setActionUrl("javascript:void(0)");
				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<QueriesVO> findByGroupAndYear(String year) {
		String val = (String) LoadConfig.getInstance().getSurveyGroup()
				.get(year);
		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String q = "SELECT CEN_GROUP  FROM  cen_object where CEN_GROUP like '"
				+ val + "'  group by CEN_GROUP ;";

		List<String> countlist = getHibernate_S9999().createSQLQuery(q);

		QueriesVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null && !"".equals(countlist.get(i))) {
				vo = new QueriesVO();
				vo.setIdentifier(j);
				vo.setIsLeaf(0);
				vo.setModelName(countlist.get(i));
				vo.setTableCode(countlist.get(i));
				vo.setParentId(0);
				vo.setActionUrl("javascript:void(0)");
				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<QueriesVO> findByGroup(int rid, int ptype) {
		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String pcenter = this.permissionManager.findPcenterByRid(rid, ptype);
		String q = "SELECT CEN_GROUP  FROM  cen_object ";
		if (!StringUtil.isBlank(pcenter)) {
			q += " where `CEN_GROUP` in (" + pcenter + ") ";
		}
		q += " group by CEN_GROUP ";

		List<String> countlist = getHibernate_S9999().createSQLQuery(q);

		QueriesVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null && !"".equals(countlist.get(i))) {
				vo = new QueriesVO();
				vo.setIdentifier(j);
				vo.setIsLeaf(0);
				vo.setModelName(countlist.get(i));
				vo.setTableCode(countlist.get(i));
				vo.setParentId(0);
				vo.setActionUrl("javascript:void(0)");
				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<QueriesVO> findByGroupAndYear(String year, int rid, int ptype) {
		String val = (String) LoadConfig.getInstance().getSurveyGroup()
				.get(year);

		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String pcenter = this.permissionManager.findPcenterByRid(rid, ptype);
		String q = "SELECT CEN_GROUP  FROM  cen_object where CEN_GROUP like '"
				+ val + "'";
		if (!StringUtil.isBlank(pcenter)) {
			q += " and `CEN_GROUP` in (" + pcenter + ") ";
		}
		q += " group by CEN_GROUP ";

		List<String> countlist = getHibernate_S9999().createSQLQuery(q);

		QueriesVO vo = null;
		for (int i = 0, j = 1; i < countlist.size(); i++, j++) {
			if (countlist.get(i) != null && !"".equals(countlist.get(i))) {
				vo = new QueriesVO();
				vo.setIdentifier(j);
				vo.setIsLeaf(0);
				vo.setModelName(countlist.get(i));
				vo.setTableCode(countlist.get(i));
				vo.setParentId(0);
				vo.setActionUrl("javascript:void(0)");
				l.add(vo);
			}
		}
		return l;
	}

	public List<QueriesVO> findByObject(String groupName, String id) {
		if (!StringUtil.isNumeric(id)) {
			id = "00";
		}

		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String q = "SELECT CEN_OBJECT_CODE,CEN_OBJECT_NAME  FROM  cen_object where `CEN_GROUP` ";
		q += " = '" + groupName + "'";

		List<Object[]> countlist = getHibernate_S9999().createSQLQuery(q);

		QueriesVO vo = null;
		for (int i = 0, j = 1, s = countlist.size(); i < s; i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new QueriesVO();
				vo.setIdentifier(Integer.valueOf(j + id));
				vo.setIsLeaf(0);
				vo.setTableCode(String.valueOf(al[0]));
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(0);
				vo.setActionUrl("javascript:void(0)");
				l.add(vo);
			}
		}
		return l;
	}

	@Override
	public List<QueriesVO> findByObjectByCodes(String codes, Integer sid) {
		String[] arr = codes.split(",");
		StringBuffer sbCodes = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sbCodes.append("'").append(arr[i]).append("'");
			if (i < arr.length - 1) {
				sbCodes.append(",");
			}
		}

		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String q = "SELECT CEN_OBJECT_CODE,CEN_OBJECT_NAME  FROM  cen_object where `CEN_OBJECT_CODE` in ("
				+ sbCodes.toString() + ")";

		List<Object[]> countlist = getHibernate_S9999().createSQLQuery(q);

		QueriesVO vo = null;
		for (int i = 0, j = 1, s = countlist.size(); i < s; i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new QueriesVO();
				vo.setIdentifier(Integer.valueOf(j + sid));
				vo.setIsLeaf(0);
				vo.setTableCode(String.valueOf(al[0]));
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(0);
				vo.setActionUrl("javascript:void(0)");
				l.add(vo);
			}
		}
		return l;
	}
	
	
	@Override
	public List<QueriesVO> findByObjectByCodes(String codes, Integer sid,
			int rid, int ptype) {
		String[] arr = codes.split(",");
		StringBuffer sbCodes = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sbCodes.append("'").append(arr[i]).append("'");
			if (i < arr.length - 1) {
				sbCodes.append(",");
			}
		}
		
		String pcenter = this.permissionManager.findPcenterByRid(rid, ptype);
		

		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String q = "SELECT CEN_OBJECT_CODE,CEN_OBJECT_NAME  FROM  cen_object where `CEN_OBJECT_CODE` in ("
				+ sbCodes.toString() + ")";
		
		if (!StringUtil.isBlank(pcenter)) {
			q += " and `CEN_OBJECT_CODE` in (" + pcenter + ")";
		}

		List<Object[]> countlist = getHibernate_S9999().createSQLQuery(q);

		QueriesVO vo = null;
		for (int i = 0, j = 1, s = countlist.size(); i < s; i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new QueriesVO();
				vo.setIdentifier(Integer.valueOf(j + sid));
				vo.setIsLeaf(0);
				vo.setTableCode(String.valueOf(al[0]));
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(0);
				vo.setActionUrl("javascript:void(0)");
				l.add(vo);
			}
		}
		return l;
	}

	public List<QueriesVO> findByObject(String groupName, String id, int rid,
			int ptype) {
		if (!StringUtil.isNumeric(id)) {
			id = "00";
		}
		String pcenter = this.permissionManager.findPcenterByRid(rid, ptype);

		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String q = "SELECT CEN_OBJECT_CODE,CEN_OBJECT_NAME  FROM  cen_object where `CEN_GROUP` ";
		q += " = '" + groupName + "'";
		if (!StringUtil.isBlank(pcenter)) {
			q += " and `CEN_OBJECT_CODE` in (" + pcenter + ")";
		}

		List<Object[]> countlist = getHibernate_S9999().createSQLQuery(q);

		QueriesVO vo = null;
		for (int i = 0, j = 1, s = countlist.size(); i < s; i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new QueriesVO();
				vo.setIdentifier(Integer.valueOf(j + id));
				vo.setIsLeaf(0);
				vo.setTableCode(String.valueOf(al[0]));
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(0);
				vo.setActionUrl("javascript:void(0)");
				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<QueriesVO> findByTable(String talCode, String id) {
		if (!StringUtil.isNumeric(id)) {
			id = "000";
		}

		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String sql = "SELECT c.`TBL_CODE`, c.`TBL_NAME` FROM `cen_object_table_rel` b,`logical_table` c WHERE b.tbl_code=c.tbl_code and `CEN_OBJECT_CODE` ";
		sql += " = '" + talCode + "'";

		List<Object[]> countlist = getHibernate_S9999().createSQLQuery(sql);

		QueriesVO vo = null;
		for (int i = 0, j = 1, s = countlist.size(); i < s; i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new QueriesVO();
				vo.setIdentifier(Integer.valueOf(j + id));
				vo.setIsLeaf(1);
				vo.setTableCode(String.valueOf(al[0]));
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(0);
				vo.setActionUrl("/manage/queries/queriesAction!leftFrame."
						+ Global.ACTION_EXT);
				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<QueriesVO> findByTable(String talCode, String id, int rid,
			int ptype) {
		if (!StringUtil.isNumeric(id)) {
			id = "000";
		}

		String pcenter = this.permissionManager.findPcenterByRid(rid, ptype);
		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String sql = "SELECT c.`TBL_CODE`, c.`TBL_NAME` FROM `cen_object_table_rel` b,`logical_table` c WHERE b.tbl_code=c.tbl_code and `CEN_OBJECT_CODE` ";
		sql += " = '" + talCode + "'";

		if (!StringUtil.isBlank(pcenter)) {
			sql += " and c.`TBL_CODE` in (" + pcenter + ")";
		}

		List<Object[]> countlist = getHibernate_S9999().createSQLQuery(sql);

		QueriesVO vo = null;
		for (int i = 0, j = 1, s = countlist.size(); i < s; i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new QueriesVO();
				vo.setIdentifier(Integer.valueOf(j + id));
				vo.setIsLeaf(1);
				vo.setTableCode(String.valueOf(al[0]));
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(0);
				vo.setActionUrl("/manage/queries/queriesAction!leftFrame."
						+ Global.ACTION_EXT);
				l.add(vo);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<QueriesVO> findByTable(String talCode, String id, int type,
			Map<String, Integer> map) {

		if (!StringUtil.isNumeric(id)) {
			id = "000";
		}

		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String sql = "SELECT c.`TBL_CODE`, c.`TBL_NAME`,b.`CEN_OBJECT_CODE` FROM `cen_object_table_rel` b,`logical_table` c WHERE b.tbl_code=c.tbl_code and `CEN_OBJECT_CODE` ";
		if (type == 1) {
			sql += " = '" + talCode + "'";
		}
		if (type == 2 || type == 3) {
			sql += " in (" + talCode + ")";
		}

		List<Object[]> countlist = getHibernate_S9999().createSQLQuery(sql);

		QueriesVO vo = null;
		for (int i = 0, j = 1, s = countlist.size(); i < s; i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new QueriesVO();
				vo.setIdentifier(Integer.valueOf(j + id));
				vo.setIsLeaf(1);
				vo.setTableCode(String.valueOf(al[0]));
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(map.get(String.valueOf(al[2])));
				vo.setActionUrl("/manage/queries/queriesAction!leftFrame."
						+ Global.ACTION_EXT);
				l.add(vo);
			}
		}
		return l;
	}

	public Object[] findByTableItem(String tableName, List<ItemTableVO> voList,
			String sv, int curPage, int pageSize) {
		Object[] tableObject = new Object[2];
		if (voList == null || voList.size() == 0) {
			return null;
		}
		String countSql = "SELECT count(1) from `" + tableName + "_info`";
		if (!StringUtil.isBlank(sv)) {
			countSql += " where ";
			countSql += sv;
		}
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
		for (int i = 0; i < voList.size(); i++) {
			bu.append("`").append(voList.get(i).getItemCode()).append("`");
			if (i != voList.size() - 1) {
				bu.append(",");
			}
		}
		bu.append(" from `" + tableName + "_info`");

		if (!StringUtil.isBlank(sv)) {
			bu.append(" where ");
			bu.append(sv);
		}
		if (logger.isDebugEnabled())
			logger.debug(bu.toString());
		List<Object[]> tableList = getHibernate_S9999().sqlQuery(bu.toString(),
				page);

		tableObject[0] = tableList; // 表信息
		tableObject[1] = page; // 页数

		return tableObject;
	}

	public List<ItemTableVO> findItemTableBy(String tableName) {
		List<ItemTableVO> itemVoList = new ArrayList<ItemTableVO>();
		String itemSql = "SELECT `ITEM_CODE`,`ITEM_NAME`,`ITEM_TYPE`,`REL_CATE_CODE_EXPR` FROM `tbl_item` WHERE `tbl_code` = '"
				+ tableName + "' AND metric_name='2' ORDER BY coordinate";
		List<Object[]> itemList = getHibernate_S9999().createSQLQuery(itemSql);
		ItemTableVO temp = null;
		String expr = null;
		Map<String, List<CodeExprVO>> exprMap = null;
		for (int i = 0; i < itemList.size(); i++) {
			temp = new ItemTableVO();
			temp.setItemCode(String.valueOf(itemList.get(i)[0]));
			temp.setItemName(String.valueOf(itemList.get(i)[1]));
			temp.setItemType(String.valueOf(itemList.get(i)[2]));
			expr = String.valueOf(itemList.get(i)[3]);
			if (!StringUtil.isBlank(expr) && !"null".equals(expr)) {
				String[] sp = expr.split(",");
				if (!StringUtil.isBlank(sp[0])) {
					exprMap = new HashMap<String, List<CodeExprVO>>();
					List<CodeExprVO> arr = findCodeExpr(sp[0]);
					exprMap.put(temp.getItemCode(), arr);
					temp.setItemType("3");
					temp.setExprMap(exprMap);
				}
			}
			itemVoList.add(temp);
			expr = null;
		}
		return itemVoList;
	}

	private List<CodeExprVO> findCodeExpr(String tablName) {
		List<CodeExprVO> arr = new ArrayList<CodeExprVO>();
		try {
			String hql = "select * from " + tablName;
			CodeExprVO vo = null;
			List<Object[]> l = this.getHibernate_S9999().createSQLQuery(hql);
			for (int i = 0; i < l.size(); i++) {
				vo = new CodeExprVO();
				Object[] arro = (Object[]) l.get(i);
				if (arro != null && arro.length == 2) {
					vo.setCode(String.valueOf(arro[0]));
					vo.setName(String.valueOf(arro[1]));
					arr.add(vo);
				} else {
					logger.error(tablName + "   返回的数据有错误");
				}
			}
		} catch (Exception e) {
			logger.error("查询数据指标出错。", e);
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> findByTableItem(String tableName,
			List<ItemTableVO> voList, String sv) {
		if (voList == null || voList.size() == 0) {
			return new ArrayList<Object[]>();
		}

		StringBuilder bu = new StringBuilder("SELECT ");
		for (int i = 0; i < voList.size(); i++) {
			bu.append("`").append(voList.get(i).getItemCode()).append("`");
			if (i != voList.size() - 1) {
				bu.append(",");
			}
		}
		bu.append(" from `" + tableName + "_info`");

		if (!StringUtil.isBlank(sv)) {
			bu.append(" where ");
			bu.append(sv);
		}
		if (logger.isDebugEnabled())
			logger.debug(bu.toString());
		List<Object[]> tableList = getHibernate_S9999().createSQLQuery(
				bu.toString());

		return tableList;
	}

	public long findCountByTableItem(String tableName,
			List<ItemTableVO> voList, String sv) {
		String countSql = "SELECT count(1) from `" + tableName + "_info`";
		if (!StringUtil.isBlank(sv)) {
			countSql += " where ";
			countSql += sv;
		}
		List r = getHibernate_S9999().createSQLQuery(countSql);

		long totalresult = 0;
		if (r != null && r.size() > 0) {
			totalresult = Long.valueOf(r.get(0).toString());
			;
		}
		if (totalresult < 1) {
			totalresult = 0;
		}
		return totalresult;
	}

	public List<Object[]> findPageByTableItem(String tableName,
			List<ItemTableVO> voList, String sv, Page page) {
		if (voList == null || voList.size() == 0) {
			return new ArrayList<Object[]>();
		}

		StringBuilder bu = new StringBuilder("SELECT ");
		for (int i = 0; i < voList.size(); i++) {
			bu.append("`").append(voList.get(i).getItemCode()).append("`");
			if (i != voList.size() - 1) {
				bu.append(",");
			}
		}
		bu.append(" from `" + tableName + "_info`");

		if (!StringUtil.isBlank(sv)) {
			bu.append(" where ");
			bu.append(sv);
		}
		if (logger.isDebugEnabled())
			logger.debug(bu.toString());

		List<Object[]> tableList = getHibernate_S9999().sqlQuery(bu.toString(),
				page);

		return tableList;
	}

	public void aaPlate014() {
		List<Object[]> list = getHibernate_S9999().createSQLQuery(
				"SELECT * FROM `Plate014` WHERE `S005_A23B` = ''");
	}

	public List<QueriesVO> findByObject(String groupName, String id, int type,
			Map<String, Integer> idMap) {
		if (!StringUtil.isNumeric(id)) {
			id = "00";
		}

		// 创建查询
		List<QueriesVO> l = new ArrayList<QueriesVO>();
		String q = "SELECT CEN_OBJECT_CODE,CEN_OBJECT_NAME,`CEN_GROUP`  FROM  cen_object where `CEN_GROUP` ";
		if (type == 1) {
			q += "= '" + groupName + "'";
		}
		if (type == 2) {
			q += "in (" + groupName + ")";
		}

		List<Object[]> countlist = getHibernate_S9999().createSQLQuery(q);

		QueriesVO vo = null;
		for (int i = 0, j = 1, s = countlist.size(); i < s; i++, j++) {
			if (countlist.get(i) != null) {
				Object[] al = countlist.get(i);
				vo = new QueriesVO();
				vo.setIdentifier(Integer.valueOf(j + id));
				vo.setIsLeaf(0);
				vo.setTableCode(String.valueOf(al[0]));
				vo.setModelName(String.valueOf(al[1]));
				vo.setParentId(idMap.get(String.valueOf(al[2])));
				vo.setActionUrl("javascript:void(0)");
				l.add(vo);
			}
		}
		return l;
	}

	@Override
	public List<QueriesVO> findStructureByPid(Integer pid) {
		List<QueriesVO> voList = new ArrayList<QueriesVO>();
		List<Structure> slist = this.structureDAO.findByPid(pid);
		if (slist == null) {
			return null;
		}
		Structure st = null;
		QueriesVO vo = null;
		Iterator<Structure> it = slist.iterator();
		while (it.hasNext()) {
			st = it.next();
			vo = new QueriesVO();
			vo.setIdentifier(st.getSid());
			vo.setParentId(st.getSpid());
			vo.setModelName(st.getSname());
			vo.setActionUrl("javascript:void(0)");
			vo.setIsLeaf(0);
			if (StringUtils.isBlank(st.getScenter())) {
				vo.setStype("0");
			} else {
				vo.setStype("1");
			}
			voList.add(vo);
		}

		return voList;
	}

	@Override
	public Structure findStructureById(Integer id) {
		return (Structure) structureDAO.findById(id);
	}

	@Override
	public List<QueriesVO> findStructureByPid(Integer pid, int rid, int ptype) {
		// 创建查询
		String pcenter = this.permissionManager.findPcenterByRid(rid, ptype);
		List<QueriesVO> voList = new ArrayList<QueriesVO>();
		List<Structure> slist = this.structureDAO.findByPid(pid, pcenter);
		if (slist == null) {
			return null;
		}
		Structure st = null;
		QueriesVO vo = null;
		Iterator<Structure> it = slist.iterator();
		while (it.hasNext()) {
			st = it.next();
			vo = new QueriesVO();
			vo.setIdentifier(st.getSid());
			vo.setParentId(st.getSpid());
			vo.setModelName(st.getSname());
			vo.setActionUrl("javascript:void(0)");
			vo.setIsLeaf(0);
			if (StringUtils.isBlank(st.getScenter())) {
				vo.setStype("0");
			} else {
				vo.setStype("1");
			}
			voList.add(vo);
		}

		return voList;
	}

	

}
