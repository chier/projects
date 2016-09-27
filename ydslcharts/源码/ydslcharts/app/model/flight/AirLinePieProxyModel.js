Ext.define('Sencha.model.flight.AirLinePieProxyModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'airline',type:'string'},
					{name:'flightTotal',type:'string',convert:function(val){
						return parseFloat(val);
					}}]
			}
});