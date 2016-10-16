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
						style : 'margin:15px 5px 15px 5px;text-align:left;',
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
							}
						}, {
							xtype : 'spacer',
							height : 550,
							width : 22
						},{
							xtype:'panel',
							width:'66%',
							height:'570px',
							cls : 'right_con',
							style : 'padding:15px 5px 15px 5px;',
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
											height:'250px',
											width:'100%',
											scrollable: {
												direction : 'vertical',
												directionLock : true,
												width: "100%"
											},
										}, {
											xtype: 'panel',
											id:'algorithmPanelId',
											height:'245px',
											width:'80%',
											style : 'margin-top:10px;margin-bottom:0px;margin-left:24px;',
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
													width : '80%',
													cls : 'btn_con3 x-button-pressing',
													_myPressed : true,
													_codeValue:'avg',
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">平均(Avg)</div>',
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
													width : '80%',
													cls : 'btn_con3',
													_myPressed : false,
													_codeValue:'sum',
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">总数(Sum)</div>',
													handler: function (button) {
														Ext.getCmp("algorithmPanelId").items.each(function(item) {
															//console.info(item);
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
													width : '80%',
													cls : 'btn_con3',
													_myPressed : false,
													_codeValue:'max',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">最大(Max)</div>',
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
													width : '80%',
													cls : 'btn_con3',
													_myPressed : false,
													_codeValue:'min',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">最小(Min)</div>',
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
													width : '80%',
													cls : 'btn_con3',
													// pressed : isPressed,
													_myPressed : false,
													_codeValue:'count',
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">计数(Count)</div>',
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
													width : '80%',
													cls : 'btn_con3',
													// pressed : isPressed,
													_myPressed : false,
													_codeValue:'STD',
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">标准方差(Std)</div>',
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
													width : '80%',
													cls : 'btn_con3',
													// pressed : isPressed,
													_myPressed : false,
													_codeValue:'STDDEV_SAMP',
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">样本方差(Stddev_samp)</div>',
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
