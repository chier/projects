Ext.define("Sencha.view.deptindex.RightTopPanel", {
	extend : "Ext.tab.Panel",
	xtype : 'DeptIndexRightTopPanel',
	requires : [
		'Ext.tab.Panel',
		'Sencha.view.deptindex.DeptIndexLineChart',
		'Sencha.view.deptindex.DeptIndexBarChart'
	],
	config : {
		id : 'deptIndexRightTopPanel',
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
			html : '<div style="position:absolute;left:0px;top:0px;color:black">部门：<span id="deptIndexDeptName">其它</span></div>',
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
			//iconMask : true,
			//							title : '柱状图',
			//iconCls : 'zhuzhuangtu',
			itemId : 'deptIndex_2',
			title : '<div><span class="title-zhuzhuangtu"></span></div>',
			xtype : 'DeptIndexBarChart',
			//style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;border:1px solid gray'
		}, {
			//iconMask : true,
			//							title : '线状图',
			itemId : 'deptIndex_1',
			//iconCls : 'zhexiantu',
			title : '<div><span class="title-zhexiantu"></span></div>',
			xtype : 'DeptIndexLineChart',
			//style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;border:1px solid gray'
		}],
		listeners : {
			painted : function(panel) {

				var bar = panel.getTabBar();
				if(bar.getItems().length === 2)
				bar.insert(3, {
					xtype : 'segmentedbutton',
					id : 'deptIndexRightTopButton',
					allowDepress : true,
					items : [{
						iconMask : true,
						cls : 'r_btn2',
						// text : '机票',
						id : 'deptIndexFlightButton',
						html : '<div><em class="icon_7"></em><span class="f_left">机票</span></div>',
						pressed : true,
						handler : function() {
							Global.DeptIndexController.tapDeptIndexFlightButton();
						}
					}, {
						iconMask : true,
						cls : 'r_btn2',
						html : '<div><em class="icon_6"></em><span class="f_left">酒店</span></div>',
						// text : '',
						id : 'deptIndexHotelButton',
						handler : function() {
							Global.DeptIndexController.tapDeptIndexHotelButton();
						}
					}]
				});

			}
		}
	}
});
