Ext.define("Sencha.view.modiapad.LeftMainPanel", {
			extend : 'Ext.Panel',
			requires : [
				'Sencha.view.modiapad.LeftTopPanel',
				'Sencha.view.modiapad.LeftBottomPanel',
			],
			xtype : 'LeftMainPanel',
			config : {
			    scroll : false, 
			   // margin : '10px 0px 10px 5px' , 				
			    tabBarPosition: 'bottom',
	            
	            layout: {                
		             type: 'vbox',
		             align: 'center'
	            },            
	            items: [
	            		{
							xtype  :  'spacer',
							height : 6,
							width  : '100%'
						},
                    	{xtype : 'LeftTopPanel',width:'100%',height:170},	
						{
							xtype : 'panel',
							cls:'line_t',
							
						},
						{
							xtype  :  'spacer',
							height : 2,
							width  : '100%'
						},
                    	{xtype : 'LeftBottomPanel',width:'100%'}
                    				
	            ] 
			}
		});
