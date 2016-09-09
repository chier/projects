<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>
<%@ page import="com.cmcc.common.Global"%>
<%
      String[] turl=Global.turl;
      pageContext.setAttribute("turl",turl);
%>
<html>
  <head>
    <title>只有企业版才拥有此功能，如需使用该功能,请申请升级为企业版!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="${base}/css/main.css" rel="stylesheet" type="text/css" media="all" />
  </head>
<script>
	var localhref = window.location.href;
	var versionid = ${userInfo.version};
	if(window.location.href.length>base.length){
		//截取去掉当前路径中的contextpath
		localhref =window.location.href.substring(base.length)
	}
	if(localhref.indexOf("?")>0){
		//截取去掉当前路径中的参数
		localhref=localhref.substring(0,localhref.indexOf("?"));
	}
		//是否有权限访问
	var ky = false;
</script>
<!-- 迭代,判断session中的数组(体验版中有权限访问的链接)中是否存在当前的访问地址 -->
<c:forEach items="${turl}" var="turl" varStatus="status">
	  <script>
	  		if(null !=versionid && 1==versionid){
	  			var url="${turl}";
		  		if(localhref==url){
		  			ky=true;
		  		}
	  		}
	  </script>
</c:forEach>
<script>
	$(document).ready(function(){
		//判断是否是体验版无权限访问的模块
		if(null !=versionid && 1==versionid){
			if(!ky){
				$.prompt("只有企业版才拥有此功能，如需使用该功能,请申请升级为企业版!", {
			        buttons:{申请升级:true},
			        alertType:'msg',
			        closeable:false,
				    submit:function(v, m, f){
						if(v){
							window.location.href="${base}/manage/corpapply/corpapply_applyUI.portal";
						}else{
							return false;
						}
					}
				});
			}
		}
	});	
</script>
</html>
