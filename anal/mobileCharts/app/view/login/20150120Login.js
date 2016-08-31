Ext.define("Sencha.view.login.Login", {
	extend : 'Ext.Panel',
	xtype : 'LoginPanel',
	config : {
		style : 'background-color:#353841;color:#1d1e22;text-shadow:0 1px 1px #fff;',
		width : '100%',
		height : '100%',
		layout : {
			type : 'vbox'
		},
		items : [{
			xtype : 'panel',
			style:'margin:0 auto;margin-top:100px;',
			width:"80%",
			items : [{
						xtype : 'panel',
						cls : 'login_logo'
					}, {
						xtype : 'panel',
						cls : 'login_con_box',
						height : 405,
						items : [{
							xtype : 'panel',
							cls : 'login_con_box2',
							height : 403,
							items : [{
										xtype : 'panel',
										cls : 'login_title',
										height : 24,
//										html : '登录'
									}, {
										xtype : 'formpanel',
										id : "login-formpanel",
										cls : 'login_con',
										height:280,
										items : [{
											xtype : 'fieldset',
											style:'margin-top:40px;',
											items : [{
														xtype : 'textfield',
														height:50,
														name : 'account',
														label : '用户名'
													}, {
														xtype : 'passwordfield',
														height:50,
														name : 'password',
														label : '密码'
													}]
										}, {
											xtype : 'button',
											height:50,
											width:"20%",
											style:'margin:0 auto;font-size:24px;',
											text : '登录',
											handler : function() {
												var _account, _password;
												var formPanel = Ext
														.getCmp("login-formpanel");
												var _jsonFromValue = JSON
														.parse(JSON
																.stringify(
																		formPanel
																				.getValues(),
																		null, 2));
												if (!_jsonFromValue) {
													Ext.Msg.alert("提示","请填写用户名和密码");
												} else {
													_account = _jsonFromValue["account"];
													_password = _jsonFromValue["password"];
													if(!_account || !_password){
														Ext.Msg.alert("提示","请填写用户名和密码");
														return;
													}

													var the_param = '{"op":"System.login","data":{"account":"'
															+ _account
															+ '","password":"'
															+ _password
															+ '"}}';
													Ext.data.JsonP.request({
														url : Global.URL,
														callbackKey : 'callback',
														type : "POST",
														params : {
															tt_requestbody : the_param,
															format : 'json'
														},
														callback : function(success, result) {
															if (!result) {
																console.log("result is null");
															} else {
																if(result.code == 200){
																	Ext.Viewport.removeAt(0);
																	Ext.Viewport.add(Ext.create('Sencha.view.home.HomeMain'));
																}else if(result.code == 0){
																	Ext.Msg.alert("提示","用户名和密码错误");
																}else{
																	Ext.Msg.alert("提示","请填写用户名和密码");
																}
															}
														}
													});
												}
											}
										}]
									}]
						}]
					}]
		}]
	}
});
