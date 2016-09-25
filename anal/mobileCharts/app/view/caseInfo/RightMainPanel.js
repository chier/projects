Ext.define("Sencha.view.caseInfo.RightMainPanel", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.caseInfo.RightTopPanel', 'Sencha.view.caseInfo.DataList'],
	xtype : 'caseInfoRightMain',
	config : {
		id : "caseInfoRightMain",
		layout : {
			type : 'vbox'
		},
		cls : 'right_con',
		style : 'margin:15px 5px 5px 5px;',
		items : [{
			xtype : 'caseInfoRightTopPanel',
			style : 'background:#f0f0f0;margin-top:10px;margin-bottom:0px;margin-left:14px;',
			width : '96%',
			height : 254
		}, {
			animate : true,
			xtype : 'spacer',
			height : 16
		}, {
			xtype : 'caseInfoDataList',
			style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;',
			width : '96%',
			height : 375
		}]
	}
});

