Ext.define('Sencha.view.hotel.HotelTimePanel', {
			xtype : 'HotelTimePanel',
			extend : 'Ext.Panel',
			id : 'hotelContent',
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
                    	id:'buttomTimeButnHotel',
                        xtype: 'segmentedbutton',
                        allowDepress: true
                    }						
						
						]
			}
		});