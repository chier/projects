Ext.define('Sencha.view.hotel.StarPiePanel', {
	extend : 'Ext.Panel',
	xtype : 'StarPiePanel',
	config : {
		iconCls : 'pie',
		layout : 'fit',
		items : [{
			xtype : 'chart',
			id : 'hotelStarPiePanel',
			cls : 'pie1',
			theme : 'Demo',
			store : 'StarPieProxyStore',
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
						var star = storeItem.get('star');
						// btnChange('airline');
						// Global.MainController.getDataByAirLineElement(cabin);

						console.info("star == " + star);
						btnChange('star');
						Global.MainController.getDataByStarElement(star);
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
					field : 'star',
					display : 'rotate',
					contrast : true,
					font : '8px'
				}
			}]
		}]
	}
});
