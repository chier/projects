Ext.define("Sencha.view.deptindex.LeftPicPanel", {
	extend : "Ext.Panel",
	requires : ['Ext.chart.Chart'],
	xtype : 'DeptIndexLeftPicPanel',
	config : {
		width : '52%',
		layout : 'fit',
		items : [{
			xtype : 'chart',
			id : 'deptIndexChart',
			height : 244,
			cls : 'pie1',
			theme : 'Demo',
			store : 'DeptInfoStore',
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
						var _deptCode = storeItem.get('deptCode');
						clickDeptIndexDpet(_deptCode);
						// $(".btn_con3_on").removeClass().addClass("btn_con3");
						// $("#btn_" + _deptCode).removeClass().addClass("btn_con3_on");
						// var _obj = new Object();
						// _obj.id = "btn_" + _deptCode
						// IndexDeptReportApp.btnDeptClick(_obj);
					}
				}
			}],
			series : [{
				type : 'pie',
				field : 'orders',
				showInLegend : true,
				highlight : {
					segment : {
						margin : 5
					}
				},
				/*
				renderer : function(storeItem, item) {
					console.info(storeItem);
					console.info(item);
				},
				*/
				label : {
					field : 'deptName',
					display : 'rotate',
					contrast : true,
					font : '8px'
				}
			}]
		}]
	}
});

