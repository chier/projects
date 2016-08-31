/**
 * 显示ajax返回的信息
 * 
 * @param {}
 *            retObj
 */
function tt_showResult(retObj, isAlertWhenSuccess) {
	if (retObj) {
		if (retObj.result == 0) { // 业务上成功
			if(isAlertWhenSuccess){
				$.messager.alert(retObj.title, retObj.msg);
			}
			return true;
		} else {
			$.messager.alert(retObj.title, retObj.msg, 'error');
			return false;
		}
	}
	return false;
}

function tt_beforeSend(){
	$.messager.progress({
		title : '正在处理... ...',
		msg : '正在处理，请稍后... ...'
	});
}

function tt_complete() {
	try{
		$.messager.progress('close');
	}catch(e){}
	
}

/**
 * 
 * @param {} contextpath
 * @param {} selectElement
 * @param {} sqlId
 * @param {} form
 * @param {} queryDatas
 * @param {} addNull
 * @param {} valueKey
 * @param {} labelKey
 * @param {} titleKey
 * @param {} sync true:同步
 */
function tt_rendOptionBySqlId(contextpath, selectElement, sqlId, form, queryDatas, addNull, valueKey, labelKey, titleKey, sync) {
	var _data = null;
	if(form) {
		_data = $(form).serialize();
	}
	var queryStr = "";
	if (queryDatas){ //[{"name":"username", "value":"zhangsanfen"}]
		for (var i = 0; i < queryDatas.length; i++) {
			queryStr += "&" + queryDatas[i]["name"] + "=" + queryDatas[i]["value"];
		}
	}
	
	var _valueKey = valueKey ? valueKey : "value";
	var _labelKey = labelKey ? labelKey : "label";
	var _titleKey = titleKey ? titleKey : "title";
	
	$.ajax({
		url : contextpath + "/common/getOptionJson.talent?sqlId=" + sqlId + queryStr,
		data : _data,
		type : "POST",
		dataType : "json",
		cache : false,
		async: !sync,
		beforeSend : function() {
			talent.Util.updateOption(selectElement, true, [{"v":"X","l":"正在加载..."}], "v", "l", true, "", "", "");
		},
		complete : function(_data) {
			var retObj = JSON.parse(_data.responseText);
			if(tt_showResult(retObj, false)) {
				talent.Util.updateOption(selectElement, true, retObj.data, _valueKey, _labelKey, true, "", "", [_titleKey], addNull);
			}
		}
	});
}

/**
 * 
 * @param {} contextpath
 * @param {} selectElement
 * @param {} url 形如/common/getOptionJson.talent
 * @param {} form
 * @param {} queryDatas 对象：[{"name":"username", "value":"zhangsanfen"}]
 * @param {} valueKey
 * @param {} labelKey
 * @param {} titleKey
 */
function tt_rendOptionByUrl(contextpath, selectElement, url, form, queryDatas, valueKey, labelKey, titleKey) {
	var _data = null;
	if(form) {
		_data = $(form).serialize();
	}
	var queryStr = "";
	if (queryDatas){ //[{"name":"username", "value":"zhangsanfen"}]
		for (var i = 0; i < queryDatas.length; i++) {
			queryStr += "&" + queryDatas[i]["name"] + "=" + queryDatas[i]["value"];
		}
	}
	
	$.ajax({
		url : contextpath + url + "?xx=" + 1 + queryStr,
		data : _data,
		type : "POST",
		dataType : "json",
		cache : false,
		beforeSend : function() {
			talent.Util.updateOption(selectElement, true, [{"v":"X","l":"正在加载..."}], "v", "l", true, "", "", "");
		},
		complete : function(_data) {
			var retObj = JSON.parse(_data.responseText);
			if(tt_showResult(retObj, false)) {
				talent.Util.updateOption(selectElement, true, JSON.parse(retObj.msg), valueKey, labelKey, true, "", "", [titleKey]);
			}
		}
	});
}

function tt_createLinkBtn(text, container) {
	var a = document.createElement("a");
	if (container){
		container.appendChild(a);
	}
	
//	a.href = "javascript:void();";
	a.style.cursor = "pointer";
	a.innerHTML = text;
	a.style.marginLeft = "4px";
	a.style.marginRight = "4px";
	return a;
}

function tt_createGridViewBtn(cellE) {
	return tt_createLinkBtn("查看", cellE);
}

function tt_createGridDelBtn(cellE) {
	return tt_createLinkBtn("删除", cellE);
}

function tt_createGridEditBtn(cellE) {
	return tt_createLinkBtn("编辑", cellE);
}


var tt_tableWrapObj = document.createElement("div");
tt_tableWrapObj.style.marginBottom = "0px";
function tt_afterRecordDel(config, grid, notRender) {
	grid.config.ext.deletedRows.push(config.rowIndex);

	if (!notRender){
		grid.render();
	}
}



/**
 * 删除选中记录
 * @param {} batchDeleteUrl
 * @param {} fieldForSubmit {"selectedids": idField}
 * @param {} fieldForTip {"selectedids": idField}
 */
var tt_batchDelete = function(config, batchDeleteUrl, fieldForSubmit) {
	this.h = function() {
		
		var checkboxs = document.getElementsByName("tt_gridCheckbox");
		if (checkboxs && checkboxs.length > 0) {
			var selectedCount = 0;
			var dataArr = [];
			for (var i = 0; i < checkboxs.length; i++){
				if (checkboxs[i].checked) {
					selectedCount++;
					for (var j in fieldForSubmit){
						dataArr.push(j + "=" + checkboxs[i].config.rowData[fieldForSubmit[j]]);
					}
				}
			}
			if (dataArr.length < 1){
				alert("您未选中任何记录!");
				return;
			} else {
				if (!confirm("确定要删除所选的"+selectedCount+"条记录?")){
					return;
				}
			}
			var _data = dataArr.join("&");
			$.ajax({
		        url : batchDeleteUrl,  
		        data : _data,  
		        type : "POST",  
		        dataType : "json",  
		        cache : false,  
		          
		        beforeSend : function() {
		            tt_beforeSend();  //显示进度条  
		        },  
		  
		        complete : function(_data) {
		            tt_complete();       //关闭进度条  
		            var retObj = JSON.parse(_data.responseText);  
		            var isSuccess = tt_showResult(retObj, false);
		            if (isSuccess) {
		            	for (var i = (checkboxs.length - 1); i >= 0; i--){
							if (checkboxs[i].checked) {
								tt_afterRecordDel(checkboxs[i].config, talent.ui.GridProxy[talent.ui.GridProxy.index], true);
							}
						}
						talent.ui.GridProxy[talent.ui.GridProxy.index].render();
		            }
		        }
		    });
		} else {
			alert("您未选中任何记录!");
		}
	};
};

var tt_keyLabelRender = talent.qe.BaseRender.extend({
	// this.config:{table:{}, rowElement:{}, cellElement:{}, rowData:{},
	// field:{}, gridConfig:{}, gridData:{}, rowIndex:{}, colIndex:{}, keyLabelMap{}}
	render : function() {
		var cloneConfig = {};
		talent.Util.copy(cloneConfig, this.config);
		
		var h = cloneConfig.tt_keyLabelMap[cloneConfig.rowData[cloneConfig.field.name]];
		if (!h) {
			h = cloneConfig.rowData[cloneConfig.field.name];
		}
		
		this.config.cellElement.innerHTML = h;
	}
});

/**
 * 
 * @param {} url 形如:"/contextpath/common/getCurrUser.talent", 也可以是: http://xxx.com/path1/path2
 * @param {} form
 * @param {} queryDatas [{"name":"username", "value":"zhangsanfen"}]
 */
function getObjByUrl(_url, form, queryDatas) {
	var _data = null;
	if(form) {
		_data = $(form).serialize();
	}
	var queryStr = "";
	if (queryDatas){ //[{"name":"username", "value":"zhangsanfen"}]
		for (var i = 0; i < queryDatas.length; i++) {
			queryStr += "&" + queryDatas[i]["name"] + "=" + queryDatas[i]["value"];
		}
	}
	
	var ret = null;
	
	$.ajax({
		url : _url + "?xxyyzzddkkoo=x" + queryStr,
		data : _data,
		type : "POST",
		async: false,
		dataType : "json",
		cache : false,
		beforeSend : function() {
			//tt_beforeSend();  //显示进度条  
		},
		complete : function(_data) {
			 //tt_complete();       //关闭进度条
			
			var retObj = JSON.parse(_data.responseText);
			if(tt_showResult(retObj, false)) {
				ret = retObj.data;
			}
		}
	});
	
	return ret;
}

/**
 * 根据sqlid获取list<map>对象
 * @param {} contextpath
 * @param {} sqlId
 * @param {} form
 * @param {} queryDatas [{"name":"username", "value":"zhangsanfen"}]
 * @return {}
 */
function getObjBySqlId(contextpath, sqlId, form, queryDatas) {
	var datas = [{"name":"sqlId", "value":sqlId}];
	if (queryDatas) {
		return getObjByUrl(contextpath + "/common/getObjBySqlId.talent", form, datas.concat(queryDatas));
	} else {
		return getObjByUrl(contextpath + "/common/getObjBySqlId.talent", form, datas);
	}
}



/**
 * 根据sqlid获取tt_keyLabelRender所需要的tt_keyLabelMap
 * @param {} contextpath
 * @param {} sqlId
 * @param {} form
 * @param {} queryDatas
 * @param {} keyKey
 * @param {} labelKey
 * @return {}
 */
function getKeyLabelMapBySqlId(contextpath, sqlId, form, queryDatas, keyKey, labelKey) {
	var listmap = getObjBySqlId(contextpath, sqlId, form, queryDatas);
	return getKeyLabelMapByListMap(listmap, keyKey, labelKey);
}

/**
 * 根据listmap对象获取tt_keyLabelRender所需要的tt_keyLabelMap
 * @param {} listmap
 * @param {} keyKey
 * @param {} labelKey
 * @return {}
 */
function getKeyLabelMapByListMap(listmap, keyKey, labelKey) {
	var ret = {};
	for (var i = 0; i < listmap.length; i++) {
		var data = listmap[i];
		ret[data[keyKey]] = data[labelKey];
	}
	return ret;
}

/**
 * 
 * @param {} checkboxName checkbox的name
 * @param {} listmap [{"myname":"kobe","myid":"kobe_id"}]
 * @param {} keyKey listmap中的key的名字，以[{"myname":"kobe"}]为例就是"myname"
 */
function setChecked4Checkbox(checkboxName, listmap, keyKey) {
	var boxs = document.getElementsByName(checkboxName);
	for (var i = 0; i  < boxs.length; i++) {
		boxs[i].checked = false;
		for (var j = 0; j < listmap.length; j++){
			if (boxs[i].value == listmap[j][keyKey]){
				boxs[i].checked = true;
			}
		}
	}
}

/**
 * 
 * @param {} contextpath
 * @param {} sqlId
 * @param {} form
 * @param {} queryDatas 形如: [{"name":"username", "value":"zhangsanfen"}]
 * @param {} keyKey 以select id myid from table1为例，参数就传"myid"
 * @param {} checkboxName checkbox的name
 */
function setChecked4CheckboxBySqlId(contextpath, sqlId, form, queryDatas, keyKey, checkboxName) {
	setChecked4Checkbox(checkboxName, getObjBySqlId(contextpath, sqlId, form, queryDatas), keyKey);
}

/**
 * 获取当前对象
 * @param {} contextpath
 */
function getCurrUser(contextpath) {
	return getObjByUrl(contextpath + "/common/getCurrUser.talent", null, null);
}
