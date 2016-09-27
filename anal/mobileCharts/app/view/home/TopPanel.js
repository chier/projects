/**
 * home 目录下 最顶部面板
 */
Ext.define("Sencha.view.home.TopPanel", {
	extend : 'Ext.Panel',
	
	requires : [
		'Ext.TitleBar',
		'Sencha.view.home.TopTimeBar',
		'Sencha.view.home.TopTimePanel'
	],
    xtype : 'HomeTopPanel',
	config : {
		tabBarPosition : 'bottom',
		cls:'topPanel',
		items : [{
			xtype : 'HomeTopTimeBar',
			docked : 'top'
		}, {
			xtype : 'HomeTopTimePanel',
			id : 'time1'
		}]
	}
});