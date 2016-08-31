Ext.define("Sencha.view.caseStatistics.LeftBottomPanel1", {
    extend: "Ext.Panel",
    xtype: 'CaseStatisticsLeftBottomPanel1',                	
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
			    html:'<div style="height:60%;width:55%;"><canvas id="canvas1"></canvas></div>'
            },{
				html:'<div style="position:relative;height:40%;text-align:left;font-size:16px;"><table style="height:100%;width:100%;"><tr><td>&nbsp;&nbsp;</td><td><span style="font-size:16px;" id="flightTotal_total_div">居民健康</span>类总量：<span  id="flightTotal_total">-</span></td></tr><tr><td>&nbsp;&nbsp;</td><td>平均数量：<span class="blue" id="flightTotal_orders">0</span></td></tr></table></div>'
				}]
    }
});