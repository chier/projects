/*
 * 文件名： Department.java
 * 
 * 创建日期： 2009-2-18
 *
 * Copyright(C) 2009, by conntsing.
 *
 * 原始作者: <a href="mailto:sun128837@126.com">conntsing</a>
 *
 */
package com.cmcc.framework.model.department;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.cmcc.common.model.GroupObject;

/**
 * 
 * 树型结构的企业组织机构表
 * 
 * @author <a href="mailto:sun128837@126.com">conntsing</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-2-18
 */
@Entity
@Table(name = "gweb_departments")
public class Department extends GroupObject {

	private static final long serialVersionUID = -2613975046804080796L;

	/**
	 * 企业id
	 */
	private Integer eid;

	/**
	 * 企业组织描述
	 */
	private String descrption;

	/**
	 * 部门id
	 */
	private Integer deptId;

	/**
	 * 显示在页面的企业组织描述
	 */
	private String showDes;

	/**
	 * 排序号
	 */
	private Integer departmentOrder;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 操作时间
	 */
	private Date dmltime;

	/**
	 * 操作标识
	 */
	private Short dmlflog;

	private Short depth;

	private String tips;

	/**
	 * @return 返回 eid。
	 */
	@Column(name = "eid")
	public Integer getEid() {
		return eid;
	}

	@Override
	@Column(name = "leafflag", length = 4)
	public Integer getIsLeaf() {
		return isLeaf;
	}

	@Override
	@Column(name = "parentid")
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * @param eid
	 *            要设置的 eid。
	 */
	public void setEid(Integer eid) {
		this.eid = eid;
	}

	/**
	 * @return 返回 企业组织描述。
	 */
	@Column(name = "deptdesc", length = 200)
	public String getDescrption() {
		return descrption;
	}

	/**
	 * @param descrption
	 *            要设置的 企业组织描述。
	 */
	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	/**
	 * @return 返回 departmentOrder。
	 */
	@Column(name = "deptorder")
	public Integer getDepartmentOrder() {
		return departmentOrder;
	}

	/**
	 * @param departmentOrder
	 *            要设置的 departmentOrder。
	 */
	public void setDepartmentOrder(Integer departmentOrder) {
		this.departmentOrder = departmentOrder;
	}

	/**
	 * @return 返回 创建时间。
	 */
	@Column(name = "createtime", nullable = true)
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            要设置的 创建时间。
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String toString() {
		ToStringBuilder strBuilder = new ToStringBuilder(this);
		strBuilder.append(super.toString());
		strBuilder.append("name", this.getName());

		strBuilder.append("descrption", this.getDescrption());

		strBuilder.append("createTime", this.getCreateTime().toString());
		return strBuilder.toString().replaceAll("'", "\"");
	}

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Department))
			return false;
		if (super.equals(o)) {
			if (this.getIdentifier() != null) {
				return this.getIdentifier().equals(
						((Department) o).getIdentifier());
			} else {
				return (((Department) o).getIdentifier() == null);
			}
		} else {
			return false;
		}
	}

	@Override
	@Column(name = "deptname")
	public String getName() {
		return name;
	}

	@Transient
	public String getShowDes() {
		return showDes;
	}

	public void setShowDes(String showDes) {
		this.showDes = showDes;
	}

	@Column(name = "depth", nullable = false)
	public Short getDepth() {
		return depth;
	}

	public void setDepth(Short depth) {
		this.depth = depth;
	}

	@Transient
	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "deptid")
	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	@Column(name = "dmlflag")
	public Short getDmlflog() {
		return dmlflog;
	}

	public void setDmlflog(Short dmlflog) {
		this.dmlflog = dmlflog;
	}

	@Column(name = "dmltime")
	public Date getDmltime() {
		return dmltime;
	}

	public void setDmltime(Date dmltime) {
		this.dmltime = dmltime;
	}

	@Override
	@Transient
	public Integer getIdentifier() {
		return this.deptId;
	}

	// public void test(){
	// @SuppressWarnings("unused")
	// ACTIVITY_TYPE type = ACTIVITY_TYPE.NANME;
	// ACTIVITY_TYPE test=(ACTIVITY_TYPE)Enum.valueOf(ACTIVITY_TYPE.class,"类型");
	// }

}
