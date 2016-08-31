

/**
 * 计算指定年份中某有份有多少周
 * @param year 指定年
 * @param month 指定月
 * @param curMonth 当前月
 * @param days
 */
function weeksOfMonth(Year,Month,curMonth,days){
	var cuMonthDays = Month === curMonth ? days : daysOfMonth(Year,Month);
	Month--; 
    var d = new Date(Year,Month,1); 
	var	day = d.getDay() === 0 ? 7 : d.getDay(); //获取当前星期X(0-6,0代表星期天)
	var endWeeks = d.getDay() === 0 ? 0 : 1;	// 第一天是否默认是第一周
	var x = cuMonthDays - (7 - day);  // 获取除去第一月剩余天数
	endWeeks += (parseInt(x/7));		  // 当前月份加上剩余天数据除以7得到的周数
	endWeeks += x%7 === 0 ? 0 : 1;
	
	return endWeeks;
}

/** 
*  判断指定日期是一年中的第几周
*/
function getWeekNumOfYear(year, month, day) { 
    var d1 = new Date(year, month-1, day);
	var d2 = new Date(year, 0, 1);
    var d = Math.round((d1 - d2) / 86400000); 
    return Math.ceil((d + ((d2.getDay() + 1) - 1)) / 7); 
}
/**
* 计算两个日期间相隔天数
* @param a
* @param b
* return number
*/
function getDiffDays(a,b){//a , b 格式为 yyyy-MM-dd 
    var a1 = a.replace(/-/g, "/");
    var b1 = b.replace(/-/g, "/");
    var d1 = new Date(a1); 
    var d2 = new Date(b1); 
    diffdate = parseInt((d2 - d1) / 1000 / 60 / 60 /24);  //把相差的毫秒数转换为天数 
    return diffdate;
}
//计算当前日期所在周的起始与结束日期
function beginAndEndDateOfWeek() {
   var now = new Date();
   var nowDayOfWeek = now.getDay();
   var nowDay = now.getDate()+1;
   var nowMonth = now.getMonth()+1;
   var nowYear = now.getYear();
   nowYear += (nowYear < 2000) ? 1900 : 0;
   var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek);
   return weekStartDate;
}
/**
* 计算指定年份中某月份有多少天
* @param year 指定年
* @param month 指定月
* return 天数
*/
function daysOfMonth(Year,Month) 
{ 
    Month--; 
    var d = new Date(Year,Month,1); 
    d.setDate(d.getDate()+32-d.getDate()); 
    return (32-d.getDate()); 
}

String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}

/**
*  获取指定日期date在days天后的日期
*  @param date 字符串 格式: '2012-2-12'
*  @param days 数值
*  return 日期对象
*/
function addDays (date,days){
       if(typeof(date)!='undefined' && date!=''){ 
	       date = date.replaceAll('-','/');
		   var now = new Date(date);
		   now.setTime(now.getTime()+(days*24*60*60*1000));
		   return now;
	   }else{
	       var now = new Date();
		   now.setTime(now.getTime()+(days*24*60*60*1000));
		   return now;
	   }
} 
/**
* 获取指定年份中指定周的起始时间
* @year 年份 格式：xxxx-xx-xx
* @weeks 一年的第几周 数值范围1-54
* return 日期对象
*/
function getBeginDateOfPerWeek(year,weeks){
	var weekDay = 1;
	var date = new Date(year,"0","1");
	var time = date.getTime();
	time+=(weeks-1)*7*24*3600000;
	date.setTime(time);
	weekDay%=7;
	var day = date.getDay();
	var time = date.getTime();
	var sub = weekDay-day;
	time+=sub*24*3600000;
	date.setTime(time);
	return date;
}

/**
* 获取指定年份中指定月的指定周的起始时间
* @year 年份 格式：xxxx-xx-xx
* @weeks 一年的第几周 数值范围1-54
* return 日期对象
*/
function getDateOfPerWeek(year,weeks){
	var weekDay = 1;
	var date = new Date(year,"0","1");
	var time = date.getTime();
	time+=(weeks-1)*7*24*3600000;
	date.setTime(time);
	weekDay%=7;
	var day = date.getDay();
	var time = date.getTime();
	var sub = weekDay-day;
	time+=sub*24*3600000;
	date.setTime(time);
	return date;
}



function getAllDateOfWeek(){
	var dates = [];
	var beginDate = beginAndEndDateOfWeek();
	dates.push(beginDate.getDate()+'号');
	var date = beginDate.getFullYear()+'-'+(beginDate.getMonth()+1)+'-'+beginDate.getDate();
	for(i=1;i<8;i++){
		dates.push(addDays(date,i).getDate()+'号');
	}
	return dates;
}
function loadJsonData(uri){
    var dataArr = [];
	$.ajax({  
		type:"GET",
		url:uri,
		dataType: "text",
		success: function(data, textStatus){
		    var jsonObj = $.parseJSON(data);
			var len = jsonObj.length;
			for(j = 0;j<len;j++)
			{
			    dataArr.push(jsonObj[j]);
			}
        },
		error : function(xhr,status,ex){
		    dataArr.push({});
		}
    });
	return dataArr;
}
function getDataStore(fields,uri){
    var store = new Ext.data.JsonStore({
        fields: fields,
        data: loadJsonData(uri)
    });
	return store;                       
}

function pressStyle(id){
  document.getElementById(id).style.backgroundImage="url(images/"+id+"_press.png)";
}
function outStyle(id){
  document.getElementById(id).style.backgroundImage="url(images/"+id+".png)";
}

function clickCanvasAll(){
	Global.MainController.totalMoneyIndex(globalTimeScope,globalTimeNumber,0,globalIndexLineBtn);
	Global.MainController.getSaleTotalDetailInIndex(globalTimeScope,globalTimeNumber,0,globalIndexLineBtn);
	//Ext.Msg.alert('获取总额数据');
}
function backIndex(){
  toHome();

	//Ext.Viewport.setActiveItem(0);

}
var globalTimeScope = 'week';
var globalTimeNumber = 1;
var globalIndexBtn = 0;//首页 【1-机票详情/2-酒店详情】 按钮点击标志位，缺省为0
var globalIndexLineBtn = 2;//首页 【1-订单量/2-金额】 按钮点击标志位，缺省为1
var globalFlightBtnFlag = 1;//机票详情页按钮标志位【1-舱位及折扣，2-航空公司，3-到达地】
var globalHotelBtnFlag = 1;//酒店详情页按钮标志位[１－星级，２－入住地]
function getDataByType_ticket(type){
	var legendNameArr;
	var FlightOrdsLine = Ext.getCmp('flightOrdsChart');
	var cancelOrdsLine = Ext.getCmp('cancelLineChart');
	var ticketTotalChart = Ext.getCmp('ticketTotalChart');
	
	if(type==='CW'){
		legendNameArr = ['头等商务舱','经济舱全价','经济舱折扣','','',''];		
		Global.MainController.updateLineDisplayStyle(FlightOrdsLine,'cabin',legendNameArr);
		Global.MainController.updateLineDisplayStyle(cancelOrdsLine,'cabin',legendNameArr);
		Global.MainController.updateLineDisplayStyle(ticketTotalChart,'cabin',legendNameArr);
		Global.MainController.getTicketDataForLine(globalTimeScope,globalTimeNumber,'cabin');
		Global.MainController.getTicketDetailDataForTabel(globalTimeScope,globalTimeNumber,'cabin');
		globalFlightBtnFlag = 1;
	}else if(type==='AC'){
		legendNameArr = ['国航','海航','深航','南航','东航','其他'];
		Global.MainController.updateLineDisplayStyle(FlightOrdsLine,'airline',legendNameArr);
		Global.MainController.updateLineDisplayStyle(cancelOrdsLine,'airline',legendNameArr);
		Global.MainController.updateLineDisplayStyle(ticketTotalChart,'airline',legendNameArr);
		Global.MainController.getTicketDataForLine(globalTimeScope,globalTimeNumber,'airLine');
		Global.MainController.getTicketDetailDataForTabel(globalTimeScope,globalTimeNumber,'airLine');
		globalFlightBtnFlag = 2;
	}else{
		legendNameArr = ['北京','天津','上海','广州','深圳','其他'];
		Global.MainController.updateLineDisplayStyle(FlightOrdsLine,'offpoint',legendNameArr);
		Global.MainController.updateLineDisplayStyle(cancelOrdsLine,'offpoint',legendNameArr);
		Global.MainController.updateLineDisplayStyle(ticketTotalChart,'offpoint',legendNameArr);
		Global.MainController.getTicketDataForLine(globalTimeScope,globalTimeNumber,'offPoint');
		Global.MainController.getTicketDetailDataForTabel(globalTimeScope,globalTimeNumber,'offPoint');
		globalFlightBtnFlag = 3;
	}
}
function btnChange(type){
	if(type==='cabin'){
		Ext.getCmp("cangweijizhekou").setCls("btn_con3_on");
		Ext.getCmp("hangkonggognsi").setCls("btn_con3");
		Ext.getCmp("daodadi").setCls("btn_con3");
	}else if(type==='airline'){
	    Ext.getCmp("cangweijizhekou").setCls("btn_con3");
		Ext.getCmp("hangkonggognsi").setCls("btn_con3_on");
		Ext.getCmp("daodadi").setCls("btn_con3");
	}else if(type==='offpoint'){
	    Ext.getCmp("cangweijizhekou").setCls("btn_con3");
		Ext.getCmp("hangkonggognsi").setCls("btn_con3");
		Ext.getCmp("daodadi").setCls("btn_con3_on");
	}else if(type==='star'){
		Ext.getCmp("xingji").setCls("btn_con3_on");
		Ext.getCmp("ruzhudi").setCls("btn_con3");
	}else if(type==='city'){
		Ext.getCmp("ruzhudi").setCls("btn_con3_on");
		Ext.getCmp("xingji").setCls("btn_con3");
	}else{
	    
	}
	
}