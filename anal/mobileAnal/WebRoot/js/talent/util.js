Array.prototype.ttCons = function(e) {
	i = 0;
	for (; i < this.length && this[i] != e; i++);
	return !(i == this.length);
};
/**
 * 将source的属性复制给destination
 * @param {} destination
 * @param {} source
 */
talent.Util.copy = function(dest, src) {
	//destination, source
	if (src && dest) {
		for (var propertyName in src) {
			dest[propertyName] = src[propertyName];
		}
	}
};

talent.Util.setStyle = function(obj, styles) {
	if (styles) {
		for (var styleName in styles) {
			obj.style[styleName] = styles[styleName];
		}
	}
};

/**
 * 
 * @param {} method
 * @param {} owner
 * @param {} args
 */
talent.Util.invoke = function(method, owner, args) {
	method.apply(owner, args);
};

/**
 * 
 * @param {} formElement
 * @return {}
 */
talent.Util.formToQueryString = function(formElement) {
//	var allStr = [""];
//	if (formElement) {
//		var formElements = formElement.elements;
//		var inputObj;
//		if (formElements) {
//			for (var i = 0; i < formElements.length; i++) {
//				inputObj = formElements[i];
//				if (inputObj.name) {
//					allStr.push(inputObj.name + "=" + encodeURIComponent(inputObj.value));
//				}
//			}
//		}
//	}
//	return allStr.join("&");
	return $(formElement).serialize();
};

talent.Util.toJson = function(obj) {
	return JSON.stringify(obj);
};

/**
 * 
 * @param {} selectElement
 * @param {} isClearBeforeUpdate
 * @param {} items
 * @param {} valueFieldName
 * @param {} labelFieldName
 * @param {} isFilterNullValue
 * @param {} optionClassName
 * @param {} optionProperties
 * @param {} optionPropertiesFromSrc
 */
talent.Util.updateOption = function(selectElement, isClearBeforeUpdate, items,
	valueFieldName, labelFieldName, isFilterNullValue, optionClassName,
	optionProperties, optionPropertiesFromSrc, addNull) {
	if (isClearBeforeUpdate == true) {
		selectElement.options.length = 0;
	}
	if(!items){
		return;
	}
	
	if (addNull) {
		var op1 = new Option("", "");
		selectElement.options.add(op1); 
	}
	
	var pusheddata = [];
	
	for (var data = 0; data < items.length; data++) {
		if (isFilterNullValue
				&& (items[data][valueFieldName] == "" || items[data][labelFieldName] == "")) {
			continue;
		}
		
		if (pusheddata.ttCons(items[data][valueFieldName])){
			continue;
		}
	
		var op = new Option(items[data][labelFieldName], items[data][valueFieldName]);
		op.title = items[data][labelFieldName];
		talent.Util.copy(op, optionProperties);
		selectElement.options.add(op); 
		

		if (optionClassName) {
			op.className = optionClassName;
		}
		if (optionPropertiesFromSrc) {
			for (var i = 0; i < optionPropertiesFromSrc.length; i++) {
				op[optionPropertiesFromSrc[i]] = items[data][optionPropertiesFromSrc[i]];
			}
		}
		
		pusheddata.push(items[data][valueFieldName]);
	}
};

/**
 * 
 * @param {} oTarget 
 * @param {} sEventType such as "click"
 * @param {} fnHandler
 */
talent.Util.addEventHandler = function(oTarget, sEventType, fnHandler) {
    if (oTarget.addEventListener) {
        oTarget.addEventListener(sEventType, fnHandler, false);   
    } else if (oTarget.attachEvent) {   
        oTarget.attachEvent("on" + sEventType, fnHandler);   
    } else {   
        oTarget["on" + sEventType] = fnHandler;   
    }
};

talent.Util.addClass = function(target, _className) {
    tClassName = target.className;
    tClassName = " " + tClassName + " ";
    if (tClassName.indexOf(" " + _className + " ") == -1) {
    	target.className = tClassName + _className;
    }
};

talent.Util.removeClass = function(target, name){
	var oClass = target.className;
	var reg = "/\\b"+name+"\\b/g";
	target.className = oClass ? oClass.replace(eval(reg),'') : '';
};
//date t ts
/**
 * 
 * @param {} element html element
 * @param {} type ["datetime","date","d","t","ts","time","timestamp"]
 * @param {} format ["yyyy-MM-dd HH:mm:ss",...]
 */
talent.Util.setupDate = function(element, type, format){
	var ifFormat = null;     //"yyyy-MM-dd";
	if(format) {
		ifFormat = format;
	} else {
		if (type == "datetime" || type == "ts" || type == "timestamp") {
			ifFormat = "yyyy-MM-dd HH:mm:ss";
		} else if (type == "date" || type == "d") {
			ifFormat = "yyyy-MM-dd";
		} else if(type == "t" || type == "time"){
			ifFormat = "HH:mm:ss";
		}
	}
	
	var dd = function(_config)
	{
		this.doClick = function(){
			//alert(_config.el);
			WdatePicker(_config);
		};
	};
	
	element.readOnly = false;
	element.className += " Wdate";
	talent.Util.addEventHandler(element, "click", new dd({dateFmt:ifFormat, skin:'whyGreen', el:element}).doClick);
};

/**
 * 根据类名实例化js对象
 * @param {} clazz
 * @return {}
 */
talent.Util.instanceByClass = function(clazz) {
	var str = "var instance = new " + clazz + "();";
	eval (str);
	return instance;
};

/**
 * 获取浏览器信息
 * @return {String}
 */
talent.Util.getOs = function() {
   if(navigator.userAgent.indexOf("MSIE") > 0) {
        return "ie";
   }
   if(isFirefox=navigator.userAgent.indexOf("Firefox") > 0) {
        return "firefox";
   }
   if(isSafari=navigator.userAgent.indexOf("Safari") > 0) {
        return "safari";
   }
   if(isCamino=navigator.userAgent.indexOf("Camino") > 0) {
        return "camino";
   }
   if(isMozilla=navigator.userAgent.indexOf("Gecko/") > 0) {
        return "gecko";
   }
};


talent.Util.trim = function(str, mode){
	if (!str){
		return "";
	}
	if (mode) {
		if (mode == "l"){
			return str.replace(/(^\s*)/g, ""); 
		} else if (mode == "r"){
			return str.replace(/(\s*$)/g, ""); 
		}
	}
	return str.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 
 * @param {} _type 形如 checkbox, text等
 */
talent.Util.createInputElement = function(_type){
	var e = document.createElement("input");
		
	try{
		e.type = _type;
	} catch(e) {
		e = document.createElement("<input type='"+_type+"'>");
	}
	return e;
};














/**
  eg:<br>
  var config = 
  {
	url:"xx.do",       //请求的url
	form: formElement, //要提交的form
	method:"post",     //post/get。默认为post
	async:false,       //true/false。默认为false(表示异步提交)
	data:[{name:"e", value:"ee"},{name:"w", value:"ww"}],
	queryStringData: "e=eee&a=ddd&c=kkkk",
    encoding:     'UTF-8',
    requestHeaders: {"name1":"value1","":""},
    thisObj: null,                        //回调方法体内，this指示的对象
	success: function(xmlHttpRequest){}   //成功请求后的回调函数
  };
  new talent.Ajax().setConfig(config).submit();
 */
talent.Ajax = function() {
	this.config = null;
	this.setConfig = function(config) {
		this.config = config;
		return this;
	};
	/**
	 * 
	 */
	this.getXmlHttpRequest = function () {
		try {
			return new ActiveXObject("MSXML2.XMLHTTP");
		}
		catch (e) {
			try {
				return new ActiveXObject("Microsoft.XMLHTTP");
			}
			catch (e1) {}
		}
		if (typeof XMLHttpRequest != "undefined") {
			return new XMLHttpRequest();
		}
	};
	
	/**
	 * @param {} config 形如: <br>
	  {
  		url:"xx.do",       //请求的url
  		form: formElement, //要提交的form
  		method:"post",    //post/get。默认为post
  		async:false,    //true/false。默认为true
  		data:[{name:"e", value:"ee"},{name:"w", value:"ww"}],
  		queryStringData: "e=eee&a=ddd&c=kkkk",
        encoding:     'UTF-8',
        requestHeaders: {"",""},
        thisObj: null, //回调方法体内，this指示的对象
        callbackParams:null,  //回调方法的第二个参数
  		success: function(xmlHttpRequest, callbackParams){}
	  }
	 */
	this.submit = function () {
		var config = this.config;
		if (!config.url) {
			return;
		}
		
		var xmlHttpRequest = this.getXmlHttpRequest();
		var contentType = 'application/x-www-form-urlencoded';
		var method = config.method ? config.method.toUpperCase() : "POST";
		var async = config.async ? config.async : false;
		var encoding = config.encoding ? config.encoding : 'utf-8';
		var thisObj = config.thisObj ? config.thisObj : window;
		
		var callbackParams = config.callbackParams ? config.callbackParams : null;
		
		var setRequestHeader = function(xmlHttpRequest, requestHeaders, postDataLength) {
			var buildinHeaders = 
			{
		      'X-Requested-With': 'XMLHttpRequest',
		      'Accept': 'text/javascript, text/html, application/xml, text/xml, */*'
//		      'Connection' : 'close'
//		      'Content-length' : postDataLength
		    };
		    //setRequestHeader("Connection", "close"); 
		    if (method == "POST") {
				 //alert(encoding);
				 buildinHeaders['Content-type'] = contentType + (encoding ? '; charset=' + encoding : '');
			}
			
			for (var name in buildinHeaders) {
				xmlHttpRequest.setRequestHeader(name, buildinHeaders[name]);
			}
			if (requestHeaders) {
				for (var name in requestHeaders) {
					xmlHttpRequest.setRequestHeader(name, requestHeaders[name]);
				}
			}
		};
		
		var url = config.url;
		var at = "talent_ajax_time=" + new Date().getMilliseconds();
		if (url.indexOf("?") == -1) {
			url += "?" + at;
		} else {
			url += "&" + at;
		}
		
		
		var postData = [];
		if (config.form) {
			postData.push(talent.Util.formToQueryString(config.form));
		}
		if (config.queryStringData) {
			postData.push(encodeURI(config.queryStringData));
		}
		if (config.data) {
			var data;
			for (var i = 0; i < config.data.length; i++) {
				data = config.data[i];
				postData.push(data.name + "=" + encodeURIComponent(data.value));
			}
		}
		var postDataStr = postData.join("&");
		//alert(postDataStr);
		
		xmlHttpRequest.open(method, url, async);
		setRequestHeader(xmlHttpRequest, config.requestHeaders, postDataStr.length);//必须在open后面执行
		var t = function() {
			if (xmlHttpRequest.readyState == 4) {
				if (config.success) {
					thisObj = config.thisObj ? config.thisObj : config.success;
					config.success.call(thisObj, xmlHttpRequest, callbackParams);
				}
			}
		};
		if (talent.Util.getOs() == "firefox") {
			xmlHttpRequest.send(postDataStr);
			xmlHttpRequest.onreadystatechange = t();
		} else {
			xmlHttpRequest.onreadystatechange = t;
			xmlHttpRequest.send(postDataStr);
		}
		
	};
};

talent.Ajax.submit = function(ajaxConfig) {
	new talent.Ajax().setConfig(ajaxConfig).submit();
};

/**
 * 实例化Render
 * 
 * @param {}
 *            renderClass
 * @param {}
 *            config
 * @return {}
 */
talent.qe.instanceRender = function(renderClass, config) {
	var str = "var render = new " + renderClass + "();";
	eval (str);
	render.setConfig(config);
	return render;
};

/**
 * invoke render
 * 
 * @param {}
 *            renderClass
 * @param {}
 *            config
 */
talent.qe.invokeRender = function(renderClass, config) {
	try  {
		var render = talent.qe.instanceRender(renderClass, config);
		render.render();
	} catch (e) {
		alert("error message from talent.qe.invokeRender: render class=" + renderClass + "\r\n" + e);
	}
};

/**
 * 基础渲染者
 */
talent.qe.BaseRender = talent.Class.extend({
	/**
	 * 所有的渲染都有一个setConfig方法
	 */
	setConfig:function (config) {
		this.config = config;
	},
	init : function (){
		
	}
});

/**
 * 基础创建者
 */
talent.qe.BaseCreator = talent.Class.extend({
	/**
	 * 所有的渲染都有一个setConfig方法
	 */
	setConfig:function (config) {
		this.config = config;
	},
	getConfig:function() {
		return this.config;
	},
	init : function (){
		
	}
});


// set form start 
/**
 * usage : 
 * 1. var isArray = talent_isSpecifiedType(obj, "Array")
 * 2. var isString = talent_isSpecifiedType(obj, "String")
 * 3. var isNumber = talent_isSpecifiedType(obj, "Number")
 * 4. var isDate = talent_isSpecifiedType(obj, "Date")
 * 5. var isRegExp = talent_isSpecifiedType(obj, "RegExp")
 * 6. var isObject = talent_isSpecifiedType(obj, "Object")
 * @param {} _obj
 * @param {} type
 * @return {true if type is matching the given value, otherwise return false}
 */
function talent_isSpecifiedType(_obj, type) {
	if (arguments.length == 2){
		return Object.prototype.toString.call(_obj) == '[object '+ type + ']';
	}
	var ret = false;
	for (var i = 1; i < arguments.length; i++) {
		ret = ret || (Object.prototype.toString.call(_obj) == '[object '+ arguments[i] + ']');
		if (ret == true){
			return true;
		}
	}
	return ret;
}
//alert(talent_isSpecifiedType(new Date(),"String","Number","Date"));
/**
 * 
 * @param {} dataObj datas which will set to the html elements
 * @param {} prefix "user." "company." and so on
 * @param {} name current level'name
 * @param {} setFormReadonly true:将表单中的元素设置为只读
 */
function talent_c_setInputValueWithDataObj(dataObj, prefix, name, _form, setFormReadonly) {
	if (!dataObj) {
		return;
	}
	
	var inputElementArray = document.getElementsByName(prefix + name);

	if (!inputElementArray) {
		return;
	}
	//var isString = talent_isSpecifiedType(dataObj, "String");
	var isArray = talent_isSpecifiedType(dataObj, "Array");
	//var isNumber = talent_isSpecifiedType(dataObj, "Number");
	var isObject = talent_isSpecifiedType(dataObj, "Object");
	
	if (talent_isSpecifiedType(dataObj, "String", "Number", "Date")) {
		talent_c_setInputArrayValue(inputElementArray, dataObj, _form, setFormReadonly);
	} else if (isObject) {
		var _prefix = prefix + "";
		if (name != "") {
			_prefix += name + ".";
		}
		
		for (var i in dataObj) {
			talent_c_setInputValueWithDataObj(dataObj[i], _prefix, i, _form, setFormReadonly);
		}
	} else if (isArray) {
		if (!inputElementArray[0]) {
			return;
		}
		if (!(talent_isSpecifiedType(dataObj[0],"String", "Number", "Date"))) {
			return;
		}
		if (inputElementArray[0].tagName == "INPUT" && (inputElementArray[0].type == "checkbox" || inputElementArray[0].type == "radio")) {
			talent_c_setValueForCheckboxAndRadio(inputElementArray, dataObj, _form, setFormReadonly);
			return;
		}
		for (var i = 0; i < dataObj.length; i++) {
			talent_c_setInputValue(inputElementArray[i], dataObj[i], _form, setFormReadonly);
		}
	}
}
function talent_c_setValueForCheckboxAndRadio(inputElementArray, dataObjArray, _form, setFormReadonly) {
	for (var i = 0; i < inputElementArray.length; i++) {
		for (var j in dataObjArray) {
			if (inputElementArray[i].form != _form){
				continue;
			}
			if (dataObjArray[j] == inputElementArray[i].value) {
				inputElementArray[i].checked = true;
				if (setFormReadonly){
					$(inputElementArray[i]).attr("readonly", true);
				}
				break;
			}
		}
	}
}
/**
 * 
 * @param {} inputElement 
 * @param {} value
 */
function talent_c_setInputValue(inputElement, _value, _form, setFormReadonly) {
	if (inputElement == null) {
		return;
	}

	if (inputElement.form != _form){
		return;
	}
	
	var value = _value;
	if (_value == null || _value == "undefined") {
		value = "";
	}
	if (inputElement.tagName == "INPUT") {
	if (inputElement.type == "text" || inputElement.type == "hidden") {
		inputElement.value = value;
	} else if (inputElement.type == "checkbox" || inputElement.type == "radio") {
		inputElement.checked = (value == inputElement.value);
	}
	} else if (inputElement.tagName == "SELECT" || inputElement.tagName == "TEXTAREA") {
		inputElement.value = value;
	}
	if (setFormReadonly){
		$(inputElement).attr("readonly", true);
	}
}
function talent_c_setInputArrayValue(inputElementArray, _value, _form, setFormReadonly) {
	if (inputElementArray == null) {
		return;
	}
	for (var i = 0; i < inputElementArray.length; i++) {
		talent_c_setInputValue(inputElementArray[i], _value, _form, setFormReadonly);
	}
}

/**
 * 用表单的数据更新grid的数据
 * @param {} data
 * @param {} _form
 */
function tt_updateGridDataWithForm(data, _form) {
	var es = _form.elements;
	for (var i = 0; i < es.length; i++) {
		data[es[i].name] = es[i].value;
	}
}
//set form end

/**
 * 是不是要求登陆的响应
 * @param {} responseText
 * @return {Boolean}
 */
function tt_isLoginResp(responseText) {
	if (responseText.indexOf("j_spring_security_check") > 0) {
		return true;
	}
}

/**
 * 根据ajax响应，分派
 * @param {} _data
 * @param {} contextpath
 * @return {Boolean}
 */
function tt_dispatchJsonAjax(_data, contextpath) {
	var status = _data.status;
	
	if (status >= 200 && status <= 299) { // 成功
		if (tt_isLoginResp(_data.responseText)) {
			parent.parent.parent.parent.location.href = contextpath + "/login/init.talent";
		}
		try {
			JSON.parse(_data.responseText);
		} catch(e) {
			var form = document.createElement("form");
			var input = document.createElement("input");
			
			document.body.appendChild(form);
			form.appendChild(input);
			
			form.action = contextpath + "/common/echoHtml.talent";
			form.method = "POST";
			
			input.setAttribute("name", "html");
			input.value = _data.responseText;
			
			form.submit();
			return false;
		}
		
		return true;
	} else if(status >= 300 && status <= 399) {
		if (status == 302) {
			if (tt_isLoginResp(_data.responseText)) {
				parent.parent.parent.parent.location.href = contextpath + "/login/init.talent";
			}
		}
		return false;
	} else if(status >= 400 && status <= 499) {
		if (status == 403){
			location.href = contextpath + "/common/toError/403.talent";
		}else if (status == 408){
			location.href = contextpath + "/common/toError/408.talent";
		}else{
			location.href = contextpath + "/common/toError/404.talent";
		}
	} else if(status >= 500 && status <= 599) {
		location.href = contextpath + "/common/toError/500.talent";
	}
	return false;
}

/**
 * 使表单的元素只读
 * @param {} formElement
 * @param {} makeItReadonly
 * @param {} makeItDisable
 */
function tt_disableForm(formElement, makeItReadonly, makeItDisable) {
	if(formElement) {
		var es = formElement.elements;
		if(es) {
			for (var i = 0; i < es.length; i++) {
				try {
					es[i].setAttribute("readonly", true);
					es[i].setAttribute("disabled", true);
				}catch(e) {}
			}
		}
	}
}

/**
 * checkbox的自动关联，用在"select all"身上
 * @param {} _nameOfCheckbox
 * @param {} _checkboxAll
 */
var tt_autoUpdateCheckbox = function (_nameOfCheckbox, _checkboxAll) {
	var _ = function(nameOfCheckbox, checkboxAll) {
		this.h = function() {
			if (checkboxAll) {
				var checkedStatus = checkboxAll.checked;
				var checkboxs = document.getElementsByName(nameOfCheckbox);
				if (checkboxs && checkboxs.length > 0) {
					for (var i = 0; i < checkboxs.length; i++) {
						checkboxs[i].checked = checkedStatus;
					}
				}
			}
		};
	};
	
	talent.Util.addEventHandler(_checkboxAll, "change", new _(_nameOfCheckbox, _checkboxAll).h);
	talent.Util.addEventHandler(_checkboxAll, "click", new _(_nameOfCheckbox, _checkboxAll).h);
};

/**
 * 
 * checkbox的自动关联，用在"单个checkbox"身上
 * @param {} _checkbox
 * @param {} _nameOfCheckbox
 * @param {} _checkboxAll
 */
var tt_autoUpdateCheckbox1 = function (_checkbox, _nameOfCheckbox, _checkboxAll) {
	var _ = function(checkbox, nameOfCheckbox, checkboxAll) {
		this.h = function() {
			if (checkboxAll) {
				var trueC = 0;
				var falseC = 0;
				var checkboxs = document.getElementsByName(nameOfCheckbox);
				if (checkboxs && checkboxs.length > 0) {
					for (var i = 0; i < checkboxs.length; i++) {
						checkboxs[i].checked ? trueC++ : falseC++;
					}
					if (trueC > falseC){
						checkboxAll.checked = true;
					} else if (trueC == falseC){
						
					} else {
						checkboxAll.checked = false;
					}
				}
			}
		};
	};
	
	talent.Util.addEventHandler(_checkbox, "change", new _(_checkbox, _nameOfCheckbox, _checkboxAll).h);
	talent.Util.addEventHandler(_checkbox, "click", new _(_checkbox, _nameOfCheckbox, _checkboxAll).h);
};

/**
 * 
 * @param {} iconPositionObj
 * @param {} targetObj
 */
var tt_setupPutdown = function(iconPositionObj, targetObj) {
	iconPositionObj.style.paddingRight = "0px";
	var span = document.createElement("span");
	iconPositionObj.appendChild(span);
	span.style.styleFloat = "right";
	span.style.cssFloat = "right";
	span.style.fontWeight = "bold";
	span.style.paddingRight = "5px";
	
	var packUp = '<span class="" title="收起"></span>';
	var putDown = '<span class=""  title="显示"></span>';
	
	
	var a = document.createElement("a");
	span.appendChild(a);
	a.className = 'tt_btn_pack_up';
	a.innerHTML = packUp; //button_close.gif
	
	var h = function() {
		if (targetObj.style.display == "none"){  //处于不可见状态
			a.innerHTML = packUp;
			a.className = 'tt_btn_pack_up';
			//
			try{$(targetObj).fadeIn("slow");}catch(e){
			targetObj.style.display = "";
			}
		} else {
			a.className = 'tt_btn_put_down';
			a.innerHTML = putDown;
			//
			
			try{$(targetObj).fadeOut("slow");}catch(e){
			targetObj.style.display = "none";
			}
		}
	};
	//$(a).bind("click", h);
	talent.Util.addEventHandler(a, "click", h);
};

/**
 * 返回两个日期相关的天数
 * @param {} startDateStr 形如:2007-01-01
 * @param {} endDateStr  形如:2007-01-01
 */
function tt_twoDateDays(startDateStr, endDateStr) {
	var s1 = startDateStr.replace(/-/g, "/");
	var s2 = endDateStr.replace(/-/g, "/");
	s1 = new Date(s1);
	s2 = new Date(s2);
	
	var time= s2.getTime() - s1.getTime();
	var days = parseInt(time / (1000 * 60 * 60 * 24));
	return days;
}