/*
 * 文件名： ContentInfoDAOHibernateImpl.java
 * 
 * 创建日期： 2009-2-20
 *
 * Copyright(C) 2008, by caowei.
 *
 * 原始作者: 曹巍
 */
package com.cmcc.framework.persistence.impl.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.common.persistence.generic.impl.GenericDAOImpl;
import com.cmcc.common.persistence.support.Page;
import com.cmcc.common.persistence.support.SearchCondition;
import com.cmcc.common.util.StringUtil;
import com.cmcc.framework.business.interfaces.content.IContentInfoManager;
import com.cmcc.framework.controller.formbean.ContentType;
import com.cmcc.framework.model.content.ContentInfo;
import com.cmcc.framework.model.content.ContentSearchCondition;
import com.cmcc.framework.persistence.interfaces.content.IContentInfoDAO;

/**
 * 内容的DAO接口HIBERNATE实现类
 * 
 * @author <a href="mailto:xiaoyoa8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.4 $
 * 
 * @since 2009-2-20
 */
public class ContentInfoDAOHibernateImpl<CotnentInfo> extends
		GenericDAOImpl<ContentInfo> implements IContentInfoDAO<ContentInfo> {
	/**
	 * Logger for this class
	 */
	private static final Log logger = LogFactory
			.getLog(ContentInfoDAOHibernateImpl.class);

	@SuppressWarnings("unchecked")
	public List<ContentInfo> findBy(SearchCondition searcher, Page page) {
		if (logger.isDebugEnabled()) {
			logger.debug("findBy(SearchCondition searcher=" + searcher
					+ ", Page page=" + page + ") - start");
		}

		ContentSearchCondition search = (ContentSearchCondition) searcher;
		StringBuffer strSql = new StringBuffer();
		strSql.append("from ContentInfo c where 1=1 and c.state <> -1 ");
		List paramList = new ArrayList();
		if (search != null) {
			if (search.getField() != null && search.getFieldValue() != null
					&& !search.getFieldValue().equals("")) {
				strSql.append(" and c.");
				strSql.append(search.getField());
				strSql.append("=?");
				paramList.add(search.getFieldValue());
			}
			// if (search.getEid() != null) {
			// strSql.append(" and c.eid=?");
			// paramList.add(search.getEid());
			// }

			if (search.getCtId() != null && search.getCtId() != 0) {
				strSql.append(" and c.ctId=?");
				paramList.add(search.getCtId());
			}

			if (search.getIds() != null && !"".equals(search.getIds())) {
				strSql.append(" and c.identifier in (").append(search.getIds())
						.append(") ");
			}
			if (search.getFieldValue() != null) {
				strSql.append(" and c.title like '%"
						+ search.getFieldValue().toString() + "%' ");
			}

			/**
			 * majianwei 修改：判断SID的长度，一位即一级菜单，两位即二级菜单
			 * 
			 */
			String sid = String.valueOf(search.getContenttype());
			if (search.getContenttype() != null) {
				// strSql.append(" and c.contype=?");

				int pid = 0;
				if (sid.length() > 1) {
					String tid = sid.substring(0, 1);
					pid = Integer.parseInt(tid);
					search.setContenttype(pid);
				}

				// paramList.add(search.getContenttype());
			}

			/**
			 * majianwei 修改说明： search.getContenttype()==11公司新闻-已发布，
			 * search.getContenttype()==12公司新闻-未发布
			 * search.getContenttype()==21通知通告-已发布，
			 * search.getContenttype()==22通知通告-未发布
			 */
			if (sid.length() > 1) {
				strSql.append(" and  c.state=? ");
				if ("11".equals(sid) || "21".equals(sid)) {
					// 状态位：已发布
					paramList.add(4);
				} else {
					// 状态位：未发布
					paramList.add(0);
				}
			} else {
				// 查询出所有信息(已发布和未发布的)
				if (search.getState() != null && search.getStateSave() != null) {
					strSql.append(" and (c.state=? or c.state=?) ");
					paramList.add(search.getState());
					paramList.add(search.getStateSave());
				}
			}
		}
		strSql.append(" order by c.isTop desc,c.releasedate desc");
		if (page.getOrderBy() != null) {
			strSql.append(",");
			strSql.append(page.getOrderBy());
			strSql.append(" ");
			if (page.getOrder() != null
					&& !page.getOrder().equalsIgnoreCase("")) {
				strSql.append(page.getOrder());
			} else {
				strSql.append("asc");
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("findBy(SearchCondition searcher=" + searcher
					+ ", Page page=" + page + ") - strHql=" + strSql + ")");
		}
		List<ContentInfo> resultList = this.getHibernate_reader_Interface(
				"gweb", null).findByHQLandPage(strSql.toString(), page,
				paramList.toArray());
		strSql.delete(0, strSql.length());
		strSql = null;
		if (logger.isInfoEnabled()) {
			logger.info("findBy(SearchCondition searcher=" + searcher
					+ ", Page page=" + page + ") - end - return value="
					+ resultList);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public List<ContentInfo> findAllBy(SearchCondition searcher) {
		if (logger.isDebugEnabled()) {
			logger.debug("findBy(SearchCondition searcher=" + searcher
					+ ",) - start");
		}

		ContentSearchCondition search = (ContentSearchCondition) searcher;
		StringBuffer strSql = new StringBuffer();
		strSql.append("from ContentInfo c where 1=1 and c.state <> -1 ");
		List paramList = new ArrayList();
		if (search != null) {
			if (search.getField() != null && search.getFieldValue() != null
					&& !search.getFieldValue().equals("")) {
				strSql.append(" and c.");
				strSql.append(search.getField());
				strSql.append("=?");
				paramList.add(search.getFieldValue());
			}
			if (search.getEid() != null) {
				strSql.append(" and c.eid=?");
				paramList.add(search.getEid());
			}

			if (search.getCtId() != null && search.getCtId() != 0) {
				strSql.append(" and c.ctId=?");
				paramList.add(search.getCtId());
			}
			/**
			 * majianwei 修改：判断SID的长度，一位即一级菜单，两位即二级菜单
			 * 
			 */
			String sid = String.valueOf(search.getContenttype());
			if (search.getContenttype() != null) {
				// strSql.append(" and c.contype=?");

				int pid = 0;
				if (sid.length() > 1) {
					String tid = sid.substring(0, 1);
					pid = Integer.parseInt(tid);
					search.setContenttype(pid);
				}

				// paramList.add(search.getContenttype());
			}

			/**
			 * majianwei 修改说明： search.getContenttype()==11公司新闻-已发布，
			 * search.getContenttype()==12公司新闻-未发布
			 * search.getContenttype()==21通知通告-已发布，
			 * search.getContenttype()==22通知通告-未发布
			 */
			if (sid.length() > 1) {
				strSql.append(" and  c.state=? ");
				if ("11".equals(sid) || "21".equals(sid)) {
					// 状态位：已发布
					paramList.add(4);
				} else {
					// 状态位：未发布
					paramList.add(0);
				}
			} else {
				// 查询出所有信息(已发布和未发布的)
				if (search.getState() != null && search.getStateSave() != null) {
					strSql.append(" and (c.state=? or c.state=?) ");
					paramList.add(search.getState());
					paramList.add(search.getStateSave());
				}
			}
		}
		strSql.append(" order by c.isTop desc,c.releasedate desc");
		List<ContentInfo> resultList = this.getHibernate_reader_Interface(
				"gweb", null).createQuery(strSql.toString(),
				paramList.toArray());
		strSql.delete(0, strSql.length());
		strSql = null;
		if (logger.isInfoEnabled()) {
			logger.info("findBy(SearchCondition searcher=" + searcher
					+ ", ) - end - return value=" + resultList);
		}
		return resultList;
	}

	@SuppressWarnings("unchecked")
	public Page findBy(SearchCondition searcher, int pageSize) {
		if (logger.isDebugEnabled()) {
			logger.debug("findBy(SearchCondition searcher=" + searcher
					+ ", int pageSize=" + pageSize + ") - start");
		}

		ContentSearchCondition search = (ContentSearchCondition) searcher;
		StringBuffer strSql = new StringBuffer();
		strSql
				.append("select count(c) from ContentInfo c where 1=1 and c.state <> -1 ");
		List paramList = new ArrayList();
		if (search != null) {
			if (search.getField() != null && search.getFieldValue() != null
					&& !search.getFieldValue().equals("")) {
				strSql.append(" and c.");
				strSql.append(search.getField());
				strSql.append("=?");
				paramList.add(search.getFieldValue());
			}
			if (search.getEid() != null) {
				strSql.append(" and c.eid=?");
				paramList.add(search.getEid());
			}
			/**
			 * majianwei 修改：判断SID的长度，一位即一级菜单，两位即二级菜单
			 * 
			 */
			String sid = String.valueOf(search.getContenttype());
			if (search.getContenttype() != null) {
				// strSql.append(" and c.contype=?");

				int pid = 0;
				if (sid.length() > 1) {
					String tid = sid.substring(0, 1);
					pid = Integer.parseInt(tid);
					search.setContenttype(pid);
				}

				// paramList.add(search.getContenttype());
			}

			if (search.getCtId() != null && search.getCtId() != 0) {
				strSql.append(" and c.ctId=?");
				paramList.add(search.getCtId());
			}

			if (!StringUtil.isBlank(search.getIds())) {
				strSql.append(" and c.identifier in (").append(search.getIds())
						.append(") ");
			}

			if (search.getFieldValue() != null) {
				strSql.append(" and c.title like '%"
						+ search.getFieldValue().toString() + "%' ");
			}

			/**
			 * majianwei 修改说明： search.getContenttype()==11公司新闻-已发布，
			 * search.getContenttype()==12公司新闻-未发布
			 * search.getContenttype()==21通知通告-已发布，
			 * search.getContenttype()==22通知通告-未发布
			 */
			if (sid.length() > 1) {
				strSql.append(" and  c.state=? ");
				if ("11".equals(sid) || "21".equals(sid)) {
					// 状态位：已发布
					paramList.add(4);
				} else {
					// 状态位：未发布
					paramList.add(0);
				}
			} else {
				// 查询出所有信息(已发布和未发布的)
				if (search.getState() != null && search.getStateSave() != null) {
					strSql.append(" and (c.state=? or c.state=?) ");
					paramList.add(search.getState());
					paramList.add(search.getStateSave());
				}
			}
		}
		List resultList = this.getHibernate_reader_Interface("gweb", null)
				.createQuery(strSql.toString(), paramList.toArray());

		long totalCount = (Long) resultList.get(0);

		// 返回分页对象
		if (totalCount < 1) {
			totalCount = 0;
		}

		Page returnPage = new Page(totalCount, pageSize);
		strSql.delete(0, strSql.length());
		strSql = null;

		if (logger.isInfoEnabled()) {
			logger.info("findBy(SearchCondition searcher=" + searcher
					+ ", int pageSize=" + pageSize + ") - end - return value="
					+ returnPage);
		}
		return returnPage;
	}

	/**
	 * 逻辑删除状态 为 -1
	 */
	public void remove(ContentInfo o) {
		/*
		o.setState(-1);
		this.getHibernate_Writer_Interface("gweb", null).saveOrUpdate(o);
		*/
		this.getHibernate_Writer_Interface("gweb", null).remove(o);
	}

	/*
	 * public List<ContentCatalog> getCatalogList(Integer eid) { String hql =
	 * "from ContentCatalog where eid=? order by identifier asc"; List<ContentCatalog>
	 * resultList = this.getHibernate_reader_Interface( "gweb", null).find(hql,
	 * eid); return resultList; }
	 * 
	 * public void updateCatalog(ContentCatalog catalog) {
	 * this.getHibernate_Writer_Interface("gweb", null).saveOrUpdate(catalog); }
	 * 
	 * public ContentCatalog getCatalog(Integer aiid, Integer eid) { String hql =
	 * "from ContentCatalog where identifier=? and eid=?"; List<ContentCatalog>
	 * catalogList = this.getHibernate_reader_Interface( "gweb", null).find(hql,
	 * aiid, eid); ContentCatalog catalog = null; if (catalogList != null &&
	 * catalogList.size() > 0) { catalog = catalogList.get(0); } return catalog; }
	 */

	@SuppressWarnings("unchecked")
	public ContentType findConTypeById(int ctId) {
		ContentType ct = new ContentType();
		String hql = "SELECT ctId,ctName,ctPublic,ctPublicAttach FROM gweb_content_type where ctId = ?";
		List<Object[]> ol = this.getHibernate_reader_Interface("gweb", null)
				.createSQLQuery(hql, ctId);
		if (ol == null || ol.size() == 0) {
			return ct;
		}

		Object[] o = null;
		for (int i = 0; i < ol.size(); i++) {
			o = ol.get(i);
			ct.setCtId(Integer.valueOf(String.valueOf(o[0])));
			ct.setCtName(String.valueOf(o[1]));
			ct.setCtPbulic(Integer.valueOf(String.valueOf(o[2])));
			ct.setCtPublicAttach(Integer.valueOf(String.valueOf(o[3])));
		}
		return ct;
	}

	@SuppressWarnings("unchecked")
	public List<ContentType> findContnetType() {
		List<ContentType> l = new ArrayList<ContentType>();
		String hql = "SELECT ctId,ctName,ctPublic,ctPublicAttach FROM gweb_content_type order by ctId asc";
		List<Object[]> ol = this.getHibernate_reader_Interface("gweb", null)
				.createSQLQuery(hql);
		if (ol == null || ol.size() == 0) {
			return l;
		}
		ContentType ct = null;
		Object[] o = null;
		for (int i = 0; i < ol.size(); i++) {
			o = ol.get(i);
			ct = new ContentType();
			ct.setCtId(Integer.valueOf(String.valueOf(o[0])));
			ct.setCtName(String.valueOf(o[1]));
			ct.setCtPbulic(Integer.valueOf(String.valueOf(o[2])));
			ct.setCtPublicAttach(Integer.valueOf(String.valueOf(o[3])));

			if (ct.getCtId() != IContentInfoManager.DataDownload) {
				l.add(ct);
			}
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<ContentType> findAllContnetType() {
		List<ContentType> l = new ArrayList<ContentType>();
		String hql = "SELECT ctId,ctName,ctPublic,ctPublicAttach FROM gweb_content_type order by ctId asc";
		List<Object[]> ol = this.getHibernate_reader_Interface("gweb", null)
				.createSQLQuery(hql);
		if (ol == null || ol.size() == 0) {
			return l;
		}
		ContentType ct = null;
		Object[] o = null;
		for (int i = 0; i < ol.size(); i++) {
			o = ol.get(i);
			ct = new ContentType();
			ct.setCtId(Integer.valueOf(String.valueOf(o[0])));
			ct.setCtName(String.valueOf(o[1]));
			ct.setCtPbulic(Integer.valueOf(String.valueOf(o[2])));
			ct.setCtPublicAttach(Integer.valueOf(String.valueOf(o[3])));
			l.add(ct);
		}
		return l;
	}

	public int updateAddAttachNumber(int id) {
		String sql = "UPDATE ContentInfo SET attachNumber=attachNumber+1 WHERE (identifier=?)  ";
		return this.getHibernate_Anal().hqlUpdate(sql, id);
	}

	public int updateAddViewNumber(int id) {
		String sql = "UPDATE ContentInfo SET viewNumber= (viewNumber+1) WHERE (identifier=?)  ";
		return this.getHibernate_Anal().hqlUpdate(sql, id);// .sqlUpdate(sql,
															// id);
	}

	public List<ContentInfo> findInfoByIds(String ids) {
		String hql = "from ContentInfo where identifier in (" + ids + ")";
		return this.getHibernate_Anal().createQuery(hql);// .sqlUpdate(sql,
															// id);
	}

	public List<ContentInfo> findContentInfoBySid(Integer sid) {
		String hql = "from ContentInfo c where c.sid = ?";
		List<ContentInfo> list = this.getHibernate_Anal().createQuery(hql,sid);
		return list;
	}
}
