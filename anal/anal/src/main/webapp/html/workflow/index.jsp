<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title><fmt:message key="efetionmanage.common.platformtitle" />
		</title>
		<link href="${base }/css/style.css" rel="stylesheet" type="text/css"
			media="all" />
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<!--[if IE]>
<link href="${base }/css/fkie.css" rel="stylesheet" type="text/css" media="all" />
<![endif]-->
		<script language="Javascript">
		document.oncontextmenu=function(e){return  false;}  
		$(function(){			
$('a,input[type="button"]').bind('focus',function(){
if(this.blur){ //如果支持 this.blur
this.blur();
}
});
});	

	function goOut(t){
		document.getElementById("webAdminForm").target="_self";
		document.getElementById("webAdminForm").action="${base }/public/common/login/logOutAction.${actionExt}?t="+t;
		document.getElementById("webAdminForm").submit();
	}
	$(function(){
		var ie6 = (jQuery.browser.msie && jQuery.browser.version < 7);
		if(ie6){
			$('#left').css({overflow:'hidden'});
		}else{
			$('#left').css({overflow:'visible'});
		}
	});
	</script>
		<style>  
  body   {  
    -moz-user-select:none;  
  }  
  .footLink a,.footLink a:link,.footLink a:hover,.footLink a:visited,.footLink a:active{color:#64758F;}
  </style>
	</head>
	<body onresize="iframeHeight()" onselectstart="return false">
		<div class="top">
			<div class="welcome3">
				${name}&nbsp;&nbsp;欢迎登录
			</div>
			<div class="logout2">
				<a href="http://${efetionurl}/ewebhelp/xssl.html" target="_blank">入门指南</a>
				|
				<c:forEach var="em" items="${emList}">
					<a href="${em.fileUrl}" target="_blank">${em.fileTypeStr}</a> |
							</c:forEach>
				<a href="#" onclick="modifyPwd();"><fmt:message
						key="efetionmanage.framework.login.modifyPassword" /> </a> |
				<a href="#" onclick="logout(0);"><fmt:message key="logOut" /> </a>
			</div>
		</div>
		<form id="webAdminForm" name="webAdminForm" action="" method="post">
		</form>
		<div class="warp_n">
			<table cellpadding="0" cellspacing="0" border="0" class="main-iframe"
				width="100%">
				<tr>
					<td width="185" id="leftTab">
						<iframe name="left" id="left" frameborder="0" scrolling="yes"
							width="100%" height="100%"
							src="${base}/manage/workflow/workflowAction.${actionExt}"></iframe>
					</td>

					<td width="12" align="left"
						style="background: url(${base }/images/bg_switch.gif) repeat-y;"
						valign="center">
						<img id="leftImg"
							src="${base}/images/backimg/menu_switch_close.gif" width=10
							height=70 onclick="changeLeft();" style="cursor: hand;" />
					</td>
					<td id="contentTab">
						<c:choose>
							<c:when test="${not empty modelAction}">
								<iframe name="left" id="right" scrolling="auto" frameborder="0"
									width="100%" height="100%" src="${base }${modelAction}"></iframe>
							</c:when>
							<c:otherwise>
								<iframe name="right" id="right" scrolling="auto" frameborder="0"
									width="100%" height="100%"
									src="${base }/manage/configguide/configGuideDispatherAction!firstPage.${actionExt}"></iframe>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</div>
		<div id="footer">
			<div id="shangfan"
				style="margin-left:150px;left:-20px;top:0;position:absolute;">
				<div id="holder">
				</div>
			</div>
		</div>
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
		leftTab.style.width="185";
		isShow=true;
		leftImg.src="${base}/images/backimg/menu_switch_close.gif";
		//alert("3");
	}
}
function obj_init() {
     var objdiv=document.getElementById("jqi");
            var w = objdiv.offsetWidth;
            var h = objdiv.offsetHeight;
            var ifrm = document.createElement('iframe');
            ifrm.src = 'javascript:false';
            ifrm.scrolling='no';
            ifrm.frameBorder='0';
            ifrm.style.cssText = 'position:absolute; visibility:inherit; top:0px; left:0px; width:' + w + 'px; height:' + h + 'px; z-index:-1; filter: \'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\'';
            objdiv.appendChild(ifrm);
}
function modifyPwd() {
    v_lable = '<div class="ms4">修改密码</div>';
	var txt = '<lable>'+v_lable+'</lable><br><div id="wrap"><table class="warp-login"><tr> <td><div class="editpsw"><dl><dt><label for="oldPassword"><fmt:message key="efetionmanage.framework.login.oldPassword" />:</label><input type="password" name="oldPassword" id="oldPassword"  maxlength="16"  onKeyUp="cleanErrorMes();" oncopy="return false;" oncut="return false;" onpaste="return false;" size="22" class="f-text" /></dt><dd><span id="aa"></span></dd><dt> <label for="password"><fmt:message key="efetionmanage.framework.login.NewPassword" /></label><input type="password" name="password" id="password"  maxlength="16" onKeyUp="validateOldpwd();" oncopy="return false;" oncut="return false;" onpaste="return false;" size="22" class="f-text" /><br><font color="gray">输入6-16位数字和字母的组合</font></dt><dd><span id="passwordmes"></span></dd><dt><label for="uid"><fmt:message key="efetionmanage.framework.login.confirmPassword" /></label><input type="password" name="confirmPassword" id="confirmPassword"  maxlength="16" oncopy="return false;" onKeyUp="validateOldpwd();" oncut="return false;" onpaste="return false;" size="22" class="f-text" /></dt><dd><span id="confirmPasswordmes"></span></dd>   </dl> </div> </td> </tr></table></div>';
	var txt1 = '<div align="center"><div class="ms4">修改密码</div><br><table class=""><tr><td align="right"><fmt:message key="efetionmanage.framework.login.oldPassword" />:<font color="#9d0c0c">*</font></td><td align="left"><input type="password" name="oldPassword" id="oldPassword"  maxlength="16"  onKeyUp="cleanErrorMes();" oncopy="return false;" oncut="return false;" onpaste="return false;" size="22" class="f-text"/></td></tr><tr><td></td><td align="left"><span id="aa" align="left"></span></td></tr><tr><td align="right"><fmt:message key="efetionmanage.framework.login.NewPassword" />:<font color="#9d0c0c">*</font></td><td align="left"><input type="password" name="password" id="password"  maxlength="16" onKeyUp="validateOldpwd();" oncopy="return false;" oncut="return false;" onpaste="return false;" size="22" class="f-text"/></td></tr><tr><td></td><td><font color="gray">输入6-16位数字和字母的组合</font></td</tr><tr><td></td><td align="left"><span id="passwordmes" align="left"></span></td></tr><tr><td align="right"><fmt:message key="efetionmanage.framework.login.confirmPassword" />:<font color="#9d0c0c">*</font></td><td align="left"><input type="password" name="confirmPassword" id="confirmPassword"  maxlength="16" oncopy="return false;" onKeyUp="validateOldpwd();" oncut="return false;" onpaste="return false;" size="22" class="f-text" /></td></tr><tr><td></td><td align="left"><span id="confirmPasswordmes" align="left"></span></td></tr></table></div>';
	$.prompt(txt1, {buttons:{确定:true, 取消:false}, submit:function (v, m, f) {
		if (v) {
				var arr = document.getElementById("oldPassword").value;
				var tmp = document.getElementById("password").value;
				var temp = '(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{2,})$';
			if(!isalphanumber(document.getElementById("oldPassword").value) || trim(document.getElementById("oldPassword").value).length<6){
				cleanErrorMes();
				if(trim(document.getElementById("oldPassword").value)==''){
					document.getElementById("aa").innerHTML='<font color="#9d0c0c"><fmt:message key="efetionmanage.framework.login.inputOldPassword"/></font>';
				}else{
					document.getElementById("aa").innerHTML='<font color="#9d0c0c"><fmt:message key="efetionmanage.framework.login.validatePassword"/></font>';
				}
				document.getElementById("oldPassword").value="";
				document.getElementById("oldPassword").focus();
				return false;
			}else if(!isalphanumber(document.getElementById("password").value)){
				cleanErrorMes();
				document.getElementById("passwordmes").innerHTML="<font color='#9d0c0c'><fmt:message key="efetionmanage.framework.login.inputNewPassword"/></font>";
				document.getElementById("password").value="";
				document.getElementById("confirmPassword").value="";
				document.getElementById("password").focus();
				return false;
			} else if(trim(document.getElementById("password").value).length<6 || trim(document.getElementById("password").value).length>16){
				cleanErrorMes();
				document.getElementById("passwordmes").innerHTML="<font color='#9d0c0c'><fmt:message key="efetionmanage.framework.login.mes"/></font>";
				document.getElementById("password").value="";
				document.getElementById("confirmPassword").value="";
				document.getElementById("password").focus();
				return false;
			} else if( !document.getElementById("password").value.match(temp)){
			    cleanErrorMes();
				document.getElementById("passwordmes").innerHTML="<font color='#9d0c0c'><fmt:message key="efetionmanage.framework.login.mes"/></font>";
				document.getElementById("password").value="";
				document.getElementById("confirmPassword").value="";
				document.getElementById("password").focus();
			
			}else if(!isalphanumber(document.getElementById("confirmPassword").value)){
				cleanErrorMes();
				document.getElementById("confirmPasswordmes").innerHTML="<font color='#9d0c0c'><fmt:message key="efetionmanage.framework.login.inputConfirmPassword"/></font>";
				document.getElementById("confirmPassword").value="";
				document.getElementById("confirmPassword").focus();
				return false;
			//}
			//else if(trim(document.getElementById("confirmPassword").value).length<6 || trim(document.getElementById("confirmPassword").value).length>16 || !document.getElementById("confirmPassword").value.match(temp)){
			//	cleanErrorMes();
			//	document.getElementById("confirmPasswordmes").innerHTML="<font color='red'><fmt:message key="efetionmanage.framework.login.validatePassword"/></font>";
			//	document.getElementById("confirmPassword").value="";
			//	document.getElementById("confirmPassword").focus();
			//	return false;
			}else if(trim(document.getElementById("password").value) !=trim(document.getElementById("confirmPassword").value)){
				cleanErrorMes();
				document.getElementById("confirmPasswordmes").innerHTML="<font color='#9d0c0c'><fmt:message key="efetionmanage.framework.webAdmin.noSamePassword"/></font>";
				document.getElementById("password").value="";
				document.getElementById("confirmPassword").value="";
				document.getElementById("password").focus();
				return false;
			}else{
				cleanErrorMes();
				$.post("${base}/manage/common/password/modifyAdminPwdAction.${actionExt}", {oldPwd:arr, newPwd:tmp}, function (data) {
					if (data == "true") {
						jQuery.ImpromptuClose();
						$.prompt("修改密码成功！");
					} else {
						//jQuery.ImpromptuClose();
						//$.prompt("旧密码验证失败！");
				cleanErrorMes();
				document.getElementById("aa").innerHTML="<font color='#9d0c0c'>密码不正确，请重新输入</font>";
				document.getElementById("password").value="";
				document.getElementById("confirmPassword").value="";
				document.getElementById("oldPassword").value="";
				document.getElementById("oldPassword").focus();
					}
				});
			}
		}else{
			jQuery.ImpromptuClose();
		}
	}});
	obj_init();
}
function validateOldpwd(){
				cleanErrorMes();
				if(trim(document.getElementById("oldPassword").value).length<6){
					if(trim(document.getElementById("oldPassword").value)==''){
						document.getElementById("aa").innerHTML='<font color="red"><fmt:message key="efetionmanage.framework.login.inputOldPassword"/></font>';
					}else{
						document.getElementById("aa").innerHTML='<font color="red"><fmt:message key="efetionmanage.framework.login.validatePassword"/></font>';
					}
					document.getElementById("oldPassword").value="";
					document.getElementById("password").value="";
					document.getElementById("confirmPassword").value="";
					document.getElementById("oldPassword").focus();
				}
}

function cleanErrorMes(){
	var mess=document.getElementsByTagName("span");
	if(mess.length>0){
		for(var i=0;i<mess.length;i++){
			mess[i].innerHTML="";
		}
	}
}

function logout(t){
	$.prompt("  <fmt:message key='efetionmanage.common.goout'></fmt:message>",{
					buttons:{确定:true,取消:false},
					alertType:'ask',
					submit:function(v,m,f){
						if(v){
							goOut(t);
						}else{
							jQuery.ImpromptuClose();
						}
					}});
	obj_init();
}

</SCRIPT>

<style>
	#shangfan {
		float:left;
		width:690px;
		padding-left:50px;
		margin-top:16px;
		height:24px;
		line-height:24px;
		overflow:hidden;}
	#shangfan a {
		color:#61adfb;}
	#shangfan a:hover {
		color:#61adfb;
		text-decoration:underline;}
</style>
<script>
function marquee(height,speed,delay){ 
	var scrollT; 
	var pause = false; 
	var ScrollBox = document.getElementById("shangfan"); 
	if(document.getElementById("holder").offsetHeight <= height) return; 
	var _tmp = ScrollBox.innerHTML.replace('holder', 'holder2') 
	ScrollBox.innerHTML += _tmp; 
	ScrollBox.onmouseover = function(){pause = true} 
	ScrollBox.onmouseout = function(){pause = false} 
	ScrollBox.scrollTop = 0; 
	
	function start(){
		scrollT = setInterval(scrolling,speed); 
		if(!pause) ScrollBox.scrollTop += 2; 
	} 
	function scrolling(){
		if(ScrollBox.scrollTop % height != 0){ 
		ScrollBox.scrollTop += 2; 
		if(ScrollBox.scrollTop >= ScrollBox.scrollHeight/2) ScrollBox.scrollTop = 0; 
		} 
		else{ 
			clearInterval(scrollT); 
			setTimeout(start,delay); 
		} 
	} 
	setTimeout(start,delay); 
}
marquee(24,30,3000); 
</script>
