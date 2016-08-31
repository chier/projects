
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
		form.className = "form-horizontal container";  //form-horizontal
		
		var fieldset = document.createElement("fieldset");
		form.appendChild(fieldset);
		
		
		var legend = document.createElement("legend");
		fieldset.appendChild(legend);
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
		for (var i = 0; i < config.fieldNames.length; i++) {
			fieldName = config.fieldNames[i];
			field = talent_qe_fieldMap[fieldName];
			
			if (i % 2 == 0) {
				var rowContainer = document.createElement("div");
				rowContainer.className = "row";
				this.fieldset.appendChild(rowContainer);
				lastRowContainer = rowContainer;
			}
			
			var fieldContainer = document.createElement("div");
			fieldContainer.className = "control-group span6";
			rowContainer.appendChild(fieldContainer);
			
			this.renderField(field, this.layout, fieldContainer);
		}
		this.layout.layout();
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
	renderInput:function(_field, container, isRenderLogic, isRenderAddCondition, isRenderRemoveCondition) {
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
		
		var inputContainer = container;//_layout.next("input");
		var container = document.createElement("div");
		var container1 = document.createElement("span");   //"添加条件"/"删除条件项"图标的span
		var container2 = document.createElement("span");   //逻辑操作符、比较操作符、比较值等元素的span
		
		inputContainer.appendChild(container);
		inputContainer.className += " condition_inputs";
		container.appendChild(container2);  //先渲染放"添加条件"/"删除条件项"图标的span，因为这个是float=right的，必须要先显示
		container.appendChild(container1);
		container.className += " condition_input";
		//container1.className = "condition_input1";
		
		//添加鼠标事件 start
		var h = function(container, _className, action) {
			this.a = function () {
				if (action == "add"){
					talent.Util.addClass(container, _className);
				} else {
					talent.Util.removeClass(container, _className);
				}
				//talent.ui.GridProxy[talent.ui.GridProxy.index].reload(selectElement.value);
			};
		};
		talent.Util.addEventHandler(container, "mouseover", new h(container, "condition_highlight", "add").a);
		talent.Util.addEventHandler(container, "mouseout", new h(container, "condition_highlight", "remove").a);
		//添加鼠标事件 end
		
		//logic operator
		if (isRenderLogic == true){
			conf = {"container": container1, "fieldName":_field.fieldName, "conditionItem":conditionItem};
			talent.Util.copy(conf, _field.logicOperatorRenderConfig.config);
			var logicOperatorRender = talent.Util.instanceByClass(_field.logicOperatorRenderConfig.clazz);
			logicOperatorRender.setConfig(conf);
			logicOperatorRender.render();
			_field["logicOperatorRender"] = logicOperatorRender;
		}
		
		
		//compare operator
		conf = {"container": container1, "fieldName":_field.fieldName, "conditionItem":conditionItem};
		talent.Util.copy(conf, _field.compareOperatorRenderConfig.config);
		var compareOperatorRender = talent.Util.instanceByClass(_field.compareOperatorRenderConfig.clazz);
		compareOperatorRender.setConfig(conf);
		compareOperatorRender.render();
		_field["compareOperatorRender"] = compareOperatorRender;
		
		//input element
		conf = {"container": container1, "fieldName":_field.fieldName, "conditionItem":conditionItem};
		talent.Util.copy(conf, _field.inputRenderConfig.config);
		var inputRender = talent.Util.instanceByClass(_field.inputRenderConfig.clazz);
		inputRender.setConfig(conf);
		inputRender.render();
		_field["inputRender"] = inputRender;
		
		if (isRenderAddCondition == true) {
			this.renderAddCondition(_field, container2, inputContainer);
		}
		if (isRenderRemoveCondition == true){
			this.renderRemoveCondition(_field, container2, inputContainer, indexOfConditionItem);
		}
	},
	
	renderAddCondition:function(_field, _iconcontainer, inputContainer) {
		_iconcontainer.className += " addCondition";
		_iconcontainer.title = "添加条件项";
		var h = function(_field, __inputContainer) {
			this.a = function () {
				talent_qe_pageRender.queryFormRender.renderInput(_field, __inputContainer, true, false, true);
				//talent.ui.GridProxy[talent.ui.GridProxy.index].reload(selectElement.value);
			};
		};
		talent.Util.addEventHandler(_iconcontainer, "click", new h(_field, inputContainer).a);
	},
	
	renderRemoveCondition:function(_field, _iconcontainer, inputContainer, indexOfConditionItem) {
		_iconcontainer.className += " removeCondition";
		_iconcontainer.title = "删除条件项";
		//_container.onclick = alert;
		
		var h = function(_field, __inputContainer, indexOfConditionItem) {
			this.a = function () {
				//_iconcontainer.parentNode.removeNode(true);
				var elementToBeRemoved = _iconcontainer.parentNode;
				elementToBeRemoved.parentNode.removeChild(elementToBeRemoved);
				_field["conditionItems"][indexOfConditionItem] = null;
				//oDiv.parentNode.removeChild(oDiv) 
				//talent_qe_pageRender.queryFormRender.renderInput(_field, __inputContainer, false, true);
				//talent.ui.GridProxy[talent.ui.GridProxy.index].reload(selectElement.value);
			};
		};
		talent.Util.addEventHandler(_iconcontainer, "click", new h(_field, inputContainer, indexOfConditionItem).a);
	},
	renderMoreCondition: function() {
		
	},
	renderBtn: function () {
		var btnContainer = document.createElement("div");
		this.fieldset.appendChild(btnContainer);
		btnContainer.className = "btn_container";
		
		
		
		//<a href="#" class="easyui-linkbutton" icon="icon-search">Search</a>
		var querybtn = document.createElement("a");
		btnContainer.appendChild(querybtn);
		//querybtn.id = "qe_querybtn";
		querybtn.href = "javascript:qe_query();";
		querybtn.icon = "icon-search";
		querybtn.className = "btn btn-primary";
		
		querybtn.innerHTML = "查  询";

	}
});