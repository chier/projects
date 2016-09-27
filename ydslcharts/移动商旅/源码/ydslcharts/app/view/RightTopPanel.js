Ext.define("Sencha.view.RightTopPanel", {
	extend : "Ext.tab.Panel",
	xtype : 'RightTopPanel',
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
			html : '<div id="indexSaleTitle" style="position:absolute;left:0px;top:0px;color:black">机票+酒店 </div>',
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
			xtype : 'BarChart',
			style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;border:1px solid gray'
		},{
			title : '<div><span class="title-zhexiantu"></span></div>',
			//iconMask: true,
			itemId : '1',
			//iconCls : 'zhexiantu',
			xtype : 'LineChart',
			style : 'background:#f0f0f0;margin-top:0px;margin-bottom:0px;margin-left:14px;border:1px solid gray'
		}],
		listeners : {
			painted : function(panel) {
				var bar = panel.getTabBar();
				if(bar.getItems().length === 2){
					bar.insert(3, {
						xtype : 'segmentedbutton',
						allowDepress : true,
						items : [
						{
							pressed:true,
							iconMask : true,
							cls : 'r_btn2',
							height : 25,
							width : 80,
							html : '<div><em class="icon_7"></em><span class="f_left">金额</span></div>',
							handler : function() {
								globalIndexLineBtn = 2;
								Global.MainController.totalMoneyIndex(globalTimeScope, globalTimeNumber, globalIndexBtn, 2);
							}
						},{
							iconMask : true,
							cls : 'r_btn2',
							height : 25,
							width : 80,
							html : '<div><em class="icon_6"></em><span class="f_left">订单</span></div>',
							handler : function() {
								globalIndexLineBtn = 1;
								Global.MainController.totalMoneyIndex(globalTimeScope, globalTimeNumber, globalIndexBtn, 1);
							}
						}]
					});
				}

			}
		}
	}
});
