package com.cmcc.framework.business.impl.content;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.business.interfaces.content.IContentAttachManager;
import com.cmcc.framework.model.content.ContentAttach;
import com.cmcc.framework.model.content.ContentInfo;
import com.cmcc.framework.persistence.interfaces.content.IContentAttachDAO;

public class ContentAttachManagerImpl implements IContentAttachManager {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ContentAttachManagerImpl.class);

	protected IContentAttachDAO contentAttachDAO;

	public ContentAttach get(Serializable id) {
		if (logger.isDebugEnabled()) {
			logger.debug("get(Serializable id=" + id + ") - start");
		}

		ContentAttach returnContentAttach = (ContentAttach) contentAttachDAO
				.getHibernate_reader_Interface("gweb", null).get(id);

		if (logger.isInfoEnabled()) {
			logger.info("get(Serializable id=" + id + ") - end - return value="
					+ returnContentAttach);
		}
		return returnContentAttach;
	}

	public void setContentAttachDAO(IContentAttachDAO dao) {
		this.contentAttachDAO = dao;

	}

	public void remove(ContentAttach attach) {
		if (logger.isDebugEnabled()) {
			logger.debug("remove(ContentAttach attach=" + attach + ") - start");
		}

		this.contentAttachDAO.getHibernate_Writer_Interface("gweb", null)
				.remove(attach);

		if (logger.isInfoEnabled()) {
			logger.info("remove(ContentAttach attach=" + attach + ") - end");
		}
	}

	public void saveOrUpdate(ContentAttach attach) {
		if (logger.isDebugEnabled()) {
			logger.debug("saveOrUpdate(ContentAttach attach=" + attach
					+ ") - start");
		}

		this.contentAttachDAO.getHibernate_Writer_Interface("gweb", null)
				.saveOrUpdate(attach);

		if (logger.isInfoEnabled()) {
			logger.info("saveOrUpdate(ContentAttach attach=" + attach
					+ ") - end");
		}
	}

	public List<ContentAttach> getByContent(ContentInfo content) {
		if (logger.isDebugEnabled()) {
			logger.debug("getByContent(ContentInfo content=" + content
					+ ") - start");
		}

		List<ContentAttach> l = this.contentAttachDAO.getByContent(content
				.getIdentifier());

		if (logger.isInfoEnabled()) {
			logger.info("getByContent(ContentInfo content=" + content
					+ ") - end - return value=" + l.size());
		}
		return l;
	}

	public boolean checkSameAttachName(String upFileName) {
		if (logger.isDebugEnabled()) {
			logger.debug("checkSameAttachName(String upFileName=" + upFileName
					+ ") - start");
		}

		SearchCondition s = new SearchCondition();
		s.setField("attName");
		s.setFieldValue(upFileName);
		List<ContentAttach> l = this.contentAttachDAO
				.getHibernate_reader_Interface("gweb", null).findBy(s);
		if (l != null && l.size() > 0) {
			if (logger.isInfoEnabled()) {
				logger.info("checkSameAttachName(String upFileName="
						+ upFileName + ") - end - return value=" + true);
			}
			return true;
		}

		if (logger.isInfoEnabled()) {
			logger.info("checkSameAttachName(String upFileName=" + upFileName
					+ ") - end - return value=" + false);
		}
		return false;
	}

	public void updateContentAttach(ContentInfo contentInfo) {
		if (contentInfo == null) {
			logger.error("contentInfo is null");
			return;
		}
		if (contentInfo.getIdentifier() == null
				|| contentInfo.getIdentifier() == 0) {
			logger.error("contentInfo getIdentifier  is null or  is 0");
			return;
		}
		if (StringUtil.isBlank(contentInfo.getAttIds())) {
			logger.error("contentInfo getAttIds is null");
			return;
		}
		String sql = "update `gweb_content_attach` set `contentid` = ?  where `conattid` in ("
				+ contentInfo.getAttIds() + ")";
		this.contentAttachDAO.getHibernate_Anal().sqlUpdate(sql, contentInfo.getIdentifier());

	}

	public List<ContentAttach> findByIds(String ids) {
		
		String sql = " from ContentAttach as a where a.identifier in ("
			+ ids + ")";
		List<ContentAttach> l = this.contentAttachDAO.getHibernate_Anal().createQuery(sql);
		return l;
	}
}
