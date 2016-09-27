Ext.define("Sencha.view.modiapad.deptinfo.LeftMainPanel", {
	extend : "Ext.Panel",
	requires : ['Sencha.view.modiapad.deptinfo.LeftButtonPanel'],
	xtype : 'DeptInfoLeftMain',
	config : {
		id : "deptInfoLeftMain",
		// tabBarPosition : 'bottom',
		//height : '90%',
		layout : 'fit',
		// fullscreen : true,
		layout : {
			//	type : 'fit'
		},
		style : 'margin:10px 5px 15px 5px;',
		items : [{
			xtype : 'button',
			width : '99%',
			height : "8%",
			cls : 'detail_box2',
			style : 'border-bottom:0px;border-radius:0.6em 0.6em 0 0;margin-bottom:0px;',
			html : '<div class="f_left">总 额： <span id="detpTotal">0</span></div><span class="pro_nr2"><em style="width:100%;" class="pro_bar3"></em></span>',
			handler : function() {
				Global.DeptInfoController.clickDeptAll();
			}
			//html : '<div class="detail_box2" onclick="Global.DeptInfoController.clickDeptAll();">' + '<div class="f_left">总 额： <span id="detpTotal">0</span></div><span class="pro_nr2"><em style="width:100%;" class="pro_bar1"></em></span>' + '</div>'

		},{
			xtype : 'panel',
			height : 140,
			cls : 'detail_box3',
			style : 'border-top:0px;border-radius: 0 0 0.6em 0.6em;margin-top:0px;',
			// style : 'border:2px solid red',
			width : "99%",
			layout : {
				type : 'hbox',
			},
			items : [{
				xtype : 'panel',
				//style : 'border:2px solid red',
				width : '45%',
				html : '<div style="padding:2px;"><canvas id="canvasDeptInfo" style="height:130px;border:0px solid red;"></canvas></div>',
			}, {
				xtype : 'panel',
				//style : 'border:1px solid red;',
				width : '55%',
				items : [{
					xtype : 'button',
					//style : 'border:2px solid red',
					id : 'deptinfo_c1_1',
					cls : 'clock_s2_a',
					height : 70,
					html : '<div style="width:100%;">' + '<p><div class="num_w1">机票： <span width="60%" id="deptFlightTotal">$ 0</span></div><em id="deptinfo_c1_em" class="icon_1t" style="float: right;"></em></p>' 
					+ '<p><span class="pro_nr3"><em id="emFlightTotal" class="pro_bar1" style="width:70%;"></em></span></p></div>',
					handler : function(button) {
						var handlerId = button.getItemId();
						console.info(handlerId);
						Global.DeptInfoController.clickDeptType(handlerId);
						button.replaceCls("clock_s2_a", "clock_s2_a_c_on");
					}
				}, {
					xtype : 'button',
					//style : 'border:2px solid red',
					id : 'deptinfo_c2_1',
					cls : 'clock_s2_a',
					height : 70,
					html : '<div style="width:100%">' + '<p><em id="deptinfo_c2_em" style="float: right;" class="icon_2t"></em><div class="num_w1">酒店： <span id="deptHotelTotal" width="60%">$ 0</span></div></p>' + '<p><span class="pro_nr3"><em  id="emHotelTotal" class="pro_bar2" style="width:30%;"></em></span></p></div>',
					handler : function(button) {
						button.replaceCls("clock_s2_a", "clock_s2_a_c_on");
						var handlerId = button.getItemId();
						console.info(handlerId);
						Global.DeptInfoController.clickDeptType(handlerId);
					}
				}]
			}]
		}, {
			animate : true,
			xtype : 'spacer',
			height : 12,
			width : "100%",
		}, {
			xtype : 'panel',
			id : 'leftButtonParentPanel',
			height : 201,
			width : '100%',
			cls : 'detail_table',
			flex : 1,
			items : [{
				xtype : 'panel',
				height : '10%',
				html : '<h2 style="padding:0 20px;" class="detail_title">机票+酒店</h2>'
			}//,         leftButtonPanel
			,{
				xtype : 'DeptInfoLeftButtonPanel',
			}]
		}]
	},
	launch : function() {//组件加载完后执行方法
		console.info("Sencha.view.deptindex.DeptIndex  launch");
	}
});

