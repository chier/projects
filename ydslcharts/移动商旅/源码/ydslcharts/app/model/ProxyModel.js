Ext.define('Sencha.model.ProxyModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'date',type:'string'},
					{name:'orders',type:'int'},
					{name:'mark',type:'int'}]
			}				
		});
