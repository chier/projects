<%@ include file="/include/docType.jsp"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>HTTP页面处理工具</title>

<%@ include file="/include/meta.jsp"%>
<style>
body {
	margin: 0px;
}
</style>

<!-- json -->
<%@ include file="/include/import_res/json_js.jsp"%>
<!-- talent -->
<%@ include file="/include/import_res/talent_js.jsp"%>
<!-- jquery -->
<%@ include file="/include/import_res/jquery_js.jsp"%>

<!-- jquery'easyui -->
<%@ include file="/include/import_res/easyui_js.jsp"%>
<!-- query engine -->

<%@ include file="/include/import_res/baseweb_js.jsp"%>

</head>
<body class="easyui-layout">
	<div region="north" split="true" style='height: 400px' title="Http+json调试工具">
		<form id='urlForm' action='<%=request.getContextPath()%>/common/httpclient/getHtml.talent' method='POST'>
			<input type='hidden' name='html' id='html'/>
			<!-- <div class='tt_panelHead' id='tt_panelHead'><span>页面分析工具</span></div> -->
			<table class='tt_queryTable' id='tt_queryTable'>
				<tr>
					<td class='tt_queryTable_label'><label for="id">方法</label></td>
					<td class='tt_queryTable_input'>
						<select id='tt_method' name='tt_method'>
							<option value='post'>post</option>
							<option value='get'>get</option>
						</select>
					</td>
					
					<td class='tt_queryTable_label'><label for="name">URL</label></td>
					<td  class='tt_queryTable_input'>
						<textarea id='tt_request_url'  name='tt_request_url' style='width:400px'>http://localhost:8080${ctx}/controller/report.talent</textarea>
					</td>
				</tr>
				
				<tr>
					<td class='tt_queryTable_label'><label for="id">代理</label></td>
					<td class='tt_queryTable_input'>
						host:<input id='' style='width:140px' name='proxyHostName'/>
						port:<input id='' style='width:50px' name='proxyPort' value='8080'/>
						schemeName:<input id='' style='width:50px' value='http' name='proxySchemeName'/>
					</td>
					
					<td class='tt_queryTable_label'><label for="id">cookies</label></td>
					<td class='tt_queryTable_input'>
						<input type='text' id='cookies1' style='width:50px' name='cookies'/>
						<input type='text' id='cookies2'  style='width:50px' name='cookies'/>
						<input type='text' id='cookies3' style='width:50px' name='cookies'/>
						<input type='text' id='cookies4' style='width:50px' name='cookies'/>
						<input type='text' id='cookies5' style='width:50px' name='cookies'/>
					</td>
				</tr>
				
				<tr>
					<td class='tt_queryTable_label'><label for="id">请求体</label></td>
					<td colspan='1' class='tt_queryTable_input'>
						<textarea style='width:400px' name='tt_requestbody'>{"op":"Comprehensive.getList","source_id":"1","view_id":"2","data":{"years":2012}}</textarea>
					</td>
					
					<td class='tt_queryTable_label'><label for="id">请求头参数</label></td>
					<td colspan='1' class='tt_queryTable_input'>
						<span style='display: inline-block'>
						<table style='width:500px' class='talent_grid_table' id='headerTable'>
							<thead>
								<tr><td>name</td><td>value</td><td>操作</td></tr>
							</thead>
							<tbody id='headerTableBody'>
								<tr>
									<td><input  style='width:150px' name='header_name' value=''/></td>
									<td><input style='width:150px' name='header_value' value=''/></td>
									<td>
										<a href="javascript:addField('headerTable', 'headerTableBody', 'header');">添加字段</a>
										<a href="javascript:removeField('headerTable', 'headerTableBody', 'header');">删除选中</a>
									</td>
								</tr>
							</tbody>
						</table>
						</span>
					</td>
				</tr>
				
				<tr>
					<td colspan='4' class='tt_queryTable_btn'>
						<a href="javascript:getHtml('urlForm', false);" class='yui3-button '>抓取响应</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div region="center" split="true" title="抓取到的页面"
		style="padding: 0px;"  class='tt_main'>
		<div id='html_container'>
			 <iframe src="" name='html_container_iframe_name' id="html_container_iframe" width="100%" height="600px"></iframe>
		</div>
	</div>
	
	

</body>

<script type="text/javascript" src="<%=request.getContextPath()%>/app-res/common/httpclient/httpclientInit.js"></script>

<script type="text/javascript">
var contextpath = '<%=request.getContextPath()%>';


</script>
</html>
