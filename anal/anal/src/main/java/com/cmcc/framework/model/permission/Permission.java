package com.cmcc.framework.model.permission;

import java.util.Date;

/**
 * 1. 对各个模块各个按钮 可以存在权限中 以便于细分
 *      2.
 * @author Administrator
 * @version 1.0
 * @created 13-三月-2013 15:27:34
 */
public class Permission{

	/**
	 * 主键id
	 */
	private int pid;
	/**
	 * 权限类型 id是会变化 类型一般不发生变化。
	 */
	private int ptype;
	/**
	 * 权限名称
	 */
	private static String pname;
	/**
	 * 权限备注
	 */
	private String pdesc;
	/**
	 * 权限创建时间
	 */
	private Date createtime;
	/**
	 * 修改时间
	 */
	private Date dmltime;
	/**
	 * 当前状态 
	 */
	private int dmlflog;
	/**
	 * 管理员备注 用来解释权限的规则
	 */
	private String developdesc;
	/**
	 * 权限内容体 权限不同 内容体不同
	 */
	private String pcenter;
	/**
	 * 是否是默认类型  1表示 true  是默认类型 。0 表示false  是自定义类型 
	 */
	private int pdefault;
	/**
	 * 可以更好的标识 某个模块。
	 */
	private int mid;

	public 	Permission(){

	}

	public void finalize() throws Throwable {

	}

	public int getPtype(){
		return ptype;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setPtype(int newVal){
		ptype = newVal;
	}

	public String getPname(){
		return pname;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setPname(String newVal){
		pname = newVal;
	}

	public Date getCreatetime(){
		return createtime;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setCreatetime(Date newVal){
		createtime = newVal;
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

	public String getDevelopdesc(){
		return developdesc;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setDevelopdesc(String newVal){
		developdesc = newVal;
	}

	public String getPcenter(){
		return pcenter;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setPcenter(String newVal){
		pcenter = newVal;
	}

	public int getPdefault(){
		return pdefault;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setPdefault(int newVal){
		pdefault = newVal;
	}

	public int getMid(){
		return mid;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setMid(int newVal){
		mid = newVal;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	
	
}