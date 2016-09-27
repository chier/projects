Ext.define("Sencha.view.modiapad.LeftBottomPanel2", {
    extend: "Ext.Panel",
    xtype: 'LeftBottomPanel2',
	// flex:1,  
    config: {
         //   layout: 'fit',
            width:'30%',
            
			layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [{//<canvas id="canvas2"></canvas>
			    html:'<div style="height:40%;width:55%;"><canvas id="canvas2"></canvas></div>'
            },{
				html:'<div style="position:relative;height:40%;width:100%;text-align:left;font-size:12px;">'
					+ '<table style="height:100%;width:100%;">'
					+ '<tr><td>&nbsp;&nbsp;</td><td>酒店总额：<span id="hotelTotal_total">-</span></td></tr>'
					+ '<tr><td>&nbsp;&nbsp;</td><td>总订单量：<span class="blue" id="hotelTotal_orders">0</span></td></tr>'
					+ '</table></div>'
			}]
    }
});