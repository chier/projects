
/**
 * 
 */
talent.qe.QueryFormRender = talent.qe.BaseRender.extend({
//	con:{
//	    "fieldNames": [
//	        "idid",
//	        "namename"
//	    ],
//	    "requiredFields": [
//	        "idid"
//	    ],
//	    "label": "人员查询",
//	    "layout": {
//	        "layout": "talent.ui.FlexGridLayout",
//	        "config": {
//	            "columns": 4,
//	            "hgap": 1,
//	            "vgap": 1
//	        }
//	    },
//	    "container": {}
//	},
	
	layout:null,
	panelId:"QueryFormRender_panelId",
	panel:null,
	
	render:function() {
		config = this.config;
		config.container.innerHTML = "";
		
		this.renderTitle();
		//config.container.title = config.label;
		this.renderFields(config.fieldNames);
		this.renderMoreCondition();
		this.renderBtn();
		
		//var html = config.container.innerHTML;
		//config.container.innerHTML = "";
		
		//下面这段代码用于ext
//		this.panel = new Ext.Panel({
//	        applyTo: config.container.parentNode,
//	        contentEl: config.container,
//	        title: config.label,
//	        id: this.panelId,
//	        collapsible:true,
//	        width: "100%"
//	    });
	},
	renderTitle:function(){
		var form = document.createElement("form");
		this.config.container.appendChild(form);
		form.className = "form-horizontal container myform";  //form-horizontal
		
		var fieldset = document.createElement("fieldset");
		form.appendChild(fieldset);
		
		//$(fieldset).attr("class", "span6");
		
		
		var legend = document.createElement("legend");
		fieldset.appendChild(legend);
		$(legend).attr("class", "mylegend");
		
		legend.innerHTML = this.config.label;
		
		this.fieldset = fieldset;
	},
	renderFields: function(fieldNames) {
		config = this.config;
		this.layout = talent.Util.instanceByClass(config.layout.clazz);
		config.layout.config.container = config.container;
		this.layout.setConfig(config.layout.config);
		var field;
		var lastRowContainer = null;
		
		//<ul id="tab" class="nav nav-tabs">
		var ul = document.createElement("ul");
		this.fieldset.appendChild(ul);
		ul.className = "nav nav-tabs myul";
		$(ul).attr("id", "nav-tabs_4qe_id");
		
		//<div id="myTabContent" class="tab-content">  存放字段的输入框等内容
		var myTabContent = document.createElement("div");
		myTabContent.className = "tab-content";
		this.fieldset.appendChild(myTabContent);
		
		for (var i = 0; i < config.fieldNames.length; i++) {
			fieldName = config.fieldNames[i];
			field = talent_qe_fieldMap[fieldName];
			
			var id4Tab = "__tab_id_4__" + fieldName;
			var id4Tooltip = "__tooltip_4__" + fieldName;
			
//			<li class="active">
//			<a href="#home" data-toggle="tab">Home</a>
			var li = document.createElement("li");
			ul.appendChild(li);
			
			var a = document.createElement("a");
			li.appendChild(a);
			$(a).attr("data-toggle","tab");
			$(a).attr("data-original-title", "为["+field.label+"]设置查询条件");
			$(a).attr("id", id4Tooltip);
			$(a).attr("href", "#" + id4Tab);
			
			$(a).tooltip({placement:'top'});
			
			if (i == 0) {
				li.className = "active";
			}
			a.innerHTML = field.label;
			
			//<div class="tab-pane fade in active" id="home">
			var fieldContainer = document.createElement("div");
			myTabContent.appendChild(fieldContainer);
			fieldContainer.id = id4Tab;
			fieldContainer.className = "tab-pane fade";
			if (i == 0) {
				fieldContainer.className += " in active";
			}
			
			this.renderField(field, this.layout, fieldContainer);
		}
		
		$('#nav-tabs_4qe_id').tab();
	},
	
	renderField: function(_field, _layout, fieldContainer) {
		//label
		
		var labelContainer = document.createElement("div");
		labelContainer.className = "control-label";
//		fieldContainer.appendChild(labelContainer);
		
		var inputContainer = document.createElement("div");
		inputContainer.className = " ";
		fieldContainer.appendChild(inputContainer);
		
		
//		var labelContainer = _layout.next("label");
		this.renderLabel(_field, inputContainer);
		//input
		
		
//		var container = _layout.next("input");
		this.renderInput(_field, inputContainer, false, true, false);
	},
	renderLabel:function(_field, labelContainer) {
		conf = {"container": labelContainer, "fieldName":_field.fieldName};
		talent.Util.copy(conf, _field.labelRenderConfig.config);
		var labelRender = talent.Util.instanceByClass(_field.labelRenderConfig.clazz);
		labelRender.setConfig(conf);
		labelRender.render();
		_field["labelRender"] = labelRender;
	},
	renderInput:function(_field, inputContainer, isRenderLogic, isRenderAddCondition, isRenderRemoveCondition) {
		var conditionItems = _field["conditionItems"];
		var indexOfConditionItem = 0;
		if (!conditionItems){
			conditionItems = [];
			conditionItems[0] = {};
			_field["conditionItems"] = conditionItems;
		} else {
			indexOfConditionItem = conditionItems.length;
			conditionItems[indexOfConditionItem] = {};
		}
		
		var conditionItem = conditionItems[indexOfConditionItem];
		
//		var inputContainer = inputContainer;//_layout.next("input");
		var container = document.createElement("div");
		
		
		var container0 = document.createElement("span");   //不放任何东西，只是用来解决谷歌的一个bug(对齐)
		var container1 = document.createElement("span");   //逻辑操作符、比较操作符、比较值等元素的span
		var container2 = document.createElement("span");   //"添加条件"/"删除条件项"图标的span
		
		
		inputContainer.appendChild(container);
		inputContainer.className += " condition_inputs";
		container.appendChild(container0);
		container.appendChild(container1);
		container.appendChild(container2);  //先渲染放"添加条件"/"删除条件项"图标的span，因为这个是float=right的，必须要先显示
		
		container.className += " condition_input";
		//container1.className = "condition_input1";
		
		//添加鼠标事件 start
//		var h = function(container, _className, action) {
//			this.a = function () {
//				if (action == "add"){
//					talent.Util.addClass(container, _className);
//				} else {
//					talent.Util.removeClass(container, _className);
//				}
//				//talent.ui.GridProxy[talent.ui.GridProxy.index].reload(selectElement.value);
//			};
//		};
//		talent.Util.addEventHandler(container, "mouseover", new h(container, "condition_highlight", "add").a);
//		talent.Util.addEventHandler(container, "mouseout", new h(container, "condition_highlight", "remove").a);
		//添加鼠标事件 end
		
		//1, logic operator
		conf = {"container": container1, "fieldName":_field.fieldName, "field":_field, "conditionItem":conditionItem};
		talent.Util.copy(conf, _field.logicOperatorRenderConfig.config);
		var logicOperatorRender = talent.Util.instanceByClass(_field.logicOperatorRenderConfig.clazz);
		logicOperatorRender.setConfig(conf);
		logicOperatorRender.render({'isRenderLogic':isRenderLogic});
		_field["logicOperatorRender"] = logicOperatorRender;
		
		
		//2, compare operator
		conf = {"container": container1, "fieldName":_field.fieldName, "conditionItem":conditionItem};
		talent.Util.copy(conf, _field.compareOperatorRenderConfig.config);
		var compareOperatorRender = talent.Util.instanceByClass(_field.compareOperatorRenderConfig.clazz);
		compareOperatorRender.setConfig(conf);
		compareOperatorRender.render();
		_field["compareOperatorRender"] = compareOperatorRender;
		
		//3, input element
		conf = {"container": container1, "fieldName":_field.fieldName, "conditionItem":conditionItem};
		talent.Util.copy(conf, _field.inputRenderConfig.config);
		var inputRender = talent.Util.instanceByClass(_field.inputRenderConfig.clazz);
		inputRender.setConfig(conf);
		inputRender.render();
		_field["inputRender"] = inputRender;
		
		//4, add or remove icon
		if (isRenderAddCondition == true) {
			this.renderAddCondition(_field, container2, inputContainer);
		}
		if (isRenderRemoveCondition == true){
			this.renderRemoveCondition(_field, container2, inputContainer, indexOfConditionItem);
		}
	},
	
	renderAddCondition:function(_field, _iconcontainer, inputContainer) {
		_iconcontainer.className += "btn btn-success addCondition";
		_iconcontainer.innerHTML = '<i class="icon-plus icon-white"></i>';
//		_iconcontainer.title = "添加条件项";
		$(_iconcontainer).attr("data-original-title", "添加条件项");
		$(_iconcontainer).tooltip({placement:'right'});
//		$(_iconcontainer).tooltip('show');
		
		var h = function(_field, __inputContainer) {
			this.a = function () {
				talent_qe_pageRender.queryFormRender.renderInput(_field, __inputContainer, true, false, true);
				//talent.ui.GridProxy[talent.ui.GridProxy.index].reload(selectElement.value);
			};
		};
		talent.Util.addEventHandler(_iconcontainer, "click", new h(_field, inputContainer).a);
	},
	
	renderRemoveCondition:function(_field, _iconcontainer, inputContainer, indexOfConditionItem) {
		_iconcontainer.className += "btn btn-warning addCondition";
		_iconcontainer.innerHTML = '<i class="icon-minus icon-white"></i>';
//		_iconcontainer.title = "删除条件项";
		$(_iconcontainer).attr("data-original-title", "删除条件项");
		$(_iconcontainer).tooltip({placement:'right'});
		
		//_container.onclick = alert;
		
		var h = function(_field, __inputContainer, indexOfConditionItem) {
			this.a = function () {
				//_iconcontainer.parentNode.removeNode(true);
				$(_iconcontainer).tooltip('hide');
				var elementToBeRemoved = _iconcontainer.parentNode;
				elementToBeRemoved.parentNode.removeChild(elementToBeRemoved);
				_field["conditionItems"][indexOfConditionItem] = null;
			};
		}; 
		talent.Util.addEventHandler(_iconcontainer, "click", new h(_field, inputContainer, indexOfConditionItem).a);
	},
	renderMoreCondition: function() {
		
	},
	renderBtn: function () {
		var row = document.createElement("div");
		this.fieldset.appendChild(row);
		row.className = "row";
		
		var conditionDiv = document.createElement("div");
		row.appendChild(conditionDiv);
		conditionDiv.className = "span6";
		conditionDiv.id = "qe_condition4ShowDiv";
		
		
		var btnDiv = document.createElement("div");
		row.appendChild(btnDiv);
		btnDiv.className = "span4 btn_container";
		
		//<a href="#" class="easyui-linkbutton" icon="icon-search">Search</a>
		var querybtn = document.createElement("a");
		btnDiv.appendChild(querybtn);
		//querybtn.id = "qe_querybtn";
		querybtn.href = "javascript:qe_query();";
		querybtn.icon = "icon-search";
		querybtn.className = "btn ";
		
		querybtn.innerHTML = '<i class="icon-search"></i>  查  询';
		
		//old button
//		var btnContainer = document.createElement("div");
//		this.fieldset.appendChild(btnContainer);
//		btnContainer.className = "btn_container";
//		
//		//<a href="#" class="easyui-linkbutton" icon="icon-search">Search</a>
//		var querybtn = document.createElement("a");
//		btnContainer.appendChild(querybtn);
//		//querybtn.id = "qe_querybtn";
//		querybtn.href = "javascript:qe_query();";
//		querybtn.icon = "icon-search";
//		querybtn.className = "btn ";
//		
//		querybtn.innerHTML = '<i class="icon-search"></i>  查  询';

	}
});