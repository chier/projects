<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>工作流列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="${base }/css/main.css">
		<link rel="stylesheet" type="text/css" href="${base }/css/3select.css">
	</head>

	<body>
		<div class="container">
			<div class="sidebar1">
				<ul class="Per_sel_ul">
					<c:forEach var="workflow" items="${workflowList}">
						<li>
							<label class="option">
								<a target="right"
									href="${base}/manage/workflow/workflowAction!findByNode.${actionExt}?wid=${workflow.id }">
									${workflow.name} </a>
							</label>
						</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</body>
</html>
