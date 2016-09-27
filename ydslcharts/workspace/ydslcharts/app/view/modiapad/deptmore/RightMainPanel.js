Ext.define("Sencha.view.modiapad.deptmore.RightMainPanel", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.modiapad.deptmore.RightTopPanel', 'Sencha.view.modiapad.deptindex.DataList'],
	xtype : 'DeptMoreRightMain',
	config : {
		id : "DeptMoreRightMain",
		cls : 'right_con',
		style : 'margin:15px 5px 5px 5px;',
		layout : {
			type : 'vbox'
		},
		items : [{
			xtype : 'DeptMoreRightTopPanel',
			style : 'background:#f0f0f0;margin-top:10px;margin-bottom:0px;margin-left:12px;',
			margin : '10px 10px 10px 10px',
			width : '96%',
			height : 200
		}, {
			animate : true,
			xtype : 'spacer',
			//	width : '0.5%',
			height : 6,
			//style : 'background-color:#999'
		}, {
			xtype : 'DeptIndexDataList',
			style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:8px;',
			margin : '10px 10px 10px 10px',
			width : '96%',
			height : 200,
		}]
	}
});

