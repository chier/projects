Ext.define("Sencha.view.modiapad.deptinfo.DeptInfo", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.modiapad.deptinfo.LeftMainPanel', 'Sencha.view.modiapad.deptinfo.RightMainPanel'],
	xtype : 'DeptInfo',
	config : {
		id : "deptInfoView",
		// tabBarPosition : 'bottom',
		cls : 'con_box',
		width : '100%',
		//html : '<span onclick="toHome();" class="trangle_bg"></span>', // trangle_bg_on 选中状态
		// fullscreen : true,
		// style : 'margin:15px 5px 15px 5px;',
		layout : {
			type : 'vbox'
		},
		items : [{
			xtype : 'button',
			cls : 'trangle_bg',
			handler : function() {
				toBackPage();
			}
		}, {
			xtype : 'panel',
			width : '100%',
			items : [{
				xtype : 'panel',
				cls : 'con_box3',
				//height : '100%',
				height : 470,
				width : '100%',
				layout : {
					type : 'hbox',
					align : 'stretch'
				},
				items : [{
					xtype : 'DeptInfoLeftMain',
					//style:'border:2px solid red',
					width : '37%',
					height : 460,
				}, {
					xtype : 'spacer',
					width : 15,
					// style:'border:2px solid red',
					// cls : 'default-background-Color'
				}, {
					xtype : 'DeptInfoRightMain',
					width : '60%',
					height : 446,
					//style:'border:2px solid red',
				}]
			}]

		}]
	},
	launch : function() {//组件加载完后执行方法
		console.info("Sencha.view.deptindex.DeptIndex  launch");
	}
});

