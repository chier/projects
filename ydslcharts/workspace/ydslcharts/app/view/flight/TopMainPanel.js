Ext.define("Sencha.view.flight.TopMainPanel", {
	extend : 'Ext.Panel',
	xtype : 'FlightTopMainPanel',

	config : {
		width : '100%',
		layout : {
			type : 'hbox',
			align : 'stretch'
		},
		height : 350,
		items : [{
			xtype : 'spacer',
			width : '1px'
		}, {
			xtype : 'panel',
			width : '32.5%',
			items : [{
				style : 'margin-top:8px;',
				html : '<div class="x-tabbar-light" style="text-align:center">机票总额</div>',
				docked : 'top',
				layout : {
					pack : 'end'
				}
			}, {
				xtype : 'FlightTotalLineChart'
			}]
		},{
			xtype : 'spacer',
			width : '5px'
		}, {
			xtype : 'panel',
			width : '32.5%',
			items : [{
				//cls:'default-tabPanel-head',
				style : 'margin-top:8px;',
				html : '<div class="x-tabbar-light" style="text-align:center">出票量 </div>',
				docked : 'top',
				layout : {
					pack : 'end'
				}
			}, {
				xtype : 'FlightOrdersLineChart',

			}]
		}, {
			xtype : 'spacer',
			width : '5px'
		}, {
			xtype : 'panel',
			width : '32.5%',
			items : [{
				//cls:'default-tabPanel-head',
				style : 'margin-top:8px;',
				html : '<div class="x-tabbar-light" style="text-align:center">退废量 </div>',
				docked : 'top',
				layout : {
					pack : 'end'
				}
			}, {
				xtype : 'CancelOrdersLineChart'
			}]

		}]
	}
});
