<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/html/epage/epagehead.jsp" %>
<html>
	<head>
		<title><fmt:message key='efetion.webpage.title.publicpage'/></title>
        <%@ include file="/html/epage/meta.jsp" %>
        <link rel="stylesheet" type="text/css" href="${base}/css/epage/style.css?v=24" media="screen" />
	</head>
  <body id="center">
  	<div class="wrap">
		<div class="area-0">
			<div class="nm"><div class="bar-left">&nbsp;</div><span class="fs2 fc3 fw1">
				      <c:choose>
							<c:when test="${empty sessionScope.COMPANYINFO || sessionScope.COMPANYINFO.corpName==''}">
							<fmt:message	key="efetion.webpage.topic.default" />
							</c:when>
							<c:otherwise>
							<c:out value="${sessionScope.COMPANYINFO.corpName}"></c:out>
							</c:otherwise>
					 </c:choose>
	 </span></div>
			<div class="tm fc5" id='nowTime'></div>	
		</div>
		
          <div class="area-1 clearfix">
			<div class="col0">
				<div class="w-mdl2">
					<div class="box">
						<div class="heading fs1 fw1"><div class="fr"><fmt:message key='efetion.webpage.weather.right'/></div><div class="icn icn1">&nbsp;</div><fmt:message key='efetion.webpage.weather.left'/></div>
						<div class="content weather clearfix">
							<div class="item fc8"><p>今天</p><img src="${base}/html/sun.png"/><p>1-15</p></div>
							<div class="item fc8"><p>今天</p><img src="${base}/html/sun.png"/><p>1-15</p></div>
							<div class="item fc8"><p>今天</p><img src="${base}/html/sun.png"/><p>1-15</p></div>
						</div>
					</div>
				</div>
				<div class="w-mdl2">
				<form name="searchform" METHOD="POST" id="searchform" action="${base}/help/showhelp.jsp" target="_blank">
					<div class="box">
						<div class="heading fs1 fw1"><div class="icn icn2">&nbsp;</div><fmt:message key='efetion.webpage.helpcenter.title'/></div>
						<div class="content">
							<div class="search">
								<div>
								<input type="text" id="q" name="q" class="text"/><input type="submit" class="button"  value="<fmt:message key='efetion.webpage.websearch.searchbutton'/>"/>
								</div>
								<a href="${base}/help/showhelp.jsp?id=abc_001" target="_blank" class="fc8">通讯录</a>　<a href="${base}/help/showhelp.jsp?id=abc_003" target="_blank"  class="fc8">群组</a>　<a href="${base}/help/showhelp.jsp?id=abc_004" target="_blank" class="fc8">常用联系人</a>
							</div>
						</div>
					</div>
					</form>
				</div>
				<div class="w-mdl2">
					<div class="box">
						<div class="heading fs1 fw1"><div class="icn icn3">&nbsp;</div><fmt:message key='efetion.webpage.link.title'/></div>
						<div class="content q-nav">
							<ul class="clearfix">
								   	<c:choose>
										<c:when test="${empty URLLIST }">
											<table>
														<tr>

															<td colspan="20" align="center">
																暂无记录
															</td>
														</tr>
													</table>
										</c:when>
										<c:otherwise>
											<c:forEach items="${URLLIST }" var="urlInfo" varStatus="status">
									       <li class="icn">
									               <a href="<c:choose>
											       <c:when test="${ fn:substring(urlInfo.url,0,7) eq 'http://' || fn:substring(urlInfo.url,0,8) eq 'https://' || fn:substring(urlInfo.url,0,6) eq 'ftp://' || fn:substring(urlInfo.url,0,7) eq 'mailto:'}">
											       <c:out value="${ urlInfo.url}" escapeXml="true"></c:out>
											       </c:when>
											       <c:otherwise>
											       <c:out value="http://${urlInfo.url}" escapeXml="true"></c:out>
											       </c:otherwise>
										          </c:choose>"  target="_blank" class="fc8">
										           <c:choose>
											       <c:when test="${ fn:length(urlInfo.displayUnit) gt 10 }">
											       <c:out value="${ fn:substring(urlInfo.displayUnit,0,10)}" escapeXml="true"></c:out>
											       </c:when>
											       <c:otherwise>
											       <c:out value="${urlInfo.displayUnit}" escapeXml="true"></c:out>
											       </c:otherwise>
										           </c:choose>										          
									             </a></li>
											</c:forEach>
										</c:otherwise>
									</c:choose>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col2">
				<div class="w-mdl4">
					<div class="heading h1 fs1 fw1"><fmt:message key='efetion.webpage.downloadfetion.title'/></div>
					<div class="content down">
						<div class="icn icn4"><a href="#">下载最新版本</a></div>
						<div class="icn icn5"><a href="#">使用说明</a></div>
						<div class="icn icn6"><a href="#">用户手册</a></div>
					</div>
				</div>
				<div class="w-mdl4">
					<div class="heading h2 fs1 fw1"><fmt:message key='efetion.webpage.expcenter.title'/></div>
					<div class="content ue">
						<p class="fc7">用户体验中心用户体验中心用户体验中心用户体验中心</p>
					</div>
				</div>
				<div class="w-mdl4" id="tabs">
				<div class="heading h3 fs1 fw1"><fmt:message key='efetion.webpage.websearch.title'/></div>
                    <ul class="tab_ul">
                        <li><a href="#tabs-1" hidefocus="hidefocus" ><fmt:message key='efetion.webpage.websearch.google'/></a> |</li>
                        <li><a href="#tabs-2" hidefocus="hidefocus" ><fmt:message key='efetion.webpage.websearch.yahoo'/></a> |</li>
                        <li><a href="#tabs-3" hidefocus="hidefocus" ><fmt:message key='efetion.webpage.websearch.baidu'/></a></li>
                    </ul>
                    <div id="tabs-1">
                        <!--google search star-->
							<table CELLPADDING="2" CELLSPACING="2">
								<tr>
									<td align="left">
										<img src="${base}/images/efetionpage/webSearch/google.gif"
											class="searchLogo" />
									</td>
								</tr>
								<tr>
									<td align="left">
										<input type="text" class="searchInput" id='googlesearch'
											accesskey="t" maxlength="150" />
										<input type="button" value="<fmt:message key='efetion.webpage.websearch.searchbutton'/>" class="button"
											onclick="searchclick('google')" />
									</td>
								</tr>
							</table>
							<!--google search end-->
                    </div>
                    <div id="tabs-2">
                        <!--yahoo search start-->
							<table CELLPADDING="2" CELLSPACING="2">
								<tr>
									<td align="left">
										<img src="${base}/images/efetionpage/webSearch/yahoo.gif"
											class="searchLogo" />
									</td>
								</tr>
								<tr>
									<td align="left">
										<input type="text" class="searchInput" id='yahoosearch'
											accesskey="t" maxlength="150" />
										<input type="button" value="<fmt:message key='efetion.webpage.websearch.searchbutton'/>" class="button"
											onclick="searchclick('yahoo')" />
									</td>
								</tr>
							</table>

							<!--yahoo search end-->
                    </div>
                    <!--baidu search star-->
                    <div id="tabs-3">
							<table CELLPADDING="2" CELLSPACING="2">
								<tr>
									<td align="left">
										<img src="${base}/images/efetionpage/webSearch/baidu.gif"
											class="searchLogo" />
									</td>
								</tr>
								<tr>
									<td align="left">
										<input type="text" class="searchInput" id='baidusearch'
											accesskey="t" maxlength="150" />
										<input type="button" value="<fmt:message key='efetion.webpage.websearch.searchbutton'/>" class="button"
											onclick="searchclick('baidu')" />
									</td>
								</tr>
							</table>

						</div>
                    <!--baidu search end-->
                </div>
			</div>
			<div class="col1">
				<div class="w-mdl3" id=>
					<div class="heading"><a class="fc3 fr" hidefocus="hidefocus" target="_blank" href="${base}/public/common/contentmanager/content/displayWebContentInfos!showMore.portal?c=${c}&contype=2&page=1"><fmt:message key='efetion.webpage.showmore'/>&gt;&gt;</a><span class="fw1 fc3 fs1"><fmt:message key='efetion.webpage.enotice.title'/></span></div>
					<ul>
                     	<c:choose>
							<c:when test="${empty NOTICELIST }">
								<table>
														<tr>

															<td colspan="20" align="center">
																暂无记录
															</td>
														</tr>
													</table>
							</c:when>
							<c:otherwise>
								<c:forEach items="${NOTICELIST }" var="continfo"
									varStatus="status">
						       <li class="icn"><span class="fr fc6">
						       <fmt:formatDate value="${continfo.releasedate}" type="date" pattern="yyyy-MM-dd"/>
												</span><a href="${base}/public/common/contentmanager/content/displayWebContentInfos.portal?c=${c}&contentId=${continfo.identifier }" target="_blank" class="fc7">
							       <c:choose>
								       <c:when test="${ fn:length(continfo.title) gt 16 }">
								       <c:out value="${ fn:substring(continfo.title,0,16)}" escapeXml="true"></c:out>
								       </c:when>
								       <c:otherwise>
								       <c:out value="${continfo.title}" escapeXml="true"></c:out>
								       </c:otherwise>
							       </c:choose>
						       </a></li>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</ul>
				</div> 
				<div class="w-mdl3">
					<div class="heading"><a class="fc3 fr" hidefocus="hidefocus" target="_blank" href="${base}/public/common/contentmanager/content/displayWebContentInfos!showMore.portal?c=${c}&contype=1&page=1"><fmt:message key='efetion.webpage.showmore'/>&gt;&gt;</a><span class="fw1 fc3 fs1"><fmt:message key='efetion.webpage.enews.title'/></span></div>
					<ul>
					   	<c:choose>
							<c:when test="${empty NEWLIST }">
								<table>
														<tr>

															<td colspan="20" align="center">
																暂无记录
															</td>
														</tr>
													</table>
							</c:when>
							<c:otherwise>
								<c:forEach items="${NEWLIST }" var="continfo"
									varStatus="status">
						       <li class="icn"><span class="fr fc6">
						       <fmt:formatDate value="${continfo.releasedate}" type="date" pattern="yyyy-MM-dd"/>
												</span><a href="${base}/public/common/contentmanager/content/displayWebContentInfos.${actionExt}?c=${c}&contentId=${continfo.identifier }" target="_blank" class="fc7">
							       <c:choose>
								       <c:when test="${ fn:length(continfo.title) gt 16 }">
								       <c:out value="${ fn:substring(continfo.title,0,16)}" escapeXml="true"></c:out>
								       </c:when>
								       <c:otherwise>
								       <c:out value="${continfo.title}" escapeXml="true"></c:out>
								       </c:otherwise>
							       </c:choose>
						       </a></li>
								</c:forEach>
							</c:otherwise>
						</c:choose>

					</ul>
				</div>
				<div class="w-mdl3"> 
					<div class="heading"><a class="fc3 fr" hidefocus="hidefocus" href="${base}/public/common/epage/showsysannouce!showMore.${actionExt}?c=${c}" target="_blank""><fmt:message key='efetion.webpage.showmore'/>&gt;&gt;</a><span class="fw1 fc3 fs1"><fmt:message key='efetion.webpage.systemannounced.title'/></span></div>
					<ul>
                     	<c:choose>
							<c:when test="${empty ANNOUCELIST }">
								<table>
														<tr>

															<td colspan="20" align="center">
																暂无记录
															</td>
														</tr>
													</table>
							</c:when>
							<c:otherwise>
								<c:forEach items="${ANNOUCELIST }" var="sysAnnouce"
									varStatus="status">
							    <c:choose>		
								<c:when test="${status.index lt 5 }">
								<li class="icn"><span class="fr fc6">
								<fmt:formatDate value="${sysAnnouce.createTime}" type="date"
												pattern="yyyy-MM-dd" />
								</span><a href="${base}/public/common/epage/showsysannouce.${actionExt}?c=${c}&sysnnouceid=${sysAnnouce.identifier}" target="_blank" class="fc7">
 <c:choose>
								       <c:when test="${ fn:length(sysAnnouce.title) gt 16 }">
								       <c:out value="${ fn:substring(sysAnnouce.title,0,16)}" escapeXml="true"></c:out>
								       </c:when>
								       <c:otherwise>
								       <c:out value="${sysAnnouce.title}" escapeXml="true"></c:out>
								       </c:otherwise>
							       </c:choose>
								  </a></li>
							      </c:when>
							     </c:choose>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
			</div>
		</div>
		
		
		<div class="footer">
		<p class="fc1"><fmt:message key='efetion.webpage.copyright.text'/></p>
		<div class="logo">&nbsp;</div>
	    </div>
	</div>	
  </body>
</html>
