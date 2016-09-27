Ext.define('Sencha.view.flight.AirLinePiePanel', {
	extend : 'Ext.Panel',
	xtype : 'AirLinePiePanel',
	config : {
		iconCls : 'pie',
		layout : 'fit',
		items : [{
			xtype : 'chart',
			id:'flightAirLinePiePanel',
			cls : 'pie1',
			theme : 'Demo',
			store : 'AirLinePieProxyStore',
			shadow : true,
			animate : true,
			style : 'background:transparent;border:0px solid red;',
			insetPadding : 8,
			interactions : [{
				type : 'itemhighlight'
			}, {
				type : 'piegrouping',
				/*
				onSelectionChange : function(me, items) {
					
					
					if(items.length) {
						var storeItem = items[0].storeItem;
						var cabin = storeItem.get('airline');
						btnChange('airline');
						Global.MainController.getDataByAirLineElement(cabin);
					}
				}
				*/
			}],
			series : [{
				type : 'pie',
				field : 'flightTotal',
				showInLegend : true,
				donut : 10,
				highlight : {
					segment : {
						margin : 7
					}
				},
				label : {
					field : 'airline',
					display : 'rotate',
					contrast : true,
					font : '8px'
				}
			}]
		}]
	}
}); 