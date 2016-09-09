<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="zh" lang="zh">
	<head>
		<meta http-equiv="Content-Style-Type" content="text/css" />
		<meta http-equiv="Content-Script-Type" content="text/javascript" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title></title>
		<style>
* {
	margin: 0;
	padding: 0;
}

body {
	background: url(${base}/images/contentinfo/tagpage/tab-bg.png) repeat-x;
	font-size: 12px;
}

a {
	text-decoration: none;
}

a:hover,ul {
	text-decoration: underline;
}

.fr {
	float: right;
}

.fc1 {
	color: #fff
}

.fc2 {
	color: #4c4c4c;
}

.fs1 {
	font-size: 14px;
}

.fw1 {
	font-weight: 700;
}

.heading {
	position: relative;
	height: 22px;
	margin: 10px 20px 15px;
	background: url(${base}/images/contentinfo/tagpage/ttl-bg.png) repeat-x;
	line-height: 22px;
}

.heading .left {
	position: absolute;
	left: -8px;
	width: 8px;
	background: url(${base}/images/contentinfo/tagpage/ttl-l.png) no-repeat;
}

.heading .right {
	position: absolute;
	right: -7px;
	width: 7px;
	background: url(${base}/images/contentinfo/tagpage/ttl-r.png) no-repeat;
}

.content {
	margin: 0 15px;
	padding-bottom: 20px;
	overflow: hidden;
	height: 50px;
}

.content img {
	display: block;
	float: left;
	border: 1px solid #82d1e7;
}

.news {
	margin-left: 85px;
	line-height: 22px;
}

.news .item {
	background: url(${base}/images/contentinfo/tagpage/dot.png) no-repeat 0
		center;
	padding-left: 15px;
}
</style>
	</head>
	<body>
		<div class="heading">
			<div class="left">&nbsp; 
			</div>
			<div class="right">
				&nbsp;
			</div>
			<p class="fc1">
				<a class="ul fc1 fr" target="_blank"
					href="${base}/public/common/contentmanager/content/displayWebContentInfos!showMore.portal?contype=2&page=1&c=${requestScope.c }"><fmt:message
						key="efetionmanage.common.more" />&gt;&gt;</a><span class="fw1 fs1">通知公告</span>
			</p>
		</div>
		<div class="content">
			<img src="${base}/images/contentinfo/tagpage/a.png" />
			<div class="news">
				<c:choose>
					<c:when test="${empty resultList2 }">
						<div>
							&nbsp;
						</div>
						<div>
							&nbsp;
						</div>
						<div>
							&nbsp;
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${resultList2 }" var="content"
							varStatus="status">
							<div class="item">
								<a class="fc2 fs1" title="<c:out value="${content.title }" escapeXml="true"/>"
									href="${base}/public/common/contentmanager/content/displayWebContentInfos.${actionExt}?contentId=${content.identifier}&c=${requestScope.c}"
									target="_blank"><c:choose>
										<c:when test="${ fn:length(content.title) gt 4 }">
											<c:out value="${ fn:substring(content.title,0,4)}"
												escapeXml="true"></c:out>
										</c:when>
										<c:otherwise>
											<c:out value="${ content.title}" escapeXml="true"></c:out>
										</c:otherwise>
									</c:choose> </a>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="heading">
			<div class="left">
				&nbsp;
			</div>
			<div class="right">
				&nbsp;
			</div>
			<p class="fc1">
				<a class="ul fc1 fr" target="_blank"
					href="${base}/public/common/contentmanager/content/displayWebContentInfos!showMore.portal?contype=1&page=1&c=${requestScope.c }"><fmt:message
						key="efetionmanage.common.more" />&gt;&gt;</a><span class="fw1 fs1"><fmt:message key="efetionmanage.framework.contentinfo.new.title"/></span>
			</p>
		</div>
		<div class="content">
			<img src="${base}/images/contentinfo/tagpage/a.png" />
			<div class="news">
				<c:choose>
					<c:when test="${empty resultList1 }">
						<div>
							&nbsp;
						</div>
						<div>
							&nbsp;
						</div>
						<div>
							&nbsp;
						</div>
					</c:when>
					<c:otherwise>
						<c:forEach items="${resultList1 }" var="content1"
							varStatus="status">
							<div class="item">
								<a class="fc2 fs1" title="<c:out value="${content1.title }" escapeXml="true"/>"
									href="${base}/public/common/contentmanager/content/displayWebContentInfos.${actionExt}?contentId=${content1.identifier}&c=${requestScope.c}"
									target="_blank"><c:choose>
										<c:when test="${ fn:length(content1.title) gt 4 }">
											<c:out value="${ fn:substring(content1.title,0,4)}"
												escapeXml="true"></c:out>
										</c:when>
										<c:otherwise>
											<c:out value="${ content1.title}" escapeXml="true"></c:out>
										</c:otherwise>
									</c:choose> </a>
							</div>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		</div>
	</body>
</html>
