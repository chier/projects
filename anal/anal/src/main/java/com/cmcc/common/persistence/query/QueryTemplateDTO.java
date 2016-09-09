package com.cmcc.common.persistence.query;

import java.util.List;


/**
 * 查询模版.
 * 该数据结构的设计完全是为满足客户端使用的方便行. 因此在对象设计上存在一些不合理的地方,
 * 例如数据的冗余, 没有使用设计来避免数据的自相矛盾等问题.
 * @author chenke
 * @since 2006-6-16
 */
public class QueryTemplateDTO extends BaseDTO {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6685779225533236279L;

	/**
     * 模版代码. 唯一标识一个查询模版，服务器端生成(使用UUID).
     * @delphi.label 模版代码
     * @delphi.size 32
     */
    private String templateCode;

    /**
     * 模版的中文名称, 用户指定. 缺省值为模版代码 templateCode.
     * @delphi.label 模版名称
     * @delphi.size 255
     */
    private String templateName;

    /**
     * 当前模版是否通过编译
     * @delphi.label 通过编译
     */
    private boolean compiled = false;

    /**
     * 用户直接指定的查询 sql
     * @delphi.label 指定SQL
     * @delphi.size 4000
     */
    private String selectSql;

    /**
     * 模版的创建日期，服务器端生成. 格式: yyyy-MM-dd HH:mm:ss SS
     * @delphi.label 创建日期
     * @delphi.size 30
     */
    private String createDate;

    /**
     * 模版的描述信息，可为空.
     * @delphi.label 模版说明
     * @delphi.size 255
     */
    private String templateDesc;

    /**
     * 0 – 不包含报表;
     * 1 - 包含报表;
     * 2 - 用户直接指定的SQL.
     * 客户端程序指定类型，非用户指定. 这个字段用于说明是否包含报表，如果包含，
     * 则需要特殊处理报表的CODE字段。
     * Note: 这个属性是冗余信息, 在 QueryTableDTO 里有属性 tableType 说明是否是报表.
     * @delphi.label 模版类型
     * @delphi.size 1
     */
    private String templateType;

    /*#QueryTableDTO Dependency_Link*/
    /**
     * List<QueryTableDTO>
     * 查询表列表
     * @delphi.label 查询表
     */
    private List queryTableList;

    /*#QueryColumnDTO Dependency_Link1*/
    /**
     * List<QueryColumnDTO>
     * 查询中涉及到的所有字段的列表, 包括计算字段.
     * @delphi.label 查询字段
     */
    private List queryColumnList;

    /*#JoinConditionDTO Dependency_Link2*/
    /**
     * List<JoinConditionDTO>
     * 查询表间关联列表
     * @delphi.label 关联
     */
    private List joinConditionList;

    /*#FilterDTO Dependency_Link3*/
    /**
     * List<FilterDTO>
     * 约束条件列表.
     * Note:  filterList 必须保证顺序的正确性. 例如 filterList 里有两个条件:
     * and A = B 和 or C = D. 如果是这个顺序, 则条件表达式为 A = B or C = D;
     * 但假如条件 or C = D 在前面, 则条件表达式为 C = D and A = B.
     * @delphi.label 约束条件
     */
    private List filterList;

    public QueryTemplateDTO() {

    }

    public QueryTemplateDTO(String templateCode, String templateName,
            boolean compiled, String selectSql, String createDate,
            String templateDesc, String templateType) {
        this.templateCode = templateCode;
        this.templateName = templateName;
        this.compiled = compiled;
        this.selectSql = selectSql;
        this.createDate = createDate;
        this.templateDesc = templateDesc;
        this.templateType = templateType;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public List getFilterList() {
        return filterList;
    }

    public void setFilterList(List filterList) {
        this.filterList = filterList;
    }

    public List getJoinConditionList() {
        return joinConditionList;
    }

    public void setJoinConditionList(List joinConditionList) {
        this.joinConditionList = joinConditionList;
    }

    public List getQueryColumnList() {
        return queryColumnList;
    }

    public void setQueryColumnList(List queryColumnList) {
        this.queryColumnList = queryColumnList;
    }

    public List getQueryTableList() {
        return queryTableList;
    }

    public void setQueryTableList(List queryTableList) {
        this.queryTableList = queryTableList;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateDesc() {
        return templateDesc;
    }

    public void setTemplateDesc(String templateDesc) {
        this.templateDesc = templateDesc;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public boolean isCompiled() {
        return compiled;
    }

    public void setCompiled(boolean compiled) {
        this.compiled = compiled;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

}
