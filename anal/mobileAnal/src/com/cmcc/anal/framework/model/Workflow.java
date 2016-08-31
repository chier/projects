package com.cmcc.anal.framework.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ImopAdmin entity.
 * 
 * @author 
 */
@Entity
@Table(name = "workflow")
public class Workflow implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2970961484916163439L;

	/**
	 * id
	 */
	private Integer id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 创建时间
	 */
	private Date createtime;

	/**
	 * 操作时间
	 */
	private Date dmltime;

	/**
	 * 操作类型
	 */
	private Integer dmlflog;

	@Column(name = "dmlflog")
	public Integer getDmlflog() {
		return dmlflog;
	}

	public void setDmlflog(Integer dmlflog) {
		this.dmlflog = dmlflog;
	}

	@Column(name = "dmltime", length = 0)
	public Date getDmltime() {
		return dmltime;
	}

	public void setDmltime(Date dmltime) {
		this.dmltime = dmltime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 128)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "createtime", length = 0)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}