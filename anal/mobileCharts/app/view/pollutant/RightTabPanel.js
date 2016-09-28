Ext.define("Sencha.view.pollutant.RightTabPanel", {
	extend : "Ext.tab.Panel",
	xtype : 'pollutantRightTabPanel',
	requires : ['Ext.tab.Panel','Sencha.view.pollutant.LineChart'],
	config : {
		scroll : false,
		defaults : {
			styleHtmlContent : true,
			iconMask : true
		},
		width : '98%',
		activeItem:0,
		tabBar : {
			// cls:'default-tabPanel-head',
			html : '<div id="indexSaleTitle" style="position:absolute;left:0px;top:0px;color:black"></div>',
			docked : 'top',
			layout : {
				pack : 'end'
			}
		},
		layout : {
			type : 'card',
			animation : Ext.os.is.Android ? null : {
				type : 'slide',
				direction : 'left'
			}

		},
		cardSwitchAnimation : {
			type : 'slide'
		},
		ui : 'light',
		items : [{
			// iconMask: true,
			title : '<div style="min-width:6.0em;line-height:24px;font-size:16px;"><span>图表</span></div>',
			cls : 'tab-border-background',
			style : 'background:#f0f0f0;',
            id : 'pollutantLineChart_1',
            xtype : 'pollutantLineChart',
            height:'200px'

			// width : '100%'
		}, {
			title : '<div style="min-width:6.0em;line-height:24px;font-size:16px;"><span>列表</span></div>',
			xtype : 'panel',
            itemId:'1',
			cls : 'tab-border-background',
			style : 'background:#f0f0f0;',
			id : 'pollutantPanel',
            html:'列表',
			items : [{

            }]
		}],
		listeners : {
			bottomchange:function( a, value, oldValue, eOpts ){
				console.info(a);
				console.info(value);
				console.info(oldValue);
				console.info(eOpts);
				
			},
			painted : function(panel) {
				var bar = panel.getTabBar();
				if (bar.getItems().length === 2) {
					bar.insert(3, {
						xtype : 'button',
						id : 'pollutantLineButton',
						cls : 'btn_con3_on',
                        dataIsPressed: true,
						style : 'margin-bottom:0px;width:142px;height:16px;',
						html : '<div class="f_right" style="min-width:8.0em;font-size:16px;color:#888888;text-shadow:rgba(0, 0, 0, 0.5) 0 -0.08em 0;">样别类别分析</div>',
						handler : function() {
							// alert("样别类别分析");

                            var _pollutantLineButton = Ext
                                .getCmp("pollutantLineButton");
                            _pollutantLineButton.setCls("btn_con3_on");
                            _pollutantLineButton.config.dataIsPressed = true;

                            var _pollutantListButton = Ext.getCmp("pollutantListButton");
                            _pollutantListButton.setCls("btn_con3");
                            _pollutantListButton.config.dataIsPressed = false;
						}

					});

					bar.insert(4, {
						xtype : 'button',
						id : 'pollutantListButton',
						cls : 'btn_con3',
                        dataIsPressed: false,
                        style : 'margin-bottom:0px;width:108px;height:16px;',
						html : '<div  class="f_right" style="min-width:6.0em;font-size:16px;color:#888888;text-shadow:rgba(0, 0, 0, 0.5) 0 -0.08em 0;">试点分析</div>',
						handler : function() {
						    // alert("试点分析");

                            var _pollutantLineButton = Ext
                                .getCmp("pollutantLineButton");
                            _pollutantLineButton.setCls("btn_con3");
                            _pollutantLineButton.config.dataIsPressed = false;

                            var _pollutantListButton = Ext.getCmp("pollutantListButton");
                            _pollutantListButton.setCls("btn_con3_on");
                            _pollutantListButton.config.dataIsPressed = true;
						}
					});
				}
			}
		}
	}
});
