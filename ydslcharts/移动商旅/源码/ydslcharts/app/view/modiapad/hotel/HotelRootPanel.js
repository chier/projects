Ext.define("Sencha.view.modiapad.hotel.HotelRootPanel", {
	extend : 'Ext.Panel',
	requires : [
		'Sencha.view.modiapad.hotel.TopMainPanel', 
		'Sencha.view.modiapad.hotel.BottomMainPanel'
	],
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
        height:470,
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
			     height:460,
			     items:[
			     {
	                xtype : 'HotelTopMainPanel',
					height: 267
			     },		
				 {
               		xtype : 'HotelBottomMainPanel',
				 	height: 200
			     }
			     ]	
		}]
	}
});