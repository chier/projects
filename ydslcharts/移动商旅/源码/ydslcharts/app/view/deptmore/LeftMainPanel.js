Ext.define("Sencha.view.deptmore.LeftMainPanel", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.deptmore.LeftPanel'],
	xtype : 'DeptMoreLeftMain',
	config : {
		id : "deptMoreLeftMain",
		// tabBarPosition : 'bottom',
		width : '24%',
		//height : '90%',
		layout : 'fit',
		// fullscreen : true,
		style : 'margin:15px 5px 15px 5px;',
		layout : {
			//	type : 'fit'
		},
		items : [/*{
			xtype : 'panel',
			height : "100%",
			html : '<h1 class="left_title">查看更多</h1>'
		}, */{
			xtype : 'DeptmoreLeftPanel',
		}]
	}
});

