<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="${base}/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<style type="text/css">
			 a:link, a:visited{color:#222;text-decoration:none;outline-style:none;}
			.content{float:left;width:996px;border:1px solid #DDD;overflow:hidden;}
			.articleList{margin:0 0 10px;list-style:none;border:0;padding:0;}
			.articleList li{margin-bottom:6px;border-bottom:1px dotted #E5E5E5;font-size:14px;overflow:hidden;padding:0 0 8px 20px;}
			.articleList li a{}
			.articleList li span{float:right;font-size:12px;color:#787878;margin-right:20px;}
			.breadcrumb{border-top:0px solid #A3D1F0;margin-bottom:10px;padding-left:12px;height:26px;line-height:26px;border-bottom:1px solid #E1E9F0;background: #d7d7d7 url(${base}/images/bg_gray_tb.gif) center center repeat-x;}
			.breadcrumb a{float:right;margin-right:10px;}
			
			/*左侧列表*/
				.m_nav{position:relative;z-index:2;}
			.hd{position:relative;height:30px;background-position:0 0;}
			.main_nav{margin-top:-1px;}
			.mod_box h3,
			.main_nav a{background:url('${base}/images/bg_sprites.png') repeat-x;}
			.main_nav a{display:block;height:35px;line-height:36px;vertical-align:middle;border-top:1px solid #D8EAFA;background-position:0 -94px;overflow:hidden;cursor:pointer;}
			.main_nav a:hover,.main_nav a.over{background-position:0 -133px;text-decoration:none;}
			.main_nav a span{display:block;padding-left:30px;*zoom:1;}
			.main_nav a.on span{background-position:15px -182px;}
			
			.main_nav a.off span{background-position:15px -206px;}
			.main_nav .second_list{padding:8px 0;border-top:1px solid #D8EAFA;}
			.main_nav .second_list a{height:18px;line-height:18px;margin:0 8px;padding:5px 0 3px 47px;border:1px solid #FFF;background-position:-138px -175px;}
			.main_nav .second_list a:hover,.main_nav .second_list a.hover{padding:5px 0 3px 47px;color:#F18934;border:1px solid #E7E7E7;background-position:19px -236px;}
			
			/* === self_service ===*/
			.self_service { padding:10px 0; text-align: center; height: 72px; overflow: hidden;}
			.self_service a {display: -moz-inline-stack; display:inline-block; *zoom:1;*display:inline;padding:0 3px;margin: 2px 0;width:78px;height:20px;background-position:4px -89px;line-height:20px;color:#333;text-align:center;}
			.self_service a:hover{text-decoration:none;}
			
			.mod_box{border:1px solid #DDD;background:#FFF;margin-bottom: 10px;}
			.mod_box h3{line-height:30px;background-position:0 0;font-weight:bold;color:#FFF;padding-left:10px;}
			.mod_box h3 .more{padding-right:10px;color:#FFF;}
			
			ul{vertical-align:baseline}
			
			.xixi1{width:209px;height:31px;background-image: url(${base}/images/index_52.png);cursor:pointer;}
			.xixi2{width:209px;height:31px;background-image:url(${base}/images/index_52-1.png);cursor:pointer;}
			.tab1{width:100px; padding-top:14px;float:left;text-align:center;cursor:pointer;color:#000000; font-size:14px; font-weight:bold; padding-left:8px; }
			.tab2{
				width:100px;
				padding-top:14px;
				float:left;
				text-align:center;
				cursor:pointer;
				font-size:14px;
				font-weight:bold;
			}
		</style>
		<script>
			function goPage(pageNum) {
					window.location.href = "${base}/manage/contentmanage/content/contentInfoOperator!indexDataDataDownload.${actionExt}?ctId=${ctId}&page="+pageNum;
				}
		</script>
	</head>
	<body>
		<div style="width:100%;height:10px;"></div>
		<form name="dataDownForm" id="dataDownForm" action="" method="post">
			<table border="0" width="99%" height="98%">
				<tr>
					<td
						style="height:28px;width:210px;vertical-align:middle;background:url(${base}/images/bg.gif);">
						<!-- 
						<div style="margin:0 auto">数据管理</div>
						 -->
						<div id="bg" class="xixi1">
							<div id="font1" class="tab1">
								数据管理
							</div>
							<div id="font2" class="tab2">
								<a
									href="${base}/manage/datadown/dataDownAction!indexFileShare.portal">文件共享</a>
							</div>
						</div>
					</td>
					<td style="width:10px;">
					</td>
					<td
						style="height:28px;width:821px;background:url(${base}/images/bg.gif);">
						<c:forEach var="info" items="${plist}">
							<c:if test="${info.id == sid}">
								<span id="lab_title" style="margin-left:20px;">${info.name}</span>
							</c:if>
						</c:forEach>
						<input type="button" name="butDown" id="butDown" value="下载"
							class="bot1other" style="margin-left:100px;"
							onclick="allExport();" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align:top;border:1px solid #CEDCDF;">
						<div style="height:98%;">
							<c:if test="${not empty plist}">
								<ul class="main_nav" id="main_nav">
									<c:forEach var="info" items="${plist}">
										<a
											href="${base}/manage/datadown/dataDownAction!indexDataDown.${actionExt}?sid=${info.id}"
											id="channel${info.id}" class="on"> <c:choose>
												<c:when test="${info.id == sid}">
													<span style="color:rgb(255,153,0);">${info.name}</span>
												</c:when>
												<c:otherwise>
													<span>${info.name}</span>
												</c:otherwise>
											</c:choose> </a>
									</c:forEach>
								</ul>
							</c:if>
						</div>
					</td>
					<td style="width:10px;">
					</td>
					<td style="vertical-align:top;">
						<div>
							<c:if test="${not empty tlist}">
								<style type="text/css">
								
								.div_level0{
									background-color:rgb(232, 245, 255);
									display:block;
									line-height:30px;
									height:30px;
								}
								.div_level0 .btn{
									float:right;
									margin-right:10px;
								}
								li a.level0{
									text-align:center;
									cursor:pointer;
									text-decoration:none;
									
								}
								
								li.evel1{
									
									border-top:1px solid #D8EAFA;
									height:20px;
									vertical-align:middle;
									overflow:hidden;
									cursor:pointer;
									font-size:14px;
									padding:0 0 8px 20px;
									
								}
								li.evel1 span{
									
									font-size:12px;
									color:#787878;
								}
							</style>
								<ul>
									<c:forEach var="ptInfo" items="${tlist}" varStatus="status">
										<li>
											<div class="div_level0">
												<table>
													<tr>
														<td width="90%">
															<input type="checkbox" name="choose_${status.count}"
																onclick="chose('choose_${status.count}',dataDownForm.ids_${status.count})"
																id="choose_${status.count}" value="" />
															<a class="level0"
																onclick="showAndHidUl(${status.count});"
																id="a_id_${status.count}" title="${ptInfo.name}"> <span
																id="treeDemo_1_span"> ${ptInfo.name}</span> </a>
														</td>
														<td>
															<input type="button" name="but_li_down"
																class="bot1other btn" id="but_li_down" value="下载"
																onclick="exportType('${status.count}');" />
														</td>
													</tr>
												</table>


											</div>
											<c:if test="${not empty conList}">
												<ul id="con_ul_${status.count}">
													<c:forEach var="conInfo" items="${conList}">
														<c:if test="${conInfo.tid == ptInfo.id}">
															<li class="evel1">
																<table>
																	<tr>
																		<td width="80%" style="height:20px;">
																			<input type="checkbox" name="ids_${status.count}"
																				id="ids_${status.count}"
																				value="${conInfo.identifier}" />
																			${conInfo.title}
																		</td>
																		<td>
																			<span><fmt:formatDate
																					pattern="yyyy-MM-dd HH:mm:ss"
																					value="${conInfo.releasedate}" />
																			</span>
																		</td>
																	</tr>
																</table>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</c:if>
										</li>
									</c:forEach>
								</ul>
							</c:if>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
<script language="javascript">
	
	/**
	 * 勾选对话框
	 */
	function chose(choose,ids){
		if(document.getElementById(choose).checked==true){
			checkall(ids);
		}else{
			checknull(ids);
		}
	}
	
	function showAndHidUl(id){
		var _conUl = $("#con_ul_"+id);
		//alert(_conUl.css("display"));
		if(_conUl.css("display") == "none"){
			// $("#con_ul_"+id).show();
			$("#con_ul_"+id).fadeIn("normal");
		}else{
// 			$("#con_ul_"+id).hide();
			$("#con_ul_"+id).fadeOut("normal");
		}
	}
	
	/**
	 * 下载所有选中的
	 */
	function allExport(){
		// 获取选中值ID
		var str = "";
		$("input:checkbox:checked").each(function(){ 
			if($(this).val() != ""){
				str += $(this).val() 
				str += ",";
			}
		}) 
		str = str.substring(0,str.length-1);
		// 获取输出的标题		
		var _title = $("#lab_title").html();
		exportAll(_title,str);
	}
	
	/**
	 * 下载类别下的数据
	 */
	function exportType(id){
		//获取选中的值
		var panduan = document.getElementsByName("ids_" + id);
		var str = getEckedStr(panduan);
		
		// 获取输出的标题		
	 	var _title = $("#lab_title").html();
		_title += "_";
		_title += $("#a_id_"+id).attr("title");

		exportAll(_title,str);
	}
	
	function getEckedStr(panduan){
		var str="";
		for(var i=0;i<panduan.length;i++){
			if(panduan[i].checked == true){
				str += panduan[i].value;
				str += ",";
			}
		}
		str = str.substring(0,str.length-1);
		return str;
	}
</script>

<SCRIPT LANGUAGE="JavaScript">

	function exportAll(title,str){
		if(title == "" || str == ""){
			return true;
		}
			$.ajax({
				type : "POST",
				async : true,
				url   :  base+"/manage/contentmanage/content/contentAttachOperator!isDataDownPermission."+actionExt,
				contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	//			data : "ctId="+ctid,
//				dataType : "json",
				success : function(msg) {
				// 	console.info("msg = " + msg);
					if(msg == 1){
						window.open("${base}/manage/contentmanage/content/contentAttachOperator!downloadAll.${actionExt}?ctIds=" + str+"&title=" + encodeURI(title),"newWindow","top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
					}else{
						 top.$.prompt("你没有权限下载附件！",{buttons:{确定:true},alertType:'error'});
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.location=base + "/commons/beforetimeout.jsp";
				}
			});
	}
</SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
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
