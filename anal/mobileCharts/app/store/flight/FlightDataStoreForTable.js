Ext.define('Sencha.store.flight.FlightDataStoreForTable', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.flight.FlightTableModel'],
    config:{
    	model   : 'Sencha.model.flight.FlightTableModel',
    	autoLoad    : false,
    	storeId : 'FlightDataStoreForTable',
        proxy: {
        type: 'jsonp',
        url : 'http://192.168.9.237:8009/report.do',
        extraParams: {  //向后台传的参数
                     jsonStr : '{"op":"Flight.flightCabin","source_id":"1","view_id":"2","data":{ '
		 + '"userId":"'+Global.ViewId+'","year":2012,"scope":week, "number":1}}'
                 },           
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    }
    }
    
});