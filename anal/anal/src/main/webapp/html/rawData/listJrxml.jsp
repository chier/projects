<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>原始数据列表</title>
		<title></title>
		<%@ include file="/commons/meta.jsp"%>
		<script src="${base}/js/tree/rawdatacommontree/xtree.js"></script>
		<script src="${base}/js/tree/rawdatacommontree/xloadtree.js"></script>
		<script src="${base}/js/tree/rawdatacommontree/xmlextras.js"></script>
		<link href="${base }/js/tree/rawdatacommontree/xtree.css"
			rel="stylesheet" type="text/css" media="all" />
		<style type="text/css">
			.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
			.vip_xinxilm{
				width:169px;
				height:26px;
				background:url(${base}/images/index_62.png) no-repeat;
				padding:5px 0px 0px 40px;
			}
			.vip_xinxilm h1{
				font-size:16px;
				color:#FFFFFF;
				font-family: "微软雅黑";
				margin:0px;
			}
		</style>
	</head>
	<body style="background-color:#e8edf0;">
		<div id="main" style="border-left:1px solid #617775;height:100%;">
			<table width="100%" height="90%" border="0" cellpadding="0"
				cellspacing="0" style="padding-left:0;">
				<tr>
					<td style="height:31px;width:100%;">
						<div class="vip_xinxi">
							<div class="vip_xinxilm">
								<h1>
									原始数据
								</h1>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td>
						<table width="100%" height="100%" border="0" cellpadding="0"
							cellspacing="0" style="padding-left:5px;">
							<tr>
								<td valign="top" width="8px">
								</td>
								<td valign="top">
									<script language="JavaScript">
								function getid(action) { 			
								 	return "";
						  		}
								/*
						  	webFXTreeConfig.rootIcon		=  base + '/images/rawdata/images/folder.png';
							webFXTreeConfig.openRootIcon	=  base + '/images/rawdata/images/folder-open.png';
							webFXTreeConfig.folderIcon		=  base + '/images/rawdata/images/folder.png';
							webFXTreeConfig.openFolderIcon	=  base + '/images/rawdata/images/folder-open.png';	
						  	
						  	console.info(webFXTreeConfig.openFolderIcon);
						  	*/
								if(document.getElementById){
								    var tree = new WebFXLoadTree('${requestScope.rootGroup.modelName}',
								    			'rawDataRight',
								    			'${rootGroup.actionUrl}',
								    			'${base}/manage/rawdata/rawDataAction!welcomePage.${actionExt}?treedate=' + new Date());
								    document.write(tree);
								}
							</script>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
