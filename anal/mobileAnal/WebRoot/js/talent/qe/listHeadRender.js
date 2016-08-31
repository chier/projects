talent.qe.field.ListHeadRender = talent.qe.BaseRender.extend({
	//this.config:{table:{}, rowElement:{}, cellElement:{}, field:{}, gridConfig:{}, gridData:{}}
	render:function () {
		this.config.cellElement.innerHTML = this.config.field.label;
	}
});