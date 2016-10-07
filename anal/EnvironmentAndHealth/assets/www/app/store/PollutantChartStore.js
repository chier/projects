Ext.define('Sencha.store.PollutantChartStore', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.PollutantChartModel'],
    config:{
    	model   : 'Sencha.model.PollutantChartModel',
    	autoLoad    : false,
    	storeId : 'PollutantChartStore',
        proxy: {
        }
    }
});