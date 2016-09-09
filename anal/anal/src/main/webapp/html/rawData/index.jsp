<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>
		<style>  
		  body   {  
		    -moz-user-select:none;  
		  }  
		  </style>
		  <script language="javascript">
			var isShow=true;
			function changeRawDataLeft(){
				var leftTab = document.getElementById("leftRowDataTab");
				var leftImg = document.getElementById("leftRowDataImg");
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
	<body>
		<div style="width:100%;height:100%">
			<div style="width:100%;height:10px;"></div>
			<table cellpadding="0" cellspacing="0" border="0"
				style="width:100%;height:96%;margin-bottom:10px;" >
				<tr>
					<td width="210" id="leftRowDataTab" style="vertical-align: top;padding-left:3px;">
						<iframe name="left" id="left" frameborder="0" scrolling="auto" style="border:1px solid #cedcdf;"
							width="100%" height="100%"
							src="${base}/manage/rawdata/rawDataAction!treeIndex.${actionExt}"></iframe>
					</td>

					<td width="8">
					</td>
					<td id="contentTab" style="vertical-align:top;padding-top: 1px;">
						<iframe name="rawDataRight" id="rawDataRight" scrolling=auto style="border:1px solid #cedcdf;"
							src="${base}/manage/rawdata/rawDataAction!welcomePage.${actionExt}"
							frameborder="0" width="99%" height="100%"></iframe>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
