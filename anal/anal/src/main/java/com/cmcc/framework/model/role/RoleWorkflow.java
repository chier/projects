package com.cmcc.framework.model.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cmcc.common.model.PersistentObject;

/**
 * 角色与项目关系映射类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-11
 */
@Entity
@Table(name = "gweb_role_workflow")
public class RoleWorkflow extends PersistentObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8572742504861634877L;

	Integer id;

	Integer rid;

	Integer wid;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "wrid")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "rid")
	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

	@Column(name = "wid")
	public Integer getWid() {
		return wid;
	}

	public void setWid(Integer wid) {
		this.wid = wid;
	}

	@Override
	@Transient
	public Object getIdentifier() {
		return id;
	}

}
