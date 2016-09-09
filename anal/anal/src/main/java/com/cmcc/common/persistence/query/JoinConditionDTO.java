package com.cmcc.common.persistence.query;


/**
 * 定义 QueryTableDTO 对象之间的关联关系.
 * Note: 对于外连接的情况有一些特殊, 例如 t1 左连接 t2 再左连接 t3
 * 和 t3 左连接 t2 再左连接 t1 的意义是不同的.
 * 因此, 在做查询模版分析的实现时, 关联顺序依据模版对象里 JoinConditionDTO 列表的顺序关联查询表.
 * 目前只支持 equals join, inner join 和 outer join 
 * @author chenke
 * @since 2006-6-16
 */
public class JoinConditionDTO extends BaseDTO {

    /**
     * 关联关系中位于左边的表的中文表名
     * @delphi.label 左表名称
     * @delphi.size 100
     */
    private String leftTableName;

    /**
     * 关联关系中位于左边的表的别名
     * @delphi.label 左表别名
     * @delphi.required true
     * @delphi.size 30
     */
    private String leftTableAlias;

    /**
     * 关联关系中位于左边的字段的中文名
     * @delphi.label 左字段名称
     * @delphi.size 100
     */
    private String leftColumnName;

    /**
     * 关联关系中位于左边的字段的别名
     * @delphi.label 左字段别名
     * @delphi.required true
     * @delphi.size 30
     */
    private String leftColumnAlias;

    /**
     * 关联关系中位于右边的表的中文表名
     * @delphi.label 右表名称
     * @delphi.size 100
     */
    private String rightTableName;

    /**
     * 关联关系中位于右边的表的别名
     * @delphi.label 右表别名
     * @delphi.required true
     * @delphi.size 30
     */
    private String rightTableAlias;

    /**
     * 关联关系中位于右边的字段的中文名
     * @delphi.label 右字段名称
     * @delphi.size 100
     */
    private String rightColumnName;

    /**
     * 关联关系中位于右边的字段的别名
     * @delphi.label 右字段别名
     * @delphi.required true
     * @delphi.size 30
     */
    private String rightColumnAlias;

    /**
     * 关联关系. 目前仅支持 =
     * @delphi.label 关联关系
     * @delphi.required true
     * @delphi.size 1
     * @delphi.validateExp exp="[=]{1}" msg="表间关联关系目前仅支持 '='"
     */
    private String relation;

    /**
     * 是否是内连接.
     * @delphi.label 内连接
     */
    private boolean innerJoin = true;

    /**
     * 是否是 left outer join, 否则是 right outer join.
     * 该属性只有当 innerJoin == false 时有效
     * @delphi.label 左连接
     */
    private boolean leftJoin = true;

    public JoinConditionDTO(String leftTableName, String leftTableAlias,
            String leftColumnName, String leftColumnAlias,
            String rightTableName, String rightTableAlias,
            String rightColumnName, String rightColumnAlias, String relation,
            boolean innerJoin, boolean leftJoin) {
        this.leftTableName = leftTableName;
        this.leftTableAlias = leftTableAlias;
        this.leftColumnName = leftColumnName;
        this.leftColumnAlias = leftColumnAlias;
        this.rightTableName = rightTableName;
        this.rightTableAlias = rightTableAlias;
        this.rightColumnName = rightColumnName;
        this.rightColumnAlias = rightColumnAlias;
        this.relation = relation;
        this.innerJoin = innerJoin;
        this.leftJoin = leftJoin;
    }

    public boolean isLeftJoin() {
        return leftJoin;
    }

    public void setLeftJoin(boolean leftJoin) {
        this.leftJoin = leftJoin;
    }

    public boolean isInnerJoin() {
        return innerJoin;
    }

    public void setInnerJoin(boolean innerJoin) {
        this.innerJoin = innerJoin;
    }

    public String getLeftColumnAlias() {
        return leftColumnAlias;
    }

    public void setLeftColumnAlias(String leftColumnAlias) {
        this.leftColumnAlias = leftColumnAlias;
    }

    public String getLeftColumnName() {
        return leftColumnName;
    }

    public void setLeftColumnName(String leftColumnName) {
        this.leftColumnName = leftColumnName;
    }

    public String getLeftTableAlias() {
        return leftTableAlias;
    }

    public void setLeftTableAlias(String leftTableAlias) {
        this.leftTableAlias = leftTableAlias;
    }

    public String getLeftTableName() {
        return leftTableName;
    }

    public void setLeftTableName(String leftTableName) {
        this.leftTableName = leftTableName;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRightColumnAlias() {
        return rightColumnAlias;
    }

    public void setRightColumnAlias(String rightColumnAlias) {
        this.rightColumnAlias = rightColumnAlias;
    }

    public String getRightColumnName() {
        return rightColumnName;
    }

    public void setRightColumnName(String rightColumnName) {
        this.rightColumnName = rightColumnName;
    }

    public String getRightTableAlias() {
        return rightTableAlias;
    }

    public void setRightTableAlias(String rightTableAlias) {
        this.rightTableAlias = rightTableAlias;
    }

    public String getRightTableName() {
        return rightTableName;
    }

    public void setRightTableName(String rightTableName) {
        this.rightTableName = rightTableName;
    }

}
