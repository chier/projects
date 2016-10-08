Ext.define('Sencha.model.PollutantChartModel', {
			extend : 'Ext.data.Model',
			config : {
				fields : [{
					name : 'SURVEYYEAR',
					type : 'string'
				}, {
					name : 'SAMPLETYPE',
					type : 'string'
				}, {
					name : 'DETECTINDEX',
					type : 'string'
				}, {
					name:'pilotShortName',
					type:'string'
				},{
					name : 'TESTRESULTSNUM',
					type : 'int'
				}]
			}
		});
