Ext.define('Sencha.view.flight.FlightTimeBar', {
			extend : 'Ext.Toolbar',
			xtype : 'FlightTimeBar',

			config : {
				layout : 'hbox',
				docked : 'top',
				items : [{
							xtype : 'spacer',
							width:920
						}, {
							xtype : 'segmentedbutton',
							allowDepress : true,
							items : [{
								        id:'weekButtonFlight',
										text : '周',
										pressed : true
									}, {
										id:'monthBuutonFlight',
										text : '月'
									}, {
										id:'seasonBuutonFlight',
										text : '季'
									}]
						}

				]
			}
		});