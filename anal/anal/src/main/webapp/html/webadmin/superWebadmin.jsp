<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/commons/noright.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- 
	superWebAdmin.jsp
	超管角色配置修改页面
	
 -->
	<head>
		<title>${requestScope.platformTitle}</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<script type="text/javascript" src="${base}/js/check_all.js"></script>
		<script type="text/javascript" src="${base }/dwr/engine.js"></script>
		<script type='text/javascript'
			src="${base }/dwr/interface/webAdminManager.js"></script>
		<script type="text/javascript" src="${base}/js/main.js"></script>
		<script language="Javascript">
	var memberSearchDefaultText = "按名称、拼音首字母、全拼、手机号码";
	var tt = "leftframe<%=System.currentTimeMillis()%>";
	function addAdmin(){
		cleanErrorMes();
		var username = document.getElementById("manager");
		if ((username.value) == "") {
			document.getElementById("mangerName").innerHTML='<fmt:message key="efetionmanage.framework.webAdmin.validateUserId"/>';
			return ;
		}
		var oldUserid = $("#oldUserid").val();
		var nowUserid = $("#userid").val();
		if(nowUserid == undefined || oldUserid == nowUserid){   // 未修改管理员绑定
			subWebAdmin();
		}else{						  // 已经修改管理员绑定 
			proWebAdminMsg();
		}
		
	}
	
	// 弹出更改管理员绑定显示信息
	function proWebAdminMsg(){
		$.prompt("  更改超级管理员绑定<fmt:message key="efetionmanage.common.employee"/>关系的操作将使您退出管理后台系统并重置超管的密码，确定进行此次操作吗？",{
			buttons:{是:true,否:false},
			alertType:'msg',
			submit:function(v,m,f){
				if(v){
					document.getElementById("webAdminForm").action='${base}/manage/safemanage/superwebadmin/${actionUrl}.${actionExt}';
					document.getElementById("webAdminForm").submit();
					gotoIndex();
					jQuery.ImpromptuClose();
				}else{
					jQuery.ImpromptuClose();
				}
		}});
	}
	 // 提交超管信息配置
	function subWebAdmin(){
		document.getElementById("webAdminForm").action='${base}/manage/safemanage/superwebadmin/${actionUrl}.${actionExt}';
		document.getElementById("webAdminForm").submit();
	}
	
	function cancel(){
		document.getElementById("webAdminForm").action='${base}/manage/safemanage/superwebadmin/${actionUrl}!toListWebAdmin.${actionExt}';
		document.getElementById("webAdminForm").submit();
	}
	
	       	Array.prototype.baoremove = function(dx)
	  {
	    if(isNaN(dx)||dx>this.length){return false;}
	    for(var i=0,n=0;i<this.length;i++)
	    {
	        if(this[i]!=this[dx])
	        {
	            this[n++]=this[i]
	        }
	    }
	    this.length-=1
	  }
		
		function getCheckedBox(elms){
			var idstr="";
	for(var i=0;i<elms.length;i++)
	{
		if(elms[i].checked == true)
		{
			idstr+=elms[i].value+";";
		}
	}
		return idstr;
		}
		
	function validatorCheckBox(idds){
		var elms = document.getElementsByName(idds);
		var x = 0;
		for(var i=0;i<elms.length;i++)
		{
			if(elms[i].checked == true)
			{
				x=x+1;
			}
		}
		if(x<1)
		{
			document.getElementById("permiss").innerHTML='<fmt:message key="efetionmanage.framework.webAdmin.notchoose"/>';
			return false;
		}else{
			return true;
		}
	}
	window.onload=function(){
	    aaa();
	}
	function textIsNull() {
		var submitbtn=document.getElementById("submitbtn");
		var un=document.getElementById("manager").value;
		if(un!=""){
			submitbtn.className = "bot4";
			submitbtn.disabled = false;
	    } else {
	        submitbtn.className = "bot3";
	        submitbtn.disabled = true;
	    }
	}
	
	function modflag(){
		document.getElementById("mod").value='1';
	
	}
	function cleanErrorMes(){
		var arr=document.getElementsByTagName("span");
		if(arr.length>0){
			for(var i=0;i<arr.length;i++){
				arr[i].innerHTML="";
			}
		}
	}
	function aaa(){
	
		var bb = '${requestScope.sendFlag}';
		if(bb==0){
			document.getElementById("isSendNote").checked=false;
		}else{
			document.getElementById("isSendNote").checked=true;
		}
	}
	
	 function clearOwnerTip() {
     	if ($("#searchOwner").attr("value") == memberSearchDefaultText) {
     		$("#searchOwner").attr("value", "");
     		$("#searchOwner").css("color", "#000000");
     	}
     }
	
	 function ownerTextBlur(val) {
     	if ($.trim(val) == '') {
        	$("#searchOwner").attr("value", memberSearchDefaultText)
            $("#searchOwner").css("color", "#949494")
        }
        document.body.focus();
     }
	
	 function ownerKeydown(event,val) {
     	if(event.keyCode == 13) {
     		startSearchOwner(val);
     		document.body.focus();
     	}
     }
 	 function startSearchOwner(value) {
 	 	var oclusterIdValue = "create";
        if ($.trim(value) == memberSearchDefaultText) {
        	value = '';
        }
     	var frame = $("#"+tt);
     	frame.attr("src","${base}/manage/common/department/listDepartmentTree!userRadio.${actionExt}?isadmin=superadmin&oclusterId=" + oclusterIdValue + "&searchOwnerValue=" + encodeURI(encodeURI(value)) + "&abcd="+Math.random());
     }    
	 function popupselect(){
            var timest = new Date();
            var oclusterIdValue = "create";
            var txt_search = '<form id="searchForm" onsubmit="return false;">';
            txt_search += '<input type="text" id="searchOwner" value="' + memberSearchDefaultText + '" size="31" maxlength="50" onfocus="clearOwnerTip()" style="color:#949494;width:224px;" onblur="ownerTextBlur(this.value)" onkeydown="ownerKeydown(event,document.getElementById(\'searchOwner\').value)">&nbsp;';
            txt_search += '<input type="button" value="查找" onclick="startSearchOwner(document.getElementById(\'searchOwner\').value)">';
            txt_search += '</form>';
            
			var txt_iframe ='<iframe frameBorder="0" width="280px" height="280px" id="'+tt+'" name="'+tt+'" src="${base}/manage/common/department/listDepartmentTree!userRadio.${actionExt}?isadmin=superadmin&kind=cluster&oclusterId='+oclusterIdValue+'&'+Math.random()+'" style="display:block;border: 1px solid rgb(202, 217, 240);margin:0 auto;"></iframe>';
			var txt = '<lable><fmt:message key="efetionmanage.framework.webadmin.selected" />&nbsp;&nbsp;</lable><br><table width="280px" border="0" cellpadding="0" align="center"><tr><td>' + txt_search + '</td></tr><tr><td>'+txt_iframe+'</td></tr></table>';
			$.prompt(txt,{
				buttons:{确定:true, 取消:false},
				submit:function(v,m,f){
					if(v){
					var frame_v = document.frames ? document.frames(tt) : document.getElementById(tt).contentWindow;
						if(frame_v.bselected <= 0){
                            $.prompt("请选取一名<fmt:message key="efetionmanage.common.employee"/>！",{buttons:{确定:true}});
						}else{
							var uid =frame_v.bselected[0].id;
							var uname = frame_v.bselected[0].name;
							var username = document.getElementById("manager");
							username.value = uname;
							document.getElementById("userid").value=uid;
							if(uid == null || '${oldUserid eq uid}'=='true'){
								document.getElementById("submitbtn").className = "bot3";
								document.getElementById("submitbtn").disabled = true;
						    } else {
						        document.getElementById("submitbtn").className = "bot4";
						        document.getElementById("submitbtn").disabled = false;
						    }
							isYidong(uid)
                            jQuery.ImpromptuClose();
						}
					}else{
                        jQuery.ImpromptuClose();
                    }
                    $("select").attr("style", "width:180px");
				}
			});
        }
    //判断移动 判断所选择的员工是否有效
    function isYidong(userId){
	    var sendMsg;
	    var date = new Date();
	    $.ajax({
			type : "POST",
			async : false,
			url : "${base}/manage/safemanage/webadmin/createWebAdminAction!isYidong.${actionExt}",
			data : "dates=" + date+"&userId=" + userId,
			dataType : "json",
			success : function(msg) {
	            sendMsg = msg;
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				window.top.location=base + "/commons/beforetimeout.jsp";
			}
		});
		var isSendNote = document.getElementById("isSendNote");
		if(sendMsg==1){
			isSendNote.disabled = false;
		}else{
			isSendNote.disabled = true;
			isSendNote.checked = false;
		}
	}
	
	function initMp(){
	
	var userId = '${requestScope.webAdmin.adminUserid}';
	isSendNote.checked = true;
	isYidong(userId);
	}
	function gotoIndex(){
        if(top!=window){
			top.location.href="${base}/";
	    }else{
			document.location.href="${base}/";
	    }
	}
</script>
</head>
	<body scroll="no">
		<form name="webAdminForm" id="webAdminForm" action="" method="post">
			<input type="hidden" id="oldUserid" name="oldUserid" value="${oldUserid}"/>
			<input type="hidden" id="userid" name="userid" value="${employee.userId}" />
			<ss:token name="webAdmin.token" />
			<div id="main" style="overflow: auto; width: 96%; height: 96%;">
				<div class="title">
					<h4>
						超管角色配置
					</h4>
				</div>
				<div class="msg"></div>
				<div class="area-warp">
					<h5>
						绑定超级管理员帐号
					</h5>
					<div class="area-content">
						<ul class="clearfix" style="margin:0;padding:0;">
							<li style="text-align:center;margin:0;padding:20px 0 20px 0; ">
								
								<table align="center">
									
									<tr>
										<td width="" align="right" class="c-title">
											&nbsp;
										</td>
										<td width="20%" align="right" class="c-title">
											<fmt:message key="efetionmanage.framework.webAdmin.superManager" />
										</td>
										<td align="left" width="">
											<input name="manager" id="manager" style="width: 150px;"
												type="text"  maxlength="16" value="${employee.nickName}"
													 disabled="disabled" style="background-color:#e5e8ed"  />
										
											<input type="button"  onclick="popupselect();" class="bot4"
		                                       value="<fmt:message key='efetionmanage.framework.webadmin.selected'/>" 
		                                       <c:if test="${actionUrl eq 'modifyWebAdminAction'}" >
													 disabled="disabled" style="background-color:#e5e8ed"  
												</c:if> 
                                      		 />
                                       <span id="mangerName" class="erro"></span>
											<p class="prompt-msg">
												<fmt:message key='efetionmanage.framework.webadmin.prompt_msg'/>
											</p>
										</td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td   align="left" valign="top" nowrap="nowrap" class="c-title"  >
											<input id="isSendNote" name="isSendNote" type="checkbox" value="1" onclick="textIsNull();" 
											 style="position:relative;top:-3px;"
											/>
											接收待办事项短信提醒
										</td>
									</tr>
								</table>
							</li>
						</ul>
					</div>
					<div class="f-bot" style="margin:0; padding:20px 0 0 0; text-align:center;">
						<input type="button" id="submitbtn"
							name="submitbtn" class="bot3" disabled = true;
							value="<fmt:message key="efetionmanage.common.confirm"/>"
							onclick="addAdmin();" onkeyup="textIsNull();" />  
							<input type="button" id="cancelbtn"
							name="cancelbtn" value="<fmt:message key="efetionmanage.common.cancel"/>"
							onclick="cancel();"  class="bot4" style="margin-left:40px;"/>
					</div>
				</div>
			</div>
		</form>
	</body>
</html>
