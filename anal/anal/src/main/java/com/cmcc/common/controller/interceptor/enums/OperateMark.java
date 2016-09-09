package com.cmcc.common.controller.interceptor.enums;
/**
 * 操作日志枚举标识
 * @author guowz
 * @since 2009-10-9
 *
 */

public enum OperateMark {
	/*默认值*/
	OPERATE_DEFAULT("default"),
	
	//退出管理后台
	LOGIN_LOGOUT("login_logout"),
	LOGIN_LOGIN("login_login"),
	
	/*角色信息操作*/
	ROLE_ADD("role_add"),
	ROLE_DELETE("role_delete"),
	ROLE_UPDATE("role_update"),
	ROLE_SELECT("role_select"),
	ROLE_BATCH_DELETE("role_batch_delete"),
	ROLE_BATCH_ADD("role_batch_add"),
	
	/*内容信息操作*/
	CONTENT_ADD("content_add"),
	CONTENT_DELETE("content_delete"),
	CONTENT_UPDATE("content_update"),
	CONTENT_SELECT("content_select"),
	CONTENT_BATCH_DELETE("content_batch_delete"),
	CONTENT_BATCH_ADD("content_batch_add"),
	CONTENT_FILE_ADD("content_file_add"),
	CONTENT_FILE_DELETE("content_file_delete"),
	CONTENT_NEWS_ADD("content_news_add"),
	CONTENT_NEWS_DELETE("content_news_delete"),
	
	// 导出数据下载
	EXPORT_DATA_DOWNLOAD_ALL("export_data_download_all"),
	
	/*预警分析
	 * 
	 */
	WARNING_ADD("warning_add"),
	
	
	// 原始数据
	RAW_DATA_QUERY("raw_data_query"),
	
	//即席查询  导出数据
	QUERIES_EXPORT("queries_export"),
	
	// olap add 添加
	OLAP_ADD("olap_add"),
	//定制分析 浏览
	CUSTOM_QUERY("custom_query"),
	
	/*管理员信息操作*/
	ADMIN_ADD("admin_add"),
	ADMIN_DELETE("admin_delete"),
	ADMIN_UPDATE("admin_update"),
	ADMIN_SELECT("admin_select"),
	ADMIN_PWD_UPDATE("admin_pwd_update"),
	
	/*超级管理员信息操作*/
	SUPERADMIN_UPDATE("superAdmin_update")
	;
	
	
	private OperateMark(){}   
    private OperateMark(String operate){
    	this.operate = operate;
    }
    
    private String operate;
    

    public String toString(){
    	return this.operate;
    }
}
