<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<%@ include file="/commons/meta.jsp"%>
		<link href="${base }/js/tree/deptSetPerTree/xtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<script src="${base}/js/tree/deptSetPerTree/xtree.js"></script>
		<script src="${base}/js/tree/deptSetPerTree/xloadtree.js"></script>
		<script src="${base}/js/tree/deptSetPerTree/xmlextras.js"></script>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<script type="text/javascript">
		function closeParent() {
		$(parent.document.getElementById("lstSelected")).css("visibility","visible");
	    $(parent.document.getElementById("2stSelected")).css("visibility","visible");
	    $(parent.document.getElementById("employee.sex")).css("visibility","visible");
	    parent.$.ImpromptuClose();   
	     
	    
}
function add() {

	var allRole = $("#lstSelected option:selected");
	var hadRole = $("#2stSelected");
	if (allRole.length < 1) {
		return;
	} else {
		for (var i = 0; i < allRole.length; i++) {
			insert(hadRole.get(0), allRole.eq(i).text(), allRole.eq(i).val());
		}
	}
}

function quanyi() {

	var oDest = document.getElementById("2stSelected");
	thisRemoveAll(oDest);
}
function yichu() {
	var oDest = document.getElementById("2stSelected");
	thisRemoveSelected(oDest);
}

function selectall() {
	var allRole = $("#lstSelected option");
	var hadRole = $("#2stSelected");
	quanyi();
	if (allRole.length < 1) {
		return;
	} else {
		for (var i = 0; i < allRole.length; i++) {
			insert(hadRole.get(0), allRole.eq(i).text(), allRole.eq(i).val());
		}
	}
}

$(document).ready(function() {

            $("#2stSelected option:disabled").css('color', '#CCC');
			var flag = '${requestScope.flag}';
			if (flag != null && flag == '1') {
			    parent.document.location.reload();
			   // closeParent();
			}
			if (flag != null && flag == 'emp'){//修改员工页面使用本页添加角色时
			parent.initPerAndRole();
			closeParent();
			}
		});

function thisRemoveAll(oSelect) {

	for (i = oSelect.childNodes.length - 1; i >= 0; i--) {
		oNode = oSelect.childNodes[i];
		if (oNode.innerHTML != 'everyone') {
			oSelect.removeChild(oNode);
		}
	}
}
function thisRemoveSelected(oSelect) {

	for (i = oSelect.childNodes.length - 1; i >= 0; i--) {
		var node = oSelect.childNodes[i];
		if (node.selected && node.innerHTML != 'everyone') {
			oSelect.removeChild(node);
		}
	}
}
function subThis() {
	var hadRoles = $("#2stSelected option");
	for (var i = 0; i < hadRoles.length; i++) {
		hadRoles.eq(i).attr("selected", true);
	}
	$("#roleForm").submit();
}


	</script>
		<style type="text/css">
html,body {
	height: 100%;
	margin: 0;
	padding: 0;
	border: 0;
	overflow: hidden;
}

body,th,td {
	font-size: 13px;
	font-weight: normal;
	color: #0f406b;
}

.divs {
	PADDING-RIGHT: 10px;
	OVERFLOW-Y: auto;
	PADDING-LEFT: 10px;
	SCROLLBAR-FACE-COLOR: #ffffff;
	FONT-SIZE: 11pt;
	PADDING-BOTTOM: 0px;
	SCROLLBAR-HIGHLIGHT-COLOR: #ffffff;
	OVERFLOW: auto;
	WIDTH: 200px;
	SCROLLBAR-SHADOW-COLOR: #919192;
	COLOR: blue;
	SCROLLBAR-3DLIGHT-COLOR: #ffffff;
	LINE-HEIGHT: 100%;
	SCROLLBAR-ARROW-COLOR: #919192;
	PADDING-TOP: 0px;
	SCROLLBAR-TRACK-COLOR: #ffffff;
	FONT-FAMILY: 宋体;
	SCROLLBAR-DARKSHADOW-COLOR: #ffffff;
	LETTER-SPACING: 1pt;
	HEIGHT: 500px;
	TEXT-ALIGN: left;
	background-repeat: no-repeat;
}
</style>
	</head>
	<body style="text-align: center;">
	<div style="border:solid 1px #cad9f0;margin: 5px">
	<table width="380px" height="280px" cellpadding="2" cellspacing="3">
			<tr>
				<td width="140px" height="20px" align="center">
					<fmt:message key="efetionmanage.framework.role.manage.all.roles"></fmt:message>
				</td>
				<td width="100px" height="20px"></td>

				<td width="140px" height="20px" align="center">
					<fmt:message key="efetionmanage.framework.employee.edit.role.user.add" />
				</td>

			</tr>
			<tr>
				<td align="left" valign="top" height="260px">
					<select name="lstSelected" size="12" id="lstSelected"
						multiple="multiple"
						style="width: 150px; border: solid 1px #cad9f0; height: 250px; font-size: 12px; color: #0f406b">
						<c:if test="${not empty requestScope.allRoleList}">
							<c:forEach items="${requestScope.allRoleList}" var="allRoleList">
							<c:if test="${allRoleList.roleType!=0}">
								<option value="${allRoleList.roleId}">
									<c:out value="${allRoleList.roleName}" escapeXml="true"></c:out>
								</option>
								</c:if>
							</c:forEach>
						</c:if>
					</select>

				</td>
				<td valign="middle" height="260px">
					<p>
						<input type="image" src="${base}/images/nbot_add.gif"
							onClick="add();" />
					</p>
					</br>
					<p>
						<input type="image" src="${base}/images/nbot_del.gif"
							onClick="yichu();" />
					</p>
					</br>
					<p>
						<input type="image" src="${base}/images/nbot_addall.gif"
							onClick="selectall();" />
					</p>
					</br>
					<p>
						<input type="image" src="${base}/images/nbot_delall.gif"
							onClick="quanyi();" />
					</p>
				</td>
				<td height="260px" align="left" valign="top">
				<form action="${base}/manage/rolemanage/role/roleAction!addRoleForUser.${actionExt}" id="roleForm" name="roleForm" method="post">
					<select name="2stSelected" size="12" id="2stSelected"
						multiple="multiple"
						style="width: 150px; border: solid 1px #cad9f0; height: 250px; font-size: 12px; color: #0f406b">
						<c:if test="${not empty requestScope.hadRoleList}">
							<c:forEach items="${requestScope.hadRoleList}" var="hadRoleList">
								<c:choose>
									<c:when test="${hadRoleList.roleName=='everyone'}">
										<option value="${hadRoleList.roleId}" disabled="disabled"">
											<c:out value="${hadRoleList.roleName}" escapeXml="true"></c:out>
										</option>
									</c:when>
									<c:otherwise>
										<option value="${hadRoleList.roleId}">
											<c:out value="${hadRoleList.roleName}" escapeXml="true"></c:out>
										</option>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</select>
					<input id="userId" name="userId" value="${requestScope.userId}" type="hidden"/>
					<input id="fromType" name="fromType" value="${requestScope.fromType}" type="hidden"/>
					</form>
				</td>
			</tr>
		</table>
		</div>
		<table width="380px">
			<tr>
				<td align="center">
					<input name="submit4" type="button" class="bot4" value="确定"
						onclick="subThis()" />
					<input name="submit4" type="button" class="bot4" value="取消"
						onclick="closeParent()" />
				</td>
			</tr>
		</table>

	</body>
</html>
