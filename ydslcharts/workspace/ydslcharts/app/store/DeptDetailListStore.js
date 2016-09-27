Ext.define('Sencha.store.DeptDetailListStore', {
	extend : 'Ext.data.JsonStore',
	requires : ['Sencha.model.DeptDetailListModel'],
	xtype : 'DeptDetailListStore',
	config : {
		model : 'Sencha.model.DeptDetailListModel',
		autoLoad : false,
		storeId : 'DeptDetailListStore',
		proxy : {
			type : 'jsonp',
			url : 'http://192.168.9.237:8009/report.do',
			extraParams : {//向后台传的参数
				// var storePie_the_param = '{"op":"Dept.getAllList","source_id":"1","view_id":"2","data":{"year":2012,"scope":"week","number":1}}';
				//jsonStr : '{"op":"Dept.detailList ","source_id":"1","view_id":"2","data":{"year":2012,"scope":"week","number":1,"deptCode":1}}'
				jsonStr : '{"op":"Dept.detailList","source_id":"1","view_id":"2","data":{"year":2012,"scope":"week", "number":1,"deptCode":"1"}}'
			},
			reader : {
				type : 'json',
				//            successProperty: 'success',
				rootProperty : 'data',
				/*
				root : function(data){
					console.info(data);
					return data;
				}*/
				/*
				root : function(data) {
					console.info("到调用了没有");
					console.info(data);
					
					var _type = 1;
					var dataArr = [];
					
					if(!data || data.code != 0) {
						alert(data.msg);
					} else {
						var _data = data.data;
						var _l = _data.length;

						var _d;
						var _total = 0;
						for(var i = 0; i < _l; i++) {
							_total += new Number(_data[i].total);
						}

						var _i = 0;
						while(_i < _l) {
							_data = data.data[_i];
							dataArr.push({
								"name" : _data.deptName,
								"data" : Math.floor((_data.total / _total) * 100, 2),
								"deptCode" : _data.deptCode,
								"flightTotal" : _data.flightTotal,
								"hotelTotal" : _data.hotelTotal,
								"total" : _data.total
							});
							_i++;
						}
					}
					
					return dataArr;
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
