Ext.define("Sencha.view.modiapad.TopPanel", {
	extend : 'Ext.Panel',
	
	requires : [
		'Ext.TitleBar',
		'Sencha.view.modiapad.TimeBar',
		'Sencha.view.modiapad.TimePanel'
	],
    xtype : 'TopPanel',
	config : {
		tabBarPosition : 'bottom',
		cls:'topPanel',
		items : [
		{
			xtype : 'TimeBar',
			docked : 'top'

		}, {
			xtype : 'TimePanel',
			id : 'time1'
		}
		]
	}
});