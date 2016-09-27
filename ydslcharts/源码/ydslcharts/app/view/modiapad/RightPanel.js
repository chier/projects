Ext.define('Sencha.view.modiapad.RightPanel', {
	extend : 'Ext.Panel',
	requires : [
		'Sencha.view.modiapad.RightTopPanel',
		'Sencha.view.modiapad.DataList',
	],
	xtype : 'RightPanel',
	fullscreen : true,
	//height : 460,
	config : {
		cls : 'right_con',
		style : 'margin:9px 3px;',
		items : [{
			xtype : 'spacer',
			width : '96%',
			height : 9
		}, {
			xtype : 'RightTopPanel',
			id : 'RightTopPanel',
			height : 200,
			//flex : 1,
			width : '96%'
		}, {
			xtype : 'DataList',
			height : 210,
			width : '96%',
			//flex : 2
		}]
	}
}); 