<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title></title>
    <%@ include file="/commons/meta.jsp" %>
    <script src="${base}/js/tree/commontree/xtree.js"></script>
	<script src="${base}/js/tree/commontree/xloadtree.js"></script>
	<script src="${base}/js/tree/commontree/xmlextras.js"></script>
	<link href="${base }/js/tree/commontree/xtree.css" rel="stylesheet" type="text/css" media="all" />
	<style type="text/css">
	body{background:#e5e8ed;}
	#main{padding:0 2px 0 2px}
	</style>
  </head>
<BODY>
<div id="main">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" >
  <tr height="100%"> 
    <td valign="top" width="8px">
    </td>
    <td valign="top">
				<script language="JavaScript">
					function getid(action) { 			
					 	return "";
			  		}
			  	
			  	
			  	webFXTreeConfig.rootIcon		=  base + '/js/tree/checkboxtree/images/folder.png';
				webFXTreeConfig.openRootIcon	=  base + '/js/tree/checkboxtree/images/folder-open.png';
				webFXTreeConfig.folderIcon		=  base + '/js/tree/checkboxtree/images/folder.png';
				webFXTreeConfig.openFolderIcon	=  base + '/js/tree/checkboxtree/images/folder-open.png';	
			  	
			  	//sText,starget, sXmlSrc, sAction, sBehavior, sIcon, sOpenIcon	
			  		
			  	document.write("<br>") ;
			  		webFXTreeConfig.isopen = true;
			  		<c:forEach items="${rootGroups}" var="root">
						if(document.getElementById){
						/**
						 * flag当点击的节点是根节点时，是否需要iframe重定向的标记
						 * 1重定向
						 * 0不重定向 
						 */
						<c:if test="${root.flag == 1}">
					        var tree = new WebFXLoadTree(
					        '${root.modelName}',
					        'right',
					        '${base}/manage/common/model/listModelTree.${actionExt}?groupId=${root.identifier}&type=parent&timestamp='+Math.random(),
					        '${base}${root.actionUrl}',
					        ''
					        );
				        </c:if>
						//如果是根节点
						<c:if test="${root.flag != 1}">
							var img = "";
							if(null!='${root.imageUrl}' && "#"!='${root.imageUrl}'){
								img = '${base}${root.imageUrl}';
							}
							var tree = new WebFXLoadTree(
							'${root.modelName}',
							'right',
							//加载子节点的url
							'${base}/manage/common/model/listModelTree.${actionExt}?groupId=${root.identifier}&type=parent&timestamp='+Math.random(),
							'${base}${root.actionUrl}',
							''
							);
						</c:if>
				        document.write(tree);
				    }
					</c:forEach>
				</script>
    	</td>
  </tr>
</table>
</div>
</body>
</html>