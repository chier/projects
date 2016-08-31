Ext.define("Sencha.view.caseStatistics.LeftMainPanel", {
			extend : 'Ext.Panel',
			xtype : 'CaseStatisticsLeftMainPanel',
			config : {
			    scroll : false, 
			    margin : '10px 0px 10px 5px' , 				
			    tabBarPosition: 'bottom',
	            height: 650,
	            layout: {                
		             type: 'vbox',
		             align: 'center'
	            },            
	            items: [
	            		
                    	{xtype : 'CaseStatisticsLeftTopPanel',width:'100%',height:200},	
						{
							xtype : 'panel',
							cls:'line_t'
							
						},
						{
							xtype  :  'spacer',
							height : 30,
							width  : '100%'
						},
                    	{xtype : 'CaseStatisticsLeftBottomPanel',width:'100%'}			
	            ] 
			}
		});
