Ext.define("Sencha.view.deptindex.DeptIndex", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.deptindex.LeftMainPanel', 'Sencha.view.deptindex.RightMainPanel'],
	xtype : 'deptIndex',
	config : {
		id : "deptIndexView",
		// tabBarPosition : 'bottom',
		cls : '',
		width : '100%',
		// html : '<span onclick="toHome();" class="trangle_bg"></span>', // trangle_bg_on 选中状态
		// fullscreen : true,

		layout : {
			type : 'vbox'
		},
		items : [{
			xtype : 'button',
			cls : 'trangle_bg',
			handler : function() {
				toHome();
			}
		}, {
			xtype : 'panel',
			width : '100%',
			items : [{
				xtype : 'panel',
				cls : 'con_box',
				//height : '100%',
				height : 650,
				width : '100%',
				layout : {
					type : 'hbox',
				},
				items : [{
					xtype : 'deptIndexLeftMain',
					width : '30%',
					height : 610,

				},
				 {
					xtype : 'spacer',
					height : 550,
					width : 22,
				}, {
					xtype : 'deptIndexRightMain',
					height : 620,
					width : '66%',
				}
				]
			}]

		}]
	},
	launch : function() {//组件加载完后执行方法
		console.info("Sencha.view.deptindex.DeptIndex  launch");
	}
});

