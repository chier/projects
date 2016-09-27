Ext.define('Sencha.controller.DeptInfoController', {
	extend : 'Sencha.controller.BaseController',
	config : {
		refs : {
			//            lineButton: 'button[id=lineButton]',
			//            barButton: 'button[id=barButton]',
			// DeptIndexLeftPicPanel : '#deptIndexLeftPicPanel', //部门详情——左侧初始化
			// DeptIndexFlightButton : '#deptIndexFlightButton', //部门详情——右侧机票点击事件'#deptIndexHotelButton', //部门详情——右侧酒店点击事件

		},
		control : {
			/*
			DeptIndexLeftPicPanel : {
			initialize : 'initDeptIndexLeftPanel'
			},
			*/
			// DeptIndexFlightButton : {
			// tap : 'tapDeptIndexFlightButton'
			// },
			// DeptIndexHotelButton : {
			// tap : 'tapDeptIndexHotelButton'
			// }
		}
	},
	init : function() {

	},
	/**
	 *初始化所有程序
	 */
	initDpetInfo : function(deptCode) {
		initMap();
		
		//console.info("initDpetInfo");
		Global.CurrentDept = deptCode;
		this.initCanvasDeptInfo();
		
		this.clickDeptAll();
	},
	/**
	 * 部门明细——右侧列表实现
	 */
	onRightButtonPanel : function(scope, number, deptCode, type) {
		var _year = this.getTimeYear();
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();
		var _deptId = deptCode || this.getCurrentDeptCode();
		var _type = type;
	//	console.info(" type ========= " + type);
	//	console.info(_deptId);
		var the_param = '{"op":"Dept.areaList","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '", "number":' + _number + ',"deptCode":"' + _deptId + '","type":' + _type + '}}';
		var store = Ext.getStore("DeptAreaStore");
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
					for(var i = 0; i < _data.length; i++) {
						var _d = _data[i];
						dataArr.push({
							"date" : _d.date,
							"productType" : _d.productType,
							"huabei" : _d.huabei,
							"huazhong" : _d.huazhong,
							"huanan" : _d.huanan,
							"xibei" : _d.xibei,
							"xinan" : _d.xinan,
							"dongbei" : _d.dongbei
						});
						_deptName = _d.deptName;
					}

					if(_deptName) {
						// document.getElementById("deptindex_leftDeptName").innerHTML = _deptName;
						document.getElementById("logo_title").innerText = _deptName + " 明细";
						document.getElementById("rightDeptName").innerHTML = _deptName;
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
				var dataArr = [];
				var _deptName;
				for(var i = 0; i < records.length; i++) {
					var _d = records[i].data;
					dataArr.push({
						"date" : _d.date,
						"productType" : _d.productType,
						"huabei" : _d.huabei,
						"huazhong" : _d.huazhong,
						"huanan" : _d.huanan,
						"xibei" : _d.xibei,
						"xinan" : _d.xinan,
						"dongbei" : _d.dongbei
					});
					_deptName = _d.deptName;
				}

				if(_deptName) {
					// document.getElementById("deptindex_leftDeptName").innerHTML = _deptName;
					document.getElementById("logo_title").innerText = _deptName + " 明细";
					document.getElementById("rightDeptName").innerHTML = _deptName;
				}
				store.removeAll();
				if(records.length != 1) {
					store.setData(dataArr);
				}
			} // end function
		});
		*/

	},
	/**
	 *	酒店总额详情图表
	 * @param {Object} scope
	 * 				时间类型
	 * @param {Object} number
	 * 				数值
	 * @param {Object} deptCode
	 * 				部门id
	 * @param {Object} type
	 * 				类型 ： 0 表示总额，1，机票总额；2，酒店总额；默认是 0
	 */
	onDeptArea : function(scope, number, deptCode, type) {
		var _year = this.getTimeYear();
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();
		var _type = type || 0;
		var _deptCode = deptCode || this.getCurrentDeptCode();

		var the_param = '{"op":"Dept.area","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '","number":' + _number + ',"deptCode":\"' + _deptCode + '\"}}';
		

					/* 地图显示功能 */
					var canvas = document.getElementById('demo');
					/*
					var myImage = new Image();
					myImage.onload = function() {
						// Draw image.
					}
					myImage.src = 'images/map.png';
					*/
					if(!canvas.getContext)
						return;
					var ctx = canvas.getContext('2d');
					
					/*
					var _vLeft = (628 - 435) / 2;
					var _vTop = (228 - 229) / 2;

					if(_vLeft < 0) {
						_vLeft = 0
					}
					if(_vTop < 0) {
						_vTop = 0;
					}
					ctx.drawImage(myImage, _vLeft, -3);

					ctx.strokeText('西北地区', 130, 45);
					ctx.strokeText('西南地区', 130, 135);
					ctx.strokeText('华北地区', 280, 45);
					ctx.strokeText('华南地区', 255, 180);
					ctx.strokeText('华中地区', 400, 145);
					ctx.strokeText('东北地区',450, 30);	
					*/
					
					// ctx.fillStyle     = '#ffffff';
					ctx.fillStyle     = '#fff';
					ctx.fillRect(112, 55, 75, 20);
					
					ctx.fillStyle     = '#fff';
					ctx.fillRect(112, 145, 75, 20);
					
					ctx.fillStyle     = '#fff';
					ctx.fillRect(262, 55, 75, 20);
					
					ctx.fillStyle     = '#fff';
					ctx.fillRect(237, 190, 75, 20);
					
					ctx.fillStyle     = '#fff';
					ctx.fillRect(382, 155, 75, 20);
					
					
					ctx.fillStyle     = '#fff';
					ctx.fillRect(440, 40, 75, 20);

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
					ctx.strokeText("-", 120, 65);
					ctx.strokeText("-", 120, 155);
					ctx.strokeText("-", 270, 65);
					ctx.strokeText("-", 245, 200);
					ctx.strokeText("-", 390, 165);
					ctx.strokeText("-", 450, 50);
				} else {
					var _l = result.data.length;
					var _i = 0;
					var _d;

					var _xibei = 0;
					var _xinan = 0;
					var _huabei = 0;
					var _huanan = 0;
					var _huazhong = 0;
					var _dongbei = 0;
					while(_i < _l) {
						_d = result.data[_i];
						//console.info(_d);
						if(_type == 0) {
							_xibei += new Number(_d.xibei);
							_xinan += new Number(_d.xinan);
							_huabei += new Number(_d.huabei);
							_huanan += new Number(_d.huanan);
							_huazhong += new Number(_d.huazhong);
							_dongbei += new Number(_d.dongbei);
						} else{// if((_i + 1) == _type) {
							
							if(_type === 1 && _d.productType.indexOf("机票金额") !== -1){		// 机票总额 
								_xibei = _d.xibei;
								_xinan = _d.xinan;
								_huabei = _d.huabei
								_huanan = _d.huanan;
								_huazhong = _d.huazhong;
								_dongbei = _d.dongbei;
							}
							if(_type === 2 && _d.productType.indexOf("酒店金额") !== -1){		// 酒店总额
								_xibei = _d.xibei;
								_xinan = _d.xinan;
								_huabei = _d.huabei
								_huanan = _d.huanan;
								_huazhong = _d.huazhong;
								_dongbei = _d.dongbei;
							}
						}
						_i++;
					}
					ctx.strokeText(usMoney(_xibei), 120, 65);
					ctx.strokeText(usMoney(_xinan), 120, 155);
					ctx.strokeText(usMoney(_huabei), 270, 65);
					ctx.strokeText(usMoney(_huanan), 245, 200);
					ctx.strokeText(usMoney(_huazhong), 390, 165);
					ctx.strokeText(usMoney(_dongbei), 450, 50);
				}
			}
		});

	},

	initCanvasDeptInfo : function() {
		var tmp = Math.round(Math.random() * 90);
		initInstrument(tmp, 0, 0);
	},
	/**
	 * 部门详情——时间点击方法
	 */
	getDataDeptInfo : function(scope, number) {

		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();
		var _deptCode = this.getCurrentDeptCode();
		var _type = 0;

		// 右侧部门列表
		this.onRightButtonPanel(_scope, _number, _deptCode, _type);

		//地图数据
		this.onDeptArea(_scope, _number, _deptCode, _type);

		// 仪表盘数据   部门机票与酒店总额信息
		this.onTotalLinearGraph(_scope, _number, _deptCode, _type);

		this.onLefeButtonPanel(_scope, _number, _deptCode);

		// 机票与酒店样式清空
		document.getElementById("deptinfo_c1_1").removeAttribute("class");
		document.getElementById("deptinfo_c1_1").setAttribute("class", "x-button-normal x-button clock_s2_a");
		// c_on
		document.getElementById("deptinfo_c1_em").removeAttribute("class");
		document.getElementById("deptinfo_c1_em").setAttribute("class", "icon_1t");

		document.getElementById("deptinfo_c2_1").removeAttribute("class");
		document.getElementById("deptinfo_c2_1").setAttribute("class", "x-button-normal x-button clock_s2_a");
		document.getElementById("deptinfo_c2_em").removeAttribute("class");
		document.getElementById("deptinfo_c2_em").setAttribute("class", "icon_2t");

		/*
		 $("#c1").removeClass().addClass("clock_s2");
		 $("#c1").children().first().find("em").attr("class", "icon_1t");
		 $("#c2").removeClass().addClass("clock_s2");
		 $("#c2").children().first().find("em").attr("class", "icon_2t");
		 */
	},
	/**
	 *  部门总额点击事件
	 */
	clickDeptAll : function() {
		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();

		this.getDataDeptInfo(_scope, _number);

		document.getElementById("rightType").innerHTML = "机票 + 酒店";
		//	Ext.DomHelper.overwrite(Ext.get("rightType"), "机票 + 酒店", true);
	},
	/**
	 *	部门类型，点击事件
	 * @param {Object} obj
	 */
	clickDeptType : function(id) {
		var _scope = this.getTimeScope();
		var _number = this.getTimeNumber();
		var _deptCode = this.getCurrentDeptCode();

		var _type = 1;
		document.getElementById(id).setAttribute("class", document.getElementById(id).getAttribute("class") + " c_on");

		if(id == "deptinfo_c1_1") {// 机票点击事件
			document.getElementById("deptinfo_c1_em").removeAttribute("class");
			document.getElementById("deptinfo_c1_em").setAttribute("class", "icon_1tOn");

			document.getElementById("deptinfo_c2_1").removeAttribute("class");
			document.getElementById("deptinfo_c2_1").setAttribute("class", "x-button-normal x-button clock_s2_a");

			document.getElementById("deptinfo_c2_em").removeAttribute("class");
			document.getElementById("deptinfo_c2_em").setAttribute("class", "icon_2t");

			Ext.DomHelper.overwrite(Ext.get("rightType"), "机票", true);
			_type = 1;
		} else {

			document.getElementById("deptinfo_c2_em").removeAttribute("class");
			document.getElementById("deptinfo_c2_em").setAttribute("class", "icon_2tOn");

			document.getElementById("deptinfo_c1_1").removeAttribute("class");
			document.getElementById("deptinfo_c1_1").setAttribute("class", "x-button-normal x-button clock_s2_a");

			document.getElementById("deptinfo_c1_em").removeAttribute("class");
			document.getElementById("deptinfo_c1_em").setAttribute("class", "icon_1t");

			document.getElementById("rightType").innerHTML = "酒店";
			// Ext.DomHelper.overwrite(Ext.get("rightType"), "酒店", true);
			_type = 2;
		}

		//地图数据
		this.onDeptArea(_scope, _number, _deptCode, _type);

		// 仪表盘数据
		this.onTotalLinearGraph(_scope, _number, _deptCode, _type);

		// 右侧列表数据
		this.onRightButtonPanel(_scope, _number, _deptCode, _type);
	},
	/**
	 * 部门总额，仪表图
	 * @param {Object} scope	 时间类型
	 * @param {Object} number	时间数值
	 * @param {Object} deptCode	部门id
	 * @param {Object} type	0 表示全部  1表示机票 2 表示酒店; 默认是0
	 */
	onTotalLinearGraph : function(scope, number, deptCode, type) {
		var _year = this.getTimeYear();
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();
		var _type = type || 0;
		var _deptCode = deptCode || this.getCurrentDeptCode();

		var the_param = '{"op":"Dept.totalLinearGraph","source_id":"1","view_id":"' + Global.ViewId + '","data":{' + '"year":2012,"scope":"' + _scope + '","number":' + _number + ',"deptCode":\"' + _deptCode + '\"}}';
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			params : {
				jsonStr : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if(!result || result.code != 0) {
					document.getElementById("detpTotal").innerHTML = "";
					document.getElementById("deptFlightTotal").innerHTML = "";
					document.getElementById("deptHotelTotal").innerHTML = "";
					initInstrument(0,0,0);
					document.getElementById("emFlightTotal").setAttribute("style", "width:1%");
					document.getElementById("emHotelTotal").setAttribute("style", "width:1%");
					
					console.log(result.msg);
				} else {

					if(result.data.hotelTotal === "") {
						result.data.hotelTotal = 0;
					}

					if(result.data.flightTotal === "") {
						result.data.flightTotal = 0;
					}

					document.getElementById("detpTotal").innerHTML = usMoney(result.data.total);
					document.getElementById("deptFlightTotal").innerHTML = usMoney(result.data.flightTotal);
					document.getElementById("deptHotelTotal").innerHTML = usMoney(result.data.hotelTotal);

					document.getElementById("emFlightTotal").setAttribute("style", "width:" + ((result.data.flightTotal / result.data.total) * 100) / 2 + "%");
					document.getElementById("emHotelTotal").setAttribute("style", "width:" + ((result.data.hotelTotal / result.data.total) * 100) / 2 + "%");

					// Ext.DomHelper.applyStyles(Ext.get("emFlightTotal"), "width:" + (result.data.flightTotal / result.data.total) * 100 + "%");
					// Ext.DomHelper.applyStyles(Ext.get("emHotelTotal"), "width:" + (result.data.hotelTotal / result.data.total) * 100 + "%");

					var _temp;
					if(_type == 0) {//全部
						_temp = result.data.total;
					}
					if(_type == 1) {//机票
						_temp = result.data.flightTotal;
					}
					if(_type == 2) {//酒店
						_temp = result.data.hotelTotal;
					}
					
					
					if(_temp !== 0) {
						while(_temp > 100) {
							_temp = _temp / 10;
						}
						_temp = (_temp / 100) * 80;
						// console.info(_temp);
						initInstrument(Math.round(_temp), 0, 0);
					} else {
						initInstrument(Math.round((1/ 100) * 80), 0, 0);
					}

				}
			}
		});

	},
	/**
	 *
	 * @param {Object} scope  	时间类型
	 * @param {Object} number	时间数值
	 * @param {Object} deptCode	部门id
	 */
	onLefeButtonPanel : function(scope, number, deptCode) {
		var _year = this.getTimeYear();
		var _scope = scope || this.getTimeScope();
		var _number = number || this.getTimeNumber();
		var _deptCode = deptCode || this.getCurrentDeptCode();
		var the_param = '{"op":"Dept.detailChart","source_id":"1","view_id":"' + Global.ViewId + '","data":{"year":' + _year + ',"scope":"' + _scope + '", "number":' + _number + ',"deptCode":\"' + _deptCode + '\"}}';
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
								"flightTotal" : parseInt(_d.flightTotal),
								"hotelTotal" : parseInt(_d.hotelTotal)
							});
						} else {
							dataArr.push({
								"date" : _d.date,
								"flightTotal" : 0,
								"hotelTotal" : 0
							});
						}
						_i++;
					}
					var store = Ext.getStore("DeptDetailListStore");
					store.removeAll();
					if(_l !== 1) {
						store.setData(dataArr);
					}

				}
			}
		});
		/*
		 store.load({
		 params : {
		 jsonStr : the_param
		 },
		 callback : function(records, operation, sucess) {

		 } // end function
		 });
		 */
	}
});

