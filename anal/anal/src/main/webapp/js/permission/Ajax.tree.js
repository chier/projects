/**
 * ztree 配置信息
 */
var setting = {
	check: {
		enable: true
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};	

/**
 * 显示定制分析树
 */
function viewCustomTree(){
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!viewCustomTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid="+$("#rid").val(),
		dataType : "json",
		success : function(msg) {
			// var zNodes = msg;
			var _btns = msg["btns"];

			if(_btns){
				for(var i=0;i<_btns.length;i++){
					if(_btns[i]){
						$("#"+_btns[i]).attr("checked","true");
					}
				}
			}
			var _zNodes = msg["trees"];
			$.fn.zTree.init($("#treeDemo"), setting, _zNodes);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

/**
 * 保存定制分析树的信息
 */
function executeCustomTree(){

	// btn 值获取
	var chs = new Array();
	var _i = 0;
	var _s = $("input[name='customBtn']:checked").size();
	$("input[name='customBtn']:checked").each(function(){
		if($(this).val()){
			chs.push($(this).val());
			if(_i != _s-1){
				chs.push(",");
			}
		}
		_i ++;
	});
	
	
	//树节点权限
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
	var nodes = treeObj.getCheckedNodes(true);
	var _str = "";
	for(var i=0,l= nodes.length;i<l;i++){
		_str += nodes[i].id;
		if(i != l-1){
			_str += ",";
		}
	}
	
	//提交保存
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!executeCustomTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid=" + $("#rid").val() + "&rname="+$("#rname").val() + "&pids="+_str+"&btns="+chs.toString(),
		// dataType : "json",
		success : function(msg) {
			var _m = "保存成功!";
			if(msg != "true"){
				_m = "保存失改!";
			}
			top.$.prompt(_m, {
				buttons : {
					确定 : true
				},
				alertType : 'msg'
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}
/**
 * 显示即席查询 tree
 */
function viewQueriesTree(){
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!viewQueriesTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid="+$("#rid").val(),
		dataType : "json",
		success : function(msg) {
			// var zNodes = msg;
			var _btns = msg["btns"];
//			console.info(_btns);
			if(_btns){
				for(var i=0;i<_btns.length;i++){
					if(_btns[i]){
						$("#"+_btns[i]).attr("checked","true");
					}
				}
			}
			var _zNodes = msg["trees"];

//			console.info(_zNodes);
			$.fn.zTree.init($("#treeDemo-queries"), setting, _zNodes);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}




/**
 * 保存即席查询树的信息
 */
function executeQueriesTree(){
	// btn 值获取
	var chs = new Array();
	var _i = 0;
	var _s = $("input[name='queriesBtn']:checked").size();
	$("input[name='queriesBtn']:checked").each(function(){
		if($(this).val()){
			chs.push($(this).val());
			if(_i != _s-1){
				chs.push(",");
			}
		}
		_i ++;
	});
	
	
	// tree值 获取
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo-queries");
	var nodes = treeObj.getCheckedNodes(true);
	var _str = "";
	for(var i=0,l= nodes.length;i<l;i++){
		_str += "'" +nodes[i].typeValue + "'";
		if(i != l-1){
			_str += ",";
		}
	}
	
	//提交保存
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!executeQueriesTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid=" + $("#rid").val() + "&rname="+$("#rname").val() + "&pids="+_str+"&btns="+chs.toString(),
		// dataType : "json",
		success : function(msg) {
			var _m = "保存成功!";
			if(msg != "true"){
				_m = "保存失改!";
			}
			top.$.prompt(_m, {
				buttons : {
					确定 : true
				},
				alertType : 'msg'
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}



/**
 * 显示loap tree
 */
function viewLoapTree(){
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!viewLoapTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid="+$("#rid").val(),
		dataType : "json",
		success : function(msg) {
			var _btns = msg["btns"];
			if(_btns){
				for(var i=0;i<_btns.length;i++){
					if(_btns[i]){
						$("#"+_btns[i]).attr("checked","true");
					}
				}
			}
			var _zNodes = msg["trees"];
			$.fn.zTree.init($("#treeDemo-loap"), setting, _zNodes);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}




/**
 * 保存loap树的信息
 */
function executeLoapTree(){
	// btn 值获取
	var chs = new Array();
	var _i = 0;
	var _s = $("input[name='loapBtn']:checked").size();
	$("input[name='loapBtn']:checked").each(function(){
		if($(this).val()){
			chs.push($(this).val());
			if(_i != _s-1){
				chs.push(",");
			}
		}
		_i ++;
	});
	
	//loap 树节点权限获取
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo-loap");
	var nodes = treeObj.getCheckedNodes(true);
	var _str = "";
	for(var i=0,l= nodes.length;i<l;i++){
		_str += "'" +nodes[i].typeValue + "'";
		if(i != l-1){
			_str += ",";
		}
	}
	
	//提交保存
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!executeLoapTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid=" + $("#rid").val() + "&rname="+$("#rname").val() + "&pids="+_str+"&btns="+chs.toString(),
		// dataType : "json",
		success : function(msg) {
			var _m = "保存成功!";
			if(msg != "true"){
				_m = "保存失改!";
			}
			top.$.prompt(_m, {
				buttons : {
					确定 : true
				},
				alertType : 'msg'
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}



/**
 * 显示原始数据 tree
 */
function viewRawDataTree(){
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!viewRawDataTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid="+$("#rid").val(),
		dataType : "json",
		success : function(msg) {
			var _btns = msg["btns"];
			if(_btns){
				for(var i=0;i<_btns.length;i++){
					if(_btns[i]){
						$("#"+_btns[i]).attr("checked","true");
						$("#raw-data-li").show();
					}
				}
			}
			var _zNodes = msg["trees"];
			$.fn.zTree.init($("#treeDemo-raw-data"), setting, _zNodes);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}




/**
 * 保存即席查询树的信息
 */
function executeRawDataTree(){
	// btn 值获取
	var chs = new Array();
	var _i = 0;
	var _s = $("input[name='rawDataBtn']:checked").size();
	$("input[name='rawDataBtn']:checked").each(function(){
		if($(this).val()){
			chs.push($(this).val());
			if(_i != _s-1){
				chs.push(",");
			}
		}
		_i ++;
	});
	

	// 树节点权限信息
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo-raw-data");
	var nodes = treeObj.getCheckedNodes(true);
	var _str = "";
	for(var i=0,l= nodes.length;i<l;i++){
		_str += nodes[i].id;
		if(i != l-1){
			_str += ",";
		}
	}
	
	//提交保存
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!executeRawDataTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid=" + $("#rid").val() + "&rname="+$("#rname").val() + "&pids="+_str+"&btns="+chs.toString(),
		// dataType : "json",
		success : function(msg) {
			var _m = "保存成功!";
			if(msg != "true"){
				_m = "保存失改!";
			}
			top.$.prompt(_m, {
				buttons : {
					确定 : true
				},
				alertType : 'msg'
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}




/**
 * 显示管理数据 tree
 */
function viewDataDownTree(){
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!viewDataDownTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid="+$("#rid").val(),
		dataType : "json",
		success : function(msg) {
			var _btns = msg["btns"];
			if(_btns){
				for(var i=0;i<_btns.length;i++){
					if(_btns[i]){
						$("#"+_btns[i]).attr("checked","true");
					}
				}
			}
			var _zNodes = msg["trees"];
			$.fn.zTree.init($("#treeDemo-datadown"), setting, _zNodes);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}




/**
 * 保存管理数据树的信息
 */
function executeDataDownTree(){
	// btn 值获取
	/*
	var chs = new Array();
	var _i = 0;
	var _s = $("input[name='centerBtn']:checked").size();
	$("input[name='centerBtn']:checked").each(function(){
		if($(this).val()){
			chs.push($(this).val());
			if(_i != _s-1){
				chs.push(",");
			}
		}
		_i ++;
	});
	*/
	//console.info(chs.toString());
	// 树节点权限信息
	var treeObj = $.fn.zTree.getZTreeObj("treeDemo-datadown");
	var nodes = treeObj.getCheckedNodes(true);
	var _str = "";
	for(var i=0,l= nodes.length;i<l;i++){
		_str += nodes[i].id;
		if(i != l-1){
			_str += ",";
		}
	}
	console.info(_str);
		
	//提交保存
	$.ajax({
		type : "POST",
		async : true,
		url   :  base+"/manage/permission/permissionAction!executeDataDownTree."+actionExt,
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : "rid=" + $("#rid").val() + "&rname="+$("#rname").val() + "&pids="+_str,
		// dataType : "json",
		success : function(msg) {
			var _m = "保存成功!";
			if(msg != "true"){
				_m = "保存失改!";
			}
			top.$.prompt(_m, {
				buttons : {
					确定 : true
				},
				alertType : 'msg'
			});
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}


