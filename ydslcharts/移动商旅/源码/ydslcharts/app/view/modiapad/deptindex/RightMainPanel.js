Ext.define("Sencha.view.modiapad.deptindex.RightMainPanel", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.deptindex.RightTopPanel', 'Sencha.view.deptindex.DataList'],
	xtype : 'deptIndexRightMain',
	config : {
		id : "deptIndexRightMain",
		layout : {
			type : 'vbox'
		},
		cls : 'right_con',
		style : 'margin:15px 5px 5px 5px;',
		items : [{
			xtype : 'DeptIndexRightTopPanel',
			style : 'background:#f0f0f0;margin-top:10px;margin-bottom:0px;margin-left:14px;',
			width : '96%',
			height : 202
		}, {
			animate : true,
			xtype : 'spacer',
			//	width : '0.5%',
			height : 9,
			//style : 'background-color:#999'
		},{
			xtype : 'DeptIndexDataList',
			style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;',
			width : '96%',
			height : 213,
		}]
	}
});

