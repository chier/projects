package com.cmcc.anal.common.persistence.query;

/**
 * 定义 where clause 中除了表间关联约束外的其他约束条件.
 * 
 * @author chenke
 * @since 2006-6-16
 */
public class FilterDTO extends BaseDTO {

	/**
	 * 条件的逻辑关系: and 还是 or
	 * 
	 * @delphi.label 并且(AND)
	 */
	private boolean andRelation = true;

	/**
	 * 引用 QueryColumnDTO.columnAlias 可能是一个字段, 也可能是一个计算字段. 如果是计算字段, 服务器端需要根据
	 * columnAlias 查到计算字段的表达式, 然后拼装到 where 条件中.
	 * 
	 * @delphi.label 字段别名
	 * @delphi.size 30
	 */
	private String columnAlias;

	/**
	 * 针对数据导入导出业务添加的属性. 查询模块不使用这个属性. 后台在做 SQL 拼装的时候, 如果 tableCode 和 columnCode
	 * 都不为 null 或者 "", 则拼 SQL 时拼装为 tableCode.columnCode
	 * 
	 * @delphi.label 字段代码
	 * @delphi.size 30
	 */
	private String columnCode;

	/**
	 * 针对数据导入导出业务添加的属性. 查询模块不使用这个属性. 后台在做 SQL 拼装的时候, 如果 tableCode 和 columnCode
	 * 都不为 null 或者 "", 则拼 SQL 时拼装为 tableCode.columnCode
	 * 
	 * @delphi.label 表代码
	 * @delphi.size 30
	 */
	private String tableCode;

	/**
	 * 条件关系符. 目前支持: = > >= < <= <> like not like in not in like后面的表达式支持通配符 % 和 _
	 * 
	 * @delphi.label 条件关系符
	 * @delphi.required true
	 * @delphi.size 20
	 */
	private String operator;

	/**
	 * 条件约束值, 可以是 literal (数值), 或者另一个字段别名 columnAlias. literal: 字符型和数值型
	 * columnAlias: 引用 QueryColumnDTO.columnAlias 如果是字符型, 则必须显式说明, 如: 'a'. Note:
	 * 1.如果条件关系符为 like 或 not like, 则服务器端判断, 如果没有 ' 符号, 则加上 ' 符号, 如: '%a_'
	 * 2.如果条件关系符为 in, 则服务器端判断, 如果没有用 ( 和 ) 包括, 则补上 ( 和 )
	 * 
	 * @delphi.label 条件约束值
	 * @delphi.required true
	 * @delphi.size 100
	 */
	private String value;

	/**
	 * value 属性的类型. 1, value 为字段别名; 2, value 为 literal(数值)
	 * 
	 * @delphi.label 条件约束值类型
	 * @delphi.required true
	 * @delphi.size 1
	 * @delphi.validateExp exp="[12]{1}" msg="条件约束值类型只能是1或者2"
	 */
	private String valueType;

	/**
	 * 该条件的优先级.
	 * 
	 * @delphi.label 优先级
	 */
	private int priority;

	/**
	 * 如果括号为 1, 则 SQL 条件前面添加一个 "(", 2 则添加两个; 如果括号为 -1, 则 SQL 条件后面添加一个 ")", 2
	 * 则添加两个;
	 * 
	 * @delphi.label 括号
	 */
	private int parenthesis;

	public FilterDTO() {
	}

	public FilterDTO(boolean andRelation, String columnAlias, String operator,
			int priority, String value, String valueType) {
		this.andRelation = andRelation;
		this.columnAlias = columnAlias;
		this.operator = operator;
		this.priority = priority;
		this.value = value;
		this.valueType = valueType;
	}

	public FilterDTO(boolean andRelation, String columnAlias,
			String columnCode, String tableCode, String operator, int priority,
			String value, String valueType) {
		this.andRelation = andRelation;
		this.columnAlias = columnAlias;
		this.columnCode = columnCode;
		this.tableCode = tableCode;
		this.operator = operator;
		this.priority = priority;
		this.value = value;
		this.valueType = valueType;
	}

	public FilterDTO(boolean andRelation, String columnAlias,
			String columnCode, String tableCode, String operator, int priority,
			String value) {
		this.andRelation = andRelation;
		this.columnAlias = columnAlias;
		this.columnCode = columnCode;
		this.tableCode = tableCode;
		this.operator = operator;
		this.priority = priority;
		this.value = value;
		this.valueType = QueryBuilder.FILTER_VALUE_LITERAL_TYPE;
	}

	public String getColumnAlias() {
		return columnAlias;
	}

	public void setColumnAlias(String columnAlias) {
		this.columnAlias = columnAlias;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isAndRelation() {
		return andRelation;
	}

	public void setAndRelation(boolean andRelation) {
		this.andRelation = andRelation;
	}

	public String getColumnCode() {
		return columnCode;
	}

	public void setColumnCode(String columnCode) {
		this.columnCode = columnCode;
	}

	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public int getParenthesis() {
		return parenthesis;
	}

	public void setParenthesis(int parenthesis) {
		this.parenthesis = parenthesis;
	}

}
