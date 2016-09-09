<!DOCTYPE html PUBLIC "-///www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html; charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<!--部门列表页面页面-->
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
	<head>
		<title></title>
		<%@ include file="/commons/meta.jsp"%>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<script src="${base}/js/jquery.form.js" type="text/javascript"></script>
		<style type="text/css">
<!--
div.jqi .jqimessage {
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

div.jqi {
	width: 410px;
	font-family: Verdana, Geneva, Arial, Helvetica, sans-serif;
	position: absolute;
	background-color: #FFFFFF;
	font-size: 11px;
	text-align: left;
	border: none;
	-moz-border-radius: 10px;
	-webkit-border-radius: 10px;
	padding: 0px;
	margin-top: -50px;
}

.spans {
	color: #007ED2;
	line-height: 22px;
	text-align: left;
	width: 300px;
}

span {
	font: 12px Arial, Helvetica, sans-serif;
	white-space: pre;
}
-->
</style>

		<script language="Javascript" charset="UTF-8" type="text/javascript">
		function goPage(pageNum) {
			if(isInteger(pageNum)){
				document.getElementById("listDepartments").action="${requestScope.actionName}.${actionExt}?page=" + pageNum;
				if(document.getElementById("searchText").value=="请输入部门名称"){
					document.getElementById("searchText").value="";
				}
				
				document.getElementById("listDepartments").submit();
			}else{
				var msg ="<fmt:message key="efetionmanage.common.not_integer"/>";
                $.prompt(msg,{buttons:{确定:true},alertType:'msg'});
			}
		}
		function keyDown(e){
			temp = window.event||e ;
			var coded=temp.keyCode||temp.which; 
			if(coded==13){
				search();
			}
		}
		function orderByField(orderBy, order) {	
			if(order == 'asc') {
				order = 'desc';
			} else {
				order = 'asc'
			}
			if(document.getElementById("searchText").value=="请输入部门名称"){
					document.getElementById("searchText").value="";
				}
			document.getElementById("listDepartments").orderBy.value=orderBy;
			document.getElementById("listDepartments").order.value=order;
			document.getElementById("listDepartments").action="${requestScope.actionName}.${actionExt}";
			document.getElementById("listDepartments").submit();
		}
		
		function checkAdd(){
			$.ajax({
				type : "POST",
				async : false,
				url : "${base}/manage/common/department/departmentOperator!createInput.${actionExt}",
				data: {groupId:$("#groupId").val()},
				dataType : "text",
				success : function(msg) {
					if("endDept"==msg) {
						$.prompt("  该部门已经是最末级部门，请选择其他部门进行添加",{buttons:{确定:true},alertType:'msg'});
					}else {
					   document.getElementById("listDepartments").action="${base}/manage/common/department/departmentOperator!createInput.${actionExt}";
					   document.getElementById("listDepartments").submit();
					}
				}
		
			});
		
		
			//document.getElementById("listDepartments").action="${base}/manage/common/department/departmentOperator!createInput.${actionExt}";
			//document.getElementById("listDepartments").submit();
		}
		/**function checkAdd(){
			
			var right = che();
			alert(right);
			if(right=='haveEmployee'){
			alert(1);
			}else{
			alert(2);
			  document.getElementById("listDepartments").action="${base}/manage/common/department/departmentOperator!createInput.${actionExt}";
			  document.getElementById("listDepartments").submit();
			}
		}
		function che(){
		alert(55555);
		var right;
		$.ajax({
				type : "POST",
				async : false,
				url : "${base}/manage/common/department/departmentOperator!createInput.${actionExt}",
				data: {groupId:$("#groupId").val()},
				dataType : "text",
				success : function(msg) {
					right = msg;
				}
		
			});
			return right;
		}
		**/
		
		function checkModify(gid){
			document.getElementById("listDepartments").action="${base}/manage/common/department/departmentOperator!editInput.${actionExt}?gId="+gid;
			document.getElementById("listDepartments").submit();
			
		}
/**
 * 批量删除部门
 */
function checkDelete(obj){
	
	$(obj).blur();
	if(checkboxCount(document.getElementById("listDepartments").gId)<1){
		$.prompt("<fmt:message key="efetionmanage.common.select_one"/>",{buttons:{确定:true},alertType:'msg'});
		return false;
	}else{
	    document.getElementById("listDepartments").action="${base}/manage/common/department/departmentOperator!delete.${actionExt}";
		removeContent();
	}
}
		function checkBatch(){// 批量填加
		document.getElementById("listDepartments").action="${base}/manage/common/department/departmentOperator!batchInput.${actionExt}";
			document.getElementById("listDepartments").submit();
		}
		function checkMove(obj){
		if(checkboxCount(document.getElementById("listDepartments").gId)<1){
				$.prompt("<fmt:message key="efetionmanage.common.select_one"/>",{buttons:{确定:true},alertType:'msg'});
				return false;
			}else{
				radioContent();
			}
		}
		
		function search() {
			document.getElementById("listDepartments").action="${base}/manage/common/department/listDepartments.${actionExt}";
			if(document.getElementById("searchText").value=="请输入部门名称"){
			document.getElementById("searchText").value="";
			}
			document.getElementById("listDepartments").submit();
		}
		function chose(ids){
			if(document.getElementById("choose").checked==true){
				checkall(ids);
			}else{
				checknull(ids);
			}
		}
		function editContent(redirect){
			var iframeURL='';
			var ajaxURL ='';
			if(redirect =='create'){
				iframeURL = 'departmentOperator!createInput.${actionExt}?';
				ajaxURL ='createDepartment.${actionExt}';
			}else{
				iframeURL = "departmentOperator!editInput.${actionExt}?gId="+redirect+"&";
				ajaxURL ="modifyDepartment.${actionExt}"
			}
			var txt = '<div id="wrap"><lable>部门添加</lable><br><iframe id = "leftframe" name="leftframe" src="'+iframeURL+'groupId='+document.getElementById("groupId").value+'"style="display:block;margin:0 auto;" marginwidth="0" height="250"  framespacing="0" marginheight="0" frameborder="0" scrolling="no" hspace="0" vspace="0"></iframe></div>';
			$.prompt(txt,{ 
				buttons:{确定:true, 取消:false},
				submit:function(v,m,f){
					if(v){
					 	var dd = document.getElementById("leftframe").contentWindow.document;
						if(dd.getElementById("name").value == ''){
							dd.getElementById("nameerror").innerHTML='<font color="red">*<fmt:message key="efetionmanage.common.must_be_not_null" /></font>';
							dd.getElementById("name").focus();
							return false;
						}
						$.post(ajaxURL,{groupId:dd.getElementById("groupId").value,identifier:redirect,name:dd.getElementById("name").value,parentId:dd.getElementById("parentId").value,description:dd.getElementById("description").value}, 
						function(data){
							if(data == 'true'){
								jQuery.ImpromptuClose()
								top.right.left.location=top.right.left.location.href;
								top.right.right1.location=top.right.right1.location.href;
							}else{ $.prompt("移动元素出错",{buttons:{确定:true},alertType:'msg'}); }							
						});
						 
					}else{
						top.right.right1.location=top.right.right1.location.href;
						jQuery.ImpromptuClose()
					}
					return false;
				}
			});
		}
		function radioContent(){
			var parent = "";
			if(document.getElementsByName("gId").length > 0){
				var gids = document.getElementsByName("gId");
				for(var i = 0 ; i < gids.length ; i ++){										
					if(gids[i].checked){
						parent += gids[i].value + ",";
					}
				}
			}
			var msg = '<span class="ms5"  style="position: relative;left: 22%">*只能选择不包含<fmt:message key="efetionmanage.common.employee"/>的部门作为上级部门</span>';
			var txt = '<lable><span class="ms4">请选择上级部门</span></lable><br><iframe id = "leftframe" name="leftframe"  width="220" height="280" src="listDepartmentTree!radio.${actionExt}?parentId='+parent+'" style="display:block;margin:0 auto;border:solid 1px #cad9f0;" FRAMEBORDER=0></iframe>'+msg;
			
			$.prompt(txt,{ 
				buttons:{确定:true, 取消:false},
				submit:function(v,m,f){
					if(v){
						if(top.right.right1.leftframe.bselected == null){
							top.right.right1.location=top.right.right1.location.href;
							jQuery.ImpromptuClose();
						}else{
						    $("#jqi_state0_button确定").blur();// 弹出层的确定取消焦点
						    if(top.right.right1.leftframe.bselected == "")//增加对未选择的判断.
						    {
						    $.prompt('至少选择一个部门',{buttons:{确定:true},alertType:'msg'});
						    return;
						    }
						   
							var arr = top.right.right1.leftframe.bselected[0].id;
							var tmp = "";
							if(document.getElementsByName("gId").length > 0){
								var gids = document.getElementsByName("gId");
								for(var i = 0 ; i < gids.length ; i ++){										
									if(gids[i].checked){
										tmp += gids[i].value + ",";
									}
								}
							}
							$.post('departmentOperator!move.${actionExt}',{gId:arr,gIdstr:tmp}, function(data){
							  if(data == 'true'){
									jQuery.ImpromptuClose()
									top.right.left.location=top.right.left.location.href;
									top.right.right1.location=top.right.right1.location.href;
								}else if(data == 'hasItem'){
									$.prompt('该部门下包含<fmt:message key="efetionmanage.common.employee"/>不允许移动',{buttons:{确定:true},alertType:'msg'});
								}else if(data == 'nameInvalid'){
									$.prompt('移动之后将出现部门名称相同的情况，禁止移动',{buttons:{确定:true},alertType:'msg'});
								}else if(data == 'gaff10'){
									$.prompt('移动部门后组织架构将超过10级，请重新选择上级部门',{buttons:{确定:true},alertType:'msg'});
								}else{ $.prompt("移动元素出错",{buttons:{确定:true},alertType:'msg'}); }							
							});
						}
					}else{
						top.right.left.location=top.right.left.location.href;
						top.right.right1.location=top.right.right1.location.href;
						jQuery.ImpromptuClose()
					}
					return false;
				}
				 
			});
		}
function removeContent(){
	// var isHad  = isHadEmp();
	// var jsonObj = eval('(' + isHad + ')');
	var msg = '<fmt:message key="efetionmanage.framework.department.department_list.delete.msg" />';
	     
	$.prompt(msg,{ 
					buttons:{是:true, 否:false},alertType : 'ask',
					submit: function(v,m,f){
						if(v){
							var tmp = "";
							if(document.getElementsByName("gId").length > 1){
								var gids = document.getElementsByName("gId");
								for(var i = 0 ; i < gids.length ; i ++){
									
									if(gids[i].checked){
										tmp += gids[i].value + ",";
									}
								}
							}else{
								tmp= document.getElementById("gId").value;
							}
							
							
							
		var date = new Date();
		var groupId = document.getElementById("groupId").value;
		
       $.ajax({
		type : "POST",
		async : false,
		url : "${base}/manage/common/department/departmentOperator!delete.${actionExt}",
		data : "dates=" + date+"&groupId="+groupId+"&allRemove=" + tmp,
		dataType : "json",
		success : function(data) {
		
		                function mycallbackfunc(v, m, f) {
								document.location.href=  document.location.href;
		                        parent.document.getElementById("left").contentWindow.document.location.reload();
		                                                  }
		                        var depNames = data[0].deptName;
		                        alert(depNames);
		                        var msg = "已删除部门";
		                        $.prompt(msg,{buttons:{确定:true},alertType:'msg',
					            callback : mycallbackfunc
				               });
		      
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
           document.location.href = "${base}/commons/error/error.jsp";
			//alert(textStatus);
        }
        });
       	 return true;
			}
			else{jQuery.ImpromptuClose();}						
		}
	});
}
		function getAllNode(v_name){
			var idstr="";
			var frame_v = document.frames ? document.frames(v_name) : document.getElementById(v_name).contentWindow;
			if(frame_v.treeXiaqu.tree.root.checked!=0){
			   var a =frame_v.treeXiaqu.getNodesAll();
			  // var arr = new Array();
			   for(var i = 0 ; i < a.length; i ++){
				var linshi=a[i];
			   		while((typeof linshi.parent!='undefined') && (typeof linshi.parent.id!='undefined')){
			   			if(linshi.parent!='undefined'&& linshi.parent.id!='undefined'){
				   			a.push(linshi.parent);
				   			linshi=linshi.parent;
			   			}
			   		}
			   }
			    for(var i = 0; i < a.length; i++ ) {  
					for(var j = a.length - 1; j > i; j-- ) {  
					    if( a[j].id == a[i].id )  
					    {  
					      a.splice(j,1);  
					    }  
				  	}  
				}
			   if(a.length>0){
			   		for(var j=0;j<a.length;j++){
			   			if(typeof a[j].id!='undefined'){
			   				idstr+=a[j].id+';';
			   			}
			   		}
			   }
		   }
		   return idstr;
		}
		
		/**
	function goSetDeptRoles(){
		var isChecked = $("input[id='gId']:checked");
			if(isChecked.length<1){
				$.prompt('请选择部门进行权限设置');
				return;
			} 
		
		
		if(isChecked.length>1){// 多选部门
		
		 var spans = '<span class="ms4">提示</span>';
		 var msg = "<span class='spans'>&nbsp;&nbsp;&nbsp;您的此次操作将重新设置选中部门查看通讯录的权限，部门默认可查看本公司所有部门的通讯录。是否继续操作？</span>";
		 $.prompt(spans+"<br/><table><tr><td><img src = '${base}/images/guantaohao.gif'/></td><td>"+msg+"</td></tr></table>",{
		  buttons:{确定:true, 取消:false},
		  submit:function(v,m,f){
		  if(v){
		     setDeptRoles();
		     jQuery.ImpromptuClose();
		      }else{
		           return true;
		        }
		        }
		           });
		}else{// 单选部门时
		
		setDeptRoles();
		}
		}	
		function setDeptRoles(){
		
		var boo = "${groupId == '0'}";
			var isChecked = $("input[id='gId']:checked");
			if(isChecked.length<1){
				$.prompt('请选择部门进行权限设置');
				return;
			} else{
			    var gids = "";
			    isChecked.each(function (i){
			    if(i==isChecked.length-1){ gids = gids+this.value}
			    else{ gids = gids+this.value+","}
			    });
			    
			   var v_name = "leftframe";
				var v_lable = '<span class="ms4">通讯录查看范围设置</span>';
				 var msg = '<span class="ms5" align="left">*员工查看本部门通讯录的权限范围默认不可修改。当查看范围为空时，员工查看权限将设置为只可查看本部门通讯录。</span>';
				var iframe = v_lable+'<iframe name="leftframe" width="390px" height="280px" style="display:block;margin:0; auto;border:solid 1px #cad9f0;"   src="${base}/manage/common/department/listDepartmentTree!initDepartment.${actionExt}?deptIds='+gids+'&kind=setper&t=<%=System.currentTimeMillis()%>"></iframe>'+msg;
	      	    $.prompt(iframe,{
						buttons:{确定:true, 取消:false},
						loaded:function(){
						},
						submit:function(v,m,f){
							if(v){
							
							 var str = document.getElementById("leftframe").contentWindow.getSe();
                            if(str==""||str==null){
                            document.getElementById("leftframe").contentWindow.hideSe();
							$.prompt("您设置的查看范围为空，将设置选中部门下的所有员工只可查看员工所在子部门的通讯录。是否继续?",{buttons:{确定:true, 取消:false},submit:function (v,m,f){
							if(v){
							$.ImpromptuClose();
							document.getElementById("leftframe").contentWindow.showSe();
							var listDepartments = $("#listDepartments");
							var url = "${base}/manage/common/department/departPermissionSettingAction!permissionsSetting.${actionExt}";
				            listDepartments.attr("action", url);
				                     $("#ps").attr("value", str);
				                     $("#thisDeptId").attr("value", gids);
				                     
				           listDepartments.ajaxSubmit({
				                     
				                     success:function(data){
				                             if(data=='1' ){
				                              
				                                $.ImpromptuClose();
				                                $("input[type='checkbox']:checked").attr("checked",false);
				                             	
												$.prompt("设置权限成功",{buttons:{确定:true}});
											
											 }else{
				                             	$.ImpromptuClose();
				                             	var txt = "设置权限失败,请重试";
												$.prompt(txt,{buttons:{确定:true}});
				                             }
				                         },
				                         dataType:'json',
				                         async:true,
				                         beforeSend:function(){
				                         
				                          var txt = "请求已发送,设置权限操作可能会耽误您几分钟,请耐心等待…";
				                          
							              $.prompt(txt,{buttons:{}});}
				                     });
							
							
							return true;
							
							}else{
							
							document.getElementById("leftframe").contentWindow.showSe();
							return true;
							
							}
							}
							} );
							return;
							}
							
							var listDepartments = $("#listDepartments");
							var url = "${base}/manage/common/department/departPermissionSettingAction!permissionsSetting.${actionExt}";
				            listDepartments.attr("action", url);
				                     $("#ps").attr("value", str);
				                     $("#thisDeptId").attr("value", gids);
				                     
				            $.ImpromptuClose();
				            
				                     listDepartments.ajaxSubmit({
				                     
				                     success:function(data){
				                             if(data=='1' ){
				                              
				                                $.ImpromptuClose();
				                                $("input[type='checkbox']:checked").attr("checked",false);
				                             	
												$.prompt("设置权限成功",{buttons:{确定:true}});
											
											 }else{
				                             	$.ImpromptuClose();
				                             	var txt = "设置权限失败,请重试";
												$.prompt(txt,{buttons:{确定:true}});
				                             }
				                         },
				                         dataType:'json',
				                         async:true,
				                         beforeSend:function(){
				                         
				                          var txt = "请求已发送,设置权限操作可能会耽误您几分钟,请耐心等待…";
				                          
							              $.prompt(txt,{buttons:{}});}
				                     });
							  return true
								}else{
								jQuery.ImpromptuClose();
								return true;
							}
						}
					});
			}
		}**/
		function getGidsStr(){
			var tmp = "";
			if(document.getElementsByName("gId").length > 0){
				var gids = document.getElementsByName("gId");
				for(var i = 0 ; i < gids.length ; i ++){										
					if(gids[i].checked){
						tmp += gids[i].value + ",";
					}
				}
			}
			return tmp;
		}
		window.onload=function(){
			var roleLevel = document.getElementById("roleLevel").value;
			if(roleLevel == 0) {
				$("#sort").css("display", "inline");
			}
		    reloadTree();// 排序后重新加载tree
	        <c:if test="${not empty requestScope.oprate }">
	         top.right.left.location.reload();
			</c:if>
			<c:if test="${not empty requestScope.gaff10 }">
				$.prompt('<fmt:message key="efetionmanage.framework.department.department_edit_10_level"/>',{buttons:{确定:true},alertType:'msg'});
			</c:if>
			<c:if test="${not empty requestScope.hasItem}">					
					$.prompt('<fmt:message key="efetionmanage.framework.department.department_create_has_employee"/>',{buttons:{确定:true},alertType:'msg'});
				</c:if>
			searchTextStyle(document.getElementById("searchText"),"请输入部门名称");
		}
		function searchnameBlur(){
			if(document.getElementById("searchText").value==null||document.getElementById("searchText").value==""){
				document.getElementById("searchText").value="请输入部门名称";
				document.getElementById("searchText").style.color='#949494';
			}
		}
		function deleteAllCheck(){
		var count= 0;
		
		if(document.getElementsByName("gId").length > 0){
		
				var gids = document.getElementsByName("gId");
				for(var i = 0 ; i < gids.length ; i ++){										
					if(gids[i].checked==false){
						$("#choose").attr("checked",false);
						break;
					}
					count++;
				}
				var checkedLength = $("input[name='gId']");
				if(checkedLength.length>0){
				if(count==checkedLength.length){
				$("#choose").attr("checked",true);
		         }
				}
			}
		}
		function isHasChild(obj){
		deleteAllCheck();
		var setbutton = document.getElementById("setbutton");
		var checks = $("input[type='checkbox']:checked");
		var len = checks.length;
		
		if(len==1){
		var ishas;
	    var date = new Date();
	    var nowDeptId = $("input[id ='gId']:checked").attr("value");
	    $.ajax({
		type : "POST",
		async : false,
		url : "${base}/manage/common/department/departPermissionSettingAction!isHasChild.${actionExt}?deptId="+nowDeptId,
		data : "dates=" + date,
		dataType : "text",
		success : function(msg) {

			ishas = msg;

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {

			alert(textStatus);

		}

	});
	    if(ishas==1){
	     setbutton.disabled= true;
	                }
	    if(ishas==0){
	     setbutton.disabled= false;
	                }
		}
		else{
		setbutton.disabled= true;
		}
		
	   }
	   function sortDept(obj){
	    window.location.href = "${base}/manage/common/department/listDepartments!goSortDep.${actionExt}?deptId="+'${requestScope.groupId}';
	   } 
	   function reloadTree(){
	   var flag = '${param.flag}';
	   if(flag==1){
	     //parent.document.getElementById("left").document.location.reload();
	     parent.document.getElementById("left").contentWindow.document.location
				.reload();
	   }
	   }
	   
	   
	   $("document").ready(function(){
      $("#dName").css("whiteSpace ","pre");
      
      });
      
      
        function isHadEmp(){
        
        var date = new Date();
        var isHad = "";
        var listDepartments = $("#listDepartments");
        listDepartments.attr("action","${base}/manage/common/department/departmentOperator!isHadEmp.${actionExt}");
        listDepartments.ajaxSubmit({
				                     
				         success:function(data){
				          isHad = data;
				                         },
				                         dataType:'text',
				                         async:false
				                     });
        
        
        
        
	    return isHad;
         }
         
         
         $("document").ready(function() {
			$(".showDiv").mousemove(function(e) {
						$("#showMsg").css("top", e.clientY);
						$("#showMsg").css("left", e.clientX + 2)
						$("#showMsg").html(this.getAttribute("dec"));
						$("#showMsg").show();
					});

			$(".showDiv").mouseout(function(e) {
						$("#showMsg").hide();
					});

		});
	</script>
	</head>

	<body>
		<div id="main">
			<div class="title">
				<h4>
					<c:choose>
						<c:when test="${!empty requestScope.deptName}">
							<c:out value="${requestScope.deptName}" escapeXml="true"></c:out>
						</c:when>
						<c:otherwise>
							<fmt:message
								key="efetionmanage.framework.department.all_departments" />
						</c:otherwise>
					</c:choose>
				</h4>
			</div>
			<form name="listDepartments" id="listDepartments" method="POST">
				<input id="roleLevel" name="roleLevel" value="${roleLevel}"
					type="hidden" />
				<table width="100%" border="0" cellspacing="0" bgcolor="#E5E8ED"
					cellpadding="0">
					<tr>
						<td width="58%">
							<div>
								<span class="table-navdiv1"><input type="button"
										value="添加" class="bot1other" onclick="checkAdd()" /> <input
										type="button" value="删除" class="bot1other"
										onclick="checkDelete()" /> </span>
							</div>
						</td>
						<td width="42%">
							<div align="right">
								<input type="text" name="searchText" style="width: 100px"
									value="${requestScope.searchText}" id="searchText"
									onblur="searchnameBlur()" />
								<input type="image" onclick="javascript:search();return false;"
									src="${base }/images/so.gif" />
							</div>
						</td>
					</tr>
				</table>

				<table cellpadding="2" cellspacing="1" border="0" class="tables">

					<thead>
						<tr style="text-align: center;">
							<td width="36">
								<input type="checkbox" name="choose" id="choose"
									onclick="javascript:chose(listDepartments.gId);" />
							</td>

							<th>
								<!-- 
								<a href="#"
									onClick="javascript:orderByField('name', <c:choose><c:when test="${requestScope.pageInfo.orderBy == 'name'}">'${requestScope.pageInfo.order}'</c:when><c:otherwise>'desc'</c:otherwise></c:choose>);"
									title="<fmt:message key="efetionmanage.common.sort"/>">
									 -->

								<fmt:message
									key="efetionmanage.framework.department.department_name" />

								<!--</a>-->
							</th>
							<th>
								<!--
								<a href="#"
									onClick="javascript:orderByField('parentId', <c:choose><c:when test="${requestScope.pageInfo.orderBy == 'parentGroupId'}">'${requestScope.pageInfo.order}'</c:when><c:otherwise>'desc'</c:otherwise></c:choose>);"
									title="<fmt:message key="efetionmanage.common.sort"/>">-->
								<fmt:message
									key="efetionmanage.framework.department.parent_department_id" />
							</th>
							<th>
								<!--
								<a href="#"
									onClick="javascript:orderByField('descrption', <c:choose><c:when test="${requestScope.pageInfo.orderBy == 'parentGroupId'}">'${requestScope.pageInfo.order}'</c:when><c:otherwise>'desc'</c:otherwise></c:choose>);"
									title="<fmt:message key="efetionmanage.common.sort"/>">-->

								<fmt:message
									key="efetionmanage.framework.department.parent_department_descr" />
							</th>
							<th width="47">
								修改
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.departments}" var="department"
							varStatus="index">
							<tr>
								<td width="36" align="center">
									<input type="checkbox" name="gId"
										value="${department.identifier}" id="gId"
										onclick="deleteAllCheck()" />
								</td>
								<td width="25%">
									${fn:escapeXml(department.name)}
								</td>

								<td width="25%">
									${department.tips}
								</td>
								<td width="40%" align="left">
									<!-- 
								<c:choose>
									<c:when test="${ fn:length(department.descrption) > 20 }">
													<span class="showDiv" dec="${department.showDes}"><c:out
															value="${ fn:substring(department.descrption,0,20)}"
															escapeXml="true"></c:out>...</span>
												</c:when>
												<c:otherwise>
													<span class="showDiv" dec="${department.showDes}"><c:out
															value="${department.descrption}" escapeXml="true"></c:out> </span>
												</c:otherwise>
								</c:choose>
								 -->
									<c:choose>
										<c:when test="${ fn:length(department.descrption) > 20 }">
											<span title="${department.descrption}"><c:out
													value="${ fn:substring(department.descrption,0,20)}"
													escapeXml="true"></c:out>...</span>
										</c:when>
										<c:otherwise>
											<span title="${department.descrption}"><c:out
													value="${department.descrption}" escapeXml="true"></c:out>
											</span>
										</c:otherwise>
									</c:choose>
								</td>
								<td width="36" align="center">
									<a href="#"
										onclick="javascript:checkModify(${department.identifier});return false;"
										title="修改部门"> <img src="${base}/images/ico_edit.gif" /> </a>
								</td>
							</tr>
						</c:forEach>
						<c:if test="${empty requestScope.departments}">

							<tr>

								<td colspan="20" align="center">
									暂无记录
								</td>
							</tr>
						</c:if>
						<div class="pages">
							<%@ include file="/commons/page.jsp"%>
						</div>
					</tbody>
				</table>
				<input type="hidden" name="groupId" id="groupId"
					value="${requestScope.groupId}" />
				<input type="hidden" name="orderBy" id="orderBy"
					value="${requestScope.pageInfo.orderBy}" />
				<input type="hidden" name="order" id="order"
					value="${requestScope.pageInfo.order}" />
				<input type="hidden" name="ps" id="ps" value="" />
				<input type="hidden" name="thisDeptId" id="thisDeptId" value="" />
			</form>
		</div>
		<div name="showMsg" id="showMsg"
			style="position: absolute; background-color: #ffffe1; border: 1px solid #000000; padding-left: 2px; padding-right: 2px; display: none; word-wrap: break-word; word-break: break-all;"></div>
	</body>
</html>
