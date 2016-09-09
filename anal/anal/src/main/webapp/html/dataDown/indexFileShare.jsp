<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>文件共享-首页</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${base}/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<style type="text/css">
			.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
			.ztree li span.button.ico_open {background-image:url(${base}/js/ztree/css/zTreeStyle/img/zTreeStandard.png)}
			.ztree li span.button.ico_close {background-image:url(${base}/js/ztree/css/zTreeStyle/img/zTreeStandard.png)}
			.ztree li span.button.ico_docu {background-image:url(${base}/js/ztree/css/zTreeStyle/img/zTreeStandard.png)}
		</style>
		<link rel="stylesheet" href="${base }/js/ztree/css/demo.css"
			type="text/css" />
		<link rel="stylesheet"
			href="${base }/js/ztree/css/zTreeStyle/zTreeStyle.css"
			type="text/css" />
		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.excheck-3.5.js"></script>
		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.exedit-3.5.js"></script>
		<!-- 树的js -->
		<script language="javascript">
						
// 左侧树配置信息
var settingSave = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		dblClickExpand: dblClickExpand,
		selectedMulti: false
	},
	edit: {
		enable: true,
		editNameSelectAll: true,
		removeTitle : "删除",
		renameTitle : "重命名",
		showRemoveBtn: showRemoveBtn,
		showRenameBtn: showRenameBtn
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeCheck: beforeCheck,
		onClick: onClick,
		beforeDrag: beforeDrag,
		beforeEditName: beforeEditName,
		beforeRemove: beforeRemove,
		beforeRename: beforeRename,
		onRemove: onRemove,
		onRename: onRename
	}
};

function dblClickExpand(treeId, treeNode) {
	return treeNode.level > 0;
}
var className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}

function beforeEditName(treeId, treeNode) {
	return true;
}

function beforeRemove(treeId, treeNode) {
	return confirm("确认删除 '" + treeNode.name + "' 吗？");
}

function onRemove(e, treeId, treeNode) {
	$.ajax({
			type : "POST",
			async : true,
			url   :  base+"/manage/datadown/dataDownAction!removeFileShareDirectory."+actionExt,
		//	contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			data : "id=" + treeNode.id,
		//	dataType : "json",
			success : function(msg) {
				if(msg != ""){
					alert("删除成功！");
				}else{
					alert("删除失败！");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.top.location=base + "/commons/beforetimeout.jsp";
			}
		});
}

function beforeRename(treeId, treeNode, newName) {
	className = (className === "dark" ? "":"dark");
	//showLog("[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
	if (newName.length == 0) {
		alert("节点名称不能为空.");
		var zTree = $.fn.zTree.getZTreeObj("fileShareTree");
		setTimeout(function(){zTree.editName(treeNode)}, 10);
		return false;
	}
	return true;
}

function onRename(e, treeId, treeNode) {
	$.ajax({
			type : "POST",
			async : true,
			url   :  base+"/manage/datadown/dataDownAction!renameFileShareDirectory."+actionExt,
		//	contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			data : "name=" + treeNode.name + "&id=" + treeNode.id,
		//	dataType : "json",
			success : function(msg) {
				if(msg != ""){
					alert("重命名成功！");
				}else{
					alert("重命名失败！");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.top.location=base + "/commons/beforetimeout.jsp";
			}
		});
}

function showRemoveBtn(treeId, treeNode) {
	return (!treeNode.isParent) && (treeNode.level > 0) && (isAdmin == "true");
}

function showRenameBtn(treeId, treeNode) {
	return (treeNode.level > 0) && (isAdmin == "true");
}

var newCount = 0;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.id
		+ "' title='新建目录' onfocus='this.blur();'></span>";
	if(isAdmin == "true"){
		sObj.after(addStr);
	}
	var btn = $("#addBtn_"+treeNode.id);
	if (btn) btn.bind("click", function(){
		newCount++;
		$.ajax({
			type : "POST",
			async : true,
			url   :  base+"/manage/datadown/dataDownAction!createFileShareDirectory."+actionExt,
		//	contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			data : "name=新建目录" + newCount + "&pid=" + treeNode.id,
		//	dataType : "json",
			success : function(msg) {
				if(msg != ""){
					var zTree = $.fn.zTree.getZTreeObj("fileShareTree");
					zTree.addNodes(treeNode, {id:msg, pId:treeNode.id, name:"新建目录" + (newCount)});
				}else{
					alert("添加失败");
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.top.location=base + "/commons/beforetimeout.jsp";
			}
		});
		
		return false;
	});
};
	
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_"+treeNode.id).unbind().remove();
};

function beforeCheck(treeId, treeNode) {
	var treeObj = $.fn.zTree.getZTreeObj("fileShareTree");
	var nodes = treeObj.getChangeCheckedNodes();
//	console.info(nodes);
//	console.info(treeNode);
	if(nodes && nodes.length > 0){
	
		if(nodes[0].id == treeNode.id){
			return false;
		}
	}
	return true;
}

function onClick(e, treeId, treeNode) {
	$("#td_title").html(treeNode.name);
	$(window.document).find("#fileShareListIframe").attr("src",
		"${base}/manage/datadown/dataDownAction!tableListFileShare.${actionExt}?fsId="+treeNode.id+"&title="+treeNode.name); 
}	

//加载完成后 方法
$(document).ready(function(){
	//初始化树信息	
	var chartData = '${treesJson}';
	chartData = eval("(" + chartData + ")");
	
	var _zNodes = chartData["trees"];
	$.fn.zTree.init($("#fileShareTree"), settingSave, _zNodes);
	
});
		</script>
		<!-- 业务js -->
		<script language="javascript">
		</script>
		<!-- 自动化边框 js -->
		<style type="text/css">
			.xixi1{width:209px;height:31px;background-image: url(${base}/images/index_52.png);cursor:pointer;}
			.xixi2{width:209px;height:31px;background-image:url(${base}/images/index_52-1.png);cursor:pointer;}
			.tab1{width:100px; padding-top:10px;float:left;text-align:center;cursor:pointer;color:#000000; font-size:14px; font-weight:bold; padding-left:8px; }
			.tab2{
				width:100px;
				padding-top:10px;
				float:left;
				text-align:center;
				cursor:pointer;
				font-size:14px;
				font-weight:bold;
			}
			a:hover{
				text-decoration:none;
			}
		</style>
	</head>
	<body>
		<div style="width:100%;height:10px;"></div>
		<table border="1" width="99%" style="height:492px;border:0px solid red;">
			<tr>
				<td style="height:28px;width:210px;vertical-align: top;">
					<div id="bg" class="xixi2">
						<div id="font1" class="tab1" >
							<a
								href="${base}/manage/datadown/dataDownAction!indexDataDown.portal">数据管理</a>
						</div>
						<div id="font2" class="tab2">文件共享</div>
					</div>
				</td>
				<td style="width:10px;">
				</td>
				<td id="td_title"
					style="height:28px;font:18px/ 131% Arial, Helvetica, sans-serif;width:821px;background:url(${base}/images/bg.gif);">
					列表内容
				</td>
			</tr>
			<tr>
				<td style="vertical-align:top;width:210px;">
					<div style="height:98%;height:98%;background-color:#E8EDF0;border:1px solid #CEDCDF;">
						<ul id="fileShareTree" class="ztree"
							style="margin-top:0px;height:447px;width:210px;overflow-y:auto;padding:0;border:none;"></ul>
					</div>
				</td>
				<td style="width:10px;">
				</td>
				<td style="vertical-align:top;">
					<iframe id="fileShareListIframe" name="fileShareListIframe"
						src="${base}/manage/datadown/dataDownAction!tableListFileShare.${actionExt}"
						width="100%" height="98%"></iframe>
				</td>
			</tr>
		</table>
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	$(document).ready(function(){
		
	});
	
	function f_frameStyleResize(targObj){
					var targWin = targObj.parent.document.all[targObj.name];
					if(targWin != null) {
						targWin.style.pixelHeight = 510;
					}
			
	}
			
	function f_iframeResize(){
		bLoadComplete = true; 
		f_frameStyleResize(self);
			
	}
	
	var bLoadComplete = false;
	window.onload = f_iframeResize;
</SCRIPT>
