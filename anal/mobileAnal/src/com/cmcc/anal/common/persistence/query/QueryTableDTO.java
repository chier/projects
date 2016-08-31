package com.cmcc.anal.common.persistence.query;

/**
 * 查询模版中涉及到的表. 一个 QueryTableDTO 实例描述一张物理表. 表别名唯一定位一个 QueryTableDTO.
 * 
 * @author chenke
 * @since 2006-6-16
 */
public class QueryTableDTO extends BaseDTO {

	/**
	 * 表代码
	 * 
	 * @delphi.label 表代码
	 * @delphi.required true
	 * @delphi.size 30
	 */
	private String tableCode;

	/**
	 * 表的中文名称, 缺省为该表的 Comment, 用户可重指定.
	 * 
	 * @delphi.label 表名称
	 * @delphi.size 100
	 */
	private String tableName;

	/**
	 * 表类型: D - 目录 A - 基层表 B - 汇总表 P - 主报表(基层表) H - 主报表(汇总表) T - 物理表 Note:
	 * 报表分为基层表和汇总表, 两者都可能有子表. 定义主报表的作用: 如果查询模版中包含多张报表, 且报表之间没有都通过 CODE 做 CODE
	 * 相等的关联条件, 则需要指定以哪张报表的 CODE 和 STAT_UNIT 做数据过滤, 例如:只查看北京市的数据.
	 * 
	 * @delphi.label 表类型
	 * @delphi.size 20
	 * @delphi.validateExp exp="[DABPHT]{1}" msg="表类型为 D (目录), A(基层表), B(汇总表),
	 *                     P(主报表(基层表)), H(主报表(汇总表)), T(物理表)"
	 */
	private String tableType;

	/**
	 * @delphi.size 1
	 * @delphi.label 0:普通表 1:基本情况表 -1：非基层表
	 */
	private int baseTableType;

	/**
	 * @delphi.size 32
	 * @delphi.label 告期别 M：月 Y：年 Q：季
	 */
	private String cycCodeType;

	/**
	 * 表别名, 缺省为 tableCode. 同一个查询模版中, tableAlias 不能重复. 用于查询模版中涉及到同一张表自关联的情况,
	 * 这种情况下, 有两个 QueryTableDTO 实例, 区别是别名不一样.
	 * 
	 * @delphi.label 表别名
	 * @delphi.required true
	 * @delphi.size 30
	 */
	private String tableAlias;

	/**
	 * 如果当前表为报表, 且是报表中的子表, 则这个属性为父表的代码; 否则为 null
	 * 
	 * @delphi.label 父表代码
	 * @delphi.size 30
	 */
	private String parentTableCode;

	/**
	 * 如果当前表为目录表, 即 tableType == "CATE" 则这个属性为该目录的目录表达式的顶点代码. 如:
	 * 42010000,42020000;
	 * 
	 * @delphi.label 目录顶点
	 * @delphi.size 255
	 */
	private String cateRoot;

	/**
	 * 如果当前表为目录表, 即 tableType == "CATE" 则这个属性为该目录的目录表达式的层表达式. 如: 1,2+;
	 * 
	 * @delphi.label 目录层表达式
	 * @delphi.size 255
	 */
	private String cateLevelNum;

	/**
	 * @delphi.label 表顶点的 y 坐标
	 */
	private int top;

	/**
	 * @delphi.label 表顶点的 x 坐标
	 */
	private int left;

	/**
	 * @delphi.label 表的宽度
	 */
	private int width;

	/**
	 * @delphi.label 表的高度
	 */
	private int height;

	public QueryTableDTO(String tableCode, String tableName, String tableType,
			String tableAlias, String parentTableCode, String cateRoot,
			String cateLevelNum) {
		this.tableCode = tableCode;
		this.tableName = tableName;
		this.tableType = tableType;
		this.tableAlias = tableAlias;
		this.parentTableCode = parentTableCode;
		this.cateRoot = cateRoot;
		this.cateLevelNum = cateLevelNum;
	}

	public String getCateLevelNum() {
		return cateLevelNum;
	}

	public void setCateLevelNum(String cateLevelNum) {
		this.cateLevelNum = cateLevelNum;
	}

	public String getCateRoot() {
		return cateRoot;
	}

	public void setCateRoot(String cateRoot) {
		this.cateRoot = cateRoot;
	}

	public String getParentTableCode() {
		return parentTableCode;
	}

	public void setParentTableCode(String parentTableCode) {
		this.parentTableCode = parentTableCode;
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

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getBaseTableType() {
		return baseTableType;
	}

	public void setBaseTableType(int baseTableType) {
		this.baseTableType = baseTableType;
	}

	public String getCycCodeType() {
		return cycCodeType;
	}

	public void setCycCodeType(String cycCodeType) {
		this.cycCodeType = cycCodeType;
	}

}
