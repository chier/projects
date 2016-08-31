/**
 * 调查数据业务类
 */
Ext.define('Sencha.controller.SurveyDataController', {
	extend : 'Sencha.controller.BaseController',
	config : {
		refs : {},
		control : {}
	},
	init : function() {
		// console.info(" SurveyDataController init ");
	},
	/**
	 * 初始化列表信息
	 */
	initList : function() {
		var dataArr = []; // 将返回的数据封装成数组后，再渲染成布局
		var the_param = '{"op":"SurveyData.getList","source_id":"'
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
					if(result.data){
						Ext.getCmp("aasurveyDataLeftList").getStore().setData(result.data);
					}else{
						Ext.getCmp("aasurveyDataLeftList").getStore().setData([]);
						
						
					}
				}
			}
		});
	},
	// 将返回的结果数据渲染成 按钮布局的数据
	onLeftListButtonLayout : function(dataArr) {
		// console.info("onLeftListButtonLayout");
		// 清空列表数据
		Ext.getCmp("surveyDataLeftList").removeAll(true, true);
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
		for (; i < _l; i++) {
			_data = dataArr[i];
			isPressed = false;
			_ddClass = "";
			_btnClass = "btn_con3";

			_html = '<span  class="num_w1" style="position:absolute;left:20px;" >'
					+ indexDeptStr(_data.surName)
					+ '</span><span class="pro_nr"></span>';

			Ext.getCmp("surveyDataLeftList").insert(
					Ext.getCmp("surveyDataLeftList").items.length, {
						xtype : 'button',
						width : '100%',
						cls : 'more_button_dd',
						pressed : isPressed,
						id : 'comBtn_' + _data.surId, // <em
														// class="square_disc_index'
														// + _barNum + '"></em>
						text : _data.surName,
						html : _html,
						handler : function(item) {
							Global.SurveyDataController.clickByItem(item);
						}
					});
		}
		var item = Ext.getCmp('surveyDataLeftList').items.items[0];
		this.clickByItem(item);
	},

	/**
	 * 根据comId 点击列表中的信息，显示数据 1.先清空所有的样式 2.对已经选择的进行样式渲染 3.查询及请求相关数据
	 * 
	 * @param {}
	 *            button
	 */
	clickByItemId : function(comId) {
		Global.SurveyTableCode = comId;
		
		// 3. 查询相关的数据，并返回及结果
		var the_param = '{"op":"SurveyData.tblItem","source_id":"'
				+ Global.SourceId + '","view_id":"' + Global.ViewId
				+ '","data":{"tblCode":"' + Global.SurveyTableCode
				+ '","page":1,"searchValue":""}}';
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
							Global.SurveyDataController.onFormLayout(result);
						}
					}
		});
		
		Global.SurveyDataController.clickByPage(1);
		
	},
	
	clickByPage : function(page) {
		var _strSV = "";
		// 查询条件的集合
		var _as = [];
		var _i = 0;
		var formPanel = Ext.getCmp("surveyFormPanel");
		var _jsonFromValue =  JSON.parse(JSON.stringify(formPanel.getValues(), null, 2));
//		console.info(typeof(_jsonFromValue));
		if(_jsonFromValue){
			for(var _p in _jsonFromValue){
//				console.info("'"+_p  + "' == '" + _jsonFromValue[_p]+"'");
				if(_jsonFromValue[_p]){
					if(_i == 0){
						_as[_i] = ""+_p + " like '%" + _jsonFromValue[_p]+"%'";
					}else{
						_as[_i] = " and " +_p + " like '%" + _jsonFromValue[_p]+"%'";
					}
					_i++;
				}
			}
		}
		_strSV = _as.join("");
//		console.info("_strSV == start");
//		console.info(_strSV);
//		console.info("_strSV == end");
		
		
		// 3. 查询相关的数据，并返回及结果
		var the_param = '{"op":"SurveyData.detail","source_id":"'
				+ Global.SourceId + '","view_id":"' + Global.ViewId
				+ '","data":{"tblCode":"' + Global.SurveyTableCode
				+ '","page":'+page+',"searchValue":"' + _strSV + '"}}';
		Ext.data.JsonP.request({
					url : Global.URL,
					callbackKey : 'callback',
					type : "POST",
					params : {
						tt_requestbody : the_param,
						format : 'json'
					},
					callback : function(success, result) {
						// console.info(result);

						if (!result || result.code != 0) {
							console.log(result.msg);
						} else {
							Global.SurveyDataController.onShowTabData(result);
						}
					}
				});
	},
	/**
	 * 填充form表代数据信息值
	 * 
	 * @param {}
	 *            result
	 */
	onFormLayout : function(result) {
		// 清空列表数据
		Ext.getCmp("surveyFieldset").removeAll(true, true);

		var _l = result.data.length;
		var i = 0;
		var _data;
		for (; i < _l; i++) {
			_data = result.data[i];
			Ext.getCmp("surveyFieldset").insert(
					Ext.getCmp("surveyFieldset").items.length, {
						xtype : 'textfield',
						name : _data.key,
						label : _data.value
					});
		}
	},
	/**
	 * 表单提交方法
	 */
	onSubmitForm : function() {
		Ext.getCmp("SurveyDataRightTabPanel").setActiveItem(0);
		Global.SurveyDataController.clickByPage(1);
	},
	/**
	 * 显示数据
	 */
	onShowTabData : function(result) {
		// 表单面板隐藏
		Ext.getCmp("surveyFormPanel").hide();
		// 数据面板显示
		Ext.getCmp("surveyDataDataList").show();

		// 头部显示情况
		var _itemList = result.data.itemList;
		var _itemlength = _itemList.length;
		var _columns = [];
		var _itemTitle;
		// 数据模型
		var _fields = [];
		var _width = (100 / 8);
		_width = _width + "%";
		// console.info("_width = " + _width);
		for (var i = 0; i < _itemlength; i++) {
			_itemTitle = _itemList[i];
			_fields[i] = {
				name : _itemTitle.itemCode,
				type : 'string'
			};
			if (i < 7) {
				_columns[i] = {
					header : _itemTitle.itemName,
					dataIndex : _itemTitle.itemCode,
					width : _width
				};
			}
		}

		Ext.regModel('_userModel', {
					fields : _fields

				});
		// 表头数据存储到表头数据缓存中
		var _titleStore = Ext.getStore("SurveyItemTitlsStore");
		_titleStore.removeAll();
		if (result && result.data.tableList.length > 0) {
			_titleStore.setData(result.data.itemList);
		}

		// 详细数据存储到数据缓存中
		var myStore = Ext.create('Ext.data.Store', {
					model : '_userModel'
				});

		myStore.removeAll();
		if (result && result.data.tableList.length > 0) {
			myStore.setData(result.data.tableList);
		}

		var surveyDataGridView = Ext.create('Sencha.view.surveyData.GridView',
				{
					title : 'Grid',
					store : 'MapStore',
					columns : _columns
				});

		// 清空列表数据
		Ext.getCmp("surveyDataDataList").removeAll(true, true);

		Ext.getCmp("surveyDataDataList").insert(0, {
			xtype : 'surveyDataGridView',
			id : 'surveyDataGridView',
			width : "97%",
			height : 500,
			scrollable : {
				direction : 'vertical',
				directionLock : true
			},
			title : 'Grid',
			columns : _columns
		});
		
		
		
		var _pageHtml = "当前显示"+result.data.pageInfo.page+"页,共"
			+ result.data.pageInfo.totalPage + "页,每页显示" + result.data.pageInfo.pageSize + "条,共" 
			+ result.data.pageInfo.resultCount + "条数据";

		Ext.getCmp("surveyDataDataList").insert(1, {
			xtype : 'toolbar',
			docked : 'bottom',
			height : 46,
			defaults : {
				styleHtmlContent : true,
				iconMask : true
			},
			cls : '.x-tabbar-light.x-docked-bottom',
			style : 'border-top:0px;',
			scrollable : false,
			items : [{
				xtype : 'button',
				cls : 'btn_con3',
				cls : 'tab-border-background',
				html : '<div class="f_right" style="font-size:16px;color:#FAFAFA;">上一页</div>',
				handler : function() {
					Global.SurveyDataController.clickByPage(result.data.pageInfo.previousPage);
				}
			}, {
				xtype : 'button',
				cls : 'btn_con3',
				cls : 'tab-border-background',
				html : '<div class="f_right" style="font-size:16px;color:#FAFAFA;">下一页</div>',
				handler : function() {
					Global.SurveyDataController.clickByPage(result.data.pageInfo.nextPage);
				}
			}, {
				xtype : 'panel',
				style:'font-size:16px;',
				html : _pageHtml
			}],
			layout : {
				pack : 'center',
				align : 'center'
			}
		});

		Ext.getCmp("surveyDataGridView").show();
		Ext.getCmp("surveyDataGridView").setStore(myStore);

	}
});
