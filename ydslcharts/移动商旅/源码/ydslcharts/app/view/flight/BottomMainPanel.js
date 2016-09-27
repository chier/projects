Ext.define("Sencha.view.flight.BottomMainPanel", {
	extend : 'Ext.Panel',
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
					xtype : 'FlightDataList',
					margin:'-20px auto auto 0',
					height : 260,
					width:'52%'
				}
		]
	}
});