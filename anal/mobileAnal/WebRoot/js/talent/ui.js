/*---- talent.ui.Grid start ----*/
talent.Ns.add("talent.ui.GridProxy"); //此非全局命名空间，所以在此定义
talent.ui.GridProxy = {index : -1};
var tt_checkboxHeadRender = talent.qe.BaseRender.extend({
	// this.config:{table:{}, rowElement:{}, cellElement:{}, rowData:{},
	// field:{}, gridConfig:{}, gridData:{}, rowIndex:{}, colIndex:{}, grid:{}}
	render : function() {
		var checkboxAll = document.createElement("input");
		checkboxAll.type = "checkbox";
		this.config.cellElement.appendChild(checkboxAll);
		checkboxAll.style.cursor = "pointer";
		checkboxAll.title = "全选/全取消";
		$(checkboxAll).attr("id", "tt_gridCheckbox_all");
//		talent.Util.addEventHandler(checkboxAll, "change", tt_gridSelectAll);
//		talent.Util.addEventHandler(checkboxAll, "click", tt_gridSelectAll);
		
		tt_autoUpdateCheckbox("tt_gridCheckbox", checkboxAll);
	}
});
var tt_checkboxListRender = talent.qe.BaseRender.extend({
	// this.config:{table:{}, rowElement:{}, cellElement:{}, rowData:{},
	// field:{}, gridConfig:{}, gridData:{}, rowIndex:{}, colIndex:{}}
	render : function() {
		var cloneConfig = {};
		talent.Util.copy(cloneConfig, this.config);
		
		var checkbox = talent.Util.createInputElement('checkbox');
		this.config.cellElement.appendChild(checkbox);
		var $checkbox = $(checkbox);

		checkbox.config = cloneConfig;
		$checkbox.attr("name", "tt_gridCheckbox");
		tt_autoUpdateCheckbox1(checkbox, "tt_gridCheckbox", document.getElementById("tt_gridCheckbox_all"));
	}
});

var tt_checkboxField = {
	name : "tt_checkbox", 
	label : "",
	listHeadRenderConfig : {
		clazz : "tt_checkboxHeadRender"
	},
	listDataRenderConfig : {
		clazz : "tt_checkboxListRender"
	},
	listHeaderProperties : {
		width : "50px"
	}
};


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
 * 可以通过grid对象获取下面这些元素
 * 	this.thead = thead;
	this.tbody = tbody;
	this.tfoot = tfoot;
	this.table = table;
	this.ext = {'addedRows':[7,8,9], 'deletedRows':[0,1,5],'edittedRows':[0,1,2]};
 */
talent.ui.Grid = function (conf) {
//	if (!conf || !conf.fields || !conf.gridData || !conf.gridData.data) {
//		return;
//	}
	this.config = conf;
	this.initExtData = function() {
		if (conf.isShowCheckbox) {
			conf.fields = [tt_checkboxField].concat(conf.fields);
		}
		
		conf.colsCount = conf.fields.length;        //有多少列要显示
		//conf.dataRows = conf.gridData.data.length;  
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
	
	var ce = function(str) {
		return document.createElement(str); //节约字节
	};
	var keyOfPageIndex = conf.keyOfPageIndex ? conf.keyOfPageIndex : "pageIndex";
	var keyOfPageSize = conf.keyOfPageSize ? conf.keyOfPageSize : "pageSize";
	
	//设置当前对象的代理对象，以方便第三方函数引用本对象
	talent.ui.GridProxy[++talent.ui.GridProxy.index] = this;

	/**
	 * 
	 */
	this.render = function() {
		conf = this.config;
		conf.container.innerHTML = "";
		if (!conf.gridData.data || conf.gridData.data.length == 0){
			conf.container.innerHTML = "<div class='tt_no_record'><span style='padding-left:20px'>&nbsp;</span>没有让您命中任何记录!<a href='javascript:void(0)' onclick='parentNode.style.display=\"none\";'>x</a></div>";
			return;
		}
		
		var table = ce("table");
		this.table = table;
		table.className = "talent_grid_table"; //talent_grid_table
		conf.container.appendChild(table);
		
		var caption = null;
		var thead = ce("thead");
		var tbody = ce("tbody");
		var tfoot = ce("tfoot");
		this.thead = thead;
		this.tbody = tbody;
		this.tfoot = tfoot;
		
		if (conf.caption){
			caption = ce("caption");
			table.appendChild(caption);
		}
		table.appendChild(thead);
		table.appendChild(tbody);
		table.appendChild(tfoot);
		
		
		
		conf.captionRender ? conf.captionRender.call(conf.captionRender, table, caption, conf) : this.renderCaption(table, caption, conf);
		conf.theadRender ? conf.theadRender.call(conf.theadRender, table, thead, conf) : this.renderThead(table, thead, conf);
		conf.tbodyRender ? conf.tbodyRender.call(conf.tbodyRender, table, tbody, conf) : this.renderTbody(table, tbody, conf);
		conf.tfootRender ? conf.tfootRender.call(conf.tfootRender, table, tfoot, conf) : this.renderTfoot(table, tfoot, conf);
		
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
			var _p = conf.paginationLocation;
			
			if (gridData.pageSize >= 20) {
				_p = "all";
			}
			
			
			var divForPagination = document.createElement("div");
			$(divForPagination).insertBefore(table);
			divForPagination.className = "digg tt_panelHead";
			divForPagination.style.paddingRight = "2px";
			
			if (conf.btnRenderConfig) {
				if (conf.btnRender) {
					
				} else if (conf.btnRenderConfig.clazz){
					conf.btnRender = talent.Util.instanceByClass(conf.btnRenderConfig.clazz);
					conf.btnRender.config = {};
					this.config.btnRender = conf.btnRender;
					talent.Util.copy(conf.btnRender.config, conf.btnRenderConfig.config);
				}
				
				if (conf.btnRender) {
					//操作menu start
					var menuLinkSpanWrap = document.createElement("span");
					divForPagination.appendChild(menuLinkSpanWrap);
					menuLinkSpanWrap.className = 'tt_grid_menu_wrapSpan';
					menuLinkSpanWrap.id = "talent_grid_head_btn";
//					
//					var menuLinkA = document.createElement("a");
//					menuLinkSpanWrap.appendChild(menuLinkA);
//					menuLinkA.id = "tt_grid_menu";
//					menuLinkA.setAttribute("id", "tt_grid_menu")
//					conf.menuObj = menuLinkA;
//					this.config.menuObj = menuLinkA;
//					
//					var menuLinkSpan1 = document.createElement("span");
//					menuLinkA.appendChild(menuLinkSpan1);
//					menuLinkSpan1.innerHTML = "操 作";
					//操作menu end
					
					
//					var theadRow = thead.insertRow(thead.rows.length);
//					var cell = theadRow.insertCell(theadRow.cells.length);
//					cell.colSpan = conf.colsCount;
//					cell.id = "talent_grid_head_btn";
//					conf.btnRender.config.rowElement = theadRow;
					
					conf.btnRender.config.cellElement = menuLinkSpanWrap;//cell;//tt_grid_menuitem_container;//
					talent.Util.copy(conf.btnRender.config, conf.btnRenderConfig.config);
					conf.btnRender.render();
					
				}
			}
			tt_setupPutdown(divForPagination, table);
			
			if (_p && (_p == "all" || _p == "h" || _p == "header")) {
				
//				var menuLinkSpan = document.createElement("span");
//				menuLinkA.appendChild(menuLinkSpan);
				
				
//				var theadRow = thead.insertRow(thead.rows.length);
//				var cell = theadRow.insertCell(theadRow.cells.length);
//				cell.colSpan = conf.colsCount;
//				cell.style.margin = "0px";
//				cell.style.padding = "0px";
//				var div = ce("div");
//				div.className = "digg";
//				cell.appendChild(div);
				
				var xx = document.createElement("span");
				divForPagination.appendChild(xx);
				xx.style.cssFloat = "right";
				xx.style.styleFloat = "right";
				this.renderPagination(xx, conf);
			}
		}
		
		
		
		
//		if (conf.btnRenderConfig) {
//			if (conf.btnRender) {
//				
//			} else if (conf.btnRenderConfig.clazz){
//				conf.btnRender = talent.Util.instanceByClass(conf.btnRenderConfig.clazz);
//				conf.btnRender.config = {};
//				this.config.btnRender = conf.btnRender;
//				talent.Util.copy(conf.btnRender.config, conf.btnRenderConfig.config);
//			}
//			
//			if (conf.btnRender) {
//				var theadRow = thead.insertRow(thead.rows.length);
//				var cell = theadRow.insertCell(theadRow.cells.length);
//				cell.colSpan = conf.colsCount;
//				cell.id = "talent_grid_head_btn";
//				
//				conf.btnRender.config.rowElement = theadRow;
//				conf.btnRender.config.cellElement = cell;
//				talent.Util.copy(conf.btnRender.config, conf.btnRenderConfig.config);
//				conf.btnRender.render();
//			}
//		}
		
		var theadRow = thead.insertRow(thead.rows.length);
		for (var fieldIndex = 0; fieldIndex < conf.fields.length; fieldIndex++) {
			var f = conf.fields[fieldIndex];
			if (!f.display) {
				continue;
			}
			var cell = document.createElement("th");//theadRow.insertCell(theadRow.cells.length);
			theadRow.appendChild(cell);
			talent.Util.copy(cell, f.listHeaderProperties);
			talent.Util.setStyle(cell, f.listHeaderStyles);
			//table, rowElement, cellElement, fieldConfig, conf, gridData
			//table, rowElement, cellElement, fieldConfig, conf, gridData
			if (f.listHeadRender) {
				f.listHeadRender.config.rowElement = theadRow;
				f.listHeadRender.config.cellElement = cell;
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
			if (!conf.gridData.data[i]){
				continue;
			}
			
			var rowIndex = tbody.rows.length;
			var row = tbody.insertRow(rowIndex);
			
			
			if (conf.highlight) {
				var _className =  conf.highlight.className;
				if (!_className) {
					_className = "talent_grid_highlight";
				}
				var h = function(target, type){
					this.p = function(){
						if (type == "add"){
							talent.Util.addClass(target, _className);
						} else if (type == "remove") {
							talent.Util.removeClass(target, _className);
						}
						
					};
				};
				
				talent.Util.addEventHandler(row, "mouseover", new h(row, "add").p);
				talent.Util.addEventHandler(row, "mouseout", new h(row, "remove").p);
			}
			
			
			rowClassName = rowClassName == prefix + "even" ? prefix + "odd" : prefix + "even";
			talent.Util.addClass(row, rowClassName);
			
			if (!conf.ext.deletedRows){
				conf.ext.deletedRows = [];
			}
			if (!conf.ext.edittedRows){
				conf.ext.edittedRows = [];
			}
			if (conf.ext.deletedRows.ttCons(rowIndex)) {
				talent.Util.addClass(row, "tt_grid_deleted_row");
				row.title = "该记录已被删除";
				row.setAttribute("disabled", true);
				row.disabled = true;
				row.tt_deleted = true;
			}
			if (conf.ext.edittedRows.ttCons(rowIndex)) {
				talent.Util.addClass(row, "tt_grid_editted_row");
				row.title = "该记录刚才被修改过";
			}
			
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
				if (gridData.data[i]){
					if(row.tt_deleted) {
						if (f.listDataRenderConfig && f.listDataRenderConfig.clazz) {
							
						} else {
							var l = gridData.data[i][conf.fields[fieldIndex].name];
							if (l) {
								cell.innerHTML = l;
							}
						}
					} else if (f.listDataRender) {
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
						var l = gridData.data[i][conf.fields[fieldIndex].name];
						if (l) {
							cell.innerHTML = l;
						}
					}
				}
			}
		}
	};
	this.renderTfoot = function(table, tfoot, conf) {
		var isPageable = !(gridData.pagination == false);
		gridData = conf.gridData;
		if (isPageable) {
			var _p = conf.paginationLocation;
			
			if (gridData.pageSize >= 20) {
				_p = "all";
			}
			
			if (!_p || (_p == "all" || _p == "f" || _p == "footer")) {
				var row = tfoot.insertRow(tfoot.rows.length);
				var cell = row.insertCell(row.cells.length);
				cell.colSpan = conf.colsCount;
				var footdiv = ce("div");
				cell.appendChild(footdiv);
				footdiv.className = "digg";
				
				
				var xx = document.createElement("span");
				footdiv.appendChild(xx);
				xx.style.cssFloat = "right";
				xx.style.styleFloat = "right";
				this.renderPagination(xx, conf);
			}
		}
	};
	
	this.renderPagination = function(div, conf) {
		//div.innerHTML = "";
		
		if (conf.paginationRender) {
			conf.paginationRender.call(conf.paginationRender, div, conf);
			return;
		}
		renderPaginationSeperator(div, conf);
		renderPaginationMsg(div, conf);
		renderPaginationSeperator(div, conf);
		renderPaginationGoto(div, conf);
		renderPaginationSeperator(div, conf);
		renderPaginationPre(div, conf);
		renderPaginationCurr(div, conf);
		renderPaginationSuffix(div, conf);
		
		if (conf.isShowCustomPageSize == null || conf.isShowCustomPageSize == undefined || conf.isShowCustomPageSize == true) {
			renderPaginationSeperator(div, conf);
			new paginationCustomPageSizeRender(div, conf).render();
			renderPaginationSeperator(div, conf);
		}
	};
	
	var renderPaginationSeperator = function(div, conf) {
		gridData = conf.gridData;
		var span = ce("span");
		div.appendChild(span);
		//span.innerHTML = "|";
		span.className = "separator";
	};
	
	var renderPaginationMsg = function(div, conf) {
		gridData = conf.gridData;
		
		var e = gridData.pageIndex * gridData.pageSize;
		e = e > gridData.recordCount ? gridData.recordCount : e;
		//alert(gridData.currPageSize);
		if (gridData.currPageSize) {
			e = gridData.currPageSize;
		}
		
		var s = ((gridData.pageIndex - 1) * gridData.pageSize + 1);
		s = s < 0 ? 0 : s;
		
		var span1 = ce("span");
		div.appendChild(span1);
		span1.innerHTML = s + "-" + e;
		
		var span2 = ce("span");
		div.appendChild(span2);
		span2.innerHTML = " / " + gridData.recordCount;
		span2.className = "record";
	};
	var renderPaginationPre = function(div, conf) {
		gridData = conf.gridData;
		
		
		if (gridData.prePageCount <= 0) {
//			//<span class="disabled">&lt;&lt;</span><span class="disabled">&lt; </span>
//			var span1 = ce("span");
//			div.appendChild(span1);
//			span1.className = "first disabled";
//			span1.innerHTML = "";
//			
//			var span2 = ce("span");
//			div.appendChild(span2);
//			span2.className = "pre disabled";
//			span2.innerHTML = "";
		} else {
			//<a href="#?page=2"> &gt;&gt; </a>
			/*第一页和上一页  start*/
			var a1 = ce("a");
			div.appendChild(a1);
			processAObj(a1, 1);
			a1.innerHTML = "";
			a1.className = "arrow first";
			
			var a2 = ce("a");
			div.appendChild(a2);
			processAObj(a2, gridData.pageIndex - 1);
			a2.innerHTML = "";
			a2.className = "arrow pre";
			
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
//			var span1 = ce("span");
//			div.appendChild(span1);
//			span1.className = "disabled";
//			span1.innerHTML = "&gt;&gt;";
//			
//			var span2 = ce("span");
//			div.appendChild(span2);
//			span2.className = "disabled";
//			span2.innerHTML = "&gt;&gt;";
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
				
				if (gridData.pageCount > sufIndexCount + gridData.pageIndex + 1) {
					var span1 = ce("span");
					div.appendChild(span1);
					span1.innerHTML = "...";
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
			a1.innerHTML = "";
			a1.className = "arrow next";
			
			var a2 = ce("a");
			div.appendChild(a2);
			processAObj(a2, gridData.pageCount);
			a2.innerHTML = "";
			a2.className = "arrow last";
			/*最后页和下一页  end*/
		}
	};
	var renderPaginationGoto = function(div, conf) {
		var span1 = ce("span");
		div.appendChild(span1);
		span1.className = "goto";
		span1.innerHTML = "去第";
		
		var spanInput = ce("span");
		div.appendChild(spanInput);
		
		var span2 = ce("span");
		div.appendChild(span2);
		span2.className = "goto";
		span2.innerHTML = "页";

		var p = ce("input");
		spanInput.appendChild(p);
		p.className = "goto_input";
		p.value = gridData.pageIndex;
		p.title = "输入页码，然后按回车键";
		try{$(p).tooltip({'title':p.title});}catch(e){}  //加tooltip
		
		try {tt.vf.num.addObj(p);}catch(e){}  //加数字验证
		
		$(p).keydown(function (event) {  
		    var keycode = event.which;  
		    if (keycode == 13) {  
		        try{$(p).tooltip('hide');}catch(e){}
				talent.ui.GridProxy[talent.ui.GridProxy.index].reload(p.value);
		    }
		 });
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
			for (var i = 20; i <= maxPageSize; i+=step) {
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
				var span = ce("span");
				div.appendChild(span);
				span.innerHTML = "每页显示";
				
				var selectElement = ce("select");
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
		this.config.ext = {};
		conf = this.config;
		var pageSize = 10;
		
		if (_pageSize) {
			pageSize = _pageSize;
		} else if (conf.gridData && conf.gridData.pageSize){
			pageSize = conf.gridData.pageSize;
		}
		
		postData = [{name:keyOfPageIndex, value: pageIndex},{name:keyOfPageSize, value:pageSize}];
		postData = postData.concat(conf.postData);
		var ajaxConfig = {
			url: conf.url,       //请求的url
			form: conf.form,     //要提交的form对象
			//method: "post",            //post/get。默认为post
			//async: true,               //true/false。默认为true
			data:postData,
			//queryStringData: "name=科比&id=888888888888",//"e=eee&a=ddd&c=kkkk",
		    //encoding:     'utf-8', //默认为'utf-8'
		    //requestHeaders: null,//{"",""},
		    thisObj: this,
			success: function(request) {
				//alert(conf.url);
				//alert(request.responseText);
				
				try{
					art.dialog.get('tt_grid_EF893L').close();
				}catch(e){}
				
				var obj = eval("(" + request.responseText + ")");
				if (obj.result && obj.title && obj.msg){
					tt_showResult(obj);
				} else {
					conf.gridData = eval("(" + request.responseText + ")");
					this.config = conf;
					this.render();
				}
			}
		};
		try {
			art.dialog({
			    title : '正在处理... ...',
			    content: '正在处理，请稍后... ...',
			    fixed: true,
			    id: 'tt_grid_EF893L'
			}).lock();
			
		}catch(e){}
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
