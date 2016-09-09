package com.cmcc.common.util;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 提供一些常用的字符串拼装, 转义, SQL拼装的方法
 * 
 * @author chenke
 * @since 2005-5-31
 */
public final class StringUtils {
	private static Log log = LogFactory.getLog(StringUtils.class);

	public static final String[] EMPTY_STRING_ARRAY = new String[] {};

	public static final int[] EMPTY_INT_ARRAY = new int[] {};

	private StringUtils() {
	}

	/**
	 * 将 String 做XML转义
	 */
	public static String esc(String s) {
		// 如果s == null, StringEscapeUtils.escapeXml(s)会返回null.
		// 而我们要求返回 ""
		if (s == null) {
			return "";
		}
		return StringEscapeUtils.escapeXml(s);
	}

	public static String unesc(String s) {
		if (s == null) {
			return "";
		}
		return StringEscapeUtils.unescapeXml(s);
	}

	/**
	 * 字符串拼装
	 * <pre>
	 * combine(new String[]{"1", "2"}, ",", "'") == "'1','2'"
	 * </pre>
	 */
	public static String combine(String[] s, String sep, String quote) {
		if (s == null || s.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length; i++) {
			if (i != 0) {
				sb.append(sep);
			}
			if (quote != null) {
				sb.append(quote);
			}
			sb.append(s[i]);
			if (quote != null) {
				sb.append(quote);
			}
		}
		return sb.toString();
	}

	/**
	 * 将 source 里的字符串都在左边加上 leftStr, 右边加上 rightStr
	 * <pre>
	 * String[] source = new String[]{"s1", "s2"};
	 * enclosedWith(source, "[", "]");
	 * source[0] == "[s1]";
	 * source[1] == "[s2]";
	 * </pre>
	 */
	public static void enclosedWith(String[] source, String leftStr,
			String rightStr) {
		for (int i = 0; i < source.length; i++) {
			if (source[i] == null) {
				continue;
			}
			source[i] = leftStr + source[i] + rightStr;
		}
	}

	/**
	 * 如果 str 不是以 leftStr 开头, 以 rightStr 结尾, 则在开头补上 leftStr,
	 * 结尾补上 rightStr
	 * <pre>
	 * enclosedWithIfNot(abc, "1", "3") == "1abc3"
	 * enclosedWithIfNot(null, "(", ")") == "()"
	 * enclosedWithIfNot(abc, "a", "c") == "abc"
	 * enclosedWithIfNot(abc, "a", "d") == "aabcd"
	 * </pre>
	 */
	public static String enclosedWithIfNot(String str, String leftStr,
			String rightStr) {
		if (str == null) {
			return leftStr + rightStr;
		}
		if (str.startsWith(leftStr) && str.endsWith(rightStr)) {
			return str;
		}
		return leftStr + str + rightStr;
	}

	/**
	 * 字符串拼装
	 * <pre>
	 * combine(new String[]{"1", "2"}, ",") == "1,2"
	 * </pre>
	 * 
	 * @deprecated 使用 jakarta-commons-lang2.1 StringUtils.join(Object[] array, String separator);
	 */
	public static String combine(String[] s, String sep) {
		return combine(s, sep, null);
	}

	/**
	 * 字符串拼装
	 * <pre>
	 * combine(new String[]{"1", "2"}) == "1,2"
	 * </pre>
	 */
	public static String combine(String[] s) {
		return combine(s, ",", null);
	}

	/**
	 * 整型数组的拼装
	 * <pre>
	 * combineIntArray(new int[]{1,2}) == "1,2"
	 * </pre>
	 */
	public static String combineIntArray(int[] n) {
		return combine(intToStringArray(n));
	}

	/**
	 * 带字段前缀的拼装
	 * <pre>
	 * combineWithPrefix(new String[]{"col1", "col2"}, t) == "t.col1,t.col2"
	 * </pre>
	 */
	public static String combineWithPrefix(String[] cols, String prefix) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cols.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(prefix).append(".").append(cols[i]);
		}
		return sb.toString();
	}

	/**
	 * 给 s 里的所有字符串加上前缀
	 * 
	 * <pre>
	 * addPrefix(new String[]{"col1", "col2"}, "t1") == String[]{"t1.col1", "t1.col2"}
	 * </pre>
	 */
	public static String[] addPrefix(String[] s, String prefix) {
		if (s == null || s.length == 0) {
			return EMPTY_STRING_ARRAY;
		}
		if (org.apache.commons.lang.StringUtils.isBlank(prefix)) {
			return s;
		}
		String[] result = copyArray(s);
		for (int i = 0; i < s.length; i++) {
			if (s[i] != null) {
				result[i] = prefix + "." + s[i];
			}
		}
		return result;
	}

	/**
	 * 补偿前缀, 即: s 如果不是以 prefix 作为前缀, 则加上该前缀;
	 * 如果是以prefix作为前缀, 则直接返回.
	 * 
	 * <pre>
	 * repairePrefix("asd", "11") == "11asd";
	 * repairePrefix("asd", "a") == "asd";
	 * repairePrefix("asd", "as") == "asd";
	 * repairePrefix(null, "as") == null;
	 * </pre>
	 */
	public static String repairePrefix(String s, String prefix) {
		if (s == null) {
			return null;
		}
		if (prefix == null) {
			return s;
		}
		if (!s.startsWith(prefix)) {
			return prefix + s;
		} else {
			return s;
		}
	}

	/**
	 * s 如果是以 prefix 作为前缀, 则去掉该前缀;
	 * 如果不是以prefix作为前缀, 则直接返回.
	 * 
	 * <pre>
	 * cutPrefix("asd", "11") == "asd";
	 * cutPrefix("asd", "a") == "sd";
	 * cutPrefix("asd", "as") == "d";
	 * cutPrefix(null, "as") == null;
	 * </pre>
	 */
	public static String cutPrefix(String s, String prefix) {
		if (s == null) {
			return null;
		}
		if (prefix == null) {
			return s;
		}
		if (s.startsWith(prefix)) {
			return s.substring(prefix.length());
		} else {
			return s;
		}
	}

	/**
	 * 补偿后缀, 即: s 如果不是以 postfix 作为后缀, 则加上该后缀;
	 * 如果是以postfix作为后缀, 则直接返回.
	 * 
	 * <pre>
	 * repairePostfix("asd", "11") == "asd11";
	 * repairePostfix("asd", "d") == "asd";
	 * repairePostfix("asd", "sd") == "asd";
	 * repairePostfix(null, "as") == null;
	 * </pre>
	 */
	public static String repairePostfix(String s, String postfix) {
		if (s == null) {
			return null;
		}
		if (postfix == null) {
			return s;
		}
		if (!s.endsWith(postfix)) {
			return s + postfix;
		} else {
			return s;
		}
	}

	/**
	 * s 如果是以 postfix 作为后缀, 则去掉该前缀;
	 * 如果不是以postfix作为后缀, 则直接返回.
	 * 
	 * <pre>
	 * cutPostfix("asd", "11") == "asd";
	 * cutPostfix("asd", "d") == "as";
	 * cutPostfix("asd", "sd") == "a";
	 * cutPostfix(null, "as") == null;
	 * </pre>
	 */
	public static String cutPostfix(String s, String postfix) {
		if (s == null) {
			return null;
		}
		if (postfix == null) {
			return s;
		}
		if (s.endsWith(postfix)) {
			return s.substring(0, s.length() - postfix.length());
		} else {
			return s;
		}
	}

	/**
	 * 将 prefix 直接补在 s 里的每个 String 前面
	 * 
	 * <pre>
	 * addPrefix(new String[]{"s1", "s2"}, "t1") == String[]{"t1s1", "t1s2"}
	 * </pre>
	 */
	public static String[] insertPrefix(String[] s, String prefix) {
		if (s == null || s.length == 0) {
			return EMPTY_STRING_ARRAY;
		}
		if (org.apache.commons.lang.StringUtils.isBlank(prefix)) {
			return s;
		}
		String[] result = copyArray(s);
		for (int i = 0; i < s.length; i++) {
			if (s[i] != null) {
				result[i] = prefix + s[i];
			}
		}
		return result;
	}

	/**
	 * 将c里的对象转换为 String
	 * 
	 * @deprecated 使用 toArray(Collection c);
	 */
	public static String[] asStringArray(Collection c) {
		return toArray(c);
	}

	/**
	 * 将 c 里的对象转换为 String.
	 * @throws ClassCastException 如果 c 里面的对象不是 String
	 */
	public static String[] toArray(Collection c) {
		if (c == null || c.size() == 0) {
			return EMPTY_STRING_ARRAY;
		}
		String[] s = new String[c.size()];
		Iterator iter = c.iterator();
		int i = 0;
		while (iter.hasNext()) {
			// 这里就写成 (String) 强制转换, 确保 c 里放的都是 String
			s[i++] = (String) iter.next();
		}
		return s;
	}

	/**
	 * 将s里的字符串转换为整型数组返回
	 */
	public static int[] toIntArray(String[] s) {
		if (s == null || s.length == 0) {
			return EMPTY_INT_ARRAY;
		}
		int[] result = new int[s.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = Integer.parseInt(s[i]);
		}
		return result;
	}

	/**
	 * 将整型数组转换为字符串数组返回
	 */
	public static String[] intToStringArray(int[] n) {
		if (n == null || n.length == 0) {
			return EMPTY_STRING_ARRAY;
		}
		String[] s = new String[n.length];
		for (int i = 0; i < n.length; i++) {
			s[i] = n[i] + "";
		}
		return s;
	}

	/**
	 * 用s填充sa
	 */
	public static void fill(String[] sa, String s) {
		for (int i = 0, n = sa.length; i < n; i++) {
			sa[i] = s;
		}
	}

	/**
	 * 返回一个数组, 数组里每个字符串都是 s , 长度为 size
	 */
	public static String[] fill(String s, int size) {
		String[] a = new String[size];
		fill(a, s);
		return a;
	}

	/**
	 * <p>将s重复times次返回
	 * 
	 * <pre>
	 * copy("12", 3) == "121212";
	 * </pre>
	 */
	public static String copy(String s, int times) {
		if (s == null) {
			return null;
		}
		if ("".equals(s)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < times; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * 带别名的字段拼装
	 * <pre>
	 * colsCombine(new String[]{"col1", "col2"}, new String[]{"al1", "al2"})
	 *  == "col1 al1,col2 al2"
	 * </pre>
	 * 
	 * <br>如果colAlias少于cols, 则不足的alias用对应的cols补齐
	 */
	public static String colsCombine(String[] cols, String[] colAlias) {
		if (cols.length < colAlias.length) {
			throw new IllegalArgumentException("cols数组的长度比别名数组的长度小");
		}
		if (cols.length == 0) {
			return "*";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0, n = cols.length; i < n; i++) {
			if (i != 0) {
				sb.append(",");
			}
			String s = (i + 1 > colAlias.length) ? cols[i] : colAlias[i];
			sb.append(cols[i]).append(" ").append(s);
		}
		return sb.toString();
	}

	/**
	 * 将Map m里的key toString填到keys里,
	 * 将Map m里的value toString填到values里
	 */
	public static void fillArrays(String[] keys, String[] values, Map m) {
		int i = 0;
		for (Iterator iter = m.keySet().iterator(); iter.hasNext(); i++) {
			Object key = iter.next();
			Object value = m.get(key);
			keys[i] = key.toString();
			values[i] = value == null ? null : value.toString();
		}
	}

	/**
	 * jakarta-common-lang StringUtils.escapeJavaString做了uncode转换.
	 * 我们不需要做这个, 因此去调了unicode转化部分
	 */
	public static void writeEscapedString(Writer out, String s)
			throws IOException {
		if (s == null) {
			return;
		}
		char[] c = s.toCharArray();
		writeEscapedString(out, c, 0, c.length);
	}

	public static void writeEscapedString(Writer out, char[] cha, int start,
			int end) throws IOException {
		for (int i = start; i < end; i++) {
			char ch = cha[i];
			if (ch > 0x7f) {
				out.write(ch);
			} else if (ch < 32) {
				switch (ch) {
				case '\b':
					out.write('\\');
					out.write('b');
					break;
				case '\n':
					out.write('\\');
					out.write('n');
					break;
				case '\t':
					out.write('\\');
					out.write('t');
					break;
				case '\f':
					out.write('\\');
					out.write('f');
					break;
				case '\r':
					out.write('\\');
					out.write('r');
					break;
				default:
					if (ch > 0xf) {
						out.write("\\u00" + hex(ch));
					} else {
						out.write("\\u000" + hex(ch));
					}
					break;
				}
			} else {
				switch (ch) {
				case '\'':
					out.write('\\');
					out.write('\'');
					break;
				case '"':
					out.write('\\');
					out.write('"');
					break;
				case '\\':
					out.write('\\');
					out.write('\\');
					break;
				default:
					out.write(ch);
					break;
				}
			}
		}
	}

	private static String hex(char ch) {
		return Integer.toHexString(ch).toUpperCase();
	}

	/**
	 * 返回 str 开始部分截取了start, 结束部分截取了end 的剩余部分
	 * jakarta-commons-lang2.1 StringUtils.substringBetween(String str, String open, String close);
	 * 方法和这个方法有一些区别:
	 * 
	 * jakarta:
	 * <pre>
	 * StringUtils.substringBetween("yabczyabcz", "y", "z") = "abc"
	 * </pre>
	 * 
	 * 本方法:
	 * <pre>
	 * StringUtils.substringWithin("yabczyabcz", "y", "z") = "abczyabc"
	 * </pre>
	 */
	public static String substringWithin(String str, String start, String end) {
		if (str.startsWith(start) && str.endsWith(end)) {
			return str.substring(start.length(), str.length() - end.length());
		} else {
			if (log.isDebugEnabled()) {
				log.debug("str [" + str + "] do not start with [" + start
						+ "] and end with [" + end + "]");
			}
			return null;
		}
	}

	/**
	 * <p>拼装一个 or 条件 SQL 语句. 如果values的值中有 null, 将被忽略
	 * 
	 * <pre>
	 * orCondition("col1", new String[]{"val1", "val2"}) == "col1='val1' or col1='val2'";
	 * </pre>
	 * 
	 * @return "" 如果values.length == 0
	 */
	public static String orCondition(String col, String[] values) {
		return orCondition(col, values, true);
	}

	/**
	 * 如果 ignoreNull == false, 则 values 中出现 null 值时, 将解释成SQL: is null
	 */
	public static String orCondition(String col, String[] values,
			boolean ignoreNull) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			if (ignoreNull && values[i] == null) {
				continue;
			}
			if (i != 0) {
				sb.append(" or ");
			}
			if (values[i] != null) {
				sb.append(col).append("='").append(values[i]).append("'");
			} else {
				sb.append(col).append(" is null");
			}
		}
		return sb.toString();
	}

	/**
	 * on 条件 SQL 拼装
	 * <pre>
	 * onCondition(new String[]{"col1", "col2"}, "t1", "t2")
	 *  == "t1.col1=t2.col1,t1.col2=t2.col2"
	 * </pre>
	 */
	public static String onCondition(String[] cols, String table1, String table2) {
		return onCondition(cols, table1, table2, ",");
	}

	/**
	 * on 条件 SQL 拼装
	 * <pre>
	 * onCondition(new String[]{"col1", "col2"}, "t1", "t2", "AND")
	 *  == "t1.col1=t2.col1 AND t1.col2=t2.col2"
	 * </pre>
	 */
	public static String onCondition(String[] cols, String table1,
			String table2, String condition) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cols.length; i++) {
			if (i != 0) {
				sb.append(condition);
			}
			sb.append(table1).append(".").append(cols[i]).append("=").append(
					table2).append(".").append(cols[i]);
		}
		return sb.toString();
	}

	/**
	 * in 条件 SQL 拼装
	 * 
	 * <pre>
	 * inCondition("col1", new String[]{"value1", "value2"})
	 *  == "col1 in ('value1','value2')";
	 * </pre>
	 * 
	 * <pre>
	 * inCondition("col1", new String[]{"value1"})
	 *  == "col1='value1'";
	 * </pre>
	 * 
	 * @return "1=2" if values is null or length == 0
	 * 
	 * TODO chenke LOW MySQL 的 in SQL 里支持多少个值?
	 */
	public static String inCondition(String colName, String[] values) {
		return inCondition(colName, values, 0, values.length, true);
	}

	public static String inCondition(String colName, int[] values) {
		if (values == null || values.length == 0) {
			return "1=2";
		}
		String[] s = intToStringArray(values);
		/**
		 * 支持假如只有一个值是的情况。
		 * @author fla
		 */
		if(s.length==1){
			return colName+"="+s[0];
		}
		return inCondition(colName, s, 0, s.length, false);
	}

	protected static String inCondition(String colName, String[] values,
			int start, int end, boolean addQuote) {
		if (values == null || values.length == 0) {
			return "1=2";
		}
		if (end > values.length) {
			end = values.length;
		}
		// oracle sql 要求 in 里面的字符不能超过 1000 个
		int max = 1000;
		if (end - start > max) {
			StringBuffer sb = new StringBuffer("(");
			int n = values.length / max + (values.length % max == 0 ? 0 : 1);
			for (int i = 0; i < n; i++) {
				int j = max * i;
				int k = max * (i + 1);
				if (i != 0) {
					sb.append(" or ");
				}
				sb.append(inCondition(colName, values, j, k, addQuote));
			}
			sb.append(")");
			return sb.toString();
		}
		StringBuffer sb = new StringBuffer();
		sb.append(colName).append(" in (");
		for (int i = start; i < end; i++) {
			if (values[i] == null) {
				continue;
			}
			if (i != start) {
				sb.append(",");
			}
			if (addQuote) {
				sb.append("'").append(values[i]).append("'");
			} else {
				sb.append(values[i]);
			}
		}
		sb.append(")");
		return sb.toString();
	}

	/**
	 * not in 条件 SQL 拼装
	 * 
	 * <pre>
	 * inCondition("col1", new String[]{"value1", "value2"})
	 *  == "col1 in ('value1','value2')";
	 * </pre>
	 * 
	 * @return null if values is null or length == 0
	 */
	public static String notInCondition(String colName, String[] values) {
		if (values == null || values.length == 0) {
			return null;
		}
		return notInCondition(colName, values, 0, values.length, true);
	}

	protected static String notInCondition(String colName, String[] values,
			int start, int end, boolean addQuote) {
		if (end > values.length) {
			end = values.length;
		}
		// oracle sql 要求 in 里面的字符不能超过 1000 个
		int max = 1000;
		if (end - start > max) {
			StringBuffer sb = new StringBuffer("(");
			int n = values.length / max + (values.length % max == 0 ? 0 : 1);
			for (int i = 0; i < n; i++) {
				int j = max * i;
				int k = max * (i + 1);
				if (i != 0) {
					sb.append(" and ");
				}
				sb.append(inCondition(colName, values, j, k, addQuote));
			}
			sb.append(")");
			return sb.toString();
		}
		StringBuffer sb = new StringBuffer();
		sb.append(colName).append(" not in (");
		for (int i = start; i < end; i++) {
			if (values[i] == null) {
				continue;
			}
			if (i != start) {
				sb.append(",");
			}
			if (addQuote) {
				sb.append("'").append(values[i]).append("'");
			} else {
				sb.append(values[i]);
			}
		}
		sb.append(")");
		return sb.toString();
	}

	public static String[] minus(String[] left, String[] right) {
		return minus(left, right, false);
	}

	/**
	 * 返回 left - right 的结果
	 * <pre>
	 * minus(new String[]{"col1", "col2"}, new String[]{"col1"}) == "col2"
	 * </pre>
	 */
	public static String[] minus(String[] left, String[] right,
			boolean ignoreCase) {
		List l = new ArrayList(left.length);
		for (int i = 0; i < left.length; i++) {
			l.add(left[i]);
		}
		for (int i = 0; i < right.length; i++) {
			for (int j = 0; j < left.length; j++) {
				boolean match = false;
				match = match(left[j], right[i], ignoreCase);
				if (!match) {
					continue;
				} else {
					l.remove(left[j]);
					break;// go to the filter loop
				}
			}
		}
		return asStringArray(l);
	}

	public static boolean match(String s1, String s2, boolean ignoreCase) {
		if (ignoreCase) {
			return s1 == null ? s2 == null : s1.equalsIgnoreCase(s2);
		} else {
			return s1 == null ? s2 == null : s1.equals(s2);
		}
	}

	public static String[] intersects(String[] left, String[] right,
			boolean ignoreCase) {
		List l = new ArrayList(left.length);
		for (int i = 0; i < left.length; i++) {
			l.add(left[i]);
		}
		for (int i = 0; i < left.length; i++) {
			boolean find = false;
			for (int j = 0; j < right.length; j++) {
				boolean match = false;
				match = match(left[i], right[j], ignoreCase);
				if (match) {
					find = true;
					break;
				}
			}
			if (!find) {
				l.remove(left[i]);
			}
		}
		return asStringArray(l);
	}

	/**
	 * <p>计算s的末尾有多少个重复的match
	 * 
	 * <pre>
	 * countEndStr("1235656", "56") == 2;
	 * </pre>
	 */
	public static int countEndStr(String s, String match) {
		int i = 0;
		for (; s.endsWith(match); s = s.substring(0, s.length()
				- match.length())) {
			i++;
		}
		return i;
	}

	/**
	 * 将s里的所有字符串trim一次
	 * 如果s为null,则返回null.
	 * 
	 * @deprecated 使用 jakarta-commons-lang2.1 StringUtils.stripAll(String[] strs);
	 */
	public static String[] trim(String[] s) {
		//高泗朋 modified 11-01
		if (s == null)
			return null;
		for (int i = 0; i < s.length; i++) {
			s[i] = s[i] == null ? null : s[i].trim();
		}
		return s;
	}

	/**
	 * 在array里查出第一个和valueToFind相等的索引
	 * @return -1 如果没有相等的
	 */
	public static int indexOf(String[] array, String valueToFind,
			boolean caseSensitive) {
		if (valueToFind == null) {
			return -1;
		}
		for (int i = 0; i < array.length; i++) {
			if (caseSensitive) {
				if (valueToFind.equals(array[i])) {
					return i;
				}
			} else {
				if (valueToFind.equalsIgnoreCase(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * 在array里查出第一个和valueToFind相等的索引. 大小写敏感
	 * @return -1 如果没有相等的
	 */
	public static int indexOf(String[] array, String valueToFind) {
		return indexOf(array, valueToFind, true);
	}

	/**
	 * 如果 superset 完全包含 subset, 返回 true.
	 * 如果 superset == null 或者 subset == null 都返回 false.
	 */
	public static boolean containsAll(String[] superset, String[] subset,
			boolean caseSensitive) {
		if (superset == null || subset == null) {
			return false;
		}
		for (int i = 0; i < subset.length; i++) {
			if (indexOf(superset, subset[i], caseSensitive) == -1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 如果 superset 完全包含 subset, 返回 true.
	 * 如果 superset == null 或者 subset == null 都返回 false.
	 * 字符串比较大小写敏感
	 */
	public static boolean containsAll(String[] superset, String[] subset) {
		return containsAll(superset, subset, true);
	}

	/**
	 * 去掉重复的字符串
	 * 
	 * <pre>
	 * unique(new String[]{"s1", "s2", "s3", "s1"}) = String[]{"s1", "s2", "s3"};
	 * </pre>
	 * 不考虑 array 中包含 null 的情况
	 */
	public static String[] unique(final String[] array, boolean caseSensitive) {
		String[] copy = copyArray(array);
		for (int i = 0; i < copy.length; i++) {
			for (int j = 0; j < copy.length; j++) {
				if (i == j) {
					continue;
				}
				if (caseSensitive) {
					if (copy[i] != null && copy[i].equals(copy[j])) {
						copy[j] = null;
					}
				} else {
					if (copy[i] != null && copy[i].equalsIgnoreCase(copy[j])) {
						copy[j] = null;
					}
				}
			}
		}
		List l = new ArrayList(copy.length);
		for (int i = 0; i < copy.length; i++) {
			if (copy[i] != null) {
				l.add(copy[i]);
			}
		}
		return asStringArray(l);
	}

	/**
	 * 大小写敏感的unique
	 */
	public static String[] unique(final String[] array) {
		return unique(array, true);
	}

	/**
	 * 返回source的一个拷贝
	 */
	public static String[] copyArray(String[] source) {
		String[] result = new String[source.length];
		for (int i = 0; i < source.length; i++) {
			result[i] = source[i];
		}
		return result;
	}

	/**
	 * 将s1 + s2的结果返回
	 * 
	 * <pre>
	 * plus(new String[]{"1"}, new String[]{"2"}) == String[]{"1", "2"};
	 * </pre>
	 * 
	 * @return String[]{} if s1 == null and s2 == null
	 */
	public static String[] plus(final String[] s1, final String[] s2) {
		if (s1 == null) {
			return s2 == null ? EMPTY_STRING_ARRAY : s2;
		} else if (s2 == null) {
			return s1;
		}
		String[] s = new String[s1.length + s2.length];
		for (int i = 0; i < s1.length; i++) {
			s[i] = s1[i];
		}
		for (int i = 0, j = s1.length; i < s2.length; i++, j++) {
			s[j] = s2[i];
		}
		return s;
	}

	/**
	 * 判断两个 String[] 对象是否相等
	 */
	public static boolean isEquals(String[] array1, String[] array2,
			boolean ignoreCase) {
		if (array1 == null || array2 == null) {
			return array1 == array2;
		}
		if (array1.length != array2.length) {
			return false;
		}
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] == null) {
				if (array2[i] != null) {
					return false;
				}
			} else {
				if (ignoreCase) {
					if (!array1[i].equalsIgnoreCase(array2[i])) {
						return false;
					}
				} else {
					if (!array1[i].equals(array2[i])) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean isEquals(String[] array1, String[] array2) {
		return isEquals(array1, array2, false);
	}

	/**
	 * 返回一个将 s 重复复制了 repeatTimes 次的字符串
	 * 
	 * <pre>
	 * repeat("12", 3) == "121212";
	 * </pre>
	 * 
	 * @deprecated 使用 jakarta-commons-lang2.1 StringUtils.repeat(String str, int repeat);
	 */
	public static String repeat(String s, int repeatTimes) {
		if (s == null) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < repeatTimes; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * 返回约束条件 col = value
	 * <pre>
	 * colsEqualsValues(new String[]{"col1", "col2"}, new String[]{"val1", "val2"})
	 *  == "col1='val1' and col2='val2'"
	 * </pre>
	 */
	public static String colsEqualsValues(String[] cols, String[] values) {
		if (cols == null || cols.length == 0 || values == null
				|| values.length == 0) {
			return null;
		}
		if (cols.length != values.length) {
			throw new IllegalArgumentException("字段数组和字段值数组长度应该相等");
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cols.length; i++) {
			if (i != 0) {
				sb.append(" and ");
			}
			sb.append(cols[i]).append("='").append(values[i]).append("'");
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * whereClause(new String[]{"col1", "col2"}) == "col1=? and col2=?"
	 * </pre>
	 */
	public static String whereClause(String[] cols) {
		if (cols == null || cols.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cols.length; i++) {
			if (i != 0) {
				sb.append(" and ");
			}
			sb.append(cols[i]).append("=?");
		}
		return sb.toString();
	}

	/**
	 * <pre>
	 * updateClause(new String[]{"col1", "col2"}) == "col1=?,col2=?"
	 * </pre>
	 */
	public static String updateClause(String[] cols) {
		if (cols == null || cols.length == 0) {
			throw new IllegalArgumentException("错误:空的update字段");
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < cols.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(cols[i]).append("=?");
		}
		return sb.toString();
	}

	/**
	 * 将 s 解析成整型值返回, 
	 * 如果 s 为空或解析出现 NumberFormatException, 则返回 defaultValue
	 */
	public static int getIntValue(String s, int defaultValue) {
		if (org.apache.commons.lang.StringUtils.isBlank(s)) {
			return defaultValue;
		}
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static int getIntValue(String s) {
		return getIntValue(s, 0);
	}

	/**
	 * 判断一个字符串是否是数字
	 * @param s
	 * @return boolean 是否
	 * 
	 * @deprecated 使用 jakarta-commons-lang2.1 StringUtils.isNumeric(String str);
	 */
	public static boolean isNumber(String s) {
		if (org.apache.commons.lang.StringUtils.isBlank(s)) {
			return false;
		}
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	/**
	 * Oracle SQL lpad 函数的 java 实现
	 * 
	 * <pre>
	 * lpad("123", 6, "0") == "000123";
	 * lpad("123", 6, "98") == "989123";
	 * </pre>
	 * 
	 * @deprecated 使用 jakarta-commons-lang2.1 StringUtils.leftPad(String str, int size, String padStr);
	 */
	public static String lpad(String str, int length, String add) {
		if (str.length() >= length) {
			return str;
		}
		int i = length - str.length();
		int repeatTimes = i / add.length();
		String s = repeat(add, repeatTimes);
		int j = length - str.length() - s.length();
		String s2 = "";
		if (j > 0) {
			s2 = add.substring(0, j);
		}
		StringBuffer sb = new StringBuffer(length);
		sb.append(s).append(s2).append(str);
		return sb.toString();
	}

	/**
	 * Oracle SQL rpad 函数的 java 实现
	 * 
	 * <pre>
	 * rpad("123", 6, "0") == "123000";
	 * rpad("123", 6, "98") == "123989";
	 * </pre>
	 * 
	 * @deprecated 使用 jakarta-commons-lang2.1 StringUtils.rightPad(String str, int size, String padStr);
	 */
	public static String rpad(String str, int length, String add) {
		if (str.length() >= length) {
			return str;
		}
		int i = length - str.length();
		int repeatTimes = i / add.length();
		String s = repeat(add, repeatTimes);
		int j = length - str.length() - s.length();
		String s2 = "";
		if (j > 0) {
			s2 = add.substring(0, j);
		}
		StringBuffer sb = new StringBuffer(length);
		sb.append(str).append(s).append(s2);
		return sb.toString();
	}

	/**
	 * 如果 s 的长度超过了长度 maxSize, 则截取最后一部分, 并补上 postFix
	 * 
	 * @param maxSize s 的最大字节长度
	 */
	public static String cut(String s, int maxSize, String postFix) {
		if (s == null) {
			return null;
		}
		if (maxSize < 1) {
			throw new IllegalArgumentException("maxSize [" + maxSize
					+ "] 应该 > 0");
		}
		if (postFix == null) {
			postFix = "";
		}
		if (postFix.getBytes().length > maxSize) {
			throw new IllegalArgumentException("postFix [" + postFix
					+ "] 长度为 [" + postFix.length() + "], 不能大于 maxSize ["
					+ maxSize + "]");
		}
		int newsize = maxSize - postFix.getBytes().length;
		String result = trim2size(s, newsize, true);
		return result + postFix;

		//        if (s.getBytes().length > maxSize) {
		//            int postFixSize = postFix.getBytes().length;
		//            int n = maxSize - postFixSize;
		//            StringBuffer sb = new StringBuffer(n);
		//            int startIndex = n / 2 - 1;
		//            String s1 = s.substring(0, startIndex);
		//            sb.append(s1);
		//            int size = s1.getBytes().length;
		//            for (;;) {
		//                String s2 = s.substring(startIndex, startIndex + 1);
		//                startIndex++;
		//                size += s2.getBytes().length;
		//                if (size > n) {
		//                    break;
		//                }
		//                sb.append(s2);
		//            }
		//            sb.append(postFix);
		//            s = sb.toString();
		//        }
		//        return s;
	}

	/**
	 * 将字符串 str 裁减到 size 个字节. 对多字节的汉字做处理, 避免出现一个汉字
	 * 的两个字节被从中间裁减了.
	 * @param str 要被裁减的字符串
	 * @param size 裁减后的字节数
	 * @param tail true, 裁减末尾部分的字符; false, 裁减开头部分的字符
	 * @return 裁减后的字符串
	 */
	public static String trim2size(String str, int size, boolean tail) {
		System.out.println("str="+str+"----size="+size+"---tail="+tail);
		if (str == null) {
			return null;
		}
		if (size <= 0) {
			return "";
		}
		int len = str.getBytes().length;
		System.out.println("---------len="+len);
		if (len <= size) {
			return str;
		}
		int n = len - size; // number of bytes need to be trim
		StringBuffer result = new StringBuffer(size);
		if (tail) {
			int startIndex = (len - n) / 2 - 1;
			if(startIndex < 0) {
				startIndex = 0;
			}
			//            startIndex
			String s0 = str.substring(0, startIndex);
			result.append(s0);
			int currentSize = s0.getBytes().length;
			for (;;) {
				String s = str.substring(startIndex, ++startIndex);
				currentSize += s.getBytes().length;
				if (currentSize > size) {
					break;
				}
				result.append(s);
			}
		} else {
			int startIndex = len - size;
			if(startIndex > str.length()) {
				startIndex = str.length();
			}
			int _str_size = str.length();
			String s0 = str.substring(startIndex);
			result.append(s0);
			int currentSize = s0.getBytes().length;
			for (;;) {
				String s = str.substring(startIndex - 1, startIndex);
				startIndex--;
				currentSize += s.getBytes().length;
				if (currentSize > size) {
					break;
				}
				result.insert(0, s);
			}
		}
		return result.toString();
	}

	public static String cut(String s, int maxSize) {
		return cut(s, maxSize, "");
	}

	/**
	 * 将 s 按照每一行最长 linelength 划分为 String[]
	 * <pre>
	 * toMultilines("12345", 2) = {"12", "34", "5"}
	 * </pre>
	 */
	public static String[] toMultilines(String s, int linelength) {
		if (s == null) {
			return EMPTY_STRING_ARRAY;
		}
		int n1 = s.length() % linelength;
		int n2 = s.length() / linelength;
		int length = n1 == 0 ? n2 : n2 + 1;
		String[] result = new String[length];
		for (int i = 0, index = 0; i < length; i++, index = index + linelength) {
			int endIndex = index + linelength;
			if (i == length - 1) {
				endIndex = s.length();
			}
			result[i] = s.substring(index, endIndex);
		}
		return result;
	}

	/**
	 * 将s里的object类型转换为字符串型数组返回
	 * @author fla
	 */
	public static String[] toStringArray(Object[] s) {
		if (s == null || s.length == 0) {
			return EMPTY_STRING_ARRAY;
		}
		String[] result = new String[s.length];
		for (int i = 0; i < s.length; i++) {
			result[i] = s[i].toString();
		}
		return result;
	}

	/**
	 * 去除重复的元素
	 * @param array
	 */
	public static String[] trimDuplicate(String[] array) {
		List list = new ArrayList();

		for (int i = 0; i < array.length; i++) {
			if (list.contains(array[i])) {
				continue;
			}
			list.add(array[i]);
		}
		return (String[]) list.toArray(new String[] {});

	}

	/**
	 * <p>拼装一个 like 条件 SQL 语句. 如果values的值中有 null, 将被忽略
	 * 
	 * <pre>
	 * likeCondition("col1", new String[]{"val1", "val2"}) == "col1 like 'val1' or col1 like 'val2'";
	 * </pre>
	 * 
	 * @return "" 如果values.length == 0
	 * @author fla
	 */
	public static String likeCondition(String col, String[] values) {
		return likeCondition(col, values, true);
	}

	/**
	 * 如果 ignoreNull == false, 则 values 中出现 null 值时, 将解释成SQL: is null
	 */
	public static String likeCondition(String col, String[] values,
			boolean ignoreNull) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			if (ignoreNull && values[i] == null) {
				continue;
			}
			if (i != 0) {
				sb.append(" or ");
			}
			if (values[i] != null) {
				sb.append(col).append(" like '").append(values[i]).append("'");
			} else {
				sb.append(col).append(" is null");
			}
		}
		return sb.toString();
	}

	
	/**
	 * <p>拼装一个 like 条件 SQL 语句. 如果values的值中有 null, 将被忽略
	 * 
	 * <pre>
	 * notLikeCondition("col1", new String[]{"val1", "val2"}) == "col1 not like 'val1' and col1 not like 'val2'";
	 * </pre>
	 * 
	 * @return "" 如果values.length == 0
	 * @author fla
	 */
	public static String notLikeCondition(String col, String[] values) {
		return notLikeCondition(col, values, true);
	}

	/**
	 * 如果 ignoreNull == false, 则 values 中出现 null 值时, 将解释成SQL: is null
	 */
	public static String notLikeCondition(String col, String[] values,
			boolean ignoreNull) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			if (ignoreNull && values[i] == null) {
				continue;
			}
			if (i != 0) {
				sb.append(" and  ");
			}
			if (values[i] != null) {
				sb.append(col).append(" not like '").append(values[i]).append("'");
			} else {
				sb.append(col).append(" is null");
			}
		}
		return sb.toString();
	}
}
