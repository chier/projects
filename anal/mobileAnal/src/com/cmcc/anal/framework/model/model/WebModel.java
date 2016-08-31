package com.cmcc.anal.framework.model.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.cmcc.anal.common.model.PersistentObject;


/**
 * 模块信息对应的Java类
 * 
 * @author <a href="mailto:gaojunling@fetionyy.com.cn">gaojl</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-2-20
 */
@Entity
@Table(name = "gweb_model")
public class WebModel extends PersistentObject {

	private static final long serialVersionUID = 5705023547192837819L;

	/** 模块ID */
	private Integer identifier;

	/** 当前组的父级组的Id (为-1时，说明该模块不显示，只为和权限对应) */
	private Integer parentId;

	/** 模块名称 */
	private String modelName;

	/** 模块请求路径 */
	private String actionUrl;

	/**
	 * 当前组是否是叶子节点
	 * 
	 * 0：不是叶子 1：是叶子
	 * 
	 */
	private Integer isLeaf = new Integer(1);

	/** 模块图片路径 */
	private String imageUrl;

	/** 模块的权限类型 （0. 为非可选权限对应的模块 1，为可选权限对应的模块） */
	private Integer permissionType;

	/**
	 * 当点击的节点是根节点时，是否需要iframe重定向的标记 1重定向 0不重定向
	 */
	private Integer flag;

	/** 模块对应的权限 */
	// private Set<Permission> permissions;
	/**
	 * 获得模块的请求路径
	 * 
	 * @return 模块的请求路径
	 * 
	 */
	private int modelTypeAdd;

	private int modelSort;

	@Column(name = "model_sort", nullable = true)
	public int getModelSort() {
		return modelSort;
	}

	public void setModelSort(int modelSort) {
		this.modelSort = modelSort;
	}

	@Column(name = "model_action_url", length = 256, nullable = true)
	public String getActionUrl() {
		return actionUrl;
	}

	/**
	 * 设置模块的请求路径
	 * 
	 * @param actionUrl
	 *            模块的请求路径
	 */
	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	/**
	 * 获得模块的ID
	 * 
	 * @return 模块的ID
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "model_aiid")
	public Integer getIdentifier() {
		return identifier;
	}

	/**
	 * 设置模块的ID
	 * 
	 * @param identifier
	 *            模块的ID
	 */
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}

	/**
	 * 获得模块的图标路径
	 * 
	 * @return 模块的图标路径
	 * 
	 */
	@Column(name = "model_ico_url", length = 256, nullable = true)
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * 设置模块的图标路径
	 * 
	 * @param imageUrl
	 *            模块的图标路径
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * 获得模块的名称
	 * 
	 * @return 模块的名称
	 * 
	 */
	@Column(name = "model_name", length = 128, nullable = true)
	public String getModelName() {
		return modelName;
	}

	/**
	 * 设置模块的名称
	 * 
	 * @param name
	 *            模块的名称
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * 获得模块权限的Set集合
	 * 
	 * @return 模块权限的Set集合
	 * 
	 */
	// @ManyToMany(targetEntity = Permission.class,
	// // 防止删除子时删除父
	// cascade ={ CascadeType.PERSIST })
	// @JoinTable(name = "gweb_model_permission", joinColumns =
	// { @JoinColumn(name = "model_id") }, inverseJoinColumns =
	// { @JoinColumn(name = "permission_id") })
	// @OrderBy("identifier")
	// public Set<Permission> getPermissions() {
	// return permissions;
	// }
	// /**
	// * 设置模块权限的Set集合
	// *
	// * @param permissions
	// * 模块权限的Set集合
	// */
	// public void setPermissions(Set<Permission> permissions) {
	// this.permissions = permissions;
	// }
	/**
	 * 获得模块权限类型
	 * 
	 * @return 模块权限类型
	 * 
	 */
	@Column(name = "model_type", nullable = true)
	public Integer getPermissionType() {
		return permissionType;
	}

	/**
	 * 设置模块权限类型
	 * 
	 * @param permissionType
	 *            模块权限类型
	 */
	public void setPermissionType(Integer permissionType) {
		this.permissionType = permissionType;
	}

	/**
	 * 获得模块叶子标示
	 * 
	 * @return 模块叶子标示
	 * 
	 */
	@Column(name = "model_is_lea", nullable = true)
	public Integer getIsLeaf() {
		return isLeaf;
	}

	/**
	 * 设置模块叶子标示
	 * 
	 * @param isLeaf
	 *            模块叶子标示
	 */
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	/**
	 * 获得模块父节点id
	 * 
	 * @return 模块父节点id
	 * 
	 */
	@Column(name = "model_parent_id", nullable = true)
	public Integer getParentId() {
		return parentId;
	}

	/**
	 * 设置模块父节点id
	 * 
	 * @param parentId
	 *            模块父节点id
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public int compareTo(WebModel o) {
		int thisid = this.getIdentifier().intValue();
		int other = o.getIdentifier().intValue();
		return thisid < other ? -1 : (thisid == other ? 0 : 1);
	}

	@Transient
	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Column(name = "model_type_add", nullable = true)
	public int getModelTypeAdd() {
		return modelTypeAdd;
	}

	public void setModelTypeAdd(int modelTypeAdd) {
		this.modelTypeAdd = modelTypeAdd;
	}

}
