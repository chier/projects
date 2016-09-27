Ext.define("Sencha.view.modiapad.LeftBottomPanel", {
	extend : 'Ext.Panel',
	requires : [
		'Sencha.view.modiapad.LeftBottomPanel1',
		'Sencha.view.modiapad.LeftBottomPanel2',
	],
	xtype : 'LeftBottomPanel',
	config : {
		flex : 3,
		layout : {
			type : 'vbox',
			align : 'stretch'
		},
		height : 140,
		defaults : {
			flex : 2,
			layout : {
				type : 'hbox',
				align : 'stretch'
			},
			defaults : {
				xtype : 'chart',
				flex : 2,
				insetPadding : 20
			}
		},
		items : [{
			flex : 1,
			width : '50%',
			// style : 'margin-left:10px;',
			items : [{
				xtype : 'LeftBottomPanel1',
				//style:'background-color: #484f57;',
				width : '30%',
				height:120
			},  {
				xtype : 'spacer',
				width : '1%'
			},{
				xtype : 'LeftBottomPanel2',
				//style:'background-color: #466f57;',
				width : '30%',
				height:120
			}]
		},{
			width : '70%',
			style:'margin-top:185px;',
			flex : 1,
			height:40,
			items : [
			{
				xtype : 'spacer',
				width : '3%'
			}, {
				xtype : 'button',
				html : '<div style="height:30px;line-height:30px;"><em class="icon_1"></em><span class="f_left">机票详情</span></div>',
				height : 40,
				width : '35%',
				cls : 'btn_con f_left',
				id:'jipiaoxiangqing',
				handler : function() {
					Global.MainController.getDataByJiPiaoIndex();
				}
			}, {
				xtype : 'spacer',
				width : '10%'
			}, {
				xtype : 'button',
				width : '35%',
				height : 40,
				html : '<div style="height:30px;line-height:30px;"><em class="icon_2"></em><span class="f_left">酒店详情</span></div>',
				id:'jiudianxiangqing',
				cls : 'btn_con f_left',
				handler : function() {
					Global.MainController.getDataByHotelIndex();
				}
			}]
		}, {
			flex : 1,
			width : '100%',
			height : 40,
			items : [{
				xtype : 'spacer',
				width : '5%'
			},{
				xtype : 'button',
				width : 300,
				height : 40,
				id : 'bumenxiangqing',
				style : 'margin-top:5px;',
				html : '<div style="height:30px;line-height:30px;"><em class="icon_3"></em><span class="f_left">部门详情</span></div>',
				cls : 'btn_con2 f_left',
				handler : function() {
					Global.MainController.tapBumenxiangqing();
				}
			}]
		}]
	}
});
