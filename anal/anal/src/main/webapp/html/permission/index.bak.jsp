<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html>
	<head>
		<title>权限管理</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>

		<style>  
  body   {  
    -moz-user-select:none;  
  }  
  .footLink a,.footLink a:link,.footLink a:hover,.footLink a:visited,.footLink a:active{color:#64758F;}
  </style>

	</head>
	<body>
		<table cellpadding="0" cellspacing="0" border="0"
			style="height:100%;width:100%;" width="100%">

			<tr>
				<td width="285" id="leftTab">
					<iframe name="left" id="left" frameborder="0" scrolling="yes"
						width="100%" height="100%"
						src="${base}/manage/common/model/listModelTree!modelList.${actionExt}"></iframe>
				</td>
				<td id="contentTab">
					<br />
					<c:choose>
						<c:when test="${not empty modelAction}">
							<iframe name="adminRight" id="adminRight" scrolling="auto"
								frameborder="0" width="100%" height="100%"
								src="${base }${modelAction}"></iframe>
						</c:when>
						<c:otherwise>
							<iframe name="adminRight" id="adminRight" scrolling="auto"
								frameborder="0" width="100%" height="100%"
								src="${base }/html/adminSetting/admin_index.jsp"></iframe>
						</c:otherwise>
					</c:choose>
					<br />
				</td>
			</tr>
		</table>

	</body>
</html>

<SCRIPT LANGUAGE="JavaScript">
/*
var isShow=true;
function changeLeft(){
	var leftTab = document.getElementById("leftTab");
	var leftImg = document.getElementById("leftImg");
	if(isShow == true){
		leftTab.style.display = "none";
		isShow=false;
		leftImg.src="${base}/images/backimg/menu_switch_expand.gif";
	}else{
		leftTab.style.display = "";
		leftTab.style.width="185";
		isShow=true;
		leftImg.src="${base}/images/backimg/menu_switch_close.gif";
	}
}
*/
</SCRIPT>
