/**
 * 全局对象，存储全局属性
 * @type 
 */
var Global = {
	maxiNum : 20,
	BaseURL:'http://59.42.176.107:8809/mobileAnal',
//	URL:'http://www.micro-view.com:81/mobileAnal/controller/report.talent',
//	 URL:'http://192.168.1.121:8080/mobileAnal/controller/report.talent',
//	 URL:'http://127.0.0.1:8080/mobileAnal/controller/report.talent',
	URL:'http://59.42.176.107:8809/mobileAnal/controller/report.talent',
	currentYears:"2014", // 当前年份，默认是2013年
	DATE : '2010-9-30',
	ViewId : '用户名+密码', // a67eb045515f4bfdb78f4224a54eb2cd ;  a9edc36a05dd42a6883d21b093b04011
	SourceId : 'sourceId',
	SurveyTableCode : '',	//记录要查询的调查数据
	SurveyPage:1,			//记录要查询的调查数据第几页
	Years:[],
	HomeController : null,
	ComprehensiveController: null,
	SurveyDataController: null,
	CaseStatisticsController:null,
	CaseInfoController:null,
	PollutantController:null,
	caseType:[{"2012":[{"1":"居民健康"},{"2":"环境调查"}]},{"2013":[{"1":"居民健康"},{"2":"环境调查"}]},{"2014":[{"1":"居民健康"},{"2":"环境调查"}]},
	          {"2015":[{"1":"居民健康"},{"2":"环境调查"}]},{"2016":[{"1":"居民健康"},{"2":"环境调查"}]}],
	getCaseType:function(year){
		var caseType = {};
		for(var i = 0 ;i < Global.caseType.length;i++){
			if(Global.caseType[i][year]){
//				console.info(Global.caseType[i][year]);
				for(var j=0; j< Global.caseType[i][year].length;j++){
					for(var obj in Global.caseType[i][year][j]){
						caseType[obj] = Global.caseType[i][year][j][obj];
//						console.info("obj = " + obj + " |value = " +  Global.caseType[i][year][j][obj]);
					}
				}
				
			}
		}
		return caseType;
	}
	
	/*
	DeptIndexController : null,
	BaseController : null,
	MainController : null,
	DeptMoreController : null,
	DeptInfoController : null,
	backPage : null
	*/
}
/**
 * 设置加载器的配置
 */
Ext.Loader.setConfig({
	enabled : true
})
/**
 * 设置一个命名空间的路径
 */
Ext.Loader.setPath({
	'Sencha' : 'app'
});

/**
 * Ext.app.Application定义了一套模型，控制器，配置文件，存储和浏览的应用程序组成。它会自动加载所有这些依赖关系，并可以指定当全部的寄托准备将被称为发射功能。
 */
Ext.application({
	// 定义名称
	name : 'Sencha',	
	// 显示的页面
	views : [
	// 以下是旧的
	'DataList', 'GridView',
	// 以下是mobileCharts
	// home 目录下
	'home.HomeMain','home.TopPanel','home.TopTimeBar','home.TopTimePanel',
	//basicInfo 目录 试点基本信息目录下
	'basicInfo.RootPanel','basicInfo.Panel2012_1','basicInfo.Panel2012_2','basicInfo.Panel2012_3','basicInfo.Panel2012_4','basicInfo.Panel2013_1','basicInfo.Panel2013_2','basicInfo.Panel2013_3','basicInfo.Panel2013_4','basicInfo.Panel2013_5','basicInfo.Panel2014_1','basicInfo.Panel2015_1','basicInfo.Panel2015_2','basicInfo.Panel2015_3','basicInfo.Panel2015_4','basicInfo.Panel2016_1','basicInfo.Panel2016_2','basicInfo.Panel2016_3','basicInfo.Panel2016_4','basicInfo.Panel2016_5',
	// caseStatistics 目录 上报情况统计目录下
	'caseStatistics.RootPanel','caseStatistics.LeftMainPanel', 'caseStatistics.LeftTopPanel', 'caseStatistics.LeftBottomPanel1', 'caseStatistics.LeftBottomPanel2', 'caseStatistics.LeftBottomPanel', 'caseStatistics.RightPanel', 'caseStatistics.RightTopPanel', 'caseStatistics.LineChart', 'caseStatistics.BarChart', 'caseStatistics.DataList',
	// caseInfo 目录， 试点详情目录
	'caseInfo.RootPanel','caseInfo.LeftMainPanel','caseInfo.LeftPicPanel','caseInfo.LeftButtonPanel','caseInfo.RightMainPanel','caseInfo.RightTopPanel','caseInfo.DataList','caseInfo.BarChart','caseInfo.LineChart',
	'login.Login',
	//CaseStatisticsRootPanel
	//surveyData 以下是调查数据目录
	'surveyData.RootPanel','surveyData.RightTabPanel','surveyData.DataList','surveyData.GridView','surveyData.DataRootPanel',
	// comprehensive 综合分析目录
	'comprehensive.RootPanel','comprehensive.RightTopPanel','comprehensive.DataList','comprehensive.LineChart','comprehensive.BarChart',
	// Pollutant 污染物分析
	'pollutant.RootPanel','pollutant.TopPilotPanel','pollutant.RightTabPanel','pollutant.LineChart','pollutant.DataList','pollutant.RightBottomButtonPanel','pollutant.BarChart'
	],
	// 数据模板
	models : [
		'ComprehensiveModel','MapModel','CaseStatModel','CaseInfoPicModel','CaseInfoChartModel','CaseInfoListModel','SurveyItemTitlsModel','Cars',
		'ProxyModel', 'DeptInfoModel', 'DeptDetailListModel', 'DeptAreaModel', 'DataModelForIndexTable', 'flight.AirLinePieProxyModel', 'flight.ArrivedPieProxyModel', 'flight.CabinPieProxyModel', 'flight.ProxyModel', 'flight.FlightTableModel', 'hotel.StarPieProxyModel', 'hotel.CityPieProxyModel', 'hotel.HotelTableModel', 'hotel.ProxyModel',
		'PollutantChartModel'
		],
	// 数据源
	stores : [
		'ComprehensiveStore','MapStore','CaseStatStore','CaseInfoPicStore','CaseInfoChartStore','CaseInfoListStore','SurveyItemTitlsStore',
		'ProxyStore', 'DeptInfoStore', 'DeptDetailListStore', 'DeptAreaStore', 'SaleDataStoreForIndexTable', 'flight.AirLinePieProxyStore', 'flight.ArrivedPieProxyStore', 'flight.CabinPieProxyStore', 'flight.FlightDataStoreForTable', 'flight.FlightOrdsProxyStore', 'flight.FlightTotalProxyStore', 'flight.CancelOrdsProxyStore', 'hotel.StarPieProxyStore', 'hotel.CityPieProxyStore', 'hotel.HotelDataStoreForTable', 'hotel.HotelTotalProxyStore', 'hotel.HotelOrdsProxyStore',
		'PollutantChartStore','PollutantTableStore'
	],
	// 业务类
	controllers : [
		'HomeController','ComprehensiveController','SurveyDataController','CaseStatisticsController','CaseInfoController','PollutantController'
		//'BaseController', 'DeptIndexController', 'DeptMoreController', 'DeptInfoController', 'Main'
],
//组件加载完后执行方法
launch : function() {
	// 页面占位隐藏
	document.getElementById("div_index").setAttribute("style", "display:none");;
//	Ext.Viewport.add(Ext.create('Sencha.view.home.HomeMain'));
		Ext.Viewport.add(Ext.create('Sencha.view.login.Login'));
// 	Ext.Viewport.add(Ext.create('Sencha.view.pollutant.RootPanel'));


	// 相关业务类，存储到全局中
	Global.HomeController = this.getApplication().getController('HomeController');
	Global.ComprehensiveController = this.getApplication().getController('ComprehensiveController');
	Global.SurveyDataController = this.getApplication().getController('SurveyDataController');

	Global.CaseStatisticsController = this.getApplication().getController('CaseStatisticsController');
	Global.CaseInfoController = this.getApplication().getController('CaseInfoController');
	Global.PollutantController =  this.getApplication().getController('PollutantController');
}
});

