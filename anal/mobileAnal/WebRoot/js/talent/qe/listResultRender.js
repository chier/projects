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