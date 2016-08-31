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