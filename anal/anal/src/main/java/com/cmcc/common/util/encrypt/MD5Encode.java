/*
 * 文件名： MD5Encode.java
 * 
 * 创建日期： 2008-2-26
 *
 * Copyright(C) 2008, by Along.
 *
 * 原始作者: <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 */
package com.cmcc.common.util.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.cmcc.common.util.StringUtil;

/**
 * MD5加密的Java类
 *
 * @author <a href="mailto:xiaozhi19820323@hotmail.com">xiaozhi</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2008-2-26
 */
public class MD5Encode {

	/**
	 * 将指定的字符串用MD5算法加密
	 * 
	 * @param inStr
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String MD5Encrypt(String inStr) {
		MessageDigest md = null;
		String outStr = null;
		try {
			md = MessageDigest.getInstance("MD5"); // 可以选中其他的算法如SHA
			byte[] digest = md.digest(inStr.getBytes()); // 返回的是byet[]，要转化为String存储比较方便
			outStr = StringUtil.bytetoString(digest);
		}
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return outStr;
	}
}
