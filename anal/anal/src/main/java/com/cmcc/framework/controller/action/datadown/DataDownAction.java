package com.cmcc.framework.controller.action.datadown;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.cmcc.common.Global;
import com.cmcc.common.exception.BusinessException;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.controller.action.webaction.WebActionBase;
import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.model.content.ContentInfo;
import com.cmcc.framework.model.datadown.FileShareInfo;
import com.cmcc.framework.model.datadown.FileShareSearcher;
import com.cmcc.framework.model.datadown.PilotInfo;

/**
 * 数据下载或者称为数据管理操作action类
 * 
 * @author zhangzhanliang
 * 
 */
public class DataDownAction extends WebActionBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1293059389311874600L;

	private Logger logger = Logger.getLogger(DataDownAction.class);

	private File upfile;

	private String upfileFileName;

	private String upfileContentType;

	public File getUpfile() {
		return upfile;
	}

	public void setUpfile(File upfile) {
		this.upfile = upfile;
	}

	public String getUpfileContentType() {
		return upfileContentType;
	}

	public void setUpfileContentType(String upfileContentType) {
		this.upfileContentType = upfileContentType;
	}

	public String getUpfileFileName() {
		return upfileFileName;
	}

	public void setUpfileFileName(String upfileFileName) {
		this.upfileFileName = upfileFileName;
	}

	/**
	 * 数据下载 首页
	 * 
	 * @return
	 * /manage/datadown/dataDownAction!indexDataDown.portal
	 */
	public String indexDataDown() {
		int rid = this.getUserSessionInfo().getRid();
		List<PilotInfo> plist = this.dataDownManager.findPilotByPidAndRid(0,rid);
		String sid = getRequest().getParameter("sid");
		if (StringUtils.isBlank(sid) || !StringUtils.isNumeric(sid)) {
			if (plist != null && plist.size() > 0) {
				sid = plist.get(0).getId().toString();
			}
		}
		List<PilotInfo> tlist = this.dataDownManager.findPilotByPidAndRid(Integer
				.valueOf(sid),0);

		List<ContentInfo> conList = this.contentInfoManager
				.findContentInfoBySid(Integer.valueOf(sid));
		
		// 存储信息
		getRequest().setAttribute("plist", plist);
		getRequest().setAttribute("tlist", tlist);
		getRequest().setAttribute("conList", conList);
		getRequest().setAttribute("sid", sid);

		return "indexDataDown";
	}

	/**
	 * 文件共享首页
	 * 
	 * @return
	 * @throws BusinessException
	 * /manage/datadown/dataDownAction!indexFileShare.portal
	 */
	public String indexFileShare() throws BusinessException {
		try {
			Map map = new HashMap();
			this.getResponse().setContentType("text/html;charset=GB2312");
			List<ZtreeVO> l = null;
			int rid = getUserSessionInfo().getRid();
			if (0 == getUserSessionInfo().getRoleLevel().intValue()) {
				rid = 0;
			}
			l = this.dataDownManager.findTreeByRid(0, rid);
			map.put("trees", l);

			JSONObject jsonObject = JSONObject.fromObject(map);
			// JSONArray jsonArray = JSONArray.fromObject(map);
			String json = jsonObject.toString();
			logger.info("json = " + json);
			// json = new String(json.getBytes(),"gbk");
			getRequest().setAttribute("treesJson", json);
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("文件管理 首页获取数据失败！");
		}
		return "indexFileShare";
	}

	/**
	 * 返回文件共享列表
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String tableListFileShare() throws BusinessException {
		//
		FileShareSearcher search = new FileShareSearcher();
		// 获取条件信息
		HttpServletRequest request = this.getRequest();

		this.getResponse().setContentType("text/html;charset=utf8");
		// 目录id
		String fsId = request.getParameter("fsId");
		if (!StringUtils.isBlank(fsId) && StringUtils.isNumeric(fsId)) {
			search.setFsId(Integer.valueOf(fsId));
		}
		// 目录名称
		String title = request.getParameter("title");
		if (!org.apache.commons.lang.StringUtils.isBlank(title)
				&& "GET".equalsIgnoreCase(request.getMethod())) {
			try {
				title = new String(title.getBytes("ISO8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		// 文件名称
		String fileName = request.getParameter("fileName");
		if (!StringUtils.isBlank(fileName)) {
			search.setFileName(fileName);
		}
		// 文件类型
		String fileExt = request.getParameter("fileExt");
		if (!StringUtils.isBlank(fileExt)) {
			search.setFileExt(fileExt);
		}
		// 起始时间
		String operateTime = request.getParameter("operate_time");
		if (!StringUtils.isBlank(operateTime)) {
			search.setOperateTime(operateTime + ":00");
		}
		// 结束时间
		String endTime = request.getParameter("end_time");
		if (!StringUtils.isBlank(endTime)) {
			search.setEndTime(endTime + ":59");
		}

		// 当前页
		String curPage = request.getParameter("curPage");
		if (!StringUtils.isBlank(curPage) && StringUtils.isNumeric(curPage)) {
			search.setCurPage(Integer.valueOf(curPage).intValue());
		} else {
			search.setCurPage(1);
		}
		// 删除的 id
		String delId = request.getParameter("delId");
		if (!StringUtils.isBlank(delId)) {
			this.dataDownManager.removeFile(delId);
		}

		// 查询数据信息
		Object[] objArr = this.dataDownManager.findFileByFileShare(search, 12);
		Page pageInfo = (Page) objArr[0];
		List<FileShareInfo> infoList = (List<FileShareInfo>) objArr[1];
		List<String> suffixList = (List<String>) objArr[2];
		// 发送到页面显示
		request.setAttribute("pageInfo", pageInfo);
		request.setAttribute("infoList", infoList);
		request.setAttribute("suffixList", suffixList);
		request.setAttribute("fsId", fsId);
		request.setAttribute("title", title);
		request.setAttribute("fileName", fileName);
		request.setAttribute("fileExt", fileExt);
		request.setAttribute("operate_time", operateTime);
		request.setAttribute("end_time", endTime);
		request.setAttribute("curPage", curPage);
		return "tableFileShare";
	}

	/**
	 * 发送到添加文件页面 1.注意添加文件时的参数。 2.返回文件时的参数
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String toAddFileShare() throws BusinessException {
		String fsId = getRequest().getParameter("fsId");
		String title = getRequest().getParameter("title");

		getRequest().setAttribute("fsId", fsId);
		getRequest().setAttribute("title", title);
		return "toAddFileShare";
	}

	private static void copy(File src, File dst) {
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new BufferedInputStream(new FileInputStream(src),
						16 * 1024);
				out = new BufferedOutputStream(new FileOutputStream(dst),
						16 * 1024);
				byte[] buffer = new byte[16 * 1024];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 添加文件并存储到数据库 1.文件上传 2.文件信息的添加 3.返回浏览信息
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public String addFileShare() throws BusinessException {
		String fsId = getRequest().getParameter("fsId");
		String title = getRequest().getParameter("title");
		String fileDesc = getRequest().getParameter("fileDesc");

		// 定义常量,保存文件路径
		final String FILE_PATH = this.getRealPath() + File.separator
				+ Global.staticFileShare + File.separator;// 文件上传的路径

		try {
			// 文件名
			String fname = String.valueOf(System.currentTimeMillis());
			// 后缀名
			String suffix = this.getUpfileFileName().substring(
					getUpfileFileName().lastIndexOf(".") + 1);

			File dst = new File(FILE_PATH + File.separator + fname + "."
					+ suffix);
			copy(upfile, dst);
			FileShareInfo info = new FileShareInfo();
			info.setPath(dst.getAbsolutePath());
			info.setSize(((Long) upfile.length()).intValue());
			info.setSuffix(suffix);
			info.setNode_name(this.upfileFileName.substring(0, upfileFileName
					.lastIndexOf(".")));

			info.setNode_type(1);
			info.setAdminName(this.getUserSessionInfo().getLoginId());
			int pid = 0;
			if (!StringUtils.isBlank(fsId) && StringUtils.isNumeric(fsId)) {
				pid = Integer.valueOf(fsId);
			}
			info.setPid(pid);
			info.setDesc(fileDesc);

			this.dataDownManager.saveFileShare(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.tableListFileShare();
	}

	/**
	 * 下载方法类
	 * 
	 * @return
	 * @throws BusinessException
	 * @throws UnsupportedEncodingException
	 */
	public String download() throws BusinessException,
			UnsupportedEncodingException {
		String sctId = getRequest().getParameter("fsId");

		if (StringUtil.isBlank(sctId) || !StringUtil.isNumeric(sctId)) {
			return null;
		}
		FileShareInfo info = this.dataDownManager.findFileShareInfoById(sctId);

		if (info == null || StringUtil.isBlank(info.getNode_name())
				|| StringUtil.isBlank(info.getPath())) {
			return null;
		}
		File file = new File(info.getPath());
		if (!file.exists()) {
			return null;
		}
		String attName = info.getNode_name() + "." + info.getSuffix();
		attName = new String(attName.getBytes("GBK"), "ISO8859-1");
		getResponse().setContentType("application/octet-stream;charset=GBK");
		getResponse().setHeader("Content-Disposition",
				"attachment; filename= " + attName);
		OutputStream outputStream = null;
		InputStream inputStream = null;
		try {
			outputStream = this.getResponse().getOutputStream();
			inputStream = new FileInputStream(file);
			byte[] bytes = new byte[1024];
			int x = 0;
			while ((x = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes);
			}
		} catch (IOException ioe) {
			logger.error(ioe);
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
				inputStream = null;
			}
			if (outputStream != null) {

				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
				outputStream = null;
			}
		}
		return null;
	}

	/**
	 * 删除文件操作
	 * 
	 * @return
	 * @throws BusinessException
	 */
	public void removeFile(String delId) {

	}

	/**
	 * 文件共享列表页 1.搜索 2.分页 3.返回当前的文件类型列表
	 * 
	 * @return
	 */
	public String fileShareList() {
		return null;
	}

	/**
	 * 文件增加页面 1.上传 2.描述信息 3.存入数据库
	 * 
	 * @return
	 */
	public String fileShareAdd() {
		return null;
	}

	/**
	 * 创建树目录
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String createFileShareDirectory() throws BusinessException {
		Writer writer = null;
		try {
			String name = getRequest().getParameter("name");
			String pid = getRequest().getParameter("pid");
			writer = getResponse().getWriter();
			logger.info("name = " + name);
			logger.info("pid = " + pid);
			if (!StringUtil.isBlank(name) && !StringUtil.isBlank(pid)) {
				// Long id = this.customManager.addTreeDirectory(name, pid);
				Long id = this.dataDownManager.createFileShareDirectory(name,
						pid);
				writer.write(id.toString());
			} else {
				writer.write("");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
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
	 * 重命名目录名称
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String renameFileShareDirectory() throws BusinessException {
		Writer writer = null;
		try {
			String name = getRequest().getParameter("name");
			String id = getRequest().getParameter("id");
			writer = getResponse().getWriter();
			logger.info("name = " + name);
			logger.info("id = " + id);
			if (!StringUtil.isBlank(name) && !StringUtil.isBlank(id)) {
				// this.customManager.renameTreeDirectory(name, id);
				this.dataDownManager.renameFileShareDirectory(name, id);
				writer.write(id.toString());
			} else {
				writer.write("");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
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
	 * 删除目录
	 * 
	 * @return
	 * @throws BusinessException
	 */
	@SuppressWarnings("unchecked")
	public String removeFileShareDirectory() throws BusinessException {
		Writer writer = null;
		try {
			String id = getRequest().getParameter("id");
			writer = getResponse().getWriter();
			logger.info("id = " + id);
			if (!StringUtil.isBlank(id)) {
				// this.customManager.removeTreeDirectory(id);
				this.dataDownManager.removeFileShareDirectory(id);
				writer.write(id.toString());
			} else {
				writer.write("");
			}
			writer.flush();
		} catch (Exception e) {
			logger.error(e);
			throw new BusinessException("预警分析 type 与 code 信息树返回错误");
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
}
