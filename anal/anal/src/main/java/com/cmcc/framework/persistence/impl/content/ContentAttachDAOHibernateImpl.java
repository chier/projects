/*
 * 文件名： ContentAttachDAOHibernateImpl.java
 * 
 * 创建日期： 2008-6-5
 *
 * Copyright(C) 2008, by caowei.
 *
 * 原始作者: 曹巍
 */
package com.cmcc.framework.persistence.impl.content;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.List;

import com.cmcc.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.framework.model.content.ContentAttach;
import com.cmcc.framework.persistence.interfaces.content.IContentAttachDAO;

/**
 * 内容附件的DAO接口HIBERNATE实现类
 * 
 * @author <a href="mailto:xiaoyoa8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2008-6-5
 */
public class ContentAttachDAOHibernateImpl<T extends ContentAttach> extends
		GenericDAOImpl<ContentAttach> implements
		IContentAttachDAO<ContentAttach> {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ContentAttachDAOHibernateImpl.class);

	public List<ContentAttach> getByContent(Serializable contentId) {
		if (logger.isDebugEnabled()) {
			logger.debug("getByContent(Serializable contentId=" + contentId
					+ ") - start");
		}

		String hql = "from ContentAttach att where att.content.identifier=?";
		List<ContentAttach> resultSet = this.getHibernate_reader_Interface(
				"gweb", null).find(hql, contentId);

		if (logger.isInfoEnabled()) {
			logger.info("getByContent(Serializable contentId=" + contentId
					+ ") - end - return value=" + resultSet.size());
		}
		return resultSet;
	}

}
