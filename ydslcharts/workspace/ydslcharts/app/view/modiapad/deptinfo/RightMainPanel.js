Ext.define("Sencha.view.modiapad.deptinfo.RightMainPanel", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.modiapad.deptindex.RightTopPanel', 'Sencha.view.modiapad.deptinfo.DataList'],
	xtype : 'DeptInfoRightMain',
	config : {
		id : "deptInfoRightMain",
		cls : 'right_con',
		style : 'margin:9px 3px;',
		layout : {
			type : 'vbox'
		},
		items : [{
			// xtype : 'DeptIndexRightTopPanel',
			xtype : 'panel',
			layout : 'fit',
			height : 230,
			width : '96%',
			//			height:'46%',
			style : '-webkit-border-radius: 10px;margin:10px 10px 0 10px;border:1px solid #b5bac0;',
			tabBar : {
				dock : 'top',
				cls : 'default-tabPanel-head',
				layout : {
					pack : 'end'
				}
			},
			cardSwitchAnimation : {
				type : 'slide'
			},
			items : [{
				xtype : 'panel',
				html : '<div class="right_box1">' + '<h2 class="right_title">' + '<div class="f_left fb">部 门：<span id="rightDeptName">部门</span>&nbsp;&nbsp;&nbsp;<span id="rightType">机票+酒店</span></div>' + '</h2>' + '<div class="r_nr"><canvas id ="demo" width="728" height="180"></canvas></div>'// <img src="images/map.png">
				+ '</div>',
			}]

		}
		/*, 
		{
			animate : true,
			xtype : 'spacer',
			width : '96%',
			//	width : '0.5%',
			height : 6,
			//style : 'background-color:#999'
		}*/, {
			xtype : 'DeptInfoDataList',
			width : '96%',
			height : 190,
		}]
	}
});

