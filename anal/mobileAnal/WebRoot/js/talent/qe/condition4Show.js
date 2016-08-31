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