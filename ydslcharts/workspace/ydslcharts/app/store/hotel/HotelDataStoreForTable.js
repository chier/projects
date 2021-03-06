Ext.define('Sencha.store.hotel.HotelDataStoreForTable', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.hotel.HotelTableModel'],
    config:{
    	model   : 'Sencha.model.hotel.HotelTableModel',
    	autoLoad    : false,
    	storeId : 'HotelDataStoreForTable',
        proxy: {
        type: 'jsonp',
        url : 'http://192.168.9.237:8009/report.do',
        extraParams: {  //向后台传的参数
                     jsonStr : '{"op":"Hotel.hotelStar","source_id":"1","view_id":"2","data":{ '
		 + '"userId":"'+Global.ViewId+'","year":2012,"scope":week, "number":1}}'
                 },           
        reader: {
            type: 'json',
            rootProperty : 'data'
        }
    }
    }
    
});