Ext.define('Sencha.model.PollutantChartModel', {
			extend : 'Ext.data.Model',
			config : {
				fields : [{
					name : 'surveyyear',
					type : 'string'
				}, {
					name : 'sampletype',
					type : 'string'
				}, {
					name : 'DETECTINDEX',
					type : 'string'
				}, {
					name : 'TESTRESULTS',
					type : 'float'
				}]
			}
		});
