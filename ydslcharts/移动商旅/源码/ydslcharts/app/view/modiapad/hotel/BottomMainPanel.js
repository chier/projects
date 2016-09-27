Ext.define("Sencha.view.modiapad.hotel.BottomMainPanel", {
	extend : 'Ext.Panel',
	requires : [
		'Sencha.view.modiapad.hotel.LeftBottomPiesPanel', 
		'Sencha.view.modiapad.hotel.HotelDataList'
	],
    xtype : 'HotelBottomMainPanel',
	config : {
        
        layout: {
                type: 'hbox',
                align: 'center'
        },
		items : [
			     {
					xtype:'HotelLeftBottomPiesPanel',
					margin:'15px auto auto 0',
					width:'45%',
					height:240
				},
				{	
					xtype:'spacer',
					width:20
				},
			    {
					xtype  : 'HotelDataList',
					margin : '-16px 0px 0px 0px',
					height : 170,
					width  : '52%'
				 }
		]
	}
});