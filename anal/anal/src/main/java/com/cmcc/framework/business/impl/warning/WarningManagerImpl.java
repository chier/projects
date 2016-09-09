package com.cmcc.framework.business.impl.warning;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableBlob;

import com.cmcc.common.persistence.query.QueryTemplateDTO;
import com.cmcc.common.persistence.uuid.UUID;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.serializer.impl.BurlapSerializer;
import com.cmcc.framework.business.interfaces.warning.IWarningManager;
import com.cmcc.framework.controller.formbean.WarningVO;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.persistence.interfaces.warning.IWarningDAO;

/**
 * 预警分析 业务manager 实现类
 * 
 * @author Administrator
 * 
 */
public class WarningManagerImpl implements IWarningManager {
	private Logger logger = Logger.getLogger(WarningManagerImpl.class);

	private IWarningDAO warningDAO;

	private BurlapSerializer burlapSerializer;

	public BurlapSerializer getBurlapSerializer() {
		if (burlapSerializer == null) {
			burlapSerializer = new BurlapSerializer();
		}
		return burlapSerializer;
	}

	public IWarningDAO getWarningDAO() {
		return warningDAO;
	}

	public void setWarningDAO(IWarningDAO warningDAO) {
		this.warningDAO = warningDAO;
	}

	public void saveOrUpdate(QueryTemplateDTO queryTemplateDTO) {
		this.warningDAO.saveOrUpdate(queryTemplateDTO);
	}

	public List<ZtreeVO> findTreeTypeAndCodeByRid(int rid) {
		return warningDAO.findTreeTypeAndCodeByRid(rid);
	}

	public List<String> findAllS999StatUnit() {
		return warningDAO.findAllS999StatUnit();
	}

	@SuppressWarnings("unchecked")
	public List findListByVOSQL(WarningVO vo) {
		// 表头数据
		List<String> tabHeadList = new ArrayList<String>();
		StringBuilder bu = new StringBuilder();
		returnSql(vo, bu, tabHeadList);
		logger.info(bu.toString());
		List<Object[]> dataList = warningDAO.getHibernate_Analysis()
				.createSQLQuery(bu.toString());
		// // 大数据类型 保留小数点后面二位
		for (int i = 0; i < dataList.size(); i++) {
			Object[] arro = dataList.get(i);
			if (arro[0] != null && StringUtil.isBlank(arro[0].toString())) {
				arro[0] = "其它";
			}
			for (int j = 0; j < arro.length; j++) {
				Object o = arro[j];
				if (o instanceof BigDecimal) {
					DecimalFormat df = new DecimalFormat("0.00");
					String db = df.format(o);
					arro[j] = db;
				}
			}
		}
		List l = new ArrayList();
		l.add(tabHeadList);
		l.add(dataList);

		return l;
	}

	/**
	 * 返回拼装的SQL 和表头信息
	 * 
	 * @param vo
	 * @param bu
	 * @param headList
	 */
	private void returnSql(WarningVO vo, StringBuilder bu, List<String> headList) {
		bu.append(" select ");
		// 增加区域显示值
		if (!StringUtil.isBlank(vo.getHidArea())) {
			if (!StringUtil.isBlank(vo.getHidFirstTable())) {
				bu.append("_" + vo.getHidFirstTable()).append(".");
			} else if (!StringUtil.isBlank(vo.getHidSecondTable())) {
				bu.append("_" + vo.getHidSecondTable()).append(".");
			}
			bu.append(vo.getHidArea());
			headList.add("区域");
		}
		// 第一字段
		if (!StringUtil.isBlank(vo.getHidFirstCode())) {
			bu.append(",");
			// bu.append("
			// SUBSTR(AVG(_plate031_info_fixed.S031_A10B),1,INSTR(AVG(_plate031_info_fixed.S031_A10B),'.')+2)");
			StringBuilder sf = new StringBuilder();
			if (!StringUtil.isBlank(vo.getHidFirstTable())) {
				sf.append("_" + vo.getHidFirstTable()).append(".");
			}
			sf.append(vo.getHidFirstCode());

			// bu.append(" SUBSTR(AVG(").append(sf).append("),1,INSTR(AVG(")
			// .append(sf).append("),'.')+2)");
			bu.append("avg(").append(sf).append(")");
			// .append(" as ");
			// .append(
			// vo.getHidFirstName());
			headList.add(vo.getHidFirstName());
			// 下限
			if (!StringUtil.isBlank(vo.getHidFirstMin())) {
				bu.append(",").append(vo.getHidFirstMin());
				headList.add("下限");
			}
			// 上限
			if (!StringUtil.isBlank(vo.getHidFirstMax())) {
				bu.append(",").append(vo.getHidFirstMax());
				headList.add("上限");
			}
		}

		// 第二字段
		if (!StringUtil.isBlank(vo.getHidSecondCode())) {
			bu.append(",");
			StringBuilder ss = new StringBuilder();
			if (!StringUtil.isBlank(vo.getHidSecondTable())) {
				ss.append("_" + vo.getHidSecondTable()).append(".");
			}
			ss.append(vo.getHidSecondCode());

			// bu.append(" SUBSTR(AVG(").append(ss).append("),1,INSTR(AVG(")
			// .append(ss).append("),'.')+2)");
			// .append(" as ").append(
			// vo.getHidSecondName());
			bu.append("avg(").append(ss).append(")");
			headList.add(vo.getHidSecondName());

			// 下限
			if (!StringUtil.isBlank(vo.getHidSecondMin())) {
				bu.append(",").append(vo.getHidSecondMin());
				headList.add("下限");
			}
			// 上限
			if (!StringUtil.isBlank(vo.getHidSecondMax())) {
				bu.append(",").append(vo.getHidSecondMax());
				headList.add("上限");
			}
		}

		bu.append(" from ");

		// 当查询的表为同一个表时
		if (!StringUtil.isBlank(vo.getHidFirstCode())
				&& vo.getHidFirstTable().equals(vo.getHidSecondTable())) {
			bu.append(vo.getHidFirstTable()).append(" as ").append(
					"_" + vo.getHidFirstTable());
		} else {
			// 二个表
			if (!StringUtil.isBlank(vo.getHidFirstTable())) {
				bu.append(vo.getHidFirstTable()).append(" as ").append(
						"_" + vo.getHidFirstTable());
				if (!StringUtil.isBlank(vo.getHidSecondTable())) {
					bu.append(",");
				}

			}
			if (!StringUtil.isBlank(vo.getHidSecondTable())) {
				bu.append(vo.getHidSecondTable()).append(" as ").append(
						"_" + vo.getHidSecondTable());
			}

			if (!StringUtil.isBlank(vo.getHidFirstTable())
					&& !StringUtil.isBlank(vo.getHidSecondTable())) {
				bu.append(" where ").append("_" + vo.getHidFirstTable())
						.append(".").append(vo.getHidArea()).append("=")
						.append("_" + vo.getHidSecondTable()).append(".")
						.append(vo.getHidArea());
			}
		}

		bu.append("  group by ");
		if (!StringUtil.isBlank(vo.getHidFirstTable())) {
			bu.append("_" + vo.getHidFirstTable()).append(".").append(
					vo.getHidArea());
			if (!StringUtil.isBlank(vo.getHidSecondTable())) {
				bu.append(",");
			}
		}

		if (!StringUtil.isBlank(vo.getHidSecondTable())) {
			bu.append("_" + vo.getHidSecondTable()).append(".").append(
					vo.getHidArea());
		}

	}

	public List<ZtreeVO> findDisctoryTreeByTypeAndRid(int type, int rid)
			throws SQLException, IOException {
		List<ZtreeVO> zl = new ArrayList<ZtreeVO>();
		// 创建查询
		String q = "SELECT `id`,`pid`,`node_name` FROM  `warning_directory`";
		if (type == 0) { // 只获取目录
			q = q + " where `node_type` = 0";
		}
		List<Object[]> cl = this.warningDAO.getHibernate_Anal().createSQLQuery(
				q);
		Object[] ao = null;
		ZtreeVO vo = null;
		int c = 0;
		if (cl != null && cl.size() > 0) {
			for (int i = 0; i < cl.size(); i++) {
				vo = new ZtreeVO();
				ao = cl.get(i);
				vo.setId(Integer.valueOf(ao[0].toString()));
				vo.setpId(Integer.valueOf(ao[1].toString()));
				vo.setName((ao[2] == null) ? "" : ao[2].toString());
				vo.setType(0);
				// if(i == 0){
				// vo.setChecked(true);
				// }
				zl.add(vo);
				c++;
			}
		}
		cl = null;
		q = null;
		List<Blob> bl = null;
		if (type == 1) {
			q = "SELECT `TEMPLATE_CONTENT` FROM  `query_template`";
			bl = this.warningDAO.getHibernate_Anal().createSQLQuery(q);
			for (int i = 0; i < bl.size(); i++) {
				Blob blob = bl.get(i);
				WarningVO wvo = getWarningVOByBlob(blob);
				vo = new ZtreeVO();
				vo.setId(c + i);
				vo.setpId(Integer.valueOf(wvo.getHidSaveWar()));
				vo.setName(wvo.getTxtName());
				vo.setCodeValue(wvo.getTemplateCode());
				vo.setType(1);
				zl.add(vo);
			}
		}

		return zl;
	}

	public int saveOrUpdateWarning(WarningVO vo) {

		if (vo == null || vo.getTemplateCode() == null) {
			insert(vo);
		} else {
			boolean exist = exist(vo.getTemplateCode());
			if (exist) {
				update(vo);
			} else {
				insert(vo);
			}
		}
		return 0;
	}

	private static final String UPDATE_SQL = "UPDATE QUERY_TEMPLATE SET TEMPLATE_NAME=?,"
			+ " TEMPLATE_DESC=?, COMPILED=?, TEMPLATE_TYPE=?,"
			+ " TEMPLATE_CONTENT=? WHERE TEMPLATE_CODE=?";

	private static final int[] UPDATE_TYPES = new int[] { Types.VARCHAR,
			Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.CLOB,
			Types.VARCHAR };

	protected void update(WarningVO queryTemplateDTO) {
		Object[] values = populateUpdateValues(queryTemplateDTO);
		updateClob(UPDATE_SQL, values, UPDATE_TYPES);
	}

	private Object[] populateUpdateValues(WarningVO qt) {
		String content = null;
		try {
			content = toContent(qt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object[] obj = new Object[] { qt.getTxtName(), "", "0", "", content,
				qt.getTemplateCode() };
		return obj;
	}

	private static final String EXIST_SQL = "SELECT COUNT(*) FROM QUERY_TEMPLATE WHERE TEMPLATE_CODE=?";

	private boolean exist(String templateCode) {
		int count = 0;
		List l = warningDAO.getHibernate_Anal().createSQLQuery(EXIST_SQL,
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

	protected void insert(WarningVO vo) {
		Object[] values = populateInsertValues(vo);
		// updateClob(INSERT_SQL, values, INSERT_TYPES);
		warningDAO.getHibernate_Anal().sqlUpdate(INSERT_SQL, values);
	}

	private int updateClob(String sql, Object[] objs, int[] types) {
		return warningDAO.getHibernate_Anal().sqlUpdate(sql, objs);
	}

	private Object[] populateInsertValues(WarningVO qt) {
		String content = null;

		if (qt.getTemplateCode() == null || "".equals(qt.getTemplateCode())) {
			qt.setTemplateCode(UUID.getUuid());
		}
		try {
			content = toContent(qt);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Object[] obj = new Object[] { qt.getTemplateCode(), qt.getTxtName(),
				"", "0", new Date(), "", content };
		return obj;
	}

	private String toContent(WarningVO vo) throws IOException {
		return getBurlapSerializer().serialize2String(vo);
	}

	@SuppressWarnings("unchecked")
	public WarningVO findWarningVoByCode(String templeCode) {
		List<Blob> bl = null;
		String q = "SELECT `TEMPLATE_CONTENT` FROM  `query_template` where  `TEMPLATE_CODE` = ?";
		bl = this.warningDAO.getHibernate_Anal().createSQLQuery(q, templeCode);
		if (bl != null && bl.size() > 0) {
			Blob blob = bl.get(0);
			try {
				WarningVO wvo = this.getWarningVOByBlob(blob);
				return wvo;
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return null;
	}

	public Long addTreeDirectory(String name, String pid) {
		return this.warningDAO.addTreeDirectory(name, pid);

	}

	public void removeTreeDirectory(Serializable id) {
		this.warningDAO.removeTreeDirectory(id);

	}

	//
	// public List<ZtreeVO> queryTreeChildren(Serializable pid) {
	// return this.warningDAO.queryTreeChildren(pid);
	// }
	//
	public void renameTreeDirectory(String name, String id) {
		this.warningDAO.renameTreeDirectory(name, id);

	}

	public void removeTemplate(Serializable id) {
		this.warningDAO.removeTemplate(id);
	}

	public void renameTemplate(String name, String id) {
		WarningVO wvo = findWarningVoByCode(id);
		wvo.setTxtName(name);
		saveOrUpdateWarning(wvo);

		// this.warningDAO.renameTemplate(name, id);
	}

	public void saveSettingDefault(int id, int min, int max) {
		this.warningDAO.saveSettingDefault(id, min, max);

	}

	private WarningVO getWarningVOByBlob(Blob blob) throws IOException,
			SQLException {

		// InputStream msgContent = blob.getBinaryStream();
		//
		// WarningVO wvo = (WarningVO)
		// getBurlapSerializer().deserialize(
		// msgContent);
		// SerializableBlob sblog = (SerializableBlob) bl.get(i);
		WarningVO wvo = (WarningVO) getBurlapSerializer().deserialize(
				blob.getBytes(1l, (int) blob.length()));
		return wvo;
	}
}
