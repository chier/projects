Ext.define('Sencha.model.hotel.HotelTableModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'date',type:'string'},
					{name:'star',type:'string'},
					{name:'hotelOrders',type:'string'},
					{name:'hotelTotal',type:'string'}]
			}				
});