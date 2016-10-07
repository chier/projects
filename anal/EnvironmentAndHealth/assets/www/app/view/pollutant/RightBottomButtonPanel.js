Ext.define("Sencha.view.pollutant.RightBottomButtonPanel", {
	extend : "Ext.Panel",
	xtype : 'pollutantButtonPanel',
	config : {
		id : 'pollutantButtonPanel',
		//layout : 'fit',
		width : '100%',
		height : 290,
		//cls : 'detail_box1',
		scrollable : {
			direction : 'vertical',
			directionLock : true
		}
		/*,
		layout : {
			type : 'vbox',
			align : 'stretch'
		}
			*/
	}
});
