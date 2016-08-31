Ext.define('Sencha.model.flight.ArrivedPieProxyModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'offPoint',type:'string'},
					{name:'flightTotal',type:'string',convert:function(val){
						return parseFloat(val);
					}}]
			}				
});