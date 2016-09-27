Ext.define('Sencha.view.flight.FlightOrdersLineChart', {
	xtype : 'FlightOrdersLineChart',
	extend : 'Ext.chart.Chart',
	requires : ['Ext.chart.Chart'],
	config : {
		cls : 'stock1',
		id : 'flightOrdsChart',
		theme : 'Demo',
		store : 'FlightOrdsProxyStore',
		style : '-moz-border-radius-bottomleft: 10px;-moz-border-radius-bottomright:10px;margin-top:8px;border:1px solid #b5bac0;',
		animate : true,
		shadow : false,
		height : 317,
		legend : {
			position : 'top',
			style : 'font-size:5px;'
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
			id : 'flightOrdsChart_Numeric',
			//minimum: 0,
			position : 'left',
			fields : ['经济舱全价', '头等商务舱', '经济舱折扣', '国航', '中航', '海航', '深航', '南航', '东航','夏航', '其他', '北京', '天津', '上海', '广州', '深圳','郑州','三亚','重庆','海口','其他','昆明','南宁','杭州','成都'],
			grid : {
				odd : {
					opacity : 1,
					fill : '#ddd',
					stroke : '#bbb',
					'stroke-width' : 1
				}
			},
			minimum : 0,
			// maximum: 100,
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
			showMarkers : true, // 显示数据点
			highlight : {
				size : 7,
				radius : 7
			},
			smooth : true,
			fill : false, // 表示数据以雾形显示
			axis : 'left',
			xField : 'date',
			yField : '中航'
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
			yField : '南航'
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
			yField : '海航'
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
			yField : '厦航'
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
			yField : '东航'
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
			yField : '其它'
		}]
	}
}); 