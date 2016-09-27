Ext.define('Sencha.view.flight.CabinPiePanel', {
	extend : 'Ext.Panel',
	xtype : 'CabinPiePanel',
	config : {
		iconCls : 'pie',
		layout : 'fit',
		items : [{
			xtype : 'chart',
			id:'flightCabinPiePanel',
			cls : 'pie1',
			theme : 'Demo',
			store : 'CabinPieProxyStore',
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
						console.info("CabinPiePanel");
						
						var storeItem = items[0].storeItem;
						var cabin = storeItem.get('cabin');
						console.info("airline == " + cabin);
						
						if(cabin == "头等、商务舱"){
							cabin = "头等商务舱";
						}
						
						// 先调用全部数据显示出来
						btnChange('cabin');
				    	Global.MainController.getDataByCabinElement(cabin);
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
					field : 'cabin',
					display : 'cross',
					contrast : true,
					font : '8px'
				}
			}]
		}]
	}
}); 