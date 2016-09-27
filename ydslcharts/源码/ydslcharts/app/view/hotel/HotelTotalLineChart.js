Ext.define('Sencha.view.hotel.HotelTotalLineChart', {
	xtype : 'HotelTotalLineChart',
	extend : 'Ext.chart.Chart',
	requires : ['Ext.chart.Chart'],
	config : {
		cls : 'stock1',
		theme : 'Demo',
		store : 'HotelTotalProxyStore',
		style : '-moz-border-radius-bottomleft: 10px;-moz-border-radius-bottomright:10px;margin-top:8px;border:1px solid #b5bac0;',
		id : 'hotelTotalLineChart',
		animate : true,
		shadow : false,
		height : 317,
		legend : {
			position : 'top'
		},
		interactions : [{
			type : 'iteminfo',
			listeners : {
				show : function(interaction, item, panel) {
					var storeItem = item.storeItem;
					panel.setHtml(['<ul><li><b>Month: </b>' + storeItem.get('date') + '</li>', '<li><b>Value: </b> ' + item.value[1] + '</li></ul>'].join(''));
				}
			}
		}],
		axes : [{
			type : 'Numeric',
			//minimum: 0,
			//maximum:30000,
			position : 'left',
			fields : ['经济型', '3星', '4星', '5星', '其他', '北京', '天津', '上海', '广州', '深圳','三亚','太原','武汉','长沙','合肥'],
			grid : true,
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
					return "  " + v.toFixed(0);
				}
			},
			minorTickSteps : 1,
			majorTickSteps : 5,
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
			showMarkers : true, // 显示数据点
			highlight : {
				size : 7,
				radius : 7
			},
			smooth : true,
			fill : false, // 表示数据以雾形显示
			axis : 'left',
			xField : 'date',
			yField : '北京'
		}, {
			type : 'line',
			showMarkers : true, // 显示数据点
			highlight : {
				size : 7,
				radius : 7
			},
			smooth : true,
			fill : false, // 表示数据以雾形显示
			axis : 'left',
			xField : 'date',
			yField : '天津'
		}, {
			type : 'line',
			showMarkers : true, // 显示数据点
			highlight : {
				size : 7,
				radius : 7
			},
			smooth : true,
			fill : false, // 表示数据以雾形显示
			axis : 'left',
			xField : 'date',
			yField : '上海'
		}, {
			type : 'line',
			showMarkers : true, // 显示数据点
			highlight : {
				size : 7,
				radius : 7
			},
			smooth : true,
			fill : false, // 表示数据以雾形显示
			axis : 'left',
			xField : 'date',
			showInLegend : true,
			yField : '广州'
		}, {
			type : 'line',
			showMarkers : true, // 显示数据点
			highlight : {
				size : 7,
				radius : 7
			},
			smooth : true,
			fill : false, // 表示数据以雾形显示
			axis : 'left',
			xField : 'date',
			showInLegend : true,
			yField : '深圳'
		}, {
			type : 'line',
			showMarkers : true, // 显示数据点
			highlight : {
				size : 7,
				radius : 7
			},
			smooth : true,
			fill : false, // 表示数据以雾形显示
			axis : 'left',
			xField : 'date',
			yField : '其他'
		}]
	}
});
