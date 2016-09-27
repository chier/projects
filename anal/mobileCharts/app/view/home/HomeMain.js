/**
 * 首页，用于区分
 */
Ext.define("Sencha.view.home.HomeMain", {
			extend : "Ext.Container",
			config : {
				tabBarPosition : 'bottom',
				id : "homeMain",
				fullscreen : true,
				layout : {
					type : 'vbox' // 垂直框（简称立式机箱）的布局
				},
				items : [{
							xtype : 'HomeTopPanel',
							id : 'HomeTopPanel'
						}, {
							id : 'rootMain',
							layout : 'card',

							layout : {
								type : 'card',
								animation : Ext.os.is.Android ? null : {
									type : 'slide', // Cover, Fade, Flip, Pop,
									// Reveal, Scroll, Slide,
									direction : 'left'
								}
							},
							height : 768,
							items : [{
										xtype : 'BasicInfoRootPanel',
										height : 768,
										id : 'BasicInfoRootPanel'

									}, {
										xtype : 'CaseStatisticsRootPanel',
										height : 768,
										id : 'CaseStatisticsRootPanel'

									}, {
										xtype : 'ComprehensiveRootPanel',
										height : 768,
										id : 'ComprehensiveRootPanel'

									}, {
										xtype : 'SurveyRootPanel',
										height : 768,
										id : 'SurveyRootPanel'

									}, {
										xtype : 'SurveyDataRootPanel',
										height : 768,
										id : 'SurveyDataRootPanel'

									},{
										xtype:'CaseInfoRootPanel',
										height:768,
										id:'CaseInfoRootPanel'
									},{
										xtype:'PollutantRootPanel',
										height:768,
										id:'PollutantRootPanel'
									}
								]
						}]
			}

		});
