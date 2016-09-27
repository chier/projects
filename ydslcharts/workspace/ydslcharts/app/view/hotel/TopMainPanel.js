Ext.define("Sencha.view.hotel.TopMainPanel", {
	extend : 'Ext.Panel',
	xtype : 'HotelTopMainPanel',
	config : {
		width : '100%',
		layout : {
			type : 'hbox',
			align : 'stretch'
		},
		height : 300,
		items : [{
			xtype : 'spacer',
			width : '1%'
		}, {
			xtype : 'panel',
			width : '48.5%',
			items : [{
				style : 'margin-top:8px;',
				html : '<div class="x-tabbar-light" style="text-align:center">酒店夜总量</div>',
				docked : 'top',
				layout : {
					pack : 'end'
				}
			}, {
				xtype : 'HotelOrdersLineChart'
			}]
		}, {
			xtype : 'spacer',
			width : '1%'
		}, {
			xtype : 'panel',
			width : '48.5%',
			items : [{
				style : 'margin-top:8px;',
				html : '<div class="x-tabbar-light" style="text-align:center">酒店总额</div>',
				docked : 'top',
				layout : {
					pack : 'end'
				}
			}, {
				xtype : 'HotelTotalLineChart'
			}]
		}]
	}
});
