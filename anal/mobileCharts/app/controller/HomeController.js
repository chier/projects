/**
 * 主页显示的业务类
 */
Ext.define('Sencha.controller.HomeController', {

	extend : 'Sencha.controller.BaseController',

	config : {
		refs : {
			TopTimeBarButton : '#topTimeBar_button',
			ButtomTimeButn : '#buttomTimeButn'
//			WeekButton : '#weekButton',
//			MonthBuuton : '#monthBuuton',
//			SeasonBuuton : '#seasonBuuton',
//			ButtomTimeButnFlight : '#buttomTimeButnFlight',
//			WeekButtonFlight : '#weekButtonFlight',
//			MonthBuutonFlight : '#monthBuutonFlight',
//			SeasonBuutonFlight : '#seasonBuutonFlight',
//			HotelDetailIndexBtn : '#jiudianxiangqing',
//			FlightDetailIndexBtn : '#jipiaoxiangqing',
//			HotelStarBtn : '#xingji',
//			HotelCityBtn : '#ruzhudi',
//			FlightCainBtn : '#cangweijizhekou',
//			FlightAirLineBtn : '#hangkonggognsi',
//			FlightOffPointBtn : '#daodadi',
//			Bumenxiangqing : '#bumenxiangqing'

		},
		control : {
			TopTimeBarButton : {
				initialize : 'getYearsInit'
			}
//			WeekButton : {
//				tap : 'tiemPanelInit'
//			},
//			MonthBuuton : {
//				tap : 'clickMonthBuuton'
//			},
//			SeasonBuuton : {
//				tap : 'clickSeasonBuuton'
//			},
//			// ButtomTimeButnFlight : {
//			// initialize : 'tiemPanelInitFlight'
//			// },
//			WeekButtonFlight : {
//				tap : 'tiemPanelInitFlight'
//			},
//			MonthBuutonFlight : {
//				tap : 'clickMonthBuutonFlight'
//			},
//			SeasonBuutonFlight : {
//				tap : 'clickSeasonBuutonFlight'
//			},
//			HotelDetailIndexBtn : {
//				tap : 'getDataByHotelIndex'
//			},
//			FlightDetailIndexBtn : {
//				tap : 'getDataByJiPiaoIndex'
//			},
//			HotelStarBtn : {
//				tap : 'getDataByStar'
//			},
//			HotelCityBtn : {
//				tap : 'getDataByCity'
//			},
//			FlightCainBtn : {
//				tap : 'getDataByCabin'
//			},
//			FlightAirLineBtn : {
//				tap : 'getDataByAirLine'
//			},
//			FlightOffPointBtn : {
//				//tap : 'getDataByOffPoint'
//			},
//			Bumenxiangqing : {
//				tap : 'tapBumenxiangqing'
//			}
		}
	},
	/**
	 * 初始化后的逻辑, 在init 方法后调用
	 */
	launch: function () {
		// 获取年份时间，并且初始化
		// this.getYearsInit();
		// this.getTimeInit();
		//  this.totalMoneyIndex(globalTimeScope, globalTimeNumber, 0, globalIndexLineBtn);
	
		
    },	
    /**
     * 初始化方案，在 launch 之法前调用
     */
	init : function() {
		
	},
	/**
	 * 获取年份时间，并且初始化
	 */
	getYearsInit : function() {
		var d = new Date();
		var the_param = '{"op":"Home.getYears","source_id":"'+ Global.SourceId +'","view_id":"' + Global.ViewId + '","data":{}}';
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			type : "POST",
			params : {
				tt_requestbody : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				Global.currentYears = result.data.currentYears;
				Global.years = result.data.years;
				Global.HomeController.yearsPanelInit();
			}
		});

	},
	/**
	 * 年份时间分段进行处理
	 */
	yearsPanelInit:function(button){
		// 清空所有按钮	
		this.getTopTimeBarButton().removeAll();
		var pressed = false;
		var tempYears = 2000;
	
		// 赋予新的按钮
		for(var s = 0;s < Global.years.length;s++){
			tempYears  = Global.years[s];
			this.getTopTimeBarButton().add({
				scope : this,
				id : 'years_' + tempYears,
				cls : 'buttonItem',
				text : tempYears + '年',
				handler : 'clickButtonByYears'
			});	
		}
		// 选中某个按钮
		
		Ext.getCmp('topTimeBar_button').items.each(function(item) {
			if(item.id == 'years_' + Global.currentYears) {
				Ext.getCmp('topTimeBar_button').setPressedButtons(item);
			}
		});
		
		
		var button = Ext.getCmp('topTimeBar_button').getPressedButtons()[0];
		this.clickButtonByYears(button);
		
	},
	/**
	 * 点击年份按钮事件
	 * @param {} button
	 */
	clickButtonByYears : function(button){
		
		// 获取当前选中的年份
		var yearsButton = Ext.getCmp('topTimeBar_button').getPressedButtons()[0];
		var yearsNum = yearsButton.id.split("_")[1];
		Global.currentYears = yearsNum;
		
		// 获取选中的按钮
		var pressedButton = Ext.getCmp('buttomTimeButn').getPressedButtons()[0];
		
		this.getButtomTimeButn().removeAll();
		
		// 初始化类型
		this.getButtomTimeButn().add({
			scope : this,
			id : 'baseicInfo',
			text : '基本情况',
			 handler : 'doData'
		});
		
		this.getButtomTimeButn().add({
			scope : this,
			id : 'caseInfo',
			text : '上报统计',
			 handler : 'doData'
		});
		this.getButtomTimeButn().add({
			scope : this,
			id : 'surveyData',
			text : '调查数据',
			 handler : 'doData'
		});
		this.getButtomTimeButn().add({
			scope : this,
			id : 'comprehensive',
			text : '综合分析',
			 handler : 'doData'
		});

		this.getButtomTimeButn().add({
			scope : this,
			id : 'pollutant',
			text : '污染物分析',
			handler : 'doData'
		});
		
		// 选中某个按钮
		Ext.getCmp('buttomTimeButn').items.each(function(item,i) {
			if(pressedButton){
				if(item.id == pressedButton.id) {
					Ext.getCmp('buttomTimeButn').setPressedButtons(item);
				}
			}else{
				if(i == 0){
					Ext.getCmp('buttomTimeButn').setPressedButtons(item);
				}
			}
		});
		
		
		pressedButton = Ext.getCmp('buttomTimeButn').getPressedButtons()[0];
		this.doData(pressedButton);
	},
	
	
		/**
	 * 获取详细数据方法
	 * 1. 获取已经选中的年份
	 * 2. 获取已经选中的面板
	 * 3. 判断当请选中的面板是那种类型
	 * 4. 切换到相应的面板
	 * 5. 加载相对应的数据
	 * @param {} button
	 */
	doData : function(button) {
		
		// 获取当前的类型，进入类型首页面板
		
		// 进入 试点基本情况面板
		if(button.id == "baseicInfo"){
			toBasicInfoIndex();

			// 获取选中的按钮
			var _y = true;
			Ext.getCmp("BasicInfoRootPanel").items.each(function(item,i){
				if(i != 0){
					if(Global.currentYears == item["_yearType"]){
//						item.enable();
//						item.setHidden(false);
//						console.info(item);
						if(_y){
							Ext.getCmp("BasicInfoRootPanel").setActiveItem(item);
							_y = false;
						}
					}else{
//						var myPanel = Ext.create('panel2012_2', {
//						});
//
//						Ext.getCmp("BasicInfoRootPanel").items.add([myPanel]);
						
//						item.clearListeners( )
						// item.destroy(); // 消毁之后 没办法回复，不行
//						item.disable();
//						item.setHidden(true);
					}
				}
				
			});
			
		}
		// 进入上报情况统计面板
		if(button.id == "caseInfo"){
			toCaseStatisticsIndex();
			Global.CaseStatisticsController.initData();
			
		}	
			
		// 进入 调查数据 类型面板
		if(button.id == "surveyData"){
			toSurveyIndex();
			Global.SurveyDataController.initList();
		}
		
		// 进入综合分析类型面板
		if(button.id == "comprehensive"){
			toComprehensiveIndex();
			Global.ComprehensiveController.initList();
		}

		// 进入污染物分析
		if(button.id == "pollutant"){
			 //alert("污染物分析");
			toPollutantIndex();
			Global.PollutantController.initList();
		}
	},
	
	
	/**
	 * 时间段分类 初始化
	 *
	 */
	tiemPanelInit : function(button) {
		console.info(button);
		// alert("ddd");
		// var buttonId = button.getItemId();
		// console.log(this.getButtomTimeButn());
		// console.info("tiemPanelInit");

		var date = new Date(Date.parse(Global.DATE.replace(/-/g, "/"))), // 转换成Data();
		startDate = new Date(date.getFullYear(), 0, 1), endDate = new Date(date.getFullYear(), 12, 31), month = date.getMonth() + 1, // 获取当前月份
		year = date.getFullYear(), // 获取当前年份
		curMonth = date.getMonth() === 1 ? 1: date.getMonth(),		// 获取上个月月份
		curWeek  = getWeekNumOfYear(year, curMonth, 1), // 判断指定日期是一年中的第几周
		startWeeks = 1, // 开始第一周
		endWeeks = getWeekNumOfYear(year, month, date.getDate()), // 判断指定日期是一年中的第几周
		startMonth = 1;
		var theWeek = 0;

		var theDate = new Date();
		var d = 1;

		theDate.setYear(year);
		theDate.setMonth(0);
		theDate.setDate(d);

		// Find the first mondy on a year
		while(theDate.getDay() != 0) {
			theDate.setDate(++d);
		}

		var x = 1;
		this.getButtomTimeButn().removeAll();
		while(startWeeks <= endWeeks - 1) {
			this.getButtomTimeButn().add({
				scope : this,
				id : 'week_' + startWeeks,
				text : '第 ' + startWeeks + ' 周',
				handler : 'getData'
			});
			startWeeks += 1;
		}

		// var i = 0;
		Ext.getCmp('buttomTimeButn').items.each(function(item) {
			if(item.id == 'week_' + curWeek) {
				Ext.getCmp('buttomTimeButn').setPressedButtons(item);
			}
		});
		
	
		
		var scro = Ext.getCmp('time1').getScrollable();// .scrollTo(100,0);
		console.info("****************************************");
		for(var ddd in scro){
			console.info(ddd + " =======          " + scro[ddd] + " || " + typeof(scro[ddd]));
		}
		
		scro.setIndicatorValue('x', 300);
		// scro.onScrollEnd();
		// scro.onScrollEnd();
		// scro.onScroll();
        // scro.setIndicatorValue('y', y);
		console.info("****************************************");
		console.info(scro);
		//scro.scrollTo(300,0,true);
		
		var button = Ext.getCmp('buttomTimeButn').getPressedButtons()[0];
		this.getData(button);
	},

	/**
	 * 月点击事件
	 */
	clickMonthBuuton : function(button) {
		// console.log("wwwwwww"+button.getItemId());
		var date = new Date(Date.parse(Global.DATE.replace(/-/g, "/"))), // 转换成Data();
		month = date.getMonth() + 1, // 获取当前月份
		curMonth = date.getMonth() === 1 ? 1: date.getMonth(),		// 获取上个月月份
		startMonth = 1;
		this.getButtomTimeButn().removeAll();
		while(startMonth <= month - 1) {// 开始月份小于或者等于现在月份

			this.getButtomTimeButn().add({
				scope : this,
				id : 'month_' + startMonth,
				text : '第 ' + startMonth + ' 月',
				handler : 'getData'
			});
			startMonth += 1;
		}

		Ext.getCmp('buttomTimeButn').items.each(function(item) {
			if(item.id == 'month_' + curMonth) {
				Ext.getCmp('buttomTimeButn').setPressedButtons(item);
			}
		});

		var button = Ext.getCmp('buttomTimeButn').getPressedButtons()[0];
		this.getData(button);
	},

	/**
	 * 季点击事件
	 */
	clickSeasonBuuton : function() {
		var date = new Date(Date.parse(Global.DATE.replace(/-/g, "/"))), // 转换成Data();
		month = date.getMonth() + 1, // 获取当前月份
		startQuarter = 1, endQuarter = (parseInt(month / 3)) + (month % 3 === 0 ? 0 : 1);
		// 开始季度
		this.getButtomTimeButn().removeAll();
		while(startQuarter <= endQuarter - 1) {
			this.getButtomTimeButn().add({
				scope : this,
				id : 'season_' + startQuarter,
				text : '第 ' + startQuarter + '季',
				handler : 'getData'
			});
			startQuarter += 1;
		}

		Ext.getCmp('buttomTimeButn').items.each(function(item) {
			if(item.id == 'season_1') {
				Ext.getCmp('buttomTimeButn').setPressedButtons(item);
			}
		});

		var button = Ext.getCmp('buttomTimeButn').getPressedButtons()[0];
		this.getData(button);
	},


	/**
	 * 销售总额数据获取
	 */
	allTotal : function(scope, number) {

		var _year = this.getTimeYear();
		var _scope = scope || 'week';
		var _number = number || 1;

		var the_param = '{"op":"Report.total","source_id":"4","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '","number":' + _number + '}}';
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			params : {
				jsonStr : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if(!result || result.code != 0) {
					console.info("allTotal " + result.msg);
					document.getElementById('fotTotalMoney').innerHTML = usMoney(0);
					document.getElementById('fotTotalOrders').innerHTML = 0;
					init(Math.round(0), 0, 0);
					
				} else {

					document.getElementById('fotTotalMoney').innerHTML = usMoney(result.data.total);
					document.getElementById('fotTotalOrders').innerHTML = result.data.orders;
					var _show = result.data.total;
					while(_show > 100) {
						_show = _show / 10;
					}

					_show = (_show / 100) * 80;
					init(Math.round(_show), 0, 0);
					
					
				}
			}
		});
	},

	/**
	 * 销售总额环比增长数据
	 */
	totalLoopGrowth : function(scope, number) {

		var _year = this.getTimeYear();
		var _scope = scope || 'week';
		var _number = number || 1;

		var the_param = '{"op":"Report.totalLoopGrowth","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '","number":' + _number + '}}';
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			params : {
				jsonStr : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if(!result || result.code != 0) {
					console.info(data.msg);
				} else {
					var  _grow = result.data.growth;
					var ps = _grow.split('%');
					
					if(ps[0] > 0){
						document.getElementById('fotTotalLoopGrowth').setAttribute("class","red");
					}else{
						document.getElementById('fotTotalLoopGrowth').setAttribute("class","green");
					}
					
					document.getElementById('fotTotalLoopGrowth').innerHTML = _grow;
				}
			}
		});
	},
	/**
	 * 获取机票/酒店总额及总订单量
	 *
	 * @param {}
	 *            scope
	 * @param {}
	 *            number
	 * @param {}
	 *            type 获取类型（1-机票，2-酒店）
	 */
	totalAndOrdersIndex : function(scope, number, type) {
		var _year = this.getTimeYear();
		var _scope = scope || 'week';
		var _number = number || 1;
		var _type = type || 1;
		var the_param;
		if(_type === 1) {
			the_param = '{"op":"Flight.flightTotal","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '","number":' + _number + '}}';
		} else {
			the_param = '{"op":"Hotel.hotelTotal","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '","number":' + _number + '}}';
		}
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
					if(_type === 1) {
						document.getElementById('flightTotal_total').innerHTML = usMoney(0);
						document.getElementById('flightTotal_orders').innerHTML = 0;
						init1(Math.round(0), 0, 0);
					} else {
						document.getElementById('hotelTotal_total').innerHTML = usMoney(0);
						document.getElementById('hotelTotal_orders').innerHTML = 0;
						init2(Math.round(0), 0, 0);
					}
				} else {
					if(_type === 1) {
						document.getElementById('flightTotal_total').innerHTML = usMoney(result.data.total);
						document.getElementById('flightTotal_orders').innerHTML = result.data.orders;
						var _show = result.data.total;
						while(_show > 100) {
							_show = _show / 10;
						}
						_show = (_show / 100) * 80;
						init1(Math.round(_show), 0, 0);
					} else {
						document.getElementById('hotelTotal_total').innerHTML = usMoney(result.data.total);
						document.getElementById('hotelTotal_orders').innerHTML = result.data.orders;
						var _show = result.data.total;
						while(_show > 100) {
							_show = _show / 10;
						}
						_show = (_show / 100) * 80;
						init2(Math.round(_show), 0, 0);
					}
				}
			}
		});
	},
	/**
	 * 仪表盘点击事件
	 *
	 * @param {}
	 *            scope
	 * @param {}
	 *            number
	 * @param {}
	 *            type 获取类型（1-机票，2-酒店）
	 */
	clickCanvasIndex : function(scope, number, type) {
		var _scope = scope || 'week';
		var _number = number || 1;
		var the_param;
		var _type = type || 1;
		if(_type === 1) {
			the_param = '{"op":"Flight.detailChart","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		} else {
			the_param = '{"op":"Hotel.detailChart","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		}
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
					var _l = result.data.length;
					var _i = 0;
					var _d;
					while(_i < _l) {
						_d = result.data[_i];
						if(_d.mark == 0) {
							if(_type === 1) {
								//console.log("flight="	+ _d.flightOrders);
								dataArr.push({
									"date" : _d.date,
									"orders" : parseInt(_d.flightOrders)
								});
							} else {
								//console.log("hotel=" + _d.hotelOrders);
								dataArr.push({
									"date" : _d.date,
									"orders" : parseInt(_d.hotelOrders)
								});
							}
						} else {
							dataArr.push({
								"date" : '',
								"orders" : ''
							});
						}
						_i++;
					}
					var store = Ext.getStore('ProxyStore');
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
	 * 获取销售总额详情（首页表格)
	 *
	 * @param {}
	 *            scope
	 * @param {}
	 *            number
	 * @param {}
	 *            type 获取类型 （0-总销售额，1-机票，2-酒店）
	 */
	getSaleTotalDetailInIndex : function(scope, number, type) {
		var _scope = scope || 'week';
		var _number = number || 1;
		var _type = type || 0;
		var the_param;
		var indexGridView = Ext.getCmp("indexGridView");
		if(_type === 0) {
			indexGridView.setColumns([{
				header : '日期',
				dataIndex : 'date',
				width : '40%'
			}, {
				header : '总量',
				dataIndex : 'orders',
				width : '20%'
			}, {
				header : '总数',
				dataIndex : 'total',
				width : '40%'
			}]);
			the_param = '{"op":"Report.detail","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		} else if(_type === 1) {
			indexGridView.setColumns([{
				header : '日期',
				dataIndex : 'date',
				width : '40%'
			}, {
				header : '机票出票量',
				dataIndex : 'orders',
				width : '20%'
			}, {
				header : '机票总额',
				dataIndex : 'total',
				width : '40%'
			}]);
			the_param = '{"op":"Flight.totalDetail","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		} else {
			indexGridView.setColumns([{
				header : '日期',
				dataIndex : 'date',
				width : '40%'
			}, {
				header : '酒店间夜量',
				dataIndex : 'orders',
				width : '20%'
			}, {
				header : '酒店总额',
				dataIndex : 'total',
				width : '40%'
			}]);
			the_param = '{"op":"Hotel.totalDetail","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		}
		indexGridView.updateItemTpl();
		
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
					console.info("****");
					console.info(_data.length);
					for(var i = 0; i < _data.length; i++) {
						var _d = _data[i];
						if(_type === 0){
							dataArr.push({
								"date" : _d.date,
								"total" : _d.total,
								"orders" : _d.orders

							});
						}else
						if(_type === 1) {
							dataArr.push({
								"date" : _d.date,
								"total" : _d.flightTotal,
								"orders" : _d.flightOrders

							});
						} else {
							dataArr.push({
								"date" : _d.date,
								"total" : _d.hotelTotal,
								"orders" : _d.hotelOrders

							});
						}
					}

					
					var store = Ext.getStore('SaleDataStoreForIndexTable');
					store.removeAll();
					store.setData(dataArr);
					//Ext.get("tktTable").dom.children[0].children[0].children[0].children[0].children[1].innerHTML
					//Ext.get("ext-element-248").dom.children[1].innerHTML = fieldName;
				}
			}
		});
		
	/*
		var store = Ext.getStore('SaleDataStoreForIndexTable');
		var dataArr = [];
		store.load({
			params : {
				jsonStr : the_param
			},
			callback : function(records, operation, sucess) {
				for(var i = 0; i < records.length; i++) {
					var _d = records[i].data;
					if(_type === 1) {
						dataArr.push({
							"date" : _d.date,
							"total" : _d.flightTotal,
							"orders" : _d.flightOrders

						});
					} else {
						dataArr.push({
							"date" : _d.date,
							"total" : _d.hotelTotal,
							"orders" : _d.hotelOrders

						});
					}
				}

				if(type !== 0) {
					console.info(dataArr);
					store.setData(dataArr);
				}
			}
		});
	*/
	},
	/**
	 * 获取金额（首页折线图)
	 *
	 * @param {}
	 *            scope
	 * @param {}
	 *            number
	 * @param {}
	 *            type 获取类型 （0-总销售额，1-机票，2-酒店）
	 * @param {}
	 *            btnType 获取类型 （1-订单量，2-金额）
	 */
	totalMoneyIndex : function(scope, number, type, btnType) {
		var _scope = scope || 'week';
		var _number = number || 1;
		var _type = type || 0;
		var _btnType = btnType || 2;
		var the_param;
		if(_type === 0) {
			the_param = '{"op":"Report.detailChart","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		} else if(_type === 1) {
			the_param = '{"op":"Flight.detailChart","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		} else {
			the_param = '{"op":"Hotel.detailChart","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		}
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
					var _l = result.data.length;
					var _i = 0;
					var _d;
					while(_i < _l) {
						_d = result.data[_i];
						if(_d.mark === 0) {
							if(_btnType === 1) {
								if(_type === 0) {
									dataArr.push({
										"date" : _d.date,
										"orders" : _d.orders
									});
								} else if(_type === 1) {
									dataArr.push({
										"date" : _d.date,
										"orders" : _d.flightOrders
									});
								} else {
									dataArr.push({
										"date" : _d.date,
										"orders" : _d.hotelOrders
									});
								}
							} else {
								if(_type === 0) {
									dataArr.push({
										"date" : _d.date,
										"orders" : _d.total
									});
								} else if(_type === 1) {
									dataArr.push({
										"date" : _d.date,
										"orders" : _d.flightTotal
									});
								} else {
									dataArr.push({
										"date" : _d.date,
										"orders" : _d.hotelTotal
									});
								}
							}
						} else {
							dataArr.push({
								"date" : '',
								"orders" : ''
							});
						}
						_i++;
					}
					var store = Ext.getStore('ProxyStore');
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
	 * 获取线图机票出票量/机票退废量/机票总额数据
	 *
	 * @param {}
	 *            scope
	 * @param {}
	 *            number
	 * @param {}
	 *            type
	 */
	getTicketDataForLine : function(scope, number, type, element) {
		// console.info("getTicketDataForLine ==  " + element);
		var _year = this.getTimeYear();
		var _scope = scope || 'week';
		var _number = number || 1;
		var _type = type || 'cabin';
		var _element = element;
		var the_param;
		if(_type === 'cabin') {
			the_param = '{"op":"Flight.flightCabin","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';

		} else if(_type === 'airLine') {
			the_param = '{"op":"Flight.flightAirline","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';

		} else {
			the_param = '{"op":"Flight.flightOffPoint","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';

		}
		var dataArr_flightOrds = [];
		var dataArr_cancelOrds = [];
		var dataArr_flightTotal = [];

		var dataArr_flightOrds2 = [];
		var dataArr_cancelOrds2 = [];
		var dataArr_flightTotal2 = [];

		var maxinum_flightOrds = 0;
		var maxinum_cancelOrds = 0;
		var maxinum_flightTotal = 0;

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
					var store = Ext.getStore('FlightOrdsProxyStore');
					store.removeAll(true);
					
					var store1 = Ext.getStore('CancelOrdsProxyStore');
					store1.removeAll(true);
					
					var store2 = Ext.getStore('FlightTotalProxyStore');
					store2.removeAll(true);
				} else {
					var _l = result.data.length;
					if(_type === 'cabin') {
					
							dataArr_flightOrds.push({
								"date" : '',
								"头等商务舱" : '',
								"经济舱全价" : '',
								"经济舱折扣" : ''
							});
							dataArr_cancelOrds.push({
								"date" : '',
								"头等商务舱" : '',
								"经济舱全价" : '',
								"经济舱折扣" : ''
							});
							dataArr_flightTotal.push({
								"date" : '',
								"头等商务舱" : '',
								"经济舱全价" : '',
								"经济舱折扣" : ''
							});
					

						for(var _j = 0; _j < _l; _j = _j + 3) {
							var cw = result.data[_j];
							var ac = result.data[_j + 1];
							var ad = result.data[_j + 2];
//							console.info(ad);
								dataArr_flightOrds.push({
									"date" : cw.date,
									"头等商务舱" : parseFloat(cw.flightOrders),
									"经济舱全价" : parseFloat(ac.flightOrders),
									"经济舱折扣" : parseFloat(ad.flightOrders)
								});

								dataArr_cancelOrds.push({
									"date" : cw.date,
									"头等商务舱" : parseFloat(cw.cancelOrders),
									"经济舱全价" : parseFloat(ac.cancelOrders),
									"经济舱折扣" : parseFloat(ad.cancelOrders)
								});

								dataArr_flightTotal.push({
									"date" : cw.date,
									"头等商务舱" : parseFloat(cw.flightTotal),
									"经济舱全价" : parseFloat(ac.flightTotal),
									"经济舱折扣" : parseFloat(ad.flightTotal)
								});
					
						}
							dataArr_flightOrds.push({
								"date" : '',
								"头等商务舱" : '',
								"经济舱全价" : '',
								"经济舱折扣" : ''
							});
							dataArr_cancelOrds.push({
								"date" : '',
								"头等商务舱" : '',
								"经济舱全价" : '',
								"经济舱折扣" : ''
							});
							dataArr_flightTotal.push({
								"date" : '',
								"头等商务舱" : '',
								"经济舱全价" : '',
								"经济舱折扣" : ''
							});
					} else if(_type === 'airLine') {
						
						var _f = new Object();
						_f.date = '';
						_f[_element[0]] = '';
						_f[_element[1]] = '';
						_f[_element[2]] = '';
						_f[_element[3]] = '';
						_f[_element[4]] = '';
						_f[_element[5]] = '';
						dataArr_flightOrds2.push(_f);
						console.info(dataArr_flightOrds2);
						
						var _c = new Object();
						_c.date = '';
						_c[_element[0]] = '';
						_c[_element[1]] = '';
						_c[_element[2]] = '';
						_c[_element[3]] = '';
						_c[_element[4]] = '';
						_c[_element[5]] = '';
						
						dataArr_cancelOrds2.push(_c);
	//					console.info(dataArr_cancelOrds2);
						
						var _r = new Object();
						_r.date = '';
						_r[_element[0]] = '';
						_r[_element[1]] = '';
						_r[_element[2]] = '';
						_r[_element[3]] = '';
						_r[_element[4]] = '';
						_r[_element[5]] = '';
						dataArr_flightTotal2.push(_r);
//						console.info(dataArr_flightTotal2);

						for(var _j = 0; _j < _l; _j = _j + 6) {
							var cw = result.data[_j];
							var ac = result.data[_j + 1];
							var ad = result.data[_j + 2];
							var ae = result.data[_j + 3];
							var af = result.data[_j + 4];
							var ag = result.data[_j + 5];
							_f = new Object();
							_f.date = cw.date;
							_f[_element[0]] = parseFloat(cw.flightOrders);
							_f[_element[1]] = parseFloat(ac.flightOrders);
							_f[_element[2]] = parseFloat(ad.flightOrders);
							_f[_element[3]] = parseFloat(ae.flightOrders);
							_f[_element[4]] = parseFloat(af.flightOrders);
							_f[_element[5]] = parseFloat(ag.flightOrders);
							
							dataArr_flightOrds2.push(_f);
							
							
							_c = new Object();
							_c.date = cw.date;
							_c[_element[0]] = parseFloat(cw.cancelOrders);
							_c[_element[1]] = parseFloat(ac.cancelOrders);
							_c[_element[2]] = parseFloat(ad.cancelOrders);
							_c[_element[3]] = parseFloat(ae.cancelOrders);
							_c[_element[4]] = parseFloat(af.cancelOrders);
							_c[_element[5]] = parseFloat(ag.cancelOrders);
							dataArr_cancelOrds2.push(
								_c
							);
							
							_r = new Object();
							_r.date = cw.date;
							_r[_element[0]] = parseFloat(cw.flightTotal);
							_r[_element[1]] = parseFloat(ac.flightTotal);
							_r[_element[2]] = parseFloat(ad.flightTotal);
							_r[_element[3]] = parseFloat(ae.flightTotal);
							_r[_element[4]] = parseFloat(af.flightTotal);
							_r[_element[5]] = parseFloat(ag.flightTotal);
							dataArr_flightTotal2.push(_r);
						}
						
						_f = new Object();
						_f.date = '';
						_f[_element[0]] = '';
						_f[_element[1]] = '';
						_f[_element[2]] = '';
						_f[_element[3]] = '';
						_f[_element[4]] = '';
						_f[_element[5]] = '';
						dataArr_flightOrds2.push(_f);
				//		console.info(dataArr_flightOrds2);
						
						_c = new Object();
						_c.date = '';
						_c[_element[0]] = '';
						_c[_element[1]] = '';
						_c[_element[2]] = '';
						_c[_element[3]] = '';
						_c[_element[4]] = '';
						_c[_element[5]] = '';
						
						dataArr_cancelOrds2.push(_c);
					//	console.info(dataArr_cancelOrds2);
						
						 _r = new Object();
						_r.date = '';
						_r[_element[0]] = '';
						_r[_element[1]] = '';
						_r[_element[2]] = '';
						_r[_element[3]] = '';
						_r[_element[4]] = '';
						_r[_element[5]] = '';
						dataArr_flightTotal2.push(_r);
					//	console.info(dataArr_flightTotal2);
						
						
						dataArr_flightOrds = dataArr_flightOrds2;
						dataArr_cancelOrds = dataArr_cancelOrds2;
						dataArr_flightTotal = dataArr_flightTotal2;
					} else {
						
						var _f = new Object();
						_f.date = '';
						_f[_element[0]] = '';
						_f[_element[1]] = '';
						_f[_element[2]] = '';
						_f[_element[3]] = '';
						_f[_element[4]] = '';
						_f[_element[5]] = '';
						
						
						dataArr_flightOrds2.push(_f);
						
						var _c = new Object();
						_c.date = '';
						_c[_element[0]] = '';
						_c[_element[1]] = '';
						_c[_element[2]] = '';
						_c[_element[3]] = '';
						_c[_element[4]] = '';
						_c[_element[5]] = '';
						
						dataArr_cancelOrds2.push(_c);
						
						var _r = new Object();
						_r.date = '';
						_r[_element[0]] = '';
						_r[_element[1]] = '';
						_r[_element[2]] = '';
						_r[_element[3]] = '';
						_r[_element[4]] = '';
						_r[_element[5]] = '';
						
						dataArr_flightTotal2.push(_r);
						for(var _j = 0; _j < _l; _j = _j + 6) {
							var cw = result.data[_j];
							var ac = result.data[_j + 1];
							var ad = result.data[_j + 2];
							var ae = result.data[_j + 3];
							var af = result.data[_j + 4];
							var ag = result.data[_j + 5];
							
							_f = new Object();
							_f.date = cw.date;
							_f[_element[0]] = parseFloat(cw.flightOrders);
							_f[_element[1]] = parseFloat(ac.flightOrders);
							_f[_element[2]] = parseFloat(ad.flightOrders);
							_f[_element[3]] = parseFloat(ae.flightOrders);
							_f[_element[4]] = parseFloat(af.flightOrders);
							_f[_element[5]] = parseFloat(ag.flightOrders);
							
							
							dataArr_flightOrds2.push(_f);
							
							_c = new Object();
							_c.date = cw.date;
							_c[_element[0]] = parseFloat(cw.cancelOrders);
							_c[_element[1]] = parseFloat(ac.cancelOrders);
							_c[_element[2]] = parseFloat(ad.cancelOrders);
							_c[_element[3]] = parseFloat(ae.cancelOrders);
							_c[_element[4]] = parseFloat(af.cancelOrders);
							_c[_element[5]] = parseFloat(ag.cancelOrders);
							dataArr_cancelOrds2.push(_c);
							
							_r = new Object();
							_r.date = cw.date;
							_r[_element[0]] = parseFloat(cw.flightTotal);
							_r[_element[1]] = parseFloat(ac.flightTotal);
							_r[_element[2]] = parseFloat(ad.flightTotal);
							_r[_element[3]] = parseFloat(ae.flightTotal);
							_r[_element[4]] = parseFloat(af.flightTotal);
							_r[_element[5]] = parseFloat(ag.flightTotal);
						
							dataArr_flightTotal2.push(_r);
						}
						
						_f = new Object();
						_f.date = '';
						_f[_element[0]] = '';
						_f[_element[1]] = '';
						_f[_element[2]] = '';
						_f[_element[3]] = '';
						_f[_element[4]] = '';
						_f[_element[5]] = '';
						
						dataArr_flightOrds2.push(_f);
						
						_c = new Object();
						_c.date = '';
						_c[_element[0]] = '';
						_c[_element[1]] = '';
						_c[_element[2]] = '';
						_c[_element[3]] = '';
						_c[_element[4]] = '';
						_c[_element[5]] = '';
						dataArr_cancelOrds2.push(_c);
						
						 _r = new Object();
						_r.date = '';
						_r[_element[0]] = '';
						_r[_element[1]] = '';
						_r[_element[2]] = '';
						_r[_element[3]] = '';
						_r[_element[4]] = '';
						_r[_element[5]] = '';
						dataArr_flightTotal2.push(_r);
					
						dataArr_flightOrds = dataArr_flightOrds2;
						dataArr_cancelOrds = dataArr_cancelOrds2;
						dataArr_flightTotal = dataArr_flightTotal2;
					}
					
			
					
					var flightOrdsChart = Ext.getCmp("flightOrdsChart");
					var cancelLineChart = Ext.getCmp("cancelLineChart");
					var ticketTotalChart = Ext.getCmp("ticketTotalChart");
					
					maxinum_flightOrds = Global.MainController.getMaximumByObject(dataArr_flightOrds);
					flightOrdsChart.getAxes().get(0).setMaximum(maxinum_flightOrds);

					maxinum_cancelOrds = Global.MainController.getMaximumByObject(dataArr_cancelOrds);
					cancelLineChart.getAxes().get(0).setMaximum(maxinum_cancelOrds);

					maxinum_flightTotal = Global.MainController.getMaximumByObject(dataArr_flightTotal);
					ticketTotalChart.getAxes().get(0).setMaximum(maxinum_flightTotal);
				

					var store = Ext.getStore('FlightOrdsProxyStore');
					store.removeAll();
					store.setData(dataArr_flightOrds);

					var store1 = Ext.getStore('CancelOrdsProxyStore');
					store1.removeAll();
					store1.setData(dataArr_cancelOrds);

					var store2 = Ext.getStore('FlightTotalProxyStore');
					store2.removeAll();
					store2.setData(dataArr_flightTotal);
				}
			}
		});
	},
	/**
	 * 返回对象中最大值 ，不保括date属性
	 */
	getMaximumByObject : function(objArr) {
		var max = 0;
		for(var i = 0; i < objArr.length; i++) {
			for(var obj in objArr[i]) {
				if(obj !== "date") {
					if(max < parseInt(objArr[i][obj])) {
						max = parseInt(objArr[i][obj]);
					}
				}
			}
		}
		if(max < 10) {
			max = max + Global.maxiNum;
		} else {
			max = max + max / 10;
		}
		return max;
	},
	getTicketDetailDataForTabel : function(scope, number, type, element) {
		var _year = this.getTimeYear();
		var _scope = scope || 'week';
		var _number = number || 1;
		var _type = type || 'cabin';
		var _element = element;
		var the_param;
		if(_type === 'cabin') {
			the_param = '{"op":"Flight.flightCabin","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		} else if(_type === 'airLine') {
			the_param = '{"op":"Flight.flightAirline","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		} else {
			the_param = '{"op":"Flight.flightOffPoint","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		}
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
					var store = Ext.getStore('FlightDataStoreForTable');
					store.removeAll();
				} else {
					var infoArr = new Array();
					var _l = result.data.length;
					var _i = 0;
					var _d;
					var fieldName;
					while(_i < _l) {
						_d = result.data[_i];
						if(_type === 'cabin') {
							if(_element) {
								if(_element == _d.cabin) {
									infoArr.push({
										"date" : _d.date,
										"cabin" : _d.cabin,
										"flightOrders" : parseFloat(_d.flightOrders),
										"cancelOrders" : parseFloat(_d.cancelOrders),
										"flightTotal" : parseFloat(_d.flightTotal)
									});
								} else if(_element == "头等商务舱" && _d.cabin == "头等、商务舱") {
									infoArr.push({
										"date" : _d.date,
										"cabin" : _d.cabin,
										"flightOrders" : parseFloat(_d.flightOrders),
										"cancelOrders" : parseFloat(_d.cancelOrders),
										"flightTotal" : parseFloat(_d.flightTotal)
									});
								}
							} else {
								infoArr.push({
									"date" : _d.date,
									"cabin" : _d.cabin,
									"flightOrders" : parseFloat(_d.flightOrders),
									"cancelOrders" : parseFloat(_d.cancelOrders),
									"flightTotal" : parseFloat(_d.flightTotal)
								});
							}
							fieldName = "舱位及折扣";
						} else if(_type === 'airLine') {
							if(_element) {
								if(_element == _d.airline) {
									infoArr.push({
										"date" : _d.date,
										"cabin" : _d.airline,
										"flightOrders" : parseFloat(_d.flightOrders),
										"cancelOrders" : parseFloat(_d.cancelOrders),
										"flightTotal" : parseFloat(_d.flightTotal)
									});
								}
							} else {
								infoArr.push({
									"date" : _d.date,
									"cabin" : _d.airline,
									"flightOrders" : parseFloat(_d.flightOrders),
									"cancelOrders" : parseFloat(_d.cancelOrders),
									"flightTotal" : parseFloat(_d.flightTotal)
								});
							}
							fieldName = "航空公司";
						} else {
							if(_element) {
								console.info(_element + "===" + _d.offPoint);
								if(_element == _d.offPoint) {
									infoArr.push({
										"date" : _d.date,
										"cabin" : _d.offPoint,
										"flightOrders" : parseFloat(_d.flightOrders),
										"cancelOrders" : parseFloat(_d.cancelOrders),
										"flightTotal" : parseFloat(_d.flightTotal)
									});
								}
							} else {
								infoArr.push({
									"date" : _d.date,
									"cabin" : _d.offPoint,
									"flightOrders" : parseFloat(_d.flightOrders),
									"cancelOrders" : parseFloat(_d.cancelOrders),
									"flightTotal" : parseFloat(_d.flightTotal)
								});
							}

							fieldName = "到达地";
						}
						_i++;
					}

					var store = Ext.getStore('FlightDataStoreForTable');
					store.removeAll();
					store.setData(infoArr);
					//Ext.get("tktTable").dom.children[0].children[0].children[0].children[0].children[1].innerHTML
					//Ext.get("ext-element-248").dom.children[1].innerHTML = fieldName;
				}
			}
		});
	},
	/**
	 * 根据按钮类型更新线图显示效果
	 *
	 * @param {}
	 *            panelObj 线图对象
	 * @param {}
	 *            type 按钮标志（舱位、航空公司、到达地）
	 * @param {}
	 *            fieldArr 图例数组
	 */
	updateLineDisplayStyle : function(panelObj, type, fieldArr) {
		// console.info(fieldArr);
		if(type === 'cabin') {
			panelObj.getAxes().items[0].setFields(fieldArr);
			panelObj.getAxes().get(0).drawLabel();

			for(var i = 0; i < 6; i++) {
				panelObj.getSeries().get(i).yField = fieldArr[i];
				panelObj.getSeries().get(i)._yField = fieldArr[i];
				panelObj.getSeries().get(i).setTitle(fieldArr[i]);
				if(i < 3 && fieldArr[i]) {
					panelObj.getSeries().get(i).showAll();
				} else {
					panelObj.getSeries().get(i).hideAll();
				}
			}
		} else if(type === 'airline' || type === 'offpoint') {
			panelObj.getAxes().items[0].setFields(fieldArr);
			panelObj.getAxes().get(0).drawLabel();
			// panelObj.getSeries().get(3).showInLegend = true;
			// panelObj.getSeries().get(4).showInLegend = true;
			// panelObj.getSeries().get(5).showInLegend = true;
			for(var i = 0; i < 6; i++) {
				panelObj.getSeries().get(i).yField = fieldArr[i];
				panelObj.getSeries().get(i)._yField = fieldArr[i];
				panelObj.getSeries().get(i).setTitle(fieldArr[i]);
				if(fieldArr[i]) {
					panelObj.getSeries().get(i).showAll();
				} else {
					panelObj.getSeries().get(i).hideAll();
				}

			}
		} else if(type === 'star') {
			panelObj.getAxes().items[0].setFields(fieldArr);
			panelObj.getAxes().get(0).drawLabel();

			// panelObj.getSeries().get(5).showInLegend = false;
			for(var i = 0; i < 6; i++) {
				panelObj.getSeries().get(i).yField = fieldArr[i];
				panelObj.getSeries().get(i)._yField = fieldArr[i];
				panelObj.getSeries().get(i).setTitle(fieldArr[i]);
				if(i < 5) {
					if(fieldArr[i]) {
						panelObj.getSeries().get(i).showAll();
					} else {
						panelObj.getSeries().get(i).hideAll();
					}
				} else {
					panelObj.getSeries().get(i).hideAll();
				}
			}
		} else {
			//console.log(fieldArr);
			panelObj.getAxes().items[0].setFields(fieldArr);
			panelObj.getAxes().get(0).drawLabel();
			panelObj.getSeries().get(5).showInLegend = true;
			for(var i = 0; i < 6; i++) {
				//console.log(panelObj.getSeries().get(i));
				panelObj.getSeries().get(i).yField = fieldArr[i];
				panelObj.getSeries().get(i)._yField = fieldArr[i];
				panelObj.getSeries().get(i).setTitle(fieldArr[i]);
				// panelObj.getSeries().get(i).showAll();
				if(fieldArr[i]) {
					panelObj.getSeries().get(i).showAll();
				} else {
					panelObj.getSeries().get(i).hideAll();
				}
			}
		}

		for(var i = 0; i < fieldArr.length; i++) {
			if(!fieldArr[i]) {
				panelObj.getSeries().get(i).showInLegend = false;
			} else {
				panelObj.getSeries().get(i).showInLegend = true;
			}
		}

		panelObj.repaint();
		panelObj.redraw(true);
	},

	getHotelDataForLine : function(scope, number, type,element) {
		console.info("getHotelDataForLine");
		var _year = this.getTimeYear();
		var _scope = scope || 'week';
		var _number = number || 1;
		var _type = type || 'star';
		var the_param;
		var _element = element;
		if(_type === 'star') {
			the_param = '{"op":"Hotel.hotelStar","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';

		} else {
			the_param = '{"op":"Hotel.hotelCity","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';

		}
		var dataArr_hotelOrds = [];
		var dataArr_hotelTotal = [];
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
					var _l = result.data.length;
					if(_type === 'star') {
						dataArr_hotelOrds.push({
							"date" : '',
							"经济型" : '',
							"3星" : '',
							"4星" : '',
							"5星" : "",
							"其他" : ''
						});
						dataArr_hotelTotal.push({
							"date" : '',
							"经济型" : '',
							"3星" : '',
							"4星" : '',
							"5星" : "",
							"其他" : ''
						});
						for(var _j = 0; _j < _l; _j = _j + 5) {
							var cw = result.data[_j];
							var ac = result.data[_j + 1];
							var ad = result.data[_j + 2];
							var ae = result.data[_j + 3];
							var af = result.data[_j + 4];
							dataArr_hotelOrds.push({
								"date" : cw.date,
								"经济型" : parseFloat(cw.hotelOrders),
								"3星" : ac.hotelOrders,
								"4星" : parseFloat(ad.hotelOrders),
								"5星" : parseFloat(ae.hotelOrders),
								"其他" : parseFloat(af.hotelOrders)
							});
							dataArr_hotelTotal.push({
								"date" : cw.date,
								"经济型" : parseFloat(cw.hotelTotal),
								"3星" : parseFloat(ac.hotelTotal),
								"4星" : parseFloat(ad.hotelTotal),
								"5星" : parseFloat(ae.hotelTotal),
								"其他" : parseFloat(af.hotelTotal)
							});
						}
						dataArr_hotelOrds.push({
							"date" : '',
							"经济型" : '',
							"3星" : '',
							"4星" : '',
							"5星" : "",
							"其他" : ''
						});
						dataArr_hotelTotal.push({
							"date" : '',
							"经济型" : '',
							"3星" : '',
							"4星" : '',
							"5星" : "",
							"其他" : ''
						});
					} else {
						var _h = new Object();
						_h.date = '';
						_h[_element[0]] = '';
						_h[_element[1]] = '';
						_h[_element[2]] = '';
						_h[_element[3]] = '';
						_h[_element[4]] = '';
						_h[_element[5]] = '';
						dataArr_hotelOrds.push(_h);
						
						
						var _h1 = new Object();
						_h1.date = '';
						_h1[_element[0]] = '';
						_h1[_element[1]] = '';
						_h1[_element[2]] = '';
						_h1[_element[3]] = '';
						_h1[_element[4]] = '';
						_h1[_element[5]] = '';
						
						dataArr_hotelTotal.push(_h1);
						/*
						dataArr_hotelOrds.push({
							"date" : '',
							"北京" : '',
							"天津" : '',
							"上海" : '',
							"广州" : "",
							"深圳" : '',
							"其他" : ''
						});
						dataArr_hotelTotal.push({
							"date" : '',
							"北京" : '',
							"天津" : '',
							"上海" : '',
							"广州" : "",
							"深圳" : '',
							"其他" : ''
						});
						*/
						for(var _j = 0; _j < _l; _j = _j + 6) {
							var cw = result.data[_j];
							var ac = result.data[_j + 1];
							var ad = result.data[_j + 2];
							var ae = result.data[_j + 3];
							var af = result.data[_j + 4];
							var ag = result.data[_j + 5];
							
							_h = new Object();
							_h.date = cw.date;
							_h[_element[0]] = parseFloat(cw.hotelOrders);
							_h[_element[1]] = parseFloat(ac.hotelOrders);
							_h[_element[2]] = parseFloat(ad.hotelOrders);
							_h[_element[3]] = parseFloat(ae.hotelOrders);
							_h[_element[4]] = parseFloat(af.hotelOrders);
							_h[_element[5]] = parseFloat(ag.hotelOrders);
							
							dataArr_hotelOrds.push(_h);
							
							
							_h1 = new Object();
							_h1.date = cw.date;
							_h1[_element[0]] = parseFloat(cw.hotelTotal);
							_h1[_element[1]] = parseFloat(ac.hotelTotal);
							_h1[_element[2]] = parseFloat(ad.hotelTotal);
							_h1[_element[3]] = parseFloat(ae.hotelTotal);
							_h1[_element[4]] = parseFloat(af.hotelTotal);
							_h1[_element[5]] = parseFloat(ag.hotelTotal);
							
							dataArr_hotelTotal.push(_h1);
							/*
							
							dataArr_hotelOrds.push({
								"date" : cw.date,
								"北京" : parseFloat(cw.hotelOrders),
								"天津" : parseFloat(ac.hotelOrders),
								"上海" : parseFloat(ad.hotelOrders),
								"广州" : parseFloat(ae.hotelOrders),
								"深圳" : parseFloat(af.hotelOrders),
								"其他" : parseFloat(ag.hotelOrders)
							});
							
							dataArr_hotelTotal.push({
								"date" : cw.date,
								"北京" : parseFloat(cw.hotelTotal),
								"天津" : parseFloat(ac.hotelTotal),
								"上海" : parseFloat(ad.hotelTotal),
								"广州" : parseFloat(ae.hotelTotal),
								"深圳" : parseFloat(af.hotelTotal),
								"其他" : parseFloat(ag.hotelTotal)
							});
							*/
						}
						_h = new Object();
						_h.date = '';
						_h[_element[0]] = '';
						_h[_element[1]] = '';
						_h[_element[2]] = '';
						_h[_element[3]] = '';
						_h[_element[4]] = '';
						_h[_element[5]] = '';
						dataArr_hotelOrds.push(_h);
						
						
						_h1 = new Object();
						_h1.date = '';
						_h1[_element[0]] = '';
						_h1[_element[1]] = '';
						_h1[_element[2]] = '';
						_h1[_element[3]] = '';
						_h1[_element[4]] = '';
						_h1[_element[5]] = '';
						
						dataArr_hotelTotal.push(_h1);
						
						/*
						dataArr_hotelOrds.push({
							"date" : '',
							"北京" : '',
							"天津" : '',
							"上海" : '',
							"广州" : "",
							"深圳" : '',
							"其他" : ''
						});
						dataArr_hotelTotal.push({
							"date" : '',
							"北京" : '',
							"天津" : '',
							"上海" : '',
							"广州" : "",
							"深圳" : '',
							"其他" : ''
						});
						*/
					}

					var hotelOrdsLine = Ext.getCmp('hotelOrdsLine');
					var hotelTotalChart = Ext.getCmp('hotelTotalLineChart');

					maxinum_flightOrds = Global.MainController.getMaximumByObject(dataArr_hotelOrds);
					hotelOrdsLine.getAxes().get(0).setMaximum(maxinum_flightOrds);

					maxinum_cancelOrds = Global.MainController.getMaximumByObject(dataArr_hotelTotal);
					hotelTotalChart.getAxes().get(0).setMaximum(maxinum_cancelOrds);

					var store = Ext.getStore('HotelOrdsProxyStore');
					store.removeAll();
					store.setData(dataArr_hotelOrds);

					var store2 = Ext.getStore('HotelTotalProxyStore');
					store2.removeAll();
					store2.setData(dataArr_hotelTotal);
					// ticketTotalChart.bindStore(store2);
				}
			}
		});
	},
	
	getHotelDetailDataForTabel : function(scope, number, type, element) {
		console.info("getHotelDetailDataForTabel");
		var _year = this.getTimeYear();
		var _scope = scope || 'week';
		var _number = number || 1;
		var _type = type || 'star';
		var the_param;
		var _element = element;
		if(_type === 'star') {
			the_param = '{"op":"Hotel.hotelStar","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		} else {
			the_param = '{"op":"Hotel.hotelCity","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":'+this.getTimeYear()+',"scope":"' + _scope + '", "number":' + _number + '}}';
		}
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
					var infoArr = new Array();
					var _l = result.data.length;
					var _i = 0;
					var _d;
					var fieldName;
					while(_i < _l) {
						_d = result.data[_i];
						if(_type === 'star') {
							fieldName = '星级';
							if(_element) {
								if(_element === _d.star) {
									infoArr.push({
										"date" : _d.date,
										"star" : _d.star,
										"hotelOrders" : parseFloat(_d.hotelOrders),
										"hotelTotal" : parseFloat(_d.hotelTotal)
									});
								}
							} else {
								infoArr.push({
									"date" : _d.date,
									"star" : _d.star,
									"hotelOrders" : parseFloat(_d.hotelOrders),
									"hotelTotal" : parseFloat(_d.hotelTotal)
								});
							}
						} else {
							fieldName = '入住地';
							if(_element) {
								if(_d.city === element) {
									infoArr.push({
										"date" : _d.date,
										"star" : _d.city,
										"hotelOrders" : parseFloat(_d.hotelOrders),
										"hotelTotal" : parseFloat(_d.hotelTotal)
									});
								}
							} else {
								infoArr.push({
									"date" : _d.date,
									"star" : _d.city,
									"hotelOrders" : parseFloat(_d.hotelOrders),
									"hotelTotal" : parseFloat(_d.hotelTotal)
								});
							}
						}
						_i++;
					}

					var store = Ext.getStore('HotelDataStoreForTable');
					store.removeAll();
					store.setData(infoArr);
					//console.log(document.getElementById("htTable"));
					//document.getElementById("htTable").children[0].children[0].children[0].children[0].children[1].innerHTML = fieldName;
				}
			}
		});
	},
	/**
	 * 酒店 详情初始化信息
	 */
	getDataByHotelIndex : function() {
		globalHotelBtnFlag = 2;
		document.getElementById("logo_title").innerText = "酒店详情";
		btnChange('city');
		toHotelDetail();
		
		this.getData();
	},
	
	/**
	* 机票  详情初始化信息
	*/
	getDataByJiPiaoIndex : function() {
		globalFlightBtnFlag = 2;
		document.getElementById("logo_title").innerText = "机票详情";
		toFlightDetail();	// 页面跳转到机票详情页面
		btnChange('airline');
		
		this.getData();
	},
	/**
	 * 酒店星级点击事件
	 */
	getDataByStar : function() {
		Ext.getCmp('htTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '星级';
		this.clickUnHighlightHotelPie();
		globalHotelBtnFlag = 1;
		var hotelOrdsLine = Ext.getCmp('hotelOrdsLine');
		var hotelTotalChart = Ext.getCmp('hotelTotalLineChart');
		var legendArr = ['经济型', '3星', '4星', '5星', '其他', ''];
		Global.MainController.updateLineDisplayStyle(hotelOrdsLine, 'star', legendArr);
		Global.MainController.updateLineDisplayStyle(hotelTotalChart, 'star', legendArr);
		Global.MainController.getHotelDataForLine(globalTimeScope, globalTimeNumber, 'star');
		Global.MainController.getHotelDetailDataForTabel(globalTimeScope, globalTimeNumber, 'star');

	},

	/**
	 * 酒店星级 元素点击事件
	 */
	getDataByStarElement : function(element) {
		this.clickUnHighlightHotelPie("hotelStarPiePanel");
		globalHotelBtnFlag = 1;
		var hotelOrdsLine = Ext.getCmp('hotelOrdsLine');
		var hotelTotalChart = Ext.getCmp('hotelTotalLineChart');
		var legendArr = [element, '', '', '', '', ''];
		Global.MainController.updateLineDisplayStyle(hotelOrdsLine, 'star', legendArr, element);
		Global.MainController.updateLineDisplayStyle(hotelTotalChart, 'star', legendArr, element);
		Global.MainController.getHotelDataForLine(globalTimeScope, globalTimeNumber, 'star', element);
		Global.MainController.getHotelDetailDataForTabel(globalTimeScope, globalTimeNumber, 'star', element);
		Ext.getCmp('htTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '星级';
	},
	/**
	 * 酒店入住地 点击事件
	 */
	getDataByCity : function() {
		Ext.getCmp('htTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '入住地';

		this.clickUnHighlightHotelPie();
		var legendArr = [];
		globalHotelBtnFlag = 2;
		var hotelOrdsLine = Ext.getCmp('hotelOrdsLine');
		var hotelTotalChart = Ext.getCmp('hotelTotalLineChart');

		var store = Ext.getStore("CityPieProxyStore");
		store.each(function(record) {
			var _data = record.data;
			legendArr.push(_data.city);
		});

		// var legendArr = ['北京', '天津', '上海', '广州', '深圳', '其他'];
		Global.MainController.updateLineDisplayStyle(hotelOrdsLine, 'city', legendArr);
		Global.MainController.updateLineDisplayStyle(hotelTotalChart, 'city', legendArr);
		
		Global.MainController.getHotelDataForLine(globalTimeScope, globalTimeNumber, 'city',legendArr);
		Global.MainController.getHotelDetailDataForTabel(globalTimeScope, globalTimeNumber, 'city');

		// 		Ext.getCmp('htTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '入住地';

	},
	/**
	 * 酒店入住地 元素点击事件
	 */
	getDataByCityElement : function(element) {
		this.clickUnHighlightHotelPie("hotelCityPiePanel");
		globalHotelBtnFlag = 2;
		var hotelOrdsLine = Ext.getCmp('hotelOrdsLine');
		var hotelTotalChart = Ext.getCmp('hotelTotalLineChart');
		var legendArr = [element, '', '', '', '', ''];
		Global.MainController.updateLineDisplayStyle(hotelOrdsLine, 'city', legendArr, element);
		Global.MainController.updateLineDisplayStyle(hotelTotalChart, 'city', legendArr, element);
		Global.MainController.getHotelDataForLine(globalTimeScope, globalTimeNumber, 'city');
		Global.MainController.getHotelDetailDataForTabel(globalTimeScope, globalTimeNumber, 'city', element);
		Ext.getCmp('htTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '入住地';

	},
	/**
	 * 舱位及折扣 点击事件
	 *
	 */
	getDataByCabin : function() {
		globalFlightBtnFlag = 1;
		this.clickUnHighlightPie();
		var legendNameArr;
		var FlightOrdsLine = Ext.getCmp('flightOrdsChart');
		var cancelOrdsLine = Ext.getCmp('cancelLineChart');
		var ticketTotalChart = Ext.getCmp('ticketTotalChart');
		legendNameArr = ['头等商务舱', '经济舱全价', '经济舱折扣', '', '', ''];
		Global.MainController.updateLineDisplayStyle(FlightOrdsLine, 'cabin', legendNameArr);
		Global.MainController.updateLineDisplayStyle(cancelOrdsLine, 'cabin', legendNameArr);
		Global.MainController.updateLineDisplayStyle(ticketTotalChart, 'cabin', legendNameArr);
		Global.MainController.getTicketDataForLine(globalTimeScope, globalTimeNumber, 'cabin');
		Global.MainController.getTicketDetailDataForTabel(globalTimeScope, globalTimeNumber, 'cabin');
		
		Ext.getCmp('tktTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '舱位及折扣';

	},
	/**
	 *  只显示某些原素
	 * @param {Object} element
	 */
	getDataByCabinElement : function(element) {
		this.clickUnHighlightPie("flightCabinPiePanel");
		var legendNameArr;
		var FlightOrdsLine = Ext.getCmp('flightOrdsChart');
		var cancelOrdsLine = Ext.getCmp('cancelLineChart');
		var ticketTotalChart = Ext.getCmp('ticketTotalChart');

		legendNameArr = [element, '', '', '', '', ''];
		Global.MainController.updateLineDisplayStyle(FlightOrdsLine, 'cabin', legendNameArr);
		Global.MainController.updateLineDisplayStyle(cancelOrdsLine, 'cabin', legendNameArr);
		Global.MainController.updateLineDisplayStyle(ticketTotalChart, 'cabin', legendNameArr);
		Global.MainController.getTicketDataForLine(globalTimeScope, globalTimeNumber, 'cabin', element);
		Global.MainController.getTicketDetailDataForTabel(globalTimeScope, globalTimeNumber, 'cabin', element);
		globalFlightBtnFlag = 1;
		Ext.getCmp('tktTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '舱位及折扣';

	},
	/**
	 *  航空公司 点击事件
	 */
	getDataByAirLine : function() {
		globalFlightBtnFlag = 2;
		this.clickUnHighlightPie();
		var legendNameArr = [];
		var FlightOrdsLine = Ext.getCmp('flightOrdsChart');
		var cancelOrdsLine = Ext.getCmp('cancelLineChart');
		var ticketTotalChart = Ext.getCmp('ticketTotalChart');
		
		var store = Ext.getStore("AirLinePieProxyStore");
		store.each(function(record) {
			var _data = record.data;
			if(!_data.airline){
				legendNameArr.push("");
			}else{
				if(_data.airline.indexOf("其") == -1){
					legendNameArr.push(_data.airline.substr(0,1) + "航");
				}else{
					legendNameArr.push("其它");
				}
			}
			
		});
		
		// legendNameArr = ['国航', '海航', '深航', '南航', '东航', '其他'];
		Global.MainController.updateLineDisplayStyle(FlightOrdsLine, 'airline', legendNameArr);
		Global.MainController.updateLineDisplayStyle(cancelOrdsLine, 'airline', legendNameArr);
		Global.MainController.updateLineDisplayStyle(ticketTotalChart, 'airline', legendNameArr);
		Global.MainController.getTicketDataForLine(globalTimeScope, globalTimeNumber, 'airLine',legendNameArr);
		Global.MainController.getTicketDetailDataForTabel(globalTimeScope, globalTimeNumber, 'airLine');
		
		Ext.getCmp('tktTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '航空公司';
	},

	/**
	 *  航空公司 点击事件
	 */
	getDataByAirLineElement : function(element) {
		this.clickUnHighlightPie("flightAirLinePiePanel");

		var legendNameArr;
		var FlightOrdsLine = Ext.getCmp('flightOrdsChart');
		var cancelOrdsLine = Ext.getCmp('cancelLineChart');
		var ticketTotalChart = Ext.getCmp('ticketTotalChart');
		legendNameArr = [element, '', '', '', '', ''];
		Global.MainController.updateLineDisplayStyle(FlightOrdsLine, 'airline', legendNameArr);
		Global.MainController.updateLineDisplayStyle(cancelOrdsLine, 'airline', legendNameArr);
		Global.MainController.updateLineDisplayStyle(ticketTotalChart, 'airline', legendNameArr);

		Global.MainController.getTicketDataForLine(globalTimeScope, globalTimeNumber, 'airLine', element);
		Global.MainController.getTicketDetailDataForTabel(globalTimeScope, globalTimeNumber, 'airLine', element);
		globalFlightBtnFlag = 2;
		console.info(Ext.getCmp('tktTable').items.items[0].element.dom.children[0]);
		Ext.getCmp('tktTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '航空公司';
		// Ext.getCmp('tktTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '航空公司';

	},

	/**
	 *到达地 点击事件
	 */
	getDataByOffPoint : function() {
		globalFlightBtnFlag = 3;
		var legendNameArr = [];
		var FlightOrdsLine = Ext.getCmp('flightOrdsChart');
		var cancelOrdsLine = Ext.getCmp('cancelLineChart');
		var ticketTotalChart = Ext.getCmp('ticketTotalChart');

		var store = Ext.getStore("ArrivedPieProxyStore");
		store.each(function(record) {
			var _data = record.data;
			legendNameArr.push(_data.offPoint);
		});

		// legendNameArr = ['北京', '天津', '上海', '广州', '深圳', '其他'];
		Global.MainController.updateLineDisplayStyle(FlightOrdsLine, 'offpoint', legendNameArr);
		Global.MainController.updateLineDisplayStyle(cancelOrdsLine, 'offpoint', legendNameArr);
		Global.MainController.updateLineDisplayStyle(ticketTotalChart, 'offpoint', legendNameArr);
		Global.MainController.getTicketDataForLine(globalTimeScope, globalTimeNumber, 'offPoint',legendNameArr);
		Global.MainController.getTicketDetailDataForTabel(globalTimeScope, globalTimeNumber, 'offPoint');
		
		Ext.getCmp('tktTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '到达地';
	},
	/**
	 * 到达地 源速点击事件
	 */
	getDataByOffPointElement : function(element) {
		this.clickUnHighlightPie("flightArrivedPiePanel");
		var legendNameArr;
		var FlightOrdsLine = Ext.getCmp('flightOrdsChart');
		var cancelOrdsLine = Ext.getCmp('cancelLineChart');
		var ticketTotalChart = Ext.getCmp('ticketTotalChart');
		legendNameArr = [element, '', '', '', '', ''];
		Global.MainController.updateLineDisplayStyle(FlightOrdsLine, 'offpoint', legendNameArr, element);
		Global.MainController.updateLineDisplayStyle(cancelOrdsLine, 'offpoint', legendNameArr, element);
		Global.MainController.updateLineDisplayStyle(ticketTotalChart, 'offpoint', legendNameArr, element);
		Global.MainController.getTicketDataForLine(globalTimeScope, globalTimeNumber, 'offPoint', element);
		Global.MainController.getTicketDetailDataForTabel(globalTimeScope, globalTimeNumber, 'offPoint', element);
		globalFlightBtnFlag = 3;
//		console.info(Ext.getCmp('tktTable').items.items[0].element.dom);
		Ext.getCmp('tktTable').items.items[0].element.dom.children[0].children[0].children[1].innerHTML = '到达地';
	},

	tapBumenxiangqing : function() {
		toDeptIndex();
	},
	/**
	 * 所有饼状图还原成原始状态
	 */
	clickUnHighlightPie : function(id) {
		if(id != "flightCabinPiePanel") {
			if(Ext.getCmp("flightCabinPiePanel")) {
				var _pie = Ext.getCmp("flightCabinPiePanel").getSeries().getAt(0);
				for(var i = 0; i < _pie.items.length; i++) {
					_pie.unHighlightItem(_pie.items[i]);
				}
			}
		}

		if(id != "flightAirLinePiePanel") {
			if(Ext.getCmp("flightAirLinePiePanel")) {
				var _pie = Ext.getCmp("flightAirLinePiePanel").getSeries().getAt(0);
				for(var i = 0; i < _pie.items.length; i++) {
					_pie.unHighlightItem(_pie.items[i]);
				}
			}
		}

		if(id != "flightArrivedPiePanel") {
			if(Ext.getCmp("flightArrivedPiePanel")) {
				var _pie = Ext.getCmp("flightArrivedPiePanel").getSeries().getAt(0);
				for(var i = 0; i < _pie.items.length; i++) {
					_pie.unHighlightItem(_pie.items[i]);
				}
			}
		}

	},
	/**
	 * 所有饼状图还原成原始状态
	 */
	clickUnHighlightHotelPie : function(id) {
		if(id != "hotelStarPiePanel") {
			if(Ext.getCmp("hotelStarPiePanel")) {
				var _pie = Ext.getCmp("hotelStarPiePanel").getSeries().getAt(0);
				for(var i = 0; i < _pie.items.length; i++) {
					_pie.unHighlightItem(_pie.items[i]);
				}
			}

		}

		if(id != "hotelCityPiePanel") {
			if(Ext.getCmp("hotelCityPiePanel")) {
				var _pie = Ext.getCmp("hotelCityPiePanel").getSeries().getAt(0);
				for(var i = 0; i < _pie.items.length; i++) {
					_pie.unHighlightItem(_pie.items[i]);
				}
			}
		}
	}


	
	
	
});
