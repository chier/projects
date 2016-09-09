package com.cmcc.framework.business.interfaces.content;

import com.cmcc.framework.model.content.ContentBody;
import com.cmcc.framework.persistence.interfaces.content.IContentBodyDAO;


public interface IContentBodyManager {
	/**
	 * 设置一个和持久层打交道的DAO对象
	 * 
	 * @param dao
	 *            要设置的DAO对象
	 */
	public void setContentBodyDAO(IContentBodyDAO dao);
	/**
	 * 根据ID获取内容主体对象
	 * 
	 * @param identifier
	 *            ID
	 * @return 内容主体
	 */
	public ContentBody get(Integer identifier);
	/**
	 * 新增或修改内容主体
	 * 
	 * @param body
	 *            内容主体
	 */
	public void saveOrUpdate(ContentBody body);

}
