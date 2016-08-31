Ext.define('Sencha.store.ComprehensiveStore', {
	extend : 'Ext.data.JsonStore',
	requires : ['Sencha.model.ComprehensiveModel'],
	xtype : 'DeptInfoStore',
	config : {
		model : 'Sencha.model.ComprehensiveModel',
		autoLoad : false,
		storeId : 'ComprehensiveStore',
		proxy : {
			type : 'jsonp',
			url : Global.URL,
			extraParams : {// 向后台传的参数
				// var storePie_the_param =
				// '{"op":"Dept.getAllList","source_id":"1","view_id":"2","data":{"year":2012,"scope":"week","number":1}}';
				jsonStr : '{"op":"Comprehensive.getList","source_id":"'+ Global.SourceId +'","view_id":"' + Global.ViewId + '","data":{"years":' + Global.currentYears +'}}'
			}
		}
	}
});
