Ext.define("Sencha.view.deptmore.DeptMore", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.deptmore.LeftMainPanel', 'Sencha.view.deptmore.RightMainPanel'],
	xtype : 'deptMore',
	config : {
		id : "deptMoreView",
		cls : 'con_box',
		width : '100%',
		// 	html : '<span onclick="toHome();" class="trangle_bg"></span>', // trangle_bg_on 选中状态
		layout : {
			type : 'vbox'
		},
		items : [{
			xtype : 'button',
			cls : 'trangle_bg',
			handler : function() {
				toDeptIndex("more");
			}
		}, {

			xtype : 'panel',
			width : '100%',
			items : [{
				xtype : 'panel',
				cls : 'con_box3',
				//height : '100%',
				height : 700,
				width : '100%',
				layout : {
					type : 'hbox',
					align : 'stretch'
				},
				items : [{
					xtype : 'DeptMoreLeftMain',
					// style:'border:2px solid #000000;',
					height : 610,
					width : '32%',

				}, {
					xtype : 'spacer',
					//	height : '100%',
					width : 15,
					// cls : 'default-background-Color'
				}, {
					xtype : 'DeptMoreRightMain',
					// style:'border:2px solid red;',
					height : 610,
					width : '65%',
				}]
			}]

		}]
	},
	launch : function() {//组件加载完后执行方法
		console.info("Sencha.view.deptindex.DeptIndex  launch");
	}
});

