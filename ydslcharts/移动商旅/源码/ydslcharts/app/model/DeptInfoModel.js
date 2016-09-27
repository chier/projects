Ext.define('Sencha.model.DeptInfoModel', {
	extend : 'Ext.data.Model',
	config : {
		fields : ['deptCode', 'deptName', 'orders','total','flightTotal','hotelTotal']
	}
});
