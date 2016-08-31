Ext.define("Sencha.view.caseStatistics.LeftTopPanel", {
	extend : "Ext.Panel",
	xtype : 'CaseStatisticsLeftTopPanel',
	config : {
		height : 250,
		layout : {
			type : 'hbox',
			align : 'stretch'
		},
		items : [{
					flex : 0.30,
					height : 250,
					html : '<canvas id="myYibiao" height=180></canvas>'
				}, {
					flex : 1,
					height : 250,
					html : '<div style="width:60%;height:100%;font-size:13px;margin-left:50px;"><table width="50%" height=50 border=0><tr><td>&nbsp;&nbsp;</td></tr><table><table style="width:100%;height:60%;" ><tr><td width="30px">&nbsp;</td><td align="left" style="font-size:20px;">上报总数量：</td></tr><tr><td width="30px">&nbsp;</td><td align="left"><label id="totalMoney"><font id="fotTotalMoney" size="5">&nbsp;&nbsp;</font></label></td></tr><tr><td width="30px">&nbsp;</td><td align="left" style="font-size:16px;"><label id="totalOrders">试点数量：<font class="blue"  id="fotTotalOrders">0</font></label></td></tr><tr><td width="30px">&nbsp;</td><td align="left" style="font-size:16px;"><label id="hbData">平均数量：<font class="red" id="fotTotalLoopGrowth">0%</font></label></td></tr></table><table width="100%" height="20%" border=0><tr><td></td></tr><table></div>'
				}]
	}
});
