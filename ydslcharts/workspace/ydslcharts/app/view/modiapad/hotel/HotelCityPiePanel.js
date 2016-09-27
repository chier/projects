Ext.define('Sencha.view.modiapad.hotel.HotelCityPiePanel', {
	extend : 'Ext.Panel',
	xtype : 'HotelCityPiePanel',
	config : {
		iconCls : 'pie',
		layout : 'fit',

		items : [{
			xtype : 'chart',
			id : 'hotelCityPiePanel',
			cls : 'pie1',
			theme : 'Demo',
			store : 'CityPieProxyStore',
			style : 'background:transparent;border:0px solid red;',
			shadow : true,
			animate : true,
			insetPadding : 8,
			interactions : [{
				type : 'itemhighlight'
			}, {
				type : 'piegrouping',
				/*
				 onSelectionChange : function(me, items) {

				 if(items.length) {
				 var storeItem = items[0].storeItem;
				 var city = storeItem.get('city');

				 console.info("city == " + city);
				 btnChange('city');
				 Global.MainController.getDataByCityElement(city);
				 }
				 }
				 */
			}],
			series : [{
				type : 'pie',
				field : 'hotelTotal',
				showInLegend : true,
				donut : 10,
				highlight : {
					segment : {
						margin : 10
					}
				},
				label : {
					field : 'city',
					display : 'rotate',
					contrast : true,
					font : '8px'
				}
			}]
		}]
	}
});
