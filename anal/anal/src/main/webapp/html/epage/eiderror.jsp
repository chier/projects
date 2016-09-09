<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/html/epage/epagehead.jsp" %>
<html>
	<head>
		<title><fmt:message key='efetion.webpage.title.publicpage'/></title>
        <%@ include file="/html/epage/meta.jsp" %>
        <link rel="stylesheet" type="text/css" href="${base}/css/epage/style.css?v=24" media="screen" />
	</head>
	<script language="javascript" type="text/javascript"> 
		  function   closeWindow()   { 
		  window.open('','_parent',''); 
		  window.close(); 
		  } 
		  
	  </script>    
	<style type="text/css">
	.error_info{
		position:relative;
		top:130px;
		width:360px;
		height:300px;
		margin:0 auto;
	}
		body {
			background-color: #EDFBFE;
			background-position: center;
			font-size: 12px;
		}
		.erro {
			background-image: url(${base}/css/epage/images/03.jpg);
			background-repeat: no-repeat;
			height: 115px;
			width: 100%;
			text-align: center;
			background-position: center;
			padding-top: 10px;
			padding-left: 0px;
		}
		.errored {
			font-size: 14px;
			line-height: 28px;
			font-weight: bold;
			color: #FF0000;
		}
		.erro p {
			font-size: 12px;
			line-height: 28px;
			padding-left: 60px;
		}

</style>
	</style>
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
		 <div class="area-err">
			<div class="erro">
			   		  <dl>
			   		  <dt class="errored"><fmt:message key="efetion.webpage.errortitle"/></dt>
			       	  <dd><fmt:message key="efetion.webpage.eiderrormsg"/></dd>
			       	  <dd>&nbsp;</dd>
			       	  <dd><a href="javascript:closeWindow()" ><fmt:message key="efetion.webpage.closewindo"/></a></dd>
			   	 	 </dl>
			</div>
		 </div>
		<div class="footer">
		<p class="fc1"><fmt:message key='efetion.webpage.copyright.text'/></p>
		<div class="logo">&nbsp;</div>
	    </div>
	</div>
<body >
</html>