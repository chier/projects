function callApp() {
		
		var chartData = [ {
			X_Label : ${itemList[0]},
			value : ${tableList[0]}
		}, {
			X_Label : ${itemList[1]},
			value : ${tableList[1]}
		}, {
			X_Label : ${itemList[2]},
			value : ${tableList[2]}
		}];
		alert(chartData);
		var setStr = "<chart><chartType>column</chartType><chartD>3D</chartD><chartView showTitle='true' showLegend='true'></chartView><chartControl enableCursor='true' enableScrollbar='true'></chartControl></chart>"
		var vm = document.getElementById("SimpleChart"); //Chart is object id
		vm.ChartCreate("折线图表", "图例（单位：--）", chartData, setStr);
}
	function showsetting() {
		var vm = document.getElementById("SimpleChart"); //Chart is object id
		vm.ShowChartSetting();
}

	function getSet() {
		var vm = document.getElementById("SimpleChart"); //Chart is object id
		alert(vm.GetChartSetting());
}

	function getByte() {
		var vm = document.getElementById("SimpleChart"); //Chart is object id
		alert(vm.GetChartSnapshot());
}