package com.cmcc.framework.business.impl.content;

import com.cmcc.framework.business.interfaces.content.IContentBodyManager;
import com.cmcc.framework.model.content.ContentBody;
import com.cmcc.framework.persistence.interfaces.content.IContentBodyDAO;

public class ContentBodyManagerImpl implements IContentBodyManager {
	protected IContentBodyDAO contentBodyDAO;

	public IContentBodyDAO getContentBodyDAO() {
		return contentBodyDAO;
	}

	public void setContentBodyDAO(IContentBodyDAO contentBodyDAO) {
		this.contentBodyDAO = contentBodyDAO;
	}

	public ContentBody get(Integer identifier) {
		ContentBody returnContentBody = (ContentBody) this.contentBodyDAO.getHibernate_reader_Interface("gweb", null).get(identifier);
		return returnContentBody;
	}

	public void saveOrUpdate(ContentBody body) {
		this.contentBodyDAO.getHibernate_Writer_Interface("gweb", null)
				.saveOrUpdate(body);
	}

}
