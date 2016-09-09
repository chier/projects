<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>

<!--编辑部门信息的页面-->
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<%@ include file="/commons/meta.jsp"%>
		<script src="${base }/js/utils/Validator.js"></script>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />

		<style type="text/css">
		<!--
		.f-bot{
			 padding:2em 0 0 230px;
		}
div.jqi{ 
	width: 400px; 
	font-family: Verdana, Geneva, Arial, Helvetica, sans-serif; 
	background-color: #FFFFFF; 
	font-size: 11px; 
	text-align: left; 
	border: solid 1px #999999;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	padding: 0px;
	margin-top: -50px;
}
div.jqi .jqimessage{ 
	padding: 10px; 
	line-height: 20px; 
	color: #444444; 
	background-image: url(${base}/images/windosw_bg.gif);
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	background-repeat: repeat-x;
	}
		-->
		.f-textarea {
			border:1px solid #C8D6F0;
			overflow:auto;
		}
		textarea {
			font-size:12px;
		}
		</style>

		<script language="javascript">
		function check(){
			String.prototype.trim = function() {return this.replace(/^\s+|\s+$/g,"");}
			if(document.getElementById("editDepartment").name.value.trim()==""){
				document.getElementById("nameerror").style.display="";
				document.getElementById("nameerror").innerHTML="<font style='font-size: 12px;color: #9d0c0c;'>*<fmt:message key="efetionmanage.common.must_be_not_null" /></font>"
				document.getElementById("editDepartment").name.focus();
				return false;
			}
			var temp =   /^(\s|\w|[\u4E00-\u9FA5])*$/;
			var name= document.getElementById("editDepartment").name.value ;
			if(name.indexOf("/")>=0 || name.indexOf("\"")>=0 || name.indexOf("<")>=0 || name.indexOf(">")>=0 || name.indexOf("&")>=0 || name.indexOf("'")>=0){
				
				document.getElementById("nameerror").style.display="";
				document.getElementById("nameerror").innerHTML="<font style='font-size: 12px;color: #9d0c0c;'>部门名称暂不支持特殊字符/<>\"'&</font>"
				document.getElementById("editDepartment").name.focus();
				return false;
			}else{
				document.getElementById("nameerror").style.display="";
				document.getElementById("nameerror").innerHTML="&nbsp;";
			}
			document.getElementById("editDepartment").submit();
		}
		function checkParent(obj){
		    v_lable = '<span class="ms4">选择上级部门</span>';
			var tt = "leftframe<%=System.currentTimeMillis()%>";
			var iframe_v = '<iframe frameBorder="0" width="250px" height="230" name="'+tt+'" id="'+tt+'" src="${base}/manage/common/department/listDepartmentTree!radio.${actionExt}?readyUpDepId='+$("#identifier").val()+'&t=<%=System.currentTimeMillis()%>" style="display:block;margin:0; auto;border:solid 1px #cad9f0;"></iframe>';
			var tables = '<lable>'+v_lable+'</lable></br></br><table width="100%" border="0" cellpadding="0"><tr><td valign="top" align="left"><span class="fr">组织结构树：</span></br></td><td align="left">'+iframe_v+'</td></tr><tr><td>&nbsp;</td><td class="ms5" align="left">*只能选择不包含<fmt:message key="efetionmanage.common.employee"/>的部门作为上一级部门</td></tr></table>'
			$.prompt(tables,{ 
				buttons:{确定:true, 取消:false},
				submit:function(v,m,f){
					if(v){
					
						var frame_v = document.frames ? document.frames(tt) : document.getElementById(tt).contentWindow;
						if(frame_v.bselected == null){
							jQuery.ImpromptuClose();
						}else{
							var bool = true;
							
							if(frame_v.bselected== "")//增加对未选择的判断.
						    {
						    $.prompt('至少选择一个部门',{buttons:{确定:true},alertType:'msg'});
						    return;
						    }
							
							var parentValue = frame_v.bselected[0].id;
							var parentName = frame_v.bselected[0].name;
							/**
 							$.ajax({url:"${base}/manage/configguide/configGuideAction!checkExistedEmployee.${actionExt}?parentId="+parentValue+"&"+Math.random(),
                                 async:false,
                                 success:function(data){
                                     if(data == '-1'){
                                     	  bool = false;
                                     	 $.prompt('<fmt:message key="efetionmanage.framework.department.department_edit_has_employee_and_department" />',{buttons:{确定:true},alertType:'msg'});
		                                 return false;
		                             }
                                 }});
                            $.ajax({url:"${base}/manage/configguide/configGuideAction!checkLevel10.${actionExt}?parentId="+parentValue+"&"+Math.random(),
                                 async:false,
                                 success:function(data){
                                     if(data == '-1'){
                                     	  bool = false;
                                     	 $.prompt('<fmt:message key="efetionmanage.framework.department.department_edit_10_level" />',{buttons:{确定:true},alertType:'msg'});
		                                 return false;
		                             }
                                 }});**/
                            if(bool){
								document.getElementById("parentName").value=parentName;					
								document.getElementById("parentId").value=parentValue;		
								jQuery.ImpromptuClose();
							}
						}
				}else{
					jQuery.ImpromptuClose();
				}
			}});
		}
		function resetValueAndCheck(){
			document.getElementById("editDepartment").reset();
			document.getElementById("nameerror").innerHTML="&nbsp;";
			document.getElementById("decerror").innerHTML="&nbsp;";
		}
		function goBack()
		{
			document.getElementById("editDepartment").action="${base}/manage/common/department/listDepartments.portal?groupId=${requestScope.groupId}" ;
			
			document.getElementById("editDepartment").submit() ;
		}
		window.onload=function(){
		
		    <c:if test="${not empty requestScope.noAssign}">					
				$.prompt('根部门下不可以添加未分配部门',{buttons:{确定:true},alertType:'msg'});
			</c:if>
		    <c:if test="${not empty requestScope.nameMax}">					
				$.prompt('部门名称长度不能超过50',{buttons:{确定:true},alertType:'msg'});
			</c:if>
		    <c:if test="${not empty requestScope.decMax}">					
				$.prompt('部门描述长度不能超过256',{buttons:{确定:true},alertType:'msg'});
			</c:if>
			<c:if test="${not empty requestScope.gaff10 }">
				$.prompt('<fmt:message key="efetionmanage.framework.department.department_edit_10_level" />',{buttons:{确定:true},alertType:'msg'});
			</c:if>
			<c:if test="${not empty requestScope.hasItem}">					
				$.prompt('<fmt:message key="efetionmanage.framework.department.department_edit_has_employee_and_department" />',{buttons:{确定:true},alertType:'msg'});
			</c:if>
			<c:if test="${not empty requestScope.nameInvalid}">					
				$.prompt('同级中该部门名称已存在，请重新输入',{buttons:{确定:true},alertType:'msg'});
			</c:if>
			document.getElementById("description").onkeydown=function(){
				var s=document.getElementById("description").value.length +1;
				var maxl = 256;
   				if(s>maxl){
   					document.getElementById("description").value=document.getElementById("description").value.substr(0,maxl-1)
   				}
			}
			document.getElementById("description").onkeyup=function(){
				var s=document.getElementById("description").value.length +1;
				var maxl = 256;
   				if(s>maxl){
   					document.getElementById("description").value=document.getElementById("description").value.substr(0,maxl)
   				}
			}
		}
		function checkTextareaSize(obj){
			var max = 256;
			var length = $("#"+obj).val().length;

			if(length > max){
				$("#"+obj).val($("#"+obj).val().substring(0,256));
			}
		}
	</script>
	</head>
	<body>
		<div id="main">
			<div class="title" style="width:98%;">
				<h4>
					<fmt:message key="efetionmanage.framework.department.department" />
					<c:if test="${requestScope.actionName eq 'modifyDepartment'}">
						<fmt:message key="efetionmanage.common.modify" />
					</c:if>
					<c:if test="${requestScope.actionName eq 'createDepartment'}">
						<fmt:message key="efetionmanage.common.add" />
					</c:if>
				</h4>
			</div>
			<form name="editDepartment" id="editDepartment"
				action="${requestScope.actionName}.${actionExt}" method="post">
				<ss:token name="user.token" />
				<input type="hidden" name="groupId" id="groupId"
					value="${requestScope.groupId}" />
				<input type="hidden" name="identifier" id="identifier"
					value="${requestScope.department.identifier}" />
				<input type="hidden" name="oldName" id="oldName"
					value="${requestScope.department.name}" />

				<input type="hidden" name="adminSelectDept" id="adminSelectDept"
					value="${adminSelectDept }" />

				<div class="area-warp" style="width:98%;overflow:hidden;">

					<div class="area-content" style="width:99%; overflow:hidden;">
						<div class="com-info">
							<div id="involidcode"></div>
							<table cellpadding="2">
								<tr>
									<td align="right" width="25%" style="word-break: keep-all;">
										<fmt:message
											key="efetionmanage.framework.department.department_name" />
										：
										<span style="color: #9d0c0c;">*</span>
									</td>
									<td width="25%">
										<input type="text" name="name" id="name" size="18"
											maxlength="50" value="${requestScope.department.name}"
											class="f-text" />

									</td>
									<td colspan="2" nowrap="nowrap" width="50%"
										style="word-break: keep-all;padding-left: 7%">
										<fmt:message
											key="efetionmanage.framework.department.parent_department" />
										：
										<input type="text" name="parentName" class="f-text"
											disabled="disabled" value="${requestScope.parentName }"
											id="parentName" />
										<%-- 
										<input type="button"
											value="<fmt:message key="efetionmanage.framework.department.choose" />"
											class="bot2" onclick="javascript:checkParent(this);" />
										--%>
										<input type="hidden" name="parentId" id="parentId"
											value="${parentId}" />
									</td>

								</tr>
								<tr>
									<td align="left" width="25%">
									</td>
									<td width="25%" height="">
										<div name="nameerror" id="nameerror">
											&nbsp;
										</div>
									</td>
									<td align="right" width="25%"></td>
									<td width="25%" nowrap="nowrap">

									</td>
								</tr>
								<tr>
									<td colspan="1" align="right" style="f-textarea;">
										<fmt:message
											key="efetionmanage.framework.department.parent_department_descr" />
										：
										<span style="color: #9d0c0c;visibility: hidden;">*</span>
									</td>
									<td colspan="3">
										<label>
											<textarea style="width: 450px"
												class="f-textarea textarea_width_l"
												onkeyup="checkTextareaSize('description');"
												name="description" id="description">${requestScope.department.descrption}</textarea>
										</label>
										<div name="decerror" id="decerror">
											&nbsp;
										</div>
									</td>
								</tr>
							</table>

						</div>
					</div>

					<div style="text-align:center; padding:8px;">
						<input type="button" name="editUserInfo_modify"
							value="<fmt:message key="efetionmanage.common.confirm"/>"
							onclick="javascript:check();return false;" class="bot4" />

						&nbsp;&nbsp;
						<input type="button"
							onclick="javascript:resetValueAndCheck();return false;"
							value="<fmt:message key="efetionmanage.common.reset"/>"
							class="bot4" />
						&nbsp;&nbsp;
						<input type="button" name="Submit2"
							value="<fmt:message key="efetionmanage.common.cancel"/>"
							onclick="javascript:goBack();" class="bot4" />
					</div>
				</div>
			</form>
		</div>
	</body>
</html>

