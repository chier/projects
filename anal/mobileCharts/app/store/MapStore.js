Ext.define('Sencha.store.MapStore', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.MapModel'],
    config:{
    	model   : 'Sencha.model.MapModel',
    	autoLoad    : false,
    	storeId : 'MapStore',
        proxy: {
        type: 'jsonp',
        url : 'http://192.168.9.237:8009/report.do',
                 extraParams: {  //向后台传的参数
                     jsonStr : '{"op":"Report.detailChart","source_id":"1","view_id":"2","data":{ '
		 + '"userId":"'+Global.ViewId+'","year":2012,"scope":week, "number":1}}'
                 },           
        reader: {
            type: 'json',
//            successProperty: 'success',
            rootProperty : 'data'
        }
    }
    }
    
});