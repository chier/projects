Ext.define('Sencha.model.PollutantChartModel', {
			extend : 'Ext.data.Model',
			config : {
				fields : [{
					name : 'createTime',
					type : 'string'
				}, {
					name : 'pname',
					type : 'string'
				}, {
					name : 'pid',
					type : 'int'
				}, {
					name : 'pCount',
					type : 'int'
				}]
			}
		});
