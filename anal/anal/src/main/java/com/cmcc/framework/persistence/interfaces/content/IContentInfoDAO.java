/*
 * 文件名： IContentDAO.java
 * 
 * 创建日期： 2009-2-20
 *
 * Copyright(C) 2008, by caowei.
 *
 * 原始作者: 曹巍
 */
package com.cmcc.framework.persistence.interfaces.content;
import java.util.List;

import com.cmcc.common.persistence.generic.interfaces.IGenericDAO;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.framework.controller.formbean.ContentType;
import com.cmcc.framework.model.content.ContentInfo;

/**
 * 内容的DAO接口
 * 
 * @author <a href="mailto:xiaoyoa8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.2 $
 * 
 * @since 2009-2-20
 */
public interface IContentInfoDAO<T extends ContentInfo> extends IGenericDAO<T> {
	/**
	 * 根据查询条件，页面 分页查询内容信息
	 * 
	 * @param searcher
	 *            查询条件
	 * @param page
	 *            页面page
	 * @return 内容信息List
	 */
	public List<ContentInfo> findBy(SearchCondition searcher, Page page);
	/**
	 * 根据查询条件，页面大小 计算页面
	 * 
	 * @param searcher
	 *            查询条件
	 * @param pageSize
	 *            页面大小
	 * @return 页面对象
	 */
	public Page findBy(SearchCondition searcher, int pageSize);
	
	/**
	 * 删除内容对象
	 * 
	 * @param o
	 *            要删除的内容对象
	 */
	public void remove(ContentInfo o);
	
	/**
	 * 返回内容所有类型
	 * 
	 * @return
	 */
	public List<ContentType> findContnetType();
	
	

	/**
	 * 返回内容所有类型
	 * 
	 * @return
	 */
	public List<ContentType> findAllContnetType();
	/**
	 * 根据ID 返回选择的类型
	 * 
	 * @param ctId
	 * @return
	 */
	public ContentType findConTypeById(int ctId);
	
	/**
	 * 根据条件 查询所有信息
	 * @param searcher
	 * @return
	 */
	List<ContentInfo> findAllBy(SearchCondition searcher);
	/**
	 * 根据id 集合返回
	 * @param ids
	 * 		id集合
	 * @return
	 */
	List<ContentInfo> findInfoByIds(String ids);
	/**
	 * 增加浏览的数量
	 * @param id
	 * @return
	 */
	int updateAddViewNumber(int id);
	
	/**
	 * 增加附件下载的数量
	 * @param id
	 * @return
	 */
	int updateAddAttachNumber(int id);
	/*
	public List<ContentCatalog> getCatalogList(Integer eid);
	
	public void updateCatalog(ContentCatalog catalog);
	
	public ContentCatalog getCatalog(Integer aiid, Integer eid);
	*/
	
	/**
	 * 根据试点ID 返回试点下所有的数据管理信息
	 * 
	 * @param sid
	 *            试点ID
	 * @return 集合
	 */
	public List<ContentInfo> findContentInfoBySid(Integer sid);
}
