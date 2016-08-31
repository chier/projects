package com.cmcc.anal.common.persistence.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cmcc.anal.common.util.StringUtils;


/**
 * @spring.bean id="queryBuilder"
 * @author chenke
 * @since 2006-6-22
 */
public class QueryBuilderImpl implements QueryBuilder {

	protected Log log = LogFactory.getLog(getClass());

//	public String buildSql(QueryTemplateDTO queryTemplateDTO) {
//		StringBuffer sql = new StringBuffer();
//		if (queryTemplateDTO.getQueryColumnList() != null
//				&& queryTemplateDTO.getQueryColumnList().size() != 0) {
//			String selectClause = buildSelectClause(queryTemplateDTO
//					.getQueryColumnList());
//			String fromClause = buildFromClause(queryTemplateDTO
//					.getQueryTableList(),
//					queryTemplateDTO.getQueryColumnList(), queryTemplateDTO
//							.getJoinConditionList());
//			String filterClause = buildFilter(queryTemplateDTO.getFilterList(),
//					queryTemplateDTO.getQueryColumnList());
//			sql.append(selectClause).append(" ").append(fromClause);
//			if (filterClause != null) {
//				sql.append(" WHERE ").append(filterClause);
//			}
//			String groupBy = buildGroupByClause(queryTemplateDTO
//					.getQueryColumnList());
//			if (groupBy != null) {
//				sql.append(" ").append(groupBy);
//			}
//			String orderBy = buildOrderByClause(queryTemplateDTO
//					.getQueryColumnList());
//			if (orderBy != null) {
//				sql.append(" ").append(orderBy);
//			}
//			return sql.toString();
//		}
//		return null;
//	}

//	public String buildSqlBefore(QueryTemplateDTO queryTemplateDTO) {
//		StringBuffer sql = new StringBuffer();
//		String selectClause = buildSelectClause(queryTemplateDTO
//				.getQueryColumnList());
//		String fromClause = buildFromClause(queryTemplateDTO
//				.getQueryTableList(), queryTemplateDTO.getQueryColumnList(),
//				queryTemplateDTO.getJoinConditionList());
//		String filterClause = buildFilter(queryTemplateDTO.getFilterList(),
//				queryTemplateDTO.getQueryColumnList());
//		sql.append(selectClause).append(" ").append(fromClause);
//		sql.append(" where 1=1 ");
//		if (filterClause != null) {
//			sql.append(" and ").append(filterClause);
//		}
//		return sql.toString();
//	}

//	public String buildSqlAfter(QueryTemplateDTO queryTemplateDTO, String sql) {
//		String groupBy = buildGroupByClause(queryTemplateDTO
//				.getQueryColumnList());
//		if (groupBy != null) {
//			sql = sql + " " + groupBy;
//			// sql.append(" ").append(groupBy);
//		}
//		String orderBy = buildOrderByClause(queryTemplateDTO
//				.getQueryColumnList());
//		if (orderBy != null) {
//			sql = sql + " " + orderBy;
//			// sql.append(" ").append(orderBy);
//		}
//		return sql;
//	}

//	public String buildSelectClause(List queryColumnList) {
//		List selectedColumns = getSelectColumn(queryColumnList);
//
//		// 经过 compile 后, selectedColumns.size() 必然大于 0
//
//		StringBuffer sql = new StringBuffer("SELECT ");
//		Collections.sort(selectedColumns, new SelectComparator());
//
//		/* SQL Server 特殊处理 */
//		if (DialectDef.SQLSERVER.equalsIgnoreCase(ResourceManager.getInstance()
//				.getDialect())) {
//			boolean isCode = false;
//			for (int i = 0; i < selectedColumns.size(); i++) {
//				QueryColumnDTO qc = (QueryColumnDTO) selectedColumns.get(i);
//
//				if ("CODE".equalsIgnoreCase(qc.getColumnAlias())) {
//					isCode = true;
//					break;
//				}
//			}
//			if (!isCode) {
//				for (int i = 0; i < selectedColumns.size(); i++) {
//					QueryColumnDTO qc = (QueryColumnDTO) selectedColumns.get(i);
//					if ("CODE".equalsIgnoreCase(qc.getColumnCode())) {
//						qc.setColumnAlias("CODE");
//						break;
//					}
//				}
//			}
//		}
//		for (int i = 0; i < selectedColumns.size(); i++) {
//			if (i != 0) {
//				sql.append(", ");
//			}
//			QueryColumnDTO qc = (QueryColumnDTO) selectedColumns.get(i);
//			if (qc.isCalculate()) {
//				String calExp = " " + qc.getCalExp();
//				calExp = translateCalExp(calExp);
//				sql.append(calExp).append(" ");
//				sql.append(qc.getColumnAlias());
//			} else if (qc.isGroup()) {
//				// count(if(length(A)>0,1,null)) A
//				// @author fla 2007-10-19
//				if (DialectDef.MYSQL.equalsIgnoreCase(ResourceManager
//						.getInstance().getDialect())) {
//					sql.append(qc.getGroupFunc()).append("( if(length(");
//					sql.append(qc.getTableAlias()).append(".");
//					sql.append(qc.getColumnCode()).append(")>0,1,null)) ");
//					sql.append(qc.getColumnAlias());
//				} else {
//					sql.append(qc.getGroupFunc()).append("(");
//					sql.append(qc.getTableAlias()).append(".");
//					sql.append(qc.getColumnCode()).append(") ");
//					sql.append(qc.getColumnAlias());
//				}
//			} else {
//				sql.append(qc.getTableAlias()).append(".");
//				sql.append(qc.getColumnCode()).append(" ");
//				sql.append(qc.getColumnAlias());
//			}
//		}
//
//		if (log.isDebugEnabled())
//			log.warn("buildSelectClause : " + sql.toString());
//		return sql.toString();
//	}

	/**
	 * 过滤掉不用于 select 的字段
	 */
	protected List getSelectColumn(List queryColumnList) {
		List selectedColumns = new ArrayList();
		for (int i = 0; i < queryColumnList.size(); i++) {
			QueryColumnDTO qc = (QueryColumnDTO) queryColumnList.get(i);
			if (qc.getSelectOrder() != -1) {
				selectedColumns.add(qc);
			}
		}
		return selectedColumns;
	}

	public String[] getLabels(List queryColumnList) {
		List selectedColumns = getSelectColumn(queryColumnList);
		Collections.sort(selectedColumns, new SelectComparator());
		String[] s = new String[selectedColumns.size()];
		for (int i = 0; i < selectedColumns.size(); i++) {
			QueryColumnDTO qc = (QueryColumnDTO) selectedColumns.get(i);
			s[i] = qc.getColumnName();
		}
		return s;
	}

	private class SelectComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			QueryColumnDTO qc1 = (QueryColumnDTO) o1;
			QueryColumnDTO qc2 = (QueryColumnDTO) o2;
			return qc1.getSelectOrder() - qc2.getSelectOrder();
		}

	}

	/**
	 * join 关系中包含外联关系
	 * 
	 * @param joinConditionList
	 *            List<JoinConditionDTO>
	 */
	private boolean hasOuterJoin(List joinConditionList) {
		if (joinConditionList == null || joinConditionList.size() == 0) {
			return false;
		}
		for (int i = 0; i < joinConditionList.size(); i++) {
			JoinConditionDTO jc = (JoinConditionDTO) joinConditionList.get(i);
			if (!jc.isInnerJoin()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 调整关联关系的顺序. 对于如下关联关系不做支持: A -- B, C -- D
	 * 
	 * @param joinConditionList
	 *            List<JoinConditionDTO>
	 */
//	protected List sortJoinCondition(List joinConditionList) {
//		List result = new ArrayList(joinConditionList.size());
//		List[] ls = divide(joinConditionList);
//		result.addAll(ls[0]);
//		for (; ls[1].size() != 0;) {
//			ls = divide(ls[1]);
//			result.addAll(ls[0]);
//		}
//		return result;
//	}

	/**
	 * @param joinConditionList
	 *            List<JoinConditionDTO>
	 */
//	private List[] divide(List joinConditionList) {
//		if (joinConditionList == null || joinConditionList.size() == 0) {
//			return new List[] { ListUtils.EMPTY_LIST, ListUtils.EMPTY_LIST };
//		}
//		List grouped = new ArrayList();
//		Set joined = new HashSet();
//		List left = joinConditionList;
//		JoinConditionDTO j = (JoinConditionDTO) left.get(0);
//		joined.add(j.getLeftTableAlias());
//		joined.add(j.getRightTableAlias());
//		grouped.add(j);
//		left.remove(0);
//
//		boolean flag = true;
//		while (flag) {
//			flag = false;
//			for (int i = 0; i < left.size(); i++) {
//				JoinConditionDTO jc = (JoinConditionDTO) joinConditionList
//						.get(i);
//				String la = jc.getLeftTableAlias();
//				String ra = jc.getRightTableAlias();
//				if (joined.contains(la) || joined.contains(ra)) {
//					joined.add(la);
//					joined.add(ra);
//					grouped.add(jc);
//					left.remove(i);
//					flag = true;
//					break;
//				}
//			}
//		}
//		return new List[] { grouped, left };
//	}

//	public String buildFromClause(List queryTableList, List queryColumnList,
//			List joinConditionList) {
//		/*
//		 * 根据 joinConditionList 里的关联关系拼装 sql, 将没有关联关系的表做 cartesian product 例如:
//		 * FROM table1 t1, table2 t2 INNER JOIN table3 t3 ON (t2.col1=t3.col1)
//		 * 即将 t1 和 t2 join t3 的结果做 cartesian product Note: 对于全部是 inner join 的情况,
//		 * 需要先将 joinConditionList 做一个排序. 例如: A -- B, C -- D, A -- C,
//		 * 这3个关联关系需要先调整顺序为 A -- B, A -- C, C -- D, 这样拼出来的 join 关系才是正确的
//		 */
//		if (hasOuterJoin(joinConditionList)) {
//			joinConditionList = sortJoinCondition(joinConditionList);
//		}
//
//		StringBuffer sql = new StringBuffer();
//
//		List joinedTable = new ArrayList();// 关联关系中已经有关联关系的表
//		JoinKeyComparator jk = new JoinKeyComparator();
//		if (joinConditionList == null || joinConditionList.size() == 0) {
//			for (int i = 0; i < queryTableList.size(); i++) {
//				QueryTableDTO qt = (QueryTableDTO) queryTableList.get(i);
//				if (i != 0) {
//					sql.append(", ");
//				}
//				sql.append(getFromSelect(qt)).append(" ").append(
//						qt.getTableAlias());
//			}
//		} else {
//			for (int i = 0; i < joinConditionList.size(); i++) {
//				JoinConditionDTO qc = (JoinConditionDTO) joinConditionList
//						.get(i);
//
//				String leftTableAlias = qc.getLeftTableAlias();
//				QueryTableDTO qtl = QueryUtils.getQueryTable(queryTableList,
//						leftTableAlias);
//				String leftTableCode = getFromSelect(qtl);
//				if (i == 0) {
//					// 之前已经做过 validate, 则 leftTableCode 不会为 null
//					sql.append(leftTableCode).append(" ")
//							.append(leftTableAlias);
//				}
//
//				String joinRelation = qc.isInnerJoin() ? " INNER JOIN " : (qc
//						.isLeftJoin() ? " LEFT OUTER JOIN "
//						: " RIGHT OUTER JOIN ");
//				sql.append(joinRelation);
//
//				String rightTableAlias = qc.getRightTableAlias();
//				QueryTableDTO qtr = QueryUtils.getQueryTable(queryTableList,
//						rightTableAlias);
//
//				String rightTableCode = getFromSelect(qtr);
//				if (i == 0) {
//					sql.append(rightTableCode).append(" ").append(
//							rightTableAlias);
//				} else {
//					if (joinedTable.contains(leftTableAlias)) {
//						sql.append(rightTableCode).append(" ").append(
//								rightTableAlias);
//					} else {
//						sql.append(leftTableCode).append(" ").append(
//								leftTableAlias);
//					}
//				}
//
//				sql.append(" ON (");
//
//				String s = buildJoinOnClause(qc, queryColumnList);
//				sql.append(s);
//
//				while (i != joinConditionList.size() - 1) {
//					// 处理两张相同的表之间的多重关联关系
//					JoinConditionDTO qc1 = (JoinConditionDTO) joinConditionList
//							.get(i + 1);
//					if (jk.compare(qc, qc1) == 0) {
//						sql.append(" AND ");
//						String s1 = buildJoinOnClause(qc1, queryColumnList);
//						sql.append(s1);
//						i++;
//					} else {
//						break;
//					}
//				}
//
//				sql.append(")");
//
//				joinedTable.add(leftTableAlias);
//				joinedTable.add(rightTableAlias);
//			}
//			// cartesian product
//			if (joinedTable.size() > 0) {
//				String[] joined = StringUtils.toArray(joinedTable);
//				String[] tableAlias = QueryUtils.getTableAlias(queryTableList);
//				String[] tablesLeft = StringUtils.minus(tableAlias, joined,
//						true);
//				if (tablesLeft != null && tablesLeft.length > 0) {
//					StringBuffer sb = new StringBuffer();
//					for (int i = 0; i < tablesLeft.length; i++) {
//						QueryTableDTO qt = QueryUtils.getQueryTable(
//								queryTableList, tablesLeft[i]);
//						String tableCode = getFromSelect(qt);
//						sb.append(tableCode).append(" ").append(tablesLeft[i])
//								.append(", ");
//					}
//					sql.insert(0, sb.toString());
//				}
//			}
//		}
//
//		sql.insert(0, "FROM ");
//
//		return sql.toString();
//	}

	protected String buildJoinOnClause(JoinConditionDTO qc, List queryColumnList) {
		StringBuffer sql = new StringBuffer();
		String leftColumnAlias = qc.getLeftColumnAlias();
		String leftColumnCode = QueryUtils.getColumnCode(queryColumnList,
				leftColumnAlias);
		String rightColumnAlias = qc.getRightColumnAlias();
		String rightColumnCode = QueryUtils.getColumnCode(queryColumnList,
				rightColumnAlias);
		sql.append(qc.getLeftTableAlias()).append(".").append(leftColumnCode);
		sql.append(qc.getRelation());
		sql.append(qc.getRightTableAlias()).append(".").append(rightColumnCode);
		return sql.toString();
	}

	// public String getFromSelect(QueryTableDTO qt) {
	// if ("D".equalsIgnoreCase(qt.getTableType())) {
	// String s = cateEntryBO.queryEntry(qt.getTableCode(), qt
	// .getCateRoot(), qt.getCateLevelNum());
	// return "(" + s + ")";
	// } else {
	// return qt.getTableCode();
	// }
	// }

	public String buildJoinClause(List joinConditionList) {
		StringBuffer sql = new StringBuffer();
		for (int i = 0; i < joinConditionList.size(); i++) {
			if (i != 0) {
				sql.append(" AND ");
			}
			JoinConditionDTO jc = (JoinConditionDTO) joinConditionList.get(i);
			sql.append(jc.getLeftTableAlias()).append(".").append(
					jc.getLeftColumnAlias());
			sql.append(jc.getRelation());
			sql.append(jc.getRightTableAlias()).append(".").append(
					jc.getRightColumnAlias());
		}
		return sql.length() == 0 ? null : sql.toString();
	}

	public String buildFilter(List filterList) {
		return buildFilter(filterList, null);
	}

	public String buildFilter(List filterList, List queryColumnList) {
		if (filterList == null || filterList.size() == 0) {
			return null;
		}
		return buildFilter1(filterList, queryColumnList);
	}

	private String buildFilter1(List filterList, List queryColumnList) {
		StringBuffer sql = new StringBuffer();
		for (int i = 0; i < filterList.size(); i++) {
			if (i != 0) {
				sql.append(getRelation(filterList, i));
			}
			FilterDTO filter = (FilterDTO) filterList.get(i);
			String s = buildOneFilter(filter, queryColumnList);
			int parenthesis = filter.getParenthesis();
			if (parenthesis > 0) {
				String ss = org.apache.commons.lang.StringUtils.repeat("(",
						parenthesis);
				sql.append(ss);
				sql.append(s);
			} else if (parenthesis < 0) {
				sql.append(s);
				String ss = org.apache.commons.lang.StringUtils.repeat(")",
						-parenthesis);
				sql.append(ss);
			} else {
				sql.append(s);
			}
		}
		return sql.toString();
	}

	/**
	 * 原先的设计, 采用优先级的方式记录约束条件之间的关系. 该方法已废弃.
	 * 
	 * @deprecated
	 */
	private String buildFilter2(List filterList, List queryColumnList) {
		StringBuffer sql = new StringBuffer();
		// 记录最后一个条件的优先级
		int fp = 1;
		for (int i = 0, p = 0; i < filterList.size(); i++) {
			FilterDTO f = (FilterDTO) filterList.get(i);
			int np = f.getPriority();
			if (np > p) {
				sql.append(getRelation(filterList, i));
				if (np > 1) {
					sql.append("(");
				}
			} else if (np < p) {
				for (int j = 0; j < (p - np); j++) {
					sql.append(")");
				}
				sql.append(getRelation(filterList, i));
			} else {
				sql.append(getRelation(filterList, i));
			}
			p = np;
			fp = np;
			String s = buildOneFilter(f, queryColumnList);
			sql.append(s);
		}
		// 如果最后一个条件的优先级不是 1 , 则需要补充 fp - 1 个括号
		for (int i = 0; i < fp - 1; i++) {
			sql.append(")");
		}
		return sql.length() == 0 ? null : sql.toString();
	}

	/**
	 * 得到当前第 i 个过滤条件的逻辑关系
	 * 
	 * @return " AND " 或者 " OR "; "", 如果 i == 0, 因为没有前逻辑关系
	 */
	private String getRelation(List filterList, int i) {
		if (i == 0) {
			return "";
		}
		FilterDTO ff = (FilterDTO) filterList.get(i - 1);
		return ff.isAndRelation() ? " AND " : " OR ";
	}

	/**
	 * 拼装一个过滤条件的 sql, 如: t1.C1=t2.C2
	 */
	private String buildOneFilter(FilterDTO filter, List queryColumnList) {
		String alias = filter.getColumnAlias();
		if (queryColumnList == null) {
			// 针对数据导入导出模块的分支

			String value = filter.getValue();
			if (QueryBuilder.FILTER_VALUE_LITERAL_TYPE.equals(filter
					.getValueType())) {
				if (filter.getOperator().toUpperCase().indexOf("IN") != -1) {
					// in(a,b,c,1,3) -> in('a','b','c','1','3')
					String[] valueElement = org.apache.commons.lang.StringUtils
							.split(value, ",");
					if (valueElement != null) {
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < valueElement.length; i++) {
							if (i != 0) {
								sb.append(",");
							}
							if (!org.apache.commons.lang.StringUtils
									.isNumeric(valueElement[i])) {

								sb.append(StringUtils.enclosedWithIfNot(
										valueElement[i], "'", "'"));
							} else {
								sb.append(valueElement[i]);// 如果为数字不加''
							}

						}
						value = sb.toString();
					}

				} else {
					if (!org.apache.commons.lang.StringUtils.isNumeric(value)) {
						value = StringUtils.enclosedWithIfNot(value, "'", "'");
					}
				}
			}
			String result = buildOneFilter(filter.getTableCode() + "."
					+ filter.getColumnCode(), filter.getOperator(), value);
			return result;
		} else {
			QueryColumnDTO qc = QueryUtils.getQueryColumn(queryColumnList,
					alias);
			if (qc == null) {
				throw new RuntimeException("过滤条件中有未知的字段别名[" + alias + "]");
			}
			String result = null;
			String filterValue = getFilterValue(filter, queryColumnList);
			// 查询模块的分支
			if (qc.isCalculate()) {
				result = buildCalExp(qc.getCalExp(), filter.getOperator(),
						filterValue);
			} else {
				result = buildOneFilter(qc.getTableAlias() + "."
						+ qc.getColumnCode(), filter.getOperator(), filterValue);
			}
			return result;
		}
	}

	/**
	 * 由于 FilterDTO 的 value 属性根据属性 valueType 分为几种, 这里处理 value 类型的逻辑
	 */
	private String getFilterValue(FilterDTO filter, List queryColumnList) {
		if (QueryBuilder.FILTER_VALUE_COLUMN_TYPE.equals(filter.getValueType())) {
			QueryColumnDTO qc = QueryUtils.getQueryColumn(queryColumnList,
					filter.getValue());
			if (qc != null && qc.isCalculate()) {
				String calExp = qc.getCalExp();
				calExp = translateCalExp(calExp);
				return calExp;
			} else {
				return filter.getValue();
			}
			// if (qc == null) {
			// throw new BusinessLogicalException("过滤条件["
			// + filter.getTableCode() + "." + filter.getColumnCode()
			// + "]中的值[" + filter.getValue()
			// + "]被定义为字段, 但是查询模版中不存在这个查询字段");
			// }
		} else if (QueryBuilder.FILTER_VALUE_LITERAL_TYPE.equals(filter
				.getValueType())) {
			String value = filter.getValue();
			// if (!org.apache.commons.lang.StringUtils.isNumeric(value)) {
			if (!"NULL".equals(value.toUpperCase())) {
				value = StringUtils.enclosedWithIfNot(value, "'", "'");
			}
			// }
			return value;
		} else {
			throw new RuntimeException("过滤条件中存在不支持的值类型["
					+ filter.getValueType() + "]");
		}
	}

	protected String buildCalExp(String calExp, String operator, String value) {
		String s = translateCalExp(calExp);
		return buildOneFilter(s, operator, value);
	}

	/**
	 * 翻译计算字段里的 SQL 函数为当前数据库的 SQL 函数
	 */
	protected String translateCalExp(String calExp) {
//		return queryDAO.translateSql(calExp);
		return null;
	}

	protected String buildOneFilter(String left, String operator, String value) {
		StringBuffer sql = new StringBuffer();
		sql.append(left).append(" ");
		sql.append(operator).append(" ");
		String s = value;
		String op = operator.toUpperCase();
		if (op.indexOf("LIKE") != -1) {
			s = StringUtils.enclosedWithIfNot(value, "'", "'");
		} else if (op.indexOf("IN") != -1) {
			s = StringUtils.enclosedWithIfNot(value, "(", ")");
		}
		sql.append(s);
		return sql.toString();
	}

//	public String buildOrderByClause(List queryColumnList) {
//		List orderByColumns = new ArrayList();
//		for (int i = 0; i < queryColumnList.size(); i++) {
//			QueryColumnDTO qc = (QueryColumnDTO) queryColumnList.get(i);
//			if (qc.getOrderBy() != -1) {
//				orderByColumns.add(qc);
//			}
//		}
//
//		if (orderByColumns.size() == 0) {
//			if (DialectDef.ORACLE9.equalsIgnoreCase(ResourceManager
//					.getInstance().getDialect())) {
//				StringBuffer sql = null;
//				if (hasGroupByClause(queryColumnList)) {
//					return null;
//				}
//				for (int i = 0; i < queryColumnList.size(); i++) {
//					QueryColumnDTO qc = (QueryColumnDTO) queryColumnList.get(i);
//					if ("CODE".equalsIgnoreCase(qc.getColumnAlias())) {
//						sql = new StringBuffer("ORDER BY ");
//						sql.append(qc.getTableAlias()).append(".").append(
//								"rowid");
//						return sql.toString();
//					}
//				}
//				if (sql == null) {
//					QueryColumnDTO qc = (QueryColumnDTO) queryColumnList.get(0);
//					sql = new StringBuffer("ORDER BY ");
//					sql.append(qc.getTableAlias()).append(".").append("rowid");
//					return sql.toString();
//				}
//			}
//			return null;
//		}
//
//		StringBuffer sql = new StringBuffer("ORDER BY ");
//		Collections.sort(orderByColumns, new OrderByComparator());
//		for (int i = 0; i < orderByColumns.size(); i++) {
//			if (i != 0) {
//				sql.append(", ");
//			}
//			QueryColumnDTO qc = (QueryColumnDTO) orderByColumns.get(i);
//
//			if (qc.isCalculate()) {
//				sql.append(qc.getCalExp());
//			} else {
//				sql.append(qc.getTableAlias()).append(".").append(
//						qc.getColumnCode());
//			}
//			sql.append(qc.isAsc() ? " ASC" : " DESC");
//		}
//
//		if (log.isDebugEnabled()) {
//			log.warn("buildOrderByClause : " + sql.toString());
//		}
//		return sql.toString();
//	}

	private class OrderByComparator implements Comparator {

		public int compare(Object o1, Object o2) {
			QueryColumnDTO qc1 = (QueryColumnDTO) o1;
			QueryColumnDTO qc2 = (QueryColumnDTO) o2;
			return qc1.getOrderBy() - qc2.getOrderBy();
		}

	}

	/**
	 * 见 QueryColumnDTO.groupFunc 的注释
	 * 
	 * @see QueryColumnDTO
	 */
	public String buildGroupByClause(List queryColumnList) {
		if (!hasGroupByClause(queryColumnList)) {
			return null;
		}
		List selectedColumns = getSelectColumn(queryColumnList);
		Collections.sort(selectedColumns, new SelectComparator());
		StringBuffer sql = new StringBuffer("GROUP BY ");
		boolean hasGroupByColumn = false;
		for (int i = 0; i < selectedColumns.size(); i++) {
			QueryColumnDTO qc = (QueryColumnDTO) selectedColumns.get(i);
			if (!qc.isGroup()) {
				if (hasGroupByColumn) {
					sql.append(", ");
				}
				// 判断是否是计算字段
				if (!qc.isCalculate()) {
					sql.append(qc.getTableAlias()).append(".");
				}
				sql.append(qc.getColumnCode());
				hasGroupByColumn = true;
			}
		}
		if (!hasGroupByColumn) {
			return null;
		}
		return sql.toString();
	}

	/**
	 * 判断是否有 group by clause
	 */
	private boolean hasGroupByClause(List queryColumnList) {
		for (int i = 0; i < queryColumnList.size(); i++) {
			QueryColumnDTO qc = (QueryColumnDTO) queryColumnList.get(i);
			if (qc.isGroup()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 拼装 标签 类似如下的
	 * 
	 * <PRE>
	 * 
	 * (t1.LABEL_CODE LIKE '%X%' OR t1.LABEL_CODE LIKE '%Y%') AND (t2.LABEL_CODE
	 * LIKE '%X%' OR t2.LABEL_CODE LIKE '%Y%')
	 * 
	 * </PRE>
	 * 
	 * @return null
	 */
	public String buildLabel(QueryTemplateDTO queryTemplateDTO,
			String[] labelCodes) {
		List tables = queryTemplateDTO.getQueryTableList();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < tables.size(); i++) {
			QueryTableDTO qt = (QueryTableDTO) tables.get(i);
			if (("A".equalsIgnoreCase(qt.getTableType()) || "P"
					.equalsIgnoreCase(qt.getTableType())) ? qt
					.getBaseTableType() == 1 : false) {
				if (sb.length() != 0)
					sb.append(" AND ");
				sb.append("(");
				for (int j = 0; j < labelCodes.length; j++) {
					if (j != 0)
						sb.append(" OR ");
					sb.append(qt.getTableAlias()).append(".LABEL_CODE LIKE '%")
							.append(labelCodes[j]).append("%'");
				}
				sb.append(")");
			}
		}
		return "".equals(sb.toString()) ? null : sb.toString();
	}

	/**
	 * 拼装 专业 类似如下的
	 * 
	 * <PRE>
	 * 
	 * t1.SPEC_CODE IN('A','B','C','D') AND t2.SPEC_CODE IN('A','B','C','D')
	 * 
	 * </PRE>
	 * 
	 * @return null
	 */
//	public String buildDspec(QueryTemplateDTO queryTemplateDTO,
//			String[] speccodes) {
//		StringBuffer sql = new StringBuffer();
//		String in = null;
//		List tables = queryTemplateDTO.getQueryTableList();
//		for (int i = 0; i < tables.size(); i++) {
//			QueryTableDTO qt = (QueryTableDTO) tables.get(i);
//			if (("A".equalsIgnoreCase(qt.getTableType()) || "P"
//					.equalsIgnoreCase(qt.getTableType())) ? qt
//					.getBaseTableType() == 1 : false) {
//				if (in == null) {
//					String[] child = queryDAO.getChild(speccodes);
//					StringBuffer insb = new StringBuffer();
//					for (int j = 0; j < child.length; j++) {
//						if (j != 0)
//							insb.append(",");
//						insb.append("'").append(child[j]).append("'");
//					}
//					in = insb.toString();
//				}
//				if (!"".equals(in)) {
//					if (sql.length() != 0)
//						sql.append(" AND ");
//					sql.append(qt.getTableAlias()).append(".SPEC_CODE IN(")
//							.append(in).append(")");
//				}
//			}
//		}
//		return "".equals(sql.toString()) ? null : sql.toString();
//	}
}
