/*
 * 文件名： IContentAttachManager.java
 * 
 * 创建日期： 2008-6-5
 *
 * Copyright(C) 2008, by caowei.
 *
 * 原始作者: 曹巍
 */
package com.cmcc.framework.business.interfaces.content;

import java.io.Serializable;
import java.util.List;

import com.cmcc.framework.model.content.ContentAttach;
import com.cmcc.framework.model.content.ContentInfo;
import com.cmcc.framework.persistence.interfaces.content.IContentAttachDAO;

/**
 * 提供内容附件操作的商业方法的接口
 * 
 * @author <a href="mailto:xiaoyoa8195@163.com">caowei</a>
 * 
 * @since 2008-6-5
 */
public interface IContentAttachManager {
	/**
	 * 设置一个和持久层打交道的DAO对象
	 * 
	 * @param dao
	 *            要设置的DAO对象
	 */
	public void setContentAttachDAO(IContentAttachDAO dao);

	/**
	 * 根据ID获取内容附件对象
	 * 
	 * @param id
	 *            内容的ID
	 * 
	 * @return 获得的内容附件对象或null
	 */
	public ContentAttach get(Serializable id);
	
	/**
	 * 根据ids集合 返回附件类集合
	 * @param ids
	 * @return
	 */
	public List<ContentAttach> findByIds(String ids);

	/**
	 * 增加或修改附件信息
	 * 
	 * @param attach
	 */
	public void saveOrUpdate(ContentAttach attach);

	/**
	 * 对附件表进行修改  与内容表匹配上
	 *
	 */
	void updateContentAttach(ContentInfo contentInfo);
	/**
	 * 删除内容附件
	 * 
	 * @param attach
	 */
	public void remove(ContentAttach attach);

	/**
	 * 检测是否有重名的附件 重名 返回true else false
	 * 
	 * @param upFileName
	 * @return
	 */
	public boolean checkSameAttachName(String upFileName);

	public List<ContentAttach> getByContent(ContentInfo content);
}
