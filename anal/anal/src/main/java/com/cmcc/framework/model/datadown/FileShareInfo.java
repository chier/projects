package com.cmcc.framework.model.datadown;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 文件共享信息
 * 
 * @author zhangzhanliang
 * 
 */
@Entity
@Table(name = "file_share")
public class FileShareInfo {

	private Integer id;

	private Integer pid;

	private String node_name;

	private Integer node_type;

	private String suffix;

	private String adminName;

	private Date createTime;

	private String desc;

	private Integer size;
	
	private String path;

	public String getPath() {
		return path;
	}
	@Column(name = "path")
	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "adminName")
	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	@Column(name = "createTime")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "desc")
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name = "node_name")
	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}
	@Column(name = "node_type")
	public Integer getNode_type() {
		return node_type;
	}

	public void setNode_type(Integer node_type) {
		this.node_type = node_type;
	}
	@Column(name = "pid")
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	@Column(name = "size")
	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
	@Column(name = "suffix")
	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

}
