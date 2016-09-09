package com.cmcc.framework.persistence.impl.warning;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;

import com.cmcc.common.persistence.query.QueryTemplateDTO;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.serializer.impl.BurlapSerializer;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.persistence.impl.BaseDAOImpl;
import com.cmcc.framework.persistence.interfaces.warning.IWarningDAO;

/**
 * 预警分析 dao 实现类
 * 
 * @author zhangzhanliang
 * 
 */
public class WarningDAOImpl extends BaseDAOImpl implements IWarningDAO {

	public Page findBy(SearchCondition searchCond, Integer pageSize) {
		return null;
	}

	public List findByPage(Page pageInfo) {
		return null;
	}

	private LobHandler lobHandler = new DefaultLobHandler();

	private BurlapSerializer burlapSerializer;

	public LobHandler getLobHandler() {
		return lobHandler;
	}

	public void setCauchoSerializer(BurlapSerializer burlapSerializer) {
		this.burlapSerializer = burlapSerializer;
	}

	public BurlapSerializer getBurlapSerializer() {
		if (burlapSerializer == null) {
			burlapSerializer = new BurlapSerializer();
		}
		return burlapSerializer;
	}

	public void saveOrUpdate(QueryTemplateDTO queryTemplateDTO) {
		if (queryTemplateDTO == null
				|| queryTemplateDTO.getTemplateCode() == null) {
			insert(queryTemplateDTO);
		} else {
			boolean exist = exist(queryTemplateDTO.getTemplateCode());
			if (exist) {
				update(queryTemplateDTO);
			} else {
				insert(queryTemplateDTO);
			}
		}
	}

	private static final String EXIST_SQL = "SELECT COUNT(*) FROM QUERY_TEMPLATE WHERE TEMPLATE_CODE=?";

	private boolean exist(String templateCode) {
		int count = 0;
		List l = this.getHibernate_Anal().createSQLQuery(EXIST_SQL,
				new Object[] { templateCode });
		if (l != null) {
			count = ((java.math.BigInteger) l.get(0)).intValue();
		}
		return count != 0;
	}

	private static final String INSERT_SQL = "INSERT INTO QUERY_TEMPLATE "
			+ "(TEMPLATE_CODE, TEMPLATE_NAME, TEMPLATE_DESC, COMPILED, CREATE_DATE, "
			+ "TEMPLATE_TYPE, TEMPLATE_CONTENT) VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final int[] INSERT_TYPES = new int[] { Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
			Types.VARCHAR, Types.CLOB };

	protected void insert(QueryTemplateDTO queryTemplateDTO) {
		Object[] values = populateInsertValues(queryTemplateDTO);
		updateClob(INSERT_SQL, values, INSERT_TYPES);
	}

	private int updateClob(String sql, Object[] objs, int[] types) {
		// for (int i = 0; i < types.length; i++) {
		// if (types[i] == Types.CLOB) {
		// objs[i] = new SqlLobValue((String) objs[i], getLobHandler());
		// }
		// }
		return this.getHibernate_Anal().sqlUpdate(sql, objs);
	}

	private Object[] populateInsertValues(QueryTemplateDTO qt) {
		String content = null;
		try {
			content = toContent(qt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object[] obj = new Object[] { qt.getTemplateCode(),
				qt.getTemplateName(), qt.getTemplateDesc(),
				qt.isCompiled() ? "1" : "0", qt.getCreateDate(),
				qt.getTemplateType(), content };
		return obj;
	}

	private String toContent(QueryTemplateDTO qt) throws IOException {
		return getBurlapSerializer().serialize2String(qt);
	}

	private static final String UPDATE_SQL = "UPDATE QUERY_TEMPLATE SET TEMPLATE_NAME=?,"
			+ " TEMPLATE_DESC=?, COMPILED=?, TEMPLATE_TYPE=?,"
			+ " TEMPLATE_CONTENT=? WHERE TEMPLATE_CODE=?";

	private static final int[] UPDATE_TYPES = new int[] { Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.CLOB,
			Types.VARCHAR };

	protected void update(QueryTemplateDTO queryTemplateDTO) {
		Object[] values = populateUpdateValues(queryTemplateDTO);
		updateClob(UPDATE_SQL, values, UPDATE_TYPES);
	}

	private Object[] populateUpdateValues(QueryTemplateDTO qt) {
		String content = null;
		try {
			content = toContent(qt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object[] obj = new Object[] { qt.getTemplateName(),
				qt.getTemplateDesc(), qt.isCompiled() ? "1" : "0",
				qt.getTemplateType(), content, qt.getTemplateCode() };
		return obj;
	}

	@SuppressWarnings("unchecked")
	public List<ZtreeVO> findTreeTypeAndCodeByRid(int rid) {
		List<ZtreeVO> zl = new ArrayList<ZtreeVO>();
		// 创建查询
		String q = "SELECT `id`,`pid`,`node_name`,`node_code`,`node_table`,`max_val`,`min_val`  FROM  `warning_type`";
		List<Object[]> cl = this.getHibernate_Anal().createSQLQuery(q);
		Object[] ao = null;
		ZtreeVO vo = null;
		if (cl != null && cl.size() > 0) {
			for (int i = 0; i < cl.size(); i++) {
				vo = new ZtreeVO();
				ao = cl.get(i);
				vo.setId(Integer.valueOf(ao[0].toString()));
				vo.setpId(Integer.valueOf(ao[1].toString()));
				vo.setName((ao[2] == null) ? "" : ao[2].toString());
				vo.setCodeValue((ao[3] == null) ? "" : ao[3].toString());
				vo.setTableValue((ao[4] == null) ? "" : ao[4].toString());
				vo.setMaxVal((ao[5] == null) ? "" : ao[5].toString());
				vo.setMinVal(ao[6] == null ? "" : ao[6].toString());
				zl.add(vo);
			}
		}
		return zl;
	}

	public List<String> findAllS999StatUnit() {
		String sql = "select `name` from `stat_unit` ";
		List<String> sl = this.getHibernate_S9999().createSQLQuery(sql);
		return sl;
	}

	public Long addTreeDirectory(String name, String pid) {
		String q = "INSERT INTO `warning_directory` (`pid`,`node_name`,`node_type`) VALUE ("
				+ pid + ",'" + name + "',0)";
		return this.getHibernate_Anal().sqlInsert(q, null);

	}

	public void removeTreeDirectory(Serializable id) {
		String q = "delete from `warning_directory` where `id` = " + id;
		this.getHibernate_Anal().sqlUpdate(q);

	}

	// public List<ZtreeVO> queryTreeChildren(Serializable pid) {
	// List<ZtreeVO> zl = new ArrayList<ZtreeVO>();
	// // 创建查询
	// String q = "SELECT
	// `id`,`pid`,`node_name`,`node_code`,`node_table`,`max_val`,`min_val` FROM
	// `warning_type` where `pid` = " + pid;
	// List<Object[]> cl = this.getHibernate_Anal().createSQLQuery(q);
	// Object[] ao = null;
	// ZtreeVO vo = null;
	// if (cl != null && cl.size() > 0) {
	// for (int i = 0; i < cl.size(); i++) {
	// vo = new ZtreeVO();
	// ao = cl.get(i);
	// vo.setId(Integer.valueOf(ao[0].toString()));
	// vo.setpId(Integer.valueOf(ao[1].toString()));
	// vo.setName((ao[2] == null) ? "" : ao[2].toString());
	// vo.setCodeValue((ao[3] == null) ? "" : ao[3].toString());
	// vo.setTableValue((ao[4] == null) ? "" : ao[4].toString());
	// vo.setMaxVal((ao[5] == null) ? "" : ao[5].toString());
	// vo.setMinVal(ao[6] == null ? "" : ao[6].toString());
	// zl.add(vo);
	// }
	// }
	// return zl;
	// }

	public void renameTreeDirectory(String name, String id) {
		String q = "UPDATE `warning_directory` SET `node_name` = ? WHERE id = ?";
		List list = new ArrayList();
		list.add(name);
		list.add(id);
		this.getHibernate_Anal().sqlUpdate(q, list.toArray());
	}

	public void removeTemplate(Serializable id) {
		String q = "delete from `query_template` where `TEMPLATE_CODE` = ?";
		List list = new ArrayList();
		list.add(id);
		this.getHibernate_Anal().sqlUpdate(q, list.toArray());

	}

	public void saveSettingDefault(int id, int min, int max) {
		String sql = "update `warning_type` set `max_val`=?,`min_val`=? where `id`=?";
		getHibernate_Anal().sqlUpdate(sql, new Object[] { max, min, id });

	}

	// public void renameTemplate(String name, String id) {
	// // TODO Auto-generated method stub
	//		
	// }

}
