Ext.define("Sencha.view.hotel.HotelMain", {
    extend: "Ext.Container",
    config: {
    	tabBarPosition: 'bottom',
        fullscreen: true,
	    layout: {
	        type: 'vbox'
	    },
        items: [//{
				///	xtype : 'HotelTopPanel',
				//	id : 'HotelTopPanel'
				//},                 
            	{   
            		
                    xtype: 'HotelRootPanel',
                	id: 'HotelRootPanel'
            	}
        ]       
    }

});


