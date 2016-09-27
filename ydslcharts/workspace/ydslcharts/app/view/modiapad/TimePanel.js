Ext.define('Sencha.view.modiapad.TimePanel', {
	xtype : 'TimePanel',
	extend : 'Ext.Panel',
	id : 'content',
	dock : "top",
	width : '100%',
	height : '15%',
	config : {
		layout : {
			type : 'hbox',
			padding : '10',
			pack : 'start',
			align : 'top'
		},
		height : 48,
		baseCls : 'time-base-segmentedbutton',
		scrollable : {
			direction : 'horizontal',
			directionLock : true,
			width:"90%"
		},
		items : [{
			id : 'buttomTimeButn',
			xtype : 'segmentedbutton',
			allowDepress : false
		}]
	}
});
