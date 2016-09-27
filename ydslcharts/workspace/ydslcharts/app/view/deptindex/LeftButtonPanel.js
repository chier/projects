Ext.define("Sencha.view.deptindex.LeftButtonPanel", {
	extend : "Ext.Panel",
	xtype : 'DeptIndexLeftButtonPanel',
	config : {
		id : 'deptIndexLeftButtonPanel',
		//layout : 'fit',
		width : '100%',
		height : 290,
		cls : 'detail_box1',
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
