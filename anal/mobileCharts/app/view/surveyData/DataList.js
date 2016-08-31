Ext.define('Sencha.view.surveyData.DataList', {
	//    extend: 'Ext.dataview.DataView',
	//    extend: 'Ext.dataview.List',
	extend : 'Ext.Panel',
	xtype : 'surveyDataDataList',
	requires: [
        'Ext.data.Store'
    ],
	config : {
		cls : 'righttop',
		ui : 'round',
		scroll : true,
		fullscreen: true, 
		scrollable: {
			direction: 'horizontal',
			directionLock: true
		},
		title : 'DataList',
		items : [
			{
				xtype:'panel',html:'请点击左侧菜单，选择显示的数据。'
			}
		]
	}
});
