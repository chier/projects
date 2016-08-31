package com.cmcc.anal.framework.model.datadown;

import com.cmcc.anal.common.persistence.support.SearchCondition;


/**
 * 文件共享列表集合
 * 
 * @author Administrator
 * 
 */
public class FileShareSearcher extends SearchCondition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6954651073437820013L;

	/**
	 * 目录id 如果等于0 获取所有文件信息
	 * 
	 */
	private Integer fsId;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 文件类型
	 */
	private String fileExt;

	/**
	 * 起始时间
	 */
	private String operateTime;

	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 当前页
	 */
	private int curPage;
	
	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public Integer getFsId() {
		return fsId;
	}

	public void setFsId(Integer fsId) {
		this.fsId = fsId;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

}
