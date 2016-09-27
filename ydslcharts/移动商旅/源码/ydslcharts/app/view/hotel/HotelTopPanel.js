Ext.define("Sencha.view.hotel.HotelTopPanel", {
	extend : 'Ext.Panel',
	requires : ['Ext.TitleBar',
		'Sencha.view.hotel.HotelTimeBar',
		'Sencha.view.hotel.HotelTimePanel'],
    xtype : 'HotelTopPanel',
	config : {
		tabBarPosition : 'bottom',
		cls:'topPanel',
		items : [{
			xtype : 'HotelTimeBar',
			docked : 'top'

		}, {
			xtype : 'HotelTimePanel',
			id : 'time1Hotel'
		}]
	}
});