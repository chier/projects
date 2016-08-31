Ext.define("Sencha.view.caseStatistics.RightTopPanel", {
	extend : "Ext.tab.Panel",
	xtype : 'CaseStatisticsRightTopPanel',
	requires : ['Ext.tab.Panel'],

	config : {
		cls : 'righttop',
		//ui : 'dark',
		scroll : false,
		defaults : {
			styleHtmlContent : true,
			iconMask : true
		},
		width : '100%',
		tabBar : {
			//cls:'default-tabPanel-head',
			style : 'margin-top:4px;margin-left:14px',
			html : '<div id="indexPilotTitle" style="position:absolute;left:0px;top:0px;color:black">试点上报数据</div>',
			docked : 'top',
			layout : {
				pack : 'end'
			}
		},
		layout: {
            type: 'card',
			animation: Ext.os.is.Android ? null :{
					type : 'slide',
					direction: 'left'
				}
			
        },
		cardSwitchAnimation : {
			type : 'slide'
		},
		ui : 'light',
		items : [{
			//iconMask: true,
			title : '<div><span class="title-zhuzhuangtu"></span></div>',
			//iconCls : 'zhuzhuangtu',
			itemId : '2',
			xtype : 'caseStatisticsBarChart',
			style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;border:1px solid gray'
		},{
			title : '<div><span class="title-zhexiantu"></span></div>',
			//iconMask: true,
			itemId : '1',
			//iconCls : 'zhexiantu',
			xtype : 'caseStatisticsLineChart',
			style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;border:1px solid gray'
		}],
		listeners : {
		}
	}
});
