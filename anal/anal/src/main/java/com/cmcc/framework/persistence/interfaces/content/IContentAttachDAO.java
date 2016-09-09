/*
 * 文件名： IContentAttachDAO.java
 * 
 * 创建日期： 2008-6-5
 *
 * Copyright(C) 2008, by caowei.
 *
 * 原始作者: 曹巍
 */
package com.cmcc.framework.persistence.interfaces.content;

import java.io.Serializable;
import java.util.List;

import com.cmcc.common.persistence.generic.interfaces.IGenericDAO;
import com.cmcc.framework.model.content.ContentAttach;

/**
 * 内容附件的DAO接口
 * 
 * @author <a href="mailto:xiaoyoa8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-6-5
 */
public interface IContentAttachDAO<T extends ContentAttach> extends
		IGenericDAO<T> {
	/**
	 * 根据内容ID获取附件列表
	 * 
	 * @param contentId
	 *            内容ID
	 * @return 附件列表
	 */

	public List<ContentAttach> getByContent(Serializable contentId);

}
