Ext.define("Sencha.view.modiapad.flight.BottomMainPanel", {
	extend : 'Ext.Panel',
	requires : [
		'Sencha.view.modiapad.flight.LeftBottomPiesPanel', 
		'Sencha.view.modiapad.flight.FlightDataList'
	],
    xtype : 'FilghtBottomMainPanel',
	config : {
      
        layout: {
                type: 'hbox',
                align: 'center'
        },
		items : [
			     {
					xtype:'FlightLeftBottomPiesPanel',
					margin:'15px auto auto 0',
					width:'45%',
					height:240
				},
				{
					xtype:'spacer',
					width:20
				},
			    {
					xtype  : 'FlightDataList',
					margin : '-16px 0px 0px 0px',
					height : 170,
					width:'52%'
				}
		]
	}
});