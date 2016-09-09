<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title></title>
		<meta name="google" value="notranslate" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<style type="text/css" media="screen">
			html,body { height: 100%;  width: 300px}
			body { margin: 0; padding: 0; overflow: auto; text-align: center; background-color: #ffffff;}
			object:focus {　outline: none;　}
		</style>
		<%@ include file="/commons/meta.jsp"%>
	
		<link rel="stylesheet" type="text/css"
			　href="${base }/html/custom/history/history.css" />
		<script type="text/javascript"
			　src="${base }/html/custom/history/history.js"></script>
		
		<script type="text/javascript">
				function getMyApp(appName) {
	        if (navigator.appName.indexOf ("Microsoft") !=-1) {
	        	
	            return window[appName];
	        } else {
	            return document[appName];
	        }
		    }
				function callApp() {
					var chartData = '${tableList}';

					chartData =  eval("("+chartData+")");
					var setStr = "<chart><chartType>column</chartType><chartD>3D</chartD><chartView showTitle='true' showLegend='true'></chartView><chartControl enableCursor='true' enableScrollbar='true'></chartControl></chart>"
					
					//ie
					//var vm = document.getElementById("Chart1"); //Chart is object id
					//vm.ChartCreate("图表"+Math.random(),"图例（单位：--）"+Math.random(),chartData,setStr);
					
					
					//火狐 ie
					getMyApp("Chart1").ChartCreate("图表${customTitle}","图例（${customTitle}）",chartData,setStr);
					

				}
				function showsetting() {
					//var vm = document.getElementById("Chart"); //Chart is object id
					getMyApp("Chart1").ShowChartSetting();
				}
	
				function getSet() {
					//var vm = document.getElementById("Chart"); //Chart is object id
					return (getMyApp("Chart1").GetChartSetting());
				}
	
				function getByte() {
					//var vm = document.getElementById("Chart"); //Chart is object id
					alert(getMyApp("Chart1").GetChartSnapshot());
				}
		</script>

	</head>
	<body>
		<div id="main" style=" float: left; min-width: 0px;">
			<div class="title" style="width: 600px;">
				<h4>
					${customTitle }
				</h4>
			</div>

			<div
				style="height: 30; top: 0; display: block; width: 600px; margin-bottom: 20px;">
				<button onclick="callApp()" style="width: 105; height: 20"
					type="button" id="callAppBtn">
					刷新数据
				</button>

				<button onclick="showsetting()" style="width: 105; height: 20"
					type="button">
					设置图表
				</button>

				<button onclick="getSet()" style="width: 105; height: 20"
					type="button">
					调出配置
				</button>

				<button onclick="getByte()" style="width: 105; height: 20"
					type="button">
					调出图片数组
				</button>
			</div>

			<div style="height: 250;top: 0;border:1">
          	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" 
							codebase="../../../download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=11.1.0" 
							width="600" height="400" id="Chart1">
						<param name="movie" value="${base }/html/custom/SimpleChart.swf" />
						<param name="quality" value="high" />
						<param name="bgcolor" value="#ffffff" />
						<param name="allowScriptAccess" value="sameDomain" />
						<param name="allowFullScreen" value="true" />
						<embed name="Chart1" src="${base }/html/custom/SimpleChart.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="500" height="400">
						</embed>
					</object>
        </div>   

		</div>
	</body>
</html>
