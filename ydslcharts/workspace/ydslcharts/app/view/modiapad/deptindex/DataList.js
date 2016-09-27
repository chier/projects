Ext.define('Sencha.view.modiapad.deptindex.DataList', {
	//    extend: 'Ext.dataview.DataView',
	//    extend: 'Ext.dataview.List',
	extend : 'Ext.Panel',
	requires : ['Sencha.view.GridView'],
	xtype : 'DeptIndexDataList',
	fullscreen : true,
	config : {
		id : 'deptIndexDataList',
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
			store : 'DeptDetailListStore',
			columns : [{
				header : '日期 ',
				dataIndex : 'date',
				width:'20%'
			}, {
				header : '部门',
				dataIndex : 'deptName',
				width:'20%'
			}, {
				header : '机票总额 ',
				dataIndex : 'flightTotal',
				width:'20%'
			}, {
				header : '酒店总额 ',
				dataIndex : 'hotelTotal',
				width:'20%'
			}, {
				header : '机票+酒店总额 ',
				dataIndex : 'total',
				width:'20%'
			}]
		}]
	}
});
