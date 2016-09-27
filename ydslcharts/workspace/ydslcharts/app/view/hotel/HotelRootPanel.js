Ext.define("Sencha.view.hotel.HotelRootPanel", {
	extend : 'Ext.Panel',
    xtype : 'HotelRootPanel',
	config : {
		layout: {
			type: 'vbox',
			align: 'stretch'            
		},        
		//html:'<span onclick="backIndex();" class="trangle_bg"></span>',
        cls:'con_box',
        id:'hotelRootView',
        width:'100%',
        height:620,
		items : [{
			xtype:'button',
			cls:'trangle_bg',
			handler:function(){
				backIndex();			
			}
		},{
			     xtype:'panel',
			     cls:'ticket-main',
			     style:'margin-left:15px;margin-top:5px;',
			     width:'98%',
			     height:660,
			     items:[
			     {
	                xtype : 'HotelTopMainPanel',
					height: 375
			     },		
				 {
               		xtype : 'HotelBottomMainPanel',
				 	height: 270
			     }
			     ]	
		}]
	}
});