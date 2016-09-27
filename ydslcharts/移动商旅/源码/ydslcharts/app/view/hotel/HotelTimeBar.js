Ext.define('Sencha.view.hotel.HotelTimeBar', {
			extend : 'Ext.Toolbar',
			xtype : 'HotelTimeBar',

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
								        id:'weekButtonHotel',
										text : '周',
										pressed : true
									}, {
										id:'monthBuutonHotel',
										text : '月'
									}, {
										id:'seasonBuutonHotel',
										text : '季'
									}]
						}

				]
			}
		});