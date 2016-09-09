<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>

<!--部门树型结构页面-->
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<%@ include file="/commons/meta.jsp"%>
		<script src="${base}/js/tree/roletree/xtree.js"></script>
		<script src="${base}/js/tree/roletree/xloadtree.js"></script>
		<script src="${base}/js/tree/roletree/xmlextras.js"></script>
		<link href="${base }/js/tree/commontree/xtree.css" rel="stylesheet"
			type="text/css" media="all" />
		<style type="text/css">
body {
	background: #f7f8fa;
}

a {
	font: 12px Arial, Helvetica, sans-serif;
	white-space: pre;
}

#main {
	padding: 15px 25px 0 15px;
}

.title {
	height: 23px;
	overflow: hidden;
	background: url(../../../images/bg_title.gif) right 0 no-repeat;
	margin-bottom: 8px;
}

.title h4 {
	margin: 0;
	font-size: 13px;
	background: url(../../../images/bg_title.gif) 0 -23px no-repeat;
	height: 23px;
	line-height: 23px;
	color: #fff;
	padding-left: 20px;
}

span {
	font: 12px Arial, Helvetica, sans-serif;
	white-space: pre;
}

.webfx-tree-item {
	white-space: pre;
}
</style>
		<script type="text/javascript">
	function setColor(obj){
	$("a").each(function(){
	if(this.id==obj.getAttribute("id")){
	$(this).css("background","#ffe086");
	}else{
	$(this).css("background","none");
	}
	});
	}
	$("document").ready(function(){
	var treeStr = "${requestScope.treeStr}";
	document.getElementById("treeContainer").innerHTML=treeStr;
	
	});
	</script>
	</head>
	<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<div id="main">
			<div class="title">
				<h4>
					角色树
				</h4>
			</div>
			<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
				<TBODY>
					<TR height="100%">
						<TD id=tds vAlign=top width=8>
							<div id="treeContainer" name="treeContainer">
						</td>
					</TR>
				</TBODY>
			</TABLE>
		</div>
		</div>
	</body>
</html>