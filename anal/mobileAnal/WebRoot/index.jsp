<%@ include file="/include/docType.jsp"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<ul>
	<li><a href='${ctx}/webservice/' target='_blank'>显示系统中所有的webservice</a></li>
	
	<br/><br/>
	<li><a target='_blank' href="<%=request.getContextPath()%>/common/httpclient/init.talent">HTTP页面处理工具</a></li>
	
	<br/><br/>
	<li><a target='_blank' href="<%=request.getContextPath()%>/common/httpclient/init.talent?suffix=report">mobile 页面处理工具</a></li>
	
	<br/><br/>
	<li><a target='_blank' href="<%=request.getContextPath()%>/common/httpclient/init.talent?suffix=login">登录</a></li>
	
	<br/><br/>
	<li><a target='_blank' href="<%=request.getContextPath()%>/common/httpclient/init.talent?suffix=getYears">返回年份</a></li>
	
	
	<br/><br/>
	<li><a target='_blank' href="<%=request.getContextPath()%>/common/httpclient/init.talent?suffix=ComprehensiveGetList">综合分析列表左侧列表</a></li>
	
		<br/><br/>
	<li><a target='_blank' href="<%=request.getContextPath()%>/common/httpclient/init.talent?suffix=ComprehensiveDetail">综合分析列表详细数据</a></li>
	
	
</ul>



