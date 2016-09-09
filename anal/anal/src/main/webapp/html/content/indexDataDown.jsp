<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

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
		</style>
		<script>
			function goPage(pageNum) {
					window.location.href = "${base}/manage/contentmanage/content/contentInfoOperator!indexDataDataDownload.${actionExt}?ctId=${ctId}&page="+pageNum;
				}
		</script>
		<SCRIPT LANGUAGE="JavaScript">

				function f_frameStyleResize(targObj){
				
						var targWin = targObj.parent.document.all[targObj.name];
						
						if(targWin != null) {
						
							var HeightValue = targObj.document.body.scrollHeight
							
							if(HeightValue < 600){HeightValue = 600} //不小于600
						
							targWin.style.pixelHeight = 560;
						
						}
				
				}
				
				function f_iframeResize(){
				
						bLoadComplete = true; f_frameStyleResize(self);
				
				}
				
				var bLoadComplete = false;
				
				window.onload = f_iframeResize;

		</SCRIPT>
	</head>
	<body>
		<div class="content">
			<form name="dataDownForm" id="dataDownForm" action="" method="post">
				<div class="breadcrumb">
					<b style="font-family:微软雅黑;">数据下载</b>
					
					<!-- 
				<a
					href="${base}/manage/contentmanage/content/contentInfoOperator!moreContent.${actionExt}?ctId=${conType.ctId}">更多</a>
				 -->
				</div>
				<c:if test="${not empty  infoList}">

					<ul class="articleList" style="background:url(${base}/images/welcom_datadown.jpg)  repeat-y;">
						<li>
							<input type="checkbox" name="choose"
								onclick="chose(dataDownForm.ids)" id="choose" value="" />
							<input type="button" name="e" id="3" value="下载附件" class="bot1other"  style="width:100px;"
								onclick="exportAll();" />
						</li>
						<c:forEach var="info" items="${ infoList}">
							<li>
								<input type="checkbox" name="ids" id="ids"
									value="${info.identifier}" />
								<a
									href="${base}/public/common/contentmanager/content/contentInfoView.${actionExt}?contentId=${info.identifier}"
									class="a_blue">${info.title}</a>
								<span><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss"
										value="${info.releasedate}" /> </span>
							</li>
						</c:forEach>
					</ul>
						<%@ include file="/commons/page.jsp"%>
				</c:if>
				<c:if test="${empty infoList}">
					<ul class="articleList">
					<li> 暂无记录 </li> </ul>
				</c:if>
			</form>
		</div>
	</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
	$(document).ready(function(){
		
	});
	function chose(ids){
		if(document.getElementById("choose").checked==true){
			checkall(ids);
		}else{
			checknull(ids);
		}
	}
	function exportAll(){
		if(checkboxCount(document.getElementById("dataDownForm").ids)<1 ){
			$.prompt("  请选择要下载的内容！",{buttons:{确定:true},alertType:'msg'});
			return false;
		}else{
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
						var panduan = document.getElementsByName("ids");
						var str="";
						for(var i=0;i<panduan.length;i++)
						{
							if(panduan[i].checked == true)
							{
								str+=panduan[i].value;
								if(i != panduan.length -1){
									str += ",";
								}
							}
						}
						str = str.substring(0,str.length-1);
						// alert(str);
						window.open("${base}/manage/contentmanage/content/contentAttachOperator!downloadAll.${actionExt}?ctIds=" + str,"newWindow","top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no");
					}else{
						 top.$.prompt("你没有权限下载附件！",{buttons:{确定:true},alertType:'error'});
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					window.top.location=base + "/commons/beforetimeout.jsp";
				}
			});
		}
	}
</SCRIPT>
