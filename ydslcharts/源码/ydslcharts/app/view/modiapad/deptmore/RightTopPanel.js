Ext.define("Sencha.view.modiapad.deptmore.RightTopPanel", {
	extend : "Ext.tab.Panel",
	xtype : 'DeptMoreRightTopPanel',
	requires : [
		'Ext.tab.Panel',
		'Sencha.view.modiapad.deptmore.DeptMoreLineChart',
		'Sencha.view.modiapad.deptmore.DeptMoreBarChart'
	],
	config : {
		id : 'deptMoreRightTopPanel',
		cls : 'righttop',
		//				ui : 'round',
		ui : "dark",
		scroll : false,
		margin : '10px 10px 10px 10px',
		//				tabBarPosition: 'bottom',
		defaults : {
			styleHtmlContent : true,
			iconMask : true
		},
		layout: {
            type: 'card',
			animation: Ext.os.is.Android ? null :{
					type : 'slide',
					direction: 'left'
				}
			
        },
		tabBar : {
			style : 'margin-top:4px;',
			html : '<div style="position:absolute;left:0px;top:0px;color:black">部门：<span id="deptMoreDeptName">其它</span></div>',
			docked : 'top',
			layout : {
				pack : 'end'
			}
		},
		ui : 'light',
		cardSwitchAnimation : {
			type : 'slide'
		},
		items : [{
			//iconMask : true,
			//							title : '柱状图',
			//iconCls : 'zhuzhuangtu',
			title:'<div><span class="title-zhuzhuangtu"></span></div>',
			itemId : 'deptMore_2',
			xtype : 'DeptMoreBarChart'
		},{
			//iconMask : true,
			//							title : '线状图',
			itemId : 'deptMore_1',
			//iconCls : 'zhexiantu',
			title:'<div><span class="title-zhexiantu"></span></div>',
			xtype : 'DeptMoreLineChart'
		}],
		listeners : {
			painted : function(panel) {
				var bar = panel.getTabBar();
			}
		}
	}
});
