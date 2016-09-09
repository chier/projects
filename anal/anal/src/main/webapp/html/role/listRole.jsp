<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>

<!--部门列表页面页面-->
<html xmlns="http://www.w3.org/1999/xhtml"
	style="overflow-x:hidden;overflow-y:auto;">
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
	width: 450px;
	font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
	position: absolute;
	background-color: #FFFFFF;
	font-size: 11px;
	text-align: left;
	border: none;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	padding: 0px;
	margin-top: -55px;
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

		if ($("#roleName").val() == '请输入角色名称') {
			$("#roleName").attr("value", "");
		}
		document.getElementById("pcoreRole").action = "${base}/manage/rolemanage/role/roleAction!findAllRole.${actionExt}?page="
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

function addText() {
	var searchText = document.getElementById("roleName");
	if (searchText.value.replace(/[ \t]+/g, "") == "") {
		searchText.value = "请输入角色名称";
		searchText.style.color = '#949494';
	}
}

function deleteText() {
	var searchText = document.getElementById("roleName");
	if (searchText.value.replace(/[ \t]+/g, "") == ""
			|| searchText.value.replace(/[ \t]+/g, "") == "请输入角色名称") {
		searchText.value = "";
		searchText.style.color = "black";
		$("#roleName").focus();
		$("#roleName").select();
	}
}

function addRole() {
	$("input").blur();
	var iframe = '<iframe id = "modelIframe" frameBorder="0" name="modelIframe" allowTransparency="true"  width="430px" height="280px" src="${base}/html/role/addRole.jsp'
			+ '"'
			+ ' style="display:block;margin:0 auto;border-style: none"></iframe>';

	$.prompt(iframe, { buttons : {}});
}
function updateRole(roleId) {
	$("a").blur();
	var isHad = nowHad(roleId);

	if (isHad == '0') {
		$.prompt("此角色不存在，请刷新页面重新选择", {
					buttons : {
						确定 : true
					},
					alertType : 'msg'
				});
	} else {
		var iframe = '<iframe id = "modelIframe" frameBorder="0" name="modelIframe" allowTransparency="true"  width="430" height="280px" src="${base}/manage/rolemanage/role/roleAction!toUpdateRole.${actionExt}?roleId='
				+ roleId
				+ '"'
				+ ' style="display:block;margin:0 auto;border-style: none"></iframe>';
		$.prompt(iframe, {
					buttons : {}
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
			//alert(textStatus);
		}
	});
	return isHad;
}

function search() {
	if ($("#roleName").val() == '请输入角色名称') {
		$("#roleName").attr("value", "");
	}
	$("#pcoreRole").submit();
}

$("document").ready(function() {
			var roleName = '${requestScope.roleName}';
			if (roleName != null && roleName != "") {
				$("#roleName").val(roleName);
			}
		});
function setAll(ids) {

	if (document.getElementById("choose").checked == true) {
		checkall(ids);
	} else {
		checknull(ids);
	}
}
function deleteAllCheck() {
	var count = 0;
	if (document.getElementsByName("roleId").length > 0) {
		var gids = document.getElementsByName("roleId");
		for (var i = 0; i < gids.length; i++) {

			if (gids[i].checked == false) {

				$("#choose").attr("checked", false);
				break;
			}
			count++;
		}
		var checkedLength = $("input[name='roleId']");
		var disabledLength = $("input[name='roleId'][disabled=true]");
		if (disabledLength.length > 0) {

			if (count == checkedLength.length - 1) {
				$("#choose").attr("checked", true);
			}
		} else {

			if (count == checkedLength.length) {
				$("#choose").attr("checked", true);
			}
		}

	}
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
		
		var iframe = '<iframe scrolling="auto" id = "modelIframe" frameBorder="0" name="modelIframe" allowTransparency="true"  width="740px" height="360px" src="${base}/manage/rolemanage/role/roleAction!goManageEmp.${actionExt}?roleId='
				+ roleId
				+ '&roleName='
				+ isHad
				+ '"'
				+ ' style="display:block;margin:0 auto;border-style: none"></iframe>';

		if (fromType == 'per') {
			iframe = '<iframe scrolling="auto" id = "modelIframe" frameBorder="0" name="modelIframe" allowTransparency="true"  width="740px" height="360px" src="${base}/manage/rolemanage/role/roleAction!goChooseRoleScope.${actionExt}?roleId='
					+ roleId
					+ '&roleName='
					+ isHad
					+ '"'
					+ ' style="display:block;margin:0 auto;border-style: none"></iframe>';
		}
		$.prompt(iframe, {
					buttons:{},
					width:'740px'
				});
	}
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
function deleteRole(obj) {
	var roleIds = $("input[name='roleId']:checked");
	if (roleIds.length < 1) {
		$.prompt("请至少选择一个角色完成此操作", {
					buttons : {
						确定 : true
					},
					alertType : 'msg'
				});
		return;
	}
	var msg = "";
	msg = "是否确认删除该角色？";
	$.prompt(msg, {
		buttons : {
			是 : true,
			否 : false
		},
		alertType : 'ask',
		submit : function(v, m, k) {
			if (v) {

				$("#pcoreRole")
						.attr("action",
								"${base}/manage/rolemanage/role/roleAction!deleteRole.${actionExt}");
				$("#pcoreRole").submit();
				return true;

			} else {

				return true;
			}
		}
	});
}
$("document").ready(function() {

	var flag = '${requestScope.flag}';

	if (flag == 1) {

		parent.document.getElementById("left").contentWindow.document.location
				.reload();
	}

});


$("document").ready(function() {
			$(".showDiv").mousemove(function(e) {
						$("#showMsg").css("top", e.clientY);
						$("#showMsg").css("left", e.clientX + 2)
						$("#showMsg").html(this.getAttribute("dec"));
						$("#showMsg").show();
					});

			$(".showDiv").mouseout(function(e) {
						$("#showMsg").hide();
					});

		});

   
	</script>
	</head>

	<body>
		<div id="main">
			<div class="title">
				<h4>
						<a href="${base }/html/adminSetting/index.jsp">管理设置</a> 
						&gt;&gt;
						<a href="${base}/manage/rolemanage/role/roleAction!findAllRole.${actionExt}">角色管理</a>
					
				</h4>
			</div>
			<form name="pcoreRole" id="pcoreRole" method="POST"
				action="${base}/manage/rolemanage/role/roleAction!findAllRole.${actionExt}">

				<table width="100%" border="0" cellspacing="0" bgcolor="#E5E8ED"
					cellpadding="0">
					<tr>
						<td width="58%">
							<div>
								<span class="table-navdiv1"> 
								
								<input type="button"
										value="添加" class="bot1other" onclick="addRole()"> 
								<input
										type="button" value="删除" class="bot1other"
										onclick="deleteRole(this)" /> </span>
							</div>
						</td>
						<td width="42%">
							<div align="right">
								<input type="text" name="roleName" size="12" value="请输入角色名称"
									id="roleName" onblur="addText()" onfocus="deleteText()"
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
									onclick="javascript:setAll(pcoreRole.roleId)" />
							</td>

							<td width="15%">
								角色名称
							</td>
							<td width="40%">
								角色描述
							</td>
							<td width="10%">
								角色权限范围
							</td>
							<td width="10%">
								修改角色
							</td>
							<%-- 
							<td width="10%">
								成员管理
							</td>
							--%>
							<td width="10%">
								权限管理
							</td>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${! empty requestScope.roleList}">
								<c:forEach items="${requestScope.roleList}" var="pcoreRole">
									<tr style="text-align: center;">
										<td>
											<c:choose>
												<c:when test="${pcoreRole.roleType==0}">
													<input type="checkbox" name="roleId"
														value="${pcoreRole.roleId}" id="roleId"
														disabled="disabled" />
												</c:when>
												<c:otherwise>
													<input type="checkbox" name="roleId"
														value="${pcoreRole.roleId}" id="roleId"
														onclick="deleteAllCheck()" />
												</c:otherwise>
											</c:choose>
										</td>
										<td align="left">
											<c:choose>
												<c:when test="${ fn:length(pcoreRole.roleName) > 10 }">
													<span title="${pcoreRole.roleName}"><c:out
															value="${ fn:substring(pcoreRole.roleName,0,10)}"
															escapeXml="true"></c:out>…</span>
												</c:when>
												<c:otherwise>
													<span title="${pcoreRole.roleName}"><c:out
															value="${pcoreRole.roleName}" escapeXml="true"></c:out> </span>
												</c:otherwise>
											</c:choose>
										</td>

										<td align="left">

											<c:choose>
												<c:when test="${ fn:length(pcoreRole.roleDec) > 24 }">
													<span class="showDiv" dec="${pcoreRole.showRoleDec}"
														decLength="${pcoreRole.decLength}"><c:out
															value="${ fn:substring(pcoreRole.roleDec,0,24)}"
															escapeXml="true"></c:out>...</span>
												</c:when>
												<c:otherwise>
													<span class="showDiv" dec="${pcoreRole.showRoleDec}"
														decLength="${pcoreRole.decLength}"><c:out
															value="${pcoreRole.roleDec}" escapeXml="true"></c:out> </span>
												</c:otherwise>
											</c:choose>
										</td>
										<td width="10%">
												自定义
										</td>
										<td>
											<a title="修改角色"
												href="javascript:updateRole('${pcoreRole.roleId}')"><img
													src="${base}/images/ico_modify.png" /> </a>
										</td>
										<%-- 
										<td>
											<c:choose>
												<c:when test="${pcoreRole.roleType==0}">
													<a href="javascript:void(0);" onclick="javascript:void(0);"
														style="cursor: default;"><img
															src="${base}/images/emphui.png" /> </a>

												</c:when>
												<c:otherwise>
													<a title="成员管理"
														href="javascript:manageEmp('${pcoreRole.roleId}')"><img
															src="${base}/images/ico_manage.png" /> </a>
												</c:otherwise>
											</c:choose>
										</td>
										--%>
										<td>
											<a title="权限管理"
												href="${base}/manage/permission/permissionAction!index.${actionExt}?rid=${pcoreRole.roleId}&rname=${pcoreRole.roleName}"><img
													src="${base}/images/ico_power.png" /> </a>
										</td>
										<!-- 
										<td align="center">
											<ul class="actor-control">
												<li class="modify">
													<a title="修改角色"
														href="javascript:updateRole('${pcoreRole.roleId}')"><span>角色</span>
													</a>
												</li>
												<c:choose>
													<c:when test="${pcoreRole.roleType==0}">
														<li
															style="background: url(${base}/images/emphui.png) center no-repeat;">
															<a  href="javascript:void(0);"
																onclick="javascript:void(0);" style="cursor: default;"><span>成员</span> </a>
														</li>
													</c:when>
													<c:otherwise>
														<li
															style="background: url(${base}/images/ico_manage.png) center no-repeat;">
															<a title="成员管理"
																href="javascript:manageEmp('${pcoreRole.roleId}')"><span>成员</span>
															</a>
														</li>
													</c:otherwise>
												</c:choose>

												<li class="power">
													<a title="权限管理"
														href="javascript:managePer('${pcoreRole.roleId}')"><span>权限</span>
													</a>
												</li>
											</ul>
										</td>-->
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
			<div class="pages">
				<%@ include file="/commons/page.jsp"%>
			</div>
		</div>
		<div name="showMsg" id="showMsg"
			style="position: absolute; background-color: #ffffe1; border: 1px solid #000000; padding-left: 2px; padding-right: 2px; display: none; word-wrap: break-word; word-break: break-all;"></div>
		</table>
		<input type="hidden" value="" id="roleScopes" name="roleScopes" />
		<input type="hidden" value="" id="userIds" name="userIds" />
		<input type="hidden" value="" id="pers" name="pers" />

	</body>
</html>
