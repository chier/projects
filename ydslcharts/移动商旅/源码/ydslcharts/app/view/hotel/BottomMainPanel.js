Ext.define("Sencha.view.hotel.BottomMainPanel", {
	extend : 'Ext.Panel',
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
					margin : '-20px 0px 0px 0px',
					height : 260,
					width  : '52%'
				 }
		]
	}
});