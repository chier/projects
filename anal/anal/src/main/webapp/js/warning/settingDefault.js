// 左侧树配置信息
var setting = {
	currNode : null,
	data: {
		simpleData: {
			enable: true
		}
	},
	callback: {
		beforeClick: beforeClick,
		onClick: onTreeClick,
		onDblClick: onTreeDbClick
	}
};



// 左侧树请求事件
function beforeClick(treeId, treeNode, clickFlag) {
	return true;
}

function onTreeClick(event, treeId, treeNode, clickFlag) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo-warning");
	zTree.expandNode(treeNode);
	if((treeNode.isParent == false)){
		setting.currNode = treeNode;
	}else{
		setting.currNode = null;
	}
}
function onTreeDbClick(event,treeId,treeNode,clickFlag){
	btnFirstClick();
}


/**
 * 显示定制分析树
 */
function viewTreeTypeCodeInfo(){
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/warning/warningAction!viewTreeTypeCodeInfo."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	//	data : "rid="+$("#rid").val(),
		dataType : "json",
		success : function(msg) {
			var _zNodes = msg["trees"];
			$.fn.zTree.init($("#treeDemo-warning"), setting, _zNodes);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			window.top.location=base + "/commons/beforetimeout.jsp";
			
		}
	});
}

/**
 * 第一节点点击方法
 */
function btnFirstClick(){
	if(setting.currNode){
		$("#tabFirst tr td:eq(0)").html(setting.currNode.name);
		$("#tabFirst tr td:eq(1)").html(setting.currNode.codeValue);
		$("#tabFirst tr td:eq(2)").html(setting.currNode.tableValue);
		
		$("#tabFirst .txtValue").each(function(index){
			if($(this).attr("name") == "hidFirstMax"){
				$(this).val(setting.currNode.maxVal);
				$("#hidFirstMax").val(setting.currNode.maxVal);
			}
			if($(this).attr("name") == "hidFirstMin"){
				$(this).val(setting.currNode.minVal);
				$("#hidFirstMin").val(setting.currNode.minVal);
			}
		});
		
		$("#hidId").val(setting.currNode.id);
	}
}

/**
 * 表头条件选择事件
 */
function perUlLiClick(obj){
	
	$(".Per_sel_ul li").each(function(){
		if($(this).attr("id") == obj.attr("id")){
			obj.attr("class","bg");
		}else{
			$(this).removeAttr("class");
		}
	});
	/*			
		// 当指标是数字时 维度按钮 不能点击				
		var _itemType = obj.attr("itemType");
		if(_itemType == 1){
			$("#btn_Dimension").attr("disabled", true); 
		}else{
			$("#btn_Dimension").removeAttr("disabled"); 
		}
	*/
}


/**
 * 保存设置的默认值
 */
function forwardSaveWarning(){
	var _hidFirstMax = $("#hidFirstMax").val();
	var _hidFirstMin = $("#hidFirstMin").val();
	var _hidId = $("#hidId").val();
	
	if(!$.trim($("#tabFirst tr td:eq(0)").html())){
		top.$.prompt("请选择一个指标！",{
			alertType:'msg'
		});
		return;
	}
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/warning/warningAction!saveSettingDefault."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "hidFirstMin=" + _hidFirstMin + "&hidFirstMax=" + _hidFirstMax + "&hidId=" + _hidId,
		// dataType : "json",
		success : function(msg) {
			if(msg == "0"){
				setting.currNode.maxVal = _hidFirstMax;
				setting.currNode.minVal = _hidFirstMin;
				var zTree = $.fn.zTree.getZTreeObj("treeDemo-warning");
				zTree.updateNode(setting.currNode);
				top.$.prompt("保存成功！",{
					alertType:'msg'
				});
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			window.top.location=base + "/commons/beforetimeout.jsp";
			
		}
	});
	
}


function save(_mf,_hidVal){
	 // alert(_mf.$("#txtName").val());
	 $.ajax({
		 type : "POST",
		 async : true,
		 url   :  base+"/manage/warning/warningAction!saveWarning."+actionExt,
		 data : "hidFirstCode=" + _mf.$("#hidFirstCode").val()
		 	 +"&hidFirstName=" + _mf.$("#hidFirstName").val()
		 	 +"&hidFirstTable=" + _mf.$("#hidFirstTable").val()
		 	 +"&hidFirstMin=" + _mf.$("#hidFirstMin").val()
		 	 +"&hidFirstMax=" + _mf.$("#hidFirstMax").val()
		 	 +"&hidSecondCode=" + _mf.$("#hidSecondCode").val()
		 	 +"&hidSecondName=" + _mf.$("#hidSecondName").val()
		 	 +"&hidSecondTable=" + _mf.$("#hidSecondTable").val()
		 	 +"&hidSecondMin=" + _mf.$("#hidSecondMin").val()
		 	 +"&hidSecondMax=" + _mf.$("#hidSecondMax").val()
		 	 +"&hidArea=" + _mf.$("#hidArea").val()
		 	 +"&hidSaveWar=" + _hidVal
		 	 +"&txtName="+_mf.$("#txtName").val(),
		// dataType : "json",
		success : function(msg) {
			top.$.prompt("保存成功!",{alertType:'msg'});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			window.top.location = base + "/commons/beforetimeout.jsp";
		}
	 });
}

$(document).ready(function(){
	// 显示树
	viewTreeTypeCodeInfo();
	
	//查询事件提交
	// $("#btnSelect").bind("click", reportTable); 
	$("#btnSave").bind("click",forwardSaveWarning);
	 
	 //  对所有输入框进行数字判断
// 	$("input[type=text]")
	$(".txtValue").each(function(){
	 	$(this).bind("keyup",function(){
	 		if(!((event.keyCode>=48&&event.keyCode<=57)
						|| (event.keyCode>=96&&event.keyCode<=105))){//考虑小键盘上的数字键
						if((event.keyCode!=190)	&& (event.keyCode != 110)){// 小数点
							event.returnvalue=false;
							
							$(this).val($(this).val().substring(0,$(this).val().length-1))// ;value=pro.value.substring(0,pro.value.length-1);
						}
						if(($(this).val().indexOf(".") != $(this).val().lastIndexOf("."))
							||$(this).val().indexOf(".") == 0){
							$(this).val($(this).val().substring(0,$(this).val().length-1))
						}
			}
			$("#" + $(this).attr("name")).val($(this).val());
	 	});
	 });
});


















