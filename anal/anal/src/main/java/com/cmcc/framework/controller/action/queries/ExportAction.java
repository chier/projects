package com.cmcc.framework.controller.action.queries;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.Global;
import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.StringUtil;
import com.cmcc.common.util.date.DateUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.ItemTableVO;

/**
 * 导出 excel 方法
 * 
 * @author zhangzhanliang
 * 
 */
public class ExportAction extends WebActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4702157379522863292L;

	/**
	 * 每次查询数
	 */
	private final int PAGESIZE = 500;

	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory.getLog(QueriesAction.class);

	/**
	 * 导出方法
	 */
	@GenericLogger(operateMark = OperateMark.QUERIES_EXPORT,operateDescription = "即席查询-导出数据", isExtend = true)
	public String execute() throws BusinessException {
		OutputStream os = null;
		try {
			String tableCode = getRequest().getParameter("tableCode");
			String searchValue = getRequest().getParameter("searchValue");

			this.getRequest().setCharacterEncoding("UTF-8");
			String currentDay = DateUtil.currentDate();
			String fname = tableCode + "查询结果" + currentDay + ".xls";// Excel文件名字
			// String sql = “xxxx”;
			// List list = getService().findList(sql);

			os = getResponse().getOutputStream();
			getResponse().reset();
			getResponse().setHeader(
					"Content-disposition",
					"attachment;filename="
							+ new String(fname.getBytes("GBK"), "ISO8859-1"));
			getResponse().setContentType("application/msexcel;charset=UTF-8");

			if (StringUtil.isBlank(tableCode)) {
				logger.error("tableCode is null");
				throw new BusinessException("tableCode is null");
			}

			List<ItemTableVO> itemVoList = queriesManager
					.findItemTableBy(tableCode);
			long totalresult = this.queriesManager.findCountByTableItem(
					tableCode, itemVoList, searchValue);
			int c = 0;
			int curPage = 0;
			// int row = 1;
//			Page page = new Page(totalresult, PAGESIZE);
			List<Object[]> tableList = null;
//			while (c < totalresult) {
//				page.setPage(curPage);
				tableList = this.queriesManager.findByTableItem(tableCode,
						itemVoList, searchValue);

				exportExcel(os, itemVoList, tableList, c + 1, curPage);
//				c += PAGESIZE;
//				curPage++;
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
					os = null;
				} catch (IOException e) {
					logger.error("关闭下载流异常", e);
					throw new BusinessException("关闭下载流异常");
				}

			}
		}
		return null;
	}

	/**
	 * 
	 * @param os
	 * @param itemVoList
	 * @param tableList
	 * @param row
	 * @param col
	 * @throws Exception
	 */
	public void exportExcel(OutputStream os, List<ItemTableVO> itemVoList,
			List<Object[]> tableList, int row, int col) throws Exception {

		// int row = 1;// 从第三行开始写
		// int col = 0;// 从第一列开始写
		WritableWorkbook wwb = null;
		try {
			wwb = Workbook.createWorkbook(os);// 创建Excel文件
			WritableSheet ws = wwb.createSheet("导出信息", 0);// 创建sheet
			// 设置表头
			Label label = null;
			ItemTableVO vo = null;
			if (col == 0) {
				for (int i = 0; i < itemVoList.size(); i++) {
					vo = itemVoList.get(i);
					label = new Label(i, 0, vo.getItemName());
					ws.addCell(label);
				}
			}
			// 设置表数据
			if (tableList != null && tableList.size() > 0) {
				Object[] arro = null;
				for (int i = 0; i < tableList.size(); i++) {
					arro = (Object[]) tableList.get(i);
					for (int j = 0; j < arro.length; j++) {
						ws.addCell(new Label(col++, row, String
								.valueOf(arro[j])));// 
					}
					row++;
					col = 0;
				}

			}
			wwb.write();

		} catch (Exception e) {
			logger.error("导出 excel 异常", e);
		} finally {
			itemVoList = null;
			if (wwb != null) {
				try {
					wwb.close();
				} catch (Exception e) {
					logger.error("导出 excel 异常", e);

				} finally {
					wwb = null;
				}
			}
		}
	}
}
