package com.cmcc.framework.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "workflow_node")
public class WorkflowNode {

	private Integer nid;// 主键id

	private Integer wid;// 工程id

	private Integer pid;// 父级节点id

	private String nodeName;// 节点名称

	private String nodeType;// 节点类型

	private String nodePath;// 节点图片路径

	private Date dmlTime; // 操作时间

	private Integer dmlFlog; // 操作类型 1 表示 添加 2 表示修改 3 表示删除

	private String nodeSetting;// 表信息

	private String showName;// 显示名称

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "nid")
	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	@Column(name = "wid")
	public Integer getWid() {
		return wid;
	}

	public void setWid(Integer wid) {
		this.wid = wid;
	}

	@Column(name = "node_name")
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Column(name = "node_type")
	public String getNodeType() {
		if (nodeType == null) {
			return "";
		}
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	@Column(name = "node_path")
	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	@Column(name = "dmltime", length = 0)
	public Date getDmlTime() {
		return dmlTime;
	}

	public void setDmlTime(Date dmlTime) {
		this.dmlTime = dmlTime;
	}

	@Column(name = "dmlflog")
	public Integer getDmlFlog() {
		return dmlFlog;
	}

	public void setDmlFlog(Integer dmlFlog) {
		this.dmlFlog = dmlFlog;
	}

	@Column(name = "pid")
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "node_setting")
	public String getNodeSetting() {
		return nodeSetting;
	}

	public void setNodeSetting(String nodeSetting) {
		this.nodeSetting = nodeSetting;
	}

	@Transient
	public String getShowName() {
		if (nodeName.indexOf("Pie") != -1) {
			return "饼状图";
		}

		if (nodeName.indexOf("Histogram") != -1) {
			return "柱状图";
		}
		if (nodeName.indexOf("Database") != -1) {
			return "数据";
		}
		return nodeName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

}
