package com.cmcc.common.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cmcc.framework.model.model.WebModel;

/**
 * 用户登陆后整个平台常用的用户信息(保存在HttpSession中)
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public class UserSessionObj {

	/** 当前用户Session的Session ID */
	private String sessionId;

	private Integer eid = 10011;

	/** 用户的 Id */
	private Integer id;

	/** 用户的登陆Id 用户名 */
	private String loginId;

	/** 登入系统的时间 */
	private String loginTime;

	/** 登出系统的时间 */
	private String logOffTime;

	/** 客户端IP地址 */
	private String ipAddress;

	/** 用户当前角色的级别 */
	private Integer roleLevel;

	private Integer pageSize = new Integer(12);

	/** 判断一个帐号在线的数目 * */
	private Integer flag = 0;

	private int rid = 0;

	private List<WebModel> allModel;

	public UserSessionObj() {
		allModel = new ArrayList<WebModel>();
	}

	/**
	 * @return 返回 pageSize。
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            要设置的 pageSize。
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获得当前用户Session的Session ID
	 * 
	 * @return 当前用户Session的Session ID。
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * 设置当前用户Session的Session ID
	 * 
	 * @param sessionId
	 *            要设置的当前用户Session的Session ID。
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * 获得当前用户的id
	 * 
	 * @return 返回当前用户的id。
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 设置当前用户的id
	 * 
	 * @param Id
	 *            要设置的用户的Id。
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 获得用户的登陆Id
	 * 
	 * @return 返回用户的登陆Id。
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * 设置用户的登陆Id
	 * 
	 * @param loginId
	 *            要设置的用户的登陆Id。
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * 获得当前用户的级别
	 * 
	 * @return 返回当前用户的级别
	 */
	public Integer getRoleLevel() {
		return roleLevel;
	}

	/**
	 * 设置当前用户的级别
	 * 
	 * @param roleLevel
	 *            用户级别
	 */
	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String toString() {
		ToStringBuilder strBuilder = new ToStringBuilder(this);

		strBuilder.append("sessionId", this.getSessionId());
		strBuilder.append("Id", this.getId());
		strBuilder.append("loginId", this.getLoginId());
		strBuilder.append("roleLevel", this.getRoleLevel());

		return strBuilder.toString();
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLogOffTime() {
		return logOffTime;
	}

	public void setLogOffTime(String logOffTime) {
		this.logOffTime = logOffTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public boolean isSuperAdmin() {
		if (0 == this.roleLevel) {
			return true;
		}
		return false;
	}

	public List<WebModel> getAllModel() {
		return allModel;
	}

	public void setAllModel(List<WebModel> allModel) {
		this.allModel = allModel;
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

}
