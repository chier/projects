Ext.define("Sencha.view.Main", {
    extend: "Ext.Container",
    config: {
    	tabBarPosition: 'bottom',
        id: "main",
        fullscreen: true,
	    layout: {
	        type: 'vbox'
	    },
	    
        items: [{
					xtype : 'TopPanel',
					id : 'TopPanel'
				},
				{
					id:'rootMain',
					layout: 'card',
					
					layout : {
						type : 'card',
						animation: Ext.os.is.Android ? null :{
							 type : 'slide',  // Cover, Fade, Flip, Pop, Reveal, Scroll, Slide, 
							 direction: 'left'
						}
					},
					height:768,
					items:[
						{         	
							xtype: 'RootPanel',
							height:768,
							id: 'RootPanel'
							
						},{
							xtype:'deptIndex',
							height:768,
							id:'deptIndexView',
							
						},{
							xtype:'deptMore',
							height:768,
							id:'deptMoreView'
						},{
							xtype:'DeptInfo',
							height:768,
							id:'deptInfoView'
						}
						,{
							xtype:'FlightRootPanel',
							height:768,
							id:'FlightRootPanel'
						}
						,{
							xtype:'HotelRootPanel',
							height:768,
							id:'HotelRootPanel'
						}
					]
				}
        ]       
    }

});


