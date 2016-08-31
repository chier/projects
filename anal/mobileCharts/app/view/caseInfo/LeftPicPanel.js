Ext.define("Sencha.view.caseInfo.LeftPicPanel", {
	extend : "Ext.Panel",
	requires : ['Ext.chart.Chart'],
	xtype : 'caseInfoLeftPicPanel',
	config : {
		width : '100%',
		layout : 'fit',
		items : [{
			xtype : 'chart',
			id : 'caseInfoChart',
			height : 244,
			cls : 'pie1',
			theme : 'Demo',
			store : 'CaseInfoPicStore',
			shadow : true,
			animate : true,
			insetPadding : 8,
			style : 'background:transparent;border:0px solid red;',
			interactions : [{
				type : 'itemhighlight'
			}, {
				type : 'piegrouping',
				onSelectionChange : function(me, items) {
					if(items.length) {
						var storeItem = items[0].storeItem;
						var _pid = storeItem.get('pid');
						var _item;
						var _id = "caseInfoBtn_" + _pid;
							Ext.getCmp("caseInfoLeftButtonPanel").items.each(function(eachItem,i) {
								if(eachItem.getId() == _id){
									_item = eachItem;
								}
							});
						Global.CaseInfoController.clickByItem(_item,"picChar");
					}
				}
			}],
			series : [{
				type : 'pie',
				field : 'ratios',
				showInLegend : true,
				highlight : {
					segment : {
						margin : 10
					}
				},
				/*
				renderer : function(storeItem, item) {
					console.info(storeItem);
					console.info(item);
				},
				*/
				label : {
					field : 'pname',
					display : 'rotate',
					contrast : true,
					font : '10px'
				}
			}]
		}]
	}
});

