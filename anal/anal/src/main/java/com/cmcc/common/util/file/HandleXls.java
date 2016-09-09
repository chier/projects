package com.cmcc.common.util.file;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import jxl.Sheet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*******************************************************************************
 * 处理上传的xls文件
 * 
 * @author lidapeng
 * 
 * @since Apr 29, 2009
 ******************************************************************************/
public class HandleXls {

	private static Log log = LogFactory.getLog(HandleXls.class);

	public void getDeptSheetList(Sheet rs, HttpSession session) {
		int deptRows = rs.getRows();
		int deptColumns = rs.getColumns();
		if (deptRows > 1) {
			List<String> deptList = new ArrayList<String>();
			for (int i = 1; i < deptRows; i++) {
				for (int j = 0; j < deptColumns; j++) {
					if (j == 0) {
						String cell = rs.getCell(j, i).getContents().trim();
						deptList.add(cell);
						log.info("dept sheet cell is :: " + cell);
					}
				}
			}
			session.setAttribute("deptList", deptList);
		}
	}

	public static List<String> getDeptSheetList(Sheet rs) {
		int deptRows = rs.getRows();
		int deptColumns = rs.getColumns();
		List<String> deptList = new ArrayList<String>();
		if (deptRows > 1) {
			for (int i = 1; i < deptRows; i++) {
				for (int j = 0; j < deptColumns; j++) {
					if (j == 0) {
						String cell = rs.getCell(j, i).getContents();
						deptList.add(cell);
						log.info("dept sheet cell is :: " + cell);
					}
				}
			}
		}
		return deptList;
	}

	/**
	 * 判断是否为空
	 * 
	 * @param cell
	 * @return
	 */
	public static boolean isNull(String cell) {
		boolean isFlag = false;
		if (cell != null && !"".equals(cell)) {
			isFlag = true;
		}
		return isFlag;
	}

	/**
	 * 企业小号补0
	 */
	public String addzero(String cell) {
		if (cell.length() == 1) {
			cell = "0000" + cell;
		} else if (cell.length() == 2) {
			cell = "000" + cell;
		} else if (cell.length() == 3) {
			cell = "00" + cell;
		} else if (cell.length() == 4) {
			cell = "0" + cell;
		}
		return cell;
	}

	/**
	 * 判断输入子串为数字
	 * 
	 * @param str
	 * @return
	 */
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 验证电子邮件<br>
	 * 可为空
	 * 
	 * @param str
	 * @return
	 */
	public boolean nocheckEMail(String str) {
		boolean flag = true;
		if (str == null || "".equals(str)) {
			flag = false;
			return flag;
		}
		String regEx = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regEx);
		flag = p.matcher(str).matches();
		return flag;
	}

	public boolean checkEMail(String str) {
		boolean flag = true;
		if (str == null || "".equals(str)) {
			return flag;
		}
		String regEx = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(regEx);
		flag = p.matcher(str).matches();
		return flag;
	}

}
