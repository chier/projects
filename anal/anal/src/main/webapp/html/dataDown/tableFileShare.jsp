<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>文件共享 - 列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${base}/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<%@ include file="/js/calendar/calendar2/calendar.jsp"%>
		<!-- 自动化边框 js -->
		<script language="javascript">
				

		</script>
	</head>
	<body>
		<div class="content">
			<form name="dataDownForm" id="dataDownForm"
				action="${base}/manage/datadown/dataDownAction!tableListFileShare.${actionExt}"
				method="post">
				<input type="hidden" name="fsId" id="fsId" value="${fsId}" />
				<input type="hidden" name="title" id="title" value="${title}" />

				<input type="hidden" name="curPage" id="curPage" value="${curPage }" />
				<input type="hidden" name="delId" id="delId" value="" />
				<!-- 表头信息 
				<div class="head" style="height:30px;width:100%;">
					<h3>${title}</h3>
				</div>
				-->
				<!-- 查询条件信息 -->
				<div class="search" style="height:70px;width:100%;background-color:rgb(232, 245, 255);">
					<div style="height:30px;width:100%;">
						文件名称：
						<input type="text" name="fileName" id="fileName"
							value="${fileName}" style="margin-right:100px;" />

						文件类型：
						<select name="fileExt" id="fileExt">
							<option value="">
								--请选择--
							</option>
							<c:forEach var="ext" items="${suffixList }">
								<option value="${ext}"
									<c:if test="${fileExt eq ext}">selected</c:if>>
									${ext}
								</option>
							</c:forEach>
						</select>
					</div>
					<div style="height:30px;width:100%;margin-top:5px;">
						上传时间：
						<input type="text" id="operate_time" name='operate_time'
							id='operate_time' style="color: #949494;"
							onclick="return showCalendar('operate_time', '%Y-%m-%d %H:%M', '24',true);"
							value='${operate_time}' onkeydown="keyDownSubmit(event)"
							readonly="readonly" />
						至:
						<input type="text" id="end_time" name='end_time' id='end_time'
							style="color: #949494;"
							onclick="return showCalendar('end_time', '%Y-%m-%d %H:%M', '24',true);"
							value='${end_time}' onkeydown="keyDownSubmit(event)"
							readonly="readonly" />

						<input type="submit" name="subFile" id="subFile" value="查询" class="bot1other"
							style="margin-left:50px;" />
						<c:if test="${userInfo.roleLevel == 0}">
							<input type="submit" name="subFile" id="subFile" value="上传文件" class="bot1other"
								onclick="uploadFile();" />
						</c:if>
					</div>
				</div>
				<!-- 列表信息 -->
				<div class="tableList" style="height:300px;width:100%;">
					<table cellpadding="2" cellspacing="1" border="0" class="tables">
						<thead>
							<tr>
								<td>
									文件名称
								</td>
								<td>
									文件类型
								</td>
								<td>
									文件大小
								</td>
								<td>
									上传时间
								</td>
								<td>
									文件描述
								</td>
								<td>
									上传人
								</td>
								<td>
									操作
								</td>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty infoList}">
								<c:forEach var="info" items="${infoList}">
									<tr>
										<td>
											${info.node_name}
										</td>
										<td>
											${info.suffix}
										</td>
										<td>
											<fmt:formatNumber type="number" value="${info.size/1024/1024}"
												maxFractionDigits="2" />M
										</td>
										<td>
											<fmt:formatDate pattern="yyyy-MM-dd"
												value="${info.createTime }" />
										</td>
										<td>
											${info.desc }
										</td>
										<td>
											${info.adminName}
										</td>
										<td>
											<a href="#"
													onclick="javascript:downFile(${info.id});return false;"
													title="下载">下载</a>
											<c:if test="${userInfo.roleLevel == 0}">
												<a href="#"
													onclick="javascript:removeFile(${info.id});return false;"
													title="删除">删除</a>
												<!--  <img style="width:10px;height:10px;" src="${base}/images/delete.png" />  -->
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty infoList}">
								<tr>
									<td colspan="7" align="center">
										暂无记录
									</td>
								</tr>
							</c:if>
						</tbody>
						<%@ include file="/commons/page.jsp"%>
					</table>
				</div>
			</form>
		</div>
	</body>
</html>

<!-- 业务js -->
<script language="javascript">
	
	function uploadFile(){
		$("#dataDownForm").attr("action","${base}/manage/datadown/dataDownAction!toAddFileShare.${actionExt}");
		$("#dataDownForm").submit();
	}
	
	function downFile(str){
		window.open("${base}/manage/datadown/dataDownAction!download.${actionExt}?fsId=" + str,"newWindow","top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
	}
	
	function removeFile(id){
		top.$.prompt("确定要删除文件吗？", {
			buttons : {
				是 : true,
				否 : false
			},
			alertType : 'ask',
			submit : function(v, m, k) {
				if (v) {
					$("#delId").val(id);
					document.getElementById("dataDownForm").submit();
						top.jQuery.ImpromptuClose();
				} else {
					top.jQuery.ImpromptuClose();
				}
			}
		});
		
	}
	function goPage(pageNum) {
		$("#curPage").val(pageNum);
		document.getElementById("dataDownForm").submit();
	}
	
		function f_frameStyleResize(targObj){
					var targWin = targObj.parent.document.all[targObj.name];
					if(targWin != null) {
						targWin.style.pixelHeight = 447;
					}
			
			}
			
			function f_iframeResize(){
					bLoadComplete = true; 
					f_frameStyleResize(self);
			
			}
			var bLoadComplete = false;
			window.onload = f_iframeResize;
	
</script>
