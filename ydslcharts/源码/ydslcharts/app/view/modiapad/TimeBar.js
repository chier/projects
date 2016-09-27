Ext.define('Sencha.view.modiapad.TimeBar', {
	extend : 'Ext.Toolbar',
	xtype : 'TimeBar',

	config : {
		id : 'timeBar',
		layout : 'hbox',
		docked : 'top',
		baseCls : 'timeBar',
		height: 40,
		width: '98%',
		items : [{
			xtype : 'panel',
			html : '<span class="logo_font">商旅报表</span>'
		}, {
			xtype : 'spacer'
		}, {
			xtype : 'panel',
			html : '<span class="logo_title" id="logo_title"></span>'
		}, {
			xtype : 'spacer'
		}, {
			xtype : 'segmentedbutton',
			allowDepress : false,
			baseCls : 'timeBar-base-segmentedbutton',
			pressedCls : 'nav_on',
			items : [{
				id : 'weekButton',
				cls : 'buttonItem',
				text : '周',
				pressed : true
			}, {
				id : 'monthBuuton',
				cls : 'buttonItem',
				text : '月'
			}, {
				id : 'seasonBuuton',
				cls : 'buttonItem',
				text : '季'
			}]
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
				xtype : 'button',
				cls : 'about_icon',
				id : 'btn1',
				pressedCls : 'about_icon2',
				handler : function(button) {
					updateInfo3()
				}
			}, {
				xtype : 'button',
				cls : 'refresh_icon',
				id : 'btn2',
				pressedCls : 'refresh_icon2',
				handler : function(button) {
					refreshMethod();
				}
			}]
		}]
	}
});
