package com.cmcc.framework.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @filename: com.cmcc.framework.model.Structure
 * @copyright: Copyright (c)2015
 * @company: 北京睿思恒信科技有限公司
 * @author: 张占亮
 * @version: 1.0
 * @create time: 2015-9-16 下午3:16:30
 * @record <table cellPadding="3" cellSpacing="0" style="width:600px">
 *         <thead style="font-weight:bold;background-color:#e3e197">
 *         <tr>
 *         <td>date</td>
 *         <td>author</td>
 *         <td>version</td>
 *         <td>description</td>
 *         </tr>
 *         </thead> <tbody style="background-color:#ffffeb">
 *         <tr>
 *         <td>2015-9-16</td>
 *         <td>张占亮</td>
 *         <td>1.0</td>
 *         <td>create</td>
 *         </tr>
 *         </tbody>
 *         </table>
 */
@Entity
@Table(name = "structure_db")
public class Structure implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1079031035858264652L;
	private static Logger log = Logger.getLogger(Structure.class);
	/**
	 * 主键ID
	 */
	private Integer sid;
	/**
	 * 父类节点ID
	 */
	private Integer spid;

	/**
	 * 显示名称
	 */
	private String sname;
	/**
	 * 节点CODE
	 */
	private String scenter;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sid", unique = true, nullable = false)
	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	@Column(name = "spid")
	public Integer getSpid() {
		return spid;
	}

	public void setSpid(Integer spid) {
		this.spid = spid;
	}

	@Column(name = "sname")
	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	@Column(name = "scenter")
	public String getScenter() {
		return scenter;
	}

	public void setScenter(String scenter) {
		this.scenter = scenter;
	}

}
