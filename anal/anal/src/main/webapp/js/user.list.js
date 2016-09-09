
var UserList = {
	dept_select_obj:null,//选中的部门对象
	left_emp_select_obj:null,//选中的待选员工对象
	righ_emp_select_obj:null,//选中的已选员工对象
	memberSearchDefaultText : "请输入部门名称",
	isEvent : false,
/**
 * 设置事件值
 * @param {} value
 */
setEvent:function(value){
	UserList.isEvent = value;
},
/**
 * 还原事件默认值
 */
defaultEvent:function(){
	UserList.isEvent = false;
},

/**************************************************************** 部门 *******************************************/
/**
 * 部门点击事件
 * @param {} obj
 */
deptClick:function (obj) {
	$("#deptUl li").each(function(){
		$(this).removeClass();
	});
	obj.toggleClass("bg");
	UserList.dept_select_obj = obj;
	setTimeout(this.deptDoClick,500);
},
/**
 * 部门双击事件
 * @param {} obj
 */
deptDbClick:function(obj){
	document.getElementById("deptId").value = obj.attr("id");
	document.getElementById("deptName").value = obj.attr("v");
    setButtionDis();
    
	parent.addDept();
	
	UserList.isEvent = true;
	setTimeout(this.defaultEvent,700);
},
/**
 * 部门点击实现事件
 */
deptDoClick:function(){
	if(!UserList.isEvent){
		
		var selDeptid = UserList.dept_select_obj.attr("id");
		var selDeptName = UserList.dept_select_obj.attr("v");
		
		page=1 ;
		document.getElementById("deptId").value = selDeptid;
		document.getElementById("deptName").value = selDeptName;
		var users = getUsers(selDeptid,page,pageSize);
		
		if (users == '-1') {
			parent.parent.document.location.href = "${base}/commons/error/error.jsp";
			return;
		}
	    setButtionDis(); // 添加部门节点可以使用
		opSelect(users);//操作员工到中间
		
		 UserList.setAlladdFalseOrTrue();
		
		if (parent.document.getElementById("thisDeptId")) {
			parent.document.getElementById("thisDeptId").value = selDeptid;
		}
	    $("#ifsearch",window.parent.document).val(0);
	}
},
/**************************************************************** 员工公共方法 *******************************************/

/**
 * 员工单击事件
 * @param {} obj
 */
empClick:function(obj){
	obj.toggleClass("bg");
	UserList.setAddClickOnTrue();
},

empDeleteClick:function(obj){
	obj.toggleClass("bg");
	UserList.setAllDeleteClickOnTrue();
},
/**************************************************************** 待选员工 *******************************************/

/**
 * 员工双击事件
 * @param {} obj
 */
leftEmpDbClick:function(obj){
	$('#leftEmpUl li').each(function(){
		$(this).removeClass();
	});
	obj.toggleClass("bg");
	this.addLeftEmp() ;
},
/**
 * 待选员工列表中所有选中的员工
 */
leftSelectEmp:function(){
	var idArr = new Array(); // 员工id集合
	var textArr = new Array();
	var fullArr = new Array();
	var allArr = new Array();
	var k = -1;
	$('#leftEmpUl li').each(function(){
		if($(this).hasClass('bg')){
			k++;
			idArr[k] = $(this).attr("id");
			textArr[k] = $(this).attr("v");
			fullArr[k] = $(this).attr("f");
		}
	});
	if(k == -1){
		return allArr;
	}
	allArr.push(idArr);
	allArr.push(textArr);
	allArr.push(fullArr);
	return allArr;
},

/**
 * 待选员工列表中所有选中的员工
 */
leftAllEmp:function(){
	var idArr = new Array(); // 员工id集合
	var textArr = new Array();
	var allArr = new Array();
	var fllArr = new Array();
	var k = -1;
	$('#leftEmpUl li').each(function(){
		k++;
		idArr[k] = $(this).attr("id");
		textArr[k] = $(this).attr("v");
		fllArr[k] = $(this).attr("f");
	});
	if(k == -1){
		return allArr;
	}
	allArr.push(idArr);
	allArr.push(textArr);
	allArr.push(fllArr);
	return allArr;
},
/**************************************************************** 已选员工 *******************************************/

/**
 * 已选员工列表中已存在的员工
 * error 返回都是逗号，很奇怪
 */
rightAllEmp:function(){
	var arr = new Array();
	$('#rightEmpUl li').each(function(){
		var id = $(this).attr("id");
		var value = $(this).attr("v")
		arr[id] = value;
	});
	return arr;
},

/**
 * 已选员工的双击方法 
 * @param {} obj
 */
rightEmpDbClick:function(obj){
	$('#rightEmpUl li').each(function(){
		$(this).removeClass();
	});
	obj.toggleClass("bg");
	this.removeRightEmp() ;
	this.setAllDeleteClickOnTrue();
},

/**
 * 已选员工列表中所有选中的员工
 */
rightSelectEmp:function(){
	var idArr = new Array(); // 员工id集合
	var textArr = new Array();
	var allArr = new Array();
	var k = -1;
	$('#rightEmpUl li').each(function(){
		if($(this).hasClass('bg')){
			k++;
			idArr[k] = $(this).attr("id");
			textArr[k] = $(this).attr("v");
		}
	});
	if(k == -1){
		return allArr;
	}
	allArr.push(idArr);
	allArr.push(textArr);
	return allArr;
},

/**************************************************************** 按钮事件 *******************************************/
/**
 * 添加员工的实现方法
 */
doAddEmp:function(){
	
},
/**
 * 添加待选中员工
 */
addLeftEmp:function(){
	var leftSelectEmps = UserList.leftSelectEmp(); // 待选列表中选择的员工
	if (leftSelectEmps.length < 1) {
        	var txt = "请选择员工进行操作";
			$.prompt(txt,{ alertType:'msg' });
		return;
	} else {
		var rightSelectEmps = UserList.rightAllEmp();// 已选列表中选择的员工
		var container= $("#rightEmpUl")
		var idArr = leftSelectEmps[0];
		var textArr = leftSelectEmps[1];
		var fullArr = leftSelectEmps[2];
		for (i = 0; i < idArr.length; i++) {
			var curVal = idArr[i];
			if (rightSelectEmps[curVal] == null) {
				var str = '<li id="' + idArr[i] + '" v="'+ textArr[i] +'" f="'+ fullArr[i] +'" onclick="UserList.empDeleteClick($(this));" ondblclick="UserList.rightEmpDbClick($(this))">' 
						 + '<label class="optionEmp">' + textArr[i] + '</label></li>';
				str = str.replace(/>,</g, "><");
				container.prepend(str);
			}
	}
	}
	this.setAllDeleteClickOnTrue() ;
},

/**
 * 删除选中的已选员工
 */
removeRightEmp:function(){
	$('#rightEmpUl li').each(function(){
		if($(this).hasClass('bg')){
			$(this).remove();
		}
	});
	this.setAllDeleteClickOnTrue();
},
/**
 * 全部添加待选列表
 */
allAddLeft:function(){
	var leftAllEmps = UserList.leftAllEmp(); // 待选列表中所有员工
	var rightAllEmps = UserList.rightAllEmp(); // 已选列表中所有员工
	var container= $("#rightEmpUl")
	var idArr = leftAllEmps[0];
	var textArr = leftAllEmps[1];
	var fullArr = leftAllEmps[2];
	for (i = 0; i < idArr.length; i++) {
		var curVal = idArr[i];
		if (rightAllEmps[curVal] == null) {
			var str = '<li id="' + idArr[i] + '" v="'+ textArr[i] +'" f="'+ fullArr[i] +'" onclick="UserList.empDeleteClick($(this));" ondblclick="UserList.rightEmpDbClick($(this))">' 
					 + '<label class="optionEmp">' + textArr[i] + '</label></li>';
			str = str.replace(/>,</g, "><");
			container.prepend(str);
		}
	}
	this.setAllDeleteClickOnTrue() ;
},
/**
 * 全部删除已选列表
 */
allRemoveRight:function(){
	$('#rightEmpUl li').each(function(){
		$(this).remove();
	});
	this.setAllDeleteClickOnTrue();
},
/**
 * 搜索已选员工，并高亮显示
 */
findChoosedUser:function(){
	$("#choosedUserName").focus();
	var choosedUserName = $("#choosedUserName").val().replace(/[ \t]+/g, "");
	if (choosedUserName == "" || choosedUserName == "请输入姓名或手机号") {
		var msg = "请输入姓名或手机号";
		$.prompt(msg, {
			buttons : {
				确定 : true
			},
			alertType : 'msg'
		});
		return;
	}
	if($('#rightEmpUl li').length>0){
		 $("#deleteEmp").attr("disabled",false);
		 $("#deleteEmp").removeClass("button ubutton2");
		 $("#deleteEmp").addClass("button button2");
	}
	var strHtml = new Array();
	$('#rightEmpUl li').each(function(i,n){
		if($(this).hasClass('bg')){
			$(this).removeClass();
		}
		var text = $(this).attr("v");
		var textFll = $(this).attr("f");
		var textId = $(this).attr("id");
		if(text.indexOf(choosedUserName) != -1 
			|| (textFll && textFll.indexOf(choosedUserName) != -1 )) {
			$(this).prependTo("#rightEmpUl");
			$(this).toggleClass("bg");
		}
	});
},

/**
 * 待选员工搜索
 */
findUser:function() {
	
   page=1 ;
	var userName = $("#userName").val().replace(/[ \t]+/g, "");
	$("#userName").focus();
	if (userName == "" || userName == "请输入姓名或手机号") {
		function mycallbackfunc(v, m, f) {
			showSe();
		}
		parent.$.prompt("请输入姓名或手机号", {
			buttons : {
				确定 : true
			},
			alertType : 'msg',
			callback : mycallbackfunc
		});
		hideSe();
		return;
	}
	quanyi1();
   $("#ifsearch").val("1");
	var users = getEmpBydepId($("#thisDeptId").val(), $("#userName").val(),page,pageSize,'notall');
	opSelect(users);
},
/**
 * 通过部门名称，查询部门列表
 */
findDeptName:function(){
	var value = $.trim($("#txtDeptName").val().replace(/[ \t]+/g, ""));
	
    if(value == this.memberSearchDefaultText ||value==""){
    	var msg = "请输入部门名称";
		$.prompt(msg, {
			buttons : {
				确定 : true
			},
			alertType : 'msg'
		});
		return;
    }
	var frame = $("#right1");
    var mathRandom = Math.random();
    frame.attr("src", "${base}/systemmessage/systemMessageTreeAction!initDepartment.${actionExt}?searchOwnerValue="+value+"&empType=${param.empType}&kind=systemmessage&t=" + mathRandom);
},
/***设置按钮不可用***/
setClickOnfalse:function(){
	$("#addEmp").attr("disabled",true);
	$("#deleteEmp").attr("disabled",true);
	$("#addAllEmp").attr("disabled",true);
	$("#deleteAllEmp").attr("disabled",true);

	$("#addEmp").toggleClass("button ubutton1");
	$("#deleteEmp").toggleClass("button ubutton2");
	$("#addAllEmp").toggleClass("button ubutton3");
	$("#deleteAllEmp").toggleClass("button ubutton4");
	
},
/***选中的时候按钮可用***/
setAddClickOnTrue:function(){
   $("#addEmp").removeClass("button ubutton1");
	$("#addAllEmp").removeClass("button ubutton3");
	$("#addEmp").attr("disabled",false);
	$("#addEmp").addClass("button button1");
	$("#addAllEmp").attr("disabled",false);
	$("#addAllEmp").addClass("button button3");
},
/***根据第二个框的值来判断全部添加按钮是否可用***/

setAlladdFalseOrTrue:function(){
	var trNum=0 ;
	var tg ;
	if(parent.document.getElementById("waitChange")==null){
		tg=document.getElementById("waitChange") ;
		var trNum = tg.getElementsByTagName("li").length;
	if(trNum==0){
		$("#addAllEmp").attr("disabled",true);
		$("#addAllEmp").toggleClass("button ubutton3");
		$("#addEmp").attr("disabled",true);
		$("#addEmp").toggleClass("button ubutton1");
	}if(trNum>0){
		if(this.ifLeftEmpCheck()){
			
			$("#addEmp").attr("disabled",false);
		$("#addEmp").removeClass("button ubutton1");
	    $("#addEmp").toggleClass("button button1");
		}
		$("#addEmp").attr("disabled",true);
		$("#addEmp").removeClass("button button1");
	    $("#addEmp").toggleClass("button ubutton1");
		$("#addAllEmp").attr("disabled",false);
		$("#addAllEmp").removeClass("button ubutton3");
	    $("#addAllEmp").toggleClass("button button3");
	}
	}else{
		tg=parent.document.getElementById("waitChange") ;
		var trNum = tg.getElementsByTagName("li").length;
	if(trNum==0){
		$("#addEmp",window.parent.document).attr("disabled",true);
		$("#addEmp",window.parent.document).toggleClass("button ubutton1");
		$("#addAllEmp",window.parent.document).attr("disabled",true);
		$("#addAllEmp",window.parent.document).toggleClass("button ubutton3");
	}if(trNum>0){
		if(this.ifLeftEmpCheck()){
			$("#addEmp",window.parent.document).attr("disabled",false);
		$("#addEmp",window.parent.document).removeClass("button ubutton1");
	    $("#addEmp",window.parent.document).toggleClass("button button1");
		}
		$("#addEmp",window.parent.document).attr("disabled",true);
		$("#addEmp",window.parent.document).removeClass("button button1");
	    $("#addEmp",window.parent.document).toggleClass("button ubutton1");
		$("#addAllEmp",window.parent.document).attr("disabled",false);
		$("#addAllEmp",window.parent.document).removeClass("button ubutton3");
	    $("#addAllEmp",window.parent.document).toggleClass("button button3");
	}
	}
	
	
},

/**添加的时候显示能全部删除**/
setdeleteClickOnTrue:function(){
	    $("#deleteEmp").attr("disabled",false);
	    $("#deleteEmp").removeClass("button ubutton2");
		$("#deleteEmp").addClass("button button2");
		$("#deleteAllEmp").attr("disabled",false);
		$("#deleteAllEmp").removeClass("button ubutton4");
		$("#deleteAllEmp").addClass("button button4");
	

},

/***添加或者全部添加的时候能显示删除按钮****/
setAllDeleteClickOnTrue:function(){
	var trNum = document.getElementById("nowChange").getElementsByTagName("li").length;
	if(trNum>0){
		if(this.ifRightEmpCheck()){
		 $("#deleteEmp").attr("disabled",false);
		$("#deleteEmp").removeClass("button ubutton2");
		$("#deleteEmp").addClass("button button2");
		}else{
		 $("#deleteEmp").attr("disabled",true);
		$("#deleteEmp").removeClass("button button2");
		$("#deleteEmp").addClass("button ubutton2");
		}
	    $("#deleteAllEmp").attr("disabled",false);
		$("#deleteAllEmp").removeClass("button ubutton4");
		$("#deleteAllEmp").addClass("button button4");
		}if(trNum==0){
			
		$("#deleteEmp").attr("disabled",true);
		$("#deleteEmp").removeClass("button button2");
		$("#deleteEmp").addClass("button ubutton2");
		$("#deleteAllEmp").attr("disabled",true);
		$("#deleteAllEmp").removeClass("button button4");
		$("#deleteAllEmp").addClass("button ubutton4");
		}
	
},


/*****判断已选员工是否有选中的*******/
ifRightEmpCheck:function(){
	var t=false ;
	$('#rightEmpUl li').each(function(){
		if($(this).hasClass('bg')){
			t=true
		}
	});
	return t ;
},
/**判断待选员工是否有选中的****/
ifLeftEmpCheck:function(){
	var t=false ;
	$('#leftEmpUl li').each(function(){
		if($(this).hasClass('bg')){
			t=true
		}
	});
	return t ;
}


}