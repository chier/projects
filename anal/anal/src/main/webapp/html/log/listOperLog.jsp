<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<%-- 
<%@ include file="/commons/noright.jsp"%>
--%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${base}/css/main.css" rel="stylesheet" type="text/css" media="all" />
				<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${base}/js/check_all.js"></script>
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<%@ include file="/js/calendar/calendar2/calendar.jsp"%>

		
		<title>Insert title here</title>
		<script language="Javascript">
		function goPage(pageNum) {
			if(isInteger(pageNum)){
				var listOperLogForm = document.getElementById('listOperLogForm');
				listOperLogForm.action="${base}/manage/safemanage/log/listOperLog.${actionExt}?page=" + pageNum;
				listOperLogFormSubmit();
			}else{
				$.prompt("<fmt:message key="efetionmanage.common.not_integer"/>",{buttons:{确定:true},alertType:'msg'});
			}
		}
		function listOperLogFormSubmit(){
			var userName =  $("#user_name").val();
			if('请输入用户名搜索'==userName){
				$("#user_name").val("") 
			}
			var congTime = $("#operate_time").val();
			var daoTime =  $("#end_time").val() ;
			if(congTime!=null&&congTime!=''&&!checkDateFormat(congTime)){
				$("#operate_time").val("")
				$.prompt("起始时间不是日期格式，请重新选择",{buttons:{确定:true},alertType:'msg'});
				return false;
			}
			if(daoTime!=null&&daoTime!=''&&!checkDateFormat(daoTime)){
				$("#end_time").val("")
				$.prompt("结束时间不是日期格式，请重新选择",{buttons:{确定:true},alertType:'msg'});
				return false;
			}
			if((!isNull(congTime))&&(!isNull(daoTime))){
				if(daoTime<congTime){
					$.prompt("起始时间不能大于结束时间",{buttons:{确定:true},alertType:'msg'});
					return false;
				}
			}
			var listOperLogForm = document.getElementById('listOperLogForm');
			listOperLogForm.submit();
		}
		function isNull(obj){
			if(obj==null||""==obj){
			
				return true;
			}
			return false;
		}
		function keyDownSubmit(e){
			temp = window.event||e ;
			var coded=temp.keyCode||temp.which; 
			if(coded==13){
				listOperLogFormSubmit();
			}
		}
		
		//是日期格式返回真，否则返回假
		function checkDateFormat(date){
            var reg = /^\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}$/;
            var reg1 = /^\d{4}-\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}$/;
			return reg.test(date) || reg1.test(date);
		}
		window.onload=function(){
			document.getElementById('toPage').onkeyup=function(e){
				temp = window.event||e ;
				var coded=temp.keyCode||temp.which; 
				if(coded==13){
					goPage(document.getElementById('toPage').value);
				}
			};
		}
		
		function userNameClick(){
			var userName = $("#user_name").val();
			if('请输入用户名搜索' == userName){
				$("#user_name").val("")
			}
		}
		function userNameBlur(){
			var userName = $("#user_name").val();
			if(''==userName){
				DWRUtil.setValue("user_name","请输入用户名搜索");
			}
		}
	</script>
	</head>
	<body>
		<div id="main">
			<div class="title">
				<h4>
					<a href="${base }/html/adminSetting/index.jsp">管理设置</a> 
					&gt;&gt;
					<a href="${base}/manage/safemanage/log/listOperLog.${actionExt}"><fmt:message
						key='efetionmanage.framework.log.log' /></a>
				</h4>
			</div>
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
					bgcolor="#E5E8ED">
					<tr>
						<form name="listOperLogForm" id="listOperLogForm"
				action="${base}/manage/safemanage/log/listOperLog.${actionExt}" method="post">
						<td  align="right">
								<fmt:message key='efetionmanage.framework.log.admin_name'/>
								<input type="text" id="user_name" name='user_name' onclick="userNameClick()" onblur="userNameBlur()" value='<c:if test="${not empty user_name}">${user_name}</c:if><c:if test="${empty user_name}">请输入用户名搜索</c:if>' onkeydown="keyDownSubmit(event)" style="color: #949494;" />
								&nbsp;&nbsp;
								<fmt:message key='efetionmanage.framework.log.searchtime'/>
								<input type="text" id="operate_time" name='operate_time' id='operate_time'  style="color: #949494;"
								onclick="return showCalendar('operate_time', '%Y-%m-%d %H:%M', '24',true);" value='${operate_time}' onkeydown="keyDownSubmit(event)" readonly="readonly"/>

								<fmt:message key='efetionmanage.framework.log.daotime'/>
								<input type="text" id="end_time" name='end_time' id='end_time'  style="color: #949494;"  onclick="return showCalendar('end_time', '%Y-%m-%d %H:%M', '24',true);" value='${end_time}' onkeydown="keyDownSubmit(event)" readonly="readonly"/>
			              </td></form>
						<td><input type="image" onclick="listOperLogFormSubmit();" src="${base }/images/so.gif" /></td>
					</tr>
				</table>
				<table cellpadding="2" cellspacing="1" border="0" class="tables">
					<thead>
						<tr>
							
							<th nowrap  width="5%">
								<fmt:message key="efetionmanage.framework.log.admin_name" /> 
							</th>

							<th nowrap  width="">
								<fmt:message key="efetionmanage.framework.log.operate_type" /> 
							</th>
							<th nowrap  width="">
								<fmt:message key="efetionmanage.framework.log.dept_name" /> 
							</th>
							<th nowrap  width="">
								<fmt:message
										key="efetionmanage.framework.log.time" /> 
							</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${requestScope.listOperLog }" var="operLog">
							<tr>
								
								<td nowrap>
									${operLog.adminName }
								</td>
								<td nowrap align="center">
									${operLog.operateDesc }
								</td>
								
								<td nowrap align="center">
									<c:if test="${operLog.employeeName!=null&&operLog.employeeName!=''}">
										<c:if test="${operLog.deptName!=null&&operLog.deptName!=''}">
											<c:out value="${operLog.employeeName}" escapeXml="true"/>(<c:out value="${operLog.deptName}" escapeXml="true"/>)
										</c:if>
										<c:if test="${operLog.deptName==null||operLog.deptName==''}">
											<c:out value="${operLog.employeeName}" escapeXml="true"/>
										</c:if>
									</c:if>
									<c:if test="${operLog.employeeName==null||operLog.employeeName==''}">
										<c:if test="${operLog.deptName!=null&&operLog.deptName!=''}">
											<c:out value="${operLog.deptName}" escapeXml="true"/>
										</c:if>
										<c:if test="${operLog.deptName==null||operLog.deptName==''}">
											<fmt:message key="efetionmanage.framework.log.all_company" /> 
										</c:if>
									</c:if>
								</td>
								<td nowrap align="center">
									<fmt:formatDate value="${operLog.operateTime }" type="date" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>

							</tr>
						</c:forEach>
					</tbody>
					
					<c:if test="${empty requestScope.listOperLog}">
			
								<tr>
					
              <td colspan="20" align="center">暂无记录</td>
             </tr>
						</c:if>
				</table>
			<div class="pages">
				<%@ include file="/commons/page.jsp"%>
			</div>
		</div>
		
	</body>
</html>