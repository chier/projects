/**
 * 上报统计业务类
 */
Ext.define('Sencha.controller.CaseStatisticsController', {
	extend : 'Sencha.controller.BaseController',
	config : {
		refs : {},
		control : {}
	},
	launch: function () {
		console.info(" CaseStatisticsController  launch");
//		 this.totalMoneyIndex(globalTimeScope, globalTimeNumber, 0, globalIndexLineBtn);
	
		
    },
	init : function() {
		console.info(" CaseStatisticsController init ");
		
		
	},
	/**
	 * 初始化列表信息
	 */
	initData : function() {
		var caseType = Global.getCaseType(Global.currentYears);
//		console.info(caseType);
		if(caseType["1"] == undefined || caseType["1"] == null){
			Ext.Msg.alert("提示","该年度仍未有数据，请稍候访问！");
			// 清除所有的数据源
			// 清除拆线与柱状图的数据源
			var store = Ext.getStore("CaseStatStore");
			store.removeAll();
			
			// 列表
			Ext.getCmp("caseStatGridView").getStore().removeAll();
			
			
			//关闭 试点详情 按钮
			 Ext.getCmp("caseInfoButton").setDisabled(true);
//			console.info(Ext.getCmp("caseInfoButton"));
			 
			 
			 // 总量
			document.getElementById('fotTotalMoney').innerHTML = 0;
			document.getElementById('fotTotalOrders').innerHTML = 0;
			document.getElementById('fotTotalLoopGrowth').innerHTML = 0;
			 
			//分类名称
			document.getElementById('flightTotal_total_div').innerHTML = "未知";
			document.getElementById('hotelTotal_total_div').innerHTML =  "未知";
			
			
			// 第一分类		
			document.getElementById('flightTotal_total').innerHTML = 0;
			document.getElementById('flightTotal_orders').innerHTML = 0;
			
			// 第二分类 
			document.getElementById('hotelTotal_total').innerHTML = 0;
			document.getElementById('hotelTotal_orders').innerHTML = 0;
			return ;
		}else{
			 Ext.getCmp("caseInfoButton").setDisabled(false);
		}
		
		
		document.getElementById('flightTotal_total_div').innerHTML = caseType["1"];
		document.getElementById('hotelTotal_total_div').innerHTML =  caseType["2"];
		
		
		var tmp = Math.round(Math.random() * 100);
		tmp = (tmp / 100) * 80;
		init(Math.round(tmp), 0, 0);
		init1(Math.round(tmp), 0, 0);
		init2(Math.round(tmp), 0, 0);
		
		console.info("CaseStatisticsController initData ");
//		var dataArr = []; // 将返回的数据封装成数组后，再渲染成布局
		var the_param = '{"op":"CaseStatistics.caseBase","source_id":"'
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
//				console.info(result);
				if (!result || result.code != 0) {
					console.log(result.msg);
				} else {
					Global.CaseStatisticsController.doSetYibiao(result);
					Global.CaseStatisticsController.doSetData(result);
				}
			}
		});
	},
	/**
	 * 对仪表的数据和显示的内容进行赋值
	 */
	doSetYibiao :function(result){
		// 上报总量情况
		document.getElementById('fotTotalMoney').innerHTML = result.data.allStatTotle;
		document.getElementById('fotTotalOrders').innerHTML = result.data.allPilotTotle;
		document.getElementById('fotTotalLoopGrowth').innerHTML = result.data.averageStat;
		
		var _show = result.data.allStatTotle;
		while(_show > 100) {
			_show = _show / 10;
		}
		_show = (_show / 100) * 80;
		init(Math.round(_show), 0, 0);
		
		// 居民健康类		
		document.getElementById('flightTotal_total').innerHTML = result.data.healthTotle;
		document.getElementById('flightTotal_orders').innerHTML = result.data.healthAverage;
		var _show = result.data.healthTotle;
		while(_show > 100) {
			_show = _show / 10;
		}
		_show = (_show / 100) * 80;
		init1(Math.round(_show), 0, 0);
		
		// 环境调查类
		
		document.getElementById('hotelTotal_total').innerHTML = result.data.environmentTotle;
		document.getElementById('hotelTotal_orders').innerHTML = result.data.environmentAverage;
		var _show = result.data.environmentTotle;
		while(_show > 100) {
			_show = _show / 10;
		}
		_show = (_show / 100) * 80;
		init2(Math.round(_show), 0, 0);
		
	},
	/**
	 * 对拆线图和数据表进行赋值
	 */
	doSetData :function(result){
		// 拆线和条形图赋值
		var store = Ext.getStore("CaseStatStore");
		store.removeAll();
		store.setData(result.data.detailList);
		store.insert(0,{"createTime":"","pname":"","pid":"","pCount":""});
		store.insert(result.data.detailList.length +1,{"createTime":"","pname":"","pid":"","pCount":""});
		
		
		// 显示数据赋值
		var storeData = Ext.create('Ext.data.Store', {
    		model: 'Sencha.model.CaseStatModel'});
    	storeData.setData(result.data.detailList);
    	
    	Ext.getCmp("caseStatGridView").setStore(storeData);
    	
	}
});
