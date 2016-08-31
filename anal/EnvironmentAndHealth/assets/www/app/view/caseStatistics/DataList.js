Ext.define('Sencha.view.caseStatistics.DataList', {
	extend : 'Ext.Panel',
	xtype : 'caseStatisticsDataList',
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
			store : 'CaseStatStore',
			style : 'height:254px;text-align:center;',
			id : 'caseStatGridView',

			columns : [{
				header : '日期',
				dataIndex : 'createTime',
				width : '40%'
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
				header : '试点',
				dataIndex : 'pname',
				width : '20%'
			}, {
				header : '上传总量',
				dataIndex : 'pCount',
				width : '40%'
			}]
		}]
	}
});
