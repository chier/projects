<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>${requestScope.platformTitle}--<fmt:message key="error" /></title>
		<%@ include file="/commons/meta.jsp" %>
	</head>
	<body>
		<h3>
			<fmt:message key="invalidToken" />
		</h3>
		<p>
			<a href="#" onclick="window.history.back();"><fmt:message key="back" /></a>
		</p>
		<br>
	</body>
</html>
