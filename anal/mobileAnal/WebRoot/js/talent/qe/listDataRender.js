talent.qe.field.ListDataRender = talent.qe.BaseRender.extend({
	//this.config:{table:{}, rowElement:{}, cellElement:{}, rowData:{}, field:{}, gridConfig:{}, gridData:{}, rowIndex:{}, colIndex:{}}
	render:function () {
		var val = this.config.rowData[this.config.field.name];
		if (val){
			this.config.cellElement.innerHTML = this.config.rowData[this.config.field.name];
		}
		
	}
});