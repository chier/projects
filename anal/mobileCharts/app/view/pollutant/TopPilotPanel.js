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
            allowMultiple : true,
			items:[
				{ text: '河北', pressed: true },
				{ text: '山西', pressed: true },
                { text: '内蒙' },
                { text: '辽宁' },
                { text: '吉林' },
                { text: '江苏' },
                { text: '浙江' },
                { text: '福建' },
                { text: '江西' },
                { text: '广东' },
                { text: '山东' },
                { text: '湖南' },
                { text: '湖北' },
                { text: '重庆' },
                { text: '贵州' },
                { text: '云南' },
                { text: '陕西' },
                { text: '甘肃' },
                { text: '新疆' },


			],
			listeners: {
				toggle: function(container, button, pressed){
					console.info("User toggled the '" + button.getText() + "' button: " + (pressed ? 'on' : 'off'));
				}
			}
		}]
	}
});
