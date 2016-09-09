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
				
				var buttomTab = document.getElementById("queriesRightBottom");
				if(isShow == true){
					leftTab.style.display = "none";
					isShow=false;
					leftImg.src="${base}/images/backimg/menu_right_open.gif";
					buttomTab.height="470";
				}else{
					leftTab.style.display = "";
					leftTab.style.width="300";
					isShow=true;
					leftImg.src="${base}/images/backimg/menu_right_close.gif";
					buttomTab.height="330";
				}
			}
			</script>
			<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj){

		var targWin = targObj.parent.document.all[targObj.name];
		
		var mainWin = targObj.parent.parent.document.all[targObj.parent.name];
		
		if(targWin != null) {
		
			var HeightValue = targObj.document.body.scrollHeight
			
			if(HeightValue < 500){HeightValue = 500} //不小于600
		
			targWin.style.pixelHeight = HeightValue - 10;
			if(mainWin !=null)
			{
				mainWin.style.pixelHeight = HeightValue+20;
			}
		}
		

}

function f_iframeResize(){

		bLoadComplete = true; f_frameStyleResize(self);

}

var bLoadComplete = false;

window.onload = f_iframeResize;

</SCRIPT>
	</head>
	<body>
		<div style="width:100%;height:78%">
			<table cellpadding="0" cellspacing="0" border="0" style="width:100%;">
				<tr id="leftFrameQueriesTab" height="300">
					<td style="vertical-align: top;padding-top: 1;">
						<iframe name="queriesLeftTop" id="queriesLeftTop" frameborder="0"
							scrolling="auto" width="100%" height="355"
							src="${base}/manage/queries/queriesAction!leftTopFrame.${actionExt}?tableCode=${tableCode}&tableName=${tableName}"></iframe>
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
					
					<td id="contentTab" style="vertical-align: top;padding-top: 1;height:330px;">
						<iframe name="queriesRightBottom" id="queriesRightBottom"  scrolling="yes"
							src="${base}/manage/queries/queriesAction.${actionExt}?tableCode=${tableCode}&tableName=${tableName}"
							frameborder="0" width="100%" height="320"></iframe>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
