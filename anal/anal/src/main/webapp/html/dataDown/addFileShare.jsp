<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>文件共享 - 添加</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${base}/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<!-- 自动化边框 js -->
		<script language="javascript">
		</script>
	</head>
	<body>
		<div class="content">
			<ss:form
				enctype="multipart/form-data"
				action ="dataDownAction!addFileShare.portal"
				method="post">
				<input type="hidden" name="fsId" id="fsId" value="${fsId}" />
				<input type="hidden" name="title" id="title" value="${title}" />

				<input type="hidden" name="curPage" id="curPage" value="${curPage }" />
				<!-- 表头信息 -->
				<div class="head" style="height:30px;width:100%;"><h3></h3></div>
				<!-- 上传内容 start-->
				<div>
					<b>选择上传的内容:</b>
					<br />
					<ss:file name="upfile" label="File" />
					<br /><br />
					<b>上传内容的描述：</b>
					<br />
					<textarea name="fileDesc" id="fileDesc" cols="45" rows="5"></textarea>
					<br />
					<input type="submit" name="subFile" id="subFile" value="上传" />
					<!-- 上传内容 end -->
				</div>
			</ss:form>
		</div>
	</body>
</html>

<!-- 业务js -->
<script language="javascript">
	
	 
</script>
