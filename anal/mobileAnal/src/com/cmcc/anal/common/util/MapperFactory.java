/**
 * 
 */
package com.cmcc.anal.common.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * @filename:	 com.pica.wificity.util.MapperFactory
 * @copyright:   Copyright (c)2010
 * @company:     talent
 * @author:      谭耀武
 * @version:     1.0
 * @create time: 2012-4-19 下午2:00:38
 * @record
 * <table cellPadding="3" cellSpacing="0" style="width:600px">
 * <thead style="font-weight:bold;background-color:#e3e197">
 * 	<tr>   <td>date</td>	<td>author</td>		<td>version</td>	<td>description</td></tr>
 * </thead>
 * <tbody style="background-color:#ffffeb">
 * 	<tr><td>2012-4-19</td>	<td>谭耀武</td>	<td>1.0</td>	<td>create</td></tr>
 * </tbody>
 * </table>
 */
public class MapperFactory {
	private static Logger log = LoggerFactory.getLogger(MapperFactory.class);
	
	public static <T> T getMapper(Class<T> t) {
		T mapper = WebSpringUtils.getBean(t.getSimpleName(), t);
		return mapper;
	}

	/**
	 * 
	 */
	public MapperFactory() {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}
}


