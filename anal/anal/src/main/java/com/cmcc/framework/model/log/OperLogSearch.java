/**      
 * 文件名称： OperLog.java
 * 
 * 创建时间： Mar 23, 2009 1:41:10 PM
 *      
 * Copyright (c) 2009 by Li Benyong.      
 */
package com.cmcc.framework.model.log;

import com.cmcc.common.persistence.support.SearchCondition;

/**
 * OperLog实体类 功能描述：
 * 
 * @author <a href="mailto:shilimin@hdxt.net.cn">Shilimin/a>
 * 
 * @version 1.1
 */

public class OperLogSearch extends SearchCondition {
	private static final long serialVersionUID = 5129397248020971771L;

	private Integer eid = new Integer(0);

	/**
	 * 管理员id
	 */
	private Integer adminId;

	/**
	 * 操作时间
	 */
	private String operateTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 管理员名称
	 */
	private String adminName;

	/**
	 * 根据eid查询到的表名
	 */
	private String tableName;

	public Integer getEid() {

		return eid;
	}

	public void setEid(Integer eid) {

		this.eid = eid;
	}

	public Integer getAdminId() {

		return adminId;
	}

	public void setAdminId(Integer adminId) {

		this.adminId = adminId;
	}

	public String getOperateTime() {

		return operateTime;
	}

	public void setOperateTime(String operateTime) {

		this.operateTime = operateTime;
	}

	public String getAdminName() {

		return adminName;
	}

	public void setAdminName(String adminName) {

		this.adminName = adminName;
	}

	public String getTableName() {

		return tableName;
	}

	public void setTableName(String tableName) {

		this.tableName = tableName;
	}

	public String getEndTime() {

		return (endTime != null && !"".equals(endTime)) ? endTime + " 59:59:59" : null;
	}

	public void setEndTime(String endTime) {

		this.endTime = endTime;
	}

}
