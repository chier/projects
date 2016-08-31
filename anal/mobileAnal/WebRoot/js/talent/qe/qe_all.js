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
}

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
	}catch (e) {
		alert("error message from talent.qe.invokeRender: render class=" + renderClass + "\r\n" + e);
	}
}

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
talent.qe.compareOperationMap_en = {
	"<" : {
		value : "<",
		label : "<",
		title: "小于"
	},
	"<=" : {
		value : "<=",
		label : "<=",
		title: "小于或等于"
	},
	"=" : {
		value : "=",
		label : "=",
		title: "等于"
	},
	"<>" : {
		value : "<>",
		label : "<>",
		title: "不等于"
	},
	">" : {
		value : ">",
		label : ">",
		title: "大于"
	},
	">=" : {
		value : ">=",
		label : ">=",
		title: "大于或等于"
	},
	"in" : {
		value : "in",
		label : "in",
		title: "包含"
	},
	"not in" : {
		value : "not in",
		label : "not in",
		title: "不包含"
	},
	"not between" : {
		value : "not between",
		label : "not between",
		title: "不在此范围"
	},
	"between" : {
		value : "between",
		label : "between",
		title: "在此范围"
	},
	"like" : {   //两边都模糊匹配like "%ddd%"
		value : "like",
		label : "like",
		title: "模糊匹配"
	},
	"left like" : {  //后端模糊匹配like "ddd%"
		value : "left like",
		label : "left like",
		title: "左边精确,右边模糊"
	},
	"right like" : {  //前端模糊匹配like "%ddd"
		value : "right like",
		label : "right like",
		title: "右边精确,左边模糊"
	}
};
talent.qe.compareOperationMap = talent.qe.compareOperationMap_en;



/**
 * 逻辑操作符map
 * @type 
 */
talent.qe.logicOperationMap_en = {
	"or" : {
		value : "or",
		label : "or",
		title: "或者"
	},
	"and" : {
		value : "and",
		label : "and",
		title: "并且"
	}
}
talent.qe.logicOperationMap = talent.qe.logicOperationMap_en;

talent.qe.sqlTypeMap = {//talent.qe.sqlTypeMap[sqlType]["myType"]
	// talent.qe.sqlTypeMap[sqlType][myType]--[v,n,int,short,f,d,boolean,ts,t,date,long]
	"1" :    {methodNameOfSetParameter:"setString", myType:"v"},
	"70" :   {methodNameOfSetParameter:"setURL", myType:"v"},
	"2" :    {methodNameOfSetParameter:"setBigDecimal", myType:"n"},
	"3" :    {methodNameOfSetParameter:"setBigDecimal", myType:"n"},
	"4" :    {methodNameOfSetParameter:"setInt", myType:"int"},
	"5" :    {methodNameOfSetParameter:"setShort", myType:"short"},
	"6" :    {methodNameOfSetParameter:"setFloat", myType:"f"},
	"7" :    {methodNameOfSetParameter:"setFloat", myType:"f"},
	"8" :    {methodNameOfSetParameter:"setDouble", myType:"f"},
	"12" :   {methodNameOfSetParameter:"setString", myType:"v"},  
	"16" :   {methodNameOfSetParameter:"setBoolean", myType:"boolean"},
	"93" :   {methodNameOfSetParameter:"setTimestamp", myType:"ts"},
	"-7" :   {methodNameOfSetParameter:"setBoolean", myType:"boolean"},
	"92" :   {methodNameOfSetParameter:"setTime", myType:"t"},
	"-6" :   {methodNameOfSetParameter:"setByte", myType:"v"},
	"-5" :   {methodNameOfSetParameter:"setLong", myType:"long"},
	"-4" :   {methodNameOfSetParameter:"setBytes", myType:"v"}, //
	"-3" :   {methodNameOfSetParameter:"setBytes", myType:"v"}, //
	"91" :   {methodNameOfSetParameter:"setDate", myType:"date"},
	"-2" :   {methodNameOfSetParameter:"setBytes", myType:"v"}, //
	"-1" :   {methodNameOfSetParameter:"setString", myType:"v"},
	"2003" : {methodNameOfSetParameter:"setArray", myType:"v"},  //
	"2000" : {methodNameOfSetParameter:"setObject", myType:"v"},
	"2006" : {methodNameOfSetParameter:"setRef", myType:"v"},
	"2004" : {methodNameOfSetParameter:"setBlob", myType:"v"},
	"2005" : {methodNameOfSetParameter:"setClob", myType:"v"}
};

/**
 * talent.qe.myTypeMap[talent.qe.sqlTypeMap[sqlType][myType]].inputRenderConfig
 * talent.qe.myTypeMap[talent.qe.sqlTypeMap[sqlType][myType]].config
 * @type 
 */
talent.qe.myTypeMap = {
	//compareOperatorRender,conditionSqlCreatorConfig,sqlValueCreatorConfig
	"v":       {inputRenderConfig:{clazz:"talent.ui.Text",config:{}},    sqlValueCreatorConfig:{clazz:"talent.qe.StringSqlValueCreator", config:{}}},
	"n":       {inputRenderConfig:{clazz:"talent.ui.Text",config:{type:"n"}},    sqlValueCreatorConfig:{clazz:"talent.qe.NumberSqlValueCreator", config:{}}},
	"int":     {inputRenderConfig:{clazz:"talent.ui.Text",config:{type:"int"}},    sqlValueCreatorConfig:{clazz:"talent.qe.NumberSqlValueCreator", config:{}}},
	"short":   {inputRenderConfig:{clazz:"talent.ui.Text",config:{type:"short"}},    sqlValueCreatorConfig:{clazz:"talent.qe.NumberSqlValueCreator", config:{}}},
	"f":       {inputRenderConfig:{clazz:"talent.ui.Text",config:{type:"f"}},    sqlValueCreatorConfig:{clazz:"talent.qe.NumberSqlValueCreator", config:{}}},
	"d":       {inputRenderConfig:{clazz:"talent.ui.Text",config:{type:"d"}},    sqlValueCreatorConfig:{clazz:"talent.qe.NumberSqlValueCreator", config:{}}},
	"boolean": {inputRenderConfig:{clazz:"talent.ui.Checkbox",config:{}},sqlValueCreatorConfig:{clazz:"talent.qe.NumberSqlValueCreator", config:{type:"boolean"}}},
	"ts":      {inputRenderConfig:{clazz:"talent.ui.Date",config:{type:"ts"}},    sqlValueCreatorConfig:{clazz:"talent.qe.DateSqlValueCreator", config:{type:"ts"}}},
	"t":       {inputRenderConfig:{clazz:"talent.ui.Date",config:{type:"t"}},    sqlValueCreatorConfig:{clazz:"talent.qe.DateSqlValueCreator", config:{type:"t"}}},
	"date":    {inputRenderConfig:{clazz:"talent.ui.Date",config:{type:"d"}},    sqlValueCreatorConfig:{clazz:"talent.qe.DateSqlValueCreator", config:{type:"d"}}},
	"long":    {inputRenderConfig:{clazz:"talent.ui.Text",config:{type:"long"}},    sqlValueCreatorConfig:{clazz:"talent.qe.NumberSqlValueCreator", config:{}}}
};


talent.qe.compareOperationMap_v = [talent.qe.compareOperationMap['='], talent.qe.compareOperationMap['<>'],
				talent.qe.compareOperationMap['like'],talent.qe.compareOperationMap['right like'],talent.qe.compareOperationMap['left like']];
talent.qe.compareOperationMap_n = [talent.qe.compareOperationMap['='], talent.qe.compareOperationMap['<'],
				talent.qe.compareOperationMap['<='], talent.qe.compareOperationMap['<>'],
				talent.qe.compareOperationMap['>'], talent.qe.compareOperationMap['>=']];
talent.qe.compareOperationMap_boolean = [talent.qe.compareOperationMap['='],talent.qe.compareOperationMap['<>']];
talent.qe.compareOperationMap_t = [talent.qe.compareOperationMap['between'], talent.qe.compareOperationMap['not between']];
				
//---------------  global var end ----------------------//
				
				
				
/**
 * 配置默认的属性
 * 
 * @type
 */
talent.qe.Config = {
	
	labelRenderConfig : {clazz:"talent.qe.LabelRender", config:{}},
	/**
	 * 一些默认的值
	 */
	model:{
		pageRenderConfig : {clazz:"talent.qe.PageRender", config:{}},
		rowEventHandlerConfig : {clazz:"talent.qe.RowEventHandler",config:{}},
		queryFormRenderConfig : {
			clazz:"talent.qe.QueryFormRender",
			layout:{
				clazz:"talent.ui.FlexGridLayout",
				config:{
					columns:4
				}
			}
		},
		listResultRenderConfig : {clazz:"talent.qe.ListResultRender",config:{}}
	},
	field : {
		inputRenderConfig : {clazz:"talent.qe.field.InputRender", config:{}},
		logicOperatorRenderConfig : {clazz:"talent.qe.field.LogicOperatorRender",config:{items:[talent.qe.logicOperationMap["or"],talent.qe.logicOperationMap["and"]]}},
		compareOperatorRenderConfig : {clazz:"talent.qe.field.CompareOperatorRender",config:{}},
		sqlValueCreatorConfig : {clazz:"talent.qe.field.SqlValueCreator",config:{}},
		conditionSqlCreatorConfig : {clazz:"talent.qe.field.ConditionSqlCreator",config:{}},
		listHeadRenderConfig : {clazz:"talent.qe.field.ListHeadRender",config:{}},
		listDataRenderConfig : {clazz:"talent.qe.field.ListDataRender",config:{}}
	},

	compareOperationMap : {
		// v(string),n(number),int,short,long,f(float),d(double),boolean,t(time),date,ts(timestamp)
		// all: compareOperationMap,
		// 字符型
		"v" : talent.qe.compareOperationMap_v,
		// 数字类型
		"n" : talent.qe.compareOperationMap_n,
		"short" : talent.qe.compareOperationMap_n,
		"int" : talent.qe.compareOperationMap_n,
		"long" : talent.qe.compareOperationMap_n,
		"f" : talent.qe.compareOperationMap_n,
		"d" : talent.qe.compareOperationMap_n,
		"boolean" : talent.qe.compareOperationMap_boolean,
		// 时间类型
		"t" : talent.qe.compareOperationMap_t,
		// 时间类型
		"date" : talent.qe.compareOperationMap_t,
		// timestamp类型
		"ts" : talent.qe.compareOperationMap_t
	}
};
talent.Ns.add("talent.qe.field");


/**
 * pagerender(页面渲染者)
 * @type 
 */
talent_qe_pageRender = null;
/**
 * key : fieldName(全部小写), value : field对象
 * 
 * @type
 */
talent_qe_fieldMap = {};

/**
 * model对象
 * @type 
 */
talent_qe_model = null;//qe_model

/**
 * 
 * @type String
 */
talent_qe_url = null;//"x.json_c?talent_class=qe.QueryEngine&talent_method=query";

/**
 * 作为查询条件的字段名数组["field1", "field2"]
 * @type 
 */
talent_qe_conditionFields = []; 

/**
 * 显示在查询列表的字段名数组["field1", "field2"]
 * @type 
 */
talent_qe_listFields = [];

/**
 * request.getContextPath()
 * @type String
 */
qe_contextpath = "";
//---------------  global var end ----------------------//

talent.qe.BaseInintializer = talent.Class.extend({
	init:function() {
		
	},
	setConfig:function(config) {
		this.config = config;
		return this;
	}
});

/**
 * 完善前台配置的字段信息
 */
talent.qe.FieldInintializer = talent.qe.BaseInintializer.extend({
	initValue : function() {
		frontFields = this.config.frontFields;
		backFields = this.config.backFields;
		
		for (var j = 0; j < frontFields.length; j++) {
			talent_qe_fieldMap[frontFields[j].name.toLowerCase()] = frontFields[j];
		}
		
		for (var i = 0; i < backFields.length; i++) {
			var backField = backFields[i];
			backField.fieldName = backField.fieldName.toLowerCase();
			frontField = talent_qe_fieldMap[backField.fieldName];
			if (frontField) {
				talent.Util.copy(frontField, backField);
				frontField.labelRenderConfig = frontField.labelRenderConfig ? frontField.labelRenderConfig : talent.qe.Config.labelRenderConfig;
				frontField.labelRenderConfig.clazz = frontField.labelRenderConfig.clazz ? frontField.labelRenderConfig.clazz : talent.qe.Config.labelRenderConfig.clazz;
				
				frontField.logicOperatorRenderConfig = frontField.logicOperatorRenderConfig ? frontField.logicOperatorRenderConfig : talent.qe.Config.field.logicOperatorRenderConfig;
				frontField.logicOperatorRenderConfig.clazz = frontField.logicOperatorRenderConfig.clazz ? frontField.logicOperatorRenderConfig.clazz : talent.qe.Config.field.logicOperatorRenderConfig.clazz;
				
				frontField.compareOperatorRenderConfig = frontField.compareOperatorRenderConfig ? frontField.compareOperatorRenderConfig : talent.qe.Config.field.compareOperatorRenderConfig;
				frontField.compareOperatorRenderConfig.clazz = frontField.compareOperatorRenderConfig.clazz ? frontField.compareOperatorRenderConfig.clazz : talent.qe.Config.field.compareOperatorRenderConfig.clazz;
				
				frontField.inputRenderConfig = frontField.inputRenderConfig ? frontField.inputRenderConfig : talent.qe.Config.field.inputRenderConfig;
				frontField.inputRenderConfig.clazz = frontField.inputRenderConfig.clazz ? frontField.inputRenderConfig.clazz : talent.qe.Config.field.inputRenderConfig.clazz;
				
				frontField.conditionSqlCreatorConfig = frontField.conditionSqlCreatorConfig ? frontField.conditionSqlCreatorConfig : talent.qe.Config.field.conditionSqlCreatorConfig;
				frontField.conditionSqlCreatorConfig.clazz = frontField.conditionSqlCreatorConfig.clazz ? frontField.conditionSqlCreatorConfig.clazz : talent.qe.Config.field.conditionSqlCreatorConfig.clazz;
				
				frontField.listDataRenderConfig = frontField.listDataRenderConfig ? frontField.listDataRenderConfig : talent.qe.Config.field.listDataRenderConfig;
				frontField.listDataRenderConfig.clazz = frontField.listDataRenderConfig.clazz ? frontField.listDataRenderConfig.clazz : talent.qe.Config.field.listDataRenderConfig.clazz;
				
				frontField.listHeadRenderConfig = frontField.listHeadRenderConfig ? frontField.listHeadRenderConfig : talent.qe.Config.field.listHeadRenderConfig;
				frontField.listHeadRenderConfig.clazz = frontField.listHeadRenderConfig.clazz ? frontField.listHeadRenderConfig.clazz : talent.qe.Config.field.listHeadRenderConfig.clazz;
				
				
				frontField.sqlValueCreatorConfig = frontField.sqlValueCreatorConfig ? frontField.sqlValueCreatorConfig : talent.qe.Config.field.sqlValueCreatorConfig;
				frontField.sqlValueCreatorConfig.clazz = frontField.sqlValueCreatorConfig.clazz ? frontField.sqlValueCreatorConfig.clazz : talent.qe.Config.field.sqlValueCreatorConfig.clazz;
			} 
		}
		//alert(JSON.stringify(this.config.backFields));
	}
});

talent.qe.ModelInintializer = talent.qe.BaseInintializer.extend({
	initValue : function() {
		model = this.config.model;
		if (model) {
			model.pageRenderConfig = model.pageRenderConfig ? model.pageRenderConfig : talent.qe.Config.model.pageRenderConfig;
			model.pageRenderConfig.clazz = model.pageRenderConfig.clazz ? model.pageRenderConfig.clazz : talent.qe.Config.model.pageRenderConfig.clazz;
			
			model.pageRenderConfig.rowEventHandlerConfig = model.pageRenderConfig.rowEventHandlerConfig ? model.pageRenderConfig.rowEventHandlerConfig : talent.qe.Config.model.rowEventHandlerConfig;
			model.pageRenderConfig.rowEventHandlerConfig.clazz = model.pageRenderConfig.rowEventHandlerConfig.clazz ? model.pageRenderConfig.rowEventHandlerConfig.clazz : talent.qe.Config.model.rowEventHandlerConfig.clazz;
			
			model.pageRenderConfig.queryFormRenderConfig = model.pageRenderConfig.queryFormRenderConfig ? model.pageRenderConfig.queryFormRenderConfig : talent.qe.Config.model.queryFormRenderConfig;
			model.pageRenderConfig.queryFormRenderConfig.clazz = model.pageRenderConfig.queryFormRenderConfig.clazz ? model.pageRenderConfig.queryFormRenderConfig.clazz : talent.qe.Config.model.queryFormRenderConfig.clazz;

			model.pageRenderConfig.queryFormRenderConfig.config.layout = model.pageRenderConfig.queryFormRenderConfig.config.layout ? model.pageRenderConfig.queryFormRenderConfig.config.layout : talent.qe.Config.model.queryFormRenderConfig.layout;
			model.pageRenderConfig.queryFormRenderConfig.config.layout.clazz = model.pageRenderConfig.queryFormRenderConfig.config.layout.clazz ? model.pageRenderConfig.queryFormRenderConfig.config.layout.clazz : talent.qe.Config.model.queryFormRenderConfig.layout.clazz;
			
			model.pageRenderConfig.listResultRenderConfig = model.pageRenderConfig.listResultRenderConfig ? model.pageRenderConfig.listResultRenderConfig : talent.qe.Config.model.listResultRenderConfig;
			model.pageRenderConfig.listResultRenderConfig.clazz = model.pageRenderConfig.listResultRenderConfig.clazz ? model.pageRenderConfig.listResultRenderConfig.clazz : talent.qe.Config.model.listResultRenderConfig.clazz;
		}
	}
});

/**
 * 点击查询按钮时，执行的函数
 */
function qe_query() {
	//alert(JSON.stringify(talent_qe_pageRender));
	
	qe_displayCondition(document.getElementById("condition4Show_container"));
	talent_qe_pageRender.renderResultList(-1, -1);
}

/**
 * 点击"配置条件项"按钮时，执行的函数
 */
function qe_config_condition() {
	
}

/**
 * 初始化初始值
 */
function talent_qe_init_value(queryEngine) {
	talent_qe_model = qe_model;
	talent_qe_conditionFields = qe_model.pageRenderConfig.queryFormRenderConfig.config.fieldNames;//TODO:需要从cookie中取值
	talent_qe_listFields = qe_model.pageRenderConfig.listResultRenderConfig.config.fieldNames;
	talent_qe_url = qe_contextpath + "/qe/query.talent";
//	talent_qe_url = qe_contextpath + "/qe/query?qeModelId=" + document.getElementById("qeModelId").value;
	
	/**
	 * 对field(字段)进行初始化
	 */
	fieldInintializer = new talent.qe.FieldInintializer();
	fieldInintializer.setConfig({frontFields:talent_qe_model.schema.fields, backFields:queryEngine.fields}).initValue();
	
	/**
	 * 对model进行初始化
	 */
	modelInintializer = new talent.qe.ModelInintializer();
	modelInintializer.setConfig({model:talent_qe_model}).initValue();
	
}

function talent_qe_init() {
	qe_contextpath = document.getElementById("contextpath").value;

	//queryEngine = queryEngineJsonObj;
	talent_qe_init_value(queryEngineJsonObj);

	
	
	conf = {container:document.getElementById("page_container"), model:talent_qe_model};
	talent.Util.copy(conf, talent_qe_model.pageRenderConfig);
	talent_qe_pageRender = talent.qe.instanceRender(talent_qe_model.pageRenderConfig.clazz, conf);
	talent_qe_pageRender.render();
	
	
	qe_displayCondition();
}
/**
 * config:{container:c1,field:f}
 */
talent.qe.LabelRender = talent.qe.BaseRender.extend({
	/**
	 * config:{container:c1,fieldName:f}
	 */
	render : function() {
		this.config.container.innerHTML = '请为<span class="mylabel">'+talent_qe_fieldMap[this.config.fieldName].label+'</span>设置查询条件';
	}
});
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


/**
 * 整条SQL的创建者
 */
talent.qe.field.ConditionSqlCreator = talent.qe.BaseCreator.extend({
	//this.config : {fieldName:f};
	/**
	 * 
	 * return: {"sql":" and (idid>=? and idid<=?  or idid>=? and idid<=? ) ","sqlParameters":[{"name":"idid","value":"1"},{"name":"idid","value":"4"},{"name":"idid","value":"6"},{"name":"idid","value":"9"}]}
	 */
	create:function () {
		//
		var f = talent_qe_fieldMap[this.config.fieldName];
		var _ret = {};
		_ret.sql = null;
		var sqlArray = [];
		_ret.sqlParameters = [];
		
		var hasCondition = false; //是否有条件
		//alert("conditionItems:"+JSON.stringify(f["conditionItems"]));
		for (var i = 0; i < f["conditionItems"].length; i++) {
			/**
			 * condition: {"sql":"idid>=? and idid<=? ","sqlParameters":[{"name":"idid","value":"1"},{"name":"idid","value":"8"}]}
			 */
			var condition = this.createItemCondition(f["conditionItems"][i], hasCondition);
			//alert(JSON.stringify(condition));
			if (condition != null) {
				hasCondition = true;
				sqlArray.push(condition.sql);
				if (condition.sqlParameters != null && condition.sqlParameters.length > 0) {
					_ret.sqlParameters = _ret.sqlParameters.concat(condition.sqlParameters);
				}
			}
		}
		if (hasCondition) {  //如果有条件则需要在
			sqlArray.push(") ");
			_ret.sql = " and (" + sqlArray.join("");
			//_ret.sql += ") ";
		}
		//alert(JSON.stringify(_ret));
		return _ret;
		
	},
	//
	/**
	 * 
	 * @param {} conditionItem:{compareOperator:seleceElement, logicOperator:seleceElement, inputElements:[]}
	 * @param _hasCondition 该字段是否已经有条件了 true:有
	 * @return {"sql":"idid>=? and idid<=? ","sqlParameters":[{"name":"idid","value":"1"},{"name":"idid","value":"8"}]}
	 */
	createItemCondition:function (conditionItem, hasCondition) {
		if (conditionItem == null) {
			return null;
		}
		
		var f = talent_qe_fieldMap[this.config.fieldName];
		if (!f.sqlValueCreator){
			f.sqlValueCreator = talent.Util.instanceByClass(f.sqlValueCreatorConfig.clazz);
			var conf = {"fieldName":this.config.fieldName};
			f.sqlValueCreator.setConfig(conf);
		}
		
		var sqlArray = [];
		var sqlParameters = [];
		
		//逻辑操作符 or 或者 and
		if (conditionItem.logicOperator && hasCondition) {
			sqlArray.push(" ");
			sqlArray.push(conditionItem.logicOperator.value);
			sqlArray.push(" ");
		}
		
		//比较操作符和比较值
		if (conditionItem.compareOperator.value == "between" || conditionItem.compareOperator.value == "not between") { //between和not between特殊处理
			var trimValue0 = talent.Util.trim(conditionItem.inputElements[0].value);
			var trimValue1 = talent.Util.trim(conditionItem.inputElements[1].value);
			if (trimValue0 == "" && trimValue1 == "") {
				return null;
			}
			//sqlArray.push(this.getLogicOperatorValue());
			
			var firstCompareOperatorValue = "<";  //not between fieldName < v1
			var secondCompareOperatorValue = ">"; //not between fieldName > v2
			var logicOperatorValue = " or ";
			if (conditionItem.compareOperator.value == "between"){
				firstCompareOperatorValue = ">=";
				secondCompareOperatorValue = "<=";
				logicOperatorValue = " and ";
			}
			if (trimValue0 != "") {
				sqlArray.push(f.name);
				sqlArray.push(firstCompareOperatorValue);
				
				//condition:   {"value":"?","sqlParameters":[{"name":"idid","value":"545454"}]}
			    //condition:   {"value":"{d '2012-02-25'}","sqlParameters":null}
				var condition = f.sqlValueCreator.create(trimValue0, conditionItem.compareOperator.value);
				sqlArray.push(condition.value);
				if (condition.sqlParameters != null && condition.sqlParameters != undefined) {
					sqlParameters = sqlParameters.concat(condition.sqlParameters);
				}

			} else {
				logicOperatorValue = "";
			}
			
			if (trimValue1 != "") {
				sqlArray.push(logicOperatorValue);
				sqlArray.push(f.name);
				sqlArray.push(secondCompareOperatorValue);
				var condition = f.sqlValueCreator.create(trimValue1, conditionItem.compareOperator.value);
				sqlArray.push(condition.value);
				//ret = {value:"?", parameters:[{name: this.config.fieldName, value:.value}]}
				if (condition.sqlParameters != null && condition.sqlParameters != undefined) {
					sqlParameters = sqlParameters.concat(condition.sqlParameters);
				}
			}
			sqlArray.push(" ");
		} else {
			var trimValue0 = talent.Util.trim(conditionItem.inputElements[0].value);
			if (trimValue0 == "") {
				return null;
			}
			
			var compareOperatorValue = conditionItem.compareOperator.value;
			if (compareOperatorValue == "right like" || compareOperatorValue == "left like") {
				compareOperatorValue = "like";
			}
			
			//sqlArray.push(this.getLogicOperatorValue() + " ");
			sqlArray.push(f.name);
			
			sqlArray.push(" " + compareOperatorValue + " ");
			
			
			//condition:   {"value":"?","sqlParameters":[{"name":"idid","value":"545454"}]}
			//condition:   {"value":"{d '2012-02-25'}","sqlParameters":null}
			var condition = f.sqlValueCreator.create(trimValue0, conditionItem.compareOperator.value);
			
			sqlArray.push(condition.value);
			//sqlParameters = {value:"?", parameters:[{name: this.config.fieldName, value:value}]}
			if (condition.sqlParameters != null && condition.sqlParameters != undefined) {
				sqlParameters = sqlParameters.concat(condition.sqlParameters);
			}
		}
		
		var ret = {"sql": sqlArray.join("")};
		if (sqlParameters && sqlParameters.length > 0){
			ret["sqlParameters"] = sqlParameters;
		}
		return ret;
	},
	
	getLogicOperatorValue:function() {
		return " and ";
	}
});

/**
 * 页面渲染者
 */
talent.qe.PageRender = talent.qe.BaseRender.extend({
	queryFormContainer:null,
	queryFormRender:null,
	
	resultListContainer:null,
	resultListPanelContainer:null,
	
	listResultRender:null,
	
	/**
	 * config:{container:container1,model:m1}
	 */
	render : function() {
//		queryFormPanelContainer = document.createElement("div");
//		this.config.container.appendChild(queryFormPanelContainer);
//		
//		queryFormPanelContainer.id = "qe_queryform_panel";
		
//		$(queryFormPanelContainer).panel({
//		  title: this.config.model.label,
//		  tools: [],
//		  collapsible:true,
//		  minimizable:false,
//		  maximizable:true,
//		  fit:true
//		});

		this.renderQueryForm(this.config.container);
	},
	
	renderQueryForm:function(container) {
		// 处理queryForm
		this.queryFormPanelContainer = document.createElement("div");
		container.appendChild(this.queryFormPanelContainer);
		this.queryFormPanelContainer.className = "queryFormDiv";
		
//		this.queryFormContainer = document.createElement("div");
//		this.queryFormPanelContainer.appendChild(this.queryFormContainer);
//		this.queryFormContainer.id = "qe_queryform";

		queryFormRender_config = this.config.model.pageRenderConfig.queryFormRenderConfig.config;
		queryFormRender_config.container = this.queryFormPanelContainer;
		
		this.queryFormRender = talent.qe.instanceRender(this.config.model.pageRenderConfig.queryFormRenderConfig.clazz, queryFormRender_config);
		this.queryFormRender.render();
	},
	/**
	 * 渲染查询结果
	 * @param {} pageIndex 第一次传-1
	 * @param {} pageSize  第一次传-1
	 */
	renderResultList: function(pageIndex, pageSize) {
		if (!this.resultListContainer) {
			this.resultListContainer = document.createElement("div");
			this.resultListPanelContainer = document.createElement("div");
			this.queryFormPanelContainer.appendChild(this.resultListPanelContainer);
			this.resultListPanelContainer.className = "listResultDiv";
			this.resultListPanelContainer.appendChild(this.resultListContainer);
			this.resultListContainer.id = "resultListContainer_id";
		}
		
		//{sql:"fieldName1=? and fieldName2 = {t 09:09:09}", sqlParameters:[{name: this.config.fieldName, value:.value}]}
		//talent_qe_conditionFields talent_qe_fieldMap
		var conditonData;
		var frontConditionSqlArray = [];
		var frontParameters = [];
//		var index = 0;
		for (var i= 0; i < talent_qe_conditionFields.length; i++) {
			var f = talent_qe_fieldMap[talent_qe_conditionFields[i]];
			if (!f.conditionSqlCreator){
				_conditionSqlCreator = talent.Util.instanceByClass(f.conditionSqlCreatorConfig.clazz);
				var conf = {"fieldName" : f.name, "field" : f};
				_conditionSqlCreator.setConfig(conf);
				f.conditionSqlCreator = _conditionSqlCreator;
			}
			var conditon = f.conditionSqlCreator.create();

			if (conditon) {
				frontConditionSqlArray.push(conditon.sql);
				if (conditon.sqlParameters) {
					frontParameters = frontParameters.concat(conditon.sqlParameters);
				}
			}
		}
		
		var conditionData = [
			{name:"frontConditionSql", value:frontConditionSqlArray.join("")},
			{name:"jsonStringOfSqlParameters", value: JSON.stringify(frontParameters)}
		];
		//config:{fields:[],gridData:[],container:{},url:{},isShowCustomPageSize:true,maxPageSize:800}
		config = {
			"fieldNames" : this.getListFields(),
			//"requiredFields" : talent_qe_model.pageRenderConfig.listResultRenderConfig.config.requiredFields,
			"container" : this.resultListContainer,
			"url" : talent_qe_url,
			"conditionData" : conditionData
			
			//TODO 需要可配置
		};
		talent.Util.copy(config, talent_qe_model.pageRenderConfig.listResultRenderConfig.config);
		
		this.listResultRender = talent.Util.instanceByClass(talent_qe_model.pageRenderConfig.listResultRenderConfig.clazz);
		this.listResultRender.setConfig(config);
		this.listResultRender.render();
	},
	
	
	 getListFields: function() {
	 	//TODO 需要考虑cookie
	 	return talent_qe_listFields;
	 }
});
talent.qe.ListResultRender = talent.qe.BaseRender.extend({
	//config:{fieldNames:[],gridData:[],container:{},url:{},isShowCustomPageSize:true,maxPageSize:800}
	/**
	 * 
	 */
	setConfig:function(_config) {
		this._super(_config);
	},
	
	render:function () {
		var gridConfig = this.config;
		gridConfig.fields = [];
		for (var i = 0; i < this.config.fieldNames.length; i++) {
			f = talent_qe_fieldMap[this.config.fieldNames[i]];
			gridConfig.fields[i]  = f;
		}
		
		gridConfig.postData = this.config.conditionData;
		//[{"name":"frontConditionSql","value":" and date1>={d 2010-07-07} "},{"name":"frontParameters","value":"[[]]"}]

		gridConfig.postData.push({name:"qeModelId", value: document.getElementById("qeModelId").value});
		
		var grid = new talent.ui.Grid(gridConfig);
		//alert(JSON.stringify(this.config.conditionData));
		grid.reload(0, 10);
	}
});

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
talent.qe.field.ListDataRender = talent.qe.BaseRender.extend({
	//this.config:{table:{}, rowElement:{}, cellElement:{}, rowData:{}, field:{}, gridConfig:{}, gridData:{}, rowIndex:{}, colIndex:{}}
	render:function () {
		var val = this.config.rowData[this.config.field.name];
		if (val){
			this.config.cellElement.innerHTML = this.config.rowData[this.config.field.name];
		}
		
	}
});
talent.qe.field.ListHeadRender = talent.qe.BaseRender.extend({
	//this.config:{table:{}, rowElement:{}, cellElement:{}, field:{}, gridConfig:{}, gridData:{}}
	render:function () {
		this.config.cellElement.innerHTML = this.config.field.label;
	}
});

/**
 * 
 * @param {}
 *            field
 * @param {}
 *            container
 */
talent.qe.field.CompareOperatorRender = talent.qe.BaseRender.extend({
	/**
	 * config:{container:c1,fieldName:f}
	 */
	render:function () {
		var selectElement = document.createElement("select");
		selectElement.className = "compareOperator";
		this.config.container.appendChild(selectElement);
		//talent_qe_fieldMap[this.config.fieldName].compareOperator = selectElement;
		
		//talent_qe_fieldMap[this.config.fieldName]
		this.config.conditionItem.compareOperator = selectElement;
		
		var optionsdata = this.config.items;
		if (!optionsdata) {
			optionsdata = talent.qe.Config.compareOperationMap[talent.qe.sqlTypeMap[talent_qe_fieldMap[this.config.fieldName].sqlType]["myType"]];
		}
		//selectElement, isClearBeforeUpdate, items,
		//valueFieldName, labelFieldName, isFilterNullValue, optionClassName,
		//optionProperties, optionPropertiesFromSrc
		talent.Util.updateOption(selectElement, true, optionsdata, "value", "label", true, "", "", ["title"]);
	}
});


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
talent.qe.RowEventHandler = talent.qe.BaseRender.extend({
	
	/**
	 * config:{container:c1,field:f}
	 */
	render : function() {
		
	}
});

/**
 * 
 * @param {}
 *            field
 * @param {}
 *            container
 */
talent.qe.field.LogicOperatorRender = talent.qe.BaseRender.extend({
			/**
			 * this.config: {"container": container1, "fieldName":_field.fieldName, "field":_field, "conditionItem":conditionItem}
			 * param: currConfig:{'isRenderLogic':true}
			 */
			render : function(currConfig) {
				if (currConfig.isRenderLogic) {
					var selectElement = document.createElement("select");
					selectElement.className = "logicOperator";
					this.config.container.appendChild(selectElement);
					this.config.conditionItem.logicOperator = selectElement;

					var optionsdata = this.config.items;
					if (!optionsdata) {
						optionsdata = talent.qe.logicOperationMap;
					}
					talent.Util.updateOption(selectElement, true, optionsdata,
							"value", "label", true, "", "", ["title"]);
				} else {
					var divElement = document.createElement("span");
					this.config.container.appendChild(divElement);
					divElement.innerHTML = "";//this.config.field.label;
					divElement.className = "logicOperator_div";
				}

			}
		});
// conditionItem:{compareOperator:seleceElement, logicOperator:seleceElement,
// inputElements:[]}

// for (var i= 0; i < talent_qe_conditionFields.length; i++) {
// var f = talent_qe_fieldMap[this.config.fieldName];

// for (var i = 0; i < f["conditionItems"].length; i++) {
function qe_displayCondition() {
	var _container = document.getElementById("qe_condition4ShowDiv");
	_container.className = "span6";
	_container.innerHTML = "";

	var divtitle = document.createElement("div"); // 显示title:"已设置了如下条件"
	_container.appendChild(divtitle);
	divtitle.className = "condition4ShowDivTitle";
	divtitle.innerHTML = "尚未设置任何查询条件";
	

	for (var i = 0; i < talent_qe_conditionFields.length; i++) {
		var f = talent_qe_fieldMap[talent_qe_conditionFields[i]];
		var needRender = false;
		var conditionItems = f["conditionItems"];
		// var hasCondition = false; //是否有条件
		var rendItems = [];
		// conditionItem:{compareOperator:seleceElement,
		// logicOperator:seleceElement, inputElements:[]}
		for (var j = 0; j < conditionItems.length; j++) {
			var conditionItem = conditionItems[j];
			var trimValue0 = talent.Util
					.trim(conditionItem.inputElements[0].value);
			// between和not between特殊处理
			if (conditionItem.compareOperator.value == "between"
					|| conditionItem.compareOperator.value == "not between") {
				var trimValue1 = talent.Util
						.trim(conditionItem.inputElements[1].value);
				if (trimValue1 || trimValue0) {
					needRender = true;
					var item = {};
					rendItems.push(item);
					// 逻辑操作符 or 或者 and
					item.logicOperator = conditionItem.logicOperator;
					if (trimValue0 && trimValue1) {
						if (conditionItem.compareOperator.value == "between") {
							item.label = " >= " + trimValue0 + " && <= "
									+ trimValue1;
						} else { // not between
							item.label = " < " + trimValue0 + " && > "
									+ trimValue1;
						}
					} else {
						if (trimValue0) {
							if (conditionItem.compareOperator.value == "between") {
								item.label = " >= " + trimValue0;
							} else { // not between
								item.label = " < " + trimValue0;
							}
						} else {
							if (conditionItem.compareOperator.value == "between") {
								item.label = " <= " + trimValue1;
							} else { // not between
								item.label = " > " + trimValue1;
							}
						}
					}
				}
			} else {
				if (trimValue0) {
					needRender = true;
					var item = {};
					rendItems.push(item);
					// 逻辑操作符 or 或者 and
					item.logicOperator = conditionItem.logicOperator;
					item.label = conditionItem.compareOperator.value + " "
							+ trimValue0;
				}
			}
		}
		if (needRender) {
			var div4F = document.createElement("div"); // 整个字段的div
			_container.appendChild(div4F);
			div4F.className = "span8 condition4Show_field";

			divtitle.innerHTML = "已设置如下条件";

//			var div4Label = document.createElement("div"); // 用于显示字段的label的div
//			div4F.appendChild(div4Label);
//			div4Label.className = "span1";
//			div4Label.innerHTML = f.label;

			var div4Condition = document.createElement("div");
			div4F.appendChild(div4Condition);
			div4Condition.className = "span7";

			for (var x = 0; x < rendItems.length; x++) {
				var item = rendItems[x];
				var div1 = document.createElement("div");
				div4Condition.appendChild(div1);

				if (x % 2 == 1) {
					div1.className = "row conditionOld";
				} else {
					div1.className = "row";
				}

				var div4Logic = document.createElement("div");
				div1.appendChild(div4Logic);
				div4Logic.className = "span1";
				if (x > 0) { // 需要显示逻辑操作符 or 或者 and
					div4Logic.innerHTML = item.logicOperator.value;
				} else {
					div4Logic.innerHTML = f.label;
					div4Logic.className = "span1 condition4ShowFieldLabel";
				}

				var div4ConditionLabel = document.createElement("div");
				div1.appendChild(div4ConditionLabel);
				div4ConditionLabel.className = "span6";
				div4ConditionLabel.innerHTML = item.label;
			}
		}
	}
}
