Ext.define('Sencha.store.CaseInfoChartStore', {
    extend  : 'Ext.data.Store',
    requires: ['Sencha.model.CaseInfoChartModel'],
    config:{
    	model   : 'Sencha.model.CaseInfoChartModel',
    	autoLoad    : false,
    	storeId : 'CaseInfoChartStore',
        proxy: {
        },
        fields: ['createTime', 'pname', 'pid', 'pCount'],
        fields: [
            {
                "createTime" : "20131231172253",
                "pname" : "河北",
                "pid" : 11,
                "pCount" : 853
            }, {
                "createTime" : "20131231172253",
                "pname" : "山西",
                "pid" : 12,
                "pCount" : 938
            }, {
                "createTime" : "20131231172253",
                "pname" : "内蒙",
                "pid" : 13,
                "pCount" : 426
            }, {
                "createTime" : "20131231172253",
                "pname" : "辽宁",
                "pid" : 14,
                "pCount" : 680
            }, {
                "createTime" : "20131231172253",
                "pname" : "吉林",
                "pid" : 15,
                "pCount" : 143
            }, {
                "createTime" : "20131231172253",
                "pname" : "江苏",
                "pid" : 16,
                "pCount" : 801
            }, {
                "createTime" : "20131231172253",
                "pname" : "浙江",
                "pid" : 17,
                "pCount" : 228
            }, {
                "createTime" : "20131231172253",
                "pname" : "福建",
                "pid" : 18,
                "pCount" : 96
            }, {
                "createTime" : "20131231172253",
                "pname" : "江西",
                "pid" : 19,
                "pCount" : 946
            }, {
                "createTime" : "20131231172253",
                "pname" : "山东",
                "pid" : 22,
                "pCount" : 412
            }, {
                "createTime" : "20131231172253",
                "pname" : "湖北",
                "pid" : 23,
                "pCount" : 307
            }, {
                "createTime" : "20131231172253",
                "pname" : "湖南",
                "pid" : 24,
                "pCount" : 443
            }, {
                "createTime" : "20131231172253",
                "pname" : "广东",
                "pid" : 21,
                "pCount" : 3022
            }, {
                "createTime" : "20131231172253",
                "pname" : "广西",
                "pid" : 25,
                "pCount" : 5843
            }, {
                "createTime" : "20131231172253",
                "pname" : "重庆",
                "pid" : 27,
                "pCount" : 1104
            }, {
                "createTime" : "20131231172253",
                "pname" : "贵州",
                "pid" : 28,
                "pCount" : 337
            }, {
                "createTime" : "20131231172253",
                "pname" : "云南",
                "pid" : 31,
                "pCount" : 5214
            }, {
                "createTime" : "20131231172253",
                "pname" : "陕西",
                "pid" : 33,
                "pCount" : 1435
            }, {
                "createTime" : "20131231172253",
                "pname" : "甘肃",
                "pid" : 35,
                "pCount" : 499
            }, {
                "createTime" : "20131231172253",
                "pname" : "新疆",
                "pid" : 36,
                "pCount" : 536
            }
        ]
    }
});