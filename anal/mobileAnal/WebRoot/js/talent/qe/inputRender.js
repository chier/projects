

talent.qe.field.InputRender = talent.qe.BaseRender.extend({
	/*config:{fieldName:f1,container:c}*/
	render:function () {
		//talent.qe.myTypeMap[talent.qe.sqlTypeMap[sqlType]["myType"]].inputRenderConfig
  		//talent.qe.myTypeMap[talent.qe.sqlTypeMap[sqlType]["myType"]].config
		var _render = talent.qe.myTypeMap[talent.qe.sqlTypeMap[talent_qe_fieldMap[this.config.fieldName].sqlType]["myType"]];
		var renderClass = _render.inputRenderConfig.clazz;
		
		var dispatchRender = talent.Util.instanceByClass(renderClass);
		var conf = {container:this.config.container, fieldName:this.config.fieldName, properties:{name:this.config.fieldName}};
		
		var _field = talent_qe_fieldMap[this.config.fieldName];
		
		var conditionItem = this.config.conditionItem;
		conditionItem.inputElements = [];
		if (conditionItem.compareOperator.value == "between" || conditionItem.compareOperator.value == "not between") {
			conf = {container:this.config.container, fieldName:this.config.fieldName, properties:{name:this.config.fieldName + "_0"}};
			conf.properties.isBetween = "true";
			talent.Util.copy(conf, _render.inputRenderConfig.config);
			dispatchRender.setConfig(conf);
			var inputElement1 = dispatchRender.render();
			talent.Util.addEventHandler(inputElement1, "change", qe_displayCondition);
			talent.Util.addEventHandler(inputElement1, "keyup", qe_displayCondition);
			talent.Util.addEventHandler(inputElement1, "click", qe_displayCondition);
			inputElement1.style.width = "150px";
			//_field.inputElements[0] = inputElement1;
			conditionItem.inputElements[0] = inputElement1;
			
			var _ = document.createTextNode("~");//.createElement("label")
			this.config.container.appendChild(_);
			
			conf = {container:this.config.container, fieldName:this.config.fieldName, properties:{name:this.config.fieldName + "_1",isBetween:"true"}};
			talent.Util.copy(conf, _render.inputRenderConfig.config);
			dispatchRender.setConfig(conf);
			var inputElement2 = dispatchRender.render();
			talent.Util.addEventHandler(inputElement2, "change", qe_displayCondition);
			talent.Util.addEventHandler(inputElement2, "keyup", qe_displayCondition);
			talent.Util.addEventHandler(inputElement2, "click", qe_displayCondition);
			inputElement2.style.width = "150px";
			//_field.inputElements[1] = inputElement2;
			this.config.conditionItem.inputElements[1]  = inputElement2;
		} else {
			talent.Util.copy(conf, _render.inputRenderConfig.config);			
			dispatchRender.setConfig(conf);
			var inputElement = dispatchRender.render();
			talent.Util.addEventHandler(inputElement, "change", qe_displayCondition);
			talent.Util.addEventHandler(inputElement, "keyup", qe_displayCondition);
			talent.Util.addEventHandler(inputElement, "click", qe_displayCondition);
			//_field.inputElements[0] = inputElement;
			this.config.conditionItem.inputElements[0] = inputElement;
		}
	}
});