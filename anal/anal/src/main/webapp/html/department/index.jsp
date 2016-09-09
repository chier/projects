<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp" %>
<!--部门入口页面-->
<html>
  <head>
    <title></title>
	<%@ include file="/commons/meta.jsp" %>
			<style type="text/css">
		html{border:0;}
		body{margin:0;padding:0;}
		span{
	font: 12px Arial,Helvetica,sans-serif;
	white-space: pre;
       }
		</style>
  </head>
<body>			 
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" class="main-iframe">
    	<tr>
         <td width="149" id="leftTab">
					<iframe name="left" id="left" src="${base}/manage/common/department/listDepartmentTree!initDepartment.${actionExt}" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" scrlling="auto">
					浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。	
					</iframe>
		  </td>

		 <td width="12" align="left"
					style="background: url(${base }/images/bg_switch2.gif) repeat-y;"
					valign="center">
					<IMG id="leftImg"
						SRC="${base}/images/backimg/menu_switch_close.gif" WIDTH=10
						HEIGHT=70 onClick="changeLeft();" style="cursor: hand;">

				</td>

		  <td id="contentTab" >
					<iframe name="right1" src="${base}/manage/common/department/listDepartments.${actionExt}" width="100%" height="100%" marginwidth="0" framespacing="0" marginheight="0" frameborder="0" scrolling="auto" hspace="0" vspace="0">
					浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。	
					</iframe>
	</td>
</tr>
</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
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
		leftTab.style.width="140";
		isShow=true;
		leftImg.src="${base}/images/backimg/menu_switch_close.gif";
		//alert("3");
	}
}
</SCRIPT>