Ext.define("Sencha.view.flight.FlightRootPanel", {
	extend : 'Ext.Panel',
	xtype : 'FlightRootPanel',
	config : {
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
		//html:'<span onclick="backIndex();" class="trangle_bg"></span>',
		cls : 'con_box',
		id : 'flightRootView',
		height : 670,
		width : '100%',
		items : [{
			xtype : 'button',
			id : 'backIndexBtn',
			cls : 'trangle_bg',
			handler : function() {
				backIndex();
			}
		}, {
			xtype : 'panel',
			cls : 'ticket-main',
			style : 'margin-left:15px;margin-top:5px;border :0px solid red',
			width : '98%',
			height : 660,
			items : [{
				xtype : 'FlightTopMainPanel',
				//style:'border :2px solid red',
				height : 375
			},{
				xtype : 'FilghtBottomMainPanel',
				height : 270
				
			}]
		}]
	}
}); 