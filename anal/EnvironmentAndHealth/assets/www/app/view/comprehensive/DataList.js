Ext.define('Sencha.view.comprehensive.DataList', {
	//    extend: 'Ext.dataview.DataView',
	//    extend: 'Ext.dataview.List',
	extend : 'Ext.Panel',
	xtype : 'comprehensiveDataList',
	fullscreen : true,
	config : {
		id : 'comprehensiveDataList',
		cls : 'righttop',
		ui : 'round',
		scroll : false,
		title : 'DataList',
		layout : {
			type : 'card'
		},
		items : [{
				xtype : 'GridView',
				id:"compreGridView",
				title : 'Grid',
				store : 'MapStore',
				columns : [{
					header : '显示1',
					dataIndex : 'key',
					width:'50%'
					}, {
					header : '显示2',
						dataIndex : 'value',
						width:'50%'
					}]
		}]
	}
});
