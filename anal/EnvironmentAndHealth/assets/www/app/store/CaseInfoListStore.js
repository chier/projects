Ext.define('Sencha.store.CaseInfoListStore', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.CaseInfoListModel'],
    config:{
    	model   : 'Sencha.model.CaseInfoListModel',
    	autoLoad    : false,
    	storeId : 'CaseInfoListStore',
        proxy: {
    }
    }
});