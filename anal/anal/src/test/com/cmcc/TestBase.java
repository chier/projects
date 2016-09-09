/*
 * 文件名： TestBase.java
 * 
 * 创建日期： 2009-2-26
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.cmcc.common.Global;
import com.cmcc.common.persistence.dao.interfaces.IEntityDAO;
import com.cmcc.common.util.ConfigUtil;

/**
 * 所有测试用例基类，供测试环境初始化配置
 * 
 * @author <a href="mailto:sun128837@126.com">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-2-26
 */
public class TestBase {
	protected Logger logger = Logger.getLogger(getClass());

	@BeforeClass
	public static void setUpBeforeClass() {
		Global._ctx = new FileSystemXmlApplicationContext(
				ConfigUtil
						.getFullConfigLocationArray(
								"F:\\projects\\Environmental\\java\\anal2015\\src\\main\\webapp",
								"spring"));
	}

	protected static Object getBean(String beanName) {
		return Global._ctx.getBean(beanName);
	}

	// @Test
	public void aa() {

		Object sessionfactory_anal_r_0 = getBean("sessionfactory_s9999_s");
		Assert.assertNotNull(sessionfactory_anal_r_0);

		Object sessionfactory_anal2_r_0 = getBean("sessionfactory_analysis_w");
		Assert.assertNotNull(sessionfactory_anal2_r_0);

	}

	/**
	 * 更新 spec code 为试点码 1.导入excl文件 2.查询所有的表 3.循环读出 t3pad*表 4.通过stat_unit_code
	 * 行政区码获取试点码，再将试点码赋予 spec code字段 5.查询 14年数据时，需要增加 spec_code字段做为试点查询条件
	 * 
	 * @author zhangzhanliang
	 */
	@Test
	public void updateSpecCode2014() {
		Map<String, String> codeMap = this.getCode(); // 行政编码与试点 code 对应信息
		List<String> tablesList = this.getTables(); // 表名信息
		Iterator<String> it = tablesList.iterator();
		String tableName = null;

		List<String> statunitcodeList = null;
		while (it.hasNext()) {
			tableName = it.next();
			statunitcodeList = getStat_unit_code(tableName);
			for (int i = 0; i < statunitcodeList.size(); i++) {
				String statunitCode = statunitcodeList.get(i);
				if (StringUtils.isNotBlank(statunitCode)) {
					String IDATACODE = codeMap.get(statunitCode);
					Log.info("表名 = " + tableName + " | 行政编码 = " + statunitCode
							+ " | 试点码 = " + IDATACODE);
					updatespecode(tableName, statunitCode, IDATACODE);
				}
			}
		}
	}

	/**
	 * 获取所有的表
	 * 
	 * @return
	 * @author zhangzhanliang
	 */
	public List<String> getTables() {
		// 查询所有的表
		SessionFactory sessionfactory_s9999 = (SessionFactory) getBean("sessionfactory_s9999_s");
		Connection conn = sessionfactory_s9999.openSession().connection();
		List<String> tableList = new ArrayList<String>();
		if (conn != null) {
			DatabaseMetaData dbmd = null;
			ResultSet rs = null;
			try {
				dbmd = conn.getMetaData();
				rs = dbmd.getTables(null, "%", "t3pad%",
						new String[] { "TABLE" });
				while (rs.next()) {
					String tname = rs.getString("TABLE_NAME");
					String type = rs.getString("TABLE_TYPE");
					if (type.equalsIgnoreCase("table")
							&& tname.indexOf("$") == -1)
						tableList.add(tname);
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(), e);
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		}
		logger.info(tableList.size());
		return tableList;
	}

	/**
	 * 获取所有的行政编码与试点对应信息
	 * 
	 * @return
	 * @author zhangzhanliang
	 */
	public Map<String, String> getCode() {
		Map<String, String> map = new HashMap<String, String>();
		IEntityDAO dao = (IEntityDAO) Global._ctx.getBean("anal_w");
		String sql = "select DISCODE,IDATACODE from scode_table";

		List<Object[]> list = dao.createSQLQuery(sql);
		Object[] objs = null;
		Iterator<Object[]> it = list.iterator();
		while (it.hasNext()) {
			objs = it.next();
			map.put(objs[0].toString(), objs[1].toString());
		}
		logger.info(map.size());
		return map;
	}

	public List<String> getStat_unit_code(String tablename) {
		IEntityDAO dao = (IEntityDAO) Global._ctx.getBean("s9999_w");
		String sql = "select stat_unit_code from `" + tablename
				+ "` group by stat_unit_code";
		List<String> list = dao.createSQLQuery(sql);
		logger.info(list.size());
		return list;
	}

	public void updatespecode(String tableName, String statunitcode,
			String specCode) {
		IEntityDAO dao = (IEntityDAO) Global._ctx.getBean("s9999_w");
		String sql = "update `" + tableName + "` set spec_code = '" + specCode
				+ "' where stat_unit_code = '" + statunitcode + "'";
		dao.sqlUpdate(sql);
	}
}
