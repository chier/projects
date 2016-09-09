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
				var leftTab = document.getElementById("leftFrameQueriesTab");
				var leftImg = document.getElementById("leftFrameQueriesImg");
				
				var buttomTab = document.getElementById("leftButtomLoadIframe2");
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
					buttomTab.height="150";
				}
			}
			
			function f_frameStyleResize(targObj){
			
					var targWin = targObj.parent.parent.document.all[targObj.parent.name];
					
					if(targWin != null) {
					
						var HeightValue = document.body.scrollHeight
					
						if(HeightValue < 400){HeightValue = 400} //不小于600
						
						var defah = 510+(window.screen.height - 768);
			
						targWin.style.pixelHeight = HeightValue + 70;
						
						
					
					}
			
			}
			
			function f_iframeResize(){
			
					bLoadComplete = true; f_frameStyleResize(self);
			
			}
			
			var bLoadComplete = false;
			
			window.onload = f_iframeResize;
			</script>
	</head>
	<body>
		<div style="width:100%;height:100%">
			<table cellpadding="0" cellspacing="0" border="0" style="width:100%;">
				<tr id="leftFrameQueriesTab" >
					<td>
						<iframe name="loapLeftTop" id="loapLeftTop" frameborder="0"
							scrolling="auto" width="100%" height="350"
							src="${base}/manage/loap/loapAction!leftTopFrame.${actionExt}?tableCode=${tableCode}&tableName=${tableName}"></iframe>
					</td>
				</tr>
				
				<tr>
					<td width="100%" align="center"
						style="background: url(${base }/images/backimg/bg_switch.gif) repeat-x;"
						valign="center">
						<img id="leftFrameQueriesImg"
							src="${base}/images/backimg/menu_right_close.gif" width="70"
							height="10" onclick="changeQueriesLeft();" style="cursor: hand;" />
					</td>
				</tr>
				
				
				<tr>
					<td id="contentTab">
						<iframe name="leftButtomLoadIframe2" id="leftButtomLoadIframe2" scrolling=auto
							frameborder="0" width="100%" height="150"></iframe>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
