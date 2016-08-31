Ext.define("Sencha.view.caseStatistics.LeftBottomPanel", {
	extend : 'Ext.Panel',
	xtype : 'CaseStatisticsLeftBottomPanel',
	config : {
		flex : 3,
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
		height : 250,
		defaults : {
			flex : 2,
			layout : {
				type : 'hbox',
				align : 'stretch'
			},
			defaults : {
				xtype : 'chart',
				flex : 2,
				insetPadding : 20
			}
		},
		items : [{
					flex : 1,
					width : '50%',
					style : 'margin-left:10px;',
					items : [{
								xtype : 'CaseStatisticsLeftBottomPanel1',
								width : '30%'
							}, {
								xtype : 'spacer',
								width : '6%'
							}, {
								xtype : 'caseStatisticsLeftBottomPanel2',
								width : '30%'
							}]
				}, {
					flex : 1,
					width : '100%',
					height : 40,
					items : [{
								xtype : 'spacer',
								width : '6%'
							}, {
								xtype : 'button',
								id:"caseInfoButton",
								width : 300,
								height : 40,
//								id : 'bumenxiangqing',
								style : 'margin-top:20px;',
								html : '<div style="height:35px;line-height:35px;"><em class="icon_3"></em><span class="f_left">试点详情</span></div>',
								cls : 'btn_con2 f_left',
								handler : function() {
									console.info("试点详情，方法点击");
									Global.CaseInfoController.tabToCaseInfo();
								}
							}]
				}]
	}
});
