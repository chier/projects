Ext.define('Sencha.model.DeptDetailListModel', {
	extend : 'Ext.data.Model',
	config : {
		fields : [{
			name : 'date',
			type : 'string'
		}, {
			name : 'deptCode',
			type : 'string'
		}, {
			name : 'deptName',
			type : 'string'
		}, {
			name : 'flightTotal',
			type : 'int'
		}, {
			name : 'hotelTotal',
			type : 'int'
		}, {
			name : 'total',
			type : 'int'
		}]
	}
});
