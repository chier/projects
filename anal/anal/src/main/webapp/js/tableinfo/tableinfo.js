/**
 * 变量： 全局变量集合
 *  	保存全局用到的配置变量
 */
var tableInfoConfig = {
	width  		: 561,
	height 		: 455,
	maxlength	: 256
};




/**
 * 方法： 点击打开 url 地址
 * 参数： 无
 * 返回值： 无
 */
function checkweburlsubmit() {
	var str_url = document.getElementById("url").value
					.replace(/^\s+|\s+$/g, "");
	// document.getElementById("url").value = str_url;
	if (checkURL(str_url.toLowerCase())) {
		var windowSizeType = $("input[name$='tinfo.windowSizeType'][checked]").val();
		// 打开新窗口
		newWindowOpen(str_url,windowSizeType);
	} else {
		document.getElementById("errorurl").innerHTML = 
			"<font color=\"#9d0c0c\">\u683c\u5f0f\u4e0d\u6b63\u786e</font>";
		return false;
	}
}

/**
 * 方法： 显示打开新窗口
 * 参数：
 *     url 打开路径
 *     windowSizeType 打开类型，如果为 0 表示默认打开。
 * 							如果为 1  表示根据参数打开。
 * 返回值：  无
 */
function newWindowOpen(str_url,windowSizeType){
	var url;
	var _strUrl = str_url.toLowerCase();
	if(_strUrl.substring(0, 7) == "http://" || 
		_strUrl.substring(0, 8) == "https://" || 
		_strUrl.substring(0, 6) == "mms://" || 
		_strUrl.substring(0, 6) == "ftp://" || 
		_strUrl.substring(0, 7) == "rtsp://"){
		url = str_url;
	}else{
		url = "http://" + str_url;
	}
	
	if(windowSizeType == 0){
		window.open(url, "newwindow", "width=" + 
			tableInfoConfig.width +",height=" + tableInfoConfig.height);
	}else{
		window.open(url);
	}
}
/**
 * 方法： 返回到浏览页面
 * 参数： backHref 路径 url
 * 返回： 无
 */
function goBack(backHref){
	if(backHref){
		window.location.href = backHref;
	}
}

/**
 * 方法： 返回描述字行，当超过最大字符时，不提示.
 * 参数： 无
 * 返回: 无
 */
function bodylength(){
	var conlength=document.getElementById("descr").value.length;
	if(conlength > tableInfoConfig.maxlength){
		var txt=document.getElementById("descr").value;
		document.getElementById("descr").value=txt.substring(0,tableInfoConfig.maxlength);
	}
}
/**
 * 方法：清空异常信息
 * 参数： 无
 * 返回： 无
 */
function cleanError(){
	document.getElementById("errortabname").innerHTML='&nbsp;';
	document.getElementById("errorurl").innerHTML='&nbsp;';
}


/**
 * 方法：验证只能是中英文
 * 参数：无
 * 返回：无
 */
function checkName(actionValue){
	var str_url=document.getElementById("url").value.toLowerCase().replace(/^\s+|\s+$/g,"");
	var tabvalue=document.getElementById("tabname").value;
	var s = /^[0-9a-zA-Z\u4e00-\u9fa5]{1,17}$/; 
	var english = /^[a-zA-Z]{1,17}$/; 
	var chinese = /^[\u4e00-\u9fa5]{1,17}$/; 
	var number = /^[0-9]{1,17}$/
	var tableng=document.getElementById("tabname").value.length;
	if(tableng<=0){
    	document.getElementById("errortabname").innerHTML='<font color="#9d0c0c">标题不能为空</font>';
		return false;
    }
	if(tabvalue.search(english)!=-1 && english.test(tabvalue)){
    	if(tableng>16){
			document.getElementById("errortabname").innerHTML='<font color="#9d0c0c">英文标题最多16个字符!</font>';
		    return false;
		}
	}
	if(tabvalue.search(number)!=-1 && number.test(tabvalue)){
    	if(tableng>16){
			document.getElementById("errortabname").innerHTML='<font color="#9d0c0c">数字标题最多16个字符!</font>';
		    return false;
		}
	}
	if(tabvalue.search(chinese)!=-1 && chinese.test(tabvalue)){
		if(tableng>8){
		    document.getElementById("errortabname").innerHTML='<font color="#9d0c0c">中文标题最多8个汉字!</font>';
		     return false;
		}
	}
	if(tabvalue.search(s)!=-1 && s.test(tabvalue)){
		var urllength = document.getElementById("url").value.length;
		if(urllength<=0){
		    document.getElementById("errorurl").innerHTML='<font color="#9d0c0c">链接不能为空</font>';
		    return false;
		}else if(!checkURL(str_url)){
		    document.getElementById("errorurl").innerHTML='<font color="#9d0c0c">格式不正确</font>';
		    return false;
		}else{
		    document.forms[0].action=actionValue;
		    return true;
		}
	}else{ 
         document.getElementById("errortabname").innerHTML='<font color="#9d0c0c">标题只能是中英文和数字</font>';
         return false;
	}
	if(actionValue){
		document.forms[0].action=actionValue;
	}
}







/**********************   保留方法  ****************************************/
/**
 * 保留方法 未用到。
 */
function IsURL(urlString){
	var str_url=document.getElementById("url").value;
	regExp = /(http[s]?|ftp):\/\/[^\/\.]+?\..+\w$/i;
	if (str_url.match(regExp)){
		window.open (str_url);
	}else{
		var txt ='格式不正确';
		$.prompt(txt,{buttons:{确定:false}}); 
	}
}

/*
function cleckEnglish(value){
	var english1 = new RegExp("[a-zA-Z]{2,18}$");
	<%--var chinese1= /^[\u4e00-\u9fa5]{1,8}$/;--%>
	if(english1.test(value)) {
		alert("tttttttttt");
		if(tableng>16) {
			alert(">16");
		}
		return true;
	}
	return false;
}
*/