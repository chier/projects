Ext.define("Sencha.view.flight.FlightTopPanel", {
	extend : 'Ext.Panel',
	requires : ['Ext.TitleBar',
		'Sencha.view.flight.FlightTimeBar',
		'Sencha.view.flight.FlightTimePanel'],
    xtype : 'FlightTopPanel',
	config : {
		tabBarPosition : 'bottom',
		cls:'topPanel',
		items : [{
			xtype : 'FlightTimeBar',
			docked : 'top'

		}, {
			xtype : 'FlightTimePanel',
			id : 'time1Flight'
		}]
	}
});