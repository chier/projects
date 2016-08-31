Ext.define('Sencha.model.hotel.CityPieProxyModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'city',type:'string'},
					{name:'hotelTotal',type:'string',convert:function(val){
						return parseFloat(val);
					}}]
			}				
});