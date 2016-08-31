package com.cmcc.anal.framework.persistence.interfaces;

import com.cmcc.anal.framework.model.admin.WebAdmin;

/**
 * 管理员操作接口
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 * @param <T>
 */
public interface IWebAdminDAO<T extends WebAdmin> extends IBaseDAO<WebAdmin> {

	/**
	 * 根据用户名返回用户信息
	 * 
	 * @param adminName
	 * @return
	 */
	WebAdmin findByAdminName(String adminName);

}
