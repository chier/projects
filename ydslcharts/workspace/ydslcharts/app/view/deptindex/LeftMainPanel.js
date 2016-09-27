Ext.define("Sencha.view.deptindex.LeftMainPanel", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.deptindex.LeftPicPanel', 'Sencha.view.deptindex.LeftTopPanel', 'Sencha.view.deptindex.LeftButtonPanel'],
	xtype : 'deptIndexLeftMain',
	config : {
		id : "deptIndexLeftMain",
		// tabBarPosition : 'bottom',
		width : '24%',
		//height : '90%',
		layout : 'fit',
		style : 'margin:15px 5px 15px 5px;',
		layout : {
			//	type : 'fit'
		},
		items : [
		/*{
			xtype : 'panel',
			height : "100%",
			html : '<h1 class="left_title">部门详情</h1>'
		},*/ {
			xtype : 'panel',
			//style : 'border:2px solid #b5bac0;',
			height : 244,

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
				xtype : 'DeptIndexLeftTopPanel'
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

