/**
 * 整条SQL的创建者
 */
talent.qe.field.ConditionSqlCreator = talent.qe.BaseCreator.extend({
	//this.config : {fieldName:f};
	/**
	 * 
	 * return: {"sql":" and (idid>=? and idid<=?  or idid>=? and idid<=? ) ","sqlParameters":[{"name":"idid","value":"1"},{"name":"idid","value":"4"},{"name":"idid","value":"6"},{"name":"idid","value":"9"}]}
	 */
	create:function () {
		//
		var f = talent_qe_fieldMap[this.config.fieldName];
		var _ret = {};
		_ret.sql = null;
		var sqlArray = [];
		_ret.sqlParameters = [];
		
		var hasCondition = false; //是否有条件
		//alert("conditionItems:"+JSON.stringify(f["conditionItems"]));
		for (var i = 0; i < f["conditionItems"].length; i++) {
			/**
			 * condition: {"sql":"idid>=? and idid<=? ","sqlParameters":[{"name":"idid","value":"1"},{"name":"idid","value":"8"}]}
			 */
			var condition = this.createItemCondition(f["conditionItems"][i], hasCondition);
			//alert(JSON.stringify(condition));
			if (condition != null) {
				hasCondition = true;
				sqlArray.push(condition.sql);
				if (condition.sqlParameters != null && condition.sqlParameters.length > 0) {
					_ret.sqlParameters = _ret.sqlParameters.concat(condition.sqlParameters);
				}
			}
		}
		if (hasCondition) {  //如果有条件则需要在
			sqlArray.push(") ");
			_ret.sql = " and (" + sqlArray.join("");
			//_ret.sql += ") ";
		}
		//alert(JSON.stringify(_ret));
		return _ret;
		
	},
	//
	/**
	 * 
	 * @param {} conditionItem:{compareOperator:seleceElement, logicOperator:seleceElement, inputElements:[]}
	 * @param _hasCondition 该字段是否已经有条件了 true:有
	 * @return {"sql":"idid>=? and idid<=? ","sqlParameters":[{"name":"idid","value":"1"},{"name":"idid","value":"8"}]}
	 */
	createItemCondition:function (conditionItem, hasCondition) {
		if (conditionItem == null) {
			return null;
		}
		
		var f = talent_qe_fieldMap[this.config.fieldName];
		if (!f.sqlValueCreator){
			f.sqlValueCreator = talent.Util.instanceByClass(f.sqlValueCreatorConfig.clazz);
			var conf = {"fieldName":this.config.fieldName};
			f.sqlValueCreator.setConfig(conf);
		}
		
		var sqlArray = [];
		var sqlParameters = [];
		
		//逻辑操作符 or 或者 and
		if (conditionItem.logicOperator && hasCondition) {
			sqlArray.push(" ");
			sqlArray.push(conditionItem.logicOperator.value);
			sqlArray.push(" ");
		}
		
		//比较操作符和比较值
		if (conditionItem.compareOperator.value == "between" || conditionItem.compareOperator.value == "not between") { //between和not between特殊处理
			var trimValue0 = talent.Util.trim(conditionItem.inputElements[0].value);
			var trimValue1 = talent.Util.trim(conditionItem.inputElements[1].value);
			if (trimValue0 == "" && trimValue1 == "") {
				return null;
			}
			//sqlArray.push(this.getLogicOperatorValue());
			
			var firstCompareOperatorValue = "<";  //not between fieldName < v1
			var secondCompareOperatorValue = ">"; //not between fieldName > v2
			var logicOperatorValue = " or ";
			if (conditionItem.compareOperator.value == "between"){
				firstCompareOperatorValue = ">=";
				secondCompareOperatorValue = "<=";
				logicOperatorValue = " and ";
			}
			if (trimValue0 != "") {
				sqlArray.push(f.name);
				sqlArray.push(firstCompareOperatorValue);
				
				//condition:   {"value":"?","sqlParameters":[{"name":"idid","value":"545454"}]}
			    //condition:   {"value":"{d '2012-02-25'}","sqlParameters":null}
				var condition = f.sqlValueCreator.create(trimValue0, conditionItem.compareOperator.value);
				sqlArray.push(condition.value);
				if (condition.sqlParameters != null && condition.sqlParameters != undefined) {
					sqlParameters = sqlParameters.concat(condition.sqlParameters);
				}

			} else {
				logicOperatorValue = "";
			}
			
			if (trimValue1 != "") {
				sqlArray.push(logicOperatorValue);
				sqlArray.push(f.name);
				sqlArray.push(secondCompareOperatorValue);
				var condition = f.sqlValueCreator.create(trimValue1, conditionItem.compareOperator.value);
				sqlArray.push(condition.value);
				//ret = {value:"?", parameters:[{name: this.config.fieldName, value:.value}]}
				if (condition.sqlParameters != null && condition.sqlParameters != undefined) {
					sqlParameters = sqlParameters.concat(condition.sqlParameters);
				}
			}
			sqlArray.push(" ");
		} else {
			var trimValue0 = talent.Util.trim(conditionItem.inputElements[0].value);
			if (trimValue0 == "") {
				return null;
			}
			
			var compareOperatorValue = conditionItem.compareOperator.value;
			if (compareOperatorValue == "right like" || compareOperatorValue == "left like") {
				compareOperatorValue = "like";
			}
			
			//sqlArray.push(this.getLogicOperatorValue() + " ");
			sqlArray.push(f.name);
			
			sqlArray.push(" " + compareOperatorValue + " ");
			
			
			//condition:   {"value":"?","sqlParameters":[{"name":"idid","value":"545454"}]}
			//condition:   {"value":"{d '2012-02-25'}","sqlParameters":null}
			var condition = f.sqlValueCreator.create(trimValue0, conditionItem.compareOperator.value);
			
			sqlArray.push(condition.value);
			//sqlParameters = {value:"?", parameters:[{name: this.config.fieldName, value:value}]}
			if (condition.sqlParameters != null && condition.sqlParameters != undefined) {
				sqlParameters = sqlParameters.concat(condition.sqlParameters);
			}
		}
		
		var ret = {"sql": sqlArray.join("")};
		if (sqlParameters && sqlParameters.length > 0){
			ret["sqlParameters"] = sqlParameters;
		}
		return ret;
	},
	
	getLogicOperatorValue:function() {
		return " and ";
	}
});