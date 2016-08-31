Ext.define('Sencha.store.CaseInfoChartStore', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.CaseInfoChartModel'],
    config:{
    	model   : 'Sencha.model.CaseInfoChartModel',
    	autoLoad    : false,
    	storeId : 'CaseInfoChartStore',
        proxy: {
    }
    }
});