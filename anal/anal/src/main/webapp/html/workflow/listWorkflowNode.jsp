<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>
<html>
	<head>
		<title>工作流列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="${base }/css/main.css">
		<script type="text/javascript">
			function clickBunNode(nid){
				$("#nodeShow div").each(function(){
					$(this).hide();
				});
				$("#nodepath_"+nid).show();
			}
		</script>
	</head>
	<body>
		<div class="main">
			<div class="title">
				<h4>
					节点信息
				</h4>
			</div>
			<div>
				<table width="100%" border="0" bgcolor="#E5E8ED" cellspacing="0"
					cellpadding="0">
					<tr>
						<td>
							<span class="table-navdiv1"> <c:forEach var="node"
									items="${workflowNodeList}">
									<input class="bot1other" id="bunNode_${node.nid}"
										onclick="clickBunNode(${node.nid});" type="button" name="name"
										value="${node.showName}" />

								</c:forEach> </span>
						</td>
					</tr>
				</table>
			</div>
			<div id="nodeShow" style="margin:5px 0;">
				<c:forEach var="node" items="${workflowNodeList}">
					<div id="nodepath_${node.nid}" name="nodePathName"
						style="display:none;width:100%;">
						<c:if test="${not empty node.nodePath }">
							<img height="88%" src="${base}/${node.nodePath}">
						</c:if>
						<c:if test="${not empty node.nodeSetting}">
							<iframe name="showDB" id="showDB" scrolling="auto"
								frameborder="0" width="100%" height="89%"
								src="${base }/manage/workflow/workflowAction!selectDB.${actionExt}?nid=${node.nid}"></iframe>
						</c:if>
					</div>
				</c:forEach>
			</div>
	</body>
</html>
