package com.cmcc.anal.common.persistence.query;

import org.apache.commons.lang.StringUtils;


/**
 * 查询模版中涉及到的字段, 包括所有select clause, where clause和order by clause中涉及到的字段.
 * 字段别名唯一定位一个字段. 
 * @author chenke
 * @since 2006-6-16
 */
public class QueryColumnDTO extends BaseDTO {

    /**
     * 该字段所属的表代码. 这个属性为冗余信息.
     * 这个属性不是必须有的, 因为计算字段没有表代码.
     * @delphi.label 表代码
     * @delphi.size 30
     */
    private String tableCode;

    /**
     * 该字段所属的表的中文名称. 这个属性为冗余信息.
     * @delphi.label 表名称
     * @delphi.size 100
     */
    private String tableName;

    /**
     * 该字段所属表的别名.
     * @delphi.label 表别名
     * 这个属性不是必须有的, 因为计算字段没有表代码.
     * @delphi.size 30
     */
    private String tableAlias;

    /**
     * 字段代码
     * @delphi.label 字段代码
     * @delphi.required true
     * @delphi.size 30
     */
    private String columnCode;

    /**
     * 字段的中文名称. 缺省为: 该字段的 Comment.
     * 用户可重指定. 如果涉及到 Comment 相同的情况, 则采用 talbeName + "_" + 该字段的 Comment 
     * @delphi.label 字段名称
     * @delphi.size 100
     */
    private String columnName;

    /**
     * 字段的别名. 缺省为 tableCode + "_" + columnCode.
     * 需要一个 columnAlias 的原因是为处理同一个字段 select 两次的情况, 
     * 如 select code code1, code code2 from table1.
     * 另外: 计算字段必须有一个 columnAlias, 且这个别名可由程序按一定规则生成, 也可由用户指定.
     * 字段别名唯一定位一个字段.
     * @delphi.label 字段别名
     * @delphi.required true
     * @delphi.size 30
     */
    private String columnAlias;

    /**
     * 该字段在 select 中出现的顺序. 从 1 开始, -1 则说明该字段不用于 select clause
     * @delphi.label 显示顺序
     */
    private int selectOrder = -1;

    /**
     * 该字段在 order by 中出现的顺序. 从 1 开始, -1 则说明该字段不用于 order by clause.
     * @delphi.label 排序顺序
     */
    private int orderBy = -1;

    /**
     * 升序还是降序. 这个属性只有当 orderBy > 0 时有效.
     * @delphi.label 升序
     */
    private boolean asc = false;

    /**
     * 当前字段是否是计算字段.
     * @delphi.label 计算字段
     */
    private boolean calculate = false;

    /**
     * 计算字段的表达式. 这个属性只有当 calculate == true 时有效.
     * @delphi.label 计算字段表达式
     * @delphi.size 255
     */
    private String calExp;

    /**
     * 如果该字段是分组字段, 则这个属性为分组函数的表达式, 
     * 如:sum, max, min, count, avg. 如果不是分组字段则为 null 或者 "".
     * 一旦包含分组字段, 则会产生 group by clause, group by 的字段为非分组字段,
     * 顺序为显示顺序.
     * @delphi.label 分组函数
     * @delphi.size 255
     */
    private String groupFunc;

    public String getGroupFunc() {
        return groupFunc;
    }

    public void setGroupFunc(String groupFunc) {
        this.groupFunc = groupFunc;
    }

    public QueryColumnDTO(String tableCode, String tableName,
            String tableAlias, String columnCode, String columnName,
            String columnAlias, int selectOrder, int orderBy, boolean asc,
            boolean calculate, String calExp) {
        this.tableCode = tableCode;
        this.tableName = tableName;
        this.tableAlias = tableAlias;
        this.columnCode = columnCode;
        this.columnName = columnName;
        this.columnAlias = columnAlias;
        this.selectOrder = selectOrder;
        this.orderBy = orderBy;
        this.asc = asc;
        this.calculate = calculate;
        this.calExp = calExp;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public boolean isCalculate() {
        return calculate;
    }

    public void setCalculate(boolean calculate) {
        this.calculate = calculate;
    }

    public String getCalExp() {
        return calExp;
    }

    public void setCalExp(String calExp) {
        this.calExp = calExp;
    }

    public String getColumnAlias() {
        return columnAlias;
    }

    public void setColumnAlias(String columnAlias) {
        this.columnAlias = columnAlias;
    }

    public String getColumnCode() {
        return columnCode;
    }

    public void setColumnCode(String columnCode) {
        this.columnCode = columnCode;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public int getSelectOrder() {
        return selectOrder;
    }

    public void setSelectOrder(int selectOrder) {
        this.selectOrder = selectOrder;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isGroup() {
        return StringUtils.isNotEmpty(groupFunc);
    }

}
