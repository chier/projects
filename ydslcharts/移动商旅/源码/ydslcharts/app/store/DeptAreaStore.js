Ext.define('Sencha.store.DeptAreaStore', {
	extend : 'Ext.data.JsonStore',
	requires : ['Sencha.model.DeptInfoModel'],
	xtype : 'DeptAreaStore',
	config : {
		model : 'Sencha.model.DeptAreaModel',
		autoLoad : false,
		storeId : 'DeptAreaStore',
		proxy : {
			type : 'jsonp',
			url : 'http://192.168.9.237:8009/report.do',
			extraParams : {//向后台传的参数
				jsonStr : '{"op":"Dept.areaList","source_id":"1","view_id":"2","data":{"year":2012,"scope":"week", "number":1,"deptCode":1}}'
			},
			reader : {
				type : 'json',
				//            successProperty: 'success',
				rootProperty : 'data'
				/*
				successProperty: 'success',
				root: function(data) {
					alert(90)
					var array = [];
	            	var fun = function(items) {
		            		for(var i in items){
								var item = items[i];
								console.info(item);
								//时间处理？
								array.push({newsId:item.newsId,
									 subject:item.subject, 
									 author:item.author, 
									 content:item.content});
							}
	            		}
	            	fun(data);
					return array
				}
				*/
			}
		}
	},
	/*
	 listeners : {
	 exception : function() {
	 console.info("Sencha.store.DeptInfoStore  exception");
	 },
	 load : function() {
	 console.info("Sencha.store.DeptInfoStore  load");
	 }
	 }
	 */
});
