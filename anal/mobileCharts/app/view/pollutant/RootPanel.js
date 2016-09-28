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
							width: '30%',
							height:'480px',
							// style:'border:1px solid #000000',
							scrollable : {
								direction : 'vertical',
								directionLock : true
							},
							items:[{
								xtype : 'button',
								width : '100%',
								cls : 'btn_con3',
								// pressed : isPressed,
								// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
								html : '<em class="square_dept1"></em><div style="position:absolute;left:44px;top:10px;">农作物</div>',
							},{
								xtype : 'button',
								width : '100%',
								cls : 'btn_con3',
								// pressed : isPressed,
								// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
								html : '<em class="square_dept2"></em><div style="position:absolute;left:44px;top:10px;">固废</div>',
							},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept3"></em><div style="position:absolute;left:44px;top:10px;">土壤</div>',
								},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept4"></em><div style="position:absolute;left:44px;top:10px;">室内空气</div>',
								},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept5"></em><div style="position:absolute;left:44px;top:10px;">家庭饮用水</div>',
								},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept6"></em><div style="position:absolute;left:44px;top:10px;">废气</div>',
								},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept3"></em><div style="position:absolute;left:44px;top:10px;">废水</div>',
								},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept1"></em><div style="position:absolute;left:44px;top:10px;">水产品</div>',
								},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept2"></em><div style="position:absolute;left:44px;top:10px;">沉积物</div>',
								},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept3"></em><div style="position:absolute;left:44px;top:10px;">环境水</div>',
								},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept4"></em><div style="position:absolute;left:44px;top:10px;">环境空气</div>',
								},
								{
									xtype : 'button',
									width : '100%',
									cls : 'btn_con3',
									// pressed : isPressed,
									// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
									html : '<em class="square_dept5"></em><div style="position:absolute;left:44px;top:10px;">生物样</div>',
								}
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
											xtype: 'panel',
											height:'200px',
											width:'100%',
											scrollable: {
												direction : 'vertical',
												directionLock : true,
												width: "100%"
											},
											items: [
												{

													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">苯乙酸</div>',
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">丙酮</div>',
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">臭气浓度</div>',
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">臭氧</div>',
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">丁醇平行</div>',
												}

											],
										}, {
											xtype: 'panel',
											height:'200px',
											width:'100%',
											scrollable: {
												direction : 'vertical',
												directionLock : true,
												width: "100%"
											},
											items: [
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">总数</div>',
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">最大</div>',
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">最小</div>',
												},
												{
													xtype : 'button',
													width : '60%',
													cls : 'btn_con3',
													// pressed : isPressed,
													// id : 'caseInfoBtn_' + _data.pid, // <em class="square_disc_index' + _barNum + '"></em>
													html : '<em class="square_dept"></em><div style="position:absolute;left:44px;top:10px;">平均</div>',
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
