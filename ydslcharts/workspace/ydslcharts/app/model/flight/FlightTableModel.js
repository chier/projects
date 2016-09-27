Ext.define('Sencha.model.flight.FlightTableModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'date',type:'string'},
					{name:'cabin',type:'string'},
					{name:'flightOrders',type:'string'},
					{name:'cancelOrders',type:'string'},
					{name:'flightTotal',type:'string'}]
			}				
});