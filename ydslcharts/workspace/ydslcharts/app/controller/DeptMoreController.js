Ext.define('Sencha.controller.DeptMoreController', {
	extend : 'Sencha.controller.BaseController',
	config : {
		refs : {

		},
		control : {

		}
	},
	init : function() {
		// console.info(" Sencha.controller.DeptMController init ");
	},
	/**
	 * 更多部门——赋值部门名称
	 */
	onSetDeptName : function(deptName) {
		Ext.DomHelper.overwrite("deptMoreDeptName", deptName);
	},
	/**
	 * 初始化左侧部门列表
	 */
	initLeftPanel : function() {
		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();
		this.onLeftPanel(_scope, _number);
	},
	/**
	 * 更多部门——左侧部门列表实现
	 */
	onLeftPanel : function(scope, number) {
		var _year = this.getTimeYear();
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();

		var the_param = '{"op":"Dept.getAllList","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '","number":' + _number + '}}';
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

					//var store = Ext.getStore("DeptInfoStore");
					//store.removeAll();
					//store.setData(dataArr);
					Global.DeptMoreController.onLeftPanelLayout(dataArr);
				}
			}
		});
	},

	/**
	 * 更多部门——左侧部门列表渲染
	 */
	onLeftPanelLayout : function(dataArr) {

		Ext.getCmp("deptMoreLeftPanel").items.each(function(item) {
			if(item)
				Ext.getCmp("deptMoreLeftPanel").remove(item);
		});
		
		var _html;
		var _ftal;
		var _htal;
		var _barNum;
		var _ddClass ;
		var i = 0;
		var _panelClass = "panel_more_dept";
		var _em = "icon_1tOn";
		for(var i = 0; i < dataArr.length; i++) {
			var _data = dataArr[i];
			if(i == 0) {					// 默认请求第一个部门
				_ddClass = "more_button_dd_on";
				Global.DeptMoreController.onClickLeftDeptFlight(_data.deptCode);
				Global.CurrentDept = _data.deptCode;
				_em = "icon_1tOn";
			} else {
				_ddClass = "more_button_dd";
				_em = "icon_1t";
			}
			_ftal = (_data.flightTotal / _data.total) * 100;
			_htal = (_data.hotelTotal / _data.total) * 100;
			_barNum = (i + 1) % 6 == 0 ? 1 : (i + 1) % 6;
			// _html = '<dt onclick="deptMoreToDeptInfo("\'+ _data.deptCode+ \'")"><span class="f_left f_16px">' + _data.deptName + '</span><a href="javascript:void(0);">明细 ></a></dt><dd type="1" id="ddDeptFlight_' + _data.deptCode + '" onclick="Global.DeptMoreController.clickLeftDeptFlight(this)"  deptCode="' + _data.deptCode + '"  class="' + _ddClass + '"><em class="icon_1t"></em><span class="num_w1">机票：¥ ' + _data.flightTotal + '</span><span class="pro_nr"><em class="pro_bar' + _barNum + '" style="width:' + _ftal + '%;"></em></span></dd><dd onclick="Global.DeptMoreController.clickLeftDeptHotel(this);" type="2" id="moreDeptHotal_' + _data.deptCode + '" deptCode="' + _data.deptCode + '" ><em class="icon_2t"></em><span 	class="num_w1">酒店：¥ ' + _data.hotelTotal + '</span><span class="pro_nr"><em class="pro_bar' + _barNum + '" style="width:' + _htal + '%;"></em></span></dd>'

			if(i !== dataArr.length -1){
				_panelClass = "panel_more_dept_lastChild";
			}

			Ext.getCmp("deptMoreLeftPanel").insert(Ext.getCmp("deptMoreLeftPanel").items.length, {
				xtype : 'panel',
				cls : _panelClass,
				items : [{
					xtype : 'button',
					id : 'moreDept_' + _data.deptCode,
					cls : 'more_title', // <em class="square_disc_index' + _barNum + '"></em>
					html : '<span class="f_16px">' + _data.deptName + '</span><a href="javascript:void(0);">明细 >></a>',
					handler : function(button) {
						var buttonvar = button.getItemId();
						var deptCode = buttonvar.substring(buttonvar.lastIndexOf("_") + 1);
						deptMoreToDeptInfo(deptCode);
					}
				}, {
					xtype : 'button',
					id : 'moreButtonFlight_' + _data.deptCode,
					cls : _ddClass,
					html : '<em id="em_flight_'+_data.deptCode+'" class="'+_em+'"></em><span>机票： ' + usMoney(_data.flightTotal) + '</span><span class="pro_nr"><em class="square_disc' + _barNum + '" style="width:' + _ftal / 3 + '%;"></em></span>',
					handler : function(button) {
						button.replaceCls("more_button_dd", "more_button_dd_on");
						var buttonvar = button.getItemId();

						Global.DeptMoreController.clickLeftDeptFlight(buttonvar);
					}
				}, {
					xtype : 'button',
					id : 'moreButtonHotel_' + _data.deptCode,
					cls : 'more_button_dd',
					html : '<em id="em_hotel_'+_data.deptCode+'" class="icon_2t"></em><span>酒店： ' + usMoney(_data.hotelTotal) + '</span><span class="pro_nr"><em class="square_disc' + _barNum + '" style="width:' + _htal / 3 + '%;"></em></span>',
					handler : function(button) {
						button.replaceCls("more_button_dd", "more_button_dd_on");
						var buttonvar = button.getItemId();
						Global.DeptMoreController.clickLeftDeptHotel(buttonvar);
					}
				}]
			});
		}
		// i++;
		// });
	},
	/**
	 * 更多部门——左侧机票点击事件
	 */
	clickLeftDeptFlight : function(id) {
		var _id = id;
		var _deptCode = _id.substring(_id.indexOf("_") + 1);

		Ext.select("div.more_button_dd_on", true).each(function() {
			document.getElementById(this.id).removeAttribute("class");
			document.getElementById(this.id).setAttribute("class", "x-button-normal x-button more_button_dd");
		});
		// document.getElementById(_id).setAttribute("class", "x-button-normal x-button more_button_dd_on");
		
		
		Ext.select("em.icon_1tOn", true).each(function() {
			document.getElementById(this.id).removeAttribute("class");
			document.getElementById(this.id).setAttribute("class", "icon_1t");
		});
		
		Ext.select("em.icon_2tOn", true).each(function() {
			document.getElementById(this.id).removeAttribute("class");
			document.getElementById(this.id).setAttribute("class", "icon_2t");
		});
		
		document.getElementById("em_flight_" + _deptCode).removeAttribute("class");
		document.getElementById("em_flight_" + _deptCode).setAttribute("class","icon_1tOn");
		
		
		this.onClickLeftDeptFlight(_deptCode);
	},
	/**
	 *  更多部门——左侧机票点击事件 实现
	 */
	onClickLeftDeptFlight : function(deptCode) {
		// console.info("onClickLeftDeptFlight");

		Global.CurrentDept = deptCode;

		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();
		var _deptCode = deptCode || this.getCurrentDeptCode();

		this.onRightFlight(_scope, _number, _deptCode);

		this.onRightButtonPanel(_scope, _number, _deptCode);
	},
	/**
	 *  更多部门——左侧酒店点击事件
	 */
	clickLeftDeptHotel : function(id) {
		// console.info("clickLeftDeptHotel");
		var _id = id;
		var _deptCode = _id.substring(_id.indexOf("_") + 1);

		Ext.select("div.more_button_dd_on", true).each(function() {
			document.getElementById(this.id).removeAttribute("class");
			document.getElementById(this.id).setAttribute("class", "x-button-normal x-button more_button_dd");
		});

		document.getElementById(_id).removeAttribute("class");
		
		Ext.select("em.icon_1tOn", true).each(function() {
			document.getElementById(this.id).removeAttribute("class");
			document.getElementById(this.id).setAttribute("class", "icon_1t");
		});
		
		
		Ext.select("em.icon_2tOn", true).each(function() {
			document.getElementById(this.id).removeAttribute("class");
			document.getElementById(this.id).setAttribute("class", "icon_2t");
		});
		document.getElementById("em_hotel_" + _deptCode).removeAttribute("class");
		document.getElementById("em_hotel_" + _deptCode).setAttribute("class","icon_2tOn");
		
		// document.getElementById(_id).setAttribute("class", "x-button-normal x-button more_button_dd_on");

		this.onClickLeftDeptHotel(_deptCode);
	},
	/**
	 * 更多部门——左侧酒店点击事件实现
	 */
	onClickLeftDeptHotel : function(deptCode) {
		// console.info("onClickLeftDeptHotel");
		Global.CurrentDept = deptCode;

		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();
		var _deptCode = deptCode || this.getCurrentDeptCode();
		this.onRightHotel(_scope, _number, _deptCode);
		this.onRightButtonPanel(_scope, _number, _deptCode);
	},

	/**
	 * 更多部门 ——右侧部门列表数据源修改
	 */
	onRightButtonPanel : function(scope, number, deptCode) {
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
				} else if(result.data.length > 0){
					var _data  = result.data;
					Global.DeptMoreController.onSetDeptName(_data[0].deptName);
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
				
			}
		});
		/*
		store.load({
			params : {
				jsonStr : the_param
			},
			callback : function(records, operation, sucess) {
				var _data = records[0].data;
				Global.DeptMoreController.onSetDeptName(_data.deptName);
			}
		});
		*/
	},
	/**
	 * 更多部门——机票 柱状图数据源修改
	 */
	onRightFlight : function(scope, number, deptCode) {
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
	 * 更多部门——酒店 柱状图 数据源修改
	 */
	onRightHotel : function(scope, number, deptCode) {
		// TODO 更多部门——酒店 柱状图 数据源修改
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
							//console.info(parseInt(_d.hotelTotal));
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
	 *更多部门——时间点击事件
	 */
	getDataDeptMore : function(scope, number) {
		// TODO  点击部门事件
		//console.info("function = getDataDeptMore");

		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();

		// 右侧 列表实现方法
		this.onLeftPanel(_scope, _number);
	}
});

