package com.cmcc.framework.business.interfaces.model;

import java.util.List;

import com.cmcc.framework.model.model.WebModel;

/**
 * webmodel 模块业务接口
 * @author <a href="mailto:chiersystem@gmail.com">张占亮</a>
 * @since 2012-6-7
 */
public interface IWebModelManager {
	
	final static int CustomModel = 3;
	final static int QueriesModel = 5;
	
	final static int LoapModel = 4;
	final static int RawDataModel = 5;
	
	final static int CenterModel = 6;
	
	final static int DateDownModel = 7;
	
	List<WebModel> findByAll();
}

