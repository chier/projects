package com.cmcc.framework.business.interfaces.login;

import java.util.List;

import com.cmcc.framework.controller.formbean.ZtreeVO;
import com.cmcc.framework.model.admin.WebAdmin;
import com.cmcc.framework.model.model.WebModel;

/**
 * 登录功能业务接口
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public interface ILoginManager {

	/**
	 * 根据帐号和密码返回
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	WebAdmin getByLoginId(String name);

	/**
	 * 根据用户来返回用户的权限
	 * 
	 * @param admin
	 * @return
	 */
	List<WebModel> findModelByAdmin(WebAdmin admin);
	

}
