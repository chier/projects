/**
 * 试点基本情况页面
 */
Ext.define("Sencha.view.caseStatistics.RootPanel", {
			extend : 'Ext.Panel',
			xtype : 'CaseStatisticsRootPanel',
			config : {
				id : 'CaseStatisticsRootPanel',
				cls : 'con_box',
				width : '100%',
				items : [{
							xtype : 'panel',
							cls : 'con_box3',
							height : 700,
							width : '100%',
							layout : {
								type : 'hbox',
								align : 'stretch'
							},
							items : [{
										xtype : 'CaseStatisticsLeftMainPanel',
										width : '40%',
										height : 540
									}, {
										xtype : 'spacer',
										width : 22,
										height : 550
									}, {
										xtype : 'CaseStatisticsRightPanel',
										width : '56%',
										height : 600
									}]
						}]
			}
		});
