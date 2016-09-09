package com.cmcc.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.BeforeClass;
import org.junit.Test;

import com.cmcc.TestBase;
import com.cmcc.framework.model.WorkflowNode;
import com.cmcc.framework.persistence.interfaces.system.IWorkflowNodeDAO;

/**
 * 节点测试类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-7-29
 */
public class TestWorkflowNodeDAO extends TestBase {
	static IWorkflowNodeDAO dao = null;

	@BeforeClass
	public static void initBeforeClass() {
		dao = (IWorkflowNodeDAO) getBean("workflowNodeDAO");
	}

	@Test
	public void showXMLInfo() {
		WorkflowNode node = (WorkflowNode) dao.findById(28);
		String setting = node.getNodeSetting();
		logger.info(setting);
		// DocumentFactory factory = DocumentFactory.getInstance();
		//
		// = factory.createDocument("utf-8");
		// doc.setText(setting);
		// logger.info(doc.getName());
		Document doc;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			doc = DocumentHelper.parseText(setting);
			Element root = doc.getRootElement();
			String query = root.element("query").getText();
			String dbName = root.element("dbName").getText();
			String driver = root.element("driver").getText();
			String password = root.element("password").getText();
			String userName = root.element("userName").getText();

			Class.forName(driver);
			conn = DriverManager.getConnection(dbName, userName, password);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			ResultSetMetaData rsm = rs.getMetaData();
			
			for (int i = 1; i < rsm.getColumnCount(); i++) {
				logger.info("第" + i + "列的名字为：" + rsm.getColumnName(i)
						+ ", 类型为：" + rsm.getColumnTypeName(i));
			}
			while (rs.next()) {
				System.out.print(rs.getString("id") + "   ");
				System.out.print(rs.getString("name") + "   ");
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
