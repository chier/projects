Ext.define('Sencha.store.DeptInfoStore', {
	extend : 'Ext.data.JsonStore',
	requires : ['Sencha.model.DeptInfoModel'],
	xtype : 'DeptInfoStore',
	config : {
		model : 'Sencha.model.DeptInfoModel',
		autoLoad : false,
		storeId : 'DeptInfoStore',
		proxy : {
			type : 'jsonp',
			url : 'http://192.168.9.237:8009/report.do',
			extraParams : {//向后台传的参数
				// var storePie_the_param = '{"op":"Dept.getAllList","source_id":"1","view_id":"2","data":{"year":2012,"scope":"week","number":1}}';
				jsonStr : '{"op":"Dept.getAllList","source_id":"1","view_id":"2","data":{"year":2012,"scope":"week","number":11}}'
			}
		}
	},
});
