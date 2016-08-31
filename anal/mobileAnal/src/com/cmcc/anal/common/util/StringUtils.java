/**
 * 
 */
package com.cmcc.anal.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @filename:	 com.pica.wificity.util.StringUtils
 * @copyright:   Copyright (c)2010
 * @company:     talent
 * @author:      谭耀武
 * @version:     1.0
 * @create time: 2012-10-7 下午2:19:54
 * @record
 * <table cellPadding="3" cellSpacing="0" style="width:600px">
 * <thead style="font-weight:bold;background-color:#e3e197">
 * 	<tr>   <td>date</td>	<td>author</td>		<td>version</td>	<td>description</td></tr>
 * </thead>
 * <tbody style="background-color:#ffffeb">
 * 	<tr><td>2012-10-7</td>	<td>谭耀武</td>	<td>1.0</td>	<td>create</td></tr>
 * </tbody>
 * </table>
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
	private static Logger log = LoggerFactory.getLogger(StringUtils.class);

	/**
	 * 
	 */
	public StringUtils() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String xx = "\r\t\t hello tanyaowu \t\t wumy \t\t\t\r\n  ";
		System.out.println(mytrim(xx));
	}

	/**
	 * 
	 * @param src
	 * @return
	 */
	public static String mytrim(String src) {
		if (src == null) {
			return null;
		}

		if (isBlank(src)) {
			return src.trim();
		}

		String ret = org.springframework.util.StringUtils.trimLeadingCharacter(src, '\t');
		ret = org.springframework.util.StringUtils.trimTrailingCharacter(src, '\t');
		
		ret = org.springframework.util.StringUtils.trimTrailingCharacter(src, '\r');
		ret = org.springframework.util.StringUtils.trimTrailingCharacter(src, '\n');
		
		ret = org.springframework.util.StringUtils.trimLeadingCharacter(src, '\r');
		ret = org.springframework.util.StringUtils.trimLeadingCharacter(src, '\n');
		return trim(ret);

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
}
