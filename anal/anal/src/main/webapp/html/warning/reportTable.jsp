<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html>
	<head>
		<title>预警分析</title>
		<link href="${base }/css/main.css" rel="stylesheet" type="text/css" />
		<%@ include file="/commons/meta.jsp"%>
		<SCRIPT LANGUAGE="JavaScript">

function f_frameStyleResize(targObj){

		var targWin = targObj.parent.document.all[targObj.name];
		var mainWin = targObj.parent.parent.document.all[targObj.parent.name];
		if(targWin != null) {
		
			var HeightValue = targObj.document.body.scrollHeight
			
			if(HeightValue < 600){HeightValue = 600} //不小于600
		
			targWin.style.pixelHeight = HeightValue;
			if(mainWin !=null)
			{
				mainWin.style.pixelHeight = HeightValue+53;
			}
			<c:if test="${type == 1}">
				parent.setUl(HeightValue - 3);
			</c:if>
		}
		
}

function f_iframeResize(){

		bLoadComplete = true; f_frameStyleResize(self);

}

var bLoadComplete = false;
<c:if test="${type == 1}">
window.onload = f_iframeResize;
</c:if>
</SCRIPT>
		


		<script type="text/javascript">
	$(document).ready(function(){
		// FlashReady();
		// refresh();
	});
	function thisMovie(movieName) {
		if (navigator.appName.indexOf("Microsoft") != -1) {
			return window[movieName];
		} else {
			return document[movieName];
		}
	}
	
	function DoubleValueFlashReady(a) {
		
        var chartData = '${jsondata}';
		chartData = eval("(" + chartData + ")");
		var setStr = "${charttype}";
		
		var chartData2 = '${jsonNegativeData}';
		chartData2 = eval("(" + chartData2 + ")");
		var setStr2 = "${negativeData}";
		thisMovie("simpleChart").ChartCreate(chartData, setStr);
		thisMovie("negativeChart").ChartCreate(chartData2, setStr2);
    }

	function showsetting() {
		return thisMovie("simpleChart").ShowChartSetting();
	}

	function getSet() {
		var str = thisMovie("simpleChart").GetChartSetting();
		console.info(str);
		return str;
	}

	function getByte() {
		return thisMovie("simpleChart").GetChartSnapshot();
	}
	
	function mdiColspan(id,ssize)
	{
		var vm = document.getElementById(id);
		//alert(vm);
		var ww=vm.offsetWidth+ssize;
		//vm.offsetWidth +=20;
		if(ssize<0 && ww <200)return;
		if(ssize>0 && ww >700)return;
		vm.style.width =ww+"px";
		//alert(vm.offsetWidth);
	}
	
	function mdiRowspan(id,ssize)
	{
		var vm = document.getElementById(id);
		//alert(vm);
		var ww=vm.offsetHeight+ssize;
		//vm.offsetWidth +=20;
		
		if(ssize<0 && ww <200)return;
		if(ssize>0 && ww >700)return;
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
	
	
	function refresh() {

			var array = [];
			for ( var int = 0; int < 17; int++) {
				array.push(Math.random()*10000);
			}
			thisMovie("negativeChart").ChartCreate(chartData);


		}
		
		
	 function NegativeFlashReady(a) {
	 	refresh();
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
		<div>
			<div
				style="background-image:url(${base}/images/menubg2.jpg);clear:both;">
				<form action="" id="exportForm">
					<input type="hidden" value="${id }" name="customId" id="customId" />
					<input type="hidden" value="${strsql }" name="strsql" id="strsql" />
					<input type="hidden" value="${customTitle }" name="customTitle"
						id="customTitle" />

					<input type="button" value="导出Excel" id="exportBtn" class="bot1other" 
						onclick="exportTable();" style="display:none;" />
					<input type="button" value="查看" id="exportBtn" style="display:none;" onclick="getSet();" class="bot1other" />

					<!--<input type="button" value="增加图片" id="exportBtn" onclick="FlashReady()" />-->
					<!-- <input type="button" value="配置图表" onclick="showsetting()" class="bot1other" /> -->
					<input type="button" name="save" id="save" onclick="clickSave();"
						style="display:none;" value="保存" class="bot1other"  />
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
					onclick="mdiColspan('chartab',-20);" onmouseover="overimg('colsm');"
					onmouseout="outimg('colsm');" alt="水平缩小" id="colsm" />
				<input type=image src="${base}\images\hbar.png" />
				<input type=image src="${base}\images\tobig.png"
					onclick="mdiColspan('chartab',20);" onmouseover="overimg('colbig');"
					onmouseout="outimg('colbig');" alt="水平放大" id="colbig" />
			</div>

			<div
				style=" width:25px;border:0px solid #b2bac5;border-right:0px solid #b2bac5;margin-top:0px;float:left">

				
				<input type=image src="${base}\images\tosm.png"
					onclick="mdiRowspan(-20);" onmouseover="overimg('rowsm');"
					onmouseout="outimg('chartd','rowsm');" alt="垂直缩小" id="rowsm" />
				<input type=image src="${base}\images\sbar.png" />
				<input type=image src="${base}\images\tobig.png"
					onclick="mdiRowspan('chartd',20);" onmouseover="overimg('rowbig');"
					onmouseout="outimg('rowbig');" alt="垂直放大" id="rowbig" />
			</div>
			<div style="float:left">
				<table cellpadding="2" cellspacing="1" border="0" width="600"
					height="360" align="center" id="chartab">

					<tr id="chartr">
						<td width="100%" height="100%" id="chartd">
							<div
								style="height: 100%;width: 100%; top: 0; border: 1; display:"
								class="flashContent">
								<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									width="100%" height="100%" id="simpleChart"
									codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
									<param name="movie" value="${base}/flash/DoubleValueChart.swf" />
									<param name="quality" value="high" />
									<param name="bgcolor" value="#ffffff" />
									<param name="allowScriptAccess" value="always" />
									<param name="allowFullScreen" value="true" />
									<param name="wmode" value="Opaque">
									<!--[if !IE]>-->
									<embed pluginspage='http://www.adobe.com/go/getflashplayer'
										width="100%" height="100%" allowScriptAccess="allowDomain"
										pluginspage="http://www.macromedia.com/go/getflashplayer"
										src="${base}/flash/DoubleValueChart.swf" name="simpleChart"
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
			<div
				style="background-image:url(${base}/images/menubg2.jpg);clear:both;">
				<form action="" id="exportForm">
					<input type="hidden" value="${id }" name="customId" id="customId" />
					<input type="hidden" value="${strsql }" name="strsql" id="strsql" />
					<input type="hidden" value="${customTitle }" name="customTitle"
						id="customTitle" />

					<input type="button" value="导出Excel" id="exportBtn" class="bot1other" 
						onclick="exportTable();" style="display:none;" />
					<input type="button" value="查看" id="exportBtn" style="display:none;" onclick="getSet();" class="bot1other" />

					<!--<input type="button" value="增加图片" id="exportBtn" onclick="FlashReady()" />-->
					<!--<input type="button" value="配置图表" onclick="showsetting()" class="bot1other" />-->
					<input type="button" name="save" id="save" onclick="clickSave();"
						style="display:none;" value="保存" class="bot1other"  />
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
					onclick="mdiColspan('chartab2',-20);" onmouseover="overimg('colsm');"
					onmouseout="outimg('colsm');" alt="水平缩小" id="colsm" />
				<input type=image src="${base}\images\hbar.png" />
				<input type=image src="${base}\images\tobig.png"
					onclick="mdiColspan('chartab2',20);" onmouseover="overimg('colbig');"
					onmouseout="outimg('colbig');" alt="水平放大" id="colbig" />
			</div>

			<div
				style=" width:25px;border:0px solid #b2bac5;border-right:0px solid #b2bac5;margin-top:0px;float:left">

				<input type=image src="${base}\images\tosm.png"
					onclick="mdiRowspan('chartd2',-20);" onmouseover="overimg('rowsm');"
					onmouseout="outimg('rowsm');" alt="垂直缩小" id="rowsm" />
				<input type=image src="${base}\images\sbar.png" />
				<input type=image src="${base}\images\tobig.png"
					onclick="mdiRowspan('chartd2',20);" onmouseover="overimg('rowbig');"
					onmouseout="outimg('rowbig');" alt="垂直放大" id="rowbig" />
				
			</div>
			<div style="float:left">
				<table cellpadding="2" cellspacing="1" border="0" width="600"
					height="360" align="center" id="chartab2">
					<tr id="chartr">
						<td width="100%" height="100%" id="chartd2">
							<div
								style="height: 100%;width: 100%; top: 0; border: 1; display:"
								class="flashContent">
								<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									width="100%" height="100%" id="negativeChart"
									codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
									<param name="movie" value="${base}/flash/DoubleValueChart.swf" />
									<param name="quality" value="high" />
									<param name="bgcolor" value="#ffffff" />
									<param name="allowScriptAccess" value="always" />
									<param name="allowFullScreen" value="true" />
									<param name="wmode" value="Opaque">
									<!--[if !IE]>-->
									<embed pluginspage='http://www.adobe.com/go/getflashplayer'
										width="100%" height="100%" allowScriptAccess="allowDomain"
										pluginspage="http://www.macromedia.com/go/getflashplayer"
										src="${base}/flash/DoubleValueChart.swf" name="negativeChart"
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
			<table cellpadding="2" cellspacing="1" border="0" class="tables"
				style="margin-top:50px;">
				<thead>
					<tr>
						<c:forEach var="str" items="${headList }">
							<td align="center" style="white-space:nowrap;">
								<nobar>${str}<nobar>
							</td>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<c:if test="${not empty dataList}">
						<c:forEach items="${dataList}" var="data">
							<tr>
								<c:forEach var="item" items="${data}">
									<td style="white-space:nowrap;">
										<nobar>${item}<nobar>
									</td>
								</c:forEach>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${empty dataList}">
						<tr>
							<td colspan="${itemCount}" align="center">
								暂无记录
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</body>
</html>
