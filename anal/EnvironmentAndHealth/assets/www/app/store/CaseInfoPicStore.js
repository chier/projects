Ext.define('Sencha.store.CaseInfoPicStore', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.CaseInfoPicModel'],
    config:{
    	model   : 'Sencha.model.CaseInfoPicModel',
    	autoLoad    : false,
    	storeId : 'CaseInfoPicStore',
        proxy: {
    }
    }
});