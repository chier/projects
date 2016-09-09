<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>权限设置管理</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" href="${base }/js/ztree/css/demo.css"
			type="text/css" />
		<link rel="stylesheet"
			href="${base }/js/ztree/css/zTreeStyle/zTreeStyle.css"
			type="text/css" />
		<link rel="stylesheet"
			href="${base }/css/menu-aim-master/bootstrap.css" type="text/css" />
		<style>
        .popover {
			height:428px;
            width: 740px;
            -webkit-border-top-left-radius: 0px;
            -webkit-border-bottom-left-radius: 0px;
            border-top-left-radius: 0px;
            border-bottom-left-radius: 0px;
            overflow: hidden;
        }

        .popover-content {
            text-align: center;
        }
        
         .popover-content  ul{
         	 margin-left:20px;
         	 top:20px;
         	
        }
        
        .popover .popover-title .execute_img{
				height:22px;
				width:71px;
				margin-left:40px;
				cursor:pointer;
        }

        .popover .popover-title .wrong_img{
				height:20px;
				width:20px;
				float:right;
				cursor:pointer;
        }
		
		.propover-right{
			text-align: left;
		}
		
		.propover-right  ul{
         	 margin-left:40px;
         	 margin-top:20px;
         	 height:350px;
        }
        
        .propover-right  ul  li{
        	list-style :none;
        	padding-left:20px;
        }
		
		.dropdown-menu{
			top:45px;
			left:20px;
		}
		.dropdown-menu  li{
			list-style :none;
			/*float:left;*/
		}
        .dropdown-menu  li  a:hover {
            background-image: none;
            color: white;
            background-color: #ffe582;
            background-color: #ffe582;
        }

        .dropdown-menu  li  a.maintainHover {
            color: white;
            background-color: rgb(255,153,0);
        }
    </style>
		<%@ include file="/commons/meta.jsp"%>

		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript"
			src="${base }/js/ztree/jquery.ztree.excheck-3.5.js"></script>

		<script type="text/javascript"
			src="${base }/js/permission/Ajax.tree.js"></script>

		<SCRIPT type="text/javascript">
		<!--
		//-->
	</SCRIPT>
	</head>
	<body>
		<div id="main">
			<input type="hidden" id="rid" name="rid" value="${rid}" />
			<input type="hidden" id="rid" name="rname" value="${rname}" />
			<div class="title">
				<h4 style="margin:0px;">
					<a href="${base }/html/adminSetting/index.jsp">管理设置</a> &gt;&gt;
					<a
						href="${base}/manage/rolemanage/role/roleAction!findAllRole.${actionExt}">角色管理</a>

				</h4>
			</div>
			<div class="navbar navbar-inverse navbar-fixed-top">
				<ul class="dropdown-menu" role="menu" style="display:block;">
					<li data-submenu-id="submenu-raw-date">
						<a href="#">原始数据</a>
					</li>
					<li data-submenu-id="submenu-queries">
						<a href="#">即席查询</a>
					</li>
					<li data-submenu-id="submenu-custom">
						<a href="#">定制分析</a>
					</li>
					<li data-submenu-id="submenu-loap">
						<a href="#">OLAP分析</a>
					</li>
					<li data-submenu-id="submenu-datadown">
						<a href="#">数据管理</a>
					</li>
				</ul>
			</div>

			<div id="submenu-custom" class="popover">
				<h3 class="popover-title">
					定制分析
					<input type="button" onclick="executeCustomTree();" class="bot1other" style="width:90px;"
										value="保存" />
					</img>
					<img src="${base}/images/errorimg/wrong_img.gif" class="wrong_img" />
				</h3>
				<div class="popover-content" style="float:left">
					<ul id="treeDemo" class="ztree"></ul>
				</div>
				<div class="propover-right">
					<ul>
						<li>
							<input type="checkbox" name="customBtn" id="exportBtn"
								value="exportBtn" style="margin-top:-1px;margin-right:10px;" />
							导出Excel
						</li>
					</ul>
				</div>
			</div>

			<div id="submenu-loap" class="popover">
				<h3 class="popover-title">
					OLAP分析
					<input type="button" onclick="executeLoapTree();" class="bot1other" style="width:90px;"
										value="保存" />
					<img src="${base}/images/errorimg/wrong_img.gif" class="wrong_img" />
				</h3>
				<div class="popover-content" style="float:left">
					<ul id="treeDemo-loap" class="ztree"></ul>
				</div>
				<div class="propover-right">
					<ul>
						<li>
							<input type="checkbox" name="loapBtn" id="exportBtn"
								value="exportBtn" style="margin-top:-1px;margin-right:10px;" />
							导出Excel
						</li>
					</ul>
				</div>
			</div>
			<div id="submenu-queries" class="popover">
				<h3 class="popover-title">
					<!--executeQueriesTree-->
					即席查询
					<input type="button" onclick="executeQueriesTree();" class="bot1other" style="width:90px;"
										value="保存" />
					<img src="${base}/images/errorimg/wrong_img.gif" class="wrong_img" />
				</h3>
				<div class="popover-content" style="float:left">
					<ul id="treeDemo-queries" class="ztree"></ul>
				</div>
				<div class="propover-right">
					<ul>
						<li>
							<input type="checkbox" name="queriesBtn" id="exportSubmit"
								value="exportSubmit" style="margin-top:-1px;margin-right:10px;" />
							导出数据
						</li>
					</ul>
				</div>
			</div>
			<div id="submenu-raw-date" class="popover">
				<h3 class="popover-title">
					原始数据
					<input type="button" onclick="executeRawDataTree();" class="bot1other" style="width:90px;"
										value="保存" />
					<img src="${base}/images/errorimg/wrong_img.gif" class="wrong_img" />
				</h3>
				<div class="popover-content" style="float:left">
					<ul id="treeDemo-raw-data" class="ztree"></ul>
				</div>
				<div class="propover-right">
					<ul>
						<li>
							<input type="checkbox" name="rawDataBtn" id="li_export"
								value="li_export" style="margin-top:-1px;margin-right:10px;"
								onclick="funRewDataEx($(this));" />
							导出
						</li>
						<li id="raw-data-li" style="display:none;list-style :none;">
							<ul>
								<li style="list-style :none;">
									<input type="checkbox" name="rawDataBtn" id="exportReportPdf"
										value="exportReportPdf"
										style="margin-top:-1px;margin-right:10px;" />
									导出为PDF格式文件
								</li>
								<li style="list-style :none;">
									<input type="checkbox" name="rawDataBtn" id="exportReportExcel"
										value="exportReportExcel"
										style="margin-top:-1px;margin-right:10px;" />
									导出Excel格式文件
								</li>
								<li style="list-style :none;">
									<input type="checkbox" name="rawDataBtn" id="exportReportHtml"
										value="exportReportHtml"
										style="margin-top:-1px;margin-right:10px;" />
									导出 HTML 格式文件
								</li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			<div id="submenu-datadown" class="popover">
				<h3 class="popover-title">
					数据管理
					<input type="button" onclick="executeDataDownTree();" class="bot1other" style="width:90px;"
										value="保存" />
					<img src="${base}/images/errorimg/wrong_img.gif" class="wrong_img" />
				</h3>
				<div class="popover-content" style="float:left">
					<ul id="treeDemo-datadown" class="ztree"></ul>
				</div>
				<div class="propover-right">
				</div>
			</div>
		</div>
		<script>
		
		// 导出点击判断
		function funRewDataEx(obj){
			if(obj.attr("checked") == true){
				$("#raw-data-li").show();
			}else{
				$("#raw-data-li").hide();
			}
		}
		function removeClassHover(obj){
	    	$(".dropdown-menu li").each(function(){
	            if($(this).attr("data-submenu-id") != obj.attr("data-submenu-id")) {
	                    $(this).find("a").removeClass("maintainHover");
	                }  else{
	                    obj.find("a").addClass("maintainHover");
	                }            
	            });
	    }
	    
	    function hideOtherPopover(submenuId){
	    	$(".popover ").each(function(){
				var $pover = $(this);
				if($pover.attr("id") != submenuId){
					$pover.css("display","none");
				}
			});
	    }
	    
	        
        var $menu = $(".dropdown-menu");
		$(document).ready(function(){
			// 点击事件
			$(".dropdown-menu li").each(function(){
				var submenuId = $(this).attr("data-submenu-id");
				var obj = $(this);
				$(this).find("a").removeClass("maintainHover");
				$(this).click(function(){
					// 删除其它选中的标识
					removeClassHover(obj);
					hideOtherPopover(submenuId);
					
					var $submenu = $("#" + submenuId),
						 offset = $menu.offset(),
						 height = $menu.outerHeight(),
						 width = $menu.outerWidth();
					
					// 定制分析选择事件
					if(submenuId == "submenu-custom"){
						viewCustomTree();
					}
					//即席查询事件
					if(submenuId == "submenu-queries"){
						viewQueriesTree();
					}
					
					//loap 查询事件
					if(submenuId == "submenu-loap"){
						viewLoapTree();
					}
					
					// 原始数据查询
					if(submenuId == "submenu-raw-date"){
						viewRawDataTree();
					}
					
					//数据管理查询
					if(submenuId == "submenu-datadown"){
						viewDataDownTree();
					}
					 
					// Show the submenu
					$submenu.css({
						display: "block",
						top: offset.top,
						left: offset.left + width + 5  // main should overlay submenu
						// height: height - 4  // padding for main dropdown's arrow
					});
				});
			});
			
			$(".popover ").each(function(){
				var $pover = $(this);
				//关闭按钮事件
				$(this).find(".wrong_img").click(function(){
					$pover.css("display","none");
				});
			});
		});
    </script>
	</body>
</html>
