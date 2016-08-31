/**
 * 
 */
package com.cmcc.anal.common.spring;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.FileCopyUtils;
/**
 * 
 * 
 * @filename:	 com.cmcc.anal.common.spring.UTF8StringHttpMessageConverter
 * @copyright:   Copyright (c)2014
 * @company:     北京掌中无限信息技术有限公司
 * @author:      张占亮
 * @version:     1.0
 * @create time: 2014-7-5 下午11:08:56
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
public class UTF8StringHttpMessageConverter extends StringHttpMessageConverter {
	private static final MediaType utf8 = new MediaType("text", "plain",
			Charset.forName("UTF-8"));
	private boolean writeAcceptCharset = true;

	@Override
	protected MediaType getDefaultContentType(String dumy) {
		return utf8;
	}

	protected List<Charset> getAcceptedCharsets() {
		return Arrays.asList(utf8.getCharSet());
	}

	protected void writeInternal(String s, HttpOutputMessage outputMessage)
			throws IOException {
		if (this.writeAcceptCharset) {
			outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
		}
		Charset charset = utf8.getCharSet();
		FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(),
				charset));
	}

	public boolean isWriteAcceptCharset() {
		return writeAcceptCharset;
	}

	public void setWriteAcceptCharset(boolean writeAcceptCharset) {
		this.writeAcceptCharset = writeAcceptCharset;
	}

}
