Ext.define('Sencha.view.flight.FlightDataList', {
    extend: 'Ext.Panel',
    xtype: 'FlightDataList',
    id:'ticketDatalst',
    flex:1,
    config: {
	    cls: 'righttop',
	    ui: 'round', 
	    scroll : false,
	    layout: {
	        type: 'card'
	    },         
	    items: [
	        {
	            xtype   : 'GridView',
	            title   : 'Grid',
	            store   : 'FlightDataStoreForTable',
	            style:'height:270px;text-align:center;',
	            id:'tktTable',
	            columns : [
	                {
	                	header    : '日期',
	                    dataIndex : 'date',
	                    width     : '20%'
	                },
	                {
	                	header    : '舱位及折扣',
	                    dataIndex : 'cabin',
	                    width     : '25%'
	                }, {
	                    header    : '机票总额',
	                    dataIndex : 'flightTotal',
	                    width     : '20%'
	                },
	                {
	                    header    : '机票出票量',
	                    dataIndex : 'flightOrders',
	                    width     : '20%'
	                },
	                {
	                    header    : '退废量',
	                    dataIndex : 'cancelOrders',
	                    width     : '15%'
	                }
	            ]
	        } 
	    ]
    }
});