//返回数字的2位表示
function padZero(num) {
	return ((num <= 9) ? ("0" + num) : num);
}
//验证是否为数字和字母
function isalphanumber(str) {
	var result = str.match(/^[a-zA-Z0-9]+$/);
	if (result == null) {
		return false;
	} else {
		return true;
	}
}
function isURL(str) {
	if (str.search(/^(http:\/\/|https:\/\/|ftp:\/\/)[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/) != -1) {
		return true;
	} else {
		return false;
	}
}
//去空格
function trim(str) {
	var relt = str.replace(/(^\s*)|(\s*$)/g, "");
	return relt;
}
function isEmail(strEmail, msg) {
	if (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1) {
		return true;
	} else {
		alert(msg);
		return false;
	}
}
function isInteger(strNum, msg) {
	if (strNum.search(/^(0|[1-9]\d*)$/) != -1) {
		return true;
	} else {
		if(typeof(msg)=='string'){
				alert(msg);
		}
		
		return false;
	}
}
function isIntegerSegmentWithComma(strNum, msg) {
	if (strNum.search(/^(((0|[1-9]\d*)+,)?)+(0|[1-9]\d*)$/) != -1) {
		return true;
	} else {
		// alert(msg);
		return false;
	}
}
function isDouble(strNum, msg) {
	if (strNum.search(/^(0|[1-9]\d*)\.(\d+)$/) != -1) {
		return true;
	} else {
		// alert(msg);
		return false;
	}
}
function isNumber(strNum, msg) {
	if (strNum.search(/^(0|[1-9]\d*)$|^(0|[1-9]\d*)\.(\d+)$/) != -1) {
		return true;
	} else {
		// alert(msg);
		return false;
	}
}

//针对2个表单有重复的增加方法
function addUnique(iSource, nSource, iDest, nDest) {
	if (iSource.value != "") {
		var iSourceArr = iSource.value.split(",");
		var nSourceArr = nSource.value.split("\n");
		var iDestArr = iDest.value.split(",");
		var tempJ = 0;
		for (var i = 0; i < iSourceArr.length; i++) {
			for (var j = 0; j < iDestArr.length; j++) {
				tempJ = j + 1;
				//alert("iSourceArr[i]=" + iSourceArr[i]);
				//alert("iDestArr[j]=" + iDestArr[j]);
				if (iSourceArr[i] == iDestArr[j]) {
					break;
				}
			}
			//alert("tempJ=" + tempJ);
			//alert("iDestArr.length=" + iDestArr.length);
			if (tempJ == iDestArr.length) {
				iDest.value += iSourceArr[i] + ",";
				nDest.value += nSourceArr[i] + "\n";
			}
			tempJ = 0;
		}
	}
}

//判断两个表单是否有重复记录，返回用"n"隔开的重复记录名。
function hasRepeat(iSource, nSource, iDest, nDest) {
	var returnStr = "";
	if (iSource.value != "") {
		var iSourceArr = iSource.value.split(",");
		var nSourceArr = nSource.value.split("\n");
		var iDestArr = iDest.value.split(",");
		var tempJ = 0;
		for (var i = 0; i < iSourceArr.length; i++) {
			for (var j = 0; j < iDestArr.length; j++) {
				tempJ = j + 1;
				//alert("iSourceArr[i]=" + iSourceArr[i]);
				//alert("iDestArr[j]=" + iDestArr[j]);
				if (iSourceArr[i] == iDestArr[j]) {
					returnStr += nSourceArr[i] + "\n";
					break;
				}
			}
			//alert("tempJ=" + tempJ);
			//alert("iDestArr.length=" + iDestArr.length);
			tempJ = 0;
		}
	}
	return returnStr;
}
function checkboxCount(obj_box) {
	check = 0;
	if (obj_box + "." != "undefined.") {
		if (obj_box.length + "." != "undefined.") {
			for (i = 0; i < obj_box.length; i++) {
				if (obj_box[i].checked) {
					check = check + 1;
				}
			}
		} else {
			if (obj_box.checked) {
				check = 1;
			}
		}
	}
	return check;
}
function checkall(obj_box) {
	if (obj_box + "." != "undefined.") {
		if (obj_box.length + "." != "undefined.") {
			for (i = 0; i < obj_box.length; i++) {
				if (!obj_box[i].disabled) {
					obj_box[i].checked = "checked";
				}
			}
		} else {
			if (!obj_box.disabled) {
				obj_box.checked = "checked";
			}
		}
	}
}
function checknull(obj_box) {
	if (obj_box + "." != "undefined.") {
		if (obj_box.length + "." != "undefined.") {
			for (i = 0; i < obj_box.length; i++) {
				obj_box[i].checked = "";
			}
		} else {
			obj_box.checked = "";
		}
	}
}
function checkAllOrNull(sign_obj_box, obj_box) {
	if (sign_obj_box.checked) {
		checkall(obj_box);
	} else {
		checknull(obj_box);
	}
}
function isNaturalNumber(strNum, msg) {
	if (strNum.search(/^([1-9]\d*)$/) != -1) {
		return true;
	} else {
		alert(msg);
		return false;
	}
}
function popWindowCenter(htmlurl, name, wid, heig) {
	scWidth = screen.width / 2 - wid / 2;
	scHeight = screen.height / 2 - heig / 2;
	window.open(htmlurl, name, "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no," + ",left=" + scWidth + ",top=" + scHeight + ",width=" + wid + ",height=" + heig);
}
function popWindowCenterNormal(htmlurl, name, wid, heig) {
	scWidth = screen.width / 2 - wid / 2;
	scHeight = screen.height / 2 - heig / 2;
	window.open(htmlurl, name, "toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes," + ",left=" + scWidth + ",top=" + scHeight + ",width=" + wid + ",height=" + heig);
}
function popWindowCenterCanScroll(htmlurl, name, wid, heig) {
	scWidth = screen.width / 2 - wid / 2;
	scHeight = screen.height / 2 - heig / 2;
	window.open(htmlurl, name, "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes," + ",left=" + scWidth + ",top=" + scHeight + ",width=" + wid + ",height=" + heig);
}
function insert(oDest, name, value) {
	var oNewNode = document.createElement("option");
	oNewNode.innerHTML = name;
	oNewNode.value = value;
	addUniqueNode(oNewNode, oDest);
}
function addUniqueNode(node, oDest) {
	var nodeExist = false;
	if (oDest.hasChildNodes()) {
		for (var y = 0; y < oDest.childNodes.length; y++) {
			if (node.value == oDest.childNodes[y].value) {
				nodeExist = true;
				break;
			}
		}
	}
	if (!nodeExist) {
		var newNode = node.cloneNode(true);
		oDest.appendChild(newNode);
	}
}
function removeSelected(oSelect) {
	for (i = oSelect.childNodes.length - 1; i >= 0; i--) {
		var node = oSelect.childNodes[i];
		if (node.selected) {
			oSelect.removeChild(node);
		}
	}
}
function newremoveSelected(oSelect){
	
$(oSelect).find("option:selected").remove();


} 
function removeAll(oSelect) {
	for (i = oSelect.childNodes.length - 1; i >= 0; i--) {
		oNode = oSelect.childNodes[i];
		oSelect.removeChild(oNode);
	}
}
function newRemoveAll(oSelect){
	
	oSelect.innerHTML="";
	
}
function ArrayElement(id, name, type) {
	this.id = id;
	this.name = name;
	this.type = type;
}
function buildReturnValue(oSelect) {
	var ret = new Array();
	for (var x = 0; x < oSelect.childNodes.length; x++) {
		if (oSelect.childNodes[x].value == null) {
			continue;
		}
		var o = new ArrayElement(oSelect.childNodes[x].value, oSelect.childNodes[x].innerHTML, 0);
		ret.push(o);
	}
	return ret;
}
function getChecked(obj_box) {
	var str = "";
	for (var i = 0; i < obj_box.length; i++) {
		if (obj_box[i].checked) {
			str += obj_box[i].value + ",";
		}
	}
	str = str.substring(0, str.length - 1);
	return str;
}
//选择BOX 是从功能到模块
function chooseChecked(obj_box) {
	for (var i = 0; i < obj_box.length; i++) {
		if ((obj_box[i].checked)) {
			for (var j = 0; j < obj_box[i].parentNode.parentNode.childNodes.length; j++) {
				//获取TR里的第一个TD 执行完第一个TD然后跳出
				if (obj_box[i].parentNode.parentNode.childNodes[j].tagName == "TD") {
					for (var t = 0; t < obj_box[i].parentNode.parentNode.childNodes[j].childNodes.length; t++) {
						if (obj_box[i].parentNode.parentNode.childNodes[j].childNodes[t].type == "checkbox") {
							obj_box[i].parentNode.parentNode.childNodes[j].childNodes[t].checked = true;
						}
					}
					break;
				}
			}
		}
	}
}
//取消BOX 是从功能到模块
function cancelChecked(obj_box) {
	for (var i = 0; i < obj_box.length; i++) {
		if (!obj_box[i].checked) {
			//获得当前所在的列对象
			var obj_td = obj_box[i].parentNode;
			
			//记录有多少没有被选种的
			var check_count = 0;
			for (var j = 0; j < obj_td.childNodes.length; j++) {			
				//判断节点类型
				if (obj_td.childNodes[j].type == "checkbox") {
					if (!obj_td.childNodes[j].checked) {
						check_count++;
					}
				}
			}
			var count = 0;
			if (window.XMLHttpRequest) {
				count = (obj_td.childNodes.length - 1) / 2;
			} else {
				if (window.ActiveXObject) {
					count = obj_td.childNodes.length / 2;
				}
			}
			//如果没有被选种的和TD下的BOX一样多，表示全都被取消了这样 模块的BOX也对应取消
			if (check_count == count) {
				for (var j = 0; j < obj_box[i].parentNode.parentNode.childNodes.length; j++) {
					if (obj_box[i].parentNode.parentNode.childNodes[j].tagName == "TD") {
						for (var t = 0; t < obj_box[i].parentNode.parentNode.childNodes[j].childNodes.length; t++) {
							if (obj_box[i].parentNode.parentNode.childNodes[j].childNodes[t].type == "checkbox") {
								obj_box[i].parentNode.parentNode.childNodes[j].childNodes[t].checked = false;
							}
						}
						break;
					}
				}
			}
		}
	}
}


//选择BOX 是从模块到功能 一个模块取消就取消他所能直接的功能BOX
function chooseModuleChecked(obj_box, index) {
	if ((obj_box[index].checked)) {
		//获得当前BOX所在的TD
		var obj_td = obj_box[index].parentNode;

		//获得最后1个TD的下标
		var td_index = 0;
		if (window.XMLHttpRequest) {
			td_index = obj_td.parentNode.childNodes.length - 2;
		} else {
			if (window.ActiveXObject) {
				td_index = obj_td.parentNode.childNodes.length - 1;
			}
		}
		for (var j = 0; j < obj_td.parentNode.childNodes[td_index].childNodes.length; j++) {
			if (obj_td.parentNode.childNodes[td_index].childNodes[j].type == "checkbox") {
				if (obj_td.parentNode.childNodes[td_index].childNodes[j].disabled == false) {
					obj_td.parentNode.childNodes[td_index].childNodes[j].checked = true;
				}
			}
		}
	}
}

//取消BOX 是从模块到功能
function cancelModuleChecked(obj_box, index) {
	if ((!obj_box[index].checked)) {
		//获得当前BOX所在的TD
		var obj_td = obj_box[index].parentNode;

		//获得最后1个TD的下标
		var td_index = 0;
		if (window.XMLHttpRequest) {
			td_index = obj_td.parentNode.childNodes.length - 2;
		} else {
			if (window.ActiveXObject) {
				td_index = obj_td.parentNode.childNodes.length - 1;
			}
		}
		for (var j = 0; j < obj_td.parentNode.childNodes[td_index].childNodes.length; j++) {
			if (obj_td.parentNode.childNodes[td_index].childNodes[j].type == "checkbox") {
				if (obj_td.parentNode.childNodes[td_index].childNodes[j].disabled == false) {
					if (obj_td.parentNode.childNodes[td_index].childNodes[j].checked == true) {
						obj_td.parentNode.childNodes[td_index].childNodes[j].checked = false;
					}
				}
			}
		}
	}
}

//判断模块的BOX是否显示
function isCheckModuleBox() {
	var obj_td = document.getElementsByName("functionTd");
	for (var i = 0; i < obj_td.length; i++) {
		//功能BOX的disabled和check有多少个
		var check_count = 0;
		var disabled_count = 0;
		for (var j = 0; j < obj_td[i].childNodes.length; j++) {
			if (obj_td[i].childNodes[j].type == "checkbox") {
				//alert(obj_td[i].childNodes[j].disabled) ;
				if (obj_td[i].childNodes[j].disabled == true) {
					if (obj_td[i].childNodes[j].checked == true) {
						check_count++;
					}
					disabled_count++;
				}
			}
		}
		
		//在一个TD里一共有多少个功能BOX的CHECKBOX
		var count = 0;
		if (window.XMLHttpRequest) {
			count = (obj_td[i].childNodes.length - 1) / 2;
		} else {
			if (window.ActiveXObject) {
				count = obj_td[i].childNodes.length / 2;
			}
		}
		if ((check_count != 0) || (disabled_count == count)) {
			for (var j = 0; j < obj_td[i].parentNode.childNodes.length; j++) {
				if (obj_td[i].parentNode.childNodes[j].tagName == "TD") {
					for (var t = 0; t < obj_td[i].parentNode.childNodes[j].childNodes.length; t++) {
						obj_td[i].parentNode.childNodes[j].childNodes[t].disabled = true;
						if (window.ActiveXObject) {
							break;
						}
					}
					break;
				}
			}
		}
	}
}

//全选BOX级联到功能
function checkBoxAll(obj_box, obj_box_function) {
	if (obj_box + "." != "undefined.") {
		if (obj_box.length + "." != "undefined.") {
			for (i = 0; i < obj_box.length; i++) {
				if (!obj_box[i].disabled) {
					obj_box[i].checked = "checked";
				}
			}
		} else {
			if (!obj_box.disabled) {
				obj_box.checked = "checked";
			}
		}
	}
	if (obj_box_function + "." != "undefined.") {
		if (obj_box_function.length + "." != "undefined.") {
			for (i = 0; i < obj_box_function.length; i++) {
				if (!obj_box_function[i].disabled) {
					obj_box_function[i].checked = "checked";
				}
			}
		} else {
			if (!obj_box_function.disabled) {
				obj_box_function.checked = "checked";
			}
		}
	}
}
function checkBoxNull(obj_box, obj_box_function) {
	if (obj_box + "." != "undefined.") {
		if (obj_box.length + "." != "undefined.") {
			for (i = 0; i < obj_box.length; i++) {
				if (!obj_box[i].disabled) {
					obj_box[i].checked = "";
				}
			}
		} else {
			if (!obj_box[i].disabled) {
				obj_box.checked = "";
			}
		}
	}
	if (obj_box_function + "." != "undefined.") {
		if (obj_box_function.length + "." != "undefined.") {
			for (i = 0; i < obj_box_function.length; i++) {
				if (!obj_box_function[i].disabled) {
					obj_box_function[i].checked = "";
				}
				if (obj_box_function[i].checked) {
					for (var j = 0; j < obj_box_function[i].parentNode.parentNode.childNodes.length; j++) {
						if (obj_box_function[i].parentNode.parentNode.childNodes[j].tagName == "TD") {
							for (var t = 0; t < obj_box_function[i].parentNode.parentNode.childNodes[j].childNodes.length; t++) {
								if (obj_box_function[i].parentNode.parentNode.childNodes[j].childNodes[t].type == "checkbox") {
									obj_box_function[i].parentNode.parentNode.childNodes[j].childNodes[t].checked = true;
								}
							}
							break;
						}
					}
				}
			}
		} else {
			if (!obj_box_function[i].disabled) {
				obj_box_function.checked = "";
			}
			if (obj_box_function[i].checked) {
				for (var j = 0; j < obj_box_function[i].parentNode.parentNode.childNodes.length; j++) {
					if (obj_box_function[i].parentNode.parentNode.childNodes[j].tagName == "TD") {
						for (var t = 0; t < obj_box_function[i].parentNode.parentNode.childNodes[j].childNodes.length; t++) {
							if (obj_box_function[i].parentNode.parentNode.childNodes[j].childNodes[t].type == "checkbox") {
								obj_box_function[i].parentNode.parentNode.childNodes[j].childNodes[t].checked = true;
							}
						}
						break;
					}
				}
			}
		}
	}
}
//显示DIV窗口
/**
*根据DIV的宽度和高度和图片路径创建DIV窗口
*
*@param dialogWidth 宽度
*@param dialogHeight 高度
*/
function showDialog(dialogWidth, dialogHeight, imgUrl) {
	msgWidth = dialogWidth;
	msgHeight = dialogHeight;
	//边框颜色
	bordercolor = "#c51100";
	
	//创建DIV元素
	var msgObj = document.createElement("div");
	msgObj.setAttribute("id", "msgDiv");
	msgObj.setAttribute("align", "center");
	msgObj.setAttribute("valign", "center");
	msgObj.style.background = "white";
	msgObj.style.border = "1px solid " + bordercolor;
	msgObj.style.position = "absolute";
	//设置DIV的大小区域
	msgObj.style.left = "40%";
	msgObj.style.top = "40%";
	msgObj.style.font = "12px/1.6em Verdana, Geneva, Arial, Helvetica, sans-serif";
	msgObj.style.marginLeft = "-225px";
	msgObj.style.marginTop = -75 + document.documentElement.scrollTop + "px";
	
	//设置DIV的宽度和高度
	msgObj.style.width = msgWidth + "px";
	msgObj.style.height = msgHeight + "px";
	msgObj.style.textAlign = "center";
	msgObj.style.lineHeight = "25px";
	msgObj.style.zIndex = "10001";
	
	//创建H4元素
	var title = document.createElement("h4");
	title.setAttribute("id", "msgTitle");
	title.setAttribute("align", "right");
	title.style.margin = "0";
	title.style.padding = "3px";
	title.style.background = bordercolor;
	title.style.filter = "progid:DXImageTransform.Microsoft.Alpha(startX=20, startY=20, finishX=100, finishY=100,style=1,opacity=75,finishOpacity=100);";
	title.style.opacity = "0.75";
	title.style.border = "1px solid " + bordercolor;
	title.style.height = "18px";
	title.style.font = "12px Verdana, Geneva, Arial, Helvetica, sans-serif";
	title.style.color = "white";
	title.style.cursor = "pointer";
	title.innerHTML = "\u5173\u95ed";
	//增加关闭功能
	title.onclick = function () {
		document.getElementById("msgDiv").removeChild(title);
		document.body.removeChild(msgObj);
	};
	//在主体力增加一个DIV元素
	document.body.appendChild(msgObj);
	document.getElementById("msgDiv").appendChild(title);
	
	//创建一个图片元素
	var img = document.createElement("img");
	img.setAttribute("id", "msgImg");
	img.setAttribute("src", imgUrl);
	document.getElementById("msgDiv").appendChild(img);
}
function animationPrompt(meesg) {
	jQuery.fn.extend({myanimation:function (speed) {
		var t = $(this);
		if (t.css("display") == "none") {
			t.show(speed, function () {
				t.hide(speed, function () {
					t.show(speed);
				});
			});
		} else {
			t.hide(speed, function () {
				t.show(speed, function () {
					t.hide(speed);
				});
			});
		}
	}});
	$.prompt(meesg, {show:"myanimation"});
}

//验证中文字母数字
function checkReg(meg) {
	var temp =  /^(\w|[\u4E00-\u9FA5])*$/;
	return temp.match(meg);
}
function checkFile(meg) {
	var temp = /[~!@#\$%\^&\*\(\)\-_\+=]+/;
	return temp.test(meg);
}
//search默認字符串
function searchTextStyle(inputElement, msg) {
	if (inputElement.value == "") {
		inputElement.value = msg;
	}
	inputElement.style.color = "#949494";
	inputElement.onfocus = function () {
		inputElement.value = "";
		inputElement.style.color = "#0A0102";
	};
}
function checkURL(str_url){
			  var strRegex = "^((https|http|ftp|rtsp|mms)?://)" 
			  + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@ 
			        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184 
			        + "|" // 允许IP和DOMAIN（域名）
			        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www. 
			        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名 
			        + "[a-z]{2,6})" // first level domain- .com or .museum 
			        + "(:[0-9]{1,5})?" // 端口- :80 
			        + "((/?)|" // a slash isn't required if there is no file name 
			        + "(//?[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"; 
			        var re=new RegExp(strRegex); 
			  //re.test()
			        if (re.test(str_url)){
			            return (true); 
			        }else{ 
			            return (false); 
			        }
}


//add by zhuzhigang
String.prototype.replaceAll = function(s1,s2) {
    return this.replace(new RegExp(s1,"gm"),s2);
}

function Map(){

	this.elements = new Array();
	
	this.size = function(){
		return this.elements.length;		
	}
	
	this.put = function(_key,_value){
		for(i=0;i<this.elements.length;i++){
			if(this.elements[i].key==_key){
				this.elements[i].value=_value;
				return;
			}
		}
		this.elements.push({key:_key,value:_value});
	}
	
	this.get = function(_key){
		try{
			for(i=0;i<this.elements.length;i++){
				if(this.elements[i].key==_key){
					return this.elements[i].value;
				}
			}
		}catch (e){
			return null;
		}
	}
	
	this.remove = function(_key){
		var bln =  false;
		try{
			for(var i=0; i<this.elements.length; i++){
				if (this.elements[i].key == _key){
					this.elements.splice(i, 1);
					return true;
				}
			}
		}catch(e){
			bln = false;
		}
		return bln;
	}

	this.removeAll = function(){
		for(var i=0; i<this.elements.length; i++){
			this.elements.splice(i, 1);
		}
	}
}

var SystemJS = {};
var userAgent = navigator.userAgent.toLowerCase();
var systemArr;
(systemArr = userAgent.match(/msie ([\d.]+)/)) ? SystemJS.ie = systemArr[1] :
(systemArr = userAgent.match(/firefox\/([\d.]+)/)) ? SystemJS.firefox = systemArr[1] :
(systemArr = userAgent.match(/chrome\/([\d.]+)/)) ? SystemJS.chrome = systemArr[1] :
(systemArr = userAgent.match(/opera.([\d.]+)/)) ? SystemJS.opera = systemArr[1] :
(systemArr = userAgent.match(/version\/([\d.]+).*safari/)) ? SystemJS.safari = systemArr[1] : 0;



Date.prototype.Format = function(formatStr)  
{  
    var str = formatStr;
    var actuallyMonth = this.getMonth() + 1;
    var Week = ['日','一','二','三','四','五','六']; 
 
    str=str.replace(/yyyy|YYYY/,this.getFullYear());  
    str=str.replace(/yy|YY/,(this.getYear() % 100)>9?(this.getYear() % 100).toString():'0' + (this.getYear() % 100));  
 
    str=str.replace(/MM/,actuallyMonth>9?actuallyMonth:'0' + actuallyMonth);  
    str=str.replace(/M/g,actuallyMonth);  
 
    str=str.replace(/w|W/g,Week[this.getDay()]);  
 
    str=str.replace(/dd|DD/,this.getDate()>9?this.getDate().toString():'0' + this.getDate());  
    str=str.replace(/d|D/g,this.getDate());  
 
    str=str.replace(/hh|HH/,this.getHours()>9?this.getHours().toString():'0' + this.getHours());  
    str=str.replace(/h|H/g,this.getHours());  
    str=str.replace(/mm/,this.getMinutes()>9?this.getMinutes().toString():'0' + this.getMinutes());  
    str=str.replace(/m/g,this.getMinutes());  
 
    str=str.replace(/ss|SS/,this.getSeconds()>9?this.getSeconds().toString():'0' + this.getSeconds());  
    str=str.replace(/s|S/g,this.getSeconds());  
 
    return str;  
}

Date.prototype.DateAdd = function(strInterval, Number) {  
    var dtTmp = this; 
    switch (strInterval) {  
        case 's' :return new Date(Date.parse(dtTmp) + (1000 * Number)); 
        case 'n' :return new Date(Date.parse(dtTmp) + (60000 * Number)); 
        case 'h' :return new Date(Date.parse(dtTmp) + (3600000 * Number)); 
        case 'd' :return new Date(Date.parse(dtTmp) + (86400000 * Number)); 
        case 'w' :return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number)); 
        case 'q' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number*3, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()); 
        case 'm' :return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()); 
        case 'y' :return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds()); 
    } 
}

Date.prototype.parseDate = function(strDate) {
	var newDate = new Date();
	var strDateArr = strDate.split(" ");
	var preDateArr = strDateArr[0].split("-");
	newDate.setFullYear(preDateArr[0]);
	newDate.setMonth(preDateArr[1]-1);
	newDate.setDate(preDateArr[2]);
	
	var secDateArr = strDateArr[1].split(":");
	newDate.setHours(secDateArr[0]);
	newDate.setMinutes(secDateArr[1]);
	return newDate;
}


function checkTimeout(urlStr) {
	var timeout = false;
	var date = new Date();
	$.ajax({
		type : "POST",
		async : false,
		url : urlStr,
		success : function(data) {
			if(data == '1'){
				timeout = true;
			}
		}
	});
	return timeout;
}

