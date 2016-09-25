/**
 * 试点基本情况页面
 */
Ext.define("Sencha.view.caseInfo.RootPanel", {
			extend : 'Ext.Panel',
			xtype : 'CaseInfoRootPanel',
			config : {
				cls : '',
				width : '100%',
				layout : {
					type : 'vbox' // 整体布局，是上下布局，主要是为了增加返回按扭
				},
				items : [{
							//返回按钮
							xtype : 'button',
							cls : 'trangle_bg',
							handler : function() {
								toCaseStatisticsIndex();
								Global.CaseStatisticsController.initData();
							}
						}, {
							xtype : 'panel',
							width : '100%',
							items : [{
										xtype : 'panel',
										cls : 'con_box',
										// height : '100%',
										height : 680,
										width : '100%',
										layout : {
											type : 'hbox'
										},
										items : [{
													xtype : 'caseInfoLeftMain',
													width : '30%',
													height : 680

												}, {
													xtype : 'spacer',
													height : 550,
													width : 22
												}, {
													xtype : 'caseInfoRightMain',
													height : 680,
													width : '66%'
												}]
									}]

						}]
			},
			launch : function() {// 组件加载完后执行方法
//				console.info("Sencha.view.deptindex.DeptIndex  launch");
			}
		});
