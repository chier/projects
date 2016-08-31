Ext.define('Sencha.store.flight.ArrivedPieProxyStore', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.flight.ArrivedPieProxyModel'],
    config:{
    	model   : 'Sencha.model.flight.ArrivedPieProxyModel',
    	autoLoad    : false,
    	storeId : 'ArrivedPieProxyStore',
        proxy: {
	        type: 'jsonp',
	        url : 'http://192.168.9.237:8009/report.do',
	        extraParams: {  //向后台传的参数
	                     jsonStr : '{"op":"Flight.flightOffPointPieChart","source_id":"1","view_id":"a9edc36a05dd42a6883d21b093b04011","data":{ '
			 + '"year":2012,"scope":week, "number":1}}'
	                 },           
	        reader: {
	            type: 'json',
	            rootProperty : 'data'
	        }
        }
    }
    
});