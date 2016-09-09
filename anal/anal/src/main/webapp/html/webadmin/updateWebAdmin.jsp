<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<!-- 
	updateWebAdmin.jsp
	管理员配置修改页面
 -->
	<head>
		<title>${requestScope.platformTitle}</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<style>
			.Per_sel_ul{padding:1px;}
			.Per_sel_ul li{
				width:100%;
				margin-bottom:5px;
				display:block; 
				cursor:pointer; 
				*margin-bottom:-2px; 
				text-overflow: ellipsis; 
				-moz-text-overflow: ellipsis;
				overflow:hidden; 
				line-height:17px;
			}
			</style>
		<script type="text/javascript" src="${base}/js/check_all.js"></script>
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<!--[if IE]>
<link href="${base }/css/fkie.css" rel="stylesheet" type="text/css" media="all" />
<![endif]-->
		<script language="Javascript">
		
	var memberSearchDefaultText = "按名称、拼音首字母、全拼、手机号码";
	
	function addAdmin(){
		if (validate('usName') && validate('pwd1') && validate('pwd2') && validate("usRole")) {
			if ('${actionUrl}' =='modifyWebAdminAction'){
				document.getElementById("webAdminForm").action='${base}/manage/safemanage/webadmin/${actionUrl}.${actionExt}?id=${webAdmin.identifier}&page=${page}';
				document.getElementById("webAdminForm").submit();
			} else {
				document.getElementById("webAdminForm").action='${base}/manage/safemanage/webadmin/${actionUrl}.${actionExt}';
				document.getElementById("webAdminForm").submit();
				
			}
			document.getElementById("submitbtn").disabled = true;
		}
	}
	
	
	Array.prototype.baoremove = function(dx)
	  {
	    if(isNaN(dx)||dx>this.length){return false;}
	    for(var i=0,n=0;i<this.length;i++)
	    {
	        if(this[i]!=this[dx])
	        {
	            this[n++]=this[i]
	        }
	    }
	    this.length-=1
	  }
		
		function getCheckedBox(elms){
			var idstr="";
			for(var i=0;i<elms.length;i++)
			{
				if(elms[i].checked == true)
				{
				idstr+=elms[i].value+";";
				}
			}
			return idstr;
		}
		
	function validatorCheckBox(idds){
		var elms = document.getElementsByName(idds);
		var x = 0;
		for(var i=0;i<elms.length;i++)
		{
			if(elms[i].checked == true)
			{
				x=x+1;
			}
		}
		if(x<1)
		{
			document.getElementById("permiss").innerHTML='<fmt:message key="efetionmanage.framework.webAdmin.notchoose"/>';
			return false;
		}else{
			return true;
		}
	}
	window.onload=function(){
		 document.getElementById("beginName").value = document.getElementById("admin").value;
	}
		
	function modflag(){
		document.getElementById("modPwd").value='1';
	
	}
	
	function modAdmin(){
		document.getElementById("modName").value='1';
	}
	
	function clearOwnerTip() {
     	if ($("#searchOwner").attr("value") == memberSearchDefaultText) {
     		$("#searchOwner").attr("value", "");
     		$("#searchOwner").css("color", "#000000");
     	}
     }
	
	 function ownerTextBlur(val) {
     	if ($.trim(val) == '') {
        	$("#searchOwner").attr("value", memberSearchDefaultText)
            $("#searchOwner").css("color", "#949494")
        }
        document.body.focus();
     }
	
	 function ownerKeydown(event,val) {
     	if(event.keyCode == 13) {
     		startSearchOwner(val);
     		document.body.focus();
     	}
     }
        
	
	function cleanErrorMes(){
		document.getElementById("login").innerHTML="";
		document.getElementById("pwd").innerHTML="";
		document.getElementById("repwd").innerHTML="";
	}
	function textIsNull() {
		cleanErrorMes();
		var submitbtn=document.getElementById("submitbtn");
		var name=document.getElementById("admin").value;
		var pw=document.getElementById("password").value;
	}
	function validate(str){
		
		var name = document.getElementById("admin");
		var paswd=document.getElementById("password");
		var repaswd=document.getElementById("rePassword");
		var beginName = document.getElementById("beginName");
		var temp = '(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{2,})$';
		if (str=='usName'){
			if(trim(name.value).length<6 || !isalphanumber(name.value)){
				document.getElementById("login").innerHTML='<fmt:message key="efetionmanage.framework.webAdmin.validateName"/>';
				name.value='';
				//name.focus();
				return false;
			} else {
					var adminName = document.getElementById("admin").value.replace(/(^\s*)|(\s*$)/g,"");
					var date = new Date();
					var booleanDate = false;
					$.ajax({
						type : "POST",
						async: false,
						url  : "${base}/manage/safemanage/webadmin/listWebAdminAction!validateAdminName.${actionExt}",
						data : "dates=" + date + "&adminName=" + adminName +"&adminid=${webAdmin.identifier}",
						success : function(msg) {
							if( msg == "0"){
								booleanDate = true;
							}
							if(booleanDate){
								document.getElementById("login").innerHTML='';
							}else{
								document.getElementById("login").innerHTML='<fmt:message key="efetionmanage.framework.webAdmin.SameName"/>';
								document.getElementById("admin").value="";
							}
						},
						error : function(XMLHttpRequest, textStatus, errorThrown) {
							window.top.location=base + "/commons/beforetimeout.jsp";
						}
					});
					return booleanDate;
			}
		} else if (str=='pwd1') {
			if(trim(paswd.value).length<6 ) {
				document.getElementById("pwd").innerHTML='输入6-16位数字和字母';
				paswd.value=""; 
				//paswd.focus();
				return false;
			} else if (!trim(paswd.value).match(temp)) {
				document.getElementById("pwd").innerHTML='密码强度不足';
				paswd.value=""; 
				//paswd.focus();
				return false;
			}else {
				document.getElementById("pwd").innerHTML='';
				return true;
			}
		} else if (str=='pwd2') {
			if(trim(paswd.value).length<6 || repaswd.value != paswd.value){
				document.getElementById("repwd").innerHTML='密码不匹配，请再次输入';
				repaswd.value="";
				//repaswd.focus();
				return false;
			} else {
				document.getElementById("repwd").innerHTML='';
				return true;
			}
		}else if(str == 'usRole'){
			var item = "";
			// var item =$('input:checkbox[name="modelId"]:checked').val();
			$("input[name='modelId']:checkbox:checked").each(function(){ 
				item += $(this).val();
				item += ",";
			}) 
			if(item){
				return true;
			}else{
				document.getElementById("roleErr").innerHTML='请选择模块';
				return false;
			}
		}
	}

$(document).ready(function(){
	var isSameName = "${isSameName}";
	if(isSameName){
		document.getElementById("login").innerHTML='用户名已经存在';
	}
	
	
	if ('${actionUrl}' =='modifyWebAdminAction'){
		$("input[name='modelId']:checkbox").each(function(){
			<c:forEach var="idv" items="${mids}">
				if("${idv}" == $(this).val()){
					$(this).attr("checked",true);
				}
			</c:forEach>
		}) 
		
		
		$("input[name='roleId']:radio").each(function(){
			console.info($(this).val());
			console.info("${webAdmin.roleId}");
				if("${webAdmin.roleId}" == $(this).val()){
					$(this).attr("checked",true);
				}
		}) 
	}
});

	
</script>
	</head>
	<body>
		<form name="webAdminForm" id="webAdminForm" action="" method="post">
			<input type="hidden" id="userid" name="userid"
				value="${employee.userId}" />
			<ss:token name="webAdmin.token" />
			<input name="deptId" id="deptId" type="hidden" value="${deptId}" />
			<input name="modPwd" id="modPwd" type="hidden" />
			<input name="modName" id="modName" type="hidden" />
			<input name="pids" id="pids" type="hidden" />
			<input name="beginName" id="beginName" type="hidden" />
			<input name="w_id" id="w_id" type="hidden"
				value="${webAdmin.identifier}" />
			<div>
				<div class="title">
					<h4>
						<a href="${base }/html/adminSetting/index.jsp">管理设置</a> 
						&gt;&gt;
						<a href="${base }/manage/safemanage/webadmin/listWebAdminAction.${actionExt}">管理员帐号管理</a>
					</h4>
				</div>
				<div class="msg"></div>
				<div class="area-warp">
					<h5>
						<c:if test="${actionUrl eq 'modifyWebAdminAction'}">
						修改帐号
					</c:if>
						<c:if test="${actionUrl ne 'modifyWebAdminAction'}">
						创建新帐号
					</c:if>
					</h5>
					<div class="area-content">
						<ul class="clearfix"
							style="padding:4px 15px 0 0; _padding:24px 15px 0 0; width:750px;">
							<li>
								<table class="area-table" align="center" width="750">
									<tr>
										<td width="10%" align="right" class="c-title">
											用户名:
											<span style="color: rgb(157, 12, 12);">*</span>
										</td>
										<td align="left" valign="top">
											<input name="admin" id="admin" style="width: 150px;"
												type="text" minlength="6" maxlength="16"
												value="${webAdmin.admin }"
												<c:if test="${actionUrl eq 'modifyWebAdminAction'}" >
													onchange="modAdmin()" 
												</c:if>
												onblur="this.value=trim(this.value),validate('usName')"
												size="20" class="f-text" onkeyup="textIsNull();" />
											<span id="login" class="erro"></span>
											<p class="prompt-msg">
												使用英文字母或数字，长度6-16位
											</p>
										</td>
									</tr>
									<tr>
										<td align="right" class="c-title">
											密码:
											<span style="color: rgb(157, 12, 12);">*</span>
										</td>
										<td align="left" valign="top">
											<input name="password" id="password" type="password"
												minlength="6" style="width: 150px;" oncopy="return false;"
												oncut="return false;" onpaste="return false;" maxlength="16"
												value="${webAdmin.password}"
												<c:if test="${actionUrl eq 'modifyWebAdminAction'}" >
													onchange="modflag();" 
												</c:if>
												size="20" class="f-text" onkeyup="textIsNull()"
												onblur="validate('pwd1')" />
											<span id="pwd" class="erro"></span>
											<p class="prompt-msg">
												使用英文字母和数字，长度6-16位
											</p>
										</td>
										<td align="left" valign="top"></td>
									</tr>
									<tr>
										<td align="right" class="c-title">
											确认密码:
											<span style="color: rgb(157, 12, 12);">*</span>
										</td>
										<td align="left" valign="top">
											<input name="rePassword" id="rePassword" type="password"
												minlength="6" style="width: 150px;" oncopy="return false;"
												value="${webAdmin.password}"
												<c:if test="${actionUrl eq 'modifyWebAdminAction'}" >
													onchange="modflag();" 
												</c:if>
												size="20" class="f-text" onkeyup="textIsNull()"
												onblur="validate('pwd2')" />
											<span id="repwd" class="erro"></span>
										</td>
										<td align="left" valign="top"></td>
									</tr>
								</table>
							</li>
							<li>
								<table class="area-table" align="center" width="750"
									style="_margin-top:-10px;">
									<tr>
										<td  width="10%" align="right" class="c-title">
											模块:
											<span style="color: rgb(157, 12, 12);">*</span>
										</td>
										<td>
											<div style="border: 1px solid #A9C1DB;margin-bottom:5px;">
												<ul class="Per_sel_ul">
													<c:forEach var="model" items="${modelList}">
														<li>
															<input type=checkbox name="modelId" value="${model.identifier}"
																id="modelId" />
															${model.modelName}
														</li>
													</c:forEach>
												</ul>
											</div>
											<span id="roleErr" class="erro"></span>
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											<span id="depErr" class="erro"></span>
										</td>
									</tr>
									<tr>
										<td  width="10%" align="right" class="c-title">
											角色:
											<span style="color: rgb(157, 12, 12);">*</span>
										</td>
										<td>
											<div style="border: 1px solid #A9C1DB;margin-bottom:5px;">
												<ul class="Per_sel_ul">
													<c:forEach var="role" items="${roleList}">
														<li>
															<input type="radio" name="roleId" value="${role.roleId}"
																id="roleId" />
															${role.roleName}
														</li>
													</c:forEach>
												</ul>
											</div>
											<span id="roleErr" class="erro"></span>
										</td>
										<td>
											&nbsp;
										</td>
										<td>
											<span id="depErr" class="erro"></span>
										</td>
									</tr>
									<tr height="20">
										<td>
											&nbsp;
										</td>
										<td valign="bottom">
											<span id="per" class="erro"></span>
											<div align="right">
												<input type="button" id="submitbtn" name="submitbtn"
													class="bot4"
													value="<fmt:message key="efetionmanage.common.confirm"/>"
													onclick="addAdmin();" />
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</div>
										</td>
										<td valign="bottom">
											<div align="right">
												<input type="button"
													style="margin-left:0px; display:block; display:inline;"
													value="<fmt:message key="efetionmanage.common.cancel"/>"
													onclick="javascript:history.back();" class="bot4" />
											</div>
										</td>
										<td>
											<span id="dep" class="erro"></span>
										</td>
									</tr>
								</table>
							</li>
						</ul>
					</div>

				</div>
			</div>
		</form>
	</body>
</html>
