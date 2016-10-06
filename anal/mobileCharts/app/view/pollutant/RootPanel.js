/**
 * 污染物分析 rootPanel页面
 */
Ext.define("Sencha.view.pollutant.RootPanel", {
			extend : 'Ext.Panel',
			xtype : 'PollutantRootPanel',
			config : {
				width : '100%',
				style:'margin-left:0pz',
				layout : {
					type : 'vbox' // 整体布局，是上下布局，主要是为了增加返回按扭
				},
				items : [
					{
						xtype : 'panel',
						// style:"border:1px solid red",
						height:'48px',
						items:[{
							// xtype:'panel',
							// html:'上显示',
							xtype : 'PollutantTopPilotPanel'
						}]
					},
					{
						xtype : 'panel',
						width:'100%',
						height:'480px',

						layout:{
							type:'hbox'// 左右布局
						},
						items:[{
							xtype: 'panel',
							id:'sampleTypePanel',
							width: '30%',
							height:'480px',
							// style:'border:1px solid #000000',
							scrollable : {
								direction : 'vertical',
								directionLock : true
							},
							items:[
							//	{
							//	xtype : 'button',
							//	width : '100%',
							//	cls : 'btn_con3',
							//	// pressed : isPressed,
							//	// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//	html : '<em class="square_dept1"></em><div style="position:absolute;left:44px;top:10px;">农作物</div>',
							//},{
							//	xtype : 'button',
							//	width : '100%',
							//	cls : 'btn_con3',
							//	// pressed : isPressed,
							//	// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//	html : '<em class="square_dept2"></em><div style="position:absolute;left:44px;top:10px;">固废</div>',
							//},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3',
							//		// pressed : isPressed,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept3"></em><div style="position:absolute;left:44px;top:10px;">土壤</div>',
							//	},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3',
                             //       pressed : true,
							//		// pressed : isPressed,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept4"></em><div style="position:absolute;left:44px;top:10px;">室内空气</div>',
							//	},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3 x-button-pressing',
							//		 pressed : true,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept5"></em><div style="position:absolute;left:44px;top:10px;">家庭饮用水</div>',
                             //       handler : function(button) {
                             //           console.info(button);
                             //           var _cls = button.getCls();
                             //           console.info(_cls);
                            //
                             //           console.info(button.config.pressed);
                             //           //var buttonvar = button.getItemId();
                             //           //var deptCode = buttonvar.substring(buttonvar.lastIndexOf("_") + 1);
                             //           //deptIndexToDeptInfo(deptCode);
                             //       }
							//	},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3',
							//		// pressed : isPressed,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept6"></em><div style="position:absolute;left:44px;top:10px;">废气</div>',
							//	},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3',
							//		// pressed : isPressed,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept3"></em><div style="position:absolute;left:44px;top:10px;">废水</div>',
							//	},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3',
							//		// pressed : isPressed,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept1"></em><div style="position:absolute;left:44px;top:10px;">水产品</div>',
							//	},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3',
							//		// pressed : isPressed,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept2"></em><div style="position:absolute;left:44px;top:10px;">沉积物</div>',
							//	},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3',
							//		// pressed : isPressed,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept3"></em><div style="position:absolute;left:44px;top:10px;">环境水</div>',
							//	},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3',
							//		// pressed : isPressed,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept4"></em><div style="position:absolute;left:44px;top:10px;">环境空气</div>',
							//	},
							//	{
							//		xtype : 'button',
							//		width : '100%',
							//		cls : 'btn_con3',
							//		// pressed : isPressed,
							//		// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
							//		html : '<em class="square_dept5"></em><div style="position:absolute;left:44px;top:10px;">生物样</div>',
							//	}
							]
						}, {
							xtype : 'spacer',
							height : 550,
							width : 22
						},{
							xtype:'panel',
							width:'66%',

							layout:{
								type:'vbox'
							},
							items:[{
								xtype:'panel',
								// html:'图表显示部分',
								// style:'border:1px solid rgb(218, 243, 14)',
								height:'300px',
								items:[
									{
										xtype : 'pollutantRightTabPanel',
										style : 'margin-top:10px;margin-bottom:0px;margin-left:14px;',
										width : '97%',
										height : 260,
										id : 'pollutantRightTabPanel'
									}
								]
							},{
								xtype:'panel',
								height:'200px',
								items:[
									{
										xtype: 'panel',
										width: '50%',
										layout: {
											type: 'hbox'
										},
										items: [{
											xtype: 'pollutantButtonPanel',
											id:'pollutantButtonPanelId',
											height:'200px',
											width:'100%',
											scrollable: {
												direction : 'vertical',
												directionLock : true,
												width: "100%"
											},
											items: [
												//{
                                                //
												//	xtype : 'button',
												//	//width : '60%',
												//	height : 50,
												//	//cls : 'btn_con3',
												//	style:'background-image:none;border-radius:0;background:#DDE0E2;',
												//	// pressed : isPressed,
												//	// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
												//	//html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">苯乙酸</div>',
												//	html:'<span  class="num_w1" style="position:absolute;left:20px;" >苯乙酸</span><div style="position:absolute;left:118px;">单位</div>' +
												//	'<span class="pro_nr"></span>'
												//},
												//{
												//	xtype : 'button',
												//	width : '60%',
												//	cls : 'btn_con3',
												//	// pressed : isPressed,
												//	// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
												//	html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">丙酮</div>',
												//},
												//{
												//	xtype : 'button',
												//	width : '60%',
												//	cls : 'btn_con3',
												//	// pressed : isPressed,
												//	// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
												//	html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">臭气浓度</div>',
												//},
												//{
												//	xtype : 'button',
												//	width : '60%',
												//	cls : 'btn_con3',
												//	// pressed : isPressed,
												//	// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
												//	html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">臭氧</div>',
												//},
												//{
												//	xtype : 'button',
												//	width : '60%',
												//	cls : 'btn_con3',
												//	// pressed : isPressed,
												//	// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
												//	html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">丁醇平行</div>',
												//}
											],
										}, {
											xtype: 'panel',
											id:'algorithmPanelId',
											height:'200px',
											width:'100%',
											scrollable: {
												direction : 'vertical',
												directionLock : true,
												width: "100%"
											},
											//listeners:{
											//	launch : function() {// 组件加载完后执行方法
											//		console.info("算法事件加载完成");
											//	},
											//},
											items: [
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3 x-button-pressing',
													_myPressed : true,
													_codeValue:'count',
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">总数</div>',
													handler: function (button) {
														Ext.getCmp("algorithmPanelId").items.each(function(item) {
															console.info(item);
															if(button.config._codeValue != item.config._codeValue){
																if(item.config._myPressed == true){
																	item.config._myPressed = false;
																	item.setCls("btn_con3");
																}
															}
														});

														if(button.config._myPressed == false){ // 当 false时，表示按钮是非按下状态，改成按下状态，存储样品类别数据
															Global.PollutantController.config.algorithm = button.config._codeValue;
															button.config._myPressed = true;
															button.setCls("btn_con3 x-button-pressing");
														}
														Global.PollutantController.ajaxGetDetectData();
													}
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													_myPressed : false,
													_codeValue:'max',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">最大</div>',
													handler: function (button) {
														Ext.getCmp("algorithmPanelId").items.each(function(item) {
															console.info(item);
															if(button.config._codeValue != item.config._codeValue){
																if(item.config._myPressed == true){
																	item.config._myPressed = false;
																	item.setCls("btn_con3");
																}
															}
														});

														if(button.config._myPressed == false){ // 当 false时，表示按钮是非按下状态，改成按下状态，存储样品类别数据
															Global.PollutantController.config.algorithm = button.config._codeValue;
															button.config._myPressed = true;
															button.setCls("btn_con3 x-button-pressing");
														}
														Global.PollutantController.ajaxGetDetectData();
													}
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													_myPressed : false,
													_codeValue:'min',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">最小</div>',
													handler: function (button) {
														Ext.getCmp("algorithmPanelId").items.each(function(item) {
															console.info(item);
															if(button.config._codeValue != item.config._codeValue){
																if(item.config._myPressed == true){
																	item.config._myPressed = false;
																	item.setCls("btn_con3");
																}
															}
														});

														if(button.config._myPressed == false){ // 当 false时，表示按钮是非按下状态，改成按下状态，存储样品类别数据
															Global.PollutantController.config.algorithm = button.config._codeValue;
															button.config._myPressed = true;
															button.setCls("btn_con3 x-button-pressing");
														}
														Global.PollutantController.ajaxGetDetectData();
													}
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													_myPressed : false,
													_codeValue:'avg',
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">平均</div>',
													handler: function (button) {
														Ext.getCmp("algorithmPanelId").items.each(function(item) {
															console.info(item);
															if(button.config._codeValue != item.config._codeValue){
																if(item.config._myPressed == true){
																	item.config._myPressed = false;
																	item.setCls("btn_con3");
																}
															}
														});

														if(button.config._myPressed == false){ // 当 false时，表示按钮是非按下状态，改成按下状态，存储样品类别数据
															Global.PollutantController.config.algorithm = button.config._codeValue;
															button.config._myPressed = true;
															button.setCls("btn_con3 x-button-pressing");
														}

														Global.PollutantController.ajaxGetDetectData();
													}
												}
											]
										}
										]
									}
								]
							}]

						}]
					}
				]
			}
		});
