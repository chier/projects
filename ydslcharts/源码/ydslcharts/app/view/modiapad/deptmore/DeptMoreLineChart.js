Ext.define('Sencha.view.modiapad.deptmore.DeptMoreLineChart', {
	xtype : 'DeptMoreLineChart',
	extend : 'Ext.chart.Chart',
	requires : ['Ext.chart.Chart'],
	config : {
		cls : 'chartaaa',
		theme : 'Demo',
		store : 'ProxyStore',
		id : 'deptMoreLineChart',
		animate : true,
		shadow : false,
		/* 是否显示标题
		 legend : {
		 position : 'top'
		 },*/
		interactions : [
		/* 用以是否可以变动大小
		 {
		 type : 'panzoom',
		 axes : {
		 left : {}
		 }
		 },*/
		{
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
			id   : 'indexNumeric',
			grid : true,
			minimum : 0,
			position : 'left',
			fields : ['orders'],
			grid : {
				odd : {
					opacity : 2,
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
					return " " + v.toFixed(0);
				}
			},
			minorTickSteps : 1,
			title : ''
		}, {
			type : 'Category',
			position : 'bottom',
			fields : ['date'],
			grid : true,
			title : ''
		}],
		series : [{
			type : 'line',
			highlight : {
				size : 7,
				radius : 7
			},
			fill : false, //雾
			smooth : true,
			label : {
				field : 'orders'
			},
			axis : 'left',
			xField : 'date',
			yField : 'orders'
		}]
	}
});
