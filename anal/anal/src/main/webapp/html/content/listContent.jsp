<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.cmcc.common.util.StringUtil"%>
<%@ page import="com.cmcc.common.util.date.DateUtil"%>
<%@ page import="com.cmcc.framework.model.content.ContentInfo"%>
<%@ page import="com.cmcc.framework.controller.formbean.ContentType"%>

<%@ page import="java.util.List"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/meta.jsp"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${base}/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<script type="text/javascript" src="${base}/js/check_all.js"></script>
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<title></title>
		<script>
		
		
		function searchContent(){
			 var theForm=$("#listContentForm");
	          var searchname = document.getElementById("searchname");
	          if(searchname==""){
	              return false;
	          }
	          theForm.attr("action","${base}/manage/contentmanage/content/listContentInfos.${actionExt}?groupId=1&page=${pageInfo.page}&ctid=${ctid}");
	          theForm.submit();
		
		}
		
		function searchnameBlur(){
		if(document.getElementById("searchName").value==null||document.getElementById("searchName").value==""){
			document.getElementById("searchName").value="输入标题查找";
			document.getElementById("searchName").style.color='#949494';
		}
	}
			function searKeyDown(e){
				temp = window.event||e ;
				var coded=temp.keyCode||temp.which; 
				if(coded==13){
					searchContent();
				}
			}
			$("document").ready(function(){
				searchTextStyle(document.getElementById("searchName"),"输入标题查找");
			});
		</script>
	</head>
	<%
				List<ContentInfo> contentList = (List<ContentInfo>) request
				.getAttribute("contentList");

		List<ContentType> typeList = (List<ContentType>) request
				.getAttribute("typeList");
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	%>
	<body>
		<div id="main">
			<div class="title">
				<h4>
					<a href="${base}/html/adminSetting/index.jsp">管理设置</a> >><a href="${base}/manage/contentmanage/content/listContentInfos.${actionExt}?groupId=1"> 内容管理</a>
				</h4>
			</div>
			<form name="listContentForm" id="listContentForm" action=""
				method="post">
				<input type="hidden" id="ctid" name="ctid" vlaue="${ctid}" />
				<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#E5E8ED">
					<tbody>
						<tr>
							<td width="50%">
								<div>
									<span class="table-navdiv1"> <input type="button"
											class="bot1other"
											value="<fmt:message key='efetionmanage.common.add'/>"
											onclick="addData();" /> &nbsp;&nbsp; <input type="button"
											class="bot1other"
											value="<fmt:message key='efetionmanage.common.delete'/>"
											onclick="delData();" /> </span>
								</div>
							</td>
							<td width="42%">
								<div align="right" style="margin-right:5px;">
									<input style="cursor: text;" onkeydown="searKeyDown(event)"
										onblur="searchnameBlur()" style="color:#949494;" type="text"
										id="searchName" name="searchName" value="输入标题查找" />
									<input type="image" src="${base }/images/so.gif"
										onclick="javascript:searchContent();return false;" />
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<table cellpadding="2" cellspacing="1" border="0" class="tables">
					<thead>
						<tr>
							<td width="4%" align="center">
								<input type="checkbox" id="jck"
									onclick="checkAll(this.form,'jck')" />
							</td>
							<th width="33%">
								<fmt:message
									key="efetionmanage.framework.contentManager.contentTitle" />
							</th>
							<th width="60">
								栏目
							</th>
							<c:if test="${fn:length(groupId) <= 1}">
								<th width="10%">
									<fmt:message
										key="efetionmanage.framework.contentManager.contentState" />
								</th>
							</c:if>
							<th width="10%">
								<fmt:message
									key="efetionmanage.framework.contentManager.contentAuthor" />
							</th>
							<th width="15%">
								<fmt:message
									key="efetionmanage.framework.contentManager.contentReleaseDate" />
							</th>
							<th width="10%">
								操作
							</th>
						</tr>
					</thead>
					<tbody>
						<%
						if (contentList == null || contentList.size() == 0) {
						%>
						<tr>
							<td colspan="7" align="center">
								暂无记录
							</td>
						</tr>
						<%
								} else {

								ContentInfo content = null;
								for (int j = 0; j < contentList.size(); j++) {
									content = contentList.get(j);
						%>
						<tr>
							<td align="center">
								<input type="checkbox" name="ids"
									value="<%=content.getIdentifier()%>" />
							</td>
							<td>
								<a
									href="${base}/public/common/contentmanager/content/contentInfoView.${actionExt}?contentId=<%=content.getIdentifier()%>"
									title="<%=StringUtil
									.str2DefaultTextHtml(content.getTitle())%>">
									<%
									if (content.getTitle().length() > 40) {
									%> <%=StringUtil.str2DefaultTextHtml(content
										.getTitle().substring(0, 40))%> <%
 } else {
 %>
									<%=StringUtil.str2DefaultTextHtml(content
										.getTitle())%> <%
 }
 %> </a>
							</td>
							<td width="60">
								<%
										for (ContentType t : typeList) {
										if (content.getCtId().equals(t.getCtId())) {
								%>
								<a
									href="${base}/manage/contentmanage/content/listContentInfos.${actionExt}?groupId=<%=t.getCtId()%>&ctid=<%=t.getCtId()%>"><%=t.getCtName()%></a>
								<%
											}
											}
								%>
							</td>
							<c:if test="${fn:length(groupId) <= 1}">
								<td align="center">
									<%
												switch (content.getState().intValue()) {
												case 0:
									%>
									<fmt:message
										key="efetionmanage.framework.systemMessage.status2" />
									<%
												break;
												case 1:
									%>
									<fmt:message
										key="efetionmanage.framework.contentManager.state1" />
									<%
												break;
												case 2:
									%>

									<fmt:message
										key="efetionmanage.framework.contentManager.state2" />
									<%
												break;
												case 3:
									%>

									<fmt:message
										key="efetionmanage.framework.contentManager.state3" />
									<%
												break;
												case 4:
									%>
									<fmt:message
										key="efetionmanage.framework.contentManager.state4" />
									<%
												break;
												case 5:
									%>
									<fmt:message
										key="efetionmanage.framework.contentManager.state5" />
									<%
									default:
									%>
									<fmt:message
										key="efetionmanage.framework.contentManager.state.no" />
									<%
									}
									%>

								</td>
							</c:if>
							<td>
								<%=StringUtil.str2DefaultTextHtml(content
									.getAuthor())%>
							</td>
							<td align="center">
								<%=DateUtil.toString(content.getReleasedate(),
									dateformat)%>
							</td>
							<td align="center">
								<a href="#" title="编辑"
									onclick="updateData('<%=content.getIdentifier()%>');">编辑</a>
							</td>
						</tr>
						<%
							}
							}
						%>
					</tbody>
				</table>
			</form>
			<div class="pages">
				<%@ include file="/commons/page.jsp"%>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
			var listContentForm = document.getElementById("listContentForm");
	function goPage(pageNum) {

		if(isInteger(pageNum)){
			listContentForm.action="${base}/manage/contentmanage/content/listContentInfos.${actionExt}?page=" + pageNum + "&groupId=${groupId}&ctid=${ctid}" ;
			listContentForm.submit();
		}else{
			$.prompt('<fmt:message key="efetionmanage.common.not_integer"/>');
		}
	}
	function addData(){
		listContentForm.action = "${base}/manage/contentmanage/content/contentInfoOperator!addInput.${actionExt }?groupId=${groupId }";
		listContentForm.submit();
	}
	
	function delData(){
		if(checkboxCount(listContentForm.ids)<1){
			var txt = '<fmt:message key="efetionmanage.common.select_one"/>';
			$.prompt(txt,{
					alertType : 'msg'
			});
			return false;
		}else{
			listContentForm.action="${base }/manage/contentmanage/content/contentInfoOperator!removeInput.${actionExt }?groupId=${groupId}";
			removeContent();
		}
		//del(listContentForm,'ids',,"<fmt:message key="delError.msg1"/>","<fmt:message key="delOrNot.msg2"/>");
	}
	
	function updateData(conid){
		listContentForm.action = "${base}/manage/contentmanage/content/contentInfoOperator!modifyInput.${actionExt }?groupId=${groupId}&conid="+ conid;
		listContentForm.submit();
	}
	
	function removeContent(){
			var txt = '<fmt:message key="delOrNot.msg2"/>';
				$.prompt(txt,{ 
					buttons:{确定:true, 取消:false},
					alertType : 'ask',
					callback: function(v,m,f){
						if(v){
							var tmp = "";
							if(document.getElementsByName("ids").length > 1){
								var gids = document.getElementsByName("ids");
								for(var i = 0 ; i < gids.length ; i ++){
									if(gids[i].checked){
										tmp += gids[i].value + ",";
									}
								}
							}else{
								tmp= document.getElementsByName("ids")[0].value;
							}
							$.post('contentInfoOperator!removeInput.${actionExt}',{groupId:${groupId},cIdstr:tmp}, function(data){
								if(data == 'true'){
									jQuery.ImpromptuClose();
									top.mainIframe.location=top.mainIframe.adminRight.location.href;
								}else{
									var txterror = "删除元素错误";
									$.prompt(txterror,{
										alertType : 'msg'
									});
								}							
							});				 
						}
						else{jQuery.ImpromptuClose();}						
					}
				});
	}
	
	
</script>
