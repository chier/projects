var contextpath = document.getElementById("contextpath").value;
var zTreeObj; // ztree对象
var zNodes; //

/**
 * 当前被右键击中的treeNode
 * 
 * @type
 */
var rightClickedTreeNode;  

// 显示快捷菜单

/**
 * 组织架构树配置
 * 
 * @type
 */
var setting = {
	view : {
		// addHoverDom : treeNodeAddHoverDom,
		// removeHoverDom : treeNodeRemoveHoverDom,
		selectedMulti : false
	},
	edit : {
	// enable : true
	},

	data : {
		simpleData : {
			enable : true,
			pIdKey : "upid"
		}
	},
	callback : {
		onRightClick : myOnRightClick,
		onClick : treeNodeClicked
	}
};



/**
 * treeNode的右键动作
 * 
 * @param {}
 *            e
 * @param {}
 *            treeId
 * @param {}
 *            treeNode
 */
function myOnRightClick(e, treeId, treeNode) {
	rightClickedTreeNode = treeNode;
	// 显示treeNode的右捷菜单
	$('#tree_node_menu').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
}



/**
 * 为treeNode的右键菜单绑定事件
 */
$(function() {
	
	// 新增
	$("#m-tree-refresh").click(function() {
		//addOgnz();
		zTreeObj.refresh();
	});
	
});


var newCount = 1;

$(document).ready(function() {
	// using('layout');
	//window.setInterval("getCurrentTime()", 1000);
	
	$.ajax({
				url : contextpath + "/main/getMenuTreeJson.talent",
				type : "POST",
				dataType : "json",
				cache : false,
				complete : function(_data) {
					
					var retObj = JSON.parse(_data.responseText);
					
					if (retObj.result == 0) { // 业务上成功
						zNodes = retObj.data;
						if (zNodes) {
							for (var i = 0; i < zNodes.length; i++) {
								var node = zNodes[i];
								node.myUrl = node.url,
								node.url = null;
								// 因为node.myUrl存在的话，就可以代表是子菜单
								if(node.myUrl) {	
									node.icon="../images/img/2.png";
								}else{
									node.icon="../images/img/1.png";
								}
							}
						}
						zTreeObj = $.fn.zTree.init($("#menuTree"), setting,zNodes);
						$("#expandAllBtn").bind("click", {type:"expandAll"}, expandNode);
						$("#collapseAllBtn").bind("click", {type:"collapseAll"}, expandNode);
						zTreeObj.expandAll(true);
					} else if (retObj.result == 1) {
						alert(retObj.msg);
					}
				}
			});
});


/**
 * 全部展开| 全部收起
 */
function expandNode(e) {
	var zTree = $.fn.zTree.getZTreeObj("menuTree"),
	type = e.data.type;
	
	if (type == "expandAll") {
		zTree.expandAll(true);
	}
	if(type == "collapseAll") {
		zTree.expandAll(false);
	}
}

/**
 * 组织架构树的click事件
 * 
 * @param {}
 *            event
 * @param {}
 *            treeId
 * @param {}
 *            treeNode
 * @param {}
 *            clickFlag
 */
function treeNodeClicked(event, treeId, treeNode, clickFlag) {
	var id = treeNode["id"];
	if(treeNode.myUrl!='' && !treeNode.isParent){
		var x = "/";
		if (treeNode.myUrl.indexOf("/") == 0){
			x = "";
		}
		var urlPath = contextpath + x + treeNode.myUrl;
		openTab(treeNode.name, urlPath, id, null);	
	}
	
}

/* 打开一个标签 */
function openTab(title, url, id, icon) {
	/**
	 * 如果这个标题的标签存在，则选择该标签 否则添加一个标签到标签组
	 */
	if ($("#tabs").tabs('exists', title)) { // 如果存在就打开它，
		$("#tabs").tabs('select', title);
	} else { // 如果不存在就创建tab
		$("#tabs").tabs('add', {
					title : title,
					content : createTabContent(url),
					closable : true,
					id : id,
					icon : icon
				});
	}
}

/* 生成标签内容 */
function createTabContent(url) {
	return '<iframe style="width:100%;height:100%;" scrolling="auto" frameborder="0" src="'
			+ url + '"></iframe>';
}

/**
 * 为tab右键各菜单项绑定click事件
 */
$(function() {
			// 刷新
			$("#m-refresh").click(function() {
						var currTab = $('#tabs').tabs('getSelected'); // 获取选中的标签项
						var url = $(currTab.panel('options').content)
								.attr('src'); // 获取该选项卡中内容标签（iframe）的 src 属性
						/* 重新设置该标签 */
						$('#tabs').tabs('update', {
									tab : currTab,
									options : {
										content : createTabContent(url)
									}
								});
					});


			// 关闭所有
			$("#m-closeall").click(function() {
						$(".tabs li").each(function(i, n) {
									var title = $(n).text();
									$('#tabs').tabs('close', title);
								});
					});

			// 关闭其它
			$("#m-closeother").click(function() {
						var currTab = $('#tabs').tabs('getSelected');
						currTitle = currTab.panel('options').title;

						$(".tabs li").each(function(i, n) {
									var title = $(n).text();

									if (currTitle != title) {
										$('#tabs').tabs('close', title);
									}
								});
					});

			// 关闭当前
			$("#m-close").click(function() {
						var currTab = $('#tabs').tabs('getSelected');
						currTitle = currTab.panel('options').title;
						$('#tabs').tabs('close', currTitle);
					});
		});

// $(function() {
// // 屏蔽右键
// $(document).bind("contextmenu", function(e) {
// return false;
// });
// });

$(function() {
			/* 为选项卡绑定右键 */
			$(".tabs li").live('contextmenu', function(e) {

						/* 选中当前触发事件的选项卡 */
						var subtitle = $(this).text();
						$('#tabs').tabs('select', subtitle);

						// 显示快捷菜单
						$('#menu').menu('show', {
									left : e.pageX,
									top : e.pageY
								});

						return false;
					});
		});

// 将一个绝对定位的对象封装成可拖动对象
function MoveControl(c) {
	/* 考虑浏览器兼容性，这里编写一个获取事件对象的公共方法 */
	c.getEvent = function(e) {
		if (!e) {
			e = event;
			e.pageX = event.x;
			e.pageY = event.y;
		}
		return e;
	};
	/* 当鼠标在该对象上按下，记录按下时鼠标的位置，并且修改监听锁，开始监听 */
	c.onmousedown = function(e) {
		e = this.getEvent(e);
		this.IX = e.pageX;
		this.IY = e.pageY;
		this.moveKey = true;
	};
	/* 当鼠标在对象上移动时，同时移动该对象 */
	c.onmousemove = function(e) {
		if (!this.moveKey)
			return;

		e = this.getEvent(e);
		this.style.top = parseInt(this.style.top ? this.style.top : 0)
				+ (e.pageY - this.IY) + "px";
		this.style.left = parseInt(this.style.left ? this.style.left : 0)
				+ (e.pageX - this.IX) + "px";
		this.IX = e.pageX;
		this.IY = e.pageY;
	};
	/* 当鼠标在对象上松开时，停止移动该对象 */
	c.onmouseup = function(e) {
		this.moveKey = false;
	};
}

//------------------ 显示日期 start ------------------
var infoTimeObj = document.getElementById("info_time");
function setCurrentTime(tm)
{
	if( infoTimeObj )
	{
		infoTimeObj.innerHTML = tm;
	}
}


function getCurrentTime()
{
	var Stamp = new Date();
	//Stamp.setTime(Stamp.getTime());
	var msg = Stamp.getFullYear() + "-" + (Stamp.getMonth() + 1) + "-" + Stamp.getDate();
	var hours = Stamp.getHours();
	if( hours < 10 )
	{
		hours = "0" + hours;
	}
	var minutes = Stamp.getMinutes();
	if( minutes < 10 )
	{
		minutes = "0" + minutes;
	}
	var seconds = Stamp.getSeconds();
	if( seconds < 10 )
	{
		seconds = "0" + seconds;
	}
	msg	+= " " + hours + ":" + minutes + ":" + seconds;
	setCurrentTime(msg);
}
	
	
//------------------ 显示日期 end ------------------

