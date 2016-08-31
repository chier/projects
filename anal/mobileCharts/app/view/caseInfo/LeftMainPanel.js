Ext.define("Sencha.view.caseInfo.LeftMainPanel", {
			extend : "Ext.Panel",
			requires : ['Sencha.view.caseInfo.LeftPicPanel',
					'Sencha.view.caseInfo.LeftButtonPanel'],
			xtype : 'caseInfoLeftMain',
			config : {
				id : "caseInfoLeftMain",
				// tabBarPosition : 'bottom',
				width : '24%',
				// height : '90%',
				layout : 'fit',
				style : 'margin:15px 5px 15px 5px;',
				layout : {
// type : 'fit'
				},
				items : [{
							xtype : 'panel',
							height : 244,
							width : '100%',
							layout : {
								type : 'hbox',
								align : 'stretch'
							},
							items : [{
										xtype : 'caseInfoLeftPicPanel'
									}]
						}, {
							xtype : 'caseInfoLeftButtonPanel',
							id : 'caseInfoLeftButtonPanel'
						}]
			},
			launch : function() {// 组件加载完后执行方法
				console.info("Sencha.view.caseInfo.caseInfo  launch");
			}
		});
