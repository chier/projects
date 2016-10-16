Ext.define('Sencha.view.pollutant.TopPilotPanel', {
	xtype : 'PollutantTopPilotPanel',
	extend : 'Ext.Panel',
	id : 'content',
	dock : "top",
	width : '100%',
	height : '15%',
	config : {
		layout : {
			type : 'hbox',
			pack : 'start',
			align : 'top'
		},
		height : 48,
		baseCls : 'time-base-segmentedbutton',
		scrollable : {
			direction : 'horizontal',
			directionLock : true,
			width:"100%"
		},
		items : [{
			id : 'buttomPilotButn',
			xtype : 'segmentedbutton',
			allowDepress : false,
			items:[

			],
			listeners: {
				toggle: function(container, button, pressed){
					Global.PollutantController.config.pilots = button.config._code;
					Global.PollutantController.ajaxGetSampletype();
				}
			}
		}]
	}
});
