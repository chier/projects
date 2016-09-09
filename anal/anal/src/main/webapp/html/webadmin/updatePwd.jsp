<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${requestScope.platformTitle}</title>
		<link href="${base }/css/style.css" rel="stylesheet" type="text/css"
			media="all" />
		<!--[if IE]>
<link href="${base }/css/fkie.css" rel="stylesheet" type="text/css" media="all" />
<![endif]-->
		<%@ include file="/commons/meta.jsp"%>
	
	<script language="Javascript">
	function reset() {
		document.getElementById("oldPassword").value="";
		document.getElementById("password").value="";
		document.getElementById("confirmPassword").value="";
		document.getElementById("oldPassword").focus();
	}
	function validatorLoginIdAndPassword(){
		if(!isalphanumber(document.getElementById("oldPassword").value) || trim(document.getElementById("oldPassword").value).length<6){
			document.getElementById("aa").innerHTML='<font color="red"><fmt:message key="efetionmanage.framework.login.validatePassword"/></font>';
			document.getElementById("oldPassword").value="";
			document.getElementById("oldPassword").focus();
			return false;
		}else if(!isalphanumber(document.getElementById("password").value) || trim(document.getElementById("password").value).length<6){
			document.getElementById("passwordmes").innerHTML="<font color='red'><fmt:message key="efetionmanage.framework.webAdmin.validatePassword"/></font>";
			document.getElementById("password").value="";
			document.getElementById("form").password.focus();
			return false;
		}else if(!isalphanumber(document.getElementById("confirmPassword").value) || trim(document.getElementById("confirmPassword").value).length<6){
			document.getElementById("confirmPasswordmes").innerHTML="<font color='red'><fmt:message key="efetionmanage.framework.login.validateConfirmPwd"/></font>";
			document.getElementById("confirmPassword").value="";
			document.getElementById("confirmPassword").focus();
			return false;
		}else if(trim(document.getElementById("password").value) !=trim(document.getElementById("confirmPassword").value)){
			document.getElementById("passwordmes").innerHTML="<font color='red'><fmt:message key="efetionmanage.framework.webAdmin.noSamePassword"/></font>";
			document.getElementById("password").value="";
			document.getElementById("confirmPassword").value="";
			document.getElementById("password").focus();
			return false;
		}else{
			document.getElementById("form").submit();
		}
	}
</script>
</head>
<body>

		<div id="wrap">
			<table class="warp-login">
						<form id="form" name="form" method="post"
			action="${base}/manage/common/password/modifyAdminPwdAction.${actionExt}">
					
				<tr>
					<td>
						<div class="login">
							<ul>
								<li>
									<label for="oldPassword">
										<fmt:message key="efetionmanage.framework.login.modifyPassword" />
										：
									</label>
									<input type="password" name="oldPassword" id="oldPassword"  maxlength="16" size="22"
										class="f-text" />
									<span id="aa"></span>
									<p class="prompt-msg">输入旧密码核对身份</p>
								</li>
								<li>
									<label for="password">
										<fmt:message key="efetionmanage.framework.login.password" />：
									</label>
									<input type="password" name="password" id="password"  maxlength="16" size="22"
										class="f-text" />
									<span id="passwordmes"></span>
									<p class="prompt-msg">填写管理员密码，使用英文字母加数字，长度6-16位</p>
								</li>
								<li>
									<label for="confirmPassword">
										<fmt:message key="efetionmanage.framework.login.confirmPassword" />
										：
									</label>
									<input type="password" name="confirmPassword" id="confirmPassword"  maxlength="16" 
										size="22" class="f-text" />
									<span id="confirmPasswordmes"></span>
									<p class="prompt-msg">再次填写，进行确认</p>
								</li>
								
								<li style="padding-left: 60px;">
									<input name="login" type="button" 
									id="login" value="<fmt:message key="efetionmanage.common.submit"/>"
									onclick="validatorLoginIdAndPassword();" class="button" />
									<input name="closed" type="button" id="closed"
									value="<fmt:message key="back"/>" onclick="javascript:window.history.back();" class="button" />
								</li>
							</ul>
						</div>
					</td>
				</tr>
				</form>
			</table>
		</div>
		
	</body>
	</html>