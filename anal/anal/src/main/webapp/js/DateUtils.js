//获得当天的日期，格式是2006-01-03
function getTodayDate() {
	var todayDate = new Date;
	var year = todayDate.getFullYear();    // 获取年
	var month = todayDate.getMonth() + 1;    // 获取月
	var date = todayDate.getDate();    // 获取日
	
	return year + "-" + padZero(month) + "-" + padZero(date);
}

//计算天数差的函数，通用。DateDiff("2004-03-02", "2004-02-25")=6
function DateDiff(sDate1, sDate2) {  //sDate1和sDate2是2002-12-18格式
	var aDate, oDate1, oDate2, iDays;
	aDate = sDate1.split("-");
	oDate1 = new Date(aDate[1] + "-" + aDate[2] + "-" + aDate[0]);  //转换为12-18-2002格式
	aDate = sDate2.split("-");
	oDate2 = new Date(aDate[1] + "-" + aDate[2] + "-" + aDate[0]);
	iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 / 24);  //把相差的毫秒数转换为天数
	return iDays;
}

//日期时间操作的工具js

//将只有日期的字符串转换为Date类型
function DATEStrToDate(dateStr){
	
	var yearStr = dateStr.substring(0,dateStr.indexOf("-"));
	
	var monthStr = dateStr.substring(dateStr.indexOf("-")+1,dateStr.lastIndexOf("-"));
	
	var dayStr = dateStr.substring(dateStr.lastIndexOf("-")+1,dateStr.length);
	
	var newDate = monthStr + "/" + dayStr + "/" + yearStr;
	
	return Date.parse(newDate);
}


//将包含日期和时间的字符串转换为Date类型
function TIMEStrToDate(timeStr){
	
	var yearStr = timeStr.substring(0,timeStr.indexOf("-"));
	
	var monthStr = timeStr.substring(timeStr.indexOf("-")+1,timeStr.lastIndexOf("-"));
	
	var dayStr = timeStr.substring(timeStr.lastIndexOf("-")+1,timeStr.indexOf(" "));
	
	var newDate = monthStr + "/" + dayStr + "/" + yearStr;
	
	return Date.parse(newDate);
}


//获得当前时间为00:00:00的日期形式
function getNowDate(){
	
	var nowDate = new Date();
	
	var newDate = (nowDate.getMonth() + 1) + "/" + nowDate.getDate() + "/" + nowDate.getFullYear();
	
	return Date.parse(newDate);
}