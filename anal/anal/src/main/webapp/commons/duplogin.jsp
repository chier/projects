<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>${requestScope.platformTitle}</title>
		<%@ include file="/commons/meta.jsp"%>
				<link href="${base }/css/style.css" rel="stylesheet" type="text/css"
			media="all" />
	</head>
<script language="javascript"> 
<!-- 
function CloseWin(){ 
var ua=navigator.userAgent 
var ie=navigator.appName=="Microsoft Internet Explorer"?true:false 
if(ie){
    var IEversion=parseFloat(ua.substring(ua.indexOf("MSIE ")+5,ua.indexOf(";",ua.indexOf("MSIE ")))) 
 if(IEversion< 5.5){
    var str  = '<object id=noTipClose classid="clsid:ADB880A6-D8FF-11CF-9377-00AA003B7A11">'
    	str +='<param name="Command" value="Close"></object>';
    	document.body.insertAdjacentHTML("beforeEnd", str); 
    	document.all.noTipClose.Click(); 
    } 
    else{ 
    	//window.opener =null; 
    	window.close(); 
    } 
}else{ 
	window.close(); 
} 
} 
//--> 
</script>
<body>
<div id="wrap">
			<div class="top">
				<div class="logo">
					&nbsp;
				</div>
			</div>

				<form id="webAdminForm" name="webAdminForm" action="${base}/public/common/login/loginAction!kickOut.${actionExt}" method="post">
			<table class="warp-login">
					<ss:token name="login.token"/>
					<input type="hidden" name="admin" id="admin" value="${loginId}"/>
					<input type="hidden" name="identifier" id="identifier" value="${kik}"/>
					<input type="hidden" name="password" id="password" maxlength="16" value="${password}"/>
					<input type="hidden" name="kick" id="kick" value="1"/>
				<tr>
					<td>
						<div class="login">
							<ul>
								<li>
										<fmt:message key="loginId"/>:${loginId}
								</li>
								<li>
									<fmt:message key="duplicateLogin"/>
								</li>
								<li>&nbsp;</li>
								<li>&nbsp;</li>
								<li style="padding-left: 60px;">
								<input type="submit" name="Submit" value="<fmt:message key="kick"/>"
								 class="button" />&nbsp;&nbsp;&nbsp;
									<input type="button" name="Submit2" value="<fmt:message key="close"/>" onclick="javascript:CloseWin();" class="button" />
								</li>
							</ul> 
						</div>
					</td>
				</tr>
			</table>
				</form>
		</div>
		<div id="footer">
			<div class="copyright">
				<fmt:message key="efetion.webpage.copyright.text"/>
			</div>
		</div>
</body>
</html>
