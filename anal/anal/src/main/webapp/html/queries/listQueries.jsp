<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml"
	style="overflow-x:auto;overflow-y:auto;">
	<head>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${base}/js/main.js"></script>
<script language="Javascript">
	function goPage(pageNum) {
		if(isInteger(pageNum, "<fmt:message key="efetionmanage.common.not_integer"/>")){
			document.getElementById("listQueriesForm").action="${base}/manage/queries/queriesAction.${actionExt}?page=" + pageNum;
			document.getElementById("listQueriesForm").target="queriesRightBottom";
			document.getElementById("listQueriesForm").submit();
		}
	}
</script>
<script language="javascript">
			var isShow=true;
			function changeQueriesLeft(){
				var leftTab = document.getElementById("leftFrameQueriesTab");
				var leftImg = document.getElementById("leftFrameQueriesImg");
				
				var buttomTab = document.getElementById("queriesRightBottom");
				if(isShow == true){
					leftTab.style.display = "none";
					isShow=false;
					leftImg.src="${base}/images/backimg/menu_right_open.gif";
					buttomTab.height="490";
				}else{
					leftTab.style.display = "";
					leftTab.style.width="300";
					isShow=true;
					leftImg.src="${base}/images/backimg/menu_right_close.gif";
					buttomTab.height="330";
				}
			}
			</script>
			
	</head>
	<body>
		<div class="main">
			<c:if test="${not empty tableList}">
					<%@ include file="/commons/page.jsp"%>
			</c:if>
			<form name="listQueriesForm" id="listQueriesForm" action="" method="post" target="queriesRightBottom">
				<input type="hidden" value="${searchValue}" name="searchValue" id="searchValue" />
				<input type="hidden" name="tableCode" id="tableCode" value="${tableCode}" />
				<input type="hidden" name="page" id="_page" value="${pageInfo.page}"/>
				<table cellpadding="2" cellspacing="1" border="0" class="tables">
	
						<thead>
							<tr>
								<c:forEach var="vo" items="${itemList }">
									<td align="center" style="white-space:nowrap;">
										<nobar>${vo.itemName }<nobar>
									</td>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
							<c:if test="${not empty tableList}">
								<c:forEach items="${tableList}" var="table">
									<tr>
										<c:forEach var="item" items="${ table}">
											<td style="white-space:nowrap;">
												<nobar>${item}<nobar>
											</td>
										</c:forEach>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty tableList}">
								<tr>
									<td colspan="${itemCount}" align="center">
										暂无记录
									</td>
								</tr>
							</c:if>
						</tbody>
				</table>
			</form>
		</div>
	</body>
</html>
