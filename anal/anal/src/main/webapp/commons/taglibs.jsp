<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="fckeditor" prefix="fck"%>
<%@ taglib uri="/struts-tags" prefix="ss"%>
<%@ page import="com.cmcc.common.Global"%>

<c:choose>
	<c:when test="${fn:length(sessionScope.language) > 0}">
		<fmt:setBundle basename="${sessionScope.language}" />
	</c:when>
	<c:otherwise>
		<fmt:setBundle basename="messages_zh_CN" />
	</c:otherwise>
</c:choose>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:choose>
	<c:when test="${pageContext.request.serverPort eq '80'}">
		<c:set var="base"
			value="${pageContext.request.scheme}://${pageContext.request.serverName}${pageContext.request.contextPath}" />
	</c:when>
	<c:otherwise>
		<c:set var="base"
			value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />
	</c:otherwise>
</c:choose>
<c:set var="actionExt" value="<%=Global.ACTION_EXT%>" />
<c:set var="contentpath" value="${pageContext.request.contextPath}" />
<c:set var="pass_time" value="<%=Global.PASSWORD_TIME%>" />
<c:set var="recently_password" value="<%=Global.RECENTLY_PASSWORD%>" />


<Script language="JavaScript">
	var base = "${base}";
	var actionExt ="${actionExt}";
	var contentpath="${contentpath}";
</Script>
