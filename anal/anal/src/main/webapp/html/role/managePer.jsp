<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>角色权限管理</title>
		<%@ include file="/commons/meta.jsp"%>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<style>
				.Per_sel_ul{padding:4px;}
			.Per_sel_ul li{width:100%; display:block; height:22px; cursor:pointer; *margin-bottom:-2px; text-overflow: ellipsis; -moz-text-overflow: ellipsis;
overflow:hidden; line-height:17px;}
			</style>
		<script type="text/javascript" src="${base}/js/main.js"></script>

		<script type="text/javascript">

   function setTextArea() {

     var roleScope2 = $("#roleScope2");
     var choosePer = $("#choosePer");
     var roleScopes = $("#roleScopes");
	if (roleScope2.attr("checked")) {
	   
		choosePer.css("cursor", "hand");
		choosePer.attr("disabled", false);
		choosePer.removeClass("bot3");
		choosePer.addClass("bot4");
		roleScopes.attr("disabled", false);
		$("#scopeWork").show();
	} else {
		$("#scopeWork").hide();
	    choosePer.css("cursor", "default");
	    choosePer.attr("disabled", true);
	    choosePer.removeClass("bot4");
	    choosePer.addClass("bot3");
	    roleScopes.attr("disabled", true);
	}
}

function deleteAllCheck() {
	var count = 0;
	if (document.getElementsByName("rolePer").length > 0) {
		var gids = document.getElementsByName("rolePer");
		for (var i = 0; i < gids.length; i++) {
			if (gids[i].checked == false) {
				$("#choose").attr("checked", false);
				break;
			}
			count++;
		}
		if (count == gids.length) {
			$("#choose").attr("checked", true);
		}
	}
}
function setAll() {

	var ids = document.getElementsByName("rolePer");
	if (document.getElementById("choose").checked == true) {
		checkall(ids);
	} else {
		checknull(ids);
	}
}
function closeParent() {

	parent.document.getElementById("roleScopes").value = "";
	parent.document.getElementById("userIds").value = "";
	parent.document.getElementById("pers").value = "";
	parent.$.ImpromptuClose();
}

$("document").ready(function() {
	
	var roleScopes = parent.document.getElementById("roleScopes").value;
	var userIds = parent.document.getElementById("userIds").value;

	var flag = '${requestScope.flag}';

	if (flag != null && flag != "") {
		if (flag == "1") {
			// parent.parent.document.location.reload();
			parent.document.location.href = parent.document.location.href;

		} else {
			closeParent();
		}
	}
	
	var roleScope = '${requestScope.thisRole.roleScope}';

	var pageScope = "roleScope" + roleScope;

	$("input[name='roleScope'][id='" + pageScope + "']").attr("checked", true);

	if ($("#roleScope2").attr("checked")) {
		$("#choosePer").attr("disabled", false);
		$("#roleScopes").attr("disabled", false);
	}

	var pers = '${requestScope.pers}';

	var perArry = pers.split(",");

	for (var i = 0; i < perArry.length; i++) {

		$("input[name='rolePer'][id='rolePer" + perArry[i] + "']").attr(
				"checked", true);

	}
	
	var pers = parent.document.getElementById("pers").value;
	
	
     if (pers != ""&&pers != "none") {

		    $("#rolePer1").attr("checked", false);
			$("#rolePer2").attr("checked", false);
			$("#rolePer3").attr("checked", false);

			var perArry = pers.split(",");

			for (var i = 0; i < perArry.length; i++) {

				$("input[name='rolePer'][id='rolePer" + perArry[i] + "']")
						.attr("checked", true);

			}
	}
	
	if(pers != ""&&pers == "none"){
	

			$("#choose").attr("checked", false);
			$("#rolePer1").attr("checked", false);
			$("#rolePer2").attr("checked", false);
			$("#rolePer3").attr("checked", false);
		}
		
		
		if(roleScopes!=""){
		 $("#roleScope2").attr("checked", true);
	     document.getElementById("roleScopes").innerHTML=roleScopes;
	     $("#userIds").val(userIds);
		}

	deleteAllCheck();

	if ($("#roleScope2").length == 0) {//改变页面大小
		$(window.parent.document).find("#managePerIframe").attr("height",
				"330px");
	}
    setTextArea();
});

function chooseRoleScope() {

	var pers = new Array();
	if ($("#rolePer1").attr("checked")) {
		pers.push("1");
	}
	if ($("#rolePer2").attr("checked")) {
		pers.push("2");
	}
	if ($("#rolePer3").attr("checked")) {
		pers.push("3");
	}
    if(!$("#rolePer1").attr("checked")&&!$("#rolePer1").attr("checked")&&!$("#rolePer1").attr("checked")){
    
       parent.document.getElementById("pers").value = "none";
    }else{
    
       parent.document.getElementById("pers").value = pers.join(",");
    
    }
    
	 //parent.document.getElementById("pers").value = pers.join(",");
	 
	// parent.$.ImpromptuClose($("#roleId").val(), "per");
	
	 parent.$.rolePerNeedClose($("#roleId").val(), "per");
	 
	 //parent.manageEmp($("#roleId").val(), "per");
	 
}


function subForm() {
	if ($("input[id='roleScope2']:checked").length > 0) {
		$("input").blur();
		if ($("#roleScopes").val() == "") {
			parent.$.prompt("角色权限范围不能为空", {
						buttons : {
							确定 : true
						},
						alertType : 'msg'
					});
			return
		}
	}
	$("#pcoreRole").submit();
}

</script>
		<style type="text/css">
.box {
	border: 0px;
	background: none;
}

body {
	background-color: transparent;
}
</style>
	</head>
	<body>
		<div class="box">
			<h3>
				<c:choose>
					<c:when test="${ fn:length(requestScope.roleName) > 24 }">
								为角色【<c:out value="${ fn:substring(requestScope.roleName,0,24)}"
							escapeXml="true"></c:out>...】进行角色权限管理
							</c:when>
					<c:otherwise>
								为角色【<c:out value="${requestScope.roleName}"></c:out>】进行角色权限管理
							</c:otherwise>
				</c:choose>

			</h3>
			<form name="pcoreRole" id="pcoreRole" method="post"
				action="${base}/manage/rolemanage/role/roleAction!saveRolePer.${actionExt}">
				<div class="box-padding">
					<div class="box-border">
						<strong class="title"><fmt:message
								key="efetionmanage.framework.role.manage.per.address.title" />
						</strong>
						<ul class="box-list">
							<li style="margin-left: 5px">
								<input type="radio" value="1" name="roleScope" id="roleScope0"
									onclick="setTextArea()" />
								<fmt:message
									key="efetionmanage.framework.role.manage.per.rolescope" />
							</li>
							<c:if test="${requestScope.roleName != 'everyone'}">
								<li style="margin-left: 5px">
									<input type="radio" value="2" name="roleScope" id="roleScope2"
										onclick="setTextArea()" />
									单独设置操作范围
								</li>
								<li>

									<div id="scopeWork" style="display:none;">
										<ul class="Per_sel_ul">
											<c:forEach var="work" items="${workList}">
												<li>
													<input type="checkbox" name="workId"
														value="${work.id}" id="workId" />
													${work.name}
												</li>
											</c:forEach>
										</ul>
									</div>
								</li>
							</c:if>
						</ul>
						<div class="clear"></div>
					</div>
				</div>
				<div class="box-bot">
					<input type="button" value="确定" class="bot4" onclick="subForm()" />
					<input type="button" value="取消" onclick="closeParent()"
						class="bot4" />
				</div>
		</div>
		<input type="hidden" id="roleId" name="roleId"
			value="${requestScope.thisRole.roleId}" />
		</form>
	</body>
</html>
