/**
 * 综合分析业务类
 */
Ext.define('Sencha.controller.ComprehensiveController', {
	extend : 'Sencha.controller.BaseController',
	config : {
		refs : {},
		control : {}
	},
	init : function() {
//		console.info(" ComprehensiveController init ");
	},
	
	/**
	 *  初始化列表
	 */
	initList : function(){
		var dataArr = []; // 将返回的数据封装成数组后，再渲染成布局
		var the_param = '{"op":"Comprehensive.getList","source_id":"'+ Global.SourceId +'","view_id":"' + Global.ViewId + '","data":{"years":' + Global.currentYears +'}}';
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
				if(!result || result.code != 0) {
					console.log(result.msg);
				} else {
					
					Ext.getCmp("ComprehensiveDataLeftList").getStore().setData(result.data);
						 
//					var dataArr = [];
//					var _datas = result.data;
//					var _data;
//					var _l = _datas.length;
//					for(var i = 0; i < _l; i++) {
//						_data = _datas[i];
//						dataArr.push({
//							"comId" : _data.comId,
//							"comName" : _data.comName
//						});
//					}
////					console.info(dataArr);
//					var store = Ext.getStore("ComprehensiveStore");
//					store.removeAll();
//					if(_l !== 1) {
//						store.setData(dataArr);
//					}
//					Global.ComprehensiveController.onLeftListButtonLayout(dataArr);
				}
			}
		});
			
	},
	/**
	 * 根据comId 点击列表中的信息，显示数据
	 * 1.先清空所有的样式
	 * 2.对已经选择的进行样式渲染
	 * 3.查询及请求相关数据
	 * @param {} button
	 */
	clickByItem : function(comId,text){
//		console.info(item);
//		console.info(item.id);
//		var comId = item.id.split("_")[1];
//		console.info("comId = " + comId);
		//1.先清空所有的样式
//		Ext.select("div.btn_con3_on", true).each(function() {
//			// 框架存在 bug，修改时有问题
//			// this.removeCls("btn_con3_on").addCls("btn_con3");
////			console.info(this.id);
//			document.getElementById(this.id).setAttribute("class", "x-button-normal x-button btn_con3");
//		});
//		
//		//2.对已经选择的 item 渲染成相关样式
//		item.replaceCls("btn_con3", "btn_con3_on");
		document.getElementById("com-title").innerText = text;
		
		//3. 查询相关的数据，并返回及结果
		var the_param = '{"op":"Comprehensive.detail","source_id":"'+ Global.SourceId +'","view_id":"' + Global.ViewId + '","data":{"comId":' + comId +'}}';
		Ext.data.JsonP.request({
			url : Global.URL,
			callbackKey : 'callback',
			type : "POST",
			params : {
				tt_requestbody : the_param,
				format : 'json'
			},
			callback : function(success, result) {
				if(!result || result.code != 0) {
					console.log(result.msg);
				} else {
					Global.ComprehensiveController.onCharAndDataListLayout(result.data.titles,result.data.result);
				}
			}
		});
	},
	/**
	 * 渲染相关的char 和 data 数据
	 * 1. 获取相关的数据 渲染 char 
	 * 2. 将相关显示到 data 上
	 */
	onCharAndDataListLayout:function(titles,result){
		var dataArr = [];
		var _l = result.length;
		var _d;
		dataArr.push({
				"date" : '',
				"orders" : ''
		});
		for(var i=0;i<_l;i++){
			_d = result[i];
			dataArr.push({
				"date" : _d.key,
				"orders" : parseInt(_d.value)
			});
		}
		dataArr.push({
				"date" : '',
				"orders" : ''
		});
		this.onCharLayout(dataArr);
		this.onDataListLayout(titles,result);
	},
	// 对拆线图和柱状图填充数据
	onCharLayout:function(dataArr){
		var store = Ext.getStore('ProxyStore');
		store.removeAll();
		store.setData(dataArr);

		var lcIndex = Ext.getCmp("ComprehensiveLineChart");
		var indexBarChart = Ext.getCmp("ComprehensiveBarChart");
		var maxinum = getMaximumByObject(dataArr);
		lcIndex.getAxes().get(0).setMaximum(maxinum);
		indexBarChart.getAxes().get(0).setMaximum(maxinum);
		
	},
	
	//填充相关的数据
	onDataListLayout:function(titles,result){
		var _columns = [{
					header : titles.key,
					dataIndex : 'key',
					width:'50%'
				}, {
				header : titles.value,
					dataIndex : 'value',
					width:'50%'
				}];
		Ext.getCmp("compreGridView")._columns = _columns;
		Ext.getCmp("compreGridView").updateItemTpl();
		
		var mapStore = Ext.getStore('MapStore');
		mapStore.removeAll();
		if(result && result.length > 0){
				mapStore.setData(result);
		}
	},
	
	// 将返回的结果数据渲染成 按钮布局的数据
	onLeftListButtonLayout:function(dataArr){
		// 清空列表数据
		Ext.getCmp("comprehensiveLeftList").removeAll(true,true);
		var _data;
		var _barNum;
		var _ddClass = "";
		var _btnClass = "btn_con3";
		var _html;
		var _btnHtml;
		var _d;
		var isPressed = false;
		var _l = dataArr.length;
		var i = 0;
		for(;i<_l;i++){
			_data = dataArr[i];
			isPressed = false;
			_ddClass = "";
			_btnClass = "btn_con3";
			_barNum = (i + 1) % 7 == 0 ? 1 : (i + 1) % 7;
			Ext.getCmp("comprehensiveLeftList").insert(Ext.getCmp("comprehensiveLeftList").items.length, {
				xtype : 'button',
				width : '100%',
				cls : _btnClass,
				pressed : isPressed,
				id : 'comBtn_' + _data.comId, // <em class="square_disc_index' + _barNum + '"></em>
				text:_data.comName,
				html : '<em class="square_dept' + _barNum + '"></em><div style="position:absolute;left:64px;top:10px;">' + indexDeptStr(_data.comName) + '</div>',
				handler : function(item) {
					 Global.ComprehensiveController.clickByItem(item);
				}
			});
		}
		var item = Ext.getCmp('comprehensiveLeftList').items.items[0];
		this.clickByItem(item);
	}
});
