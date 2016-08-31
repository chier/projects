/**
 * map存储的键值对关系的 model
 */
Ext.define('Sencha.model.MapModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'key',type:'string'},
					{name:'value',type:'float'}]
			}				
});
