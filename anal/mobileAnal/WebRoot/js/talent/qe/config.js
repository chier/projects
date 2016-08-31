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