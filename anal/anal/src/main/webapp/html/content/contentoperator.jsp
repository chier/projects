<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="${base }/js/check_all.js"></script>
		<title></title>
		<link href="${base}/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
	</head>
	<body leftmargin="0" topmargin="0" scroll="no"
		style="overflow-y: hidden">

		<div id="main">
			<div class="title">
				<h4>
					<fmt:message
						key="efetionmanage.framework.contentManager.allColumns" />
				</h4>
			</div>

			<table cellpadding="2" cellspacing="1" border="0" class="tables">
				<thead>
					<tr>
						<th width="30%" nowsap>
							<fmt:message
								key="efetionmanage.framework.contentManager.columnName" />
						</th>
						<th width="60%" nowsap>
							<fmt:message
								key="efetionmanage.framework.contentManager.columnDesc" />
						</th>
						<th width="10%" nowsap>
							<fmt:message key="efetionmanage.common.edit" />
						</th>
					</tr>
				</thead>
                <c:forEach items="${catalogList}" var="catalog" varStatus="status">
                 <tr>
						<td align="center">
                             <c:out value="${catalog.title}" escapeXml="true"/>
						</td>
						<td >
							<c:out value="${catalog.desc}" escapeXml="true"/>
						</td>
						<td  align="center">
							<a title="<fmt:message key='efetionmanage.common.modify'/>"
								href="${base }/manage/contentmanage/content/contentInfoOperator!getCatalog.${actionExt }?aiid=${catalog.identifier }"><img
									src="${base}/images/ico_edit.gif" /> </a>
						</td>
					</tr>
                </c:forEach>
			</table>
		</div>
	</body>
</html>
