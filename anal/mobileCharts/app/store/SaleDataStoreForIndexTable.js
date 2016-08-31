Ext.define('Sencha.store.SaleDataStoreForIndexTable', {
	extend : 'Ext.data.Store',
	requires : ['Sencha.model.DataModelForIndexTable'],
	config : {
		model : 'Sencha.model.DataModelForIndexTable',
		autoLoad : false,
		storeId : 'SaleDataStoreForIndexTable',
		proxy : {
			type : 'jsonp',
			url : 'http://192.168.9.237:8009/report.do',
			extraParams : {//向后台传的参数
				jsonStr : '{"op":"Report.detail","source_id":"1","view_id":"2","data":{ ' + '"userId":"' + Global.ViewId + '","year":2012,"scope":week, "number":1}}'
			},
			reader : {
				format : 'json',
				type : 'json',
				rootProperty : 'data'
			}
		},
		load : function(options, scope){
			var me = this;
			console.info(me.getProxy());
			
			return me.callParent(options,scope);
		}
	}

}); 