Ext.define('Sencha.view.modiapad.DataList', {
	extend : 'Ext.Panel',
	requires : ['Sencha.view.GridView'],
	xtype : 'DataList',
	flex : 1,
	config : {
		cls : 'righttop',
		ui : 'round',
		scroll : false,
		margin : '10px 10px 10px 10px',
		title : 'DataList',
		layout : {
			type : 'card'
		},
		items : [{
			xtype : 'GridView',
			title : 'Grid',
			store : 'SaleDataStoreForIndexTable',
			style : 'text-align:center;',
			id : 'indexGridView',
			columns : [{
				header : '日期',
				dataIndex : 'date',
				width : '40%',
			},
			{
				header : '机+酒总量',
				dataIndex : 'orders',
				width : '20%',
			}, {
				header : '机+酒总额',
				dataIndex : 'total',
				width : '40%',
			}]
		}]
	}
});
