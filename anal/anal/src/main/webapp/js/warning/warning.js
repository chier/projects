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
// 	alert("双击效果");
	// 当第一参数为空时
	if($.trim($("#hidFirstName").val()) == ""){
		btnFirstClick();
	}else{
		btnSecondClick();
	}
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
			// var zNodes = msg;
			/*
			var _btns = msg["btns"];
			if(_btns){
				for(var i=0;i<_btns.length;i++){
					if(_btns[i]){
						$("#"+_btns[i]).attr("checked","true");
					}
				}
			}
			*/
			var _zNodes = msg["trees"];
			// $.fn.zTree.init($("#treeDemo"), setting, _zNodes);
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
		
		$("#hidFirstName").val(setting.currNode.name);
		$("#hidFirstCode").val(setting.currNode.codeValue);
		$("#hidFirstTable").val(setting.currNode.tableValue);
	}
}

/**
 * 第二节点点击方法
 */
function btnSecondClick(){
	if(setting.currNode){
		$("#tabSecond tr td:eq(0)").html(setting.currNode.name);
		$("#tabSecond tr td:eq(1)").html(setting.currNode.codeValue);
		$("#tabSecond tr td:eq(2)").html(setting.currNode.tableValue);
		
		$("#tabSecond .txtValue").each(function(index){
			if($(this).attr("name") == "hidSecondMax"){
				$(this).val(setting.currNode.maxVal);
				$("#hidSecondMax").val(setting.currNode.maxVal);
			}
			if($(this).attr("name") == "hidSecondMin"){
				$(this).val(setting.currNode.minVal);
				$("#hidSecondMin").val(setting.currNode.minVal);
			}
		});
		
		$("#hidSecondName").val(setting.currNode.name);
		$("#hidSecondCode").val(setting.currNode.codeValue);
		$("#hidSecondTable").val(setting.currNode.tableValue);
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
 * 提交查询报表table信息
 */
function reportTable(){
		if(!$.trim($("#tabSecond tr td:eq(0)").html()) 
			&& !$.trim($("#tabFirst tr td:eq(0)").html())){
			top.$.prompt("请选择一个指标！",{
				alertType:'msg'
			});
			return;
		}
		//select 选择区域值获取
		$("#hidArea").val($("#selArea").val());
		
	    var txt_iframe ='<iframe frameBorder="0" width="800" height="400" '
	            	+ 'allowtransparency="true" id="warnIframe" name="warnIframe" '
	            	+ 'style="display:block;border: 1px solid rgb(202, 217, 240);margin:0 auto;"></iframe>';
	   
	   var _txt_form = $("#divFormWarning").html();
	   _txt_form = _txt_form.replace("actionurl",base + "/manage/warning/warningAction!queryReportTable." + actionExt);
	   var _txt_main = '<div style="width:880px;height:480px;padding-left:5px;border:0px solid red;">浏览显示<br /><br />'
							+ txt_iframe
							+ _txt_form
							+ '</div><script>$(document).ready(function(){$("#formWarning").submit();})\n function clearData(){top.$.prompt("没有数据！",{alertType:"msg"});top.jQuery.ImpromptuClose();}</script>';
	top.$.prompt(_txt_main,{
		width:"900px",
		height:"500px",
		buttons:{确定:true},
		submit:function(v,m,f){
			//if(v){
			//	alert("确定");
			//}else{
				top.jQuery.ImpromptuClose();
			//}
		}
	});
}

/**
 * 保存预警分析方法 
 */
function forwardSaveWarning(){
	if(!$.trim($("#tabSecond tr td:eq(0)").html()) 
			&& !$.trim($("#tabFirst tr td:eq(0)").html())){
			top.$.prompt("请选择一个指标！",{
				alertType:'msg'
			});
			return;
		}
		
		if(!$.trim($("#txtName").val())){
			top.$.prompt("请填写标题！",{
				alertType:'msg'
			});
			return ;
		}
		
		//select 选择区域值获取
		$("#hidArea").val($("#selArea").val());
		
	    var txt_iframe ='<iframe frameBorder="0" height="400" '
	            	+ 'allowtransparency="true" id="warnIframe" name="warnIframe" src="'+base+'/html/warning/saveWarning.jsp"' 
	            	+ 'style="display:block;border: 0px solid rgb(202, 217, 240);margin:0 auto;"></iframe>';
	   
	   var _txt_main = '<div style="padding-left:5px;border:0px solid red;">'
							+ txt_iframe
							// + _txt_form
							+ '</div>';
	top.$.prompt(_txt_main,{
		width:"400px",
		height:"400px",
		buttons:{确定:true, 取消:false},
		submit:function(v,m,f){
			if(v){
				var _mf;
				if(document.frames){
					// IE
					_mf   = top.document.frames["mainIframe"];
				}else{
					// FF
					 _mf = top.document.getElementById("mainIframe").contentWindow;
				}
				var warnIframe;
				if(document.frames){
					// IE
					warnIframe   = top.document.frames["warnIframe"];
				}else{
					// FF
					 warnIframe = top.document.getElementById("warnIframe").contentWindow;
				}
				var _hidVal = warnIframe.getChangeNodeId();
				
				// _mf.save(_mf,_hidVal);
				var _adminRight;
				if(document.frames){
					// IE
					_adminRight   = _mf.document.frames["adminRight"];
				}else{
					// FF
					 _adminRight = _mf.document.getElementById("adminRight").contentWindow;
				}
				_adminRight.save(_adminRight,_hidVal);
			}
			top.jQuery.ImpromptuClose();
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
	$("#btnSelect").bind("click", reportTable); 
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


















