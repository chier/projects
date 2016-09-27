Ext.define('Sencha.view.modiapad.flight.ArrivedPiePanel', {
	extend : 'Ext.Panel',
	xtype : 'ArrivedPiePanel',
	config : {
		iconCls : 'pie',
		layout : 'fit',
		items : [{
			xtype : 'chart',
			id : 'flightArrivedPiePanel',
			cls : 'pie1',
			theme : 'Demo',
			store : 'ArrivedPieProxyStore',
			shadow : true,
			style : 'background:transparent;border:0px solid red;',
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
						var cabin = storeItem.get('offPoint');
						btnChange('offpoint');
						Global.MainController.getDataByOffPointElement(cabin);
					}
				}
				*/
			}],
			series : [{
				type : 'pie',
				field : 'flightTotal',
				showInLegend : true,
			//	donut : 10,
				highlight : {
					segment : {
						margin : 7
					}
				},
				label : {
					field : 'offPoint',
					display : 'rotate',
					contrast : true,
					font : '8px'
				}
			}]
		}]
	}
});
