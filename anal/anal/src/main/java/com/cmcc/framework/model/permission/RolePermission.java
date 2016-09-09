package com.cmcc.framework.model.permission;

import java.util.Date;

/**
 * 角色与权限关联表
 * @author Administrator
 * @version 1.0
 * @created 13-三月-2013 15:15:47
 */
public class RolePermission{

	/**
	 * 角色id
	 */
	private int rid;
	/**
	 * 权限id
	 */
	private int pid;
	/**
	 * 修改时间
	 */
	private Date dmltime;
	/**
	 * 修改状态
	 */
	private int dmlflog;

	public RolePermission(){

	}

	public void finalize() throws Throwable {

	}

	public int getRid(){
		return rid;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setRid(int newVal){
		rid = newVal;
	}

	public int getPid(){
		return pid;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setPid(int newVal){
		pid = newVal;
	}

	public Date getDmltime(){
		return dmltime;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setDmltime(Date newVal){
		dmltime = newVal;
	}

	public int getDmlflog(){
		return dmlflog;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setDmlflog(int newVal){
		dmlflog = newVal;
	}

}