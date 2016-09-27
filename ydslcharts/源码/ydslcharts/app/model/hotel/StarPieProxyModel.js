Ext.define('Sencha.model.hotel.StarPieProxyModel', {
			extend : 'Ext.data.Model',
			config: {
				fields : [{name:'star',type:'string',convert:function(val){
						return val+"";
					}},
					{name:'hotelTotal',type:'string',convert:function(val){
						return parseFloat(val);
					}}]
			}				
});