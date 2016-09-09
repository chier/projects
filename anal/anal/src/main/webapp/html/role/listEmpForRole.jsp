<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>

<!--部门列表页面页面-->
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow-x:hidden;overflow-y:auto;">
	<head>
		<title></title>
		<%@ include file="/commons/meta.jsp"%>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<script src="${base}/js/jquery.form.js" type="text/javascript"></script>
		<style type="text/css">
<!--
div.jqi .jqimessage {
	padding: 10px;
	line-height: 20px;
	color: #444444;
	background-image: url(${base}/images/windosw_bg.gif);
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-repeat: repeat-x;
}

div.jqi {
	width: 400px;
	font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
	position: absolute;
	background-color: #FFFFFF;
	font-size: 11px;
	text-align: left;
	border: none;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	padding: 0px;
	margin-top: -50px;
	background-image: url(${base}/images/windosw_bg.gif);
	background-repeat: repeat-x;
}

.spans {
	color: #007ED2;
	line-height: 22px;
	text-align: left;
}
-->
</style>

		<script language="Javascript" charset="UTF-8" type="text/javascript">
		function goPage(pageNum) {

	if (isInteger(pageNum)) {

		if ($("#userName").val() == '请输入<fmt:message key="efetionmanage.common.employee"/>姓名') {
			$("#userName").attr("value", "");
		}
		document.getElementById("pcoreRole").action = "${base}/manage/rolemanage/role/roleAction!findAllUserInRole.${actionExt}?page="
				+ pageNum;
		document.getElementById("pcoreRole").submit();
	} else {
		var msg = '请录入要跳转到第几页';
		$.prompt(msg, {
					buttons : {
						确定 : true
					},
					alertType : 'msg'
				});
	}
}

function search() {
	if ($("#userName").val() == '请输入<fmt:message key="efetionmanage.common.employee"/>姓名') {
		$("#userName").attr("value", "");
	}
	$("#pcoreRole")
			.attr("action",
					"${base}/manage/rolemanage/role/roleAction!findAllUserInRole.${actionExt}");
	$("#pcoreRole").submit();
}

$("document").ready(function() {
			var userName = '${requestScope.userName}';
			if (userName != null && userName != "") {
				$("#userName").val(userName);
			}
		});
function addText() {
	var searchText = document.getElementById("userName");
	if (searchText.value.replace(/[ \t]+/g, "") == "") {
		searchText.value = "请输入<fmt:message key="efetionmanage.common.employee"/>姓名";
		searchText.style.color = '#949494';
	}
}

function deleteText() {
	var searchText = document.getElementById("userName");
	if (searchText.value.replace(/[ \t]+/g, "") == ""
			|| searchText.value.replace(/[ \t]+/g, "") == "请输入<fmt:message key="efetionmanage.common.employee"/>姓名") {
		searchText.value = "";
		searchText.style.color = "black";
		$("#userName").focus();
		$("#userName").select();
	}
}
function addRole(userId) {

	$("a").blur();
	var roleId = $("#roleId").val();
	var v_lable = '<span class="ms4">为<fmt:message key="efetionmanage.common.employee"/>添加角色</span>';
	var iframe = v_lable
			+ '<iframe name="leftframe" frameBorder="0" width="410px" height="340px" style="display:block;margin:0;"   src="${base}/manage/rolemanage/role/roleAction!goAddRoleForUser.${actionExt}?userId='
			+ userId + '&roleId=' + roleId + '&t='
			+ <%=System.currentTimeMillis()%> + '"></iframe>';
	$.prompt(iframe, {
				buttons : {},
				loaded : function() {
					$(".jqi").css("width", "425px");
				}

			});

}
function setAll(ids) {

	if (document.getElementById("choose").checked == true) {
		checkall(ids);
	} else {
		checknull(ids);
	}
	//opButton();
}
function deleteAllCheck() {
	var count = 0;
	if (document.getElementsByName("userId").length > 0) {
		var gids = document.getElementsByName("userId");
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
	//opButton();
}
function deleteUserFromRole() {
	
	var userIds = $("input[name='userId']:checked");
	if (userIds.length < 1) {
		$.prompt("至少选择一个准备删除的<fmt:message key="efetionmanage.common.employee"/>", {
					buttons : {
						确定 : true
					},
					alertType : 'msg'
				});
		return;
	}

	$.prompt("此操作将导致<fmt:message key="efetionmanage.common.employee"/>不再享有该角色的权限,请再次确认!", {
				buttons : {
					是 : true,
					否 : false
				},alertType : 'ask',
				submit : function(v, m, k) {
					if (v) {

						$("#pcoreRole")
								.attr("action",
										"${base}/manage/rolemanage/role/roleAction!deleteUserFromRole.${actionExt}");
						$("#pcoreRole").submit();
						return true;

					} else {

						return true;
					}
				}
			});
}
function manageEmp(roleId, fromType) {
	var isHad = nowHad(roleId);

	if (isHad == '0') {

		$.prompt("此角色不存在，请刷新页面重新选择", {
					buttons : {
						确定 : true
					},
					alertType : 'msg'
				});

	} else {
		var span = ""
		var iframe = '<iframe scrolling="auto" id = "modelIframe" frameBorder="0" name="modelIframe" allowTransparency="true"  width="665px" height="360px" src="${base}/manage/rolemanage/role/roleAction!goManageEmp.${actionExt}?roleId='
				+ roleId
				+ '&roleName='
				+ isHad
				+ '"'
				+ ' style="display:block;margin:0 auto;border-style: none"></iframe>';

		if (fromType == 'per') {
			iframe = '<iframe scrolling="auto" id = "modelIframe" frameBorder="0" name="modelIframe" allowTransparency="true"  width="665px" height="360px" src="${base}/manage/rolemanage/role/roleAction!goChooseRoleScope.${actionExt}?roleId='
					+ roleId
					+ '&roleName='
					+ isHad
					+ '"'
					+ ' style="display:block;margin:0 auto;border-style: none"></iframe>';
		}

		$.prompt(iframe, {
					buttons : {},
					width : '665px',
					loaded : function() {
						$(".jqi").css("margin-top", "-65px");

					}
				});
	}
}
function nowHad(roleId) {

	var date = new Date();
	var isHad = "";
	$.ajax({
		type : "POST",
		async : false,
		url : "${base}/manage/rolemanage/role/roleAction!isHadRole.${actionExt}",
		data : "dates=" + date + "&roleId=" + roleId,
		dataType : "text",
		success : function(msg) {
			isHad = msg;

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		
             document.location.href = "${base}/commons/error/error.jsp";
		    //	alert(textStatus);
		}
	});
	return isHad;
}
function managePer(roleId) {

	var isHad = nowHad(roleId);

	if (isHad == '0') {

		$.prompt("此角色不存在，请刷新页面重新选择", {
					buttons : {
						确定 : true
					},
					alertType : 'msg'
				});

	} else {

		var managePerIframe = '<iframe id = "managePerIframe" frameBorder="0" name="managePerIframe" allowTransparency="true"  width="500px" height="460px" src="${base}/manage/rolemanage/role/roleAction!goManagePer.${actionExt}?roleId='
				+ roleId
				+ '&roleName='
				+ isHad
				+ '"'
				+ ' style="display:block;margin:0 auto;border-style: none"></iframe>';
		$.prompt(managePerIframe, {
					buttons : {},
					width : '540px'
				});

	}
}

function opButton() {
	var userIds = $("input[name='userId']:checked");
	if (userIds.length > 0) {
		$("#setPerButton").attr("disabled", true);
	} else {
		$("#setPerButton").attr("disabled", false);
	}
}
	</script>
	</head>

	<body>
	 <div id="main">
			<div class="title">
				<h4>
					角色&nbsp;<c:out value="${requestScope.roleName}" escapeXml="true"></c:out>
				</h4>
			</div>
			<form name="pcoreRole" id="pcoreRole" method="POST" action="${base}/manage/rolemanage/role/roleAction!findAllUserInRole.${actionExt}">
				<input type="hidden" value="${requestScope.roleId}" id="roleId"
					name="roleId" />
				<table width="100%" border="0" cellspacing="0" bgcolor="#E5E8ED"
					cellpadding="0">
					<tr>
						<td width="58%">
							<div>
								<span class="table-navdiv1"> <input type="button" id="addUserButton" name="addUserButton"
										value="添加<fmt:message key="efetionmanage.common.employee"/>" class="bot1other"
										onclick="manageEmp('${requestScope.roleId}')"> <input
										type="button" value="删除<fmt:message key="efetionmanage.common.employee"/>" class="bot1other"
										onclick="deleteUserFromRole(this)" /> <input type="button" id = "setPerButton" name = "setPerButton"
										value="为该角色设置权限" class="bot1other3" onclick="managePer('${requestScope.roleId}')" /> </span>
							</div>
						</td>
						<td width="42%">
							<div align="right">
								<input type="text" name="userName" size="12" value="请输入<fmt:message key="efetionmanage.common.employee"/>姓名"
									id="userName" onblur="addText()" onfocus="deleteText()"
									style="color: #949494;" />

								<input type="image" onclick="javascript:search();return false;"
									src="${base }/images/so.gif" />
							</div>
						</td>
					</tr>
				</table>
				<table cellpadding="2" cellspacing="1" border="0" class="tables">

					<thead>
						<tr style="text-align: center;">
							<td width="5%">
								<input type="checkbox" name="choose" id="choose"
									onclick="javascript:setAll(pcoreRole.userId)" />
							</td>

							<th width="20%">
								<fmt:message key="efetionmanage.common.employee"/>姓名
							</th>
							<th width="40%">
								所属部门
							</th>
							<th width="35%">
								分配角色
							</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${! empty requestScope.roleList}">
								<c:forEach items="${requestScope.roleList}" var="pcoreRole">
									<tr style="text-align: center;">
										<td>
											<input type="checkbox" name="userId"
												value="${pcoreRole.userId}" id="userId"
												onclick="deleteAllCheck()" />
										</td>
										<td align="left">
											<c:out value="${pcoreRole.userName}" escapeXml="true"></c:out>
										</td>

										<td align="left">
										
										  <c:choose>
												<c:when test="${ fn:length(pcoreRole.deptNames) > 30 }">
													<span title="${pcoreRole.deptNames}"><c:out
															value="${ fn:substring(pcoreRole.deptNames,0,30)}"
															escapeXml="true"></c:out>…</span>
												</c:when>
												<c:otherwise>
													<span title="${pcoreRole.deptNames}"><c:out
															value="${pcoreRole.deptNames}" escapeXml="true"></c:out>
													</span>
												</c:otherwise>
											</c:choose>
										</td>

										<td align="center" valign="middle" style="padding-top:3px">
										<a id="addRoleA" name="addRoleA" title="为<fmt:message key="efetionmanage.common.employee"/>添加角色"
														href="javascript:addRole('${pcoreRole.userId}')"><img id="addRoleI" name="addRoleI" src="${base}/images/addroleforuser.png"></img></a>
										<!-- 
                                           <ul class="actor-control2">
												<li class="modify">
													<a title="为用户添加角色"
														href="javascript:addRole('${pcoreRole.userId}')"><span>d</span>
													</a>
												</li>
											</ul> -->
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="20" align="center">
										暂无记录
									</td>
								</tr>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</form>
			<div class="pages"><%@ include file="/commons/page.jsp"%></div>
		</div>
		<input type="hidden" value="" id="roleScopes" name="roleScopes"/>
		<input type="hidden" value="" id="userIds" name="userIds"/>
		<input type="hidden" value="" id="pers" name="pers"/>
	</body>
</html>
