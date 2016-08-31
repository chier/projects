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
			style:'',
			cls:'login',
			items : [{
						xtype : 'panel',
						cls:'logo',
						style:'font-size: 44px;',
						html:'环境与健康移动分析系统'
					}, {
						xtype : 'panel',
						cls:'login_form',
						items:[{
							xtype : 'panel',
							id : "login-formpanel",
							cls:'user',
							layout : {
								type : 'hbox'
							},
							items:[{
								xtype:'textfield',
								id:'account',
								cls:'text_value',
								style:'',
								height:50,
								name : 'account'
							},{
								xtype:'passwordfield',
								id:'password',
								cls:'text_values',
								height:50,
								name : 'password'
							}]
						}]
					},{
						xtype : 'button',
						cls:'tip',
						html:'登     录',
						handler : function() {
							var _account, _password;
							_account = Ext.getCmp("account").getValue();
							_password =  Ext.getCmp("password").getValue();
							
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
					},{
						xtype : 'panel',
						cls:'foot',
						html:'Copyright@2013 SCIES Corporation, All Rights Reserved<br>环境保护部华南环境科学研究所  版权所有'
					}]
		}]
	}
});
