package com.cmcc.framework.controller.action.permission;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.cmcc.common.controller.interceptor.annotations.GenericLogger;
import com.cmcc.common.controller.interceptor.enums.OperateMark;
import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.business.interfaces.model.IWebModelManager;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.ZtreeVO;

/**
 * 权限管理 action
 * 
 * @author zhangzhanliang
 * 
 */
public class PermissionAction extends WebActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7685189414730015620L;

	private Logger logger = Logger.getLogger(PermissionAction.class);

	/**
	 * 浏览 定制分析的树节点
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String viewCustomTree() throws BusinessException {
		Writer writer = null;
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			String srid = getRequest().getParameter("rid");
			writer = this.getResponse().getWriter();
			List<ZtreeVO> l = null;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
				l = new ArrayList<ZtreeVO>();
			} else {
				l = this.permissionManager.findCustomByTree(Integer
						.valueOf(srid));
				map.put("trees", l);
			}

			String btns = permissionManager.findPcenterByRid(Integer
					.valueOf(srid), PermissionMark.CustomBtn_permission
					.getValue());
			if (!StringUtil.isBlank(btns)) {
				String[] objs = btns.split(",");
				map.put("btns", objs);
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();
			if (logger.isDebugEnabled()) {
				logger.debug("json = " + json);
			}
			// json = new String(json.getBytes(),"gbk");
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("定制分析 tree 列表显示失败");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 处理定制分析的树节点
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@GenericLogger(operateMark = OperateMark.ROLE_UPDATE, operateDescription = "角色管理-修改角色功能权限-定制分析")
	public String executeCustomTree() throws BusinessException {
		Writer writer = null;
		try {
			this.getResponse().setContentType("text/html;charset=GBK");
			writer = getResponse().getWriter();
			String srid = getRequest().getParameter("rid");
			String pids = getRequest().getParameter("pids");
			String btns = getRequest().getParameter("btns");
			int c = 0;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
			} else {
				c = this.permissionManager.saveAndUpdatePermission(Integer
						.valueOf(srid), pids, IWebModelManager.CustomModel,
						PermissionMark.CustomTree_permission.getValue());
			}
			// 节点树保存成功后
			if (c != 0) {
				c = this.permissionManager.saveAndUpdatePermission(Integer
						.valueOf(srid), btns, IWebModelManager.CustomModel,
						PermissionMark.CustomBtn_permission.getValue());
			}
			if (c != 0) {
				writer.write("true");
			} else {
				writer.write("false");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("保存定制分析权限出错！");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 浏览 即席查询 树节点
	 * 
	 * @return
	 */
	public String viewQueriesTree() throws BusinessException {
		Writer writer = null;
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			String srid = getRequest().getParameter("rid");
			writer = this.getResponse().getWriter();
			List<ZtreeVO> l = null;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
				l = new ArrayList<ZtreeVO>();
			} else {
				l = this.permissionManager.findQueriesByTree(Integer
						.valueOf(srid));
				map.put("trees", l);
			}

			String btns = permissionManager.findPcenterByRid(Integer
					.valueOf(srid), PermissionMark.QueriesBtn_permission
					.getValue());
			if (!StringUtil.isBlank(btns)) {
				String[] objs = btns.split(",");
				map.put("btns", objs);
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();
			if (logger.isDebugEnabled()) {
				logger.debug("json = " + json);
			}
			logger.info(json);
			// json = new String(json.getBytes(),"gbk");
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("即席查询 tree 列表显示失败");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 处理 即席查询 树权限节点
	 */
	@GenericLogger(operateMark = OperateMark.ROLE_UPDATE, operateDescription = "角色管理-修改角色功能权限-即席查询")
	public String executeQueriesTree() throws BusinessException {
		Writer writer = null;
		try {
			this.getResponse().setContentType("text/html;charset=GBK");
			writer = getResponse().getWriter();
			String srid = getRequest().getParameter("rid");
			String pids = getRequest().getParameter("pids");
			String btns = getRequest().getParameter("btns");
			// 保存节点树
			int c = 0;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
			} else {
				c = this.permissionManager.saveAndUpdatePermission(Integer
						.valueOf(srid), pids, IWebModelManager.QueriesModel,
						PermissionMark.QueriesTree_permission.getValue());
			}
			// 节点树保存成功后
			if (c != 0) {
				c = this.permissionManager.saveAndUpdatePermission(Integer
						.valueOf(srid), btns, IWebModelManager.QueriesModel,
						PermissionMark.QueriesBtn_permission.getValue());
			}

			// 保存按钮权限

			if (c != 0) {
				writer.write("true");
			} else {
				writer.write("false");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("保存即席查询权限出错！");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 浏览 loap 树节点
	 * 
	 * @return
	 */
	public String viewLoapTree() throws BusinessException {
		Writer writer = null;
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			String srid = getRequest().getParameter("rid");
			writer = this.getResponse().getWriter();
			List<ZtreeVO> l = null;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
				l = new ArrayList<ZtreeVO>();
			} else {
				l = this.permissionManager
						.findLoapByTree(Integer.valueOf(srid));
				map.put("trees", l);
			}

			String btns = permissionManager.findPcenterByRid(Integer
					.valueOf(srid), PermissionMark.LoapBtn_permission
					.getValue());
			if (!StringUtil.isBlank(btns)) {
				String[] objs = btns.split(",");
				map.put("btns", objs);
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();

			if (logger.isDebugEnabled()) {
				logger.debug("json = " + json);
			}
			logger.info(json);
			// json = new String(json.getBytes(),"gbk");
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("loap tree 列表显示失败");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 处理 loap 树权限节点
	 */
	@GenericLogger(operateMark = OperateMark.ROLE_UPDATE, operateDescription = "角色管理-修改角色功能权限-olap")
	public String executeLoapTree() throws BusinessException {
		Writer writer = null;
		try {
			this.getResponse().setContentType("text/html;charset=GBK");
			writer = getResponse().getWriter();
			String srid = getRequest().getParameter("rid");
			String pids = getRequest().getParameter("pids");
			String btns = getRequest().getParameter("btns");
			int c = 0;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
			} else {
				c = this.permissionManager.saveAndUpdatePermission(Integer
						.valueOf(srid), pids, IWebModelManager.LoapModel,
						PermissionMark.LoapTree_permission.getValue());
			}

			// 节点树保存成功后
			if (c != 0) {
				c = this.permissionManager.saveAndUpdatePermission(Integer
						.valueOf(srid), btns, IWebModelManager.LoapModel,
						PermissionMark.LoapBtn_permission.getValue());
			}

			if (c != 0) {
				writer.write("true");
			} else {
				writer.write("false");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("保存loap权限出错！");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 浏览 原始数据 树节点
	 * 
	 * @return
	 */
	public String viewRawDataTree() throws BusinessException {
		Writer writer = null;
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			String srid = getRequest().getParameter("rid");
			writer = this.getResponse().getWriter();
			List<ZtreeVO> l = null;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
				l = new ArrayList<ZtreeVO>();
			} else {
				l = this.permissionManager.findRowDataByTree(Integer
						.valueOf(srid));
			}
			map.put("trees", l);
			String btns = permissionManager.findPcenterByRid(Integer
					.valueOf(srid), PermissionMark.RawDataBtn_permission
					.getValue());
			if (!StringUtil.isBlank(btns)) {
				String[] objs = btns.split(",");
				map.put("btns", objs);
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();
			if (logger.isDebugEnabled()) {
				logger.debug("json = " + json);
			}
			logger.info(json);
			// json = new String(json.getBytes(),"gbk");
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("原始数据 tree 列表显示失败");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 处理 原始数据 树权限节点
	 */
	@GenericLogger(operateMark = OperateMark.ROLE_UPDATE, operateDescription = "角色管理-修改角色功能权限-原始数据")
	public String executeRawDataTree() throws BusinessException {
		Writer writer = null;
		try {
			this.getResponse().setContentType("text/html;charset=GBK");
			writer = getResponse().getWriter();
			String srid = getRequest().getParameter("rid");
			String pids = getRequest().getParameter("pids");
			String btns = getRequest().getParameter("btns");
			int c = 0;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
			} else {
				c = this.permissionManager.saveAndUpdatePermission(Integer
						.valueOf(srid), pids, IWebModelManager.RawDataModel,
						PermissionMark.RawDataTree_permission.getValue());
			}

			// 节点树保存成功后
			if (c != 0) {
				c = this.permissionManager.saveAndUpdatePermission(Integer
						.valueOf(srid), btns, IWebModelManager.RawDataModel,
						PermissionMark.RawDataBtn_permission.getValue());
			}

			if (c != 0) {
				writer.write("true");
			} else {
				writer.write("false");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("保存  原始数据 权限出错！");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 浏览 数据下载 树节点
	 * 
	 * @return
	 */
	public String viewDataDownTree() throws BusinessException {
		Writer writer = null;
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			String srid = getRequest().getParameter("rid");
			writer = this.getResponse().getWriter();
			List<ZtreeVO> l = null;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
				l = new ArrayList<ZtreeVO>();
			} else {
				l = this.permissionManager.findDataDownByTree(Integer
						.valueOf(srid));
			}
			map.put("trees", l);
//			String btns = permissionManager.findPcenterByRid(Integer
//					.valueOf(srid),
//					PermissionMark.CenterAttachDownload_permission.getValue());
//			if (!StringUtil.isBlank(btns)) {
//				String[] objs = btns.split(",");
//				map.put("btns", objs);
//			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();
			if (logger.isDebugEnabled()) {
				logger.debug("json = " + json);
			}
			logger.info(json);
			// json = new String(json.getBytes(),"gbk");
			writer.write(json);
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("数据管理 tree 列表显示失败");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 处理 数据下载 树权限节点
	 */
	@GenericLogger(operateMark = OperateMark.ROLE_UPDATE, operateDescription = "角色管理-修改角色功能权限-数据下载")
	public String executeDataDownTree() throws BusinessException {
		Writer writer = null;
		try {
			this.getResponse().setContentType("text/html;charset=GBK");
			writer = getResponse().getWriter();
			String srid = getRequest().getParameter("rid");
			String pids = getRequest().getParameter("pids");
			// String btns = getRequest().getParameter("btns");
			int c = 0;
			if (StringUtil.isBlank(srid) || !StringUtil.isNumeric(srid)) {
				logger.error("rid  is null or rid is not numeric | rid = "
						+ srid);
			} else {
				c = this.permissionManager.saveAndUpdatePermission(Integer
						.valueOf(srid), pids, IWebModelManager.DateDownModel,
						PermissionMark.DataDownTree_permission.getValue());
			}

			// 节点树保存成功后
			
//			if (c != 0) {
//				c = this.permissionManager.saveAndUpdatePermission(Integer
//						.valueOf(srid), btns, IWebModelManager.CenterModel,
//						PermissionMark.CenterAttachDownload_permission
//								.getValue());
//			}

			if (c != 0) {
				writer.write("true");
			} else {
				writer.write("false");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("保存  报告分析 权限出错！");
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		return null;
	}

	/**
	 * 权限管理首页
	 * 
	 * @return
	 */
	public String index() throws BusinessException {
		String rid = getRequest().getParameter("rid");
		String rname = getRequest().getParameter("rname");
		getRequest().setAttribute("rid", rid);
		getRequest().setAttribute("rname", rname);
		return "index";
	}

	/**
	 * 权限管理的
	 */
	public String execute() {
		return null;
	}

}
