Ext.define('Sencha.store.PollutantTableStore', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.PollutantChartModel'],
    config:{
    	model   : 'Sencha.model.PollutantChartModel',
    	autoLoad    : false,
    	storeId : 'PollutantTableStore',
        proxy: {
        }
    }
});