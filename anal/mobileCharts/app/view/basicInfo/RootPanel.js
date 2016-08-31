/**
 * 试点基本情况页面
 */
Ext.define("Sencha.view.basicInfo.RootPanel", {
			extend : 'Ext.Carousel',
			xtype : 'BasicInfoRootPanel',
			config : {
				id : 'RootPanel',
				fullscreen : true,
				direction : 'horizontal',
				defaults : {
					styleHtmlContent : true
				},
				cls : 'con_box',
				width : '100%',
				items : [
//				         {xtype:'panel',width:500,height:500,html:'正在加载数据中。。。'}
						{
							xtype:'panel2012_1',
							id:'panel2012_1'
						},
						{
							xtype:'panel2012_2',
							yearType:2012
						},
						{
							xtype:'panel2012_3',
							yearType:2012
						},
						{
							xtype:'panel2012_4',
							yearType:2012
						},{
							xtype:'panel2013_1',
							yearType:2013
						},
						{
							xtype:'panel2013_2',
							yearType:2013
						},
						{
							xtype:'panel2013_3',
							yearType:2013
						},
						{
							xtype:'panel2013_4',
							yearType:2013
						},
						{
							xtype:'panel2013_5',
							yearType:2013
						},{
							xtype:'panel2014_1',
							yearType:2014
						}
						]
			}
		});
