Ext.define("Sencha.view.caseInfo.RightTopPanel", {
	extend : "Ext.tab.Panel",
	xtype : 'caseInfoRightTopPanel',
	requires : [
		'Ext.tab.Panel',
		'Sencha.view.caseInfo.LineChart',
		'Sencha.view.caseInfo.BarChart'
//		'Sencha.view.deptindex.DeptIndexLineChart',
//		'Sencha.view.deptindex.DeptIndexBarChart'
	],
	config : {
//		id : 'caseInfoRightTopPanel',
		cls : 'righttop',
		scroll : false,
		defaults : {
			styleHtmlContent : true,
			iconMask : true
		},
		width : '100%',
		tabBar : {
			//cls:'default-tabPanel-head',
			style : 'margin-top:4px;width:100%;',
			html : '<div style="position:absolute;left:0px;top:0px;color:black">&nbsp;<span id="deptIndexDeptName">试点详情</span></div>',
			docked : 'top',
			layout : {
				pack : 'end'
			}
		},
		ui : 'light',
		cardSwitchAnimation : {
			type : 'slide'
		},
		layout: {
            type: 'card',
			animation: Ext.os.is.Android ? null :{
					type : 'slide',
					direction: 'left'
				}
			
        },
		items : [{
//			xtype:'panel',
//			html:'那里出来问题了？ 1'
			itemId : 'deptIndex_2',
			title : '<div><span class="title-zhuzhuangtu"></span></div>',
			xtype : 'CaseInfoBarChart'
//			xtype:'DeptIndexBarChart'
		}, {
//			xtype:'panel',
//			html:'那里出来问题了？ 2'
			itemId : 'deptIndex_1',
			title : '<div><span class="title-zhexiantu"></span></div>',
			xtype : 'CaseInfoLineChart'
//			xtype : 'DeptIndexLineChart'
		}],
		listeners : {
			painted : function(panel) {

				var bar = panel.getTabBar();
				if(bar.getItems().length === 2)
				bar.insert(3, {
					xtype : 'segmentedbutton',
					id : 'caseInfoRightTopButton',
					allowDepress : true,
					items : [{
						iconMask : true,
						cls : 'r_btn2',
						style:'width:109px;',
						// text : '机票',
						id : 'caseInfoFlightButton',
						html : '<div><em class="icon_7"></em><span id="bar_btn_1">居民健康</span></div>',
						pressed : true,
						handler : function() {
							Global.CaseInfoController.onDetailChart(1);
						}
					}, {
						iconMask : true,
						cls : 'r_btn2',
						style:'width:109px;',
						html : '<div><em class="icon_6"></em><span id="bar_btn_2">环境调查</span></div>',
						// text : '',
						id : 'caseInfoHotelButton',
						handler : function() {
							Global.CaseInfoController.onDetailChart(2);
						}
					}]
				});

			}
		}
	}
});
