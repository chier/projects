<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="/commons/taglibs.jsp" %>
<%@ include file="/commons/meta.jsp" %>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>出错提示页面</title>
<link type="text/css" rel="stylesheet" href="${base}/css/style_error.css" />
<style type="text/css">
	.error_info{
		position:relative;
		top:130px;
		width:360px;
		height:300px;
		margin:0 auto;
	}
	.error_info form{
		margin:0;
		padding:0;
	}
	.error_info form dl{
		width:360px;
		height:180px;
		margin:0;
		padding:0;
		border:1px solid #d1d1d1;
	}
	.error_info form dl dt{
		position:relative;
		top:30px;
		*top:26px;
		_top:26px;
		float:right;
		text-align:left;
		width:220px;
		line-height:22px;
		color:#F00;
		font-weight:bold;
	}
	.error_info form dl dd{
		position:relative;
		top:-26px;
		*top:24px;
		_top:24px;
		clear:both;
		margin:0;
		padding:0;
		float:right;
		width:220px;
		line-height:22px;
		color:#0f406a;
	}
	.error_info form dl dd.has_img{
		position:relative;
		top:30px;
		float:left;
		width:120px;
		margin:0;
		padding:0;
		text-align:right;
	}
	.error_info form p{
		margin:20px auto;
		text-align:center;
	}
	.error_info form p input{
		border:none;
	}
	.error_info form p .re_back{
		border:none;
		width:65px;
		height:23px;
		color:#fff;
		text-align:center;
		cursor:pointer;
		background:url(${base}/images/errorimg/re_back_button.gif) top left no-repeat;
	}
	.error_info form p .show_manage_info{
		margin-left:10px;
		border:none;
		width:102px;
		height:23px;
		color:#fff;
		text-align:center;
		cursor:pointer;
		background:url(${base}/images/errorimg/show_manager_info.gif) top left no-repeat;
	}
	
</style>
</head>

<body>
<div class="error_info">
   		<dl>
   		  <dt><fmt:message key="efetionmanage.common.error.system_runtime_error"/></dt>
       	  <dd class="has_img"><img src="${base}/images/errorimg/wrong_img.gif" /></dd>
       	  <dd><fmt:message key="efetionmanage.common.error.reason"/></dd>
       	  <dd>1.<fmt:message key="efetionmanage.common.error.you_has_no_rights"/></dd>
       	  <dd>2.<fmt:message key="efetionmanage.common.error.please_login_in_and_linked_this_page"/></dd>
   	 	  </dl>
       		  

</div>
</body>
</html>