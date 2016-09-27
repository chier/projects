Ext.define("Sencha.view.modiapad.LeftBottomPanel1", {
    extend: "Ext.Panel",
    xtype: 'LeftBottomPanel1',                	
    // flex:1,
    config: {
    	//    layout: 'fit',
            width:'30%',
            
			layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [{//
			    html:'<div style="height:100%;width:100%;"><canvas id="canvas1"></canvas></div>'
            },{
				html:'<div style="position:relative;height:40%;text-align:left;font-size:12px;">'
					+'<table style="height:100%;width:100%;">'
					+'<tr><td>&nbsp;&nbsp;</td><td>机票总额：<span  id="flightTotal_total">-</span></td></tr>'
					+'<tr><td>&nbsp;&nbsp;</td><td>总订单量：<span class="blue" id="flightTotal_orders">0</span></td></tr>'
					+'</table></div>'
			}]
    }
});