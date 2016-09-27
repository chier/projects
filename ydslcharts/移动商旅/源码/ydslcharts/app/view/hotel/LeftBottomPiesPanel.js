Ext.define("Sencha.view.hotel.LeftBottomPiesPanel", {
	extend : 'Ext.Panel',
    xtype : 'HotelLeftBottomPiesPanel',
	config : {
        layout: {
                type: 'vbox',
                align: 'stretch'
        },
        defaults:{
            flex:1,
            layout:{
            	type: 'hbox',
                align: 'stretch'
            },
            defaults:{
            	xtype: 'chart',
                flex: 2,
                insetPadding: 20
            }
        },
		items : [{
					//html:'<table align="center" width="100%" height="100%"><tr><td width="45%" align="center"><div id="xingji" onclick="getDataByType(\'star\');"/   onMouseDown="pressStyle(\'xingji\');" onMouseOut="outStyle(\'xingji\')"  ></td><td width="45%" align="center"><div id="ruzhudi" onclick="getDataByType(\'city\');"/   onMouseDown="pressStyle(\'ruzhudi\');" onMouseOut="outStyle(\'ruzhudi\')"></td></tr></table>',
				    height:50,
				    width:'90%',
				    items:[
				    {
				       xtype:'spacer',
				       width:'7%'
				    },{
				    	xtype:'button',
				    	id:'xingji',
				    	width:'40%',
				    	cls:'btn_con3',
				    	html:'<div style="height:35px;line-height:35px;"><em class="t_icon4"></em><span class="f_left">星级</span></div>',
				    	handler:function(){
				    		btnChange('star');
				    		Global.MainController.getDataByStar();
				    	}
				    },{
				        xtype:'spacer',
				        width:'15%'				    
				    },{
				    	xtype:'button',
				    	id:'ruzhudi',
				    	cls:'btn_con3_on',
				    	html:'<div style="height:35px;line-height:35px;"><em class="t_icon5"></em><span class="f_left">入住地</span></div>',
				    	width:'40%',
				    	handler:function(){
				    		btnChange('city');
				    		Global.MainController.getDataByCity();
				    	}
				    }
				    ]
				 },			
				 {	 
				    height:200,
				    width:'99%',
                    items:[				
                    {xtype:'StarPiePanel'},
                    {xtype:'HotelCityPiePanel'}
				    ]
				 }
		]
	}
});