Ext.define('Sencha.view.flight.FlightTimePanel', {
			xtype : 'FlightTimePanel',
			extend : 'Ext.Panel',
			id : 'flightContent',
			dock : "top",
			style : 'background-color:#24364C;',
			width : '100%',
			height : '15%',
			config : {

				layout : {
					type : 'hbox',
					padding : '10',
					pack : 'start',
					align : 'top'
				},
				height : 40,
				scrollable : {
					direction : 'horizontal',
					directionLock : true
				},
				items : [
                    {
                    	id:'buttomTimeButnFlight',
                        xtype: 'segmentedbutton',
                        allowDepress: true
                    }						
						
						]
			}
		});