/*
 * 文件名： ContentInfoManagerImpl.java
 * 
 * 创建日期： 2008-6-5
 *
 * Copyright(C) 2008, by caowei.
 *
 * 原始作者: 曹巍
 */
package com.cmcc.framework.business.impl.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.business.interfaces.content.IContentAttachManager;
import com.cmcc.framework.business.interfaces.content.IContentInfoManager;
import com.cmcc.framework.business.interfaces.permission.IPermissionManager;
import com.cmcc.framework.controller.formbean.ContentType;
import com.cmcc.framework.model.content.ContentInfo;
import com.cmcc.framework.model.content.ContentSearchCondition;
import com.cmcc.framework.persistence.interfaces.content.IContentInfoDAO;

public class ContentInfoManagerImpl implements IContentInfoManager {
	protected IContentInfoDAO contentInfoDAO;

	private IContentAttachManager contentAttachManager;

	private IPermissionManager permissionManager;

	public IPermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(IPermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	public IContentAttachManager getContentAttachManager() {
		return contentAttachManager;
	}

	public void setContentAttachManager(
			IContentAttachManager contentAttachManager) {
		this.contentAttachManager = contentAttachManager;
	}

	public IContentInfoDAO getContentInfoDAO() {
		return contentInfoDAO;
	}

	public ContentInfo get(Serializable id) {
		ContentInfo returnContentInfo = (ContentInfo) contentInfoDAO
				.getHibernate_reader_Interface("gweb", null).get(id);
		return returnContentInfo;
	}

	public List<ContentInfo> getAll() {
		List<ContentInfo> returnList = contentInfoDAO
				.getHibernate_reader_Interface("gweb", null).getAll();
		return returnList;
	}

	public List<ContentInfo> getAll(Page page) {
		List<ContentInfo> returnList = contentInfoDAO
				.getHibernate_reader_Interface("gweb", null).getAll(page);
		return returnList;
	}

	public Page getAll(int pageSize) {
		Page returnPage = contentInfoDAO.getHibernate_reader_Interface("gweb",
				null).getAll(pageSize);
		return returnPage;
	}

	public void remove(ContentInfo o) {
		this.contentInfoDAO.remove(o);
	}

	public void saveOrUpdate(ContentInfo o) {
		contentInfoDAO.getHibernate_Writer_Interface("gweb", null)
				.saveOrUpdate(o);
		if (o.getIdentifier() != null && o.getIdentifier() != 0) {
			this.contentAttachManager.updateContentAttach(o);
		}
	}

	public void setContentInfoDAO(IContentInfoDAO ContentInfoDAO) {
		this.contentInfoDAO = ContentInfoDAO;
	}

	public List<ContentInfo> getAllBySearchListPage(SearchCondition search,
			Page p) {
		return this.contentInfoDAO.findBy(search, p);
	}

	public Page getPageBySearchList(int pagesize, SearchCondition searchs) {
		return this.contentInfoDAO.findBy(searchs, pagesize);
	}

	public List<ContentInfo> getAllByEIDAndContype(Page pageinfo, int contype,
			int crid, String search, int eid) {
		ContentSearchCondition searcher = new ContentSearchCondition();
		searcher.setEid(new Integer(eid));
		searcher.setContenttype(new Integer(contype));
		searcher.setCtId(crid);
		searcher.setState(new Integer(4));
		searcher.setStateSave(new Integer(0));
		if (!StringUtil.isBlank(search)) {
			searcher.setFieldValue(search);
		}
		return getAllBySearchListPage(searcher, pageinfo);
	}

	public Page getPageByEIDAndConType(int eid, int contype, int ctid,
			String search, int pagesize) {
		ContentSearchCondition searcher = new ContentSearchCondition();
		searcher.setEid(new Integer(eid));
		searcher.setContenttype(new Integer(contype));
		searcher.setCtId(ctid);
		searcher.setState(new Integer(4));
		searcher.setStateSave(new Integer(0));
		if (!StringUtil.isBlank(search)) {
			searcher.setFieldValue(search);
		}
		return getPageBySearchList(pagesize, searcher);
	}

	public ContentType findConTypeById(int ctId) {
		return this.contentInfoDAO.findConTypeById(ctId);
	}

	@SuppressWarnings("unchecked")
	public List<ContentType> findAllContnetType() {
		return this.contentInfoDAO.findAllContnetType();
	}

	@SuppressWarnings("unchecked")
	public List<ContentType> findContnetType() {
		return this.contentInfoDAO.findContnetType();
	}

	@SuppressWarnings("unchecked")
	public List<ContentType> findContnetType(PermissionMark mark) {
		List<ContentType> tl = new ArrayList<ContentType>();
		if (mark == PermissionMark.CenterTree_permission) {
			List<ContentType> typeList = this.findAllContnetType();
			for (int i = 0, s = typeList.size(); i < s; i++) {
				for (int j = 0; j < IContentInfoManager.permission.length; j++) {
					if (typeList.get(i).getCtName().equals(
							IContentInfoManager.permission[j])) {
						tl.add(typeList.get(i));
						break;
					}
				}
			}
			return tl;
		} else {
			return new ArrayList<ContentType>();
		}
	}

	public List<ContentType> findContnetType(int topNumber, int rid) {

		List<ContentType> typeList = this.contentInfoDAO.findContnetType();

		ContentType t = null;
		Page pageinfo = new Page(topNumber, topNumber);

		// 每个类型 提取某数量值
		for (int i = 0, s = typeList.size(); i < s; i++) {
			t = typeList.get(i);
			ContentSearchCondition searcher = new ContentSearchCondition();
			searcher.setState(new Integer(4));
			searcher.setStateSave(new Integer(0));
			searcher.setCtId(t.getCtId());
			for (int j = 0; j < IContentInfoManager.permission.length; j++) {
				if (t.getCtName().equals(IContentInfoManager.permission[j])) {
					String pcenter = this.permissionManager.findPcenterByRid(
							rid, PermissionMark.CenterTree_permission
									.getValue());
					searcher.setIds(pcenter);
				}
			}
			searcher.setContenttype(11);
			t.setInfoList(getAllBySearchListPage(searcher, pageinfo));
		}

		return typeList;
	}

	public List<ContentType> findContnetType(int topNumber) {
		List<ContentType> typeList = this.contentInfoDAO.findContnetType();

		ContentType t = null;
		Page pageinfo = new Page(topNumber, topNumber);

		// 每个类型 提取某数量值
		for (int i = 0, s = typeList.size(); i < s; i++) {
			t = typeList.get(i);
			ContentSearchCondition searcher = new ContentSearchCondition();
			searcher.setState(new Integer(4));
			searcher.setStateSave(new Integer(0));
			searcher.setCtId(t.getCtId());
			searcher.setContenttype(11);
			t.setInfoList(getAllBySearchListPage(searcher, pageinfo));
		}

		return typeList;
	}

	public ContentType findContnetType(int topNumber, int type, int rid) {

		ContentType t = this.contentInfoDAO.findConTypeById(type);
		Page pageinfo = new Page(topNumber, topNumber);
		// 每个类型 提取某数量值
		ContentSearchCondition searcher = new ContentSearchCondition();
		searcher.setState(new Integer(4));
		searcher.setStateSave(new Integer(0));
		searcher.setCtId(t.getCtId());
		searcher.setContenttype(11);
		if (rid != 0) {
			for (int j = 0; j < IContentInfoManager.permission.length; j++) {
				if (t.getCtName().equals(IContentInfoManager.permission[j])) {
					String pcenter = this.permissionManager.findPcenterByRid(
							rid, PermissionMark.CenterTree_permission
									.getValue());
					searcher.setIds(pcenter);
				}
			}
		}
		t.setInfoList(getAllBySearchListPage(searcher, pageinfo));

		return t;
	}

	public List<ContentInfo> findAllInfoByctId(int ctId) {
		ContentSearchCondition searcher = new ContentSearchCondition();
		searcher.setState(new Integer(4));
		searcher.setStateSave(new Integer(0));
		searcher.setCtId(ctId);
		searcher.setContenttype(11);
		return findAllBy(searcher);
	}

	public List<ContentInfo> findAllBy(SearchCondition searcher) {
		return this.contentInfoDAO.findAllBy(searcher);
	}

	public int updateAddAttachNumber(int id) {
		return this.contentInfoDAO.updateAddAttachNumber(id);
	}

	public int updateAddViewNumber(int id) {
		return this.contentInfoDAO.updateAddViewNumber(id);
	}

	public List<ContentInfo> findInfoByIds(String ids) {
		return this.contentInfoDAO.findInfoByIds(ids);
	}

	public List<ContentInfo> findContentInfoBySid(Integer sid) {
		return this.contentInfoDAO.findContentInfoBySid(sid);
	}
}
