/**
 *父级 公共方法集成类
 */
Ext.define('Sencha.controller.BaseController', {
	extend : 'Ext.app.Controller',
	config : {

	},
	/**
	 * 返回时间年份 后期修改
	 */
	getTimeYear : function() {
		//return 2013;
		return Global.DATE.substring(0,4);
	},
	/**
	 *  返回时间范围参数
	 */
	getTimeScope : function() {
		var array = Ext.getCmp('buttomTimeButn').getPressedButtons();
		var id = array[0].id;
		return id.substring(0, id.indexOf("_"));

	},
	/**
	 * 返回时间数值
	 */
	getTimeNumber : function() {
		var array = Ext.getCmp('buttomTimeButn').getPressedButtons();
		console.info(array);
		var id = array[0].id;
		return id.substring(id.indexOf("_") + 1);
	},
	/**
	 *  返回当前选择的部门
	 */
	getCurrentDeptCode : function() {
		return Global.CurrentDept || 1;
	},
	/**
	 * 判断页面实现 方法
	 */
	onIsPage : function(str) {
		var _rootMain = Ext.getCmp("rootMain");
		var aitem = _rootMain.getActiveItem();
		if(aitem.getId() == str){
			return true;
		}
		console.info(aitem);
		
		return false;
	/*
		var isPage = false;
		var main = Ext.getCmp('main');
		main.items.each(function(item) {
			if(item.id == str) {
				isPage = true;
			}
		});
		return isPage;
	*/
	},
	/**
	 *判断 当前页面是不是首页
	 */
	isHomePage : function() {
		return this.onIsPage("RootPanel");
	},
	/**
	 * 判断当前是不是部门详情页面
	 */
	isDeptIndexPage : function() {
		return this.onIsPage("deptIndexView");
	},
	/**
	 * 判断当前是不是更多部门页面
	 */
	isDeptMorePage : function() {
		return this.onIsPage("deptMoreView");
	},
	/**
	 * 判断当前是不是部门详情页面
	 */
	isDeptInfoPage : function() {
		return this.onIsPage("deptInfoView");
	},
	isFlightInfoPage : function() {
		return this.onIsPage("FlightRootPanel");
	},
	isHotelInfoPage : function() {
		return this.onIsPage("HotelRootPanel");
	},
});

/**
 * 跳转到部门详情页面
 */
function toDeptIndex(str) {
	
	var mainPanel = Ext.getCmp("rootMain");
	var main_2 = Ext.getCmp("deptIndexView");
	
	if(Ext.os.is.Android){
		mainPanel.setActiveItem(main_2);
	}else{
		if(str){
			mainPanel.animateActiveItem(main_2,{
				type : 'slide',
				direction: 'right'
			});
		}else{
			mainPanel.animateActiveItem(main_2,{
				type : 'slide',
				direction: 'left'
			});
		}
	}
	
	Global.DeptIndexController.initDpetIndexPanel();
	document.getElementById("logo_title").innerText = "部门详情";
}

/**
 * 返回首页
 */
function toHome() {
	firstInit = false;
	firstInit1 = false;
	firstInit2 = false;

	var mainPanel = Ext.getCmp("rootMain");
	var main_2 = Ext.getCmp("RootPanel");
		
	
	if(Ext.os.is.Android){
		mainPanel.setActiveItem(main_2);
	}else{	
		mainPanel.animateActiveItem(main_2,{
			type : 'slide',
			direction: 'right'
		});
	}
	

	var tmp = Math.round(Math.random() * 100);
	tmp = (tmp / 100) * 80;
	init(Math.round(tmp), 0, 0);
	init1(Math.round(tmp), 0, 0);
	init2(Math.round(tmp), 0, 0);
	// Global.MainController.getTimeInit();
	Global.MainController.getData();
	document.getElementById("logo_title").innerText = "首  页";
}

/**
 * 返回到更多部门页面
 */
function toMoreDept(str) {

	var mainPanel = Ext.getCmp("rootMain");
	var main_2 = Ext.getCmp("deptMoreView");
	
	
	if(Ext.os.is.Android){
		mainPanel.setActiveItem(main_2);
	}else{
		if(str){
			mainPanel.animateActiveItem(main_2,{
				type : 'slide',
				direction: 'right'
			});
		}else{
			mainPanel.animateActiveItem(main_2,{
				type : 'slide',
				direction: 'left'
			});
		}
	}

	
	Global.DeptMoreController.initLeftPanel();
	document.getElementById("logo_title").innerText = "查看更多";
}

/**
 * 转发到 机票详情页面
 */
function toFlightDetail() {

	var mainPanel = Ext.getCmp("rootMain");
	var main_2 = Ext.getCmp("FlightRootPanel");
			
	if(Ext.os.is.Android){
		mainPanel.setActiveItem(main_2);
	}else{
		mainPanel.animateActiveItem(main_2,{
				type : 'slide',
				direction: 'left'
			});
	}
	
	
	document.getElementById("logo_title").innerText = "机票详情";
	
}

function toHotelDetail() {
	var mainPanel = Ext.getCmp("rootMain");
	var main_2 = Ext.getCmp("HotelRootPanel");
			
	
	if(Ext.os.is.Android){
		mainPanel.setActiveItem(main_2);
	}else{
		mainPanel.animateActiveItem(main_2,{
			type : 'slide',
			direction: 'left'
		});
	}
	// mainPanel.setActiveItem(main_2);
	
	document.getElementById("logo_title").innerText = "酒店详情";
	//Global.MainController.initHotelPanel();
}

/**
 * 返回到部门详情页面
 */
function deptMoreToDeptInfo(deptCode) {
	// console.info("deptMoreToDeptInfo");
	Global.backPage = "moreDept";
	
	var mainPanel = Ext.getCmp("rootMain");
	var main_2 = Ext.getCmp("deptInfoView");
			

	if(Ext.os.is.Android){
		mainPanel.setActiveItem(main_2);
	}else{
		mainPanel.animateActiveItem(main_2,{
			type : 'slide',
			direction: 'left'
		});
	}
	// mainPanel.setActiveItem(main_2);
	
	
	Global.DeptInfoController.initDpetInfo(deptCode);
	document.getElementById("logo_title").innerText = "部门明细";
}

/**
 * 返回到部门详情页面
 */
function deptIndexToDeptInfo(deptCode) {
	/// console.info("deptIndexToDeptInfo == deptCode " + deptCode);
	Global.backPage = "deptIndex";

	var mainPanel = Ext.getCmp("rootMain");
	var main_2 = Ext.getCmp("deptInfoView");
			
			// alert(mainPanel);
	if(Ext.os.is.Android){
		mainPanel.setActiveItem(main_2);
	}else{
		mainPanel.animateActiveItem(main_2,{
			type : 'slide',
			direction: 'left'
		});
	}
	// mainPanel.setActiveItem(main_2);
	
	
	Global.DeptInfoController.initDpetInfo(deptCode);
	document.getElementById("logo_title").innerText = "部门 明细";
}

/**
 * 部门详情返回页面
 */
function toBackPage() {
	if(Global.backPage == "moreDept") {
		toMoreDept("info");
	} else {
		toDeptIndex("info");
	}
}

/**
 * 部门详情——选择部门事件
 * @param {Object} deptCode
 */
function clickDeptIndexDpet(deptCode) {

	Global.DeptIndexController.clickDpetIndexDept(deptCode);

	Ext.select("div.btn_con3_on", true).each(function() {
		// 框架存在 bug，修改时有问题
		// this.removeCls("btn_con3_on").addCls("btn_con3");
		console.info(this.id);
		document.getElementById(this.id).setAttribute("class", "x-button-normal x-button btn_con3");
	});
	// 框 架存在 bug
	// Ext.get("deptIndex_btn_" + deptCode).removeCls("btn_con3").addCls("btn_con3_on");

	document.getElementById("deptIndex_btn-" + deptCode).setAttribute("class", "x-button-normal x-button btn_con3_on");
	document.getElementById("logo_title").innerText = "部门明细";
}

//帮助事件
function updateInfo3(value) {
	Ext.get("btn1").addCls("about_icon2");
	if(navigator.platform == "iPad") {
		window.location = "http://www.pica.com";
	} else {
		window.myButton.myClick();
	}

	Ext.get("btn1").removeCls("about_icon2").addCls("about_icon");
}

//刷新方式
function refreshMethod() {
	
	var scro = Ext.getCmp('time1').getScrollable();// .scrollTo(100,0);
	scro.onScrollEnd();
	
	Global.loading = true;
	Ext.get("btn2").removeCls("refresh_icon");
	Ext.get("btn2").addCls("refresh_icon2");

	Global.MainController.getData();
	
	Ext.get("btn2").removeCls("refresh_icon2");
	Ext.get("btn2").addCls("refresh_icon");
	Global.loading = false;
}

/**
 * Format a number as US currency
 * @param {Number/String} value The numeric value to format
 * @return {String} The formatted currency string
 */

function usMoney(v) {
	v = (Math.round((v - 0) * 100)) / 100;
	v = (v == Math.floor(v)) ? v + ".00" : ((v * 10 == Math.floor(v * 10)) ? v + "0" : v);
	// v = (v == Math.floor(v)) ? v : ((v * 10 == Math.floor(v * 10)) ? v + "0" : v);
	v = String(v);
	var ps = v.split('.');
	var whole = ps[0];
	// var sub = ps[1] ? '.' + ps[1] : '';//'.00';
	var sub = "";
	
	var r = /(\d+)(\d{3})/;
	while(r.test(whole)) {
		whole = whole.replace(r, '$1' + ',' + '$2');
	}
	v = whole + sub;
	if(v.charAt(0) == '-') {
		return '-$ ' + v.substr(1);
	}
	return "$ " + v;
}
function indexDeptStr(str){
	str = String(str);
	if(str.length > 4){
		return str.substr(0,4) + "...";
	}
	return str;
}
