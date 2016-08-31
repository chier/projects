/**
 * 
 */
package com.cmcc.anal.common.spring.converter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
/**
 * 
 * 
 * @filename:	 com.cmcc.anal.common.spring.converter.DateConverter
 * @copyright:   Copyright (c)2014
 * @company:     北京掌中无限信息技术有限公司
 * @author:      张占亮
 * @version:     1.0
 * @create time: 2014-7-5 下午11:06:29
 * @record
 * <table cellPadding="3" cellSpacing="0" style="width:600px">
 * <thead style="font-weight:bold;background-color:#e3e197">
 * 	<tr>   <td>date</td>	<td>author</td>		<td>version</td>	<td>description</td></tr>
 * </thead>
 * <tbody style="background-color:#ffffeb">
 * 	<tr><td>2014-7-5</td>	<td>张占亮</td>	<td>1.0</td>	<td>create</td></tr>
 * </tbody>
 * </table>
 */
public class DateConverter implements Converter<String, Date> {
    
    private static Logger logger = LoggerFactory.getLogger(DateConverter.class);
    
    private static String yyyy_MM_dd =  "yyyy-MM-dd";
    private static String yyyy_MM_dd_HH_mm_ss =  "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date convert(String source)
    {
        SimpleDateFormat df = null;
        if (StringUtils.isBlank(source)) {
            return null;
        } else if (source.length() == yyyy_MM_dd.length()){
            df = new SimpleDateFormat(yyyy_MM_dd);
        } else if (source.length() == yyyy_MM_dd_HH_mm_ss.length()){
            df = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        } else {
        	logger.error("can not convert, the source string is {}", source);
            return null;
        }
        try
        {
            return df.parse(source);
        } catch (ParseException e)
        {
        	logger.error("can not convert, the source string is " + source , e);
            return null;
        }
    }
}