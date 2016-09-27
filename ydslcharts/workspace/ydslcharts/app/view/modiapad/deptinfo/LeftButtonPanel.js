Ext.define("Sencha.view.modiapad.deptinfo.LeftButtonPanel", {
	extend : "Ext.Panel",
	requires : ['Ext.chart.Chart'],
	xtype : 'DeptInfoLeftButtonPanel',
	config : {

		id : "deptInfoLeftButtonPanel",
		items : {
			xtype : 'chart',
			height : 201,
			store : "DeptDetailListStore",
			style : '-moz-border-radius-bottomleft: 10px;-moz-border-radius-bottomright:10px;margin-top:8px;border:1px solid #b5bac0;',
			animate : true,
			shadow : false,
			legend : {
				position : 'top'
			},
			axes : [{
				type : 'Numeric',
				position : 'left',
				fields : ['flightTotal', 'hotelTotal'],
				grid : true,
				grid : {
					odd : {
						opacity : 1,
						fill : '#ddd',
						stroke : '#bbb',
						'stroke-width' : 1
					}
				},
				minorTickSteps : 1,
				majorTickSteps : 5,
				label : {
					renderer : function(v) {
						if(v === 0) {
							return "";
						}
						return " " + v.toFixed(0);
					}
				},
				dashSize : 0
			}, {
				type : 'Category',
				position : 'bottom',
				fields : ['date'],
				grid : true,
				dashSize : 1
			}],
			series : [{
				type : 'line',
				highlight : {
					size : 7,
					radius : 7
				},

				fill : false,
				smooth : true,
				showMarkers : true,
				axis : 'left',
				//axis: 'left',
				xField : 'date',
				yField : 'flightTotal',
				title : '机票',
				showInLegend : false
			}, {
				type : 'line',
				highlight : {
					size : 7,
					radius : 7
				},
				//axis: 'left',
				fill : false,
				smooth : true,
				showMarkers : true,
				axis : 'left',
				xField : 'date',
				yField : 'hotelTotal',
				title : '酒店',
				showInLegend : false
			}]
		}

	}
});

