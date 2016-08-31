
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