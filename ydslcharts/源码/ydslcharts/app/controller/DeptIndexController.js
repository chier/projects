Ext.define('Sencha.controller.DeptIndexController', {
	extend : 'Sencha.controller.BaseController',
	config : {
		refs : {
		},
		control : {
		}
	},
	init : function() {
		// console.info(" Sencha.controller.DeptIndexController init ");
	},
	/**
	 *初始化所有程序
	 */
	initDpetIndexPanel : function() {
		this.initDeptIndexLeftPanel();
	},
	/**
	 * 部门详情——右侧机票点击事件实现方法
	 */
	tapDeptIndexFlightButton : function() {
		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();
		var _deptCode = this.getCurrentDeptCode();
		this.onDeptIndexFlightButton(_scope, _number, _deptCode);
	},

	/**
	 * 部门详情——右侧机票点击事件实现方法
	 */
	onDeptIndexFlightButton : function(scope, number, deptCode) {
		//Ext.Msg.alert('提示', '机票点击事件');
		var _year = this.getTimeYear();
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();
		var _deptCode = deptCode || this.getCurrentDeptCode();

		var the_param = '{"op":"Dept.detailChart","source_id":"1","view_id":"' + Global.ViewId + '","data":{ ' + '"year":"' + _year + '","scope":"' + _scope + '", "number":"' + _number + '","deptCode":"' + _deptCode + '"}}';
		var dataArr = [];
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			params : {
				jsonStr : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if(!result || result.code != 0) {
					console.log(result.msg);
				} else {
					var _l = result.data.length;
					var _i = 0;
					var _d;
					while(_i < _l) {
						_d = result.data[_i];
						if(_d.mark == 0) {
							dataArr.push({
								"date" : _d.date,
								"orders" : parseInt(_d.flightTotal)
							});
						} else {
							dataArr.push({
								"date" : _d.date,
								"orders" : 0
							});
						}
						_i++;
					}
					var store = Ext.getStore("ProxyStore");
					store.removeAll();
					store.setData(dataArr);

					var lcIndex = Ext.getCmp("lcIndex");
					var indexBarChart = Ext.getCmp("indexBarChart");
					var maxinum = Global.MainController.getMaximumByObject(dataArr);
					lcIndex.getAxes().get(0).setMaximum(maxinum);
					indexBarChart.getAxes().get(0).setMaximum(maxinum);

				}
			}
		});

	},

	/**
	 * 部门详情——右侧酒店点击事件
	 */
	tapDeptIndexHotelButton : function() {
		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();
		var _deptCode = this.getCurrentDeptCode();
		this.onDeptIndexHotelButton(_scope, _number, _deptCode);
	},
	/**
	 * 部门详情——右侧酒店点击事件 实现方法
	 */
	onDeptIndexHotelButton : function(scope, number, deptCode) {
		//Ext.Msg.alert('提示', '酒店点击事件');
		var _year = this.getTimeYear();
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();
		var _deptCode = deptCode || this.getCurrentDeptCode();

		var the_param = '{"op":"Dept.detailChart","source_id":"1","view_id":"' + Global.ViewId + '","data":{ ' + '"year":"' + _year + '","scope":"' + _scope + '", "number":"' + _number + '","deptCode":"' + _deptCode + '"}}';
		var dataArr = [];
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			params : {
				jsonStr : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if(!result || result.code != 0) {
					console.log(result.msg);
					var store = Ext.getStore("ProxyStore");
					store.removeAll();
				} else {
					var _l = result.data.length;
					var _i = 0;
					var _d;
					while(_i < _l) {
						_d = result.data[_i];
						// 	console.info(_d);
						if(_d.mark == 0) {
							dataArr.push({
								"date" : _d.date,
								"orders" : parseInt(_d.hotelTotal)
							});
						} else {
							dataArr.push({
								"date" : _d.date,
								"orders" : 0
							});
						}
						_i++;
					}

					var store = Ext.getStore("ProxyStore");
					store.removeAll();
					store.setData(dataArr);
					//store.sync();

					var lcIndex = Ext.getCmp("lcIndex");
					var indexBarChart = Ext.getCmp("indexBarChart");
					var maxinum = Global.MainController.getMaximumByObject(dataArr);
					lcIndex.getAxes().get(0).setMaximum(maxinum);
					indexBarChart.getAxes().get(0).setMaximum(maxinum);
				}
			}
		});

	},
	/**
	 * 部门详情—— 左侧初始化
	 */
	initDeptIndexLeftPanel : function() {
		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();
		this.onDeptIndexLeftPanel(_scope, _number);
	},

	/**
	 *  部门详情——左侧实现
	 */
	onDeptIndexLeftPanel : function(scope, number) {
		var _year = this.getTimeYear();
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();

		var the_param = '{"op":"Dept.getList","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '","number":' + _number + '}}';
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			params : {
				jsonStr : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if(!result || result.code != 0) {
					console.log(result.msg);
				} else {
					var dataArr = [];
					// var pieDataArr = [];
					var _datas = result.data;
					var _total = 0;
					var _data;
					// var _codes = [];
					for(var i = 0; i < _datas.length; i++) {
						_total += new Number(_datas[i].total);
					}
					for(var i = 0; i < _datas.length; i++) {
						_data = _datas[i];
						dataArr.push({
							"deptCode" : _data.deptCode,
							"deptName" : indexDeptStr(_data.deptName),
							"orders" : Math.floor((_data.total / _total) * 100, 2),
							"total" : _data.total
						});
					}
					var store = Ext.getStore("DeptInfoStore");
					store.removeAll();
					store.setData(dataArr);
					Global.DeptIndexController.onDeptIndexLeftDeptLayout(store);

					// Global.DeptIndexController.onDeptIndexLeftButtonLayout(dataArr);
				}
			}
		});

		var all_the_param = '{"op":"Dept.getAllList","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '","number":' + _number + '}}';
		dataArr = [];
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			params : {
				jsonStr : all_the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if(!result || result.code != 0) {
					console.log(result.msg);
				} else {
					var dataArr = [];
					var _datas = result.data;
					var _total = 0;
					var _data;
					for(var i = 0; i < _datas.length; i++) {
						_total += new Number(_datas[i].total);
					}
					for(var i = 0; i < _datas.length; i++) {
						_data = _datas[i];
						dataArr.push({
							"deptCode" : _data.deptCode,
							"deptName" : _data.deptName,
							"orders" : Math.floor((_data.total / _total) * 100, 2),
							"hotelTotal" : _data.hotelTotal,
							"flightTotal" : _data.flightTotal,
							"total" : _data.total
						});
					}
				//	console.info(dataArr);
					Global.DeptIndexController.onDeptIndexLeftButtonLayout(dataArr);
				}
			}
		});
	},
	/**
	 * 部门详情——左侧下面部门喧染
	 */
	onDeptIndexLeftButtonLayout : function(dataArr) {

		Ext.getCmp("deptIndexLeftButtonPanel").items.each(function(item) {
			Ext.getCmp("deptIndexLeftButtonPanel").remove(item);
		});

		for(var i = 0; i < dataArr.length; i++) {
			_data = dataArr[i];

			_barNum = (i + 1) % 7 == 0 ? 1 : (i + 1) % 7;
			/*
			 * <em id="em_" ' + _data.deptCode + ' class="detail_disc'
			 + _barNum + '" style="width:'
			 + _data.orders + ' %"></em>
			 
			 style="position:absolute;right:20px;"
			 
			  
			 */
			_html = '<span  class="num_w1" style="position:absolute;left:20px;" >' + indexDeptStr(_data.deptName) + '</span><div style="position:absolute;left:118px;">' 
					+ usMoney(_data.total) + '</div><span class="pro_nr"></span><a href="javascript:void(0);">明细>></a>';

			Ext.getCmp("deptIndexLeftButtonPanel").insert(Ext.getCmp("deptIndexLeftButtonPanel").items.length, {
				xtype : 'button',
				height : 50,
				id : "ddDeptHotal_" + _data.deptCode,
				html : _html,
				style:'background-image:none;border-radius:0;background:#DDE0E2;',
				handler : function(button) {
					var buttonvar = button.getItemId();
					var deptCode = buttonvar.substring(buttonvar.lastIndexOf("_") + 1);
					deptIndexToDeptInfo(deptCode);
				}
			});
		}
	},
	/**
	 * 部门详情——左侧部门喧染
	 */
	onDeptIndexLeftDeptLayout : function(store) {
	//	console.info(Ext.getCmp("deptIndexLeftTopPanel"));
		Ext.getCmp("deptIndexLeftTopPanel").items.each(function(item) {
			Ext.getCmp("deptIndexLeftTopPanel").remove(item);
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

			if(i == 0) {
				Global.DeptIndexController.clickDpetIndexDept('deptCode_deptAll');
			}
			_barNum = (i + 1) % 7 == 0 ? 1 : (i + 1) % 7;

			//_btnHtml = '<button style="width:160px;" onclick="clickDeptIndexDpet(' + _data.deptCode + ');" class="' + _btnClass + '" id="deptIndex_btn_' + _data.deptCode + '" ><em class="square_disc' + _barNum + '"></em><div class="f_left">' + _data.deptName + '：' + _data.orders + '%</div></button>';

			Ext.getCmp("deptIndexLeftTopPanel").insert(Ext.getCmp("deptIndexLeftTopPanel").items.length, {
				xtype : 'button',
				width : '100%',
				cls : _btnClass,
				pressed : isPressed,
				id : 'deptIndex_btn-' + _data.deptCode, // <em class="square_disc_index' + _barNum + '"></em>
				html : '<em class="square_dept' + _barNum + '"></em><div style="position:absolute;left:44px;top:10px;">' + indexDeptStr(_data.deptName) + '：' + _data.orders + '%</div>',
				handler : function(button) {
					button.replaceCls("btn_con3", "btn_con3_on");
					var buttonvar = button.getItemId();
					var deptCode = buttonvar.substring(buttonvar.lastIndexOf("-") + 1);
					clickDeptIndexDpet(deptCode);
				}
			});

			i++;
		});
	},
	/**
	 *部门详情——右侧列表初始化
	 */
	initDeptIndexRightButtonPanel : function() {
		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();
		this.onDeptIndexRightButtonPanel(_scope, _number, "deptCode_deptAll");
	},
	/**
	 * 部门详情——右侧列表实现
	 */
	onDeptIndexRightButtonPanel : function(scope, number, deptCode) {
		var _year = this.getTimeYear();
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();
		var _deptId = deptCode || this.getCurrentDeptCode();
		var the_param = '{"op":"Dept.detailList","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '", "number":' + _number + ',"deptCode":"' + _deptId + '"}}';
		var store = Ext.getStore("DeptDetailListStore");
	
		store.removeAll();
		
		
	
		var dataArr = [];
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			params : {
				jsonStr : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if(!result || result.code != 0) {
					console.info(result.msg);
				} else {
					var _data  = result.data;
					for(var i = 0; i < _data.length; i++) {
						var _d = _data[i];
						dataArr.push({
							"date" : _d.date,
							"deptCode" : _d.deptCode,
							"deptName" : _d.deptName,
							"flightTotal" : _d.flightTotal,
							"hotelTotal"  : _d.hotelTotal,
							"total" : _d.total
						});
					}
					store.setData(dataArr);

				}
									
				if(deptCode == "deptCode_deptAll") {
					Global.DeptIndexController.onSetDeptName("全部");
				} else if(result.data.length > 0){
					var _data = result.data[0];
					Global.DeptIndexController.onSetDeptName(_data.deptName);
				}
				
			}
		});
		/*
		store.load({
			params : {
				format : 'json',
				jsonStr : the_param
			},
			callback : function(records, operation, sucess) {
				if(deptCode == "deptCode_deptAll") {
					Global.DeptIndexController.onSetDeptName("全部");
				} else {
					var _data = records[0].data;
					Global.DeptIndexController.onSetDeptName(_data.deptName);
				}

			} // end function
		});
		*/
	},
	/**
	 * 部门详情 —— 赋于部门名称
	 */
	onSetDeptName : function(deptName) {
		Ext.DomHelper.overwrite("deptIndexDeptName", deptName);
	},
	/**
	 * 部门详情——时间点击方法
	 */
	getDataDeptIndex : function(scope, number) {
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();

		// 左侧部门列表实现方法
		this.onDeptIndexLeftPanel(_scope, _number);

		// 右侧 列表实现方法
		this.onDeptIndexRightButtonPanel(_scope, _number, "deptCode_deptAll");

		// 右侧 机票柱状形实现方法
		var pressedButton = Ext.getCmp('deptIndexRightTopButton').getPressedButtons()[0];
		//console.info(pressedButton.id);
		if(pressedButton.id == 'deptIndexFlightButton') {//表示 选择的是机票
			this.onDeptIndexFlightButton(_scope, _number);
		}
		if(pressedButton.id == 'deptIndexHotelButton') {//表示 选择的是酒店
			this.onDeptIndexHotelButton(_scope, _number);
		}
	},
	/**
	 * 部门详情——部门点击事件
	 */
	clickDpetIndexDept : function(deptCode) {
		// console.info("function = clickDpetIndexDept");
		var _deptCode = deptCode;
		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();

		// 右侧 列表实现方法
		this.onDeptIndexRightButtonPanel(_scope, _number, _deptCode);

		// 右侧 机票柱状形实现方法
		var pressedButton = Ext.getCmp('deptIndexRightTopButton').getPressedButtons()[0];
		if(pressedButton.id == 'deptIndexFlightButton') {//表示 选择的是机票
			this.onDeptIndexFlightButton(_scope, _number, _deptCode);
		}
		if(pressedButton.id == 'deptIndexHotelButton') {//表示 选择的是酒店
			this.onDeptIndexHotelButton(_scope, _number, _deptCode);
		}

		// 左侧饼状图发生变化
		var _pie = Ext.getCmp("deptIndexChart").getSeries().getAt(0);
		for(var i = 0; i < _pie.items.length; i++) {
			if(_pie.items[i].storeItem.get("deptCode") == _deptCode) {
				_pie.highlightItem(_pie.items[i]);
			} else {
				_pie.unHighlightItem(_pie.items[i]);
			}
		}

	},
	tapDeptindexButton : function() {
		toMoreDept();
	},
});

