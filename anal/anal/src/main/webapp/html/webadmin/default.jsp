<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<%@ include file="/commons/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<c:choose>
			<c:when test="${userSessionInfo.version eq 2}">
		<link href="${base }/css/ulogin2.css" rel="stylesheet" type="text/css"
			media="all" />
			</c:when>
			<c:otherwise>
		<link href="${base }/css/ulogin.css" rel="stylesheet" type="text/css"
			media="all" />
			</c:otherwise>
		</c:choose>
		
		<style>
.yellow {
	font-family: 微软雅黑,Arial;
	font-size: 18px;
	font-style: italic;
	float: right;
	color: #ec6900;
	position:absolute;
	font-weight:bold;
	right:520px!Important;
    bottom: 340px!Important;
    right:245px;
    bottom: 340px;
}
</style>
	</head>

	<body>
	<div class="yellow">
			${requestScope.version}
</div>
	</body>
</html>