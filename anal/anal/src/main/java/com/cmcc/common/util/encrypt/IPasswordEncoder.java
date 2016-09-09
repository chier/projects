/*
 * 文件名： IPasswordEncoder.java
 * 
 * 创建日期： 2008-2-26
 *
 * Copyright(C) 2008, by Along.
 *
 * 原始作者: <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 */
package com.cmcc.common.util.encrypt;

/**
 * 为用户密码加密的类必须实现此接口。
 *
 * @author <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-2-26
 */
public interface IPasswordEncoder {

	/**
	 * 给指定字符串加密，并返回加密后的密文。
	 * 
	 * @param inStr
	 *            要被加密的字符串
	 * @return 加密后的字符串
	 */
	public String encode(String inStr);

	/**
	 * 给指定字符串解密，并返回解密后的原文。
	 * 
	 * @param inStr
	 *            要被解密的字符串
	 * @return 解密后的字符串
	 */
	public String decode(String inStr);
}
