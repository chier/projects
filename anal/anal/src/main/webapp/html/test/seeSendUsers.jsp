<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>
<%@ include file="/js/calendar/calendar2/calendar.jsp"%>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<link href="${base}/css/manage1.css" rel="stylesheet" type="text/css"
			media="all" />
		<script type="text/javascript">
$("document").ready(function() {

	$("#username").focus();
	$("#username").blur();
	
	var content = $("#username");
	if (content.val().replace(/[ \t]+/g, "") == ""
			|| content.val().replace(/[ \t]+/g, "") == "请输入姓名或手机号") {
		content.val("请输入姓名或手机号");
		content.css("color", "#949494");
	}
	
	
	
});
function addText() {
	var content = document.getElementById("username");
	if (content.value.replace(/[ \t]+/g, "") == "") {
		content.value = "请输入姓名或手机号";
		content.style.color = '#949494';
	}
}

function deleteText() {
	var content = document.getElementById("username");
	if (content.value.replace(/[ \t]+/g, "") == ""
			|| content.value.replace(/[ \t]+/g, "") == "请输入姓名或手机号") {
		content.value = "";
		content.style.color = "black";
	}
}       
function findUser(){
    $("#userForm").submit();
}

function checkInputChar(){
	var userName = $.trim($("#username").val());
	var charType = 0;var lastCharType = 0;
	for(var i = 0; i < userName.length; i++){
		var chrName = userName.charAt(i);
		
		if(chrName >= 0 && chrName <= 9){
			charType = 0;
		}else if(/.*[\u4e00-\u9fa5]+.*$/.test(chrName)){
			charType = 1;
		}else{
			charType = 2;
		}
		//alert(chrName + ":" + charType);
		/*
		if(i == 0 && charType == 2){
			userName = "";
			break;
		}else if(i > 0 && lastCharType != charType){
			userName = userName.substring(0, i);
			//alert("userName:" + userName);
			break;
		}
		*/
		lastCharType = charType;
	}

	//var valid = userName.replace(/[^\u4E00-\u9FA5]/g,'');
	//alert(userName);
	$("#username").val(userName);
}                                
                                
</script>
	</head>
	<body style="background:none">
		<div style="background:none;text-align:center;">
			<form id="userForm" autocomplete="off" name="userForm" method="post"
				action="${base}/systemmessage/systemMessageAction!findByUserInfo.${actionExt}">
				<input type="hidden" value="${requestScope.aiid}" id="aiid"
					name="aiid" />

				<div class="mesbox" style="width:555px;background:none;border:none;">
					<div class="conbox" style="100%;background:none;border:none;">
						<table cellpadding="0" cellspacing="0" width="100%" border="0">
							<tr>
								<td width="100" align="left" height="30" valign="middle">
									<span class="gray pdlr4 b">人员列表：</span>
								</td>
								<td valign="middle" align="right">
									<nobr>
									<span class="pdlr4"> <input id="username"
											name="username" type="text" style="width: 135px;"
											value="${requestScope.username }" onkeyup="checkInputChar()"
											onfocus="deleteText()" onblur="addText()" /> </span>
									<span class="pdlr4"> <a href="javascript:findUser()"
										class="icon_search gray">查找</a> </span>
									</nobr>
								</td>
							</tr>
							<tr>
								<td valign="top" colspan="2">
									<div
										style="overflow-y: auto;width:95%;height:220px;border:solid 1px #a9c1db;padding:5px;">
										<table style="width: 100%; overflow: hidden;">
											<tr>
												<c:forEach items="${requestScope.userList}" var="s"
													varStatus="num">
													<c:choose>
														<c:when test="${(num.index+1)%3 != 0}">
															<td align="left" style="width: 30%; overflow: hidden;">
																${s.realname}(${s.mp})
															</td>
														</c:when>
														<c:otherwise>
															<c:if test="${num.index%3!=0}">
																<td align="left" style="width: 30%; overflow: hidden;">
																	${s.realname}(${s.mp})
																</td>
															</c:if>
											</tr>
											<c:if test="${num.index%3 == 0}">
												<tr>
											</c:if>
														</c:otherwise>
												</c:choose>
											</c:forEach>
										</table>
									</div>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</form>
		</div>
	</body>
</html>
