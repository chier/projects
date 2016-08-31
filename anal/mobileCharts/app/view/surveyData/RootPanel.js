/**
 * 综合分析首页
 */
Ext.define("Sencha.view.surveyData.RootPanel", {
			extend : 'Ext.Panel',
			xtype : 'SurveyRootPanel',
			requires : ['Sencha.model.Cars', 'Ext.data.TreeStore'],
			config : {
				// id : 'SurveyDataRootPanel',
				layout : {
					type : 'hbox'
				},
				cls : 'con_box',
				width : '100%',
				items : [{
					xtype : 'nestedlist',
					id : "aasurveyDataLeftList",
					store : {
						type : "tree",
						id : "AANestedListStore",
						model : "Sencha.model.Cars",
						defaultRootProperty : 'items',
						root : {}
					},
					displayField : 'surName',
					title:'调查数据',
					style : 'margin:15px 5px 15px 5px;',
					width : '30%',
					cls : 'detail_box1',
					scrollable : true,
					height : 610,
						// html : ' one 这是综合分析首页。位于 ComprehensiveRootPanel'
					listeners: {
						leafitemtap: function(item, list, index, c) {
							Global.SurveyDataController.clickByItemId(list.getStore().getAt(index).data["surId"]);
						}
					}
					}, {
					xtype : 'spacer',
					height : 550,
					width : 22
				}, {
					xtype : 'panel',
					width : '66%',
					height : 620,
					layout : {
						type : 'vbox'
					},
					cls : 'right_con',
					style : 'margin:15px 5px 5px 5px;margin-top:300;',
					items : [{
								xtype : 'SurveyDataRightTabPanel',
								style : 'margin-top:10px;margin-bottom:0px;margin-left:14px;',
								width : '97%',
								height : 576,
								id : 'SurveyDataRightTabPanel'
							}]
				}]
			}
		});
