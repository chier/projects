<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
			.articleList{margin:30px 15px 10px;list-style:none;border:0;padding:0;}
			.articleList li{margin-bottom:8px;border-bottom:1px dotted #E5E5E5;font-size:14px;overflow:hidden;padding:0 0 8px 20px;}
			.articleList li a{float:left;}
			.articleList li span{float:right;font-size:12px;color:#787878;}
			.breadcrumb{border-top:1px solid #A3D1F0;margin-bottom:10px;padding-left:12px;height:26px;line-height:26px;border-bottom:1px solid #E1E9F0;background: #d7d7d7 url(${base}/images/bg_gray_tb.gif) center center repeat-x;}
			.breadcrumb a{float:right;margin-right:10px;}
		</style>
	</head>
	<body
		style="background:url(${base}/images/welcom_center.jpg) repeat-y;width:942px;">
		<div class="content" style="width:940px;">
			<c:forEach var="conType" items="${typeList }">
				<div class="breadcrumb">
					${conType.ctName}:
					<a href="${base}/manage/contentmanage/content/contentInfoOperator!moreContent.${actionExt}?ctId=${conType.ctId}">更多</a>
				</div>
				<c:if test="${not empty  conType.infoList}">
					<ul class="articleList">
						<c:forEach var="info" items="${ conType.infoList}">
							<li>
								<a href="${base}/public/common/contentmanager/content/contentInfoView.${actionExt}?contentId=${info.identifier}" class="a_blue">${info.title}</a>
								<span><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${info.releasedate}"/> </span>
							</li>
						</c:forEach>
					</ul>
				</c:if>
			</c:forEach>
		</div>
	</body>
	<script type="text/javascript">
		$("document").ready(function(){
			var targWin = self.parent.document.all[self.name];
				if(targWin != null) {
					targWin.style.pixelHeight = 670;
				}
		});
	
	</script>
</html>
