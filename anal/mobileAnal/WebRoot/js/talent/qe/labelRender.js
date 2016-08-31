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