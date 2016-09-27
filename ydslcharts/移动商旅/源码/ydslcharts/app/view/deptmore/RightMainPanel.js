Ext.define("Sencha.view.deptmore.RightMainPanel", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.deptmore.RightTopPanel', 'Sencha.view.deptindex.DataList'],
	xtype : 'DeptMoreRightMain',
	config : {
		id : "DeptMoreRightMain",
		cls : 'right_con',
		style : 'margin:15px 5px 15px 5px;',
		layout : {
			type : 'vbox'
		},
		items : [{
			xtype : 'DeptMoreRightTopPanel',
			style : 'background:#f0f0f0;margin-top:10px;margin-bottom:0px;margin-left:20px;',
			width : '96%',
			height : 250
		}, {
			animate : true,
			xtype : 'spacer',
			//	width : '0.5%',
			height : 10,
			//style : 'background-color:#999'
		}, {
			xtype : 'DeptIndexDataList',
			style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;',
			width : '96%',
			height : 310,
		}]
	}
});

