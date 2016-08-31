/**
 * 单个输入框的值创建者
 */
talent.qe.BaseConditionSqlCreator = talent.Class.extend({
	TYPE_PARAM:"param",
	TYPE_VALUE:"value",
	setConfig:function(_config){
		this.config = _config;
	},
	getConfig:function() {
		return this.config;
	},
	getValueByCompareOperator: function(_value, compareOperatorValue) {
		var v = _value;
		if (compareOperatorValue == "like") {
			v = "%" + v + "%";
		} else if(compareOperatorValue == "left like") {
			v += "%";
		} else if(compareOperatorValue == "right like") {
			v = "%" + v;
		}
		return v;
		
	}
});

talent.qe.field.SqlValueCreator = talent.qe.BaseCreator.extend({
	//config:{fieldName:f,inputElements:inputElements}
	create:function (_value, compareOperatorValue) {
		var field = talent_qe_fieldMap[this.config.fieldName];
		var _creatorConfig = talent.qe.myTypeMap[talent.qe.sqlTypeMap[field.sqlType]["myType"]];
		var creatorClass = _creatorConfig.sqlValueCreatorConfig.clazz;
		var dispatchCreator = talent.Util.instanceByClass(creatorClass);
		var conf = {fieldName:this.config.fieldName, inputElements:this.config.inputElements};
		talent.Util.copy(conf, _creatorConfig.sqlValueCreatorConfig.config);
		dispatchCreator.setConfig(conf);
		//ret = {value:"?", parameters:[{name: this.config.fieldName, value:this.config.inputElements[0].value}]}
		var ret = dispatchCreator.create(_value, compareOperatorValue);
		return ret;
	}
});

talent.qe.StringSqlValueCreator = talent.qe.BaseConditionSqlCreator.extend({
	//config:{fieldName:f}
	create:function(_value, compareOperatorValue) {
		var ret = {"value":"?", "sqlParameters":[{name: this.config.fieldName, value:this.getValueByCompareOperator(_value, compareOperatorValue)}]};
		return ret;
	}
});

talent.qe.NumberSqlValueCreator = talent.qe.BaseConditionSqlCreator.extend({
	//config:{fieldName:f}
	create:function(_value, compareOperatorValue) {
		var ret = {"value":"?", "sqlParameters":[{name: this.config.fieldName, value:this.getValueByCompareOperator(_value, compareOperatorValue)}]};
		//alert(JSON.stringify(ret));
		return ret;
	}
});

talent.qe.DateSqlValueCreator = talent.qe.BaseConditionSqlCreator.extend({
	//config:{fieldName:f}
	create:function(_value, compareOperatorValue) {
		var ret = {"value":"{" + this.config.type + " '" + _value + "'}", "sqlParameters":null};
		return ret;
	}
});

