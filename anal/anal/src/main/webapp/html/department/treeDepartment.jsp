<!DOCTYPE html PUBLIC "-///www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!--部门树型结构页面-->
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
	<head>
		<title></title>
		<%@ include file="/commons/meta.jsp"%>
		<script src="${base}/js/tree/commontree/xtree.js"></script>
		<script src="${base}/js/tree/commontree/xloadtree.js"></script>
		<script src="${base}/js/tree/commontree/xmlextras.js"></script>
		<link href="${base }/js/tree/commontree/xtree.css" rel="stylesheet"
			type="text/css" media="all" />
		<style type="text/css">
body {
	background: #f7f8fa;
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
span{
	font: 12px Arial,Helvetica,sans-serif;
	white-space: pre;
}
	.webfx-tree-item{
		white-space: pre;
	}
</style>
	</head>
	<BODY leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<div id="main">
			<div class="title">
				<h4>
					<fmt:message
						key="efetionmanage.framework.department.all_departments_tree" />
				</h4>
			</div>
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr height="100%">
					<td valign="top" width="8px">
						<script language="JavaScript">
			function getid(action) { 			
			 	return "";
	  		}
			if(document.getElementById){
				 var tree;
				if(${kind eq "admin"}){
					tree = new WebFXLoadTree('${requestScope.rootGroup.name}','right1','${base}/manage/common/department/listDepartmentTree.${actionExt}?groupId=${requestScope.rootGroup.deptId}&type=${requestScope.type}&kind=${requestScope.kind}&treedate=' + new Date(),'${base}/manage/safemanage/webadmin/listWebAdminAction.${actionExt}?groupId=${requestScope.rootGroup.identifier}&type=${requestScope.type}');
				}else{
					tree = new WebFXLoadTree('${requestScope.rootGroup.name}','right1','${base}/manage/common/department/listDepartmentTree.${actionExt}?groupId=${requestScope.rootGroup.deptId}&type=${requestScope.type}&kind=${requestScope.kind}&treedate=' + new Date(),'${base}/manage/common/department/listDepartments.${actionExt}?groupId=${requestScope.rootGroup.identifier}&type=${requestScope.type}');				
				}
	             
	           
	             document.write(tree);
	         }
		</script>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>