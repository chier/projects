<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>

<html>
	<head>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<style type="text/css">
			margin: 0;;
			padding: 0;;
			text-align: left;
			font:none;
		</style>
	</head>
	<body>
		<table cellpadding="2" cellspacing="1" border="0" class="tables">
			<thead>
				<tr>
					<c:forEach var="col" items="${colList}">
						<th>
							${col}
						</th>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="st" items="${stList}">
					<tr>
						<c:forEach var="s" items="${st}">
							<td>
								${s}
							</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</body>
</html>
