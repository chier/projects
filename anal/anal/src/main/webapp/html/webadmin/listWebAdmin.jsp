<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow-x:hidden;overflow-y:auto;">
	<head>
		<title>${requestScope.platformTitle}</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<STYLE TYPE="text/css">
input,button {
	cursor: hand
}
</STYLE>
		<script language="Javascript">
	$(function(){
		$('a,input[type="image"],input[type="button"]').bind('focus',function(){
			if(this.blur){ //如果支持 this.blur
				this.blur();
			}
		});
	});	
	function goPage(pageNum) {
		if(isInteger(pageNum, "<fmt:message key="efetionmanage.common.not_integer"/>")){
		
			document.getElementById("webAdminForm").action="${base}/manage/safemanage/webadmin/listWebAdminAction.${actionExt}?page=" + pageNum;
			if(document.getElementById("searchName").value=="输入用户名查找"){
					document.getElementById("searchName").value="";
			}
			document.getElementById("webAdminForm").submit();
		}
	}
	function chose(ids){
		if(document.getElementById("choose").checked==true){
			checkall(ids);
		}else{
			checknull(ids);
		}
	}
	function toAddAdmin(){
		document.getElementById("webAdminForm").action="${base }/manage/safemanage/webadmin/createWebAdminAction!toCreate.${actionExt}";
		document.getElementById("webAdminForm").submit();
	}
	function searchAdmin(){
		 var theForm=$("#webAdminForm");
          var searchname = document.getElementById("searchname");
          if(searchname==""){
              return false;
          }
          theForm.attr("action","${base }/manage/safemanage/webadmin/listWebAdminAction.${actionExt}?page=${pageInfo.page}");
          theForm.submit();
	
	}
	function modifyAdmin(adminId){
		document.getElementById("webAdminForm").action="${base }/manage/safemanage/webadmin/modifyWebAdminAction!toModify.${actionExt}?page=${pageInfo.page}&adminId="+adminId;
		document.getElementById("webAdminForm").submit();
		
	}
	function removeAdmin(){
	
		if(checkboxCount(document.getElementById("webAdminForm").ids)<1 ){
			$.prompt("  请选择要删除的管理员帐号",{buttons:{确定:true,取消:false},alertType:'msg'});
			return false;
		}else{
				$.prompt("  确定删除选中的帐号吗？",{
					buttons:{确定:true,取消:false},
					alertType:'ask',
					submit:function(v,m,f){
						if(v){
							var panduan = document.getElementsByName("ids");
							var str="";
							for(var i=0;i<panduan.length;i++)
							{
								if(panduan[i].checked == true)
								{
									var hh=panduan[i].value.split(";");
									str+=hh[0]+";";
								}
							}
		
							$.post('${base }/manage/safemanage/webadmin/removeWebAdminAction.${actionExt}?page=${pageInfo.page}',{removeids:str}, function(data){
								if(data == 'true'){
									jQuery.ImpromptuClose();
									checknull(document.getElementsByName("ids"));
									if(trim(document.getElementById("searchName").value) == "请输入用户名查找"){
										document.getElementById("webAdminForm").action="${base}/manage/safemanage/webadmin/listWebAdminAction.${actionExt}?page=${pageInfo.page}";
										document.getElementById("webAdminForm").submit();
									}else{
										searchAdmin();
									}
								}else{
									jQuery.ImpromptuClose();
									$.prompt("  删除操作失败",{buttons:{确定:true,取消:false},alertType:'msg'});
								}
							});
						}else{
							jQuery.ImpromptuClose();
						}
					}});
		}
	}
	function clearcheck(){
		var els=document.getElementsByTagName("input");
		for(var i=0 ;i<els.length; i++){
			if(els[i].type=="checkbox"){
				els[i].checked=false;
			}
		}
	}
	window.onload=function(){
		clearcheck();
		searchTextStyle(document.getElementById("searchName"),"输入用户名查找");
		var a = '${requestScope.adminList}';
		if (a!=null&&a!=""){
			document.getElementById('toPage').onkeyup=function(e){
				temp = window.event||e ;
				var coded=temp.keyCode||temp.which; 
				if(coded==13){
					goPage(document.getElementById('toPage').value);
				}
			};
		}
	}
	function searchnameBlur(){
		if(document.getElementById("searchName").value==null||document.getElementById("searchName").value==""){
			document.getElementById("searchName").value="输入用户名查找";
			document.getElementById("searchName").style.color='#949494';
		}
	}
	function searKeyDown(e){
		temp = window.event||e ;
		var coded=temp.keyCode||temp.which; 
		if(coded==13){
			searchAdmin();
		}
	}
	function deleteAllCheck(){
		var count= 0;
		if(document.getElementsByName("ids").length > 0){
			var gids = document.getElementsByName("ids");
			for(var i = 0 ; i < gids.length ; i ++){										
				if(gids[i].checked==false){
					$("#choose").attr("checked",false);
					break;
				}
				count++;
			}
			
			var checkedLength = $("input[name='ids']");
			if(checkedLength.length>0){
			if(count==checkedLength.length){
			$("#choose").attr("checked",true);
	         }
			}
		}
	}
</script>
	</head>
	<body >
		<div id="main" >
			<div class="title" >
				<h4>
						<a href="${base }/html/adminSetting/index.jsp">管理设置</a> 
						&gt;&gt;
						<a href="${base }/manage/safemanage/webadmin/listWebAdminAction.${actionExt}">${deptName}</a>
					
				</h4>
			</div>

			<form name="webAdminForm" id="webAdminForm" action="" method="post">
				<input id="groupId" name="groupId" type="hidden" value="${groupId}" />
				<table width="100%" border="0" bgcolor="#E5E8ED" cellspacing="0"
					cellpadding="0">
					<tr height="28">
						<td width="54%">
							<div>
								<span class="table-navdiv1"> <input type="button"
										onclick="toAddAdmin();" class="bot1other"
										value="<fmt:message
										key="efetionmanage.framework.webAdmin.addWebAdmin" />" />
									<input type="button" onclick="removeAdmin();" class="bot1other"
										value="<fmt:message
										key="efetionmanage.framework.webAdmin.removeWebAdmin" />" />
								</span>
							</div>
						</td>
						<td width="42%">
							<div align="right">
								<input style="cursor: text;" onkeydown="searKeyDown(event)"
									onblur="searchnameBlur()" style="color:#949494;" type="text"
									id="searchName" name="searchName" value="输入用户名查找" />
								<input type="image" src="${base }/images/so.gif"
									onclick="javascript:searchAdmin();return false;" />
							</div>
						</td>
					</tr>
				</table>
				<table cellpadding="2" cellspacing="1" border="0" class="tables">
					<thead>
						<tr>
							<td width="2%" align="center">
								<input type="checkbox" name="choose" id="choose" 
									onclick="chose(webAdminForm.ids)" />
							</td>
							<td align="center">
								<fmt:message key="efetionmanage.framework.login.loginId" />
							</td>
							<td align="center">
								管理范围
							</td>
							<td align="center">
								创建时间
							</td>
							<td align="center">
								修改
							</td>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty adminList}">
							<c:forEach items="${adminList}" var="admin">
								<tr>
									<c:if test="${admin ne null}">
										<td>
											<input type="checkbox" id="ids" name="ids"
												value="${admin.identifier}" onclick="deleteAllCheck();"/>
										</td>
									</c:if>
									<td>
										${admin.admin}
									</td>
									<td>
										${admin.modelNames }
									</td>
									<td align="center">
										<c:if test="${admin.createTime!=null}">
											<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${admin.createTime}"/> 
										</c:if>
										<c:if test="${admin.createTime==null}">
											-
										</c:if>
									</td>
									<td align="center">
										<a href="#"
										onclick="javascript:modifyAdmin(${admin.identifier});return false;"
										title="修改帐号"> <img src="${base}/images/ico_edit.gif" /> </a>
									</td>
								</tr>
							</c:forEach>
						</c:if>

					</tbody>
					<c:if test="${empty adminList}">
						<tr>
							<td colspan="20" align="center">
								暂无记录
							</td>
						</tr>
					</c:if>
					<%@ include file="/commons/page.jsp"%>
				</table>
			</form>
	</body>
</html>
