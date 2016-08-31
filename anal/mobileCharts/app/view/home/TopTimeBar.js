Ext.define('Sencha.view.home.TopTimeBar', {
	extend : 'Ext.Toolbar',
	xtype : 'HomeTopTimeBar',

	config : {
		id : 'timeBar',
		layout : 'hbox',
		docked : 'top',
		baseCls : 'timeBar',
		items : [{
			xtype : 'panel',
			html : '<span class="logo_font">环境与健康移动分析展示系统</span>'
		}, {
			xtype : 'spacer'
		}, {
			xtype : 'panel',
			html : '<span class="logo_title" id="logo_title"></span>'
		}, {
			xtype : 'spacer'
		}, {
			xtype : 'segmentedbutton',
			id : 'topTimeBar_button',
			allowDepress : false,
			baseCls : 'timeBar-base-segmentedbutton',
			pressedCls : 'nav_on',
			items : []
		}, {
			xtype : 'spacer',
			width : '2%'
		}, {
			xtype : 'panel',
			style : 'font-size:0',
			layout : {
				type : 'hbox',
				align : 'stretch'
			},
			items : [{
//				xtype : 'button',
//				cls : 'about_icon',
//				id : 'btn1',
//				pressedCls : 'about_icon2',
//				handler : function(button) {
//					updateInfo3()
//				}
			}, {
//				xtype : 'button',
//				cls : 'refresh_icon',
//				id : 'btn2',
//				pressedCls : 'refresh_icon2',
//				handler : function(button) {
//					refreshMethod();
//				}
			}]
		}]
	}
});
