package com.cmcc.common.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 节点工具类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-7-29
 */
public class NodeUtil {
	/**
	 * 解析 xml 信息
	 * 
	 * @param setting
	 * @return
	 * @throws DocumentException
	 */
	public static Map selectXml(String setting) {
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(setting);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		String query = root.element("query").getText();
		String dbName = root.element("dbName").getText();
		String driver = root.element("driver").getText();
		String password = root.element("password").getText();
		String userName = root.element("userName").getText();
		Map<String, String> map = new HashMap<String, String>();
		map.put("query", query);
		map.put("dbName", dbName);
		map.put("driver", driver);
		map.put("password", password);
		map.put("userName", userName);
		return map;
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List[] findDB(Map<String, String> map) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsm = null;
		List colList = new ArrayList();
		List stList = new ArrayList();
		try {
			Class.forName(map.get("driver"));

			conn = DriverManager.getConnection(map.get("dbName"), map
					.get("userName"), map.get("password"));
			stmt = conn.createStatement();
			rs = stmt.executeQuery(map.get("query"));
			rsm = rs.getMetaData();
			for (int i = 1; i < rsm.getColumnCount(); i++) {
				colList.add(conversionColumn(rsm.getColumnName(i)));
			}
			while (rs.next()) {
				List temp = new ArrayList();
				for (int i = 1; i < rsm.getColumnCount(); i++) {
					temp.add(rs.getObject(i));
				}
				stList.add(temp);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new List[] { colList, stList };
	}

	public static String conversionColumn(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}

		if (str.indexOf("id") != -1) {
			return "标识";
		}
		if (str.indexOf("name") != -1) {
			return "名称";
		}
		if ("createtime".equalsIgnoreCase(str)) {
			return "创建时间";
		}
		if ("dmltime".equalsIgnoreCase(str)) {
			return "操作时间";
		}
		if ("dmlflog".equalsIgnoreCase(str)) {
			return "操作类型";
		}
		return str;
	}
}
