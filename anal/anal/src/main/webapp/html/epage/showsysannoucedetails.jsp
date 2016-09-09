
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/html/epage/epagehead.jsp" %>
<head>
		<%@ include file="/html/epage/meta.jsp" %>
		<title>${SYSANNOUCE.title }</title>
<link rel="stylesheet" type="text/css" href="${base}/css/epage/openpage/css/style.css?v=24" media="screen" />
</head>

<body>
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
							<pre><c:out value="${sessionScope.COMPANYINFO.corpName}" escapeXml="true"></c:out><pre>
							</c:otherwise>
					    </c:choose>
                        </dt>
                        <dd><div class="tm fc5" id='nowTime'></div>	</dd>
                    </dl>
              	</div>
            </td>
        </tr>
    </thead>
    <tbody>
    	<tr>
        	<td class="warp" vlain="top">
            	<div class="main">
                	<pre><h2><c:out value="${SYSANNOUCE.title }" escapeXml="true"></c:out></h2></pre>
                    <div class="info">                    
                    <fmt:message
						key='efetionmanage.framework.contentManager.contentReleaseDate' />
					:
					<fmt:formatDate value="${SYSANNOUCE.createTime}" type="date"
						pattern="yyyy-MM-dd" /></div>
                  <div class="content">
                  <pre><c:out value="${SYSANNOUCE.anouceCountent }" escapeXml="true"></c:out> </pre>
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
</body>
</html>