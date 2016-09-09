<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="fckeditor" prefix="fck"%>
<%@ taglib uri="/struts-tags" prefix="ss"%>
<%@ page import="com.cmcc.common.Global"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="serverName" value="${pageContext.request.serverName}" />
<c:set var="serverPort" value="${pageContext.request.serverPort}" />
<c:set var="scheme" value="${pageContext.request.scheme}" />
<c:choose>
	<c:when test="${serverPort eq '80'}"> 
	     <c:choose>
	     <c:when test="${ctx eq '/'}"> 
		 <c:set var="base" value="${scheme}://${serverName}" />
		 </c:when>
		 <c:otherwise>
		 <c:set var="base" value="${scheme}://${serverName}${ctx}" />
		 </c:otherwise>
		 </c:choose>
	</c:when>
	<c:otherwise>
	     <c:choose>
	     <c:when test="${ctx eq '/'}"> 
		 <c:set var="base" value="${scheme}://${serverName}:${serverPort}" />
		 </c:when>
		 <c:otherwise>
		 <c:set var="base" value="${scheme}://${serverName}:${serverPort}${ctx}" />
		 </c:otherwise>
		 </c:choose>
	</c:otherwise>
</c:choose>
<c:set var="actionExt" value="<%=Global.ACTION_EXT%>" />
<Script language="JavaScript">
	var base = "${base}";
	var actionExt ="${actionExt}";
	var contentpath="${contentpath}";
</Script>




