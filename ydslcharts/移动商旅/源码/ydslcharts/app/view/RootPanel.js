Ext.define("Sencha.view.RootPanel", {
	extend : 'Ext.Panel',
	xtype : 'RootPanel',

	config : {
		id : 'RootPanel',

		cls : 'con_box',
		width : '100%',
		items : [{
			xtype : 'panel',
			cls : 'con_box3',
			height : 700,
			width : '100%',
			layout : {
				type : 'hbox',
				align : 'stretch'
			},
			items : [{
				xtype : 'LeftMainPanel',
				width : '40%',
				height : 540
			}, {
				xtype : 'spacer',
				width : 22,
				height : 550
			}, {
				xtype : 'RightPanel',
				width : '56%',
				height : 600
			}]
		}]
	}
});
