/**
 * 试点上报详情 业务类
 */
Ext.define('Sencha.controller.CaseInfoController', {
	extend : 'Sencha.controller.BaseController',
	config : {
		refs : {},
		control : {}
	},
	launch: function () {
		console.info(" CaseInfoController  launch");
    },
	init : function() {
		console.info(" CaseInfoController init ");
		
		
	},
	// 跳转到试点详情页面
	tabToCaseInfo:function(){
		console.info("tabToCaseInfo");
		toCaseInfoIndex();
		Global.CaseInfoController.initList();
	},
	/**
	 * 获取左侧关于试点列表及饼图的信息
	 */
	initList:function(){
		var caseType = Global.getCaseType(Global.currentYears);
		document.getElementById('bar_btn_1').innerHTML = caseType["1"];
		document.getElementById('bar_btn_2').innerHTML =  caseType["2"];
		
		
		
		
		console.info("CaseInfoController initList ");
//		var dataArr = []; // 将返回的数据封装成数组后，再渲染成布局
		var the_param = '{"op":"CaseInfo.PieChart","source_id":"'
				+ Global.SourceId + '","view_id":"' + Global.ViewId
				+ '","data":{"years":' + Global.currentYears + '}}';
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			type : "POST",
			params : {
				tt_requestbody : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				console.info(result);
				if (!result || result.code != 0) {
					console.log(result.msg);
				} else {
					Global.CaseInfoController.doDataPicChar(result);
				}
			}
		});
		
		Global.CaseInfoController.onDetailList();
		Global.CaseInfoController.onDetailChart();
	},
	/**
	 * 对饼图进行赋值
	 * @param {} result
	 */
	doDataPicChar:function(result){
		var store = Ext.getStore("CaseInfoPicStore");
		store.removeAll();
		store.setData(result.data);
		
		Global.CaseInfoController.doDataButtonList(store);
	},
	/**
	 * 对按钮赋值
	 * @param {} result
	 */
	doDataButtonList:function(store){
		Ext.getCmp("caseInfoLeftButtonPanel").items.each(function(item) {
			Ext.getCmp("caseInfoLeftButtonPanel").remove(item);
		});

		var _data;
		var _barNum;
		var _ddClass = "";
		var _btnClass = "btn_con3";
		var _html;
		var _btnHtml;
		var _d;
		var i = 0;
		var isPressed = false;

		store.each(function(record) {
			_data = record.data;

			isPressed = false;
			_ddClass = "";
			_btnClass = "btn_con3";

//			if(i == 0) {
////				Global.DeptIndexController.clickDpetIndexDept('deptCode_deptAll');
//			}
			_barNum = (i + 1) % 7 == 0 ? 1 : (i + 1) % 7;

			//_btnHtml = '<button style="width:160px;" onclick="clickDeptIndexDpet(' + _data.deptCode + ');" class="' + _btnClass + '" id="deptIndex_btn_' + _data.deptCode + '" ><em class="square_disc' + _barNum + '"></em><div class="f_left">' + _data.deptName + '：' + _data.orders + '%</div></button>';
			_html = '<span  class="num_w1" style="position:absolute;left:20px;" >' + indexDeptStr(_data.surName) + '</span><span class="pro_nr"></span>';
			Ext.getCmp("caseInfoLeftButtonPanel").insert(Ext.getCmp("caseInfoLeftButtonPanel").items.length, {
				xtype : 'button',
				width : '100%',
				cls : _btnClass,
				pressed : isPressed,
				id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
				html : '<em class="square_dept' + _barNum + '"></em><div style="position:absolute;left:44px;top:10px;">' + indexDeptStr(_data.pname) + '：' + _data.ratios + '%</div>',
				handler : function(button) {
					Global.CaseInfoController.clickByItem(button);
				}
			});

			i++;
		});
	},
	/**
	 * 点击按钮时，显示内容
	 */
	clickByItem:function(item,clickType){
		var pid = item.getId().split("_")[1];
		
		// 清空相关样式
		Ext.getCmp("caseInfoLeftButtonPanel").items.each(function(eachItem,i) {
			eachItem.setCls("btn_con3");
		});
		item.setCls("btn_con3_on");
		
		if(!clickType){ //当不存在值时，即是按钮请求，图饼跟着变化
			// 左侧饼状图发生变化
			var _pie = Ext.getCmp("caseInfoChart").getSeries().getAt(0);
			for(var i = 0; i < _pie.items.length; i++) {
				if(_pie.items[i].storeItem.get("pid") == pid) {
					_pie.highlightItem(_pie.items[i]);
				} else {
					_pie.unHighlightItem(_pie.items[i]);
				}
			}
		}
	},
	// 请求右侧列表数据
	onDetailList:function(){
		var the_param = '{"op":"CaseInfo.detailList","source_id":"'
				+ Global.SourceId + '","view_id":"' + Global.ViewId
				+ '","data":{"years":' + Global.currentYears + '}}';
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			type : "POST",
			params : {
				tt_requestbody : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				console.info(result);
				if (!result || result.code != 0) {
					console.log(result.msg);
				} else {
					var _columns = [{
						header : '日期 ',
						dataIndex : 'createTime',
						width:'20%'
					}, {
						header : '试点',
						dataIndex : 'pname',
						width:'20%'
					},{
						header : Global.getCaseType(Global.currentYears)["1"] +'类 ',
						dataIndex : 'healthTotal',
						width:'20%'
					},{
						header : Global.getCaseType(Global.currentYears)["2"] +'类 ',
						dataIndex : 'environmentTotal',
						width:'20%'
					},{
						header : '总额 ',
						dataIndex : 'total',
						width:'20%'
					}];
					Ext.getCmp("caseInfoGridView")._columns = _columns;
					Ext.getCmp("caseInfoGridView").updateItemTpl();
					
					var store = Ext.getStore("CaseInfoListStore");
					store.removeAll();
					store.setData(result.data);
				}
			}
		});
	},
	// 请求右侧拆线与柱状图表数据
	onDetailChart:function(ctype){
		
		if(!ctype){
			ctype = 1;
		}
		console.info(ctype);
		var the_param = '{"op":"CaseInfo.detailChart","source_id":"'
				+ Global.SourceId + '","view_id":"' + Global.ViewId
				+ '","data":{"years":' + Global.currentYears + ',"ctype":' + ctype + '}}';
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			type : "POST",
			params : {
				tt_requestbody : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if (!result || result.code != 0) {
					console.log(result.msg);
				} else {
					var store = Ext.getStore("CaseInfoChartStore");
					store.removeAll();
					store.setData(result.data);
					store.insert(0,{"pname":"","pCount":""});
					store.insert(result.data.length +1,{"pname":"","pCount":""});
				}
			}
		});
	}
	
	
	
});
