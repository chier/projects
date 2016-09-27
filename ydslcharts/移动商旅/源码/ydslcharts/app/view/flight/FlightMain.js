Ext.define("Sencha.view.flight.FlightMain", {
    extend: "Ext.Container",
    config: {
    	tabBarPosition: 'bottom',
        fullscreen: true,
	    layout: {
	        type: 'vbox'
	    },
        items: [//{
				//	xtype : 'FlightTopPanel',
				//	id : 'FlightTopPanel'
				//},                 
            	{   
            		
                    xtype: 'FlightRootPanel',
                	id: 'FlightRootPanel'
            	}
        ]       
    }

});


