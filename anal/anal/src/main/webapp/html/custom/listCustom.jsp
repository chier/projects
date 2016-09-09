<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html>
	<head>
		<title>定制分析</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>
<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj){

var targWin = targObj.parent.document.all[targObj.name];

if(targWin != null) {

var HeightValue = targObj.document.body.scrollHeight+5

if(HeightValue < 600){HeightValue = 600} //不小于600

targWin.style.pixelHeight = HeightValue;

}

}

function f_iframeResize(){

bLoadComplete = true; f_frameStyleResize(self);

}

var bLoadComplete = false;

window.onload = f_iframeResize;

</SCRIPT>
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
		<div style="width:100%;height:100%">
			<table cellpadding="0" cellspacing="0" border="0" style="width:100%;height:100%" align=top>
				<tr>
					<td id="contentTab1" style="vertical-align: top;padding-top: 1;">
						<iframe name="customRight1" id="customRight1" scrolling=auto
							src="${base}/manage/custom/customAction!executeTable.${actionExt}?id=${id}&customTitle=${customTitle}"
							frameborder="0" width="100%" height="100%"></iframe>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
