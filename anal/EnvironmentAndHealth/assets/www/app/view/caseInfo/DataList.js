Ext.define('Sencha.view.caseInfo.DataList', {
	//    extend: 'Ext.dataview.DataView',
	//    extend: 'Ext.dataview.List',
	extend : 'Ext.Panel',
	xtype : 'caseInfoDataList',
	fullscreen : true,
	config : {
		id : 'caseInfoDataList',
		cls : 'righttop',
		ui : 'round',
		scroll : false,
		title : 'DataList',
		layout : {
			type : 'card'
		},
		items : [{
			xtype : 'GridView',
			title : 'Grid',
			id:"caseInfoGridView",
			store : 'CaseInfoListStore',
			columns : [{
				header : '日期 ',
				dataIndex : 'createTime',
				width:'20%'
			}, {
				header : '试点',
				dataIndex : 'pname',
				width:'20%'
			}, {
				header : Global.getCaseType(Global.currentYears)["1"] +'类 ',
				dataIndex : 'healthTotal',
				width:'20%'
			}, {
				header : Global.getCaseType(Global.currentYears)["2"] +'类 ',
				dataIndex : 'environmentTotal',
				width:'20%'
			}, {
				header : '总数',
				dataIndex : 'total',
				width:'20%'
			}]
		}]
	}
});
