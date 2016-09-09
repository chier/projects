<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>预警分析--前端显示</title>
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
		<style type="text/css">
			.vip_xinxilm{
				width:170px;
				height:26px;
				background:url(${base}/images/index_62.png) no-repeat;
				padding:5px 0px 0px 40px;
			}
			.vip_xinxilm h1{
				font-size:16px;
				color:#FFFFFF;
				font-family: "微软雅黑";
				line-height:22px;
			}
		</style>
		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.excheck-3.5.js"></script>

		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.exedit-3.5.js"></script>

		<script type="text/javascript">
		function setUl(heightVlaue){
			$("#queryWarningTree").css({height:heightVlaue});
		}
		<!--
		var setting = {
			view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				removeTitle:"删除",
				renameTitle:"重命名",
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: showRenameBtn
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				// beforeClick: beforeClick,
				onClick: onClick,
				
				beforeDrag: beforeDrag,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename
			}
		};
		
		
		var newCount = 0;
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.id
				+ "' title='新建目录' onfocus='this.blur();'></span>";
			if(treeNode.codeValue == "" && isAdmin  == "true"){
				sObj.after(addStr);
			}
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
							var zTree = $.fn.zTree.getZTreeObj("queryWarningTree");
							zTree.addNodes(treeNode, {id:msg, pId:treeNode.id, codeValue : "" ,name:"新建目录" + (newCount)});
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

		var className = "dark";
		function beforeDrag(treeId, treeNodes) {
			return false;
		}
		
		function beforeEditName(treeId, treeNode) {
			/*
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？");
			*/
			return true;
		}
		
		function beforeRemove(treeId, treeNode) {
			/*
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			*/
			return confirm("确认删除 '" + treeNode.name + "' 吗？");
			
			
		}
		
		function onRemove(e, treeId, treeNode) {
			if(treeNode.codeValue == ""){
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
			}else{
				$.ajax({
					type : "POST",
					async : true,
					url   :  base+"/manage/warning/warningAction!removeTemplate."+actionExt,
					// contentType: "application/x-www-form-urlencoded; charset=utf-8", 
					data : "id="+treeNode.codeValue,
					// dataType : "json",
					success : function(msg) {
						alert("删除成功！");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						window.top.location=base + "/commons/beforetimeout.jsp";
					}
				});
			}
		}
		function beforeRename(treeId, treeNode, newName) {
			className = (className === "dark" ? "":"dark");
			//showLog("[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		function onRename(e, treeId, treeNode) {
			if(treeNode.codeValue == ""){
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
			}else{
				$.ajax({
					type : "POST",
					async : true,
					url   :  base+"/manage/warning/warningAction!reNameTemplate."+actionExt,
					// contentType: "application/x-www-form-urlencoded; charset=utf-8", 
					data : "name=" + treeNode.name + "&id="+treeNode.codeValue,
					// dataType : "json",
					success : function(msg) {
						alert("修改成功");
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						 window.top.location=base + "/commons/beforetimeout.jsp";
					}
				});
			}
		}
		function showRemoveBtn(treeId, treeNode) {
			return (!treeNode.isParent && isAdmin  == "true");

		}
		
		function showRenameBtn(treeId, treeNode) {
			return (isAdmin  == "true");
		}
	
		function beforeClick(treeId, treeNode, clickFlag) {
			return treeNode.type == 1;
		}
		function onClick(event, treeId, treeNode, clickFlag) {
			if( treeNode.type == 1){
				showWarning(treeNode.codeValue);
			}
			var zTree = $.fn.zTree.getZTreeObj("queryWarningTree");
			zTree.expandNode(treeNode);
		}		
		/**
		 * 显示定制分析树
		 */
		function viewTreeDirectory(){
			$.ajax({
				type : "POST",
				async : true,
				url   :  base+"/manage/warning/warningAction!viewAllTree."+actionExt,
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
			//	data : "rid="+$("#rid").val(),
				dataType : "json",
				success : function(msg) {
					var _zNodes = msg["trees"];
					// console.info(_zNodes);
					$.fn.zTree.init($("#queryWarningTree"), setting, _zNodes);
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// window.top.location=base + "/commons/beforetimeout.jsp";
				}
			});
		}
		/**
		 * 显示预警分析
		 */
		function showWarning(templeCode){
			$.ajax({
				type : "POST",
				async : true,
				url   :  base+"/manage/warning/warningAction!showWarning."+actionExt,
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
				data : "templeCode=" + templeCode,
				dataType : "json",
				success : function(msg) {
					for(var ob in msg){
						$("#" +ob).val(msg[ob]);
					}
					$("#formWarning").attr("action",base+"/manage/warning/warningAction!queryReportTable."+actionExt +"?type=1");
					$("#formWarning").submit();
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					// window.top.location=base + "/commons/beforetimeout.jsp";
				}
			});
		}
		
		//加载完成后 方法
		$(document).ready(function(){
			viewTreeDirectory();
		});
		//-->
		
		</SCRIPT>
		<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj){

		var targWin = targObj.parent.document.all[targObj.name];

		var HeightValue = targObj.document.body.scrollHeight;
		
		targWin.style.pixelHeight = 530;
		

}

function f_iframeResize(){
		bLoadComplete = true; f_frameStyleResize(self);
		//this.document.body.scrollHeight;
		//alert(""+this.document.body.scrollHeight);
}

var bLoadComplete = false;

	window.onload = f_iframeResize;

</SCRIPT>

	</head>
	<body>
		<div style="width:100%;height:513px;margin-top:10px;">
			<div style="float:left;height:511px;background:#e8edf0;margin-left:3px;">
				<div class="vip_xinxi" style="border: 1px solid #617775;border-buttom:none;">
					<div class="vip_xinxilm">
						<h1>预警分析</h1>
					</div>
				</div>
				<ul id="queryWarningTree" class="ztree"
					style="height:471px;border: 1px solid #617775;border-top:none;border-buttom:none;overflow-y:auto;margin-top:0px;width:199px;background:#e8edf0;"></ul>
			</div>
			<div style="float:left;width:10px;height:511px;">
			</div>
			<div style="float:left;width:75%;height:510px;border:0px solid red">
				<iframe frameBorder="0" width="100%" height="100%"
					allowtransparency="true" id="warnIframe" name="warnIframe"
					src="${base}/html/warning/welcome.jsp"
					style="display:block;border: 1px solid rgb(202, 217, 240);margin:0 auto;"></iframe>
				<form method="post" id="formWarning" target="warnIframe">
					<input type="hidden" value="" id="hidFirstCode" name="hidFirstCode" />
					<input type="hidden" value="" id="hidFirstName" name="hidFirstName" />
					<input type="hidden" value="" id="hidFirstTable"
						name="hidFirstTable" />
					<input type="hidden" value="" id="hidFirstMin" name="hidFirstMin" />
					<input type="hidden" value="" id="hidFirstMax" name="hidFirstMax" />

					<input type="hidden" value="" id="hidSecondCode"
						name="hidSecondCode" />
					<input type="hidden" value="" id="hidSecondName"
						name="hidSecondName" />
					<input type="hidden" value="" id="hidSecondTable"
						name="hidSecondTable" />

					<input type="hidden" value="" id="hidSecondMin" name="hidSecondMin" />
					<input type="hidden" value="" id="hidSecondMax" name="hidSecondMax" />

					<input type="hidden" value="" id="hidArea" name="hidArea" />
				</form>
			</div>
		</div>
	</body>
</html>
