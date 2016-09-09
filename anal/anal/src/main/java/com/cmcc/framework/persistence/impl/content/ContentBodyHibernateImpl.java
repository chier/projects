package com.cmcc.framework.persistence.impl.content;

import com.cmcc.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.framework.model.content.ContentBody;
import com.cmcc.framework.persistence.interfaces.content.IContentBodyDAO;

public class ContentBodyHibernateImpl<T extends ContentBody> extends
		GenericDAOImpl<ContentBody> implements IContentBodyDAO<ContentBody> {
}
