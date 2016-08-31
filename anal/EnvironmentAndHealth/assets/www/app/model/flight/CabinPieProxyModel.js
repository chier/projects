Ext.define('Sencha.model.flight.CabinPieProxyModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'cabin',type:'string',convert:function(val){
						return val+"";
					}},
					{name:'flightTotal',type:'string',convert:function(val){
						return parseFloat(val);
					}}]
			}				
});