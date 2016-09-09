<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<%@ include file="/commons/meta.jsp"%>
		<link href="${base }/css/css130522.css" rel="stylesheet"
			type="text/css" />
		<SCRIPT LANGUAGE="JavaScript">

			function f_frameStyleResize(targObj){
					var targWin = targObj.parent.document.all[targObj.name];
					if(targWin != null) {
						targWin.style.pixelHeight = $(document).find("body").height() + 3;
						
					}
			}
			function f_iframeResize(){
					bLoadComplete = true; 
					f_frameStyleResize(self);
			
			}
			var bLoadComplete = false;
			window.onload = f_iframeResize;
			</SCRIPT>
	</head>
	<body style="background-image:none;background-color:#fff;height:100%">
		<!--导航结束-->
		<div  style="background-image:url(${base}/images/ab.png);background-repeat: repeat-y repeat-x;height:100%;width:100%;position:absolute;"></div>
		
		<div class="banner" style='position:absolute;bottom:0'>
			<img src="${base}/images/index_23.png" width="100%" height="409" />
		</div>
		<div class="clear"></div>
	</body>
</html>
