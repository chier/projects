Ext.define('Sencha.view.RightPanel', {
	extend : 'Ext.Panel',
	xtype : 'RightPanel',
	fullscreen : true,
	height : 650,
	config : {
		cls : 'right_con',
		style : 'margin:15px 5px 15px 5px;',
		items : [{
			xtype : 'spacer',
			width : '100%',
			height : 22

		}, {
			xtype : 'RightTopPanel',
			id : 'RightTopPanel',
			height : 300,
			flex : 1,
			width : '98%'
		}, {
			xtype : 'DataList',
			height : 260,
			width : '97%',
			flex : 2
		}]
	}
}); 