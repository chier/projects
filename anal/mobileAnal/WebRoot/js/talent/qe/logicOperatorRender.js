
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