/**
 * map存储的键值对关系的 model
 */
Ext.define('Sencha.model.CaseStatModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'createTime',type:'string',convert:function(val){
//						console.info(val);
						return val.substring(0,4)+'-'+val.substring(4,6)+'-'+val.substring(6,8);
					}},
					{name:'pname',type:'string'},
					{name:'pid',type:'int'},
					{name:'pCount',type:'int'}]
			}				
});
