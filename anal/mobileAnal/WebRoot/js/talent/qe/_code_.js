/*queryengine 数据*/
var queryengine = {
    "fields": [
        {
		    "idid": {
		        "name": "idid",
		        "label": "标识",
		        "labelRender": "talent.qe.LabelRender",
		        "compareOperatorRender": "talent.qe.field.CompareOperatorRender",
		        "inputRender": "talent.qe.field.InputRender",
		        "conditionSqlCreator": "talent.qe.field.ConditionSqlCreator",
		        "listRender": "talent.qe.field.ListRender",
		        "defalutValueOfDb": "",
		        "fieldAliasName": "idid",
		        "fieldName": "idid",
		        "id": 0,
		        "javaType": "int",
		        "methodNameOfSetParameter": "setInt",
		        "nullable": true,
		        "precision": 8,
		        "remarks": "",
		        "scale": 0,
		        "sqlType": 3,
		        "sqlTypeName": "DECIMAL",
		        "tableName": "getFieldMetaDataBySqljhus"
		    },
		    "namename": {
		        "name": "namename",
		        "label": "姓名",
		        "labelRender": "talent.qe.LabelRender",
		        "compareOperatorRender": "talent.qe.field.CompareOperatorRender",
		        "inputRender": "talent.qe.field.InputRender",
		        "conditionSqlCreator": "talent.qe.field.ConditionSqlCreator",
		        "listRender": "talent.qe.field.ListRender",
		        "defalutValueOfDb": "",
		        "fieldAliasName": "namename",
		        "fieldName": "namename",
		        "id": 0,
		        "javaType": "java.lang.String",
		        "methodNameOfSetParameter": "setString",
		        "nullable": true,
		        "precision": 50,
		        "remarks": "",
		        "scale": 0,
		        "sqlType": 12,
		        "sqlTypeName": "VARCHAR",
		        "tableName": "getFieldMetaDataBySqljhus",
		        
		        "inputElements":[{}],
		        "compareOperator": {} //这个是
		        
		    }
		}
    ],
    "model": {
        "resources": [
            {
                "path": "qe/qe_demo.js"
            }
        ],
        "schema": {
            "sqlGenerator": {
                "sql": "select tt.id as idid, tt.name as namename from talent_test_user tt where 1=1 and tt.name != {t '09:09:09'}\n\t\t \n\t\t \n\t\tand tt.name = ?\n\t\t \n    order by tt.id desc",
                "sqlId": "talent.QueryEngine.testUser"
            }
        }
    },
    "qeModelId": "demoModel"
};


/**如何实用继承**/
talent.qe.testExtend_1 = talent.Class.extend({
	init : function() {
		alert(1);
	}
});
talent.qe.testExtend_2 = talent.qe.testExtend_1.extend({
	init : function() {
		this._super();
		alert(2);
	}
});

var sss2 = new talent.qe.testExtend_2();
//var sss1 = new talent.qe.testExtend_1();









var model = {
    "name": "",
    "label": "人员查询",
    "pageRenderConfig": {
        "clazz": "talent.qe.PageRender",
        "rowEventHandlerConfig": {
            "clazz": "talent.qe.RowEventHandler",
            "config": {}
        },
        "queryFormRenderConfig": {
            "clazz": "talent.qe.QueryFormRender",
            "config": {
                "fields": [
                    "idid",
                    "namename",
                    "float1",
                    "date1",
                    "time1",
                    "timestamp1"
                ],
                "requiredFields": [
                    "idid"
                ],
                "label": "人员查询",
                "layout": {
                    "clazz": "talent.ui.FlexGridLayout",
                    "config": {
                        "columns": 4,
                        "container": {}
                    }
                },
                "container": {}
            }
        },
        "listResultRenderConfig": {
            "clazz": "talent.qe.ListResultRender",
            "config": {
                "fields": [
                    "idid",
                    "namename",
                    "float1",
                    "date1",
                    "time1",
                    "timestamp1"
                ],
                "requiredFields": [
                    "idid"
                ]
            }
        }
    },
    "schema": {
        "fields": [
            {
                "name": "idid",
                "label": "number1",
                "labelRenderConfig": {
                    "clazz": "talent.qe.LabelRender",
                    "config": {}
                },
                "compareOperatorRenderConfig": {
                    "config": {
                        "items": [
                            {
                                "value": "between",
                                "label": "between"
                            },
                            {
                                "value": "like",
                                "label": "like"
                            },
                            {
                                "value": "like_b",
                                "label": "like_b"
                            },
                            {
                                "value": "like_a",
                                "label": "like_a"
                            }
                        ]
                    },
                    "clazz": "talent.qe.field.CompareOperatorRender"
                },
                "inputRenderConfig": {
                    "clazz": "talent.qe.field.InputRender",
                    "config": {}
                },
                "conditionSqlCreatorConfig": {
                    "clazz": "talent.qe.field.ConditionSqlCreator",
                    "config": {}
                },
                "sqlValueCreatorConfig": {
                    "clazz": "talent.qe.field.SqlValueCreator",
                    "config": {}
                },
                "listHeadRenderConfig": {
                    "clazz": "talent.qe.field.ListHeadRender",
                    "config": {}
                },
                "listRenderConfig": {
                    "clazz": "talent.qe.field.ListRender",
                    "config": {}
                },
                "defalutValueOfDb": "",
                "fieldAliasName": "idid",
                "fieldName": "idid",
                "id": 0,
                "javaType": "int",
                "methodNameOfSetParameter": "setInt",
                "nullable": true,
                "precision": 8,
                "remarks": "",
                "scale": 0,
                "sqlType": 3,
                "sqlTypeName": "DECIMAL",
                "tableName": "getFieldMetaDataBySqljhus",
                "compareOperator": {},
                "inputElements": [
                    {
                        "isBetween": "true",
                        "hasRendered": true
                    },
                    {
                        "isBetween": "true",
                        "hasRendered": true
                    }
                ]
            },
            {
                "name": "namename",
                "label": "varchar1",
                "defalutValueOfDb": "",
                "fieldAliasName": "namename",
                "fieldName": "namename",
                "id": 0,
                "javaType": "java.lang.String",
                "methodNameOfSetParameter": "setString",
                "nullable": true,
                "precision": 50,
                "remarks": "",
                "scale": 0,
                "sqlType": 12,
                "sqlTypeName": "VARCHAR",
                "tableName": "getFieldMetaDataBySqljhus",
                "labelRenderConfig": {
                    "clazz": "talent.qe.LabelRender",
                    "config": {}
                },
                "compareOperatorRenderConfig": {
                    "clazz": "talent.qe.field.CompareOperatorRender",
                    "config": {}
                },
                "inputRenderConfig": {
                    "clazz": "talent.qe.field.InputRender",
                    "config": {}
                },
                "conditionSqlCreatorConfig": {
                    "clazz": "talent.qe.field.ConditionSqlCreator",
                    "config": {}
                },
                "listRenderConfig": {
                    "clazz": "talent.qe.field.ListRender",
                    "config": {}
                },
                "listHeadRenderConfig": {
                    "clazz": "talent.qe.field.ListHeadRender",
                    "config": {}
                },
                "sqlValueCreatorConfig": {
                    "clazz": "talent.qe.field.SqlValueCreator",
                    "config": {}
                },
                "compareOperator": {},
                "inputElements": [
                    {
                        "hasRendered": true
                    }
                ]
            },
            {
                "name": "float1",
                "label": "float1",
                "defalutValueOfDb": "",
                "fieldAliasName": "float1",
                "fieldName": "float1",
                "id": 0,
                "javaType": "float",
                "methodNameOfSetParameter": "setFloat",
                "nullable": true,
                "precision": 4,
                "remarks": "",
                "scale": 2,
                "sqlType": 7,
                "sqlTypeName": "FLOAT",
                "tableName": "getFieldMetaDataBySqljhus",
                "labelRenderConfig": {
                    "clazz": "talent.qe.LabelRender",
                    "config": {}
                },
                "compareOperatorRenderConfig": {
                    "clazz": "talent.qe.field.CompareOperatorRender",
                    "config": {}
                },
                "inputRenderConfig": {
                    "clazz": "talent.qe.field.InputRender",
                    "config": {}
                },
                "conditionSqlCreatorConfig": {
                    "clazz": "talent.qe.field.ConditionSqlCreator",
                    "config": {}
                },
                "listRenderConfig": {
                    "clazz": "talent.qe.field.ListRender",
                    "config": {}
                },
                "listHeadRenderConfig": {
                    "clazz": "talent.qe.field.ListHeadRender",
                    "config": {}
                },
                "sqlValueCreatorConfig": {
                    "clazz": "talent.qe.field.SqlValueCreator",
                    "config": {}
                },
                "compareOperator": {},
                "inputElements": [
                    {
                        "hasRendered": true
                    }
                ]
            },
            {
                "name": "time1",
                "label": "time1",
                "defalutValueOfDb": "",
                "fieldAliasName": "time1",
                "fieldName": "time1",
                "id": 0,
                "javaType": "java.sql.Time",
                "methodNameOfSetParameter": "setTime",
                "nullable": true,
                "precision": 8,
                "remarks": "",
                "scale": 0,
                "sqlType": 92,
                "sqlTypeName": "TIME",
                "tableName": "getFieldMetaDataBySqljhus",
                "labelRenderConfig": {
                    "clazz": "talent.qe.LabelRender",
                    "config": {}
                },
                "compareOperatorRenderConfig": {
                    "clazz": "talent.qe.field.CompareOperatorRender",
                    "config": {}
                },
                "inputRenderConfig": {
                    "clazz": "talent.qe.field.InputRender",
                    "config": {}
                },
                "conditionSqlCreatorConfig": {
                    "clazz": "talent.qe.field.ConditionSqlCreator",
                    "config": {}
                },
                "listRenderConfig": {
                    "clazz": "talent.qe.field.ListRender",
                    "config": {}
                },
                "listHeadRenderConfig": {
                    "clazz": "talent.qe.field.ListHeadRender",
                    "config": {}
                },
                "sqlValueCreatorConfig": {
                    "clazz": "talent.qe.field.SqlValueCreator",
                    "config": {}
                },
                "compareOperator": {},
                "inputElements": [
                    {
                        "hasRendered": true
                    }
                ]
            },
            {
                "name": "timestamp1",
                "label": "timestamp1",
                "defalutValueOfDb": "",
                "fieldAliasName": "timestamp1",
                "fieldName": "timestamp1",
                "id": 0,
                "javaType": "java.sql.Timestamp",
                "methodNameOfSetParameter": "setTimestamp",
                "nullable": true,
                "precision": 19,
                "remarks": "",
                "scale": 0,
                "sqlType": 93,
                "sqlTypeName": "TIMESTAMP",
                "tableName": "getFieldMetaDataBySqljhus",
                "labelRenderConfig": {
                    "clazz": "talent.qe.LabelRender",
                    "config": {}
                },
                "compareOperatorRenderConfig": {
                    "clazz": "talent.qe.field.CompareOperatorRender",
                    "config": {}
                },
                "inputRenderConfig": {
                    "clazz": "talent.qe.field.InputRender",
                    "config": {}
                },
                "conditionSqlCreatorConfig": {
                    "clazz": "talent.qe.field.ConditionSqlCreator",
                    "config": {}
                },
                "listRenderConfig": {
                    "clazz": "talent.qe.field.ListRender",
                    "config": {}
                },
                "listHeadRenderConfig": {
                    "clazz": "talent.qe.field.ListHeadRender",
                    "config": {}
                },
                "sqlValueCreatorConfig": {
                    "clazz": "talent.qe.field.SqlValueCreator",
                    "config": {}
                },
                "compareOperator": {},
                "inputElements": [
                    {
                        "hasRendered": true
                    }
                ]
            },
            {
                "name": "date1",
                "compareOperatorRenderConfig": {
                    "config": {
                        "items": [
                            {
                                "value": "between",
                                "label": "between"
                            }
                        ]
                    },
                    "clazz": "talent.qe.field.CompareOperatorRender"
                },
                "label": "date1",
                "defalutValueOfDb": "",
                "fieldAliasName": "date1",
                "fieldName": "date1",
                "id": 0,
                "javaType": "java.sql.Date",
                "methodNameOfSetParameter": "setDate",
                "nullable": true,
                "precision": 10,
                "remarks": "",
                "scale": 0,
                "sqlType": 91,
                "sqlTypeName": "DATE",
                "tableName": "getFieldMetaDataBySqljhus",
                "labelRenderConfig": {
                    "clazz": "talent.qe.LabelRender",
                    "config": {}
                },
                "inputRenderConfig": {
                    "clazz": "talent.qe.field.InputRender",
                    "config": {}
                },
                "conditionSqlCreatorConfig": {
                    "clazz": "talent.qe.field.ConditionSqlCreator",
                    "config": {}
                },
                "listRenderConfig": {
                    "clazz": "talent.qe.field.ListRender",
                    "config": {}
                },
                "listHeadRenderConfig": {
                    "clazz": "talent.qe.field.ListHeadRender",
                    "config": {}
                },
                "sqlValueCreatorConfig": {
                    "clazz": "talent.qe.field.SqlValueCreator",
                    "config": {}
                },
                "compareOperator": {},
                "inputElements": [
                    {
                        "isBetween": "true",
                        "hasRendered": true
                    },
                    {
                        "isBetween": "true",
                        "hasRendered": true
                    }
                ]
            }
        ]
    }
}