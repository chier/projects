Ext.define("Sencha.view.TopPanel", {
	extend : 'Ext.Panel',
	
	requires : [
		'Ext.TitleBar',
		'Sencha.view.TimeBar',
		'Sencha.view.TimePanel'
	],
    xtype : 'TopPanel',
	config : {
		tabBarPosition : 'bottom',
		cls:'topPanel',
		items : [{
			xtype : 'TimeBar',
			docked : 'top'

		}, {
			xtype : 'TimePanel',
			id : 'time1'
		}]
	}
});