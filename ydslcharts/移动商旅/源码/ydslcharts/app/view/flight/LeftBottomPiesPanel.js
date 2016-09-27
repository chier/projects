Ext.define("Sencha.view.flight.LeftBottomPiesPanel", {
	extend : 'Ext.Panel',
    xtype : 'FlightLeftBottomPiesPanel',
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
					//html:'<table align="center" width="100%"><tr><td width="33%" align="center"><div id="cangweijizhekou" onclick="getDataByType_ticket(\'CW\');"/   onMouseDown="pressStyle(\'cangweijizhekou\');" onMouseOut="outStyle(\'cangweijizhekou\')"  ></td><td width="33%" align="center"><div id="hangkonggognsi" onclick="getDataByType_ticket(\'AC\');"/   onMouseDown="pressStyle(\'hangkonggognsi\');" onMouseOut="outStyle(\'hangkonggognsi\')"></td><td width="33%" align="center"><div id="daodadi" onclick="getDataByType_ticket(\'AD\');"/   onMouseDown="pressStyle(\'daodadi\');" onMouseOut="outStyle(\'daodadi\')"></td></tr></table>',
				    height:50,
				    width:'90%',
				    items:[
				    {
				    	xtype:'spacer',
				    	width:'3%'
				    },{
				    	xtype:'button',
				    	width:'30%',
				    	cls:'btn_con3',
				    	html:'<div style="height:35px;line-height:30px;"><em class="t_icon1"></em><span class="f_left">舱&nbsp;&nbsp;&nbsp;&nbsp;位</span></div>',
				    	id:'cangweijizhekou',
				    	handler:function(){
				    		btnChange('cabin');
				    		Global.MainController.getDataByCabin();
				    	}
				    },{
				    	xtype:'spacer',
				    	width:'4%'
				    },{
				    	xtype:'button',
				    	width:'33%',
						cls:'btn_con3_on',
				    	html:'<div style="height:35px;line-height:30px;"><em class="t_icon2"></em><span class="f_left">航空公司</span></div>',
				    	id:'hangkonggognsi',
				    	handler:function(){
				    		btnChange('airline');
				    		Global.MainController.getDataByAirLine();
				    	}
				    },{
				    	xtype:'spacer',
				    	width:'4%'
				    },{
				    	xtype:'button',
				    	width:'30%',
				    	cls:'btn_con3',
				    	html:'<div style="height:35px;line-height:30px;"><em class="t_icon3"></em><span class="f_left">到达地</span></div>',
				    	id:'daodadi',
				    	handler:function(){
				    		btnChange('offpoint');
				    		Global.MainController.getDataByOffPoint();
				    	}
				    }
				    ]
				 },			
				 {	 
				    height:200,
				    width:'94%',
                    items:[				
                    {xtype:'CabinPiePanel'},
                    {xtype:'AirLinePiePanel'},
                    {xtype:'ArrivedPiePanel'}
				    ]
				 }
		]
	}
});