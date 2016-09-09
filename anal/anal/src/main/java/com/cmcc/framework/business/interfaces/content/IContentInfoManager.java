/*
 * 文件名： IContentInfoManager.java
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

import com.cmcc.common.controller.interceptor.enums.PermissionMark;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.controller.formbean.ContentType;
import com.cmcc.framework.model.content.ContentInfo;
import com.cmcc.framework.persistence.interfaces.content.IContentInfoDAO;

/**
 * 提供内容操作的商业方法的接口
 * 
 * @author <a href="mailto:xiaoyoa8195@163.com">caowei</a>
 * 
 * 
 * @since 2008-6-5
 */
public interface IContentInfoManager {

	public final static String[] permission = new String[] { "数据下载" };

	public final static Integer[] permissionIds = new Integer[] { 3 };

	/**
	 * 技术报告的值
	 */
	public final static int TechnicalReport = 2;

	/**
	 * 数据下载的值
	 */
	public final static int DataDownload = 3;

	/**
	 * 设置一个和持久层打交道的DAO对象
	 * 
	 * @param dao
	 *            要设置的DAO对象
	 */
	public void setContentInfoDAO(IContentInfoDAO dao);

	/**
	 * 设置基它东西
	 * 
	 * @param contentAttachManager
	 */
	public void setContentAttachManager(
			IContentAttachManager contentAttachManager);

	/**
	 * 根据ID获取内容对象
	 * 
	 * @param id
	 *            内容的ID
	 * 
	 * @return 获得的内容对象或null
	 */
	public ContentInfo get(Serializable id);

	/**
	 * 获取全部内容对象
	 * 
	 * @return 获取的全部内容对象的List,List的size可能为0
	 */
	public List<ContentInfo> getAll();

	/**
	 * 获取全部内容对象,并根据 page 返回当前页的List
	 * 
	 * @param page
	 *            分页信息对象
	 * 
	 * @return 符合条件的内容对象的List,List的size可能为0
	 */
	public List<ContentInfo> getAll(Page page);

	/**
	 * 获取内容的分页信息对象
	 * 
	 * @param pageSize
	 *            分页大小
	 * 
	 * @return 分页信息对象
	 */
	public Page getAll(int pageSize);

	/**
	 * 保存或更新内容对象
	 * 
	 * @param o
	 *            要保存或更新的内容对象
	 */
	public void saveOrUpdate(ContentInfo o);

	/**
	 * 删除内容对象
	 * 
	 * @param o
	 *            要删除的内容对象
	 */
	public void remove(ContentInfo o);

	/**
	 * 根据查询条件集合,分页对象查询内容信息
	 * 
	 * @param searchs
	 *            查询条件集合
	 * @param p
	 *            页面对象
	 * @return 内容信息
	 */
	public List<ContentInfo> getAllBySearchListPage(SearchCondition searchs,
			Page p);

	/**
	 * 根据页面大小,查询条件集合,获取分页信息
	 * 
	 * @param pagesize
	 *            页面大小
	 * @param searchs
	 *            查询条件集合
	 * @return 分页信息
	 */
	public Page getPageBySearchList(int pagesize, SearchCondition search);

	/**
	 * 
	 * @param eid
	 *            企业ID
	 * @param contype
	 *            栏目类型
	 * @param pagesize
	 *            页面大小
	 * @return 分页信息
	 */

	public Page getPageByEIDAndConType(int eid, int contype, int ctid,
			String strSearch, int pagesize);

	/**
	 * 
	 * @param pageinfo
	 *            页面信息
	 * @param contype
	 *            栏目类型
	 * @param eid
	 *            企业ID
	 * @return
	 */

	public List<ContentInfo> getAllByEIDAndContype(Page pageinfo, int contype,
			int crid, String strSearch, int eid);

	/**
	 * 返回内容所有类型
	 * 
	 * @return
	 */
	public List<ContentType> findAllContnetType();

	/**
	 * 返回内容除下载数据外所有类型
	 * 
	 * @return
	 */
	public List<ContentType> findContnetType();

	/**
	 * 根据权限内容返回类型
	 * 
	 * @param mark
	 * @return
	 */
	public List<ContentType> findContnetType(PermissionMark mark);

	/**
	 * 返回内容所有类型
	 * 
	 * @param 根据时间
	 *            获取显示的数量
	 * @return
	 */
	public List<ContentType> findContnetType(int topNumber);

	/**
	 * 返回内容所有类型
	 * 
	 * @param 根据时间
	 *            获取显示的数量
	 * @param type
	 *            获取type的值
	 * @param rid
	 *            角色id 如果为0 表示查看所有
	 * @return
	 */
	public ContentType findContnetType(int topNumber, int type, int rid);

	/**
	 * 返回内容所有类型
	 * 
	 * @param 根据时间
	 *            获取显示的数量
	 * @param 角色
	 *            id
	 * @return
	 */
	public List<ContentType> findContnetType(int topNumber, int rid);

	/**
	 * 根据ID 返回选择的类型
	 * 
	 * @param ctId
	 * @return
	 */
	public ContentType findConTypeById(int ctId);

	/**
	 * 根据类型 id 返回此类型下所有的值
	 * 
	 * @param ctId
	 *            ct Id
	 * @return
	 */
	public List<ContentInfo> findAllInfoByctId(int ctId);

	/**
	 * 根据id集合 返回info集合
	 * 
	 * @param ids
	 *            id 集合
	 * @return info 集合
	 */
	public List<ContentInfo> findInfoByIds(String ids);

	List<ContentInfo> findAllBy(SearchCondition searcher);

	/*
	 * public List<ContentCatalog> getCatalogList(Integer eid);
	 * 
	 * public Map<String, String> getCatalogMap(Integer eid);
	 * 
	 * public void initCatalog(Integer eid, Integer version);
	 * 
	 * public ContentCatalog getCatalog(Integer aiid, Integer eid);
	 * 
	 * public void updateCatalog(ContentCatalog catalog);
	 */

	/**
	 * 增加浏览的数量
	 * 
	 * @param id
	 * @return
	 */
	int updateAddViewNumber(int id);

	/**
	 * 增加附件下载的数量
	 * 
	 * @param id
	 * @return
	 */
	int updateAddAttachNumber(int id);

	/**
	 * 根据试点ID 返回试点下所有的数据管理信息
	 * 
	 * @param sid
	 *            试点ID
	 * @return 集合
	 */
	public List<ContentInfo> findContentInfoBySid(Integer sid);
}