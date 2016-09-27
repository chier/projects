Ext.define('Sencha.model.DataModelForIndexTable', {
	extend : 'Ext.data.Model',
	config : {
		// id : 'dataModelForIndexTable',
		// fields : ['date','total','orders']

		// fields : ['date','total','orders','flightOrders','flightTotal','hotelOrders','hotelTotal']
		fields : [{
			name : 'date',
			type : 'string'
		}, {
			name : 'flightOrders',
			type : 'int'
		}, {
			name : 'flightTotal',
			type : 'int'
		}, {
			name : 'hotelOrders',
			type : 'int'
		}, {
			name : 'hotelTotal',
			type : 'int'
		}, {
			name : 'orders',
			type : 'string'
		}, {
			name : 'total',
			type : 'string'
		}]
	}
});
