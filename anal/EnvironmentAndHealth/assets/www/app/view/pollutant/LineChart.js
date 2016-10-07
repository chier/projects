Ext.define('Sencha.view.pollutant.LineChart', {
	xtype : 'pollutantLineChart',
	extend : 'Ext.chart.Chart',
	requires : ['Ext.chart.Chart'],
	config : {
		cls : 'chartaaa',
		theme : 'Demo',
		store : 'PollutantChartStore',
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
			//{
			//	type : 'iteminfo',
			//	listeners : {
					//show : function(interaction, item, panel) {
					//	var storeItem = item.storeItem;
					//	console.info(item);
					//	panel.setHtml(['<ul><li><b>样品类别: </b>' +  item.value[0] + '</li>', '<li><b>上传数量: </b> ' + item.value[1] + '</li></ul>'].join(''));
					//}
				//}
			//}
		],
		axes : [{
			type : 'Numeric',
			//id   : 'indexNumeric',
			//grid : true,
			minimum : 0,
			position : 'left',
			fields : ['TESTRESULTS'],
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
			fields : ['SAMPLETYPE'],
			//grid : true,
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
				field : 'TESTRESULTS'
			},
			axis : 'left',
			xField : 'SAMPLETYPE',
			yField : 'TESTRESULTS'
		}]
	}
});
