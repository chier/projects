<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title><fmt:message key="efetionmanage.common.platformtitle" />
		</title>

		<link href="${base }/css/css130522.css" rel="stylesheet"
			type="text/css" />
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<script language="Javascript">
		document.oncontextmenu=function(e){return  false;}  
		$(function(){			
$('a,input[type="button"]').bind('focus',function(){
if(this.blur){ //如果支持 this.blur
this.blur();
}
});
});	
	/**
	 * 获取焦点效果
	 */
	function overmenustyle(clickIndex)
	{
		$("li:eq("+clickIndex+")").removeClass("menu_line").addClass("menu_hover")
	}
	/**
	 * 释放焦点效果
	 */
	function outmenustyle(clickIndex){
		if($(".menu_hover").size() > 1){
			$("li:eq("+clickIndex+")").removeClass("menu_hover").addClass("menu_line")
		}
	}
	
	/**
	 * 点击样式更换
	 */
	function chengemenustyle(clickIndex){
		$("li").each(function(index, value){
			if(index == clickIndex){
				$(this).removeClass("menu_line").addClass("menu_hover");
			}else{
				$(this).removeClass("menu_hover").addClass("menu_line");
			}
		});
	}
	
	function goOut(t){
		document.getElementById("webAdminForm").target="_self";
		document.getElementById("webAdminForm").action="${base }/public/common/login/logOutAction.${actionExt}?t="+t;
		document.getElementById("webAdminForm").submit();
	}
	function changepng(n,c)
	{
		document.getElementById(n).src="${base }/images/"+n+"-"+c+".png";
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
		<style type="text/css">
  *{
    padding:0px;}
  #all{width:400px;
	   margin:0px auto;
	   color:#f00;	   }
  #top,#bt{height:20px;
           background-color:#ccc;
		   color:#00f;
		   font-weight:bold;}
  #listtt{width:25%;
        height:120px;
        float:left;
		background-color:#eee;}
  #contenttt{width:75%;
        height:120px;
		background-color:#f7f7f7;}
</style>
	</head>
	<body onload="iframeHeight()" >
		<SCRIPT LANGUAGE="JavaScript"> 
var s = ""; //
s += "\r\n网页可见区域宽："+ document.body.clientWidth; 
s += "\r\n网页可见区域高："+ document.body.clientHeight; 
s += "\r\n网页可见区域宽："+ document.body.offsetWidth +" (包括边线和滚动条的宽)"; 
s += "\r\n网页可见区域高："+ document.body.offsetHeight +" (包括边线的宽)"; 
s += "\r\n网页正文全文宽："+ document.body.scrollWidth; 
s += "\r\n网页正文全文高："+ document.body.scrollHeight; 
s += "\r\n网页被卷去的高："+ document.body.scrollTop; 
s += "\r\n网页被卷去的左："+ document.body.scrollLeft; 
s += "\r\n网页正文部分上："+ window.screenTop; 
s += "\r\n网页正文部分左："+ window.screenLeft; 
s += "\r\n屏幕分辨率的高："+ window.screen.height; 
s += "\r\n屏幕分辨率的宽："+ window.screen.width; 
s += "\r\n屏幕可用工作区高度："+ window.screen.availHeight; 
s += "\r\n屏幕可用工作区宽度："+ window.screen.availWidth; 
s += "\r\n你的屏幕设置是 "+ window.screen.colorDepth +" 位彩色"; 
s += "\r\n你的屏幕设置 "+ window.screen.deviceXDPI +" 像素/英寸"; 
//alert(s); 
//document.all["mainIframe"].body.style.pixelHeight=document.body.scrollHeight-100;

function getObject(objectId) 
{ 
	if(document.getElementById && document.getElementById(objectId)) 
	{ 
	return document.getElementById(objectId) 
	} 
	else if(document.all && document.all(objectId)) 
	{ 
	return document.all(objectId) 
	} 
	else if(document.layers && document.layers[objectId]) 
	{ 
	return document.layers[objectId] 
	} 
	else 
	{ 
	return false 
	} 
}
</SCRIPT>
		<input type="hidden" name="topCsId" id="topCsId" value="" />
		<div class="content">
			<!--顶部开始-->
			<div class="header">
				<div class="header_nr">
					<div class="logo">
						<img src="${base}/images/index_03.png" width="82" height="74" />
					</div>
					<div class="header_nr01">
						<img src="${base}/images/index_04.png" width="424" height="41" />
					</div>
					<div class="header_right">
						<!--
						<div class="header_01">
							<a href="#">设为首页</a>
						</div>
						 -->
						<div class="header_03">
							<a href="javascript:void(0);" onclick="modifyPwd();">修改密码</a>
						</div>
						<div class="header_02">
							<a href="javascript:void(0);" onclick="logout(0);">安全退出</a>
						</div>
						<form id="webAdminForm" name="webAdminForm" action=""
								method="post">
						</form>
					</div>
				</div>
			</div>
			<!--导航开始-->
			<div class="menu">
				<div class="menu_top"></div>
				<div class="menu_nr">
					<ul>
						<c:forEach var="model" items="${userInfo.allModel}" varStatus="status">
								<c:if test="${status.index == 0}">
									<li class="menu_hover">
								</c:if>
								<c:if test="${status.index != 0}">
									<li class="menu_line">								
								</c:if>
									<a href="${base}${model.actionUrl}" target="mainIframe" 
									onclick="chengemenustyle('${status.index}')"
									onmouseover="overmenustyle('${status.index}')"
									onmouseout="outmenustyle('${status.index}')" >${model.modelName}</a>
								</li>
						</c:forEach>
					</ul>
				</div>
			</div>
			
			<div>
			<iframe name="mainIframe" id="mainIframe" scrolling="auto" 
						frameborder="0" width="100%" src="${base }/public/common/login/loginAction!index.${actionExt}"></iframe>
			<!--底部开始-->
			</div>
			<div class="clear"></div>
			<div class="foot">
				<div class="foot_nr">
					<div class="foot_bq">
						Copyright@2013 SCIES Corporation, All Rights Reserved
						<p>
							环境保护部华南环境科学研究所 版权所有
					</div>
				</div>
			</div>
			<!--底部结束-->
		</div>
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
var isShow=true;
function iframeHeight()
{
	//alert(document.body.clientHeight);
	var hg = document.body.scrollHeight-200;
	$("#mainIframe").height(hg);
	
}
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

