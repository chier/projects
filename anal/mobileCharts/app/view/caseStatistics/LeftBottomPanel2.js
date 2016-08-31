Ext.define("Sencha.view.caseStatistics.LeftBottomPanel2", {
    extend: "Ext.Panel",
    xtype: 'caseStatisticsLeftBottomPanel2',
	flex:1,  
    config: {
            layout: 'fit',
            width:'30%',
            height:'100%',
            
			layout: {
                type: 'vbox',
                align: 'stretch'
            },
            items: [{
			    html:'<div style="height:60%;width:55%;"><canvas id="canvas2"></canvas></div>'
            },{
				html:'<div style="position:relative;height:40%;width:100%;text-align:left;font-size:16px;"><table style="height:100%;width:100%;"><tr><td>&nbsp;&nbsp;</td><td><span style="font-size:16px;"  id="hotelTotal_total_div">环境调查类</span>总量：<span id="hotelTotal_total">-</span></td></tr><tr><td>&nbsp;&nbsp;</td><td>平均数量：：<span class="blue" id="hotelTotal_orders">0</span></td></tr></table></div>'
				}]
    }
});