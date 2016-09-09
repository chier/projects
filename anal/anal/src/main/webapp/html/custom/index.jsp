<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<html>
	<head>
		<title>定制分析</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>

		<script language="javascript">
			var isShow=true;
			function changeloapLeft(){
				var leftTab = document.getElementById("leftcustomTab");
				var leftImg = document.getElementById("leftloapImg");
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
			<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj){

		var targWin = targObj.parent.document.all[targObj.name];
		
		if(targWin != null) {
		
			var HeightValue = targObj.document.body.scrollHeight
		
			if(HeightValue < 400){HeightValue = 400} //不小于600
			//alert(HeightValue);
			targWin.style.pixelHeight = HeightValue;
		
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
		<div style="width:100%;height:98%;">
			<div style="width:100%;height:10px;"></div>
			<table cellpadding="0" cellspacing="0" border="0"
				style="width:100%;height:97%;margin-bottom:10px;" >
				<tr>
					<td width="210" id="leftcustomTab" style="vertical-align: top;padding-left:3px;">
						<iframe name="customLeft" id="customLeft" frameborder="0" scrolling="auto" style="border:1px solid #cedcdf;"
							width="100%" height="100%" src="${base}/manage/custom/customAction!treeIndex.${actionExt}"></iframe>
					</td>
					<td width="8">
					</td>
					<td id="contentTab" style="vertical-align: top;padding-top: 1;">
						<iframe name="customRight" id="customRight" scrolling="auto" style="border:1px solid #cedcdf;"
							src="${base}/manage/custom/customAction!welcomePage.${actionExt}"
							frameborder="0" width="99%" height="100%"></iframe>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
