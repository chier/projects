<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml"
	style="overflow-x:hidden;overflow-y:auto;">
	<head>
		<title></title>
		<meta name="google" value="notranslate" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="${base }/js/loap/loap.js"></script>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css"
			media="all" />
		<%@ include file="/commons/meta.jsp"%>
		<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj,HeightValue){

		var targWin = targObj.parent.parent.parent.document.all[targObj.parent.parent.name];
		
		var custWin = targObj.parent.parent.document.all["customRight"];
		
		var searchWin1 = targObj.parent.document.all["customRight1"];
		
		if(targWin != null) {
		
			//var HeightValue = targObj.document.body.scrollHeight
		
			if(HeightValue < 600){HeightValue = 600;} //不小于600
		  
		  if(custWin !=null)
		  {
		  	custWin.style.pixelHeight = HeightValue;
		  }
		  if(searchWin1!=null)
		  {
		
		  	searchWin1.style.pixelHeight = HeightValue +10;
		  }
			targWin.style.pixelHeight = HeightValue+50;
			
		}

}

function f_iframeResize(){
		bLoadComplete = true; 
		//alert("a");
		var HeightValue = document.body.scrollHeight;
		f_frameStyleResize(this,HeightValue);

}

var bLoadComplete = false;

window.onload =  function(){
	f_iframeResize();
	}

</SCRIPT>


		<script type="text/javascript">
$(document).ready(function(){
	var _btnstr = "${btns}";
	if(_btnstr){
		var _btnArr = _btnstr.split(",");
		for(var i=0;i<_btnArr.length;i++){
			$("#" + _btnArr[i]).show();
		}
	}else{
		$(":button").each(function(){
			$(this).show();
		});
	}
});
	function exportTable() {
		getByte();
		setTimeout(
			function(){
				var customTitle = $("#customTitle").val();
				var id = $("#id").val();
				var action = base
						+ "/manage/custom/customAction!exportTable.${actionExt}?customTitle="
						+ customTitle + "&id=" + id;
				var form = $("#exportForm");
				form.attr("action", action);
				form.submit();
			}
		,2000);   
	}
	
	function thisMovie(movieName) {
		if (navigator.appName.indexOf("Microsoft") != -1) {
			return window[movieName];
		} else {
			return document[movieName];
		}
	}

	function FlashReady() {
		var chartData = '${jsondata}';
		chartData = eval("(" + chartData + ")");
		//var setStr = "<chart><chartType>column</chartType><chartD>3D</chartD><chartView showTitle='true' showLegend='true'></chartView><chartControl enableCursor='true' enableScrollbar='true'></chartControl></chart>"
		var setStr = "${charttype}";
		
		


		
		//ie
		//var vm = document.getElementById("Chart1"); //Chart is object id
		//vm.ChartCreate("图表"+Math.random(),"图例（单位：--）"+Math.random(),chartData,setStr);

		//火狐 ie
		thisMovie("simpleChart").ChartCreate(chartData, setStr);
	}
	// 					function addchart(){
	// 						document.getElementById("chartset1").style.display="";

	// 						//FlashReady();
	// 						window.setTimeout(function(){
	// 					FlashReady();
	// 				},3000);

	// 					}
	function showsetting() {
		return thisMovie("simpleChart").ShowChartSetting();
	}

	function getSet() {
		return thisMovie("simpleChart").GetChartSetting();
	}

	function getByte() {
		//var vm = document.getElementById("simpleChart"); //Chart is object id
		// return thisMovie("simpleChart").GetChartSnapshot();
		
		// var action = base
		// 		+ "/manage/custom/customAction!exportTable.${actionExt}?customTitle="
		// 		+ customTitle + "&id=" + id;
		thisMovie("simpleChart").GetChartSnapshot(
				base + "/manage/custom/customAction!saveImage.${actionExt}");
	}
	
	function mdiColspan(ssize)
	{
		var vm = document.getElementById("chartab");
		//alert(vm);
		var ww=vm.offsetWidth+ssize;
		//vm.offsetWidth +=20;
		if(ssize<0 && ww <200)return;
		if(ssize>0 && ww >750)return;
		vm.style.width =ww+"px";
		//alert(vm.offsetWidth);
	}
	
	function mdiRowspan(ssize)
	{
		var vm = document.getElementById("chartd");
		//alert(vm);
		var ww=vm.offsetHeight+ssize;
		//vm.offsetWidth +=20;
		if(ssize<0 && ww <200)return;
		if(ssize>0 && ww >1200)return;
		vm.style.height =ww+"px";
		f_iframeResize();
	}
	function orgsize(ow,oh)
	{
		var vw = document.getElementById("chartab");
		vw.style.width =ow+"px";
		var vh = document.getElementById("chartd");
		vh.style.height =oh+"px";
	}
	function overimg(idname)
	{
		var vm = document.getElementById(idname);
		//alert(vm.src);
		var newimg = vm.src;
		newimg=newimg.replace('.png','-over.png');
		vm.src=newimg;
	}
	function outimg(idname)
	{
		var vm = document.getElementById(idname);
		//alert(vm.src);
		var newimg = vm.src;
		newimg=newimg.replace('-over.png','.png');
		vm.src=newimg;
	}
	
		</script>
		<style type="text/css">
		.mainright_tdtop{
			color:#004685;
			line-height:25px;
			font-weight:bold;
			background-image:url(../../images/barbk.png);
			background-position:100% 100%;
			background-repeat:repeat;
			height:25px;
			clear:both;
		}
		.toolbar img{
			clear: both;
			flex-start;
			cursor: hand;
		}
		
		</style>
	</head>
	<body>

		<div style="margin:0px auto 0px auto;margin-top:1px;">
			<div
				style="height:25px;width:100%;border-left:1px solid #b2bac5;border-right:1px solid #b2bac5;"
				class="mainright_tdtop">
				<label style="padding-left:10px;">
					${customTitle}
				</label>
			</div>
			<div
				style="background-image:url(../../images/menubg2.jpg);clear:both;">
				<form action="" id="exportForm">
					<input type="hidden" value="${id }" name="customId" id="customId" />
					<input type="hidden" value="${strsql }" name="strsql" id="strsql" />
					<input type="hidden" value="${customTitle }" name="customTitle"
						id="customTitle" />

					<input type="button" value="导出Excel" id="exportBtn"
						class="bot1other" onclick="exportTable();"
						style="display:none;width:72px;" />

					<input type="button" value="增加图片" id="exportBtn" onclick="getByte()" />
					<input type="button" value="配置图表" onclick="showsetting()"
						class="bot1other" />
					<!-- 
			<input type="button" name="save" id="save"  onclick="clickSave();"  value="保存" class="bot1other" />
			 -->
					<!--<input type="button" value="导出配置文件" onclick="getSet()" />
			<input type="button" value="导出图片数据" onclick="getByte()" />-->
				</form>
			</div>
			<div
				style="height:25px;width:500px;border:0px solid #b2bac5;border-right:0px solid #b2bac5;margin-left:1px"
				class="toolbar">
				<input type=image src="${base}\images\yssize.png"
					onclick="orgsize(600,400);" onmouseover="overimg('orgsize');"
					onmouseout="outimg('orgsize');" id="orgsize" />
				<input type=image src="${base}\images\tosm.png"
					onclick="mdiColspan(-10);" onmouseover="overimg('colsm');"
					onmouseout="outimg('colsm');" alt="水平缩小" id="colsm" />
				<input type=image src="${base}\images\hbar.png" />
				<input type=image src="${base}\images\tobig.png"
					onclick="mdiColspan(10);" onmouseover="overimg('colbig');"
					onmouseout="outimg('colbig');" alt="水平放大" id="colbig" />
			</div>

			<div
				style="height:400px;width:25px;border:0px solid #b2bac5;border-right:0px solid #b2bac5;margin-top:0px;float:left">

				<input type=image src="${base}\images\tosm.png"
					onclick="mdiRowspan(-10);" onmouseover="overimg('rowsm');"
					onmouseout="outimg('rowsm');" alt="垂直缩小" id="rowsm" />
				<input type=image src="${base}\images\sbar.png" />
				<input type=image src="${base}\images\tobig.png"
					onclick="mdiRowspan(10);" onmouseover="overimg('rowbig');"
					onmouseout="outimg('rowbig');" alt="垂直放大" id="rowbig" />
			</div>
			<div style="float:left">
				<table cellpadding="2" cellspacing="1" border="0" width="600"
					height="400" align="center" id="chartab">

					<tr id="chartr">
						<td width="100%" height="100%" id="chartd">
							<div
								style="height: 100%;width: 100%; top: 0; border: 1; display:"
								class="flashContent">
								<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									width="100%" height="100%" id="simpleChart"
									codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
									<param name="movie" value="${base}/html/custom/SimpleChart.swf" />
									<param name="quality" value="high" />
									<param name="bgcolor" value="#ffffff" />
									<param name="allowScriptAccess" value="always" />
									<param name="allowFullScreen" value="true" />
									<param name="wmode" value="Opaque">
									<!--[if !IE]>-->
									<embed pluginspage='http://www.adobe.com/go/getflashplayer'
										width="100%" height="100%" allowScriptAccess="allowDomain"
										pluginspage="http://www.macromedia.com/go/getflashplayer"
										src="${base}/flash/SimpleChart.swf" name="simpleChart"
										swliveconnect="true" />
									<!--<![endif]-->
								</object>
							</div>
						</td>
					</tr>
				</table>
			</div>

		</div>
		<div>
			<table cellpadding="2" cellspacing="1" border="0" class="tables">
				<thead>
					<tr>
						<c:forEach var="item" items="${itemList }">
							<td align="center">
								${item }
							</td>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty tableList}">
						<c:forEach items="${tableList}" var="table">
							<tr>
								<c:forEach var="item" items="${ table}">
									<td>
										${item}
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty tableList}">
						<tr>
							<td colspan="20" align="center">
								暂无记录
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<div style="height:10px;">

		</div>
	</body>
</html>
