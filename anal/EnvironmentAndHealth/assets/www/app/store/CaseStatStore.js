Ext.define('Sencha.store.CaseStatStore', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.CaseStatModel'],
    config:{
    	model   : 'Sencha.model.CaseStatModel',
    	autoLoad    : false,
    	storeId : 'CaseStatStore',
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