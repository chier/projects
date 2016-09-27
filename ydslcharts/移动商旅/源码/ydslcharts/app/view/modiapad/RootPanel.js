Ext.define("Sencha.view.modiapad.RootPanel", {
	extend : 'Ext.Panel',
	requires : [
		'Sencha.view.modiapad.LeftMainPanel',
		'Sencha.view.modiapad.RightPanel'
	],
	xtype : 'RootPanel',

	config : {
		id : 'RootPanel',
		cls : 'con_box',
		width : '100%',
		items : [{
			xtype : 'panel',
			cls : 'con_box3',
			height: 470,
			width : '100%',
			layout : {
				type : 'hbox',
				align : 'stretch'
			},
			items : [
			{
				xtype : 'LeftMainPanel',
			//	style:'background-color: #484f57;',
				width : '38%',
			//	height : 460
			}, {
				xtype : 'spacer',
				width : 22,
			//	height : 470
			}, {
				xtype : 'RightPanel',
				//style : 'height:90%;background-color: #466f57;',
				width : '56%',
				height : 450
			}]
		}]
	}
});
