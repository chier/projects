Ext.define('Sencha.view.pollutant.BarChart', {
	xtype : 'pollutantBarChart',
	extend : 'Ext.chart.Chart',
	requires : ['Ext.chart.Chart'],
	config : {
		id : 'pollutantBarChart',
		themeCls : 'column1',
		style : 'background:#f0f0f0;',
		animate : {
			easing : 'bounceOut',
			duration : 750
		},
		store : 'PollutantChartStore',
		shadow : false,
		axes : [{

			type : 'Numeric',
			position : 'left',
			fields : ['TESTRESULTSNUM'],
			//minimum: 0,
			//maximum:50000,
			grid : {
				odd : {
					opacity : 1,
					fill : '#ddd',
					stroke : '#bbb',
					'stroke-width' : 1
				}
			},
			label : {
				renderer : function(v) {
					if(v === 0) {
						return "";
					}
					return v.toFixed(0);
				}
			},
			minorTickSteps : 1,
			majorTickSteps : 5,
			title : ''
		}, {
			type : 'Category',
			position : 'bottom',
			fields : ['pilotShortName'],
			grid : true,
			title : ''
		}],
		series : [{
			type : 'column',
			axis : 'left',
			highlight : true,
			renderer : function(sprite, storeItem, barAttr, i, store) {
				barAttr.fill = "#A52A2A";
				return barAttr;
			},
			label : {
				field : 'TESTRESULTSNUM'
			},
			xField : 'pilotShortName',
			yField : 'TESTRESULTSNUM'
		}]
	}
});
