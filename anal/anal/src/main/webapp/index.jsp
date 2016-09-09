<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>全国重点地区环境与健康调查分析平台</title>
		<link href="${base }/css/index.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<script language="Javascript">
		var re=/^\d{10}$/;
	function keyDown(e){
		temp = window.event||e ;
		var coded=temp.keyCode||temp.which; 
		if(coded==13){
			// vpic();
		}
	}
	
	/**
	 * 重新获取验证码
	 */
	function refreshcode(){
		document.getElementById("piccode").value="";
	   	document.getElementById("err_msg").innerHTML="";
	   	$("#err_msg_div").hide();
       	document.getElementById("err_msg").focus();
       	var verify=document.getElementById('verCode'); 
       	verify.setAttribute('src','${base}/public/common/login/piccode.portal?'+Math.random());  
    }
	
	
function vadmin(){ 
	if(document.getElementById("admin").value==""){
		document.getElementById("err_msg").innerHTML='用户名不能为空';
		$("#err_msg_div").show();
	}else if(trim(document.getElementById("admin").value).length<6){
		document.getElementById("err_msg").innerHTML='输入6-16位数字或字母的组合';
		$("#err_msg_div").show();
		document.getElementById("admin").value="";
		return false;
	}else {
		document.getElementById("err_msg").innerHTML="";
		$("#err_msg_div").hide();
	}
}
	function vpass(){
		if(document.getElementById("password").value==""){
			document.getElementById("err_msg").innerHTML='密码不能为空';
			$("#err_msg_div").show();
		}else if(trim(document.getElementById("password").value).length<6 || trim(document.getElementById("password").value).length>16){
			document.getElementById("err_msg").innerHTML='区分大小写，输入6-16位数字和字母的组合';
			$("#err_msg_div").show();
			document.getElementById("password").value="";
			return false;
		}else {
			document.getElementById("err_msg").innerHTML="";
			$("#err_msg_div").hide();
		}
	}
	function vpic(){
		var aValue = trim(document.getElementById("admin").value);
		var pValue = trim(document.getElementById("password").value);
		var checkStatus = document.getElementById("myCheck").checked;
		if(checkStatus){
			setCookie("admin",aValue);
		}
		var yzCode = getYanZ();
		if(yzCode == "success"){
			document.getElementById("err_msg").innerHTML="";
			$("#err_msg_div").hide();
			return true;
		}else{
			refreshcode();
			 document.getElementById("err_msg").innerHTML='<fmt:message key="efetionmanage.framework.login.validateCodePic"/>';
			 $("#err_msg_div").show();
			 document.getElementById("piccode").value="";
			 return false;
		}
		return true;
	}
	function reloadcode(){ 
		document.getElementById("admin").value = getCookie("admin"); 
       document.getElementById("admin").focus();
       var verify=document.getElementById('verCode'); 
       verify.setAttribute('src','${base}/public/common/login/piccode.portal?t='+Math.random());  
       requesterror();
     } 	 
	function cleanError(){
		
	}
	
	function requesterror(){
		<c:if test="${empty relogin}" >
			return ;
		</c:if>
		<c:if test="${not empty relogin}" >
			<c:if test="${empty resetPassword}" >
				<c:if test="${relogin=='nameErr'}" >
					 $.prompt("  填入的用户名或密码有误，请重新登录！",{buttons:{确定:true},alertType:'error'});
				     var name = "";
				     var pwd = "";	
					 fillText(name,pwd);
				</c:if>
				<c:if test="${relogin=='pwdErr'}" >
				   <c:if test="${flogNum==1}" >
				   	 $.prompt("  填入的用户名或密码有误，请重新登录！",{buttons:{确定:true},alertType:'error'});
				  </c:if>
				  <c:if test="${flogNum==2}" >
				     $.prompt("  你已经连续两次输入错误的帐号信息。累计到达三次时，你的帐号将被冻结。",{buttons:{确定:true},alertType:'error'});
				  </c:if>
				  <c:if test="${flogNum==3}" >
				  	 var msg = "  您的账号已被冻结，请联系超级管理员或客户经理重置密码，或通过“忘记密码”将密码找回并解冻。";
				     $.prompt(msg,{buttons:{确定:true},alertType:'error'});
				  </c:if>
				     var name = "";
				     var pwd = "";	
					 fillText(name,pwd);
				</c:if>
				<c:if test="${relogin=='suspension'}" >
					 $.prompt("  该企业已经暂停业务，请重新输入！",{buttons:{确定:true},alertType:'error'});
					 var shortname = "";
				     var name = "";
				     var pwd = "";	
					 fillText(name,pwd);
				</c:if>
				<c:if test="${relogin=='closed'}" >
					 $.prompt("  该企业已经关闭业务，请重新输入！",{buttons:{确定:true},alertType:'error'});
					 var shortname = "";
				     var name = "";
				     var pwd = "";	
					 fillText(name,pwd);
				</c:if>
				<c:if test="${relogin=='notOpen'}" >
					 $.prompt("   该企业尚未开通业务，请重新输入！",{buttons:{确定:true},alertType:'error'});
					 var shortname = "";
				     var code = "";
				     var name = "";
				     var pwd = "";	
					 fillText(name,pwd);
				</c:if>
				<c:if test="${relogin=='default'}" >
					 $.prompt("  填入的信息有误，请重新输入！ ",{buttons:{确定:true},alertType:'error'});
					 var shortname = "";
				     var code = "";
				     var name = "";
				     var pwd = "";	
					 fillText(name,pwd);
				</c:if>
				<c:if test="${relogin=='gdadc'}" >
					 var txt = '<fmt:message key="adc.prompt.txt"/>';
					 $.prompt(txt,{
						buttons:{是:true, 否:false},alertType:'msg',
						submit:function(v,m,f){
							if(v){
								toAdc();
							} else {
								$.ImpromptuClose();
							}
						}
					 });
				</c:if>
			</c:if>
		</c:if>
	}
	
	/**
	 * 验证码验证方法
	 */
	function getYanZ() {
		var yzm='';
		var date = new Date();
		$.ajax({
			type : "POST",
			async : false,
			url : "${base}/public/common/login/validate.portal",
			data: {piccode:$("#piccode").val()},
			dataType : "json",
			success : function(msg) {
				if("1"==msg){
				    yzm = 'success';
				}
			}
	
		});
		return yzm;
	}
	
	function getPassword(){
		document.getElementById("webAdminForm").action='${base}/public/common/login/forgetPwdAction!intoForgetPwd.${actionExt}';
		document.getElementById("webAdminForm").submit();
	}
	function fillText(name,pwd){
		    document.getElementById("admin").value=name;
		    document.getElementById("password").value=pwd;
	}
	function cleans(){
		document.getElementById("admin").value="";
		document.getElementById("password").value="";
	}
	function setCookie(admin,aValue){
		var Days = 30;
		var exp = new Date(); 
		exp.setTime(exp.getTime() + Days*24*60*60*1000);
		//alert("全"+str);
		document.cookie = admin + "=" + escape (aValue) + ";expires=" + exp.toGMTString() + ";path=/";
		document.cookie = "expires=" + exp.toGMTString() + ";expires=" + exp.toGMTString() + ";path=/";
	}
	
	function getCookie(cookieName){
		//获得Cookie文件中的信息
		var cookieMesaage = document.cookie;
		var cookieValue = "";
		//获得Cookie值
		if (cookieName=="admin"){
			cookieValue = returnCookieValue(cookieMesaage,"admin")
			return cookieValue;
		}
		//else if (cookieName=="pwd"){
		//	cookieValue = returnCookieValue(cookieMesaage,"pwd")
		//	return cookieValue;	
		//}
	}

	
	
	function returnCookieValue(cookieValue,cookieName){
		//设置返回值
		var returnCookieValue = "";
		//查找Cookie名在整个Cookie文件内容中的位置
		var cookieNameIndex = cookieValue.indexOf(cookieName+"=");
		//如果在Cookie文件内容中找到Cookie名，则进一步查找该Cookie的值
		if (cookieNameIndex!=-1){
			//查找Cookie值在Cookie文件内容中的位置。该位置为Cookie名的位置加上Cookie名的长度，再加上“=”号的长度，即1
			var cookieValueBeginIndex = cookieNameIndex +cookieName.length + 1;
			//查找Cookie值在Cookie文件内容中的位置。
			//该位置为从Cookie名的位置开始到第一个“;”为止
			var cookieVlaueEndIndex = cookieValue.indexOf(";",cookieValueBeginIndex);
			
			//如果从Cookie名的位置开始到整个Cookie文件内容的最后都没有找到“;”，那么Cookie值的结束位置就是整个Cookie内容的最后
			
			if (cookieVlaueEndIndex==-1){
			    cookieVlaueEndIndex = cookieValue.length;
			}
			//通过Cookie值的开始位置和结束位置获得Cookie值
			
			returnCookieValue = cookieValue.substring(cookieValueBeginIndex, cookieVlaueEndIndex);
		}
		//返回Cookie值
		return returnCookieValue;
	}
	
	function removeCookie() {
		$.prompt("  是否确认清除管理后台登录信息？",{
			buttons:{是:true,否:false},
			alertType:'msg',
			submit:function(v,m,f){
				if(v){
					cleans();
					jQuery.ImpromptuClose();
				}else{
					jQuery.ImpromptuClose();
				}
			}});
		
	}

/**
 * 页面加载完执行方法
 */
$("document").ready(function (){
	$(".showDiv").mousemove(function(e){
		$("#showMsg").css("top",e.clientY);
	    $("#showMsg").css("left",e.clientX+2)
	    $("#showMsg").show();
	});
       
	$(".showDiv").mouseout(function(e){
		$("#showMsg").hide();
	});
	// 提交方法
	$("#login").click(function(){
		if(vpic()){
			$("#webAdminForm").submit();
		}
	});
});
</script>
		<style>

</style>
	</head>
	<body onload="reloadcode();">
		<form id="webAdminForm" name="webAdminForm" method="post"
			action="${base}/public/common/login/loginAction.${actionExt}">
			<div class="top">
				<img src="${base}/images/1_03.gif" />
			</div>
			<div class="conter">
				<div class="conter_bj">
					<div class="login">
						<div class="login_nr">
							<table width="350" border="0">
								<tr>
									<td width="70" height="25" class="nr">
										<div align="center">
											用户名:
										</div>
									</td>
									<td height="25" colspan="4">

										<div align="left">
											<input type="text" name="admin" id="admin" onblur="vadmin();"
												size="22"
												style="height:18px; width:150px; border:solid 1px #cadcb2; font-size:12px; color:#81b432;" />
										</div>
									</td>
									<td width="119" height="25" rowspan="5" align="right"
										valign="top">
										<a href="#" id="login"><img src="${base}/images/anniu.png"
												width="106" height="110" border="0" style="margin-top:-7px;" />
										</a>
									</td>
								</tr>
								<tr>
									<td height="25" class="nr">
										<div align="center">
											密&nbsp;&nbsp;码:
										</div>
									</td>
									<td height="25" colspan="4">
										<div align="left">
											<input type="password" name="password" id="password"
												onclick="this.select();" maxlength="16" size="22"
												onblur="vpass();"
												style="height:18px; width:150px; border:solid 1px #cadcb2; font-size:12px; color:#81b432;" />
										</div>
									</td>
								</tr>
								<tr>
									<td height="15">
										&nbsp;
									</td>
									<td height="15" colspan="4">
										<div align="left" id="err_msg_div" style="display:none;">
											<img src="${base}/images/anniu1.png" width="14" height="15"
												style=" margin-bottom:-4px;" />
											<span class="STYLE1" id="err_msg">密码有误！</span>
										</div>
									</td>
								</tr>
								<tr>
									<td height="25" class="nr">
										<div align="center">
											验证码:
										</div>
									</td>
									<td height="25" colspan="4">
										<div align="left">
											<input type="text" name="piccode" id="piccode" name="piccode"
												size="6" maxlength="4" autocomplete="off" onkeydown="keyDown(event);"
												style="height:18px; width:85px; border:solid 1px #cadcb2; font-size:12px; color:#81b432;" />
											<a href="#"> <img id="verCode" name="verCode" width="61"
													height="20" border="0"
													style="position: relative; margin-bottom:-4px;"
													onclick="refreshcode();" /> </a>

										</div>
									</td>
								</tr>
								<tr>
									<td height="25" colspan="5" align="center" class="nr001">
										<input type="checkbox" name="myCheck" id="myCheck"
											checked="checked" />&nbsp;保存登录信息
										&nbsp;&nbsp;&nbsp;&nbsp;
										<a href="#" onclick="removeCookie();">清除登录信息</a>
									</td>
								</tr>
							</table>

						</div>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
