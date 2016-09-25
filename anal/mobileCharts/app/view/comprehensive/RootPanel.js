/**
 * 综合分析首页
 */
Ext.define("Sencha.view.comprehensive.RootPanel", {
	extend : 'Ext.Panel',
	requires : ['Sencha.model.ComprehensiveModel', 'Ext.data.TreeStore'],
	xtype : 'ComprehensiveRootPanel',
	config : {
		id : 'ComprehensiveRootPanel',
		cls : 'con_box',
		width : '100%',
		layout : {
			type : 'hbox'
		},

		items : [{
			xtype : 'nestedlist',
			id : "ComprehensiveDataLeftList",
			store : {
				type : "tree",
				model : "Sencha.model.ComprehensiveModel",
				defaultRootProperty : 'items',
				root : {}
			},
			displayField : 'comName',
			title : '综合分析',
			style : 'margin:15px 5px 15px 5px;text-align:left;',
			width : '30%',
			cls : 'detail_box1',
			scrollable : true,
			height : 670,
			// html : ' one 这是综合分析首页。位于 ComprehensiveRootPanel'
			listeners : {
				leafitemtap : function(item, list, index, c) {
//					Global.SurveyDataController.clickByItemId(list.getStore()
//							.getAt(index).data["surId"]);
					var data = list.getStore().getAt(index).data;
					Global.ComprehensiveController.clickByItem(data.comId,data.comName);
				}
			}

		}, {
			xtype : 'spacer',
			height : 550,
			width : 22
		}, {
			xtype : 'panel',
			width : '66%',
			height : 670,
			layout : {
				type : 'vbox'
			},
			cls : 'right_con',
			style : 'margin:15px 5px 5px 5px;',
			items : [{
				xtype : 'ComprehensiveRightTopPanel',
				style : 'background:#f0f0f0;margin-top:10px;margin-bottom:0px;margin-left:14px;',
				width : '96%',
				height : 254
			}, {
				animate : true,
				xtype : 'spacer',
				// width : '0.5%',
				height : 16
					// style : 'background-color:#999'
				}, {
				xtype : 'comprehensiveDataList',
				style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;',
				width : '96%',
				height : 375
			}]
		}]
	}
});
