/* 
 * 文件名称：AttachMap.java
 * 
 * 创建时间：2009-3-3
 *
 * 原始作者：曹巍
 */
package com.cmcc.framework.controller.action.content;

import java.util.HashMap;
import java.util.Iterator;

import com.cmcc.framework.model.content.ContentAttach;

/**
 * 
 * 内容附件MAP集合类
 * 
 * @author <a href="mailto:xiaoyao8195@163.com">caowei</a>
 * 
 * @version $Revision: 1.1 $
 * 
 * @since 2009-3-3
 */
public class AttachMap {
	/**
	 * 私有内容附件map集合
	 */
	private HashMap<String, ContentAttach> attmap = new HashMap<String, ContentAttach>();

	/**
	 * 将内容附件集合放入map
	 * 
	 * @param attkey
	 *            附件KEY值
	 * @param attach
	 *            附件VO
	 */
	public void addAttach(String attkey, ContentAttach attach) {
		attmap.put(attkey, attach);
	}

	/**
	 * 根据key值删除附件VO对象
	 * 
	 * @param key
	 */
	public void removeAttach(String key) {
		attmap.remove(key);

	}

	/**
	 * 获取所有附件迭代
	 * 
	 * @return 所有附件迭代
	 */
	public Iterator<ContentAttach> getAll() {
		return attmap.values().iterator();
	}

	/**
	 * 删除附件集合
	 * 
	 */
	public void removeAll() {
		this.attmap.clear();
	}

	/**
	 * 获取附件个数
	 * 
	 * @return
	 */
	public int getSize() {
		return attmap.size();
	}

	/**
	 * 根据KEY获取附件VO
	 * 
	 * @param attachkey
	 *            附件KEY
	 * @return 附件VO
	 */
	public ContentAttach get(String attachkey) {
		return attmap.get(attachkey);
	}
}
