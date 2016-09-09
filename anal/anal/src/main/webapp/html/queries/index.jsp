<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html>
	<head>
		<title>即席查询</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>

		<script language="javascript">
			var isShow=true;
			function changeQueriesLeft(){
				var leftTab = document.getElementById("leftQueriesTab");
				var leftImg = document.getElementById("leftQueriesImg");
				if(isShow == true){
					leftTab.style.display = "none";
					isShow=false;
					leftImg.src="${base}/images/backimg/menu_switch_expand.gif";
				}else{
					leftTab.style.display = "";
					leftTab.style.width="210";
					isShow=true;
					leftImg.src="${base}/images/backimg/menu_switch_close.gif";
				}
			}
			</script>
	</head>
	<body >
		<div style="width:100%;height:99%">
			<div style="width:100%;height:10px;"></div>
			<table cellpadding="0" cellspacing="0" border="0"
				style="width:100%;height:96%;margin-bottom:10px;" >
				<tr>
					<td width="210" id="leftQueriesTab" style="vertical-align: top;padding-left:3px;">
						<iframe name="queriesLeft" id="queriesLeft" frameborder="0" scrolling="auto" style="border:1px solid #cedcdf;"
							width="100%" height="100%"
							src="${base}/manage/queries/queriesAction!treeIndex.${actionExt}"></iframe>
					</td>
					<td width="8">
					</td>
					<td id="contentTab" style="vertical-align:top;padding-top: 1px;">
						<iframe name="queriesRight" id="queriesRight" scrolling=no  style="border:1px solid #cedcdf;"
							src="${base}/manage/queries/queriesAction!welcomePage.${actionExt}"
							frameborder="0" width="99%" height="100%"></iframe>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
