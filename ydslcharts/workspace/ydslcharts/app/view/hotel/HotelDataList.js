Ext.define('Sencha.view.hotel.HotelDataList', {
    extend: 'Ext.Panel',
    xtype: 'HotelDataList',
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
	            store   : 'HotelDataStoreForTable',
	            style   : 'height:270px;text-align:center;',
	            id:'htTable',
	            columns : [
	                {
	                    header    : '日期',
	                    dataIndex : 'date',
	                    width     : '25%'
	                },
	                {
	                    header    : '入住地',
	                    dataIndex : 'star',
	                    width     : '30%'
	                },
	                {
	                    header    : '酒店间夜量',
	                    dataIndex : 'hotelOrders',
	                    width     : '25%'
	                },
	                {
	                    header    : '酒店总额',
	                    dataIndex : 'hotelTotal',
	                    width     : '20%'
	                }
	            ]
	        } 
	    ]
    }
});