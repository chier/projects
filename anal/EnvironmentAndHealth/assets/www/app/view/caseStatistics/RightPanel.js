Ext.define('Sencha.view.caseStatistics.RightPanel', {
	extend : 'Ext.Panel',
	xtype : 'CaseStatisticsRightPanel',
	fullscreen : true,
	height : 650,
	config : {
		cls : 'right_con',
		style : 'margin:15px 5px 15px 5px;',
		items : [{
			xtype : 'spacer',
			width : '100%',
			height : 22

		}, {
			xtype : 'CaseStatisticsRightTopPanel',
			id : 'CaseStatisticsRightTopPanel',
			height : 300,
			flex : 1,
			width : '98%'
		}, {
			xtype : 'caseStatisticsDataList',
			height : 260,
			width : '97%',
			flex : 2
		}]
	}
}); 