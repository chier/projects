var talent = {};

/**
 * 此段代码摘自网上，网址已经找不到了
 */
// 自执行的匿名函数创建一个上下文，避免引入全局变量
(function() {
	// initializing变量用来标示当前是否处于类的创建阶段，
	// - 在类的创建阶段是不能调用原型方法init的
	// - 我们曾在本系列的第三篇文章中详细阐述了这个问题
	// fnTest是一个正则表达式，可能的取值为（/\b_super\b/ 或 /.*/）
	// - 对 /xyz/.test(function() { xyz; }) 的测试是为了检测浏览器是否支持test参数为函数的情况
	// - 不过我对IE7.0,Chrome2.0,FF3.5进行了测试，此测试都返回true。
	// - 所以我想这样对fnTest赋值大部分情况下也是对的：fnTest = /\b_super\b/;
	var initializing = false, fnTest = /xyz/.test(function() {
				xyz;
			}) ? /\b_super\b/ : /.*/;
	// 基类构造函数
	// 这里的this是window，所以这整段代码就向外界开辟了一扇窗户 - window.Class
	this.talent.Class = function() {
	};
	// 继承方法定义
	talent.Class.extend = function(prop) {
		// 这个地方很是迷惑人，还记得我在本系列的第二篇文章中提到的么
		// - this具体指向什么不是定义时能决定的，而是要看此函数是怎么被调用的
		// - 我们已经知道extend肯定是作为方法调用的，而不是作为构造函数
		// - 所以这里this指向的不是Object，而是Function（即是Class），那么this.prototype就是父类的原型对象
		// - 注意：_super指向父类的原型对象，我们会在后面的代码中多次碰见这个变量
		var _super = this.prototype;
		// 通过将子类的原型指向父类的一个实例对象来完成继承
		// - 注意：this是基类构造函数（即是Class）
		initializing = true;
		var prototype = new this();
		initializing = false;
		// 我觉得这段代码是经过作者优化过的，所以读起来非常生硬，我会在后面详解
		for (var name in prop) {
			prototype[name] = typeof prop[name] == "function"
					&& typeof _super[name] == "function"
					&& fnTest.test(prop[name]) ? (function(name, fn) {
				return function() {
					var tmp = this._super;
					this._super = _super[name];
					var ret = fn.apply(this, arguments);
					this._super = tmp;
					return ret;
				};
			})(name, prop[name]) : prop[name];
		}
		// 这个地方可以看出，Resig很会伪装哦
		// - 使用一个同名的局部变量来覆盖全局变量，很是迷惑人
		// - 如果你觉得拗口的话，完全可以使用另外一个名字，比如function F()来代替function Class()
		// - 注意：这里的Class不是在最外层定义的那个基类构造函数
		function Class() {
			// 在类的实例化时，调用原型方法init
			if (!initializing && this.init)
				this.init.apply(this, arguments);
		}
		// 子类的prototype指向父类的实例（完成继承的关键）
		Class.prototype = prototype;
		// 修正constructor指向错误
		Class.constructor = Class;
		// 子类自动获取extend方法，arguments.callee指向当前正在执行的函数
		Class.extend = arguments.callee;
		return Class;
	};
})();

/**
 * register namespace. <br>
 * eg: talent.Ns.add("com.talent.model1"); or
 * talent.Ns.register("com.talent.model1");
 * 
 * @type
 */
talent.Ns = {
	add : function(fullNS){
		// 将命名空间切成N部分, 比如Grandsoft、GEA等
	    var nsArray = fullNS.split('.');
	    var sEval = "";
	    var sNS = "";
	    for (var i = 0; i < nsArray.length; i++)
	    {
	        if (i != 0) sNS += ".";
	        sNS += nsArray[i];
	        // 依次创建构造命名空间对象（假如不存在的话）的语句
	        // 比如先创建Grandsoft，然后创建Grandsoft.GEA，依次下去
	        sEval += "if (typeof(" + sNS + ") == 'undefined') " + sNS + " = new Object();"
	    }
	    if (sEval != "") eval(sEval);
	},
	
	add1 : function(n) {
		a = n.split('.');
		ns = "";
		for (i in a) {
			ns += a[i];
			sEval += "if (typeof(" + ns + ") == 'undefined') " + ns + " = new Object();";
			if (sEval != "") eval(sEval);
			
		}
	}
};
talent.Ns.register = talent.Ns.add;

/* 命名空间集中定义处 */
talent.Ns.add("talent.ui");
talent.Ns.add("talent.Util");
talent.Ns.add("talent.Ajax");
talent.Ns.add("talent.qe");
/**
 * 将source的属性复制给destination
 * @param {} destination
 * @param {} source
 */
talent.Util.copy = function(destination, source) {
	//destination, source
	if (source && destination) {
		for (var propertyName in source) {
			destination[propertyName] = source[propertyName];
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
	var allStr = [""];
	if (formElement) {
		var formElements = formElement.elements;
		var inputObj;
		if (formElements) {
			for (var i = 0; i < formElements.length; i++) {
				inputObj = formElements[i];
				if (inputObj.name) {
					allStr.push(inputObj.name + "=" + encodeURIComponent(inputObj.value));
				}
			}
		}
	}
	return allStr.join("&");
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
	optionProperties, optionPropertiesFromSrc) {
	if (isClearBeforeUpdate == true) {
		selectElement.options.length = 0;
	}
	for (var data = 0; data < items.length; data++) {
		if (isFilterNullValue
				&& (items[data][valueFieldName] == "" || items[data][labelFieldName] == "")) {
			continue;
		}
	
		var theOption = new Option(items[data][labelFieldName], items[data][valueFieldName]);
		theOption.title = items[data][labelFieldName];
		talent.Util.copy(theOption, optionProperties);
		selectElement.options.add(theOption); 
		

		if (optionClassName) {
			theOption.className = optionClassName;
		}
		if (optionPropertiesFromSrc) {
			for (var i = 0; i < optionPropertiesFromSrc.length; i++) {
				theOption[optionPropertiesFromSrc[i]] = items[data][optionPropertiesFromSrc[i]];
			}
		}
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
	element.readOnly = true;
	element.className += " Wdate";
	element.onclick = new Function("WdatePicker({dateFmt:'" + ifFormat + "', skin:'whyGreen'})"); //WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt
}

/**
 * 根据类名实例化js对象
 * @param {} clazz
 * @return {}
 */
talent.Util.instanceByClass = function(clazz) {
	var str = "var instance = new " + clazz + "();";
	eval (str);
	return instance;
}

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
}
















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
    requestHeaders: {"",""},
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
		      'Accept': 'text/javascript, text/html, application/xml, text/xml, */*',
		      'Connection' : 'close',
		      'Content-length' : postDataLength
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
		if (url.indexOf("?") == -1) {
			url += "?";
		}
		url += "&talent_ajax_time=" + new Date().getMilliseconds();
		
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
}

talent.Ajax.submit = function(ajaxConfig) {
	new talent.Ajax().setConfig(ajaxConfig).submit();
}
/*---- talent.ui.Grid start ----*/
talent.Ns.add("talent.ui.GridProxy"); //此非全局命名空间，所以在此定义
talent.ui.GridProxy = {index : -1};

/**
 * @param {} conf grid配置，参见demo_page.js中的conf
 * conf.gridData： grid数据，形如：<br><pre>
  {
    "data":
    [
        {
            "name": "乔丹",
            "id": "1"
        },
        {
            "name": "科比",
            "id": "2"
        }
    ],
    "pageCount": 100,  //总页数
    "pageIndex": 6,    //当前是第几页
    "pageSize": 2,     //每页显示多少条
    "pagination": true,//如果不想分页，请将此属性设为false
    "prePageCount": 5, //在当前页的前面还有多少页
    "recordCount": 500,//总记录数
    "sufPageCount": 94 //在当前页的后面还有多少页
  }
</pre>
 * conf.container 显示grid数据的容器，一般为一个div对象
 * 
 */
talent.ui.Grid = function (conf) {
//	if (!conf || !conf.fields || !conf.gridData || !conf.gridData.data) {
//		return;
//	}
	this.initExtData = function() {
		conf.colsCount = conf.fields.length;        //有多少列要显示
		conf.dataRows = conf.gridData.data.length;  
		for (var i = 0; i < conf.fields.length; i++) {
			var f = conf.fields[i];
			f.display = (f.display == undefined) ? true : f.display;
			if (!f.display) {
				conf.colsCount--;
			}
			/**
			if (f.colSpan && f.colSpan > 1) {
				conf.colsCount += (f.colSpan - 1);
			}
			if (f.rowSpan && f.rowSpan > 1) {
				//conf.dataRows += (f.rowSpan - 1) * conf.gridData.data.length;
			}
			*/
		}
	};
	this.initExtData();
	
	var d = document;
	var ce = function(str) {
		return document.createElement(str); //节约字节
	};
	var keyOfPageIndex = conf.keyOfPageIndex ? conf.keyOfPageIndex : "pageable.pageIndex";
	var keyOfPageSize = conf.keyOfPageSize ? conf.keyOfPageSize : "pageable.pageSize";
	
	//设置当前对象的代理对象，以方便第三方函数引用本对象
	talent.ui.GridProxy[++talent.ui.GridProxy.index] = this;

	/**
	 * 
	 */
	this.render = function() {
		var s = new Date().getMilliseconds();
		var table = ce("table");
		table.className = "talent_grid_table";
		conf.container.innerHTML = "";
		conf.container.appendChild(table);
		
		var caption = ce("caption");
		var thead = ce("thead");
		var tbody = ce("tbody");
		var tfoot = ce("tfoot");
		
		
		table.appendChild(caption);
		table.appendChild(thead);
		table.appendChild(tbody);
		table.appendChild(tfoot);
		
		conf.captionRender ? conf.captionRender.call(conf.captionRender, table, caption, conf) : this.renderCaption(table, caption, conf);
		conf.theadRender ? conf.theadRender.call(conf.theadRender, table, thead, conf) : this.renderThead(table, thead, conf);
		conf.tbodyRender ? conf.tbodyRender.call(conf.tbodyRender, table, tbody, conf) : this.renderTbody(table, tbody, conf);
		conf.tfootRender ? conf.tfootRender.call(conf.tfootRender, table, tfoot, conf) : this.renderTfoot(table, tfoot, conf);
		
		var e = new Date().getMilliseconds();
		//alert(e - s);
	};
	
	this.renderCaption = function(table, caption, conf) {
		if (conf.caption){
			if (conf.caption.render) {
				conf.caption.render.call(window, table, caption, conf.gridData, conf);
			} else if (conf.caption.label) {
				caption.innerHTML = conf.caption.label;
			}
		}
	};
	/**
	 * 
	 */
	this.renderThead = function(table, thead, conf) {
		gridData = conf.gridData;
		var isPageable = !(gridData.pagination == false);
		if (isPageable) {
			if (conf.paginationLocation && 
			(conf.paginationLocation == "all" || conf.paginationLocation == "h" || conf.paginationLocation == "header")){
				var theadRow = thead.insertRow(thead.rows.length);
				var cell = theadRow.insertCell(theadRow.cells.length);
				cell.colSpan = conf.colsCount;
				cell.style.margin = "0px";
				cell.style.padding = "0px";
				var div = ce("div");
				div.className = "digg";
				cell.appendChild(div);
				this.renderPagination(div, conf);
			}
		}
		
		var theadRow = thead.insertRow(thead.rows.length);
		for (var fieldIndex = 0; fieldIndex < conf.fields.length; fieldIndex++) {
			var f = conf.fields[fieldIndex];
			if (!f.display) {
				continue;
			}
			var cell = theadRow.insertCell(theadRow.cells.length);
			talent.Util.copy(cell, f.listHeaderProperties);
			talent.Util.setStyle(cell, f.listHeaderStyles);
			//table, rowElement, cellElement, fieldConfig, conf, gridData
			//table, rowElement, cellElement, fieldConfig, conf, gridData
			if (f.listHeadRender) {
				f.listHeadRender.config.rowElement = theadRow;
				f.listHeadRender.config.cellElement = cell
				f.listHeadRender.render();
				//table, rowElement, cellElement, fieldConfig, gridConfig, gridData
				
				//f.listHeadRender.call(f.listHeadRender, table, theadRow, cell, f, conf, gridData);
			} else if (f.listHeadRenderConfig && f.listHeadRenderConfig.clazz) {
				//table, rowElement, cellElement, fieldConfig, gridConfig, gridData
				f.listHeadRender = talent.Util.instanceByClass(f.listHeadRenderConfig.clazz);
				f.listHeadRender.config = {};
				talent.Util.copy(f.listHeadRender.config, f.listHeadRenderConfig.config);
				f.listHeadRender.config.field = f;
				f.listHeadRender.config.gridConfig = conf;
				f.listHeadRender.config.gridData = gridData;
				f.listHeadRender.config.rowElement = theadRow;
				f.listHeadRender.config.cellElement = cell;
				f.listHeadRender.render();
			} else if (f.label) {
				cell.innerHTML = f.label;
			}
		}
	};
	this.renderTbody = function(table, tbody, conf) {
		prefix = "talent_grid_table_tbody_row_";
		var rowClassName = prefix + "even";
		gridData = conf.gridData;
		
		//var dataIndex = 0;
		for (var i = 0; i < conf.gridData.data.length; i++) {
			var row = tbody.insertRow(tbody.rows.length);
			rowClassName = rowClassName == prefix + "even" ? prefix + "odd" : prefix + "even";
			row.className = rowClassName;
			
			for (var fieldIndex = 0; fieldIndex < conf.fields.length; fieldIndex++) {
				var f = conf.fields[fieldIndex];
				if (!f.display) {
					continue;
				}
				var cell = row.insertCell(row.cells.length);
				talent.Util.copy(cell, f.listDataProperties);
				talent.Util.setStyle(cell, f.listDataStyles);
				//(rowElement, cellElement, rowData, fieldConfig, conf, gridData)
				
				
				
				
				//table, row, cell, gridData.data[i], f, conf, gridData, i, fieldIndex
				//table, rowElement, cellElement, rowData, fieldConfig, gridConfig, gridData, rowIndex, colIndex
				if (f.listDataRender) {
					f.listDataRender.config.rowIndex = i;
					f.listDataRender.config.colIndex = fieldIndex;
					f.listDataRender.config.rowElement = row;
					f.listDataRender.config.cellElement = cell;
					f.listDataRender.config.rowData = gridData.data[i];
					f.listDataRender.render();
				} else if (f.listDataRenderConfig && f.listDataRenderConfig.clazz) {
					f.listDataRender = talent.Util.instanceByClass(f.listDataRenderConfig.clazz);
					f.listDataRender.config = {};
					talent.Util.copy(f.listDataRender.config, f.listDataRenderConfig.config);
					f.listDataRender.config.table = table;
					f.listDataRender.config.field = f;
					f.listDataRender.config.gridConfig = conf;
					f.listDataRender.config.gridData = gridData;
					f.listDataRender.config.rowIndex = i;
					f.listDataRender.config.colIndex = fieldIndex;
					f.listDataRender.config.rowElement = row;
					f.listDataRender.config.cellElement = cell;
					f.listDataRender.config.rowData = gridData.data[i];
					f.listDataRender.render();
				} else {
					cell.innerHTML = gridData.data[i][conf.fields[fieldIndex].name];
				}
				
			}
		}
	};
	this.renderTfoot = function(table, tfoot, conf) {
		var isPageable = !(gridData.pagination == false);
		gridData = conf.gridData;
		if (isPageable) {
			if (!conf.paginationLocation || 
			(conf.paginationLocation == "all" || conf.paginationLocation == "f" || conf.paginationLocation == "footer")) {
				var row = tfoot.insertRow(tfoot.rows.length);
				var cell = row.insertCell(row.cells.length);
				cell.colSpan = conf.colsCount;
				var footdiv = ce("div");
				cell.appendChild(footdiv);
				footdiv.className = "digg";
				this.renderPagination(footdiv, conf);
			}
		}
	};
	
	this.renderPagination = function(div, conf) {
		div.innerHTML = "";
		if (conf.paginationRender) {
			conf.paginationRender.call(conf.paginationRender, div, conf);
			return;
		}
		renderPaginationMsg(div, conf);
		renderPaginationGoto(div, conf);
		renderPaginationPre(div, conf);
		renderPaginationCurr(div, conf);
		renderPaginationSuffix(div, conf);
		
		
		if (conf.isShowCustomPageSize == null || conf.isShowCustomPageSize == undefined || conf.isShowCustomPageSize == true) {
			new paginationCustomPageSizeRender(div, conf).render();
		}
	};
	var renderPaginationMsg = function(div, conf) {
		gridData = conf.gridData;
		var span = ce("span");
		div.appendChild(span);
		var x = gridData.pageIndex * gridData.pageSize;
		x = x > gridData.recordCount ? gridData.recordCount : x;
		var s = ((gridData.pageIndex - 1) * gridData.pageSize + 1);
		s = s < 0 ? 0 : s;
		span.innerHTML = s + "-" + x +" / " + gridData.recordCount;
		span.className = "record";
	};
	var renderPaginationPre = function(div, conf) {
		gridData = conf.gridData;
		
		
		if (gridData.prePageCount <= 0) {
			//<span class="disabled">&lt;&lt;</span><span class="disabled">&lt; </span>
			var span1 = ce("span");
			div.appendChild(span1);
			span1.className = "disabled";
			span1.innerHTML = "&lt;&lt;";
			
			var span2 = ce("span");
			div.appendChild(span2);
			span2.className = "disabled";
			span2.innerHTML = "&lt;";
		} else {
			//<a href="#?page=2"> &gt;&gt; </a>
			/*第一页和上一页  start*/
			var a1 = ce("a");
			div.appendChild(a1);
			processAObj(a1, 1);
			a1.innerHTML = "&lt;&lt;";
			a1.className = "arrow";
			
			var a2 = ce("a");
			div.appendChild(a2);
			processAObj(a2, gridData.pageIndex - 1);
			a2.innerHTML = "&lt;";
			a2.className = "arrow";
			
			/*第一页和上一页  end*/
			/*<a href="#?page=2">2</a>*/
			var preIndexCount = gridData.prePageCount > conf.paginationCount ? conf.paginationCount : gridData.prePageCount;
			for (var i = preIndexCount; i > 0; i--) {
				//gridData.pageIndex - i;
				var a = ce("a");
				div.appendChild(a);
				var _index = gridData.pageIndex - i;
				processAObj(a, _index);
				a.innerHTML = _index;
			}
		}
	};
	var renderPaginationCurr = function(div, conf) {
		//<span class="current">1</span>
		var span1 = ce("span");
		div.appendChild(span1);
		span1.className = "current";
		span1.innerHTML = conf.gridData.pageIndex;
	};
	var renderPaginationSuffix = function(div, conf) {
		gridData = conf.gridData;
		if (gridData.sufPageCount <= 0) {
			//<span class="disabled">&gt;&gt;</span><span class="disabled">&gt;&gt;</span>
			var span1 = ce("span");
			div.appendChild(span1);
			span1.className = "disabled";
			span1.innerHTML = "&gt;&gt;";
			
			var span2 = ce("span");
			div.appendChild(span2);
			span2.className = "disabled";
			span2.innerHTML = "&gt;&gt;";
		} else {
			/*<a href="#?page=2">2</a>*/
			var sufIndexCount = gridData.sufPageCount > conf.paginationCount ? conf.paginationCount : gridData.sufPageCount;
			for (var i = 1; i <= sufIndexCount; i++) {
				//gridData.pageIndex - i;
				var a = ce("a");
				div.appendChild(a);
				var _index = gridData.pageIndex + i;
				processAObj(a, _index);
				a.innerHTML = _index;
			}
			
			//最后一页需要渲染，这样可以看出有多少页
			if (gridData.pageCount > sufIndexCount + gridData.pageIndex) {
				var lastIndex = sufIndexCount + gridData.pageIndex;
				
				if (gridData.pageCount > sufIndexCount + gridData.pageIndex + 1) {
					var span1 = ce("span");
					div.appendChild(span1);
					span1.innerHTML = "... ...";
				}
				
				var a = ce("a");
				div.appendChild(a);
				processAObj(a, gridData.pageCount);
				a.innerHTML = gridData.pageCount;
			}
			
			//<a href="#?page=2"> &gt;&gt; </a>
			/*最后页和下一页  start*/
			var a1 = ce("a");
			div.appendChild(a1);
			processAObj(a1, gridData.pageIndex + 1);
			a1.innerHTML = "&gt;";
			a1.className = "arrow";
			
			var a2 = ce("a");
			div.appendChild(a2);
			processAObj(a2, gridData.pageCount);
			a2.innerHTML = "&gt;&gt;";
			a2.className = "arrow";
			/*最后页和下一页  end*/
		}
	};
	var renderPaginationGoto = function(div, conf) {
		var span1 = ce("span");
		div.appendChild(span1);
		span1.className = "goto";
		span1.innerHTML = "go to";
		
		var spanInput = ce("span");
		div.appendChild(spanInput);
		//spanInput.className = "goto";
		//spanInput.innerHTML = "goto";
		
		var selectElement = ce("select");
		spanInput.appendChild(selectElement);
		var items = [];
		//alert(gridData.pageCount);
		for (var i = 1; i <= gridData.pageCount; i++) {
			items.push({v:i,l:i,title:"第"+i+"页"});
		}
		talent.Util.updateOption(selectElement, true, items, "v", "l", true, "", "", ["title"]);
		selectElement.value = gridData.pageIndex;
		
		var h = function(selectElement) {
			this.a = function () {
				talent.ui.GridProxy[talent.ui.GridProxy.index].reload(selectElement.value);
			};
		};
		talent.Util.addEventHandler(selectElement, "change", new h(selectElement).a);
		
	};
	var paginationCustomPageSizeRender = function( div, conf) {
		gridData = conf.gridData;
		this.render = function() {
			
			options = [];
			maxPageSize = 200;
			if (conf.maxPageSize && !isNaN(conf.maxPageSize)) {
				maxPageSize = conf.maxPageSize;
			}
			maxPageSize = maxPageSize > gridData.recordCount ? gridData.recordCount : maxPageSize;
			
			if (maxPageSize >= 5) {
				options.push({v:5,l:5});
			}
			if (maxPageSize >= 10) {
				options.push({v:10,l:10});
			}
			
			var step = 20;
			for (i = 20; i <= maxPageSize; i+=step) {
				options.push({v:i,l:i});
				
				if (i >= 1000){
					step = 100;
				}else if (i >= 500){
					step = 50;
				}
			}
			if (gridData.recordCount <= maxPageSize && gridData.recordCount > 0){//RecordCount
				options.push({v:gridData.recordCount, l: "all"});
			}
			
			//alert(options[0].l);
			if (options.length > 0) {
				selectElement = ce("select");
				div.appendChild(selectElement);
				var handler = function (pageindex, pagesizeO) {
					var f = function() {
						talent.ui.GridProxy[talent.ui.GridProxy.index].reload(pageindex, pagesizeO.value);
					};
					return f;
				};
				talent.Util.addEventHandler(selectElement, "change", new handler(gridData.pageIndex, selectElement));
				talent.Util.updateOption(selectElement, "true", options, "v", "l", true, null, null, null);
				selectElement.value = gridData.pageSize;
			}
		};
	};
	
	//processAObj(a,pageIndex);
	var processAObj = function(aObj, pageIndex) {
		aObj.href = "javascript:void(0)";
		//alert(pageIndex);
		var h = function(pageIndex) {
			this.a = function () {
				talent.ui.GridProxy[talent.ui.GridProxy.index].reload(pageIndex);
			};
		};
		talent.Util.addEventHandler(aObj, "click", new h(pageIndex).a);
	};
	/**
	 * 
	 */
	this.reload = function (pageIndex, _pageSize) {
		//alert(pageIndex);
		var pageSize = conf.gridData.pageSize;
		if (_pageSize) {
			pageSize = _pageSize;
		} 
		
		postData = [{name:keyOfPageIndex, value: pageIndex},{name:keyOfPageSize, value:pageSize}];
		postData = postData.concat(conf.postData);
		var ajaxConfig = {
			url: conf.url,       //请求的url
			form: conf.form,     //要提交的form
			//method: "post",            //post/get。默认为post
			//async: true,               //true/false。默认为true
			data:postData,
			//queryStringData: "name=科比&id=888888888888",//"e=eee&a=ddd&c=kkkk",
		    //encoding:     'utf-8', //默认为'utf-8'
		    //requestHeaders: null,//{"",""},
		    thisObj: this,
			success: function(request) {
				//alert(request.responseText);
				conf.gridData = eval("(" + request.responseText + ")");
				this.render();
			}
		};
		//talent.Ajax.submit(ajaxConfig);
		new talent.Ajax().setConfig(ajaxConfig).submit();
	};
};
/*---- talent.ui.Grid end   ----*/

talent.ui.Element = talent.Class.extend({
	element:null,
	/**
	 * config:{container:c,properties:{width:"80px",className:"pp"},styles:{padding:""}}
	 */
	init:function(_config) {
		this.config = _config ? _config : {};
	},
	setConfig:function(_config){
		this.config = _config ? _config : {};
	},
	setProperties:function() {
		talent.Util.copy(this.element, this.config.properties);
		talent.Util.setStyle(this.element, this.config.styles);
	},
	render:function() {
		if (!this.element || this.element.hasRendered) {
			this.element = this.create();
		}
		this.config.container.appendChild(this.element);
		this.element.hasRendered = true;
		return this.element;
	}
});



talent.ui.Text = talent.ui.Element.extend({
	/**
	 * config:{container:c,properties:{width:"80px",className:"pp",name:"",id:""},styles:{padding:""}}
	 */
	init:function(_config) {
		this._super(_config);
	},
	create:function() {
		this.element = document.createElement("input");
		this.element.type = "text";
		//talent.Util.setupDate(this.element, this.config.type);
		this.setProperties();
		return this.element;
	}
});

talent.ui.Date = talent.ui.Text.extend({
	/**
	 * config:{container:c,type:"ts,t,d",properties:{width:"80px",className:"pp",name:"",id:""},styles:{padding:""}}
	 */
	init:function(_config) {
		this._super(_config);
	},
	create:function() {
		this.element = this._super();
		talent.Util.setupDate(this.element, this.config.type);
		return this.element;
	}
});

talent.ui.Checkbox = talent.ui.Element.extend({
	/**
	 * config:{container:c,properties:{width:"80px",className:"pp"},styles:{padding:""}}
	 */
	init:function(_config) {
		this._super(_config);
	},
	create:function() {
		element = document.createElement("input");
		element.type = "checkbox";
		this.setProperties();
		return element;
	}
});

talent.ui.Select = talent.ui.Element.extend({
	/**
	  config:{
		  container:c,
		  items:[
			  {value:"1",lable/text:"1"},
			  {value:"2",lable/text:"2"}
		  ],
		  url:"",
		  method:getData,
		  properties:{width:"80px",className:"pp"},
		  styles:{padding:""}
	  }
	 */
	init:function(_config) {
		this._super(_config);
	},
	create:function() {
		element = document.createElement("select");
		this.setProperties();
		return element;
	}
});

