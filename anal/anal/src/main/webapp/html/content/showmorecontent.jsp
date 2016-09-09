<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/html/epage/epagehead.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<%@ include file="/html/epage/meta.jsp" %>
		<link rel="stylesheet" type="text/css" href="${base}/css/epage/openpage/css/style.css?v=24" media="screen" />
		<script src="${base }/js/public/jquery-impromptu.js?v=2.3" type="text/javascript"></script>
        <link href="${base }/commons/impromptu/impromptu.css" type="text/css" rel="stylesheet"/>
		<script type="text/javascript">
			function goPage(pageNum) {
			var listContentForm = document.getElementById("listContentForm");
				if(isInteger(pageNum, "<fmt:message key="notInteger"/>")){
					listContentForm.action="${base}/public/common/contentmanager/content/displayWebContentInfos!showMore.${actionExt}?c=${c}&contype=${conType}&page=" + pageNum;
					listContentForm.submit();
				}
			}
			function isInteger(strNum, msg) {
				if (strNum.search(/^(0|[1-9]\d*)$/) != -1) {
					return true;
				} else {
					if(typeof(msg)=='string'){
							alert(msg);
					}
					
					return false;
				}
			}			
	</script>
		<title>
					<c:choose>
						<c:when test="${conType eq 1 }"><fmt:message key="efetionmanage.framework.contentinfo.new.title"/>列表</c:when>
						<c:when test="${conType eq 2 }">通知公告列表</c:when>
						<c:when test="${conType eq 3 }">电子调查列表</c:when>
					</c:choose>
		</title>
	</head>
	<body id="center">
	<c:set var="indexitem" value="0"></c:set>
    <form name="listContentForm" id="listContentForm" action="" 	method="post">
	<table class="tables" border="0" cellpadding="0" cellspacing="0">
	<thead>
    	<tr>
        	<td height="130">
            	<div class="top">
                	<dl>
                        <dt>
				      <c:choose>
							<c:when test="${empty sessionScope.COMPANYINFO || sessionScope.COMPANYINFO.corpName==''}">
							<fmt:message	key="efetion.webpage.topic.default" />
							</c:when>
							<c:otherwise>
							<c:out value="${sessionScope.COMPANYINFO.corpName}" escapeXml="true"></c:out>
							</c:otherwise>
					 </c:choose>                        </dt>
                        <dd><div class="tm fc5" id='nowTime'></div>	</dd>
                    </dl>
              	</div>
            </td>
        </tr>
    </thead>
    <tbody>
    	<tr>
        	<td class="warp">
            	<div class="main">
                	<div class="list">
                    	<dl>
                    	<dt>
					<c:choose>
						<c:when test="${conType eq 1 }"><fmt:message key="efetionmanage.framework.contentinfo.new.title"/>列表</c:when>
						<c:when test="${conType eq 2 }">通知通告列表</c:when>
						<c:when test="${conType eq 3 }">电子调查列表</c:when>
					</c:choose>
                    	</dt>
                    	
                            <c:choose>
							<c:when test="${empty resultList }">
							<dd>
													<table>
														<tr>

															<td colspan="20" align="center">
																暂无记录
															</td>
														</tr>
													</table>

												</dd>
							</c:when>
							<c:otherwise>
								<c:forEach items="${resultList }" var="content"	varStatus="status">
								<dd><a href="${base}/public/common/contentmanager/content/displayWebContentInfos.${actionExt}?c=${c }&contentId=${content. identifier}"
												target="_blank">
												<c:out value="${content.title }" escapeXml="true"></c:out>
												</a><span class="more"><fmt:formatDate value="${content.releasedate}" type="date"
												pattern="yyyy-MM-dd" /></span></dd>
												<c:set var="indexitem" value="${status.index }"></c:set>
								</c:forEach>
							</c:otherwise>
						</c:choose>
                        </dl>
					    <c:if test="${indexitem < pageInfo.pageSize-1}">
						<c:forEach var="i" begin="${indexitem}" end="${pageInfo.pageSize-1}" step="1"> 
						 <br>
						</c:forEach >
						</c:if>	
                        <br>
                        
                        <c:if test="${pageInfo.resultCount > pageInfo.pageSize}">
                        <hr align="center" >
                        <%@ include file="/commons/page.jsp"%>
                        </c:if>
                    </div>
                </div>
            </td>
        </tr>
    </tbody>
    <tfoot>
    	<tr>
        	<td class="footer">
    			<div class="copy">
                	<fmt:message key='efetion.webpage.copyright.text'/>
                </div>
    		</td>
        </tr>
    </tfoot>
</table>
	</form>
	</body>
</html>

