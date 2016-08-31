Ext.define('Sencha.view.DataList', {
	extend : 'Ext.Panel',
	xtype : 'DataList',
	flex : 1,
	config : {
		cls : 'righttop',
		ui : 'round',
		scroll : false,
		margin : '10px 10px 10px 10px',
		title : 'DataList',
		layout : {
			type : 'card'
		},
		items : [{
			xtype : 'GridView',
			title : 'Grid',
			store : 'SaleDataStoreForIndexTable',
			style : 'height:254px;text-align:center;',
			id : 'indexGridView',

			columns : [{
				header : '日期',
				dataIndex : 'date',
				width : '40%',
			}, /*{
			 header    : '机票出票量',
			 dataIndex : 'flightOrders',
			 width:'12%',
			 },{
			 header    : '机票出总额',
			 dataIndex : 'flightTotal',
			 width:'12%',
			 },
			 {
			 header    : '酒店间夜量',
			 dataIndex : 'hotelOrders',
			 width:'12%',
			 },
			 {
			 header    : '酒店总额',
			 dataIndex : 'hotelTotal',
			 width:'12%',
			 },*/
			{
				header : '机+酒总量',
				dataIndex : 'orders',
				width : '20%',
			}, {
				header : '机+酒总额',
				dataIndex : 'total',
				width : '40%',
			}]
		}]
	}
});
