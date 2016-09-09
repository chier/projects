package com.cmcc.framework.business.impl.model;

import java.util.List;

import com.cmcc.framework.business.interfaces.model.IWebModelManager;
import com.cmcc.framework.model.model.WebModel;
import com.cmcc.framework.persistence.interfaces.model.IWebModelDAO;

/**
 * webmodel 业务实现类
 * 
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public class WebModelManagerImpl implements IWebModelManager {

	private IWebModelDAO webModelDAO;

	public IWebModelDAO getWebModelDAO() {
		return webModelDAO;
	}

	public void setWebModelDAO(IWebModelDAO webModelDAO) {
		this.webModelDAO = webModelDAO;
	}

	public List<WebModel> findByAll() {
		return webModelDAO.findByAll();
	}

}
