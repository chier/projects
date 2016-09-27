Ext.define("Sencha.view.modiapad.deptindex.LeftMainPanel", {
	extend : "Ext.Panel",
	requires : [
		'Sencha.view.modiapad.deptindex.LeftPicPanel',
		'Sencha.view.modiapad.deptindex.LeftTopPanel', 
		'Sencha.view.modiapad.deptindex.LeftButtonPanel'],
	xtype : 'deptIndexLeftMain',
	config : {
		id : "deptIndexLeftMain",
		
		width : '24%',
		layout : 'fit',
		style : 'margin:9px 3px 9px 3px;',
		layout : {
			//	type : 'fit'
		},
		items : [
		{
			xtype : 'panel',
			height : 174,
			width : '100%',
			layout : {
				type : 'hbox',
				align : 'stretch'
			},
			items : [{
				xtype : 'DeptIndexLeftPicPanel',
			}, {
				animate : true,
				xtype : 'spacer',
				width : '1%',
				// cls : 'default-background-Color'
			}, {
				xtype : 'DeptIndexLeftTopPanel',
				id : 'deptIndexLeftTopPanel'
			}]
		}, {
			xtype : 'DeptIndexLeftButtonPanel',
		}, {

			xtype : 'button',
			width: '90%',
			id : 'deptindexButton',
			cls : 'btn_con2',
			text : '查看更多',
			
			handler: function(){
				toMoreDept();
			}
		}]
	},
	launch : function() {//组件加载完后执行方法
		console.info("Sencha.view.deptindex.DeptIndex  launch");
	}
});

