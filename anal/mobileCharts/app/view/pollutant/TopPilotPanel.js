Ext.define('Sencha.view.pollutant.TopPilotPanel', {
	xtype : 'PollutantTopPilotPanel',
	extend : 'Ext.Panel',
	id : 'content',
	dock : "top",
	width : '100%',
	height : '15%',
	config : {
		layout : {
			type : 'hbox',
			pack : 'start',
			align : 'top'
		},
		height : 48,
		baseCls : 'time-base-segmentedbutton',
		scrollable : {
			direction : 'horizontal',
			directionLock : true,
			width:"100%"
		},
		items : [{
			id : 'buttomPilotButn',
			xtype : 'segmentedbutton',
            allowMultiple : true,
			items:[
                //{ text: '河北', pressed: true },
                //{ text: '山西', pressed: true },
                //{ text: '内蒙' },
                //{ text: '辽宁' },
                //{ text: '吉林' },
                //{ text: '江苏' },
                //{ text: '浙江' },
                //{ text: '福建' },
                //{ text: '江西' },
                //{ text: '广东' },
                //{ text: '山东' },
                //{ text: '湖南' },
                //{ text: '湖北' },
                //{ text: '重庆' },
                //{ text: '贵州' },
                //{ text: '云南' },
                //{ text: '陕西' },
                //{ text: '甘肃' },
                //{ text: '新疆' },
			],
			listeners: {
				toggle: function(container, button, pressed){
					console.info(button);
					if(pressed == true){ // 当== true时，表示该按钮按下状态,按下状态时，将按钮的值存储到数组中
						Global.PollutantController.config.pilots.push(button.config._code);
					}else{  //当 == false时，表示该按钮处于释放状态，将按钮存储在数组中的值取消掉
						var _arr = Global.PollutantController.config.pilots;
						for(var i= 0;i<_arr.length;i++){
							if(button.config._code == _arr[i]){
								_arr.splice(i,1);
							}
						}
						Global.PollutantController.config.pilots = _arr;
					}
					//var _arr = Global.PollutantController.config.pilots;
					//for(var i=0;i<_arr.length;i++){
					//	console.info(_arr[i]);
					//}
					Global.PollutantController.ajaxGetSampletype();
					//console.info("User toggled the '" + button.getText() + "' button: " + (pressed ? 'on' : 'off'));
				}
			}
		}]
	}
});
