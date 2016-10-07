Ext.define('Sencha.view.pollutant.DataList', {
	extend : 'Ext.Panel',
	xtype : 'pollutantDataList',
	fullscreen : true,
	config : {
		//cls : 'righttop',
		ui : 'round',

		scroll : false,
		//margin : '10px 10px 10px 10px',
		//title : 'DataList',
		layout : {
			type : 'card'
		},
		items : [{
			xtype : 'GridView',
			title : 'Grid',
			store : 'PollutantTableStore',
			//style : 'height:254px;text-align:center;',
			id : 'pollutantGridView',
			columns : [{
				header : '年份',
				dataIndex : 'SURVEYYEAR',
				width : '20%'
			},
			{
				header : '样品类别',
				dataIndex : 'SAMPLETYPE',
				width : '20%'
			}, {
				header : '污染物',
				dataIndex : 'DETECTINDEX',
				width : '40%'
			}, {
				header : '污染值',
				dataIndex : 'TESTRESULTS',
				width : '20%'
			}
			]
		}]
	}
});
