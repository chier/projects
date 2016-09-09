<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>保存分析</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${base }/js/ztree/css/demo.css"
			type="text/css" />
		<link rel="stylesheet"
			href="${base }/js/ztree/css/zTreeStyle/zTreeStyle.css"
			type="text/css" />
		<style type="text/css">
			.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
		</style>
		<%@ include file="/commons/meta.jsp"%>
		
			<script type="text/javascript"
				src="${base }/js/ztree/jquery.ztree.core-3.5.js"></script>
			<script type="text/javascript"
				src="${base }/js/ztree/jquery.ztree.excheck-3.5.js"></script>
			<script type="text/javascript"
				src="${base }/js/ztree/jquery.ztree.exedit-3.5.js"></script>		
			<script type="text/javascript"
				src="${base }/js/permission/Ajax.tree.js"></script>
				
<SCRIPT type="text/javascript">
			
// 左侧树配置信息
var settingSave = {
	view: {
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
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
	check: {
		enable: true,
		chkStyle: "radio",
		radioType: "all"
	},
	data: {
		simpleData: {
		enable: true
		}
	}
	,
	callback: {
		beforeCheck: beforeCheck,
		onCheck: onCheck,
		
			
		beforeDrag: beforeDrag,
		beforeEditName: beforeEditName,
		beforeRemove: beforeRemove,
		beforeRename: beforeRename,
		onRemove: onRemove,
		onRename: onRename
	}
};

var className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}

function beforeEditName(treeId, treeNode) {
//	className = (className === "dark" ? "":"dark");
	//showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
//	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
//	zTree.selectNode(treeNode);
	// return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
	return true;
}

function beforeRemove(treeId, treeNode) {
	return confirm("确认删除 '" + treeNode.name + "' 吗？");
}

function onRemove(e, treeId, treeNode) {
	$.ajax({
			type : "POST",
			async : true,
			url   :  base+"/manage/warning/warningAction!removeDirectoryTree."+actionExt,
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
		var zTree = $.fn.zTree.getZTreeObj("saveWarningTree");
		setTimeout(function(){zTree.editName(treeNode)}, 10);
		return false;
	}
	return true;
}

function onRename(e, treeId, treeNode) {
	$.ajax({
			type : "POST",
			async : true,
			url   :  base+"/manage/warning/warningAction!renameDirectoryTree."+actionExt,
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
	// return !treeNode.isFirstNode;
	return !treeNode.isParent;
}

function showRenameBtn(treeId, treeNode) {
	// return !treeNode.isLastNode;
	return true;
}

var newCount = 0;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.id
		+ "' title='新建目录' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_"+treeNode.id);
	if (btn) btn.bind("click", function(){
		newCount++;
		$.ajax({
			type : "POST",
			async : true,
			url   :  base+"/manage/warning/warningAction!createDirectoryTree."+actionExt,
		//	contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			data : "name=新建目录" + newCount + "&pid=" + treeNode.id,
		//	dataType : "json",
			success : function(msg) {
				if(msg != ""){
					var zTree = $.fn.zTree.getZTreeObj("saveWarningTree");
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
	var treeObj = $.fn.zTree.getZTreeObj("saveWarningTree");
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

function onCheck(e, treeId, treeNode) {

	$("#hidSaveWar").val(treeNode.id);
	
	// showLog("[ "+getTime()+" onCheck ]&nbsp;&nbsp;&nbsp;&nbsp;" + treeNode.name );
}	
/**
 * 显示定制分析树
 */
function viewTreeDirectory(){
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/warning/warningAction!viewDirectoryTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	//	data : "rid="+$("#rid").val(),
		dataType : "json",
		success : function(msg) {
			// var zNodes = msg;
			/*
			var _btns = msg["btns"];
			if(_btns){
				for(var i=0;i<_btns.length;i++){
					if(_btns[i]){
						$("#"+_btns[i]).attr("checked","true");
					}
				}
			}
			*/
			var _zNodes = msg["trees"];
			// $.fn.zTree.init($("#treeDemo"), setting, _zNodes);
			$.fn.zTree.init($("#saveWarningTree"), settingSave, _zNodes);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			window.top.location=base + "/commons/beforetimeout.jsp";
		}
	});
}
/**
 * 返回选中的节点id
 */
function getChangeNodeId(){
	return $("#hidSaveWar").val();
}

//加载完成后 方法
$(document).ready(function(){
	viewTreeDirectory();
});

</SCRIPT>
	</head>
	<body>
		<div>
			<ul id="saveWarningTree" class="ztree"></ul>
			<input type="hidden" id="hidSaveWar" name="hidSaveWar" />
		</div>
	</body>
</html>
