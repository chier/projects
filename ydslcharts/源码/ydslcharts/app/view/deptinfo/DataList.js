Ext.define('Sencha.view.deptinfo.DataList', {
	extend : 'Ext.Panel',
	xtype : 'DeptInfoDataList',
	fullscreen : true,
	config : {
		id : 'deptInfoDataList',
		cls : 'righttop',
		ui : 'round',
		scroll : false,
		width : '96%',
		margin : '10px 10px 10px 10px',
		title : 'DataList',
		layout : {
			type : 'card'
		},
		items : [{
			xtype : 'GridView',
			title : 'Grid',
			store : 'DeptAreaStore',
			columns : [{
				header : '日期 ',
				dataIndex : 'date',
				width : '14%'
			}, {
				header : '类型',
				dataIndex : 'productType',
				width : '14%'
			}, {
				header : '华北 ',
				dataIndex : 'huabei',
				width : '12%'
			}, {
				header : '华中 ',
				dataIndex : 'huazhong',
				width : '12%'
			}, {
				header : '华南 ',
				dataIndex : 'huanan',
				width : '12%'
			}, {
				header : '西北 ',
				dataIndex : 'xibei',
				width : '12%'
			}, {
				header : '西南 ',
				dataIndex : 'xinan',
				width : '12%'
			}, {
				header : '东北 ',
				dataIndex : 'dongbei',
				width : '12%'
			}]
		}]
	}
});
