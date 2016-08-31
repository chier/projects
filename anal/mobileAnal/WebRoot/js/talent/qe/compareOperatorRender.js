
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