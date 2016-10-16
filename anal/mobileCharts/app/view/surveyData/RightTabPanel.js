Ext.define("Sencha.view.surveyData.RightTabPanel", {
	extend : "Ext.tab.Panel",
	xtype : 'SurveyDataRightTabPanel',
	requires : ['Ext.tab.Panel'],
	config : {
		scroll : false,
		defaults : {
			styleHtmlContent : true,
			iconMask : true
		},
		width : '98%',
		activeItem:0,
		tabBar : {
			// cls:'default-tabPanel-head',
			html : '<div id="indexSaleTitle" style="position:absolute;left:0px;top:0px;color:black"></div>',
			docked : 'top',
			layout : {
				pack : 'end'
			}
		},
		layout : {
			type : 'card',
			animation : Ext.os.is.Android ? null : {
				type : 'slide',
				direction : 'left'
			}

		},
		cardSwitchAnimation : {
			type : 'slide'
		},
		ui : 'light',
		items : [{
			// iconMask: true,
			title : '<div style="min-width:6.0em;line-height:24px;font-size:16px;"><span>全部数据</span></div>',
			cls : 'tab-border-background',
			style : 'background:#f0f0f0;',
			xtype : 'surveyDataDataList',
			// scrollable : {
			// direction : 'vertical',
			// directionLock : true
			// },
			id : 'surveyDataDataList',
			width : '100%'
		}, {
			title : '<div style="min-width:6.0em;line-height:24px;font-size:16px;"><span>条件查询</span></div>',
			xtype : 'formpanel',
			cls : 'tab-border-background',
			style : 'background:#f0f0f0;',
			id : 'surveyFormPanel',
			items : [{
						xtype : 'fieldset',
						style : 'background:#f0f0f0;',
						id : "surveyFieldset",
						items : []
					}, {
						xtype : 'toolbar',
						style : 'border-top:0px;',
						docked : 'bottom',
						layout : {
							pack : 'center'
						},
						items : [{
									xtype : 'button',
									text : '提交',
									handler : function() {
										Global.SurveyDataController
												.onSubmitForm();
									}
								}, {
									xtype : 'button',
									text : '重置',
									handler : function() {
										surveyFormPanel.reset();
									}
								}]
					}]
		}],
		listeners : {
			bottomchange:function( a, value, oldValue, eOpts ){
				console.info(a);
				console.info(value);
				console.info(oldValue);
				console.info(eOpts);
				
			},
			painted : function(panel) {
				var bar = panel.getTabBar();
				if (bar.getItems().length === 2) {
					bar.insert(3, {
						xtype : 'button',
						id : 'primevalDataButton',
						cls : 'btn_con3',
						style : 'margin-bottom:0px;width:108px;height:16px;',
						html : '<div class="f_right" style="min-width:6.0em;font-size:16px;color:#888888;text-shadow:rgba(0, 0, 0, 0.5) 0 -0.08em 0;">原始数据</div>',
						handler : function() {
							var surveyDataGridView = Ext
									.getCmp("surveyDataGridView");
							var selection = surveyDataGridView.getSelection();
							if (selection.length == 0) {
								Ext.Msg.alert("提示", "请选择一条数据！");
							} else {
								var _code = selection[0].data["CODE"];
								// 3. 查询相关的数据，并返回及结果
								var the_param = '{"op":"SurveyData.dfPicInfo","source_id":"'
										+ Global.SourceId
										+ '","view_id":"'
										+ Global.ViewId
										+ '","data":{"tblCode":"'
										+ Global.SurveyTableCode
										+ '","code":"'
										+ _code + '"}}';
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
										var _panel = Ext.create(
											'Ext.form.Panel', {
												style : 'background:#f0f0f0;',
												width : "70%",
												height : 670,
												fullscreen : true,
												items : [{
													xtype : 'panel',
													style : 'background:#f0f0f0;',
													height: 1064,
													html : '<div style="position: absolute; left: 5px; top: 5px;">' +
														'<img src="'+Global.BaseURL+'/controller/showImage.talent?imageUrl='+
													result.data +'" /></div>'
													// html :' 显示东西'
												}, {
													xtype : 'toolbar',
													docked : 'bottom',
													layout : {
														pack : 'center'
													},
													items : [{
														xtype : 'button',
														text : '关闭',
														handler : function() {
															var form = this
																.up('panel');
															form.hide();
														}
													}]
												}]
											});
										var _btn = Ext.getCmp("surveyData");
										_panel.showBy(_btn);
									}
								});
							}
						}

					});

					bar.insert(4, {
						xtype : 'button',
						id : 'infoDataButton',
						cls : 'btn_con3',
						style : 'margin-bottom:0px;width:108px;height:16px;',
						html : '<div  class="f_right" style="min-width:6.0em;font-size:16px;color:#888888;text-shadow:rgba(0, 0, 0, 0.5) 0 -0.08em 0;">详细数据</div>',
						handler : function() {
							var surveyDataGridView = Ext
								.getCmp("surveyDataGridView");
							var selection = surveyDataGridView.getSelection();
							if (selection.length == 0) {
								Ext.Msg.alert("提示", "请选择一条数据！");
							} else {
								var _code = selection[0].data["CODE"];
								// 3. 查询相关的数据，并返回及结果
								var the_param = '{"op":"SurveyData.japserInfo","source_id":"'
									+ Global.SourceId
									+ '","view_id":"'
									+ Global.ViewId
									+ '","data":{"tblCode":"'
									+ Global.SurveyTableCode
									+ '","code":"'
									+ _code + '"}}';
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
										var _html = result.data;

										var _panel = Ext.create(
											'Ext.form.Panel', {
												style : 'background:#f0f0f0;',
												width : "70%",
												height : 670,

												fullscreen : true,
												items : [{
													xtype : 'panel',
													style : 'background:#f0f0f0;',
													html : _html
													// html :' 显示东西'
												}, {
													xtype : 'toolbar',
													docked : 'bottom',
													layout : {
														pack : 'center'
													},
													items : [{
														xtype : 'button',
														text : '关闭',
														handler : function() {
															var form = this
																.up('panel');
															form.hide();
														}
													}]
												}]
											});
										var _btn = Ext.getCmp("surveyData");
										_panel.showBy(_btn);
									}
								});
							}
						}

					});
				}
			}
		}
	}
});
