Ext.define("Sencha.view.modiapad.Main", {
    extend: "Ext.Container",
	requires : [
		'Sencha.view.modiapad.TopPanel',
		'Sencha.view.modiapad.RootPanel',
		'Sencha.view.modiapad.deptindex.DeptIndex',
		'Sencha.view.modiapad.deptmore.DeptMore',
		'Sencha.view.modiapad.deptinfo.DeptInfo',
		'Sencha.view.modiapad.flight.FlightRootPanel',
		'Sencha.view.modiapad.hotel.HotelRootPanel'
	],
    config: {
    	
        id: "main",
        fullscreen: true,
	    layout: {
	        type: 'vbox'
	    },
	   
        items: [
			{
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
				height:470,
				/*
				items:[
					{
						xtype:'panel',
						html:'123456'
					}
				]
				*/
				items:[
					
						{         	
							xtype: 'RootPanel',
						//	height:768,
							id: 'RootPanel'
							
						},
						{
							xtype:'deptIndex',
						//	height:768,
							id:'deptIndexView',
							
						},{
							xtype:'deptMore',
						//	height:768,
							id:'deptMoreView'
						},{
							xtype:'DeptInfo',
						//	height:768,
							id:'deptInfoView'
						}
						,{
							xtype:'FlightRootPanel',
						//	height:768,
							id:'FlightRootPanel'
						}
						,{
							xtype:'HotelRootPanel',
						//	height:768,
							id:'HotelRootPanel'
						}
					]
				}
        ]       
    }

});


