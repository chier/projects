var Global = {
	maxiNum : 20,
	URL : 'http://218.241.167.189:8009/report.do',
	//URL : 'http://localhost/charts/servlet/HttpReport',
	DATE : '2010-9-30',
	ViewId : 'a9edc36a05dd42a6883d21b093b04011', // a67eb045515f4bfdb78f4224a54eb2cd ;  a9edc36a05dd42a6883d21b093b04011
	CurrentDept : null,
	DeptIndexController : null,
	BaseController : null,
	MainController : null,
	DeptMoreController : null,
	DeptInfoController : null,
	backPage : null

}
Ext.Loader.setConfig({
	enabled : true
})

Ext.Loader.setPath({
	'Sencha' : 'app'
});

Ext.application({
	name : 'Sencha',
	views : ['Main', 'TopPanel', 'TimeBar', 'TimePanel', 'RootPanel', 'LeftMainPanel', 'LeftTopPanel', 'LeftBottomPanel1', 'LeftBottomPanel2', 'LeftBottomPanel', 'RightPanel', 'RightTopPanel', 'LineChart', 'BarChart', 'DataList', 'GridView',
	//机票详情页
	'flight.FlightMain', 'flight.CabinPiePanel', 'flight.AirLinePiePanel', 'flight.ArrivedPiePanel', 'flight.LeftBottomPiesPanel', 'flight.FlightOrdersLineChart', 'flight.CancelOrdersLineChart', 'flight.FlightTotalLineChart', 'flight.FlightDataList', 'flight.TopMainPanel', 'flight.BottomMainPanel', 'flight.FlightTimeBar', 'flight.FlightTimePanel', 'flight.FlightTopPanel', 'flight.FlightRootPanel',
	//酒店详情页
	'hotel.HotelMain', 'hotel.StarPiePanel', 'hotel.HotelCityPiePanel', 'hotel.LeftBottomPiesPanel', 'hotel.HotelOrdersLineChart', 'hotel.HotelTotalLineChart', 'hotel.HotelDataList', 'hotel.TopMainPanel', 'hotel.BottomMainPanel', 'hotel.HotelTimeBar', 'hotel.HotelTimePanel', 'hotel.HotelTopPanel', 'hotel.HotelRootPanel', 'deptindex.DeptIndex', 'deptmore.DeptMore', 'deptinfo.DeptInfo'],
	models : ['ProxyModel', 'DeptInfoModel', 'DeptDetailListModel', 'DeptAreaModel', 'DataModelForIndexTable', 'flight.AirLinePieProxyModel', 'flight.ArrivedPieProxyModel', 'flight.CabinPieProxyModel', 'flight.ProxyModel', 'flight.FlightTableModel', 'hotel.StarPieProxyModel', 'hotel.CityPieProxyModel', 'hotel.HotelTableModel', 'hotel.ProxyModel'],

	stores : ['ProxyStore', 'DeptInfoStore', 'DeptDetailListStore', 'DeptAreaStore', 'SaleDataStoreForIndexTable', 'flight.AirLinePieProxyStore', 'flight.ArrivedPieProxyStore', 'flight.CabinPieProxyStore', 'flight.FlightDataStoreForTable', 'flight.FlightOrdsProxyStore', 'flight.FlightTotalProxyStore', 'flight.CancelOrdsProxyStore', 'hotel.StarPieProxyStore', 'hotel.CityPieProxyStore', 'hotel.HotelDataStoreForTable', 'hotel.HotelTotalProxyStore', 'hotel.HotelOrdsProxyStore'],

	controllers : ['BaseController', 'DeptIndexController', 'DeptMoreController', 'DeptInfoController', 'Main'],
	launch : function() {//组件加载完后执行方法
		// console.info("launch");
		document.getElementById("div_index").setAttribute("style", "display:none");;
		// Ext.create("Sencha.view.Main", {title: 'ssss'});
		Ext.Viewport.add(Ext.create('Sencha.view.Main'));
		Global.DeptIndexController = this.getApplication().getController('DeptIndexController');
		Global.BaseController = this.getApplication().getController('BaseController');
		Global.MainController = this.getApplication().getController('Main');
		Global.DeptMoreController = this.getApplication().getController('DeptMoreController');
		Global.DeptInfoController = this.getApplication().getController('DeptInfoController');
		
		// toHome();	// 返回首页
		// Global.MainController.getDataByJiPiaoIndex();
		// Global.MainController.getDataByHotelIndex();
		// toDeptIndex();
		//  toMoreDept();
		// deptMoreToDeptInfo("61b413f54b3b412ab3ad4ec09ddec4ee");
	}
});
