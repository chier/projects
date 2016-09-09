<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>

<html>
	<head>
		<!-- 系统广播 管理范围单独设置页面  -->
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="stylesheet" type="text/css" href="${base}/style/page.css" />
		<%@ include file="/commons/meta.jsp"%>
		<script src="${base}/js/user.list.js"></script>
		<link href="${base }/css/3select.css" rel="stylesheet"
			type="text/css" media="all" />
		<link href="${base }/css/role/common.css" rel="stylesheet"
			type="text/css" media="all" />
		<link href="${base }/css/role/layout.css" rel="stylesheet"
			type="text/css" media="all" />
		<link href="${base }/css/role/main.css" rel="stylesheet"
			type="text/css" media="all" />

		<script type="text/javascript">
$(document).ready(function() {
	var content = document.getElementById("userName");
	var choosedUserName = document.getElementById("choosedUserName");
	var txtDeptName = document.getElementById("txtDeptName");
	if (content.value == "") {
		content.value = "请输入姓名或手机号";
	}
	if (choosedUserName.value == "") {
		choosedUserName.value = "请输入姓名或手机号";
	}
	
	if(txtDeptName.value == ""){
		txtDeptName.value = UserList.memberSearchDefaultText;
	}
	
	content.style.color = "#999";
	choosedUserName.style.color = "#999";
	txtDeptName.style.color = "#999";
	
	// 将选择框 里的数据添加到已选列表中
//	prop2stSelected();
	
   /**	<c:if test="${(param.action eq 'modify') || (param.action eq 'resend') }">
		var users = getSystemMessageChhooseEmp();
		opSelect1(users,"2stSelected");
	</c:if>***/
   UserList.setClickOnfalse();
});
function hideSe() {
	$("#lstSelected").hide();
		$("#2stSelected").hide();
	}
function showSe() {
	$("#lstSelected").show();
	$("#2stSelected").show();
}
function quanyi1() {
	var waitUser = $("#leftEmpUl");
	javascript : newRemoveAll(waitUser.get(0));
}
function prop2stSelected(){
	var userIds = parent.document.getElementById("permisionEmpIds").value 
	var userName = parent.document.getElementById("permisionNames").value;
	
	if(userIds != undefined && userName != undefined
		&& userIds != "" && userName != ""){
		var userIdsArray = userIds.split(",");
		var userNameArray = userName.split(",");
		
		var selectIdName = "2stSelected";
		var waitChangeName = "nowChange";
		
		var oDest = document.getElementById(selectIdName);
		var optionStrArray = new Array();
		optionStrArray.push('<select name="'+selectIdName+'" title="员工" id="'+selectIdName+'" multiple="multiple" ondblclick="dclickDel()" style="width: 160px; height: 203px; font-size: 12px; color: #0f406b">');
	
		for (var i = 0; i < userIdsArray.length; i++) {
			optionStrArray.push('<option value = "' + userIdsArray[i] + '">'
				+ userNameArray[i] + "</option>");
		}
		optionStrArray.push('</select>');
		var waitChange = document.getElementById(waitChangeName);
		var newStr = optionStrArray.toString();
		newStr = newStr.replace(/>,</g, "><");
		waitChange.innerHTML = newStr;
	}
}
function dclickDel(){
        yichu() ;
       }
function closeParent() {
	var opType = '${requestScope.optype}';
	if (opType == 'per') {

		parent.$(".jqi").css("width", "510px");
		parent.$.ImpromptuGoToState('state0');

	} else {
		parent.$.ImpromptuClose();
	}
}

function getAllSelectEmp(node) {//得到所有选择的员工
var idArr = new Array();
	var textArr = new Array();
	var allArr = new Array();
	var k = -1;

	for (i = 0; i < node.options.length; i++) {
		if (node.options[i].selected) {
			k++;
			idArr[k] = node.options[i].value;
			textArr[k] = node.options[i].text;
		}
	}
	allArr.push(idArr);
	allArr.push(textArr);
	return allArr;
}
        
       function dclickEmp(){
        add() ;
        }

function getAllEmp(node) {//得到所有的员工
	var idArr = new Array();
	var textArr = new Array();
	var allArr = new Array();
	var k = -1;

	for (i = 0; i < node.options.length; i++) {

		k++;
		idArr[k] = node.options[i].value;
		textArr[k] = node.options[i].text;

	}
	allArr.push(idArr);
	allArr.push(textArr);
	return allArr;
}

function getSelectedEmp(node) {//得到所有已经选择的员工

	var arr = new Array();

	for (i = 0; i < node.options.length; i++) {
		arr[node.options[i].value] = node.options[i].text;
	}
	return arr;
}

function initNewSelect(container, node, selectFirst, allSelected, selectedEmp) {

	var arrNew = new Array();
	var k = -1;
	var num = 0;
	var idArr = allSelected[0];
	var textArr = allSelected[1];
	for (i = 0; i < idArr.length; i++) {
		var curVal = idArr[i];
		if (selectedEmp[curVal] == null) {
			k++;
			num++;
			arrNew[k] = "<option value='" + idArr[i] + "' class='optionEmp'>" + textArr[i]
					+ "</option>";
		}
	}
    var newStr = arrNew.toString();
	newStr = newStr.replace(/>,</g, "><");
	newStr = selectFirst + newStr + node.innerHTML  + '</select>';
	container.innerHTML = newStr;
}

function add() {
	var waitUser = $("#lstSelected option:selected");


		var select1 = document.getElementById("lstSelected");
		var select2 = document.getElementById("2stSelected");
		var allSelected = getAllSelectEmp(select1);
		var selectedEmp = getSelectedEmp(select2);
		var selectFirst = '<select name="2stSelected" id="2stSelected" multiple="multiple" ondblclick="dclickDel()" style="width: 160px; height: 203px; font-size: 12px; color: #0f406b;">'
		initNewSelect(document.getElementById("nowChange"), select2,
				selectFirst, allSelected, selectedEmp);
	
}

function quanyi() {
   var hadUser = $("#2stSelected");
	javascript : newRemoveAll(hadUser.get(0));
	
        
}



function yichu() {
 
     var oDest = document.getElementById("2stSelected");
	removeSelected(oDest);
}

function selectall() {

	var select1 = document.getElementById("lstSelected");
	var select2 = document.getElementById("2stSelected");
	var allSelected = getAllEmp(select1);
	var selectedEmp = getSelectedEmp(select2);
	var selectFirst = '<select name="2stSelected" id="2stSelected" multiple="multiple" ondblclick="dclickDel()" style="width: 160px; height: 203px; font-size: 12px; color: #0f406b;">'
	initNewSelect(document.getElementById("nowChange"), select2, selectFirst,
			allSelected, selectedEmp);
}

var page = 1;
var totalPage = 1;
var pageSize = 1000;
var provPage = 1;
var nextPage = 2;
var objSize = 0;
        

/**
 * 添加部门 txt 信息
 */
function addDeptText(txt){
	if (txt.value.replace(/[ \t]+/g, "") == "") {
		txt.value = UserList.memberSearchDefaultText;
		txt.style.color = '#949494';
	}
}
/**
 * 删除部门txt信息
 */
function deleteDeptText(txt){
	
	if (txt.value.replace(/[ \t]+/g, "") == ""
			|| txt.value.replace(/[ \t]+/g, "") == UserList.memberSearchDefaultText) {
		txt.value = "";
		txt.style.color = "black";
	}
}


function addText(txt) {
	if (txt.value.replace(/[ \t]+/g, "") == "") {
		txt.value = "请输入姓名或手机号";
		txt.style.color = '#949494';
	}
}

function deleteText(txt) {
	if (txt.value.replace(/[ \t]+/g, "") == ""
			|| txt.value.replace(/[ \t]+/g, "") == "请输入姓名或手机号") {
		txt.value = "";
		txt.style.color = "black";
	}
}

function txtChange(txt) {
	txt.style.color = "";
}
        
  /***
       删除后台的session信息
        **/
        
function cleanMessageChhooseEmp(type,ids,datatype) {
	var date = new Date();
	thisUrl = "${base}/systemmessage/systemMessageTreeAction!updateSelectEmp.${actionExt}";
	$.ajax({
		type : "POST",
		async : false,
		url : thisUrl,
		data : "type="+type+"&ids="+ids+"&date="+date,
        dataType : '+datatype+',
		success : function(msg) {
        
           
           if(type="delete"){
			var hadUser = $("#2stSelected");
           $("#ypageInfo").hide();
	        newRemoveAll(hadUser.get(0));
       
            }else{
          alert("操作员工失败,请重试");
         }
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("操作员工失败");
		}
	});
}
        
/**
 * 根据部门信息 返回待办员工列表信息
 */
function getSystemMessageChhooseEmp() {
	var date = new Date();
    var url = "";
	thisUrl = "${base}/systemmessage/systemMessageAction!getSystemMessageChhooseEmp.${actionExt}";
	$.ajax({
		type : "POST",
		async : false,
		url : thisUrl,
		data : "ids=${param.aiid}"+"&pageSize="+pageSize,
		dataType : "json",
		success : function(msg) {
			users = msg;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("取得部门员工失败");
		}
	});
	return users;
}
        function loadPageInfo() {
	if(totalPage<=0) {
		$("#dpageInfo").hide();
		return;
	}
	$("#totalPage").html(page+"/"+totalPage);
	
	var first = $("#first");
	var prev = $("#prev");
	var next = $("#next");
	var last = $("#last");
	first.removeClass();
	prev.removeClass();
	next.removeClass();
	last.removeClass();
        
	
	if(totalPage<=1) {
		first.addClass("default_pgBtn default_pgFirstDisabled");
		prev.addClass("default_pgBtn default_pgPrevDisabled");
		next.addClass("default_pgBtn default_pgNextDisabled");
		last.addClass("default_pgBtn default_pgLastDisabled");
	} else if(totalPage>1) {
		if(page==1) {
			first.addClass("default_pgBtn default_pgFirstDisabled");
			prev.addClass("default_pgBtn default_pgPrevDisabled");
			next.addClass("default_pgBtn default_pgNext");
			last.addClass("default_pgBtn default_pgLast");
		     $("#first").unbind("click");
             $("#prev").unbind("click");
			$("#next").unbind("click").click(function(){ goPage(page+1); return false;}); 
		    $("#last").unbind("click").click(function(){ goPage(totalPage); return false;});
		} else if(page==totalPage) {
			first.addClass("default_pgBtn default_pgFirst");
			prev.addClass("default_pgBtn default_pgPrev");
			next.addClass("default_pgBtn default_pgNextDisabled");
			last.addClass("default_pgBtn default_pgLastDisabled");
			$("#next").unbind("click");
             $("#last").unbind("click");
			$("#first").unbind("click").click(function(){ goPage(1); return false;}); 
			$("#prev").unbind("click").click(function(){ goPage(page-1); return false;});
		} else {
			first.addClass("default_pgBtn default_pgFirst");
			prev.addClass("default_pgBtn default_pgPrev");
			next.addClass("default_pgBtn default_pgNext");
			last.addClass("default_pgBtn default_pgLast");
			$("#first").unbind("click").click(function(){ goPage(1); return false;}); 
		    $("#prev").unbind("click").click(function( ){ goPage(page-1); return false;});
			$("#next").unbind("click").click(function(){ goPage(page+1); return false;}); 
		    $("#last").unbind("click").click(function(){ goPage(totalPage); return false;});
		
		}
	}

	$("#dpageInfo").show();
}
        
 
function goPage(p) {
   	var users = getEmpBydepId($("#thisDeptId").val(),$("#userName").val(),p,pageSize,'notall');
	 opSelect(users);//操作员工到中间
}
        
        

/**
 * 根据部门信息 返回待办员工列表信息
 */
function getEmpBydepId(depId, userInfo) {
	var users;
	var date = new Date();
	userInfo = escape(encodeURIComponent(userInfo));
    var url = "";
	thisUrl = "${base}/systemmessage/systemMessageTreeAction!getSystemMessageEmp.${actionExt}?cmccFlag=0";
	$.ajax({
		type : "POST",
		async : false,
		url : thisUrl,
		data : "dates=" + date + "&deptId=" + depId + "&userInfo=" + userInfo,
		dataType : "json",
		success : function(msg) {
			users = msg;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("取得部门员工失败");
		}
	});
	return users;
}     
        
        
function getEmpBydepId(depId, userInfo,page,pageSize,type) {
	var users;
	var date = new Date();
	userInfo = escape(encodeURIComponent(userInfo));
    var thisUrl = "${base}/systemmessage/systemMessageTreeAction!getSystemMessageEmp.${actionExt}?cmccFlag=0";
        if(type=="all"){
        thisUrl = "${base}/systemmessage/systemMessageTreeAction!getAllMessageEmp.${actionExt}?cmccFlag=0";
        }
        if(type=="gopage"){
        thisUrl = "${base}/systemmessage/systemMessageTreeAction!getSelectEmpPage.${actionExt}";
        }
	$.ajax({
		type : "POST",
		async : false,
		url : thisUrl,
		data : "dates=" + date + "&deptId=" + depId +"&page="+page+"&pageSize="+pageSize+"&userInfo=" + userInfo,
		dataType : "json",
		success : function(msg) {
			users = msg;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("取得部门员工失败");
		}
	});
	return users;
}
/**
 * Ajax 异步，将员工信息添加的待选员工列表中
 */
function opSelect(users, selectId) {
	var selectIdName = "leftEmpUl";
	var waitChangeName = "waitChange";
	if(selectId){
		selectIdName = selectId
		waitChangeName = "nowChange";
	}
//	 var oDest = document.getElementById("leftEmpUl");
 //    quanyi(oDest);
	var optionStrArray = new Array();
	optionStrArray.push('<ul id="leftEmpUl" class="Per_sel_ul">');
	var userJson = users[0].userJson;
	if(users[0].returncount!=0){
       
		var userJson = users[0].userJson;
	     page = users[0]['page'];
	        
	    totalPage = users[0]['totalPage'];
		pageSize = users[0]['pageSize'];
		provPage = users[0]['provPage'];
		nextPage = users[0]['nextPage'];
		objSize = users[0]['objSize'];
	       
		var size = userJson.length;
		for (var i = 0; i < size; i++) {
			optionStrArray.push('<li id="'+ userJson[i].userId +'" v="' + userJson[i].userName+((userJson[i].userMark==null||userJson[i].userMark=="")?"":("("+userJson[i].userMark+")"))
				 + '" f="' + userJson[i].fullspell +'"'
				 + '" onclick="UserList.empClick($(this));" ondblclick="UserList.leftEmpDbClick($(this))">'
				 + '<label class="optionEmp">' + userJson[i].userName+((userJson[i].userMark==null||userJson[i].userMark=="")?"":("("+userJson[i].userMark+")")) 
				 + '</label></li>');
	      
		}
		optionStrArray.push('</ul>');
		var waitChange = document.getElementById(waitChangeName);
		var newStr = optionStrArray.toString();
		newStr = newStr.replace(/>,</g, "><");
		waitChange.innerHTML = newStr;
	    UserList.setAlladdFalseOrTrue();
		   if(objSize>pageSize){
		    	loadPageInfo() ;
		    }else{
		    	$("#dpageInfo").hide();
		    }
    }else{
    	$("#dpageInfo").hide();
    }
}
   

function toFind(e) {
	var isie = (document.all) ? true : false;
	var key;
	var nodeType;
	if (isie) {
		key = window.event.keyCode;
        nodeType = window.event.srcElement;
	} else {
		key = e.which;
        nodeType = e.target;
	}
     
	if (key == 13) {
		if(nodeType.name == 'txtDeptName'){
			//UserList.findDeptName();
		    document.getElementById("txtDeptNamehref").focus();
		}else if(nodeType.name == 'userName'){
			
		   // UserList.findUser();
		    document.getElementById("userNamehref").focus();
		}else{
			
		   // UserList.findChoosedUser();
		    document.getElementById("choosedUserNamehref").focus();
		   
	    }
	}
}

function getChoosedUserMp(){
    var userMps;
	var date = new Date();
	var choosedUserName = escape(encodeURIComponent($("#choosedUserName").val()));
    $.ajax({
		type : "POST",
		async : false,
		url : "${base}/systemmessage/systemMessageTreeAction!getChooseUserMp.${actionExt}",
		data : "dates=" + date + "&pageSize="+pageSize+"&ChoosedUserName=" + choosedUserName,
		dataType : "json",
		success : function(msg) {
			userMps = msg;
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		    document.location.href = "${base}/commons/error/error.jsp";
		}
	});
   return userMps ;
}
function addDept(){
	 var deptId  = $(window.frames["right1"].document).find("#deptId").val();
	 var deptName  =$(window.frames["right1"].document).find("#deptName").val();
	 if(checkDept(deptId)){
	    return false ;
	 }
   var container= $("#rightEmpUl")
   var optionStrArray = new Array();
   var _deptName = deptName;
   if(_deptName.length > 13){
   		_deptName = _deptName.substring(0,13);
   		_deptName = _deptName + "...";
   }
   
   optionStrArray.push('<li id=' + deptId + ' v=' +deptName
			+ ' onclick="UserList.empDeleteClick($(this));" ondblclick="UserList.rightEmpDbClick($(this));">' 
			+ '<label class="optionDept">'+_deptName+'</label>'+ '</li>');
	
	var newStr = optionStrArray.toString();
	newStr = newStr.replace(/>,</g, "><");
	container.prepend(newStr);
    UserList.setAllDeleteClickOnTrue();
}
 /***获取所有li的值***/
    function getAllLiValue(){
    var id_str = '';
	$('#userlist_right li').each(function(){
		var id = $(this).attr('id');
		//id_str += ','+id.split('_')[1];
		id_str += ','+$(this).attr('id');
	});
    
    return id_str ;
    }
    
    
  /*** 检查部门是否存在 **/     
function checkDept(deptId){
	var b=false ;
   
    $("#nowChange li").each(function() {
     var id = $(this).attr('id');
    	if(id==deptId){
        	b=true ;
         	return b ;
        } 
    });
    return b ;
}
/**
 * 通过部门名称，查询部门列表
 */
function findDeptName(){
	
	var value = $.trim($("#txtDeptName").val().replace(/[ \t]+/g, ""));

    if(value == UserList.memberSearchDefaultText){
    	value = "";
    /*
    	var msg = "请输入部门名称";
		$.prompt(msg, {
			buttons : {
				确定 : true
			},
			alertType : 'msg'
		});
	*/
    }

	var frame = $("#right1");
    var mathRandom = Math.random();
    frame.attr("src", "${base}/systemmessage/systemMessageTreeAction!initDepartment.${actionExt}?searchOwnerValue="+value+"&cmccFlag=0&empType=${param.empType}&kind=systemmessage&t=" + mathRandom);
}

</script>
	</head>
	<body style="background:none;" onload="focus()">
		<input id="thisDeptId" name="thisDeptId" value="all" type="hidden" />
		<input type="hidden" value="0" id="addMsg" name="addMsg" />
		<input type="hidden" value="0" id="ifsearch" name="ifsearch" />
		<div class="tanchu tanchu_xzfw"
			style="background:none;border:none; width:auto;">
			<div class="tanchu_xzfw_body">
				<div class="leftbox floatleft">
					<div class="h28">
						按部门查询
						<span class="pdlr4 lightgray">(双击添加部门)</span>
					</div>
					<div class="insbox3">
						<div class="inputbox">
							<input type="text" class="normalinput input floatleft"
								onfocus="deleteDeptText(this)" style="width:150px;"
								id="txtDeptName" name="txtDeptName" onblur="addDeptText(this)"
								onkeydown="toFind(event)" />
							<a id="txtDeptNamehref" href="#"
								onclick="findDeptName();return false;" class="inputa floatright"></a>
						</div>
						<div class="insbox4" id="waitChange">
							<ul id="leftEmpUl" class="Per_sel_ul">
								<c:forEach var="allAdmin" items="${allAdminList }">
									<li id="${allAdmin.identifier}">
										${allAdmin.admin}
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
					<div class="spacer"></div>
					<div>

						<p style="text-align:right">
							<button class="button_adnew_g" id="addDept" name="addDept"
								onclick="addDept()" style="cursor: pointer;" disabled></button>
						</p>
					</div>
				</div>
				<div class="vspacer floatleft"></div>
				<div class="midbox floatleft">
					<div class="h28">
						符合条件的用户
						<span class="pdlr4 lightgray">(双击直接添加)</span>
					</div>
					<div class="insbox3">
						<div class="inputbox">
							<input type="text" class="normalinput input floatleft"
								onfocus="deleteText(this)" id="userName" name="userName"
								onblur="addText(this)" onkeydown="toFind(event)" />
							<a id="userNamehref" href="#"
								onclick="UserList.findUser(this); return false;"
								class="inputa floatright"></a>
						</div>
						<div class="insbox4" id="waitChange">
							<ul id="leftEmpUl" class="Per_sel_ul"></ul>
						</div>
					</div>
					<table cellspacing="0" cellpadding="0" border="0" width="100%"
						class="default_pgToolbar" id="dpageInfo" name="dpageInfo"
						style="Display:none; background:none; border:0;">
						<tbody>
							<tr>
								<td>
									<table width="100%">
										<tbody>
											<tr>

												<td align="right">
													<table cellspacing="0" border="0" cellspadding="0">
														<tbody>
															<tr>
																<td valign="middle">
																	<div class="default_pgBtn default_pgFirst" title="首页"
																		id="first" name="first"></div>
																</td>
																<td valign="middle">
																	<div class="default_pgBtn default_pgPrev" title="上页"
																		id="prev" name="prev"></div>
																</td>

																<td valign="middle" id="totalPage" name="totalPage">
																	1/1
																</td>
																<td valign="middle">
																	<div class="default_pgBtn default_pgNext" title="下页"
																		id="next" name="next"></div>
																</td>
																<td valign="middle">
																	<div class="default_pgBtn default_pgLast" title="尾页"
																		id="last" name="last"></div>
																</td>


															</tr>
														</tbody>
													</table>
												</td>
											</tr>
										</tbody>
									</table>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="vspacer floatleft"></div>
				<div class="midbt floatleft">
					<p>
						<button class="button button1" id="addEmp" name="addEmp"
							onclick="UserList.addLeftEmp();"></button>
					</p>
					<div class="spacer"></div>
					<p>
						<button class="button button2" id="deleteEmp" name="deleteEmp"
							onclick="UserList.removeRightEmp();"></button>
					</p>
					<div class="spacer"></div>
					<p>
						<button class="button button3" id="addAllEmp" name="addAllEmp"
							onclick="UserList.allAddLeft()"></button>
					</p>
					<div class="spacer"></div>
					<p>
						<button class="button button4" id="deleteAllEmp"
							name="deleteAllEmp" onclick="UserList.allRemoveRight();"></button>
					</p>
				</div>
				<div class="vspacer floatleft"></div>
				<div class="midbox floatleft">
					<div class="h28">
						已选部门/用户
						<span class="pdlr4 lightgray">(双击删除)</span>
					</div>
					<div class="insbox3">
						<div class="inputbox">
							<input type="text" class="normalinput input floatleft"
								id="choosedUserName" name="choosedUserName"
								onfocus="deleteText(this)" onblur="addText(this)"
								onkeydown="toFind(event)" />
							<a id="choosedUserNamehref" href="#"
								onclick="UserList.findChoosedUser(); return false;"
								class="inputa floatright"></a>
						</div>
						<div id="nowChange" class="insbox4">
							<ul id="rightEmpUl" class="Per_sel_ul">
								<c:forEach var="admin" items="${adminList }">
									<li id="${admin.identifier}">
										${admin.admin}
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
	</body>
</html>
