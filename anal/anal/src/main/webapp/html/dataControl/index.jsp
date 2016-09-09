<%@ page contentType="text/html;charset=gb2312"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="/commons/taglibs.jsp"%>
<html>
<head>
<style>
body {
	margin: 0px;
	padding: 0px;
	font-size: 12px;
	text-align: center;
}

body>div {
	text-align: center;
	margin-right: auto;
	margin-left: auto;
}

.content {
	width: 990px;
}

.content .left {
	float: left;
	width: 50%;
	border: 1px solid #91C9F2;
	margin: 1px;
}

.content .center {
	float: left;
	border: 1px solid #91C9F2;
	margin: 2px;
	width: 48%
}

.content .right {
	float: right;
	width: 10%;
	border: 1px solid #91C9F2;
	margin: 1px
}

.mo {
	height: auto;
	border: 0px solid #CC;
	margin: 5px;
	background: #FFF
}

.mo h1 {
	background: #369CE7;
	height: 18px;
	padding: 3px;
	color: #FFFFFF;
	cursor: move
}

.mo .nr {
	height: 180px;
	border: 0px solid #F3F3F3;
	margin: 1px
}

.mo .nr2 {
	border: 0px solid #F3F3F3;
	margin: 1px
}

h1 {
	margin: 0px;
	padding: 0px;
	text-align: left;
	font-size: 12px
}
</style>
<script>
var dragobj={}
window.onerror=function(){return false}
function on_ini(){
 String.prototype.inc=function(s){return this.indexOf(s)>-1?true:false}
 var agent=navigator.userAgent
 window.isOpr=agent.inc("Opera")
 window.isIE=agent.inc("IE")&&!isOpr
 window.isMoz=agent.inc("Mozilla")&&!isOpr&&!isIE
 if(isMoz){
  Event.prototype.__defineGetter__("x",function(){return this.clientX+2})
  Event.prototype.__defineGetter__("y",function(){return this.clientY+2})
 }
 basic_ini()
}
function basic_ini(){
 window.$=function(obj){return typeof(obj)=="string"?document.getElementById(obj):obj}
 window.oDel=function(obj){if($(obj)!=null){$(obj).parentNode.removeChild($(obj))}}
}
window.onload=function(){
 f_iframeResize();
 on_ini()
 var o=document.getElementsByTagName("h1")
 for(var i=0;i<o.length;i++){
  o[i].onmousedown=function(e){
   if(dragobj.o!=null)
    return false
   e=e||event
   dragobj.o=this.parentNode
   dragobj.xy=getxy(dragobj.o)
   dragobj.xx=new Array((e.x-dragobj.xy[1]),(e.y-dragobj.xy[0]))
   dragobj.o.style.width=dragobj.xy[2]+"px"
   dragobj.o.style.height=dragobj.xy[3]+"px"
   dragobj.o.style.left=(e.x-dragobj.xx[0])+"px"
   dragobj.o.style.top=(e.y-dragobj.xx[1])+"px"   
   dragobj.o.style.position="absolute"
   var om=document.createElement("div")
   dragobj.otemp=om
   om.style.width=dragobj.xy[2]+"px"
   om.style.height=dragobj.xy[3]+"px"
   dragobj.o.parentNode.insertBefore(om,dragobj.o)
   return false
  }
 }
}
document.onselectstart=function(){return false}
window.onfocus=function(){document.onmouseup()}
window.onblur=function(){document.onmouseup()}
document.onmouseup=function(){
 if(dragobj.o!=null){
  dragobj.o.style.width="auto"
  dragobj.o.style.height="auto"
  dragobj.otemp.parentNode.insertBefore(dragobj.o,dragobj.otemp)
  dragobj.o.style.position=""
  oDel(dragobj.otemp)
  dragobj={}
  f_iframeResize();
 }
}
document.onmousemove=function(e){
 e=e||event
 if(dragobj.o!=null){
  dragobj.o.style.left=(e.x-dragobj.xx[0])+"px"
  dragobj.o.style.top=(e.y-dragobj.xx[1])+"px"
  createtmpl(e)
 }
}
function getxy(e){
 var a=new Array()
 var t=e.offsetTop;
 var l=e.offsetLeft;
 var w=e.offsetWidth;
 var h=e.offsetHeight;
 while(e=e.offsetParent){
  t+=e.offsetTop;
  l+=e.offsetLeft;
 }
 a[0]=t;a[1]=l;a[2]=w;a[3]=h
  return a;
}
function inner(o,e){
 var a=getxy(o)
 if(e.x>a[1]&&e.x<(a[1]+a[2])&&e.y>a[0]&&e.y<(a[0]+a[3])){
  if(e.y<(a[0]+a[3]/2))
   return 1;
  else
   return 2;
 }else
  return 0;
}
function createtmpl(e){
 for(var i=0;i<12;i++){
  if($("m"+i)==dragobj.o)
   continue
  var b=inner($("m"+i),e)
  if(b==0)
   continue
  dragobj.otemp.style.width=$("m"+i).offsetWidth
  if(b==1){
   $("m"+i).parentNode.insertBefore(dragobj.otemp,$("m"+i))
  }else{
   if($("m"+i).nextSibling==null){
    $("m"+i).parentNode.appendChild(dragobj.otemp)
   }else{
    $("m"+i).parentNode.insertBefore(dragobj.otemp,$("m"+i).nextSibling)
   }
  }
  return
 }
 for(var j=0;j<3;j++){
  if($("dom"+j).innerHTML.inc("div")||$("dom"+j).innerHTML.inc("DIV"))
   continue
  var op=getxy($("dom"+j))
  if(e.x>(op[1]+10)&&e.x<(op[1]+op[2]-10)){
   $("dom"+j).appendChild(dragobj.otemp)
   dragobj.otemp.style.width=(op[2]-10)+"px"
  }
 }
 
}
</script>
<script>
//表格操作类方法
var  highlightcolor='#c1ebff';
//此处clickcolor只能用win系统颜色代码才能成功,如果用#xxxxxx的代码就不行,还没搞清楚为什么:(
var  clickcolor='#51b2f6';
function  changeto(){
		source=event.srcElement;
		if  (source.tagName=="TR"||source.tagName=="TABLE")
		return;
		while(source.tagName!="TD")
		source=source.parentElement;
		source=source.parentElement;
		cs  =  source.children;
		//alert(cs.length);
		if  (cs[1].style.backgroundColor!=highlightcolor&&source.id!="nc"&&cs[1].style.backgroundColor!=clickcolor)
		for(i=0;i<cs.length;i++){
			cs[i].style.backgroundColor=highlightcolor;
		}
}

function  changeback(){
		if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
		return
		if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
		//source.style.backgroundColor=originalcolor
		for(i=0;i<cs.length;i++){
			cs[i].style.backgroundColor="";
		}
}

function  clickto(){
		source=event.srcElement;
		if  (source.tagName=="TR"||source.tagName=="TABLE")
		return;
		while(source.tagName!="TD")
		source=source.parentElement;
		source=source.parentElement;
		cs  =  source.children;
		//alert(cs.length);
		if  (cs[1].style.backgroundColor!=clickcolor&&source.id!="nc")
		for(i=0;i<cs.length;i++){
			cs[i].style.backgroundColor=clickcolor;
		}
		else
		for(i=0;i<cs.length;i++){
			cs[i].style.backgroundColor="";
		}
}

function getObject(objectId) 
{ 
	if(document.getElementById && document.getElementById(objectId)) 
	{ 
	return document.getElementById(objectId) 
	} 
	else if(document.all && document.all(objectId)) 
	{ 
	return document.all(objectId) 
	} 
	else if(document.layers && document.layers[objectId]) 
	{ 
	return document.layers[objectId] 
	} 
	else 
	{ 
	return false 
	} 
}

	function thisMovie(movieName) {
        if (navigator.appName.indexOf("Microsoft") != -1) {
            return window[movieName];
        } else {
            return document[movieName];
        }
    }
</script>

<SCRIPT LANGUAGE="JavaScript">
function getObject(objectId) 
{ 
	if(document.getElementById && document.getElementById(objectId)) 
	{ 
	return document.getElementById(objectId) 
	} 
	else if(document.all && document.all(objectId)) 
	{ 
	return document.all(objectId) 
	} 
	else if(document.layers && document.layers[objectId]) 
	{ 
	return document.layers[objectId] 
	} 
	else 
	{ 
	return false 
	} 
}
function f_frameStyleResize(targObj){

		var leftdiv = getObject("dom1");
		var maxdivHeight = getObject("dom0").offsetHeight> getObject("dom1").offsetHeight?getObject("dom0").offsetHeight : getObject("dom1").offsetHeight;

		var targWin = targObj.parent.document.all[targObj.name];

		targWin.style.pixelHeight = maxdivHeight+30;
}

function f_iframeResize(){
		bLoadComplete = true; 
		f_frameStyleResize(this);

}

var bLoadComplete  = false;

//window.onload =  f_iframeResize;

</SCRIPT>
</head>
<body>
<div class="content" style="width:953px;">

<div class=left id=dom1>

<div class=mo id=m11>
<h1>数据上报情况</h1>
<div class="nr2">
<table width="470px" border="0" cellpadding="0" cellspacing="1"
	bgcolor="b5d6e6" onmouseover="changeto()" onmouseout="changeback()"
	id="datatable">
	<tr>
		<td width="65%" height="22" background="${base}/images/bg.gif"
			bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">调查点</span></div>
		</td>
		<td width="35%" background="${base}/images/bg.gif" bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">上报数据量</span></div>
		</td>
	</tr>
	<tr>
		<td height="20" bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">山西洪洞县试点</span></div>
		</td>
		<td bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">30529</span></div>
		</td>
	</tr>
	<tr>
		<td height="20" bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">江西大余县试点</span></div>
		</td>
		<td bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">10540</span></div>
		</td>
	</tr>
	<tr>
		<td height="20" bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">广东广石化试点</span></div>
		</td>
		<td bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">14979</span></div>
		</td>
	</tr>
	<tr>
		<td height="20" bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">广西南丹市试点</span></div>
		</td>
		<td bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">19469</span></div>
		</td>
	</tr>
	<tr>
		<td height="20" bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">浙江路桥区试点</span></div>
		</td>
		<td bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">1020</span></div>
		</td>
	</tr>
	<tr>
		<td height="20" bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">云南会泽县试点</span></div>
		</td>
		<td bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">30925</span></div>
		</td>
	</tr>
	<tr>
		<td height="20" bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">江苏高淳市试点</span></div>
		</td>
		<td bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">7827</span></div>
		</td>
	</tr>
	<tr>
		<td height="20" bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">山东淄博市试点</span></div>
		</td>
		<td bgcolor="#FFFFFF">
		<div align="center"><span class="STYLE1">14457</span></div>
		</td>
	</tr>
</table>

</div>
</div>


<div class=mo id=m4>
<h1>上报情况对比图1</h1>
<div class="nr2"><object
	classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%"
	height="300px" id="bubbleChart"
	codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
	<param name="movie" value="BubbleChart.swf" />
	<param name="quality" value="high" />
	<param name="bgcolor" value="#ffffff" />
	<param name="allowScriptAccess" value="always" />
	<param name="allowFullScreen" value="true" />
	<!--[if !IE]>--> <embed
		pluginspage='http://www.adobe.com/go/getflashplayer' width="100%"
		height="300px" allowScriptAccess="allowDomain"
		pluginspage="http://www.macromedia.com/go/getflashplayer"
		src="BubbleChart.swf" name="bubbleChart"
		swliveconnect="true" /> <!--<![endif]--> </object><script
	type="text/javascript">

    function BubbleFlashReady(a) {
    	var array = [];
		for ( var int = 0; int < 17; int++) {
			//array.push(Math.random()*10000);
                            array.push({x:int,y:int/2,value:Math.random()*10000});
		};

		/*
		 * @param title String
		 * @param legendTitle String
		 * @param data Array
		 * @param setting String
		 */
		thisMovie("bubbleChart").ChartCreate(array);
		}
	</script></div>
</div>

<div class=mo id=m5>
<h1>上报情况雷达分布</h1>
<div class="nr2" ><object margin="auto"
	classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%"
	height="255px" 
	id="simpleChart"
	codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
	<!--<param name="movie" value="${base}/html/custom/RadarChart.swf" />-->
	<param name="movie" value="RadarChart.swf" />
	<param name="quality" value="high" />
	<param name="bgcolor" value="#ffffff" />
	<param name="allowScriptAccess" value="always" />
	<param name="allowFullScreen" value="true" />
	<!--[if !IE]>--> <embed
		pluginspage='http://www.adobe.com/go/getflashplayer' width="100%"
		height="255px" allowScriptAccess="allowDomain"
		pluginspage="http://www.macromedia.com/go/getflashplayer"
		src="RadarChart.swf" name="simpleChart" swliveconnect="true" /> <!--<![endif]-->
</object> <script type="text/javascript">

    function FlashReady() {
				var setStrRadar = "<chart><dataField><field>X_Label</field><field>value1</field></dataField><chartSeries><series><seriesType>radar</seriesType><seriesTitle>value1</seriesTitle><valueField>value1</valueField></series><series><seriesType>radar</seriesType></series></chartSeries><chartTitle>图表名</chartTitle><xAxisField>X_Label</xAxisField><chartD>2D</chartD><chartView showTitle='true' showLegend='false'></chartView><chartControl enableCursor='true' enableScrollbar='true'></chartControl></chart>";
				var setStrPie = "<chart><dataField><field>X_Label</field><field>value1</field></dataField><chartSeries><series><seriesType>pie</seriesType><seriesTitle>value1</seriesTitle><valueField>value1</valueField></series><series><seriesType>radar</seriesType></series></chartSeries><chartTitle>图表名</chartTitle><xAxisField>X_Label</xAxisField><chartD>2D</chartD><chartView showTitle='false' showLegend='false'></chartView><chartControl enableCursor='true' enableScrollbar='true'></chartControl></chart>";
				var chartData = [
									{X_Label:"山西洪洞县",value1:30529},
									{X_Label:"江西大余县",value1:10540},
									{X_Label:"广东广石化",value1:14979},
									{X_Label:"广西南丹市",value1:19469},
									{X_Label:"浙江路桥区",value1:1020},
									{X_Label:"云南会泽县",value1:30925},
									{X_Label:"江苏高淳市",value1:7827},
									{X_Label:"山东淄博市",value1:14457}
									];
				
				//var setStr = "<chart><dataField><field>省</field><field>行政区划代码（合计值）</field></dataField><chartSeries><series><seriesType>column</seriesType><seriesTitle>行政区划代码（合计值）</seriesTitle><valueField>行政区划代码（合计值）</valueField></series></chartSeries><chartTitle>图表名</chartTitle><xAxisField>省</xAxisField><chartD>2D</chartD><chartView showTitle='true' showLegend='true'></chartView><chartControl enableCursor='true' enableScrollbar='true'></chartControl></chart>";
		
			  //var chartData = [{"省":"广西","行政区划代码（合计值）":731},{"省":"江苏","行政区划代码（合计值）":703},{"省":"江西","行政区划代码（合计值）":481},{"省":"山东","行政区划代码（合计值）":511},{"省":"山西","行政区划代码（合计值）":363},{"省":"云南","行政区划代码（合计值）":871},{"省":"浙江","行政区划代码（合计值）":763}];

				thisMovie("simpleChart").ChartCreate(chartData,setStrRadar);
		}
	</script></div>
	
	<div class=mo id=m2>
	<h1>上报情况对比图</h1>
	<div class="nr2">
		<object
		classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%"
		height="300px" id="gauge"
		codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
		<param name="movie" value="Gauge.swf" />
		<param name="quality" value="high" />
		<param name="bgcolor" value="#ffffff" />
		<param name="allowScriptAccess" value="always" />
		<param name="allowFullScreen" value="true" />
		<!--[if !IE]>--> <embed
			pluginspage='http://www.adobe.com/go/getflashplayer' width="100%"
			height="300px" allowScriptAccess="allowDomain"
			pluginspage="http://www.macromedia.com/go/getflashplayer"
			src="Gauge.swf" name="gauge"
			swliveconnect="true" /> <!--<![endif]--> </object>
			
			
			<script
		type="text/javascript">
	
	    function GaugeFlashReady(a) {
	
			/**
			 * @param alertValues String "-150,-100,-50,0,50,100,150"
			 * @param alertColors String "red,#ff8c00,yellow,green,blue,#6F00FF"
			 * @param alertAlphas Array ".8,.8,.8,.8,.8,.8"
			 * @param miniMun Number
			 * @param maxiMun Number
			 * @param value Number
			 *   */
			thisMovie("gauge").GaugeSet("-150,-100,-50,0,50,100,150","red,#ff8c00,yellow,green,blue,#6F00FF",".8,.8,.8,.8,.8,.8",-150,150,80);
			}
		</script>
	</div>
</div>
</div>

</div>
<div class=center id=dom0>
	<div class=mo id=m6 >
			<h1>试点铅检出结果</h1>
			<div class="nr2">
					<object
						classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%"
						height="300px" id="barLineChart"
						codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
						<param name="movie" value="BarLineChart.swf" />
						<param name="quality" value="high" />
						<param name="bgcolor" value="#ffffff" />
						<param name="allowScriptAccess" value="always" />
						<param name="allowFullScreen" value="true" />
						<!--[if !IE]>--> 
						<embed
							pluginspage='http://www.adobe.com/go/getflashplayer' width="100%"
							height="300px" allowScriptAccess="allowDomain"
							pluginspage="http://www.macromedia.com/go/getflashplayer"
							src="BarLineChart.swf" name="barLineChart"
							swliveconnect="true" /> <!--<![endif]--> 
					</object>
					<script	type="text/javascript">
			
			    function BarLineFlashReady(a) {
			    	var array = [
			    	                {xLabel:"路桥",value1:296,value2:74},
			    	                {xLabel:"云和",value1:30.5,value2:20},
			    	                {xLabel:"大余",value1:468,value2:26},
			    	                {xLabel:"南丹",value1:4500,value2:512},
			    	                {xLabel:"会泽",value1:6154,value2:1021}
			    	            ];
			
			    	 			/**
			    	 			 * @param title String
			    	 			 * @param legendTitle String
			    	 			 * @param data Array
			    	 			 * @param setting String
			    	 			 *   */
			    	     
									//var vm = document.getElementById("simpleChart");
									//vm.ChartCreate("","图例（单位：--）"+Math.random(),chartData,setStr);
									thisMovie("barLineChart").ChartCreate(array,"铅(最大值)","铅(平均值)");
					}
				</script>
			</div>
		</div>
<div class=mo id=m0 style="display: none">
<h1>dom0</h1>
<div class="nr2"></div>
</div>
<div class=mo id=m1>
<h1>健康调查人数情况</h1>
<div class="nr2"><object
	classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%"
	height="300px" id="stackedAreaChart"
	codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
	<param name="movie" value="StackedAreaChart.swf" />
	<param name="quality" value="high" />
	<param name="bgcolor" value="#ffffff" />
	<param name="allowScriptAccess" value="always" />
	<param name="allowFullScreen" value="true" />
	<!--[if !IE]>--> <embed
		pluginspage='http://www.adobe.com/go/getflashplayer' width="100%"
		height="300px" allowScriptAccess="allowDomain"
		pluginspage="http://www.macromedia.com/go/getflashplayer"
		src="StackedAreaChart.swf" name="stackedAreaChart"
		swliveconnect="true" /> <!--<![endif]--> </object><script
	type="text/javascript">

    function StackedAreaFlashReady(a) {
    	var array = [
    	 			{xLabel:"会泽",value1:20,value2:3.0,value3:75,value4:110},
    	 			{xLabel:"南丹",value1:28,value2:2.4,value3:63,value4:120},
    	 			{xLabel:"路桥",value1:30,value2:2.1,value3:53,value4:140},
    	 			{xLabel:"大余",value1:20,value2:3.0,value3:65,value4:150},
    	 			{xLabel:"洪洞",value1:28,value2:2.4,value3:53,value4:130},
    	 			{xLabel:"黄埔",value1:30,value2:2.1,value3:63,value4:140},
    	 			{xLabel:"淄博",value1:20,value2:3.0,value3:75,value4:110},
    	 			{xLabel:"高淳",value1:28,value2:2.4,value3:83,value4:100}
    	 		];

    	 			/**
    	 			 * @param title String
    	 			 * @param legendTitle String
    	 			 * @param data Array
    	 			 * @param setting String
    	 			 *   */
    	                         //var vm = document.getElementById("simpleChart");
    	                         //vm.ChartCreate("","图例（单位：--）"+Math.random(),chartData,setStr);
    	 			thisMovie("stackedAreaChart").ChartCreate(array,"患病人数","化验人数","体检人数","调查人数");
		}
	</script></div>
</div>
<div class=mo id=m2 style="display: ">
<h1>重金属检出率</h1>
<div class="nr2"><object
	classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%"
	height="200px" id="stackedBarChart"
	codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
	<param name="movie" value="StackedBarChart.swf" />
	<param name="quality" value="high" />
	<param name="bgcolor" value="#ffffff" />
	<param name="allowScriptAccess" value="always" />
	<param name="allowFullScreen" value="true" />
	<!--[if !IE]>--> <embed
		pluginspage='http://www.adobe.com/go/getflashplayer' width="100%"
		height="200px" allowScriptAccess="allowDomain"
		pluginspage="http://www.macromedia.com/go/getflashplayer"
		src="StackedBarChart.swf" name="stackedBarChart"
		swliveconnect="true" /> <!--<![endif]--> </object><script
	type="text/javascript">

    function StackedBarFlashReady(a) {
    	var array = [
    	 			{xLabel:"浙江路桥",value1:54/56,value2:0,value3:54/56,value4:44/56,value5:1},
    	 			{xLabel:"浙江云和",value1:0.66,value2:0.66,value3:0.66,value4:0.66,value5:0.66},
    	 			{xLabel:"江西大余",value1:1212/1258,value2:1177/1258,value3:1432/1462,value4:0,value5:0},
    	 			{xLabel:"广西南丹",value1:764/872,value2:810/885,value3:761/872,value4:0,value5:0},
    	 			{xLabel:"云南会泽",value1:675/856,value2:667/837,value3:687/855,value4:0,value5:0}
    	 		];

    	 			/**
    	 			 * @param title String
    	 			 * @param legendTitle String
    	 			 * @param data Array
    	 			 * @param setting String
    	 			 *   */
    	                         //var vm = document.getElementById("simpleChart");
    	                         //vm.ChartCreate("","图例（单位：--）"+Math.random(),chartData,setStr);
    	 			thisMovie("stackedBarChart").ChartCreate(array,"铅检出率","砷检出率","镉检出率","汞检出率","铬检出率");
		}
	</script></div>
</div>
<div class=mo id=m3>
<h1>重金属复合检出率</h1>
<div class="nr"><object
	classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%"
	height="100%" id="stackedColumnChart"
	codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab">
	<param name="movie" value="StackedColumnChart.swf" />
	<param name="quality" value="high" />
	<param name="bgcolor" value="#ffffff" />
	<param name="allowScriptAccess" value="always" />
	<param name="allowFullScreen" value="true" />
	<!--[if !IE]>--> <embed
		pluginspage='http://www.adobe.com/go/getflashplayer' width="100%"
		height="100%" allowScriptAccess="allowDomain"
		pluginspage="http://www.macromedia.com/go/getflashplayer"
		src="StackedColumnChart.swf" name="stackedColumnChart"
		swliveconnect="true" /> <!--<![endif]--> </object><script
	type="text/javascript">

    function StackedColumnFlashReady(a) {
    	var array = [
    	 			{xLabel:"浙江路桥",value1:54/56,value2:0,value3:54/56,value4:44/56,value5:1},
    	 			{xLabel:"浙江云和",value1:0.66,value2:0.66,value3:0.66,value4:0.66,value5:0.66},
    	 			{xLabel:"江西大余",value1:1212/1258,value2:1177/1258,value3:1432/1462,value4:0,value5:0},
    	 			{xLabel:"广西南丹",value1:764/872,value2:810/885,value3:761/872,value4:0,value5:0},
    	 			{xLabel:"云南会泽",value1:675/856,value2:667/837,value3:687/855,value4:0,value5:0}
    	 		];

    	 			/**
    	 			 * @param title String
    	 			 * @param legendTitle String
    	 			 * @param data Array
    	 			 * @param setting String
    	 			 *   */
    	 			thisMovie("stackedColumnChart").ChartCreate(array,"铅检出率","砷检出率","镉检出率","汞检出率","铬检出率");
		}
	</script></div>
</div>
</div>
</div>
</body>
</html>
