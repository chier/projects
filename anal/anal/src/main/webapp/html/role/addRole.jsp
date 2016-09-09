<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>
<%@ include file="/js/calendar/calendar2/calendar.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<style>
body {
    background-color: transparent;
	font-size: 12px;
}
</style>
		<script type="text/javascript">
        function clearError() {
	$("font[name='er']").hide();
	
	
	
}

function clearNameError() {

	$("font[id='roleNameNull']").hide();
	$("font[id='roleNameSame']").hide();

}

function clearDecError() {

	$("font[id='roleDecNull']").hide();

}

function closeParent() {
parent.$.ImpromptuClose();

}

function subForm() {
    
	if(checkRoleName()){
	var roleForm = document.getElementById("roleForm");
	roleForm.submit();
	}
	}
function checkRoleName() {

if ($("#roleName").val().replace(/[ \t]+/g, "") == "") {
        $("#roleNameNull").css("display", "block");
		$("#roleNameNoNull").css("display", "none");
	    return false;
	    }else{
	     var had = isHadName($("#roleName").val().replace(/[ \t]+/g, ""));
               if(had=='1'){
               $("#roleNameSame").css("display", "block");
		       $("#roleNameNoNull").css("display", "none");
	           return false;
	           }else{
	           $("#roleNameNoNull").css("display", "block");
	           return true;
	            }
	    }
 }
$("document").ready(function() {
	document.getElementById("roleName").select();
    var saveFlag = '${requestScope.saveFlag}';
    if (saveFlag != null && saveFlag == '1') {
    	parent.document.location.href = parent.document.location.href;
        //parent.parent.document.location.reload();
	}
});                  
 function isHadName(roleName){
    var date = new Date();
        var isHad = "";
	  $.ajax({
		type : "POST",
		async : false,
		url : "${base}/manage/rolemanage/role/roleAction!isHadSameNameRole.${actionExt}",
		data : "dates=" + date+"&roleName="+roleName,
		dataType : "text",
		success : function(msg) {
		      isHad = msg;
		      
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
            parent.document.location.href = "${base}/commons/error/error.jsp";
			//alert(textStatus);
        }
        });
        return isHad;
 }   
 
    function limtStr(){
   if($("#roleDec").val().length>256){
    $("#roleDec").val($("#roleDec").val().substring(0,256)); 
   }
   }                 
</script>
	</head>
	<body>
		<span class="ms4">添加角色</span>
		</br>
		<form
			action="${base}/manage/rolemanage/role/roleAction!saveRole.${actionExt}"
			name="roleForm" method="post" id="roleForm">
			<table width="420" align="center">
      <tr>
      <td width="100" align="right" style="margin-top: 2px">角色名称：<span style="color: #9d0c0c;">*</span></td>
      <td width="220" align="left" valign="top"><input type="text" id="roleName" name="roleName" class="f-text"
							value="${fn:escapeXml(requestScope.roleName)}" maxlength="60"
							size="33" style="width: 220px;" onkeydown="clearError()"
							onmousedown="clearError()"  onfocus="clearNameError()" /></td>
	  <td><!--<span style="color: #9d0c0c;">*</span> --></td>						
      <td width="100">  <font id="roleNameSame" name="roleNameSame"
							style="font-size: 12px; display: none; color: #9d0c0c;"><!--<img
								src="${base }/images/refuse.gif" /> -->名称已存在</font>
						<font id="roleNameNull" name="roleNameNull"
							style="font-size: 12px; display: none; color: #9d0c0c;"><!--<img
								src="${base }/images/refuse.gif" />-->不能为空</font>
						<font id="roleNameNoNull" name="roleNameNoNull"
							style="font-size: 12px; display: none; color: #9d0c0c;"><!--<img border="0"
								src="${base }/images/pass.gif"  /> --></font></td>
								
 </tr>
				<tr>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right" valign="top">
						角色描述：<span style="color: #9d0c0c;visibility: hidden">*</span></td>
			  <td align="left" valign="top">
						<textarea  rows="8" cols="30" id="roleDec" name="roleDec"
							style="width: 220px" onkeydown="clearError()" onkeyup="limtStr()"
							onmousedown="clearError()" onfocus="checkRoleName()">${fn:escapeXml(requestScope.roleDec)}</textarea>
					<div><font color="#9d0c0c" style="font-size: 12px">角色描述支持256个字符</font></div>
					</td>
					<!-- 
					<td align="left">
						<font color="red" id="roleDecNull" name="roleDecNull"
							style="font-size: 12px; display: none">不能为空</font>
					</td>-->
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="left" tyle="margin-top: 2px">
						<span class="prompt-msg"> 
                           <c:if test="${requestScope.errors=='1'}">
								<font color="#9d0c0c" name="er" style="font-size: 12px">角色名称不能为空</font>
							</c:if> <c:if test="${requestScope.errors=='2'}">
								<font name="er" color="#9d0c0c" style="font-size: 12px">角色名称长度不能大于60</font>
							</c:if> <c:if test="${requestScope.errors=='3'}">
								<font name="er" color="#9d0c0c" style="font-size: 12px">角色描述不能为空</font>
							</c:if> <c:if test="${requestScope.errors=='4'}">
								<font name="er" color="#9d0c0c" style="font-size: 12px">角色描述长度不能大于256</font>
							</c:if> <c:if test="${requestScope.errors=='5'}">
								<font name="er" color="#9d0c0c" style="font-size: 12px">角色名称已经存在</font>
							</c:if></span>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
			</table>
	</form>
		<table width="400">
			<tr>
				<td align="right">
					<input name="sub" type="button" class="bot4" value="确定"
						onclick="subForm();" />
				</td>
				<td align="left">
					<input name="cancel" type="button" class="bot4" value="取消"
						onclick="closeParent();" />
				</td>
			</tr>
		</table>
	</body>
</html>
